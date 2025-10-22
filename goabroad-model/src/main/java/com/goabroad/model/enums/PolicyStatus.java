package com.goabroad.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 政策状态枚举
 * 
 * @author GoAbroad Team
 * @version 1.0
 * @since 2024-10-19
 */
@Getter
@AllArgsConstructor
public enum PolicyStatus {
    
    /**
     * 生效中
     */
    ACTIVE("active", "生效中"),
    
    /**
     * 已过期
     */
    EXPIRED("expired", "已过期"),
    
    /**
     * 草稿
     */
    DRAFT("draft", "草稿");
    
    /**
     * 数据库值
     */
    private final String value;
    
    /**
     * 描述
     */
    private final String description;
}

