package com.goabroad.repository.mysql;

import com.goabroad.model.entity.CostCalculation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 费用计算记录Repository接口
 * 
 * @author GoAbroad Team
 * @version 1.0
 * @since 2024-10-19
 */
@Repository
public interface CostCalculationRepository extends JpaRepository<CostCalculation, Long> {
    
    /**
     * 根据用户ID查询计算记录
     * 
     * @param userId 用户ID
     * @param pageable 分页参数
     * @return 计算记录列表
     */
    Page<CostCalculation> findByUserId(Long userId, Pageable pageable);
    
    /**
     * 根据用户ID和国家代码查询计算记录
     * 
     * @param userId 用户ID
     * @param countryCode 国家代码
     * @return 计算记录列表
     */
    @Query("SELECT cc FROM CostCalculation cc WHERE cc.userId = :userId AND cc.countryCode = :countryCode ORDER BY cc.createdAt DESC")
    List<CostCalculation> findByUserIdAndCountryCode(@Param("userId") Long userId, 
                                                       @Param("countryCode") String countryCode);
    
    /**
     * 统计用户的计算记录数量
     * 
     * @param userId 用户ID
     * @return 记录数量
     */
    long countByUserId(Long userId);
}

