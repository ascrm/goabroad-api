package com.goabroad.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 配置值类型枚举
 * 
 * @author GoAbroad Team
 * @version 1.0
 * @since 2024-10-19
 */
@Getter
@AllArgsConstructor
public enum ConfigValueType {
    
    /**
     * 字符串
     */
    STRING("string", "字符串"),
    
    /**
     * 数字
     */
    NUMBER("number", "数字"),
    
    /**
     * 布尔值
     */
    BOOLEAN("boolean", "布尔值"),
    
    /**
     * JSON
     */
    JSON("json", "JSON");
    
    /**
     * 数据库值
     */
    private final String value;
    
    /**
     * 描述
     */
    private final String description;
}

