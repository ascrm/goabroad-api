package com.goabroad.web.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.goabroad.common.response.Result;
import com.goabroad.model.dto.LoginDto;
import com.goabroad.model.dto.RegisterDto;
import com.goabroad.model.dto.vo.UserVo;
import com.goabroad.service.auth.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 认证控制器
 * 
 * @author GoAbroad Team
 * @version 1.0
 * @since 2024-10-19
 */
@RestController
@RequestMapping("/api/auth")
@Tag(name = "认证管理", description = "用户认证相关接口")
@Slf4j
@RequiredArgsConstructor
public class AuthController {
    
    private final AuthService authService;

    @GetMapping("/test")
    public String test(){
        return "连接成功";
    }

    /**
     * 用户注册（手机号注册）
     */
    @PostMapping("/register")
    @Operation(summary = "用户注册", description = "手机号短信验证码注册")
    public Result<Map<String, Object>> register(@Valid @RequestBody RegisterDto request) {
        authService.register(request);
        
        // 注册成功后自动登录返回用户信息和Token
        String token = StpUtil.getTokenValue();
        Map<String, Object> data = new HashMap<>();
        data.put("token", token);
        data.put("tokenName", StpUtil.getTokenName());
        data.put("tokenTimeout", StpUtil.getTokenTimeout());
        
        return Result.success("注册成功", data);
    }
    
    /**
     * 用户登录
     */
    @PostMapping("/login")
    @Operation(summary = "用户登录", description = "手机号或邮箱+密码登录")
    public Result<Map<String, Object>> login(@Valid @RequestBody LoginDto request) {
        UserVo userVo = authService.login(request);

        String token = StpUtil.getTokenValue();
        Map<String, Object> data = new HashMap<>();
        data.put("user", userVo);
        data.put("token", token);
        data.put("tokenName", StpUtil.getTokenName());
        data.put("tokenTimeout", StpUtil.getTokenTimeout());
        
        return Result.success("登录成功", data);
    }
    
    /**
     * 用户登出
     */
    @PostMapping("/logout")
    @Operation(summary = "用户登出", description = "退出登录")
    public Result<String> logout() {
        long userId = StpUtil.getLoginIdAsLong();
        authService.logout(userId);
        return Result.success("登出成功");
    }

    /**
     * 发送短信验证码
     */
    @GetMapping("/send-sms-code")
    @Operation(summary = "发送短信验证码", description = "发送短信验证码")
    public Result<String> sendSmsVerificationCode(@Parameter(description = "手机号") @RequestParam String phone) {
        log.info("收到发送短信验证码请求, phone: {}", phone);

        // 调用服务层发送短信验证码
        authService.sendSmsVerificationCode(phone);

        return Result.success("验证码已发送，请查收短信");
    }
    
    /**
     * 刷新Token
     */
    @PostMapping("/refresh-token")
    @Operation(summary = "刷新Token", description = "刷新访问令牌")
    public Result<Map<String, Object>> refreshToken(
            @Parameter(description = "刷新令牌") @RequestParam String refreshToken) {
        log.info("收到刷新Token请求");
        
        // 调用服务层刷新Token
        UserVo userVo = authService.refreshToken(refreshToken);
        
        // 获取新Token
        String token = StpUtil.getTokenValue();
        
        // 构建响应
        Map<String, Object> data = new HashMap<>();
        data.put("user", userVo);
        data.put("token", token);
        data.put("tokenName", StpUtil.getTokenName());
        data.put("tokenTimeout", StpUtil.getTokenTimeout());
        
        return Result.success("Token刷新成功", data);
    }
    
    /**
     * 获取当前登录用户信息
     */
    @GetMapping("/me")
    @Operation(summary = "获取当前用户", description = "获取当前登录用户信息")
    public Result<UserVo> getCurrentUser() {
        // 获取当前登录用户ID
        long userId = StpUtil.getLoginIdAsLong();
        log.info("获取当前用户信息, userId: {}", userId);
        
        // TODO: 调用 UserService 获取用户详细信息
        // UserResponse user = userService.getUserById(userId);
        
        return Result.success("获取成功", null);
    }
    
    /**
     * 重置密码
     */
    @PostMapping("/reset-password")
    @Operation(summary = "重置密码", description = "通过验证码重置密码")
    public Result<String> resetPassword(
            @Parameter(description = "邮箱地址") @RequestParam String email,
            @Parameter(description = "验证码") @RequestParam String code,
            @Parameter(description = "新密码") @RequestParam String newPassword) {
        log.info("收到重置密码请求, email: {}", email);
        
        // 调用服务层重置密码
        authService.resetPassword(email, code, newPassword);
        
        return Result.success("密码重置成功，请重新登录");
    }
    
    /**
     * 验证Token是否有效
     */
    @GetMapping("/validate")
    @Operation(summary = "验证Token", description = "验证Token是否有效")
    public Result<Map<String, Object>> validateToken() {
        // 获取当前登录状态
        boolean isLogin = StpUtil.isLogin();
        
        Map<String, Object> data = new HashMap<>();
        data.put("isValid", isLogin);
        
        if (isLogin) {
            data.put("userId", StpUtil.getLoginIdAsLong());
            data.put("tokenTimeout", StpUtil.getTokenTimeout());
        }
        
        return Result.success(data);
    }
}

