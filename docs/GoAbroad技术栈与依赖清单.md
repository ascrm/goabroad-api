# 📦 GoAbroad 出国助手 - 技术栈与依赖清单

## 前端技术栈 (React Native)

### 1. 核心框架

```json
{
  "dependencies": {    
    // ===== 导航 =====
    "@react-navigation/stack": "^6.3.20",
    "@react-navigation/drawer": "^6.6.6",
    
    // ===== 状态管理 =====
    "@reduxjs/toolkit": "^1.9.7",
    "react-redux": "^8.1.3",
    "redux-persist": "^6.0.0",
    "redux-thunk": "^2.4.2",
    
    // ===== 网络请求 =====
    "axios": "^1.6.0",
    "@tanstack/react-query": "^5.0.0",
    
    // ===== UI组件库 =====
    "react-native-paper": "^5.11.0", // Material Design
    
    // ===== 图标 =====
    "@expo/vector-icons": "^13.0.0", // 如果使用Expo
    
    // ===== 动画 =====
    "react-native-reanimated": "^3.5.4",
    "lottie-react-native": "^6.4.1", // Lottie动画
    
    // ===== 表单处理 =====
    "react-hook-form": "^7.48.2",
    "yup": "^1.3.3", // 表单验证
    
    // ===== 日期时间 =====
    "dayjs": "^1.11.10",
    "react-native-date-picker": "^4.3.3",
    
    // ===== 图片处理 =====
    "react-native-image-picker": "^7.0.3",
    "react-native-expo-image-cache": "^8.6.3", // 图片缓存
    "react-native-image-crop-picker": "^0.40.0",
    "react-native-image-viewing": "^0.2.2",
    
    // ===== 文件处理 =====
    "react-native-document-picker": "^9.1.0",
    "react-native-fs": "^2.20.0",
    "react-native-pdf": "^6.7.3",
    
    // ===== 本地存储 =====
    "@react-native-async-storage/async-storage": "^1.19.5",
    "react-native-mmkv": "^2.10.2", // 高性能存储
    
    // ===== 推送通知 =====
    "@react-native-firebase/messaging": "^18.6.1",
    "@react-native-firebase/analytics": "^18.6.1",
    "@notifee/react-native": "^7.8.0", // 本地通知
    
    // ===== WebView =====
    "react-native-webview": "^13.6.2",
    
    // ===== 地图定位 =====
    "react-native-maps": "^1.8.2",
    "@react-native-community/geolocation": "^3.1.0",
    
    // ===== 分享 =====
    "react-native-share": "^10.0.0",
    
    // ===== 设备信息 =====
    "react-native-device-info": "^10.11.0",
    
    // ===== 网络状态 =====
    "@react-native-community/netinfo": "^11.1.0",
    
    // ===== 键盘处理 =====
    "react-native-keyboard-aware-scroll-view": "^0.9.5",
    
    // ===== 下拉刷新 =====
    "react-native-refresh-control": "^1.0.1",
    
    // ===== 图表 =====
    "react-native-chart-kit": "^6.12.0",
    "victory-native": "^36.8.6",
    
    // ===== 视频播放 =====
    "react-native-video": "^5.2.1",
    
    // ===== 相机 =====
    "react-native-camera": "^4.2.1",
    // 或
    "react-native-vision-camera": "^3.6.2",
    
    // ===== 启动屏 =====
    "react-native-splash-screen": "^3.3.0",
    
    // ===== 崩溃监控 =====
    "@sentry/react-native": "^5.13.0",
    
    // ===== 国际化 =====
    "i18next": "^23.7.6",
    "react-i18next": "^13.5.0",
    "react-native-localize": "^3.0.3",
    
    // ===== 工具库 =====
    "lodash": "^4.17.21",
    "ramda": "^0.29.1",
    "uuid": "^9.0.1",
    
    // ===== 样式工具 =====
    "tailwind-rn": "^4.2.0"
  },
  
  "devDependencies": {
    // ===== TypeScript =====
    "typescript": "^5.2.2",
    "@types/react": "^18.2.37",
    "@types/react-native": "^0.72.6",
    "@types/lodash": "^4.14.201",
    
    // ===== 测试 =====
    "@testing-library/react-native": "^12.4.0",
    "@testing-library/jest-native": "^5.4.3",
    "jest": "^29.7.0",
    "detox": "^20.13.5", // E2E测试
    
    // ===== 代码规范 =====
    "eslint": "^8.53.0",
    "@typescript-eslint/eslint-plugin": "^6.11.0",
    "@typescript-eslint/parser": "^6.11.0",
    "eslint-plugin-react": "^7.33.2",
    "eslint-plugin-react-native": "^4.1.0",
    "prettier": "^3.1.0",
    
    // ===== 构建工具 =====
    "@react-native-community/cli": "^12.0.0",
    "metro-react-native-babel-preset": "^0.77.0",
    
    // ===== 其他 =====
    "@babel/core": "^7.23.3",
    "@babel/runtime": "^7.23.4",
    "react-native-dotenv": "^3.4.9" // 环境变量
  }
}
```

