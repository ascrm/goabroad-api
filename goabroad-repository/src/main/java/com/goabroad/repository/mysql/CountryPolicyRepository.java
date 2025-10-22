package com.goabroad.repository.mysql;

import com.goabroad.model.entity.CountryPolicy;
import com.goabroad.model.enums.PolicyStatus;
import com.goabroad.model.enums.PolicyType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * 国家政策更新Repository接口
 * 
 * @author GoAbroad Team
 * @version 1.0
 * @since 2024-10-19
 */
@Repository
public interface CountryPolicyRepository extends JpaRepository<CountryPolicy, Long> {
    
    /**
     * 根据国家代码查询政策列表
     * 
     * @param countryCode 国家代码
     * @param pageable 分页参数
     * @return 政策列表
     */
    Page<CountryPolicy> findByCountryCode(String countryCode, Pageable pageable);
    
    /**
     * 根据国家代码和政策类型查询政策列表
     * 
     * @param countryCode 国家代码
     * @param policyType 政策类型
     * @param pageable 分页参数
     * @return 政策列表
     */
    Page<CountryPolicy> findByCountryCodeAndPolicyType(String countryCode, PolicyType policyType, Pageable pageable);
    
    /**
     * 根据国家代码和状态查询政策列表
     * 
     * @param countryCode 国家代码
     * @param status 状态
     * @param pageable 分页参数
     * @return 政策列表
     */
    Page<CountryPolicy> findByCountryCodeAndStatus(String countryCode, PolicyStatus status, Pageable pageable);
    
    /**
     * 查询重要政策更新
     * 
     * @param pageable 分页参数
     * @return 政策列表
     */
    @Query("SELECT cp FROM CountryPolicy cp WHERE cp.isImportant = true AND cp.status = 'ACTIVE' ORDER BY cp.effectiveDate DESC")
    Page<CountryPolicy> findImportantPolicies(Pageable pageable);
    
    /**
     * 根据生效日期范围查询政策
     * 
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return 政策列表
     */
    @Query("SELECT cp FROM CountryPolicy cp WHERE cp.effectiveDate BETWEEN :startDate AND :endDate ORDER BY cp.effectiveDate DESC")
    List<CountryPolicy> findByEffectiveDateBetween(@Param("startDate") LocalDate startDate, 
                                                     @Param("endDate") LocalDate endDate);
    
    /**
     * 统计国家的政策数量
     * 
     * @param countryCode 国家代码
     * @return 政策数量
     */
    long countByCountryCode(String countryCode);
    
    /**
     * 统计国家指定类型的政策数量
     * 
     * @param countryCode 国家代码
     * @param policyType 政策类型
     * @return 政策数量
     */
    long countByCountryCodeAndPolicyType(String countryCode, PolicyType policyType);
}

