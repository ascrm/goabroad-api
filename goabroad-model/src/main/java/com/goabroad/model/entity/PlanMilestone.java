package com.goabroad.model.entity;

import com.goabroad.model.enums.MilestoneStatus;
import lombok.*;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 规划里程碑实体类
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
@Table(name = "plan_milestones", indexes = {
    @Index(name = "idx_plan_id", columnList = "plan_id"),
    @Index(name = "idx_milestone_date", columnList = "milestone_date"),
    @Index(name = "idx_deleted", columnList = "deleted")
})
public class PlanMilestone extends BaseEntity {
    
    /**
     * 规划ID
     */
    @Column(name = "plan_id", nullable = false)
    private Long planId;
    
    /**
     * 里程碑名称
     */
    @Column(name = "name", nullable = false, length = 100)
    private String name;
    
    /**
     * 说明
     */
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;
    
    /**
     * 图标名称
     */
    @Column(name = "icon", length = 50)
    private String icon;
    
    /**
     * 里程碑日期
     */
    @Column(name = "milestone_date", nullable = false)
    private LocalDate milestoneDate;
    
    /**
     * 状态
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 20)
    @Builder.Default
    private MilestoneStatus status = MilestoneStatus.PENDING;
    
    /**
     * 排序
     */
    @Column(name = "sort_order", nullable = false)
    private Short sortOrder;
    
    /**
     * 完成时间
     */
    @Column(name = "completed_at")
    private LocalDateTime completedAt;
}

