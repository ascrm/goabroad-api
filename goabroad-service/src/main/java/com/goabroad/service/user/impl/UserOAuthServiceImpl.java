package com.goabroad.service.user.impl;

import com.goabroad.model.entity.UserOAuthBinding;
import com.goabroad.model.enums.OAuthProvider;
import com.goabroad.service.user.UserOAuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 用户第三方登录绑定服务实现
 * 
 * @author GoAbroad Team
 * @version 1.0
 * @since 2024-10-19
 */
@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class UserOAuthServiceImpl implements UserOAuthService {
    @Override
    public UserOAuthBinding bindOAuth(Long userId, OAuthProvider provider, String oauthUserId, String accessToken) {
        return null;
    }

    @Override
    public void unbindOAuth(Long userId, OAuthProvider provider) {

    }

    @Override
    public List<UserOAuthBinding> getUserBindings(Long userId) {
        return List.of();
    }

    @Override
    public boolean isBindingExists(Long userId, OAuthProvider provider) {
        return false;
    }

    @Override
    public Long getUserIdByOAuth(OAuthProvider provider, String oauthUserId) {
        return 0L;
    }
}

