package com.goabroad.model.mapper;

import com.goabroad.model.dto.response.CountryDetailResponse;
import com.goabroad.model.dto.response.CountryResponse;
import com.goabroad.model.entity.Country;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

/**
 * 国家信息实体与DTO转换Mapper
 * 
 * @author GoAbroad Team
 * @version 1.0
 * @since 2024-10-19
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CountryMapper {
    
    /**
     * 实体转简略响应DTO
     *
     * @param country 国家实体
     * @return 国家响应DTO
     */
    CountryResponse toResponse(Country country);
    
    /**
     * 实体列表转响应DTO列表
     *
     * @param countries 国家实体列表
     * @return 国家响应DTO列表
     */
    List<CountryResponse> toResponseList(List<Country> countries);
    
    /**
     * 实体转详细响应DTO
     *
     * @param country 国家实体
     * @return 国家详细响应DTO
     */
    CountryDetailResponse toDetailResponse(Country country);
}

