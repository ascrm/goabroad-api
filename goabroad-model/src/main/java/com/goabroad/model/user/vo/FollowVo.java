package com.goabroad.model.user.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 关注操作响应VO
 * 
 * @author GoAbroad Team
 * @version 1.0
 * @since 2024-10-25
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "关注操作响应")
public class FollowVo {
    
    @Schema(description = "是否已关注", example = "true")
    private Boolean isFollowing;
    
    @Schema(description = "粉丝数", example = "121")
    private Integer followersCount;
}

