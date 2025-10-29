package com.goabroad.service.community.post.repository;

import com.goabroad.model.community.post.entity.PostTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 帖子标签关联数据访问接口
 * 
 * @author GoAbroad Team
 * @version 1.0
 * @since 2024-10-29
 */
@Repository
public interface PostTagRepository extends JpaRepository<PostTag, Long> {
    
    /**
     * 批量查询帖子的标签ID
     * 
     * @param postIds 帖子ID列表
     * @return 帖子标签关联列表
     */
    @Query("SELECT pt FROM PostTag pt WHERE pt.postId IN :postIds AND pt.deleted = false")
    List<PostTag> findByPostIdIn(@Param("postIds") List<Long> postIds);
    
    /**
     * 查询单个帖子的标签ID列表
     * 
     * @param postId 帖子ID
     * @return 标签ID列表
     */
    @Query("SELECT pt.tagId FROM PostTag pt WHERE pt.postId = :postId AND pt.deleted = false")
    List<Long> findTagIdsByPostId(@Param("postId") Long postId);
}

