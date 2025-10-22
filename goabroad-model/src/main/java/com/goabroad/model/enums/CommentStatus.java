package com.goabroad.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 评论状态枚举
 * 
 * @author GoAbroad Team
 * @version 1.0
 * @since 2024-10-19
 */
@Getter
@AllArgsConstructor
public enum CommentStatus {
    
    /**
     * 可见
     */
    VISIBLE("visible", "可见"),
    
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

