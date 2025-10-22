package com.goabroad.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 规划类型枚举
 * 
 * @author GoAbroad Team
 * @version 1.0
 * @since 2024-10-19
 */
@Getter
@AllArgsConstructor
public enum PlanType {
    
    /**
     * 留学规划
     */
    STUDY("study", "留学"),
    
    /**
     * 工作规划
     */
    WORK("work", "工作"),
    
    /**
     * 移民规划
     */
    IMMIGRATION("immigration", "移民");
    
    /**
     * 数据库值
     */
    private final String value;
    
    /**
     * 类型描述
     */
    private final String description;
}
