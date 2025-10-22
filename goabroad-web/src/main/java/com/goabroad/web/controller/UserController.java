package com.goabroad.web.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户控制器
 * 
 * 职责：
 * 1. 接收HTTP请求
 * 2. 参数校验
 * 3. 调用Service层
 * 4. 返回统一响应
 * 
 * 注意：不包含任何业务逻辑！
 * 
 * @author GoAbroad Team
 * @version 1.0
 * @since 2024-10-19
 */
@RestController
@RequestMapping("/api/v1/users")
@Tag(name = "用户管理", description = "用户信息管理相关接口")
@Slf4j
public class UserController {}

