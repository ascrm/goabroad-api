package com.goabroad.service.file.service;

import org.springframework.web.multipart.MultipartFile;
import com.goabroad.model.vo.FileUploadVo;
import com.goabroad.model.enums.FileType;

/**
 * 文件存储服务接口
 * 
 * @author GoAbroad Team
 * @version 1.0
 * @since 2024-10-25
 */
public interface FileStorageService {
    
    /**
     * 上传文件
     * 
     * @param file 文件
     * @param fileType 文件类型
     * @param userId 用户ID
     * @return 文件信息
     */
    FileUploadVo uploadFile(MultipartFile file, FileType fileType, Long userId);
    
    /**
     * 删除文件
     * 
     * @param fileUrl 文件URL
     */
    void deleteFile(String fileUrl);
    
    /**
     * 生成唯一文件名
     * 
     * @param originalFilename 原始文件名
     * @return 唯一文件名
     */
    String generateUniqueFilename(String originalFilename);
}

