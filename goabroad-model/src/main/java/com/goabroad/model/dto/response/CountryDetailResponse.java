package com.goabroad.model.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 国家详细信息响应DTO
 * 
 * @author GoAbroad Team
 * @version 1.0
 * @since 2024-10-19
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CountryDetailResponse implements Serializable {
    
    @Serial
    private static final long serialVersionUID = 1L;
    
    /**
     * 国家ID
     */
    private Long id;
    
    /**
     * 国家代码
     */
    private String code;
    
    /**
     * 国家中文名
     */
    private String nameZh;
    
    /**
     * 国家英文名
     */
    private String nameEn;
    
    /**
     * 国旗图标URL
     */
    private String flagUrl;
    
    /**
     * 首都
     */
    private String capital;
    
    /**
     * 官方语言
     */
    private String officialLanguages;
    
    /**
     * 货币代码
     */
    private String currencyCode;
    
    /**
     * 货币符号
     */
    private String currencySymbol;
    
    /**
     * 时区
     */
    private String timezone;
    
    /**
     * 国家简介
     */
    private String description;
    
    /**
     * 留学信息
     */
    private String studyInfo;
    
    /**
     * 工作信息
     */
    private String workInfo;
    
    /**
     * 生活信息
     */
    private String livingInfo;
    
    /**
     * 是否热门国家
     */
    private Boolean isPopular;
    
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

