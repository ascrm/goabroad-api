package com.goabroad.model.community.post.converter;

import com.goabroad.model.community.post.converter.helper.PostConverterHelper;
import com.goabroad.model.community.post.dto.CreatePostDto;
import com.goabroad.model.community.post.entity.Post;
import com.goabroad.model.community.post.enums.PostStatus;
import com.goabroad.model.community.post.vo.PostDetailVo;
import com.goabroad.model.community.post.vo.PostSimpleVo;
import org.mapstruct.*;

import java.time.LocalDateTime;

/**
 * 帖子实体转换器
 * 使用 MapStruct 进行对象转换
 * 
 * @author GoAbroad Team
 * @version 1.0
 * @since 2024-10-29
 */
@Mapper(componentModel = "spring", uses = PostConverterHelper.class)
public interface PostConverter {
    
    // ============================================
    // DTO -> Entity 转换
    // ============================================

    /**
     * CreatePostDto 转换为 Post 实体
     * 
     * @param dto 创建帖子DTO
     * @param authorId 作者ID
     * @param summary 摘要（如果提供则覆盖dto中的summary）
     * @param status 发布状态（如果提供则覆盖dto中的status）
     * @param publishedAt 发布时间
     * @return Post实体
     */
    @Mapping(target = "mediaUrls", source = "dto.mediaUrls")
    @Mapping(target = "allowComment", source = "dto.allowComment")
    @Mapping(target = "summary", source = "summary")
    @Mapping(target = "status", source = "status")
    @Mapping(target = "authorId", source = "authorId")
    @Mapping(target = "publishedAt", source = "publishedAt")
    Post toPost(CreatePostDto dto, Long authorId, String summary, PostStatus status, LocalDateTime publishedAt);
    
    // ============================================
    // Entity -> VO 转换
    // ============================================
    
    /**
     * Post实体转换为PostDetailVo
     * 
     * @param post 帖子实体
     * @return 帖子详情VO
     */
    PostDetailVo toPostDetailVo(Post post);
    
    /**
     * Post实体转换为PostSimpleVo
     * 
     * @param post 帖子实体
     * @return 帖子简要信息VO
     */
    @Mapping(target = "contentType", expression = "java(post.getContentType().name())")
    @Mapping(target = "tags", ignore = true)
    @Mapping(target = "isLiked", ignore = true)
    @Mapping(target = "isCollected", ignore = true)
    PostSimpleVo toPostSimpleVo(Post post);
}

