package com.goabroad.model.converter;

import com.goabroad.model.dto.UpdateUserProfileDto;
import com.goabroad.model.entity.User;
import com.goabroad.model.vo.UserProfileVo;
import com.goabroad.model.vo.UserPublicVo;
import com.goabroad.model.vo.UserSimpleVo;
import org.mapstruct.*;

/**
 * 用户对象映射器
 * 
 * @author GoAbroad Team
 * @version 1.0
 * @since 2024-10-25
 */
@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserMapper {
    
    /**
     * User转UserPublicVo
     */
    @Mapping(target = "stats", ignore = true)
    @Mapping(target = "isFollowing", ignore = true)
    @Mapping(target = "badges", ignore = true)
    @Mapping(target = "targetCountry", ignore = true)
    UserPublicVo toUserPublicVo(User user);
    
    /**
     * User转UserProfileVo
     */
    @Mapping(target = "targetCountry", ignore = true)
    @Mapping(target = "targetType", ignore = true)
    @Mapping(target = "targetDate", ignore = true)
    @Mapping(target = "currentStatus", ignore = true)
    UserProfileVo toUserProfileVo(User user);
    
    /**
     * User转UserSimpleVo
     */
    @Mapping(target = "isFollowing", ignore = true)
    UserSimpleVo toUserSimpleVo(User user);
    
    /**
     * 更新User实体（从UpdateUserProfileDto）
     * 只更新非null字段
     */
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateUserFromDto(UpdateUserProfileDto dto, @MappingTarget User user);
}

