package com.goabroad.model.converter;

import com.goabroad.model.entity.User;
import com.goabroad.model.vo.UserVo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * 用户实体转换器
 * 使用 MapStruct 进行对象转换
 * 
 * @author GoAbroad Team
 * @version 1.0
 * @since 2024-10-19
 */
@Mapper(componentModel = "spring")
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
}

