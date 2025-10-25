package com.goabroad.model.entity;

import com.goabroad.model.enums.AdminRole;
import com.goabroad.model.enums.AdminStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;

/**
 * 管理员实体类
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
@Table(name = "admin_users", indexes = {
    @Index(name = "uk_username", columnList = "username", unique = true),
    @Index(name = "uk_email", columnList = "email", unique = true),
    @Index(name = "idx_deleted", columnList = "deleted")
})
public class AdminUser extends BaseEntity {
    
    /**
     * 管理员用户名
     */
    @Column(name = "username", nullable = false, unique = true, length = 50)
    private String username;
    
    /**
     * 密码哈希
     */
    @Column(name = "password_hash", nullable = false)
    private String passwordHash;
    
    /**
     * 邮箱
     */
    @Column(name = "email", nullable = false, unique = true, length = 100)
    private String email;
    
    /**
     * 昵称
     */
    @Column(name = "nickname", length = 50)
    private String nickname;
    
    /**
     * 角色
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "role", length = 20)
    @Builder.Default
    private AdminRole role = AdminRole.EDITOR;
    
    /**
     * 权限列表（JSON）
     */
    @Column(name = "permissions", columnDefinition = "JSONB")
    @JdbcTypeCode(SqlTypes.JSON)
    private String permissions;
    
    /**
     * 状态
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 20)
    @Builder.Default
    private AdminStatus status = AdminStatus.ACTIVE;
    
    /**
     * 最后登录时间
     */
    @Column(name = "last_login_at")
    private LocalDateTime lastLoginAt;
    
    /**
     * 最后登录IP
     */
    @Column(name = "last_login_ip", length = 45)
    private String lastLoginIp;
}

