package com.goabroad.model.entity;

import com.goabroad.model.enums.MaterialCategory;
import com.goabroad.model.enums.TaskStatus;
import lombok.*;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 材料清单实体类
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
@Table(name = "material_checklists", indexes = {
    @Index(name = "idx_plan_id", columnList = "plan_id"),
    @Index(name = "idx_category", columnList = "category"),
    @Index(name = "idx_status", columnList = "status"),
    @Index(name = "idx_deleted", columnList = "deleted")
})
public class MaterialChecklist extends BaseEntity {
    
    /**
     * 规划ID
     */
    @Column(name = "plan_id", nullable = false)
    private Long planId;
    
    /**
     * 材料名称
     */
    @Column(name = "material_name", nullable = false, length = 200)
    private String materialName;
    
    /**
     * 材料类别
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "category", nullable = false, length = 20)
    private MaterialCategory category;
    
    /**
     * 材料说明
     */
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;
    
    /**
     * 要求细节（格式、份数等）
     */
    @Column(name = "requirements", columnDefinition = "TEXT")
    private String requirements;
    
    /**
     * 示例文件URL
     */
    @Column(name = "example_url", length = 500)
    private String exampleUrl;
    
    // ========== 状态 ==========
    
    /**
     * 准备状态
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    @Builder.Default
    private TaskStatus status = TaskStatus.NOT_STARTED;
    
    // ========== 提醒 ==========
    
    /**
     * 提醒日期
     */
    @Column(name = "reminder_date")
    private LocalDate reminderDate;
    
    /**
     * 截止日期
     */
    @Column(name = "due_date")
    private LocalDate dueDate;
    
    // ========== 文件数量（冗余） ==========
    
    /**
     * 已上传文件数
     */
    @Column(name = "file_count", nullable = false)
    @Builder.Default
    private Short fileCount = 0;
    
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