### 2. 前端开发工具

```bash
# ===== 包管理器 =====
npm / yarn / pnpm

# ===== React Native CLI =====
npx react-native init GoAbroad

# ===== 调试工具 =====
- Flipper (官方调试工具)
- React Native Debugger
- Reactotron

# ===== 代码编辑器 =====
- VS Code
  插件：
  - React Native Tools
  - ES7+ React/Redux/React-Native snippets
  - Prettier
  - ESLint
  - Auto Import

# ===== 版本管理 =====
- Git
- GitHub / GitLab

# ===== CI/CD =====
- GitHub Actions
- Fastlane (自动化构建)
- CodePush (热更新)

# ===== 性能监控 =====
- Firebase Performance Monitoring
- Sentry
- New Relic Mobile

# ===== 测试分发 =====
- TestFlight (iOS)
- Firebase App Distribution
- AppCenter
```

---

## 后端技术栈 (Java Spring)

### 1. 核心框架

```xml
<!-- pom.xml -->
<properties>
    <java.version>17</java.version>
    <spring-boot.version>3.1.5</spring-boot.version>
</properties>

<dependencies>
    <!-- ===== Spring Boot 核心 ===== -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-jpa</artifactId>
    </dependency>
    
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-validation</artifactId>
    </dependency>
    
    <!-- ===== 数据库 ===== -->
    <!-- MySQL -->
    <dependency>
        <groupId>com.mysql</groupId>
        <artifactId>mysql-connector-j</artifactId>
        <version>8.1.0</version>
    </dependency>
    
    <!-- PostgreSQL (可选) -->
    <dependency>
        <groupId>org.postgresql</groupId>
        <artifactId>postgresql</artifactId>
        <version>42.6.0</version>
    </dependency>
    
    <!-- Hikari 连接池 (Spring Boot 默认) -->
    <dependency>
        <groupId>com.zaxxer</groupId>
        <artifactId>HikariCP</artifactId>
    </dependency>
    
    <!-- ===== Redis ===== -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-redis</artifactId>
    </dependency>
    
    <dependency>
        <groupId>org.apache.commons</groupId>
        <artifactId>commons-pool2</artifactId>
    </dependency>
    
    <!-- ===== 缓存 ===== -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-cache</artifactId>
    </dependency>
    
    <dependency>
        <groupId>com.github.ben-manes.caffeine</groupId>
        <artifactId>caffeine</artifactId>
        <version>3.1.8</version>
    </dependency>
    
    <!-- ===== 安全认证 ===== -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-security</artifactId>
    </dependency>
    
    <!-- JWT -->
    <dependency>
        <groupId>io.jsonwebtoken</groupId>
        <artifactId>jjwt-api</artifactId>
        <version>0.12.3</version>
    </dependency>
    <dependency>
        <groupId>io.jsonwebtoken</groupId>
        <artifactId>jjwt-impl</artifactId>
        <version>0.12.3</version>
    </dependency>
    <dependency>
        <groupId>io.jsonwebtoken</groupId>
        <artifactId>jjwt-jackson</artifactId>
        <version>0.12.3</version>
    </dependency>
    
    <!-- OAuth2 (第三方登录) -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-oauth2-client</artifactId>
    </dependency>
    
    <!-- ===== API 文档 ===== -->
    <!-- Springdoc OpenAPI 3 (替代 Swagger 2) -->
    <dependency>
        <groupId>org.springdoc</groupId>
        <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
        <version>2.2.0</version>
    </dependency>
    
    <!-- ===== 对象映射 ===== -->
    <!-- MapStruct -->
    <dependency>
        <groupId>org.mapstruct</groupId>
        <artifactId>mapstruct</artifactId>
        <version>1.5.5.Final</version>
    </dependency>
    <dependency>
        <groupId>org.mapstruct</groupId>
        <artifactId>mapstruct-processor</artifactId>
        <version>1.5.5.Final</version>
        <scope>provided</scope>
    </dependency>
    
    <!-- ===== JSON 处理 ===== -->
    <dependency>
        <groupId>com.fasterxml.jackson.core</groupId>
        <artifactId>jackson-databind</artifactId>
    </dependency>
    
    <dependency>
        <groupId>com.fasterxml.jackson.datatype</groupId>
        <artifactId>jackson-datatype-jsr310</artifactId>
    </dependency>
    
    <!-- ===== 工具类 ===== -->
    <!-- Lombok -->
    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
        <scope>provided</scope>
    </dependency>
    
    <!-- Apache Commons -->
    <dependency>
        <groupId>org.apache.commons</groupId>
        <artifactId>commons-lang3</artifactId>
        <version>3.13.0</version>
    </dependency>
    
    <dependency>
        <groupId>commons-io</groupId>
        <artifactId>commons-io</artifactId>
        <version>2.15.0</version>
    </dependency>
    
    <!-- Guava -->
    <dependency>
        <groupId>com.google.guava</groupId>
        <artifactId>guava</artifactId>
        <version>32.1.3-jre</version>
    </dependency>
    
    <!-- ===== HTTP 客户端 ===== -->
    <!-- OkHttp -->
    <dependency>
        <groupId>com.squareup.okhttp3</groupId>
        <artifactId>okhttp</artifactId>
        <version>4.12.0</version>
    </dependency>
    
    <!-- Apache HttpClient -->
    <dependency>
        <groupId>org.apache.httpcomponents.client5</groupId>
        <artifactId>httpclient5</artifactId>
        <version>5.3</version>
    </dependency>
    
    <!-- ===== 文件存储 ===== -->
    <!-- OSS (阿里云) -->
    <dependency>
        <groupId>com.aliyun.oss</groupId>
        <artifactId>aliyun-sdk-oss</artifactId>
        <version>3.17.2</version>
    </dependency>
    
    <!-- MinIO (私有化) -->
    <dependency>
        <groupId>io.minio</groupId>
        <artifactId>minio</artifactId>
        <version>8.5.7</version>
    </dependency>
    
    <!-- AWS S3 -->
    <dependency>
        <groupId>software.amazon.awssdk</groupId>
        <artifactId>s3</artifactId>
        <version>2.21.23</version>
    </dependency>
    
    <!-- ===== 消息队列 ===== -->
    <!-- RabbitMQ -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-amqp</artifactId>
    </dependency>
    
    <!-- Kafka (可选) -->
    <dependency>
        <groupId>org.springframework.kafka</groupId>
        <artifactId>spring-kafka</artifactId>
    </dependency>
    
    <!-- ===== 搜索引擎 ===== -->
    <!-- Elasticsearch -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-elasticsearch</artifactId>
    </dependency>
    
    <!-- ===== 邮件发送 ===== -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-mail</artifactId>
    </dependency>
    
    <!-- ===== 短信服务 ===== -->
    <!-- 阿里云短信 -->
    <dependency>
        <groupId>com.aliyun</groupId>
        <artifactId>dysmsapi20170525</artifactId>
        <version>2.0.24</version>
    </dependency>
    
    <!-- ===== 定时任务 ===== -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-quartz</artifactId>
    </dependency>
    
    <!-- ===== WebSocket ===== -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-websocket</artifactId>
    </dependency>
    
    <!-- ===== 监控 ===== -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-actuator</artifactId>
    </dependency>
    
    <!-- Micrometer (指标) -->
    <dependency>
        <groupId>io.micrometer</groupId>
        <artifactId>micrometer-registry-prometheus</artifactId>
    </dependency>
    
    <!-- ===== 日志 ===== -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-logging</artifactId>
    </dependency>
    
    <!-- ===== 限流 ===== -->
    <!-- Resilience4j -->
    <dependency>
        <groupId>io.github.resilience4j</groupId>
        <artifactId>resilience4j-spring-boot3</artifactId>
        <version>2.1.0</version>
    </dependency>
    
    <!-- Bucket4j (令牌桶) -->
    <dependency>
        <groupId>com.github.vladimir-bukhtoyarov</groupId>
        <artifactId>bucket4j-core</artifactId>
        <version>8.7.0</version>
    </dependency>
    
    <!-- ===== 数据库迁移 ===== -->
    <!-- Flyway -->
    <dependency>
        <groupId>org.flywaydb</groupId>
        <artifactId>flyway-core</artifactId>
    </dependency>
    
    <dependency>
        <groupId>org.flywaydb</groupId>
        <artifactId>flyway-mysql</artifactId>
    </dependency>
    
    <!-- Liquibase (可选) -->
    <dependency>
        <groupId>org.liquibase</groupId>
        <artifactId>liquibase-core</artifactId>
    </dependency>
    
    <!-- ===== 测试 ===== -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-test</artifactId>
        <scope>test</scope>
    </dependency>
    
    <dependency>
        <groupId>org.springframework.security</groupId>
        <artifactId>spring-security-test</artifactId>
        <scope>test</scope>
    </dependency>
    
    <!-- H2 (测试数据库) -->
    <dependency>
        <groupId>com.h2database</groupId>
        <artifactId>h2</artifactId>
        <scope>test</scope>
    </dependency>
    
    <!-- Mockito -->
    <dependency>
        <groupId>org.mockito</groupId>
        <artifactId>mockito-core</artifactId>
        <scope>test</scope>
    </dependency>
    
    <!-- ===== 配置处理 ===== -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-configuration-processor</artifactId>
        <optional>true</optional>
    </dependency>
    
    <!-- ===== 热部署 (开发环境) ===== -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-devtools</artifactId>
        <scope>runtime</scope>
        <optional>true</optional>
    </dependency>
</dependencies>
```

