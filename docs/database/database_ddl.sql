create table admin_users
(
    id            bigint unsigned auto_increment
        primary key,
    username      varchar(50)                                                                 not null comment '管理员用户名',
    password_hash varchar(255)                                                                not null comment '密码哈希',
    email         varchar(100)                                                                not null comment '邮箱',
    nickname      varchar(50)                                                                 null comment '昵称',
    role          enum ('SUPER_ADMIN', 'ADMIN', 'EDITOR', 'VIEWER') default 'EDITOR'          null,
    permissions   json                                                                        null comment '权限列表',
    status        enum ('ACTIVE', 'INACTIVE', 'DISABLED')           default 'ACTIVE'          null,
    last_login_at datetime                                                                    null,
    last_login_ip varchar(45)                                                                 null,
    created_at    datetime                                          default CURRENT_TIMESTAMP null,
    updated_at    datetime                                          default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP,
    deleted       tinyint(1)                                        default 0                 null comment '逻辑删除标记',
    version_id    int unsigned                                      default '0'               null comment '乐观锁版本号',
    constraint uk_email
        unique (email),
    constraint uk_username
        unique (username)
)
    comment '管理员表';

create index idx_deleted
    on admin_users (deleted);

create table audit_logs
(
    id            bigint unsigned auto_increment
        primary key,
    user_id       bigint unsigned                       null comment '用户ID',
    admin_id      bigint unsigned                       null comment '管理员ID',
    action        varchar(100)                          not null comment '操作动作（user.register/post.delete等）',
    resource_type varchar(50)                           null comment '资源类型',
    resource_id   bigint unsigned                       null comment '资源ID',
    ip_address    varchar(45)                           null comment 'IP地址',
    user_agent    varchar(500)                          null comment 'User Agent',
    request_data  json                                  null comment '请求数据',
    response_data json                                  null comment '响应数据',
    status        varchar(20) default 'success'         null comment '操作状态（success/failed）',
    created_at    datetime    default CURRENT_TIMESTAMP null
)
    comment '操作审计日志';

create index idx_action
    on audit_logs (action);

create index idx_admin_id
    on audit_logs (admin_id);

create index idx_created_at
    on audit_logs (created_at);

create index idx_user_id
    on audit_logs (user_id);

create table countries
(
    id                bigint unsigned auto_increment
        primary key,
    code              char(2)                                    not null comment '国家代码 ISO 3166-1 alpha-2 (US/UK/CA)',
    name_zh           varchar(50)                                not null comment '中文名称',
    name_en           varchar(50)                                not null comment '英文名称',
    flag_emoji        varchar(10)                                null comment '国旗emoji ??',
    overview          json                                       null comment '国家概览：{"description":"...","advantages":[],"disadvantages":[]}',
    study_info        json                                       null comment '留学信息：{"education_system":"...","application_process":[],"requirements":{}}',
    work_info         json                                       null comment '工作信息：{"visa_types":[],"job_market":"...","salary_range":{}}',
    immigration_info  json                                       null comment '移民信息：{"types":[],"requirements":{},"timeline":"..."}',
    living_info       json                                       null comment '生活信息：{"climate":"...","cost_of_living":{},"safety_index":8}',
    avg_tuition_min   decimal(10, 2)                             null comment '学费最低（年，单位：人民币）',
    avg_tuition_max   decimal(10, 2)                             null comment '学费最高（年）',
    avg_living_cost   decimal(10, 2)                             null comment '平均生活费（年）',
    difficulty_rating tinyint unsigned default '5'               null comment '申请难度 1-10',
    popularity_score  int unsigned     default '0'               null comment '热度分数',
    is_active         tinyint(1)       default 1                 null comment '是否启用',
    is_featured       tinyint(1)       default 0                 null comment '是否推荐',
    sort_order        int              default 0                 null comment '排序权重',
    plan_count        int unsigned     default '0'               null comment '规划数量',
    view_count        int unsigned     default '0'               null comment '浏览次数',
    created_at        datetime         default CURRENT_TIMESTAMP null,
    updated_at        datetime         default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP,
    constraint uk_code
        unique (code)
)
    comment '国家信息表';

create index idx_is_active
    on countries (is_active);

create index idx_sort_popularity
    on countries (sort_order, popularity_score);

create table country_policies
(
    id             bigint unsigned auto_increment
        primary key,
    country_code   char(2)                                                                 not null comment '国家代码',
    policy_type    enum ('VISA', 'IMMIGRATION', 'WORK', 'STUDY') default 'VISA'            not null,
    title          varchar(200)                                                            not null comment '政策标题',
    content        text                                                                    not null comment '政策内容（Markdown）',
    effective_date date                                                                    null comment '生效日期',
    source_url     varchar(500)                                                            null comment '来源链接',
    is_important   tinyint(1)                                    default 0                 null comment '是否重要更新',
    status         enum ('ACTIVE', 'INACTIVE', 'EXPIRED')        default 'ACTIVE'          null,
    created_at     datetime                                      default CURRENT_TIMESTAMP null,
    updated_at     datetime                                      default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP,
    deleted        tinyint(1)                                    default 0                 null comment '逻辑删除标记',
    version_id     int unsigned                                  default '0'               null comment '乐观锁版本号',
    constraint country_policies_ibfk_1
        foreign key (country_code) references countries (code)
            on delete cascade
)
    comment '国家政策更新';

