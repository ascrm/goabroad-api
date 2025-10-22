package com.goabroad.repository.mysql;

import com.goabroad.model.entity.PlanMilestone;
import com.goabroad.model.enums.MilestoneStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * 规划里程碑Repository接口
 * 
 * @author GoAbroad Team
 * @version 1.0
 * @since 2024-10-19
 */
@Repository
public interface PlanMilestoneRepository extends JpaRepository<PlanMilestone, Long> {
    
    /**
     * 根据规划ID查询里程碑列表
     * 
     * @param planId 规划ID
     * @return 里程碑列表
     */
    @Query("SELECT pm FROM PlanMilestone pm WHERE pm.planId = :planId ORDER BY pm.milestoneDate ASC")
    List<PlanMilestone> findByPlanIdOrderByMilestoneDate(@Param("planId") Long planId);
    
    /**
     * 根据规划ID和状态查询里程碑列表
     * 
     * @param planId 规划ID
     * @param status 状态
     * @return 里程碑列表
     */
    List<PlanMilestone> findByPlanIdAndStatus(Long planId, MilestoneStatus status);
    
    /**
     * 根据规划ID和日期范围查询里程碑
     * 
     * @param planId 规划ID
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return 里程碑列表
     */
    @Query("SELECT pm FROM PlanMilestone pm WHERE pm.planId = :planId AND pm.milestoneDate BETWEEN :startDate AND :endDate ORDER BY pm.milestoneDate ASC")
    List<PlanMilestone> findByPlanIdAndDateBetween(@Param("planId") Long planId, 
                                                     @Param("startDate") LocalDate startDate, 
                                                     @Param("endDate") LocalDate endDate);
    
    /**
     * 统计规划的里程碑数量
     * 
     * @param planId 规划ID
     * @return 里程碑数量
     */
    long countByPlanId(Long planId);
    
    /**
     * 统计规划指定状态的里程碑数量
     * 
     * @param planId 规划ID
     * @param status 状态
     * @return 里程碑数量
     */
    long countByPlanIdAndStatus(Long planId, MilestoneStatus status);
    
    /**
     * 删除规划的所有里程碑
     * 
     * @param planId 规划ID
     */
    void deleteByPlanId(Long planId);
}

