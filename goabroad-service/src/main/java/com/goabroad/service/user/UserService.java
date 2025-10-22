package com.goabroad.service.user;

import com.goabroad.model.dto.vo.UserVo;
import com.goabroad.model.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * 用户服务接口
 * 
 * @author GoAbroad Team
 * @version 1.0
 * @since 2024-10-19
 */
public interface UserService {
    
    /**
     * 根据ID获取用户信息
     * 
     * @param userId 用户ID
     * @return 用户信息
     */
    UserVo getUserById(Long userId);
    
    /**
     * 根据用户名获取用户信息
     * 
     * @param username 用户名
     * @return 用户信息
     */
    UserVo getUserByUsername(String username);
    
    /**
     * 根据邮箱获取用户信息
     * 
     * @param email 邮箱
     * @return 用户信息
     */
    UserVo getUserByEmail(String email);
    
    /**
     * 更新用户信息
     * 
     * @param userId 用户ID
     * @param request 更新请求
     * @return 更新后的用户信息
     */
    UserVo updateUser(Long userId, User request);
    
    /**
     * 更新用户头像
     * 
     * @param userId 用户ID
     * @param avatarUrl 头像URL
     * @return 更新后的用户信息
     */
    UserVo updateAvatar(Long userId, String avatarUrl);
    
    /**
     * 更新用户密码
     * 
     * @param userId 用户ID
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     */
    void updatePassword(Long userId, String oldPassword, String newPassword);
    
    /**
     * 关注用户
     * 
     * @param userId 用户ID
     * @param followUserId 被关注用户ID
     */
    void followUser(Long userId, Long followUserId);
    
    /**
     * 取消关注
     * 
     * @param userId 用户ID
     * @param followUserId 被关注用户ID
     */
    void unfollowUser(Long userId, Long followUserId);
    
    /**
     * 检查是否关注
     * 
     * @param userId 用户ID
     * @param followUserId 被关注用户ID
     * @return 是否关注
     */
    boolean isFollowing(Long userId, Long followUserId);
    
    /**
     * 获取关注列表
     * 
     * @param userId 用户ID
     * @param pageable 分页参数
     * @return 用户列表
     */
    Page<UserVo> getFollowingList(Long userId, Pageable pageable);
    
    /**
     * 获取粉丝列表
     * 
     * @param userId 用户ID
     * @param pageable 分页参数
     * @return 用户列表
     */
    Page<UserVo> getFollowerList(Long userId, Pageable pageable);
    
    /**
     * 搜索用户
     * 
     * @param keyword 关键词
     * @param pageable 分页参数
     * @return 用户列表
     */
    Page<UserVo> searchUsers(String keyword, Pageable pageable);
    
    /**
     * 获取用户统计信息
     * 
     * @param userId 用户ID
     * @return 统计信息（关注数、粉丝数、帖子数等）
     */
    UserVo getUserStats(Long userId);
    
    /**
     * 禁用用户
     * 
     * @param userId 用户ID
     */
    void disableUser(Long userId);
    
    /**
     * 启用用户
     * 
     * @param userId 用户ID
     */
    void enableUser(Long userId);
}

