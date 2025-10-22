package com.goabroad.model.dto;

import com.goabroad.model.dto.validation.Create;
import com.goabroad.model.enums.ContentType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

/**
 * 帖子DTO（创建和更新共用）
 * 
 * @author GoAbroad Team
 * @version 1.0
 * @since 2024-10-21
 */
@Data
public class PostDTO {
    
    @NotBlank(message = "标题不能为空", groups = Create.class)
    @Size(max = 200, message = "标题长度不能超过200字符")
    private String title;
    
    @NotBlank(message = "内容不能为空", groups = Create.class)
    private String content;
    
    private ContentType contentType;
    
    private String category;
    
    private String countryCode;
    
    private List<String> tags;
    
    private List<String> images;
}

