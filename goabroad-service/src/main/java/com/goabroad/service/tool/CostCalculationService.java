package com.goabroad.service.tool;

import com.goabroad.model.dto.CostCalculationDTO;
import com.goabroad.model.entity.CostCalculation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 费用计算服务接口
 * 
 * @author GoAbroad Team
 * @version 1.0
 * @since 2024-10-19
 */
public interface CostCalculationService {
    
    /**
     * 计算费用
     * 
     * @param userId 用户ID
     * @param dto 计算DTO
     * @return 计算结果
     */
    CostCalculation calculate(Long userId, CostCalculationDTO dto);
    
    /**
     * 获取计算历史
     * 
     * @param userId 用户ID
     * @param pageable 分页参数
     * @return 计算历史列表
     */
    Page<CostCalculation> getCalculationHistory(Long userId, Pageable pageable);
    
    /**
     * 根据国家获取计算历史
     * 
     * @param userId 用户ID
     * @param countryCode 国家代码
     * @return 计算历史列表
     */
    List<CostCalculation> getCalculationsByCountry(Long userId, String countryCode);
    
    /**
     * 删除计算记录
     * 
     * @param calculationId 计算ID
     * @param userId 用户ID
     */
    void deleteCalculation(Long calculationId, Long userId);
}

