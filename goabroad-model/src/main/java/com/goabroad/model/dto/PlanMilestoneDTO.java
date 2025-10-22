package com.goabroad.model.dto;

import com.goabroad.model.dto.common.BaseDTO;
import com.goabroad.model.dto.validation.Create;
import com.goabroad.model.enums.MilestoneStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * 规划里程碑DTO
 * 
 * @author GoAbroad Team
 * @version 1.0
 * @since 2024-10-19
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class PlanMilestoneDTO extends BaseDTO {

    /**
     * 规划ID
     */
    @NotNull(message = "规划ID不能为空", groups = Create.class)
    private Long planId;
    
    /**
     * 里程碑名称
     */
    @NotBlank(message = "里程碑名称不能为空", groups = Create.class)
    private String milestoneName;
    
    /**
     * 描述
     */
    private String description;
    
    /**
     * 目标日期
     */
    @NotNull(message = "目标日期不能为空", groups = Create.class)
    private LocalDate targetDate;
    
    /**
     * 完成日期
     */
    private LocalDate completedDate;
    
    /**
     * 状态
     */
    private MilestoneStatus status;
    
    /**
     * 排序顺序
     */
    private Integer sortOrder;
    
    /**
     * 备注
     */
    private String notes;
}

