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
 * ANSWER（写答案）查询策略
 * <p>
 * 特点：按点赞数排序，优先展示高质量回答
 * 
 * @author GoAbroad Team
 * @version 1.0
 * @since 2024-11-05
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class AnswerQueryStrategy implements PostQueryStrategy {
    
    private final PostRepository postRepository;
    
    @Override
    public ContentType getSupportedContentType() {
        return ContentType.ANSWER;
    }
    
    @Override
    public Page<Post> queryPosts(Pageable pageable) {
        log.info("执行ANSWER类型帖子查询，分页参数: {}", pageable);
        
        // 构建查询条件：内容类型为ANSWER，已发布，未删除
        Specification<Post> spec = (root, query, cb) -> cb.and(
                cb.equal(root.get("contentType"), ContentType.ANSWER),
                cb.equal(root.get("status"), PostStatus.PUBLISHED),
                cb.equal(root.get("deleted"), false)
        );
        
        // 查询答案列表（可以按点赞数排序，展示优质回答）
        Page<Post> posts = postRepository.findAll(spec, pageable);
        
        log.info("ANSWER类型帖子查询完成，共{}条", posts.getTotalElements());
        return posts;
    }
    
    @Override
    public void processQueryResult(Page<Post> posts) {
        // ANSWER类型：可以标记优质答案、被采纳的答案等
        log.debug("处理ANSWER类型查询结果，共{}条记录", posts.getNumberOfElements());
        
        // 示例：可以在这里添加业务逻辑，如标记高赞答案
        posts.getContent().forEach(post -> {
            if (post.getLikeCount() > 100) {
                log.debug("答案[{}]获得高赞：{}个赞", post.getId(), post.getLikeCount());
            }
        });
    }
}

