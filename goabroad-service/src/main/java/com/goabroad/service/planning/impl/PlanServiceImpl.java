package com.goabroad.service.planning.impl;

import com.goabroad.model.dto.PlanDTO;
import com.goabroad.model.dto.vo.PlanVO;
import com.goabroad.model.enums.PlanStatus;
import com.goabroad.service.planning.PlanService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 规划服务实现
 * 
 * @author GoAbroad Team
 * @version 1.0
 * @since 2024-10-19
 */
@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class PlanServiceImpl implements PlanService {


    @Override
    public PlanVO createPlan(Long userId, PlanDTO dto) {
        return null;
    }

    @Override
    public PlanVO updatePlan(Long planId, Long userId, PlanDTO dto) {
        return null;
    }

    @Override
    public void deletePlan(Long planId, Long userId) {

    }

    @Override
    public PlanVO getPlanById(Long planId) {
        return null;
    }

    @Override
    public Page<PlanVO> getUserPlans(Long userId, Pageable pageable) {
        return null;
    }

    @Override
    public Page<PlanVO> getUserPlansByStatus(Long userId, PlanStatus status, Pageable pageable) {
        return null;
    }

    @Override
    public List<PlanVO> getActivePlans(Long userId) {
        return List.of();
    }

    @Override
    public void updatePlanStatus(Long planId, Long userId, PlanStatus status) {

    }

    @Override
    public void updatePlanProgress(Long planId) {

    }

    @Override
    public boolean checkPlanPermission(Long planId, Long userId) {
        return false;
    }
}
