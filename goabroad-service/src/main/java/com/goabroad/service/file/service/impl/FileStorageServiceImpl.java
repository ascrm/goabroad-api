package com.goabroad.service.file.service.impl;

import cn.hutool.core.util.IdUtil;
import com.goabroad.model.enums.FileType;
import com.goabroad.model.vo.FileUploadVo;
import com.goabroad.service.file.service.FileStorageService;
import com.goabroad.service.file.tools.FilePathBuilder;
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
        if (fileValidator.isImage(file.getContentType())) {
            processImageMetadata(file, fileType, objectName, vo);
        }
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
    
    /**
     * 处理图片元数据（尺寸、缩略图等）
     */
    private void processImageMetadata(MultipartFile file, FileType fileType, String objectName, FileUploadVo vo) {
        // 获取图片尺寸
        ImageProcessor.ImageDimension dimension = imageProcessor.getImageDimension(file);
        if (dimension != null) {
            vo.setWidth(dimension.width());
            vo.setHeight(dimension.height());
        }
        
        // 生成缩略图（仅对头像和帖子图片）
        if (fileType == FileType.AVATAR || fileType == FileType.POST_IMAGE) {
            String thumbnailUrl = uploadThumbnail(file, objectName);
            vo.setThumbnailUrl(thumbnailUrl);
        }
    }
    
    /**
     * 上传缩略图
     */
    private String uploadThumbnail(MultipartFile file, String originalObjectName) {
        ImageProcessor.ThumbnailData thumbnail = imageProcessor.generateThumbnail(file);
        if (thumbnail == null) {
            return null;
        }
        
        String thumbnailObjectName = imageProcessor.buildThumbnailObjectName(originalObjectName);
        
        return minioHelper.uploadFile(
                thumbnailObjectName,
                imageProcessor.toInputStream(thumbnail.data()),
                thumbnail.data().length,
                thumbnail.contentType()
        );
    }
}

