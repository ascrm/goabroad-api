package com.goabroad.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.goabroad.model.enums.Gender;
import com.goabroad.model.enums.UserStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 用户详细资料VO
 * 
 * @author GoAbroad Team
 * @version 1.0
 * @since 2024-10-25
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "用户详细资料")
public class UserProfileVo {
    
    @Schema(description = "用户ID", example = "1")
    private Long id;
    
    @Schema(description = "用户名", example = "zhangsan")
    private String username;
    
    @Schema(description = "昵称", example = "新昵称")
    private String nickname;
    
    @Schema(description = "个人简介", example = "个人简介")
    private String bio;
    
    @Schema(description = "性别", example = "MALE")
    private Gender gender;
    
    @Schema(description = "目标国家", example = "US")
    private String targetCountry;
    
    @Schema(description = "目标类型", example = "study")
    private String targetType;
    
    @Schema(description = "目标日期", example = "2026-09-01")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate targetDate;
    
    @Schema(description = "当前状态", example = "在读大学生")
    private String currentStatus;
    
    @Schema(description = "账号状态", example = "ACTIVE")
    private UserStatus status;
    
    @Schema(description = "更新时间", example = "2024-10-25T10:30:00")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime updatedAt;
}

