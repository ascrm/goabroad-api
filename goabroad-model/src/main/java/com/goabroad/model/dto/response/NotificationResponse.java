package com.goabroad.model.dto.response;

import com.goabroad.model.enums.NotificationType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 通知响应DTO
 * 
 * @author GoAbroad Team
 * @version 1.0
 * @since 2024-10-19
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationResponse implements Serializable {
    
    @Serial
    private static final long serialVersionUID = 1L;
    
    /**
     * 通知ID
     */
    private Long id;
    
    /**
     * 接收用户ID
     */
    private Long userId;
    
    /**
     * 通知标题
     */
    private String title;
    
    /**
     * 通知内容
     */
    private String content;
    
    /**
     * 通知类型
     */
    private NotificationType notificationType;
    
    /**
     * 通知状态
     */
    private Integer status;
    
    /**
     * 关联实体类型
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
    
    /**
     * 是否已读
     */
    private Boolean isRead;
    
    /**
     * 创建时间
     */
    private LocalDateTime createdAt;
    
    /**
     * 读取时间
     */
    private LocalDateTime readAt;
}

