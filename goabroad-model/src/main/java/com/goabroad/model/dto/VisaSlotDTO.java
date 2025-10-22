package com.goabroad.model.dto;

import com.goabroad.model.dto.common.BaseDTO;
import com.goabroad.model.dto.validation.Create;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 签证预约DTO
 * 
 * @author GoAbroad Team
 * @version 1.0
 * @since 2024-10-19
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class VisaSlotDTO extends BaseDTO {
    
    /**
     * 国家代码
     */
    @NotBlank(message = "国家代码不能为空", groups = Create.class)
    private String countryCode;
    
    /**
     * 签证类型
     */
    @NotBlank(message = "签证类型不能为空", groups = Create.class)
    private String visaType;
    
    /**
     * 使馆城市
     */
    @NotBlank(message = "使馆城市不能为空", groups = Create.class)
    private String embassyCity;
    
    /**
     * 预约日期
     */
    @NotNull(message = "预约日期不能为空", groups = Create.class)
    private LocalDate slotDate;
    
    /**
     * 可用名额数
     */
    @NotNull(message = "可用名额数不能为空", groups = Create.class)
    @Positive(message = "可用名额数必须大于0")
    private Integer availableSlots;
    
    /**
     * 总名额数
     */
    @Positive(message = "总名额数必须大于0")
    private Integer totalSlots;
    
    /**
     * 预约费用
     */
    private BigDecimal fee;
    
    /**
     * 币种
     */
    private String currency;
    
    /**
     * 备注
     */
    private String notes;
}

