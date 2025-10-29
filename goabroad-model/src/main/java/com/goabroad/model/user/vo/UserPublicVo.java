package com.goabroad.model.user.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.goabroad.model.user.enums.Gender;
import com.goabroad.model.user.enums.UserStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 用户公开资料VO
 * 
 * @author GoAbroad Team
 * @version 1.0
 * @since 2024-10-25
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "用户公开资料")
public class UserPublicVo {
    
    @Schema(description = "用户ID", example = "1")
    private Long id;
    
    @Schema(description = "用户名", example = "goabroad_xiaoxin")
    private String username;
    
    @Schema(description = "昵称", example = "GoAbroad小新")
    private String nickname;
    
    @Schema(description = "头像URL", example = "https://cdn.goabroad.com/avatars/uuid-123.jpg")
    private String avatarUrl;
    
    @Schema(description = "个人简介", example = "正在准备美国留学")
    private String bio;
    
    @Schema(description = "性别", example = "MALE")
    private Gender gender;
    
    @Schema(description = "用户等级", example = "5")
    private Short level;
    
    @Schema(description = "账号状态", example = "ACTIVE")
    private UserStatus status;
    
    @Schema(description = "徽章列表", example = "[\"新人\", \"探索者\", \"热心助人\"]")
    private List<String> badges;
    
    @Schema(description = "目标国家", example = "US")
    private String targetCountry;
    
    @Schema(description = "统计信息")
    private UserStats stats;
    
    @Schema(description = "当前用户是否关注该用户", example = "false")
    private Boolean isFollowing;
    
    @Schema(description = "创建时间", example = "2024-01-01T10:00:00")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdAt;
    
    /**
     * 用户统计信息
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "用户统计信息")
    public static class UserStats {
        @Schema(description = "帖子数", example = "25")
        private Integer postsCount;
        
        @Schema(description = "粉丝数", example = "120")
        private Integer followersCount;
        
        @Schema(description = "关注数", example = "85")
        private Integer followingCount;
        
        @Schema(description = "获赞数", example = "350")
        private Integer likesCount;
    }
}

