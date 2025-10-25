package com.goabroad.common.constant;

/**
 * 应用常量类
 * <p>
 * 定义系统中使用的所有常量
 * 
 * @author GoAbroad Team
 * @version 1.0
 * @since 2024-10-19
 */
public final class AppConstant {

    /**
     * 私有构造方法，防止实例化
     */
    private AppConstant() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    // ==================== 认证相关常量 ====================

    /**
     * Token请求头名称
     */
    public static final String TOKEN_HEADER = "Authorization";

    /**
     * Token前缀
     */
    public static final String TOKEN_PREFIX = "Bearer ";

    /**
     * Token类型
     */
    public static final String TOKEN_TYPE = "Bearer";

    /**
     * 登录用户ID键名（用于Sa-Token存储）
     */
    public static final String LOGIN_USER_KEY = "userId";

    /**
     * 登录用户名键名
     */
    public static final String LOGIN_USERNAME_KEY = "username";

    /**
     * 登录用户角色键名
     */
    public static final String LOGIN_USER_ROLE_KEY = "role";

    /**
     * Token有效期（7天，单位：秒）
     */
    public static final long TOKEN_EXPIRE_TIME = 7 * 24 * 60 * 60;

    /**
     * 刷新Token有效期（30天，单位：秒）
     */
    public static final long REFRESH_TOKEN_EXPIRE_TIME = 30 * 24 * 60 * 60;

    // ==================== 分页相关常量 ====================

    /**
     * 默认页码
     */
    public static final int DEFAULT_PAGE_NUM = 1;

    /**
     * 默认每页大小
     */
    public static final int DEFAULT_PAGE_SIZE = 10;

    /**
     * 最大每页大小
     */
    public static final int MAX_PAGE_SIZE = 100;

    /**
     * 最小每页大小
     */
    public static final int MIN_PAGE_SIZE = 1;

    // ==================== 缓存相关常量 ====================

    /**
     * 缓存键前缀 - 用户信息
     */
    public static final String CACHE_USER_PREFIX = "user:";

    /**
     * 缓存键前缀 - 国家信息
     */
    public static final String CACHE_COUNTRY_PREFIX = "country:";

    /**
     * 缓存键前缀 - 验证码
     */
    public static final String CACHE_CAPTCHA_PREFIX = "captcha:";

    /**
     * 缓存键前缀 - 短信验证码
     */
    public static final String CACHE_SMS_CODE_PREFIX = "sms:code:";

    /**
     * 缓存键前缀 - 邮箱验证码
     */
    public static final String CACHE_EMAIL_CODE_PREFIX = "email:code:";

    /**
     * 缓存键前缀 - Token黑名单
     */
    public static final String CACHE_TOKEN_BLACKLIST_PREFIX = "token:blacklist:";

    /**
     * 缓存键前缀 - 限流
     */
    public static final String CACHE_RATE_LIMIT_PREFIX = "rate:limit:";

    /**
     * 默认缓存过期时间（1小时，单位：秒）
     */
    public static final long DEFAULT_CACHE_EXPIRE_TIME = 60 * 60;

    /**
     * 短缓存过期时间（5分钟，单位：秒）
     */
    public static final long SHORT_CACHE_EXPIRE_TIME = 5 * 60;

    /**
     * 长缓存过期时间（1天，单位：秒）
     */
    public static final long LONG_CACHE_EXPIRE_TIME = 24 * 60 * 60;

    /**
     * 验证码过期时间（5分钟，单位：秒）
     */
    public static final long CAPTCHA_EXPIRE_TIME = 5 * 60;

    // ==================== 文件上传相关常量 ====================

    /**
     * 文件上传最大大小（10MB，单位：字节）
     */
    public static final long MAX_FILE_SIZE = 10 * 1024 * 1024;

    /**
     * 图片文件最大大小（5MB，单位：字节）
     */
    public static final long MAX_IMAGE_SIZE = 5 * 1024 * 1024;

    /**
     * 文档文件最大大小（20MB，单位：字节）
     */
    public static final long MAX_DOCUMENT_SIZE = 20 * 1024 * 1024;

