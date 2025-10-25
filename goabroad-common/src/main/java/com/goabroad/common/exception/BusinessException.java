package com.goabroad.common.exception;

import com.goabroad.common.pojo.ResultCode;
import lombok.Getter;

import java.io.Serial;

/**
 * 业务异常
 * 
 * @author GoAbroad Team
 * @version 1.0
 * @since 2024-10-19
 */
@Getter
public class BusinessException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;
    
    /**
     * 错误码
     */
    private final Integer code;
    
    /**
     * 错误消息
     */
    private final String message;
    
    public BusinessException(Integer code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }
    
    public BusinessException(ResultCode resultCode) {
        super(resultCode.getMessage());
        this.code = resultCode.getCode();
        this.message = resultCode.getMessage();
    }
    
    public BusinessException(ResultCode resultCode, String message) {
        super(message);
        this.code = resultCode.getCode();
        this.message = message;
    }
    
    /**
     * 快速创建业务异常
     */
    public static BusinessException of(ResultCode resultCode) {
        return new BusinessException(resultCode);
    }
    
    /**
     * 快速创建业务异常（自定义消息）
     */
    public static BusinessException of(ResultCode resultCode, String message) {
        return new BusinessException(resultCode, message);
    }
}
