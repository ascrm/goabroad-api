package com.goabroad.model.entity;

import com.goabroad.model.enums.EducationLevel;
import lombok.*;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.Year;

/**
 * 用户详细资料实体类
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
@Table(name = "user_profiles", indexes = {
    @Index(name = "uk_user_id", columnList = "user_id", unique = true),
    @Index(name = "idx_deleted", columnList = "deleted")
})
public class UserProfile extends BaseEntity {
    
    /**
     * 用户ID
     */
    @Column(name = "user_id", nullable = false, unique = true)
    private Long userId;
    
    // ========== 教育背景 ==========
    
    /**
     * 学历
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "education_level", length = 20)
    private EducationLevel educationLevel;
    
    /**
     * 专业
     */
    @Column(name = "major", length = 100)
    private String major;
    
    /**
     * 学校
     */
    @Column(name = "school", length = 200)
    private String school;
    
    /**
     * 毕业年份
     */
    @Column(name = "graduation_year")
    private Year graduationYear;
    
    /**
     * GPA (如 3.75)
     */
    @Column(name = "gpa", precision = 4, scale = 2)
    private BigDecimal gpa;
    
    // ========== 语言成绩 ==========
    
    /**
     * 托福分数
     */
    @Column(name = "toefl_score")
    private Integer toeflScore;
    
    /**
     * 雅思分数 (如 7.5)
     */
    @Column(name = "ielts_score", precision = 3, scale = 1)
    private BigDecimal ieltsScore;
    
    /**
     * GRE分数
     */
    @Column(name = "gre_score")
    private Integer greScore;
    
    /**
     * GMAT分数
     */
    @Column(name = "gmat_score")
    private Integer gmatScore;
    
    // ========== 工作经历 ==========
    
    /**
     * 工作年限
     */
    @Column(name = "work_years")
    @Builder.Default
    private Integer workYears = 0;
    
    /**
     * 当前公司
     */
    @Column(name = "current_company", length = 200)
    private String currentCompany;
    
    /**
     * 当前职位
     */
    @Column(name = "current_position", length = 100)
    private String currentPosition;
    
    // ========== 其他信息 ==========
    
    /**
     * 所在城市
     */
    @Column(name = "location", length = 100)
    private String location;
    
    /**
     * 出生年份
     */
    @Column(name = "birth_year")
    private Year birthYear;
}

