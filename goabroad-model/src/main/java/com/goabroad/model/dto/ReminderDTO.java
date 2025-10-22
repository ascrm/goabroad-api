package com.goabroad.model.dto;

import com.goabroad.model.dto.common.BaseDTO;
import com.goabroad.model.dto.validation.Create;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 提醒DTO
 * 
 * @author GoAbroad Team
 * @version 1.0
 * @since 2024-10-19
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ReminderDTO extends BaseDTO {
    
    /**
     * 提醒标题
     */
    @NotBlank(message = "提醒标题不能为空", groups = Create.class)
    private String title;
    
    /**
     * 提醒内容
     */
    private String content;
    
    /**
     * 提醒类型
     */
    @NotNull(message = "提醒类型不能为空", groups = Create.class)
    private Integer reminderType;
    
    /**
     * 提醒时间
     */
    @NotNull(message = "提醒时间不能为空", groups = Create.class)
    private LocalDateTime remindAt;
    
    /**
     * 提醒状态
     */
    private Integer status;
    
    /**
     * 关联的规划ID
     */
    private Long planId;
    
    /**
     * 关联的里程碑ID
     */
    private Long milestoneId;
    
    /**
     * 是否重复
     */
    private Boolean isRecurring;
    
    /**
     * 重复规则（如：DAILY, WEEKLY, MONTHLY）
     */
    private String recurringRule;
}

