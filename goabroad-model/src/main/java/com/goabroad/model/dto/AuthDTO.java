package com.goabroad.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 认证信息DTO（登录返回）
 * 
 * @author GoAbroad Team
 * @version 1.0
 * @since 2024-10-21
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthDTO {
    
    private String accessToken;
    
    private String refreshToken;
    
    private Long expiresIn;
    
    private String tokenType = "Bearer";
    
    private Long userId;
    
    private String username;
}

