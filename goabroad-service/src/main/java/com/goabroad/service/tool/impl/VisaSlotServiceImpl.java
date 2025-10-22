package com.goabroad.service.tool.impl;

import com.goabroad.model.dto.VisaSlotDTO;
import com.goabroad.model.entity.VisaSlot;
import com.goabroad.service.tool.VisaSlotService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

/**
 * 签证预约服务实现
 * 
 * @author GoAbroad Team
 * @version 1.0
 * @since 2024-10-19
 */
@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class VisaSlotServiceImpl implements VisaSlotService {
    @Override
    public VisaSlot createVisaSlot(VisaSlotDTO dto) {
        return null;
    }

    @Override
    public VisaSlot updateVisaSlot(Long slotId, VisaSlotDTO dto) {
        return null;
    }

    @Override
    public void deleteVisaSlot(Long slotId) {

    }

    @Override
    public List<VisaSlot> getAvailableSlots(String countryCode, String visaType, String embassyCity) {
        return List.of();
    }

    @Override
    public List<VisaSlot> getSlotsByDateRange(String countryCode, LocalDate startDate, LocalDate endDate) {
        return List.of();
    }

    @Override
    public List<VisaSlot> getSlotsByCountry(String countryCode) {
        return List.of();
    }
}

