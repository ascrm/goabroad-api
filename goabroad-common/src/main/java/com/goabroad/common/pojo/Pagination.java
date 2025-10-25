package com.goabroad.common.pojo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * 分页信息
 * 
 * @author GoAbroad Team
 * @version 1.0
 * @since 2024-10-25
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "分页信息")
public class Pagination implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    
    /**
     * 当前页码（从1开始）
     */
    @Schema(description = "当前页码", example = "1")
    private Integer currentPage;
    
    /**
     * 每页大小
     */
    @Schema(description = "每页大小", example = "20")
    private Integer pageSize;
    
    /**
     * 总记录数
     */
    @Schema(description = "总记录数", example = "100")
    private Long totalItems;
    
    /**
     * 总页数
     */
    @Schema(description = "总页数", example = "5")
    private Integer totalPages;
    
    /**
     * 是否有下一页
     */
    @Schema(description = "是否有下一页", example = "true")
    private Boolean hasNext;
    
    /**
     * 是否有上一页
     */
    @Schema(description = "是否有上一页", example = "false")
    private Boolean hasPrevious;
    
    /**
     * 是否第一页
     */
    @Schema(description = "是否第一页", example = "true")
    private Boolean isFirstPage;
    
    /**
     * 是否最后一页
     */
    @Schema(description = "是否最后一页", example = "false")
    private Boolean isLastPage;
    
    /**
     * 构建分页信息
     * 
     * @param page 当前页码
     * @param pageSize 每页大小
     * @param total 总记录数
     * @return 分页信息
     */
    public static Pagination of(Integer page, Integer pageSize, Long total) {
        // 计算总页数（避免除0）
        int totalPages = pageSize > 0 ? (int) ((total + pageSize - 1) / pageSize) : 0;
        
        // 判断各种状态
        boolean hasNext = page < totalPages;
        boolean hasPrevious = page > 1;
        boolean isFirstPage = page == 1;
        boolean isLastPage = page >= totalPages && totalPages > 0;
        
        return Pagination.builder()
                .currentPage(page)
                .pageSize(pageSize)
                .totalItems(total)
                .totalPages(totalPages)
                .hasNext(hasNext)
                .hasPrevious(hasPrevious)
                .isFirstPage(isFirstPage)
                .isLastPage(isLastPage)
                .build();
    }
}

