-- 이미지 게시판 테이블
CREATE TABLE TB_IMG_BRD_LST (
    IMG_BRD_SEQ BIGSERIAL PRIMARY KEY,           -- 이미지 게시판 시퀀스
    IMG_BRD_TITL VARCHAR(200) NOT NULL,          -- 이미지 게시판 제목
    IMG_BRD_DESC TEXT,                           -- 이미지 게시판 설명
    MAX_SEL_CNT INTEGER DEFAULT 5,               -- 최대 선택 개수 (기본값 5개)
    REG_EMP_ID VARCHAR(20) NOT NULL,             -- 등록 직원 ID
    UPD_EMP_ID VARCHAR(20),                      -- 수정 직원 ID
    DEL_YN CHAR(1) DEFAULT 'N',                 -- 삭제 여부
    DEL_DT TIMESTAMP,                          -- 삭제 일시
    REG_DT TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- 등록 일시
    UPD_DT TIMESTAMP                           -- 수정 일시
);

-- 이미지 파일 테이블 (텍스트 필드 추가)
CREATE TABLE TB_IMG_FILE_LST (
    IMG_FILE_SEQ BIGSERIAL PRIMARY KEY,          -- 이미지 파일 시퀀스
    IMG_BRD_SEQ BIGINT NOT NULL,                 -- 이미지 게시판 시퀀스
    IMG_FILE_NM VARCHAR(255) NOT NULL,           -- 이미지 파일명
    IMG_FILE_PATH VARCHAR(500) NOT NULL,         -- 이미지 파일 경로
    IMG_FILE_SIZE BIGINT,                        -- 이미지 파일 크기
    IMG_FILE_EXT VARCHAR(10),                    -- 이미지 파일 확장자
    IMG_TEXT TEXT,                               -- 이미지 관련 텍스트
    IMG_ORD INTEGER DEFAULT 0,                  -- 이미지 순서
    REG_EMP_ID VARCHAR(20) NOT NULL,             -- 등록 직원 ID
    UPD_EMP_ID VARCHAR(20),                      -- 수정 직원 ID
    DEL_YN CHAR(1) DEFAULT 'N',                 -- 삭제 여부
    DEL_DT TIMESTAMP,                          -- 삭제 일시
    REG_DT TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- 등록 일시
    UPD_DT TIMESTAMP                          -- 수정 일시
   
);

-- 사용자 이미지 선택 테이블
CREATE TABLE TB_IMG_SEL_LST (
    IMG_SEL_SEQ BIGSERIAL PRIMARY KEY,           -- 이미지 선택 시퀀스
    IMG_BRD_SEQ BIGINT NOT NULL,                 -- 이미지 게시판 시퀀스
    IMG_FILE_SEQ BIGINT NOT NULL,                -- 이미지 파일 시퀀스
    SEL_EMP_ID VARCHAR(20) NOT NULL,             -- 선택한 직원 ID
    SEL_DT TIMESTAMP DEFAULT CURRENT_TIMESTAMP -- 선택 일시
    
);

-- 인덱스 생성
CREATE INDEX IDX_IMG_BRD_LST_DEL_YN ON TB_IMG_BRD_LST(DEL_YN);
CREATE INDEX IDX_IMG_FILE_LST_BRD_SEQ ON TB_IMG_FILE_LST(IMG_BRD_SEQ);
CREATE INDEX IDX_IMG_FILE_LST_DEL_YN ON TB_IMG_FILE_LST(DEL_YN);
CREATE INDEX IDX_IMG_SEL_LST_BRD_SEQ ON TB_IMG_SEL_LST(IMG_BRD_SEQ);
CREATE INDEX IDX_IMG_SEL_LST_EMP_ID ON TB_IMG_SEL_LST(SEL_EMP_ID);

-- 테이블 코멘트 추가
COMMENT ON TABLE TB_IMG_BRD_LST IS '이미지 게시판 목록';
COMMENT ON TABLE TB_IMG_FILE_LST IS '이미지 파일 목록';
COMMENT ON TABLE TB_IMG_SEL_LST IS '이미지 선택 목록';

