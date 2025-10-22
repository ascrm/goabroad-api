package com.goabroad.model.dto.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.goabroad.model.enums.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 用户响应DTO
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
     * 邮箱
     */
    private String email;
    
    /**
     * 头像URL
     */
    private String avatarUrl;
    
    /**
     * 昵称
     */
    private String nickname;
    
    /**
     * 用户状态
     */
    private UserStatus status;
    
    /**
     * 用户等级
     */
    private Integer level;
    
    /**
     * 积分
     */
    private Integer points;
    
    /**
     * 个人简介
     */
    private String bio;
    
    /**
     * 手机号
     */
    private String phone;
    
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
    
    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;
}

