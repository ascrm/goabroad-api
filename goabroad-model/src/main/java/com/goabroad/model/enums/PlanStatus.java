package com.goabroad.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 规划状态枚举
 * 
 * @author GoAbroad Team
 * @version 1.0
 * @since 2024-10-19
 */
@Getter
@AllArgsConstructor
public enum PlanStatus {
    
    /**
     * 激活/进行中
     */
    ACTIVE("active", "进行中"),
    
    /**
     * 已完成
     */
    COMPLETED("completed", "已完成"),
    
    /**
     * 已暂停
     */
    PAUSED("paused", "已暂停"),
    
    /**
     * 已归档
     */
    ARCHIVED("archived", "已归档");
    
    /**
     * 数据库值
     */
    private final String value;
    
    /**
     * 状态描述
     */
    private final String description;
}
