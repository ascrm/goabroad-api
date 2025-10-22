package com.goabroad.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 用户状态枚举
 * 
 * @author GoAbroad Team
 * @version 1.0
 * @since 2024-10-19
 */
@Getter
@AllArgsConstructor
public enum UserStatus {
    
    /**
     * 正常
     */
    ACTIVE("active", "正常"),
    
    /**
     * 未激活
     */
    INACTIVE("inactive", "未激活"),
    
    /**
     * 被封禁
     */
    BANNED("banned", "被封禁"),
    
    /**
     * 已删除
     */
    DELETED("deleted", "已删除");
    
    /**
     * 数据库值
     */
    private final String value;
    
    /**
     * 描述
     */
    private final String description;
}
