package com.goabroad.repository.mysql;

import com.goabroad.model.entity.Plan;
import com.goabroad.model.enums.PlanStatus;
import com.goabroad.model.enums.PlanType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 规划Repository接口
 * 
 * @author GoAbroad Team
 * @version 1.0
 * @since 2024-10-19
 */
@Repository
public interface PlanRepository extends JpaRepository<Plan, Long> {
    
    /**
     * 根据用户ID查询规划列表
     * 
     * @param userId 用户ID
     * @param pageable 分页参数
     * @return 规划列表
     */
    Page<Plan> findByUserId(Long userId, Pageable pageable);
    
    /**
     * 根据用户ID和状态查询规划列表
     * 
     * @param userId 用户ID
     * @param status 规划状态
     * @param pageable 分页参数
     * @return 规划列表
     */
    Page<Plan> findByUserIdAndStatus(Long userId, PlanStatus status, Pageable pageable);
    
    /**
     * 根据用户ID和规划类型查询规划列表
     * 
     * @param userId 用户ID
     * @param planType 规划类型
     * @return 规划列表
     */
    List<Plan> findByUserIdAndPlanType(Long userId, PlanType planType);
    
    /**
     * 根据用户ID和国家代码查询规划
     * 
     * @param userId 用户ID
     * @param countryCode 国家代码
     * @return 规划对象
     */
    Optional<Plan> findByUserIdAndCountryCode(Long userId, String countryCode);
    
    /**
     * 统计用户的规划数量
     * 
     * @param userId 用户ID
     * @return 规划数量
     */
    long countByUserId(Long userId);
    
    /**
     * 统计用户指定状态的规划数量
     * 
     * @param userId 用户ID
     * @param status 规划状态
     * @return 规划数量
     */
    long countByUserIdAndStatus(Long userId, PlanStatus status);
    
    /**
     * 查询用户的进行中规划
     * 
     * @param userId 用户ID
     * @return 规划列表
     */
    @Query("SELECT p FROM Plan p WHERE p.userId = :userId AND p.status = 'IN_PROGRESS' ORDER BY p.updatedAt DESC")
    List<Plan> findActiveByUserId(@Param("userId") Long userId);
    
    /**
     * 根据国家代码统计规划数量（用于统计热门国家）
     * 
     * @param countryCode 国家代码
     * @return 规划数量
     */
    long countByCountryCode(String countryCode);
}

