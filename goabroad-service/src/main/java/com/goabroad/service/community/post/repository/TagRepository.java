package com.goabroad.service.community.post.repository;

import com.goabroad.model.community.tag.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * 标签数据访问接口
 * 
 * @author GoAbroad Team
 * @version 1.0
 * @since 2024-10-29
 */
@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
    
    /**
     * 根据标签名称查找标签
     * 
     * @param name 标签名称
     * @param deleted 是否删除
     * @return 标签实体
     */
    Optional<Tag> findByNameAndDeleted(String name, Boolean deleted);
}

