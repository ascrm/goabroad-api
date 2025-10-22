package com.goabroad.service.user;

import com.goabroad.model.dto.UserPreferencesDTO;
import com.goabroad.model.entity.UserPreferences;

/**
 * 用户偏好设置服务接口
 * 
 * @author GoAbroad Team
 * @version 1.0
 * @since 2024-10-19
 */
public interface UserPreferencesService {
    
    /**
     * 获取用户偏好设置
     * 
     * @param userId 用户ID
     * @return 偏好设置
     */
    UserPreferences getUserPreferences(Long userId);
    
    /**
     * 创建用户偏好设置
     * 
     * @param userId 用户ID
     * @param dto 偏好设置DTO
     * @return 偏好设置
     */
    UserPreferences createUserPreferences(Long userId, UserPreferencesDTO dto);
    
    /**
     * 更新用户偏好设置
     * 
     * @param userId 用户ID
     * @param request 偏好设置请求
     * @return 更新后的偏好设置
     */
    UserPreferences updateUserPreferences(Long userId, UserPreferencesDTO dto);
    
    /**
     * 添加目标国家
     * 
     * @param userId 用户ID
     * @param countryCode 国家代码
     */
    void addTargetCountry(Long userId, String countryCode);
    
    /**
     * 移除目标国家
     * 
     * @param userId 用户ID
     * @param countryCode 国家代码
     */
    void removeTargetCountry(Long userId, String countryCode);
    
    /**
     * 删除用户偏好设置
     * 
     * @param userId 用户ID
     */
    void deleteUserPreferences(Long userId);
}

