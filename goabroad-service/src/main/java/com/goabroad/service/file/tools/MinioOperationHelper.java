package com.goabroad.service.file.tools;

import com.goabroad.common.exception.BusinessException;
import com.goabroad.common.pojo.ResultCode;
import io.minio.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.InputStream;

/**
 * MinIO 操作助手
 * 提供 MinIO 的基础操作封装
 * 
 * @author GoAbroad Team
 * @version 1.0
 * @since 2024-10-25
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class MinioOperationHelper {
    
    private final MinioClient minioClient;
    
    @Value("${minio.bucket-name}")
    private String bucketName;
    
    @Value("${minio.base-url}")
    private String baseUrl;
    
    /**
     * 上传文件到 MinIO
     * 
     * @param objectName 对象名称（路径）
     * @param inputStream 输入流
     * @param size 文件大小
     * @param contentType 内容类型
     * @return 文件访问URL
     */
    public String uploadFile(String objectName, InputStream inputStream, long size, String contentType) {
        try {
            // 确保存储桶存在
            ensureBucketExists();
            
            // 上传文件
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(objectName)
                            .stream(inputStream, size, -1)
                            .contentType(contentType)
                            .build()
            );
            
            // 返回文件URL
            return buildFileUrl(objectName);
            
        } catch (Exception e) {
            log.error("MinIO文件上传失败 - objectName: {}", objectName, e);
            throw new BusinessException(ResultCode.FILE_UPLOAD_FAILED, "文件上传失败: " + e.getMessage());
        }
    }
    
    /**
     * 删除文件
     * 
     * @param objectName 对象名称
     */
    public void deleteFile(String objectName) {
        try {
            minioClient.removeObject(
                    RemoveObjectArgs.builder()
                            .bucket(bucketName)
                            .object(objectName)
                            .build()
            );
            log.info("MinIO文件删除成功 - objectName: {}", objectName);
            
        } catch (Exception e) {
            log.error("MinIO文件删除失败 - objectName: {}", objectName, e);
            throw new BusinessException(ResultCode.FILE_DELETE_FAILED, "文件删除失败");
        }
    }
    
    /**
     * 静默删除文件（不抛出异常）
     * 
     * @param objectName 对象名称
     * @return 是否删除成功
     */
    public boolean deleteFileSilently(String objectName) {
        try {
            deleteFile(objectName);
            return true;
        } catch (Exception e) {
            log.debug("MinIO文件删除失败（忽略）- objectName: {}", objectName);
            return false;
        }
    }
    
    /**
     * 从URL提取对象名称
     * 
     * @param fileUrl 文件URL
     * @return 对象名称
     */
    public String extractObjectName(String fileUrl) {
        String prefix = baseUrl + "/" + bucketName + "/";
        if (fileUrl != null && fileUrl.startsWith(prefix)) {
            return fileUrl.substring(prefix.length());
        }
        throw new BusinessException(ResultCode.BAD_REQUEST, "无效的文件URL");
    }
    
    /**
     * 构建文件URL
     * 
     * @param objectName 对象名称
     * @return 文件URL
     */
    public String buildFileUrl(String objectName) {
        return baseUrl + "/" + bucketName + "/" + objectName;
    }
    
    /**
     * 确保存储桶存在（如果不存在则创建）
     */
    private void ensureBucketExists() {
        try {
            boolean exists = minioClient.bucketExists(
                    BucketExistsArgs.builder()
                            .bucket(bucketName)
                            .build()
            );
            
            if (!exists) {
                // 创建存储桶
                minioClient.makeBucket(
                        MakeBucketArgs.builder()
                                .bucket(bucketName)
                                .build()
                );
                
                // 设置存储桶为公开读
                setPublicReadPolicy();
                
                log.info("MinIO存储桶创建成功: {}", bucketName);
            }
            
        } catch (Exception e) {
            log.error("MinIO存储桶检查或创建失败", e);
            throw new BusinessException(ResultCode.FILE_UPLOAD_FAILED, "MinIO存储桶初始化失败");
        }
    }
    
    /**
     * 设置存储桶为公开读
     */
    private void setPublicReadPolicy() {
        try {
            String policy = """
                    {
                        "Version": "2012-10-17",
                        "Statement": [
                            {
                                "Effect": "Allow",
                                "Principal": {"AWS": "*"},
                                "Action": ["s3:GetObject"],
                                "Resource": ["arn:aws:s3:::%s/*"]
                            }
                        ]
                    }
                    """.formatted(bucketName);
            
            minioClient.setBucketPolicy(
                    SetBucketPolicyArgs.builder()
                            .bucket(bucketName)
                            .config(policy)
                            .build()
            );
            
        } catch (Exception e) {
            log.warn("设置MinIO存储桶公开读策略失败", e);
        }
    }
}

