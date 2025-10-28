package com.goabroad.service.community.post.repository;

import com.goabroad.model.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 帖子数据访问接口
 * 
 * @author GoAbroad Team
 * @version 1.0
 * @since 2024-10-28
 */
@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    
    /**
     * 根据ID和deleted状态查询帖子
     * 
     * @param id 帖子ID
     * @param deleted 是否删除
     * @return 帖子实体
     */
    Post findByIdAndDeleted(Long id, Boolean deleted);
}

