package com.goabroad.model.dto.common;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * DTO基类
 * 
 * @author GoAbroad Team
 * @version 1.0
 * @since 2024-10-21
 */
@Data
public abstract class BaseDTO {
    
    private Long id;
    
    private LocalDateTime createdAt;
    
    private LocalDateTime updatedAt;
}

