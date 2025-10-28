package com.goabroad.web.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.goabroad.common.pojo.Result;
import com.goabroad.model.dto.CreatePostDto;
import com.goabroad.model.vo.PostDetailVo;
import com.goabroad.service.community.post.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * 社区模块控制器
 * 
 * @author GoAbroad Team
 * @version 1.0
 * @since 2024-10-28
 */
@RestController
@RequestMapping("/api/community")
@Tag(name = "社区模块", description = "社区相关接口：帖子发布、编辑、删除、点赞、收藏、评论等")
@Slf4j
@RequiredArgsConstructor
public class CommunityController {
    
    private final PostService postService;
    
    /**
     * 发布帖子
     */
    @PostMapping("/posts")
    @Operation(summary = "发布帖子", description = "用户发布新帖子")
    public Result<PostDetailVo> createPost(@Valid @RequestBody CreatePostDto dto) {
        // 获取当前登录用户ID
        long userId = StpUtil.getLoginIdAsLong();
        
        // 发布帖子
        PostDetailVo postVo = postService.createPost(userId, dto);
        
        return Result.success("发布成功", postVo);
    }
}

