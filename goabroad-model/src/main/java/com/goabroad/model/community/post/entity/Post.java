package com.goabroad.model.community.post.entity;

import com.goabroad.model.BaseEntity;
import com.goabroad.model.community.post.enums.ContentType;
import com.goabroad.model.community.post.enums.PostStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 帖子实体类
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
@Table(name = "posts", indexes = {
    @Index(name = "idx_author_id", columnList = "author_id"),
    @Index(name = "idx_category", columnList = "category"),
    @Index(name = "idx_country_code", columnList = "country_code"),
    @Index(name = "idx_content_type", columnList = "content_type"),
    @Index(name = "idx_status", columnList = "status"),
    @Index(name = "idx_created_at", columnList = "created_at"),
    @Index(name = "idx_hot_featured", columnList = "is_hot,is_featured"),
    @Index(name = "idx_deleted", columnList = "deleted")
})
public class Post extends BaseEntity {
    
    /**
     * 作者ID
     */
    @Column(name = "author_id", nullable = false)
    private Long authorId;
    
    // ========== 内容 ==========
    
    /**
     * 标题
     */
    @Column(name = "title", nullable = false, length = 200)
    private String title;
    
    /**
     * 正文内容（Markdown）
     */
    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    private String content;
    
    /**
     * 摘要（自动提取）
     */
    @Column(name = "summary", length = 500)
    private String summary;
    
    // ========== 类型 ==========
    
    /**
     * 内容类型
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "content_type", nullable = false, length = 20)
    private ContentType contentType;
    
    /**
     * 分类
     */
    @Column(name = "category", length = 50)
    private String category;
    
    // ========== 媒体 ==========
    
    /**
     * 封面图
     */
    @Column(name = "cover_image", length = 500)
    private String coverImage;
    
    /**
     * 图片/视频列表 ["url1","url2"]
     */
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "media_urls", columnDefinition = "JSONB")
    private List<String> mediaUrls;
    
    // ========== 统计（冗余，提升性能） ==========
    
    /**
     * 浏览量
     */
    @Column(name = "view_count", nullable = false)
    @Builder.Default
    private Integer viewCount = 0;
    
    /**
     * 点赞数
     */
    @Column(name = "like_count", nullable = false)
    @Builder.Default
    private Integer likeCount = 0;
    
    /**
     * 评论数
     */
    @Column(name = "comment_count", nullable = false)
    @Builder.Default
    private Integer commentCount = 0;
    
    /**
     * 收藏数
     */
    @Column(name = "collect_count", nullable = false)
    @Builder.Default
    private Integer collectCount = 0;
    
    /**
     * 分享数
     */
    @Column(name = "share_count", nullable = false)
    @Builder.Default
    private Integer shareCount = 0;
    
    // ========== 状态 ==========
    
    /**
     * 是否精选
     */
    @Column(name = "is_featured", nullable = false)
    @Builder.Default
    private Boolean isFeatured = false;
    
    /**
     * 是否置顶
     */
    @Column(name = "is_pinned", nullable = false)
    @Builder.Default
    private Boolean isPinned = false;
    
    /**
     * 是否热门
     */
    @Column(name = "is_hot", nullable = false)
    @Builder.Default
    private Boolean isHot = false;
    
    /**
     * 发布状态
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    @Builder.Default
    private PostStatus status = PostStatus.PUBLISHED;
    
    // ========== 权限 ==========
    
    /**
     * 允许评论
     */
    @Column(name = "allow_comment", nullable = false)
    @Builder.Default
    private Boolean allowComment = true;
    
    // ========== SEO ==========
    
    /**
     * URL友好标识
     */
    @Column(name = "slug", length = 200)
    private String slug;
    
    /**
     * 发布时间
     */
    @Column(name = "published_at")
    private LocalDateTime publishedAt;
}
