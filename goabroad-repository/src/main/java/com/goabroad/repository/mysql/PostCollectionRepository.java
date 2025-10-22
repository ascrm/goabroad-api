package com.goabroad.repository.mysql;

import com.goabroad.model.entity.PostCollection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 帖子收藏Repository接口
 * 
 * @author GoAbroad Team
 * @version 1.0
 * @since 2024-10-19
 */
@Repository
public interface PostCollectionRepository extends JpaRepository<PostCollection, Long> {
    
    /**
     * 查询用户是否收藏了帖子
     * 
     * @param postId 帖子ID
     * @param userId 用户ID
     * @return 收藏记录
     */
    Optional<PostCollection> findByPostIdAndUserId(Long postId, Long userId);
    
    /**
     * 检查用户是否收藏了帖子
     * 
     * @param postId 帖子ID
     * @param userId 用户ID
     * @return 是否收藏
     */
    boolean existsByPostIdAndUserId(Long postId, Long userId);
    
    /**
     * 根据用户ID和收藏夹查询收藏列表
     * 
     * @param userId 用户ID
     * @param folder 收藏夹名称
     * @param pageable 分页参数
     * @return 收藏列表
     */
    Page<PostCollection> findByUserIdAndFolder(Long userId, String folder, Pageable pageable);
    
    /**
     * 根据用户ID查询所有收藏
     * 
     * @param userId 用户ID
     * @param pageable 分页参数
     * @return 收藏列表
     */
    Page<PostCollection> findByUserId(Long userId, Pageable pageable);
    
    /**
     * 查询用户的收藏夹列表
     * 
     * @param userId 用户ID
     * @return 收藏夹名称列表
     */
    @Query("SELECT DISTINCT c.folder FROM PostCollection c WHERE c.userId = :userId")
    List<String> findFoldersByUserId(@Param("userId") Long userId);
    
    /**
     * 统计帖子的收藏数
     * 
     * @param postId 帖子ID
     * @return 收藏数
     */
    long countByPostId(Long postId);
    
    /**
     * 统计用户的收藏数
     * 
     * @param userId 用户ID
     * @return 收藏数
     */
    long countByUserId(Long userId);
    
    /**
     * 删除收藏记录
     * 
     * @param postId 帖子ID
     * @param userId 用户ID
     */
    void deleteByPostIdAndUserId(Long postId, Long userId);
    
    /**
     * 删除帖子的所有收藏
     * 
     * @param postId 帖子ID
     */
    void deleteByPostId(Long postId);
}

