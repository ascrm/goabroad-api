package com.goabroad.service.auth.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.RandomUtil;
import com.goabroad.common.exception.BusinessException;
import com.goabroad.common.response.ResultCode;
import com.goabroad.model.dto.LoginDto;
import com.goabroad.model.dto.RegisterDto;
import com.goabroad.model.dto.vo.UserVo;
import com.goabroad.model.entity.User;
import com.goabroad.model.enums.UserStatus;
import com.goabroad.model.mapper.UserMapper;
import com.goabroad.repository.mysql.UserRepository;
import com.goabroad.service.auth.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

/**
 * 认证服务实现
 *
 * @author GoAbroad Team
 * @version 1.0
 * @since 2024-10-19
 */
@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final RedisTemplate<String, String> redisTemplate;
    private final RestTemplate restTemplate;

    // 密码加密器（BCrypt）
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // Redis Key 前缀
    private static final String VERIFICATION_CODE_PREFIX = "auth:code:email:";
    private static final String SMS_CODE_PREFIX = "auth:code:sms:";
    private static final int CODE_EXPIRE_MINUTES = 5; // 验证码有效期5分钟
    
    // 短信服务配置
    private static final String SMS_API_URL = "https://push.spug.cc/send/E3w5LmlZwWrxYea4";

    @Override
    public void register(RegisterDto register) {
        // 1. 验证手机号是否已注册
        if (userRepository.existsByPhone(register.getPhone())) {
            throw BusinessException.of(ResultCode.ERROR, "该手机号已被注册");
        }
        
        // 2. 验证短信验证码
        String redisKey = SMS_CODE_PREFIX + register.getPhone();
        String savedCode = redisTemplate.opsForValue().get(redisKey);
        if (savedCode == null) {
            throw BusinessException.of(ResultCode.ERROR, "验证码已过期，请重新获取");
        }
        if (!savedCode.equals(register.getCode())) {
            throw BusinessException.of(ResultCode.ERROR, "验证码错误");
        }

        String randomUsername = generateRandomUsername();
        String encodedPassword = passwordEncoder.encode(register.getPassword());
        
        User user = User.builder()
                .username(randomUsername)
                .phone(register.getPhone())
                .passwordHash(encodedPassword)
                .nickname("用户" + RandomUtil.randomNumbers(6))
                .email(null) // 邮箱初始为空
                .phoneVerified(true) // 手机号已验证
                .emailVerified(false)
                .build();
        
        User savedUser = userRepository.save(user);
        redisTemplate.delete(redisKey);
        StpUtil.login(savedUser.getId());
    }
    
    /**
     * 生成随机用户名
     * 格式：user_ + 时间戳后6位 + 4位随机数
     * 示例：user_123456_7890
     * 
     * @return 随机用户名
     */
    private String generateRandomUsername() {
        long timestamp = System.currentTimeMillis();
        String timestampSuffix = String.valueOf(timestamp).substring(7); // 取后6位
        String randomSuffix = RandomUtil.randomNumbers(4);
        return "user_" + timestampSuffix + "_" + randomSuffix;
    }

    @Override
    public UserVo login(LoginDto loginDto) {
        // 1. 根据手机号或邮箱查询用户
        User user = userRepository.findByPhoneOrEmail(loginDto.getAccount(), loginDto.getAccount())
                .orElseThrow(() -> BusinessException.of(ResultCode.ERROR, "手机号/邮箱或密码错误"));

        // 2. 验证用户状态
        if (user.getStatus() == UserStatus.BANNED) {
            throw BusinessException.of(ResultCode.ERROR, "账号已被封禁，请联系管理员");
        }

        if (user.getStatus() == UserStatus.INACTIVE) {
            throw BusinessException.of(ResultCode.ERROR, "账号未激活，请先激活账号");
        }

        // 3. 验证密码
        if (!passwordEncoder.matches(loginDto.getPassword(), user.getPasswordHash())) {
            throw BusinessException.of(ResultCode.ERROR, "手机号/邮箱或密码错误");
        }

        // 4. 更新最后登录时间并登录
        user.setLastLoginAt(LocalDateTime.now());
        userRepository.save(user);
        StpUtil.login(user.getId());
        
        return UserMapper.INSTANCE.toResponse(user);
    }

    @Override
    public UserVo refreshToken(String refreshToken) {
        log.info("刷新Token开始");

        try {
            // 验证当前登录状态
            StpUtil.checkLogin();

            // 获取当前登录用户ID
            long userId = StpUtil.getLoginIdAsLong();

            // 查询用户信息
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> BusinessException.of(ResultCode.ERROR, "用户不存在"));

            // 重新登录以刷新Token
            StpUtil.logout(userId);
            StpUtil.login(userId);

            UserVo response = UserMapper.INSTANCE.toResponse(user);
            log.info("Token刷新成功, userId: {}", userId);
            return response;
            
        } catch (Exception e) {
            log.error("Token刷新失败", e);
            throw BusinessException.of(ResultCode.ERROR, "Token刷新失败，请重新登录");
        }
    }
    
    @Override
    public void logout(Long userId) {
        log.info("用户登出开始, userId: {}", userId);
        
        try {
            // 使用 Sa-Token 登出
            StpUtil.logout(userId);
            log.info("用户登出成功, userId: {}", userId);
        } catch (Exception e) {
            log.error("用户登出失败, userId: {}", userId, e);
            throw BusinessException.of(ResultCode.ERROR, "登出失败");
        }
    }

    @Override
    public void sendSmsVerificationCode(String phone) {
        String code = RandomUtil.randomNumbers(6);

        // 2. 保存到Redis（设置5分钟过期）
        String redisKey = SMS_CODE_PREFIX + phone;
        redisTemplate.opsForValue().set(redisKey, code, CODE_EXPIRE_MINUTES, TimeUnit.MINUTES);

        // 3. 发送短信
        try {
            sendSmsRequest(phone, code);
            log.info("短信验证码发送成功, phone: {}, code: {}, 有效期{}分钟", phone, code, CODE_EXPIRE_MINUTES);
        } catch (Exception e) {
            log.error("短信验证码发送失败, phone: {}", phone, e);
            throw BusinessException.of(ResultCode.ERROR, "短信发送失败，请稍后重试");
        }
    }


    @Override
    @Transactional(readOnly = true)
    public Long validateToken(String token) {
        log.debug("验证Token开始");
        
        try {
            // Sa-Token 自动验证
            Object loginId = StpUtil.getLoginIdByToken(token);
            if (loginId == null) {
                return null;
            }
            return Long.parseLong(loginId.toString());
        } catch (Exception e) {
            log.debug("Token验证失败", e);
            return null;
        }
    }

    /**
     * 发送短信请求
     * 
     * @param phone 手机号
     * @param code 验证码
     */
    private void sendSmsRequest(String phone, String code) {
        try {
            // 构建请求体（表单格式）
            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            params.add("key1", "GoAbroad");
            params.add("key2", code);
            params.add("key3", "3");
            params.add("targets", phone);
            
            // 设置请求头
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            
            // 创建请求实体
            HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, headers);
            
            // 发送POST请求
            ResponseEntity<String> response = restTemplate.exchange(
                    SMS_API_URL,
                    HttpMethod.POST,
                    requestEntity,
                    String.class
            );
            
            // 检查响应状态
            if (response.getStatusCode().is2xxSuccessful()) {
                log.info("短信发送成功, 响应: {}", response.getBody());
            } else {
                log.error("短信发送失败, HTTP状态码: {}, 响应: {}", 
                        response.getStatusCode(), response.getBody());
                throw new RuntimeException("短信发送失败: HTTP " + response.getStatusCode());
            }
        } catch (Exception e) {
            log.error("短信发送异常, phone: {}", phone, e);
            throw new RuntimeException("短信发送失败: " + e.getMessage());
        }
    }
    
    @Override
    @Transactional(readOnly = true)
    public boolean verifyCode(String email, String code) {
        log.info("验证验证码开始, email: {}", email);
        
        // 从Redis获取验证码
        String redisKey = VERIFICATION_CODE_PREFIX + email;
        String savedCode = redisTemplate.opsForValue().get(redisKey);
        
        if (savedCode == null) {
            log.warn("验证码已过期或不存在, email: {}", email);
            return false;
        }
        
        boolean isValid = savedCode.equals(code);
        log.info("验证码验证结果: {}, email: {}", isValid ? "成功" : "失败", email);
        return isValid;
    }
    
    @Override
    public void resetPassword(String email, String code, String newPassword) {
        log.info("重置密码开始, email: {}", email);
        
        // 1. 验证验证码
        if (!verifyCode(email, code)) {
            log.warn("验证码错误, email: {}", email);
            throw BusinessException.of(ResultCode.ERROR, "验证码错误或已过期");
        }
        
        // 2. 查询用户
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> BusinessException.of(ResultCode.ERROR, "用户不存在"));
        
        // 3. 加密新密码
        String encodedPassword = passwordEncoder.encode(newPassword);
        user.setPasswordHash(encodedPassword);
        
        // 4. 保存用户
        userRepository.save(user);
        
        // 5. 删除验证码
        String redisKey = VERIFICATION_CODE_PREFIX + email;
        redisTemplate.delete(redisKey);
        
        // 6. 登出所有设备
        StpUtil.logout(user.getId());
        
        log.info("密码重置成功, email: {}, userId: {}", email, user.getId());
    }
}