create index idx_country_type
    on country_policies (country_code, policy_type);

create index idx_deleted
    on country_policies (deleted);

create index idx_effective_date
    on country_policies (effective_date);

create table system_configs
(
    id           bigint unsigned auto_increment
        primary key,
    config_key   varchar(100)                                                           not null comment '配置键',
    config_value text                                                                   not null comment '配置值',
    value_type   enum ('STRING', 'NUMBER', 'BOOLEAN', 'JSON') default 'STRING'          not null,
    description  varchar(500)                                                           null comment '配置说明',
    is_public    tinyint(1)                                   default 0                 null comment '是否公开（前端可访问）',
    updated_at   datetime                                     default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP,
    created_at   datetime                                     default CURRENT_TIMESTAMP null,
    deleted      tinyint(1)                                   default 0                 null comment '逻辑删除标记',
    version_id   int unsigned                                 default '0'               null comment '乐观锁版本号',
    constraint uk_config_key
        unique (config_key)
)
    comment '系统配置表';

create index idx_deleted
    on system_configs (deleted);

create table tags
(
    id          bigint unsigned auto_increment
        primary key,
    name        varchar(50)                            not null comment '标签名称',
    slug        varchar(50)                            not null comment 'URL友好标识',
    description varchar(200)                           null comment '标签描述',
    color       varchar(20)                            null comment '标签颜色（HEX）',
    post_count  int unsigned default '0'               null comment '帖子数（冗余）',
    created_at  datetime     default CURRENT_TIMESTAMP null,
    updated_at  datetime     default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP,
    deleted     tinyint(1)   default 0                 null comment '逻辑删除标记',
    version_id  int unsigned default '0'               null comment '乐观锁版本号',
    constraint uk_name
        unique (name),
    constraint uk_slug
        unique (slug)
)
    comment '标签字典表';

create index idx_deleted
    on tags (deleted);

create index idx_post_count
    on tags (post_count desc);

create table users
(
    id              bigint unsigned auto_increment comment '用户ID'
        primary key,
    username        varchar(50)                                                                not null comment '用户名',
    email           varchar(100)                                                               null comment '邮箱（唯一，可选）',
    phone           varchar(20)                                                                null comment '手机号',
    password_hash   varchar(255)                                                               not null comment '密码哈希（bcrypt）',
    nickname        varchar(50)                                                                null comment '昵称',
    avatar_url      varchar(500)                                                               null comment '头像URL',
    bio             varchar(500)                                                               null comment '个人简介',
    gender          enum ('MALE', 'FEMALE', 'OTHER', 'PREFER_NOT_TO_SAY')                      null,
    level           tinyint unsigned                                 default '1'               null comment '用户等级 1-10',
    points          int unsigned                                     default '0'               null comment '积分',
    exp             int unsigned                                     default '0'               null comment '经验值',
    post_count      int unsigned                                     default '0'               null comment '发帖数',
    follower_count  int unsigned                                     default '0'               null comment '粉丝数',
    following_count int unsigned                                     default '0'               null comment '关注数',
    status          enum ('ACTIVE', 'INACTIVE', 'BANNED', 'DELETED') default 'ACTIVE'          not null,
    email_verified  tinyint(1)                                       default 0                 null comment '邮箱是否验证',
    phone_verified  tinyint(1)                                       default 0                 null comment '手机是否验证',
    is_vip          tinyint(1)                                       default 0                 null comment '是否会员',
    vip_expire_at   datetime                                                                   null comment '会员到期时间',
    last_login_at   datetime                                                                   null comment '最后登录时间',
    last_login_ip   varchar(45)                                                                null comment '最后登录IP',
    created_at      datetime                                         default CURRENT_TIMESTAMP null comment '创建时间',
    updated_at      datetime                                         default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '更新时间',
    deleted         tinyint(1)                                       default 0                 null comment '逻辑删除标记 0=未删除 1=已删除',
    version_id      int unsigned                                     default '0'               null comment '乐观锁版本号',
    constraint uk_email
        unique (email),
    constraint uk_phone
        unique (phone),
    constraint uk_username
        unique (username)
)
    comment '用户主表';

