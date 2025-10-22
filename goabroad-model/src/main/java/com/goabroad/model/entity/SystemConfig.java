package com.goabroad.model.entity;

import com.goabroad.model.enums.ConfigValueType;
import lombok.*;

import jakarta.persistence.*;

/**
 * 系统配置实体类
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
@Table(name = "system_configs", indexes = {
    @Index(name = "uk_config_key", columnList = "config_key", unique = true),
    @Index(name = "idx_deleted", columnList = "deleted")
})
public class SystemConfig extends BaseEntity {
    
    /**
     * 配置键
     */
    @Column(name = "config_key", nullable = false, unique = true, length = 100)
    private String configKey;
    
    /**
     * 配置值
     */
    @Column(name = "config_value", nullable = false, columnDefinition = "TEXT")
    private String configValue;
    
    /**
     * 值类型
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "value_type", length = 20)
    @Builder.Default
    private ConfigValueType valueType = ConfigValueType.STRING;
    
    /**
     * 配置说明
     */
    @Column(name = "description", length = 500)
    private String description;
    
    /**
     * 是否公开（前端可访问）
     */
    @Column(name = "is_public")
    @Builder.Default
    private Boolean isPublic = false;
}

