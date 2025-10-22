package com.goabroad.model.dto;

import com.goabroad.model.dto.validation.Create;
import com.goabroad.model.enums.ConfigValueType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 系统配置DTO
 * 
 * @author GoAbroad Team
 * @version 1.0
 * @since 2024-10-21
 */
@Data
public class SystemConfigDTO {
    
    @NotBlank(message = "配置键不能为空", groups = Create.class)
    private String configKey;
    
    @NotBlank(message = "配置值不能为空", groups = Create.class)
    private String configValue;
    
    @NotNull(message = "值类型不能为空", groups = Create.class)
    private ConfigValueType valueType;
    
    private String description;
    
    private Boolean isPublic;
}

