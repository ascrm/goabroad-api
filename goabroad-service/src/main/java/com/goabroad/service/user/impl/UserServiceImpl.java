package com.goabroad.service.user.impl;

import com.goabroad.model.dto.vo.UserVo;
import com.goabroad.model.entity.User;
import com.goabroad.service.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 用户服务实现
 * 
 * @author GoAbroad Team
 * @version 1.0
 * @since 2024-10-19
 */
@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl implements UserService {
    @Override
    public UserVo getUserById(Long userId) {
        return null;
    }

    @Override
    public UserVo getUserByUsername(String username) {
        return null;
    }

    @Override
    public UserVo getUserByEmail(String email) {
        return null;
    }

    @Override
    public UserVo updateUser(Long userId, User request) {
        return null;
    }

    @Override
    public UserVo updateAvatar(Long userId, String avatarUrl) {
        return null;
    }

    @Override
    public void updatePassword(Long userId, String oldPassword, String newPassword) {

    }

    @Override
    public void followUser(Long userId, Long followUserId) {

    }

    @Override
    public void unfollowUser(Long userId, Long followUserId) {

    }

    @Override
    public boolean isFollowing(Long userId, Long followUserId) {
        return false;
    }

    @Override
    public Page<UserVo> getFollowingList(Long userId, Pageable pageable) {
        return null;
    }

    @Override
    public Page<UserVo> getFollowerList(Long userId, Pageable pageable) {
        return null;
    }

    @Override
    public Page<UserVo> searchUsers(String keyword, Pageable pageable) {
        return null;
    }

    @Override
    public UserVo getUserStats(Long userId) {
        return null;
    }

    @Override
    public void disableUser(Long userId) {

    }

    @Override
    public void enableUser(Long userId) {

    }

    // TODO: 注入依赖

}

