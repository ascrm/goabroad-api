package com.goabroad.service.planning;

import com.goabroad.model.dto.MaterialDTO;
import com.goabroad.model.entity.MaterialChecklist;
import com.goabroad.model.entity.MaterialFile;

import java.util.List;

/**
 * 材料清单服务接口
 * 
 * @author GoAbroad Team
 * @version 1.0
 * @since 2024-10-19
 */
public interface MaterialService {
    
    /**
     * 创建材料清单项
     * 
     * @param planId 规划ID
     * @param dto 材料DTO
     * @return 材料清单
     */
    MaterialChecklist createMaterialItem(Long planId, MaterialDTO dto);
    
    /**
     * 更新材料清单项
     * 
     * @param checklistId 清单ID
     * @param dto 材料DTO
     * @return 更新后的材料清单
     */
    MaterialChecklist updateMaterialItem(Long checklistId, MaterialDTO dto);
    
    /**
     * 删除材料清单项
     * 
     * @param checklistId 清单ID
     */
    void deleteMaterialItem(Long checklistId);
    
    /**
     * 获取规划的材料清单
     * 
     * @param planId 规划ID
     * @return 材料清单列表
     */
    List<MaterialChecklist> getPlanMaterials(Long planId);
    
    /**
     * 标记材料为已完成
     * 
     * @param checklistId 清单ID
     */
    void markMaterialCompleted(Long checklistId);
    
    /**
     * 上传材料文件
     * 
     * @param checklistId 清单ID
     * @param fileName 文件名
     * @param fileUrl 文件URL
     * @param fileSize 文件大小
     * @return 文件记录
     */
    MaterialFile uploadMaterialFile(Long checklistId, String fileName, String fileUrl, Long fileSize);
    
    /**
     * 删除材料文件
     * 
     * @param fileId 文件ID
     */
    void deleteMaterialFile(Long fileId);
    
    /**
     * 获取材料的文件列表
     * 
     * @param checklistId 清单ID
     * @return 文件列表
     */
    List<MaterialFile> getMaterialFiles(Long checklistId);
    
    /**
     * 生成默认材料清单（根据规划类型）
     * 
     * @param planId 规划ID
     */
    void generateDefaultMaterials(Long planId);
}

