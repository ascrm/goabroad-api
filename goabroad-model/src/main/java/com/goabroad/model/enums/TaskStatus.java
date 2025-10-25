package com.goabroad.model.enums;

/**
 * 任务状态枚举
 * 
 * @author GoAbroad Team
 * @version 1.0
 * @since 2024-10-25
 */
public enum TaskStatus {
    /**
     * 未开始
     */
    NOT_STARTED,
    
    /**
     * 进行中
     */
    IN_PROGRESS,
    
    /**
     * 已完成
     */
    COMPLETED,
    
    /**
     * 已逾期
     */
    OVERDUE,
    
    /**
     * 已跳过
     */
    SKIPPED
}

