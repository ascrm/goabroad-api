package com.goabroad.model.dto;

import com.goabroad.model.dto.validation.Create;
import com.goabroad.model.enums.MaterialCategory;
import com.goabroad.model.enums.TaskStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 材料DTO（创建和更新共用）
 * 
 * @author GoAbroad Team
 * @version 1.0
 * @since 2024-10-21
 */
@Data
public class MaterialDTO {
    
    @NotNull(message = "规划ID不能为空", groups = Create.class)
    private Long planId;
    
    @NotBlank(message = "材料名称不能为空", groups = Create.class)
    private String materialName;
    
    @NotNull(message = "材料类别不能为空", groups = Create.class)
    private MaterialCategory category;
    
    private String description;
    
    private Boolean isRequired;
    
    private TaskStatus status;
}

