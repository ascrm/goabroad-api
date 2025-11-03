package com.goabroad.service.file.tools;

import com.goabroad.model.file.enums.FileType;
import com.goabroad.model.file.vo.FileUploadVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件上传辅助工具
 * 负责文件上传过程中的图片处理逻辑
 * 
 * @author GoAbroad Team
 * @version 1.0
 * @since 2024-11-03
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class FileUploadHelper {
    
    private final ImageProcessor imageProcessor;
    private final MinioOperationHelper minioHelper;
    private final FileValidator fileValidator;
    
    /**
     * 处理图片元数据（尺寸、缩略图等）
     * 
     * @param file 图片文件
     * @param fileType 文件类型
     * @param objectName 对象名称
     * @param vo 文件上传响应对象
     */
    public void processImageMetadata(MultipartFile file, FileType fileType, String objectName, FileUploadVo vo) {
        // 检查是否为图片
        if (!fileValidator.isImage(file.getContentType())) {
            return;
        }
        
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
     * 
     * @param file 原始图片文件
     * @param originalObjectName 原始对象名称
     * @return 缩略图URL，如果生成失败返回 null
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

