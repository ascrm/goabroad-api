package com.goabroad.service.community.post.tools;

import cn.hutool.core.util.StrUtil;
import com.goabroad.model.community.post.entity.PostTag;
import com.goabroad.model.community.tag.entity.Tag;
import com.goabroad.service.community.post.repository.PostTagRepository;
import com.goabroad.service.community.post.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 帖子服务辅助工具类
 * 提供帖子相关的工具方法
 * 
 * @author GoAbroad Team
 * @version 1.0
 * @since 2024-11-04
 */
@Component
@RequiredArgsConstructor
public class PostServiceHelper {
    
    private final TagRepository tagRepository;
    private final PostTagRepository postTagRepository;
    
    /**
     * 处理并保存标签
     * 
     * @param tagNames 标签名称列表
     * @param postId 帖子ID
     * @return 保存后的标签列表
     */
    public List<Tag> processAndSaveTags(List<String> tagNames, Long postId) {
        List<Tag> savedTags = new ArrayList<>();
        
        for (String tagName : tagNames) {
            // 跳过空标签
            if (StrUtil.isBlank(tagName)) {
                continue;
            }
            
            // 清理标签名称（使用final变量）
            final String cleanedTagName = tagName.trim();
            
            // 查找或创建标签
            Tag tag = tagRepository.findByNameAndDeleted(cleanedTagName, false)
                    .map(existingTag -> {
                        // 标签已存在，增加帖子计数
                        existingTag.setPostCount(existingTag.getPostCount() + 1);
                        return tagRepository.save(existingTag);
                    })
                    .orElseGet(() -> {
                        // 创建新标签
                        Tag newTag = Tag.builder()
                                .name(cleanedTagName)
                                .slug(generateSlug(cleanedTagName))
                                .postCount(1)
                                .build();
                        newTag.setDeleted(false);
                        return tagRepository.save(newTag);
                    });
            
            // 创建帖子-标签关联
            PostTag postTag = PostTag.builder()
                    .postId(postId)
                    .tagId(tag.getId())
                    .build();
            postTag.setDeleted(false);
            postTagRepository.save(postTag);
            
            savedTags.add(tag);
        }
        
        return savedTags;
    }
    
    /**
     * 生成标签的slug（URL友好标识）
     * 
     * @param tagName 标签名称
     * @return slug
     */
    public String generateSlug(String tagName) {
        // 简单实现：转小写并替换空格为连字符
        return tagName.toLowerCase()
                .replaceAll("\\s+", "-")
                .replaceAll("[^a-z0-9\\u4e00-\\u9fa5-]", "");
    }
    
    /**
     * 生成摘要（自动截取content前100字）
     * 
     * @param content 内容
     * @return 摘要
     */
    public String generateSummary(String content) {
        if (StrUtil.isBlank(content)) {
            return "";
        }
        
        // 移除Markdown标记（简单处理）
        String plainText = content
                .replaceAll("#+ ", "")  // 移除标题标记
                .replaceAll("\\*\\*(.+?)\\*\\*", "$1")  // 移除粗体
                .replaceAll("\\*(.+?)\\*", "$1")  // 移除斜体
                .replaceAll("\\[(.+?)\\]\\(.+?\\)", "$1")  // 移除链接，保留文字
                .replaceAll("\\n+", " ")  // 换行替换为空格
                .trim();
        
        // 截取前100字
        if (plainText.length() > 100) {
            return plainText.substring(0, 100) + "...";
        }
        return plainText;
    }
}

