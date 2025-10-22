package com.goabroad.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 学历枚举
 * 
 * @author GoAbroad Team
 * @version 1.0
 * @since 2024-10-19
 */
@Getter
@AllArgsConstructor
public enum EducationLevel {
    
    /**
     * 高中
     */
    HIGH_SCHOOL("high_school", "高中"),
    
    /**
     * 专科
     */
    ASSOCIATE("associate", "专科"),
    
    /**
     * 本科
     */
    BACHELOR("bachelor", "本科"),
    
    /**
     * 硕士
     */
    MASTER("master", "硕士"),
    
    /**
     * 博士
     */
    PHD("phd", "博士"),
    
    /**
     * 其他
     */
    OTHER("other", "其他");
    
    /**
     * 数据库值
     */
    private final String value;
    
    /**
     * 描述
     */
    private final String description;
}

