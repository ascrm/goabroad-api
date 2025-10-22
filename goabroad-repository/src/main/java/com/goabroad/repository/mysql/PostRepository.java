package com.goabroad.repository.mysql;

import com.goabroad.model.entity.Post;
import com.goabroad.model.enums.ContentType;
import com.goabroad.model.enums.PostStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 帖子Repository接口
 * 
 * @author GoAbroad Team
 * @version 1.0
 * @since 2024-10-19
 */
@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    
    /**
     * 根据作者ID查询帖子列表
     * 
     * @param authorId 作者ID
     * @param pageable 分页参数
     * @return 帖子列表
     */
    Page<Post> findByAuthorId(Long authorId, Pageable pageable);
    
    /**
     * 根据状态查询帖子列表
     * 
     * @param status 帖子状态
     * @param pageable 分页参数
     * @return 帖子列表
     */
    Page<Post> findByStatus(PostStatus status, Pageable pageable);
    
    /**
     * 根据内容类型查询帖子列表
     * 
     * @param contentType 内容类型
     * @param pageable 分页参数
     * @return 帖子列表
     */
    Page<Post> findByContentType(ContentType contentType, Pageable pageable);
    
    /**
     * 根据分类查询帖子列表
     * 
     * @param category 分类
     * @param pageable 分页参数
     * @return 帖子列表
     */
    Page<Post> findByCategory(String category, Pageable pageable);
    
    /**
     * 根据国家代码查询帖子列表
     * 
     * @param countryCode 国家代码
     * @param pageable 分页参数
     * @return 帖子列表
     */
    Page<Post> findByCountryCode(String countryCode, Pageable pageable);
    
    /**
     * 查询精选帖子
     * 
     * @param pageable 分页参数
     * @return 帖子列表
     */
    @Query("SELECT p FROM Post p WHERE p.isFeatured = true AND p.status = 'PUBLISHED' ORDER BY p.createdAt DESC")
    Page<Post> findFeaturedPosts(Pageable pageable);
    
    /**
     * 查询热门帖子
     * 
     * @param pageable 分页参数
     * @return 帖子列表
     */
    @Query("SELECT p FROM Post p WHERE p.isHot = true AND p.status = 'PUBLISHED' ORDER BY p.viewCount DESC, p.likeCount DESC")
    Page<Post> findHotPosts(Pageable pageable);
    
    /**
     * 根据关键词搜索帖子
     * 
     * @param keyword 关键词
     * @param pageable 分页参数
     * @return 帖子列表
     */
    @Query("SELECT p FROM Post p WHERE p.status = 'PUBLISHED' AND (p.title LIKE %:keyword% OR p.content LIKE %:keyword%) ORDER BY p.createdAt DESC")
    Page<Post> searchPosts(@Param("keyword") String keyword, Pageable pageable);
    
    /**
     * 统计作者的帖子数量
     * 
     * @param authorId 作者ID
     * @return 帖子数量
     */
    long countByAuthorId(Long authorId);
    
    /**
     * 统计指定状态的帖子数量
     * 
     * @param authorId 作者ID
     * @param status 帖子状态
     * @return 帖子数量
     */
    long countByAuthorIdAndStatus(Long authorId, PostStatus status);
    
    /**
     * 增加浏览量
     * 
     * @param id 帖子ID
     */
    @Modifying
    @Query("UPDATE Post p SET p.viewCount = p.viewCount + 1 WHERE p.id = :id")
    void incrementViewCount(@Param("id") Long id);
    
    /**
     * 增加点赞数
     * 
     * @param id 帖子ID
     */
    @Modifying
    @Query("UPDATE Post p SET p.likeCount = p.likeCount + 1 WHERE p.id = :id")
    void incrementLikeCount(@Param("id") Long id);
    
    /**
     * 减少点赞数
     * 
     * @param id 帖子ID
     */
    @Modifying
    @Query("UPDATE Post p SET p.likeCount = p.likeCount - 1 WHERE p.id = :id AND p.likeCount > 0")
    void decrementLikeCount(@Param("id") Long id);
    
    /**
     * 增加评论数
     * 
     * @param id 帖子ID
     */
    @Modifying
    @Query("UPDATE Post p SET p.commentCount = p.commentCount + 1 WHERE p.id = :id")
    void incrementCommentCount(@Param("id") Long id);
    
    /**
     * 减少评论数
     * 
     * @param id 帖子ID
     */
    @Modifying
    @Query("UPDATE Post p SET p.commentCount = p.commentCount - 1 WHERE p.id = :id AND p.commentCount > 0")
    void decrementCommentCount(@Param("id") Long id);
    
    /**
     * 增加收藏数
     * 
     * @param id 帖子ID
     */
    @Modifying
    @Query("UPDATE Post p SET p.collectCount = p.collectCount + 1 WHERE p.id = :id")
    void incrementCollectCount(@Param("id") Long id);
    
    /**
     * 减少收藏数
     * 
     * @param id 帖子ID
     */
    @Modifying
    @Query("UPDATE Post p SET p.collectCount = p.collectCount - 1 WHERE p.id = :id AND p.collectCount > 0")
    void decrementCollectCount(@Param("id") Long id);
}

