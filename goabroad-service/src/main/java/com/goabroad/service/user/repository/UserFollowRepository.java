package com.goabroad.service.user.repository;

import com.goabroad.model.entity.UserFollow;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 用户关注关系数据访问层
 * 
 * @author GoAbroad Team
 * @version 1.0
 * @since 2024-10-25
 */
@Repository
public interface UserFollowRepository extends JpaRepository<UserFollow, Long> {
    
    /**
     * 查询关注关系
     * 
     * @param followerId 关注者ID
     * @param followeeId 被关注者ID
     * @return 关注关系
     */
    Optional<UserFollow> findByFollowerIdAndFolloweeIdAndDeletedFalse(Long followerId, Long followeeId);
    
    /**
     * 判断是否已关注
     * 
     * @param followerId 关注者ID
     * @param followeeId 被关注者ID
     * @return true-已关注，false-未关注
     */
    boolean existsByFollowerIdAndFolloweeIdAndDeletedFalse(Long followerId, Long followeeId);
    
    /**
     * 获取用户的关注列表
     * 
     * @param followerId 关注者ID
     * @param pageable 分页参数
     * @return 关注列表
     */
    Page<UserFollow> findByFollowerIdAndDeletedFalse(Long followerId, Pageable pageable);
    
    /**
     * 获取用户的粉丝列表
     * 
     * @param followeeId 被关注者ID
     * @param pageable 分页参数
     * @return 粉丝列表
     */
    Page<UserFollow> findByFolloweeIdAndDeletedFalse(Long followeeId, Pageable pageable);
    
    /**
     * 统计用户的关注数
     * 
     * @param followerId 关注者ID
     * @return 关注数
     */
    long countByFollowerIdAndDeletedFalse(Long followerId);
    
    /**
     * 统计用户的粉丝数
     * 
     * @param followeeId 被关注者ID
     * @return 粉丝数
     */
    long countByFolloweeIdAndDeletedFalse(Long followeeId);
    
    /**
     * 批量查询关注状态
     * 
     * @param followerId 关注者ID
     * @param followeeIds 被关注者ID列表
     * @return 已关注的用户ID列表
     */
    @Query("SELECT f.followeeId FROM UserFollow f WHERE f.followerId = :followerId AND f.followeeId IN :followeeIds AND f.deleted = false")
    List<Long> findFollowedUserIds(@Param("followerId") Long followerId, @Param("followeeIds") List<Long> followeeIds);
}

