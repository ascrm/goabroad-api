package com.goabroad.service.community.post.repository;

import com.goabroad.model.community.post.entity.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 帖子点赞数据访问接口
 * 
 * @author GoAbroad Team
 * @version 1.0
 * @since 2024-10-29
 */
@Repository
public interface PostLikeRepository extends JpaRepository<PostLike, Long> {
    
    /**
     * 查询用户是否点赞了指定帖子
     * 
     * @param userId 用户ID
     * @param postId 帖子ID
     * @param deleted 是否删除
     * @return 是否点赞
     */
    boolean existsByUserIdAndPostIdAndDeleted(Long userId, Long postId, Boolean deleted);
    
    /**
     * 批量查询用户点赞的帖子ID列表
     * 
     * @param userId 用户ID
     * @param postIds 帖子ID列表
     * @return 已点赞的帖子ID列表
     */
    @Query("SELECT pl.postId FROM PostLike pl WHERE pl.userId = :userId AND pl.postId IN :postIds AND pl.deleted = false")
    List<Long> findLikedPostIds(@Param("userId") Long userId, @Param("postIds") List<Long> postIds);
}

