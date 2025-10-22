package com.goabroad.service.admin;

import com.goabroad.model.dto.SystemConfigDTO;
import com.goabroad.model.dto.response.SystemConfigResponse;

import java.util.List;

/**
 * 系统配置服务接口
 * 
 * @author GoAbroad Team
 * @version 1.0
 * @since 2024-10-19
 */
public interface SystemConfigService {
    
    /**
     * 创建配置项
     * 
     * @param dto 配置DTO
     * @return 配置信息
     */
    SystemConfigResponse createConfig(SystemConfigDTO dto);
    
    /**
     * 更新配置项
     * 
     * @param configKey 配置键
     * @param dto 配置DTO
     * @return 更新后的配置信息
     */
    SystemConfigResponse updateConfig(String configKey, SystemConfigDTO dto);
    
    /**
     * 删除配置项
     * 
     * @param configKey 配置键
     */
    void deleteConfig(String configKey);
    
    /**
     * 根据配置键获取配置
     * 
     * @param configKey 配置键
     * @return 配置信息
     */
    SystemConfigResponse getConfigByKey(String configKey);
    
    /**
     * 获取配置值
     * 
     * @param configKey 配置键
     * @return 配置值
     */
    String getConfigValue(String configKey);
    
    /**
     * 获取配置值（带默认值）
     * 
     * @param configKey 配置键
     * @param defaultValue 默认值
     * @return 配置值
     */
    String getConfigValue(String configKey, String defaultValue);
    
    /**
     * 获取所有配置
     * 
     * @return 配置列表
     */
    List<SystemConfigResponse> getAllConfigs();
    
    /**
     * 获取所有公开配置
     * 
     * @return 公开配置列表
     */
    List<SystemConfigResponse> getPublicConfigs();
    
    /**
     * 搜索配置
     * 
     * @param keyword 关键词
     * @return 配置列表
     */
    List<SystemConfigResponse> searchConfigs(String keyword);
}

