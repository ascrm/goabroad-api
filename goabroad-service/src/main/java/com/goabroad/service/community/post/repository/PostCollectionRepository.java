package com.goabroad.service.community.post.repository;

import com.goabroad.model.community.post.entity.PostCollection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 帖子收藏数据访问接口
 * 
 * @author GoAbroad Team
 * @version 1.0
 * @since 2024-10-29
 */
@Repository
public interface PostCollectionRepository extends JpaRepository<PostCollection, Long> {
    
    /**
     * 查询用户是否收藏了指定帖子
     * 
     * @param userId 用户ID
     * @param postId 帖子ID
     * @param deleted 是否删除
     * @return 是否收藏
     */
    boolean existsByUserIdAndPostIdAndDeleted(Long userId, Long postId, Boolean deleted);
    
    /**
     * 批量查询用户收藏的帖子ID列表
     * 
     * @param userId 用户ID
     * @param postIds 帖子ID列表
     * @return 已收藏的帖子ID列表
     */
    @Query("SELECT pc.postId FROM PostCollection pc WHERE pc.userId = :userId AND pc.postId IN :postIds AND pc.deleted = false")
    List<Long> findCollectedPostIds(@Param("userId") Long userId, @Param("postIds") List<Long> postIds);
}

