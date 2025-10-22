-- ====================================================================
-- 迁移脚本：将 users 表的 email 字段改为可空
-- 版本：V3
-- 日期：2025-10-22
-- 说明：支持手机号注册，email 字段变为可选
-- ====================================================================

-- 1. 修改 email 字段为可空
ALTER TABLE `users` MODIFY COLUMN `email` VARCHAR(100) NULL COMMENT '邮箱（唯一，可选）';

-- 2. 为已有的空邮箱数据清理（如果有的话）
UPDATE `users` SET `email` = NULL WHERE `email` = '';

-- 3. 检查唯一约束（保留唯一约束，但允许 NULL）
-- MySQL 的唯一索引允许多个 NULL 值，所以不需要修改索引

-- 验证修改
SELECT 
    COLUMN_NAME,
    IS_NULLABLE,
    COLUMN_TYPE,
    COLUMN_COMMENT
FROM INFORMATION_SCHEMA.COLUMNS
WHERE TABLE_SCHEMA = 'goabroad_db'
  AND TABLE_NAME = 'users'
  AND COLUMN_NAME = 'email';

