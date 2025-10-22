package com.goabroad.service.planning.impl;

import com.goabroad.model.dto.MaterialDTO;
import com.goabroad.model.entity.MaterialChecklist;
import com.goabroad.model.entity.MaterialFile;
import com.goabroad.service.planning.MaterialService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 材料清单服务实现
 * 
 * @author GoAbroad Team
 * @version 1.0
 * @since 2024-10-19
 */
@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class MaterialServiceImpl implements MaterialService {
    @Override
    public MaterialChecklist createMaterialItem(Long planId, MaterialDTO dto) {
        return null;
    }

    @Override
    public MaterialChecklist updateMaterialItem(Long checklistId, MaterialDTO dto) {
        return null;
    }

    @Override
    public void deleteMaterialItem(Long checklistId) {

    }

    @Override
    public List<MaterialChecklist> getPlanMaterials(Long planId) {
        return List.of();
    }

    @Override
    public void markMaterialCompleted(Long checklistId) {

    }

    @Override
    public MaterialFile uploadMaterialFile(Long checklistId, String fileName, String fileUrl, Long fileSize) {
        return null;
    }

    @Override
    public void deleteMaterialFile(Long fileId) {

    }

    @Override
    public List<MaterialFile> getMaterialFiles(Long checklistId) {
        return List.of();
    }

    @Override
    public void generateDefaultMaterials(Long planId) {

    }

    // TODO: 注入依赖
    

}
