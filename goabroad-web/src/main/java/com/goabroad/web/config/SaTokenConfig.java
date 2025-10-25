package com.goabroad.web.config;

import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.stp.StpUtil;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Sa-Token 配置类
 * 
 * @author GoAbroad Team
 * @version 1.0
 * @since 2024-10-19
 */
@Configuration
public class SaTokenConfig implements WebMvcConfigurer {
    
    /**
     * 注册 Sa-Token 拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册 Sa-Token 拦截器，校验规则为 StpUtil.checkLogin() 登录校验
        registry.addInterceptor(new SaInterceptor(handle -> StpUtil.checkLogin()))
                .addPathPatterns("/**") // 拦截所有路径
                .excludePathPatterns(
                        "/api/auth/test",

                        // 排除认证接口
                        "/api/auth/login",
                        "/api/auth/register",
                        "/api/auth/send-code",
                        "/api/auth/send-sms-code",
                        "/api/auth/reset-password",
                        
                        // 排除公开的国家信息接口
                        "/api/countries/**",
                        
                        // 排除公开的用户接口（GET请求）
                        "/api/users/*/",
                        "/api/users/*/following",
                        "/api/users/*/followers",
                        
                        // 排除 Swagger 文档
                        "/swagger-ui/**",
                        "/v3/api-docs/**",
                        "/swagger-resources/**",
                        "/webjars/**",
                        
                        // 排除健康检查
                        "/actuator/**",
                        
                        // 排除静态资源
                        "/static/**",
                        "/public/**",
                        "/upload/**",
                        
                        // 排除错误页面
                        "/error"
                );
    }

}

