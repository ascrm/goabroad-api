package com.goabroad.model.dto.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 分页响应
 * 
 * @author GoAbroad Team
 * @version 1.0
 * @since 2024-10-21
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageResponse<T> {
    
    private List<T> items;
    
    private Long total;
    
    private Integer page;
    
    private Integer size;
    
    private Integer totalPages;
    
    public static <T> PageResponse<T> of(List<T> items, Long total, Integer page, Integer size) {
        PageResponse<T> response = new PageResponse<>();
        response.setItems(items);
        response.setTotal(total);
        response.setPage(page);
        response.setSize(size);
        response.setTotalPages((int) Math.ceil((double) total / size));
        return response;
    }
}

