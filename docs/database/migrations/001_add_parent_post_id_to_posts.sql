-- ============================================
-- 迁移脚本：为 posts 表添加 parent_post_id 字段
-- 创建时间: 2024-11-05
-- 目的：支持问答关联功能
-- ============================================

-- 1. 添加 parent_post_id 字段（可为空）
ALTER TABLE posts ADD COLUMN parent_post_id BIGINT;

-- 2. 添加 answer_count 字段（回答数量，仅问题帖子有效）
ALTER TABLE posts ADD COLUMN answer_count INTEGER DEFAULT 0;

-- 3. 添加外键约束
ALTER TABLE posts ADD CONSTRAINT fk_posts_parent 
    FOREIGN KEY (parent_post_id) REFERENCES posts(id) ON DELETE CASCADE;

-- 4. 添加索引，优化查询性能
CREATE INDEX idx_posts_parent_post_id ON posts(parent_post_id);

-- 5. 添加字段注释
COMMENT ON COLUMN posts.parent_post_id IS '父帖子ID（回答帖子关联到问题帖子，问题帖子为NULL）';
COMMENT ON COLUMN posts.answer_count IS '回答数（仅问题帖子有效）';

-- ============================================
-- 回滚脚本（如果需要）
-- ============================================
-- DROP INDEX IF EXISTS idx_posts_parent_post_id;
-- ALTER TABLE posts DROP CONSTRAINT IF EXISTS fk_posts_parent;
-- ALTER TABLE posts DROP COLUMN IF EXISTS answer_count;
-- ALTER TABLE posts DROP COLUMN IF EXISTS parent_post_id;

