package com.goabroad.service.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

/**
 * RestTemplate 配置
 *
 * @author GoAbroad Team
 * @version 1.0
 * @since 2024-10-22
 */
@Configuration
public class RestTemplateConfig {

    /**
     * 创建 RestTemplate Bean
     * 
     * @param builder RestTemplate构建器
     * @return RestTemplate实例
     */
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder
                .setConnectTimeout(Duration.ofSeconds(10))  // 连接超时：10秒
                .setReadTimeout(Duration.ofSeconds(10))     // 读取超时：10秒
                .requestFactory(SimpleClientHttpRequestFactory.class)
                .build();
    }
}

