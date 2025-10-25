package com.goabroad.web.interceptor;

import cn.dev33.satoken.stp.StpUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * 请求日志拦截器
 * 自动记录所有 API 请求的详细信息
 * 
 * @author GoAbroad Team
 * @version 1.0
 * @since 2024-10-25
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class LoggingInterceptor implements HandlerInterceptor {
    
    private static final String START_TIME = "startTime";
    private final ObjectMapper objectMapper;
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // 记录请求开始时间
        request.setAttribute(START_TIME, System.currentTimeMillis());
        
        // 获取请求信息
        String method = request.getMethod();
        String uri = request.getRequestURI();
        String queryString = request.getQueryString();
        String fullUrl = queryString != null ? uri + "?" + queryString : uri;
        
        // 获取当前登录用户ID（如果已登录）
        String userId = "未登录";
        if (StpUtil.isLogin()) {
            userId = StpUtil.getLoginIdAsString();
        }
        
        // 获取请求头
        Map<String, String> headers = new HashMap<>();
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            // 过滤敏感信息
            if (!headerName.equalsIgnoreCase("authorization") && 
                !headerName.equalsIgnoreCase("cookie")) {
                headers.put(headerName, request.getHeader(headerName));
            }
        }
        
        // 获取请求参数
        Map<String, String[]> params = request.getParameterMap();
        
        log.info("==================== 请求开始 ====================");
        log.info("请求方法: {}", method);
        log.info("请求地址: {}", fullUrl);
        log.info("客户端IP: {}", getClientIP(request));
        log.info("用户ID: {}", userId);
        
        if (!params.isEmpty()) {
            log.info("请求参数: {}", formatParams(params));
        }
        
        // 如果是 POST/PUT/PATCH 请求，尝试记录请求体
        if ("POST".equalsIgnoreCase(method) || 
            "PUT".equalsIgnoreCase(method) || 
            "PATCH".equalsIgnoreCase(method)) {
            
            String contentType = request.getContentType();
            if (contentType != null && contentType.contains("application/json")) {
                // 请求体的内容会在 postHandle 中记录
                log.info("请求类型: application/json");
            }
        }
        
        return true;
    }
    
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
        // 此方法在控制器方法执行后调用
    }
    
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        Long startTime = (Long) request.getAttribute(START_TIME);
        if (startTime != null) {
            long duration = System.currentTimeMillis() - startTime;
            
            String method = request.getMethod();
            String uri = request.getRequestURI();
            int status = response.getStatus();
            
            if (ex != null) {
                log.error("==================== 请求异常 ====================");
                log.error("请求方法: {}", method);
                log.error("请求地址: {}", uri);
                log.error("响应状态: {}", status);
                log.error("执行时长: {}ms", duration);
                log.error("异常信息: ", ex);
            } else {
                log.info("==================== 请求结束 ====================");
                log.info("响应状态: {}", status);
                log.info("执行时长: {}ms", duration);
            }
            
            log.info("==================================================");
        }
    }
    
    /**
     * 获取客户端真实IP
     */
    private String getClientIP(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
    
    /**
     * 格式化请求参数
     */
    private String formatParams(Map<String, String[]> params) {
        Map<String, Object> result = new HashMap<>();
        params.forEach((key, value) -> {
            if (value.length == 1) {
                result.put(key, value[0]);
            } else {
                result.put(key, value);
            }
        });
        
        try {
            return objectMapper.writeValueAsString(result);
        } catch (Exception e) {
            return params.toString();
        }
    }
}

