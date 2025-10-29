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
     * 作者信息
     */
    @Schema(description = "作者信息")
    private AuthorVo author;
    
    /**
     * 内容类型
     */
    @Schema(description = "内容类型", example = "POST")
    private ContentType contentType;
    
    /**
     * 标题
     */
    @Schema(description = "标题", example = "美国F1签证面签经验分享")
    private String title;
    
    /**
     * 内容
     */
    @Schema(description = "内容（Markdown格式）")
    private String content;
    
    /**
     * 状态
     */
    @Schema(description = "状态", example = "PUBLISHED")
    private PostStatus status;
    
    /**
     * 封面图片URL
     */
    @Schema(description = "封面图片URL")
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
    @Schema(description = "标签列表")
    private List<String> tags;
    
    /**
     * 国家代码
     */
    @Schema(description = "国家代码", example = "US")
    private String country;
    
    /**
     * 国家名称
     */
    @Schema(description = "国家名称", example = "美国")
    private String countryName;
    
    /**
     * 阶段标签
     */
    @Schema(description = "阶段标签", example = "签证办理")
    private String stage;
    
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
     * 浏览数
     */
    @Schema(description = "浏览数", example = "0")
    private Integer viewCount;
    
    /**
     * 是否点赞
     */
    @Schema(description = "当前用户是否点赞", example = "false")
    private Boolean isLiked;
    
    /**
     * 是否收藏
     */
    @Schema(description = "当前用户是否收藏", example = "false")
    private Boolean isCollected;
    
    /**
     * 是否置顶
     */
    @Schema(description = "是否置顶", example = "false")
    private Boolean isPinned;
    
    /**
     * 是否精选
     */
    @Schema(description = "是否精选", example = "false")
    private Boolean isFeatured;
    
    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    private LocalDateTime createdAt;
    
    /**
     * 更新时间
     */
    @Schema(description = "更新时间")
    private LocalDateTime updatedAt;
    
    /**
     * 作者简要信息
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "作者信息")
    public static class AuthorVo {
        
        @Schema(description = "用户ID", example = "1")
        private Long id;
        
        @Schema(description = "用户名", example = "user_123")
        private String username;
        
        @Schema(description = "昵称", example = "GoAbroad小新")
        private String nickname;
        
        @Schema(description = "头像URL")
        private String avatarUrl;
        
        @Schema(description = "个人简介")
        private String bio;
        
        @Schema(description = "用户等级", example = "5")
        private Short level;
        
        @Schema(description = "徽章列表")
        private List<String> badges;
        
        @Schema(description = "是否关注该作者", example = "false")
        private Boolean isFollowing;
    }
}

