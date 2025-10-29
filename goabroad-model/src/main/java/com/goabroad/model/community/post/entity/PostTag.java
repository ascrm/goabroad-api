package com.goabroad.model.community.post.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

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
@EntityListeners(AuditingEntityListener.class)
public class PostTag implements Serializable {
    
    @Serial
    private static final long serialVersionUID = 1L;
    
    /**
     * 主键ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
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
    
    /**
     * 创建时间
     */
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    /**
     * 逻辑删除标记
     */
    @Column(name = "deleted", nullable = false)
    @Builder.Default
    private Boolean deleted = false;
    
    /**
     * 乐观锁版本号
     */
    @Version
    @Column(name = "version_id", nullable = false)
    @Builder.Default
    private Integer versionId = 0;
}

