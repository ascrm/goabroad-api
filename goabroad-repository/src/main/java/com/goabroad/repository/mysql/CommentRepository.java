package com.goabroad.repository.mysql;

import com.goabroad.model.entity.Comment;
import com.goabroad.model.enums.CommentStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 评论Repository接口
 * 
 * @author GoAbroad Team
 * @version 1.0
 * @since 2024-10-19
 */
@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    
    /**
     * 根据帖子ID查询评论列表（分页）
     * 
     * @param postId 帖子ID
     * @param pageable 分页参数
     * @return 评论列表
     */
    Page<Comment> findByPostId(Long postId, Pageable pageable);
    
    /**
     * 根据帖子ID和状态查询评论列表
     * 
     * @param postId 帖子ID
     * @param status 评论状态
     * @param pageable 分页参数
     * @return 评论列表
     */
    Page<Comment> findByPostIdAndStatus(Long postId, CommentStatus status, Pageable pageable);
    
    /**
     * 根据根评论ID查询回复列表
     * 
     * @param rootId 根评论ID
     * @return 回复列表
     */
    @Query("SELECT c FROM Comment c WHERE c.rootId = :rootId AND c.status = 'VISIBLE' ORDER BY c.createdAt ASC")
    List<Comment> findRepliesByRootId(@Param("rootId") Long rootId);
    
    /**
     * 根据父评论ID查询直接回复
     * 
     * @param parentId 父评论ID
     * @return 回复列表
     */
    List<Comment> findByParentId(Long parentId);
    
    /**
     * 根据作者ID查询评论列表
     * 
     * @param authorId 作者ID
     * @param pageable 分页参数
     * @return 评论列表
     */
    Page<Comment> findByAuthorId(Long authorId, Pageable pageable);
    
    /**
     * 统计帖子的评论数量
     * 
     * @param postId 帖子ID
     * @return 评论数量
     */
    long countByPostId(Long postId);
    
    /**
     * 统计帖子指定状态的评论数量
     * 
     * @param postId 帖子ID
     * @param status 评论状态
     * @return 评论数量
     */
    long countByPostIdAndStatus(Long postId, CommentStatus status);
    
    /**
     * 统计用户的评论数量
     * 
     * @param authorId 作者ID
     * @return 评论数量
     */
    long countByAuthorId(Long authorId);
    
    /**
     * 增加点赞数
     * 
     * @param id 评论ID
     */
    @Modifying
    @Query("UPDATE Comment c SET c.likeCount = c.likeCount + 1 WHERE c.id = :id")
    void incrementLikeCount(@Param("id") Long id);
    
    /**
     * 减少点赞数
     * 
     * @param id 评论ID
     */
    @Modifying
    @Query("UPDATE Comment c SET c.likeCount = c.likeCount - 1 WHERE c.id = :id AND c.likeCount > 0")
    void decrementLikeCount(@Param("id") Long id);
    
    /**
     * 增加回复数
     * 
     * @param id 评论ID
     */
    @Modifying
    @Query("UPDATE Comment c SET c.replyCount = c.replyCount + 1 WHERE c.id = :id")
    void incrementReplyCount(@Param("id") Long id);
    
    /**
     * 减少回复数
     * 
     * @param id 评论ID
     */
    @Modifying
    @Query("UPDATE Comment c SET c.replyCount = c.replyCount - 1 WHERE c.id = :id AND c.replyCount > 0")
    void decrementReplyCount(@Param("id") Long id);
    
    /**
     * 删除帖子的所有评论
     * 
     * @param postId 帖子ID
     */
    void deleteByPostId(Long postId);
}

