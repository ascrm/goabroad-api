package com.goabroad.repository.mysql;

import com.goabroad.model.entity.Notification;
import com.goabroad.model.enums.NotificationType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * 通知Repository接口
 * 
 * @author GoAbroad Team
 * @version 1.0
 * @since 2024-10-19
 */
@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    
    /**
     * 根据用户ID查询通知列表
     * 
     * @param userId 用户ID
     * @param pageable 分页参数
     * @return 通知列表
     */
    Page<Notification> findByUserId(Long userId, Pageable pageable);
    
    /**
     * 根据用户ID和已读状态查询通知
     * 
     * @param userId 用户ID
     * @param isRead 是否已读
     * @param pageable 分页参数
     * @return 通知列表
     */
    Page<Notification> findByUserIdAndIsRead(Long userId, Boolean isRead, Pageable pageable);
    
    /**
     * 根据用户ID和通知类型查询通知
     * 
     * @param userId 用户ID
     * @param type 通知类型
     * @param pageable 分页参数
     * @return 通知列表
     */
    Page<Notification> findByUserIdAndType(Long userId, NotificationType type, Pageable pageable);
    
    /**
     * 统计用户的未读通知数量
     * 
     * @param userId 用户ID
     * @return 未读通知数量
     */
    long countByUserIdAndIsRead(Long userId, Boolean isRead);
    
    /**
     * 标记所有通知为已读
     * 
     * @param userId 用户ID
     */
    @Modifying
    @Query("UPDATE Notification n SET n.isRead = true WHERE n.userId = :userId AND n.isRead = false")
    void markAllAsRead(@Param("userId") Long userId);
    
    /**
     * 删除用户的所有通知
     * 
     * @param userId 用户ID
     */
    void deleteByUserId(Long userId);
}

