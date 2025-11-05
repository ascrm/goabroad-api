package com.goabroad.service.community.post.repository;

import com.goabroad.model.community.post.entity.Post;
import com.goabroad.model.community.post.enums.ContentType;
import com.goabroad.model.community.post.enums.PostStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * 帖子数据访问接口
 * 
 * @author GoAbroad Team
 * @version 1.0
 * @since 2024-10-28
 */
@Repository
public interface PostRepository extends JpaRepository<Post, Long>, JpaSpecificationExecutor<Post> {
    
    /**
     * 根据ID和deleted状态查询帖子
     * 
     * @param id 帖子ID
     * @param deleted 是否删除
     * @return 帖子实体
     */
    Post findByIdAndDeleted(Long id, Boolean deleted);
    
    /**
     * 查询用户所有已发布的帖子（分页）
     * 
     * @param authorId 作者ID
     * @param status 帖子状态
     * @param deleted 是否删除
     * @param pageable 分页参数
     * @return 帖子分页结果
     */
    Page<Post> findByAuthorIdAndStatusAndDeletedOrderByCreatedAtDesc(
            Long authorId, PostStatus status, Boolean deleted, Pageable pageable);
    
    /**
     * 查询用户指定类型的已发布帖子（分页）
     * 
     * @param authorId 作者ID
     * @param contentType 内容类型
     * @param status 帖子状态
     * @param deleted 是否删除
     * @param pageable 分页参数
     * @return 帖子分页结果
     */
    Page<Post> findByAuthorIdAndContentTypeAndStatusAndDeletedOrderByCreatedAtDesc(
            Long authorId, ContentType contentType, PostStatus status, Boolean deleted, Pageable pageable);
}

