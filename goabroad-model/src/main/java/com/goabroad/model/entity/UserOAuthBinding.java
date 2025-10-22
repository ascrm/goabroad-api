package com.goabroad.model.entity;

import com.goabroad.model.enums.OAuthProvider;
import lombok.*;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * 第三方登录绑定实体类
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
@Table(name = "user_oauth_bindings", indexes = {
    @Index(name = "uk_provider_user", columnList = "provider,provider_user_id", unique = true),
    @Index(name = "idx_user_id", columnList = "user_id"),
    @Index(name = "idx_deleted", columnList = "deleted")
})
public class UserOAuthBinding extends BaseEntity {
    
    /**
     * 用户ID
     */
    @Column(name = "user_id", nullable = false)
    private Long userId;
    
    /**
     * 第三方平台
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "provider", nullable = false, length = 20)
    private OAuthProvider provider;
    
    /**
     * 第三方用户ID（openid/unionid）
     */
    @Column(name = "provider_user_id", nullable = false, length = 100)
    private String providerUserId;
    
    /**
     * 第三方用户名
     */
    @Column(name = "provider_username", length = 100)
    private String providerUsername;
    
    /**
     * 第三方头像
     */
    @Column(name = "provider_avatar", length = 500)
    private String providerAvatar;
    
    /**
     * 访问令牌（加密存储）
     */
    @Column(name = "access_token", columnDefinition = "TEXT")
    private String accessToken;
    
    /**
     * 刷新令牌
     */
    @Column(name = "refresh_token", columnDefinition = "TEXT")
    private String refreshToken;
    
    /**
     * 令牌过期时间
     */
    @Column(name = "expires_at")
    private LocalDateTime expiresAt;
}

