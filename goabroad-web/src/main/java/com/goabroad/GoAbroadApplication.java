package com.goabroad;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * GoAbroad 留学规划系统启动类
 * 
 * @author GoAbroad Team
 * @version 1.0
 * @since 2024-10-19
 */
@SpringBootApplication
@EnableJpaAuditing // 启用 JPA 审计
@EnableCaching // 启用缓存
@EnableScheduling // 启用定时任务
@EnableAsync // 启用异步任务
public class GoAbroadApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(GoAbroadApplication.class, args);
        System.out.println("""
            
            ========================================
              GoAbroad 留学规划系统启动成功！
              API 文档地址: http://localhost:8080/swagger-ui/index.html
            ========================================
            """);
    }
}

