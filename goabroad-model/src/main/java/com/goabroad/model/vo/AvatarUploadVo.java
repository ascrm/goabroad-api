package com.goabroad.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 头像上传响应VO
 * 
 * @author GoAbroad Team
 * @version 1.0
 * @since 2024-10-25
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "头像上传响应")
public class AvatarUploadVo {
    
    @Schema(description = "头像URL", example = "https://cdn.goabroad.com/avatars/uuid-123.jpg")
    private String avatar;
    
    @Schema(description = "缩略图URL", example = "https://cdn.goabroad.com/avatars/uuid-123_thumb.jpg")
    private String thumbnail;
}

