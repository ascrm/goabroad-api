package com.goabroad.repository.mysql;

import com.goabroad.model.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 国家信息Repository接口
 * 
 * @author GoAbroad Team
 * @version 1.0
 * @since 2024-10-19
 */
@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {
    
    /**
     * 根据国家代码查询国家信息
     * 
     * @param code 国家代码
     * @return 国家信息
     */
    Optional<Country> findByCode(String code);
    
    /**
     * 根据国家中文名查询国家信息
     * 
     * @param nameZh 国家中文名
     * @return 国家信息
     */
    Optional<Country> findByNameZh(String nameZh);
    
    /**
     * 检查国家代码是否存在
     * 
     * @param code 国家代码
     * @return 是否存在
     */
    boolean existsByCode(String code);
    
    /**
     * 查询热门国家（按热度排序）
     * 
     * @return 热门国家列表
     */
    @Query("SELECT c FROM Country c WHERE c.isActive = true ORDER BY c.sortOrder ASC")
    List<Country> findPopularCountries();
    
    /**
     * 根据关键词搜索国家（国家名称或英文名）
     * 
     * @param keyword 关键词
     * @return 国家列表
     */
    @Query("SELECT c FROM Country c WHERE c.nameZh LIKE %:keyword% OR c.nameEn LIKE %:keyword%")
    List<Country> searchCountries(@Param("keyword") String keyword);
    
    /**
     * 查询所有国家（按排序顺序）
     * 
     * @return 国家列表
     */
    @Query("SELECT c FROM Country c ORDER BY c.sortOrder ASC, c.nameZh ASC")
    List<Country> findAllOrderedBySortOrder();
}

