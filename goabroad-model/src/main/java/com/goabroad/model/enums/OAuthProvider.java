package com.goabroad.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 第三方平台枚举
 * 
 * @author GoAbroad Team
 * @version 1.0
 * @since 2024-10-19
 */
@Getter
@AllArgsConstructor
public enum OAuthProvider {
    
    /**
     * 微信
     */
    WECHAT("wechat", "微信"),
    
    /**
     * QQ
     */
    QQ("qq", "QQ"),
    
    /**
     * Apple
     */
    APPLE("apple", "Apple"),
    
    /**
     * Google
     */
    GOOGLE("google", "Google"),
    
    /**
     * GitHub
     */
    GITHUB("github", "GitHub");
    
    /**
     * 数据库值
     */
    private final String value;
    
    /**
     * 描述
     */
    private final String description;
}

