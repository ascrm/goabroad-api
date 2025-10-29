package com.goabroad.model.community.post.entity;

import com.goabroad.model.BaseEntity;
import lombok.*;

import jakarta.persistence.*;

/**
 * 帖子点赞记录实体类
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
@Table(name = "post_likes", indexes = {
    @Index(name = "idx_user_id", columnList = "user_id"),
    @Index(name = "idx_deleted", columnList = "deleted")
}, uniqueConstraints = {
    @UniqueConstraint(name = "uk_post_user", columnNames = {"post_id", "user_id"})
})
public class PostLike extends BaseEntity {
    
    /**
     * 帖子ID
     */
    @Column(name = "post_id", nullable = false)
    private Long postId;
    
    /**
     * 用户ID
     */
    @Column(name = "user_id", nullable = false)
    private Long userId;
}

