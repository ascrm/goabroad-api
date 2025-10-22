package com.goabroad.repository.mysql;

import com.goabroad.model.entity.MaterialChecklist;
import com.goabroad.model.enums.MaterialCategory;
import com.goabroad.model.enums.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 材料清单Repository接口
 * 
 * @author GoAbroad Team
 * @version 1.0
 * @since 2024-10-19
 */
@Repository
public interface MaterialChecklistRepository extends JpaRepository<MaterialChecklist, Long> {
    
    /**
     * 根据规划ID查询材料清单
     * 
     * @param planId 规划ID
     * @return 材料清单列表
     */
    @Query("SELECT m FROM MaterialChecklist m WHERE m.planId = :planId ORDER BY m.category ASC, m.sortOrder ASC")
    List<MaterialChecklist> findByPlanIdOrderByCategoryAndSortOrder(@Param("planId") Long planId);
    
    /**
     * 根据规划ID和类别查询材料清单
     * 
     * @param planId 规划ID
     * @param category 材料类别
     * @return 材料清单列表
     */
    List<MaterialChecklist> findByPlanIdAndCategory(Long planId, MaterialCategory category);
    
    /**
     * 根据规划ID和状态查询材料清单
     * 
     * @param planId 规划ID
     * @param status 准备状态
     * @return 材料清单列表
     */
    List<MaterialChecklist> findByPlanIdAndStatus(Long planId, TaskStatus status);
    
    /**
     * 统计规划的材料数量
     * 
     * @param planId 规划ID
     * @return 材料数量
     */
    long countByPlanId(Long planId);
    
    /**
     * 统计规划指定状态的材料数量
     * 
     * @param planId 规划ID
     * @param status 准备状态
     * @return 材料数量
     */
    long countByPlanIdAndStatus(Long planId, TaskStatus status);
    
    /**
     * 统计规划指定类别的材料数量
     * 
     * @param planId 规划ID
     * @param category 材料类别
     * @return 材料数量
     */
    long countByPlanIdAndCategory(Long planId, MaterialCategory category);
    
    /**
     * 删除规划的所有材料清单
     * 
     * @param planId 规划ID
     */
    void deleteByPlanId(Long planId);
}

