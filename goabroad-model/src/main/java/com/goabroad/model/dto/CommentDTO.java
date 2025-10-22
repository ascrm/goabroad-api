package com.goabroad.model.dto;

import com.goabroad.model.dto.validation.Create;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 评论DTO
 * 
 * @author GoAbroad Team
 * @version 1.0
 * @since 2024-10-21
 */
@Data
public class CommentDTO {
    
    @NotNull(message = "帖子ID不能为空", groups = Create.class)
    private Long postId;
    
    @NotBlank(message = "评论内容不能为空", groups = Create.class)
    private String content;
    
    private Long parentId;
    
    private Long replyToUserId;
}

