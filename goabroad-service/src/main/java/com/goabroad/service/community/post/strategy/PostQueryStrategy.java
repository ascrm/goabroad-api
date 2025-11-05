package com.goabroad.service.community.post.strategy;

import com.goabroad.model.community.post.entity.Post;
import com.goabroad.model.community.post.enums.ContentType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * 帖子查询策略接口
 * <p>
 * 根据不同的内容类型（ContentType）提供不同的查询逻辑和业务处理
 * 
 * @author GoAbroad Team
 * @version 1.0
 * @since 2024-11-05
 */
public interface PostQueryStrategy {
    
    /**
     * 获取该策略支持的内容类型
     * 
     * @return 内容类型
     */
    ContentType getSupportedContentType();
    
    /**
     * 根据内容类型查询帖子列表（分页）
     * 
     * @param pageable 分页参数
     * @return 帖子分页结果
     */
    Page<Post> queryPosts(Pageable pageable);
    
    /**
     * 对查询结果进行特定的业务处理
     * <p>
     * 例如：QUESTION类型可能需要统计答案数，GUIDE类型可能需要标记优质攻略等
     * 
     * @param posts 查询到的帖子列表
     */
    default void processQueryResult(Page<Post> posts) {
        // 默认不做额外处理，子类可以覆盖此方法
    }
}

