package com.goabroad.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.Size;
import java.io.Serial;
import java.io.Serializable;

/**
 * 更新用户信息请求DTO
 * 
 * @author GoAbroad Team
 * @version 1.0
 * @since 2024-10-19
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserRequest implements Serializable {
    
    @Serial
    private static final long serialVersionUID = 1L;
    
    /**
     * 昵称
     */
    @Size(max = 50, message = "昵称长度不能超过50位")
    private String nickname;
    
    /**
     * 头像URL
     */
    @Size(max = 500, message = "头像URL长度不能超过500位")
    private String avatarUrl;
    
    /**
     * 个人简介
     */
    @Size(max = 500, message = "个人简介长度不能超过500位")
    private String bio;
    
    /**
     * 手机号
     */
    @Size(max = 20, message = "手机号长度不能超过20位")
    private String phone;
}

