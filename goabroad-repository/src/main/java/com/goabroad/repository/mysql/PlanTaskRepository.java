package com.goabroad.repository.mysql;

import com.goabroad.model.entity.PlanTask;
import com.goabroad.model.enums.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * 规划任务Repository接口
 * 
 * @author GoAbroad Team
 * @version 1.0
 * @since 2024-10-19
 */
@Repository
public interface PlanTaskRepository extends JpaRepository<PlanTask, Long> {
    
    /**
     * 根据阶段ID查询任务列表
     * 
     * @param stageId 阶段ID
     * @return 任务列表
     */
    @Query("SELECT t FROM PlanTask t WHERE t.stageId = :stageId ORDER BY t.sortOrder ASC")
    List<PlanTask> findByStageIdOrderBySortOrder(@Param("stageId") Long stageId);
    
    /**
     * 根据规划ID查询任务列表
     * 
     * @param planId 规划ID
     * @return 任务列表
     */
    List<PlanTask> findByPlanId(Long planId);
    
    /**
     * 根据阶段ID和状态查询任务列表
     * 
     * @param stageId 阶段ID
     * @param status 任务状态
     * @return 任务列表
     */
    List<PlanTask> findByStageIdAndStatus(Long stageId, TaskStatus status);
    
    /**
     * 根据规划ID和截止日期范围查询任务
     * 
     * @param planId 规划ID
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return 任务列表
     */
    @Query("SELECT t FROM PlanTask t WHERE t.planId = :planId AND t.dueDate BETWEEN :startDate AND :endDate ORDER BY t.dueDate ASC")
    List<PlanTask> findByPlanIdAndDueDateBetween(@Param("planId") Long planId, 
                                                   @Param("startDate") LocalDate startDate, 
                                                   @Param("endDate") LocalDate endDate);
    
    /**
     * 统计阶段的任务数量
     * 
     * @param stageId 阶段ID
     * @return 任务数量
     */
    long countByStageId(Long stageId);
    
    /**
     * 统计阶段指定状态的任务数量
     * 
     * @param stageId 阶段ID
     * @param status 任务状态
     * @return 任务数量
     */
    long countByStageIdAndStatus(Long stageId, TaskStatus status);
    
    /**
     * 删除阶段的所有任务
     * 
     * @param stageId 阶段ID
     */
    void deleteByStageId(Long stageId);
    
    /**
     * 删除规划的所有任务
     * 
     * @param planId 规划ID
     */
    void deleteByPlanId(Long planId);
}

