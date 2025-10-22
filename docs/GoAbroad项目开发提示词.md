# 🤖 GoAbroad 项目开发提示词（AI Agent 专用）

我将为您生成一份完整的、分阶段的提示词，可以直接用于指导AI完成项目开发：

---

```markdown
# GoAbroad 出国助手后端API - AI开发指南

## 📋 项目概述

**项目名称**: GoAbroad 出国助手后端API
**项目类型**: Java Spring Boot 多模块毕业设计项目
**工作目录**: D:\User\Code\毕业设计\goabroad-api
**技术栈**: Spring Boot 3.1.5 + Java 17 + MySQL + Redis + JWT
**架构模式**: 多模块单体应用（6个子模块）

**核心功能**:
- 用户认证与授权（注册/登录/JWT）
- 国家信息查询（留学/工作信息）
- 出国规划管理（材料清单、时间线）
- 社区论坛（帖子、评论）
- 实用工具（费用计算、GPA转换）
- 通知提醒系统

**项目文档位置**:
- 架构设计: `docs/GoAbroad 后端架构设计文档.md`
- 产品设计: `docs/GoAbroad产品设计文档.md`
- 技术栈: `docs/GoAbroad技术栈与依赖清单.md`

---

## 🎯 开发原则

在开发过程中，请严格遵守以下原则：

1. **严格按照架构文档执行** - 所有代码结构必须符合设计文档
2. **自下而上开发** - 从common模块开始，逐层向上
3. **模块独立性** - 禁止出现循环依赖
4. **代码规范** - 使用Lombok、MapStruct、统一注释风格
5. **依赖管理** - 版本号统一在父POM的dependencyManagement中管理
6. **先基础后扩展** - 先实现核心功能，再添加高级特性
7. **可测试性** - 每个Service方法都应该可单元测试

---

## 📦 模块依赖关系（必须严格遵守）

```
goabroad-web
    ├── goabroad-service
    │       ├── goabroad-repository
    │       │       └── goabroad-model
    │       │               └── goabroad-common
    │       └── goabroad-security
    │               └── goabroad-model
    │                       └── goabroad-common
    └── goabroad-common
```

❌ **禁止的依赖**:
- common → 任何其他模块
- model → repository/service/web
- repository → service/web
- service → web

---

## 🚀 阶段一：环境准备与父POM配置

### 目标
完善项目的Maven配置，建立统一的依赖管理体系。

### 任务清单

#### 1.1 完善根目录 `pom.xml`

参考文档第5.1节，需要添加：

**Properties 部分**:
```xml
<properties>
    <java.version>17</java.version>
    <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    <goabroad.version>1.0.0</goabroad.version>
    
    <!-- 依赖版本 -->
    <spring-boot.version>3.1.5</spring-boot.version>
    <jwt.version>0.12.3</jwt.version>
    <mapstruct.version>1.5.5.Final</mapstruct.version>
    <springdoc.version>2.2.0</springdoc.version>
    <aliyun-oss.version>3.17.2</aliyun-oss.version>
    <commons-lang3.version>3.13.0</commons-lang3.version>
    <commons-io.version>2.15.0</commons-io.version>
    <guava.version>32.1.3-jre</guava.version>
</properties>
```

**DependencyManagement 部分**: 添加所有内部模块和第三方依赖的版本管理

**PluginManagement 部分**: 配置Maven编译插件，支持Lombok和MapStruct

#### 1.2 创建数据库

```sql
CREATE DATABASE goabroad CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

#### 1.3 准备配置文件模板

在 `goabroad-web/src/main/resources/` 创建:
- `application.yml` (主配置)
- `application-dev.yml` (开发环境)
- `application-prod.yml` (生产环境)

### 验收标准
- ✅ 父POM能够成功编译: `mvn clean compile`
- ✅ 所有子模块正确识别
- ✅ 依赖版本统一管理
- ✅ 数据库创建成功

