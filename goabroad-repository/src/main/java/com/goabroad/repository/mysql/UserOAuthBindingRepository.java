package com.goabroad.repository.mysql;

import com.goabroad.model.entity.UserOAuthBinding;
import com.goabroad.model.enums.OAuthProvider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 用户第三方登录绑定Repository接口
 * 
 * @author GoAbroad Team
 * @version 1.0
 * @since 2024-10-19
 */
@Repository
public interface UserOAuthBindingRepository extends JpaRepository<UserOAuthBinding, Long> {
    
    /**
     * 根据用户ID查询所有绑定
     * 
     * @param userId 用户ID
     * @return 绑定列表
     */
    List<UserOAuthBinding> findByUserId(Long userId);
    
    /**
     * 根据用户ID和平台查询绑定
     * 
     * @param userId 用户ID
     * @param provider 平台
     * @return 绑定记录
     */
    Optional<UserOAuthBinding> findByUserIdAndProvider(Long userId, OAuthProvider provider);
    
    /**
     * 检查用户是否已绑定某平台
     * 
     * @param userId 用户ID
     * @param provider 平台
     * @return 是否已绑定
     */
    boolean existsByUserIdAndProvider(Long userId, OAuthProvider provider);
    
    /**
     * 删除绑定
     * 
     * @param userId 用户ID
     * @param provider 平台
     */
    void deleteByUserIdAndProvider(Long userId, OAuthProvider provider);
    
    /**
     * 删除用户的所有绑定
     * 
     * @param userId 用户ID
     */
    void deleteByUserId(Long userId);
}

