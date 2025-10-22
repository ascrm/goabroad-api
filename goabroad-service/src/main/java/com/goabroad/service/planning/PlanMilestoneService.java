package com.goabroad.service.planning;

import com.goabroad.model.dto.PlanMilestoneDTO;
import com.goabroad.model.entity.PlanMilestone;
import com.goabroad.model.enums.MilestoneStatus;

import java.time.LocalDate;
import java.util.List;

/**
 * 规划里程碑服务接口
 * 
 * @author GoAbroad Team
 * @version 1.0
 * @since 2024-10-19
 */
public interface PlanMilestoneService {
    
    /**
     * 创建里程碑
     * 
     * @param planId 规划ID
     * @param dto 里程碑DTO
     * @return 里程碑信息
     */
    PlanMilestone createMilestone(Long planId, PlanMilestoneDTO dto);
    
    /**
     * 更新里程碑
     * 
     * @param milestoneId 里程碑ID
     * @param dto 里程碑DTO
     * @return 更新后的里程碑信息
     */
    PlanMilestone updateMilestone(Long milestoneId, PlanMilestoneDTO dto);
    
    /**
     * 删除里程碑
     * 
     * @param milestoneId 里程碑ID
     */
    void deleteMilestone(Long milestoneId);
    
    /**
     * 获取规划的里程碑列表
     * 
     * @param planId 规划ID
     * @return 里程碑列表
     */
    List<PlanMilestone> getPlanMilestones(Long planId);
    
    /**
     * 根据状态获取里程碑
     * 
     * @param planId 规划ID
     * @param status 状态
     * @return 里程碑列表
     */
    List<PlanMilestone> getMilestonesByStatus(Long planId, MilestoneStatus status);
    
    /**
     * 根据日期范围获取里程碑
     * 
     * @param planId 规划ID
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return 里程碑列表
     */
    List<PlanMilestone> getMilestonesByDateRange(Long planId, LocalDate startDate, LocalDate endDate);
    
    /**
     * 标记里程碑为已完成
     * 
     * @param milestoneId 里程碑ID
     */
    void markMilestoneCompleted(Long milestoneId);
    
    /**
     * 更新里程碑状态
     * 
     * @param milestoneId 里程碑ID
     * @param status 新状态
     */
    void updateMilestoneStatus(Long milestoneId, MilestoneStatus status);
}

