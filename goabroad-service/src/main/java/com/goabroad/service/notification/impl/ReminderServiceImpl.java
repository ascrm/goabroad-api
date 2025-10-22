package com.goabroad.service.notification.impl;

import com.goabroad.model.dto.ReminderDTO;
import com.goabroad.model.entity.Reminder;
import com.goabroad.service.notification.ReminderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 提醒服务实现
 * 
 * @author GoAbroad Team
 * @version 1.0
 * @since 2024-10-19
 */
@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class ReminderServiceImpl implements ReminderService {
    
    // TODO: 注入依赖
    
    @Override
    public Reminder createReminder(Long userId, ReminderDTO request) {
        log.info("创建提醒, userId: {}, request: {}", userId, request);
        // TODO: 实现业务逻辑
        return null;
    }
    
    @Override
    public Reminder updateReminder(Long reminderId, Long userId, ReminderDTO request) {
        log.info("更新提醒, reminderId: {}, userId: {}, request: {}", reminderId, userId, request);
        // TODO: 实现业务逻辑
        return null;
    }
    
    @Override
    public void deleteReminder(Long reminderId, Long userId) {
        log.info("删除提醒, reminderId: {}, userId: {}", reminderId, userId);
        // TODO: 实现业务逻辑
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Reminder> getUserReminders(Long userId) {
        log.info("获取用户的提醒列表, userId: {}", userId);
        // TODO: 实现业务逻辑
        return null;
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Reminder> getUpcomingReminders(Long userId) {
        log.info("获取即将到期的提醒, userId: {}", userId);
        // TODO: 实现业务逻辑
        return null;
    }
    
    @Override
    public void markAsSent(Long reminderId) {
        log.info("标记提醒为已发送, reminderId: {}", reminderId);
        // TODO: 实现业务逻辑
    }
    
    @Override
    public void checkAndSendReminders() {
        log.info("定时检查并发送提醒");
        // TODO: 实现业务逻辑
        // 1. 查询需要发送的提醒
        // 2. 发送通知
        // 3. 标记为已发送
    }
}
