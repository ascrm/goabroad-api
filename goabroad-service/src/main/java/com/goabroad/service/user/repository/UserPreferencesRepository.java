package com.goabroad.service.user.repository;

import com.goabroad.model.entity.UserPreferences;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * 用户偏好设置数据访问层
 * 
 * @author GoAbroad Team
 * @version 1.0
 * @since 2024-10-25
 */
@Repository
public interface UserPreferencesRepository extends JpaRepository<UserPreferences, Long> {
    
    /**
     * 根据用户ID查询偏好设置
     * 
     * @param userId 用户ID
     * @return 偏好设置
     */
    Optional<UserPreferences> findByUserIdAndDeletedFalse(Long userId);
    
    /**
     * 判断用户是否已有偏好设置
     * 
     * @param userId 用户ID
     * @return true-存在，false-不存在
     */
    boolean existsByUserIdAndDeletedFalse(Long userId);
}

