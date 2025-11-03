package com.goabroad.model.user.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 用户简要信息VO (用于列表展示)
 * 
 * @author GoAbroad Team
 * @version 1.0
 * @since 2024-10-25
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "用户简要信息")
public class UserSimpleVo {
    
    @Schema(description = "用户ID", example = "1")
    private Long id;
    
    @Schema(description = "用户名", example = "zhangsan")
    private String username;
    
    @Schema(description = "昵称", example = "张三")
    private String nickname;
    
    @Schema(description = "头像URL", example = "https://cdn.goabroad.com/avatars/1.jpg")
    private String avatarUrl;
    
    @Schema(description = "个人简介", example = "正在准备留学")
    private String bio;
    
    @Schema(description = "是否关注", example = "true")
    private Boolean isFollowing;
    
    @Schema(description = "创建时间", example = "2024-01-01T10:00:00")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdAt;
}

