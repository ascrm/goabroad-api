package com.goabroad.web.config;

import io.minio.MinioClient;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * MinIO 配置类
 * 
 * @author GoAbroad Team
 * @version 1.0
 * @since 2024-10-25
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "minio")
public class MinioConfig {
    
    /**
     * MinIO 服务地址
     */
    private String endpoint;
    
    /**
     * MinIO 访问密钥
     */
    private String accessKey;
    
    /**
     * MinIO 密钥
     */
    private String secretKey;
    
    /**
     * 默认存储桶名称
     */
    private String bucketName;
    
    /**
     * 文件访问URL前缀
     */
    private String baseUrl;
    
    /**
     * 是否使用 HTTPS
     */
    private boolean secure = false;
    
    /**
     * 创建 MinioClient Bean
     */
    @Bean
    public MinioClient minioClient() {
        return MinioClient.builder()
                .endpoint(endpoint)
                .credentials(accessKey, secretKey)
                .build();
    }
}

