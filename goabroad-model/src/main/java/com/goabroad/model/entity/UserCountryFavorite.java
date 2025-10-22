package com.goabroad.model.entity;

import lombok.*;

import jakarta.persistence.*;

/**
 * 用户收藏国家实体类
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
@Table(name = "user_country_favorites", indexes = {
    @Index(name = "uk_user_country", columnList = "user_id,country_code", unique = true),
    @Index(name = "idx_country_code", columnList = "country_code"),
    @Index(name = "idx_deleted", columnList = "deleted")
})
public class UserCountryFavorite extends BaseEntity {
    
    /**
     * 用户ID
     */
    @Column(name = "user_id", nullable = false)
    private Long userId;
    
    /**
     * 国家代码
     */
    @Column(name = "country_code", nullable = false, length = 2)
    private String countryCode;
}

