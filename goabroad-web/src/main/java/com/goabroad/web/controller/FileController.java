package com.goabroad.web.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.goabroad.common.pojo.Result;
import com.goabroad.model.file.enums.FileType;
import com.goabroad.model.file.vo.FileUploadVo;
import com.goabroad.service.file.service.FileStorageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

/**
 * 文件上传控制器
 * 
 * @author GoAbroad Team
 * @version 1.0
 * @since 2024-10-25
 */
@RestController
@RequestMapping("/api/upload")
@Tag(name = "文件上传", description = "文件上传相关接口")
@Slf4j
@RequiredArgsConstructor
public class FileController {
    
    private final FileStorageService fileStorageService;
    
    /**
     * 通用文件上传
     */
    @PostMapping
    @Operation(summary = "通用文件上传", description = "上传单个文件")
    public Result<String> uploadFile(
            @Parameter(description = "文件", required = true) @RequestParam("file") MultipartFile file) {
        
        Long userId = StpUtil.getLoginIdAsLong();
        
        FileUploadVo vo = fileStorageService.uploadFile(file, FileType.ATTACHMENT, userId);
        return Result.success("上传成功", vo.getUrl());
    }
    
    /**
     * 批量文件上传
     */
    @PostMapping("/batch")
    @Operation(summary = "批量文件上传", description = "批量上传多个文件")
    public Result<List<String>> uploadFiles(
            @Parameter(description = "文件列表", required = true) @RequestParam("files") MultipartFile[] files) {
        
        Long userId = StpUtil.getLoginIdAsLong();
        
        List<String> urls = new ArrayList<>();
        
        for (MultipartFile file : files) {
            try {
                FileUploadVo vo = fileStorageService.uploadFile(file, FileType.ATTACHMENT, userId);
                urls.add(vo.getUrl());
            } catch (Exception e) {
                log.error("文件上传失败: {}", file.getOriginalFilename(), e);
            }
        }
        
        return Result.success("上传完成", urls);
    }
    
    /**
     * 删除文件
     */
    @DeleteMapping
    @Operation(summary = "删除文件", description = "删除已上传的文件")
    public Result<String> deleteFile(
            @Parameter(description = "文件URL", required = true) @RequestParam String fileUrl) {
        
        fileStorageService.deleteFile(fileUrl);
        return Result.success("文件删除成功");
    }
}

