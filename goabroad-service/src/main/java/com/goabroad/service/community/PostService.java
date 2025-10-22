package com.goabroad.service.community;

import com.goabroad.model.dto.PostDTO;
import com.goabroad.model.dto.vo.PostVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * 帖子服务接口
 * 
 * @author GoAbroad Team
 * @version 1.0
 * @since 2024-10-19
 */
public interface PostService {
    
    /**
     * 创建帖子
     * 
     * @param userId 用户ID
     * @param dto 帖子DTO
     * @return 帖子信息
     */
    PostVO createPost(Long userId, PostDTO dto);
    
    /**
     * 更新帖子
     * 
     * @param postId 帖子ID
     * @param userId 用户ID
     * @param dto 帖子DTO
     * @return 更新后的帖子信息
     */
    PostVO updatePost(Long postId, Long userId, PostDTO dto);
    
    /**
     * 删除帖子
     * 
     * @param postId 帖子ID
     * @param userId 用户ID
     */
    void deletePost(Long postId, Long userId);
    
    /**
     * 获取帖子详情
     * 
     * @param postId 帖子ID
     * @return 帖子详情
     */
    PostVO getPostById(Long postId);
    
    /**
     * 分页获取帖子列表
     * 
     * @param pageable 分页参数
     * @return 帖子列表
     */
    Page<PostVO> getPosts(Pageable pageable);
    
    /**
     * 获取用户的帖子列表
     * 
     * @param userId 用户ID
     * @param pageable 分页参数
     * @return 帖子列表
     */
    Page<PostVO> getUserPosts(Long userId, Pageable pageable);
    
    /**
     * 获取热门帖子
     * 
     * @param pageable 分页参数
     * @return 热门帖子列表
     */
    Page<PostVO> getHotPosts(Pageable pageable);
    
    /**
     * 搜索帖子
     * 
     * @param keyword 关键词
     * @param pageable 分页参数
     * @return 帖子列表
     */
    Page<PostVO> searchPosts(String keyword, Pageable pageable);
    
    /**
     * 点赞帖子
     * 
     * @param postId 帖子ID
     * @param userId 用户ID
     */
    void likePost(Long postId, Long userId);
    
    /**
     * 取消点赞
     * 
     * @param postId 帖子ID
     * @param userId 用户ID
     */
    void unlikePost(Long postId, Long userId);
    
    /**
     * 收藏帖子
     * 
     * @param postId 帖子ID
     * @param userId 用户ID
     */
    void collectPost(Long postId, Long userId);
    
    /**
     * 取消收藏
     * 
     * @param postId 帖子ID
     * @param userId 用户ID
     */
    void uncollectPost(Long postId, Long userId);
    
    /**
     * 获取用户收藏的帖子列表
     * 
     * @param userId 用户ID
     * @param pageable 分页参数
     * @return 帖子列表
     */
    Page<PostVO> getUserCollectedPosts(Long userId, Pageable pageable);
}
