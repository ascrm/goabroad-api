package com.goabroad.repository.mysql;

import com.goabroad.model.entity.VisaSlot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 签证预约信息Repository接口
 * 
 * @author GoAbroad Team
 * @version 1.0
 * @since 2024-10-19
 */
@Repository
public interface VisaSlotRepository extends JpaRepository<VisaSlot, Long> {
    
    /**
     * 根据国家代码查询签证名额
     * 
     * @param countryCode 国家代码
     * @return 签证名额列表
     */
    List<VisaSlot> findByCountryCode(String countryCode);
    
    /**
     * 根据国家代码和签证类型查询
     * 
     * @param countryCode 国家代码
     * @param visaType 签证类型
     * @return 签证名额列表
     */
    List<VisaSlot> findByCountryCodeAndVisaType(String countryCode, String visaType);
    
    /**
     * 根据国家代码、签证类型和使馆城市查询
     * 
     * @param countryCode 国家代码
     * @param visaType 签证类型
     * @param embassyCity 使馆城市
     * @return 签证名额列表
     */
    List<VisaSlot> findByCountryCodeAndVisaTypeAndEmbassyCity(String countryCode, String visaType, String embassyCity);
}

