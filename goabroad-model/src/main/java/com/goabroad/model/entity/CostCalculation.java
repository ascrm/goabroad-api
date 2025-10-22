package com.goabroad.model.entity;

import lombok.*;

import jakarta.persistence.*;

/**
 * 费用计算记录实体类
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
@Table(name = "cost_calculations", indexes = {
    @Index(name = "idx_user_id", columnList = "user_id"),
    @Index(name = "idx_country_code", columnList = "country_code"),
    @Index(name = "idx_deleted", columnList = "deleted")
})
public class CostCalculation extends BaseEntity {
    
    /**
     * 用户ID（可为空，游客也可用）
     */
    @Column(name = "user_id")
    private Long userId;
    
    /**
     * 国家代码
     */
    @Column(name = "country_code", nullable = false, length = 2)
    private String countryCode;
    
    /**
     * 输入参数（JSON）
     * 示例：{"school_type":"public","region":"west_coast","tuition":40000}
     */
    @Column(name = "input_params", nullable = false, columnDefinition = "JSON")
    // @JdbcTypeCode(SqlTypes.JSON)
    private String inputParams;
    
    /**
     * 计算结果（JSON）
     * 示例：{"total_per_year":77500,"breakdown":{...}}
     */
    @Column(name = "result", nullable = false, columnDefinition = "JSON")
    // @JdbcTypeCode(SqlTypes.JSON)
    private String result;
    
    /**
     * 方案名称（用户自定义）
     */
    @Column(name = "scheme_name", length = 100)
    private String schemeName;
    
    /**
     * 是否保存
     */
    @Column(name = "is_saved")
    @Builder.Default
    private Boolean isSaved = false;
}

