package com.goabroad.service.community.post.service.impl;

import cn.hutool.core.util.StrUtil;
import com.goabroad.common.exception.BusinessException;
import com.goabroad.common.pojo.ResultCode;
import com.goabroad.model.community.post.converter.PostConverter;
import com.goabroad.model.community.post.dto.CreatePostDto;
import com.goabroad.model.community.post.entity.Post;
import com.goabroad.model.user.entity.User;
import com.goabroad.model.community.post.enums.PostStatus;
import com.goabroad.model.community.post.vo.PostDetailVo;
import com.goabroad.service.community.post.repository.PostRepository;
import com.goabroad.service.community.post.service.PostService;
import com.goabroad.service.community.post.tools.PostServiceHelper;
import com.goabroad.service.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * 帖子服务实现类
 * 
 * @author GoAbroad Team
 * @version 1.0
 * @since 2024-10-28
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final PostConverter postConverter;
    private final PostServiceHelper postServiceHelper;
    
    /**
     * 发布帖子
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public PostDetailVo createPost(Long userId, CreatePostDto dto) {
        // 1. 查询用户
        User user = userRepository.findById(userId)
                .orElseThrow(() -> BusinessException.of(ResultCode.USER_NOT_FOUND));
        
        // 2. 处理摘要（如果未提供，自动截取content前100字）
        String summary = dto.getSummary();
        if (StrUtil.isBlank(summary)) {
            summary = postServiceHelper.generateSummary(dto.getContent());
        }
        
        // 3. 确定发布状态和发布时间
        PostStatus status = dto.getStatus() != null ? dto.getStatus() : PostStatus.PUBLISHED;
        LocalDateTime publishedAt = (status == PostStatus.PUBLISHED) ? LocalDateTime.now() : null;
        
        // 4. 使用 MapStruct 转换 DTO 为 Post 实体
        Post post = postConverter.toPost(dto, userId, summary, status, publishedAt);
        
        // 5. 保存帖子
        post = postRepository.save(post);
        
        // 6. 如果是已发布状态，更新用户的发帖数
        if (post.getStatus() == PostStatus.PUBLISHED) {
            user.setPostCount(user.getPostCount() + 1);
            userRepository.save(user);
        }
        
        log.info("用户{}发布了帖子，帖子ID: {}, 标题: {}, 状态: {}", 
                userId, post.getId(), post.getTitle(), post.getStatus());
        
        // 7. 使用 MapStruct 转换为 VO 并返回
        return postConverter.toPostDetailVo(post);
    }
}

