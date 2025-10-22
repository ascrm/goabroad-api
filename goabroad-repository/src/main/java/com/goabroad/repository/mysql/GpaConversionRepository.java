package com.goabroad.repository.mysql;

import com.goabroad.model.entity.GpaConversion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * GPA转换记录Repository接口
 * 
 * @author GoAbroad Team
 * @version 1.0
 * @since 2024-10-19
 */
@Repository
public interface GpaConversionRepository extends JpaRepository<GpaConversion, Long> {
    
    /**
     * 根据用户ID查询转换记录
     * 
     * @param userId 用户ID
     * @param pageable 分页参数
     * @return 转换记录列表
     */
    Page<GpaConversion> findByUserId(Long userId, Pageable pageable);
    
    /**
     * 根据用户ID和转换系统查询记录
     * 
     * @param userId 用户ID
     * @param fromSystem 源系统
     * @param toSystem 目标系统
     * @return 转换记录列表
     */
    List<GpaConversion> findByUserIdAndFromSystemAndToSystem(Long userId, String fromSystem, String toSystem);
    
    /**
     * 统计用户的转换记录数量
     * 
     * @param userId 用户ID
     * @return 记录数量
     */
    long countByUserId(Long userId);
}

