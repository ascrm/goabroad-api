package com.goabroad.service.user;

import com.goabroad.model.dto.UserProfileDTO;
import com.goabroad.model.entity.UserProfile;

/**
 * 用户详细资料服务接口
 * 
 * @author GoAbroad Team
 * @version 1.0
 * @since 2024-10-19
 */
public interface UserProfileService {
    
    /**
     * 获取用户资料
     * 
     * @param userId 用户ID
     * @return 用户资料
     */
    UserProfile getUserProfile(Long userId);
    
    /**
     * 创建用户资料
     * 
     * @param userId 用户ID
     * @param dto 资料DTO
     * @return 用户资料
     */
    UserProfile createUserProfile(Long userId, UserProfileDTO dto);
    
    /**
     * 更新用户资料
     * 
     * @param userId 用户ID
     * @param request 资料请求
     * @return 更新后的用户资料
     */
    UserProfile updateUserProfile(Long userId, UserProfileDTO dto);
    
    /**
     * 删除用户资料
     * 
     * @param userId 用户ID
     */
    void deleteUserProfile(Long userId);
    
    /**
     * 检查用户是否已创建资料
     * 
     * @param userId 用户ID
     * @return 是否已创建
     */
    boolean hasUserProfile(Long userId);
}

