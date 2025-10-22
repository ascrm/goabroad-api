package com.goabroad.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 性别枚举
 * 
 * @author GoAbroad Team
 * @version 1.0
 * @since 2024-10-19
 */
@Getter
@AllArgsConstructor
public enum Gender {
    
    /**
     * 男性
     */
    MALE("male", "男"),
    
    /**
     * 女性
     */
    FEMALE("female", "女"),
    
    /**
     * 其他
     */
    OTHER("other", "其他"),
    
    /**
     * 不愿透露
     */
    PREFER_NOT_TO_SAY("prefer_not_to_say", "不愿透露");
    
    /**
     * 数据库值
     */
    private final String value;
    
    /**
     * 描述
     */
    private final String description;
}

