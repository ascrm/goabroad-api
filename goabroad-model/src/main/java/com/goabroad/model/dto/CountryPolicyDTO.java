package com.goabroad.model.dto;

import com.goabroad.model.dto.validation.Create;
import com.goabroad.model.enums.PolicyType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

/**
 * 国家政策DTO
 * 
 * @author GoAbroad Team
 * @version 1.0
 * @since 2024-10-21
 */
@Data
public class CountryPolicyDTO {
    
    @NotBlank(message = "国家代码不能为空", groups = Create.class)
    private String countryCode;
    
    @NotBlank(message = "政策标题不能为空", groups = Create.class)
    private String title;
    
    @NotBlank(message = "政策内容不能为空", groups = Create.class)
    private String content;
    
    @NotNull(message = "政策类型不能为空", groups = Create.class)
    private PolicyType policyType;
    
    @NotNull(message = "生效日期不能为空", groups = Create.class)
    private LocalDate effectiveDate;
    
    private LocalDate expiryDate;
    
    private Boolean isImportant;
    
    private String sourceUrl;
}

