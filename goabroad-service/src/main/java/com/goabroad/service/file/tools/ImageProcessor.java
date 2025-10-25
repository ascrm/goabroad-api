package com.goabroad.service.file.tools;

import cn.hutool.core.io.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 图片处理工具
 * 负责图片的尺寸获取、缩略图生成等功能
 * 
 * @author GoAbroad Team
 * @version 1.0
 * @since 2024-10-25
 */
@Slf4j
@Component
public class ImageProcessor {
    
    private static final int THUMBNAIL_MAX_SIZE = 200; // 缩略图最大尺寸
    
    /**
     * 图片尺寸信息
     */
    public record ImageDimension(int width, int height) {}
    
    /**
     * 缩略图数据
     */
    public record ThumbnailData(byte[] data, String contentType) {}
    
    /**
     * 获取图片尺寸
     * 
     * @param file 图片文件
     * @return 图片尺寸，如果获取失败返回 null
     */
    public ImageDimension getImageDimension(MultipartFile file) {
        try {
            BufferedImage image = ImageIO.read(file.getInputStream());
            if (image != null) {
                return new ImageDimension(image.getWidth(), image.getHeight());
            }
        } catch (IOException e) {
            log.warn("无法读取图片尺寸: {}", e.getMessage());
        }
        return null;
    }
    
    /**
     * 生成缩略图
     * 
     * @param originalFile 原始图片文件
     * @return 缩略图数据，如果生成失败返回 null
     */
    public ThumbnailData generateThumbnail(MultipartFile originalFile) {
        try {
            // 读取原始图片
            BufferedImage originalImage = ImageIO.read(originalFile.getInputStream());
            if (originalImage == null) {
                return null;
            }
            
            // 计算缩略图尺寸（保持宽高比）
            ImageDimension thumbnailSize = calculateThumbnailSize(
                    originalImage.getWidth(), 
                    originalImage.getHeight()
            );
            
            // 创建缩略图
            BufferedImage thumbnail = createThumbnail(originalImage, thumbnailSize);
            
            // 转换为字节数组
            byte[] thumbnailBytes = convertToBytes(thumbnail, getImageFormat(originalFile.getOriginalFilename()));
            
            return new ThumbnailData(thumbnailBytes, originalFile.getContentType());
            
        } catch (Exception e) {
            log.warn("生成缩略图失败: {}", e.getMessage());
            return null;
        }
    }
    
    /**
     * 生成缩略图对象名称
     * 
     * @param originalObjectName 原始对象名称
     * @return 缩略图对象名称
     */
    public String buildThumbnailObjectName(String originalObjectName) {
        return originalObjectName.replace(".", "_thumb.");
    }
    
    /**
     * 计算缩略图尺寸（保持宽高比）
     */
    private ImageDimension calculateThumbnailSize(int originalWidth, int originalHeight) {
        double scale = Math.min(
                (double) THUMBNAIL_MAX_SIZE / originalWidth, 
                (double) THUMBNAIL_MAX_SIZE / originalHeight
        );
        
        int thumbnailWidth = (int) (originalWidth * scale);
        int thumbnailHeight = (int) (originalHeight * scale);
        
        return new ImageDimension(thumbnailWidth, thumbnailHeight);
    }
    
    /**
     * 创建缩略图
     */
    private BufferedImage createThumbnail(BufferedImage originalImage, ImageDimension size) {
        BufferedImage thumbnail = new BufferedImage(
                size.width(), 
                size.height(), 
                BufferedImage.TYPE_INT_RGB
        );
        
        Graphics2D graphics = thumbnail.createGraphics();
        graphics.setRenderingHint(
                RenderingHints.KEY_INTERPOLATION, 
                RenderingHints.VALUE_INTERPOLATION_BILINEAR
        );
        graphics.drawImage(
                originalImage.getScaledInstance(size.width(), size.height(), Image.SCALE_SMOOTH), 
                0, 0, null
        );
        graphics.dispose();
        
        return thumbnail;
    }
    
    /**
     * 将图片转换为字节数组
     */
    private byte[] convertToBytes(BufferedImage image, String format) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, format, baos);
        return baos.toByteArray();
    }
    
    /**
     * 获取图片格式
     */
    private String getImageFormat(String filename) {
        String extension = FileUtil.extName(filename).toLowerCase();
        return switch (extension) {
            case "png" -> "png";
            case "gif" -> "gif";
            default -> "jpg";
        };
    }
    
    /**
     * 将字节数组转换为输入流
     */
    public InputStream toInputStream(byte[] data) {
        return new ByteArrayInputStream(data);
    }
}

