package com.goabroad.service.admin;

import com.goabroad.model.dto.response.AuditLogResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

/**
 * 审计日志服务接口
 * 
 * @author GoAbroad Team
 * @version 1.0
 * @since 2024-10-19
 */
public interface AuditLogService {
    
    /**
     * 记录审计日志
     * 
     * @param userId 用户ID
     * @param adminId 管理员ID
     * @param action 操作动作
     * @param resourceType 资源类型
     * @param resourceId 资源ID
     * @param description 描述
     * @param status 状态
     */
    void log(Long userId, Long adminId, String action, String resourceType, Long resourceId, 
             String description, String status);
    
    /**
     * 根据用户ID查询日志
     * 
     * @param userId 用户ID
     * @param pageable 分页参数
     * @return 日志列表
     */
    Page<AuditLogResponse> getLogsByUserId(Long userId, Pageable pageable);
    
    /**
     * 根据管理员ID查询日志
     * 
     * @param adminId 管理员ID
     * @param pageable 分页参数
     * @return 日志列表
     */
    Page<AuditLogResponse> getLogsByAdminId(Long adminId, Pageable pageable);
    
    /**
     * 根据操作动作查询日志
     * 
     * @param action 操作动作
     * @param pageable 分页参数
     * @return 日志列表
     */
    Page<AuditLogResponse> getLogsByAction(String action, Pageable pageable);
    
    /**
     * 根据资源查询日志
     * 
     * @param resourceType 资源类型
     * @param resourceId 资源ID
     * @param pageable 分页参数
     * @return 日志列表
     */
    Page<AuditLogResponse> getLogsByResource(String resourceType, Long resourceId, Pageable pageable);
    
    /**
     * 根据时间范围查询日志
     * 
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @param pageable 分页参数
     * @return 日志列表
     */
    Page<AuditLogResponse> getLogsByTimeRange(LocalDateTime startTime, LocalDateTime endTime, Pageable pageable);
}

