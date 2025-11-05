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
 * GUIDE（写攻略）查询策略
 * <p>
 * 特点：综合考虑浏览量、点赞数、收藏数等指标，
 * 优先展示热门攻略和精选攻略
 * 
 * @author GoAbroad Team
 * @version 1.0
 * @since 2024-11-05
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class GuideQueryStrategy implements PostQueryStrategy {
    
    private final PostRepository postRepository;
    
    @Override
    public ContentType getSupportedContentType() {
        return ContentType.GUIDE;
    }
    
    @Override
    public Page<Post> queryPosts(Pageable pageable) {
        log.info("执行GUIDE类型帖子查询，分页参数: {}", pageable);
        
        // 构建查询条件：内容类型为GUIDE，已发布，未删除
        // 优先展示精选攻略
        Specification<Post> spec = (root, query, cb) -> cb.and(
                cb.equal(root.get("contentType"), ContentType.GUIDE),
                cb.equal(root.get("status"), PostStatus.PUBLISHED),
                cb.equal(root.get("deleted"), false)
        );
        
        // 查询攻略列表（可以按热度、精选状态等排序）
        Page<Post> posts = postRepository.findAll(spec, pageable);
        
        log.info("GUIDE类型帖子查询完成，共{}条", posts.getTotalElements());
        return posts;
    }
    
    @Override
    public void processQueryResult(Page<Post> posts) {
        // GUIDE类型：可以标记精选攻略、热门攻略等
        log.debug("处理GUIDE类型查询结果，共{}条记录", posts.getNumberOfElements());
        
        // 示例：可以在这里添加业务逻辑，如标记优质攻略
        posts.getContent().forEach(post -> {
            if (post.getIsFeatured()) {
                log.debug("攻略[{}]为精选内容", post.getId());
            }
            if (post.getCollectCount() > 50) {
                log.debug("攻略[{}]收藏数较高：{}次", post.getId(), post.getCollectCount());
            }
        });
    }
}

