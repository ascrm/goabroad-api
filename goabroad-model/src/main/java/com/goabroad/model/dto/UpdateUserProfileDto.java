package com.goabroad.model.dto;

import com.goabroad.model.enums.Gender;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

/**
 * 更新用户资料DTO
 * 
 * @author GoAbroad Team
 * @version 1.0
 * @since 2024-10-25
 */
@Data
@Schema(description = "更新用户资料请求")
public class UpdateUserProfileDto {
    
    @Schema(description = "昵称", example = "GoAbroad小新")
    @Size(min = 2, max = 50, message = "昵称长度必须在2-50个字符之间")
    private String nickname;
    
    @Schema(description = "个人简介", example = "正在准备美国留学")
    @Size(max = 500, message = "个人简介不能超过500个字符")
    private String bio;
    
    @Schema(description = "性别", example = "MALE")
    private Gender gender;
    
    @Schema(description = "目标国家代码", example = "US")
    private String targetCountry;
    
    @Schema(description = "目标类型", example = "study", allowableValues = {"study", "work", "immigration", "travel", "undecided"})
    private String targetType;
    
    @Schema(description = "目标日期", example = "2026-09-01")
    private LocalDate targetDate;
    
    @Schema(description = "当前状态", example = "在读大学生")
    @Size(max = 100, message = "当前状态不能超过100个字符")
    private String currentStatus;

    @Schema(description = "用户头像", example = "file://path/to/image.jpg")
    private String avatarUrl;
}

