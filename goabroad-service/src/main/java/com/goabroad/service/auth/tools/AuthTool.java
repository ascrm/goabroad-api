package com.goabroad.service.auth.tools;

import cn.hutool.core.util.RandomUtil;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Component
public class AuthTool {

    @Value("${Sms.url}")
    private String SMS_API_URL;

    @Resource
    private RestTemplate restTemplate;

    /**
     * 发送短信请求
     *
     * @param phone 手机号
     * @param code 验证码
     */
    public void sendSmsRequest(String phone, String code) {
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
    public String generateRandomUsername() {
        long timestamp = System.currentTimeMillis();
        String timestampSuffix = String.valueOf(timestamp).substring(7); // 取后6位
        String randomSuffix = RandomUtil.randomNumbers(4);
        return "user_" + timestampSuffix + "_" + randomSuffix;
    }
}
