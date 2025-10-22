package com.goabroad.model.entity;

import lombok.*;

import jakarta.persistence.*;
import java.math.BigDecimal;

/**
 * GPA转换记录实体类
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
@Table(name = "gpa_conversions", indexes = {
    @Index(name = "idx_user_id", columnList = "user_id"),
    @Index(name = "idx_deleted", columnList = "deleted")
})
public class GpaConversion extends BaseEntity {
    
    /**
     * 用户ID
     */
    @Column(name = "user_id")
    private Long userId;
    
    /**
     * 源系统（china_4.0/china_100/uk等）
     */
    @Column(name = "from_system", nullable = false, length = 50)
    private String fromSystem;
    
    /**
     * 目标系统
     */
    @Column(name = "to_system", nullable = false, length = 50)
    private String toSystem;
    
    /**
     * 输入值
     */
    @Column(name = "input_value", nullable = false, precision = 5, scale = 2)
    private BigDecimal inputValue;
    
    /**
     * 输出值
     */
    @Column(name = "output_value", nullable = false, precision = 5, scale = 2)
    private BigDecimal outputValue;
}