-- 컬럼 코멘트 추가
COMMENT ON COLUMN TB_IMG_BRD_LST.IMG_BRD_SEQ IS '이미지 게시판 시퀀스';
COMMENT ON COLUMN TB_IMG_BRD_LST.IMG_BRD_TITL IS '이미지 게시판 제목';
COMMENT ON COLUMN TB_IMG_BRD_LST.IMG_BRD_DESC IS '이미지 게시판 설명';
COMMENT ON COLUMN TB_IMG_BRD_LST.MAX_SEL_CNT IS '최대 선택 개수';
COMMENT ON COLUMN TB_IMG_BRD_LST.REG_EMP_ID IS '등록 직원 ID';
COMMENT ON COLUMN TB_IMG_BRD_LST.UPD_EMP_ID IS '수정 직원 ID';
COMMENT ON COLUMN TB_IMG_BRD_LST.DEL_YN IS '삭제 여부 (Y/N)';
COMMENT ON COLUMN TB_IMG_BRD_LST.DEL_DT IS '삭제 일시';
COMMENT ON COLUMN TB_IMG_BRD_LST.REG_DT IS '등록 일시';
COMMENT ON COLUMN TB_IMG_BRD_LST.UPD_DT IS '수정 일시';

COMMENT ON COLUMN TB_IMG_FILE_LST.IMG_FILE_SEQ IS '이미지 파일 시퀀스';
COMMENT ON COLUMN TB_IMG_FILE_LST.IMG_BRD_SEQ IS '이미지 게시판 시퀀스';
COMMENT ON COLUMN TB_IMG_FILE_LST.IMG_FILE_NM IS '이미지 파일명';
COMMENT ON COLUMN TB_IMG_FILE_LST.IMG_FILE_PATH IS '이미지 파일 경로';
COMMENT ON COLUMN TB_IMG_FILE_LST.IMG_FILE_SIZE IS '이미지 파일 크기';
COMMENT ON COLUMN TB_IMG_FILE_LST.IMG_FILE_EXT IS '이미지 파일 확장자';
COMMENT ON COLUMN TB_IMG_FILE_LST.IMG_TEXT IS '이미지 관련 텍스트';
COMMENT ON COLUMN TB_IMG_FILE_LST.IMG_ORD IS '이미지 순서';
COMMENT ON COLUMN TB_IMG_FILE_LST.REG_EMP_ID IS '등록 직원 ID';
COMMENT ON COLUMN TB_IMG_FILE_LST.UPD_EMP_ID IS '수정 직원 ID';
COMMENT ON COLUMN TB_IMG_FILE_LST.DEL_YN IS '삭제 여부 (Y/N)';
COMMENT ON COLUMN TB_IMG_FILE_LST.DEL_DT IS '삭제 일시';
COMMENT ON COLUMN TB_IMG_FILE_LST.REG_DT IS '등록 일시';
COMMENT ON COLUMN TB_IMG_FILE_LST.UPD_DT IS '수정 일시';

COMMENT ON COLUMN TB_IMG_SEL_LST.IMG_SEL_SEQ IS '이미지 선택 시퀀스';
COMMENT ON COLUMN TB_IMG_SEL_LST.IMG_BRD_SEQ IS '이미지 게시판 시퀀스';
COMMENT ON COLUMN TB_IMG_SEL_LST.IMG_FILE_SEQ IS '이미지 파일 시퀀스';
COMMENT ON COLUMN TB_IMG_SEL_LST.SEL_EMP_ID IS '선택한 직원 ID';
COMMENT ON COLUMN TB_IMG_SEL_LST.SEL_DT IS '선택 일시';

-- 시퀀스 생성 (PostgreSQL에서는 BIGSERIAL이 자동으로 시퀀스를 생성하므로 별도 생성 불필요) 

-- =====================================================
-- 파일 요약
-- =====================================================
-- 
-- 생성된 테이블:
-- - TB_IMG_BRD_LST: 이미지 게시판 목록 테이블
-- - TB_IMG_FILE_LST: 이미지 파일 목록 테이블  
-- - TB_IMG_SEL_LST: 이미지 선택 목록 테이블
-- 
-- 생성된 인덱스:
-- - IDX_IMG_BRD_LST_DEL_YN: 이미지 게시판 삭제 여부 인덱스
-- - IDX_IMG_FILE_LST_BRD_SEQ: 이미지 파일 게시판 시퀀스 인덱스
-- - IDX_IMG_FILE_LST_DEL_YN: 이미지 파일 삭제 여부 인덱스
-- - IDX_IMG_SEL_LST_BRD_SEQ: 이미지 선택 게시판 시퀀스 인덱스
-- - IDX_IMG_SEL_LST_EMP_ID: 이미지 선택 직원 ID 인덱스
-- 
-- 주요 기능:
-- - 이미지 게시판 생성 및 관리
-- - 이미지 파일 업로드 및 관리
-- - 사용자별 이미지 선택 기능
-- - 이미지 순서 관리
-- - 최대 선택 개수 제한
-- 
-- 축약 규칙 적용:
-- - BOARD -> BRD
-- - FILE -> FILE (이미 축약됨)
-- - ORDER -> ORD
-- - DATE -> DT
-- 
-- 작성일: 2024년
-- 작성자: ERI 시스템 개발팀
-- =====================================================