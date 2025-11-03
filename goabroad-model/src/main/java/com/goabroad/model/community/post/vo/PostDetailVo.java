package com.goabroad.model.community.post.vo;

import com.goabroad.model.community.post.enums.ContentType;
import com.goabroad.model.community.post.enums.PostStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 帖子详情VO
 * 
 * @author GoAbroad Team
 * @version 1.0
 * @since 2024-10-28
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "帖子详情")
public class PostDetailVo {
    
    /**
     * 帖子ID
     */
    @Schema(description = "帖子ID", example = "1")
    private Long id;
    
    /**
     * 作者ID
     */
    @Schema(description = "作者用户ID", example = "456")
    private Long authorId;
    
    /**
     * 内容类型
     */
    @Schema(description = "内容类型", example = "GUIDE")
    private ContentType contentType;
    
    /**
     * 标题
     */
    @Schema(description = "标题", example = "美国F1签证面签完整攻略")
    private String title;
    
    /**
     * 内容
     */
    @Schema(description = "正文内容（Markdown格式）")
    private String content;
    
    /**
     * 摘要
     */
    @Schema(description = "摘要", example = "F1签证面签通过，分享准备材料和面试技巧")
    private String summary;
    
    /**
     * 分类
     */
    @Schema(description = "分类", example = "签证")
    private String category;
    
    /**
     * 封面图片URL
     */
    @Schema(description = "封面图URL", example = "https://cdn.goabroad.com/posts/cover-123.jpg")
    private String coverImage;
    
    /**
     * 媒体文件URL数组（图片/视频）
     */
    @Schema(description = "图片/视频 URL 数组")
    private List<String> mediaUrls;
    
    /**
     * 发布状态
     */
    @Schema(description = "发布状态", example = "PUBLISHED")
    private PostStatus status;
    
    /**
     * 是否允许评论
     */
    @Schema(description = "是否允许评论", example = "true")
    private Boolean allowComment;
    
    /**
     * 浏览量
     */
    @Schema(description = "浏览量", example = "0")
    private Integer viewCount;
    
    /**
     * 点赞数
     */
    @Schema(description = "点赞数", example = "0")
    private Integer likeCount;
    
    /**
     * 评论数
     */
    @Schema(description = "评论数", example = "0")
    private Integer commentCount;
    
    /**
     * 收藏数
     */
    @Schema(description = "收藏数", example = "0")
    private Integer collectCount;
    
    /**
     * 分享数
     */
    @Schema(description = "分享数", example = "0")
    private Integer shareCount;
    
    /**
     * 是否精选（管理员设置）
     */
    @Schema(description = "是否精选（管理员设置）", example = "false")
    private Boolean isFeatured;
    
    /**
     * 是否置顶（管理员设置）
     */
    @Schema(description = "是否置顶（管理员设置）", example = "false")
    private Boolean isPinned;
    
    /**
     * 是否热门（系统计算）
     */
    @Schema(description = "是否热门（系统计算）", example = "false")
    private Boolean isHot;
    
    /**
     * 创建时间
     */
    @Schema(description = "创建时间", example = "2024-11-03T10:00:00")
    private LocalDateTime createdAt;
    
    /**
     * 更新时间
     */
    @Schema(description = "更新时间", example = "2024-11-03T10:00:00")
    private LocalDateTime updatedAt;
    
    /**
     * 发布时间
     */
    @Schema(description = "发布时间", example = "2024-11-03T10:00:00")
    private LocalDateTime publishedAt;
}

