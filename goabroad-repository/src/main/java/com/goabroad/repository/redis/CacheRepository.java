package com.goabroad.repository.redis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import jakarta.annotation.Resource;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Redis缓存Repository
 * 封装常用的Redis操作
 * 
 * @author GoAbroad Team
 * @version 1.0
 * @since 2024-10-19
 */
@Repository
@Slf4j
public class CacheRepository {
    
    @Resource
    private RedisTemplate<String, Object> redisTemplate;
    
    // ========== 通用操作 ==========
    
    /**
     * 设置缓存
     * 
     * @param key 键
     * @param value 值
     */
    public void set(String key, Object value) {
        try {
            redisTemplate.opsForValue().set(key, value);
        } catch (Exception e) {
            log.error("Redis set error, key: {}", key, e);
        }
    }
    
    /**
     * 设置缓存（带过期时间）
     * 
     * @param key 键
     * @param value 值
     * @param timeout 过期时间
     * @param unit 时间单位
     */
    public void set(String key, Object value, long timeout, TimeUnit unit) {
        try {
            redisTemplate.opsForValue().set(key, value, timeout, unit);
        } catch (Exception e) {
            log.error("Redis set with timeout error, key: {}", key, e);
        }
    }
    
    /**
     * 获取缓存
     * 
     * @param key 键
     * @return 值
     */
    public Object get(String key) {
        try {
            return redisTemplate.opsForValue().get(key);
        } catch (Exception e) {
            log.error("Redis get error, key: {}", key, e);
            return null;
        }
    }
    
    /**
     * 删除缓存
     * 
     * @param key 键
     * @return 是否成功
     */
    public Boolean delete(String key) {
        try {
            return redisTemplate.delete(key);
        } catch (Exception e) {
            log.error("Redis delete error, key: {}", key, e);
            return false;
        }
    }
    
    /**
     * 批量删除缓存
     * 
     * @param keys 键集合
     * @return 删除数量
     */
    public Long delete(Collection<String> keys) {
        try {
            return redisTemplate.delete(keys);
        } catch (Exception e) {
            log.error("Redis delete batch error", e);
            return 0L;
        }
    }
    
    /**
     * 判断键是否存在
     * 
     * @param key 键
     * @return 是否存在
     */
    public Boolean hasKey(String key) {
        try {
            return redisTemplate.hasKey(key);
        } catch (Exception e) {
            log.error("Redis hasKey error, key: {}", key, e);
            return false;
        }
    }
    
    /**
     * 设置过期时间
     * 
     * @param key 键
     * @param timeout 过期时间
     * @param unit 时间单位
     * @return 是否成功
     */
    public Boolean expire(String key, long timeout, TimeUnit unit) {
        try {
            return redisTemplate.expire(key, timeout, unit);
        } catch (Exception e) {
            log.error("Redis expire error, key: {}", key, e);
            return false;
        }
    }
    
    /**
     * 获取过期时间
     * 
     * @param key 键
     * @param unit 时间单位
     * @return 过期时间
     */
    public Long getExpire(String key, TimeUnit unit) {
        try {
            return redisTemplate.getExpire(key, unit);
        } catch (Exception e) {
            log.error("Redis getExpire error, key: {}", key, e);
            return -1L;
        }
    }
    
    // ========== String操作 ==========
    
    /**
     * 递增
     * 
     * @param key 键
     * @param delta 递增值
     * @return 递增后的值
     */
    public Long increment(String key, long delta) {
        try {
            return redisTemplate.opsForValue().increment(key, delta);
        } catch (Exception e) {
            log.error("Redis increment error, key: {}", key, e);
            return null;
        }
    }
    
    /**
     * 递减
     * 
     * @param key 键
     * @param delta 递减值
     * @return 递减后的值
     */
    public Long decrement(String key, long delta) {
        try {
            return redisTemplate.opsForValue().decrement(key, delta);
        } catch (Exception e) {
            log.error("Redis decrement error, key: {}", key, e);
            return null;
        }
    }
    
    // ========== Hash操作 ==========
    
    /**
     * 获取Hash中的值
     * 
     * @param key 键
     * @param field 字段
     * @return 值
     */
    public Object hGet(String key, String field) {
        try {
            return redisTemplate.opsForHash().get(key, field);
        } catch (Exception e) {
            log.error("Redis hGet error, key: {}, field: {}", key, field, e);
            return null;
        }
    }
    
    /**
     * 设置Hash中的值
     * 
     * @param key 键
     * @param field 字段
     * @param value 值
     */
    public void hSet(String key, String field, Object value) {
        try {
            redisTemplate.opsForHash().put(key, field, value);
        } catch (Exception e) {
            log.error("Redis hSet error, key: {}, field: {}", key, field, e);
        }
    }
    
    /**
     * 获取Hash的所有键值对
     * 
     * @param key 键
     * @return 键值对Map
     */
    public Map<Object, Object> hGetAll(String key) {
        try {
            return redisTemplate.opsForHash().entries(key);
        } catch (Exception e) {
            log.error("Redis hGetAll error, key: {}", key, e);
            return null;
        }
    }
    
