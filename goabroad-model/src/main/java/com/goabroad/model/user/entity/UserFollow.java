package com.goabroad.model.user.entity;

import com.goabroad.model.BaseEntity;
import lombok.*;

import jakarta.persistence.*;

/**
 * 用户关注关系实体类
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
@Table(name = "user_follows", indexes = {
    @Index(name = "idx_followee_id", columnList = "followee_id"),
    @Index(name = "idx_deleted", columnList = "deleted")
}, uniqueConstraints = {
    @UniqueConstraint(name = "uk_follower_followee", columnNames = {"follower_id", "followee_id"})
})
public class UserFollow extends BaseEntity {
    
    /**
     * 关注者ID
     */
    @Column(name = "follower_id", nullable = false)
    private Long followerId;
    
    /**
     * 被关注者ID
     */
    @Column(name = "followee_id", nullable = false)
    private Long followeeId;
}

