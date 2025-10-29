package com.goabroad.web.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.goabroad.common.pojo.Result;
import com.goabroad.model.auth.dto.LoginDto;
import com.goabroad.model.auth.dto.RegisterDto;
import com.goabroad.model.user.vo.UserVo;
import com.goabroad.service.auth.service.AuthService;
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
        authService.sendSmsVerificationCode(phone);
        return Result.success("验证码已发送，请查收短信");
    }
}

