package com.goabroad.model.community.post.converter.helper;

import com.goabroad.model.community.post.entity.Post;

import java.util.ArrayList;
import java.util.List;

/**
 * 帖子转换器辅助工具类
 * 提供转换过程中的辅助方法
 * 
 * @author GoAbroad Team
 * @version 1.0
 * @since 2024-11-04
 */
public class PostConverterHelper {
    
    /**
     * 确保列表不为null
     * 如果为null，返回空列表
     * 
     * @param list 列表
     * @return 非null的列表
     */
    public static <T> List<T> ensureListNotNull(List<T> list) {
        return list != null ? list : new ArrayList<>();
    }
    
    /**
     * 初始化allowComment字段
     * 如果为null，返回true
     * 
     * @param allowComment 是否允许评论
     * @return 非null的allowComment值
     */
    public static Boolean initializeAllowComment(Boolean allowComment) {
        return allowComment != null ? allowComment : true;
    }
    
    /**
     * 生成内容预览
     * 优先使用summary，否则截取content前200字符
     * 
     * @param post 帖子实体
     * @return 内容预览
     */
    public static String generatePreview(Post post) {
        if (post.getSummary() != null && !post.getSummary().isBlank()) {
            return post.getSummary();
        }
        if (post.getContent() != null && post.getContent().length() > 200) {
            return post.getContent().substring(0, 200) + "...";
        }
        return post.getContent();
    }
}

