package com.goabroad.repository.mysql;

import com.goabroad.model.entity.PlanStage;
import com.goabroad.model.enums.StageStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 规划阶段Repository接口
 * 
 * @author GoAbroad Team
 * @version 1.0
 * @since 2024-10-19
 */
@Repository
public interface PlanStageRepository extends JpaRepository<PlanStage, Long> {
    
    /**
     * 根据规划ID查询阶段列表
     * 
     * @param planId 规划ID
     * @return 阶段列表
     */
    @Query("SELECT s FROM PlanStage s WHERE s.planId = :planId ORDER BY s.sortOrder ASC")
    List<PlanStage> findByPlanIdOrderBySortOrder(@Param("planId") Long planId);
    
    /**
     * 根据规划ID和状态查询阶段列表
     * 
     * @param planId 规划ID
     * @param status 阶段状态
     * @return 阶段列表
     */
    List<PlanStage> findByPlanIdAndStatus(Long planId, StageStatus status);
    
    /**
     * 统计规划的阶段数量
     * 
     * @param planId 规划ID
     * @return 阶段数量
     */
    long countByPlanId(Long planId);
    
    /**
     * 统计规划指定状态的阶段数量
     * 
     * @param planId 规划ID
     * @param status 阶段状态
     * @return 阶段数量
     */
    long countByPlanIdAndStatus(Long planId, StageStatus status);
    
    /**
     * 删除规划的所有阶段
     * 
     * @param planId 规划ID
     */
    void deleteByPlanId(Long planId);
}

