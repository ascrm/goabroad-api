package com.goabroad.service.planning;

import com.goabroad.model.dto.PlanDTO;
import com.goabroad.model.dto.vo.PlanVO;
import com.goabroad.model.enums.PlanStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 规划服务接口
 * 
 * @author GoAbroad Team
 * @version 1.0
 * @since 2024-10-19
 */
public interface PlanService {
    
    /**
     * 创建规划
     * 
     * @param userId 用户ID
     * @param request 创建请求
     * @return 规划信息
     */
    PlanVO createPlan(Long userId, PlanDTO dto);
    
    /**
     * 更新规划
     * 
     * @param planId 规划ID
     * @param userId 用户ID
     * @param dto 规划DTO
     * @return 更新后的规划信息
     */
    PlanVO updatePlan(Long planId, Long userId, PlanDTO dto);
    
    /**
     * 删除规划
     * 
     * @param planId 规划ID
     * @param userId 用户ID
     */
    void deletePlan(Long planId, Long userId);
    
    /**
     * 获取规划详情
     * 
     * @param planId 规划ID
     * @return 规划详情
     */
    PlanVO getPlanById(Long planId);
    
    /**
     * 获取用户的所有规划
     * 
     * @param userId 用户ID
     * @param pageable 分页参数
     * @return 规划列表
     */
    Page<PlanVO> getUserPlans(Long userId, Pageable pageable);
    
    /**
     * 根据状态获取用户规划
     * 
     * @param userId 用户ID
     * @param status 状态
     * @param pageable 分页参数
     * @return 规划列表
     */
    Page<PlanVO> getUserPlansByStatus(Long userId, PlanStatus status, Pageable pageable);
    
    /**
     * 获取用户进行中的规划
     * 
     * @param userId 用户ID
     * @return 规划列表
     */
    List<PlanVO> getActivePlans(Long userId);
    
    /**
     * 更新规划状态
     * 
     * @param planId 规划ID
     * @param userId 用户ID
     * @param status 新状态
     */
    void updatePlanStatus(Long planId, Long userId, PlanStatus status);
    
    /**
     * 更新规划进度
     * 
     * @param planId 规划ID
     */
    void updatePlanProgress(Long planId);
    
    /**
     * 检查规划权限
     * 
     * @param planId 规划ID
     * @param userId 用户ID
     * @return 是否有权限
     */
    boolean checkPlanPermission(Long planId, Long userId);
}