---

## 🔧 阶段二：goabroad-common 模块（公共基础）

### 目标
构建整个项目的基础设施层，提供通用工具类、常量、异常和响应格式。

### 任务清单

#### 2.1 配置 `goabroad-common/pom.xml`

添加依赖：
- jackson-databind
- commons-lang3
- commons-io
- guava
- lombok
- jakarta.validation-api

#### 2.2 创建包结构

```
com.goabroad.common/
├── constant/          # 常量类
├── enums/             # 枚举
├── exception/         # 自定义异常
├── util/              # 工具类
├── annotation/        # 自定义注解
└── response/          # 统一响应格式
```

#### 2.3 核心类实现

**必须实现的类**:

1. `Result<T>` - 统一响应格式
```java
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result<T> {
    private Integer code;
    private String message;
    private T data;
    private Long timestamp;
    
    public static <T> Result<T> success(T data) { ... }
    public static <T> Result<T> error(Integer code, String message) { ... }
}
```

2. `ResponseCode` - 响应码枚举（200/400/401/403/404/500等）

3. `BusinessException` - 业务异常类

4. `AppConstants` - 应用常量（JWT相关、分页默认值等）

5. 工具类：
   - `DateUtil` - 日期处理
   - `StringUtil` - 字符串处理
   - `JsonUtil` - JSON序列化/反序列化
   - `ValidatorUtil` - 参数验证

### 验收标准
- ✅ 模块独立编译成功
- ✅ 所有工具类方法有完整JavaDoc注释
- ✅ Result类支持链式调用
- ✅ 枚举类定义完整

---

## 📊 阶段三：goabroad-model 模块（数据模型）

### 目标
定义所有数据库实体类、DTO和VO，建立对象映射关系。

### 任务清单

#### 3.1 配置 `goabroad-model/pom.xml`

依赖：
- goabroad-common
- spring-boot-starter-data-jpa
- spring-boot-starter-validation
- mapstruct
- lombok
- jackson-annotations

#### 3.2 创建包结构

```
com.goabroad.model/
├── entity/            # JPA实体类
├── dto/
│   ├── request/       # 请求DTO
│   └── response/      # 响应DTO
├── vo/                # 视图对象
└── mapper/            # MapStruct映射接口
```

#### 3.3 核心实体类（按优先级）

**第一批（用户系统）**:
1. `User.java` - 用户实体
   - 字段：id, username, email, passwordHash, avatarUrl, nickname, status, level, points
   - 使用 @Entity, @Table, @Data, @CreatedDate, @LastModifiedDate

2. `UserRole.java` - 用户角色（ADMIN/USER）

**第二批（国家信息）**:
3. `Country.java` - 国家信息
4. `CountryStudyInfo.java` - 留学信息（可以JSON存储在Country表中）
5. `CountryWorkInfo.java` - 工作信息

**第三批（规划系统）**:
6. `Plan.java` - 规划
7. `MaterialChecklist.java` - 材料清单
8. `Timeline.java` - 时间线
9. `TimelineTask.java` - 时间线任务

**第四批（社区）**:
10. `Post.java` - 帖子
11. `Comment.java` - 评论
12. `Like.java` - 点赞
13. `Collection.java` - 收藏

**第五批（通知）**:
14. `Notification.java` - 通知
15. `Reminder.java` - 提醒

#### 3.4 DTO 定义

**Request DTO 示例**:
- `LoginRequest` - 登录请求（username, password）
- `RegisterRequest` - 注册请求
- `CreatePlanRequest` - 创建规划请求
- `CreatePostRequest` - 创建帖子请求

**Response DTO 示例**:
- `UserResponse` - 用户响应（不含密码）
- `LoginResponse` - 登录响应（包含token）
- `CountryResponse` - 国家信息响应
- `PlanResponse` - 规划响应

#### 3.5 MapStruct Mapper