create table cost_calculations
(
    id           bigint unsigned auto_increment
        primary key,
    user_id      bigint unsigned                        null comment '用户ID（可为空，游客也可用）',
    country_code char(2)                                not null comment '国家代码',
    input_params json                                   not null comment '输入参数：{"school_type":"public","region":"west_coast","tuition":40000}',
    result       json                                   not null comment '结果：{"total_per_year":77500,"breakdown":{...}}',
    scheme_name  varchar(100)                           null comment '方案名称（用户自定义）',
    is_saved     tinyint(1)   default 0                 null comment '是否保存',
    created_at   datetime     default CURRENT_TIMESTAMP null,
    deleted      tinyint(1)   default 0                 null comment '逻辑删除标记',
    version_id   int unsigned default '0'               null comment '乐观锁版本号',
    constraint cost_calculations_ibfk_1
        foreign key (user_id) references users (id)
            on delete cascade
)
    comment '费用计算记录';

create index idx_country_code
    on cost_calculations (country_code);

create index idx_deleted
    on cost_calculations (deleted);

create index idx_user_id
    on cost_calculations (user_id);

create table gpa_conversions
(
    id           bigint unsigned auto_increment
        primary key,
    user_id      bigint unsigned                        null comment '用户ID',
    from_system  varchar(50)                            not null comment '源系统（china_4.0/china_100/uk等）',
    to_system    varchar(50)                            not null comment '目标系统',
    input_value  decimal(5, 2)                          not null comment '输入值',
    output_value decimal(5, 2)                          not null comment '输出值',
    created_at   datetime     default CURRENT_TIMESTAMP null,
    deleted      tinyint(1)   default 0                 null comment '逻辑删除标记',
    version_id   int unsigned default '0'               null comment '乐观锁版本号',
    constraint gpa_conversions_ibfk_1
        foreign key (user_id) references users (id)
            on delete cascade
)
    comment 'GPA转换记录';

create index idx_deleted
    on gpa_conversions (deleted);

create index idx_user_id
    on gpa_conversions (user_id);

create table notifications
(
    id           bigint unsigned auto_increment
        primary key,
    user_id      bigint unsigned                                                                                             not null comment '接收者ID',
    sender_id    bigint unsigned                                                                                             null comment '发送者ID（系统通知为NULL）',
    type         enum ('SYSTEM', 'LIKE', 'COMMENT', 'FOLLOW', 'REPLY', 'MENTION', 'POLICY_UPDATE') default 'SYSTEM'          not null,
    title        varchar(200)                                                                                                not null comment '通知标题',
    content      text                                                                                                        not null comment '通知内容',
    related_type varchar(50)                                                                                                 null comment '关联对象类型（post/comment/plan）',
    related_id   bigint unsigned                                                                                             null comment '关联对象ID',
    is_read      tinyint(1)                                                                        default 0                 null comment '是否已读',
    read_at      datetime                                                                                                    null comment '阅读时间',
    created_at   datetime                                                                          default CURRENT_TIMESTAMP null,
    deleted      tinyint(1)                                                                        default 0                 null comment '逻辑删除标记',
    version_id   int unsigned                                                                      default '0'               null comment '乐观锁版本号',
    constraint notifications_ibfk_1
        foreign key (user_id) references users (id)
            on delete cascade,
    constraint notifications_ibfk_2
        foreign key (sender_id) references users (id)
            on delete set null
)
    comment '系统通知表';

create index idx_created_at
    on notifications (created_at desc);

create index idx_deleted
    on notifications (deleted);

create index idx_is_read
    on notifications (is_read);

create index idx_type
    on notifications (type);

create index idx_user_id
    on notifications (user_id);

create index sender_id
    on notifications (sender_id);

create table plans
(
    id               bigint unsigned auto_increment
        primary key,
    user_id          bigint unsigned                                                              not null comment '用户ID',
    country_code     char(2)                                                                      not null comment '目标国家',
    plan_type        enum ('STUDY', 'WORK', 'IMMIGRATION')              default 'STUDY'           not null,
    sub_type         varchar(50)                                                                  null comment '细分类型（bachelor/master/phd/work_visa等）',
    target_date      date                                                                         null comment '计划出发日期',
    title            varchar(200)                                                                 null comment '规划标题（如"2026年美国CS硕士申请"）',
    current_status   json                                                                         null comment '用户当前状态：{"education":"bachelor","gpa":3.5,"toefl":100}',
    progress         tinyint unsigned                                   default '0'               null comment '整体进度 0-100',
    current_stage    varchar(50)                                                                  null comment '当前所处阶段名称',
    total_stages     tinyint unsigned                                   default '0'               null comment '总阶段数',
    completed_stages tinyint unsigned                                   default '0'               null comment '已完成阶段数',
    total_tasks      smallint unsigned                                  default '0'               null comment '总任务数',
    completed_tasks  smallint unsigned                                  default '0'               null comment '已完成任务数',
    status           enum ('ACTIVE', 'PAUSED', 'COMPLETED', 'ARCHIVED') default 'ACTIVE'          not null,
    created_at       datetime                                           default CURRENT_TIMESTAMP null,
    updated_at       datetime                                           default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP,
    completed_at     datetime                                                                     null comment '完成时间',
    deleted          tinyint(1)                                         default 0                 null comment '逻辑删除标记',
    version_id       int unsigned                                       default '0'               null comment '乐观锁版本号',
    constraint plans_ibfk_1
        foreign key (user_id) references users (id)
            on delete cascade,
    constraint plans_ibfk_2
        foreign key (country_code) references countries (code)
)
    comment '用户规划表';

