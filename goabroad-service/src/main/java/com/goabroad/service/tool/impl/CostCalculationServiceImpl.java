package com.goabroad.service.tool.impl;

import com.goabroad.model.dto.CostCalculationDTO;
import com.goabroad.model.entity.CostCalculation;
import com.goabroad.service.tool.CostCalculationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 费用计算服务实现
 * 
 * @author GoAbroad Team
 * @version 1.0
 * @since 2024-10-19
 */
@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class CostCalculationServiceImpl implements CostCalculationService {
    @Override
    public CostCalculation calculate(Long userId, CostCalculationDTO dto) {
        return null;
    }

    @Override
    public Page<CostCalculation> getCalculationHistory(Long userId, Pageable pageable) {
        return null;
    }

    @Override
    public List<CostCalculation> getCalculationsByCountry(Long userId, String countryCode) {
        return List.of();
    }

    @Override
    public void deleteCalculation(Long calculationId, Long userId) {

    }
}

