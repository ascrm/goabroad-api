package com.goabroad.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 审计日志响应DTO
 * 
 * @author GoAbroad Team
 * @version 1.0
 * @since 2024-10-19
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuditLogResponse implements Serializable {
    
    @Serial
    private static final long serialVersionUID = 1L;
    
    /**
     * 日志ID
     */
    private Long id;
    
    /**
     * 操作类型（CREATE, UPDATE, DELETE, LOGIN, LOGOUT等）
     */
    private String action;
    
    /**
     * 实体类型（USER, POST, PLAN等）
     */
    private String entityType;
    
    /**
     * 实体ID
     */
    private Long entityId;
    
    /**
     * 操作用户ID
     */
    private Long userId;
    
    /**
     * 操作用户名
     */
    private String username;
    
    /**
     * IP地址
     */
    private String ipAddress;
    
    /**
     * User Agent
     */
    private String userAgent;
    
    /**
     * 详细信息
     */
    private String details;
    
    /**
     * 操作结果（SUCCESS, FAILURE）
     */
    private String result;
    
    /**
     * 错误信息（如果操作失败）
     */
    private String errorMessage;
    
    /**
     * 创建时间
     */
    private LocalDateTime createdAt;
}

