package com.goabroad.model.community.post.dto;

import com.goabroad.model.community.post.enums.ContentType;
import com.goabroad.model.community.post.enums.PostStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 发布帖子请求DTO
 * 
 * @author GoAbroad Team
 * @version 1.0
 * @since 2024-10-28
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "发布帖子请求")
public class CreatePostDto {
    
    /**
     * 内容类型
     */
    @NotNull(message = "内容类型不能为空")
    @Schema(description = "内容类型：攻略，日常，提问，回答",
            example = "POST")
    private ContentType contentType;
    
    /**
     * 父帖子ID（回答帖子关联到问题帖子）
     */
    @Schema(description = "父帖子ID（创建回答帖子时必填，问题帖子无需填写）", 
            example = "123",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Long parentPostId;
    
    /**
     * 标题
     */
    @Size(min = 1, max = 200, message = "标题长度必须在1-200之间")
    @Schema(description = "帖子标题", example = "美国F1签证面签经验分享",requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String title;
    
    /**
     * 内容
     */
    @Size(max = 50000, message = "内容长度不能超过50000")
    @Schema(description = "帖子内容（Markdown格式）", 
            example = "# 前言\n\n今天刚刚通过面签...",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String content;
    
    /**
     * 摘要（用于列表展示）
     */
    @Size(max = 500, message = "摘要长度不能超过500")
    @Schema(description = "摘要（用于列表展示，不传则后端自动截取content前100字）", 
            example = "F1签证面签通过，分享准备材料和面试技巧",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String summary;
    
    /**
     * 分类标签
     */
    @Size(max = 50, message = "分类标签长度不能超过50")
    @Schema(description = "分类标签（如：留学、签证、生活等）", 
            example = "签证",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String category;
    
    /**
     * 封面图片URL
     */
    @Size(max = 500, message = "封面图片URL长度不能超过500")
    @Schema(description = "封面图片URL", 
            example = "https://cdn.goabroad.com/posts/cover-123.jpg",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String coverImage;
    
    /**
     * 图片/视频URL数组
     */
    @Schema(description = "图片/视频 URL 数组",
            example = "[\"url1\", \"url2\"]",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private List<String> mediaUrls;
    
    /**
     * 发布状态
     */
    @Schema(description = "发布状态：DRAFT-草稿, PUBLISHED-已发布", 
            example = "PUBLISHED", 
            defaultValue = "PUBLISHED",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private PostStatus status;
    
    /**
     * 是否允许评论
     */
    @Schema(description = "是否允许评论", 
            example = "true", 
            defaultValue = "true",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Boolean allowComment;
    
    /**
     * 标签列表
     */
    @Schema(description = "标签列表", 
            example = "[\"留学\", \"签证\", \"美国\"]",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private List<String> tags;
}

