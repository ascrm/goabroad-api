package com.goabroad.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 国家信息实体类（字典表，不使用逻辑删除和乐观锁）
 * 
 * @author GoAbroad Team
 * @version 1.0
 * @since 2024-10-19
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "countries", indexes = {
    @Index(name = "idx_is_active", columnList = "is_active"),
    @Index(name = "idx_sort_popularity", columnList = "sort_order,popularity_score")
}, uniqueConstraints = {
    @UniqueConstraint(name = "uk_code", columnNames = "code")
})
public class Country {
    
    /**
     * 主键ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    /**
     * 国家代码（ISO 3166-1 alpha-2，如：US, UK, CA）
     */
    @Column(name = "code", nullable = false, length = 2)
    private String code;
    
    /**
     * 国家中文名
     */
    @Column(name = "name_zh", nullable = false, length = 50)
    private String nameZh;
    
    /**
     * 国家英文名
     */
    @Column(name = "name_en", nullable = false, length = 50)
    private String nameEn;
    
    /**
     * 国旗emoji 🇺🇸
     */
    @Column(name = "flag_emoji", length = 10)
    private String flagEmoji;
    
    // ========== 概览信息（JSON） ==========
    
    /**
     * 国家概览：{"description":"...","advantages":[],"disadvantages":[]}
     */
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "overview", columnDefinition = "JSONB")
    private String overview;
    
    /**
     * 留学信息：{"education_system":"...","application_process":[],"requirements":{}}
     */
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "study_info", columnDefinition = "JSONB")
    private String studyInfo;
    
    /**
     * 工作信息：{"visa_types":[],"job_market":"...","salary_range":{}}
     */
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "work_info", columnDefinition = "JSONB")
    private String workInfo;
    
    /**
     * 移民信息：{"types":[],"requirements":{},"timeline":"..."}
     */
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "immigration_info", columnDefinition = "JSONB")
    private String immigrationInfo;
    
    /**
     * 生活信息：{"climate":"...","cost_of_living":{},"safety_index":8}
     */
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "living_info", columnDefinition = "JSONB")
    private String livingInfo;
    
    // ========== 费用信息 ==========
    
    /**
     * 学费最低（年，单位：人民币）
     */
    @Column(name = "avg_tuition_min", precision = 10, scale = 2)
    private BigDecimal avgTuitionMin;
    
    /**
     * 学费最高（年）
     */
    @Column(name = "avg_tuition_max", precision = 10, scale = 2)
    private BigDecimal avgTuitionMax;
    
    /**
     * 平均生活费（年）
     */
    @Column(name = "avg_living_cost", precision = 10, scale = 2)
    private BigDecimal avgLivingCost;
    
    // ========== 难度评级 ==========
    
    /**
     * 申请难度 1-10
     */
    @Column(name = "difficulty_rating", nullable = false)
    @Builder.Default
    private Short difficultyRating = 5;
    
    /**
     * 热度分数
     */
    @Column(name = "popularity_score", nullable = false)
    @Builder.Default
    private Integer popularityScore = 0;
    
    // ========== 状态 ==========
    
    /**
     * 是否启用
     */
    @Column(name = "is_active", nullable = false)
    @Builder.Default
    private Boolean isActive = true;
    
    /**
     * 是否推荐
     */
    @Column(name = "is_featured", nullable = false)
    @Builder.Default
    private Boolean isFeatured = false;
    
    /**
     * 排序权重
     */
    @Column(name = "sort_order", nullable = false)
    @Builder.Default
    private Integer sortOrder = 0;
    
    // ========== 统计（冗余） ==========
    
    /**
     * 规划数量
     */
    @Column(name = "plan_count", nullable = false)
    @Builder.Default
    private Integer planCount = 0;
    
    /**
     * 浏览次数
     */
    @Column(name = "view_count", nullable = false)
    @Builder.Default
    private Integer viewCount = 0;
    
    // ========== 时间戳 ==========
    
    /**
     * 创建时间
     */
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    /**
     * 更新时间
     */
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
    
    @PrePersist
    protected void onCreate() {
        createdAt = java.time.LocalDateTime.now();
        updatedAt = java.time.LocalDateTime.now();
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = java.time.LocalDateTime.now();
    }
}
