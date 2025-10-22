package com.goabroad.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 内容类型枚举
 * 
 * @author GoAbroad Team
 * @version 1.0
 * @since 2024-10-19
 */
@Getter
@AllArgsConstructor
public enum ContentType {
    
    /**
     * 普通帖子（经验分享）
     */
    POST("post", "普通帖子"),
    
    /**
     * 提问
     */
    QUESTION("question", "提问"),
    
    /**
     * 动态（短内容）
     */
    TIMELINE("timeline", "动态"),
    
    /**
     * 视频日志
     */
    VLOG("vlog", "视频日志");
    
    /**
     * 数据库值
     */
    private final String value;
    
    /**
     * 类型描述
     */
    private final String description;
}

