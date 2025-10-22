package com.goabroad.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDate;

/**
 * 用户偏好设置实体类
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
@Table(name = "user_preferences", indexes = {
    @Index(name = "uk_user_id", columnList = "user_id", unique = true),
    @Index(name = "idx_deleted", columnList = "deleted")
})
public class UserPreferences extends BaseEntity {
    
    /**
     * 用户ID
     */
    @Column(name = "user_id", nullable = false, unique = true)
    private Long userId;
    
    // ========== 目标国家 ==========
    
    /**
     * 目标国家代码列表 ["US","UK","CA"]
     * 存储为JSON
     */
    @Column(name = "target_countries", columnDefinition = "JSON")
    @JdbcTypeCode(SqlTypes.JSON)
    private String targetCountries;
    
    // ========== 出国类型 ==========
    
    /**
     * 出国目的 (study, work, immigration, travel, undecided)
     */
    @Column(name = "target_type", length = 20)
    private String targetType;
    
    /**
     * 细分类型（bachelor/master/phd等）
     */
    @Column(name = "target_subtype", length = 50)
    private String targetSubtype;
    
    // ========== 时间规划 ==========
    
    /**
     * 计划时间（within_3_months/within_6_months等）
     */
    @Column(name = "time_frame", length = 50)
    private String timeFrame;
    
    /**
     * 计划出发日期
     */
    @Column(name = "target_departure_date")
    private LocalDate targetDepartureDate;
    
    // ========== 当前阶段 ==========
    
    /**
     * 当前所处阶段
     */
    @Column(name = "current_stage", length = 50)
    private String currentStage;
    
    // ========== 偏好设置 ==========
    
    /**
     * 是否接收通知
     */
    @Column(name = "notification_enabled")
    @Builder.Default
    private Boolean notificationEnabled = true;
    
    /**
     * 是否接收邮件通知
     */
    @Column(name = "email_notification")
    @Builder.Default
    private Boolean emailNotification = true;
    
    /**
     * 是否接收推送通知
     */
    @Column(name = "push_notification")
    @Builder.Default
    private Boolean pushNotification = true;
}