create table material_checklists
(
    id            bigint unsigned auto_increment
        primary key,
    plan_id       bigint unsigned                                                                                  not null comment '规划ID',
    material_name varchar(200)                                                                                     not null comment '材料名称',
    category      enum ('REQUIRED', 'SUPPORTING', 'OPTIONAL')                            default 'REQUIRED'        not null,
    description   text                                                                                             null comment '材料说明',
    requirements  text                                                                                             null comment '要求细节（格式、份数等）',
    example_url   varchar(500)                                                                                     null comment '示例文件URL',
    status        enum ('NOT_STARTED', 'IN_PROGRESS', 'COMPLETED', 'OVERDUE', 'SKIPPED') default 'NOT_STARTED'     not null,
    reminder_date date                                                                                             null comment '提醒日期',
    due_date      date                                                                                             null comment '截止日期',
    file_count    tinyint unsigned                                                       default '0'               null comment '已上传文件数',
    sort_order    smallint unsigned                                                                                not null comment '排序',
    created_at    datetime                                                               default CURRENT_TIMESTAMP null,
    updated_at    datetime                                                               default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP,
    completed_at  datetime                                                                                         null comment '完成时间',
    deleted       tinyint(1)                                                             default 0                 null comment '逻辑删除标记',
    version_id    int unsigned                                                           default '0'               null comment '乐观锁版本号',
    constraint material_checklists_ibfk_1
        foreign key (plan_id) references plans (id)
            on delete cascade
)
    comment '材料清单表';

create index idx_category
    on material_checklists (category);

create index idx_deleted
    on material_checklists (deleted);

create index idx_plan_id
    on material_checklists (plan_id);

create index idx_status
    on material_checklists (status);

create table material_files
(
    id           bigint unsigned auto_increment
        primary key,
    checklist_id bigint unsigned                        not null comment '材料清单ID',
    user_id      bigint unsigned                        not null comment '用户ID',
    file_name    varchar(255)                           not null comment '文件名',
    file_url     varchar(500)                           not null comment '文件URL（OSS）',
    file_type    varchar(50)                            null comment '文件类型（pdf/jpg/png）',
    file_size    int unsigned                           null comment '文件大小（字节）',
    uploaded_at  datetime     default CURRENT_TIMESTAMP null comment '上传时间',
    created_at   datetime     default CURRENT_TIMESTAMP null,
    deleted      tinyint(1)   default 0                 null comment '逻辑删除标记',
    version_id   int unsigned default '0'               null comment '乐观锁版本号',
    constraint material_files_ibfk_1
        foreign key (checklist_id) references material_checklists (id)
            on delete cascade,
    constraint material_files_ibfk_2
        foreign key (user_id) references users (id)
            on delete cascade
)
    comment '材料文件记录';

create index idx_checklist_id
    on material_files (checklist_id);

create index idx_deleted
    on material_files (deleted);

create index idx_user_id
    on material_files (user_id);

create table plan_milestones
(
    id             bigint unsigned auto_increment
        primary key,
    plan_id        bigint unsigned                                                   not null comment '规划ID',
    name           varchar(100)                                                      not null comment '里程碑名称',
    description    text                                                              null comment '说明',
    icon           varchar(50)                                                       null comment '图标名称',
    milestone_date date                                                              not null comment '里程碑日期',
    status         enum ('PENDING', 'COMPLETED', 'MISSED') default 'PENDING'         null,
    sort_order     tinyint unsigned                                                  not null comment '排序',
    created_at     datetime                                default CURRENT_TIMESTAMP null,
    updated_at     datetime                                default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP,
    completed_at   datetime                                                          null comment '完成时间',
    deleted        tinyint(1)                              default 0                 null comment '逻辑删除标记',
    version_id     int unsigned                            default '0'               null comment '乐观锁版本号',
    constraint plan_milestones_ibfk_1
        foreign key (plan_id) references plans (id)
            on delete cascade
)
    comment '规划里程碑表';

create index idx_deleted
    on plan_milestones (deleted);

create index idx_milestone_date
    on plan_milestones (milestone_date);

create index idx_plan_id
    on plan_milestones (plan_id);

