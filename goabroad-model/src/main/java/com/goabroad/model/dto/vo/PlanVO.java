package com.goabroad.model.dto.vo;

import com.goabroad.model.enums.PlanStatus;
import com.goabroad.model.enums.PlanType;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 规划视图对象（包含国家名称、进度等）
 * 
 * @author GoAbroad Team
 * @version 1.0
 * @since 2024-10-21
 */
@Data
public class PlanVO {
    
    private Long id;
    private Long userId;
    
    private String planName;
    private PlanType planType;
    
    // 目标信息
    private String countryCode;
    private String countryName;  // 从Country表关联
    private String targetSchool;
    private String targetMajor;
    private LocalDate targetDate;
    
    private String description;
    private PlanStatus status;
    
    // 进度信息
    private Integer progress;  // 0-100
    private Integer totalMilestones;
    private Integer completedMilestones;
    private Integer totalMaterials;
    private Integer completedMaterials;
    
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

