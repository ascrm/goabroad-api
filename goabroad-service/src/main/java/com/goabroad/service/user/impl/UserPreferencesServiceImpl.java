package com.goabroad.service.user.impl;

import com.goabroad.model.dto.UserPreferencesDTO;
import com.goabroad.model.entity.UserPreferences;
import com.goabroad.service.user.UserPreferencesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 用户偏好设置服务实现
 * 
 * @author GoAbroad Team
 * @version 1.0
 * @since 2024-10-19
 */
@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class UserPreferencesServiceImpl implements UserPreferencesService {

    @Override
    public UserPreferences getUserPreferences(Long userId) {
        return null;
    }

    @Override
    public UserPreferences createUserPreferences(Long userId, UserPreferencesDTO dto) {
        return null;
    }

    @Override
    public UserPreferences updateUserPreferences(Long userId, UserPreferencesDTO dto) {
        return null;
    }

    @Override
    public void addTargetCountry(Long userId, String countryCode) {

    }

    @Override
    public void removeTargetCountry(Long userId, String countryCode) {

    }

    @Override
    public void deleteUserPreferences(Long userId) {

    }
}

