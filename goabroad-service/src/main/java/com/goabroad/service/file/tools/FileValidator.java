package com.goabroad.service.file.tools;

import com.goabroad.common.exception.BusinessException;
import com.goabroad.common.pojo.ResultCode;
import com.goabroad.model.enums.FileType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件验证工具
 * 负责文件的各项验证逻辑
 * 
 * @author GoAbroad Team
 * @version 1.0
 * @since 2024-10-25
 */
@Slf4j
@Component
public class FileValidator {
    
    @Value("${file.upload.max-size:10485760}") // 默认 10MB
    private Long maxFileSize;
    
    /**
     * 验证文件
     * 
     * @param file 待验证的文件
     * @param fileType 文件类型
     */
    public void validate(MultipartFile file, FileType fileType) {
        validateNotEmpty(file);
        validateSize(file);
        validateMimeType(file, fileType);
    }
    
    /**
     * 验证文件不为空
     */
    private void validateNotEmpty(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new BusinessException(ResultCode.BAD_REQUEST, "文件不能为空");
        }
    }
    
    /**
     * 验证文件大小
     */
    private void validateSize(MultipartFile file) {
        if (file.getSize() > maxFileSize) {
            long maxSizeMB = maxFileSize / 1024 / 1024;
            throw new BusinessException(ResultCode.BAD_REQUEST, 
                    String.format("文件大小超过限制（最大 %d MB）", maxSizeMB));
        }
    }
    
    /**
     * 验证文件MIME类型
     */
    private void validateMimeType(MultipartFile file, FileType fileType) {
        String contentType = file.getContentType();
        if (contentType == null || !fileType.isAllowedMimeType(contentType)) {
            throw new BusinessException(ResultCode.BAD_REQUEST, 
                    "不支持的文件类型，允许的类型: " + String.join(", ", fileType.getAllowedMimeTypes()));
        }
    }
    
    /**
     * 判断是否为图片
     */
    public boolean isImage(String contentType) {
        return contentType != null && contentType.startsWith("image/");
    }
}

