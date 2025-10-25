package com.goabroad.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 文件上传响应VO
 * 
 * @author GoAbroad Team
 * @version 1.0
 * @since 2024-10-25
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "文件上传响应")
public class FileUploadVo {
    
    @Schema(description = "文件ID", example = "file-123")
    private String id;
    
    @Schema(description = "文件URL", example = "https://cdn.goabroad.com/uploads/2024/10/25/file-123.jpg")
    private String url;
    
    @Schema(description = "缩略图URL（仅图片）", example = "https://cdn.goabroad.com/uploads/2024/10/25/file-123_thumb.jpg")
    private String thumbnailUrl;
    
    @Schema(description = "存储的文件名", example = "uuid-123.jpg")
    private String filename;
    
    @Schema(description = "原始文件名", example = "我的照片.jpg")
    private String originalName;
    
    @Schema(description = "文件大小（字节）", example = "2048000")
    private Long size;
    
    @Schema(description = "MIME类型", example = "image/jpeg")
    private String mimeType;
    
    @Schema(description = "图片宽度（仅图片）", example = "1920")
    private Integer width;
    
    @Schema(description = "图片高度（仅图片）", example = "1080")
    private Integer height;
    
    @Schema(description = "上传时间", example = "2024-10-25T18:00:00")
    private LocalDateTime uploadedAt;
}

