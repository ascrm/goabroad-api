package com.goabroad.service.planning.impl;

import com.goabroad.model.dto.PlanMilestoneDTO;
import com.goabroad.model.entity.PlanMilestone;
import com.goabroad.model.enums.MilestoneStatus;
import com.goabroad.service.planning.PlanMilestoneService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

/**
 * 规划里程碑服务实现
 * 
 * @author GoAbroad Team
 * @version 1.0
 * @since 2024-10-19
 */
@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class PlanMilestoneServiceImpl implements PlanMilestoneService {

    @Override
    public PlanMilestone createMilestone(Long planId, PlanMilestoneDTO dto) {
        return null;
    }

    @Override
    public PlanMilestone updateMilestone(Long milestoneId, PlanMilestoneDTO dto) {
        return null;
    }

    @Override
    public void deleteMilestone(Long milestoneId) {

    }

    @Override
    public List<PlanMilestone> getPlanMilestones(Long planId) {
        return List.of();
    }

    @Override
    public List<PlanMilestone> getMilestonesByStatus(Long planId, MilestoneStatus status) {
        return List.of();
    }

    @Override
    public List<PlanMilestone> getMilestonesByDateRange(Long planId, LocalDate startDate, LocalDate endDate) {
        return List.of();
    }

    @Override
    public void markMilestoneCompleted(Long milestoneId) {

    }

    @Override
    public void updateMilestoneStatus(Long milestoneId, MilestoneStatus status) {

    }
}

