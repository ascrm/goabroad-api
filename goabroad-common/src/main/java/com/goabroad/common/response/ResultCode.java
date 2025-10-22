package com.goabroad.common.response;

import lombok.Getter;

/**
 * 统一响应码枚举
 * 
 * @author GoAbroad Team
 * @version 1.0
 * @since 2024-10-19
 */
@Getter
public enum ResultCode {
    
    // ========== 通用状态码 ==========
    SUCCESS(200, "操作成功"),
    ERROR(500, "操作失败"),
    BAD_REQUEST(400, "请求参数错误"),
    UNAUTHORIZED(401, "未授权"),
    FORBIDDEN(403, "无权限访问"),
    NOT_FOUND(404, "资源不存在"),
    METHOD_NOT_ALLOWED(405, "请求方法不支持"),
    TOO_MANY_REQUESTS(429, "请求过于频繁"),
    
    // ========== 认证相关 1xxx ==========
    AUTH_LOGIN_FAILED(1001, "用户名或密码错误"),
    AUTH_TOKEN_INVALID(1002, "Token无效或已过期"),
    AUTH_TOKEN_EXPIRED(1003, "Token已过期"),
    AUTH_ACCOUNT_DISABLED(1004, "账号已被禁用"),
    AUTH_ACCOUNT_NOT_FOUND(1005, "账号不存在"),
    AUTH_USERNAME_EXISTS(1006, "用户名已存在"),
    AUTH_EMAIL_EXISTS(1007, "邮箱已被注册"),
    
    // ========== 用户相关 2xxx ==========
    USER_NOT_FOUND(2001, "用户不存在"),
    USER_ALREADY_FOLLOWED(2002, "已关注该用户"),
    USER_NOT_FOLLOWED(2003, "未关注该用户"),
    USER_CANNOT_FOLLOW_SELF(2004, "不能关注自己"),
    
    // ========== 国家相关 3xxx ==========
    COUNTRY_NOT_FOUND(3001, "国家信息不存在"),
    
    // ========== 规划相关 4xxx ==========
    PLAN_NOT_FOUND(4001, "规划不存在"),
    PLAN_ACCESS_DENIED(4002, "无权访问该规划"),
    MATERIAL_NOT_FOUND(4003, "材料不存在"),
    
    // ========== 社区相关 5xxx ==========
    POST_NOT_FOUND(5001, "帖子不存在"),
    POST_ACCESS_DENIED(5002, "无权操作该帖子"),
    COMMENT_NOT_FOUND(5003, "评论不存在"),
    COMMENT_ACCESS_DENIED(5004, "无权操作该评论"),
    ALREADY_LIKED(5005, "已点赞"),
    NOT_LIKED(5006, "未点赞"),
    ALREADY_COLLECTED(5007, "已收藏"),
    NOT_COLLECTED(5008, "未收藏"),
    
    // ========== 通知相关 6xxx ==========
    NOTIFICATION_NOT_FOUND(6001, "通知不存在"),
    REMINDER_NOT_FOUND(6002, "提醒不存在"),
    
    // ========== 文件相关 7xxx ==========
    FILE_UPLOAD_FAILED(7001, "文件上传失败"),
    FILE_TYPE_NOT_SUPPORTED(7002, "不支持的文件类型"),
    FILE_SIZE_EXCEEDED(7003, "文件大小超过限制"),
    FILE_NOT_FOUND(7004, "文件不存在"),
    
    // ========== 数据验证相关 8xxx ==========
    VALIDATION_FAILED(8001, "数据验证失败"),
    PARAMETER_MISSING(8002, "缺少必要参数"),
    PARAMETER_INVALID(8003, "参数格式不正确");
    
    private final Integer code;
    private final String message;
    
    ResultCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}

