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
 * QUESTION（提问题）查询策略
 * <p>
 * 特点：可以根据评论数（答案数）、浏览量等维度排序，
 * 优先展示热门问题或未回答的问题
 * 
 * @author GoAbroad Team
 * @version 1.0
 * @since 2024-11-05
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class QuestionQueryStrategy implements PostQueryStrategy {
    
    private final PostRepository postRepository;
    
    @Override
    public ContentType getSupportedContentType() {
        return ContentType.QUESTION;
    }
    
    @Override
    public Page<Post> queryPosts(Pageable pageable) {
        log.info("执行QUESTION类型帖子查询，分页参数: {}", pageable);
        
        // 构建查询条件：内容类型为QUESTION，已发布，未删除
        Specification<Post> spec = (root, query, cb) -> cb.and(
                cb.equal(root.get("contentType"), ContentType.QUESTION),
                cb.equal(root.get("status"), PostStatus.PUBLISHED),
                cb.equal(root.get("deleted"), false)
        );
        
        // 查询问题列表（可以按评论数、浏览量等排序）
        // 这里默认按发布时间倒序，实际可根据业务需求调整排序规则
        Page<Post> posts = postRepository.findAll(spec, pageable);
        
        log.info("QUESTION类型帖子查询完成，共{}条", posts.getTotalElements());
        return posts;
    }
    
    @Override
    public void processQueryResult(Page<Post> posts) {
        // QUESTION类型：可以标记未回答的问题、热门问题等
        log.debug("处理QUESTION类型查询结果，共{}条记录", posts.getNumberOfElements());
        
        // 示例：可以在这里添加业务逻辑，如统计答案数、标记悬赏问题等
        posts.getContent().forEach(post -> {
            if (post.getCommentCount() == 0) {
                log.debug("问题[{}]暂无回答", post.getId());
            }
        });
    }
}

