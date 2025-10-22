package com.goabroad.service.country;

import com.goabroad.model.dto.CountryPolicyDTO;
import com.goabroad.model.entity.CountryPolicy;
import com.goabroad.model.enums.PolicyType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

/**
 * 国家政策更新服务接口
 * 
 * @author GoAbroad Team
 * @version 1.0
 * @since 2024-10-19
 */
public interface CountryPolicyService {
    
    /**
     * 创建政策更新
     * 
     * @param dto 政策DTO
     * @return 政策信息
     */
    CountryPolicy createPolicy(CountryPolicyDTO dto);
    
    /**
     * 更新政策信息
     * 
     * @param policyId 政策ID
     * @param dto 政策DTO
     * @return 更新后的政策信息
     */
    CountryPolicy updatePolicy(Long policyId, CountryPolicyDTO dto);
    
    /**
     * 删除政策
     * 
     * @param policyId 政策ID
     */
    void deletePolicy(Long policyId);
    
    /**
     * 根据ID获取政策详情
     * 
     * @param policyId 政策ID
     * @return 政策详情
     */
    CountryPolicy getPolicyById(Long policyId);
    
    /**
     * 获取国家的政策列表
     * 
     * @param countryCode 国家代码
     * @param pageable 分页参数
     * @return 政策列表
     */
    Page<CountryPolicy> getCountryPolicies(String countryCode, Pageable pageable);
    
    /**
     * 根据国家和政策类型查询
     * 
     * @param countryCode 国家代码
     * @param policyType 政策类型
     * @param pageable 分页参数
     * @return 政策列表
     */
    Page<CountryPolicy> getPoliciesByType(String countryCode, PolicyType policyType, Pageable pageable);
    
    /**
     * 获取重要政策更新
     * 
     * @param pageable 分页参数
     * @return 重要政策列表
     */
    Page<CountryPolicy> getImportantPolicies(Pageable pageable);
    
    /**
     * 根据生效日期范围查询政策
     * 
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return 政策列表
     */
    List<CountryPolicy> getPoliciesByDateRange(LocalDate startDate, LocalDate endDate);
    
    /**
     * 标记政策为重要
     * 
     * @param policyId 政策ID
     */
    void markAsImportant(Long policyId);
    
    /**
     * 发布政策
     * 
     * @param policyId 政策ID
     */
    void publishPolicy(Long policyId);
}

