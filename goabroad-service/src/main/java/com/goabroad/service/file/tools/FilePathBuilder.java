package com.goabroad.service.file.tools;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import com.goabroad.model.file.enums.FileType;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * 文件路径构建工具
 * 负责生成文件名和路径
 * 
 * @author GoAbroad Team
 * @version 1.0
 * @since 2024-10-25
 */
@Component
public class FilePathBuilder {
    
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy/MM/dd");
    
    /**
     * 生成唯一文件名
     * 
     * @param originalFilename 原始文件名
     * @return 唯一文件名（UUID + 扩展名）
     */
    public String generateUniqueFilename(String originalFilename) {
        String extension = FileUtil.extName(originalFilename);
        return IdUtil.simpleUUID() + (extension.isEmpty() ? "" : "." + extension);
    }
    
    /**
     * 构建对象路径
     * 格式: type/yyyy/MM/dd/filename
     * 
     * @param fileType 文件类型
     * @param uniqueFilename 唯一文件名
     * @return 对象路径
     */
    public String buildObjectPath(FileType fileType, String uniqueFilename) {
        String datePath = LocalDate.now().format(DATE_FORMATTER);
        return fileType.getPathPrefix() + datePath + "/" + uniqueFilename;
    }
}

