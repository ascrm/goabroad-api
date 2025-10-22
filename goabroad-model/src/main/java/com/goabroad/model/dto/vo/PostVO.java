package com.goabroad.model.dto.vo;

import com.goabroad.model.enums.ContentType;
import com.goabroad.model.enums.PostStatus;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 帖子视图对象（包含作者信息、统计信息等）
 * 
 * @author GoAbroad Team
 * @version 1.0
 * @since 2024-10-21
 */
@Data
public class PostVO {
    
    private Long id;
    
    // 作者信息
    private Long authorId;
    private String authorUsername;
    private String authorNickname;
    private String authorAvatar;
    
    // 帖子内容
    private String title;
    private String content;
    private ContentType contentType;
    private String category;
    private String countryCode;
    private List<String> tags;
    private List<String> images;
    
    // 统计信息
    private Integer viewCount;
    private Integer likeCount;
    private Integer commentCount;
    private Integer collectCount;
    
    // 状态
    private Boolean isFeatured;
    private Boolean isHot;
    private PostStatus status;
    
    // 时间
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    // 当前用户交互状态
    private Boolean isLiked;
    private Boolean isCollected;
}

