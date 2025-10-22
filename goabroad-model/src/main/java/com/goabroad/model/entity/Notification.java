package com.goabroad.model.entity;

import com.goabroad.model.enums.NotificationType;
import lombok.*;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * 系统通知实体类
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
@Table(name = "notifications", indexes = {
    @Index(name = "idx_user_id", columnList = "user_id"),
    @Index(name = "idx_is_read", columnList = "is_read"),
    @Index(name = "idx_type", columnList = "type"),
    @Index(name = "idx_created_at", columnList = "created_at"),
    @Index(name = "idx_deleted", columnList = "deleted")
})
public class Notification extends BaseEntity {
    
    /**
     * 接收者ID
     */
    @Column(name = "user_id", nullable = false)
    private Long userId;
    
    /**
     * 发送者ID（系统通知为NULL）
     */
    @Column(name = "sender_id")
    private Long senderId;
    
    /**
     * 通知类型
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false, length = 20)
    private NotificationType type;
    
    /**
     * 通知标题
     */
    @Column(name = "title", nullable = false, length = 200)
    private String title;
    
    /**
     * 通知内容
     */
    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    private String content;
    
    // ========== 关联对象 ==========
    
    /**
     * 关联对象类型（post/comment/plan）
     */
    @Column(name = "related_type", length = 50)
    private String relatedType;
    
    /**
     * 关联对象ID
     */
    @Column(name = "related_id")
    private Long relatedId;
    
    // ========== 状态 ==========
    
    /**
     * 是否已读
     */
    @Column(name = "is_read", nullable = false)
    @Builder.Default
    private Boolean isRead = false;
    
    /**
     * 阅读时间
     */
    @Column(name = "read_at")
    private LocalDateTime readAt;
}
