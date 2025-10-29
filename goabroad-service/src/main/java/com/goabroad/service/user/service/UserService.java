package com.goabroad.service.user.service;

import com.goabroad.common.pojo.PageResult;
import com.goabroad.model.community.post.vo.PostSimpleVo;
import com.goabroad.model.user.vo.FollowVo;
import com.goabroad.model.user.dto.UpdateUserProfileDto;
import com.goabroad.model.user.vo.UserProfileVo;
import com.goabroad.model.user.vo.UserPublicVo;
import com.goabroad.model.user.vo.UserSimpleVo;
import org.springframework.data.domain.Pageable;

/**
 * 用户服务接口
 * 
 * @author GoAbroad Team
 * @version 1.0
 * @since 2024-10-25
 */
public interface UserService {
    
    /**
     * 获取用户公开资料
     * 
     * @param userId 用户ID
     * @param currentUserId 当前登录用户ID（可能为null）
     * @return 用户公开资料
     */
    UserPublicVo getUserPublicProfile(Long userId, Long currentUserId);
    
    /**
     * 获取当前用户详细资料
     * 
     * @param userId 用户ID
     * @return 用户详细资料
     */
    UserProfileVo getCurrentUserProfile(Long userId);
    
    /**
     * 更新用户资料
     * 
     * @param userId 用户ID
     * @param dto 更新请求
     * @return 更新后的用户资料
     */
    UserProfileVo updateUserProfile(Long userId, UpdateUserProfileDto dto);
    
    /**
     * 关注用户
     * 
     * @param followerId 关注者ID
     * @param followeeId 被关注者ID
     * @return 关注结果
     */
    FollowVo followUser(Long followerId, Long followeeId);
    
    /**
     * 取消关注用户
     * 
     * @param followerId 关注者ID
     * @param followeeId 被关注者ID
     * @return 取消关注结果
     */
    FollowVo unfollowUser(Long followerId, Long followeeId);
    
    /**
     * 获取用户的关注列表
     * 
     * @param userId 用户ID
     * @param currentUserId 当前登录用户ID（可能为null）
     * @param pageable 分页参数
     * @return 关注列表
     */
    PageResult<UserSimpleVo> getFollowingList(Long userId, Long currentUserId, Pageable pageable);
    
    /**
     * 获取用户的粉丝列表
     * 
     * @param userId 用户ID
     * @param currentUserId 当前登录用户ID（可能为null）
     * @param pageable 分页参数
     * @return 粉丝列表
     */
    PageResult<UserSimpleVo> getFollowersList(Long userId, Long currentUserId, Pageable pageable);
    
    /**
     * 判断是否关注某用户
     * 
     * @param followerId 关注者ID
     * @param followeeId 被关注者ID
     * @return true-已关注，false-未关注
     */
    boolean isFollowing(Long followerId, Long followeeId);
    
    /**
     * 获取用户发布的帖子列表
     * 
     * @param userId 用户ID
     * @param currentUserId 当前登录用户ID（可能为null）
     * @param type 帖子类型（可选）
     * @param pageable 分页参数
     * @return 帖子列表
     */
    PageResult<PostSimpleVo> getUserPosts(Long userId, Long currentUserId, String type, Pageable pageable);
    
    /**
     * 获取用户收藏的帖子列表
     * 
     * @param userId 用户ID
     * @param pageable 分页参数
     * @return 收藏的帖子列表
     */
    PageResult<PostSimpleVo> getUserFavorites(Long userId, Pageable pageable);
}