### 2. Gradle 配置 (可选)

如果使用 Gradle 而不是 Maven:

```gradle
// build.gradle
plugins {
    id 'java'
    id 'org.springframework.boot' version '3.1.5'
    id 'io.spring.dependency-management' version '1.1.3'
}

group = 'com.goabroad'
version = '1.0.0'
sourceCompatibility = '17'

configurations {
    compileOnly {
        extendsFrom annotationProcessor
    }
}

repositories {
    mavenCentral()
    maven { url 'https://maven.aliyun.com/repository/public/' }
}

dependencies {
    // Spring Boot
    implementation 'org.springframework.boot:spring-boot-starter-web'
    implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
    implementation 'org.springframework.boot:spring-boot-starter-security'
    implementation 'org.springframework.boot:spring-boot-starter-data-redis'
    
    // Database
    runtimeOnly 'com.mysql:mysql-connector-j'
    
    // JWT
    implementation 'io.jsonwebtoken:jjwt-api:0.12.3'
    runtimeOnly 'io.jsonwebtoken:jjwt-impl:0.12.3'
    runtimeOnly 'io.jsonwebtoken:jjwt-jackson:0.12.3'
    
    // Lombok
    compileOnly 'org.projectlombok:lombok'
    annotationProcessor 'org.projectlombok:lombok'
    
    // MapStruct
    implementation 'org.mapstruct:mapstruct:1.5.5.Final'
    annotationProcessor 'org.mapstruct:mapstruct-processor:1.5.5.Final'
    
    // API Documentation
    implementation 'org.springdoc:springdoc-openapi-starter-webmvc-ui:2.2.0'
    
    // Test
    testImplementation 'org.springframework.boot:spring-boot-starter-test'
    testImplementation 'org.springframework.security:spring-security-test'
}

tasks.named('test') {
    useJUnitPlatform()
}
```

