package com.goabroad.model.user.entity;

import com.goabroad.model.BaseEntity;
import com.goabroad.model.user.enums.Gender;
import com.goabroad.model.user.enums.UserStatus;
import lombok.*;

import jakarta.persistence.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

/**
 * 用户实体类
 * 
 * @author GoAbroad Team
 * @version 1.0
 * @since 2024-10-19
 */
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users", indexes = {
    @Index(name = "idx_users_status", columnList = "status"),
    @Index(name = "idx_users_deleted", columnList = "deleted"),
    @Index(name = "idx_users_created_at", columnList = "created_at")
}, uniqueConstraints = {
    @UniqueConstraint(name = "uk_username", columnNames = "username"),
    @UniqueConstraint(name = "uk_email", columnNames = "email"),
    @UniqueConstraint(name = "uk_phone", columnNames = "phone")
})
public class User extends BaseEntity {
    
    /**
     * 用户名（唯一）
     */
    @Column(name = "username", nullable = false, length = 50)
    private String username;
    
    /**
     * 邮箱（唯一，可选）
     */
    @Column(name = "email", length = 100)
    private String email;
    
    /**
     * 手机号
     */
    @Column(name = "phone", length = 20)
    private String phone;
    
    /**
     * 密码哈希值（bcrypt）
     */
    @Column(name = "password_hash", nullable = false)
    private String passwordHash;
    
    // ========== 基本信息 ==========
    
    /**
     * 昵称
     */
    @Column(name = "nickname", length = 50)
    private String nickname;
    
    /**
     * 头像URL
     */
    @Column(name = "avatar_url", length = 500)
    private String avatarUrl;
    
    /**
     * 个人简介
     */
    @Column(name = "bio", length = 500)
    private String bio;
    
    /**
     * 性别
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "gender", length = 30)
    private Gender gender;
    
    /**
     * 出生日期
     */
    @Column(name = "birth_date")
    private java.time.LocalDate birthDate;
    
    /**
     * 所在地
     */
    @Column(name = "location", length = 100)
    private String location;

    // ========== 统计数据（冗余） ==========
    
    /**
     * 发帖数
     */
    @Column(name = "post_count", nullable = false)
    @Builder.Default
    private Integer postCount = 0;
    
    /**
     * 粉丝数
     */
    @Column(name = "follower_count", nullable = false)
    @Builder.Default
    private Integer followerCount = 0;
    
    /**
     * 关注数
     */
    @Column(name = "following_count", nullable = false)
    @Builder.Default
    private Integer followingCount = 0;
    
    // ========== 账号状态 ==========
    
    /**
     * 账号状态
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    @Builder.Default
    private UserStatus status = UserStatus.ACTIVE;
    
    /**
     * 邮箱是否验证
     */
    @Column(name = "email_verified", nullable = false)
    @Builder.Default
    private Boolean emailVerified = false;
    
    /**
     * 手机是否验证
     */
    @Column(name = "phone_verified", nullable = false)
    @Builder.Default
    private Boolean phoneVerified = false;
    
    // ========== 最后活跃 ==========
    
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
