package com.goabroad.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 管理员角色枚举
 * 
 * @author GoAbroad Team
 * @version 1.0
 * @since 2024-10-19
 */
@Getter
@AllArgsConstructor
public enum AdminRole {
    
    /**
     * 超级管理员
     */
    SUPER_ADMIN("super_admin", "超级管理员"),
    
    /**
     * 管理员
     */
    ADMIN("admin", "管理员"),
    
    /**
     * 编辑
     */
    EDITOR("editor", "编辑"),
    
    /**
     * 查看者
     */
    VIEWER("viewer", "查看者");
    
    /**
     * 数据库值
     */
    private final String value;
    
    /**
     * 描述
     */
    private final String description;
}

