package com.goabroad.repository.mysql;

import com.goabroad.model.entity.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * 用户详细资料Repository接口
 * 
 * @author GoAbroad Team
 * @version 1.0
 * @since 2024-10-19
 */
@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {
    
    /**
     * 根据用户ID查询用户资料
     * 
     * @param userId 用户ID
     * @return 用户资料
     */
    Optional<UserProfile> findByUserId(Long userId);
    
    /**
     * 检查用户是否已创建资料
     * 
     * @param userId 用户ID
     * @return 是否存在
     */
    boolean existsByUserId(Long userId);
    
    /**
     * 删除用户资料
     * 
     * @param userId 用户ID
     */
    void deleteByUserId(Long userId);
}

