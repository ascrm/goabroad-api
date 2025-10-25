package com.goabroad.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.io.Serial;
import java.io.Serializable;

import static com.goabroad.common.constant.AppConstant.USERNAME_REGEX;

/**
 * 用户注册请求DTO（手机短信注册）
 * 
 * @author GoAbroad Team
 * @version 1.0
 * @since 2024-10-19
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterDto implements Serializable {
    
    @Serial
    private static final long serialVersionUID = 1L;
    
    /**
     * 手机号（必填）
     */
    @NotBlank(message = "手机号不能为空")
    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机号格式不正确")
    private String phone;
    
    /**
     * 短信验证码（必填）
     */
    @NotBlank(message = "验证码不能为空")
    @Pattern(regexp = "^\\d{6}$", message = "验证码格式不正确")
    private String code;
    
    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空")
    @Size(min = 6, max = 20, message = "密码长度必须在6-20位之间")
    private String password;
    
    /**
     * 昵称（可选）
     */
    @Size(max = 50, message = "昵称长度不能超过50位")
    private String nickname;
    
    // ========== 以下字段保留用于后续绑定功能 ==========
    
    /**
     * 用户名（注册时自动生成，后续可修改）
     */
    @Pattern(regexp = USERNAME_REGEX, message = "用户名格式不正确，只能包含字母、数字、下划线，长度3-20位")
    private String username;
    
    /**
     * 邮箱（可选，注册时为空，后续可绑定）
     */
    @Email(message = "邮箱格式不正确")
    private String email;
}

