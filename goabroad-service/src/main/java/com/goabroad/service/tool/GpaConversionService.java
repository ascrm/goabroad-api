package com.goabroad.service.tool;

import com.goabroad.model.dto.GpaConversionDTO;
import com.goabroad.model.entity.GpaConversion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * GPA转换服务接口
 * 
 * @author GoAbroad Team
 * @version 1.0
 * @since 2024-10-19
 */
public interface GpaConversionService {
    
    /**
     * GPA转换
     * 
     * @param userId 用户ID
     * @param dto 转换DTO
     * @return 转换结果
     */
    GpaConversion convert(Long userId, GpaConversionDTO dto);
    
    /**
     * 获取转换历史
     * 
     * @param userId 用户ID
     * @param pageable 分页参数
     * @return 转换历史列表
     */
    Page<GpaConversion> getConversionHistory(Long userId, Pageable pageable);
    
    /**
     * 删除转换记录
     * 
     * @param conversionId 转换ID
     * @param userId 用户ID
     */
    void deleteConversion(Long conversionId, Long userId);
}

