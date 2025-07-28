-- TB_IMG_BRD_LST 테이블만 삭제하는 DDL
-- 외래키 의존성을 제거한 후 테이블 삭제

-- 1. TB_IMG_FILE_LST 테이블에서 img_brd_seq 외래키 제약조건 제거
ALTER TABLE TB_IMG_FILE_LST DROP CONSTRAINT IF EXISTS fk_img_file_lst_brd_seq;

-- 2. TB_IMG_SEL_LST 테이블에서 img_brd_seq 외래키 제약조건 제거  
ALTER TABLE TB_IMG_SEL_LST DROP CONSTRAINT IF EXISTS fk_img_sel_lst_brd_seq;

-- 3. TB_IMG_BRD_LST 테이블 삭제
DROP TABLE IF EXISTS TB_IMG_BRD_LST CASCADE;

-- 관련 인덱스도 자동으로 삭제됨
-- - idx_img_brd_lst_del_yn

-- 시퀀스도 자동으로 삭제됨 (PostgreSQL에서 테이블 삭제 시 자동 삭제)
-- - TB_IMG_BRD_LST_img_brd_seq_seq 