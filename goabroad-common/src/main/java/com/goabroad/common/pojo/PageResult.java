package com.goabroad.common.pojo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * 分页响应结果封装
 * 
 * @author GoAbroad Team
 * @version 1.0
 * @since 2024-10-19
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "分页响应结果")
public class PageResult<T> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    
    /**
     * 数据列表
     */
    @Schema(description = "数据列表")
    private List<T> items;
    
    /**
     * 分页信息
     */
    @Schema(description = "分页信息")
    private Pagination pagination;
    
    /**
     * 构建分页结果
     * 
     * @param items 数据列表
     * @param page 当前页码
     * @param pageSize 每页大小
     * @param total 总记录数
     * @return 分页结果
     */
    public static <T> PageResult<T> of(List<T> items, Integer page, Integer pageSize, Long total) {
        return PageResult.<T>builder()
                .items(items)
                .pagination(Pagination.of(page, pageSize, total))
                .build();
    }
    
    /**
     * 构建空分页结果
     * 
     * @param page 当前页码
     * @param pageSize 每页大小
     * @return 空分页结果
     */
    public static <T> PageResult<T> empty(Integer page, Integer pageSize) {
        return PageResult.<T>builder()
                .items(List.of())
                .pagination(Pagination.of(page, pageSize, 0L))
                .build();
    }
}

