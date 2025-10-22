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

/**
 * 费用计算DTO
 * 
 * @author GoAbroad Team
 * @version 1.0
 * @since 2024-10-19
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CostCalculationDTO extends BaseDTO {
    
    /**
     * 国家代码
     */
    @NotBlank(message = "国家代码不能为空", groups = Create.class)
    private String countryCode;
    
    /**
     * 城市
     */
    private String city;
    
    /**
     * 学校名称
     */
    private String schoolName;
    
    /**
     * 专业
     */
    private String major;
    
    /**
     * 学习时长（年）
     */
    @NotNull(message = "学习时长不能为空", groups = Create.class)
    @Positive(message = "学习时长必须大于0")
    private Integer studyYears;
    
    /**
     * 学费（每年）
     */
    private BigDecimal tuitionPerYear;
    
    /**
     * 生活费（每月）
     */
    private BigDecimal livingCostPerMonth;
    
    /**
     * 住宿费（每月）
     */
    private BigDecimal accommodationPerMonth;
    
    /**
     * 签证费
     */
    private BigDecimal visaFee;
    
    /**
     * 语言考试费
     */
    private BigDecimal languageTestFee;
    
    /**
     * 申请费
     */
    private BigDecimal applicationFee;
    
    /**
     * 其他费用
     */
    private BigDecimal otherCosts;
    
    /**
     * 总费用
     */
    private BigDecimal totalCost;
    
    /**
     * 币种
     */
    private String currency;
    
    /**
     * 备注
     */
    private String notes;
}