```java
@Mapper(componentModel = "spring")
public interface UserMapper {
    UserResponse toResponse(User user);
    List<UserResponse> toResponseList(List<User> users);
    User toEntity(RegisterRequest request);
}
```

### 验收标准
- ✅ 所有实体类添加了JPA注解
- ✅ 所有字段使用驼峰命名
- ✅ 使用 @Data/@Builder/@NoArgsConstructor/@AllArgsConstructor
- ✅ Request DTO 添加了 Validation 注解（@NotNull, @Size等）
- ✅ MapStruct 编译后生成实现类

---

## 🔐 阶段四：goabroad-security 模块（安全认证）

### 目标
实现JWT认证、Spring Security配置、密码加密。

### 任务清单

#### 4.1 配置 `goabroad-security/pom.xml`

依赖：
- goabroad-common
- goabroad-model
- spring-boot-starter-security
- jjwt-api/jjwt-impl/jjwt-jackson
- spring-boot-starter-data-redis

#### 4.2 创建包结构

```
com.goabroad.security/
├── jwt/               # JWT相关
├── config/            # Security配置
├── service/           # UserDetailsService实现
└── util/              # 加密工具
```

#### 4.3 核心类实现

1. **JwtTokenProvider.java** - JWT工具类
```java
@Component
public class JwtTokenProvider {
    @Value("${jwt.secret}")
    private String secretKey;
    
    @Value("${jwt.expiration}")
    private Long expiration;
    
    public String generateToken(UserDetails userDetails);
    public boolean validateToken(String token);
    public String getUsernameFromToken(String token);
    public Claims getClaimsFromToken(String token);
}
```

2. **JwtAuthenticationFilter.java** - JWT过滤器
   - 继承 OncePerRequestFilter
   - 从请求头提取JWT
   - 验证并设置SecurityContext

3. **SecurityConfig.java** - Security配置
```java
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) {
        // 配置白名单：/api/v1/auth/**
        // 配置JWT过滤器
        // 禁用CSRF（使用JWT不需要）
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
```

4. **UserDetailsServiceImpl.java** - 用户详情服务
   - 实现 UserDetailsService
   - 从数据库加载用户信息

#### 4.4 配置项（application.yml）

```yaml
jwt:
  secret: your-secret-key-at-least-256-bits-long
  expiration: 604800000  # 7天
  header: Authorization
  prefix: "Bearer "
```

### 验收标准
- ✅ JWT能够正确生成和解析
- ✅ Security配置生效，未认证请求被拦截
- ✅ 白名单路径可以访问
- ✅ 密码使用BCrypt加密

---

## 💾 阶段五：goabroad-repository 模块（数据访问）

### 目标
定义所有Repository接口，实现数据库CRUD操作。

### 任务清单

#### 5.1 配置 `goabroad-repository/pom.xml`

依赖：
- goabroad-common
- goabroad-model
- spring-boot-starter-data-jpa
- mysql-connector-j
- spring-boot-starter-data-redis
- commons-pool2

#### 5.2 创建包结构

```
com.goabroad.repository/
├── mysql/             # MySQL Repository
├── redis/             # Redis Repository
└── es/                # Elasticsearch Repository（可选）
```

#### 5.3 Repository 接口（按优先级）

**第一批**:
```java
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}
```

**第二批**:
- `CountryRepository` - 国家信息
- `PlanRepository` - 规划
- `MaterialChecklistRepository` - 材料清单

**第三批**:
- `PostRepository` - 帖子
- `CommentRepository` - 评论
- `NotificationRepository` - 通知

#### 5.4 Redis 缓存封装

```java
@Repository
public class CacheRepository {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    
    public void set(String key, Object value, long timeout, TimeUnit unit);
    public Object get(String key);
    public void delete(String key);
    public boolean hasKey(String key);
}
```

#### 5.5 Redis 配置

```java
@Configuration
public class RedisConfig {
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
        // 配置序列化器
    }
}
```

