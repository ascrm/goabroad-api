package com.goabroad.model.entity;

import com.goabroad.model.enums.Gender;
import com.goabroad.model.enums.UserStatus;
import lombok.*;

import jakarta.persistence.*;
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
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users", indexes = {
    @Index(name = "idx_status", columnList = "status"),
    @Index(name = "idx_deleted", columnList = "deleted"),
    @Index(name = "idx_created_at", columnList = "created_at"),
    @Index(name = "idx_level_points", columnList = "level,points")
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
    @Column(name = "gender", length = 20)
    private Gender gender;
    
    // ========== 社区等级 ==========
    
    /**
     * 用户等级 1-10
     */
    @Column(name = "level", nullable = false)
    @Builder.Default
    private Short level = 1;
    
    /**
     * 积分
     */
    @Column(name = "points", nullable = false)
    @Builder.Default
    private Integer points = 0;
    
    /**
     * 经验值
     */
    @Column(name = "exp", nullable = false)
    @Builder.Default
    private Integer exp = 0;
    
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
    
    /**
     * 是否会员
     */
    @Column(name = "is_vip", nullable = false)
    @Builder.Default
    private Boolean isVip = false;
    
    /**
     * 会员到期时间
     */
    @Column(name = "vip_expire_at")
    private LocalDateTime vipExpireAt;
    
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
