我来为您编写一份详细的GoAbroad后端架构文档。这份文档可以直接放入您的docs文件夹中。

---

# 🏗️ GoAbroad 后端架构设计文档

**项目名称**: GoAbroad 出国助手 - 后端API服务  
**架构模式**: 多模块单体应用 (Multi-Module Monolith)  
**技术栈**: Spring Boot 3.1.5 + Java 17 + Maven  
**文档版本**: v1.0  
**编写日期**: 2024年10月

---

## 📑 目录

1. [架构总览](#1-架构总览)
2. [模块划分](#2-模块划分)
3. [模块详细设计](#3-模块详细设计)
4. [依赖关系图](#4-依赖关系图)
5. [POM配置](#5-pom配置)
6. [包结构规范](#6-包结构规范)
7. [数据流转](#7-数据流转)
8. [开发规范](#8-开发规范)

---

## 1. 架构总览

### 1.1 设计原则

- **单一职责**: 每个模块只负责一个业务领域
- **依赖倒置**: 高层模块不依赖低层模块，依赖于抽象
- **低耦合高内聚**: 模块间通过接口通信，内部高度内聚
- **可测试性**: 每个模块可独立测试
- **可扩展性**: 便于后期拆分为微服务

### 1.2 分层架构

```
┌─────────────────────────────────────────────┐
│          External Clients (前端)             │
│       (iOS App / Android App / Web)         │
└─────────────────────────────────────────────┘
                    ↓ HTTPS
┌─────────────────────────────────────────────┐
│            Web Layer (表现层)                │
│         goabroad-web (Controller)           │
└─────────────────────────────────────────────┘
                    ↓
┌─────────────────────────────────────────────┐
│          Service Layer (业务层)              │
│    goabroad-service (Business Logic)        │
└─────────────────────────────────────────────┘
                    ↓
┌─────────────────────────────────────────────┐
│        Repository Layer (数据访问层)         │
│     goabroad-repository (Data Access)       │
└─────────────────────────────────────────────┘
                    ↓
┌─────────────────────────────────────────────┐
│          Database Layer (数据层)             │
│      MySQL + Redis + Elasticsearch          │
└─────────────────────────────────────────────┘

Cross-Cutting Concerns (横切关注点):
├─ goabroad-common (工具类、常量)
├─ goabroad-model (实体模型)
└─ goabroad-security (安全认证)
```

---

## 2. 模块划分

### 2.1 模块概览

| 模块名称 | 类型 | 职责 | 打包方式 |
|---------|------|------|---------|
| `goabroad-parent` | 父模块 | 统一依赖管理、版本控制 | pom |
| `goabroad-common` | 基础模块 | 公共工具类、常量、枚举、异常 | jar |
| `goabroad-model` | 模型模块 | 实体类、DTO、VO | jar |
| `goabroad-security` | 安全模块 | JWT、认证、授权、加密 | jar |
| `goabroad-repository` | 数据访问模块 | Repository、DAO | jar |
| `goabroad-service` | 业务逻辑模块 | 核心业务逻辑 | jar |
| `goabroad-web` | Web接口模块 | Controller、启动类、配置 | jar (可执行) |

### 2.2 项目目录结构

```
goabroad-backend/
│
├── pom.xml                          # 父POM，依赖管理
│
├── goabroad-common/                 # 公共模块
│   ├── pom.xml
│   └── src/main/java/com/goabroad/common/
│       ├── constant/                # 常量
│       ├── enums/                   # 枚举
│       ├── exception/               # 自定义异常
│       ├── util/                    # 工具类
│       └── annotation/              # 自定义注解
│
├── goabroad-model/                  # 模型模块
│   ├── pom.xml
│   └── src/main/java/com/goabroad/model/
│       ├── entity/                  # JPA实体类
│       │   ├── User.java
│       │   ├── Country.java
│       │   ├── Plan.java
│       │   ├── Post.java
│       │   └── ...
│       ├── dto/                     # 数据传输对象
│       │   ├── request/             # 请求DTO
│       │   └── response/            # 响应DTO
│       └── vo/                      # 视图对象
│
├── goabroad-security/               # 安全模块
│   ├── pom.xml
│   └── src/main/java/com/goabroad/security/
│       ├── jwt/                     # JWT相关
│       │   ├── JwtTokenProvider.java
│       │   └── JwtAuthenticationFilter.java
│       ├── config/                  # 安全配置
│       │   └── SecurityConfig.java
│       ├── service/                 # 认证服务
│       │   └── UserDetailsServiceImpl.java
│       └── util/                    # 安全工具类
│           └── PasswordEncoder.java
│
├── goabroad-repository/             # 数据访问模块
│   ├── pom.xml
│   └── src/main/java/com/goabroad/repository/
│       ├── mysql/                   # MySQL Repository
│       │   ├── UserRepository.java
│       │   ├── CountryRepository.java
│       │   ├── PlanRepository.java
│       │   ├── PostRepository.java
│       │   └── ...
│       ├── redis/                   # Redis Repository
│       │   └── CacheRepository.java
│       └── es/                      # Elasticsearch Repository
│           └── SearchRepository.java
│
├── goabroad-service/                # 业务逻辑模块
│   ├── pom.xml
│   └── src/main/java/com/goabroad/service/
│       ├── user/                    # 用户服务
│       │   ├── UserService.java
│       │   └── impl/
│       │       └── UserServiceImpl.java
│       ├── auth/                    # 认证服务
│       │   ├── AuthService.java
│       │   └── impl/
│       ├── country/                 # 国家信息服务
│       │   ├── CountryService.java
│       │   └── impl/
│       ├── planning/                # 规划服务
│       │   ├── PlanService.java
│       │   ├── MaterialService.java
│       │   ├── TimelineService.java
│       │   └── impl/
│       ├── community/               # 社区服务
│       │   ├── PostService.java
│       │   ├── CommentService.java
│       │   └── impl/
│       ├── tool/                    # 工具服务
│       │   ├── CostCalculatorService.java
│       │   ├── GpaConverterService.java
│       │   └── impl/
│       ├── notification/            # 通知服务
│       │   ├── NotificationService.java
│       │   ├── ReminderService.java
│       │   └── impl/
│       └── storage/                 # 文件存储服务
│           ├── FileStorageService.java
│           └── impl/
│
└── goabroad-web/                    # Web接口模块 (启动入口)
    ├── pom.xml
    └── src/
        ├── main/
        │   ├── java/com/goabroad/web/
        │   │   ├── Application.java           # 启动类
        │   │   ├── controller/                # 控制器
        │   │   │   ├── AuthController.java
        │   │   │   ├── UserController.java
        │   │   │   ├── CountryController.java
        │   │   │   ├── PlanController.java
        │   │   │   ├── CommunityController.java
        │   │   │   ├── ToolController.java
        │   │   │   └── NotificationController.java
        │   │   ├── config/                    # 配置类
        │   │   │   ├── WebMvcConfig.java
        │   │   │   ├── CorsConfig.java
        │   │   │   ├── SwaggerConfig.java
        │   │   │   ├── RedisConfig.java
        │   │   │   └── AsyncConfig.java
        │   │   ├── filter/                    # 过滤器
        │   │   │   └── RequestLogFilter.java
        │   │   ├── interceptor/               # 拦截器
        │   │   │   └── RateLimitInterceptor.java
        │   │   ├── aspect/                    # AOP切面
        │   │   │   ├── LogAspect.java
        │   │   │   └── ValidationAspect.java
        │   │   └── handler/                   # 异常处理
        │   │       └── GlobalExceptionHandler.java
        │   └── resources/
        │       ├── application.yml
        │       ├── application-dev.yml
        │       ├── application-prod.yml
        │       ├── logback-spring.xml
        │       └── db/
        │           └── migration/             # Flyway迁移脚本
        │               ├── V1__init_database.sql
        │               ├── V2__add_user_table.sql
        │               └── ...
        └── test/                              # 测试
            └── java/com/goabroad/web/
```

---

## 3. 模块详细设计

### 3.1 goabroad-common (公共模块)

#### 职责
- 提供全局公共工具类
- 定义系统常量和枚举
- 定义自定义异常
- 提供公共注解
- 提供通用响应格式

#### 核心类

```java
// 统一响应格式
public class Result<T> {
    private Integer code;
    private String message;
    private T data;
    private Long timestamp;
}

// 业务异常
public class BusinessException extends RuntimeException {
    private Integer code;
    private String message;
}

// 常量类
public class AppConstants {
    public static final String JWT_HEADER = "Authorization";
    public static final String JWT_PREFIX = "Bearer ";
    public static final Integer TOKEN_EXPIRE_TIME = 7 * 24 * 3600; // 7天
}

// 枚举
public enum ResponseCode {
    SUCCESS(200, "成功"),
    UNAUTHORIZED(401, "未授权"),
    FORBIDDEN(403, "无权限"),
    NOT_FOUND(404, "资源不存在"),
    INTERNAL_ERROR(500, "服务器错误");
}

// 工具类
public class DateUtil { ... }
public class StringUtil { ... }
public class JsonUtil { ... }
public class ValidatorUtil { ... }
```

#### 依赖项
```xml
<dependencies>
    <!-- Jackson for JSON -->
    <dependency>
        <groupId>com.fasterxml.jackson.core</groupId>
        <artifactId>jackson-databind</artifactId>
    </dependency>
    
    <!-- Apache Commons -->
    <dependency>
        <groupId>org.apache.commons</groupId>
        <artifactId>commons-lang3</artifactId>
    </dependency>
    
    <dependency>
        <groupId>commons-io</groupId>
        <artifactId>commons-io</artifactId>
    </dependency>
    
    <!-- Lombok -->
    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
        <scope>provided</scope>
    </dependency>
    
    <!-- Validation API -->
    <dependency>
        <groupId>jakarta.validation</groupId>
        <artifactId>jakarta.validation-api</artifactId>
    </dependency>
</dependencies>
```

---

### 3.2 goabroad-model (模型模块)

#### 职责
- 定义数据库实体类 (Entity)
- 定义数据传输对象 (DTO)
- 定义视图对象 (VO)
- 提供对象映射 (Mapper)

#### 核心类

```java
// Entity示例
@Entity
@Table(name = "users")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String username;
    private String email;
    private String passwordHash;
    private String avatarUrl;
    private String nickname;
    
    @Enumerated(EnumType.STRING)
    private UserStatus status;
    
    private Integer level;
    private Integer points;
    
    @CreatedDate
    private LocalDateTime createdAt;
    
    @LastModifiedDate
    private LocalDateTime updatedAt;
}

// Request DTO示例
@Data
public class LoginRequest {
    @NotBlank(message = "用户名不能为空")
    private String username;
    
    @NotBlank(message = "密码不能为空")
    private String password;
}

// Response DTO示例
@Data
public class UserResponse {
    private Long id;
    private String username;
    private String email;
    private String avatarUrl;
    private String nickname;
    private Integer level;
    private Integer points;
}

// Mapper接口 (MapStruct)
@Mapper(componentModel = "spring")
public interface UserMapper {
    UserResponse toResponse(User user);
    List<UserResponse> toResponseList(List<User> users);
}
```

#### 主要实体类

| 实体类 | 说明 | 核心字段 |
|--------|------|---------|
| `User` | 用户 | id, username, email, passwordHash, level, points |
| `Country` | 国家信息 | id, code, nameZh, nameEn, studyInfo, workInfo |
| `Plan` | 规划 | id, userId, countryCode, planType, timeline, progress |
| `MaterialChecklist` | 材料清单 | id, planId, materialName, status, fileUrls |
| `Post` | 帖子 | id, authorId, title, content, category, tags |
| `Comment` | 评论 | id, postId, authorId, content, parentId |
| `Reminder` | 提醒 | id, userId, planId, title, remindTime |

#### 依赖项
```xml
<dependencies>
    <!-- 依赖common模块 -->
    <dependency>
        <groupId>com.goabroad</groupId>
        <artifactId>goabroad-common</artifactId>
    </dependency>
    
    <!-- JPA -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-jpa</artifactId>
    </dependency>
    
    <!-- Validation -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-validation</artifactId>
    </dependency>
    
    <!-- MapStruct -->
    <dependency>
        <groupId>org.mapstruct</groupId>
        <artifactId>mapstruct</artifactId>
    </dependency>
    
    <!-- Lombok -->
    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
        <scope>provided</scope>
    </dependency>
    
    <!-- JSON处理 -->
    <dependency>
        <groupId>com.fasterxml.jackson.core</groupId>
        <artifactId>jackson-annotations</artifactId>
    </dependency>
</dependencies>
```

---

### 3.3 goabroad-security (安全模块)

#### 职责
- JWT令牌生成与验证
- 用户认证与授权
- 密码加密处理
- Security配置
- 权限拦截

#### 核心类

```java
// JWT工具类
@Component
public class JwtTokenProvider {
    public String generateToken(UserDetails userDetails);
    public boolean validateToken(String token);
    public String getUsernameFromToken(String token);
    public Claims getClaimsFromToken(String token);
}

// JWT过滤器
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, 
                                   HttpServletResponse response, 
                                   FilterChain filterChain) {
        // 从请求头提取JWT
        // 验证并设置SecurityContext
    }
}

// Security配置
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) {
        // 配置安全策略
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

// UserDetailsService实现
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String username) {
        // 从数据库加载用户
    }
}
```

#### 依赖项
```xml
<dependencies>
    <!-- 依赖common和model模块 -->
    <dependency>
        <groupId>com.goabroad</groupId>
        <artifactId>goabroad-common</artifactId>
    </dependency>
    <dependency>
        <groupId>com.goabroad</groupId>
        <artifactId>goabroad-model</artifactId>
    </dependency>
    
    <!-- Spring Security -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-security</artifactId>
    </dependency>
    
    <!-- JWT -->
    <dependency>
        <groupId>io.jsonwebtoken</groupId>
        <artifactId>jjwt-api</artifactId>
    </dependency>
    <dependency>
        <groupId>io.jsonwebtoken</groupId>
        <artifactId>jjwt-impl</artifactId>
    </dependency>
    <dependency>
        <groupId>io.jsonwebtoken</groupId>
        <artifactId>jjwt-jackson</artifactId>
    </dependency>
    
    <!-- Redis (存储Token黑名单) -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-redis</artifactId>
    </dependency>
</dependencies>
```

---

### 3.4 goabroad-repository (数据访问模块)

#### 职责
- 定义Repository接口
- 实现数据库CRUD操作
- 自定义查询方法
- 缓存层封装
- Elasticsearch操作

#### 核心接口

```java
// MySQL Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    
    @Query("SELECT u FROM User u WHERE u.level >= :level")
    List<User> findHighLevelUsers(@Param("level") Integer level);
}

public interface CountryRepository extends JpaRepository<Country, Long> {
    Optional<Country> findByCode(String code);
    List<Country> findByIsActiveTrue();
}

public interface PlanRepository extends JpaRepository<Plan, Long> {
    List<Plan> findByUserId(Long userId);
    List<Plan> findByUserIdAndStatus(Long userId, PlanStatus status);
    
    @Query("SELECT p FROM Plan p WHERE p.userId = :userId " +
           "AND p.status = 'ACTIVE' ORDER BY p.createdAt DESC")
    List<Plan> findActiveUserPlans(@Param("userId") Long userId);
}

public interface PostRepository extends JpaRepository<Post, Long> {
    Page<Post> findByCategory(String category, Pageable pageable);
    Page<Post> findByAuthorId(Long authorId, Pageable pageable);
    
    @Query("SELECT p FROM Post p WHERE p.status = 'PUBLISHED' " +
           "ORDER BY p.createdAt DESC")
    Page<Post> findPublishedPosts(Pageable pageable);
}

// Redis Cache Repository
@Repository
public class CacheRepository {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    
    public void set(String key, Object value, long timeout, TimeUnit unit);
    public Object get(String key);
    public void delete(String key);
    public boolean hasKey(String key);
}

// Elasticsearch Repository
public interface PostSearchRepository 
    extends ElasticsearchRepository<Post, Long> {
    
    List<Post> findByTitleContaining(String keyword);
    List<Post> findByContentContaining(String keyword);
}
```

#### 依赖项
```xml
<dependencies>
    <!-- 依赖model模块 -->
    <dependency>
        <groupId>com.goabroad</groupId>
        <artifactId>goabroad-model</artifactId>
    </dependency>
    <dependency>
        <groupId>com.goabroad</groupId>
        <artifactId>goabroad-common</artifactId>
    </dependency>
    
    <!-- Spring Data JPA -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-jpa</artifactId>
    </dependency>
    
    <!-- MySQL Driver -->
    <dependency>
        <groupId>com.mysql</groupId>
        <artifactId>mysql-connector-j</artifactId>
    </dependency>
    
    <!-- Spring Data Redis -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-redis</artifactId>
    </dependency>
    
    <dependency>
        <groupId>org.apache.commons</groupId>
        <artifactId>commons-pool2</artifactId>
    </dependency>
    
    <!-- Elasticsearch (可选) -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-elasticsearch</artifactId>
    </dependency>
    
    <!-- Querydsl (复杂查询，可选) -->
    <dependency>
        <groupId>com.querydsl</groupId>
        <artifactId>querydsl-jpa</artifactId>
    </dependency>
</dependencies>
```

---

### 3.5 goabroad-service (业务逻辑模块)

#### 职责
- 实现核心业务逻辑
- 事务管理
- 业务规则验证
- 调用Repository
- 集成第三方服务

#### 核心服务

##### 3.5.1 用户服务 (user/)

```java
public interface UserService {
    UserResponse getUserById(Long id);
    UserResponse getUserByUsername(String username);
    UserResponse updateUser(Long id, UpdateUserRequest request);
    void deleteUser(Long id);
    PageResponse<UserResponse> searchUsers(UserSearchRequest request);
}

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserMapper userMapper;
    
    // 实现方法...
}
```

##### 3.5.2 认证服务 (auth/)

```java
public interface AuthService {
    LoginResponse login(LoginRequest request);
    RegisterResponse register(RegisterRequest request);
    void logout(String token);
    TokenResponse refreshToken(String refreshToken);
    void sendVerificationEmail(String email);
    void resetPassword(ResetPasswordRequest request);
}
```

##### 3.5.3 国家信息服务 (country/)

```java
public interface CountryService {
    List<CountryResponse> getAllCountries();
    CountryDetailResponse getCountryByCode(String code);
    CountryStudyInfoResponse getStudyInfo(String code);
    CountryWorkInfoResponse getWorkInfo(String code);
    List<CountryResponse> searchCountries(CountrySearchRequest request);
}
```

##### 3.5.4 规划服务 (planning/)

```java
public interface PlanService {
    PlanResponse createPlan(CreatePlanRequest request);
    PlanResponse getPlanById(Long id);
    List<PlanResponse> getUserPlans(Long userId);
    PlanResponse updatePlan(Long id, UpdatePlanRequest request);
    void deletePlan(Long id);
    TimelineResponse generateTimeline(GenerateTimelineRequest request);
}

public interface MaterialService {
    List<MaterialResponse> getPlanMaterials(Long planId);
    MaterialResponse addMaterial(Long planId, AddMaterialRequest request);
    MaterialResponse updateMaterialStatus(Long id, MaterialStatus status);
    void deleteMaterial(Long id);
    String uploadMaterialFile(Long materialId, MultipartFile file);
}

public interface TimelineService {
    TimelineResponse getTimeline(Long planId);
    TaskResponse completeTask(Long taskId);
    void setTaskReminder(Long taskId, LocalDateTime remindTime);
}
```

##### 3.5.5 社区服务 (community/)

```java
public interface PostService {
    PostResponse createPost(CreatePostRequest request);
    PostResponse getPostById(Long id);
    PageResponse<PostResponse> getPosts(PostQueryRequest request);
    PostResponse updatePost(Long id, UpdatePostRequest request);
    void deletePost(Long id);
    void likePost(Long postId, Long userId);
    void collectPost(Long postId, Long userId);
}

public interface CommentService {
    CommentResponse addComment(AddCommentRequest request);
    PageResponse<CommentResponse> getPostComments(Long postId);
    void deleteComment(Long id);
    void likeComment(Long commentId, Long userId);
}
```

##### 3.5.6 工具服务 (tool/)

```java
public interface CostCalculatorService {
    CostEstimateResponse calculateCost(CostCalculateRequest request);
}

public interface GpaConverterService {
    GpaConvertResponse convertGpa(GpaConvertRequest request);
}

public interface VisaSlotService {
    List<VisaSlotResponse> getAvailableSlots(String country);
    void subscribeSlotNotification(Long userId, String country);
}
```

##### 3.5.7 通知服务 (notification/)

```java
public interface NotificationService {
    void sendNotification(Long userId, NotificationRequest request);
    PageResponse<NotificationResponse> getUserNotifications(Long userId);
    void markAsRead(Long notificationId);
}

public interface ReminderService {
    ReminderResponse createReminder(CreateReminderRequest request);
    List<ReminderResponse> getUserReminders(Long userId);
    void deleteReminder(Long id);
    void checkAndSendReminders(); // 定时任务调用
}
```

##### 3.5.8 文件存储服务 (storage/)

```java
public interface FileStorageService {
    String uploadFile(MultipartFile file, String folder);
    void deleteFile(String fileUrl);
    byte[] downloadFile(String fileUrl);
    String getPresignedUrl(String fileUrl, int expireMinutes);
}

@Service
public class FileStorageServiceImpl implements FileStorageService {
    // 对接OSS/S3/MinIO
}
```

#### 依赖项
```xml
<dependencies>
    <!-- 依赖其他模块 -->
    <dependency>
        <groupId>com.goabroad</groupId>
        <artifactId>goabroad-common</artifactId>
    </dependency>
    <dependency>
        <groupId>com.goabroad</groupId>
        <artifactId>goabroad-model</artifactId>
    </dependency>
    <dependency>
        <groupId>com.goabroad</groupId>
        <artifactId>goabroad-repository</artifactId>
    </dependency>
    <dependency>
        <groupId>com.goabroad</groupId>
        <artifactId>goabroad-security</artifactId>
    </dependency>
    
    <!-- Spring Boot -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter</artifactId>
    </dependency>
    
    <!-- 事务支持 -->
    <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-tx</artifactId>
    </dependency>
    
    <!-- 缓存 -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-cache</artifactId>
    </dependency>
    
    <dependency>
        <groupId>com.github.ben-manes.caffeine</groupId>
        <artifactId>caffeine</artifactId>
    </dependency>
    
    <!-- 文件存储 (选择一个) -->
    <!-- 阿里云OSS -->
    <dependency>
        <groupId>com.aliyun.oss</groupId>
        <artifactId>aliyun-sdk-oss</artifactId>
    </dependency>
    
    <!-- 邮件服务 -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-mail</artifactId>
    </dependency>
    
    <!-- 短信服务 -->
    <dependency>
        <groupId>com.aliyun</groupId>
        <artifactId>dysmsapi20170525</artifactId>
    </dependency>
    
    <!-- HTTP客户端 -->
    <dependency>
        <groupId>com.squareup.okhttp3</groupId>
        <artifactId>okhttp</artifactId>
    </dependency>
    
    <!-- 定时任务 -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-quartz</artifactId>
    </dependency>
    
    <!-- 消息队列 -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-amqp</artifactId>
    </dependency>
</dependencies>
```

---

### 3.6 goabroad-web (Web接口模块)

#### 职责
- 接收HTTP请求
- 参数验证
- 调用Service层
- 返回响应
- 全局异常处理
- API文档
- 应用启动入口

#### 核心Controller

```java
@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "认证接口", description = "用户注册、登录、登出")
public class AuthController {
    @Autowired
    private AuthService authService;
    
    @PostMapping("/register")
    @Operation(summary = "用户注册")
    public Result<RegisterResponse> register(
        @Valid @RequestBody RegisterRequest request) {
        return Result.success(authService.register(request));
    }
    
    @PostMapping("/login")
    @Operation(summary = "用户登录")
    public Result<LoginResponse> login(
        @Valid @RequestBody LoginRequest request) {
        return Result.success(authService.login(request));
    }
}

@RestController
@RequestMapping("/api/v1/users")
@Tag(name = "用户接口")
public class UserController {
    @Autowired
    private UserService userService;
    
    @GetMapping("/me")
    @Operation(summary = "获取当前用户信息")
    public Result<UserResponse> getCurrentUser() {
        // 从SecurityContext获取当前用户
    }
    
    @PutMapping("/me")
    @Operation(summary = "更新用户信息")
    public Result<UserResponse> updateCurrentUser(
        @Valid @RequestBody UpdateUserRequest request) {
        // ...
    }
}

@RestController
@RequestMapping("/api/v1/countries")
@Tag(name = "国家信息接口")
public class CountryController {
    @Autowired
    private CountryService countryService;
    
    @GetMapping
    @Operation(summary = "获取国家列表")
    public Result<List<CountryResponse>> getAllCountries() {
        return Result.success(countryService.getAllCountries());
    }
    
    @GetMapping("/{code}")
    @Operation(summary = "获取国家详情")
    public Result<CountryDetailResponse> getCountry(
        @PathVariable String code) {
        return Result.success(countryService.getCountryByCode(code));
    }
}

@RestController
@RequestMapping("/api/v1/plans")
@Tag(name = "规划接口")
public class PlanController {
    @Autowired
    private PlanService planService;
    @Autowired
    private MaterialService materialService;
    
    @PostMapping
    @Operation(summary = "创建规划")
    public Result<PlanResponse> createPlan(
        @Valid @RequestBody CreatePlanRequest request) {
        return Result.success(planService.createPlan(request));
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "获取规划详情")
    public Result<PlanResponse> getPlan(@PathVariable Long id) {
        return Result.success(planService.getPlanById(id));
    }
    
    @GetMapping("/{id}/materials")
    @Operation(summary = "获取材料清单")
    public Result<List<MaterialResponse>> getMaterials(
        @PathVariable Long id) {
        return Result.success(materialService.getPlanMaterials(id));
    }
}

@RestController
@RequestMapping("/api/v1/community")
@Tag(name = "社区接口")
public class CommunityController {
    @Autowired
    private PostService postService;
    @Autowired
    private CommentService commentService;
    
    @GetMapping("/posts")
    @Operation(summary = "获取帖子列表")
    public Result<PageResponse<PostResponse>> getPosts(
        PostQueryRequest request) {
        return Result.success(postService.getPosts(request));
    }
    
    @PostMapping("/posts")
    @Operation(summary = "发布帖子")
    public Result<PostResponse> createPost(
        @Valid @RequestBody CreatePostRequest request) {
        return Result.success(postService.createPost(request));
    }
}
```

#### 全局异常处理

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
    public Result<?> handleValidationException(
        MethodArgumentNotValidException e) {
        String message = e.getBindingResult()
            .getFieldError()
            .getDefaultMessage();
        return Result.error(400, message);
    }
    
    @ExceptionHandler(Exception.class)
    public Result<?> handleException(Exception e) {
        log.error("系统异常", e);
        return Result.error(500, "系统错误，请稍后重试");
    }
}
```

#### 配置类

```java
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
            .allowedOrigins("*")
            .allowedMethods("GET", "POST", "PUT", "DELETE")
            .allowedHeaders("*")
            .maxAge(3600);
    }
    
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 添加拦截器
    }
}

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("GoAbroad API")
                .version("v1.0")
                .description("出国助手API文档"));
    }
}
```

#### 依赖项
```xml
<dependencies>
    <!-- 依赖所有模块 -->
    <dependency>
        <groupId>com.goabroad</groupId>
        <artifactId>goabroad-common</artifactId>
    </dependency>
    <dependency>
        <groupId>com.goabroad</groupId>
        <artifactId>goabroad-model</artifactId>
    </dependency>
    <dependency>
        <groupId>com.goabroad</groupId>
        <artifactId>goabroad-security</artifactId>
    </dependency>
    <dependency>
        <groupId>com.goabroad</groupId>
        <artifactId>goabroad-repository</artifactId>
    </dependency>
    <dependency>
        <groupId>com.goabroad</groupId>
        <artifactId>goabroad-service</artifactId>
    </dependency>
    
    <!-- Spring Boot Web -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    
    <!-- Validation -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-validation</artifactId>
    </dependency>
    
    <!-- Swagger/OpenAPI -->
    <dependency>
        <groupId>org.springdoc</groupId>
        <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
    </dependency>
    
    <!-- Actuator (监控) -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-actuator</artifactId>
    </dependency>
    
    <!-- Micrometer (指标) -->
    <dependency>
        <groupId>io.micrometer</groupId>
        <artifactId>micrometer-registry-prometheus</artifactId>
    </dependency>
    
    <!-- 限流 -->
    <dependency>
        <groupId>io.github.resilience4j</groupId>
        <artifactId>resilience4j-spring-boot3</artifactId>
    </dependency>
    
    <!-- 数据库迁移 -->
    <dependency>
        <groupId>org.flywaydb</groupId>
        <artifactId>flyway-core</artifactId>
    </dependency>
    <dependency>
        <groupId>org.flywaydb</groupId>
        <artifactId>flyway-mysql</artifactId>
    </dependency>
    
    <!-- 热部署 (开发环境) -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-devtools</artifactId>
        <scope>runtime</scope>
        <optional>true</optional>
    </dependency>
    
    <!-- 测试 -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-test</artifactId>
        <scope>test</scope>
    </dependency>
</dependencies>
```

---

## 4. 依赖关系图

### 4.1 模块依赖关系

```
goabroad-web (启动层)
    │
    ├──→ goabroad-service (业务层)
    │       │
    │       ├──→ goabroad-repository (数据访问层)
    │       │       │
    │       │       └──→ goabroad-model (模型层)
    │       │               │
    │       │               └──→ goabroad-common (公共层)
    │       │
    │       ├──→ goabroad-security (安全层)
    │       │       │
    │       │       └──→ goabroad-model
    │       │               │
    │       │               └──→ goabroad-common
    │       │
    │       └──→ goabroad-common
    │
    └──→ goabroad-common
```

### 4.2 依赖规则

✅ **允许的依赖**:
- Web → Service → Repository → Model → Common
- Security → Model → Common
- 任何模块 → Common

❌ **禁止的依赖**:
- Common → 任何其他模块
- Model → Repository/Service/Web
- Repository → Service/Web
- Service → Web

---

## 5. POM配置

### 5.1 父POM (goabroad-backend/pom.xml)

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 
         http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.goabroad</groupId>
    <artifactId>goabroad-backend</artifactId>
    <version>1.0.0</version>
    <packaging>pom</packaging>

    <name>GoAbroad Backend</name>
    <description>GoAbroad出国助手后端服务</description>

    <!-- 子模块 -->
    <modules>
        <module>goabroad-common</module>
        <module>goabroad-model</module>
        <module>goabroad-security</module>
        <module>goabroad-repository</module>
        <module>goabroad-service</module>
        <module>goabroad-web</module>
    </modules>

    <!-- 继承Spring Boot Parent -->
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>3.1.5</version>
        <relativePath/>
    </parent>

    <properties>
        <!-- Java版本 -->
        <java.version>17</java.version>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        
        <!-- 内部模块版本 -->
        <goabroad.version>1.0.0</goabroad.version>
        
        <!-- 依赖版本 -->
        <jwt.version>0.12.3</jwt.version>
        <mapstruct.version>1.5.5.Final</mapstruct.version>
        <springdoc.version>2.2.0</springdoc.version>
        <aliyun-oss.version>3.17.2</aliyun-oss.version>
        <aliyun-sms.version>2.0.24</aliyun-sms.version>
        <commons-lang3.version>3.13.0</commons-lang3.version>
        <commons-io.version>2.15.0</commons-io.version>
        <guava.version>32.1.3-jre</guava.version>
        <okhttp.version>4.12.0</okhttp.version>
        <resilience4j.version>2.1.0</resilience4j.version>
    </properties>

    <!-- 统一依赖管理 -->
    <dependencyManagement>
        <dependencies>
            <!-- ===== 内部模块 ===== -->
            <dependency>
                <groupId>com.goabroad</groupId>
                <artifactId>goabroad-common</artifactId>
                <version>${goabroad.version}</version>
            </dependency>
            <dependency>
                <groupId>com.goabroad</groupId>
                <artifactId>goabroad-model</artifactId>
                <version>${goabroad.version}</version>
            </dependency>
            <dependency>
                <groupId>com.goabroad</groupId>
                <artifactId>goabroad-security</artifactId>
                <version>${goabroad.version}</version>
            </dependency>
            <dependency>
                <groupId>com.goabroad</groupId>
                <artifactId>goabroad-repository</artifactId>
                <version>${goabroad.version}</version>
            </dependency>
            <dependency>
                <groupId>com.goabroad</groupId>
                <artifactId>goabroad-service</artifactId>
                <version>${goabroad.version}</version>
            </dependency>

            <!-- ===== JWT ===== -->
            <dependency>
                <groupId>io.jsonwebtoken</groupId>
                <artifactId>jjwt-api</artifactId>
                <version>${jwt.version}</version>
            </dependency>
            <dependency>
                <groupId>io.jsonwebtoken</groupId>
                <artifactId>jjwt-impl</artifactId>
                <version>${jwt.version}</version>
            </dependency>
            <dependency>
                <groupId>io.jsonwebtoken</groupId>
                <artifactId>jjwt-jackson</artifactId>
                <version>${jwt.version}</version>
            </dependency>

            <!-- ===== MapStruct ===== -->
            <dependency>
                <groupId>org.mapstruct</groupId>
                <artifactId>mapstruct</artifactId>
                <version>${mapstruct.version}</version>
            </dependency>
            <dependency>
                <groupId>org.mapstruct</groupId>
                <artifactId>mapstruct-processor</artifactId>
                <version>${mapstruct.version}</version>
            </dependency>

            <!-- ===== Springdoc OpenAPI ===== -->
            <dependency>
                <groupId>org.springdoc</groupId>
                <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
                <version>${springdoc.version}</version>
            </dependency>

            <!-- ===== 阿里云OSS ===== -->
            <dependency>
                <groupId>com.aliyun.oss</groupId>
                <artifactId>aliyun-sdk-oss</artifactId>
                <version>${aliyun-oss.version}</version>
            </dependency>

            <!-- ===== 阿里云短信 ===== -->
            <dependency>
                <groupId>com.aliyun</groupId>
                <artifactId>dysmsapi20170525</artifactId>
                <version>${aliyun-sms.version}</version>
            </dependency>

            <!-- ===== Apache Commons ===== -->
            <dependency>
                <groupId>org.apache.commons</groupId>
                <artifactId>commons-lang3</artifactId>
                <version>${commons-lang3.version}</version>
            </dependency>
            <dependency>
                <groupId>commons-io</groupId>
                <artifactId>commons-io</artifactId>
                <version>${commons-io.version}</version>
            </dependency>

            <!-- ===== Guava ===== -->
            <dependency>
                <groupId>com.google.guava</groupId>
                <artifactId>guava</artifactId>
                <version>${guava.version}</version>
            </dependency>

            <!-- ===== OkHttp ===== -->
            <dependency>
                <groupId>com.squareup.okhttp3</groupId>
                <artifactId>okhttp</artifactId>
                <version>${okhttp.version}</version>
            </dependency>

            <!-- ===== Resilience4j ===== -->
            <dependency>
                <groupId>io.github.resilience4j</groupId>
                <artifactId>resilience4j-spring-boot3</artifactId>
                <version>${resilience4j.version}</version>
            </dependency>
        </dependencies>
    </dependencyManagement>

    <build>
        <pluginManagement>
            <plugins>
                <!-- Spring Boot Maven Plugin -->
                <plugin>
                    <groupId>org.springframework.boot</groupId>
                    <artifactId>spring-boot-maven-plugin</artifactId>
                </plugin>

                <!-- Maven Compiler Plugin -->
                <plugin>
                    <groupId>org.apache.maven.plugins</groupId>
                    <artifactId>maven-compiler-plugin</artifactId>
                    <configuration>
                        <source>${java.version}</source>
                        <target>${java.version}</target>
                        <annotationProcessorPaths>
                            <!-- Lombok -->
                            <path>
                                <groupId>org.projectlombok</groupId>
                                <artifactId>lombok</artifactId>
                                <version>${lombok.version}</version>
                            </path>
                            <!-- MapStruct -->
                            <path>
                                <groupId>org.mapstruct</groupId>
                                <artifactId>mapstruct-processor</artifactId>
                                <version>${mapstruct.version}</version>
                            </path>
                        </annotationProcessorPaths>
                    </configuration>
                </plugin>
            </plugins>
        </pluginManagement>
    </build>
</project>
```

### 5.2 子模块POM示例

#### goabroad-common/pom.xml
```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 
         http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <parent>
        <groupId>com.goabroad</groupId>
        <artifactId>goabroad-backend</artifactId>
        <version>1.0.0</version>
    </parent>

    <artifactId>goabroad-common</artifactId>
    <packaging>jar</packaging>

    <name>GoAbroad Common</name>
    <description>公共模块-工具类和常量</description>

    <dependencies>
        <!-- Jackson -->
        <dependency>
            <groupId>com.fasterxml.jackson.core</groupId>
            <artifactId>jackson-databind</artifactId>
        </dependency>

        <!-- Apache Commons -->
        <dependency>
            <groupId>org.apache.commons</groupId>
            <artifactId>commons-lang3</artifactId>
        </dependency>
        <dependency>
            <groupId>commons-io</groupId>
            <artifactId>commons-io</artifactId>
        </dependency>

        <!-- Guava -->
        <dependency>
            <groupId>com.google.guava</groupId>
            <artifactId>guava</artifactId>
        </dependency>

        <!-- Lombok -->
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <scope>provided</scope>
        </dependency>

        <!-- Validation API -->
        <dependency>
            <groupId>jakarta.validation</groupId>
            <artifactId>jakarta.validation-api</artifactId>
        </dependency>
    </dependencies>
</project>
```

#### goabroad-web/pom.xml
```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 
         http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <parent>
        <groupId>com.goabroad</groupId>
        <artifactId>goabroad-backend</artifactId>
        <version>1.0.0</version>
    </parent>

    <artifactId>goabroad-web</artifactId>
    <packaging>jar</packaging>

    <name>GoAbroad Web</name>
    <description>Web层-Controller和启动类</description>

    <dependencies>
        <!-- 依赖内部模块 -->
        <dependency>
            <groupId>com.goabroad</groupId>
            <artifactId>goabroad-common</artifactId>
        </dependency>
        <dependency>
            <groupId>com.goabroad</groupId>
            <artifactId>goabroad-model</artifactId>
        </dependency>
        <dependency>
            <groupId>com.goabroad</groupId>
            <artifactId>goabroad-security</artifactId>
        </dependency>
        <dependency>
            <groupId>com.goabroad</groupId>
            <artifactId>goabroad-repository</artifactId>
        </dependency>
        <dependency>
            <groupId>com.goabroad</groupId>
            <artifactId>goabroad-service</artifactId>
        </dependency>

        <!-- Spring Boot Web -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>

        <!-- Validation -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-validation</artifactId>
        </dependency>

        <!-- Swagger -->
        <dependency>
            <groupId>org.springdoc</groupId>
            <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
        </dependency>

        <!-- Actuator -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-actuator</artifactId>
        </dependency>

        <!-- Flyway -->
        <dependency>
            <groupId>org.flywaydb</groupId>
            <artifactId>flyway-core</artifactId>
        </dependency>
        <dependency>
            <groupId>org.flywaydb</groupId>
            <artifactId>flyway-mysql</artifactId>
        </dependency>

        <!-- DevTools -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-devtools</artifactId>
            <scope>runtime</scope>
            <optional>true</optional>
        </dependency>

        <!-- Test -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <!-- Spring Boot打包插件 -->
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
                <configuration>
                    <mainClass>com.goabroad.web.Application</mainClass>
                </configuration>
            </plugin>
        </plugins>
    </build>
</project>
```

---

## 6. 包结构规范

### 6.1 命名规范

```
com.goabroad.{module}.{layer}.{business}

示例:
- com.goabroad.common.util.DateUtil
- com.goabroad.model.entity.User
- com.goabroad.security.jwt.JwtTokenProvider
- com.goabroad.repository.mysql.UserRepository
- com.goabroad.service.user.UserService
- com.goabroad.web.controller.UserController
```

### 6.2 类命名规范

| 类型 | 命名规范 | 示例 |
|-----|---------|------|
| Entity | 名词 | User, Country, Plan |
| DTO (Request) | 动词+名词+Request | CreateUserRequest, LoginRequest |
| DTO (Response) | 名词+Response | UserResponse, LoginResponse |
| VO | 名词+VO | UserVO, PostVO |
| Repository | 名词+Repository | UserRepository |
| Service接口 | 名词+Service | UserService |
| Service实现 | 名词+ServiceImpl | UserServiceImpl |
| Controller | 名词+Controller | UserController |
| Mapper | 名词+Mapper | UserMapper |
| Util | 功能+Util | DateUtil, StringUtil |
| Exception | 名词+Exception | BusinessException |
| Config | 功能+Config | SecurityConfig, RedisConfig |

---

## 7. 数据流转

### 7.1 请求处理流程

```
Client (前端)
    ↓ HTTP Request
Filter (过滤器)
    ↓ JWT验证
Interceptor (拦截器)
    ↓ 限流、日志
Controller (控制器)
    ↓ 参数验证
Service (业务逻辑)
    ↓ 事务处理
Repository (数据访问)
    ↓ SQL查询
Database (数据库)
    ↓ 返回数据
Repository
    ↓ Entity → DTO
Service
    ↓ 业务处理
Controller
    ↓ 统一响应格式
Client
```

### 7.2 对象转换

```
Entity (数据库实体)
    ↓ Mapper (MapStruct)
DTO (数据传输对象)
    ↓
Client (前端)

注意:
- Controller → Service: Request DTO
- Service → Repository: Entity
- Repository → Service: Entity
- Service → Controller: Response DTO
- Controller → Client: Result<Response DTO>
```

---

## 8. 开发规范

### 8.1 代码规范

1. **统一使用Lombok**减少样板代码
2. **Service层方法必须加@Transactional**
3. **Controller只负责参数接收和响应**，不写业务逻辑
4. **Repository只负责数据访问**，不写业务逻辑
5. **异常统一使用BusinessException**，由全局异常处理器处理
6. **日志使用@Slf4j**，统一格式
7. **所有API必须写Swagger注解**

### 8.2 Git提交规范

```
feat: 新功能
fix: 修复Bug
docs: 文档更新
style: 代码格式调整
refactor: 重构
test: 测试
chore: 构建工具或依赖更新

示例:
feat(user): 添加用户注册功能
fix(plan): 修复规划创建时的空指针异常
docs(api): 更新API文档
```

### 8.3 分支管理

```
main/master     - 生产环境
develop         - 开发环境
feature/xxx     - 功能分支
hotfix/xxx      - 紧急修复分支
```

---

## 9. 构建和运行

### 9.1 本地开发

```bash
# 1. 克隆项目
git clone https://github.com/your-repo/goabroad-backend.git
cd goabroad-backend

# 2. 编译项目
mvn clean install -DskipTests

# 3. 启动数据库和Redis (Docker)
docker-compose up -d mysql redis

# 4. 运行Flyway迁移
mvn flyway:migrate

# 5. 启动应用
cd goabroad-web
mvn spring-boot:run

# 6. 访问Swagger文档
http://localhost:8080/swagger-ui.html
```

### 9.2 打包部署

```bash
# 打包
mvn clean package -DskipTests

# 运行
java -jar goabroad-web/target/goabroad-web-1.0.0.jar

# Docker打包
docker build -t goabroad-backend:1.0.0 .

# Docker运行
docker run -d -p 8080:8080 \
  --name goabroad-backend \
  -e SPRING_PROFILES_ACTIVE=prod \
  goabroad-backend:1.0.0
```

---

## 10. 总结

这份架构设计遵循以下原则：

✅ **清晰的职责划分** - 每个模块职责单一明确  
✅ **合理的依赖关系** - 避免循环依赖  
✅ **易于测试** - 每个模块可独立测试  
✅ **便于扩展** - 新功能只需新增Service和Controller  
✅ **符合最佳实践** - 符合企业级项目标准  
✅ **适合毕业设计** - 既不过度复杂，又体现架构能力  

通过这个多模块架构，您的项目将具备良好的可维护性、可测试性和可扩展性，同时也能在毕业答辩中充分展示您的架构设计能力。

---

**文档编写人**: AI Assistant  
**最后更新**: 2024年10月  
**文档状态**: v1.0 正式版