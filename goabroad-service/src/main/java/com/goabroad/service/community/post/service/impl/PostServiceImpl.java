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
        
        // 2. 处理摘要（如果未提供，自动截取content前100字）
        String summary = dto.getSummary();
        if (StrUtil.isBlank(summary)) {
            summary = generateSummary(dto.getContent());
        }
        
        // 3. 确定发布状态和发布时间
        PostStatus status = dto.getStatus() != null ? dto.getStatus() : PostStatus.PUBLISHED;
        LocalDateTime publishedAt = (status == PostStatus.PUBLISHED) ? LocalDateTime.now() : null;
        
        // 4. 构建帖子实体
        Post post = Post.builder()
                .authorId(userId)
                .contentType(dto.getContentType())
                .title(dto.getTitle())
                .content(dto.getContent())
                .summary(summary)
                .category(dto.getCategory())
                .coverImage(dto.getCoverImage())
                .mediaUrls(dto.getMediaUrls())
                .status(status)
                .allowComment(dto.getAllowComment() != null ? dto.getAllowComment() : true)
                .publishedAt(publishedAt)
                .build();
        
        // 设置BaseEntity的字段（deleted已在BaseEntity中有默认值false）
        post.setDeleted(false);
        
        // 5. 保存帖子
        post = postRepository.save(post);
        
        // 6. 如果是已发布状态，更新用户的发帖数
        if (post.getStatus() == PostStatus.PUBLISHED) {
            user.setPostCount(user.getPostCount() + 1);
            userRepository.save(user);
        }
        
        log.info("用户{}发布了帖子，帖子ID: {}, 标题: {}, 状态: {}", 
                userId, post.getId(), post.getTitle(), post.getStatus());
        
        // 7. 转换为VO并返回
        return convertToPostDetailVo(post);
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
     * 生成摘要（自动截取content前100字）
     */
    private String generateSummary(String content) {
        if (StrUtil.isBlank(content)) {
            return "";
        }
        
        // 移除Markdown标记（简单处理）
        String plainText = content
                .replaceAll("#+ ", "")  // 移除标题标记
                .replaceAll("\\*\\*(.+?)\\*\\*", "$1")  // 移除粗体
                .replaceAll("\\*(.+?)\\*", "$1")  // 移除斜体
                .replaceAll("\\[(.+?)\\]\\(.+?\\)", "$1")  // 移除链接，保留文字
                .replaceAll("\\n+", " ")  // 换行替换为空格
                .trim();
        
        // 截取前100字
        if (plainText.length() > 100) {
            return plainText.substring(0, 100) + "...";
        }
        return plainText;
    }
    
    /**
     * 转换为帖子详情VO
     */
    private PostDetailVo convertToPostDetailVo(Post post) {
        return PostDetailVo.builder()
                .id(post.getId())
                .authorId(post.getAuthorId())
                .contentType(post.getContentType())
                .title(post.getTitle())
                .content(post.getContent())
                .summary(post.getSummary())
                .category(post.getCategory())
                .coverImage(post.getCoverImage())
                .mediaUrls(post.getMediaUrls() != null ? post.getMediaUrls() : new ArrayList<>())
                .status(post.getStatus())
                .allowComment(post.getAllowComment())
                .viewCount(post.getViewCount())
                .likeCount(post.getLikeCount())
                .commentCount(post.getCommentCount())
                .collectCount(post.getCollectCount())
                .shareCount(post.getShareCount())
                .isFeatured(post.getIsFeatured())
                .isPinned(post.getIsPinned())
                .isHot(post.getIsHot())
                .createdAt(post.getCreatedAt())
                .updatedAt(post.getUpdatedAt())
                .publishedAt(post.getPublishedAt())
                .build();
    }
    
}

