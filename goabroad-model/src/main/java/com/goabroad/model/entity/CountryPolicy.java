package com.goabroad.model.entity;

import com.goabroad.model.enums.PolicyStatus;
import com.goabroad.model.enums.PolicyType;
import lombok.*;

import jakarta.persistence.*;
import java.time.LocalDate;

/**
 * 国家政策更新实体类
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
@Table(name = "country_policies", indexes = {
    @Index(name = "idx_country_type", columnList = "country_code,policy_type"),
    @Index(name = "idx_effective_date", columnList = "effective_date"),
    @Index(name = "idx_deleted", columnList = "deleted")
})
public class CountryPolicy extends BaseEntity {
    
    /**
     * 国家代码
     */
    @Column(name = "country_code", nullable = false, length = 2)
    private String countryCode;
    
    /**
     * 政策类型
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "policy_type", nullable = false, length = 20)
    private PolicyType policyType;
    
    /**
     * 政策标题
     */
    @Column(name = "title", nullable = false, length = 200)
    private String title;
    
    /**
     * 政策内容（Markdown）
     */
    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    private String content;
    
    /**
     * 生效日期
     */
    @Column(name = "effective_date")
    private LocalDate effectiveDate;
    
    /**
     * 来源链接
     */
    @Column(name = "source_url", length = 500)
    private String sourceUrl;
    
    /**
     * 是否重要更新
     */
    @Column(name = "is_important")
    @Builder.Default
    private Boolean isImportant = false;
    
    /**
     * 状态
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 20)
    @Builder.Default
    private PolicyStatus status = PolicyStatus.ACTIVE;
}

