package com.goabroad.repository.mysql;

import com.goabroad.model.entity.AuditLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 操作审计日志Repository接口
 * 
 * @author GoAbroad Team
 * @version 1.0
 * @since 2024-10-19
 */
@Repository
public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {
    
    /**
     * 根据用户ID查询日志
     * 
     * @param userId 用户ID
     * @param pageable 分页参数
     * @return 日志列表
     */
    Page<AuditLog> findByUserId(Long userId, Pageable pageable);
    
    /**
     * 根据管理员ID查询日志
     * 
     * @param adminId 管理员ID
     * @param pageable 分页参数
     * @return 日志列表
     */
    Page<AuditLog> findByAdminId(Long adminId, Pageable pageable);
    
    /**
     * 根据操作动作查询日志
     * 
     * @param action 操作动作
     * @param pageable 分页参数
     * @return 日志列表
     */
    Page<AuditLog> findByAction(String action, Pageable pageable);
    
    /**
     * 根据资源类型和资源ID查询日志
     * 
     * @param resourceType 资源类型
     * @param resourceId 资源ID
     * @param pageable 分页参数
     * @return 日志列表
     */
    Page<AuditLog> findByResourceTypeAndResourceId(String resourceType, Long resourceId, Pageable pageable);
    
    /**
     * 根据状态查询日志
     * 
     * @param status 操作状态
     * @param pageable 分页参数
     * @return 日志列表
     */
    Page<AuditLog> findByStatus(String status, Pageable pageable);
    
    /**
     * 根据时间范围查询日志
     * 
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @param pageable 分页参数
     * @return 日志列表
     */
    @Query("SELECT al FROM AuditLog al WHERE al.createdAt BETWEEN :startTime AND :endTime ORDER BY al.createdAt DESC")
    Page<AuditLog> findByCreatedAtBetween(@Param("startTime") LocalDateTime startTime, 
                                           @Param("endTime") LocalDateTime endTime, 
                                           Pageable pageable);
    
    /**
     * 根据用户ID和操作动作查询日志
     * 
     * @param userId 用户ID
     * @param action 操作动作
     * @return 日志列表
     */
    List<AuditLog> findByUserIdAndAction(Long userId, String action);
    
    /**
     * 统计用户的操作次数
     * 
     * @param userId 用户ID
     * @return 操作次数
     */
    long countByUserId(Long userId);
    
    /**
     * 统计管理员的操作次数
     * 
     * @param adminId 管理员ID
     * @return 操作次数
     */
    long countByAdminId(Long adminId);
    
    /**
     * 统计指定操作的次数
     * 
     * @param action 操作动作
     * @return 操作次数
     */
    long countByAction(String action);
}

