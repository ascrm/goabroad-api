package com.goabroad.model.enums;

import lombok.Getter;

/**
 * 文件类型枚举
 * 
 * @author GoAbroad Team
 * @version 1.0
 * @since 2024-10-25
 */
@Getter
public enum FileType {
    
    /**
     * 用户头像
     */
    AVATAR("avatar", "avatars/", new String[]{"image/jpeg", "image/png", "image/gif", "image/webp"}),
    
    /**
     * 帖子图片
     */
    POST_IMAGE("post_image", "posts/", new String[]{"image/jpeg", "image/png", "image/gif", "image/webp"}),
    
    /**
     * 材料文件
     */
    MATERIAL("material", "materials/", new String[]{"application/pdf", "image/jpeg", "image/png", "application/msword", "application/vnd.openxmlformats-officedocument.wordprocessingml.document"}),
    
    /**
     * 附件
     */
    ATTACHMENT("attachment", "attachments/", new String[]{"*/*"});
    
    /**
     * 类型代码
     */
    private final String code;
    
    /**
     * 存储路径前缀
     */
    private final String pathPrefix;
    
    /**
     * 允许的MIME类型
     */
    private final String[] allowedMimeTypes;
    
    FileType(String code, String pathPrefix, String[] allowedMimeTypes) {
        this.code = code;
        this.pathPrefix = pathPrefix;
        this.allowedMimeTypes = allowedMimeTypes;
    }
    
    /**
     * 根据代码获取枚举
     */
    public static FileType fromCode(String code) {
        for (FileType type : values()) {
            if (type.code.equals(code)) {
                return type;
            }
        }
        throw new IllegalArgumentException("未知的文件类型: " + code);
    }
    
    /**
     * 检查MIME类型是否被允许
     */
    public boolean isAllowedMimeType(String mimeType) {
        if (allowedMimeTypes[0].equals("*/*")) {
            return true;
        }
        for (String allowed : allowedMimeTypes) {
            if (allowed.equals(mimeType)) {
                return true;
            }
        }
        return false;
    }
}

