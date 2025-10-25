package com.goabroad.model.entity;

import com.goabroad.model.enums.TaskStatus;
import lombok.*;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 规划任务实体类
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
@Table(name = "plan_tasks", indexes = {
    @Index(name = "idx_stage_id", columnList = "stage_id"),
    @Index(name = "idx_plan_id", columnList = "plan_id"),
    @Index(name = "idx_status", columnList = "status"),
    @Index(name = "idx_due_date", columnList = "due_date"),
    @Index(name = "idx_deleted", columnList = "deleted")
})
public class PlanTask extends BaseEntity {
    
    /**
     * 阶段ID
     */
    @Column(name = "stage_id", nullable = false)
    private Long stageId;
    
    /**
     * 规划ID（冗余，便于查询）
     */
    @Column(name = "plan_id", nullable = false)
    private Long planId;
    
    /**
     * 任务名称
     */
    @Column(name = "name", nullable = false, length = 200)
    private String name;
    
    /**
     * 任务详细说明
     */
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;
    
    // ========== 截止时间 ==========
    
    /**
     * 截止日期
     */
    @Column(name = "due_date")
    private LocalDate dueDate;
    
    /**
     * 提醒日期
     */
    @Column(name = "reminder_date")
    private LocalDate reminderDate;
    
    // ========== 状态 ==========
    
    /**
     * 任务状态
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    @Builder.Default
    private TaskStatus status = TaskStatus.NOT_STARTED;
    
    // ========== 关联资源 ==========
    
    /**
     * 详细指南链接
     */
    @Column(name = "guide_url", length = 500)
    private String guideUrl;
    
    /**
     * 相关工具（cost_calculator/gpa_converter）
     */
    @Column(name = "related_tool", length = 50)
    private String relatedTool;
    
    // ========== 排序 ==========
    
    /**
     * 排序顺序
     */
    @Column(name = "sort_order", nullable = false)
    private Short sortOrder;
    
    /**
     * 完成时间
     */
    @Column(name = "completed_at")
    private LocalDateTime completedAt;
}

