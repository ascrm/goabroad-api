[5 tools called]

好的，我已经阅读了产品设计文档。现在为您重新编写一份简洁、清晰的后端架构文档。

---

# 🏗️ GoAbroad 后端架构设计文档

**项目名称**: GoAbroad 出国助手 - 后端服务  
**架构类型**: Spring Boot 单体应用  
**技术栈**: Spring Boot 3.x + Java 17 + Maven  
**文档版本**: v2.0  
**编写日期**: 2024年10月

---

## 📑 目录

1. [项目概述](#1-项目概述)
2. [架构设计原则](#2-架构设计原则)
3. [技术架构](#3-技术架构)
4. [模块划分](#4-模块划分)
5. [分层架构](#5-分层架构)
6. [核心功能模块](#6-核心功能模块)
7. [数据流转](#7-数据流转)
8. [技术选型](#8-技术选型)
9. [部署架构](#9-部署架构)
10. [开发规范](#10-开发规范)

---

## 1. 项目概述

### 1.1 项目背景

GoAbroad是一款面向出国人群的一站式助手应用，帮助用户规划留学、工作、移民全流程，提供国家信息查询、时间线规划、材料管理、经验社区等功能。

### 1.2 核心目标

- 为用户提供个性化的出国准备时间线
- 提供全面的国家信息和政策指南
- 搭建真实的用户经验分享社区
- 提供实用的工具集（费用计算、GPA转换等）
- 智能提醒和进度管理

### 1.3 用户角色

| 角色 | 说明 |
|-----|------|
| **普通用户** | 使用规划、工具、社区功能 |
| **管理员** | 管理内容、用户、系统配置 |
| **系统** | 自动化任务、通知、数据统计 |

---

## 2. 架构设计原则

### 2.1 核心原则

**简单性（Simplicity）**
- 采用单体架构，避免过度设计
- 清晰的分层结构，职责分明
- 模块数量控制在合理范围

**可维护性（Maintainability）**
- 代码结构清晰，命名规范
- 低耦合高内聚
- 完善的注释和文档

**可扩展性（Scalability）**
- 模块化设计，便于功能扩展
- 预留接口，方便后期优化
- 数据库设计考虑未来增长

**安全性（Security）**
- JWT身份认证
- 接口权限控制
- 数据加密存储
- 防止常见Web攻击

---

## 3. 技术架构

### 3.1 整体架构图

```
┌─────────────────────────────────────────────┐
│              客户端层 (Client)               │
│         iOS App | Android App | Web         │
└─────────────────┬───────────────────────────┘
                  │ HTTPS / RESTful API
                  ↓
┌─────────────────────────────────────────────┐
│            API网关/反向代理层                │
│            Nginx / API Gateway              │
└─────────────────┬───────────────────────────┘
                  │
                  ↓
┌─────────────────────────────────────────────┐
│          Spring Boot 应用层                  │
│  ┌────────────────────────────────────────┐ │
│  │        Controller 控制器层              │ │
│  │   (接收请求、参数验证、返回响应)         │ │
│  └────────────────┬───────────────────────┘ │
│                   │                          │
│  ┌────────────────┴───────────────────────┐ │
│  │         Service 业务逻辑层              │ │
│  │   (业务处理、事务管理、规则验证)         │ │
│  └────────────────┬───────────────────────┘ │
│                   │                          │
│  ┌────────────────┴───────────────────────┐ │
│  │       Repository 数据访问层             │ │
│  │       (数据库操作、缓存操作)             │ │
│  └──────────────────────┬─────────────────┘ │
└─────────────────────────┼────────────────────┘
                          |
        ┌─────────────────┼──────────────┐
        │                 │              │
        ↓                 ↓              ↓
   ┌────────────┐    ┌────────┐     ┌────────┐
   │ PostgreSQL │    │  Redis │     │  Minio │
   │    主数据   │    │  缓存   │     │ 文件   │
   └────────────┘    └────────┘     └────────┘
```

### 3.2 架构说明

**单体应用优势**
- 开发简单，易于调试
- 部署方便，运维成本低
- 适合中小规模应用
- 满足毕业设计要求

**核心组件**
- **Spring Boot**: 应用框架
- **Spring MVC**: Web层框架
- **Spring Data JPA**: 数据访问
- **SaToken**: 安全认证
- **PostgreSQL**: 关系型数据库
- **Redis**: 缓存和会话
- **Minio**: 对象存储（图片、文件）

---

## 4. 模块划分

### 4.1 Maven模块结构

采用**Maven多模块**设计，共分为**4个核心模块**：

```
goabroad-api (父工程)
│
├── goabroad-common          # 公共模块
│   └── 工具类、常量、枚举、异常、统一响应
│
├── goabroad-model           # 数据模型模块
│   └── 实体类(Entity)、DTO、VO、Mapper
│
├── goabroad-service         # 业务逻辑模块
│   └── Service接口、ServiceImpl、Repository
│
└── goabroad-web             # Web入口模块
    └── Controller、配置类、过滤器、启动类
```

### 4.2 模块职责

| 模块 | 职责 | 主要内容 | 对外依赖 |
|------|------|----------|---------|
| **goabroad-common** | 公共基础 | 工具类、常量、枚举、自定义异常、统一响应格式 | 无 |
| **goabroad-model** | 数据模型 | Entity实体类、DTO传输对象、VO视图对象、MapStruct映射器 | common |
| **goabroad-service** | 业务处理 | Service业务逻辑、Repository数据访问、JWT工具、第三方集成 | model, common |
| **goabroad-web** | Web入口 | Controller接口、全局异常处理、配置类、启动类 | service, model, common |

### 4.3 模块依赖关系

```
goabroad-web (启动模块)
    ↓ 依赖
goabroad-service (业务模块)
    ↓ 依赖
goabroad-model (模型模块)
    ↓ 依赖
goabroad-common (公共模块)
```

**依赖规则**：
- ✅ 允许：高层模块依赖低层模块
- ❌ 禁止：低层模块依赖高层模块（避免循环依赖）
- ❌ 禁止：跨层依赖（如web直接依赖model而不通过service）

---

## 5. 分层架构

### 5.1 四层架构

```
┌─────────────────────────────────────────┐
│  Controller层 (表现层)                   │
│  - 接收HTTP请求                          │
│  - 参数验证                              │
│  - 调用Service                           │
│  - 返回统一格式响应                       │
└─────────────────┬───────────────────────┘
                  │
┌─────────────────┴───────────────────────┐
│  Service层 (业务逻辑层)                  │
│  - 核心业务逻辑                          │
│  - 事务管理                              │
│  - 业务规则验证                          │
│  - 对象转换(Entity ↔ DTO)               │
└─────────────────┬───────────────────────┘
                  │
┌─────────────────┴───────────────────────┐
│  Repository层 (数据访问层)               │
│  - 数据库CRUD操作                        │
│  - 自定义查询                            │
│  - 缓存操作                              │
└─────────────────┬───────────────────────┘
                  │
┌─────────────────┴───────────────────────┐
│  Entity层 (实体层)                       │
│  - JPA实体类                             │
│  - 数据库表映射                          │
└─────────────────────────────────────────┘
```

### 5.2 跨层组件

**Config（配置组件）**
- Spring Security配置
- Redis配置
- Swagger配置
- CORS跨域配置
- 异步任务配置

**Exception Handler（异常处理）**
- 全局异常捕获
- 统一错误响应
- 日志记录

**Aspect（切面）**
- 操作日志切面
- 性能监控切面
- 参数校验切面

---

## 6. 核心功能模块

### 6.1 功能模块概览

| 模块 | 功能说明 | 核心实体 |
|------|---------|---------|
| **认证授权** | 用户注册、登录、JWT管理 | User, UserOAuth |
| **用户管理** | 个人信息、偏好设置、账户管理 | User, UserProfile |
| **国家信息** | 国家详情、政策、院校、签证信息 | Country, Policy, Institution |
| **规划管理** | 时间线规划、任务管理、进度跟踪 | Plan, Stage, Milestone, Task |
| **材料管理** | 材料清单、上传、状态管理 | MaterialChecklist, MaterialUpload |
| **社区模块** | 帖子、评论、点赞、收藏 | Post, Comment, Like, Collection |
| **通知提醒** | 系统通知、任务提醒、消息推送 | Notification, Reminder |
| **工具集** | 费用计算、GPA转换、签证查询 | 无独立实体（工具类） |
| **管理后台** | 内容管理、用户管理、系统配置 | Admin, SystemConfig |

### 6.2 模块服务清单

#### 认证授权模块 (auth)
- `AuthService`: 登录、注册、登出、刷新Token
- `OAuthService`: 第三方登录（微信、QQ、Apple）
- `JwtTokenProvider`: JWT生成和验证

#### 用户模块 (user)
- `UserService`: 用户CRUD、信息更新
- `UserProfileService`: 用户画像、偏好设置
- `UserLevelService`: 等级、积分管理

#### 国家信息模块 (country)
- `CountryService`: 国家列表、详情、搜索
- `PolicyService`: 政策信息管理
- `InstitutionService`: 院校信息

#### 规划模块 (planning)
- `PlanService`: 规划创建、更新、删除
- `TimelineService`: 时间线生成和管理
- `TaskService`: 任务管理、完成状态
- `ProgressService`: 进度计算

#### 材料模块 (material)
- `MaterialService`: 材料清单管理
- `FileStorageService`: 文件上传、下载、删除

#### 社区模块 (community)
- `PostService`: 帖子发布、编辑、删除
- `CommentService`: 评论管理
- `InteractionService`: 点赞、收藏、分享

#### 通知模块 (notification)
- `NotificationService`: 系统通知
- `ReminderService`: 定时提醒
- `PushService`: 消息推送

#### 工具模块 (tool)
- `CostCalculatorService`: 费用计算
- `GpaConverterService`: GPA转换
- `VisaToolService`: 签证查询工具

#### 管理模块 (admin)
- `AdminService`: 管理员管理
- `ContentModerationService`: 内容审核
- `SystemConfigService`: 系统配置

---

## 7. 数据流转

### 7.1 请求处理流程

```
客户端发起请求
    ↓
Nginx反向代理
    ↓
Spring Boot应用
    ↓
过滤器链 (Filter Chain)
    - JWTAuthenticationFilter: JWT验证
    - CorsFilter: 跨域处理
    - RequestLogFilter: 请求日志
    ↓
拦截器 (Interceptor)
    - RateLimitInterceptor: 限流
    - PermissionInterceptor: 权限检查
    ↓
Controller层
    - 参数接收 (@RequestBody, @RequestParam)
    - 参数验证 (@Valid)
    - 调用Service
    ↓
Service层
    - 业务逻辑处理
    - 事务管理 (@Transactional)
    - Entity ↔ DTO 转换
    - 调用Repository
    ↓
Repository层
    - 数据库查询 (JPA)
    - 缓存操作 (Redis)
    ↓
返回结果
    - Entity → DTO转换
    - 封装统一响应格式 Result<T>
    ↓
响应给客户端
```

### 7.2 数据对象转换

```
客户端 → Controller
    传输: Request DTO (JSON)
    示例: LoginRequest, CreatePlanRequest

Controller → Service
    传输: Request DTO
    验证: @Valid注解校验

Service → Repository
    传输: Entity对象
    转换: DTO → Entity (通过MapStruct)

Repository → Database
    传输: Entity对象
    映射: JPA自动映射

Database → Repository
    返回: Entity对象

Repository → Service
    返回: Entity对象

Service → Controller
    传输: Response DTO
    转换: Entity → DTO (通过MapStruct)

Controller → 客户端
    传输: Result<Response DTO> (JSON)
    示例: Result<UserResponse>
```

### 7.3 异常处理流程

```
业务异常抛出
    ↓
异常类型判断
    ├─ BusinessException (业务异常)
    │   └→ 返回: code + message
    │
    ├─ ValidationException (参数校验异常)
    │   └→ 返回: 400 + 错误字段信息
    │
    ├─ AuthenticationException (认证异常)
    │   └→ 返回: 401 + "未登录"
    │
    ├─ AccessDeniedException (授权异常)
    │   └→ 返回: 403 + "无权限"
    │
    └─ Exception (其他异常)
        └→ 返回: 500 + "系统错误"
    ↓
记录日志
    ↓
封装统一错误响应
    ↓
返回客户端
```

---

## 8. 技术选型

### 8.1 核心框架

| 技术 | 版本 | 说明 |
|------|------|------|
| **Spring Boot** | 3.1.5 | 应用框架 |
| **Spring MVC** | (included) | Web框架 |
| **Spring Data JPA** | (included) | ORM框架 |
| **Spring Security** | (included) | 安全框架 |
| **Hibernate** | 6.x | JPA实现 |

### 8.2 数据存储

| 技术 | 版本 | 用途 |
|------|------|------|
| **PostgreSQL** | 14+ | 主数据库 |
| **Redis** | 7.0+ | 缓存、会话、限流 |
| **阿里云OSS** | - | 文件存储 |

### 8.3 安全认证

| 技术 | 版本 | 用途 |
|------|------|------|
| **JWT** | 0.12.x | Token生成和验证 |
| **BCrypt** | (included) | 密码加密 |

### 8.4 工具库

| 技术 | 版本 | 用途 |
|------|------|------|
| **Lombok** | 1.18.30 | 简化代码 |
| **MapStruct** | 1.5.5 | 对象映射 |
| **Apache Commons** | 3.x | 工具类 |
| **Jackson** | (included) | JSON处理 |
| **Guava** | 32.x | Google工具库 |

### 8.5 文档和监控

| 技术 | 版本 | 用途 |
|------|------|------|
| **Springdoc OpenAPI** | 2.2.0 | API文档 |
| **Spring Actuator** | (included) | 健康检查 |
| **Logback** | (included) | 日志 |

### 8.6 其他组件

| 技术 | 版本 | 用途 |
|------|------|------|
| **Flyway** | 9.x | 数据库迁移 |
| **Spring Mail** | (included) | 邮件发送 |
| **Quartz** | 2.3.x | 定时任务 |
| **OkHttp** | 4.x | HTTP客户端 |

---

## 9. 部署架构

### 9.1 开发环境

```
开发机器 (本地)
    ↓
IntelliJ IDEA + Maven
    ↓
Spring Boot DevTools (热部署)
    ↓
本地PostgreSQL + Redis (Docker)
```

### 9.2 生产环境

```
┌────────────────────────────────────┐
│         负载均衡 (Nginx)            │
└────────┬───────────────────────────┘
         │
         ├─────────────┬─────────────┐
         ↓             ↓             ↓
    ┌────────┐    ┌────────┐    ┌────────┐
    │ App 1  │    │ App 2  │    │ App N  │
    │ :8080  │    │ :8080  │    │ :8080  │
    └────────┘    └────────┘    └────────┘
         │             │             │
         └─────────────┼─────────────┘
                       ↓
         ┌─────────────────────────┐
         │    PostgreSQL主从集群    │
         │    Redis哨兵集群         │
         │    OSS对象存储          │
         └─────────────────────────┘
```

### 9.3 容器化部署

```yaml
# Docker Compose示例
services:
  app:
    image: goabroad-api:latest
    ports:
      - "8080:8080"
    environment:
      - SPRING_PROFILES_ACTIVE=prod
    depends_on:
      - postgres
      - redis
  
  postgres:
    image: postgres:16-alpine
    environment:
      - POSTGRES_DB=goabroad_db
      - POSTGRES_USER=postgres
      - POSTGRES_PASSWORD=ascrm
    volumes:
      - postgres_data:/var/lib/postgresql/data
  
  redis:
    image: redis:7-alpine
    volumes:
      - redis_data:/data
  
  nginx:
    image: nginx:alpine
    ports:
      - "80:80"
      - "443:443"
```

---

## 10. 开发规范

### 10.1 命名规范

**包命名**
```
com.goabroad.{module}.{layer}.{business}

示例:
- com.goabroad.common.util.DateUtil
- com.goabroad.model.entity.User
- com.goabroad.service.user.UserService
- com.goabroad.web.controller.UserController
```

**类命名**
| 类型 | 规范 | 示例 |
|------|------|------|
| Entity | 名词 | User, Country, Plan |
| DTO (请求) | 动词+名词+Request | LoginRequest, CreatePlanRequest |
| DTO (响应) | 名词+Response | UserResponse, PlanResponse |
| Service接口 | 名词+Service | UserService |
| Service实现 | 名词+ServiceImpl | UserServiceImpl |
| Repository | 名词+Repository | UserRepository |
| Controller | 名词+Controller | UserController |
| Util | 功能+Util | DateUtil, StringUtil |

**方法命名**
| 操作 | 前缀 | 示例 |
|------|------|------|
| 查询单个 | get/find | getUserById, findByUsername |
| 查询列表 | list/query | listUsers, queryByStatus |
| 新增 | create/add | createPlan, addComment |
| 更新 | update | updateUser, updateStatus |
| 删除 | delete/remove | deletePost, removeComment |
| 判断 | is/has/exists | isActive, existsByEmail |

### 10.2 分层职责

**Controller层**
- ✅ 接收请求参数
- ✅ 参数验证（@Valid）
- ✅ 调用Service
- ✅ 返回统一格式响应
- ❌ 不写业务逻辑
- ❌ 不直接操作数据库

**Service层**
- ✅ 业务逻辑处理
- ✅ 事务管理（@Transactional）
- ✅ DTO ↔ Entity转换
- ✅ 调用Repository
- ✅ 集成第三方服务
- ❌ 不处理HTTP请求/响应

**Repository层**
- ✅ 数据库CRUD
- ✅ 自定义查询
- ✅ 缓存操作
- ❌ 不写业务逻辑

### 10.3 API设计规范

**RESTful风格**
```
GET    /api/v1/users           # 查询列表
GET    /api/v1/users/{id}      # 查询详情
POST   /api/v1/users           # 新增
PUT    /api/v1/users/{id}      # 更新
DELETE /api/v1/users/{id}      # 删除
```

**统一响应格式**
```json
// 成功响应
{
  "code": 200,
  "message": "success",
  "data": { ... },
  "timestamp": 1698765432000
}

// 错误响应
{
  "code": 400,
  "message": "参数错误",
  "data": null,
  "timestamp": 1698765432000
}
```

### 10.4 日志规范

**日志级别**
- **ERROR**: 系统错误、异常
- **WARN**: 警告信息、潜在问题
- **INFO**: 关键业务日志
- **DEBUG**: 调试信息（生产环境关闭）

**日志格式**
```
[时间] [级别] [线程] [类名] - 日志内容

示例:
2024-10-25 10:30:45.123 [INFO] [http-nio-8080-exec-1] 
  c.g.s.u.UserServiceImpl - 用户登录成功: userId=10001
```

### 10.5 Git提交规范

```
<type>(<scope>): <subject>

type类型:
- feat: 新功能
- fix: 修复Bug
- docs: 文档更新
- style: 代码格式
- refactor: 重构
- test: 测试
- chore: 构建/工具

示例:
feat(user): 添加用户注册功能
fix(plan): 修复时间线计算错误
docs(api): 更新API文档
```

---

## 11. 总结

### 11.1 架构优势

✅ **简单清晰** - 4个模块，职责明确  
✅ **易于开发** - Spring Boot单体应用，开发效率高  
✅ **便于维护** - 清晰的分层，规范的命名  
✅ **适度设计** - 符合毕业设计规模，不过度设计  
✅ **可扩展** - 模块化设计，便于后期优化

### 11.2 技术亮点

- Spring Boot 3.x 现代化框架
- JWT无状态认证
- Redis缓存优化
- MapStruct高效对象映射
- Flyway数据库版本管理
- Swagger API文档自动生成
- 统一异常处理和响应格式

### 11.3 适用场景

这套架构设计适合：
- 中小规模Web应用
- 毕业设计项目
- 快速原型开发
- 团队2-5人的项目

---

**文档编写**: AI Assistant  
**最后更新**: 2024年10月  
**文档状态**: v2.0 正式版