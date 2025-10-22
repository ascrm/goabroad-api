package com.goabroad.model.dto;

import com.goabroad.model.dto.common.BaseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 用户偏好设置DTO
 * 
 * @author GoAbroad Team
 * @version 1.0
 * @since 2024-10-19
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UserPreferencesDTO extends BaseDTO {
    
    /**
     * 语言偏好
     */
    private String language;
    
    /**
     * 时区
     */
    private String timezone;
    
    /**
     * 货币偏好
     */
    private String currency;
    
    /**
     * 是否接收邮件通知
     */
    private Boolean emailNotificationEnabled;
    
    /**
     * 是否接收系统通知
     */
    private Boolean systemNotificationEnabled;
    
    /**
     * 是否接收提醒通知
     */
    private Boolean reminderNotificationEnabled;
    
    /**
     * 是否接收社区通知
     */
    private Boolean communityNotificationEnabled;
    
    /**
     * 隐私设置：是否公开个人资料
     */
    private Boolean profilePublic;
    
    /**
     * 隐私设置：是否公开规划
     */
    private Boolean planPublic;
    
    /**
     * 主题设置（light/dark/auto）
     */
    private String theme;
    
    /**
     * 其他自定义设置（JSON格式）
     */
    private String customSettings;
}

