package com.goabroad.model.mapper;

import com.goabroad.model.dto.RegisterDto;
import com.goabroad.model.dto.vo.UserVo;
import com.goabroad.model.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 用户对象映射器（MapStruct）
 * 
 * @author GoAbroad Team
 * @version 1.0
 * @since 2024-10-19
 */
@Mapper(componentModel = "spring")
public interface UserMapper {
    
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
    
    /**
     * User 转 UserVo
     * 
     * @param user 用户实体
     * @return 用户响应DTO
     */
    UserVo toResponse(User user);
    
    /**
     * User 列表转 UserVo 列表
     * 
     * @param users 用户实体列表
     * @return 用户响应DTO列表
     */
    List<UserVo> toResponseList(List<User> users);
    
    /**
     * RegisterDto 转 User
     * 
     * @param request 注册请求
     * @return 用户实体
     */
    User toEntity(RegisterDto request);
}
