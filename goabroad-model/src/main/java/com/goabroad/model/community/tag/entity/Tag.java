package com.goabroad.model.community.tag.entity;

import com.goabroad.model.BaseEntity;
import lombok.*;

import jakarta.persistence.*;

/**
 * 标签字典实体类
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
@Table(name = "tags", indexes = {
    @Index(name = "idx_post_count", columnList = "post_count"),
    @Index(name = "idx_deleted", columnList = "deleted")
}, uniqueConstraints = {
    @UniqueConstraint(name = "uk_name", columnNames = "name"),
    @UniqueConstraint(name = "uk_slug", columnNames = "slug")
})
public class Tag extends BaseEntity {
    
    /**
     * 标签名称
     */
    @Column(name = "name", nullable = false, length = 50)
    private String name;
    
    /**
     * URL友好标识
     */
    @Column(name = "slug", nullable = false, length = 50)
    private String slug;
    
    /**
     * 标签描述
     */
    @Column(name = "description", length = 200)
    private String description;
    
    /**
     * 标签颜色（HEX）
     */
    @Column(name = "color", length = 20)
    private String color;
    
    /**
     * 帖子数（冗余）
     */
    @Column(name = "post_count", nullable = false)
    @Builder.Default
    private Integer postCount = 0;
}

