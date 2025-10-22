package com.goabroad.service.notification;

import com.goabroad.model.dto.response.NotificationResponse;
import com.goabroad.model.enums.NotificationType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * 通知服务接口
 * 
 * @author GoAbroad Team
 * @version 1.0
 * @since 2024-10-19
 */
public interface NotificationService {
    
    /**
     * 发送通知
     * 
     * @param userId 用户ID
     * @param type 通知类型
     * @param title 标题
     * @param content 内容
     * @param relatedId 关联ID（如帖子ID、评论ID等）
     * @return 通知信息
     */
    NotificationResponse sendNotification(Long userId, NotificationType type, String title, String content, Long relatedId);
    
    /**
     * 获取用户的通知列表
     * 
     * @param userId 用户ID
     * @param pageable 分页参数
     * @return 通知列表
     */
    Page<NotificationResponse> getUserNotifications(Long userId, Pageable pageable);
    
    /**
     * 获取未读通知数量
     * 
     * @param userId 用户ID
     * @return 未读数量
     */
    long getUnreadCount(Long userId);
    
    /**
     * 标记通知为已读
     * 
     * @param notificationId 通知ID
     * @param userId 用户ID
     */
    void markAsRead(Long notificationId, Long userId);
    
    /**
     * 标记所有通知为已读
     * 
     * @param userId 用户ID
     */
    void markAllAsRead(Long userId);
    
    /**
     * 删除通知
     * 
     * @param notificationId 通知ID
     * @param userId 用户ID
     */
    void deleteNotification(Long notificationId, Long userId);
    
    /**
     * 删除所有通知
     * 
     * @param userId 用户ID
     */
    void deleteAllNotifications(Long userId);
}
