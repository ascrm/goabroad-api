package com.goabroad.web.exception;

import com.goabroad.common.exception.BusinessException;
import com.goabroad.common.pojo.Result;
import com.goabroad.common.pojo.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import java.util.stream.Collectors;

/**
 * 全局异常处理器
 * 统一处理系统异常，返回统一格式的错误响应
 * 
 * @author GoAbroad Team
 * @version 1.0
 * @since 2024-10-19
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    
    /**
     * 处理业务异常
     */
    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(HttpStatus.OK)
    public Result<?> handleBusinessException(BusinessException e) {
        log.warn("业务异常: code={}, message={}", e.getCode(), e.getMessage());
        return Result.error(e.getCode(), e.getMessage());
    }
    
    /**
     * 处理参数校验异常（@RequestBody 参数）
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        String message = e.getBindingResult().getFieldErrors().stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining("; "));
        log.warn("参数校验失败: {}", message);
        return Result.error(ResultCode.VALIDATION_FAILED.getCode(), message);
    }
    
    /**
     * 处理参数绑定异常（@ModelAttribute 参数）
     */
    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result<?> handleBindException(BindException e) {
        String message = e.getFieldErrors().stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining("; "));
        log.warn("参数绑定失败: {}", message);
        return Result.error(ResultCode.VALIDATION_FAILED.getCode(), message);
    }
    
    /**
     * 处理约束违反异常（@Valid 参数）
     */
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result<?> handleConstraintViolationException(ConstraintViolationException e) {
        String message = e.getConstraintViolations().stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining("; "));
        log.warn("约束违反: {}", message);
        return Result.error(ResultCode.VALIDATION_FAILED.getCode(), message);
    }
    
    /**
     * 处理参数类型不匹配异常
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result<?> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        String message = String.format("参数 '%s' 类型不匹配，期望类型: %s", 
                e.getName(), 
                e.getRequiredType() != null ? e.getRequiredType().getSimpleName() : "未知");
        log.warn("参数类型不匹配: {}", message);
        return Result.error(ResultCode.PARAMETER_INVALID.getCode(), message);
    }
    
    /**
     * 处理404异常
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Result<?> handleNoHandlerFoundException(NoHandlerFoundException e) {
        log.warn("请求的资源不存在: {}", e.getRequestURL());
        return Result.error(ResultCode.NOT_FOUND.getCode(), "请求的资源不存在");
    }
    
    /**
     * 处理 Sa-Token 认证异常
     */
    @ExceptionHandler(cn.dev33.satoken.exception.NotLoginException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Result<?> handleNotLoginException(cn.dev33.satoken.exception.NotLoginException e) {
        log.warn("未登录或Token已过期: {}", e.getMessage());
        
        // 根据不同的异常类型返回不同的提示
        String message;
        switch (e.getType()) {
            case cn.dev33.satoken.exception.NotLoginException.NOT_TOKEN:
                message = "未提供登录凭证";
                break;
            case cn.dev33.satoken.exception.NotLoginException.INVALID_TOKEN:
                message = "登录凭证无效";
                break;
            case cn.dev33.satoken.exception.NotLoginException.TOKEN_TIMEOUT:
                message = "登录已过期，请重新登录";
                break;
            case cn.dev33.satoken.exception.NotLoginException.BE_REPLACED:
                message = "账号在其他地方登录，您已被踢下线";
                break;
            case cn.dev33.satoken.exception.NotLoginException.KICK_OUT:
                message = "您已被强制下线";
                break;
            default:
                message = "请先登录";
        }
        
        return Result.error(ResultCode.UNAUTHORIZED.getCode(), message);
    }
    
    /**
     * 处理 Sa-Token 权限异常
     */
    @ExceptionHandler(cn.dev33.satoken.exception.NotPermissionException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public Result<?> handleNotPermissionException(cn.dev33.satoken.exception.NotPermissionException e) {
        log.warn("权限不足: {}", e.getMessage());
        return Result.error(ResultCode.FORBIDDEN.getCode(), "权限不足，无法访问");
    }
    
    /**
     * 处理 Sa-Token 角色异常
     */
    @ExceptionHandler(cn.dev33.satoken.exception.NotRoleException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public Result<?> handleNotRoleException(cn.dev33.satoken.exception.NotRoleException e) {
        log.warn("角色权限不足: {}", e.getMessage());
        return Result.error(ResultCode.FORBIDDEN.getCode(), "角色权限不足，无法访问");
    }
    
    /**
     * 处理空指针异常
     */
    @ExceptionHandler(NullPointerException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Result<?> handleNullPointerException(NullPointerException e) {
        log.error("空指针异常", e);
        return Result.error(ResultCode.ERROR.getCode(), "系统内部错误");
    }
    
    /**
     * 处理非法参数异常
     */
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result<?> handleIllegalArgumentException(IllegalArgumentException e) {
        log.warn("非法参数异常: {}", e.getMessage());
        return Result.error(ResultCode.PARAMETER_INVALID.getCode(), e.getMessage());
    }
    
    /**
     * 处理所有未捕获的异常
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Result<?> handleException(Exception e) {
        log.error("系统异常", e);
        return Result.error(ResultCode.ERROR.getCode(), "系统错误，请稍后重试");
    }
}

