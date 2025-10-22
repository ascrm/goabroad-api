package com.goabroad.model.entity;

import com.goabroad.model.enums.CommentStatus;
import lombok.*;

import jakarta.persistence.*;

/**
 * 评论实体类
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
@Table(name = "comments", indexes = {
    @Index(name = "idx_post_id", columnList = "post_id"),
    @Index(name = "idx_author_id", columnList = "author_id"),
    @Index(name = "idx_parent_id", columnList = "parent_id"),
    @Index(name = "idx_root_id", columnList = "root_id"),
    @Index(name = "idx_created_at", columnList = "created_at"),
    @Index(name = "idx_deleted", columnList = "deleted")
})
public class Comment extends BaseEntity {
    
    /**
     * 帖子ID
     */
    @Column(name = "post_id", nullable = false)
    private Long postId;
    
    /**
     * 评论者ID
     */
    @Column(name = "author_id", nullable = false)
    private Long authorId;
    
    /**
     * 父评论ID（回复用）
     */
    @Column(name = "parent_id")
    private Long parentId;
    
    /**
     * 根评论ID（方便查询楼层）
     */
    @Column(name = "root_id")
    private Long rootId;
    
    /**
     * 评论内容
     */
    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    private String content;
    
    // ========== 统计 ==========
    
    /**
     * 点赞数
     */
    @Column(name = "like_count", nullable = false)
    @Builder.Default
    private Integer likeCount = 0;
    
    /**
     * 回复数
     */
    @Column(name = "reply_count", nullable = false)
    @Builder.Default
    private Integer replyCount = 0;
    
    // ========== 状态 ==========
    
    /**
     * 状态
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    @Builder.Default
    private CommentStatus status = CommentStatus.VISIBLE;
}
