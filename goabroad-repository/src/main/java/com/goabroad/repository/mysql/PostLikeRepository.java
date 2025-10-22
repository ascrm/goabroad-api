package com.goabroad.repository.mysql;

import com.goabroad.model.entity.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 帖子点赞Repository接口
 * 
 * @author GoAbroad Team
 * @version 1.0
 * @since 2024-10-19
 */
@Repository
public interface PostLikeRepository extends JpaRepository<PostLike, Long> {
    
    /**
     * 查询用户是否点赞了帖子
     * 
     * @param postId 帖子ID
     * @param userId 用户ID
     * @return 点赞记录
     */
    Optional<PostLike> findByPostIdAndUserId(Long postId, Long userId);
    
    /**
     * 检查用户是否点赞了帖子
     * 
     * @param postId 帖子ID
     * @param userId 用户ID
     * @return 是否点赞
     */
    boolean existsByPostIdAndUserId(Long postId, Long userId);
    
    /**
     * 根据用户ID查询点赞列表
     * 
     * @param userId 用户ID
     * @return 点赞列表
     */
    List<PostLike> findByUserId(Long userId);
    
    /**
     * 统计帖子的点赞数
     * 
     * @param postId 帖子ID
     * @return 点赞数
     */
    long countByPostId(Long postId);
    
    /**
     * 删除点赞记录
     * 
     * @param postId 帖子ID
     * @param userId 用户ID
     */
    void deleteByPostIdAndUserId(Long postId, Long userId);
    
    /**
     * 删除帖子的所有点赞
     * 
     * @param postId 帖子ID
     */
    void deleteByPostId(Long postId);
}

