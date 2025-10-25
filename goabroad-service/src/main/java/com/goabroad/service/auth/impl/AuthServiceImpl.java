package com.goabroad.service.auth.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.RandomUtil;
import com.goabroad.common.exception.BusinessException;
import com.goabroad.common.response.ResultCode;
import com.goabroad.model.dto.LoginDto;
import com.goabroad.model.dto.RegisterDto;
import com.goabroad.model.entity.User;
import com.goabroad.model.enums.UserStatus;
import com.goabroad.repository.mysql.UserRepository;
import com.goabroad.service.auth.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

import static com.goabroad.common.constant.CacheKeyConstant.*;

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

    private final RedisTemplate<String, String> redisTemplate;

    private final RestTemplate restTemplate;

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Value("${Sms.url}")
    private String SMS_API_URL;

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
    public void logout(Long userId) {
        StpUtil.logout(userId);
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
    public boolean verifyCode(String email, String code) {
        // 从Redis获取验证码
        String redisKey = VERIFICATION_CODE_PREFIX + email;
        String savedCode = redisTemplate.opsForValue().get(redisKey);
        
        if (savedCode == null) {
            log.warn("验证码已过期或不存在, email: {}", email);
            return false;
        }
        
        return savedCode.equals(code);
    }

    /**
     * 发送短信请求
     *
     * @param phone 手机号
     * @param code 验证码
     */
    private void sendSmsRequest(String phone, String code) {
        // 构建请求体（表单格式）
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("key1", "GoAbroad");
        params.add("key2", code);
        params.add("key3", "3");
        params.add("targets", phone);

        // 设置请求头
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, headers);
        restTemplate.exchange(SMS_API_URL, HttpMethod.POST, requestEntity, String.class);
    }

    /**
     * 生成随机用户名
     *
     * @return 随机用户名
     */
    private String generateRandomUsername() {
        long timestamp = System.currentTimeMillis();
        String timestampSuffix = String.valueOf(timestamp).substring(7); // 取后6位
        String randomSuffix = RandomUtil.randomNumbers(4);
        return "user_" + timestampSuffix + "_" + randomSuffix;
    }
}
