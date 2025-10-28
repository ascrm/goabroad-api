package com.goabroad.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 帖子简要信息VO (用于列表展示)
 * 
 * @author GoAbroad Team
 * @version 1.0
 * @since 2024-10-28
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "帖子简要信息")
public class PostSimpleVo {
    
    @Schema(description = "帖子ID", example = "post-123")
    private Long id;
    
    @Schema(description = "标题", example = "美国F1签证面签经验分享")
    private String title;
    
    @Schema(description = "内容预览", example = "今天刚刚通过面签...")
    private String contentPreview;
    
    @Schema(description = "封面图", example = "https://cdn.goabroad.com/posts/cover-123.jpg")
    private String coverImage;
    
    @Schema(description = "内容类型", example = "POST")
    private String contentType;
    
    @Schema(description = "标签", example = "[\"美国\", \"签证\", \"F1\"]")
    private List<String> tags;
    
    @Schema(description = "点赞数", example = "125")
    private Integer likeCount;
    
    @Schema(description = "评论数", example = "32")
    private Integer commentCount;
    
    @Schema(description = "收藏数", example = "85")
    private Integer collectCount;
    
    @Schema(description = "浏览数", example = "1520")
    private Integer viewCount;
    
    @Schema(description = "是否点赞", example = "false")
    private Boolean isLiked;
    
    @Schema(description = "是否收藏", example = "false")
    private Boolean isCollected;
    
    @Schema(description = "创建时间", example = "2024-10-20T10:00:00")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdAt;
}

