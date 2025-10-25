package com.goabroad.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "认证信息DTO")
public class AuthDTO {
    
    @Schema(description = "访问令牌", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
    private String accessToken;
    
    @Schema(description = "刷新令牌", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
    private String refreshToken;
    
    @Schema(description = "令牌过期时间（秒）", example = "7200")
    private Long expiresIn;
    
    @Schema(description = "令牌类型", example = "Bearer")
    private String tokenType = "Bearer";
    
    @Schema(description = "用户ID", example = "1001")
    private Long userId;
    
    @Schema(description = "用户名", example = "johndoe")
    private String username;
}

