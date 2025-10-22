package com.goabroad.repository.mysql;

import com.goabroad.model.entity.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 标签Repository接口
 * 
 * @author GoAbroad Team
 * @version 1.0
 * @since 2024-10-19
 */
@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
    
    /**
     * 根据标签名称查询标签
     * 
     * @param name 标签名称
     * @return 标签对象
     */
    Optional<Tag> findByName(String name);
    
    /**
     * 根据slug查询标签
     * 
     * @param slug URL友好标识
     * @return 标签对象
     */
    Optional<Tag> findBySlug(String slug);
    
    /**
     * 检查标签名称是否存在
     * 
     * @param name 标签名称
     * @return 是否存在
     */
    boolean existsByName(String name);
    
    /**
     * 查询热门标签（按帖子数排序）
     * 
     * @param pageable 分页参数
     * @return 标签列表
     */
    @Query("SELECT t FROM Tag t ORDER BY t.postCount DESC")
    Page<Tag> findHotTags(Pageable pageable);
    
    /**
     * 根据关键词搜索标签
     * 
     * @param keyword 关键词
     * @return 标签列表
     */
    @Query("SELECT t FROM Tag t WHERE t.name LIKE %:keyword% ORDER BY t.postCount DESC")
    List<Tag> searchTags(@Param("keyword") String keyword);
    
    /**
     * 增加标签的帖子数
     * 
     * @param id 标签ID
     */
    @Modifying
    @Query("UPDATE Tag t SET t.postCount = t.postCount + 1 WHERE t.id = :id")
    void incrementPostCount(@Param("id") Long id);
    
    /**
     * 减少标签的帖子数
     * 
     * @param id 标签ID
     */
    @Modifying
    @Query("UPDATE Tag t SET t.postCount = t.postCount - 1 WHERE t.id = :id AND t.postCount > 0")
    void decrementPostCount(@Param("id") Long id);
}

