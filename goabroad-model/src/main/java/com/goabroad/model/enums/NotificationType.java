package com.goabroad.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 通知类型枚举
 * 
 * @author GoAbroad Team
 * @version 1.0
 * @since 2024-10-19
 */
@Getter
@AllArgsConstructor
public enum NotificationType {
    
    /**
     * 系统通知
     */
    SYSTEM("system", "系统通知"),
    
    /**
     * 点赞通知
     */
    LIKE("like", "点赞通知"),
    
    /**
     * 评论通知
     */
    COMMENT("comment", "评论通知"),
    
    /**
     * 关注通知
     */
    FOLLOW("follow", "关注通知"),
    
    /**
     * 回复通知
     */
    REPLY("reply", "回复通知"),
    
    /**
     * @提及通知
     */
    MENTION("mention", "@提及通知"),
    
    /**
     * 政策更新通知
     */
    POLICY_UPDATE("policy_update", "政策更新通知");
    
    /**
     * 数据库值
     */
    private final String value;
    
    /**
     * 类型描述
     */
    private final String description;
}
