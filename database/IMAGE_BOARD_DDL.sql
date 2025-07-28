-- 이미지 게시판 테이블
CREATE TABLE TB_IMG_BRD_LST (
    img_brd_seq BIGSERIAL PRIMARY KEY,           -- 이미지 게시판 시퀀스
    img_brd_titl VARCHAR(200) NOT NULL,          -- 이미지 게시판 제목
    img_brd_desc TEXT,                           -- 이미지 게시판 설명
    max_sel_cnt INTEGER DEFAULT 5,               -- 최대 선택 개수 (기본값 5개)
    reg_emp_id VARCHAR(20) NOT NULL,             -- 등록 직원 ID
    upd_emp_id VARCHAR(20),                      -- 수정 직원 ID
    del_yn CHAR(1) DEFAULT 'N',                 -- 삭제 여부
    del_date TIMESTAMP,                          -- 삭제 일시
    reg_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- 등록 일시
    upd_date TIMESTAMP                           -- 수정 일시
);

-- 이미지 파일 테이블 (텍스트 필드 추가)
CREATE TABLE TB_IMG_FILE_LST (
    img_file_seq BIGSERIAL PRIMARY KEY,          -- 이미지 파일 시퀀스
    img_brd_seq BIGINT NOT NULL,                 -- 이미지 게시판 시퀀스
    img_file_nm VARCHAR(255) NOT NULL,           -- 이미지 파일명
    img_file_path VARCHAR(500) NOT NULL,         -- 이미지 파일 경로
    img_file_size BIGINT,                        -- 이미지 파일 크기
    img_file_ext VARCHAR(10),                    -- 이미지 파일 확장자
    img_text TEXT,                               -- 이미지 관련 텍스트
    img_ordr INTEGER DEFAULT 0,                  -- 이미지 순서
    reg_emp_id VARCHAR(20) NOT NULL,             -- 등록 직원 ID
    upd_emp_id VARCHAR(20),                      -- 수정 직원 ID
    del_yn CHAR(1) DEFAULT 'N',                 -- 삭제 여부
    del_date TIMESTAMP,                          -- 삭제 일시
    reg_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- 등록 일시
    upd_date TIMESTAMP                          -- 수정 일시
   
);

-- 사용자 이미지 선택 테이블
CREATE TABLE TB_IMG_SEL_LST (
    img_sel_seq BIGSERIAL PRIMARY KEY,           -- 이미지 선택 시퀀스
    img_brd_seq BIGINT NOT NULL,                 -- 이미지 게시판 시퀀스
    img_file_seq BIGINT NOT NULL,                -- 이미지 파일 시퀀스
    sel_emp_id VARCHAR(20) NOT NULL,             -- 선택한 직원 ID
    sel_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP -- 선택 일시
    
);

-- 인덱스 생성
CREATE INDEX idx_img_brd_lst_del_yn ON TB_IMG_BRD_LST(del_yn);
CREATE INDEX idx_img_file_lst_brd_seq ON TB_IMG_FILE_LST(img_brd_seq);
CREATE INDEX idx_img_file_lst_del_yn ON TB_IMG_FILE_LST(del_yn);
CREATE INDEX idx_img_sel_lst_brd_seq ON TB_IMG_SEL_LST(img_brd_seq);
CREATE INDEX idx_img_sel_lst_emp_id ON TB_IMG_SEL_LST(sel_emp_id);

-- 시퀀스 생성 (PostgreSQL에서는 BIGSERIAL이 자동으로 시퀀스를 생성하므로 별도 생성 불필요) 