package com.goabroad.model.dto;

import com.goabroad.model.dto.validation.Create;
import com.goabroad.model.dto.validation.Update;
import com.goabroad.model.enums.PlanStatus;
import com.goabroad.model.enums.PlanType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

/**
 * 规划DTO（创建和更新共用）
 * 
 * @author GoAbroad Team
 * @version 1.0
 * @since 2024-10-21
 */
@Data
public class PlanDTO {
    
    @NotBlank(message = "规划名称不能为空", groups = Create.class)
    private String planName;
    
    @NotNull(message = "规划类型不能为空", groups = Create.class)
    private PlanType planType;
    
    @NotBlank(message = "目标国家不能为空", groups = Create.class)
    private String countryCode;
    
    private String targetSchool;
    
    private String targetMajor;
    
    @NotNull(message = "目标入学日期不能为空", groups = Create.class)
    private LocalDate targetDate;
    
    private String description;
    
    private PlanStatus status;
}

