package com.goabroad.model.user.converter;

import com.goabroad.model.user.dto.UpdateUserProfileDto;
import com.goabroad.model.user.entity.User;
import com.goabroad.model.user.vo.UserProfileVo;
import com.goabroad.model.user.vo.UserPublicVo;
import com.goabroad.model.user.vo.UserSimpleVo;
import com.goabroad.model.user.vo.UserVo;
import org.mapstruct.*;

/**
 * 用户实体转换器
 * 使用 MapStruct 进行对象转换
 * 
 * @author GoAbroad Team
 * @version 1.0
 * @since 2024-10-19
 */
@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserConverter {
    
    /**
     * User 实体转换为 UserVo（带脱敏）
     * 
     * @param user 用户实体
     * @return 用户视图对象
     */
    @Mapping(target = "phone", expression = "java(cn.hutool.core.util.DesensitizedUtil.mobilePhone(user.getPhone()))")
    @Mapping(target = "email", expression = "java(cn.hutool.core.util.DesensitizedUtil.email(user.getEmail()))")
    UserVo toVo(User user);
    
    /**
     * User 实体转换为 UserVo（不脱敏）
     * 
     * @param user 用户实体
     * @return 用户视图对象
     */
    UserVo toVoWithoutMask(User user);
    
    /**
     * User转UserPublicVo
     * 
     * @param user 用户实体
     * @return 用户公开信息VO
     */
    @Mapping(target = "stats", ignore = true)
    @Mapping(target = "isFollowing", ignore = true)
    @Mapping(target = "badges", ignore = true)
    @Mapping(target = "targetCountry", ignore = true)
    UserPublicVo toUserPublicVo(User user);
    
    /**
     * User转UserProfileVo
     * 
     * @param user 用户实体
     * @return 用户资料VO
     */
    @Mapping(target = "targetCountry", ignore = true)
    @Mapping(target = "targetType", ignore = true)
    @Mapping(target = "targetDate", ignore = true)
    @Mapping(target = "currentStatus", ignore = true)
    UserProfileVo toUserProfileVo(User user);
    
    /**
     * User转UserSimpleVo
     * 
     * @param user 用户实体
     * @return 用户简要信息VO
     */
    @Mapping(target = "isFollowing", ignore = true)
    UserSimpleVo toUserSimpleVo(User user);
    
    /**
     * 更新User实体（从UpdateUserProfileDto）
     * 只更新非null字段
     * 
     * @param dto 更新用户资料DTO
     * @param user 用户实体（目标对象）
     */
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateUserFromDto(UpdateUserProfileDto dto, @MappingTarget User user);
}

