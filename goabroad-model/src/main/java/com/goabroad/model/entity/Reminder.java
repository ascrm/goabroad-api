package com.goabroad.model.entity;

import lombok.*;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

/**
 * 用户提醒实体类
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
@Table(name = "reminders", indexes = {
    @Index(name = "idx_user_id", columnList = "user_id"),
    @Index(name = "idx_plan_id", columnList = "plan_id"),
    @Index(name = "idx_remind_time", columnList = "remind_time"),
    @Index(name = "idx_is_sent", columnList = "is_sent"),
    @Index(name = "idx_deleted", columnList = "deleted")
})
public class Reminder extends BaseEntity {
    
    /**
     * 用户ID
     */
    @Column(name = "user_id", nullable = false)
    private Long userId;
    
    /**
     * 关联规划ID
     */
    @Column(name = "plan_id")
    private Long planId;
    
    /**
     * 关联任务ID
     */
    @Column(name = "task_id")
    private Long taskId;
    
    /**
     * 关联材料清单ID
     */
    @Column(name = "checklist_id")
    private Long checklistId;
    
    /**
     * 提醒标题
     */
    @Column(name = "title", nullable = false, length = 200)
    private String title;
    
    /**
     * 提醒内容
     */
    @Column(name = "content", columnDefinition = "TEXT")
    private String content;
    
    /**
     * 提醒时间
     */
    @Column(name = "remind_time", nullable = false)
    private LocalDateTime remindTime;
    
    // ========== 重复设置 ==========
    
    /**
     * 是否重复
     */
    @Column(name = "is_recurring", nullable = false)
    @Builder.Default
    private Boolean isRecurring = false;
    
    /**
     * 重复规则（daily/weekly/monthly）
     */
    @Column(name = "recurrence_rule", length = 100)
    private String recurrenceRule;
    
    // ========== 发送状态 ==========
    
    /**
     * 是否已发送
     */
    @Column(name = "is_sent", nullable = false)
    @Builder.Default
    private Boolean isSent = false;
    
    /**
     * 发送时间
     */
    @Column(name = "sent_at")
    private LocalDateTime sentAt;
}
