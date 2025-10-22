package com.goabroad.model.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 国家信息响应DTO
 * 
 * @author GoAbroad Team
 * @version 1.0
 * @since 2024-10-19
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CountryResponse implements Serializable {
    
    @Serial
    private static final long serialVersionUID = 1L;
    
    /**
     * 国家ID
     */
    private Long id;
    
    /**
     * 国家代码 (ISO 3166-1 alpha-2)
     */
    private String code;
    
    /**
     * 中文名称
     */
    private String nameZh;
    
    /**
     * 英文名称
     */
    private String nameEn;
    
    /**
     * 国旗emoji 🇺🇸
     */
    private String flagEmoji;
    
    /**
     * 国家概览（JSON对象）
     */
    private Object overview;
    
    /**
     * 留学信息（JSON对象）
     */
    private Object studyInfo;
    
    /**
     * 工作信息（JSON对象）
     */
    private Object workInfo;
    
    /**
     * 移民信息（JSON对象）
     */
    private Object immigrationInfo;
    
    /**
     * 生活信息（JSON对象）
     */
    private Object livingInfo;
    
    /**
     * 学费最低（年，单位：人民币）
     */
    private BigDecimal avgTuitionMin;
    
    /**
     * 学费最高（年）
     */
    private BigDecimal avgTuitionMax;
    
    /**
     * 平均生活费（年）
     */
    private BigDecimal avgLivingCost;
    
    /**
     * 申请难度 1-10
     */
    private Integer difficultyRating;
    
    /**
     * 热度分数
     */
    private Integer popularityScore;
    
    /**
     * 是否启用
     */
    private Boolean isActive;
    
    /**
     * 是否推荐
     */
    private Boolean isFeatured;
    
    /**
     * 排序权重
     */
    private Integer sortOrder;
    
    /**
     * 规划数量
     */
    private Integer planCount;
    
    /**
     * 浏览次数
     */
    private Integer viewCount;
    
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
    
    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;
}
