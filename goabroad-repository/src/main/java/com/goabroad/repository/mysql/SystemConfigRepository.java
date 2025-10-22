package com.goabroad.repository.mysql;

import com.goabroad.model.entity.SystemConfig;
import com.goabroad.model.enums.ConfigValueType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 系统配置Repository接口
 * 
 * @author GoAbroad Team
 * @version 1.0
 * @since 2024-10-19
 */
@Repository
public interface SystemConfigRepository extends JpaRepository<SystemConfig, Long> {
    
    /**
     * 根据配置键查询配置
     * 
     * @param configKey 配置键
     * @return 配置对象
     */
    Optional<SystemConfig> findByConfigKey(String configKey);
    
    /**
     * 检查配置键是否存在
     * 
     * @param configKey 配置键
     * @return 是否存在
     */
    boolean existsByConfigKey(String configKey);
    
    /**
     * 查询所有公开配置
     * 
     * @return 公开配置列表
     */
    @Query("SELECT sc FROM SystemConfig sc WHERE sc.isPublic = true")
    List<SystemConfig> findAllPublicConfigs();
    
    /**
     * 根据值类型查询配置列表
     * 
     * @param valueType 值类型
     * @return 配置列表
     */
    List<SystemConfig> findByValueType(ConfigValueType valueType);
    
    /**
     * 根据关键词搜索配置（配置键或说明）
     * 
     * @param keyword 关键词
     * @return 配置列表
     */
    @Query("SELECT sc FROM SystemConfig sc WHERE sc.configKey LIKE %:keyword% OR sc.description LIKE %:keyword%")
    List<SystemConfig> searchConfigs(@Param("keyword") String keyword);
    
    /**
     * 统计公开配置数量
     * 
     * @return 公开配置数量
     */
    long countByIsPublic(Boolean isPublic);
}

