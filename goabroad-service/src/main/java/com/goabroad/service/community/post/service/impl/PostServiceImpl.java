package com.goabroad.service.community.post.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.goabroad.common.exception.BusinessException;
import com.goabroad.common.pojo.ResultCode;
import com.goabroad.model.community.post.dto.CreatePostDto;
import com.goabroad.model.community.post.entity.Post;
import com.goabroad.model.community.post.entity.PostTag;
import com.goabroad.model.community.tag.entity.Tag;
import com.goabroad.model.user.entity.User;
import com.goabroad.model.community.post.enums.PostStatus;
import com.goabroad.model.community.post.vo.PostDetailVo;
import com.goabroad.service.community.post.repository.PostRepository;
import com.goabroad.service.community.post.repository.PostTagRepository;
import com.goabroad.service.community.post.repository.TagRepository;
import com.goabroad.service.community.post.service.PostService;
import com.goabroad.service.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 帖子服务实现类
 * 
 * @author GoAbroad Team
 * @version 1.0
 * @since 2024-10-28
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final TagRepository tagRepository;
    private final PostTagRepository postTagRepository;
    
    /**
     * 发布帖子
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public PostDetailVo createPost(Long userId, CreatePostDto dto) {
        // 1. 查询用户
        User user = userRepository.findById(userId)
                .orElseThrow(() -> BusinessException.of(ResultCode.USER_NOT_FOUND));
        
        // 2. 合并图片和视频到mediaUrls
        List<String> mediaUrls = new ArrayList<>();
        if (dto.getImages() != null && !dto.getImages().isEmpty()) {
            mediaUrls.addAll(dto.getImages());
        }
        if (dto.getVideos() != null && !dto.getVideos().isEmpty()) {
            mediaUrls.addAll(dto.getVideos());
        }
        
        // 3. 构建帖子实体
        Post post = Post.builder()
                .authorId(userId)
                .contentType(dto.getContentType())
                .title(dto.getTitle())
                .content(dto.getContent())
                .status(dto.getStatus() != null ? dto.getStatus() : PostStatus.PUBLISHED)
                .coverImage(dto.getCoverImage())
                .mediaUrls(mediaUrls.isEmpty() ? null : mediaUrls)
                .countryCode(dto.getCountry())
                .category(dto.getStage())
                .publishedAt(dto.getStatus() == PostStatus.PUBLISHED ? LocalDateTime.now() : null)
                .build();
        
        // 设置BaseEntity的字段（deleted已在BaseEntity中有默认值false）
        post.setDeleted(false);
        
        // 4. 保存帖子
        post = postRepository.save(post);
        
        // 5. 处理标签
        if (CollUtil.isNotEmpty(dto.getTags())) {
            List<Tag> savedTags = processAndSaveTags(dto.getTags(), post.getId());
            log.info("帖子{}关联了{}个标签", post.getId(), savedTags.size());
        }
        
        // 6. 如果是已发布状态，更新用户的发帖数
        if (post.getStatus() == PostStatus.PUBLISHED) {
            user.setPostCount(user.getPostCount() + 1);
            userRepository.save(user);
        }
        
        log.info("用户{}发布了帖子，帖子ID: {}, 标题: {}", userId, post.getId(), post.getTitle());
        
        // 7. 转换为VO并返回
        return convertToPostDetailVo(post, user);
    }
    
    /**
     * 处理并保存标签
     * 
     * @param tagNames 标签名称列表
     * @param postId 帖子ID
     * @return 保存后的标签列表
     */
    private List<Tag> processAndSaveTags(List<String> tagNames, Long postId) {
        List<Tag> savedTags = new ArrayList<>();
        
        for (String tagName : tagNames) {
            // 跳过空标签
            if (StrUtil.isBlank(tagName)) {
                continue;
            }
            
            // 清理标签名称（使用final变量）
            final String cleanedTagName = tagName.trim();
            
            // 查找或创建标签
            Tag tag = tagRepository.findByNameAndDeleted(cleanedTagName, false)
                    .map(existingTag -> {
                        // 标签已存在，增加帖子计数
                        existingTag.setPostCount(existingTag.getPostCount() + 1);
                        return tagRepository.save(existingTag);
                    })
                    .orElseGet(() -> {
                        // 创建新标签
                        Tag newTag = Tag.builder()
                                .name(cleanedTagName)
                                .slug(generateSlug(cleanedTagName))
                                .postCount(1)
                                .build();
                        newTag.setDeleted(false);
                        return tagRepository.save(newTag);
                    });
            
            // 创建帖子-标签关联
            PostTag postTag = PostTag.builder()
                    .postId(postId)
                    .tagId(tag.getId())
                    .build();
            postTag.setDeleted(false);
            postTagRepository.save(postTag);
            
            savedTags.add(tag);
        }
        
        return savedTags;
    }
    
    /**
     * 生成标签的slug（URL友好标识）
     * 
     * @param tagName 标签名称
     * @return slug
     */
    private String generateSlug(String tagName) {
        // 简单实现：转小写并替换空格为连字符
        return tagName.toLowerCase()
                .replaceAll("\\s+", "-")
                .replaceAll("[^a-z0-9\\u4e00-\\u9fa5-]", "");
    }
    
    /**
     * 转换为帖子详情VO
     */
    private PostDetailVo convertToPostDetailVo(Post post, User user) {
        // 构建作者信息
        PostDetailVo.AuthorVo authorVo = PostDetailVo.AuthorVo.builder()
                .id(user.getId())
                .username(user.getUsername())
                .nickname(user.getNickname())
                .avatarUrl(user.getAvatarUrl())
                .bio(user.getBio())
                .level(user.getLevel())
                .badges(generateBadges(user))
                .isFollowing(false) // 自己发的帖子，不需要关注状态
                .build();
        
        // 解析JSON字段
        List<String> mediaUrls = post.getMediaUrls() != null ? post.getMediaUrls() : new ArrayList<>();
        
        // 简单处理：所有媒体URL都作为images返回（后续可优化区分图片和视频）
        List<String> images = new ArrayList<>(mediaUrls);
        List<String> videos = new ArrayList<>();
        
        // 构建帖子详情VO
        return PostDetailVo.builder()
                .id(post.getId())
                .author(authorVo)
                .contentType(post.getContentType())
                .title(post.getTitle())
                .content(post.getContent())
                .status(post.getStatus())
                .coverImage(post.getCoverImage())
                .images(images)
                .videos(videos)
                .country(post.getCountryCode())
                .countryName(getCountryName(post.getCountryCode()))
                .stage(post.getCategory())
                .likeCount(post.getLikeCount())
                .commentCount(post.getCommentCount())
                .collectCount(post.getCollectCount())
                .viewCount(post.getViewCount())
                .isLiked(false)
                .isCollected(false)
                .isPinned(post.getIsPinned())
                .isFeatured(post.getIsFeatured())
                .createdAt(post.getCreatedAt())
                .updatedAt(post.getUpdatedAt())
                .build();
    }
    
    /**
     * 生成用户徽章
     */
    private List<String> generateBadges(User user) {
        List<String> badges = new ArrayList<>();
        
        // 根据用户等级和发帖数生成徽章
        if (user.getLevel() == 1) {
            badges.add("新人");
        }
        if (user.getPostCount() >= 10) {
            badges.add("活跃用户");
        }
        if (user.getPostCount() >= 50) {
            badges.add("内容贡献者");
        }
        
        return badges;
    }
    
    /**
     * 获取国家名称
     * TODO: 后续从国家表中查询
     */
    private String getCountryName(String countryCode) {
        if (StrUtil.isBlank(countryCode)) {
            return null;
        }
        
        // 临时硬编码，后续从数据库查询
        return switch (countryCode) {
            case "US" -> "美国";
            case "UK" -> "英国";
            case "CA" -> "加拿大";
            case "AU" -> "澳大利亚";
            case "DE" -> "德国";
            case "FR" -> "法国";
            case "JP" -> "日本";
            case "KR" -> "韩国";
            case "SG" -> "新加坡";
            default -> countryCode;
        };
    }
}

