package com.goabroad.web.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.Components;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Springdoc OpenAPI 文档配置
 * 
 * 配置内容：
 * 1. API 基本信息
 * 2. 认证配置
 * 
 * 访问地址：http://localhost:8080/swagger-ui/index.html
 * 
 * @author GoAbroad Team
 * @version 1.0
 * @since 2024-10-19
 */
@Configuration
public class SwaggerConfig {
    
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(apiInfo())
                .components(components())
                .addSecurityItem(securityRequirement());
    }
    
    /**
     * API 基本信息
     */
    private Info apiInfo() {
        return new Info()
                .title("GoAbroad 留学规划系统 API")
                .description("留学规划系统后端接口文档")
                .version("1.0.0")
                .contact(new Contact()
                        .name("GoAbroad Team")
                        .email("goabroad@example.com"))
                .license(new License()
                        .name("Apache 2.0")
                        .url("https://www.apache.org/licenses/LICENSE-2.0"));
    }
    
    /**
     * 安全认证配置
     */
    private Components components() {
        return new Components()
                .addSecuritySchemes("Bearer Token", new SecurityScheme()
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("bearer")
                        .bearerFormat("JWT")
                        .description("请在下方输入JWT Token"));
    }
    
    /**
     * 安全要求配置
     */
    private SecurityRequirement securityRequirement() {
        return new SecurityRequirement().addList("Bearer Token");
    }
}

