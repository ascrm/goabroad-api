package com.goabroad.service.community.post.strategy.impl;

import com.goabroad.model.community.post.entity.Post;
import com.goabroad.model.community.post.enums.ContentType;
import com.goabroad.model.community.post.enums.PostStatus;
import com.goabroad.service.community.post.repository.PostRepository;
import com.goabroad.service.community.post.strategy.PostQueryStrategy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

/**
 * TREND（日常生活动态）查询策略
 * <p>
 * 特点：按发布时间倒序，优先展示最新的生活动态
 * 
 * @author GoAbroad Team
 * @version 1.0
 * @since 2024-11-05
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class TrendQueryStrategy implements PostQueryStrategy {
    
    private final PostRepository postRepository;
    
    @Override
    public ContentType getSupportedContentType() {
        return ContentType.TREND;
    }
    
    @Override
    public Page<Post> queryPosts(Pageable pageable) {
        log.info("执行TREND类型帖子查询，分页参数: {}", pageable);
        
        // 构建查询条件：内容类型为TREND，已发布，未删除
        Specification<Post> spec = (root, query, cb) -> cb.and(
                cb.equal(root.get("contentType"), ContentType.TREND),
                cb.equal(root.get("status"), PostStatus.PUBLISHED),
                cb.equal(root.get("deleted"), false)
        );
        
        // 按发布时间倒序查询
        Page<Post> posts = postRepository.findAll(spec, pageable);
        
        log.info("TREND类型帖子查询完成，共{}条", posts.getTotalElements());
        return posts;
    }
    
    @Override
    public void processQueryResult(Page<Post> posts) {
        // TREND类型：可以在这里添加特殊处理，比如标记含有图片的动态
        log.debug("处理TREND类型查询结果，共{}条记录", posts.getNumberOfElements());
    }
}