### 验收标准
- ✅ 所有Repository继承JpaRepository
- ✅ 自定义查询方法命名规范
- ✅ 复杂查询使用@Query注解
- ✅ Redis连接正常

---

## ⚙️ 阶段六：goabroad-service 模块（业务逻辑）

### 目标
实现核心业务逻辑，处理事务和业务规则。

### 任务清单

#### 6.1 配置 `goabroad-service/pom.xml`

依赖：
- goabroad-common
- goabroad-model
- goabroad-repository
- goabroad-security
- spring-boot-starter
- spring-tx
- spring-boot-starter-cache
- caffeine

#### 6.2 创建包结构

```
com.goabroad.service/
├── user/              # 用户服务
├── auth/              # 认证服务
├── country/           # 国家信息服务
├── planning/          # 规划服务
├── community/         # 社区服务
├── tool/              # 工具服务
├── notification/      # 通知服务
└── storage/           # 文件存储服务
```

#### 6.3 服务实现顺序

**第一批（认证系统）**:

1. **AuthService & AuthServiceImpl**
```java
public interface AuthService {
    LoginResponse login(LoginRequest request);
    RegisterResponse register(RegisterRequest request);
    void logout(String token);
    TokenResponse refreshToken(String refreshToken);
}

@Service
@Transactional
public class AuthServiceImpl implements AuthService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    
    @Override
    public LoginResponse login(LoginRequest request) {
        // 1. 查询用户
        // 2. 验证密码
        // 3. 生成JWT
        // 4. 返回响应
    }
    
    @Override
    public RegisterResponse register(RegisterRequest request) {
        // 1. 检查用户名/邮箱是否存在
        // 2. 加密密码
        // 3. 保存用户
        // 4. 返回响应
    }
}
```

2. **UserService & UserServiceImpl**
```java
public interface UserService {
    UserResponse getUserById(Long id);
    UserResponse getUserByUsername(String username);
    UserResponse updateUser(Long id, UpdateUserRequest request);
    void deleteUser(Long id);
}
```

**第二批（国家信息）**:

3. **CountryService**
```java
public interface CountryService {
    List<CountryResponse> getAllCountries();
    CountryDetailResponse getCountryByCode(String code);
    CountryStudyInfoResponse getStudyInfo(String code);
}
```

**第三批（规划系统）**:

4. **PlanService** - 规划管理
5. **MaterialService** - 材料清单
6. **TimelineService** - 时间线管理

**第四批（社区）**:

7. **PostService** - 帖子管理
8. **CommentService** - 评论管理

**第五批（其他）**:

9. **NotificationService** - 通知服务
10. **FileStorageService** - 文件存储（可对接OSS）

### 开发规范

- ✅ 所有Service实现类添加 @Service 和 @Transactional
- ✅ 使用 @Autowired 或构造器注入依赖
- ✅ 所有方法添加完整的JavaDoc注释
- ✅ 业务异常使用 BusinessException 抛出
- ✅ 日志使用 @Slf4j
- ✅ 可缓存的方法添加 @Cacheable

### 验收标准
- ✅ 每个Service有对应的接口和实现类
- ✅ 事务配置正确
- ✅ 异常处理完善
- ✅ 返回的DTO不包含敏感信息

---

## 🌐 阶段七：goabroad-web 模块（Web接口）

### 目标
实现RESTful API接口，提供统一的HTTP入口。

### 任务清单

#### 7.1 配置 `goabroad-web/pom.xml`

依赖：
- 所有内部模块（common/model/security/repository/service）
- spring-boot-starter-web
- spring-boot-starter-validation
- springdoc-openapi-starter-webmvc-ui
- spring-boot-starter-actuator
- flyway-core
- flyway-mysql

#### 7.2 创建包结构

```
com.goabroad.web/
├── Application.java   # 启动类
├── controller/        # 控制器
├── config/            # 配置类
├── filter/            # 过滤器
├── interceptor/       # 拦截器
├── aspect/            # AOP切面
└── handler/           # 全局异常处理
```

