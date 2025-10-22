package com.goabroad.model.dto.vo;

import com.goabroad.model.enums.CommentStatus;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 评论视图对象（包含作者信息、回复等）
 * 
 * @author GoAbroad Team
 * @version 1.0
 * @since 2024-10-21
 */
@Data
public class CommentVO {
    
    private Long id;
    private Long postId;
    
    // 作者信息
    private Long authorId;
    private String authorUsername;
    private String authorNickname;
    private String authorAvatar;
    
    // 评论内容
    private String content;
    
    // 回复关系
    private Long parentId;
    private Long rootId;
    private Long replyToUserId;
    private String replyToUsername;
    
    // 统计
    private Integer likeCount;
    private Integer replyCount;
    
    // 状态
    private CommentStatus status;
    private LocalDateTime createdAt;
    
    // 当前用户状态
    private Boolean isLiked;
    
    // 子评论
    private List<CommentVO> replies;
}

