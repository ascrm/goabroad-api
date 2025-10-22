package com.goabroad.service.user.impl;

import com.goabroad.model.dto.UserProfileDTO;
import com.goabroad.model.entity.UserProfile;
import com.goabroad.service.user.UserProfileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 用户详细资料服务实现
 * 
 * @author GoAbroad Team
 * @version 1.0
 * @since 2024-10-19
 */
@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class UserProfileServiceImpl implements UserProfileService {
    @Override
    public UserProfile getUserProfile(Long userId) {
        return null;
    }

    @Override
    public UserProfile createUserProfile(Long userId, UserProfileDTO dto) {
        return null;
    }

    @Override
    public UserProfile updateUserProfile(Long userId, UserProfileDTO dto) {
        return null;
    }

    @Override
    public void deleteUserProfile(Long userId) {

    }

    @Override
    public boolean hasUserProfile(Long userId) {
        return false;
    }
}