### 3. 后端开发工具

```bash
# ===== IDE =====
- IntelliJ IDEA Ultimate (推荐)
- Eclipse + Spring Tool Suite
- VS Code + Java Extension Pack

# ===== 构建工具 =====
- Maven 3.8+
- Gradle 8.0+

# ===== 数据库工具 =====
- DBeaver (通用数据库客户端)
- MySQL Workbench
- Navicat
- DataGrip (JetBrains)

# ===== Redis 工具 =====
- Redis Desktop Manager
- RedisInsight
- Medis (Mac)

# ===== API 测试 =====
- Postman
- Insomnia
- Bruno
- JMeter (压力测试)

# ===== 代码质量 =====
- SonarQube
- CheckStyle
- PMD
- SpotBugs

# ===== 性能分析 =====
- JProfiler
- YourKit
- VisualVM
- Arthas (阿里巴巴)

# ===== 文档工具 =====
- Swagger UI (集成在项目中)
- Apifox / ApiPost (国产工具)

# ===== 版本控制 =====
- Git
- GitLab / GitHub
- SourceTree / GitKraken

# ===== CI/CD =====
- Jenkins
- GitLab CI
- GitHub Actions
- Travis CI

# ===== 容器化 =====
- Docker
- Docker Compose
- Kubernetes
- Helm

# ===== 监控告警 =====
- Prometheus + Grafana
- ELK Stack (Elasticsearch, Logstash, Kibana)
- Skywalking (APM)
- Sentry
- Zipkin (链路追踪)

# ===== 服务治理 =====
- Spring Cloud (微服务)
- Nacos (配置中心)
- Sentinel (流控)
```