create table plan_stages
(
    id              bigint unsigned auto_increment
        primary key,
    plan_id         bigint unsigned                                                            not null comment '规划ID',
    name            varchar(100)                                                               not null comment '阶段名称（如"语言考试阶段"）',
    description     text                                                                       null comment '阶段描述',
    start_date      date                                                                       null comment '开始日期',
    end_date        date                                                                       null comment '结束日期',
    duration_days   smallint unsigned                                                          null comment '预计天数',
    status          enum ('NOT_STARTED', 'IN_PROGRESS', 'COMPLETED') default 'NOT_STARTED'     not null,
    progress        tinyint unsigned                                 default '0'               null comment '阶段进度 0-100',
    total_tasks     smallint unsigned                                default '0'               null comment '任务总数',
    completed_tasks smallint unsigned                                default '0'               null comment '已完成任务数',
    sort_order      tinyint unsigned                                                           not null comment '排序顺序',
    created_at      datetime                                         default CURRENT_TIMESTAMP null,
    updated_at      datetime                                         default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP,
    completed_at    datetime                                                                   null comment '完成时间',
    deleted         tinyint(1)                                       default 0                 null comment '逻辑删除标记',
    version_id      int unsigned                                     default '0'               null comment '乐观锁版本号',
    constraint plan_stages_ibfk_1
        foreign key (plan_id) references plans (id)
            on delete cascade
)
    comment '规划阶段表';

create index idx_deleted
    on plan_stages (deleted);

create index idx_plan_id
    on plan_stages (plan_id);

create index idx_sort_order
    on plan_stages (sort_order);

create index idx_status
    on plan_stages (status);

create table plan_tasks
(
    id            bigint unsigned auto_increment
        primary key,
    stage_id      bigint unsigned                                                                                  not null comment '阶段ID',
    plan_id       bigint unsigned                                                                                  not null comment '规划ID（冗余，便于查询）',
    name          varchar(200)                                                                                     not null comment '任务名称',
    description   text                                                                                             null comment '任务详细说明',
    due_date      date                                                                                             null comment '截止日期',
    reminder_date date                                                                                             null comment '提醒日期',
    status        enum ('NOT_STARTED', 'IN_PROGRESS', 'COMPLETED', 'OVERDUE', 'SKIPPED') default 'NOT_STARTED'     not null,
    guide_url     varchar(500)                                                                                     null comment '详细指南链接',
    related_tool  varchar(50)                                                                                      null comment '相关工具（cost_calculator/gpa_converter）',
    sort_order    smallint unsigned                                                                                not null comment '排序顺序',
    created_at    datetime                                                               default CURRENT_TIMESTAMP null,
    updated_at    datetime                                                               default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP,
    completed_at  datetime                                                                                         null comment '完成时间',
    deleted       tinyint(1)                                                             default 0                 null comment '逻辑删除标记',
    version_id    int unsigned                                                           default '0'               null comment '乐观锁版本号',
    constraint plan_tasks_ibfk_1
        foreign key (stage_id) references plan_stages (id)
            on delete cascade,
    constraint plan_tasks_ibfk_2
        foreign key (plan_id) references plans (id)
            on delete cascade
)
    comment '规划任务表';

create index idx_deleted
    on plan_tasks (deleted);

create index idx_due_date
    on plan_tasks (due_date);

create index idx_plan_id
    on plan_tasks (plan_id);

create index idx_stage_id
    on plan_tasks (stage_id);

create index idx_status
    on plan_tasks (status);

create index idx_country_code
    on plans (country_code);

create index idx_deleted
    on plans (deleted);

create index idx_plan_type
    on plans (plan_type);

create index idx_status
    on plans (status);

create index idx_user_id
    on plans (user_id);

create table posts
(
    id            bigint unsigned auto_increment
        primary key,
    author_id     bigint unsigned                                                         not null comment '作者ID',
    title         varchar(200)                                                            not null comment '标题',
    content       mediumtext                                                              not null comment '正文内容（Markdown）',
    summary       varchar(500)                                                            null comment '摘要（自动提取）',
    content_type  enum ('POST', 'QUESTION', 'TIMELINE', 'VLOG') default 'POST'            not null,
    category      varchar(50)                                                             null comment '分区（按国家/阶段/类型）',
    country_code  char(2)                                                                 null comment '关联国家',
    cover_image   varchar(500)                                                            null comment '封面图',
    media_urls    json                                                                    null comment '图片/视频列表 ["url1","url2"]',
    view_count    int unsigned                                  default '0'               null comment '浏览量',
    like_count    int unsigned                                  default '0'               null comment '点赞数',
    comment_count int unsigned                                  default '0'               null comment '评论数',
    collect_count int unsigned                                  default '0'               null comment '收藏数',
    share_count   int unsigned                                  default '0'               null comment '分享数',
    is_featured   tinyint(1)                                    default 0                 null comment '是否精选',
    is_pinned     tinyint(1)                                    default 0                 null comment '是否置顶',
    is_hot        tinyint(1)                                    default 0                 null comment '是否热门',
    status        enum ('DRAFT', 'PUBLISHED', 'DELETED')        default 'PUBLISHED'       not null,
    allow_comment tinyint(1)                                    default 1                 null comment '允许评论',
    slug          varchar(200)                                                            null comment 'URL友好标识',
    created_at    datetime                                      default CURRENT_TIMESTAMP null,
    updated_at    datetime                                      default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP,
    published_at  datetime                                                                null comment '发布时间',
    deleted       tinyint(1)                                    default 0                 null comment '逻辑删除标记',
    version_id    int unsigned                                  default '0'               null comment '乐观锁版本号',
    constraint posts_ibfk_1
        foreign key (author_id) references users (id)
            on delete cascade
)
    comment '帖子表';

