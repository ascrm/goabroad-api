package com.goabroad.model.entity;

import lombok.*;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * 操作审计日志实体类
 * 注意：日志表不添加逻辑删除和乐观锁
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
@Table(name = "audit_logs", indexes = {
    @Index(name = "idx_user_id", columnList = "user_id"),
    @Index(name = "idx_admin_id", columnList = "admin_id"),
    @Index(name = "idx_action", columnList = "action"),
    @Index(name = "idx_created_at", columnList = "created_at")
})
public class AuditLog {
    
    /**
     * 主键ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    /**
     * 用户ID
     */
    @Column(name = "user_id")
    private Long userId;
    
    /**
     * 管理员ID
     */
    @Column(name = "admin_id")
    private Long adminId;
    
    /**
     * 操作动作（user.register/post.delete等）
     */
    @Column(name = "action", nullable = false, length = 100)
    private String action;
    
    /**
     * 资源类型
     */
    @Column(name = "resource_type", length = 50)
    private String resourceType;
    
    /**
     * 资源ID
     */
    @Column(name = "resource_id")
    private Long resourceId;
    
    /**
     * IP地址
     */
    @Column(name = "ip_address", length = 45)
    private String ipAddress;
    
    /**
     * User Agent
     */
    @Column(name = "user_agent", length = 500)
    private String userAgent;
    
    /**
     * 请求数据（JSON）
     */
    @Column(name = "request_data", columnDefinition = "JSON")
    // @JdbcTypeCode(SqlTypes.JSON)
    private String requestData;
    
    /**
     * 响应数据（JSON）
     */
    @Column(name = "response_data", columnDefinition = "JSON")
    // @JdbcTypeCode(SqlTypes.JSON)
    private String responseData;
    
    /**
     * 操作状态（success/failed）
     */
    @Column(name = "status", length = 20)
    @Builder.Default
    private String status = "success";
    
    /**
     * 创建时间
     */
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}

