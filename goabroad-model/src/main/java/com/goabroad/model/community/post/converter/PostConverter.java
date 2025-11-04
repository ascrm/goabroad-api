package com.goabroad.model.community.post.converter;

import com.goabroad.model.community.post.dto.CreatePostDto;
import com.goabroad.model.community.post.entity.Post;
import com.goabroad.model.community.post.enums.PostStatus;
import com.goabroad.model.community.post.vo.PostDetailVo;
import com.goabroad.model.community.post.vo.PostSimpleVo;
import org.mapstruct.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 帖子实体转换器
 * 使用 MapStruct 进行对象转换
 * 
 * @author GoAbroad Team
 * @version 1.0
 * @since 2024-10-29
 */
@Mapper(componentModel = "spring")
public interface PostConverter {
    
    // ============================================
    // DTO -> Entity 转换
    // ============================================

    /**
     * CreatePostDto 转换为 Post 实体
     */
    @Mapping(target = "mediaUrls", source = "dto.mediaUrls", qualifiedByName = "initializeMediaUrls")
    @Mapping(target = "allowComment", source = "dto.allowComment", qualifiedByName = "initializeAllowComment")
    Post toPost(CreatePostDto dto, Long authorId, String summary, PostStatus status, LocalDateTime publishedAt);

    /**
     * 初始化媒体URL列表
     * 如果为null，返回空列表
     */
    @Named("initializeMediaUrls")
    default List<String> initializeMediaUrls(List<String> mediaUrls) {
        return mediaUrls != null ? mediaUrls : new ArrayList<>();
    }
    
    /**
     * 初始化allowComment字段
     * 如果为null，返回true
     */
    @Named("initializeAllowComment")
    default Boolean initializeAllowComment(Boolean allowComment) {
        return allowComment != null ? allowComment : true;
    }
    
    // ============================================
    // Entity -> VO 转换
    // ============================================
    
    /**
     * Post实体转换为PostDetailVo
     * 
     * @param post 帖子实体
     * @return 帖子详情VO
     */
    @Mapping(target = "mediaUrls", source = "mediaUrls", qualifiedByName = "ensureMediaUrlsList")
    PostDetailVo toPostDetailVo(Post post);
    
    /**
     * Post实体列表转换为PostDetailVo列表
     * 
     * @param posts 帖子实体列表
     * @return 帖子详情VO列表
     */
    List<PostDetailVo> toPostDetailVoList(List<Post> posts);
    
    /**
     * 确保媒体URL列表不为null
     */
    @Named("ensureMediaUrlsList")
    default List<String> ensureMediaUrlsList(List<String> mediaUrls) {
        return mediaUrls != null ? mediaUrls : new ArrayList<>();
    }
    
    /**
     * Post实体转换为PostSimpleVo
     * 
     * @param post 帖子实体
     * @return 帖子简要信息VO
     */
    @Mapping(target = "contentPreview", source = "post", qualifiedByName = "generatePreview")
    @Mapping(target = "contentType", expression = "java(post.getContentType().name())")
    @Mapping(target = "tags", ignore = true)
    @Mapping(target = "isLiked", ignore = true)
    @Mapping(target = "isCollected", ignore = true)
    PostSimpleVo toPostSimpleVo(Post post);
    
    /**
     * Post实体列表转换为PostSimpleVo列表
     * 
     * @param posts 帖子实体列表
     * @return 帖子简要信息VO列表
     */
    List<PostSimpleVo> toPostSimpleVoList(List<Post> posts);
    
    /**
     * 生成内容预览
     * 优先使用summary，否则截取content前200字符
     * 
     * @param post 帖子实体
     * @return 内容预览
     */
    @Named("generatePreview")
    default String generatePreview(Post post) {
        if (post.getSummary() != null && !post.getSummary().isBlank()) {
            return post.getSummary();
        }
        if (post.getContent() != null && post.getContent().length() > 200) {
            return post.getContent().substring(0, 200) + "...";
        }
        return post.getContent();
    }
    
    // ============================================
    // 更新操作
    // ============================================
    
    /**
     * 使用DTO更新Post实体
     * 只更新非null字段
     * 
     * @param dto CreatePostDto
     * @param post 要更新的Post实体
     */
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updatePostFromDto(CreatePostDto dto, @MappingTarget Post post);
}

