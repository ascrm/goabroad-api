package com.goabroad.repository.mysql;

import com.goabroad.model.entity.UserPreferences;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 用户偏好设置Repository接口
 * 
 * @author GoAbroad Team
 * @version 1.0
 * @since 2024-10-19
 */
@Repository
public interface UserPreferencesRepository extends JpaRepository<UserPreferences, Long> {
    
    /**
     * 根据用户ID查询用户偏好
     * 
     * @param userId 用户ID
     * @return 用户偏好
     */
    Optional<UserPreferences> findByUserId(Long userId);
    
    /**
     * 根据目标国家代码查询用户列表（用于推送政策更新）
     * 
     * @param countryCode 国家代码
     * @return 用户偏好列表
     */
    List<UserPreferences> findByTargetCountries(String countryCode);
    
    /**
     * 检查用户是否已创建偏好设置
     * 
     * @param userId 用户ID
     * @return 是否存在
     */
    boolean existsByUserId(Long userId);
    
    /**
     * 删除用户偏好设置
     * 
     * @param userId 用户ID
     */
    void deleteByUserId(Long userId);
}

