package com.goabroad.service.user.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.goabroad.common.exception.BusinessException;
import com.goabroad.common.pojo.PageResult;
import com.goabroad.common.pojo.Pagination;
import com.goabroad.common.pojo.ResultCode;
import com.goabroad.model.converter.UserMapper;
import com.goabroad.model.dto.UpdateUserProfileDto;
import com.goabroad.model.entity.User;
import com.goabroad.model.entity.UserFollow;
import com.goabroad.model.entity.UserPreferences;
import com.goabroad.model.vo.*;
import com.goabroad.service.user.repository.UserFollowRepository;
import com.goabroad.service.user.repository.UserPreferencesRepository;
import com.goabroad.service.user.repository.UserRepository;
import com.goabroad.service.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户服务实现类
 * 
 * @author GoAbroad Team
 * @version 1.0
 * @since 2024-10-25
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    
    private final UserRepository userRepository;
    private final UserFollowRepository userFollowRepository;
    private final UserPreferencesRepository userPreferencesRepository;
    private final UserMapper userMapper;
    
    @Override
    public UserPublicVo getUserPublicProfile(Long userId, Long currentUserId) {
        // 查询用户
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ResultCode.USER_NOT_FOUND));
        
        // 转换为VO
        UserPublicVo vo = userMapper.toUserPublicVo(user);
        
        // 设置统计信息
        UserPublicVo.UserStats stats = UserPublicVo.UserStats.builder()
                .postsCount(user.getPostCount())
                .followersCount(user.getFollowerCount())
                .followingCount(user.getFollowingCount())
                .likesCount(0) // TODO: 从帖子表统计获赞数
                .build();
        vo.setStats(stats);
        
        // 设置徽章（示例）
        List<String> badges = new ArrayList<>();
        if (user.getLevel() >= 1) {
            badges.add("新人");
        }
        if (user.getLevel() >= 3) {
            badges.add("探索者");
        }
        if (user.getPostCount() >= 10) {
            badges.add("热心助人");
        }
        vo.setBadges(badges);
        
        // 查询用户偏好获取目标国家
        userPreferencesRepository.findByUserIdAndDeletedFalse(userId)
                .ifPresent(prefs -> vo.setTargetCountry(prefs.getTargetType()));
        
        // 判断当前用户是否关注该用户
        if (currentUserId != null && !currentUserId.equals(userId)) {
            boolean isFollowing = userFollowRepository.existsByFollowerIdAndFolloweeIdAndDeletedFalse(
                    currentUserId, userId);
            vo.setIsFollowing(isFollowing);
        } else {
            vo.setIsFollowing(false);
        }
        
        return vo;
    }
    
    @Override
    public UserProfileVo getCurrentUserProfile(Long userId) {
        // 查询用户
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ResultCode.USER_NOT_FOUND));
        
        // 转换为VO
        UserProfileVo vo = userMapper.toUserProfileVo(user);
        
        // 查询用户偏好设置
        userPreferencesRepository.findByUserIdAndDeletedFalse(userId)
                .ifPresent(prefs -> {
                    vo.setTargetCountry(prefs.getTargetType());
                    vo.setTargetType(prefs.getTargetType());
                    vo.setTargetDate(prefs.getTargetDepartureDate());
                    vo.setCurrentStatus(prefs.getCurrentStage());
                });
        
        return vo;
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserProfileVo updateUserProfile(Long userId, UpdateUserProfileDto dto) {
        // 查询现有用户
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ResultCode.USER_NOT_FOUND));
        
        // 更新用户基本信息（使用MapStruct，只更新非null字段）
        userMapper.updateUserFromDto(dto, user);
        userRepository.save(user);
        
        // 更新或创建用户偏好设置
        UserPreferences preferences = userPreferencesRepository.findByUserIdAndDeletedFalse(userId)
                .orElseGet(() -> UserPreferences.builder()
                        .userId(userId)
                        .build());
        
        if (dto.getTargetCountry() != null) {
            preferences.setTargetCountries(dto.getTargetCountry());
        }
        if (dto.getTargetType() != null) {
            preferences.setTargetType(dto.getTargetType());
        }
        if (dto.getTargetDate() != null) {
            preferences.setTargetDepartureDate(dto.getTargetDate());
        }
        if (dto.getCurrentStatus() != null) {
            preferences.setCurrentStage(dto.getCurrentStatus());
        }
        
        userPreferencesRepository.save(preferences);
        
        // 返回更新后的用户资料
        return getCurrentUserProfile(userId);
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public FollowVo followUser(Long followerId, Long followeeId) {
        // 检查参数
        if (followerId.equals(followeeId)) {
            throw new BusinessException(ResultCode.USER_CANNOT_FOLLOW_SELF);
        }
        
        // 检查被关注用户是否存在
        User followee = userRepository.findById(followeeId)
                .orElseThrow(() -> new BusinessException(ResultCode.USER_NOT_FOUND));
        
        // 检查是否已关注
        if (userFollowRepository.existsByFollowerIdAndFolloweeIdAndDeletedFalse(followerId, followeeId)) {
            throw new BusinessException(ResultCode.USER_ALREADY_FOLLOWED);
        }
        
        // 创建关注关系
        UserFollow userFollow = UserFollow.builder()
                .followerId(followerId)
                .followeeId(followeeId)
                .build();
        userFollowRepository.save(userFollow);
        
        // 更新被关注者的粉丝数
        followee.setFollowerCount(followee.getFollowerCount() + 1);
        userRepository.save(followee);
        
        // 更新关注者的关注数
        User follower = userRepository.findById(followerId)
                .orElseThrow(() -> new BusinessException(ResultCode.USER_NOT_FOUND));
        follower.setFollowingCount(follower.getFollowingCount() + 1);
        userRepository.save(follower);
        
        log.info("用户关注成功: followerId={}, followeeId={}", followerId, followeeId);
        
        return FollowVo.builder()
                .isFollowing(true)
                .followersCount(followee.getFollowerCount())
                .build();
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public FollowVo unfollowUser(Long followerId, Long followeeId) {
        // 检查参数
        if (followerId.equals(followeeId)) {
            throw new BusinessException(ResultCode.USER_CANNOT_FOLLOW_SELF);
        }
        
        // 查找关注关系
        UserFollow userFollow = userFollowRepository.findByFollowerIdAndFolloweeIdAndDeletedFalse(followerId, followeeId)
                .orElseThrow(() -> new BusinessException(ResultCode.USER_NOT_FOLLOWED));
        
        // 软删除关注关系
        userFollow.setDeleted(true);
        userFollowRepository.save(userFollow);
        
        // 更新被关注者的粉丝数
        User followee = userRepository.findById(followeeId)
                .orElseThrow(() -> new BusinessException(ResultCode.USER_NOT_FOUND));
        followee.setFollowerCount(Math.max(0, followee.getFollowerCount() - 1));
        userRepository.save(followee);
        
        // 更新关注者的关注数
        User follower = userRepository.findById(followerId)
                .orElseThrow(() -> new BusinessException(ResultCode.USER_NOT_FOUND));
        follower.setFollowingCount(Math.max(0, follower.getFollowingCount() - 1));
        userRepository.save(follower);
        
        log.info("取消关注成功: followerId={}, followeeId={}", followerId, followeeId);
        
        return FollowVo.builder()
                .isFollowing(false)
                .followersCount(followee.getFollowerCount())
                .build();
    }
    
    @Override
    public PageResult<UserSimpleVo> getFollowingList(Long userId, Long currentUserId, Pageable pageable) {
        // 查询关注列表
        Page<UserFollow> followPage = userFollowRepository.findByFollowerIdAndDeletedFalse(userId, pageable);
        
        // 获取被关注者ID列表
        List<Long> followeeIds = followPage.getContent().stream()
                .map(UserFollow::getFolloweeId)
                .collect(Collectors.toList());
        
        // 查询被关注者信息
        List<UserSimpleVo> users = new ArrayList<>();
        if (CollUtil.isNotEmpty(followeeIds)) {
            List<User> userList = userRepository.findAllById(followeeIds);
            
            // 批量查询当前用户的关注状态
            List<Long> followedIds = new ArrayList<>();
            if (currentUserId != null) {
                followedIds = userFollowRepository.findFollowedUserIds(currentUserId, followeeIds);
            }
            
            List<Long> finalFollowedIds = followedIds;
            users = userList.stream()
                    .map(user -> {
                        UserSimpleVo vo = userMapper.toUserSimpleVo(user);
                        vo.setIsFollowing(finalFollowedIds.contains(user.getId()));
                        return vo;
                    })
                    .collect(Collectors.toList());
        }
        
        // 构建分页结果
        Pagination pagination = Pagination.builder()
                .currentPage(followPage.getNumber() + 1)
                .pageSize(followPage.getSize())
                .totalItems(followPage.getTotalElements())
                .totalPages(followPage.getTotalPages())
                .hasNext(followPage.hasNext())
                .hasPrevious(followPage.hasPrevious())
                .isFirstPage(followPage.isFirst())
                .isLastPage(followPage.isLast())
                .build();
        
        return PageResult.<UserSimpleVo>builder()
                .items(users)
                .pagination(pagination)
                .build();
    }
    
    @Override
    public PageResult<UserSimpleVo> getFollowersList(Long userId, Long currentUserId, Pageable pageable) {
        // 查询粉丝列表
        Page<UserFollow> followPage = userFollowRepository.findByFolloweeIdAndDeletedFalse(userId, pageable);
        
        // 获取关注者ID列表
        List<Long> followerIds = followPage.getContent().stream()
                .map(UserFollow::getFollowerId)
                .collect(Collectors.toList());
        
        // 查询关注者信息
        List<UserSimpleVo> users = new ArrayList<>();
        if (CollUtil.isNotEmpty(followerIds)) {
            List<User> userList = userRepository.findAllById(followerIds);
            
            // 批量查询当前用户的关注状态
            List<Long> followedIds = new ArrayList<>();
            if (currentUserId != null) {
                followedIds = userFollowRepository.findFollowedUserIds(currentUserId, followerIds);
            }
            
            List<Long> finalFollowedIds = followedIds;
            users = userList.stream()
                    .map(user -> {
                        UserSimpleVo vo = userMapper.toUserSimpleVo(user);
                        vo.setIsFollowing(finalFollowedIds.contains(user.getId()));
                        return vo;
                    })
                    .collect(Collectors.toList());
        }
        
        // 构建分页结果
        Pagination pagination = Pagination.builder()
                .currentPage(followPage.getNumber() + 1)
                .pageSize(followPage.getSize())
                .totalItems(followPage.getTotalElements())
                .totalPages(followPage.getTotalPages())
                .hasNext(followPage.hasNext())
                .hasPrevious(followPage.hasPrevious())
                .isFirstPage(followPage.isFirst())
                .isLastPage(followPage.isLast())
                .build();
        
        return PageResult.<UserSimpleVo>builder()
                .items(users)
                .pagination(pagination)
                .build();
    }
    
    @Override
    public boolean isFollowing(Long followerId, Long followeeId) {
        return userFollowRepository.existsByFollowerIdAndFolloweeIdAndDeletedFalse(followerId, followeeId);
    }
    
    @Override
    public PageResult<PostSimpleVo> getUserPosts(Long userId, Long currentUserId, String type, Pageable pageable) {
        // TODO: 实现获取用户帖子列表（需要帖子模块支持）
        // 1. 查询用户是否存在
        userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ResultCode.USER_NOT_FOUND));
        
        // 2. 返回空列表（待帖子模块实现）
        log.warn("获取用户帖子列表功能待实现: userId={}, type={}", userId, type);
        
        Pagination pagination = Pagination.builder()
                .currentPage(pageable.getPageNumber() + 1)
                .pageSize(pageable.getPageSize())
                .totalItems(0L)
                .totalPages(0)
                .hasNext(false)
                .hasPrevious(false)
                .isFirstPage(true)
                .isLastPage(true)
                .build();
        
        return PageResult.<PostSimpleVo>builder()
                .items(new ArrayList<>())
                .pagination(pagination)
                .build();
    }
    
    @Override
    public PageResult<PostSimpleVo> getUserFavorites(Long userId, Pageable pageable) {
        // TODO: 实现获取用户收藏列表（需要帖子模块支持）
        // 1. 查询用户是否存在
        userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ResultCode.USER_NOT_FOUND));
        
        // 2. 返回空列表（待帖子模块实现）
        log.warn("获取用户收藏列表功能待实现: userId={}", userId);
        
        Pagination pagination = Pagination.builder()
                .currentPage(pageable.getPageNumber() + 1)
                .pageSize(pageable.getPageSize())
                .totalItems(0L)
                .totalPages(0)
                .hasNext(false)
                .hasPrevious(false)
                .isFirstPage(true)
                .isLastPage(true)
                .build();
        
        return PageResult.<PostSimpleVo>builder()
                .items(new ArrayList<>())
                .pagination(pagination)
                .build();
    }
}

