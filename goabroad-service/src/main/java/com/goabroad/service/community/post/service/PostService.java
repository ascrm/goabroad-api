package com.goabroad.service.community.post.service;

import com.goabroad.model.dto.CreatePostDto;
import com.goabroad.model.vo.PostDetailVo;

/**
 * 帖子服务接口
 * 
 * @author GoAbroad Team
 * @version 1.0
 * @since 2024-10-28
 */
public interface PostService {
    
    /**
     * 发布帖子
     * 
     * @param userId 用户ID
     * @param dto 发布帖子请求DTO
     * @return 帖子详情VO
     */
    PostDetailVo createPost(Long userId, CreatePostDto dto);
}