    /**
     * 允许上传的图片格式
     */
    public static final String[] ALLOWED_IMAGE_TYPES = {
            "image/jpeg", "image/jpg", "image/png", "image/gif", "image/webp"
    };

    /**
     * 允许上传的文档格式
     */
    public static final String[] ALLOWED_DOCUMENT_TYPES = {
            "application/pdf",
            "application/msword",
            "application/vnd.openxmlformats-officedocument.wordprocessingml.document",
            "application/vnd.ms-excel",
            "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
    };

    // ==================== 用户相关常量 ====================

    /**
     * 密码最小长度
     */
    public static final int PASSWORD_MIN_LENGTH = 6;

    /**
     * 密码最大长度
     */
    public static final int PASSWORD_MAX_LENGTH = 20;

    /**
     * 用户名最小长度
     */
    public static final int USERNAME_MIN_LENGTH = 3;

    /**
     * 用户名最大长度
     */
    public static final int USERNAME_MAX_LENGTH = 20;

    /**
     * 昵称最大长度
     */
    public static final int NICKNAME_MAX_LENGTH = 50;

    /**
     * 默认用户头像
     */
    public static final String DEFAULT_AVATAR = "/images/default-avatar.png";

    /**
     * 默认用户等级
     */
    public static final int DEFAULT_USER_LEVEL = 1;

    /**
     * 默认用户积分
     */
    public static final int DEFAULT_USER_POINTS = 0;

    // ==================== 验证码相关常量 ====================

    /**
     * 验证码长度
     */
    public static final int CAPTCHA_LENGTH = 6;

    /**
     * 短信验证码长度
     */
    public static final int SMS_CODE_LENGTH = 6;

    /**
     * 邮箱验证码长度
     */
    public static final int EMAIL_CODE_LENGTH = 6;

    /**
     * 验证码重新发送间隔（60秒）
     */
    public static final int VERIFY_CODE_RESEND_INTERVAL = 60;

    // ==================== 限流相关常量 ====================

    /**
     * 默认限流次数（每分钟）
     */
    public static final int DEFAULT_RATE_LIMIT = 60;

    /**
     * 登录限流次数（每分钟）
     */
    public static final int LOGIN_RATE_LIMIT = 5;

    /**
     * 注册限流次数（每小时）
     */
    public static final int REGISTER_RATE_LIMIT = 3;

    /**
     * 发送验证码限流次数（每小时）
     */
    public static final int SEND_CODE_RATE_LIMIT = 10;

    // ==================== 正则表达式常量 ====================
    // 注意：通用的手机号、邮箱、身份证验证请使用 Hutool 的 Validator 工具类
    // import cn.hutool.core.lang.Validator;
    // Validator.isMobile(phone), Validator.isEmail(email), Validator.isIdCard(idCard)

    /**
     * 用户名正则表达式（字母、数字、下划线，3-20位）
     * 业务特定规则，不使用通用验证器
     */
    public static final String USERNAME_REGEX = "^[a-zA-Z0-9_]{3,20}$";

    // ==================== 业务相关常量 ====================

    /**
     * 系统管理员角色
     */
    public static final String ROLE_ADMIN = "ADMIN";

    /**
     * 普通用户角色
     */
    public static final String ROLE_USER = "USER";

    /**
     * VIP用户角色
     */
    public static final String ROLE_VIP = "VIP";

    /**
     * 用户状态 - 正常
     */
    public static final String USER_STATUS_ACTIVE = "ACTIVE";

    /**
     * 用户状态 - 禁用
     */
    public static final String USER_STATUS_DISABLED = "DISABLED";

    /**
     * 用户状态 - 未激活
     */
    public static final String USER_STATUS_INACTIVE = "INACTIVE";

    /**
     * 默认排序字段
     */
    public static final String DEFAULT_SORT_FIELD = "createdAt";

    /**
     * 默认排序方向（降序）
     */
    public static final String DEFAULT_SORT_DIRECTION = "DESC";
}

