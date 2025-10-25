package com.goabroad.model.vo;

import com.goabroad.model.enums.Gender;
import com.goabroad.model.enums.UserStatus;
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
public class UserVo implements Serializable {
    
    @Serial
    private static final long serialVersionUID = 1L;
    
    /**
     * 用户ID
     */
    private Long id;
    
    /**
     * 用户名
     */
    private String username;
    
    /**
     * 邮箱（脱敏）
     */
    private String email;
    
    /**
     * 手机号（脱敏）
     */
    private String phone;
    
    /**
     * 昵称
     */
    private String nickname;
    
    /**
     * 头像URL
     */
    private String avatarUrl;
    
    /**
     * 个人简介
     */
    private String bio;
    
    /**
     * 性别
     */
    private Gender gender;
    
    /**
     * 用户等级
     */
    private Short level;
    
    /**
     * 积分
     */
    private Integer points;
    
    /**
     * 经验值
     */
    private Integer exp;
    
    /**
     * 发帖数
     */
    private Integer postCount;
    
    /**
     * 粉丝数
     */
    private Integer followerCount;
    
    /**
     * 关注数
     */
    private Integer followingCount;
    
    /**
     * 账号状态
     */
    private UserStatus status;
    
    /**
     * 邮箱是否验证
     */
    private Boolean emailVerified;
    
    /**
     * 手机是否验证
     */
    private Boolean phoneVerified;
    
    /**
     * 是否会员
     */
    private Boolean isVip;
    
    /**
     * 会员到期时间
     */
    private LocalDateTime vipExpireAt;
    
    /**
     * 最后登录时间
     */
    private LocalDateTime lastLoginAt;
    
    /**
     * 创建时间
     */
    private LocalDateTime createdAt;
    
    /**
     * 更新时间
     */
    private LocalDateTime updatedAt;
}
