package com.goabroad.service.notification;

import com.goabroad.model.dto.ReminderDTO;
import com.goabroad.model.entity.Reminder;

import java.util.List;

/**
 * 提醒服务接口
 * 
 * @author GoAbroad Team
 * @version 1.0
 * @since 2024-10-19
 */
public interface ReminderService {
    
    /**
     * 创建提醒
     * 
     * @param userId 用户ID
     * @param dto 提醒DTO
     * @return 提醒信息
     */
    Reminder createReminder(Long userId, ReminderDTO dto);
    
    /**
     * 更新提醒
     * 
     * @param reminderId 提醒ID
     * @param userId 用户ID
     * @param dto 提醒DTO
     * @return 更新后的提醒信息
     */
    Reminder updateReminder(Long reminderId, Long userId, ReminderDTO dto);
    
    /**
     * 删除提醒
     * 
     * @param reminderId 提醒ID
     * @param userId 用户ID
     */
    void deleteReminder(Long reminderId, Long userId);
    
    /**
     * 获取用户的提醒列表
     * 
     * @param userId 用户ID
     * @return 提醒列表
     */
    List<Reminder> getUserReminders(Long userId);
    
    /**
     * 获取即将到期的提醒
     * 
     * @param userId 用户ID
     * @return 提醒列表
     */
    List<Reminder> getUpcomingReminders(Long userId);
    
    /**
     * 标记提醒为已发送
     * 
     * @param reminderId 提醒ID
     */
    void markAsSent(Long reminderId);
    
    /**
     * 定时检查并发送提醒（定时任务调用）
     */
    void checkAndSendReminders();
}
