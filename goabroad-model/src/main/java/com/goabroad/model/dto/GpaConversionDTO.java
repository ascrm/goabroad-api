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
 * GPA换算DTO
 * 
 * @author GoAbroad Team
 * @version 1.0
 * @since 2024-10-19
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class GpaConversionDTO extends BaseDTO {
    
    /**
     * 原始分数
     */
    @NotNull(message = "原始分数不能为空", groups = Create.class)
    @Positive(message = "原始分数必须大于0")
    private BigDecimal originalScore;
    
    /**
     * 原始计分制（如：100、5.0、4.0）
     */
    @NotBlank(message = "原始计分制不能为空", groups = Create.class)
    private String originalScale;
    
    /**
     * 目标计分制
     */
    @NotBlank(message = "目标计分制不能为空", groups = Create.class)
    private String targetScale;
    
    /**
     * 换算后的GPA
     */
    private BigDecimal convertedGpa;
    
    /**
     * 学校名称
     */
    private String schoolName;
    
    /**
     * 专业
     */
    private String major;
    
    /**
     * 备注
     */
    private String notes;
}

