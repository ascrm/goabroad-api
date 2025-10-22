package com.goabroad.repository.mysql;

import com.goabroad.model.entity.UserCountryFavorite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 用户收藏国家Repository接口
 * 
 * @author GoAbroad Team
 * @version 1.0
 * @since 2024-10-19
 */
@Repository
public interface UserCountryFavoriteRepository extends JpaRepository<UserCountryFavorite, Long> {
    
    /**
     * 根据用户ID查询收藏列表
     * 
     * @param userId 用户ID
     * @return 收藏列表
     */
    @Query("SELECT ucf FROM UserCountryFavorite ucf WHERE ucf.userId = :userId ORDER BY ucf.createdAt DESC")
    List<UserCountryFavorite> findByUserIdOrderByCreatedAtDesc(@Param("userId") Long userId);
    
    /**
     * 查询用户是否收藏了某国家
     * 
     * @param userId 用户ID
     * @param countryCode 国家代码
     * @return 收藏记录
     */
    Optional<UserCountryFavorite> findByUserIdAndCountryCode(Long userId, String countryCode);
    
    /**
     * 检查用户是否收藏了某国家
     * 
     * @param userId 用户ID
     * @param countryCode 国家代码
     * @return 是否已收藏
     */
    boolean existsByUserIdAndCountryCode(Long userId, String countryCode);
    
    /**
     * 统计用户的收藏数量
     * 
     * @param userId 用户ID
     * @return 收藏数量
     */
    long countByUserId(Long userId);
    
    /**
     * 统计国家被收藏的次数
     * 
     * @param countryCode 国家代码
     * @return 收藏次数
     */
    long countByCountryCode(String countryCode);
    
    /**
     * 取消收藏
     * 
     * @param userId 用户ID
     * @param countryCode 国家代码
     */
    void deleteByUserIdAndCountryCode(Long userId, String countryCode);
}

