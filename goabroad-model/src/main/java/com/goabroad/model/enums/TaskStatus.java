package com.goabroad.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 任务状态枚举
 * 
 * @author GoAbroad Team
 * @version 1.0
 * @since 2024-10-19
 */
@Getter
@AllArgsConstructor
public enum TaskStatus {
    
    /**
     * 未开始
     */
    NOT_STARTED("not_started", "未开始"),
    
    /**
     * 进行中
     */
    IN_PROGRESS("in_progress", "进行中"),
    
    /**
     * 已完成
     */
    COMPLETED("completed", "已完成"),
    
    /**
     * 已跳过
     */
    SKIPPED("skipped", "已跳过");
    
    /**
     * 数据库值
     */
    private final String value;
    
    /**
     * 状态描述
     */
    private final String description;
}

