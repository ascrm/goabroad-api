package com.goabroad.repository.mysql;

import com.goabroad.model.entity.UserFollow;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * 用户关注Repository接口
 * 
 * @author GoAbroad Team
 * @version 1.0
 * @since 2024-10-19
 */
@Repository
public interface UserFollowRepository extends JpaRepository<UserFollow, Long> {
    
    /**
     * 查询关注关系
     * 
     * @param followerId 关注者ID
     * @param followeeId 被关注者ID
     * @return 关注记录
     */
    Optional<UserFollow> findByFollowerIdAndFolloweeId(Long followerId, Long followeeId);
    
    /**
     * 检查是否已关注
     * 
     * @param followerId 关注者ID
     * @param followeeId 被关注者ID
     * @return 是否已关注
     */
    boolean existsByFollowerIdAndFolloweeId(Long followerId, Long followeeId);
    
    /**
     * 查询关注列表（我关注的人）
     * 
     * @param followerId 关注者ID
     * @param pageable 分页参数
     * @return 关注列表
     */
    Page<UserFollow> findByFollowerId(Long followerId, Pageable pageable);
    
    /**
     * 查询粉丝列表（关注我的人）
     * 
     * @param followeeId 被关注者ID
     * @param pageable 分页参数
     * @return 粉丝列表
     */
    Page<UserFollow> findByFolloweeId(Long followeeId, Pageable pageable);
    
    /**
     * 统计关注数量
     * 
     * @param followerId 关注者ID
     * @return 关注数量
     */
    long countByFollowerId(Long followerId);
    
    /**
     * 统计粉丝数量
     * 
     * @param followeeId 被关注者ID
     * @return 粉丝数量
     */
    long countByFolloweeId(Long followeeId);
    
    /**
     * 取消关注
     * 
     * @param followerId 关注者ID
     * @param followeeId 被关注者ID
     */
    void deleteByFollowerIdAndFolloweeId(Long followerId, Long followeeId);
}

