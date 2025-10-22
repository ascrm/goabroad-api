# goabroad-web 模块

## 📦 模块说明

Web接口层模块，负责处理 HTTP 请求、响应封装、参数校验、API 文档等。

**重要原则：Controller 层只负责请求处理，不包含任何业务逻辑！**

## 📂 模块结构

```
com.goabroad/
├── GoAbroadApplication.java     # 启动类
└── web/
    ├── controller/              # 控制器层（纯粹的请求处理）
    │   ├── AuthController.java
    │   ├── UserController.java
    │   ├── CountryController.java
    │   ├── PlanController.java
    │   ├── MaterialController.java
    │   ├── PostController.java
    │   ├── CommentController.java
    │   ├── NotificationController.java
    │   ├── ReminderController.java
    │   └── FileController.java
    ├── config/                  # 配置类
    │   ├── WebMvcConfig.java   # Web MVC 配置
    │   └── SwaggerConfig.java  # API 文档配置
    ├── interceptor/             # 拦截器
    │   └── AuthInterceptor.java
    └── exception/               # 异常处理
        └── GlobalExceptionHandler.java

resources/
├── application.yml              # 主配置文件
├── application-dev.yml          # 开发环境配置
└── application-prod.yml         # 生产环境配置
```

## 🎯 Controller 设计原则

### ✅ Controller 应该做什么

1. **接收 HTTP 请求** - 接收前端传来的请求
2. **参数校验** - 使用 `@Valid` 进行参数验证
3. **调用 Service** - 调用业务逻辑层处理
4. **返回响应** - 封装为统一的 `Result` 格式
5. **日志记录** - 记录请求信息

### ❌ Controller 不应该做什么

1. **业务逻辑** - 不要写任何业务判断和计算
2. **数据库操作** - 不要直接调用 Repository
3. **复杂计算** - 不要进行复杂的数据处理
4. **事务管理** - 事务应该在 Service 层

### 📝 标准 Controller 示例

```java
@RestController
@RequestMapping("/api/v1/users")
@Tag(name = "用户管理")
@Slf4j
public class UserController {
    
    @Autowired
    private UserService userService; // 只注入 Service
    
    @GetMapping("/{id}")
    @Operation(summary = "获取用户详情")
    public Result<UserResponse> getUserById(@PathVariable Long id) {
        // 1. 记录日志
        log.info("收到获取用户详情请求，id: {}", id);
        
        // 2. 调用 Service（业务逻辑在 Service 中）
        UserResponse user = userService.getUserById(id);
        
        // 3. 返回结果
        return Result.success(user);
    }
}
```

## 🔧 功能特性

### 1. **统一响应格式**

所有接口返回统一的 `Result<T>` 格式：

```json
{
  "code": 200,
  "message": "操作成功",
  "data": { ... },
  "timestamp": 1697788800000
}
```

**分页响应** `PageResult<T>`：

```json
{
  "code": 200,
  "message": "操作成功",
  "data": {
    "records": [...],
    "total": 100,
    "pageNum": 1,
    "pageSize": 20,
    "pages": 5,
    "hasNext": true,
    "hasPrevious": false
  },
  "timestamp": 1697788800000
}
```

### 2. **全局异常处理**

`GlobalExceptionHandler` 统一处理异常：

- ✅ 业务异常（BusinessException）
- ✅ 参数校验异常（MethodArgumentNotValidException）
- ✅ 404 异常（NoHandlerFoundException）
- ✅ 系统异常（Exception）

### 3. **参数校验**

使用 `@Valid` + Bean Validation：

```java
@PostMapping
public Result<UserResponse> create(@Valid @RequestBody CreateUserRequest request) {
    // 参数不合法会自动抛出异常，由全局异常处理器处理
}
```

### 4. **认证拦截**

`AuthInterceptor` 拦截需要认证的接口：

- 检查 `Authorization` 头中的 JWT Token
- 验证 Token 有效性
- 提取用户信息存储到 ThreadLocal

**公开接口**（不需要认证）：
- 登录/注册接口
- 国家信息查询
- 帖子浏览（不点赞、不评论）

### 5. **CORS 跨域支持**

`WebMvcConfig` 配置跨域：

- 允许所有源（开发环境）
- 允许常用 HTTP 方法
- 允许携带凭证

### 6. **API 文档**

使用 Springdoc OpenAPI 提供交互式 API 文档：

- **访问地址**: http://localhost:8080/swagger-ui/index.html
- **认证支持**: Bearer Token
- **OpenAPI 规范**: OpenAPI 3.0

## 📋 Controller 清单

### 认证模块
- `AuthController` - 登录、注册、登出、Token 刷新

### 用户模块
- `UserController` - 用户信息管理、关注功能

### 国家模块
- `CountryController` - 国家信息查询

### 规划模块
- `PlanController` - 留学规划管理
- `MaterialController` - 材料清单管理

