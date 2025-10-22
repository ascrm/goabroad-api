package com.goabroad.service.country;

import com.goabroad.model.dto.response.CountryResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 国家信息服务接口
 * 
 * @author GoAbroad Team
 * @version 1.0
 * @since 2024-10-19
 */
public interface CountryService {
    
    /**
     * 根据国家代码获取国家信息
     * 
     * @param countryCode 国家代码
     * @return 国家信息
     */
    CountryResponse getCountryByCode(String countryCode);
    
    /**
     * 获取所有国家列表
     * 
     * @return 国家列表
     */
    List<CountryResponse> getAllCountries();
    
    /**
     * 分页查询国家列表
     * 
     * @param pageable 分页参数
     * @return 国家列表
     */
    Page<CountryResponse> getCountries(Pageable pageable);
    
    /**
     * 获取热门国家列表
     * 
     * @param limit 数量限制
     * @return 热门国家列表
     */
    List<CountryResponse> getPopularCountries(Integer limit);
    
    /**
     * 搜索国家（按中文名或英文名）
     * 
     * @param keyword 关键词
     * @return 国家列表
     */
    List<CountryResponse> searchCountries(String keyword);
    
    /**
     * 根据中文名查询国家
     * 
     * @param nameZh 中文名
     * @return 国家信息
     */
    CountryResponse getCountryByNameZh(String nameZh);
    
    /**
     * 根据英文名查询国家
     * 
     * @param nameEn 英文名
     * @return 国家信息
     */
    CountryResponse getCountryByNameEn(String nameEn);
    
    /**
     * 获取国家统计信息
     * 
     * @param countryCode 国家代码
     * @return 统计信息（收藏数、帖子数、规划数等）
     */
    CountryResponse getCountryStats(String countryCode);
}