create table comments
(
    id          bigint unsigned auto_increment
        primary key,
    post_id     bigint unsigned                                                 not null comment '帖子ID',
    author_id   bigint unsigned                                                 not null comment '评论者ID',
    parent_id   bigint unsigned                                                 null comment '父评论ID（回复用）',
    root_id     bigint unsigned                                                 null comment '根评论ID（方便查询楼层）',
    content     text                                                            not null comment '评论内容',
    like_count  int unsigned                          default '0'               null comment '点赞数',
    reply_count int unsigned                          default '0'               null comment '回复数',
    status      enum ('VISIBLE', 'HIDDEN', 'DELETED') default 'VISIBLE'         not null,
    created_at  datetime                              default CURRENT_TIMESTAMP null,
    updated_at  datetime                              default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP,
    deleted     tinyint(1)                            default 0                 null comment '逻辑删除标记',
    version_id  int unsigned                          default '0'               null comment '乐观锁版本号',
    constraint comments_ibfk_1
        foreign key (post_id) references posts (id)
            on delete cascade,
    constraint comments_ibfk_2
        foreign key (author_id) references users (id)
            on delete cascade,
    constraint comments_ibfk_3
        foreign key (parent_id) references comments (id)
            on delete cascade
)
    comment '评论表';

create index idx_author_id
    on comments (author_id);

create index idx_created_at
    on comments (created_at);

create index idx_deleted
    on comments (deleted);

create index idx_parent_id
    on comments (parent_id);

create index idx_post_id
    on comments (post_id);

create index idx_root_id
    on comments (root_id);

create table post_collections
(
    id         bigint unsigned auto_increment
        primary key,
    post_id    bigint unsigned                        not null comment '帖子ID',
    user_id    bigint unsigned                        not null comment '用户ID',
    folder     varchar(50)  default 'default'         null comment '收藏夹名称',
    created_at datetime     default CURRENT_TIMESTAMP null,
    deleted    tinyint(1)   default 0                 null comment '逻辑删除标记',
    version_id int unsigned default '0'               null comment '乐观锁版本号',
    constraint uk_post_user
        unique (post_id, user_id),
    constraint post_collections_ibfk_1
        foreign key (post_id) references posts (id)
            on delete cascade,
    constraint post_collections_ibfk_2
        foreign key (user_id) references users (id)
            on delete cascade
)
    comment '帖子收藏记录';

create index idx_deleted
    on post_collections (deleted);

create index idx_user_folder
    on post_collections (user_id, folder);

create table post_likes
(
    id         bigint unsigned auto_increment
        primary key,
    post_id    bigint unsigned                        not null comment '帖子ID',
    user_id    bigint unsigned                        not null comment '用户ID',
    created_at datetime     default CURRENT_TIMESTAMP null,
    deleted    tinyint(1)   default 0                 null comment '逻辑删除标记',
    version_id int unsigned default '0'               null comment '乐观锁版本号',
    constraint uk_post_user
        unique (post_id, user_id),
    constraint post_likes_ibfk_1
        foreign key (post_id) references posts (id)
            on delete cascade,
    constraint post_likes_ibfk_2
        foreign key (user_id) references users (id)
            on delete cascade
)
    comment '帖子点赞记录';

create index idx_deleted
    on post_likes (deleted);

create index idx_user_id
    on post_likes (user_id);

create table post_tags
(
    id         bigint unsigned auto_increment
        primary key,
    post_id    bigint unsigned                        not null,
    tag_id     bigint unsigned                        not null,
    created_at datetime     default CURRENT_TIMESTAMP null,
    deleted    tinyint(1)   default 0                 null comment '逻辑删除标记',
    version_id int unsigned default '0'               null comment '乐观锁版本号',
    constraint uk_post_tag
        unique (post_id, tag_id),
    constraint post_tags_ibfk_1
        foreign key (post_id) references posts (id)
            on delete cascade,
    constraint post_tags_ibfk_2
        foreign key (tag_id) references tags (id)
            on delete cascade
)
    comment '帖子标签关联表';

