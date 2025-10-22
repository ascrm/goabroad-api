package com.goabroad.service.community;

import com.goabroad.model.entity.Tag;
import com.goabroad.model.dto.vo.PostVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 标签服务接口
 * 
 * @author GoAbroad Team
 * @version 1.0
 * @since 2024-10-19
 */
public interface TagService {
    
    /**
     * 创建标签
     * 
     * @param name 标签名称
     * @return 标签信息
     */
    Tag createTag(String name);
    
    /**
     * 更新标签
     * 
     * @param tagId 标签ID
     * @param name 标签名称
     * @return 更新后的标签信息
     */
    Tag updateTag(Long tagId, String name);
    
    /**
     * 删除标签
     * 
     * @param tagId 标签ID
     */
    void deleteTag(Long tagId);
    
    /**
     * 根据ID获取标签
     * 
     * @param tagId 标签ID
     * @return 标签信息
     */
    Tag getTagById(Long tagId);
    
    /**
     * 根据名称获取标签
     * 
     * @param tagName 标签名称
     * @return 标签信息
     */
    Tag getTagByName(String tagName);
    
    /**
     * 获取所有标签
     * 
     * @param pageable 分页参数
     * @return 标签列表
     */
    Page<Tag> getAllTags(Pageable pageable);
    
    /**
     * 获取热门标签
     * 
     * @param limit 数量限制
     * @return 热门标签列表
     */
    List<Tag> getPopularTags(Integer limit);
    
    /**
     * 搜索标签
     * 
     * @param keyword 关键词
     * @return 标签列表
     */
    List<Tag> searchTags(String keyword);
    
    /**
     * 根据标签获取帖子列表
     * 
     * @param tagId 标签ID
     * @param pageable 分页参数
     * @return 帖子列表
     */
    Page<PostVO> getPostsByTag(Long tagId, Pageable pageable);
}

