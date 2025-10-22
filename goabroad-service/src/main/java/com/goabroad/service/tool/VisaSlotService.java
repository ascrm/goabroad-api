package com.goabroad.service.tool;

import com.goabroad.model.dto.VisaSlotDTO;
import com.goabroad.model.entity.VisaSlot;

import java.time.LocalDate;
import java.util.List;

/**
 * 签证预约服务接口
 * 
 * @author GoAbroad Team
 * @version 1.0
 * @since 2024-10-19
 */
public interface VisaSlotService {
    
    /**
     * 创建签证名额信息
     * 
     * @param dto 名额DTO
     * @return 名额信息
     */
    VisaSlot createVisaSlot(VisaSlotDTO dto);
    
    /**
     * 更新签证名额
     * 
     * @param slotId 名额ID
     * @param dto 名额DTO
     * @return 更新后的名额信息
     */
    VisaSlot updateVisaSlot(Long slotId, VisaSlotDTO dto);
    
    /**
     * 删除签证名额
     * 
     * @param slotId 名额ID
     */
    void deleteVisaSlot(Long slotId);
    
    /**
     * 查询可用的签证名额
     * 
     * @param countryCode 国家代码
     * @param visaType 签证类型
     * @param embassyCity 使馆城市
     * @return 可用名额列表
     */
    List<VisaSlot> getAvailableSlots(String countryCode, String visaType, String embassyCity);
    
    /**
     * 根据日期范围查询可用名额
     * 
     * @param countryCode 国家代码
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return 可用名额列表
     */
    List<VisaSlot> getSlotsByDateRange(String countryCode, LocalDate startDate, LocalDate endDate);
    
    /**
     * 根据国家查询签证名额
     * 
     * @param countryCode 国家代码
     * @return 签证名额列表
     */
    List<VisaSlot> getSlotsByCountry(String countryCode);
}

