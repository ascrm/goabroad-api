package com.goabroad.service.file.service.impl;

import cn.hutool.core.util.IdUtil;
import com.goabroad.model.file.enums.FileType;
import com.goabroad.model.file.vo.FileUploadVo;
import com.goabroad.service.file.service.FileStorageService;
import com.goabroad.service.file.tools.FilePathBuilder;
import com.goabroad.service.file.tools.FileUploadHelper;
import com.goabroad.service.file.tools.FileValidator;
import com.goabroad.service.file.tools.ImageProcessor;
import com.goabroad.service.file.tools.MinioOperationHelper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

/**
 * MinIO 文件存储服务实现
 * 
 * @author GoAbroad Team
 * @version 1.0
 * @since 2024-10-25
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class FileStorageServiceImpl implements FileStorageService {
    
    private final MinioOperationHelper minioHelper;
    private final FileValidator fileValidator;
    private final ImageProcessor imageProcessor;
    private final FilePathBuilder pathBuilder;
    private final FileUploadHelper uploadHelper;

    @SneakyThrows
    @Override
    public FileUploadVo uploadFile(MultipartFile file, FileType fileType, Long userId) {
        // 1. 验证文件
        fileValidator.validate(file, fileType);

        // 2. 生成文件路径
        String originalFilename = file.getOriginalFilename();
        String uniqueFilename = pathBuilder.generateUniqueFilename(originalFilename);
        String objectName = pathBuilder.buildObjectPath(fileType, uniqueFilename);

        // 3. 上传文件到 MinIO
        String fileUrl = minioHelper.uploadFile(objectName,
                file.getInputStream(),
                file.getSize(),
                file.getContentType()
        );

        // 4. 构建基础响应
        FileUploadVo vo = FileUploadVo.builder()
                .id(IdUtil.simpleUUID())
                .url(fileUrl)
                .filename(uniqueFilename)
                .originalName(originalFilename)
                .size(file.getSize())
                .mimeType(file.getContentType())
                .uploadedAt(LocalDateTime.now())
                .build();

        // 5. 处理图片特殊逻辑
        uploadHelper.processImageMetadata(file, fileType, objectName, vo);
        
        return vo;
    }
    
    @Override
    public void deleteFile(String fileUrl) {
        // 1. 提取对象名称
        String objectName = minioHelper.extractObjectName(fileUrl);
        
        // 2. 删除主文件
        minioHelper.deleteFile(objectName);
        
        // 3. 尝试删除缩略图（如果存在）
        String thumbnailObjectName = imageProcessor.buildThumbnailObjectName(objectName);
        minioHelper.deleteFileSilently(thumbnailObjectName);
    }
    
    @Override
    public String generateUniqueFilename(String originalFilename) {
        return pathBuilder.generateUniqueFilename(originalFilename);
    }
}

