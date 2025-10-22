package com.goabroad.model.dto.response;

import com.goabroad.model.enums.AdminRole;
import com.goabroad.model.enums.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * 管理员用户响应DTO
 * 
 * @author GoAbroad Team
 * @version 1.0
 * @since 2024-10-19
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminUserResponse implements Serializable {
    
    @Serial
    private static final long serialVersionUID = 1L;
    
    /**
     * 管理员ID
     */
    private Long id;
    
    /**
     * 用户名
     */
    private String username;
    
    /**
     * 邮箱
     */
    private String email;
    
    /**
     * 真实姓名
     */
    private String realName;
    
    /**
     * 管理员角色
     */
    private AdminRole role;
    
    /**
     * 权限列表
     */
    private Set<String> permissions;
    
    /**
     * 状态
     */
    private UserStatus status;
    
    /**
     * 部门
     */
    private String department;
    
    /**
     * 最后登录时间
     */
    private LocalDateTime lastLoginAt;
    
    /**
     * 最后登录IP
     */
    private String lastLoginIp;
    
    /**
     * 创建时间
     */
    private LocalDateTime createdAt;
    
    /**
     * 更新时间
     */
    private LocalDateTime updatedAt;
}

