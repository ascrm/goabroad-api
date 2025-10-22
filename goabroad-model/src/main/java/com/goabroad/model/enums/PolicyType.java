package com.goabroad.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 政策类型枚举
 * 
 * @author GoAbroad Team
 * @version 1.0
 * @since 2024-10-19
 */
@Getter
@AllArgsConstructor
public enum PolicyType {
    
    /**
     * 签证政策
     */
    VISA("visa", "签证"),
    
    /**
     * 留学政策
     */
    STUDY("study", "留学"),
    
    /**
     * 工作政策
     */
    WORK("work", "工作"),
    
    /**
     * 移民政策
     */
    IMMIGRATION("immigration", "移民"),
    
    /**
     * 其他政策
     */
    OTHER("other", "其他");
    
    /**
     * 数据库值
     */
    private final String value;
    
    /**
     * 描述
     */
    private final String description;
}