create index idx_deleted
    on post_tags (deleted);

create index idx_tag_id
    on post_tags (tag_id);

create fulltext index ft_title_content
    on posts (title, content);

create index idx_author_id
    on posts (author_id);

create index idx_category
    on posts (category);

create index idx_content_type
    on posts (content_type);

create index idx_country_code
    on posts (country_code);

create index idx_created_at
    on posts (created_at desc);

create index idx_deleted
    on posts (deleted);

create index idx_hot_featured
    on posts (is_hot, is_featured);

create index idx_status
    on posts (status);

create table reminders
(
    id              bigint unsigned auto_increment
        primary key,
    user_id         bigint unsigned                        not null comment '用户ID',
    plan_id         bigint unsigned                        null comment '关联规划ID',
    task_id         bigint unsigned                        null comment '关联任务ID',
    checklist_id    bigint unsigned                        null comment '关联材料清单ID',
    title           varchar(200)                           not null comment '提醒标题',
    content         text                                   null comment '提醒内容',
    remind_time     datetime                               not null comment '提醒时间',
    is_recurring    tinyint(1)   default 0                 null comment '是否重复',
    recurrence_rule varchar(100)                           null comment '重复规则（daily/weekly/monthly）',
    is_sent         tinyint(1)   default 0                 null comment '是否已发送',
    sent_at         datetime                               null comment '发送时间',
    created_at      datetime     default CURRENT_TIMESTAMP null,
    updated_at      datetime     default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP,
    deleted         tinyint(1)   default 0                 null comment '逻辑删除标记',
    version_id      int unsigned default '0'               null comment '乐观锁版本号',
    constraint reminders_ibfk_1
        foreign key (user_id) references users (id)
            on delete cascade,
    constraint reminders_ibfk_2
        foreign key (plan_id) references plans (id)
            on delete cascade
)
    comment '用户提醒表';

create index idx_deleted
    on reminders (deleted);

create index idx_is_sent
    on reminders (is_sent);

create index idx_remind_time
    on reminders (remind_time);

create index idx_user_id
    on reminders (user_id);

create index plan_id
    on reminders (plan_id);

create table user_country_favorites
(
    id           bigint unsigned auto_increment
        primary key,
    user_id      bigint unsigned                        not null,
    country_code char(2)                                not null,
    created_at   datetime     default CURRENT_TIMESTAMP null,
    deleted      tinyint(1)   default 0                 null comment '逻辑删除标记',
    version_id   int unsigned default '0'               null comment '乐观锁版本号',
    constraint uk_user_country
        unique (user_id, country_code),
    constraint user_country_favorites_ibfk_1
        foreign key (user_id) references users (id)
            on delete cascade,
    constraint user_country_favorites_ibfk_2
        foreign key (country_code) references countries (code)
            on delete cascade
)
    comment '用户收藏国家';

create index idx_country_code
    on user_country_favorites (country_code);

create index idx_deleted
    on user_country_favorites (deleted);

create table user_follows
(
    id          bigint unsigned auto_increment
        primary key,
    follower_id bigint unsigned                        not null comment '关注者ID',
    followee_id bigint unsigned                        not null comment '被关注者ID',
    created_at  datetime     default CURRENT_TIMESTAMP null,
    deleted     tinyint(1)   default 0                 null comment '逻辑删除标记',
    version_id  int unsigned default '0'               null comment '乐观锁版本号',
    constraint uk_follower_followee
        unique (follower_id, followee_id),
    constraint user_follows_ibfk_1
        foreign key (follower_id) references users (id)
            on delete cascade,
    constraint user_follows_ibfk_2
        foreign key (followee_id) references users (id)
            on delete cascade
)
    comment '用户关注关系';

create index idx_deleted
    on user_follows (deleted);

create index idx_followee_id
    on user_follows (followee_id);

create table user_oauth_bindings
(
    id                bigint unsigned auto_increment
        primary key,
    user_id           bigint unsigned                                    not null comment '用户ID',
    provider          enum ('wechat', 'qq', 'apple', 'google', 'github') not null comment '第三方平台',
    provider_user_id  varchar(100)                                       not null comment '第三方用户ID（openid/unionid）',
    provider_username varchar(100)                                       null comment '第三方用户名',
    provider_avatar   varchar(500)                                       null comment '第三方头像',
    access_token      text                                               null comment '访问令牌（加密存储）',
    refresh_token     text                                               null comment '刷新令牌',
    expires_at        datetime                                           null comment '令牌过期时间',
    created_at        datetime     default CURRENT_TIMESTAMP             null,
    updated_at        datetime     default CURRENT_TIMESTAMP             null on update CURRENT_TIMESTAMP,
    deleted           tinyint(1)   default 0                             null comment '逻辑删除标记',
    version_id        int unsigned default '0'                           null comment '乐观锁版本号',
    constraint uk_provider_user
        unique (provider, provider_user_id),
    constraint user_oauth_bindings_ibfk_1
        foreign key (user_id) references users (id)
            on delete cascade
)
    comment '第三方登录绑定';

