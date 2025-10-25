package com.goabroad.model.vo;

import com.goabroad.model.enums.Gender;
import com.goabroad.model.enums.UserStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 用户视图对象
 * 
 * @author GoAbroad Team
 * @version 1.0
 * @since 2024-10-19
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "用户视图对象")
public class UserVo implements Serializable {
    
    @Serial
    private static final long serialVersionUID = 1L;
    
    @Schema(description = "用户ID", example = "1001")
    private Long id;
    
    @Schema(description = "用户名", example = "johndoe")
    private String username;
    
    @Schema(description = "邮箱（脱敏）", example = "joh****@example.com")
    private String email;
    
    @Schema(description = "手机号（脱敏）", example = "138****8000")
    private String phone;
    
    @Schema(description = "昵称", example = "约翰")
    private String nickname;
    
    @Schema(description = "头像URL", example = "https://cdn.example.com/avatar/1001.jpg")
    private String avatarUrl;
    
    @Schema(description = "个人简介", example = "热爱旅行的留学生")
    private String bio;
    
    @Schema(description = "性别", example = "MALE")
    private Gender gender;
    
    @Schema(description = "用户等级", example = "5")
    private Short level;
    
    @Schema(description = "积分", example = "1500")
    private Integer points;
    
    @Schema(description = "经验值", example = "8600")
    private Integer exp;
    
    @Schema(description = "发帖数", example = "42")
    private Integer postCount;
    
    @Schema(description = "粉丝数", example = "128")
    private Integer followerCount;
    
    @Schema(description = "关注数", example = "86")
    private Integer followingCount;
    
    @Schema(description = "账号状态", example = "ACTIVE")
    private UserStatus status;
    
    @Schema(description = "邮箱是否验证", example = "true")
    private Boolean emailVerified;
    
    @Schema(description = "手机是否验证", example = "true")
    private Boolean phoneVerified;
    
    @Schema(description = "是否会员", example = "false")
    private Boolean isVip;
    
    @Schema(description = "会员到期时间", example = "2025-12-31T23:59:59")
    private LocalDateTime vipExpireAt;
    
    @Schema(description = "最后登录时间", example = "2024-10-25T10:30:00")
    private LocalDateTime lastLoginAt;
    
    @Schema(description = "创建时间", example = "2024-01-15T08:00:00")
    private LocalDateTime createdAt;
    
    @Schema(description = "更新时间", example = "2024-10-25T10:30:00")
    private LocalDateTime updatedAt;
}
