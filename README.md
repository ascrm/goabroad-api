# 🌍 GoAbroad 出国助手后端API

[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.1.5-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Java](https://img.shields.io/badge/Java-17-orange.svg)](https://www.oracle.com/java/)
[![MySQL](https://img.shields.io/badge/MySQL-8.0+-blue.svg)](https://www.mysql.com/)
[![Redis](https://img.shields.io/badge/Redis-7.0+-red.svg)](https://redis.io/)
[![License](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)

> 一个为计划出国留学、工作、移民的用户提供全方位信息查询和规划管理的后端API系统。

## 📋 项目简介

GoAbroad 出国助手是一个**毕业设计项目**，采用现代化的技术栈构建，提供完整的出国规划解决方案。

### 核心功能

- 🔐 **用户认证与授权** - JWT无状态认证、角色权限管理
- 🌏 **国家信息查询** - 留学、工作、签证信息一站式查询
- 📋 **出国规划管理** - 材料清单、时间线追踪
- 💬 **社区论坛** - 经验分享、问答互动
- 🛠️ **实用工具** - 费用计算器、GPA转换器、签证预约监控
- 🔔 **智能提醒** - 重要时间节点通知

## 🏗️ 项目架构

### 多模块设计

```
goabroad-api (父项目)
├── goabroad-common       # 公共模块：工具类、常量、异常、统一响应
├── goabroad-model        # 数据模型：实体类、DTO、VO、Mapper
├── goabroad-security     # 安全模块：JWT、Spring Security
├── goabroad-repository   # 数据访问：Repository、Redis、Elasticsearch
├── goabroad-service      # 业务逻辑：Service接口与实现
└── goabroad-web          # Web接口：Controller、启动类、配置
```

### 技术选型

| 技术 | 版本 | 说明 |
|------|------|------|
| Spring Boot | 3.1.5 | 核心框架 |
| Java | 17 | 开发语言 |
| MySQL | 8.0+ | 关系型数据库 |
| Redis | 7.0+ | 缓存与会话 |
| JWT | 0.12.3 | 身份认证 |
| MapStruct | 1.5.5 | 对象映射 |
| Springdoc | 2.2.0 | API文档 |
| Lombok | - | 代码简化 |

## 🚀 快速开始

### 环境要求

- JDK 17+
- Maven 3.8+
- MySQL 8.0+
- Redis 7.0+
- IntelliJ IDEA / Eclipse

### 1. 克隆项目

```bash
git clone https://github.com/yourusername/goabroad-api.git
cd goabroad-api
```

### 2. 创建数据库

```bash
mysql -u root -p < docs/database/create_database.sql
```

或手动执行：
```sql
CREATE DATABASE goabroad CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

### 3. 配置文件

修改 `goabroad-web/src/main/resources/application-dev.yml`：

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/goabroad
    username: root
    password: your_password
  
  redis:
    host: localhost
    port: 6379
    password: 

jwt:
  secret: your-secret-key-at-least-256-bits-long
```

### 4. 编译项目

```bash
mvn clean compile
```

### 5. 运行项目

```bash
cd goabroad-web
mvn spring-boot:run
```

或在 IDE 中运行 `Application.java`

### 6. 访问应用

- **Swagger文档**: http://localhost:8080/swagger-ui.html
- **API接口**: http://localhost:8080/api/v1/
- **健康检查**: http://localhost:8080/actuator/health

## 📚 文档

- [后端架构设计文档](docs/GoAbroad%20后端架构设计文档.md)
- [产品设计文档](docs/GoAbroad产品设计文档.md)
- [技术栈与依赖清单](docs/GoAbroad技术栈与依赖清单.md)
- [项目开发提示词](docs/GoAbroad%20项目开发提示词.md)
- [数据库文档](docs/database/GoAbroad%20数据库设计文档.md)
- [阶段一完成说明](docs/阶段一完成说明.md)

## 📦 模块说明

### goabroad-common
公共基础模块，提供通用工具类、常量定义、自定义异常和统一响应格式。

**主要内容**：
- `Result<T>` - 统一响应格式
- `ResponseCode` - 响应码枚举
- `BusinessException` - 业务异常
- 工具类：DateUtil、StringUtil、JsonUtil等

### goabroad-model
数据模型模块，定义所有实体类、DTO和VO。

**主要内容**：
- Entity：JPA实体类
- DTO：数据传输对象（Request/Response）
- VO：视图对象
- Mapper：MapStruct映射接口

### goabroad-security
安全认证模块，处理JWT认证和Spring Security配置。

**主要内容**：
- JwtTokenProvider - JWT工具类
- JwtAuthenticationFilter - JWT过滤器
- SecurityConfig - Security配置
- UserDetailsService实现

### goabroad-repository
数据访问模块，提供数据库操作接口。

**主要内容**：
- JPA Repository接口
- Redis缓存操作
- Elasticsearch搜索（可选）

### goabroad-service
业务逻辑模块，实现核心业务功能。

**主要内容**：
- AuthService - 认证服务
- UserService - 用户服务
- CountryService - 国家信息服务
- PlanService - 规划服务
- CommunityService - 社区服务

### goabroad-web
Web接口模块，提供RESTful API。

**主要内容**：
- Controller层
- 全局异常处理
- 配置类
- 启动类

## 🔧 开发指南

### API规范

所有API遵循RESTful设计：

```
GET    /api/v1/users           # 获取用户列表
GET    /api/v1/users/{id}      # 获取单个用户
POST   /api/v1/users           # 创建用户
PUT    /api/v1/users/{id}      # 更新用户
DELETE /api/v1/users/{id}      # 删除用户
```

### 统一响应格式

```json
{
  "code": 200,
  "message": "操作成功",
  "data": {
    "id": 1,
    "username": "zhangsan"
  },
  "timestamp": 1697712345678
}
```

### 认证方式

使用JWT Bearer Token：

```http
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
```

## 🧪 测试

```bash
# 运行所有测试
mvn test

# 运行特定模块测试
cd goabroad-service
mvn test

# 跳过测试编译
mvn clean package -DskipTests
```

## 📈 开发进度

- [x] **阶段一**：环境准备与父POM配置 ✅
- [ ] **阶段二**：goabroad-common 模块（公共基础）
- [ ] **阶段三**：goabroad-model 模块（数据模型）
- [ ] **阶段四**：goabroad-security 模块（安全认证）
- [ ] **阶段五**：goabroad-repository 模块（数据访问）
- [ ] **阶段六**：goabroad-service 模块（业务逻辑）
- [ ] **阶段七**：goabroad-web 模块（Web接口）
- [ ] **阶段八**：测试与优化
- [ ] **阶段九**：文档完善与部署准备
- [ ] **阶段十**：毕业设计特殊要求

**当前进度：20%**

## 🤝 贡献指南

本项目是毕业设计项目，暂不接受外部贡献。如有建议，欢迎提Issue。

## 📄 许可证

本项目采用 MIT 许可证。详见 [LICENSE](LICENSE) 文件。

## 👨‍💻 作者

- **姓名**：[您的姓名]
- **学校**：[您的学校]
- **专业**：[您的专业]
- **年级**：[您的年级]
- **邮箱**：[您的邮箱]

## 📞 联系方式

如有问题或建议，请通过以下方式联系：

- Email: your.email@example.com
- GitHub Issues: [提交Issue](https://github.com/yourusername/goabroad-api/issues)

## 🙏 致谢

感谢以下开源项目：

- [Spring Boot](https://spring.io/projects/spring-boot)
- [MyBatis](https://mybatis.org/)
- [MapStruct](https://mapstruct.org/)
- [Lombok](https://projectlombok.org/)
- [Springdoc OpenAPI](https://springdoc.org/)

---

⭐ 如果这个项目对你有帮助，请给个Star吧！

