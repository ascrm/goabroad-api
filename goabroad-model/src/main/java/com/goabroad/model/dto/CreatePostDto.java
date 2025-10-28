package com.goabroad.model.dto;

import com.goabroad.model.enums.ContentType;
import com.goabroad.model.enums.PostStatus;
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
    @Schema(description = "内容类型：POST-经验分享, QUESTION-问题, TIMELINE-时间线, VLOG-视频", 
            example = "POST")
    private ContentType contentType;
    
    /**
     * 标题
     */
    @Size(min = 1, max = 200, message = "标题长度必须在1-200之间")
    @Schema(description = "帖子标题", example = "美国F1签证面签经验分享",requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String title;
    
    /**
     * 内容（Markdown格式）
     */
    @NotBlank(message = "内容不能为空")
    @Size(min = 1, max = 50000, message = "内容长度必须在1-50000之间")
    @Schema(description = "帖子内容（Markdown格式）", 
            example = "# 前言\n\n今天刚刚通过面签...")
    private String content;
    
    /**
     * 发布状态
     */
    @Schema(description = "发布状态：DRAFT-草稿, PUBLISHED-已发布", 
            example = "PUBLISHED", defaultValue = "PUBLISHED")
    private PostStatus status;
    
    /**
     * 封面图片URL
     */
    @Size(max = 500, message = "封面图片URL长度不能超过500")
    @Schema(description = "封面图片URL", 
            example = "https://cdn.goabroad.com/posts/cover-123.jpg")
    private String coverImage;
    
    /**
     * 图片URL列表
     */
    @Schema(description = "图片URL列表")
    private List<String> images;
    
    /**
     * 视频URL列表
     */
    @Schema(description = "视频URL列表")
    private List<String> videos;
    
    /**
     * 标签列表
     */
    @Schema(description = "标签列表", example = "[\"美国\", \"签证\", \"F1\"]")
    private List<String> tags;
    
    /**
     * 国家代码
     */
    @Size(max = 10, message = "国家代码长度不能超过10")
    @Schema(description = "国家代码", example = "US")
    private String country;
    
    /**
     * 阶段标签
     */
    @Size(max = 50, message = "阶段标签长度不能超过50")
    @Schema(description = "阶段标签", example = "签证办理")
    private String stage;
}

