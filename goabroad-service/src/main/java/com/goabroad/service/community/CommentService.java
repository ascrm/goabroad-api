package com.goabroad.service.community;

import com.goabroad.model.dto.CommentDTO;
import com.goabroad.model.dto.vo.CommentVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * 评论服务接口
 * 
 * @author GoAbroad Team
 * @version 1.0
 * @since 2024-10-19
 */
public interface CommentService {
    
    /**
     * 创建评论
     * 
     * @param userId 用户ID
     * @param dto 评论DTO
     * @return 评论信息
     */
    CommentVO createComment(Long userId, CommentDTO dto);
    
    /**
     * 删除评论
     * 
     * @param commentId 评论ID
     * @param userId 用户ID
     */
    void deleteComment(Long commentId, Long userId);
    
    /**
     * 获取帖子的评论列表
     * 
     * @param postId 帖子ID
     * @param pageable 分页参数
     * @return 评论列表
     */
    Page<CommentVO> getPostComments(Long postId, Pageable pageable);
    
    /**
     * 获取评论的回复列表
     * 
     * @param parentId 父评论ID
     * @param pageable 分页参数
     * @return 回复列表
     */
    Page<CommentVO> getCommentReplies(Long parentId, Pageable pageable);
    
    /**
     * 获取用户的评论列表
     * 
     * @param userId 用户ID
     * @param pageable 分页参数
     * @return 评论列表
     */
    Page<CommentVO> getUserComments(Long userId, Pageable pageable);
}
