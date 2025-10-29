package com.goabroad.model.community.post.converter;

import com.goabroad.model.community.post.entity.Post;
import com.goabroad.model.community.post.vo.PostSimpleVo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

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
     * 生成内容预览
     * 优先使用summary，否则截取content前200字符
     * 
     * @param post 帖子实体
     * @return 内容预览
     */
    @Named("generatePreview")
    default String generatePreview(Post post) {
        if (post.getSummary() != null) {
            return post.getSummary();
        }
        if (post.getContent() != null && post.getContent().length() > 200) {
            return post.getContent().substring(0, 200) + "...";
        }
        return post.getContent();
    }
}

