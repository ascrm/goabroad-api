package com.goabroad.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Web MVC 配置
 * 配置内容：
 * 1. CORS 跨域配置
 * 2. 拦截器配置
 * 3. RestTemplate HTTP 客户端
 * 4. 其他 Web MVC 相关配置
 * 
 * @author GoAbroad Team
 * @version 1.0
 * @since 2024-10-19
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    /**
     * 配置跨域
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                // 允许的源
                .allowedOriginPatterns("*")
                // 允许的请求方法
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                // 允许的请求头
                .allowedHeaders("*")
                // 允许携带凭证
                .allowCredentials(true)
                // 预检请求的有效期（秒）
                .maxAge(3600);
    }
    
    /**
     * 配置 RestTemplate Bean
     * 用于发送 HTTP 请求（如短信API调用）
     * 
     * @return RestTemplate 实例
     */
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}

