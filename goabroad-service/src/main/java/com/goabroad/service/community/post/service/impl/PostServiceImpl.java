package com.goabroad.service.community.post.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.goabroad.common.exception.BusinessException;
import com.goabroad.common.pojo.ResultCode;
import com.goabroad.model.dto.CreatePostDto;
import com.goabroad.model.entity.Post;
import com.goabroad.model.entity.User;
import com.goabroad.model.enums.PostStatus;
import com.goabroad.model.vo.PostDetailVo;
import com.goabroad.service.community.post.repository.PostRepository;
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
                .tagsJson(dto.getTags() != null && !dto.getTags().isEmpty() ? JSONUtil.toJsonStr(dto.getTags()) : null)
                .countryCode(dto.getCountry())
                .category(dto.getStage())
                .publishedAt(dto.getStatus() == PostStatus.PUBLISHED ? LocalDateTime.now() : null)
                .build();
        
        // 设置BaseEntity的字段（deleted已在BaseEntity中有默认值false）
        post.setDeleted(false);
        
        // 4. 保存帖子
        post = postRepository.save(post);
        
        // 5. 如果是已发布状态，更新用户的发帖数
        if (post.getStatus() == PostStatus.PUBLISHED) {
            user.setPostCount(user.getPostCount() + 1);
            userRepository.save(user);
        }
        
        log.info("用户{}发布了帖子，帖子ID: {}, 标题: {}", userId, post.getId(), post.getTitle());
        
        // 6. 转换为VO并返回
        return convertToPostDetailVo(post, user);
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
        
        List<String> tags = StrUtil.isNotBlank(post.getTagsJson()) 
                ? JSONUtil.toList(post.getTagsJson(), String.class) 
                : new ArrayList<>();
        
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
                .tags(tags)
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