#### 7.3 启动类

```java
@SpringBootApplication(scanBasePackages = "com.goabroad")
@EnableJpaRepositories("com.goabroad.repository")
@EntityScan("com.goabroad.model.entity")
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
```

#### 7.4 Controller 实现（按优先级）

**第一批（认证）**:

```java
@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "认证接口", description = "用户注册、登录、登出")
@Slf4j
public class AuthController {
    @Autowired
    private AuthService authService;
    
    @PostMapping("/register")
    @Operation(summary = "用户注册")
    public Result<RegisterResponse> register(@Valid @RequestBody RegisterRequest request) {
        return Result.success(authService.register(request));
    }
    
    @PostMapping("/login")
    @Operation(summary = "用户登录")
    public Result<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        return Result.success(authService.login(request));
    }
    
    @PostMapping("/logout")
    @Operation(summary = "用户登出")
    public Result<Void> logout(@RequestHeader("Authorization") String token) {
        authService.logout(token);
        return Result.success(null);
    }
}
```

**第二批（用户）**:
- `UserController` - 用户信息管理

**第三批（国家）**:
- `CountryController` - 国家信息查询

**第四批（规划）**:
- `PlanController` - 规划管理
- `MaterialController` - 材料管理

**第五批（社区）**:
- `CommunityController` - 社区功能

#### 7.5 全局异常处理

```java
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    
    @ExceptionHandler(BusinessException.class)
    public Result<?> handleBusinessException(BusinessException e) {
        log.error("业务异常: {}", e.getMessage());
        return Result.error(e.getCode(), e.getMessage());
    }
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<?> handleValidationException(MethodArgumentNotValidException e) {
        String message = e.getBindingResult().getFieldError().getDefaultMessage();
        return Result.error(400, message);
    }
    
    @ExceptionHandler(Exception.class)
    public Result<?> handleException(Exception e) {
        log.error("系统异常", e);
        return Result.error(500, "系统错误，请稍后重试");
    }
}
```

#### 7.6 配置类

1. **WebMvcConfig** - CORS、拦截器
2. **SwaggerConfig** - API文档配置
3. **AsyncConfig** - 异步任务配置（可选）

#### 7.7 application.yml 配置

```yaml
spring:
  application:
    name: goabroad-api
  
  datasource:
    url: jdbc:mysql://localhost:3306/goabroad?useUnicode=true&characterEncoding=utf8&serverTimezone=Asia/Shanghai
    username: root
    password: your_password
    driver-class-name: com.mysql.cj.jdbc.Driver
    hikari:
      maximum-pool-size: 20
      minimum-idle: 5
  
  jpa:
    hibernate:
      ddl-auto: none
    show-sql: false
    properties:
      hibernate:
        format_sql: true
  
  redis:
    host: localhost
    port: 6379
    database: 0
    timeout: 5000ms
    lettuce:
      pool:
        max-active: 8
        max-idle: 8
        min-idle: 0
  
  flyway:
    enabled: true
    locations: classpath:db/migration
    baseline-on-migrate: true

jwt:
  secret: goabroad-secret-key-must-be-at-least-256-bits-long-for-security
  expiration: 604800000
  header: Authorization
  prefix: "Bearer "

springdoc:
  api-docs:
    path: /v3/api-docs
  swagger-ui:
    path: /swagger-ui.html

server:
  port: 8080
```

#### 7.8 数据库迁移脚本

在 `src/main/resources/db/migration/` 创建:

**V1__init_database.sql**:
```sql
-- 用户表
CREATE TABLE users (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(100) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    avatar_url VARCHAR(500),
    nickname VARCHAR(50),
    status VARCHAR(20) DEFAULT 'ACTIVE',
    level INT DEFAULT 1,
    points INT DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_username (username),
    INDEX idx_email (email)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 其他表...
```