create index idx_deleted
    on user_oauth_bindings (deleted);

create index idx_user_id
    on user_oauth_bindings (user_id);

create table user_preferences
(
    id                    bigint unsigned auto_increment
        primary key,
    user_id               bigint unsigned                                              not null comment '用户ID',
    target_countries      json                                                         null comment '目标国家代码列表 ["US","UK","CA"]',
    target_type           enum ('study', 'work', 'immigration', 'travel', 'undecided') null comment '出国目的',
    target_subtype        varchar(50)                                                  null comment '细分类型（bachelor/master/phd等）',
    time_frame            varchar(50)                                                  null comment '计划时间（within_3_months/within_6_months等）',
    target_departure_date date                                                         null comment '计划出发日期',
    current_stage         varchar(50)                                                  null comment '当前所处阶段',
    notification_enabled  tinyint(1)   default 1                                       null comment '是否接收通知',
    email_notification    tinyint(1)   default 1                                       null comment '是否接收邮件通知',
    push_notification     tinyint(1)   default 1                                       null comment '是否接收推送通知',
    created_at            datetime     default CURRENT_TIMESTAMP                       null,
    updated_at            datetime     default CURRENT_TIMESTAMP                       null on update CURRENT_TIMESTAMP,
    deleted               tinyint(1)   default 0                                       null comment '逻辑删除标记',
    version_id            int unsigned default '0'                                     null comment '乐观锁版本号',
    constraint uk_user_id
        unique (user_id),
    constraint user_preferences_ibfk_1
        foreign key (user_id) references users (id)
            on delete cascade
)
    comment '用户偏好设置';

create index idx_deleted
    on user_preferences (deleted);

create table user_profiles
(
    id               bigint unsigned auto_increment
        primary key,
    user_id          bigint unsigned                                                         not null comment '用户ID',
    education_level  enum ('high_school', 'associate', 'bachelor', 'master', 'phd', 'other') null comment '学历',
    major            varchar(100)                                                            null comment '专业',
    school           varchar(200)                                                            null comment '学校',
    graduation_year  year                                                                    null comment '毕业年份',
    gpa              decimal(4, 2)                                                           null comment 'GPA (如 3.75)',
    toefl_score      smallint unsigned                                                       null comment '托福分数',
    ielts_score      decimal(3, 1)                                                           null comment '雅思分数 (如 7.5)',
    gre_score        smallint unsigned                                                       null comment 'GRE分数',
    gmat_score       smallint unsigned                                                       null comment 'GMAT分数',
    work_years       tinyint unsigned default '0'                                            null comment '工作年限',
    current_company  varchar(200)                                                            null comment '当前公司',
    current_position varchar(100)                                                            null comment '当前职位',
    location         varchar(100)                                                            null comment '所在城市',
    birth_year       year                                                                    null comment '出生年份',
    created_at       datetime         default CURRENT_TIMESTAMP                              null,
    updated_at       datetime         default CURRENT_TIMESTAMP                              null on update CURRENT_TIMESTAMP,
    deleted          tinyint(1)       default 0                                              null comment '逻辑删除标记',
    version_id       int unsigned     default '0'                                            null comment '乐观锁版本号',
    constraint uk_user_id
        unique (user_id),
    constraint user_profiles_ibfk_1
        foreign key (user_id) references users (id)
            on delete cascade
)
    comment '用户详细资料';

create index idx_deleted
    on user_profiles (deleted);

create index idx_created_at
    on users (created_at);

create index idx_deleted
    on users (deleted);

create index idx_level_points
    on users (level, points);

create index idx_status
    on users (status);

create table visa_slots
(
    id              bigint unsigned auto_increment
        primary key,
    country_code    char(2)                                     not null comment '国家代码',
    visa_type       varchar(50)                                 not null comment '签证类型（F1/B1/H1B等）',
    embassy_city    varchar(50)                                 not null comment '使馆城市（beijing/shanghai/guangzhou）',
    earliest_date   date                                        null comment '最早可预约日期',
    available_slots smallint unsigned default '0'               null comment '可用名额数',
    updated_at      datetime          default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '更新时间',
    created_at      datetime          default CURRENT_TIMESTAMP null,
    deleted         tinyint(1)        default 0                 null comment '逻辑删除标记',
    version_id      int unsigned      default '0'               null comment '乐观锁版本号'
)
    comment '签证预约信息';

create index idx_country_type
    on visa_slots (country_code, visa_type);

create index idx_deleted
    on visa_slots (deleted);

create index idx_earliest_date
    on visa_slots (earliest_date);

create index idx_embassy_city
    on visa_slots (embassy_city);
