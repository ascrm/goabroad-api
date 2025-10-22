package com.goabroad.model.dto;

import com.goabroad.model.dto.common.BaseDTO;
import com.goabroad.model.dto.validation.Create;
import com.goabroad.model.enums.NotificationType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 通知DTO
 * 
 * @author GoAbroad Team
 * @version 1.0
 * @since 2024-10-19
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class NotificationDTO extends BaseDTO {
    
    /**
     * 通知标题
     */
    @NotBlank(message = "通知标题不能为空", groups = Create.class)
    private String title;
    
    /**
     * 通知内容
     */
    @NotBlank(message = "通知内容不能为空", groups = Create.class)
    private String content;
    
    /**
     * 通知类型
     */
    @NotNull(message = "通知类型不能为空", groups = Create.class)
    private NotificationType notificationType;
    
    /**
     * 通知状态
     */
    private Integer status;
    
    /**
     * 关联实体类型（如：POST, COMMENT, PLAN等）
     */
    private String relatedEntityType;
    
    /**
     * 关联实体ID
     */
    private Long relatedEntityId;
    
    /**
     * 跳转URL
     */
    private String actionUrl;
}

