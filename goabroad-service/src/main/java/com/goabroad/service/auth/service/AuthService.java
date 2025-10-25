package com.goabroad.service.auth.service;

import com.goabroad.model.dto.LoginDto;
import com.goabroad.model.dto.RegisterDto;
import com.goabroad.model.vo.UserVo;

/**
 * 认证服务接口
 * 
 * @author GoAbroad Team
 * @version 1.0
 * @since 2024-10-19
 */
public interface AuthService {
    
    /**
     * 用户注册
     * 
     * @param request 注册请求
     */
    void register(RegisterDto request);
    
    /**
     * 用户登录
     * 
     * @param request 登录请求
     * @return 认证响应（包含token）
     */
    UserVo login(LoginDto request);
    
    /**
     * 用户登出
     * 
     * @param userId 用户ID
     */
    void logout(Long userId);

    /**
     * 发送短信验证码
     *
     * @param phone 手机号
     */
    void sendSmsVerificationCode(String phone);
    
    /**
     * 验证验证码
     * 
     * @param email 邮箱
     * @param code 验证码
     * @return 是否验证成功
     */
    boolean verifyCode(String email, String code);
}
