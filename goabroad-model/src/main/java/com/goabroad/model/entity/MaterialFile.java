package com.goabroad.model.entity;

import lombok.*;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * 材料文件记录实体类
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
@Table(name = "material_files", indexes = {
    @Index(name = "idx_checklist_id", columnList = "checklist_id"),
    @Index(name = "idx_user_id", columnList = "user_id"),
    @Index(name = "idx_deleted", columnList = "deleted")
})
public class MaterialFile extends BaseEntity {
    
    /**
     * 材料清单ID
     */
    @Column(name = "checklist_id", nullable = false)
    private Long checklistId;
    
    /**
     * 用户ID
     */
    @Column(name = "user_id", nullable = false)
    private Long userId;
    
    /**
     * 文件名
     */
    @Column(name = "file_name", nullable = false, length = 255)
    private String fileName;
    
    /**
     * 文件URL（OSS）
     */
    @Column(name = "file_url", nullable = false, length = 500)
    private String fileUrl;
    
    /**
     * 文件类型（pdf/jpg/png）
     */
    @Column(name = "file_type", length = 50)
    private String fileType;
    
    /**
     * 文件大小（字节）
     */
    @Column(name = "file_size")
    private Long fileSize;
    
    /**
     * 上传时间
     */
    @Column(name = "uploaded_at")
    private LocalDateTime uploadedAt;
    
    @PrePersist
    protected void onCreate() {
        if (uploadedAt == null) {
            uploadedAt = LocalDateTime.now();
        }
    }
}

