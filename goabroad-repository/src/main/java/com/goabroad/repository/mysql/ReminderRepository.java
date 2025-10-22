package com.goabroad.repository.mysql;

import com.goabroad.model.entity.Reminder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 提醒Repository接口
 * 
 * @author GoAbroad Team
 * @version 1.0
 * @since 2024-10-19
 */
@Repository
public interface ReminderRepository extends JpaRepository<Reminder, Long> {
    
    /**
     * 根据用户ID查询提醒列表
     * 
     * @param userId 用户ID
     * @param pageable 分页参数
     * @return 提醒列表
     */
    Page<Reminder> findByUserId(Long userId, Pageable pageable);
    
    /**
     * 根据用户ID和发送状态查询提醒
     * 
     * @param userId 用户ID
     * @param isSent 是否已发送
     * @param pageable 分页参数
     * @return 提醒列表
     */
    Page<Reminder> findByUserIdAndIsSent(Long userId, Boolean isSent, Pageable pageable);
    
    /**
     * 根据规划ID查询提醒列表
     * 
     * @param planId 规划ID
     * @return 提醒列表
     */
    List<Reminder> findByPlanId(Long planId);
    
    /**
     * 根据任务ID查询提醒
     * 
     * @param taskId 任务ID
     * @return 提醒列表
     */
    List<Reminder> findByTaskId(Long taskId);
    
    /**
     * 查询待发送的提醒（提醒时间已到且未发送）
     * 
     * @param now 当前时间
     * @return 提醒列表
     */
    @Query("SELECT r FROM Reminder r WHERE r.isSent = false AND r.remindTime <= :now ORDER BY r.remindTime ASC")
    List<Reminder> findPendingReminders(@Param("now") LocalDateTime now);
    
    /**
     * 统计用户的未发送提醒数量
     * 
     * @param userId 用户ID
     * @return 未发送提醒数量
     */
    long countByUserIdAndIsSent(Long userId, Boolean isSent);
    
    /**
     * 删除规划的所有提醒
     * 
     * @param planId 规划ID
     */
    void deleteByPlanId(Long planId);
    
    /**
     * 删除任务的所有提醒
     * 
     * @param taskId 任务ID
     */
    void deleteByTaskId(Long taskId);
}

