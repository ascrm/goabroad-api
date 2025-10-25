package com.goabroad.model.entity;

import lombok.*;

import jakarta.persistence.*;
import java.time.LocalDate;

/**
 * 签证预约信息实体类
 * 
 * @author GoAbroad Team
 * @version 1.0
 * @since 2024-10-19
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "visa_slots", indexes = {
    @Index(name = "idx_country_type", columnList = "country_code,visa_type"),
    @Index(name = "idx_embassy_city", columnList = "embassy_city"),
    @Index(name = "idx_earliest_date", columnList = "earliest_date"),
    @Index(name = "idx_deleted", columnList = "deleted")
})
public class VisaSlot extends BaseEntity {
    
    /**
     * 国家代码
     */
    @Column(name = "country_code", nullable = false, length = 2)
    private String countryCode;
    
    /**
     * 签证类型（F1/B1/H1B等）
     */
    @Column(name = "visa_type", nullable = false, length = 50)
    private String visaType;
    
    /**
     * 使馆城市（beijing/shanghai/guangzhou）
     */
    @Column(name = "embassy_city", nullable = false, length = 50)
    private String embassyCity;
    
    /**
     * 最早可预约日期
     */
    @Column(name = "earliest_date")
    private LocalDate earliestDate;
    
    /**
     * 可用名额数
     */
    @Column(name = "available_slots")
    @Builder.Default
    private Short availableSlots = 0;
}