    /**
     * 删除Hash中的字段
     * 
     * @param key 键
     * @param fields 字段
     * @return 删除数量
     */
    public Long hDelete(String key, Object... fields) {
        try {
            return redisTemplate.opsForHash().delete(key, fields);
        } catch (Exception e) {
            log.error("Redis hDelete error, key: {}", key, e);
            return 0L;
        }
    }
    
    // ========== Set操作 ==========
    
    /**
     * 向Set中添加元素
     * 
     * @param key 键
     * @param values 值
     * @return 添加数量
     */
    public Long sAdd(String key, Object... values) {
        try {
            return redisTemplate.opsForSet().add(key, values);
        } catch (Exception e) {
            log.error("Redis sAdd error, key: {}", key, e);
            return 0L;
        }
    }
    
    /**
     * 获取Set中的所有元素
     * 
     * @param key 键
     * @return 元素集合
     */
    public Set<Object> sMembers(String key) {
        try {
            return redisTemplate.opsForSet().members(key);
        } catch (Exception e) {
            log.error("Redis sMembers error, key: {}", key, e);
            return null;
        }
    }
    
    /**
     * 判断元素是否在Set中
     * 
     * @param key 键
     * @param value 值
     * @return 是否存在
     */
    public Boolean sIsMember(String key, Object value) {
        try {
            return redisTemplate.opsForSet().isMember(key, value);
        } catch (Exception e) {
            log.error("Redis sIsMember error, key: {}", key, e);
            return false;
        }
    }
    
    /**
     * 从Set中移除元素
     * 
     * @param key 键
     * @param values 值
     * @return 移除数量
     */
    public Long sRemove(String key, Object... values) {
        try {
            return redisTemplate.opsForSet().remove(key, values);
        } catch (Exception e) {
            log.error("Redis sRemove error, key: {}", key, e);
            return 0L;
        }
    }
    
    // ========== List操作 ==========
    
    /**
     * 从List左侧添加元素
     * 
     * @param key 键
     * @param value 值
     * @return List长度
     */
    public Long lLeftPush(String key, Object value) {
        try {
            return redisTemplate.opsForList().leftPush(key, value);
        } catch (Exception e) {
            log.error("Redis lLeftPush error, key: {}", key, e);
            return 0L;
        }
    }
    
    /**
     * 从List右侧添加元素
     * 
     * @param key 键
     * @param value 值
     * @return List长度
     */
    public Long lRightPush(String key, Object value) {
        try {
            return redisTemplate.opsForList().rightPush(key, value);
        } catch (Exception e) {
            log.error("Redis lRightPush error, key: {}", key, e);
            return 0L;
        }
    }
    
    /**
     * 获取List指定范围的元素
     * 
     * @param key 键
     * @param start 开始位置
     * @param end 结束位置
     * @return 元素列表
     */
    public List<Object> lRange(String key, long start, long end) {
        try {
            return redisTemplate.opsForList().range(key, start, end);
        } catch (Exception e) {
            log.error("Redis lRange error, key: {}", key, e);
            return null;
        }
    }
    
    /**
     * 获取List长度
     * 
     * @param key 键
     * @return List长度
     */
    public Long lSize(String key) {
        try {
            return redisTemplate.opsForList().size(key);
        } catch (Exception e) {
            log.error("Redis lSize error, key: {}", key, e);
            return 0L;
        }
    }
    
    // ========== ZSet操作 ==========
    
    /**
     * 向ZSet中添加元素
     * 
     * @param key 键
     * @param value 值
     * @param score 分数
     * @return 是否成功
     */
    public Boolean zAdd(String key, Object value, double score) {
        try {
            return redisTemplate.opsForZSet().add(key, value, score);
        } catch (Exception e) {
            log.error("Redis zAdd error, key: {}", key, e);
            return false;
        }
    }
    
    /**
     * 获取ZSet指定范围的元素（按分数从低到高）
     * 
     * @param key 键
     * @param start 开始位置
     * @param end 结束位置
     * @return 元素集合
     */
    public Set<Object> zRange(String key, long start, long end) {
        try {
            return redisTemplate.opsForZSet().range(key, start, end);
        } catch (Exception e) {
            log.error("Redis zRange error, key: {}", key, e);
            return null;
        }
    }
    
    /**
     * 获取ZSet指定范围的元素（按分数从高到低）
     * 
     * @param key 键
     * @param start 开始位置
     * @param end 结束位置
     * @return 元素集合
     */
    public Set<Object> zReverseRange(String key, long start, long end) {
        try {
            return redisTemplate.opsForZSet().reverseRange(key, start, end);
        } catch (Exception e) {
            log.error("Redis zReverseRange error, key: {}", key, e);
            return null;
        }
    }
    
    /**
     * 移除ZSet中的元素
     * 
     * @param key 键
     * @param values 值
     * @return 移除数量
     */
    public Long zRemove(String key, Object... values) {
        try {
            return redisTemplate.opsForZSet().remove(key, values);
        } catch (Exception e) {
            log.error("Redis zRemove error, key: {}", key, e);
            return 0L;
        }
    }
}

