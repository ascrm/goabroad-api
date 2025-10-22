package com.goabroad.model.dto.common;

import lombok.Data;

/**
 * 分页请求基类
 * 
 * @author GoAbroad Team
 * @version 1.0
 * @since 2024-10-21
 */
@Data
public class PageRequest {
    
    private Integer page = 1;
    
    private Integer size = 20;
    
    private String sortBy;
    
    private String sortOrder = "desc";
    
    public int getOffset() {
        return (page - 1) * size;
    }
}

