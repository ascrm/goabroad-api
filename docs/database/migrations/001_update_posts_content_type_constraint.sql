-- ============================================
-- Migration: 更新posts表的content_type约束
-- Date: 2025-11-03
-- Description: 将content_type的CHECK约束从旧值(POST, QUESTION, TIMELINE, VLOG)
--              更新为新值(TREND, QUESTION, ANSWER, GUIDE)以匹配API文档和代码枚举
-- ============================================

-- 1. 删除旧的CHECK约束
ALTER TABLE posts DROP CONSTRAINT IF EXISTS posts_content_type_check;

-- 2. 添加新的CHECK约束
ALTER TABLE posts ADD CONSTRAINT posts_content_type_check 
    CHECK (content_type IN ('TREND', 'QUESTION', 'ANSWER', 'GUIDE'));

-- 3. 更新默认值（如果需要的话，将默认值从POST改为TREND）
ALTER TABLE posts ALTER COLUMN content_type SET DEFAULT 'TREND';

-- 4. 可选：如果已有数据需要迁移，取消下面的注释并根据实际情况调整映射关系
-- UPDATE posts SET content_type = 'TREND' WHERE content_type = 'POST';
-- UPDATE posts SET content_type = 'TREND' WHERE content_type = 'TIMELINE';
-- UPDATE posts SET content_type = 'ANSWER' WHERE content_type = 'VLOG';
-- 注意：QUESTION保持不变

COMMENT ON COLUMN posts.content_type IS '内容类型: TREND(日常生活动态), QUESTION(提问题), ANSWER(写答案), GUIDE(写攻略)';


