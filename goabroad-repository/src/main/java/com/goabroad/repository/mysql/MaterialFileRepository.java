package com.goabroad.repository.mysql;

import com.goabroad.model.entity.MaterialFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 材料文件记录Repository接口
 * 
 * @author GoAbroad Team
 * @version 1.0
 * @since 2024-10-19
 */
@Repository
public interface MaterialFileRepository extends JpaRepository<MaterialFile, Long> {
    
    /**
     * 根据材料清单ID查询文件列表
     * 
     * @param checklistId 材料清单ID
     * @return 文件列表
     */
    @Query("SELECT mf FROM MaterialFile mf WHERE mf.checklistId = :checklistId ORDER BY mf.createdAt DESC")
    List<MaterialFile> findByChecklistIdOrderByCreatedAtDesc(@Param("checklistId") Long checklistId);
    
    /**
     * 统计材料清单的文件数量
     * 
     * @param checklistId 材料清单ID
     * @return 文件数量
     */
    long countByChecklistId(Long checklistId);

    /**
     * 删除材料清单的所有文件
     * 
     * @param checklistId 材料清单ID
     */
    void deleteByChecklistId(Long checklistId);

}