### 验收标准
- ✅ 应用能够成功启动
- ✅ Swagger文档可访问: http://localhost:8080/swagger-ui.html
- ✅ 注册/登录接口可正常调用
- ✅ JWT认证生效
- ✅ 全局异常处理生效
- ✅ 数据库表自动创建

---

## 🧪 阶段八：测试与优化

### 目标
编写单元测试、集成测试，优化性能。

### 任务清单

#### 8.1 单元测试

为每个Service编写测试:

```java
@SpringBootTest
@Transactional
class UserServiceTest {
    
    @Autowired
    private UserService userService;
    
    @Test
    void testRegister() {
        RegisterRequest request = new RegisterRequest();
        request.setUsername("test");
        request.setEmail("test@example.com");
        request.setPassword("password123");
        
        RegisterResponse response = authService.register(request);
        assertNotNull(response);
        assertNotNull(response.getId());
    }
}
```

#### 8.2 API测试

使用Postman或编写集成测试:

```java
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class AuthControllerTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @Test
    void testLogin() throws Exception {
        mockMvc.perform(post("/api/v1/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\":\"test\",\"password\":\"password123\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));
    }
}
```

#### 8.3 性能优化

- 添加缓存注解 @Cacheable
- 配置数据库连接池
- 添加索引
- 查询优化（N+1问题）

### 验收标准
- ✅ 测试覆盖率 > 60%
- ✅ 所有核心接口有集成测试
- ✅ 响应时间 < 100ms（本地环境）

---

## 📚 阶段九：文档完善与部署准备

### 目标
完善项目文档，准备部署材料。

### 任务清单

#### 9.1 README.md

编写项目说明文档，包含:
- 项目介绍
- 技术栈
- 快速开始
- API文档链接
- 部署说明

#### 9.2 Docker支持

**Dockerfile**:
```dockerfile
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY goabroad-web/target/goabroad-web-1.0.0.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
```

**docker-compose.yml**:
```yaml
version: '3.8'
services:
  mysql:
    image: mysql:8.0
    environment:
      MYSQL_ROOT_PASSWORD: password
      MYSQL_DATABASE: goabroad
    ports:
      - "3306:3306"
  
  redis:
    image: redis:7-alpine
    ports:
      - "6379:6379"
  
  app:
    build: .
    ports:
      - "8080:8080"
    depends_on:
      - mysql
      - redis
```

#### 9.3 日志配置

**logback-spring.xml**:
```xml
<?xml version="1.0" encoding="UTF-8"?>
<configuration>
    <include resource="org/springframework/boot/logging/logback/defaults.xml"/>
    
    <appender name="CONSOLE" class="ch.qos.logback.core.ConsoleAppender">
        <encoder>
            <pattern>%d{yyyy-MM-dd HH:mm:ss} [%thread] %-5level %logger{36} - %msg%n</pattern>
        </encoder>
    </appender>
    
    <root level="INFO">
        <appender-ref ref="CONSOLE"/>
    </root>
    
    <logger name="com.goabroad" level="DEBUG"/>
</configuration>
```

#### 9.4 健康检查

```java
@RestController
@RequestMapping("/actuator")
public class HealthController {
    
    @GetMapping("/health")
    public Result<Map<String, String>> health() {
        Map<String, String> health = new HashMap<>();
        health.put("status", "UP");
        health.put("timestamp", LocalDateTime.now().toString());
        return Result.success(health);
    }
}
```

### 验收标准
- ✅ README文档完整
- ✅ Docker镜像构建成功
- ✅ docker-compose一键启动
- ✅ 日志输出规范

---

## 🎓 阶段十：毕业设计特殊要求

### 目标
满足毕业设计的学术要求和答辩需求。

### 任务清单

#### 10.1 代码注释

确保所有类和方法都有完整的JavaDoc注释:

