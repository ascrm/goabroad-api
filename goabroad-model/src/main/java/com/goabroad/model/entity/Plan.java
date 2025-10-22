package com.goabroad.model.entity;

import com.goabroad.model.enums.PlanStatus;
import com.goabroad.model.enums.PlanType;
import lombok.*;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 规划实体类
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
@Table(name = "plans", indexes = {
    @Index(name = "idx_user_id", columnList = "user_id"),
    @Index(name = "idx_country_code", columnList = "country_code"),
    @Index(name = "idx_status", columnList = "status"),
    @Index(name = "idx_plan_type", columnList = "plan_type"),
    @Index(name = "idx_deleted", columnList = "deleted")
})
public class Plan extends BaseEntity {
    
    /**
     * 用户ID
     */
    @Column(name = "user_id", nullable = false)
    private Long userId;
    
    /**
     * 目标国家代码
     */
    @Column(name = "country_code", nullable = false, length = 2)
    private String countryCode;
    
    // ========== 规划类型 ==========
    
    /**
     * 规划类型
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "plan_type", nullable = false, length = 20)
    private PlanType planType;
    
    /**
     * 细分类型（bachelor/master/phd/work_visa等）
     */
    @Column(name = "sub_type", length = 50)
    private String subType;
    
    // ========== 目标 ==========
    
    /**
     * 计划出发日期
     */
    @Column(name = "target_date")
    private LocalDate targetDate;
    
    /**
     * 规划标题（如"2026年美国CS硕士申请"）
     */
    @Column(name = "title", length = 200)
    private String title;
    
    /**
     * 用户当前状态：{"education":"bachelor","gpa":3.5,"toefl":100}
     */
//    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "current_status", columnDefinition = "JSON")
    private String currentStatus;
    
    // ========== 进度 ==========
    
    /**
     * 整体进度 0-100
     */
    @Column(name = "progress", nullable = false)
    @Builder.Default
    private Integer progress = 0;
    
    /**
     * 当前所处阶段名称
     */
    @Column(name = "current_stage", length = 50)
    private String currentStage;
    
    // ========== 统计 ==========
    
    /**
     * 总阶段数
     */
    @Column(name = "total_stages", nullable = false)
    @Builder.Default
    private Integer totalStages = 0;
    
    /**
     * 已完成阶段数
     */
    @Column(name = "completed_stages", nullable = false)
    @Builder.Default
    private Integer completedStages = 0;
    
    /**
     * 总任务数
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
    
    // ========== 状态 ==========
    
    /**
     * 规划状态
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    @Builder.Default
    private PlanStatus status = PlanStatus.ACTIVE;
    
    /**
     * 完成时间
     */
    @Column(name = "completed_at")
    private LocalDateTime completedAt;
}
