package com.goabroad.service.community.post.service;

import com.goabroad.model.community.post.dto.CreatePostDto;
import com.goabroad.model.community.post.enums.ContentType;
import com.goabroad.model.community.post.vo.PostDetailVo;
import com.goabroad.model.community.post.vo.PostSimpleVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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
    
    /**
     * 根据内容类型查询帖子列表（分页）
     * <p>
     * 使用策略模式，根据不同的 contentType 应用不同的查询策略和业务逻辑
     * 
     * @param contentType 内容类型
     * @param pageable 分页参数
     * @return 帖子简要信息分页结果
     */
    Page<PostSimpleVo> getPostsByContentType(ContentType contentType, Pageable pageable);
}