```java
/**
 * 用户服务接口
 * 提供用户信息的增删改查功能
 * 
 * @author Your Name
 * @version 1.0
 * @since 2024-10-19
 */
public interface UserService {
    
    /**
     * 根据ID获取用户信息
     * 
     * @param id 用户ID
     * @return 用户响应对象
     * @throws BusinessException 当用户不存在时抛出
     */
    UserResponse getUserById(Long id);
}
```

#### 10.2 设计模式体现

在代码中体现常见设计模式:
- ✅ 工厂模式（对象创建）
- ✅ 策略模式（不同的计算策略）
- ✅ 单例模式（Spring Bean默认）
- ✅ 代理模式（AOP、事务）
- ✅ 模板方法模式（抽象Service）

#### 10.3 技术亮点总结

在文档中突出以下亮点:
- ✅ 多模块架构设计
- ✅ JWT无状态认证
- ✅ Redis缓存优化
- ✅ RESTful API设计
- ✅ 统一异常处理
- ✅ Swagger API文档
- ✅ 数据库版本管理（Flyway）
- ✅ Docker容器化部署

#### 10.4 性能测试报告

使用JMeter进行压力测试，生成测试报告:
- 并发用户数
- TPS（每秒事务数）
- 响应时间
- 成功率

### 验收标准
- ✅ 代码注释覆盖率 > 80%
- ✅ 至少体现3种设计模式
- ✅ 有完整的性能测试报告
- ✅ 技术文档图文并茂

---

## ⚠️ 开发注意事项

### 常见问题预防

1. **循环依赖**: 严格遵守模块依赖关系，不要违反
2. **密码安全**: 永远不要返回passwordHash字段
3. **SQL注入**: 使用JPA或@Param，不要拼接SQL
4. **空指针**: 多使用Optional和Objects.requireNonNull
5. **事务失效**: @Transactional只对public方法生效
6. **缓存雪崩**: 设置缓存过期时间随机化
7. **内存泄漏**: 注意关闭流和数据库连接

### 代码质量检查

每完成一个阶段，运行以下检查:

```bash
# 编译检查
mvn clean compile

# 运行测试
mvn test

# 打包检查
mvn clean package -DskipTests

# 代码规范检查（如果配置了CheckStyle）
mvn checkstyle:check
```

---

## 📋 开发进度跟踪

每完成一个阶段，在此打勾：

- [ ] 阶段一：环境准备与父POM配置
- [ ] 阶段二：goabroad-common 模块
- [ ] 阶段三：goabroad-model 模块
- [ ] 阶段四：goabroad-security 模块
- [ ] 阶段五：goabroad-repository 模块
- [ ] 阶段六：goabroad-service 模块
- [ ] 阶段七：goabroad-web 模块
- [ ] 阶段八：测试与优化
- [ ] 阶段九：文档完善与部署准备
- [ ] 阶段十：毕业设计特殊要求

---

## 🚀 如何使用这份提示词

**方式一：分阶段提问**
```
请根据"阶段一：环境准备与父POM配置"的要求，
帮我完善项目的父POM文件。
```

**方式二：具体任务**
```
请根据"阶段三：goabroad-model 模块"的要求，
帮我创建User实体类，包含所有必需的字段和注解。
```

**方式三：批量生成**
```
请根据"阶段七：goabroad-web 模块"的要求，
帮我生成AuthController的完整代码，包括注册、登录、登出接口。
```

---

## 📞 获取帮助

遇到问题时，提供以下信息：
1. 当前所在阶段
2. 具体错误信息
3. 相关代码片段
4. 已尝试的解决方案

Good luck with your graduation project! 🎓
```

---

## 💡 使用说明

这份提示词可以：

1. **整体使用** - 直接提供给AI，让它全局了解项目
2. **分段使用** - 每次只提供某个阶段的内容
3. **参考使用** - 作为开发检查清单

建议保存为 `docs/AI开发指南.md`，随时查阅和更新！