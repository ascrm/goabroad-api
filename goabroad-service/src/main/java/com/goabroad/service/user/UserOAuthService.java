package com.goabroad.service.user;

import com.goabroad.model.entity.UserOAuthBinding;
import com.goabroad.model.enums.OAuthProvider;

import java.util.List;

/**
 * 用户第三方登录绑定服务接口
 * 
 * @author GoAbroad Team
 * @version 1.0
 * @since 2024-10-19
 */
public interface UserOAuthService {
    
    /**
     * 绑定第三方账号
     * 
     * @param userId 用户ID
     * @param provider 第三方平台
     * @param oauthUserId 第三方用户ID
     * @param accessToken 访问令牌
     * @return 绑定记录
     */
    UserOAuthBinding bindOAuth(Long userId, OAuthProvider provider, String oauthUserId, String accessToken);
    
    /**
     * 解绑第三方账号
     * 
     * @param userId 用户ID
     * @param provider 第三方平台
     */
    void unbindOAuth(Long userId, OAuthProvider provider);
    
    /**
     * 获取用户的所有绑定
     * 
     * @param userId 用户ID
     * @return 绑定列表
     */
    List<UserOAuthBinding> getUserBindings(Long userId);
    
    /**
     * 检查用户是否已绑定某平台
     * 
     * @param userId 用户ID
     * @param provider 第三方平台
     * @return 是否已绑定
     */
    boolean isBindingExists(Long userId, OAuthProvider provider);
    
    /**
     * 根据第三方账号查询用户ID
     * 
     * @param provider 第三方平台
     * @param oauthUserId 第三方用户ID
     * @return 用户ID，未找到返回null
     */
    Long getUserIdByOAuth(OAuthProvider provider, String oauthUserId);
}