---

## 基础设施与中间件

### 1. 数据库

```bash
# ===== 关系型数据库 =====
MySQL 8.0+
PostgreSQL 14+

# ===== 非关系型数据库 =====
Redis 7.0+ (缓存、会话)
MongoDB 6.0+ (日志、非结构化数据)

# ===== 搜索引擎 =====
Elasticsearch 8.0+ (全文搜索)
```

### 2. 消息队列

```bash
RabbitMQ 3.12+ (异步任务)
Kafka 3.5+ (日志、数据流，可选)
```

### 3. 对象存储

```bash
# 云存储
阿里云 OSS
腾讯云 COS
AWS S3

# 私有化
MinIO
```

### 4. CDN

```bash
阿里云 CDN
腾讯云 CDN
Cloudflare
```

### 5. 服务器

```bash
# Web服务器
Nginx 1.24+
Apache 2.4+

# 应用服务器
Tomcat 10+ (如果不用内置)
Undertow (Spring Boot 可选)
```

---

## 开发环境配置

### 前端环境

```bash
# Node.js 版本管理
nvm install 18.18.0
nvm use 18.18.0

# 全局依赖
npm install -g react-native-cli
npm install -g @react-native-community/cli
npm install -g expo-cli  # 如果使用 Expo

# iOS 开发 (macOS)
- Xcode 15+
- CocoaPods
- Watchman

# Android 开发
- Android Studio
- Android SDK (API 31+)
- Java JDK 11+
```

### 后端环境

```bash
# Java
JDK 17+ (推荐 Amazon Corretto 或 OpenJDK)

# 构建工具
Maven 3.8+ 或 Gradle 8.0+

# 数据库
MySQL 8.0+ / PostgreSQL 14+

# Redis
Redis 7.0+

# 消息队列
RabbitMQ 3.12+

# 容器 (可选)
Docker Desktop
Docker Compose
```

