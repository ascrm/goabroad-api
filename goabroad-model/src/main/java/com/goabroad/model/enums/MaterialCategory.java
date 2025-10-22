package com.goabroad.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 材料类别枚举
 * 
 * @author GoAbroad Team
 * @version 1.0
 * @since 2024-10-19
 */
@Getter
@AllArgsConstructor
public enum MaterialCategory {
    
    /**
     * 必需材料
     */
    REQUIRED("required", "必需材料"),
    
    /**
     * 支持性材料
     */
    SUPPORTING("supporting", "支持性材料"),
    
    /**
     * 可选材料
     */
    OPTIONAL("optional", "可选材料");
    
    /**
     * 数据库值
     */
    private final String value;
    
    /**
     * 类别描述
     */
    private final String description;
}

