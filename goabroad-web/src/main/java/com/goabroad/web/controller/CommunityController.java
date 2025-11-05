package com.goabroad.web.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.goabroad.common.exception.BusinessException;
import com.goabroad.common.pojo.Result;
import com.goabroad.common.pojo.ResultCode;
import com.goabroad.model.community.post.dto.CreatePostDto;
import com.goabroad.model.community.post.enums.ContentType;
import com.goabroad.model.community.post.vo.PostDetailVo;
import com.goabroad.model.community.post.vo.PostSimpleVo;
import com.goabroad.service.community.post.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    
    /**
     * 根据内容类型查询帖子列表（分页）
     * <p>
     * 使用工厂-策略模式，根据不同的 contentType 应用不同的查询策略
     */
    @GetMapping("/posts")
    @Operation(summary = "根据内容类型查询帖子列表", 
               description = "根据内容类型（TREND/QUESTION/ANSWER/GUIDE）查询帖子列表，支持分页和排序")
    public Result<Page<PostSimpleVo>> getPostsByContentType(
            @Parameter(description = "内容类型", required = true, example = "GUIDE")
            @RequestParam ContentType contentType,
            
            @Parameter(description = "页码（从0开始）", example = "0")
            @RequestParam(defaultValue = "0") int page,
            
            @Parameter(description = "每页数量", example = "20")
            @RequestParam(defaultValue = "20") int size,
            
            @Parameter(description = "排序字段", example = "createdAt")
            @RequestParam(defaultValue = "createdAt") String sortBy,
            
            @Parameter(description = "排序方向（ASC/DESC）", example = "DESC")
            @RequestParam(defaultValue = "DESC") Sort.Direction direction
    ) {
        log.info("查询帖子列表 - 内容类型: {}, 页码: {}, 每页数量: {}", contentType, page, size);
        
        // 参数验证
        if (contentType == null) {
            throw BusinessException.of(ResultCode.PARAM_ERROR, "内容类型不能为空");
        }
        
        if (page < 0) {
            throw BusinessException.of(ResultCode.PARAM_ERROR, "页码不能小于0");
        }
        
        if (size <= 0 || size > 100) {
            throw BusinessException.of(ResultCode.PARAM_ERROR, "每页数量必须在1-100之间");
        }
        
        // 构建分页参数
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));
        
        // 查询帖子列表
        Page<PostSimpleVo> posts = postService.getPostsByContentType(contentType, pageable);
        
        return Result.success("查询成功", posts);
    }
}

