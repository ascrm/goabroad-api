package com.goabroad.web.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.goabroad.common.pojo.PageResult;
import com.goabroad.common.pojo.Result;
import com.goabroad.model.dto.UpdateUserProfileDto;
import com.goabroad.model.vo.FollowVo;
import com.goabroad.model.vo.UserProfileVo;
import com.goabroad.model.vo.UserPublicVo;
import com.goabroad.model.vo.UserSimpleVo;
import com.goabroad.service.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

/**
 * 用户控制器
 * 
 * @author GoAbroad Team
 * @version 1.0
 * @since 2024-10-25
 */
@RestController
@RequestMapping("/api/users")
@Tag(name = "用户管理", description = "用户相关接口")
@Slf4j
@RequiredArgsConstructor
public class UserController {
    
    private final UserService userService;
    
    /**
     * 获取用户公开资料
     */
    @GetMapping("/{userId}")
    @Operation(summary = "获取用户公开资料", description = "获取指定用户的公开资料")
    public Result<UserPublicVo> getUserPublicProfile(
            @Parameter(description = "用户ID", required = true) @PathVariable Long userId) {
        
        // 获取当前登录用户ID（如果已登录）
        Long currentUserId = null;
        if (StpUtil.isLogin()) {
            currentUserId = StpUtil.getLoginIdAsLong();
        }
        
        UserPublicVo vo = userService.getUserPublicProfile(userId, currentUserId);
        return Result.success(vo);
    }
    
    /**
     * 获取当前用户详细资料
     */
    @GetMapping("/profile")
    @Operation(summary = "获取当前用户详细资料", description = "获取当前登录用户的详细资料")
    public Result<UserProfileVo> getCurrentUserProfile() {
        Long userId = StpUtil.getLoginIdAsLong();
        UserProfileVo vo = userService.getCurrentUserProfile(userId);
        return Result.success(vo);
    }
    
    /**
     * 更新用户资料
     */
    @PutMapping("/profile")
    @Operation(summary = "更新用户资料", description = "更新当前用户的资料")
    public Result<UserProfileVo> updateUserProfile(@Valid @RequestBody UpdateUserProfileDto dto) {
        Long userId = StpUtil.getLoginIdAsLong();
        UserProfileVo vo = userService.updateUserProfile(userId, dto);
        return Result.success("资料更新成功", vo);
    }
    
    /**
     * 关注用户
     */
    @PostMapping("/{userId}/follow")
    @Operation(summary = "关注用户", description = "关注指定用户")
    public Result<FollowVo> followUser(
            @Parameter(description = "用户ID", required = true) @PathVariable Long userId) {
        
        Long currentUserId = StpUtil.getLoginIdAsLong();
        FollowVo vo = userService.followUser(currentUserId, userId);
        return Result.success("关注成功", vo);
    }
    
    /**
     * 取消关注用户
     */
    @DeleteMapping("/{userId}/follow")
    @Operation(summary = "取消关注用户", description = "取消关注指定用户")
    public Result<FollowVo> unfollowUser(
            @Parameter(description = "用户ID", required = true) @PathVariable Long userId) {
        
        Long currentUserId = StpUtil.getLoginIdAsLong();
        FollowVo vo = userService.unfollowUser(currentUserId, userId);
        return Result.success("取消关注成功", vo);
    }
    
    /**
     * 获取关注列表
     */
    @GetMapping("/{userId}/following")
    @Operation(summary = "获取关注列表", description = "获取用户的关注列表")
    public Result<PageResult<UserSimpleVo>> getFollowingList(
            @Parameter(description = "用户ID", required = true) @PathVariable Long userId,
            @Parameter(description = "页码", example = "1") @RequestParam(defaultValue = "1") Integer page,
            @Parameter(description = "每页数量", example = "20") @RequestParam(defaultValue = "20") Integer pageSize) {
        
        // 获取当前登录用户ID（如果已登录）
        Long currentUserId = null;
        if (StpUtil.isLogin()) {
            currentUserId = StpUtil.getLoginIdAsLong();
        }
        
        // 页码从0开始
        Pageable pageable = PageRequest.of(page - 1, pageSize);
        PageResult<UserSimpleVo> result = userService.getFollowingList(userId, currentUserId, pageable);
        return Result.success(result);
    }
    
    /**
     * 获取粉丝列表
     */
    @GetMapping("/{userId}/followers")
    @Operation(summary = "获取粉丝列表", description = "获取用户的粉丝列表")
    public Result<PageResult<UserSimpleVo>> getFollowersList(
            @Parameter(description = "用户ID", required = true) @PathVariable Long userId,
            @Parameter(description = "页码", example = "1") @RequestParam(defaultValue = "1") Integer page,
            @Parameter(description = "每页数量", example = "20") @RequestParam(defaultValue = "20") Integer pageSize) {
        
        // 获取当前登录用户ID（如果已登录）
        Long currentUserId = null;
        if (StpUtil.isLogin()) {
            currentUserId = StpUtil.getLoginIdAsLong();
        }
        
        // 页码从0开始
        Pageable pageable = PageRequest.of(page - 1, pageSize);
        PageResult<UserSimpleVo> result = userService.getFollowersList(userId, currentUserId, pageable);
        return Result.success(result);
    }
}