### 社区模块
- `PostController` - 帖子管理（发布、点赞、收藏）
- `CommentController` - 评论管理（发表、回复）

### 通知模块
- `NotificationController` - 通知管理
- `ReminderController` - 提醒管理

### 文件模块
- `FileController` - 文件上传（头像、帖子图片）

## 🚀 快速开始

### 1. 配置数据库

修改 `application.yml`：

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/goabroad
    username: root
    password: your_password
```

### 2. 配置 Redis

```yaml
spring:
  redis:
    host: localhost
    port: 6379
```

### 3. 启动应用

```bash
# 使用 Maven 启动
mvn spring-boot:run

# 或直接运行主类
java -jar goabroad-web-1.0.0.jar

# 指定环境
java -jar goabroad-web-1.0.0.jar --spring.profiles.active=dev
```

### 4. 访问 API 文档

打开浏览器访问：http://localhost:8080/swagger-ui/index.html

## 📊 配置说明

### application.yml

主要配置项：

| 配置项 | 说明 | 默认值 |
|-------|------|--------|
| `server.port` | 服务端口 | 8080 |
| `spring.datasource.url` | 数据库连接 | - |
| `spring.redis.host` | Redis 主机 | localhost |
| `jwt.secret` | JWT 密钥 | - |
| `jwt.expiration` | Token 有效期 | 7天 |
| `storage.local.path` | 文件存储路径 | - |

### 环境配置

- `application-dev.yml` - 开发环境
  - 显示 SQL
  - 详细日志
  - 开启 API 文档

- `application-prod.yml` - 生产环境
  - 不显示 SQL
  - 简化日志
  - 关闭 API 文档

## 🔒 安全配置

### JWT Token

- **算法**: HS256
- **有效期**: 7 天
- **刷新 Token**: 30 天
- **请求头**: `Authorization: Bearer {token}`

### 拦截规则

需要认证的接口：
- 所有 `/api/**` 接口

不需要认证的接口：
- `/api/v1/auth/register`
- `/api/v1/auth/login`
- `/api/v1/countries/**`
- 帖子浏览相关接口

## 📝 开发规范

### 1. Controller 命名

- 以业务模块命名
- 使用 `Controller` 后缀
- 示例：`UserController`、`PostController`

### 2. 请求映射

- 使用 RESTful 风格
- 统一前缀：`/api/v1`
- 使用标准 HTTP 方法

```java
GET    /api/v1/users/{id}     - 查询
POST   /api/v1/users          - 创建
PUT    /api/v1/users/{id}     - 更新
DELETE /api/v1/users/{id}     - 删除
```

### 3. 参数接收

- 查询参数：`@RequestParam`
- 路径参数：`@PathVariable`
- 请求体：`@RequestBody`
- 请求头：`@RequestHeader`

### 4. 响应格式

**成功响应**：
```java
return Result.success(data);
```

**失败响应**（由异常处理器自动处理）：
```java
throw BusinessException.of(ResultCode.USER_NOT_FOUND);
```

## 🧪 测试

### API 文档测试

1. 访问 http://localhost:8080/swagger-ui/index.html
2. 选择接口
3. 填写参数
4. 点击"调试"

### 使用 Postman

导出 OpenAPI 规范：http://localhost:8080/v3/api-docs

## 📦 打包部署

### Maven 打包

```bash
# 打包（跳过测试）
mvn clean package -DskipTests

# 生成的 jar 包位置
goabroad-web/target/goabroad-web-1.0.0.jar
```

### 运行

```bash
# 生产环境运行
java -jar goabroad-web-1.0.0.jar --spring.profiles.active=prod
```

## 🎯 后续开发计划

当前状态：✅ **Web层骨架搭建完成**

已完成：
- ✅ 10个 Controller（覆盖所有功能模块）
- ✅ 全局异常处理
- ✅ 统一响应封装
- ✅ 认证拦截器
- ✅ CORS 跨域配置
- ✅ API 文档配置
- ✅ 启动类和配置文件

下一步工作：进入**阶段八：数据库和完整功能实现**

主要任务：
1. 创建数据库表
2. 实现 Service 层的业务逻辑
3. 编写单元测试和集成测试
4. 性能优化和安全加固

## 💡 重要提示

1. **Controller 不写业务逻辑** - 这是最重要的原则！
2. **参数校验交给 @Valid** - 不要手动校验
3. **异常统一抛出** - 不要手动封装错误响应
4. **日志记录请求** - 便于调试和追踪
5. **API 文档注解** - 便于前后端对接

## ✨ 总结

goabroad-web 模块作为系统的入口层，严格遵循"薄Controller"原则，只负责请求的接收和响应的返回，所有业务逻辑都委托给 Service 层处理。这种设计使得系统结构清晰、职责分明、易于维护和测试。

