package com.goabroad.repository.mysql;

import com.goabroad.model.entity.PostTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 帖子标签关联Repository接口
 * 
 * @author GoAbroad Team
 * @version 1.0
 * @since 2024-10-19
 */
@Repository
public interface PostTagRepository extends JpaRepository<PostTag, Long> {
    
    /**
     * 根据帖子ID查询标签关联
     * 
     * @param postId 帖子ID
     * @return 标签关联列表
     */
    List<PostTag> findByPostId(Long postId);
    
    /**
     * 根据标签ID查询帖子关联
     * 
     * @param tagId 标签ID
     * @return 帖子关联列表
     */
    List<PostTag> findByTagId(Long tagId);
    
    /**
     * 检查帖子和标签的关联是否存在
     * 
     * @param postId 帖子ID
     * @param tagId 标签ID
     * @return 是否存在
     */
    boolean existsByPostIdAndTagId(Long postId, Long tagId);
    
    /**
     * 查询帖子的标签ID列表
     * 
     * @param postId 帖子ID
     * @return 标签ID列表
     */
    @Query("SELECT pt.tagId FROM PostTag pt WHERE pt.postId = :postId")
    List<Long> findTagIdsByPostId(@Param("postId") Long postId);
    
    /**
     * 删除帖子的所有标签关联
     * 
     * @param postId 帖子ID
     */
    void deleteByPostId(Long postId);
    
    /**
     * 删除标签的所有帖子关联
     * 
     * @param tagId 标签ID
     */
    void deleteByTagId(Long tagId);
    
    /**
     * 删除指定的帖子标签关联
     * 
     * @param postId 帖子ID
     * @param tagId 标签ID
     */
    void deleteByPostIdAndTagId(Long postId, Long tagId);
}