---

## 项目结构建议

### 前端目录结构

```
GoAbroad/
├── android/               # Android 原生代码
├── ios/                   # iOS 原生代码
├── src/
│   ├── api/              # API 请求
│   ├── assets/           # 静态资源
│   │   ├── images/
│   │   ├── fonts/
│   │   └── icons/
│   ├── components/       # 公共组件
│   │   ├── Button/
│   │   ├── Input/
│   │   └── Card/
│   ├── screens/          # 页面
│   │   ├── Home/
│   │   ├── Country/
│   │   ├── Planning/
│   │   └── Profile/
│   ├── navigation/       # 导航配置
│   ├── store/            # Redux store
│   │   ├── slices/
│   │   └── index.ts
│   ├── hooks/            # 自定义 Hooks
│   ├── utils/            # 工具函数
│   ├── constants/        # 常量
│   ├── types/            # TypeScript 类型
│   ├── services/         # 业务逻辑
│   ├── theme/            # 主题配置
│   └── i18n/             # 国际化
├── __tests__/            # 测试文件
├── .env                  # 环境变量
├── package.json
└── tsconfig.json
```

### 后端目录结构

```
goabroad-backend/
├── src/main/java/com/goabroad/
│   ├── config/           # 配置类
│   │   ├── SecurityConfig.java
│   │   ├── RedisConfig.java
│   │   └── SwaggerConfig.java
│   ├── controller/       # 控制器
│   │   ├── AuthController.java
│   │   ├── CountryController.java
│   │   └── PlanningController.java
│   ├── service/          # 服务层
│   │   ├── impl/
│   │   └── UserService.java
│   ├── repository/       # 数据访问层
│   │   └── UserRepository.java
│   ├── entity/           # 实体类
│   │   └── User.java
│   ├── dto/              # 数据传输对象
│   │   ├── request/
│   │   └── response/
│   ├── mapper/           # 对象映射
│   │   └── UserMapper.java
│   ├── security/         # 安全相关
│   │   ├── JwtTokenProvider.java
│   │   └── JwtAuthenticationFilter.java
│   ├── exception/        # 异常处理
│   │   ├── GlobalExceptionHandler.java
│   │   └── CustomException.java
│   ├── util/             # 工具类
│   │   ├── DateUtil.java
│   │   └── StringUtil.java
│   ├── constant/         # 常量
│   │   └── AppConstants.java
│   └── Application.java  # 启动类
├── src/main/resources/
│   ├── application.yml   # 主配置
│   ├── application-dev.yml
│   ├── application-prod.yml
│   ├── db/migration/     # Flyway 迁移脚本
│   ├── static/
│   └── templates/
└── src/test/             # 测试
```

---

## 推荐的开发流程

### 1. 环境搭建

```bash
# 前端
1. 安装 Node.js 18+
2. 安装 React Native CLI
3. 配置 Android Studio / Xcode
4. 克隆项目并安装依赖: npm install
5. 启动开发服务器: npm start

# 后端
1. 安装 JDK 17+
2. 安装 Maven/Gradle
3. 安装 MySQL/PostgreSQL
4. 安装 Redis
5. 导入项目到 IDE
6. 运行数据库迁移脚本
7. 启动应用: mvn spring-boot:run
```

### 2. 本地开发配置

```yaml
# application-dev.yml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/goabroad?useUnicode=true&characterEncoding=utf8
    username: root
    password: password
  redis:
    host: localhost
    port: 6379
  jpa:
    show-sql: true
    hibernate:
      ddl-auto: update
      
logging:
  level:
    com.goabroad: DEBUG
```

### 3. 代码规范

- **前端**: ESLint + Prettier
- **后端**: CheckStyle + Google Java Style Guide
- **Git提交**: Conventional Commits (feat/fix/docs/style/refactor/test/chore)

---

这份技术栈清单涵盖了 GoAbroad 项目从开发到部署的完整依赖。根据实际需求，可以适当增减某些依赖。建议先实现 MVP (最小可行产品)，然后逐步引入更多功能和优化。