# 📊 GoAbroad 数据库设计文档 (MySQL 8.0+)

---

**项目名称**: GoAbroad 出国助手  
**数据库类型**: MySQL 8.0+  
**存储引擎**: InnoDB  
**字符集**: utf8mb4  
**排序规则**: utf8mb4_unicode_ci  
**版本**: v1.0  
**最后更新**: 2025-10-19

---

## 📑 目录

1. [数据库概述](#1-数据库概述)
2. [架构设计](#2-架构设计)
3. [数据表设计](#3-数据表设计)
4. [关系图](#4-关系图)
5. [索引策略](#5-索引策略)
6. [数据字典](#6-数据字典)
7. [SQL迁移脚本](#7-sql迁移脚本)
8. [性能优化建议](#8-性能优化建议)

---

## 1. 数据库概述

### 1.1 技术栈

- **主数据库**: MySQL 8.0.x
- **存储引擎**: InnoDB（支持事务、外键）
- **字符集**: utf8mb4（支持 emoji 和多语言）
- **缓存层**: Redis 7.x
- **文件存储**: 阿里云 OSS / 腾讯云 COS
- **ORM**: Prisma / TypeORM

### 1.2 命名规范

- **数据库名**: `goabroad_db`
- **表名**: 小写+下划线，复数形式（如 `users`, `posts`）
- **列名**: 小写+下划线（如 `user_id`, `created_at`）
- **主键**: 统一使用 `id` (BIGINT UNSIGNED AUTO_INCREMENT 或 CHAR(36) UUID)
- **外键**: `{关联表单数}_id` (如 `user_id`, `plan_id`)
- **索引**: `idx_{表名}_{列名}` (如 `idx_users_email`)
- **唯一索引**: `uk_{表名}_{列名}` (如 `uk_users_username`)

### 1.3 数据类型规范

```
ID主键:        BIGINT UNSIGNED AUTO_INCREMENT  或  CHAR(36) [UUID]
外键:          BIGINT UNSIGNED  或  CHAR(36)
用户名:        VARCHAR(50)
邮箱:          VARCHAR(100)
手机号:        VARCHAR(20)
密码哈希:      VARCHAR(255)
短文本:        VARCHAR(255)
长文本:        TEXT / MEDIUMTEXT
JSON数据:      JSON
布尔值:        TINYINT(1)  [0=false, 1=true]
日期:          DATE
时间戳:        DATETIME(3)  [支持毫秒]
金额:          DECIMAL(10,2)
百分比:        TINYINT UNSIGNED  [0-100]
枚举:          ENUM / VARCHAR
```

### 1.4 设计原则

- ✅ **规范化**: 遵循第三范式，减少冗余
- ✅ **适度反规范化**: 热点数据（计数）冗余，提升查询性能
- ✅ **软删除**: 重要数据使用 `deleted_at` 而非物理删除
- ✅ **时间戳**: 所有表包含 `created_at`, `updated_at`
- ✅ **索引优化**: 高频查询字段建立索引
- ✅ **外键约束**: 开发环境使用，生产环境可选

---

## 2. 架构设计

### 2.1 模块划分

```
GoAbroad MySQL Database
│
├── 01_用户模块 (User Module)
│   ├── users                    用户主表
│   ├── user_profiles            用户详细资料（扩展）
│   ├── user_preferences         用户偏好设置
│   ├── user_oauth_bindings      第三方登录绑定
│   └── user_sessions            会话管理（可选）
│
├── 02_国家信息模块 (Country Module)
│   ├── countries                国家信息主表
│   ├── country_policies         政策更新记录
│   └── user_country_favorites   用户收藏的国家
│
├── 03_规划模块 (Planning Module)
│   ├── plans                    规划主表
│   ├── plan_stages              规划阶段
│   ├── plan_tasks               阶段任务
│   ├── plan_milestones          里程碑
│   ├── material_checklists      材料清单
│   └── material_files           材料文件记录
│
├── 04_社区模块 (Community Module)
│   ├── posts                    帖子主表
│   ├── comments                 评论
│   ├── post_likes               点赞记录
│   ├── post_collections         收藏记录
│   ├── tags                     标签字典表
│   ├── post_tags                帖子-标签关联表
│   └── user_follows             用户关注关系
│
├── 05_工具模块 (Tools Module)
│   ├── cost_calculations        费用计算记录
│   ├── gpa_conversions          GPA转换记录
│   └── visa_slots               签证预约信息
│
├── 06_通知模块 (Notification Module)
│   ├── reminders                用户提醒
│   └── notifications            系统通知
│
└── 07_系统模块 (System Module)
    ├── admin_users              管理员账号
    ├── audit_logs               操作审计日志
    └── system_configs           系统配置
```

### 2.2 ER 关系概览

```
User 1:N Plan (一个用户多个规划)
User 1:N Post (一个用户多篇帖子)
User 1:N Comment (一个用户多条评论)
User N:M User (关注关系)
User N:M Post (点赞、收藏)

Country 1:N Plan (一个国家多个规划)

Plan 1:N Stage (一个规划多个阶段)
Plan 1:N Milestone (一个规划多个里程碑)
Plan 1:N MaterialChecklist (一个规划多份材料)

Stage 1:N Task (一个阶段多个任务)

MaterialChecklist 1:N MaterialFile (一份材料多个文件)

Post 1:N Comment (一篇帖子多条评论)
Post N:M Tag (多对多)

Comment 1:N Comment (评论嵌套，自关联)
```

---

## 3. 数据表设计

### 3.1 用户模块

#### 3.1.1 users (用户主表)

**功能**: 存储用户核心信息

```sql
CREATE TABLE `users` (
  `id` BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY COMMENT '用户ID',
  `username` VARCHAR(50) NOT NULL COMMENT '用户名',
  `email` VARCHAR(100) NOT NULL COMMENT '邮箱',
  `phone` VARCHAR(20) DEFAULT NULL COMMENT '手机号',
  `password_hash` VARCHAR(255) NOT NULL COMMENT '密码哈希（bcrypt）',
  
  -- 基本信息
  `nickname` VARCHAR(50) DEFAULT NULL COMMENT '昵称',
  `avatar_url` VARCHAR(500) DEFAULT NULL COMMENT '头像URL',
  `bio` VARCHAR(500) DEFAULT NULL COMMENT '个人简介',
  `gender` ENUM('male', 'female', 'other', 'prefer_not_to_say') DEFAULT NULL COMMENT '性别',
  
  -- 社区等级
  `level` TINYINT UNSIGNED DEFAULT 1 COMMENT '用户等级 1-10',
  `points` INT UNSIGNED DEFAULT 0 COMMENT '积分',
  `exp` INT UNSIGNED DEFAULT 0 COMMENT '经验值',
  
  -- 统计数据（冗余，提升查询性能）
  `post_count` INT UNSIGNED DEFAULT 0 COMMENT '发帖数',
  `follower_count` INT UNSIGNED DEFAULT 0 COMMENT '粉丝数',
  `following_count` INT UNSIGNED DEFAULT 0 COMMENT '关注数',
  
  -- 账号状态
  `status` ENUM('active', 'inactive', 'banned', 'deleted') DEFAULT 'active' COMMENT '账号状态',
  `email_verified` TINYINT(1) DEFAULT 0 COMMENT '邮箱是否验证',
  `phone_verified` TINYINT(1) DEFAULT 0 COMMENT '手机是否验证',
  `is_vip` TINYINT(1) DEFAULT 0 COMMENT '是否会员',
  `vip_expire_at` DATETIME DEFAULT NULL COMMENT '会员到期时间',
  
  -- 最后活跃
  `last_login_at` DATETIME DEFAULT NULL COMMENT '最后登录时间',
  `last_login_ip` VARCHAR(45) DEFAULT NULL COMMENT '最后登录IP',
  
  -- 时间戳
  `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted_at` DATETIME DEFAULT NULL COMMENT '软删除时间',
  
  -- 索引
  UNIQUE KEY `uk_username` (`username`),
  UNIQUE KEY `uk_email` (`email`),
  UNIQUE KEY `uk_phone` (`phone`),
  KEY `idx_status` (`status`),
  KEY `idx_created_at` (`created_at`),
  KEY `idx_level_points` (`level`, `points`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户主表';
```

#### 3.1.2 user_profiles (用户详细资料)

**功能**: 扩展用户信息（垂直拆分，降低主表体积）

```sql
CREATE TABLE `user_profiles` (
  `id` BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  `user_id` BIGINT UNSIGNED NOT NULL COMMENT '用户ID',
  
  -- 教育背景
  `education_level` ENUM('high_school', 'associate', 'bachelor', 'master', 'phd', 'other') DEFAULT NULL COMMENT '学历',
  `major` VARCHAR(100) DEFAULT NULL COMMENT '专业',
  `school` VARCHAR(200) DEFAULT NULL COMMENT '学校',
  `graduation_year` YEAR DEFAULT NULL COMMENT '毕业年份',
  `gpa` DECIMAL(4,2) DEFAULT NULL COMMENT 'GPA (如 3.75)',
  
  -- 语言成绩
  `toefl_score` SMALLINT UNSIGNED DEFAULT NULL COMMENT '托福分数',
  `ielts_score` DECIMAL(3,1) DEFAULT NULL COMMENT '雅思分数 (如 7.5)',
  `gre_score` SMALLINT UNSIGNED DEFAULT NULL COMMENT 'GRE分数',
  `gmat_score` SMALLINT UNSIGNED DEFAULT NULL COMMENT 'GMAT分数',
  
  -- 工作经历
  `work_years` TINYINT UNSIGNED DEFAULT 0 COMMENT '工作年限',
  `current_company` VARCHAR(200) DEFAULT NULL COMMENT '当前公司',
  `current_position` VARCHAR(100) DEFAULT NULL COMMENT '当前职位',
  
  -- 其他信息
  `location` VARCHAR(100) DEFAULT NULL COMMENT '所在城市',
  `birth_year` YEAR DEFAULT NULL COMMENT '出生年份',
  
  `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
  `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  
  UNIQUE KEY `uk_user_id` (`user_id`),
  FOREIGN KEY (`user_id`) REFERENCES `users`(`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户详细资料';
```

#### 3.1.3 user_preferences (用户偏好设置)

**功能**: 存储用户的兴趣标签和偏好

```sql
CREATE TABLE `user_preferences` (
  `id` BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  `user_id` BIGINT UNSIGNED NOT NULL COMMENT '用户ID',
  
  -- 目标国家（JSON数组）
  `target_countries` JSON DEFAULT NULL COMMENT '目标国家代码列表 ["US","UK","CA"]',
  
  -- 出国类型
  `target_type` ENUM('study', 'work', 'immigration', 'travel', 'undecided') DEFAULT NULL COMMENT '出国目的',
  `target_subtype` VARCHAR(50) DEFAULT NULL COMMENT '细分类型（bachelor/master/phd等）',
  
  -- 时间规划
  `time_frame` VARCHAR(50) DEFAULT NULL COMMENT '计划时间（within_3_months/within_6_months等）',
  `target_departure_date` DATE DEFAULT NULL COMMENT '计划出发日期',
  
  -- 当前阶段
  `current_stage` VARCHAR(50) DEFAULT NULL COMMENT '当前所处阶段',
  
  -- 偏好设置
  `notification_enabled` TINYINT(1) DEFAULT 1 COMMENT '是否接收通知',
  `email_notification` TINYINT(1) DEFAULT 1 COMMENT '是否接收邮件通知',
  `push_notification` TINYINT(1) DEFAULT 1 COMMENT '是否接收推送通知',
  
  `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
  `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  
  UNIQUE KEY `uk_user_id` (`user_id`),
  FOREIGN KEY (`user_id`) REFERENCES `users`(`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户偏好设置';
```

#### 3.1.4 user_oauth_bindings (第三方登录绑定)

**功能**: 管理微信、QQ、Apple等第三方登录

```sql
CREATE TABLE `user_oauth_bindings` (
  `id` BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  `user_id` BIGINT UNSIGNED NOT NULL COMMENT '用户ID',
  `provider` ENUM('wechat', 'qq', 'apple', 'google', 'github') NOT NULL COMMENT '第三方平台',
  `provider_user_id` VARCHAR(100) NOT NULL COMMENT '第三方用户ID（openid/unionid）',
  `provider_username` VARCHAR(100) DEFAULT NULL COMMENT '第三方用户名',
  `provider_avatar` VARCHAR(500) DEFAULT NULL COMMENT '第三方头像',
  `access_token` TEXT DEFAULT NULL COMMENT '访问令牌（加密存储）',
  `refresh_token` TEXT DEFAULT NULL COMMENT '刷新令牌',
  `expires_at` DATETIME DEFAULT NULL COMMENT '令牌过期时间',
  
  `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
  `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  
  UNIQUE KEY `uk_provider_user` (`provider`, `provider_user_id`),
  KEY `idx_user_id` (`user_id`),
  FOREIGN KEY (`user_id`) REFERENCES `users`(`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='第三方登录绑定';
```

---

### 3.2 国家信息模块

#### 3.2.1 countries (国家信息主表)

**功能**: 存储各国基本信息、留学、工作、移民等详细内容

```sql
CREATE TABLE `countries` (
  `id` BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  `code` CHAR(2) NOT NULL COMMENT '国家代码 ISO 3166-1 alpha-2 (US/UK/CA)',
  `name_zh` VARCHAR(50) NOT NULL COMMENT '中文名称',
  `name_en` VARCHAR(50) NOT NULL COMMENT '英文名称',
  `flag_emoji` VARCHAR(10) DEFAULT NULL COMMENT '国旗emoji 🇺🇸',
  
  -- 概览信息（JSON）
  `overview` JSON DEFAULT NULL COMMENT '国家概览：{"description":"...","advantages":[],"disadvantages":[]}',
  
  -- 留学信息（JSON）
  `study_info` JSON DEFAULT NULL COMMENT '留学信息：{"education_system":"...","application_process":[],"requirements":{}}',
  
  -- 工作信息（JSON）
  `work_info` JSON DEFAULT NULL COMMENT '工作信息：{"visa_types":[],"job_market":"...","salary_range":{}}',
  
  -- 移民信息（JSON）
  `immigration_info` JSON DEFAULT NULL COMMENT '移民信息：{"types":[],"requirements":{},"timeline":"..."}',
  
  -- 生活信息（JSON）
  `living_info` JSON DEFAULT NULL COMMENT '生活信息：{"climate":"...","cost_of_living":{},"safety_index":8}',
  
  -- 费用信息
  `avg_tuition_min` DECIMAL(10,2) DEFAULT NULL COMMENT '学费最低（年，单位：人民币）',
  `avg_tuition_max` DECIMAL(10,2) DEFAULT NULL COMMENT '学费最高（年）',
  `avg_living_cost` DECIMAL(10,2) DEFAULT NULL COMMENT '平均生活费（年）',
  
  -- 难度评级
  `difficulty_rating` TINYINT UNSIGNED DEFAULT 5 COMMENT '申请难度 1-10',
  `popularity_score` INT UNSIGNED DEFAULT 0 COMMENT '热度分数',
  
  -- 状态
  `is_active` TINYINT(1) DEFAULT 1 COMMENT '是否启用',
  `is_featured` TINYINT(1) DEFAULT 0 COMMENT '是否推荐',
  `sort_order` INT DEFAULT 0 COMMENT '排序权重',
  
  -- 统计（冗余）
  `plan_count` INT UNSIGNED DEFAULT 0 COMMENT '规划数量',
  `view_count` INT UNSIGNED DEFAULT 0 COMMENT '浏览次数',
  
  `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
  `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  
  UNIQUE KEY `uk_code` (`code`),
  KEY `idx_is_active` (`is_active`),
  KEY `idx_sort_popularity` (`sort_order`, `popularity_score`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='国家信息表';
```

#### 3.2.2 country_policies (国家政策更新)

**功能**: 记录签证、移民等政策的动态更新

```sql
CREATE TABLE `country_policies` (
  `id` BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  `country_code` CHAR(2) NOT NULL COMMENT '国家代码',
  `policy_type` ENUM('visa', 'study', 'work', 'immigration', 'other') NOT NULL COMMENT '政策类型',
  `title` VARCHAR(200) NOT NULL COMMENT '政策标题',
  `content` TEXT NOT NULL COMMENT '政策内容（Markdown）',
  `effective_date` DATE DEFAULT NULL COMMENT '生效日期',
  `source_url` VARCHAR(500) DEFAULT NULL COMMENT '来源链接',
  
  `is_important` TINYINT(1) DEFAULT 0 COMMENT '是否重要更新',
  `status` ENUM('active', 'expired', 'draft') DEFAULT 'active' COMMENT '状态',
  
  `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
  `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  
  KEY `idx_country_type` (`country_code`, `policy_type`),
  KEY `idx_effective_date` (`effective_date`),
  FOREIGN KEY (`country_code`) REFERENCES `countries`(`code`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='国家政策更新';
```

#### 3.2.3 user_country_favorites (用户收藏的国家)

**功能**: 用户收藏/关注的国家

```sql
CREATE TABLE `user_country_favorites` (
  `id` BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  `user_id` BIGINT UNSIGNED NOT NULL,
  `country_code` CHAR(2) NOT NULL,
  `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
  
  UNIQUE KEY `uk_user_country` (`user_id`, `country_code`),
  KEY `idx_country_code` (`country_code`),
  FOREIGN KEY (`user_id`) REFERENCES `users`(`id`) ON DELETE CASCADE,
  FOREIGN KEY (`country_code`) REFERENCES `countries`(`code`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户收藏国家';
```

---

### 3.3 规划模块

#### 3.3.1 plans (规划主表)

**功能**: 用户的留学/工作/移民规划

```sql
CREATE TABLE `plans` (
  `id` BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  `user_id` BIGINT UNSIGNED NOT NULL COMMENT '用户ID',
  `country_code` CHAR(2) NOT NULL COMMENT '目标国家',
  
  -- 规划类型
  `plan_type` ENUM('study', 'work', 'immigration') NOT NULL COMMENT '规划类型',
  `sub_type` VARCHAR(50) DEFAULT NULL COMMENT '细分类型（bachelor/master/phd/work_visa等）',
  
  -- 目标
  `target_date` DATE DEFAULT NULL COMMENT '计划出发日期',
  `title` VARCHAR(200) DEFAULT NULL COMMENT '规划标题（如"2026年美国CS硕士申请"）',
  
  -- 当前状态（JSON）
  `current_status` JSON DEFAULT NULL COMMENT '用户当前状态：{"education":"bachelor","gpa":3.5,"toefl":100}',
  
  -- 进度
  `progress` TINYINT UNSIGNED DEFAULT 0 COMMENT '整体进度 0-100',
  `current_stage` VARCHAR(50) DEFAULT NULL COMMENT '当前所处阶段名称',
  
  -- 统计
  `total_stages` TINYINT UNSIGNED DEFAULT 0 COMMENT '总阶段数',
  `completed_stages` TINYINT UNSIGNED DEFAULT 0 COMMENT '已完成阶段数',
  `total_tasks` SMALLINT UNSIGNED DEFAULT 0 COMMENT '总任务数',
  `completed_tasks` SMALLINT UNSIGNED DEFAULT 0 COMMENT '已完成任务数',
  
  -- 状态
  `status` ENUM('active', 'completed', 'paused', 'archived') DEFAULT 'active' COMMENT '规划状态',
  
  `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
  `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `completed_at` DATETIME DEFAULT NULL COMMENT '完成时间',
  
  KEY `idx_user_id` (`user_id`),
  KEY `idx_country_code` (`country_code`),
  KEY `idx_status` (`status`),
  KEY `idx_plan_type` (`plan_type`),
  FOREIGN KEY (`user_id`) REFERENCES `users`(`id`) ON DELETE CASCADE,
  FOREIGN KEY (`country_code`) REFERENCES `countries`(`code`) ON DELETE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户规划表';
```

#### 3.3.2 plan_stages (规划阶段)

**功能**: 规划的各个阶段（如语言考试、材料准备、申请提交等）

```sql
CREATE TABLE `plan_stages` (
  `id` BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  `plan_id` BIGINT UNSIGNED NOT NULL COMMENT '规划ID',
  
  `name` VARCHAR(100) NOT NULL COMMENT '阶段名称（如"语言考试阶段"）',
  `description` TEXT DEFAULT NULL COMMENT '阶段描述',
  
  -- 时间范围
  `start_date` DATE DEFAULT NULL COMMENT '开始日期',
  `end_date` DATE DEFAULT NULL COMMENT '结束日期',
  `duration_days` SMALLINT UNSIGNED DEFAULT NULL COMMENT '预计天数',
  
  -- 状态
  `status` ENUM('not_started', 'in_progress', 'completed', 'overdue') DEFAULT 'not_started' COMMENT '阶段状态',
  `progress` TINYINT UNSIGNED DEFAULT 0 COMMENT '阶段进度 0-100',
  
  -- 统计
  `total_tasks` SMALLINT UNSIGNED DEFAULT 0 COMMENT '任务总数',
  `completed_tasks` SMALLINT UNSIGNED DEFAULT 0 COMMENT '已完成任务数',
  
  -- 排序
  `sort_order` TINYINT UNSIGNED NOT NULL COMMENT '排序顺序',
  
  `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
  `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `completed_at` DATETIME DEFAULT NULL COMMENT '完成时间',
  
  KEY `idx_plan_id` (`plan_id`),
  KEY `idx_status` (`status`),
  KEY `idx_sort_order` (`sort_order`),
  FOREIGN KEY (`plan_id`) REFERENCES `plans`(`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='规划阶段表';
```

#### 3.3.3 plan_tasks (阶段任务)

**功能**: 每个阶段下的具体任务

```sql
CREATE TABLE `plan_tasks` (
  `id` BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  `stage_id` BIGINT UNSIGNED NOT NULL COMMENT '阶段ID',
  `plan_id` BIGINT UNSIGNED NOT NULL COMMENT '规划ID（冗余，便于查询）',
  
  `name` VARCHAR(200) NOT NULL COMMENT '任务名称',
  `description` TEXT DEFAULT NULL COMMENT '任务详细说明',
  
  -- 截止时间
  `due_date` DATE DEFAULT NULL COMMENT '截止日期',
  `reminder_date` DATE DEFAULT NULL COMMENT '提醒日期',
  
  -- 状态
  `status` ENUM('not_started', 'in_progress', 'completed', 'skipped') DEFAULT 'not_started' COMMENT '任务状态',
  
  -- 关联资源
  `guide_url` VARCHAR(500) DEFAULT NULL COMMENT '详细指南链接',
  `related_tool` VARCHAR(50) DEFAULT NULL COMMENT '相关工具（cost_calculator/gpa_converter）',
  
  -- 排序
  `sort_order` SMALLINT UNSIGNED NOT NULL COMMENT '排序顺序',
  
  `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
  `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `completed_at` DATETIME DEFAULT NULL COMMENT '完成时间',
  
  KEY `idx_stage_id` (`stage_id`),
  KEY `idx_plan_id` (`plan_id`),
  KEY `idx_status` (`status`),
  KEY `idx_due_date` (`due_date`),
  FOREIGN KEY (`stage_id`) REFERENCES `plan_stages`(`id`) ON DELETE CASCADE,
  FOREIGN KEY (`plan_id`) REFERENCES `plans`(`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='规划任务表';
```

#### 3.3.4 plan_milestones (里程碑)

**功能**: 规划的关键节点（如"托福出分"、"拿到offer"、"签证通过"）

```sql
CREATE TABLE `plan_milestones` (
  `id` BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  `plan_id` BIGINT UNSIGNED NOT NULL COMMENT '规划ID',
  
  `name` VARCHAR(100) NOT NULL COMMENT '里程碑名称',
  `description` TEXT DEFAULT NULL COMMENT '说明',
  `icon` VARCHAR(50) DEFAULT NULL COMMENT '图标名称',
  
  `milestone_date` DATE NOT NULL COMMENT '里程碑日期',
  `status` ENUM('pending', 'completed', 'missed') DEFAULT 'pending' COMMENT '状态',
  
  `sort_order` TINYINT UNSIGNED NOT NULL COMMENT '排序',
  
  `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
  `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `completed_at` DATETIME DEFAULT NULL COMMENT '完成时间',
  
  KEY `idx_plan_id` (`plan_id`),
  KEY `idx_milestone_date` (`milestone_date`),
  FOREIGN KEY (`plan_id`) REFERENCES `plans`(`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='规划里程碑表';
```

#### 3.3.5 material_checklists (材料清单)

**功能**: 申请、签证所需的材料清单

```sql
CREATE TABLE `material_checklists` (
  `id` BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  `plan_id` BIGINT UNSIGNED NOT NULL COMMENT '规划ID',
  
  `material_name` VARCHAR(200) NOT NULL COMMENT '材料名称',
  `category` ENUM('required', 'supporting', 'optional') NOT NULL COMMENT '材料类别',
  
  `description` TEXT DEFAULT NULL COMMENT '材料说明',
  `requirements` TEXT DEFAULT NULL COMMENT '要求细节（格式、份数等）',
  `example_url` VARCHAR(500) DEFAULT NULL COMMENT '示例文件URL',
  
  -- 状态
  `status` ENUM('not_started', 'in_progress', 'completed') DEFAULT 'not_started' COMMENT '准备状态',
  
  -- 提醒
  `reminder_date` DATE DEFAULT NULL COMMENT '提醒日期',
  `due_date` DATE DEFAULT NULL COMMENT '截止日期',
  
  -- 文件数量（冗余）
  `file_count` TINYINT UNSIGNED DEFAULT 0 COMMENT '已上传文件数',
  
  `sort_order` SMALLINT UNSIGNED NOT NULL COMMENT '排序',
  
  `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
  `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `completed_at` DATETIME DEFAULT NULL COMMENT '完成时间',
  
  KEY `idx_plan_id` (`plan_id`),
  KEY `idx_category` (`category`),
  KEY `idx_status` (`status`),
  FOREIGN KEY (`plan_id`) REFERENCES `plans`(`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='材料清单表';
```

#### 3.3.6 material_files (材料文件记录)

**功能**: 用户上传的材料文件记录

```sql
CREATE TABLE `material_files` (
  `id` BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  `checklist_id` BIGINT UNSIGNED NOT NULL COMMENT '材料清单ID',
  `user_id` BIGINT UNSIGNED NOT NULL COMMENT '用户ID',
  
  `file_name` VARCHAR(255) NOT NULL COMMENT '文件名',
  `file_url` VARCHAR(500) NOT NULL COMMENT '文件URL（OSS）',
  `file_type` VARCHAR(50) DEFAULT NULL COMMENT '文件类型（pdf/jpg/png）',
  `file_size` INT UNSIGNED DEFAULT NULL COMMENT '文件大小（字节）',
  
  `uploaded_at` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '上传时间',
  `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
  
  KEY `idx_checklist_id` (`checklist_id`),
  KEY `idx_user_id` (`user_id`),
  FOREIGN KEY (`checklist_id`) REFERENCES `material_checklists`(`id`) ON DELETE CASCADE,
  FOREIGN KEY (`user_id`) REFERENCES `users`(`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='材料文件记录';
```

---

### 3.4 社区模块

#### 3.4.1 posts (帖子主表)

**功能**: 用户发布的帖子、问答、动态等

```sql
CREATE TABLE `posts` (
  `id` BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  `author_id` BIGINT UNSIGNED NOT NULL COMMENT '作者ID',
  
  -- 内容
  `title` VARCHAR(200) NOT NULL COMMENT '标题',
  `content` MEDIUMTEXT NOT NULL COMMENT '正文内容（Markdown）',
  `summary` VARCHAR(500) DEFAULT NULL COMMENT '摘要（自动提取）',
  
  -- 类型
  `content_type` ENUM('post', 'question', 'timeline', 'vlog') DEFAULT 'post' COMMENT '内容类型',
  
  -- 分类
  `category` VARCHAR(50) DEFAULT NULL COMMENT '分区（按国家/阶段/类型）',
  `country_code` CHAR(2) DEFAULT NULL COMMENT '关联国家',
  
  -- 媒体
  `cover_image` VARCHAR(500) DEFAULT NULL COMMENT '封面图',
  `media_urls` JSON DEFAULT NULL COMMENT '图片/视频列表 ["url1","url2"]',
  
  -- 统计（冗余，提升性能）
  `view_count` INT UNSIGNED DEFAULT 0 COMMENT '浏览量',
  `like_count` INT UNSIGNED DEFAULT 0 COMMENT '点赞数',
  `comment_count` INT UNSIGNED DEFAULT 0 COMMENT '评论数',
  `collect_count` INT UNSIGNED DEFAULT 0 COMMENT '收藏数',
  `share_count` INT UNSIGNED DEFAULT 0 COMMENT '分享数',
  
  -- 状态
  `is_featured` TINYINT(1) DEFAULT 0 COMMENT '是否精选',
  `is_pinned` TINYINT(1) DEFAULT 0 COMMENT '是否置顶',
  `is_hot` TINYINT(1) DEFAULT 0 COMMENT '是否热门',
  `status` ENUM('draft', 'published', 'hidden', 'deleted') DEFAULT 'published' COMMENT '发布状态',
  
  -- 权限
  `allow_comment` TINYINT(1) DEFAULT 1 COMMENT '允许评论',
  
  -- SEO
  `slug` VARCHAR(200) DEFAULT NULL COMMENT 'URL友好标识',
  
  `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
  `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `published_at` DATETIME DEFAULT NULL COMMENT '发布时间',
  `deleted_at` DATETIME DEFAULT NULL COMMENT '删除时间',
  
  KEY `idx_author_id` (`author_id`),
  KEY `idx_category` (`category`),
  KEY `idx_country_code` (`country_code`),
  KEY `idx_content_type` (`content_type`),
  KEY `idx_status` (`status`),
  KEY `idx_created_at` (`created_at` DESC),
  KEY `idx_hot_featured` (`is_hot`, `is_featured`),
  FULLTEXT KEY `ft_title_content` (`title`, `content`),
  FOREIGN KEY (`author_id`) REFERENCES `users`(`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='帖子表';
```

#### 3.4.2 comments (评论表)

**功能**: 帖子的评论和回复（支持嵌套）

```sql
CREATE TABLE `comments` (
  `id` BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  `post_id` BIGINT UNSIGNED NOT NULL COMMENT '帖子ID',
  `author_id` BIGINT UNSIGNED NOT NULL COMMENT '评论者ID',
  `parent_id` BIGINT UNSIGNED DEFAULT NULL COMMENT '父评论ID（回复用）',
  `root_id` BIGINT UNSIGNED DEFAULT NULL COMMENT '根评论ID（方便查询楼层）',
  
  `content` TEXT NOT NULL COMMENT '评论内容',
  
  -- 统计
  `like_count` INT UNSIGNED DEFAULT 0 COMMENT '点赞数',
  `reply_count` INT UNSIGNED DEFAULT 0 COMMENT '回复数',
  
  -- 状态
  `status` ENUM('visible', 'hidden', 'deleted') DEFAULT 'visible' COMMENT '状态',
  
  `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
  `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `deleted_at` DATETIME DEFAULT NULL,
  
  KEY `idx_post_id` (`post_id`),
  KEY `idx_author_id` (`author_id`),
  KEY `idx_parent_id` (`parent_id`),
  KEY `idx_root_id` (`root_id`),
  KEY `idx_created_at` (`created_at`),
  FOREIGN KEY (`post_id`) REFERENCES `posts`(`id`) ON DELETE CASCADE,
  FOREIGN KEY (`author_id`) REFERENCES `users`(`id`) ON DELETE CASCADE,
  FOREIGN KEY (`parent_id`) REFERENCES `comments`(`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='评论表';
```

#### 3.4.3 post_likes (点赞记录)

**功能**: 用户对帖子的点赞记录

```sql
CREATE TABLE `post_likes` (
  `id` BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  `post_id` BIGINT UNSIGNED NOT NULL COMMENT '帖子ID',
  `user_id` BIGINT UNSIGNED NOT NULL COMMENT '用户ID',
  `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
  
  UNIQUE KEY `uk_post_user` (`post_id`, `user_id`),
  KEY `idx_user_id` (`user_id`),
  FOREIGN KEY (`post_id`) REFERENCES `posts`(`id`) ON DELETE CASCADE,
  FOREIGN KEY (`user_id`) REFERENCES `users`(`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='帖子点赞记录';
```

#### 3.4.4 post_collections (收藏记录)

**功能**: 用户收藏的帖子

```sql
CREATE TABLE `post_collections` (
  `id` BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  `post_id` BIGINT UNSIGNED NOT NULL COMMENT '帖子ID',
  `user_id` BIGINT UNSIGNED NOT NULL COMMENT '用户ID',
  `folder` VARCHAR(50) DEFAULT 'default' COMMENT '收藏夹名称',
  `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
  
  UNIQUE KEY `uk_post_user` (`post_id`, `user_id`),
  KEY `idx_user_folder` (`user_id`, `folder`),
  FOREIGN KEY (`post_id`) REFERENCES `posts`(`id`) ON DELETE CASCADE,
  FOREIGN KEY (`user_id`) REFERENCES `users`(`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='帖子收藏记录';
```

#### 3.4.5 tags (标签字典表)

**功能**: 全局标签字典

```sql
CREATE TABLE `tags` (
  `id` BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  `name` VARCHAR(50) NOT NULL COMMENT '标签名称',
  `slug` VARCHAR(50) NOT NULL COMMENT 'URL友好标识',
  `description` VARCHAR(200) DEFAULT NULL COMMENT '标签描述',
  `color` VARCHAR(20) DEFAULT NULL COMMENT '标签颜色（HEX）',
  
  `post_count` INT UNSIGNED DEFAULT 0 COMMENT '帖子数（冗余）',
  
  `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
  `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  
  UNIQUE KEY `uk_name` (`name`),
  UNIQUE KEY `uk_slug` (`slug`),
  KEY `idx_post_count` (`post_count` DESC)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='标签字典表';
```

#### 3.4.6 post_tags (帖子-标签关联表)

**功能**: 帖子与标签的多对多关系

```sql
CREATE TABLE `post_tags` (
  `id` BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  `post_id` BIGINT UNSIGNED NOT NULL,
  `tag_id` BIGINT UNSIGNED NOT NULL,
  `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
  
  UNIQUE KEY `uk_post_tag` (`post_id`, `tag_id`),
  KEY `idx_tag_id` (`tag_id`),
  FOREIGN KEY (`post_id`) REFERENCES `posts`(`id`) ON DELETE CASCADE,
  FOREIGN KEY (`tag_id`) REFERENCES `tags`(`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='帖子标签关联表';
```

#### 3.4.7 user_follows (用户关注关系)

**功能**: 用户之间的关注/粉丝关系

```sql
CREATE TABLE `user_follows` (
  `id` BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  `follower_id` BIGINT UNSIGNED NOT NULL COMMENT '关注者ID',
  `followee_id` BIGINT UNSIGNED NOT NULL COMMENT '被关注者ID',
  `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
  
  UNIQUE KEY `uk_follower_followee` (`follower_id`, `followee_id`),
  KEY `idx_followee_id` (`followee_id`),
  FOREIGN KEY (`follower_id`) REFERENCES `users`(`id`) ON DELETE CASCADE,
  FOREIGN KEY (`followee_id`) REFERENCES `users`(`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户关注关系';
```

---

### 3.5 工具模块

#### 3.5.1 cost_calculations (费用计算记录)

**功能**: 保存用户的费用计算结果

```sql
CREATE TABLE `cost_calculations` (
  `id` BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  `user_id` BIGINT UNSIGNED DEFAULT NULL COMMENT '用户ID（可为空，游客也可用）',
  `country_code` CHAR(2) NOT NULL COMMENT '国家代码',
  
  -- 输入参数（JSON）
  `input_params` JSON NOT NULL COMMENT '输入参数：{"school_type":"public","region":"west_coast","tuition":40000}',
  
  -- 计算结果（JSON）
  `result` JSON NOT NULL COMMENT '结果：{"total_per_year":77500,"breakdown":{...}}',
  
  `scheme_name` VARCHAR(100) DEFAULT NULL COMMENT '方案名称（用户自定义）',
  `is_saved` TINYINT(1) DEFAULT 0 COMMENT '是否保存',
  
  `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
  
  KEY `idx_user_id` (`user_id`),
  KEY `idx_country_code` (`country_code`),
  FOREIGN KEY (`user_id`) REFERENCES `users`(`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='费用计算记录';
```

#### 3.5.2 gpa_conversions (GPA转换记录)

**功能**: GPA转换工具的使用记录

```sql
CREATE TABLE `gpa_conversions` (
  `id` BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  `user_id` BIGINT UNSIGNED DEFAULT NULL COMMENT '用户ID',
  
  `from_system` VARCHAR(50) NOT NULL COMMENT '源系统（china_4.0/china_100/uk等）',
  `to_system` VARCHAR(50) NOT NULL COMMENT '目标系统',
  `input_value` DECIMAL(5,2) NOT NULL COMMENT '输入值',
  `output_value` DECIMAL(5,2) NOT NULL COMMENT '输出值',
  
  `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
  
  KEY `idx_user_id` (`user_id`),
  FOREIGN KEY (`user_id`) REFERENCES `users`(`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='GPA转换记录';
```

#### 3.5.3 visa_slots (签证预约信息)

**功能**: 各使馆签证预约情况（爬虫/手动维护）

```sql
CREATE TABLE `visa_slots` (
  `id` BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  `country_code` CHAR(2) NOT NULL COMMENT '国家代码',
  `visa_type` VARCHAR(50) NOT NULL COMMENT '签证类型（F1/B1/H1B等）',
  `embassy_city` VARCHAR(50) NOT NULL COMMENT '使馆城市（beijing/shanghai/guangzhou）',
  
  `earliest_date` DATE DEFAULT NULL COMMENT '最早可预约日期',
  `available_slots` SMALLINT UNSIGNED DEFAULT 0 COMMENT '可用名额数',
  
  `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
  
  KEY `idx_country_type` (`country_code`, `visa_type`),
  KEY `idx_embassy_city` (`embassy_city`),
  KEY `idx_earliest_date` (`earliest_date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='签证预约信息';
```

---

### 3.6 通知模块

#### 3.6.1 reminders (用户提醒)

**功能**: 用户设置的任务提醒、截止日期提醒等

```sql
CREATE TABLE `reminders` (
  `id` BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  `user_id` BIGINT UNSIGNED NOT NULL COMMENT '用户ID',
  `plan_id` BIGINT UNSIGNED DEFAULT NULL COMMENT '关联规划ID',
  `task_id` BIGINT UNSIGNED DEFAULT NULL COMMENT '关联任务ID',
  `checklist_id` BIGINT UNSIGNED DEFAULT NULL COMMENT '关联材料清单ID',
  
  `title` VARCHAR(200) NOT NULL COMMENT '提醒标题',
  `content` TEXT DEFAULT NULL COMMENT '提醒内容',
  `remind_time` DATETIME NOT NULL COMMENT '提醒时间',
  
  -- 重复设置
  `is_recurring` TINYINT(1) DEFAULT 0 COMMENT '是否重复',
  `recurrence_rule` VARCHAR(100) DEFAULT NULL COMMENT '重复规则（daily/weekly/monthly）',
  
  -- 发送状态
  `is_sent` TINYINT(1) DEFAULT 0 COMMENT '是否已发送',
  `sent_at` DATETIME DEFAULT NULL COMMENT '发送时间',
  
  `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
  `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  
  KEY `idx_user_id` (`user_id`),
  KEY `idx_remind_time` (`remind_time`),
  KEY `idx_is_sent` (`is_sent`),
  FOREIGN KEY (`user_id`) REFERENCES `users`(`id`) ON DELETE CASCADE,
  FOREIGN KEY (`plan_id`) REFERENCES `plans`(`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户提醒表';
```

#### 3.6.2 notifications (系统通知)

**功能**: 系统消息、互动通知（评论、点赞、关注等）

```sql
CREATE TABLE `notifications` (
  `id` BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  `user_id` BIGINT UNSIGNED NOT NULL COMMENT '接收者ID',
  `sender_id` BIGINT UNSIGNED DEFAULT NULL COMMENT '发送者ID（系统通知为NULL）',
  
  `type` ENUM('system', 'like', 'comment', 'follow', 'reply', 'mention', 'policy_update') NOT NULL COMMENT '通知类型',
  `title` VARCHAR(200) NOT NULL COMMENT '通知标题',
  `content` TEXT NOT NULL COMMENT '通知内容',
  
  -- 关联对象
  `related_type` VARCHAR(50) DEFAULT NULL COMMENT '关联对象类型（post/comment/plan）',
  `related_id` BIGINT UNSIGNED DEFAULT NULL COMMENT '关联对象ID',
  
  -- 状态
  `is_read` TINYINT(1) DEFAULT 0 COMMENT '是否已读',
  `read_at` DATETIME DEFAULT NULL COMMENT '阅读时间',
  
  `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
  
  KEY `idx_user_id` (`user_id`),
  KEY `idx_is_read` (`is_read`),
  KEY `idx_type` (`type`),
  KEY `idx_created_at` (`created_at` DESC),
  FOREIGN KEY (`user_id`) REFERENCES `users`(`id`) ON DELETE CASCADE,
  FOREIGN KEY (`sender_id`) REFERENCES `users`(`id`) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='系统通知表';
```

---

### 3.7 系统模块

#### 3.7.1 admin_users (管理员)

**功能**: 后台管理员账号

```sql
CREATE TABLE `admin_users` (
  `id` BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  `username` VARCHAR(50) NOT NULL COMMENT '管理员用户名',
  `password_hash` VARCHAR(255) NOT NULL COMMENT '密码哈希',
  `email` VARCHAR(100) NOT NULL COMMENT '邮箱',
  `nickname` VARCHAR(50) DEFAULT NULL COMMENT '昵称',
  
  `role` ENUM('super_admin', 'admin', 'editor', 'viewer') DEFAULT 'editor' COMMENT '角色',
  `permissions` JSON DEFAULT NULL COMMENT '权限列表',
  
  `status` ENUM('active', 'disabled') DEFAULT 'active' COMMENT '状态',
  
  `last_login_at` DATETIME DEFAULT NULL,
  `last_login_ip` VARCHAR(45) DEFAULT NULL,
  
  `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
  `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  
  UNIQUE KEY `uk_username` (`username`),
  UNIQUE KEY `uk_email` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='管理员表';
```

#### 3.7.2 audit_logs (操作审计日志)

**功能**: 记录关键操作日志（用户行为、管理员操作）

```sql
CREATE TABLE `audit_logs` (
  `id` BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  `user_id` BIGINT UNSIGNED DEFAULT NULL COMMENT '用户ID',
  `admin_id` BIGINT UNSIGNED DEFAULT NULL COMMENT '管理员ID',
  
  `action` VARCHAR(100) NOT NULL COMMENT '操作动作（user.register/post.delete等）',
  `resource_type` VARCHAR(50) DEFAULT NULL COMMENT '资源类型',
  `resource_id` BIGINT UNSIGNED DEFAULT NULL COMMENT '资源ID',
  
  `ip_address` VARCHAR(45) DEFAULT NULL COMMENT 'IP地址',
  `user_agent` VARCHAR(500) DEFAULT NULL COMMENT 'User Agent',
  
  `request_data` JSON DEFAULT NULL COMMENT '请求数据',
  `response_data` JSON DEFAULT NULL COMMENT '响应数据',
  
  `status` VARCHAR(20) DEFAULT 'success' COMMENT '操作状态（success/failed）',
  
  `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
  
  KEY `idx_user_id` (`user_id`),
  KEY `idx_admin_id` (`admin_id`),
  KEY `idx_action` (`action`),
  KEY `idx_created_at` (`created_at`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='操作审计日志';
```

#### 3.7.3 system_configs (系统配置)

**功能**: 存储系统级配置参数

```sql
CREATE TABLE `system_configs` (
  `id` BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  `config_key` VARCHAR(100) NOT NULL COMMENT '配置键',
  `config_value` TEXT NOT NULL COMMENT '配置值',
  `value_type` ENUM('string', 'number', 'boolean', 'json') DEFAULT 'string' COMMENT '值类型',
  `description` VARCHAR(500) DEFAULT NULL COMMENT '配置说明',
  `is_public` TINYINT(1) DEFAULT 0 COMMENT '是否公开（前端可访问）',
  
  `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
  
  UNIQUE KEY `uk_config_key` (`config_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='系统配置表';
```

---

## 4. 关系图

### 4.1 核心关系流程图

```
┌──────────┐
│  users   │──────┬──────────────────────┐
└──────────┘      │                      │
      │           │                      │
      │           ▼                      ▼
      │     ┌──────────┐          ┌──────────┐
      │     │  plans   │          │  posts   │
      │     └──────────┘          └──────────┘
      │           │                      │
      │           ├──────┐               ├──────┐
      │           ▼      ▼               ▼      ▼
      │     ┌─────────┐ ┌─────────┐  ┌────────┐ ┌────────┐
      │     │ stages  │ │materials│  │comments│ │  likes │
      │     └─────────┘ └─────────┘  └────────┘ └────────┘
      │           │
      │           ▼
      │     ┌─────────┐
      │     │  tasks  │
      │     └─────────┘
      │
      ▼
┌──────────────┐
│ preferences  │
└──────────────┘
```

### 4.2 数据量级预估（第一年）

| 表名 | 预估记录数 | 增长速度 | 备注 |
|------|-----------|---------|------|
| users | 50,000 | 快 | 用户基数 |
| posts | 100,000 | 中 | 日均 100-200 篇 |
| comments | 500,000 | 快 | 评论活跃 |
| plans | 20,000 | 中 | 40% 用户创建规划 |
| plan_tasks | 200,000 | 中 | 平均每规划 10 个任务 |
| countries | 20 | 慢 | 逐步增加国家 |
| notifications | 1,000,000 | 快 | 高频产生 |

---

## 5. 索引策略

### 5.1 索引原则

1. **主键索引**: 所有表自动创建（`id`）
2. **唯一索引**: `username`, `email`, `phone` 等唯一字段
3. **外键索引**: 所有外键字段
4. **查询索引**: 高频 WHERE/ORDER BY 字段
5. **复合索引**: 多条件查询（遵循最左前缀原则）
6. **全文索引**: 帖子标题和内容搜索

### 5.2 重点索引设计

```sql
-- users 表
CREATE INDEX idx_users_status_level ON users(status, level);
CREATE INDEX idx_users_created_at ON users(created_at DESC);

-- posts 表
CREATE INDEX idx_posts_author_status ON posts(author_id, status);
CREATE INDEX idx_posts_category_created ON posts(category, created_at DESC);
CREATE INDEX idx_posts_hot_featured ON posts(is_hot, is_featured, created_at DESC);
CREATE FULLTEXT INDEX ft_posts_search ON posts(title, content);

-- plans 表
CREATE INDEX idx_plans_user_status ON plans(user_id, status);
CREATE INDEX idx_plans_country_type ON plans(country_code, plan_type);

-- plan_tasks 表
CREATE INDEX idx_tasks_plan_status ON plan_tasks(plan_id, status);
CREATE INDEX idx_tasks_stage_order ON plan_tasks(stage_id, sort_order);

-- comments 表
CREATE INDEX idx_comments_post_created ON comments(post_id, created_at);

-- notifications 表
CREATE INDEX idx_notifications_user_read ON notifications(user_id, is_read, created_at DESC);
```

### 5.3 索引维护建议

- ✅ 定期 `ANALYZE TABLE` 更新统计信息
- ✅ 监控慢查询日志，优化高频查询
- ✅ 避免过度索引（写入性能下降）
- ✅ 考虑使用覆盖索引减少回表

---

## 6. 数据字典

### 6.1 枚举值定义

#### 用户状态 (users.status)
```
active    - 正常
inactive  - 未激活
banned    - 被封禁
deleted   - 已删除（软删除）
```

#### 规划类型 (plans.plan_type)
```
study       - 留学
work        - 工作
immigration - 移民
```

#### 任务状态 (plan_tasks.status)
```
not_started - 未开始
in_progress - 进行中
completed   - 已完成
skipped     - 已跳过
```

#### 材料类别 (material_checklists.category)
```
required   - 必需材料
supporting - 支持性材料
optional   - 可选材料
```

#### 帖子类型 (posts.content_type)
```
post     - 普通帖子（经验分享）
question - 提问
timeline - 动态（短内容）
vlog     - 视频日志
```

#### 通知类型 (notifications.type)
```
system        - 系统通知
like          - 点赞
comment       - 评论
follow        - 关注
reply         - 回复
mention       - @提及
policy_update - 政策更新
```

---

## 7. SQL迁移脚本

### 7.1 创建数据库

```sql
-- 创建数据库
CREATE DATABASE IF NOT EXISTS goabroad_db
DEFAULT CHARACTER SET utf8mb4
DEFAULT COLLATE utf8mb4_unicode_ci;

USE goabroad_db;
```

### 7.2 初始化脚本（完整版）

由于篇幅限制，完整的 DDL 脚本已在上面各表设计中给出。执行顺序如下：

```sql
-- 1. 用户模块
SOURCE 01_users.sql;
SOURCE 02_user_profiles.sql;
SOURCE 03_user_preferences.sql;
SOURCE 04_user_oauth_bindings.sql;

-- 2. 国家模块
SOURCE 05_countries.sql;
SOURCE 06_country_policies.sql;
SOURCE 07_user_country_favorites.sql;

-- 3. 规划模块
SOURCE 08_plans.sql;
SOURCE 09_plan_stages.sql;
SOURCE 10_plan_tasks.sql;
SOURCE 11_plan_milestones.sql;
SOURCE 12_material_checklists.sql;
SOURCE 13_material_files.sql;

-- 4. 社区模块
SOURCE 14_posts.sql;
SOURCE 15_comments.sql;
SOURCE 16_post_likes.sql;
SOURCE 17_post_collections.sql;
SOURCE 18_tags.sql;
SOURCE 19_post_tags.sql;
SOURCE 20_user_follows.sql;

-- 5. 工具模块
SOURCE 21_cost_calculations.sql;
SOURCE 22_gpa_conversions.sql;
SOURCE 23_visa_slots.sql;

-- 6. 通知模块
SOURCE 24_reminders.sql;
SOURCE 25_notifications.sql;

-- 7. 系统模块
SOURCE 26_admin_users.sql;
SOURCE 27_audit_logs.sql;
SOURCE 28_system_configs.sql;
```

### 7.3 种子数据（示例）

```sql
-- 插入初始管理员
INSERT INTO admin_users (username, password_hash, email, nickname, role) VALUES
('admin', '$2a$10$abcdefghijklmnopqrstuvwxyz1234567890', 'admin@goabroad.com', '超级管理员', 'super_admin');

-- 插入初始国家数据
INSERT INTO countries (code, name_zh, name_en, flag_emoji, is_active, sort_order) VALUES
('US', '美国', 'United States', '🇺🇸', 1, 1),
('UK', '英国', 'United Kingdom', '🇬🇧', 1, 2),
('CA', '加拿大', 'Canada', '🇨🇦', 1, 3),
('AU', '澳大利亚', 'Australia', '🇦🇺', 1, 4),
('JP', '日本', 'Japan', '🇯🇵', 1, 5);

-- 插入初始标签
INSERT INTO tags (name, slug, color) VALUES
('美国留学', 'us-study', '#2563EB'),
('签证经验', 'visa-experience', '#059669'),
('材料准备', 'materials', '#D97706'),
('语言考试', 'language-test', '#DC2626'),
('行前准备', 'pre-departure', '#06B6D4');

-- 插入系统配置
INSERT INTO system_configs (config_key, config_value, value_type, description, is_public) VALUES
('site_name', 'GoAbroad 出国助手', 'string', '网站名称', 1),
('site_url', 'https://goabroad.app', 'string', '网站URL', 1),
('user_init_points', '100', 'number', '新用户初始积分', 0),
('vip_price_monthly', '19.9', 'number', '月度会员价格', 1),
('vip_price_yearly', '149', 'number', '年度会员价格', 1);
```

---

## 8. 性能优化建议

### 8.1 MySQL 配置优化

```ini
# my.cnf 推荐配置

[mysqld]
# 字符集
character-set-server = utf8mb4
collation-server = utf8mb4_unicode_ci

# InnoDB 配置
innodb_buffer_pool_size = 2G        # 根据服务器内存调整（建议50-70%）
innodb_log_file_size = 256M
innodb_flush_log_at_trx_commit = 2  # 平衡性能和安全
innodb_flush_method = O_DIRECT

# 连接数
max_connections = 500
max_connect_errors = 10000

# 查询缓存（MySQL 8.0已移除）
# query_cache_size = 0

# 慢查询日志
slow_query_log = 1
slow_query_log_file = /var/log/mysql/slow.log
long_query_time = 2

# 二进制日志（主从复制/备份）
log_bin = mysql-bin
expire_logs_days = 7
```

### 8.2 查询优化技巧

#### 1. 使用覆盖索引
```sql
-- 不好：需要回表
SELECT * FROM posts WHERE author_id = 123;

-- 好：覆盖索引
SELECT id, title, created_at FROM posts WHERE author_id = 123;
```

#### 2. 避免 SELECT *
```sql
-- 不好
SELECT * FROM users WHERE id = 1;

-- 好：只查询需要的字段
SELECT id, username, nickname, avatar_url FROM users WHERE id = 1;
```

#### 3. 分页优化（深分页问题）
```sql
-- 不好：OFFSET 过大时性能差
SELECT * FROM posts ORDER BY id DESC LIMIT 10000, 20;

-- 好：使用游标分页
SELECT * FROM posts WHERE id < 12345 ORDER BY id DESC LIMIT 20;
```

#### 4. COUNT 优化
```sql
-- 不好：全表扫描
SELECT COUNT(*) FROM posts;

-- 好：利用索引
SELECT COUNT(*) FROM posts WHERE status = 'published';

-- 或使用近似值（大数据量时）
SELECT table_rows FROM information_schema.tables 
WHERE table_name = 'posts';
```

### 8.3 缓存策略

#### Redis 缓存设计

```
热点数据缓存：
├── user:{id}           用户信息（TTL: 1小时）
├── post:{id}           帖子详情（TTL: 30分钟）
├── post:hot            热门帖子列表（TTL: 5分钟）
├── country:{code}      国家信息（TTL: 24小时）
├── user:{id}:plans     用户规划列表（TTL: 10分钟）
└── stats:*             统计数据（计数器）
```

#### 缓存更新策略
- **Cache Aside**: 读时缓存，写时删除
- **Write Through**: 写时同步更新缓存
- **Write Behind**: 异步写入数据库

### 8.4 分库分表建议（未来扩展）

当单表数据量超过千万级时考虑：

**垂直拆分**：
- `posts` → `posts_content` (内容单独存储)
- `users` → `user_profiles` (已实现)

**水平分表**：
- `posts_2024_Q1`, `posts_2024_Q2` (按时间)
- `posts_0`, `posts_1` ... (按哈希)

**读写分离**：
- Master（写）+ Slave（读）
- 使用中间件：ProxySQL / MaxScale

---

## 9. 备份与恢复

### 9.1 备份策略

```bash
# 全量备份（每天凌晨3点）
mysqldump -u root -p --single-transaction \
  --master-data=2 --flush-logs \
  goabroad_db > backup_$(date +%Y%m%d).sql

# 增量备份（利用 binlog）
mysqlbinlog mysql-bin.000001 > incremental.sql
```

### 9.2 恢复流程

```bash
# 恢复全量备份
mysql -u root -p goabroad_db < backup_20241019.sql

# 应用增量备份
mysql -u root -p goabroad_db < incremental.sql
```

---

## 10. 安全建议

### 10.1 数据库安全

✅ **最小权限原则**
```sql
-- 应用程序账号（不要用root）
CREATE USER 'goabroad_app'@'%' IDENTIFIED BY 'strong_password';
GRANT SELECT, INSERT, UPDATE, DELETE ON goabroad_db.* TO 'goabroad_app'@'%';
FLUSH PRIVILEGES;
```

✅ **敏感数据加密**
- 密码：使用 bcrypt (password_hash字段)
- Token：AES加密存储
- 个人信息：考虑字段级加密

✅ **SQL注入防护**
- 使用参数化查询（Prepared Statement）
- ORM框架（Prisma/TypeORM）自动防护

✅ **定期安全审计**
- 检查 audit_logs 表
- 监控异常查询

---

## 11. 监控指标

### 11.1 关键指标

```sql
-- 1. 数据库大小
SELECT 
  table_schema AS 'Database',
  ROUND(SUM(data_length + index_length) / 1024 / 1024, 2) AS 'Size (MB)'
FROM information_schema.tables
WHERE table_schema = 'goabroad_db';

-- 2. 各表行数和大小
SELECT 
  table_name,
  table_rows,
  ROUND(data_length / 1024 / 1024, 2) AS 'Data (MB)',
  ROUND(index_length / 1024 / 1024, 2) AS 'Index (MB)'
FROM information_schema.tables
WHERE table_schema = 'goabroad_db'
ORDER BY data_length DESC;

-- 3. 慢查询统计
SELECT * FROM mysql.slow_log ORDER BY query_time DESC LIMIT 10;
```

### 11.2 监控工具推荐

- **Prometheus + Grafana**: 可视化监控
- **Percona Monitoring**: MySQL专用监控
- **Sentry**: 应用层错误追踪

---

## 附录

### A. 表清单汇总

| # | 表名 | 功能 | 记录数预估 |
|---|------|------|-----------|
| 1 | users | 用户主表 | 50,000 |
| 2 | user_profiles | 用户资料 | 50,000 |
| 3 | user_preferences | 用户偏好 | 50,000 |
| 4 | user_oauth_bindings | 第三方登录 | 30,000 |
| 5 | countries | 国家信息 | 20 |
| 6 | country_policies | 政策更新 | 500 |
| 7 | user_country_favorites | 收藏国家 | 100,000 |
| 8 | plans | 规划主表 | 20,000 |
| 9 | plan_stages | 规划阶段 | 160,000 |
| 10 | plan_tasks | 阶段任务 | 200,000 |
| 11 | plan_milestones | 里程碑 | 100,000 |
| 12 | material_checklists | 材料清单 | 300,000 |
| 13 | material_files | 材料文件 | 500,000 |
| 14 | posts | 帖子 | 100,000 |
| 15 | comments | 评论 | 500,000 |
| 16 | post_likes | 点赞记录 | 1,000,000 |
| 17 | post_collections | 收藏记录 | 300,000 |
| 18 | tags | 标签字典 | 500 |
| 19 | post_tags | 帖子-标签 | 300,000 |
| 20 | user_follows | 关注关系 | 200,000 |
| 21 | cost_calculations | 费用计算 | 50,000 |
| 22 | gpa_conversions | GPA转换 | 30,000 |
| 23 | visa_slots | 签证预约 | 100 |
| 24 | reminders | 提醒 | 500,000 |
| 25 | notifications | 通知 | 1,000,000 |
| 26 | admin_users | 管理员 | 10 |
| 27 | audit_logs | 审计日志 | 5,000,000 |
| 28 | system_configs | 系统配置 | 100 |

**总计**: 28 张表

### B. 版本历史

| 版本 | 日期 | 变更说明 |
|------|------|----------|
| v1.0 | 2025-10-19 | 初始版本，基于 MySQL 8.0 设计 |

---

**文档完**

---

这份数据库文档涵盖了 GoAbroad 项目的所有核心功能模块，你可以：

1. **直接使用**: 复制 SQL 脚本创建数据库
2. **根据需求调整**: 字段类型、索引等都可以微调
3. **配合 ORM**: 推荐使用 Prisma，我可以帮你转换成 Prisma Schema

需要我：
- 生成 Prisma Schema 文件吗？
- 创建初始化脚本（包含测试数据）吗？
- 生成 API 接口文档吗？

请告诉我下一步需要什么！