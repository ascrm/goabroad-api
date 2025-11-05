package com.goabroad.service.community.post.strategy;

import com.goabroad.common.exception.BusinessException;
import com.goabroad.common.pojo.ResultCode;
import com.goabroad.model.community.post.enums.ContentType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 帖子查询策略工厂
 * <p>
 * 根据不同的内容类型（ContentType）返回对应的查询策略
 * 
 * @author GoAbroad Team
 * @version 1.0
 * @since 2024-11-05
 */
@Component
@Slf4j
public class PostQueryStrategyFactory {
    
    /**
     * 策略缓存：key为ContentType，value为对应的策略实现
     */
    private final Map<ContentType, PostQueryStrategy> strategyMap = new ConcurrentHashMap<>();
    
    /**
     * 构造函数：通过依赖注入获取所有策略实现，并注册到缓存中
     * 
     * @param strategies Spring自动注入的所有PostQueryStrategy实现类
     */
    public PostQueryStrategyFactory(List<PostQueryStrategy> strategies) {
        for (PostQueryStrategy strategy : strategies) {
            ContentType contentType = strategy.getSupportedContentType();
            strategyMap.put(contentType, strategy);
            log.info("注册帖子查询策略: {} -> {}", contentType, strategy.getClass().getSimpleName());
        }
        log.info("策略工厂初始化完成，共注册{}个查询策略", strategyMap.size());
    }
    
    /**
     * 根据内容类型获取对应的查询策略
     * 
     * @param contentType 内容类型
     * @return 对应的查询策略
     * @throws BusinessException 如果找不到对应的策略
     */
    public PostQueryStrategy getStrategy(ContentType contentType) {
        PostQueryStrategy strategy = strategyMap.get(contentType);
        if (strategy == null) {
            log.error("未找到ContentType={}对应的查询策略", contentType);
            throw BusinessException.of(ResultCode.PARAM_ERROR, "不支持的内容类型: " + contentType);
        }
        return strategy;
    }
}

