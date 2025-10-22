package com.goabroad.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 响应状态码枚举
 * <p>
 * 定义系统中所有可能的响应状态码和对应的提示信息
 * 遵循HTTP状态码规范
 * 
 * @author GoAbroad Team
 * @version 1.0
 * @since 2024-10-19
 */
@Getter
@AllArgsConstructor
public enum ResponseCode {

    // ==================== 成功响应 2xx ====================
    /**
     * 操作成功
     */
    SUCCESS(200, "操作成功"),

    /**
     * 创建成功
     */
    CREATED(201, "创建成功"),

    // ==================== 客户端错误 4xx ====================
    /**
     * 请求参数错误
     */
    BAD_REQUEST(400, "请求参数错误"),

    /**
     * 未授权（未登录）
     */
    UNAUTHORIZED(401, "未授权，请先登录"),

    /**
     * 禁止访问（无权限）
     */
    FORBIDDEN(403, "无权限访问该资源"),

    /**
     * 资源不存在
     */
    NOT_FOUND(404, "请求的资源不存在"),

    /**
     * 资源冲突
     */
    CONFLICT(409, "资源冲突，操作无法完成"),

    /**
     * 请求过于频繁
     */
    TOO_MANY_REQUESTS(429, "请求过于频繁，请稍后重试"),

    // ==================== 服务器错误 5xx ====================
    /**
     * 服务器内部错误
     */
    INTERNAL_SERVER_ERROR(500, "服务器内部错误"),

    /**
     * 服务不可用
     */
    SERVICE_UNAVAILABLE(503, "服务暂时不可用，请稍后重试"),

    // ==================== 业务错误码 1xxx ====================
    /**
     * 业务逻辑错误（通用）
     */
    BUSINESS_ERROR(1000, "业务处理失败"),

    /**
     * 数据验证失败
     */
    VALIDATION_ERROR(1001, "数据验证失败"),

    /**
     * 数据已存在
     */
    DATA_ALREADY_EXISTS(1002, "数据已存在"),

    /**
     * 数据不存在
     */
    DATA_NOT_FOUND(1003, "数据不存在"),

    /**
     * 数据状态异常
     */
    DATA_STATUS_ERROR(1004, "数据状态异常，无法操作"),

    /**
     * 操作频繁
     */
    OPERATION_TOO_FREQUENT(1005, "操作过于频繁，请稍后再试"),

    // ==================== 用户相关错误 2xxx ====================
    /**
     * 用户不存在
     */
    USER_NOT_FOUND(2001, "用户不存在"),

    /**
     * 用户名或密码错误
     */
    USER_PASSWORD_ERROR(2002, "用户名或密码错误"),

    /**
     * 用户已存在
     */
    USER_ALREADY_EXISTS(2003, "用户已存在"),

    /**
     * 用户已被禁用
     */
    USER_DISABLED(2004, "用户已被禁用"),

    /**
     * 用户未激活
     */
    USER_NOT_ACTIVATED(2005, "用户未激活"),

    /**
     * 密码过于简单
     */
    PASSWORD_TOO_WEAK(2006, "密码过于简单，请使用更复杂的密码"),

    /**
     * 原密码错误
     */
    OLD_PASSWORD_ERROR(2007, "原密码错误"),

    /**
     * 邮箱已被使用
     */
    EMAIL_ALREADY_USED(2008, "邮箱已被使用"),

    /**
     * 手机号已被使用
     */
    PHONE_ALREADY_USED(2009, "手机号已被使用"),

    // ==================== Token相关错误 3xxx ====================
    /**
     * Token无效
     */
    TOKEN_INVALID(3001, "Token无效"),

    /**
     * Token已过期
     */
    TOKEN_EXPIRED(3002, "Token已过期，请重新登录"),

    /**
     * Token缺失
     */
    TOKEN_MISSING(3003, "未提供Token"),

    /**
     * Token被禁用
     */
    TOKEN_DISABLED(3004, "Token已被禁用"),

    /**
     * 刷新Token失败
     */
    REFRESH_TOKEN_FAILED(3005, "刷新Token失败"),

    // ==================== 文件相关错误 4xxx ====================
    /**
     * 文件上传失败
     */
    FILE_UPLOAD_FAILED(4001, "文件上传失败"),

    /**
     * 文件类型不支持
     */
    FILE_TYPE_NOT_SUPPORTED(4002, "文件类型不支持"),

    /**
     * 文件大小超出限制
     */
    FILE_SIZE_EXCEEDED(4003, "文件大小超出限制"),

    /**
     * 文件不存在
     */
    FILE_NOT_FOUND(4004, "文件不存在"),

    /**
     * 文件下载失败
     */
    FILE_DOWNLOAD_FAILED(4005, "文件下载失败"),

    // ==================== 第三方服务错误 5xxx ====================
    /**
     * 第三方服务调用失败
     */
    THIRD_PARTY_SERVICE_ERROR(5001, "第三方服务调用失败"),

    /**
     * 短信发送失败
     */
    SMS_SEND_FAILED(5002, "短信发送失败"),

    /**
     * 邮件发送失败
     */
    EMAIL_SEND_FAILED(5003, "邮件发送失败"),

    /**
     * 支付服务异常
     */
    PAYMENT_SERVICE_ERROR(5004, "支付服务异常"),

    /**
     * 验证码错误
     */
    VERIFICATION_CODE_ERROR(5005, "验证码错误"),

    /**
     * 验证码已过期
     */
    VERIFICATION_CODE_EXPIRED(5006, "验证码已过期");

    /**
     * 响应码
     */
    private final Integer code;

    /**
     * 响应消息
     */
    private final String message;

    /**
     * 根据响应码获取枚举
     *
     * @param code 响应码
     * @return ResponseCode枚举，如果不存在返回null
     */
    public static ResponseCode getByCode(Integer code) {
        if (code == null) {
            return null;
        }
        for (ResponseCode responseCode : values()) {
            if (responseCode.getCode().equals(code)) {
                return responseCode;
            }
        }
        return null;
    }

    /**
     * 判断是否为成功状态码
     *
     * @return true表示成功，false表示失败
     */
    public boolean isSuccess() {
        return this.code != null && this.code == 200;
    }

    /**
     * 判断是否为客户端错误
     *
     * @return true表示客户端错误
     */
    public boolean isClientError() {
        return this.code != null && this.code >= 400 && this.code < 500;
    }

    /**
     * 判断是否为服务器错误
     *
     * @return true表示服务器错误
     */
    public boolean isServerError() {
        return this.code != null && this.code >= 500 && this.code < 600;
    }
}

