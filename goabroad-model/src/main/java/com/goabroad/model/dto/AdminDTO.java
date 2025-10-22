package com.goabroad.model.dto;

import com.goabroad.model.dto.validation.Create;
import com.goabroad.model.enums.AdminRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 管理员DTO（创建和更新共用）
 * 
 * @author GoAbroad Team
 * @version 1.0
 * @since 2024-10-21
 */
@Data
public class AdminDTO {
    
    @NotBlank(message = "用户名不能为空", groups = Create.class)
    private String username;
    
    @NotBlank(message = "密码不能为空", groups = Create.class)
    private String password;
    
    @Email(message = "邮箱格式不正确")
    private String email;
    
    private String nickname;
    
    @NotNull(message = "角色不能为空", groups = Create.class)
    private AdminRole role;
}

