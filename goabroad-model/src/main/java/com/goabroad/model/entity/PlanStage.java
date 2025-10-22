package com.goabroad.model.entity;

import com.goabroad.model.enums.StageStatus;
import lombok.*;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 规划阶段实体类
 * 
 * @author GoAbroad Team
 * @version 1.0
 * @since 2024-10-19
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "plan_stages", indexes = {
    @Index(name = "idx_plan_id", columnList = "plan_id"),
    @Index(name = "idx_status", columnList = "status"),
    @Index(name = "idx_sort_order", columnList = "sort_order"),
    @Index(name = "idx_deleted", columnList = "deleted")
})
public class PlanStage extends BaseEntity {
    
    /**
     * 规划ID
     */
    @Column(name = "plan_id", nullable = false)
    private Long planId;
    
    /**
     * 阶段名称（如"语言考试阶段"）
     */
    @Column(name = "name", nullable = false, length = 100)
    private String name;
    
    /**
     * 阶段描述
     */
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;
    
    // ========== 时间范围 ==========
    
    /**
     * 开始日期
     */
    @Column(name = "start_date")
    private LocalDate startDate;
    
    /**
     * 结束日期
     */
    @Column(name = "end_date")
    private LocalDate endDate;
    
    /**
     * 预计天数
     */
    @Column(name = "duration_days")
    private Integer durationDays;
    
    // ========== 状态 ==========
    
    /**
     * 阶段状态
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    @Builder.Default
    private StageStatus status = StageStatus.NOT_STARTED;
    
    /**
     * 阶段进度 0-100
     */
    @Column(name = "progress", nullable = false)
    @Builder.Default
    private Integer progress = 0;
    
    // ========== 统计 ==========
    
    /**
     * 任务总数
     */
    @Column(name = "total_tasks", nullable = false)
    @Builder.Default
    private Integer totalTasks = 0;
    
    /**
     * 已完成任务数
     */
    @Column(name = "completed_tasks", nullable = false)
    @Builder.Default
    private Integer completedTasks = 0;
    
    // ========== 排序 ==========
    
    /**
     * 排序顺序
     */
    @Column(name = "sort_order", nullable = false)
    private Integer sortOrder;
    
    /**
     * 完成时间
     */
    @Column(name = "completed_at")
    private LocalDateTime completedAt;
}

