package com.goabroad.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 里程碑状态枚举
 * 
 * @author GoAbroad Team
 * @version 1.0
 * @since 2024-10-19
 */
@Getter
@AllArgsConstructor
public enum MilestoneStatus {
    
    /**
     * 待完成
     */
    PENDING("pending", "待完成"),
    
    /**
     * 已完成
     */
    COMPLETED("completed", "已完成"),
    
    /**
     * 已错过
     */
    MISSED("missed", "已错过");
    
    /**
     * 数据库值
     */
    private final String value;
    
    /**
     * 描述
     */
    private final String description;
}

