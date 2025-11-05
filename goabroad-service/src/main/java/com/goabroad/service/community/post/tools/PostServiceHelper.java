package com.goabroad.service.community.post.tools;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.goabroad.model.community.post.entity.PostTag;
import com.goabroad.model.community.post.vo.PostSimpleVo;
import com.goabroad.model.community.tag.entity.Tag;
import com.goabroad.model.user.converter.UserConverter;
import com.goabroad.model.user.entity.User;
import com.goabroad.model.user.vo.UserSimpleVo;
import com.goabroad.service.community.post.repository.PostTagRepository;
import com.goabroad.service.community.post.repository.TagRepository;
import com.goabroad.service.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

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
@Slf4j
public class PostServiceHelper {
    
    private final TagRepository tagRepository;
    private final PostTagRepository postTagRepository;
    private final UserRepository userRepository;
    private final UserConverter userConverter;
    
    /**
     * 处理并保存标签
     *
     * @param tagNames 标签名称列表
     * @param postId   帖子ID
     */
    public void processAndSaveTags(List<String> tagNames, Long postId) {
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
                .replaceAll("\\[(.+?)]\\(.+?\\)", "$1")  // 移除链接，保留文字
                .replaceAll("\\n+", " ")  // 换行替换为空格
                .trim();
        
        // 截取前100字
        if (plainText.length() > 100) {
            return plainText.substring(0, 100) + "...";
        }
        return plainText;
    }

    /**
     * 批量填充帖子的作者信息
     * <p>
     * 使用批量查询优化性能，避免 N+1 查询问题
     *
     * @param postVoList 帖子VO列表
     */
    public void fillAuthorInfo(List<PostSimpleVo> postVoList) {
        if (CollUtil.isEmpty(postVoList)) {
            return;
        }

        // 1. 收集所有需要查询的作者ID
        Set<Long> authorIds = postVoList.stream()
                .map(PostSimpleVo::getAuthorId)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        if (CollUtil.isEmpty(authorIds)) {
            log.warn("帖子列表中没有有效的作者ID");
            return;
        }

        // 2. 批量查询所有作者信息
        List<User> authors = userRepository.findAllById(authorIds);

        // 3. 转换为 Map，方便根据 ID 快速查找
        Map<Long, UserSimpleVo> authorMap = authors.stream()
                .collect(Collectors.toMap(
                        User::getId,
                        userConverter::toUserSimpleVo
                ));

        // 4. 填充每个帖子的作者信息
        postVoList.forEach(postVo -> {
            UserSimpleVo author = authorMap.get(postVo.getAuthorId());
            if (author != null) {
                postVo.setAuthor(author);
            } else {
                log.warn("未找到帖子{}的作者信息，作者ID: {}", postVo.getId(), postVo.getAuthorId());
            }
        });

        log.debug("成功填充{}个帖子的作者信息", postVoList.size());
    }
}

