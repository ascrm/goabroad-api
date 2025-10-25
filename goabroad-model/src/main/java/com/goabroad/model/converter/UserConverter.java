package com.goabroad.model.converter;

import com.goabroad.model.entity.User;
import com.goabroad.model.vo.UserVo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * 用户实体转换器
 * 使用 MapStruct 进行对象转换
 * 
 * @author GoAbroad Team
 * @version 1.0
 * @since 2024-10-19
 */
@Mapper
public interface UserConverter {
    
    UserConverter INSTANCE = Mappers.getMapper(UserConverter.class);
    
    /**
     * User 实体转换为 UserVo
     * 
     * @param user 用户实体
     * @return 用户视图对象
     */
    @Mapping(target = "email", expression = "java(maskEmail(user.getEmail()))")
    @Mapping(target = "phone", expression = "java(maskPhone(user.getPhone()))")
    UserVo toResponse(User user);
    
    /**
     * User 实体转换为 UserVo（不脱敏）
     * 
     * @param user 用户实体
     * @return 用户视图对象
     */
    UserVo toResponseWithoutMask(User user);
    
    /**
     * 邮箱脱敏
     * 示例：test@example.com -> t***@example.com
     * 
     * @param email 原始邮箱
     * @return 脱敏后的邮箱
     */
    default String maskEmail(String email) {
        if (email == null || email.isEmpty()) {
            return null;
        }
        int atIndex = email.indexOf("@");
        if (atIndex <= 1) {
            return email;
        }
        String prefix = email.substring(0, 1);
        String suffix = email.substring(atIndex);
        return prefix + "***" + suffix;
    }
    
    /**
     * 手机号脱敏
     * 示例：13812345678 -> 138****5678
     * 
     * @param phone 原始手机号
     * @return 脱敏后的手机号
     */
    default String maskPhone(String phone) {
        if (phone == null || phone.length() < 11) {
            return phone;
        }
        return phone.substring(0, 3) + "****" + phone.substring(7);
    }
}

