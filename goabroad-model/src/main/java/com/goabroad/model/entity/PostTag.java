package com.goabroad.model.entity;

import lombok.*;

import jakarta.persistence.*;

/**
 * 帖子-标签关联实体类
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
@Table(name = "post_tags", indexes = {
    @Index(name = "idx_tag_id", columnList = "tag_id"),
    @Index(name = "idx_deleted", columnList = "deleted")
}, uniqueConstraints = {
    @UniqueConstraint(name = "uk_post_tag", columnNames = {"post_id", "tag_id"})
})
public class PostTag extends BaseEntity {
    
    /**
     * 帖子ID
     */
    @Column(name = "post_id", nullable = false)
    private Long postId;
    
    /**
     * 标签ID
     */
    @Column(name = "tag_id", nullable = false)
    private Long tagId;
}

