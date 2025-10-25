package com.goabroad.common.pojo;

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
    
    // ========== 认证相关 10xxx ==========
    EMAIL_ALREADY_EXISTS(10001, "邮箱已被注册"),
    PHONE_ALREADY_EXISTS(10002, "手机号已被注册"),
    ACCOUNT_OR_PASSWORD_ERROR(10003, "账号或密码错误"),
    VERIFICATION_CODE_ERROR(10004, "验证码错误或已过期"),
    TOKEN_EXPIRED(10005, "Token已过期"),
    TOKEN_INVALID(10006, "Token无效"),
    ACCOUNT_BANNED(10007, "账号已被禁用"),
    ACCOUNT_NOT_ACTIVATED(10008, "账号未激活"),
    THIRD_PARTY_LOGIN_FAILED(10009, "第三方登录失败"),
    EMAIL_FORMAT_ERROR(10010, "邮箱格式错误"),
    PASSWORD_STRENGTH_INSUFFICIENT(10011, "密码强度不足"),
    VERIFICATION_CODE_SEND_FREQUENTLY(10012, "验证码发送过于频繁"),
    
    // ========== 用户相关 20xxx ==========
    USER_NOT_FOUND(20001, "用户不存在"),
    USER_ALREADY_FOLLOWED(20002, "用户已被关注"),
    USER_NOT_FOLLOWED(20003, "用户未被关注"),
    USER_CANNOT_FOLLOW_SELF(20004, "不能关注自己"),
    NICKNAME_ALREADY_USED(20005, "昵称已被使用"),
    NICKNAME_CONTAINS_SENSITIVE_WORDS(20006, "昵称包含敏感词"),
    
    // ========== 社区相关 30xxx ==========
    POST_NOT_FOUND(30001, "帖子不存在"),
    COMMENT_NOT_FOUND(30002, "评论不存在"),
    POST_EDIT_DENIED(30003, "无权限编辑此帖子"),
    POST_DELETE_DENIED(30004, "无权限删除此帖子"),
    POST_DELETED(30005, "帖子已被删除"),
    CONTENT_CONTAINS_SENSITIVE_WORDS(30006, "内容包含敏感词"),
    IMAGE_COUNT_EXCEEDED(30007, "图片数量超过限制"),
    VIDEO_SIZE_EXCEEDED(30008, "视频大小超过限制"),
    POST_ALREADY_LIKED(30009, "帖子已被点赞"),
    POST_NOT_LIKED(30010, "帖子未被点赞"),
    POST_ALREADY_COLLECTED(30011, "帖子已被收藏"),
    POST_NOT_COLLECTED(30012, "帖子未被收藏"),
    
    // ========== 规划相关 40xxx ==========
    PLAN_NOT_FOUND(40001, "规划不存在"),
    TASK_NOT_FOUND(40002, "任务不存在"),
    MATERIAL_NOT_FOUND(40003, "材料不存在"),
    PLAN_ACCESS_DENIED(40004, "无权限访问此规划"),
    PLAN_COUNT_EXCEEDED(40005, "规划数量已达上限"),
    FILE_NOT_FOUND(40006, "文件不存在"),
    FILE_SIZE_EXCEEDED(40007, "文件大小超过限制"),
    FILE_TYPE_NOT_SUPPORTED(40008, "不支持的文件格式"),
    
    // ========== 国家相关 50xxx ==========
    COUNTRY_NOT_FOUND(50001, "国家不存在"),
    COUNTRY_ALREADY_FAVORITED(50002, "国家已被收藏"),
    COUNTRY_NOT_FAVORITED(50003, "国家未被收藏"),
    
    // ========== 文件上传相关 60xxx ==========
    FILE_EMPTY(60001, "文件不能为空"),
    FILE_SIZE_EXCEEDED_LIMIT(60002, "文件大小超过限制"),
    FILE_TYPE_NOT_SUPPORTED_UPLOAD(60003, "不支持的文件类型"),
    FILE_UPLOAD_FAILED(60004, "上传失败"),
    FILE_DELETED(60005, "文件已被删除"),
    
    // ========== 系统相关 90xxx ==========
    SYSTEM_MAINTENANCE(90001, "系统维护中"),
    API_DEPRECATED(90002, "接口已废弃"),
    REQUEST_TOO_FREQUENTLY(90003, "请求过于频繁"),
    VALIDATION_FAILED(90004, "参数验证失败"),
    DATABASE_ERROR(90005, "数据库错误"),
    THIRD_PARTY_SERVICE_ERROR(90006, "第三方服务错误");
    
    private final Integer code;
    private final String message;
    
    ResultCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}

