# 🛠️ GoAbroad 后端技术栈与依赖清单

基于您的 `pom.xml` 文件，我为您整理了完整的技术栈清单：

---

## 📦 核心信息

| 项目 | 版本 |
|------|------|
| **项目名称** | GoAbroad API |
| **版本号** | 1.0.0 |
| **Java 版本** | 21 |
| **Spring Boot 版本** | 3.5.6 (parent) / 3.3.5 (property) |
| **编码格式** | UTF-8 |
| **构建工具** | Maven |
| **打包方式** | pom (多模块) |

---

## 🏗️ 项目模块结构

```
goabroad-api (父工程 1.0.0)
├── goabroad-common          # 公共模块
├── goabroad-model           # 数据模型模块
├── goabroad-service         # 业务逻辑模块
└── goabroad-web             # Web入口模块
```

---

## 🔧 核心框架

| 框架 | 版本 | 说明 |
|------|------|------|
| **Spring Boot Parent** | 3.5.6 | Spring Boot 父工程 |
| **Spring Boot** | 3.3.5 | 应用框架（实际运行版本） |
| **Spring Data JPA** | (继承自 Spring Boot) | 数据访问框架 |
| **Spring Web MVC** | (继承自 Spring Boot) | Web 框架 |

---

## 🔐 认证授权

| 依赖 | 版本 | 说明 |
|------|------|------|
| **sa-token-spring-boot3-starter** | 1.44.0 | Sa-Token 权限认证框架（主包） |
| **sa-token-redis-jackson** | 1.44.0 | Sa-Token Redis 集成（使用 Jackson 序列化） |

> **注意**：项目使用 Sa-Token 替代传统 JWT，支持更强大的权限管理功能

---

## 🗄️ 数据持久化

| 依赖 | 版本 | 说明 |
|------|------|------|
| **Spring Data JPA** | (继承自 Spring Boot) | ORM 框架 |
| **Hibernate** | (继承自 Spring Boot) | JPA 实现 |
| **PostgreSQL JDBC Driver** | (需在子模块中引入) | PostgreSQL 驱动 |
| **Spring Data Redis** | (需在子模块中引入) | Redis 数据访问 |

---

## 💾 缓存解决方案

| 依赖 | 版本 | 说明 |
|------|------|------|
| **Caffeine** | 3.2.2 | 本地缓存（内存缓存） |
| **Spring Data Redis** | (继承自 Spring Boot) | Redis 分布式缓存 |

---

## 🔄 对象映射

| 依赖 | 版本 | Scope | 说明 |
|------|------|-------|------|
| **mapstruct** | 1.6.3 | compile | MapStruct 运行时依赖 |
| **mapstruct-processor** | 1.6.3 | provided | MapStruct 注解处理器 |

---

## 📚 API 文档

| 依赖 | 版本 | 说明 |
|------|------|------|
| **springdoc-openapi-starter-webmvc-ui** | 2.8.8 | Springdoc OpenAPI (Swagger UI) |

> 访问地址：`http://localhost:8080/swagger-ui.html`

---

## 🛠️ 工具类库

### 国产工具库
| 依赖 | 版本 | 说明 |
|------|------|------|
| **hutool-all** | 5.8.40 | Hutool 工具类全家桶 |

### Apache 工具库
| 依赖 | 版本 | 说明 |
|------|------|------|
| **commons-lang3** | 3.19.0 | Apache 通用工具类 |
| **commons-io** | 2.18.0 | Apache IO 工具类 |

### Google 工具库
| 依赖 | 版本 | 说明 |
|------|------|------|
| **guava** | 33.5.0-jre | Google Guava 工具库 |

---

## 🌐 HTTP 客户端

| 依赖 | 版本 | 说明 |
|------|------|------|
| **okhttp** | 5.2.1 | OkHttp HTTP 客户端 |

---

## 📦 对象存储

| 依赖 | 版本 | 说明 |
|------|------|------|
| **minio** | 8.6.0 | MinIO 对象存储客户端 |

---

## 📱 第三方服务

| 依赖 | 版本 | 说明 |
|------|------|------|
| **dysmsapi20170525** | 2.0.24 | 阿里云短信服务 SDK |

---

## 🛡️ 容错与限流

| 依赖 | 版本 | 说明 |
|------|------|------|
| **resilience4j-spring-boot3** | 2.2.0 | Resilience4j 容错库（熔断、限流、重试） |

---

## ⚙️ 开发工具

| 依赖 | 版本 | Scope | 说明 |
|------|------|-------|------|
| **lombok** | 1.18.32 | provided | 简化 Java 代码（getter/setter/builder 等） |

---

## 🔌 Maven 插件

| 插件 | 版本 | 说明 |
|------|------|------|
| **spring-boot-maven-plugin** | (继承自 Spring Boot Parent) | Spring Boot 打包插件 |
| **maven-compiler-plugin** | 3.14.0 | Maven 编译插件 |

### 注解处理器配置
```xml
<annotationProcessorPaths>
  - Lombok: 1.18.32
  - MapStruct Processor: 1.6.3
</annotationProcessorPaths>
```

---

## 📊 完整依赖树

```
goabroad-api:1.0.0
│
├─ 核心框架
│  ├─ Spring Boot Parent: 3.5.6
│  └─ Spring Boot: 3.3.5
│
├─ 认证授权
│  ├─ Sa-Token Spring Boot3 Starter: 1.44.0
│  └─ Sa-Token Redis Jackson: 1.44.0
│
├─ 对象映射
│  ├─ MapStruct: 1.6.3
│  └─ MapStruct Processor: 1.6.3
│
├─ API 文档
│  └─ Springdoc OpenAPI: 2.8.8
│
├─ 工具类库
│  ├─ Hutool: 5.8.40
│  ├─ Commons Lang3: 3.19.0
│  ├─ Commons IO: 2.18.0
│  └─ Guava: 33.5.0-jre
│
├─ HTTP 客户端
│  └─ OkHttp: 5.2.1
│
├─ 缓存
│  └─ Caffeine: 3.2.2
│
├─ 对象存储
│  └─ MinIO: 8.6.0
│
├─ 第三方服务
│  └─ 阿里云短信: 2.0.24
│
├─ 容错限流
│  └─ Resilience4j: 2.2.0
│
└─ 开发工具
   └─ Lombok: 1.18.32
```

---

## 🎯 技术亮点

1. ✅ **最新技术栈**：Java 21 + Spring Boot 3.5.6
2. ✅ **Sa-Token 认证**：比传统 JWT 更强大的权限框架
3. ✅ **MapStruct 映射**：编译期对象映射，性能优于反射
4. ✅ **多级缓存**：Caffeine（本地）+ Redis（分布式）
5. ✅ **容错机制**：Resilience4j 提供熔断、限流、重试
6. ✅ **对象存储**：MinIO 自建存储服务
7. ✅ **国产优先**：Hutool、Sa-Token 等优秀国产框架
8. ✅ **API 文档**：Springdoc OpenAPI 自动生成文档

---

## 📝 注意事项

1. **Spring Boot 版本冲突**：
    - Parent 定义为 `3.5.6`
    - Properties 定义为 `3.3.5`
    - 建议统一为一个版本，推荐使用 `3.3.5`（更稳定）

2. **子模块依赖**：
    - 数据库驱动（PostgreSQL）需要在具体子模块中引入
    - Redis 相关依赖需要在子模块中引入

3. **注解处理器顺序**：
    - Lombok 必须在 MapStruct 之前配置
    - 当前配置正确 ✅

---

**文档生成时间**：2024年10月25日  
**基于文件**：`pom.xml`  
**项目版本**：1.0.0