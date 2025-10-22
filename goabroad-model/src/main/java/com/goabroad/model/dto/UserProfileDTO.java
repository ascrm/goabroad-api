package com.goabroad.model.dto;

import com.goabroad.model.dto.common.BaseDTO;
import com.goabroad.model.enums.Gender;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * 用户资料DTO
 * 
 * @author GoAbroad Team
 * @version 1.0
 * @since 2024-10-19
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UserProfileDTO extends BaseDTO {
    
    /**
     * 真实姓名
     */
    private String realName;
    
    /**
     * 性别
     */
    private Gender gender;
    
    /**
     * 生日
     */
    @Past(message = "生日必须是过去的日期")
    private LocalDate birthday;
    
    /**
     * 手机号
     */
    private String phone;
    
    /**
     * 国家/地区
     */
    private String country;
    
    /**
     * 省份
     */
    private String province;
    
    /**
     * 城市
     */
    private String city;
    
    /**
     * 学校
     */
    private String school;
    
    /**
     * 专业
     */
    private String major;
    
    /**
     * 学历
     */
    private Integer education;
    
    /**
     * 个人简介
     */
    private String bio;
    
    /**
     * 个人网站
     */
    private String website;
    
    /**
     * 目标国家（多个国家用逗号分隔）
     */
    private String targetCountries;
    
    /**
     * 目标学校（多个学校用逗号分隔）
     */
    private String targetSchools;
    
    /**
     * 目标专业
     */
    private String targetMajor;
    
    /**
     * 预计出国时间
     */
    private LocalDate expectedDepartureDate;
}

