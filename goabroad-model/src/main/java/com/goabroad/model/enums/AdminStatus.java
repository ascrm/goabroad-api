package com.goabroad.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 管理员状态枚举
 * 
 * @author GoAbroad Team
 * @version 1.0
 * @since 2024-10-19
 */
@Getter
@AllArgsConstructor
public enum AdminStatus {
    
    /**
     * 启用
     */
    ACTIVE("active", "启用"),
    
    /**
     * 禁用
     */
    DISABLED("disabled", "禁用");
    
    /**
     * 数据库值
     */
    private final String value;
    
    /**
     * 描述
     */
    private final String description;
}

