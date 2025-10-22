package com.goabroad.model.entity;

import lombok.*;

import jakarta.persistence.*;

/**
 * 帖子收藏记录实体类
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
@Table(name = "post_collections", indexes = {
    @Index(name = "idx_user_folder", columnList = "user_id,folder"),
    @Index(name = "idx_deleted", columnList = "deleted")
}, uniqueConstraints = {
    @UniqueConstraint(name = "uk_post_user", columnNames = {"post_id", "user_id"})
})
public class PostCollection extends BaseEntity {
    
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
    
    /**
     * 收藏夹名称
     */
    @Column(name = "folder", length = 50)
    @Builder.Default
    private String folder = "default";
}

