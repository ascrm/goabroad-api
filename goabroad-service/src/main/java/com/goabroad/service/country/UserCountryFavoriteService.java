package com.goabroad.service.country;

import com.goabroad.model.dto.vo.UserVo;

import java.util.List;

/**
 * 用户收藏国家服务接口
 * 
 * @author GoAbroad Team
 * @version 1.0
 * @since 2024-10-19
 */
public interface UserCountryFavoriteService {
    
    /**
     * 收藏国家
     * 
     * @param userId 用户ID
     * @param countryCode 国家代码
     * @param notes 备注
     * @return 收藏记录
     */
    UserVo favoriteCountry(Long userId, String countryCode, String notes);
    
    /**
     * 取消收藏
     * 
     * @param userId 用户ID
     * @param countryCode 国家代码
     */
    void unfavoriteCountry(Long userId, String countryCode);
    
    /**
     * 检查是否已收藏
     * 
     * @param userId 用户ID
     * @param countryCode 国家代码
     * @return 是否已收藏
     */
    boolean isFavorited(Long userId, String countryCode);
    
    /**
     * 获取用户的收藏列表
     * 
     * @param userId 用户ID
     * @return 收藏列表
     */
    List<UserVo> getUserFavorites(Long userId);
    
    /**
     * 更新收藏备注
     * 
     * @param userId 用户ID
     * @param countryCode 国家代码
     * @param notes 新的备注
     * @return 更新后的收藏记录
     */
    UserVo updateFavoriteNotes(Long userId, String countryCode, String notes);
    
    /**
     * 统计国家被收藏次数
     * 
     * @param countryCode 国家代码
     * @return 收藏次数
     */
    long getCountryFavoriteCount(String countryCode);
}

