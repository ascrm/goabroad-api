package com.goabroad.service.tool.impl;

import com.goabroad.model.dto.GpaConversionDTO;
import com.goabroad.model.entity.GpaConversion;
import com.goabroad.service.tool.GpaConversionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * GPA转换服务实现
 * 
 * @author GoAbroad Team
 * @version 1.0
 * @since 2024-10-19
 */
@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class GpaConversionServiceImpl implements GpaConversionService {

    @Override
    public GpaConversion convert(Long userId, GpaConversionDTO dto) {
        return null;
    }

    @Override
    public Page<GpaConversion> getConversionHistory(Long userId, Pageable pageable) {
        return null;
    }

    @Override
    public void deleteConversion(Long conversionId, Long userId) {

    }
}

