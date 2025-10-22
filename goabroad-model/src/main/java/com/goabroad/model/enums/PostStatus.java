package com.goabroad.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 帖子状态枚举
 * 
 * @author GoAbroad Team
 * @version 1.0
 * @since 2024-10-19
 */
@Getter
@AllArgsConstructor
public enum PostStatus {
    
    /**
     * 草稿
     */
    DRAFT("draft", "草稿"),
    
    /**
     * 已发布
     */
    PUBLISHED("published", "已发布"),
    
    /**
     * 已隐藏
     */
    HIDDEN("hidden", "已隐藏"),
    
    /**
     * 已删除
     */
    DELETED("deleted", "已删除");
    
    /**
     * 数据库值
     */
    private final String value;
    
    /**
     * 状态描述
     */
    private final String description;
}
