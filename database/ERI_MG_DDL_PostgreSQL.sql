-- =================================================================
-- ERI Employee Rights Protection System (Management System) DDL Script
-- PostgreSQL 12+ Compatible
-- =================================================================

-- =================================================================
-- 1. 공지사항 관리 (Notice Management)
-- =================================================================

CREATE TABLE TB_NTI_LST (
    SEQ         BIGSERIAL    NOT NULL,                    -- 일련번호 (PK)
    TTL         VARCHAR(255) NULL,                        -- 제목
    CNTN        TEXT         NULL,                        -- 내용
    STS_CD      VARCHAR(20)  NULL,                        -- 상태코드
    FILE_ATT_YN CHAR(1)      NOT NULL DEFAULT 'N',        -- 첨부파일 존재 여부
    DEL_YN      CHAR(1)      NOT NULL DEFAULT 'N',        -- 삭제 여부 (Y/N)
    DEL_DT      TIMESTAMP    NULL,                        -- 삭제 일시
    REG_EMP_ID  VARCHAR(255) NULL,                        -- 등록 직원 ID
    UPD_EMP_ID  VARCHAR(255) NULL,                        -- 수정 직원 ID
    REG_DT      TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP, -- 등록 일시
    UPD_DT      TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,   -- 수정 일시
    PRIMARY KEY (SEQ)
);
COMMENT ON TABLE TB_NTI_LST IS '공지사항 목록';
COMMENT ON COLUMN TB_NTI_LST.SEQ IS '일련번호';
COMMENT ON COLUMN TB_NTI_LST.TTL IS '제목';
COMMENT ON COLUMN TB_NTI_LST.CNTN IS '내용';
COMMENT ON COLUMN TB_NTI_LST.STS_CD IS '상태코드';
COMMENT ON COLUMN TB_NTI_LST.FILE_ATT_YN IS '첨부파일 존재 여부';
COMMENT ON COLUMN TB_NTI_LST.DEL_YN IS '삭제 여부 (Y/N)';
COMMENT ON COLUMN TB_NTI_LST.DEL_DT IS '삭제 일시';
COMMENT ON COLUMN TB_NTI_LST.REG_EMP_ID IS '등록 직원 ID';
COMMENT ON COLUMN TB_NTI_LST.UPD_EMP_ID IS '수정 직원 ID';
COMMENT ON COLUMN TB_NTI_LST.REG_DT IS '등록 일시';
COMMENT ON COLUMN TB_NTI_LST.UPD_DT IS '수정 일시';
CREATE INDEX IDX_NTI_LST_DEL_YN ON TB_NTI_LST (DEL_YN);

CREATE TABLE TB_NTI_TRSM_RGST_DTL (
    SEQ          BIGINT      NOT NULL,                    -- 일련번호 (PK)
    NTI_SEQ      BIGINT      NULL,                        -- 공지사항 일련번호
    TRSM_MDI_CD  VARCHAR(20) NULL,                        -- 발송매체코드
    DEL_YN       CHAR(1)      NOT NULL DEFAULT 'N',        -- 삭제 여부 (Y/N)
    DEL_DT       TIMESTAMP    NULL,                        -- 삭제 일시
    REG_DT       TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP, -- 등록 일시
    UPD_DT       TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,   -- 수정 일시
    PRIMARY KEY (SEQ)
);
COMMENT ON TABLE TB_NTI_TRSM_RGST_DTL IS '공지사항 발송등록 명세';
COMMENT ON COLUMN TB_NTI_TRSM_RGST_DTL.SEQ IS '일련번호';
COMMENT ON COLUMN TB_NTI_TRSM_RGST_DTL.NTI_SEQ IS '공지사항 일련번호';
COMMENT ON COLUMN TB_NTI_TRSM_RGST_DTL.TRSM_MDI_CD IS '발송매체코드';
COMMENT ON COLUMN TB_NTI_TRSM_RGST_DTL.DEL_YN IS '삭제 여부 (Y/N)';
COMMENT ON COLUMN TB_NTI_TRSM_RGST_DTL.DEL_DT IS '삭제 일시';
COMMENT ON COLUMN TB_NTI_TRSM_RGST_DTL.REG_DT IS '등록 일시';
COMMENT ON COLUMN TB_NTI_TRSM_RGST_DTL.UPD_DT IS '수정 일시';
CREATE INDEX IDX_NTI_TRSM_RGST_DTL_DEL_YN ON TB_NTI_TRSM_RGST_DTL (DEL_YN);

CREATE TABLE TB_NTI_TRSM_TRGT_DTL (
    SEQ          BIGINT      NOT NULL,                    -- 일련번호 (PK)
    EMP_ID       VARCHAR(255) NOT NULL,                   -- 직원 ID (PK)
    DEL_YN       CHAR(1)      NOT NULL DEFAULT 'N',        -- 삭제 여부 (Y/N)
    DEL_DT       TIMESTAMP    NULL,                        -- 삭제 일시
    REG_DT       TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP, -- 등록 일시
    UPD_DT       TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,   -- 수정 일시
    PRIMARY KEY (SEQ, EMP_ID)
);
COMMENT ON TABLE TB_NTI_TRSM_TRGT_DTL IS '공지사항 발송대상자 명세';
COMMENT ON COLUMN TB_NTI_TRSM_TRGT_DTL.SEQ IS '일련번호';
COMMENT ON COLUMN TB_NTI_TRSM_TRGT_DTL.EMP_ID IS '직원 ID';
COMMENT ON COLUMN TB_NTI_TRSM_TRGT_DTL.DEL_YN IS '삭제 여부 (Y/N)';
COMMENT ON COLUMN TB_NTI_TRSM_TRGT_DTL.DEL_DT IS '삭제 일시';
COMMENT ON COLUMN TB_NTI_TRSM_TRGT_DTL.REG_DT IS '등록 일시';
COMMENT ON COLUMN TB_NTI_TRSM_TRGT_DTL.UPD_DT IS '수정 일시';
CREATE INDEX IDX_NTI_TRSM_TRGT_DTL_DEL_YN ON TB_NTI_TRSM_TRGT_DTL (DEL_YN);

-- =================================================================
-- 2. 프로그램 관리 (Program Management)
-- =================================================================

CREATE TABLE TB_PGM_LST (
    SEQ             BIGSERIAL    NOT NULL,                    -- 일련번호 (PK)
    PGM_ID          VARCHAR(20)  NULL,                        -- 프로그램 ID
    PGM_NM          VARCHAR(100) NULL,                        -- 프로그램명
    PGM_DESC        TEXT         NULL,                        -- 프로그램 설명
    PGM_TY_CD       VARCHAR(20)  NULL,                        -- 프로그램 유형코드
    PGM_APP_UNIT_CD VARCHAR(20)  NULL,                        -- 프로그램 신청단위코드
    PGM_STT_DT      DATE         NULL,                        -- 프로그램 시작일자
    PGM_END_DT      DATE         NULL,                        -- 프로그램 종료일자
    PGM_STT_TM      TIME         NULL,                        -- 프로그램 시작시간
    PGM_END_TM      TIME         NULL,                        -- 프로그램 종료시간
    PGM_PLCE        VARCHAR(100) NULL,                        -- 프로그램 장소
    PGM_CNT         INTEGER      NULL,                        -- 프로그램 인원수
    PGM_CNT_LMT     INTEGER      NULL,                        -- 프로그램 인원제한
    PGM_STS_CD      VARCHAR(20)  NULL,                        -- 프로그램 상태코드
    FILE_ATT_YN     CHAR(1)      NOT NULL DEFAULT 'N',        -- 첨부파일 존재 여부
    DEL_YN          CHAR(1)      NOT NULL DEFAULT 'N',        -- 삭제 여부 (Y/N)
    DEL_DT          TIMESTAMP    NULL,                        -- 삭제 일시
    REG_EMP_ID      VARCHAR(255) NULL,                        -- 등록 직원 ID
    UPD_EMP_ID      VARCHAR(255) NULL,                        -- 수정 직원 ID
    REG_DT          TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP, -- 등록 일시
    UPD_DT          TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,   -- 수정 일시
    PRIMARY KEY (SEQ)
);
COMMENT ON TABLE TB_PGM_LST IS '프로그램 목록';
COMMENT ON COLUMN TB_PGM_LST.SEQ IS '일련번호';
COMMENT ON COLUMN TB_PGM_LST.PGM_ID IS '프로그램 ID';
COMMENT ON COLUMN TB_PGM_LST.PGM_NM IS '프로그램명';
COMMENT ON COLUMN TB_PGM_LST.PGM_DESC IS '프로그램 설명';
COMMENT ON COLUMN TB_PGM_LST.PGM_TY_CD IS '프로그램 유형코드';
COMMENT ON COLUMN TB_PGM_LST.PGM_APP_UNIT_CD IS '프로그램 신청단위코드';
COMMENT ON COLUMN TB_PGM_LST.PGM_STT_DT IS '프로그램 시작일자';
COMMENT ON COLUMN TB_PGM_LST.PGM_END_DT IS '프로그램 종료일자';
COMMENT ON COLUMN TB_PGM_LST.PGM_STT_TM IS '프로그램 시작시간';
COMMENT ON COLUMN TB_PGM_LST.PGM_END_TM IS '프로그램 종료시간';
COMMENT ON COLUMN TB_PGM_LST.PGM_PLCE IS '프로그램 장소';
COMMENT ON COLUMN TB_PGM_LST.PGM_CNT IS '프로그램 인원수';
COMMENT ON COLUMN TB_PGM_LST.PGM_CNT_LMT IS '프로그램 인원제한';
COMMENT ON COLUMN TB_PGM_LST.PGM_STS_CD IS '프로그램 상태코드';
COMMENT ON COLUMN TB_PGM_LST.FILE_ATT_YN IS '첨부파일 존재 여부';
COMMENT ON COLUMN TB_PGM_LST.DEL_YN IS '삭제 여부 (Y/N)';
COMMENT ON COLUMN TB_PGM_LST.DEL_DT IS '삭제 일시';
COMMENT ON COLUMN TB_PGM_LST.REG_EMP_ID IS '등록 직원 ID';
COMMENT ON COLUMN TB_PGM_LST.UPD_EMP_ID IS '수정 직원 ID';
COMMENT ON COLUMN TB_PGM_LST.REG_DT IS '등록 일시';
COMMENT ON COLUMN TB_PGM_LST.UPD_DT IS '수정 일시';
CREATE INDEX IDX_PGM_LST_DEL_YN ON TB_PGM_LST (DEL_YN);

CREATE TABLE TB_PGM_PRE_ASGN_DTL (
    PGM_ID          VARCHAR(20) NOT NULL,                    -- 프로그램 ID (PK)
    PRE_ASGN_SEQ    BIGINT      NOT NULL,                    -- 사전과제 일련번호 (PK)
    PRE_ASGN_TTL    VARCHAR(255) NULL,                        -- 사전과제 제목
    PRE_ASGN_CNTN   TEXT         NULL,                        -- 사전과제 내용
    PRE_ASGN_DUE_DT DATE         NULL,                        -- 사전과제 마감일자
    PRE_ASGN_DUE_TM TIME         NULL,                        -- 사전과제 마감시간
    DEL_YN          CHAR(1)      NOT NULL DEFAULT 'N',        -- 삭제 여부 (Y/N)
    DEL_DT          TIMESTAMP    NULL,                        -- 삭제 일시
    REG_DT          TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP, -- 등록 일시
    UPD_DT          TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,   -- 수정 일시
    PRIMARY KEY (PGM_ID, PRE_ASGN_SEQ)
);
COMMENT ON TABLE TB_PGM_PRE_ASGN_DTL IS '프로그램 사전과제 명세';
COMMENT ON COLUMN TB_PGM_PRE_ASGN_DTL.PGM_ID IS '프로그램 ID';
COMMENT ON COLUMN TB_PGM_PRE_ASGN_DTL.PRE_ASGN_SEQ IS '사전과제 일련번호';
COMMENT ON COLUMN TB_PGM_PRE_ASGN_DTL.PRE_ASGN_TTL IS '사전과제 제목';
COMMENT ON COLUMN TB_PGM_PRE_ASGN_DTL.PRE_ASGN_CNTN IS '사전과제 내용';
COMMENT ON COLUMN TB_PGM_PRE_ASGN_DTL.PRE_ASGN_DUE_DT IS '사전과제 마감일자';
COMMENT ON COLUMN TB_PGM_PRE_ASGN_DTL.PRE_ASGN_DUE_TM IS '사전과제 마감시간';
COMMENT ON COLUMN TB_PGM_PRE_ASGN_DTL.DEL_YN IS '삭제 여부 (Y/N)';
COMMENT ON COLUMN TB_PGM_PRE_ASGN_DTL.DEL_DT IS '삭제 일시';
COMMENT ON COLUMN TB_PGM_PRE_ASGN_DTL.REG_DT IS '등록 일시';
COMMENT ON COLUMN TB_PGM_PRE_ASGN_DTL.UPD_DT IS '수정 일시';
CREATE INDEX IDX_PGM_PRE_ASGN_DTL_DEL_YN ON TB_PGM_PRE_ASGN_DTL (DEL_YN);

CREATE TABLE TB_PGM_CNTN_TRSM_DTL (
    PGM_ID          VARCHAR(20) NOT NULL,                    -- 프로그램 ID (PK)
    CNTN_SEQ        BIGINT      NOT NULL,                    -- 콘텐츠 일련번호 (PK)
    CNTN_TTL        VARCHAR(255) NULL,                        -- 콘텐츠 제목
    CNTN_CNTN       TEXT        NULL,                        -- 콘텐츠 내용
    CNTN_TRSM_DT    DATE        NULL,                        -- 콘텐츠 발송일자
    CNTN_TRSM_TM    TIME        NULL,                        -- 콘텐츠 발송시간
    REG_EMP_ID      VARCHAR(255) NULL,                        -- 등록 직원 ID
    UPD_EMP_ID      VARCHAR(255) NULL,                        -- 수정 직원 ID
    DEL_YN          CHAR(1)      NOT NULL DEFAULT 'N',        -- 삭제 여부 (Y/N)
    DEL_DT          TIMESTAMP    NULL,                        -- 삭제 일시
    REG_DT          TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP, -- 등록 일시
    UPD_DT          TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,   -- 수정 일시
    PRIMARY KEY (PGM_ID, CNTN_SEQ)
);
COMMENT ON TABLE TB_PGM_CNTN_TRSM_DTL IS '프로그램 콘텐츠 발송 명세';
COMMENT ON COLUMN TB_PGM_CNTN_TRSM_DTL.PGM_ID IS '프로그램 ID';
COMMENT ON COLUMN TB_PGM_CNTN_TRSM_DTL.CNTN_SEQ IS '콘텐츠 일련번호';
COMMENT ON COLUMN TB_PGM_CNTN_TRSM_DTL.CNTN_TTL IS '콘텐츠 제목';
COMMENT ON COLUMN TB_PGM_CNTN_TRSM_DTL.CNTN_CNTN IS '콘텐츠 내용';
COMMENT ON COLUMN TB_PGM_CNTN_TRSM_DTL.CNTN_TRSM_DT IS '콘텐츠 발송일자';
COMMENT ON COLUMN TB_PGM_CNTN_TRSM_DTL.CNTN_TRSM_TM IS '콘텐츠 발송시간';
COMMENT ON COLUMN TB_PGM_CNTN_TRSM_DTL.REG_EMP_ID IS '등록 직원 ID';
COMMENT ON COLUMN TB_PGM_CNTN_TRSM_DTL.UPD_EMP_ID IS '수정 직원 ID';
COMMENT ON COLUMN TB_PGM_CNTN_TRSM_DTL.DEL_YN IS '삭제 여부 (Y/N)';
COMMENT ON COLUMN TB_PGM_CNTN_TRSM_DTL.DEL_DT IS '삭제 일시';
COMMENT ON COLUMN TB_PGM_CNTN_TRSM_DTL.REG_DT IS '등록 일시';
COMMENT ON COLUMN TB_PGM_CNTN_TRSM_DTL.UPD_DT IS '수정 일시';
CREATE INDEX IDX_PGM_CNTN_TRSM_DTL_DEL_YN ON TB_PGM_CNTN_TRSM_DTL (DEL_YN);

CREATE TABLE TB_CNTN_TRSM_TRGT_DTL (
    PGM_ID          VARCHAR(20) NOT NULL,                    -- 프로그램 ID (PK)
    CNTN_SEQ        BIGINT      NOT NULL,                    -- 콘텐츠 일련번호 (PK)
    EMP_ID          VARCHAR(255) NOT NULL,                    -- 직원 ID (PK)
    DEL_YN          CHAR(1)      NOT NULL DEFAULT 'N',        -- 삭제 여부 (Y/N)
    DEL_DT          TIMESTAMP    NULL,                        -- 삭제 일시
    REG_DT          TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP, -- 등록 일시
    UPD_DT          TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,   -- 수정 일시
    PRIMARY KEY (PGM_ID, CNTN_SEQ, EMP_ID)
);
COMMENT ON TABLE TB_CNTN_TRSM_TRGT_DTL IS '콘텐츠 발송대상자 명세';
COMMENT ON COLUMN TB_CNTN_TRSM_TRGT_DTL.PGM_ID IS '프로그램 ID';
COMMENT ON COLUMN TB_CNTN_TRSM_TRGT_DTL.CNTN_SEQ IS '콘텐츠 일련번호';
COMMENT ON COLUMN TB_CNTN_TRSM_TRGT_DTL.EMP_ID IS '직원 ID';
COMMENT ON COLUMN TB_CNTN_TRSM_TRGT_DTL.DEL_YN IS '삭제 여부 (Y/N)';
COMMENT ON COLUMN TB_CNTN_TRSM_TRGT_DTL.DEL_DT IS '삭제 일시';
COMMENT ON COLUMN TB_CNTN_TRSM_TRGT_DTL.REG_DT IS '등록 일시';
COMMENT ON COLUMN TB_CNTN_TRSM_TRGT_DTL.UPD_DT IS '수정 일시';
CREATE INDEX IDX_CNTN_TRSM_TRGT_DTL_DEL_YN ON TB_CNTN_TRSM_TRGT_DTL (DEL_YN);

-- =================================================================
-- 3. 상담관리시스템 (Counseling Management System)
-- =================================================================

CREATE TABLE TB_GNRL_CNSL (
    APP_DT          DATE        NOT NULL,                     -- 신청일자 (PK)
    CNSL_APP_SEQ    BIGINT      NOT NULL,                    -- 상담신청 일련번호 (PK)
    CNSL_SEQ        BIGINT      NOT NULL,                    -- 상담 일련번호 (PK)
    CNSL_TY_CD      VARCHAR(20) NULL,                        -- 상담 유형코드
    CNSL_LCTN_CD    VARCHAR(20) NULL,                        -- 상담 위치코드
    CNSL_DT         DATE        NULL,                        -- 상담일자
    CNSL_TM         TIME        NULL,                        -- 상담시간
    CNSL_DUR        INTEGER     NULL,                        -- 상담시간(분)
    CNSL_CNTN       TEXT        NULL,                        -- 상담내용
    CNSL_STS_CD     VARCHAR(20) NULL,                        -- 상담상태코드
    FILE_ATT_YN     CHAR(1)      NOT NULL DEFAULT 'N',        -- 첨부파일 존재 여부
    DEL_YN          CHAR(1)      NOT NULL DEFAULT 'N',        -- 삭제 여부 (Y/N)
    DEL_DT          TIMESTAMP    NULL,                        -- 삭제 일시
    REG_EMP_ID      VARCHAR(255) NULL,                        -- 등록 직원 ID
    UPD_EMP_ID      VARCHAR(255) NULL,                        -- 수정 직원 ID
    REG_DT          TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP, -- 등록 일시
    UPD_DT          TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,   -- 수정 일시
    PRIMARY KEY (APP_DT, CNSL_APP_SEQ, CNSL_SEQ)
);
COMMENT ON TABLE TB_GNRL_CNSL IS '일반 상담';
COMMENT ON COLUMN TB_GNRL_CNSL.APP_DT IS '신청일자';
COMMENT ON COLUMN TB_GNRL_CNSL.CNSL_APP_SEQ IS '상담신청 일련번호';
COMMENT ON COLUMN TB_GNRL_CNSL.CNSL_SEQ IS '상담 일련번호';
COMMENT ON COLUMN TB_GNRL_CNSL.CNSL_TY_CD IS '상담 유형코드';
COMMENT ON COLUMN TB_GNRL_CNSL.CNSL_LCTN_CD IS '상담 위치코드';
COMMENT ON COLUMN TB_GNRL_CNSL.CNSL_DT IS '상담일자';
COMMENT ON COLUMN TB_GNRL_CNSL.CNSL_TM IS '상담시간';
COMMENT ON COLUMN TB_GNRL_CNSL.CNSL_DUR IS '상담시간(분)';
COMMENT ON COLUMN TB_GNRL_CNSL.CNSL_CNTN IS '상담내용';
COMMENT ON COLUMN TB_GNRL_CNSL.CNSL_STS_CD IS '상담상태코드';
COMMENT ON COLUMN TB_GNRL_CNSL.FILE_ATT_YN IS '첨부파일 존재 여부';
COMMENT ON COLUMN TB_GNRL_CNSL.DEL_YN IS '삭제 여부 (Y/N)';
COMMENT ON COLUMN TB_GNRL_CNSL.DEL_DT IS '삭제 일시';
COMMENT ON COLUMN TB_GNRL_CNSL.REG_EMP_ID IS '등록 직원 ID';
COMMENT ON COLUMN TB_GNRL_CNSL.UPD_EMP_ID IS '수정 직원 ID';
COMMENT ON COLUMN TB_GNRL_CNSL.REG_DT IS '등록 일시';
COMMENT ON COLUMN TB_GNRL_CNSL.UPD_DT IS '수정 일시';
CREATE INDEX IDX_GNRL_CNSL_DEL_YN ON TB_GNRL_CNSL (DEL_YN);

CREATE TABLE TB_GNRL_CNSL_DTL (
    APP_DT          DATE        NOT NULL,                     -- 신청일자 (PK)
    CNSL_APP_SEQ    BIGINT      NOT NULL,                    -- 상담신청 일련번호 (PK)
    CNSL_SEQ        BIGINT      NOT NULL,                    -- 상담 일련번호 (PK)
    DTL_SEQ         BIGINT      NOT NULL,                    -- 상세 일련번호 (PK)
    CNSL_DTL_CNTN   TEXT        NULL,                        -- 상담 상세내용
    CNSL_DTL_RCMD   TEXT        NULL,                        -- 상담 상세권고사항
    DEL_YN          CHAR(1)      NOT NULL DEFAULT 'N',        -- 삭제 여부 (Y/N)
    DEL_DT          TIMESTAMP    NULL,                        -- 삭제 일시
    REG_DT          TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP, -- 등록 일시
    UPD_DT          TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,   -- 수정 일시
    PRIMARY KEY (APP_DT, CNSL_APP_SEQ, CNSL_SEQ, DTL_SEQ)
);
COMMENT ON TABLE TB_GNRL_CNSL_DTL IS '일반 상담 명세';
COMMENT ON COLUMN TB_GNRL_CNSL_DTL.APP_DT IS '신청일자';
COMMENT ON COLUMN TB_GNRL_CNSL_DTL.CNSL_APP_SEQ IS '상담신청 일련번호';
COMMENT ON COLUMN TB_GNRL_CNSL_DTL.CNSL_SEQ IS '상담 일련번호';
COMMENT ON COLUMN TB_GNRL_CNSL_DTL.DTL_SEQ IS '상세 일련번호';
COMMENT ON COLUMN TB_GNRL_CNSL_DTL.CNSL_DTL_CNTN IS '상담 상세내용';
COMMENT ON COLUMN TB_GNRL_CNSL_DTL.CNSL_DTL_RCMD IS '상담 상세권고사항';
COMMENT ON COLUMN TB_GNRL_CNSL_DTL.DEL_YN IS '삭제 여부 (Y/N)';
COMMENT ON COLUMN TB_GNRL_CNSL_DTL.DEL_DT IS '삭제 일시';
COMMENT ON COLUMN TB_GNRL_CNSL_DTL.REG_DT IS '등록 일시';
COMMENT ON COLUMN TB_GNRL_CNSL_DTL.UPD_DT IS '수정 일시';
CREATE INDEX IDX_GNRL_CNSL_DTL_DEL_YN ON TB_GNRL_CNSL_DTL (DEL_YN);

CREATE TABLE TB_DSRD_PSY_TEST_DTL (
    APP_DT          DATE        NOT NULL,                     -- 신청일자 (PK)
    CNSL_APP_SEQ    BIGINT      NOT NULL,                    -- 상담신청 일련번호 (PK)
    CNSL_SEQ        BIGINT      NOT NULL,                    -- 상담 일련번호 (PK)
    PSY_TEST_CD     VARCHAR(20) NOT NULL,                    -- 심리검사 코드 (PK)
    PSY_TEST_NM     VARCHAR(100) NULL,                        -- 심리검사명
    PSY_TEST_DESC   TEXT        NULL,                        -- 심리검사 설명
    DEL_YN          CHAR(1)      NOT NULL DEFAULT 'N',        -- 삭제 여부 (Y/N)
    DEL_DT          TIMESTAMP    NULL,                        -- 삭제 일시
    REG_DT          TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP, -- 등록 일시
    UPD_DT          TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,   -- 수정 일시
    PRIMARY KEY (APP_DT, CNSL_APP_SEQ, CNSL_SEQ, PSY_TEST_CD)
);
COMMENT ON TABLE TB_DSRD_PSY_TEST_DTL IS '희망 심리검사 명세';
COMMENT ON COLUMN TB_DSRD_PSY_TEST_DTL.APP_DT IS '신청일자';
COMMENT ON COLUMN TB_DSRD_PSY_TEST_DTL.CNSL_APP_SEQ IS '상담신청 일련번호';
COMMENT ON COLUMN TB_DSRD_PSY_TEST_DTL.CNSL_SEQ IS '상담 일련번호';
COMMENT ON COLUMN TB_DSRD_PSY_TEST_DTL.PSY_TEST_CD IS '심리검사 코드';
COMMENT ON COLUMN TB_DSRD_PSY_TEST_DTL.PSY_TEST_NM IS '심리검사명';
COMMENT ON COLUMN TB_DSRD_PSY_TEST_DTL.PSY_TEST_DESC IS '심리검사 설명';
COMMENT ON COLUMN TB_DSRD_PSY_TEST_DTL.DEL_YN IS '삭제 여부 (Y/N)';
COMMENT ON COLUMN TB_DSRD_PSY_TEST_DTL.DEL_DT IS '삭제 일시';
COMMENT ON COLUMN TB_DSRD_PSY_TEST_DTL.REG_DT IS '등록 일시';
COMMENT ON COLUMN TB_DSRD_PSY_TEST_DTL.UPD_DT IS '수정 일시';
CREATE INDEX IDX_DSRD_PSY_TEST_DTL_DEL_YN ON TB_DSRD_PSY_TEST_DTL (DEL_YN);

CREATE TABLE TB_RMT_CNSL (
    APP_DT          DATE        NOT NULL,                     -- 신청일자 (PK)
    CNSL_APP_SEQ    BIGINT      NOT NULL,                    -- 상담신청 일련번호 (PK)
    CMT_SEQ         BIGINT      NOT NULL,                    -- 상담 일련번호 (PK)
    CMT_TY_CD       VARCHAR(20) NULL,                        -- 상담 유형코드
    CMT_DT          DATE        NULL,                        -- 상담일자
    CMT_TM          TIME        NULL,                        -- 상담시간
    CMT_DUR         INTEGER     NULL,                        -- 상담시간(분)
    CMT_CNTN        TEXT        NULL,                        -- 상담내용
    CMT_STS_CD      VARCHAR(20) NULL,                        -- 상담상태코드
    FILE_ATT_YN     CHAR(1)      NOT NULL DEFAULT 'N',        -- 첨부파일 존재 여부
    DEL_YN          CHAR(1)      NOT NULL DEFAULT 'N',        -- 삭제 여부 (Y/N)
    DEL_DT          TIMESTAMP    NULL,                        -- 삭제 일시
    REG_EMP_ID      VARCHAR(255) NULL,                        -- 등록 직원 ID
    UPD_EMP_ID      VARCHAR(255) NULL,                        -- 수정 직원 ID
    REG_DT          TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP, -- 등록 일시
    UPD_DT          TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,   -- 수정 일시
    PRIMARY KEY (APP_DT, CNSL_APP_SEQ, CMT_SEQ)
);
COMMENT ON TABLE TB_RMT_CNSL IS '비대면 상담';
COMMENT ON COLUMN TB_RMT_CNSL.APP_DT IS '신청일자';
COMMENT ON COLUMN TB_RMT_CNSL.CNSL_APP_SEQ IS '상담신청 일련번호';
COMMENT ON COLUMN TB_RMT_CNSL.CMT_SEQ IS '상담 일련번호';
COMMENT ON COLUMN TB_RMT_CNSL.CMT_TY_CD IS '상담 유형코드';
COMMENT ON COLUMN TB_RMT_CNSL.CMT_DT IS '상담일자';
COMMENT ON COLUMN TB_RMT_CNSL.CMT_TM IS '상담시간';
COMMENT ON COLUMN TB_RMT_CNSL.CMT_DUR IS '상담시간(분)';
COMMENT ON COLUMN TB_RMT_CNSL.CMT_CNTN IS '상담내용';
COMMENT ON COLUMN TB_RMT_CNSL.CMT_STS_CD IS '상담상태코드';
COMMENT ON COLUMN TB_RMT_CNSL.FILE_ATT_YN IS '첨부파일 존재 여부';
COMMENT ON COLUMN TB_RMT_CNSL.DEL_YN IS '삭제 여부 (Y/N)';
COMMENT ON COLUMN TB_RMT_CNSL.DEL_DT IS '삭제 일시';
COMMENT ON COLUMN TB_RMT_CNSL.REG_EMP_ID IS '등록 직원 ID';
COMMENT ON COLUMN TB_RMT_CNSL.UPD_EMP_ID IS '수정 직원 ID';
COMMENT ON COLUMN TB_RMT_CNSL.REG_DT IS '등록 일시';
COMMENT ON COLUMN TB_RMT_CNSL.UPD_DT IS '수정 일시';
CREATE INDEX IDX_RMT_CNSL_DEL_YN ON TB_RMT_CNSL (DEL_YN);

CREATE TABLE TB_RMT_CNSL_DTL (
    APP_DT          DATE        NOT NULL,                     -- 신청일자 (PK)
    CNSL_APP_SEQ    BIGINT      NOT NULL,                    -- 상담신청 일련번호 (PK)
    CMT_SEQ         BIGINT      NOT NULL,                    -- 상담 일련번호 (PK)
    DTL_SEQ         BIGINT      NOT NULL,                    -- 상세 일련번호 (PK)
    CMT_DTL_CNTN    TEXT        NULL,                        -- 상담 상세내용
    CMT_DTL_RCMD    TEXT        NULL,                        -- 상담 상세권고사항
    DEL_YN          CHAR(1)      NOT NULL DEFAULT 'N',        -- 삭제 여부 (Y/N)
    DEL_DT          TIMESTAMP    NULL,                        -- 삭제 일시
    REG_DT          TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP, -- 등록 일시
    UPD_DT          TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,   -- 수정 일시
    PRIMARY KEY (APP_DT, CNSL_APP_SEQ, CMT_SEQ, DTL_SEQ)
);
COMMENT ON TABLE TB_RMT_CNSL_DTL IS '비대면 상담 명세';
COMMENT ON COLUMN TB_RMT_CNSL_DTL.APP_DT IS '신청일자';
COMMENT ON COLUMN TB_RMT_CNSL_DTL.CNSL_APP_SEQ IS '상담신청 일련번호';
COMMENT ON COLUMN TB_RMT_CNSL_DTL.CMT_SEQ IS '상담 일련번호';
COMMENT ON COLUMN TB_RMT_CNSL_DTL.DTL_SEQ IS '상세 일련번호';
COMMENT ON COLUMN TB_RMT_CNSL_DTL.CMT_DTL_CNTN IS '상담 상세내용';
COMMENT ON COLUMN TB_RMT_CNSL_DTL.CMT_DTL_RCMD IS '상담 상세권고사항';
COMMENT ON COLUMN TB_RMT_CNSL_DTL.DEL_YN IS '삭제 여부 (Y/N)';
COMMENT ON COLUMN TB_RMT_CNSL_DTL.DEL_DT IS '삭제 일시';
COMMENT ON COLUMN TB_RMT_CNSL_DTL.REG_DT IS '등록 일시';
COMMENT ON COLUMN TB_RMT_CNSL_DTL.UPD_DT IS '수정 일시';
CREATE INDEX IDX_RMT_CNSL_DTL_DEL_YN ON TB_RMT_CNSL_DTL (DEL_YN);

-- =================================================================
-- 3.1. 전문가 상담관리시스템 (Expert Counseling Management System)
-- =================================================================

-- 3.1.1. 전문가 상담 신청 (TB_EXP_CNSL_APP)
-- 직원의 전문가 상담 신청 정보를 관리합니다.
CREATE TABLE TB_EXP_CNSL_APP (
    APP_SEQ         BIGSERIAL    NOT NULL,                -- 신청 일련번호 (PK)
    APP_DT          DATE         NOT NULL,                -- 신청일자
    APP_TM          TIME         NOT NULL,                -- 신청시간
    EMP_ID          VARCHAR(255) NOT NULL,                -- 신청자 직원ID
    CNSL_DT         DATE         NULL,                    -- 상담일자
    CNSL_TM         TIME         NULL,                    -- 상담시간
    CNSLR_EMP_ID    VARCHAR(255) NULL,                    -- 상담사 직원ID
    CNSL_TY_CD      VARCHAR(20)  NULL,                    -- 상담유형코드 (FACE_TO_FACE/REMOTE)
    CNSL_CNTN       TEXT         NULL,                    -- 상담내용
    ANONYMOUS_YN    CHAR(1)      NOT NULL DEFAULT 'N',    -- 익명신청여부
    APRV_STS_CD     VARCHAR(20)  NOT NULL DEFAULT 'PENDING', -- 승인상태코드 (PENDING/APPROVED/REJECTED/COMPLETED)
    APRV_EMP_ID     VARCHAR(255) NULL,                    -- 승인자 직원ID
    APRV_DT         DATE         NULL,                    -- 승인일자
    APRV_TM         TIME         NULL,                    -- 승인시간
    REJ_RSN         TEXT         NULL,                    -- 반려사유
    DEL_YN          CHAR(1)      NOT NULL DEFAULT 'N',    -- 삭제여부
    DEL_DT          TIMESTAMP    NULL,                    -- 삭제일시
    REG_EMP_ID      VARCHAR(255) NULL,                    -- 등록직원ID
    UPD_EMP_ID      VARCHAR(255) NULL,                    -- 수정직원ID
    REG_DT          TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP, -- 등록일시
    UPD_DT          TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,         -- 수정일시
    PRIMARY KEY (APP_SEQ)
);

COMMENT ON TABLE TB_EXP_CNSL_APP IS '전문가 상담 신청';
COMMENT ON COLUMN TB_EXP_CNSL_APP.APP_SEQ IS '신청 일련번호';
COMMENT ON COLUMN TB_EXP_CNSL_APP.APP_DT IS '신청일자';
COMMENT ON COLUMN TB_EXP_CNSL_APP.APP_TM IS '신청시간';
COMMENT ON COLUMN TB_EXP_CNSL_APP.EMP_ID IS '신청자 직원ID';
COMMENT ON COLUMN TB_EXP_CNSL_APP.CNSL_DT IS '상담일자';
COMMENT ON COLUMN TB_EXP_CNSL_APP.CNSL_TM IS '상담시간';
COMMENT ON COLUMN TB_EXP_CNSL_APP.CNSLR_EMP_ID IS '상담사 직원ID';
COMMENT ON COLUMN TB_EXP_CNSL_APP.CNSL_TY_CD IS '상담유형코드';
COMMENT ON COLUMN TB_EXP_CNSL_APP.CNSL_CNTN IS '상담내용';
COMMENT ON COLUMN TB_EXP_CNSL_APP.ANONYMOUS_YN IS '익명신청여부';
COMMENT ON COLUMN TB_EXP_CNSL_APP.APRV_STS_CD IS '승인상태코드';
COMMENT ON COLUMN TB_EXP_CNSL_APP.APRV_EMP_ID IS '승인자 직원ID';
COMMENT ON COLUMN TB_EXP_CNSL_APP.APRV_DT IS '승인일자';
COMMENT ON COLUMN TB_EXP_CNSL_APP.APRV_TM IS '승인시간';
COMMENT ON COLUMN TB_EXP_CNSL_APP.REJ_RSN IS '반려사유';
COMMENT ON COLUMN TB_EXP_CNSL_APP.DEL_YN IS '삭제 여부 (Y/N)';
COMMENT ON COLUMN TB_EXP_CNSL_APP.DEL_DT IS '삭제 일시';
COMMENT ON COLUMN TB_EXP_CNSL_APP.REG_EMP_ID IS '등록 직원 ID';
COMMENT ON COLUMN TB_EXP_CNSL_APP.UPD_EMP_ID IS '수정 직원 ID';
COMMENT ON COLUMN TB_EXP_CNSL_APP.REG_DT IS '등록 일시';
COMMENT ON COLUMN TB_EXP_CNSL_APP.UPD_DT IS '수정 일시';

CREATE INDEX IDX_EXP_CNSL_APP_EMP ON TB_EXP_CNSL_APP (EMP_ID);
CREATE INDEX IDX_EXP_CNSL_APP_CNSLR ON TB_EXP_CNSL_APP (CNSLR_EMP_ID);
CREATE INDEX IDX_EXP_CNSL_APP_APRV_STS ON TB_EXP_CNSL_APP (APRV_STS_CD);
CREATE INDEX IDX_EXP_CNSL_APP_DT_TM ON TB_EXP_CNSL_APP (APP_DT, APP_TM);
CREATE INDEX IDX_EXP_CNSL_APP_DEL_YN ON TB_EXP_CNSL_APP (DEL_YN);
CREATE INDEX IDX_EXP_CNSL_APP_REG_DT ON TB_EXP_CNSL_APP (REG_DT);

-- 3.1.2. 전문가 상담 일지 (TB_EXP_CNSL_DRY)
CREATE TABLE TB_EXP_CNSL_DRY (
    DRY_SEQ       BIGSERIAL    NOT NULL,                -- 일지 일련번호 (PK)
    APP_SEQ         BIGINT       NOT NULL,                -- 신청 일련번호
    CNSL_DT         DATE         NOT NULL,                -- 상담일자
    CNSL_TM         TIME         NOT NULL,                -- 상담시간
    CNSLR_EMP_ID    VARCHAR(255) NOT NULL,                -- 상담사 직원ID
    CNSL_CNTN       TEXT         NOT NULL,                -- 상담내용
    CNSL_RES        TEXT         NULL,                    -- 상담결과
    CNSL_RCMD       TEXT         NULL,                    -- 상담권고사항
    CNSL_DUR        INTEGER      NULL,                    -- 상담시간(분)
    CNSL_STS_CD     VARCHAR(20)  NOT NULL DEFAULT 'COMPLETED', -- 상담상태코드 (SCHEDULED/IN_PROGRESS/COMPLETED/CANCELLED)
    DEL_YN          CHAR(1)      NOT NULL DEFAULT 'N',    -- 삭제여부
    DEL_DT          TIMESTAMP    NULL,                    -- 삭제일시
    REG_EMP_ID      VARCHAR(255) NULL,                    -- 등록직원ID
    UPD_EMP_ID      VARCHAR(255) NULL,                    -- 수정직원ID
    REG_DT          TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP, -- 등록일시
    UPD_DT          TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,         -- 수정일시
    PRIMARY KEY (DRY_SEQ)
);

COMMENT ON TABLE TB_EXP_CNSL_DRY IS '전문가 상담 일지';
COMMENT ON COLUMN TB_EXP_CNSL_DRY.DRY_SEQ IS '일지 일련번호';
COMMENT ON COLUMN TB_EXP_CNSL_DRY.APP_SEQ IS '신청 일련번호';
COMMENT ON COLUMN TB_EXP_CNSL_DRY.CNSL_DT IS '상담일자';
COMMENT ON COLUMN TB_EXP_CNSL_DRY.CNSL_TM IS '상담시간';
COMMENT ON COLUMN TB_EXP_CNSL_DRY.CNSLR_EMP_ID IS '상담사 직원ID';
COMMENT ON COLUMN TB_EXP_CNSL_DRY.CNSL_CNTN IS '상담내용';
COMMENT ON COLUMN TB_EXP_CNSL_DRY.CNSL_RES IS '상담결과';
COMMENT ON COLUMN TB_EXP_CNSL_DRY.CNSL_RCMD IS '상담권고사항';
COMMENT ON COLUMN TB_EXP_CNSL_DRY.CNSL_DUR IS '상담시간(분)';
COMMENT ON COLUMN TB_EXP_CNSL_DRY.CNSL_STS_CD IS '상담상태코드';
COMMENT ON COLUMN TB_EXP_CNSL_DRY.DEL_YN IS '삭제 여부 (Y/N)';
COMMENT ON COLUMN TB_EXP_CNSL_DRY.DEL_DT IS '삭제 일시';
COMMENT ON COLUMN TB_EXP_CNSL_DRY.REG_EMP_ID IS '등록 직원 ID';
COMMENT ON COLUMN TB_EXP_CNSL_DRY.UPD_EMP_ID IS '수정 직원 ID';
COMMENT ON COLUMN TB_EXP_CNSL_DRY.REG_DT IS '등록 일시';
COMMENT ON COLUMN TB_EXP_CNSL_DRY.UPD_DT IS '수정 일시';

CREATE INDEX IDX_EXP_CNSL_DRY_APP ON TB_EXP_CNSL_DRY (APP_SEQ);
CREATE INDEX IDX_EXP_CNSL_DRY_CNSLR ON TB_EXP_CNSL_DRY (CNSLR_EMP_ID);
CREATE INDEX IDX_EXP_CNSL_DRY_DT_TM ON TB_EXP_CNSL_DRY (CNSL_DT, CNSL_TM);
CREATE INDEX IDX_EXP_CNSL_DRY_STS ON TB_EXP_CNSL_DRY (CNSL_STS_CD);
CREATE INDEX IDX_EXP_CNSL_DRY_DEL_YN ON TB_EXP_CNSL_DRY (DEL_YN);
CREATE INDEX IDX_EXP_CNSL_DRY_REG_DT ON TB_EXP_CNSL_DRY (REG_DT);

-- 3.1.3. 상담사 스케줄 (TB_CNSLR_SCHEDULE)
CREATE TABLE TB_CNSLR_SCHEDULE (
    SCH_SEQ         BIGSERIAL    NOT NULL,                -- 스케줄 일련번호 (PK)
    CNSLR_EMP_ID    VARCHAR(255) NOT NULL,                -- 상담사 직원ID
    SCH_DT          DATE         NOT NULL,                -- 스케줄일자
    SCH_TM          TIME         NOT NULL,                -- 스케줄시간
    SCH_TY_CD       VARCHAR(20)  NOT NULL,                -- 스케줄유형코드 (AVAILABLE/UNAVAILABLE/BREAK)
    SCH_CNTN        TEXT         NULL,                    -- 스케줄내용
    DEL_YN          CHAR(1)      NOT NULL DEFAULT 'N',    -- 삭제여부
    DEL_DT          TIMESTAMP    NULL,                    -- 삭제일시
    REG_EMP_ID      VARCHAR(255) NULL,                    -- 등록직원ID
    UPD_EMP_ID      VARCHAR(255) NULL,                    -- 수정직원ID
    REG_DT          TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP, -- 등록일시
    UPD_DT          TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,         -- 수정일시
    PRIMARY KEY (SCH_SEQ)
);

COMMENT ON TABLE TB_CNSLR_SCHEDULE IS '상담사 스케줄';
COMMENT ON COLUMN TB_CNSLR_SCHEDULE.SCH_SEQ IS '스케줄 일련번호';
COMMENT ON COLUMN TB_CNSLR_SCHEDULE.CNSLR_EMP_ID IS '상담사 직원ID';
COMMENT ON COLUMN TB_CNSLR_SCHEDULE.SCH_DT IS '스케줄일자';
COMMENT ON COLUMN TB_CNSLR_SCHEDULE.SCH_TM IS '스케줄시간';
COMMENT ON COLUMN TB_CNSLR_SCHEDULE.SCH_TY_CD IS '스케줄유형코드';
COMMENT ON COLUMN TB_CNSLR_SCHEDULE.SCH_CNTN IS '스케줄내용';
COMMENT ON COLUMN TB_CNSLR_SCHEDULE.DEL_YN IS '삭제 여부 (Y/N)';
COMMENT ON COLUMN TB_CNSLR_SCHEDULE.DEL_DT IS '삭제 일시';
COMMENT ON COLUMN TB_CNSLR_SCHEDULE.REG_EMP_ID IS '등록 직원 ID';
COMMENT ON COLUMN TB_CNSLR_SCHEDULE.UPD_EMP_ID IS '수정 직원 ID';
COMMENT ON COLUMN TB_CNSLR_SCHEDULE.REG_DT IS '등록 일시';
COMMENT ON COLUMN TB_CNSLR_SCHEDULE.UPD_DT IS '수정 일시';

CREATE INDEX IDX_CNSLR_SCHEDULE_CNSLR ON TB_CNSLR_SCHEDULE (CNSLR_EMP_ID);
CREATE INDEX IDX_CNSLR_SCHEDULE_DT_TM ON TB_CNSLR_SCHEDULE (SCH_DT, SCH_TM);
CREATE INDEX IDX_CNSLR_SCHEDULE_TY ON TB_CNSLR_SCHEDULE (SCH_TY_CD);
CREATE INDEX IDX_CNSLR_SCHEDULE_DEL_YN ON TB_CNSLR_SCHEDULE (DEL_YN);
CREATE INDEX IDX_CNSLR_SCHEDULE_REG_DT ON TB_CNSLR_SCHEDULE (REG_DT);

-- 3.1.4. 상담사 배정 관리 (TB_CNSL_ASSIGNMENT)
CREATE TABLE TB_CNSL_ASSIGNMENT (
    ASGN_SEQ        BIGSERIAL    NOT NULL,                -- 배정 일련번호 (PK)
    APP_SEQ         BIGINT       NOT NULL,                -- 신청 일련번호
    CNSLR_EMP_ID    VARCHAR(255) NOT NULL,                -- 상담사 직원ID
    ASGN_DT         DATE         NOT NULL,                -- 배정일자
    ASGN_TM         TIME         NOT NULL,                -- 배정시간
    ASGN_STS_CD     VARCHAR(20)  NOT NULL DEFAULT 'ASSIGNED', -- 배정상태코드 (ASSIGNED/CONFIRMED/CANCELLED)
    ASGN_RSN        TEXT         NULL,                    -- 배정사유
    DEL_YN          CHAR(1)      NOT NULL DEFAULT 'N',    -- 삭제여부
    DEL_DT          TIMESTAMP    NULL,                    -- 삭제일시
    REG_EMP_ID      VARCHAR(255) NULL,                    -- 등록직원ID
    UPD_EMP_ID      VARCHAR(255) NULL,                    -- 수정직원ID
    REG_DT          TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP, -- 등록일시
    UPD_DT          TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,         -- 수정일시
    PRIMARY KEY (ASGN_SEQ)
);

COMMENT ON TABLE TB_CNSL_ASSIGNMENT IS '상담사 배정 관리';
COMMENT ON COLUMN TB_CNSL_ASSIGNMENT.ASGN_SEQ IS '배정 일련번호';
COMMENT ON COLUMN TB_CNSL_ASSIGNMENT.APP_SEQ IS '신청 일련번호';
COMMENT ON COLUMN TB_CNSL_ASSIGNMENT.CNSLR_EMP_ID IS '상담사 직원ID';
COMMENT ON COLUMN TB_CNSL_ASSIGNMENT.ASGN_DT IS '배정일자';
COMMENT ON COLUMN TB_CNSL_ASSIGNMENT.ASGN_TM IS '배정시간';
COMMENT ON COLUMN TB_CNSL_ASSIGNMENT.ASGN_STS_CD IS '배정상태코드';
COMMENT ON COLUMN TB_CNSL_ASSIGNMENT.ASGN_RSN IS '배정사유';
COMMENT ON COLUMN TB_CNSL_ASSIGNMENT.DEL_YN IS '삭제 여부 (Y/N)';
COMMENT ON COLUMN TB_CNSL_ASSIGNMENT.DEL_DT IS '삭제 일시';
COMMENT ON COLUMN TB_CNSL_ASSIGNMENT.REG_EMP_ID IS '등록 직원 ID';
COMMENT ON COLUMN TB_CNSL_ASSIGNMENT.UPD_EMP_ID IS '수정 직원 ID';
COMMENT ON COLUMN TB_CNSL_ASSIGNMENT.REG_DT IS '등록 일시';
COMMENT ON COLUMN TB_CNSL_ASSIGNMENT.UPD_DT IS '수정 일시';

CREATE INDEX IDX_CNSL_ASSIGNMENT_APP ON TB_CNSL_ASSIGNMENT (APP_SEQ);
CREATE INDEX IDX_CNSL_ASSIGNMENT_CNSLR ON TB_CNSL_ASSIGNMENT (CNSLR_EMP_ID);
CREATE INDEX IDX_CNSL_ASSIGNMENT_DT_TM ON TB_CNSL_ASSIGNMENT (ASGN_DT, ASGN_TM);
CREATE INDEX IDX_CNSL_ASSIGNMENT_STS ON TB_CNSL_ASSIGNMENT (ASGN_STS_CD);
CREATE INDEX IDX_CNSL_ASSIGNMENT_DEL_YN ON TB_CNSL_ASSIGNMENT (DEL_YN);
CREATE INDEX IDX_CNSL_ASSIGNMENT_REG_DT ON TB_CNSL_ASSIGNMENT (REG_DT);

-- 3.1.5. 상담 시간대별 제한 설정 (TB_CNSL_TIME_LIMIT)
CREATE TABLE TB_CNSL_TIME_LIMIT (
    LIMIT_SEQ       BIGSERIAL    NOT NULL,                -- 제한 일련번호 (PK)
    CNSL_DT         DATE         NOT NULL,                -- 상담일자
    CNSL_TM         TIME         NOT NULL,                -- 상담시간
    MAX_CNT         INTEGER      NOT NULL DEFAULT 3,      -- 최대신청수 (기본값 3)
    CURRENT_CNT     INTEGER      NOT NULL DEFAULT 0,      -- 현재신청수
    AVAIL_YN        CHAR(1)      NOT NULL DEFAULT 'Y',    -- 신청가능여부
    DEL_YN          CHAR(1)      NOT NULL DEFAULT 'N',    -- 삭제여부
    DEL_DT          TIMESTAMP    NULL,                    -- 삭제일시
    REG_EMP_ID      VARCHAR(255) NULL,                    -- 등록직원ID
    UPD_EMP_ID      VARCHAR(255) NULL,                    -- 수정직원ID
    REG_DT          TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP, -- 등록일시
    UPD_DT          TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,         -- 수정일시
    PRIMARY KEY (LIMIT_SEQ)
);

COMMENT ON TABLE TB_CNSL_TIME_LIMIT IS '상담 시간대별 제한 설정';
COMMENT ON COLUMN TB_CNSL_TIME_LIMIT.LIMIT_SEQ IS '제한 일련번호';
COMMENT ON COLUMN TB_CNSL_TIME_LIMIT.CNSL_DT IS '상담일자';
COMMENT ON COLUMN TB_CNSL_TIME_LIMIT.CNSL_TM IS '상담시간';
COMMENT ON COLUMN TB_CNSL_TIME_LIMIT.MAX_CNT IS '최대신청수';
COMMENT ON COLUMN TB_CNSL_TIME_LIMIT.CURRENT_CNT IS '현재신청수';
COMMENT ON COLUMN TB_CNSL_TIME_LIMIT.AVAIL_YN IS '신청가능여부';
COMMENT ON COLUMN TB_CNSL_TIME_LIMIT.DEL_YN IS '삭제 여부 (Y/N)';
COMMENT ON COLUMN TB_CNSL_TIME_LIMIT.DEL_DT IS '삭제 일시';
COMMENT ON COLUMN TB_CNSL_TIME_LIMIT.REG_EMP_ID IS '등록 직원 ID';
COMMENT ON COLUMN TB_CNSL_TIME_LIMIT.UPD_EMP_ID IS '수정 직원 ID';
COMMENT ON COLUMN TB_CNSL_TIME_LIMIT.REG_DT IS '등록 일시';
COMMENT ON COLUMN TB_CNSL_TIME_LIMIT.UPD_DT IS '수정 일시';

CREATE INDEX IDX_CNSL_TIME_LIMIT_DT_TM ON TB_CNSL_TIME_LIMIT (CNSL_DT, CNSL_TM);
CREATE INDEX IDX_CNSL_TIME_LIMIT_AVAIL ON TB_CNSL_TIME_LIMIT (AVAIL_YN);
CREATE INDEX IDX_CNSL_TIME_LIMIT_DEL_YN ON TB_CNSL_TIME_LIMIT (DEL_YN);
CREATE INDEX IDX_CNSL_TIME_LIMIT_REG_DT ON TB_CNSL_TIME_LIMIT (REG_DT);

-- 3.1.6. 상담사 정보 (TB_CNSLR_INFO)
CREATE TABLE TB_CNSLR_INFO (
    CNSLR_EMP_ID    VARCHAR(255) NOT NULL,                -- 상담사 직원ID (PK)
    CNSLR_NM        VARCHAR(100) NOT NULL,                -- 상담사명
    CNSLR_CLSF_CD   VARCHAR(20)  NOT NULL,                -- 상담사구분코드 (PROF/GEN/INT)
    CNSLR_SPCL_CD   VARCHAR(20)  NULL,                    -- 상담사전문분야코드
    CNSLR_LIC_CD    VARCHAR(20)  NULL,                    -- 상담사자격증코드
    CNSLR_INTRO     TEXT         NULL,                    -- 상담사소개
    CNSLR_IMG_URL   VARCHAR(255) NULL,                    -- 상담사이미지URL
    AVAIL_YN        CHAR(1)      NOT NULL DEFAULT 'Y',    -- 상담가능여부
    DEL_YN          CHAR(1)      NOT NULL DEFAULT 'N',    -- 삭제여부
    DEL_DT          TIMESTAMP    NULL,                    -- 삭제일시
    REG_EMP_ID      VARCHAR(255) NULL,                    -- 등록직원ID
    UPD_EMP_ID      VARCHAR(255) NULL,                    -- 수정직원ID
    REG_DT          TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP, -- 등록일시
    UPD_DT          TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,         -- 수정일시
    PRIMARY KEY (CNSLR_EMP_ID)
);

COMMENT ON TABLE TB_CNSLR_INFO IS '상담사 정보';
COMMENT ON COLUMN TB_CNSLR_INFO.CNSLR_EMP_ID IS '상담사 직원ID';
COMMENT ON COLUMN TB_CNSLR_INFO.CNSLR_NM IS '상담사명';
COMMENT ON COLUMN TB_CNSLR_INFO.CNSLR_CLSF_CD IS '상담사구분코드';
COMMENT ON COLUMN TB_CNSLR_INFO.CNSLR_SPCL_CD IS '상담사전문분야코드';
COMMENT ON COLUMN TB_CNSLR_INFO.CNSLR_LIC_CD IS '상담사자격증코드';
COMMENT ON COLUMN TB_CNSLR_INFO.CNSLR_INTRO IS '상담사소개';
COMMENT ON COLUMN TB_CNSLR_INFO.CNSLR_IMG_URL IS '상담사이미지URL';
COMMENT ON COLUMN TB_CNSLR_INFO.AVAIL_YN IS '상담가능여부';
COMMENT ON COLUMN TB_CNSLR_INFO.DEL_YN IS '삭제 여부 (Y/N)';
COMMENT ON COLUMN TB_CNSLR_INFO.DEL_DT IS '삭제 일시';
COMMENT ON COLUMN TB_CNSLR_INFO.REG_EMP_ID IS '등록 직원 ID';
COMMENT ON COLUMN TB_CNSLR_INFO.UPD_EMP_ID IS '수정 직원 ID';
COMMENT ON COLUMN TB_CNSLR_INFO.REG_DT IS '등록 일시';
COMMENT ON COLUMN TB_CNSLR_INFO.UPD_DT IS '수정 일시';

CREATE INDEX IDX_CNSLR_INFO_CLSF ON TB_CNSLR_INFO (CNSLR_CLSF_CD);
CREATE INDEX IDX_CNSLR_INFO_AVAIL ON TB_CNSLR_INFO (AVAIL_YN);
CREATE INDEX IDX_CNSLR_INFO_DEL_YN ON TB_CNSLR_INFO (DEL_YN);
CREATE INDEX IDX_CNSLR_INFO_REG_DT ON TB_CNSLR_INFO (REG_DT);

-- =================================================================
-- 4. 관리자 관리 (Admin Management)
-- =================================================================

CREATE TABLE TB_ADMIN_LST (
    ADMIN_ID    VARCHAR(255) NOT NULL,
    ADMIN_STS_CD VARCHAR(20)  NULL,
    DEL_YN      CHAR(1)      NOT NULL DEFAULT 'N',
    DEL_DT      TIMESTAMP    NULL,
    REG_EMP_ID  VARCHAR(255) NULL,
    UPD_EMP_ID  VARCHAR(255) NULL,
    REG_DT      TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    UPD_DT      TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (ADMIN_ID)
);
COMMENT ON TABLE TB_ADMIN_LST IS '관리자 목록';
COMMENT ON COLUMN TB_ADMIN_LST.ADMIN_ID IS '관리자 ID';
COMMENT ON COLUMN TB_ADMIN_LST.ADMIN_STS_CD IS '관리자 상태코드';
COMMENT ON COLUMN TB_ADMIN_LST.DEL_YN IS '삭제 여부 (Y/N)';
COMMENT ON COLUMN TB_ADMIN_LST.DEL_DT IS '삭제 일시';
COMMENT ON COLUMN TB_ADMIN_LST.REG_EMP_ID IS '등록 직원 ID';
COMMENT ON COLUMN TB_ADMIN_LST.UPD_EMP_ID IS '수정 직원 ID';
COMMENT ON COLUMN TB_ADMIN_LST.REG_DT IS '등록 일시';
COMMENT ON COLUMN TB_ADMIN_LST.UPD_DT IS '수정 일시';
CREATE INDEX IDX_ADMIN_LST_DEL_YN ON TB_ADMIN_LST (DEL_YN);
CREATE INDEX IDX_ADMIN_LST_STS_CD ON TB_ADMIN_LST (ADMIN_STS_CD);

-- =================================================================
-- 5. 직원 참조 정보 (ERI 시스템 내부용)
-- =================================================================

CREATE TABLE TB_EMP_REF (
    ERI_EMP_ID      VARCHAR(20)  NOT NULL,           -- ERI 시스템 내부 식별번호 (E00000001 형식)
    EMP_NM          VARCHAR(50)  NULL,               -- 직원명
    GNDR_DCD        CHAR(1)      NULL,               -- 성별구분코드 (M:남성, F:여성)
    HLOF_YN         CHAR(1)      NULL,               -- 재직여부 (Y:재직, N:퇴직)
    ETBN_YMD        DATE         NULL,               -- 입행년월일 (은행 입행일자)
    BLNG_BRCD       CHAR(4)      NULL,               -- 소속부점코드
    BETEAM_CD       CHAR(4)      NULL,               -- 소속팀코드
    EXIG_BLNG_YMD   DATE         NULL,               -- 현소속년월일 (현재 소속 발령일자)
    TRTH_WORK_BRCD  CHAR(4)      NULL,               -- 실제근무부점코드
    RSWR_BRCD       CHAR(4)      NULL,               -- 주재근무부점코드
    JBTT_CD         CHAR(4)      NULL,               -- 직위코드
    JBTT_YMD        DATE         NULL,               -- 직위년월일 (직위 발령일자)
    NAME_NM         VARCHAR(30)  NULL,               -- 호칭명
    JBCL_CD         CHAR(1)      NULL,               -- 직급코드 (1:11급, 2:22급, 3:33급, 4:44급, 5:55급, 6:66급, 9:99급)
    TRTH_BIRT_YMD   DATE         NULL,               -- 실제생년월일
    SLCN_UNCG_BIRT_YMD DATE      NULL,               -- 양력환산생년월일
    INSL_DCD        CHAR(1)      NULL,               -- 음양구분코드 (S:양력, L:음력)
    EMP_CPN         VARCHAR(50)  NULL,               -- 직원휴대폰번호
    EMP_EXTI_NO     VARCHAR(50)  NULL,               -- 직원내선번호
    EAD             VARCHAR(50)  NULL,               -- 이메일주소
    DEL_YN          CHAR(1)      NOT NULL DEFAULT 'N',
    DEL_DT          TIMESTAMP    NULL,
    REG_DT          TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    UPD_DT          TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (ERI_EMP_ID)
);

COMMENT ON TABLE TB_EMP_REF IS '직원 참조 정보 (ERI 시스템 내부용)';
COMMENT ON COLUMN TB_EMP_REF.ERI_EMP_ID IS 'ERI 시스템 내부 식별번호 (E00000001 형식)';
COMMENT ON COLUMN TB_EMP_REF.EMP_NM IS '직원명';
COMMENT ON COLUMN TB_EMP_REF.GNDR_DCD IS '성별구분코드 (M:남성, F:여성)';
COMMENT ON COLUMN TB_EMP_REF.HLOF_YN IS '재직여부 (Y:재직, N:퇴직)';
COMMENT ON COLUMN TB_EMP_REF.ETBN_YMD IS '입행년월일 (은행 입행일자)';
COMMENT ON COLUMN TB_EMP_REF.BLNG_BRCD IS '소속부점코드';
COMMENT ON COLUMN TB_EMP_REF.BETEAM_CD IS '소속팀코드';
COMMENT ON COLUMN TB_EMP_REF.EXIG_BLNG_YMD IS '현소속년월일 (현재 소속 발령일자)';
COMMENT ON COLUMN TB_EMP_REF.TRTH_WORK_BRCD IS '실제근무부점코드';
COMMENT ON COLUMN TB_EMP_REF.RSWR_BRCD IS '주재근무부점코드';
COMMENT ON COLUMN TB_EMP_REF.JBTT_CD IS '직위코드';
COMMENT ON COLUMN TB_EMP_REF.JBTT_YMD IS '직위년월일 (직위 발령일자)';
COMMENT ON COLUMN TB_EMP_REF.NAME_NM IS '호칭명';
COMMENT ON COLUMN TB_EMP_REF.JBCL_CD IS '직급코드 (1:11급, 2:22급, 3:33급, 4:44급, 5:55급, 6:66급, 9:99급)';
COMMENT ON COLUMN TB_EMP_REF.TRTH_BIRT_YMD IS '실제생년월일';
COMMENT ON COLUMN TB_EMP_REF.SLCN_UNCG_BIRT_YMD IS '양력환산생년월일';
COMMENT ON COLUMN TB_EMP_REF.INSL_DCD IS '음양구분코드 (S:양력, L:음력)';
COMMENT ON COLUMN TB_EMP_REF.EMP_CPN IS '직원휴대폰번호';
COMMENT ON COLUMN TB_EMP_REF.EMP_EXTI_NO IS '직원내선번호';
COMMENT ON COLUMN TB_EMP_REF.EAD IS '이메일주소';
COMMENT ON COLUMN TB_EMP_REF.DEL_YN IS '삭제 여부 (Y/N)';
COMMENT ON COLUMN TB_EMP_REF.DEL_DT IS '삭제 일시';
COMMENT ON COLUMN TB_EMP_REF.REG_DT IS '등록 일시';
COMMENT ON COLUMN TB_EMP_REF.UPD_DT IS '수정 일시';

-- 인덱스 생성
CREATE INDEX IDX_EMP_REF_DEL_YN ON TB_EMP_REF (DEL_YN);
CREATE INDEX IDX_EMP_REF_HLOF_YN ON TB_EMP_REF (HLOF_YN);
CREATE INDEX IDX_EMP_REF_BLNG_BRCD ON TB_EMP_REF (BLNG_BRCD);
CREATE INDEX IDX_EMP_REF_BETEAM_CD ON TB_EMP_REF (BETEAM_CD);
CREATE INDEX IDX_EMP_REF_JBCL_CD ON TB_EMP_REF (JBCL_CD);
CREATE INDEX IDX_EMP_REF_ETBN_YMD ON TB_EMP_REF (ETBN_YMD);
CREATE INDEX IDX_EMP_REF_EAD ON TB_EMP_REF (EAD);
CREATE INDEX IDX_EMP_REF_EMP_CPN ON TB_EMP_REF (EMP_CPN);

-- =================================================================
-- 6. 권한관리 (Authority Management)
-- =================================================================

CREATE TABLE TB_AUTH_LST (
    AUTH_CD     VARCHAR(20)  NOT NULL,                        -- 권한 코드 (PK)
    AUTH_NM     VARCHAR(100) NULL,                            -- 권한명
    AUTH_DESC   TEXT         NULL,                            -- 권한 설명
    AUTH_LVL    INTEGER      NOT NULL DEFAULT 1,              -- 권한레벨 (1~10, 숫자가 클수록 높은 권한)
    DEL_YN      CHAR(1)      NOT NULL DEFAULT 'N',            -- 삭제 여부 (Y/N)
    DEL_DT      TIMESTAMP    NULL,                            -- 삭제 일시
    REG_EMP_ID  VARCHAR(255) NULL,                            -- 등록 직원 ID
    UPD_EMP_ID  VARCHAR(255) NULL,                            -- 수정 직원 ID
    REG_DT      TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP, -- 등록 일시
    UPD_DT      TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,       -- 수정 일시
    PRIMARY KEY (AUTH_CD)
);
COMMENT ON TABLE TB_AUTH_LST IS '권한 목록';
COMMENT ON COLUMN TB_AUTH_LST.AUTH_CD IS '권한 코드';
COMMENT ON COLUMN TB_AUTH_LST.AUTH_NM IS '권한명';
COMMENT ON COLUMN TB_AUTH_LST.AUTH_DESC IS '권한 설명';
COMMENT ON COLUMN TB_AUTH_LST.AUTH_LVL IS '권한레벨 (1~10, 숫자가 클수록 높은 권한)';
COMMENT ON COLUMN TB_AUTH_LST.DEL_YN IS '삭제 여부 (Y/N)';
COMMENT ON COLUMN TB_AUTH_LST.DEL_DT IS '삭제 일시';
COMMENT ON COLUMN TB_AUTH_LST.REG_EMP_ID IS '등록 직원 ID';
COMMENT ON COLUMN TB_AUTH_LST.UPD_EMP_ID IS '수정 직원 ID';
COMMENT ON COLUMN TB_AUTH_LST.REG_DT IS '등록 일시';
COMMENT ON COLUMN TB_AUTH_LST.UPD_DT IS '수정 일시';
CREATE INDEX IDX_AUTH_LST_DEL_YN ON TB_AUTH_LST (DEL_YN);

CREATE TABLE TB_MNU_LST (
    MNU_CD      VARCHAR(20)  NOT NULL,         -- 메뉴 코드 (PK)
    MNU_NM      VARCHAR(100) NOT NULL,         -- 메뉴명
    MNU_URL     VARCHAR(255) NULL,             -- 메뉴 URL
    MNU_DESC    TEXT         NULL,             -- 메뉴 설명
    MNU_ORD     INTEGER      NOT NULL DEFAULT 1, -- 메뉴 순서
    MNU_LVL     INTEGER      NOT NULL DEFAULT 1, -- 메뉴 레벨 (1: 대메뉴, 2: 서브메뉴)
    P_MNU_CD    VARCHAR(20)  NULL,             -- 상위 메뉴 코드 (2depth일 때만)
    MNU_USE_YN  CHAR(1)      NOT NULL DEFAULT 'Y', -- 사용여부
    MNU_AUTH_TYPE VARCHAR(20) NOT NULL DEFAULT 'USER', -- 메뉴권한구분 (USER: 일반사용자, COUNSELOR: 상담사, ADMIN: 관리자)
    DEL_YN      CHAR(1)      NOT NULL DEFAULT 'N',
    DEL_DT      TIMESTAMP    NULL,
    REG_EMP_ID  VARCHAR(255) NULL,
    UPD_EMP_ID  VARCHAR(255) NULL,
    REG_DT      TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    UPD_DT      TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (MNU_CD)
);
COMMENT ON TABLE TB_MNU_LST IS '메뉴 목록';
COMMENT ON COLUMN TB_MNU_LST.MNU_CD IS '메뉴 코드';
COMMENT ON COLUMN TB_MNU_LST.MNU_NM IS '메뉴명';
COMMENT ON COLUMN TB_MNU_LST.MNU_URL IS '메뉴 URL';
COMMENT ON COLUMN TB_MNU_LST.MNU_DESC IS '메뉴 설명';
COMMENT ON COLUMN TB_MNU_LST.MNU_ORD IS '메뉴 순서';
COMMENT ON COLUMN TB_MNU_LST.MNU_USE_YN IS '메뉴 사용여부';
COMMENT ON COLUMN TB_MNU_LST.MNU_AUTH_TYPE IS '메뉴권한구분 (USER: 일반사용자, COUNSELOR: 상담사, ADMIN: 관리자)';
COMMENT ON COLUMN TB_MNU_LST.MNU_LVL IS '메뉴 레벨 (1: 대메뉴, 2: 서브메뉴)';
COMMENT ON COLUMN TB_MNU_LST.P_MNU_CD IS '상위 메뉴 코드 (2depth일 때만)';
CREATE INDEX IDX_MNU_LST_DEL_YN ON TB_MNU_LST (DEL_YN);
CREATE INDEX IDX_MNU_LST_ORD ON TB_MNU_LST (MNU_ORD);
CREATE INDEX IDX_MNU_LST_USE ON TB_MNU_LST (MNU_USE_YN);
CREATE INDEX IDX_MNU_LST_AUTH ON TB_MNU_LST (MNU_AUTH_TYPE);

-- =================================================================
-- 4.1. 권한-메뉴 매핑 (Authority-Menu Mapping)
-- =================================================================

CREATE TABLE TB_AUTH_GRP_AUTH_MAP (
    AUTH_CD     VARCHAR(20)  NOT NULL,
    MNU_CD      VARCHAR(20)  NOT NULL,
    DEL_YN      CHAR(1)      NOT NULL DEFAULT 'N',
    DEL_DT      TIMESTAMP    NULL,
    REG_EMP_ID  VARCHAR(255) NULL,
    UPD_EMP_ID  VARCHAR(255) NULL,
    REG_DT      TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    UPD_DT      TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (AUTH_CD, MNU_CD)
);
COMMENT ON TABLE TB_AUTH_GRP_AUTH_MAP IS '권한-메뉴 매핑';
COMMENT ON COLUMN TB_AUTH_GRP_AUTH_MAP.AUTH_CD IS '권한 코드';
COMMENT ON COLUMN TB_AUTH_GRP_AUTH_MAP.MNU_CD IS '메뉴 코드';
CREATE INDEX IDX_AUTH_GRP_AUTH_MAP_DEL_YN ON TB_AUTH_GRP_AUTH_MAP (DEL_YN);
CREATE INDEX IDX_AUTH_GRP_AUTH_MAP_AUTH ON TB_AUTH_GRP_AUTH_MAP (AUTH_CD);
CREATE INDEX IDX_AUTH_GRP_AUTH_MAP_MNU ON TB_AUTH_GRP_AUTH_MAP (MNU_CD);


CREATE TABLE TB_DEPT_LST (
    DEPT_CD     VARCHAR(20)  NOT NULL,                        -- 부서 코드 (PK)
    DEPT_NM     VARCHAR(100) NULL,                            -- 부서명
    DEPT_DESC   TEXT         NULL,                            -- 부서 설명
    DEPT_MGR_EMP_ID VARCHAR(255) NULL,                        -- 부서장 직원 ID
    DEPT_STS_CD VARCHAR(20)  NULL,                            -- 부서 상태코드
    FILE_ATT_YN CHAR(1)      NOT NULL DEFAULT 'N',            -- 첨부파일 존재 여부
    DEL_YN      CHAR(1)      NOT NULL DEFAULT 'N',            -- 삭제 여부 (Y/N)
    DEL_DT      TIMESTAMP    NULL,                            -- 삭제 일시
    REG_EMP_ID  VARCHAR(255) NULL,                            -- 등록 직원 ID
    UPD_EMP_ID  VARCHAR(255) NULL,                            -- 수정 직원 ID
    REG_DT      TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP, -- 등록 일시
    UPD_DT      TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,       -- 수정 일시
    PRIMARY KEY (DEPT_CD)
);
COMMENT ON TABLE TB_DEPT_LST IS '부서 목록';
COMMENT ON COLUMN TB_DEPT_LST.DEPT_CD IS '부서 코드';
COMMENT ON COLUMN TB_DEPT_LST.DEPT_NM IS '부서명';
COMMENT ON COLUMN TB_DEPT_LST.DEPT_DESC IS '부서 설명';
COMMENT ON COLUMN TB_DEPT_LST.DEPT_MGR_EMP_ID IS '부서장 직원 ID';
COMMENT ON COLUMN TB_DEPT_LST.DEPT_STS_CD IS '부서 상태코드';
COMMENT ON COLUMN TB_DEPT_LST.FILE_ATT_YN IS '첨부파일 존재 여부';
COMMENT ON COLUMN TB_DEPT_LST.DEL_YN IS '삭제 여부 (Y/N)';
COMMENT ON COLUMN TB_DEPT_LST.DEL_DT IS '삭제 일시';
COMMENT ON COLUMN TB_DEPT_LST.REG_EMP_ID IS '등록 직원 ID';
COMMENT ON COLUMN TB_DEPT_LST.UPD_EMP_ID IS '수정 직원 ID';
COMMENT ON COLUMN TB_DEPT_LST.REG_DT IS '등록 일시';
COMMENT ON COLUMN TB_DEPT_LST.UPD_DT IS '수정 일시';
CREATE INDEX IDX_DEPT_LST_DEL_YN ON TB_DEPT_LST (DEL_YN);

CREATE TABLE TB_CNSLR_LST (
    CNSLR_EMP_ID     VARCHAR(255) NOT NULL,     -- 상담사직원ID
    CNSLR_INFO_CLSF_CD VARCHAR(20) NULL,        -- 상담자정보구분코드
    CNSLR_NM         VARCHAR(100) NULL,         -- 상담사명
    CNSLR_EXP_YR     INTEGER      NULL,         -- 상담사경력년수
    CNSLR_STS_CD     VARCHAR(20)  NULL,         -- 상담사상태코드 (ACT:활성, INACT:비활성)
    CNSLR_AVL_YN     CHAR(1)      NOT NULL DEFAULT 'Y', -- 상담사가능여부
    CNSLR_DESC       TEXT         NULL,         -- 상담사설명
    
    DEL_YN           CHAR(1)      NOT NULL DEFAULT 'N',
    DEL_DT           TIMESTAMP    NULL,
    REG_EMP_ID       VARCHAR(255) NULL,
    UPD_EMP_ID       VARCHAR(255) NULL,
    REG_DT           TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    UPD_DT           TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (CNSLR_EMP_ID)
);
COMMENT ON TABLE TB_CNSLR_LST IS '상담사 목록';
COMMENT ON COLUMN TB_CNSLR_LST.CNSLR_EMP_ID IS '상담사직원ID';
COMMENT ON COLUMN TB_CNSLR_LST.CNSLR_INFO_CLSF_CD IS '상담자정보구분코드';
COMMENT ON COLUMN TB_CNSLR_LST.CNSLR_NM IS '상담사명';
COMMENT ON COLUMN TB_CNSLR_LST.CNSLR_EXP_YR IS '상담사경력년수';
COMMENT ON COLUMN TB_CNSLR_LST.CNSLR_STS_CD IS '상담사상태코드 (ACT:활성, INACT:비활성)';
COMMENT ON COLUMN TB_CNSLR_LST.CNSLR_AVL_YN IS '상담사가능여부 (Y:가능, N:불가능)';
COMMENT ON COLUMN TB_CNSLR_LST.CNSLR_DESC IS '상담사설명';
CREATE INDEX IDX_CNSLR_LST_DEL_YN ON TB_CNSLR_LST (DEL_YN);
CREATE INDEX IDX_CNSLR_LST_INFO_CLSF ON TB_CNSLR_LST (CNSLR_INFO_CLSF_CD);
CREATE INDEX IDX_CNSLR_LST_STS_CD ON TB_CNSLR_LST (CNSLR_STS_CD);
CREATE INDEX IDX_CNSLR_LST_AVL_YN ON TB_CNSLR_LST (CNSLR_AVL_YN);

-- =================================================================
-- 6. 첨부파일 관리 (File Attachment Management)
-- =================================================================



-- 첨부파일 관리 테이블 (개선된 구조)
CREATE TABLE TB_FILE_ATTACH (
    FILE_SEQ        BIGSERIAL    NOT NULL,
    REF_TBL_CD      VARCHAR(20)  NOT NULL,     -- 참조 테이블 코드 (FK)
    REF_PK_VAL      VARCHAR(255) NOT NULL,     -- 참조 테이블의 PK 값 (문자열로 통일)
    FILE_NM         VARCHAR(255) NOT NULL,     -- 원본 파일명
    FILE_SAVE_NM    VARCHAR(255) NOT NULL,     -- 저장된 파일명 (UUID 등)
    FILE_PATH       VARCHAR(500) NOT NULL,     -- 파일 저장 경로
    FILE_SIZE       BIGINT       NOT NULL,     -- 파일 크기 (bytes)
    FILE_EXT        VARCHAR(20)  NULL,         -- 파일 확장자
    FILE_MIME_TYPE  VARCHAR(100) NULL,         -- MIME 타입
    FILE_DESC       TEXT         NULL,         -- 파일 설명
    FILE_ORD        INTEGER      NOT NULL DEFAULT 1, -- 파일 순서
    DOWN_CNT        INTEGER      NOT NULL DEFAULT 0, -- 다운로드 횟수
    DEL_YN          CHAR(1)      NOT NULL DEFAULT 'N',
    DEL_DT          TIMESTAMP    NULL,
    REG_EMP_ID      VARCHAR(255) NULL,
    UPD_EMP_ID      VARCHAR(255) NULL,
    REG_DT          TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    UPD_DT          TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (FILE_SEQ)
);
COMMENT ON TABLE TB_FILE_ATTACH IS '첨부파일 관리';
COMMENT ON COLUMN TB_FILE_ATTACH.REF_TBL_CD IS '참조 테이블 코드';
COMMENT ON COLUMN TB_FILE_ATTACH.REF_PK_VAL IS '참조 테이블의 PK 값 (문자열로 통일)';
COMMENT ON COLUMN TB_FILE_ATTACH.FILE_NM IS '원본 파일명';
COMMENT ON COLUMN TB_FILE_ATTACH.FILE_SAVE_NM IS '저장된 파일명 (UUID 등)';
COMMENT ON COLUMN TB_FILE_ATTACH.FILE_PATH IS '파일 저장 경로';
COMMENT ON COLUMN TB_FILE_ATTACH.FILE_SIZE IS '파일 크기 (bytes)';
COMMENT ON COLUMN TB_FILE_ATTACH.FILE_EXT IS '파일 확장자';
COMMENT ON COLUMN TB_FILE_ATTACH.FILE_MIME_TYPE IS 'MIME 타입';
COMMENT ON COLUMN TB_FILE_ATTACH.FILE_DESC IS '파일 설명';
COMMENT ON COLUMN TB_FILE_ATTACH.FILE_ORD IS '파일 순서';
COMMENT ON COLUMN TB_FILE_ATTACH.DOWN_CNT IS '다운로드 횟수';
CREATE INDEX IDX_FILE_ATTACH_REF ON TB_FILE_ATTACH (REF_TBL_CD, REF_PK_VAL);
CREATE INDEX IDX_FILE_ATTACH_DEL_YN ON TB_FILE_ATTACH (DEL_YN);
CREATE INDEX IDX_FILE_ATTACH_ORD ON TB_FILE_ATTACH (FILE_ORD);



-- =================================================================
-- 7. 샘플 데이터 삽입 (Sample Data Insertion)
-- =================================================================

-- 7.1. 관리자 목록 샘플 데이터
INSERT INTO TB_ADMIN_LST (ADMIN_ID, ADMIN_STS_CD, REG_EMP_ID) VALUES
('ADMIN001', 'STS001', 'ADMIN001'),
('ADMIN002', 'STS001', 'ADMIN001'),
('ADMIN003', 'STS001', 'ADMIN001'),
('ADMIN004', 'STS001', 'ADMIN001'),
('ADMIN005', 'STS001', 'ADMIN001');

-- 7.2. 권한 목록 샘플 데이터
INSERT INTO TB_AUTH_LST (AUTH_CD, AUTH_NM, AUTH_DESC, AUTH_LVL) VALUES
('AUTH_001', '사용자 관리', '사용자 정보 조회/등록/수정/삭제', 8),
('AUTH_002', '부서 관리', '부서 정보 조회/등록/수정/삭제', 7),
('AUTH_003', '프로그램 관리', '프로그램 정보 조회/등록/수정/삭제', 6),
('AUTH_004', '상담 관리', '상담 정보 조회/등록/수정/삭제', 5),
('AUTH_005', '공지사항 관리', '공지사항 조회/등록/수정/삭제', 4),
('AUTH_006', '통계 조회', '통계 정보 조회', 3),
('AUTH_007', '권한 관리', '권한 정보 조회/등록/수정/삭제', 9),
('AUTH_008', '메뉴 관리', '메뉴 정보 조회/등록/수정/삭제', 9);

-- TB_MNU_LST 샘플 데이터
INSERT INTO TB_MNU_LST (MNU_CD, MNU_NM, MNU_URL, MNU_DESC, MNU_ORD, MNU_LVL, P_MNU_CD, MNU_USE_YN, MNU_AUTH_TYPE, REG_EMP_ID)
VALUES
('MNU_001', '프로그램', NULL, NULL, 1, 1, NULL, 'Y', 'ADMIN', 'ADMIN001'),
('MNU_002', 'IBK마음건강검진', NULL, NULL, 2, 1, NULL, 'Y', 'ADMIN', 'ADMIN001'),
('MNU_003', '상담신청', NULL, NULL, 3, 1, NULL, 'Y', 'ADMIN', 'ADMIN001'),
('MNU_004', '자료실', NULL, NULL, 4, 1, NULL, 'Y', 'ADMIN', 'ADMIN001'),
('MNU_005', '설정하기', NULL, NULL, 5, 1, NULL, 'Y', 'ADMIN', 'ADMIN001'),
('MNU_006', '관리자 페이지', NULL, NULL, 6, 1, NULL, 'Y', 'ADMIN', 'ADMIN001'),

('MNU_005_01', '설정하기', NULL, NULL, 1, 2, 'MNU_005', 'Y', 'ADMIN', 'ADMIN001'),
('MNU_001_01', '프로그램 조회', '/program/inquiry', NULL, 1, 2, 'MNU_001', 'Y', 'ADMIN', 'ADMIN001'),
('MNU_001_02', '프로그램 신청', '/program/apply', NULL, 2, 2, 'MNU_001', 'Y', 'ADMIN', 'ADMIN001'),
('MNU_002_01', 'IBK마음건강검진', '/health-check/ibk', NULL, 1, 2, 'MNU_002', 'Y', 'ADMIN', 'ADMIN001'),
('MNU_003_01', '오프라인 상담신청', '/consultation/offline', NULL, 1, 2, 'MNU_003', 'Y', 'ADMIN', 'ADMIN001'),
('MNU_003_02', '비대면 상담신청', '/consultation/online', NULL, 2, 2, 'MNU_003', 'Y', 'ADMIN', 'ADMIN001'),
('MNU_003_03', '상담 신청 현황', '/consultation/status', NULL, 3, 2, 'MNU_003', 'Y', 'ADMIN', 'ADMIN001'),
('MNU_004_01', '공지사항', '/resources/notice', NULL, 1, 2, 'MNU_004', 'Y', 'ADMIN', 'ADMIN001'),
('MNU_004_02', '직원권익보호 게시판', '/resources/board', NULL, 2, 2, 'MNU_004', 'Y', 'ADMIN', 'ADMIN001'),
('MNU_004_03', '마음챙김레터', '/resources/letter', NULL, 3, 2, 'MNU_004', 'Y', 'ADMIN', 'ADMIN001'),
('MNU_006_01', '공통코드관리', '/admin/comCdMng', '시스템에서 사용되는 공통코드(그룹코드, 상세코드)를 관리하는 메뉴', 1, 2, 'MNU_006', 'Y', 'ADMIN', 'ADMIN001'),
('MNU_006_02', '권한관리', '/admin/authMng', '시스템 사용자의 권한을 관리하는 메뉴', 2, 2, 'MNU_006', 'Y', 'ADMIN', 'ADMIN001'),
('MNU_006_03', '메뉴관리', '/admin/menuMng', '시스템 메뉴 구조를 관리하는 메뉴', 3, 2, 'MNU_006', 'Y', 'ADMIN', 'ADMIN001'),
('MNU_006_04', '사용자관리', '/admin/userMng', '시스템 사용자 정보를 관리하는 메뉴', 4, 2, 'MNU_006', 'Y', 'ADMIN', 'ADMIN001'),
('MNU_006_05', '부서관리', '/admin/deptMng', '조직 부서 정보를 관리하는 메뉴', 5, 2, 'MNU_006', 'Y', 'ADMIN', 'ADMIN001'),
('MNU_006_06', '프로그램관리', '/admin/programMng', '시스템 프로그램 정보를 관리하는 메뉴', 6, 2, 'MNU_006', 'Y', 'ADMIN', 'ADMIN001'),
('MNU_006_07', '상담관리', '/admin/consultationMng', '상담 관련 정보를 관리하는 메뉴', 7, 2, 'MNU_006', 'Y', 'ADMIN', 'ADMIN001'),
('MNU_006_08', '공지사항관리', '/admin/noticeMng', '공지사항을 관리하는 메뉴', 8, 2, 'MNU_006', 'Y', 'ADMIN', 'ADMIN001'),
('MNU_006_09', '통계관리', '/admin/statisticsMng', '시스템 통계 정보를 관리하는 메뉴', 9, 2, 'MNU_006', 'Y', 'ADMIN', 'ADMIN001'),
('MNU_006_10', '시스템설정', '/admin/systemConfig', '시스템 전반적인 설정을 관리하는 메뉴', 10, 2, 'MNU_006', 'Y', 'ADMIN', 'ADMIN001'),
('MNU_006_11', '로그관리', '/admin/logMng', '시스템 로그를 조회하고 관리하는 메뉴', 11, 2, 'MNU_006', 'Y', 'ADMIN', 'ADMIN001'),
('MNU_006_12', '백업관리', '/admin/backupMng', '시스템 데이터 백업을 관리하는 메뉴', 12, 2, 'MNU_006', 'Y', 'ADMIN', 'ADMIN001');

-- 7.3. 권한-메뉴 매핑 샘플 데이터
INSERT INTO TB_AUTH_GRP_AUTH_MAP (AUTH_CD, MNU_CD, REG_EMP_ID) VALUES
-- 사용자 관리 권한
('AUTH_001', 'MNU_006_04', 'ADMIN001'),
-- 부서 관리 권한
('AUTH_002', 'MNU_006_05', 'ADMIN001'),
-- 프로그램 관리 권한
('AUTH_003', 'MNU_006_06', 'ADMIN001'),
-- 상담 관리 권한
('AUTH_004', 'MNU_006_07', 'ADMIN001'),
-- 공지사항 관리 권한
('AUTH_005', 'MNU_006_08', 'ADMIN001'),
-- 통계 조회 권한
('AUTH_006', 'MNU_006_09', 'ADMIN001'),
-- 권한 관리 권한
('AUTH_007', 'MNU_006_02', 'ADMIN001'),
-- 메뉴 관리 권한
('AUTH_008', 'MNU_006_03', 'ADMIN001');

-- 7.4. 공지사항 목록 샘플 데이터
INSERT INTO TB_NTI_LST (TTL, CNTN, STS_CD, REG_EMP_ID) VALUES
('2024년 IBK마음건강검진 안내', '안녕하세요. 2024년 IBK마음건강검진이 시작됩니다. 모든 직원분들의 참여를 부탁드립니다. 검진 일정은 개별 안내드리겠습니다.', 'STS001', 'ADMIN001'),
('직원권익보호 프로그램 신청 안내', '직원권익보호 프로그램 신청이 시작되었습니다. 신청 기간은 2024년 3월 1일부터 3월 31일까지입니다. 많은 참여 부탁드립니다.', 'STS001', 'ADMIN001'),
('상담 서비스 이용 안내', '직원 상담 서비스 이용 방법을 안내드립니다. 오프라인 상담과 비대면 상담 모두 가능합니다. 상담 신청은 시스템을 통해 가능합니다.', 'STS001', 'ADMIN001'),
('마음챙김 프로그램 운영 안내', '월 1회 마음챙김 프로그램이 운영됩니다. 프로그램 참여를 통해 스트레스 관리와 마음 건강을 챙겨보세요.', 'STS001', 'ADMIN001'),
('직원 복지 프로그램 안내', '다양한 직원 복지 프로그램이 준비되어 있습니다. 프로그램별 상세 내용은 자료실에서 확인하실 수 있습니다.', 'STS001', 'ADMIN001'),
('시스템 점검 안내', '2024년 3월 15일 새벽 2시부터 4시까지 시스템 점검이 예정되어 있습니다. 해당 시간대에는 서비스 이용이 제한됩니다.', 'STS002', 'ADMIN001'),
('개인정보 보호 정책 변경 안내', '개인정보 보호 정책이 변경되었습니다. 주요 변경사항은 개인정보 수집 및 이용에 대한 동의 절차 강화입니다.', 'STS001', 'ADMIN001'),
('연차 사용 안내', '2024년 연차 사용에 대한 안내입니다. 연차 사용 계획을 미리 세워주시기 바랍니다.', 'STS001', 'ADMIN001'),
('건강검진 결과 확인 안내', '2023년 건강검진 결과가 시스템에 업로드되었습니다. 개인정보 보호를 위해 개별 확인 부탁드립니다.', 'STS001', 'ADMIN001'),
('재택근무 정책 안내', '재택근무 정책이 새롭게 도입되었습니다. 재택근무 신청 및 운영에 대한 상세 안내는 별도 공지드리겠습니다.', 'STS001', 'ADMIN001');



-- 7.6. 첨부파일 샘플 데이터 (개선된 구조)
INSERT INTO TB_FILE_ATTACH (REF_TBL_CD, REF_PK_VAL, FILE_NM, FILE_SAVE_NM, FILE_PATH, FILE_SIZE, FILE_EXT, FILE_MIME_TYPE, FILE_DESC, FILE_ORD, REG_EMP_ID) VALUES
('NTI', '1', '2024_IBK_마음건강검진_안내서.pdf', '2024_IBK_마음건강검진_안내서_20240301_001.pdf', '/uploads/notice/2024/03/', 2048576, 'pdf', 'application/pdf', '2024년 IBK마음건강검진 안내서', 1, 'ADMIN001'),
('NTI', '1', '마음건강검진_신청서.xlsx', '마음건강검진_신청서_20240301_002.xlsx', '/uploads/notice/2024/03/', 512000, 'xlsx', 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet', '마음건강검진 신청서 양식', 2, 'ADMIN001'),
('NTI', '2', '직원권익보호_프로그램_신청_가이드.pdf', '직원권익보호_프로그램_신청_가이드_20240301_003.pdf', '/uploads/notice/2024/03/', 1536000, 'pdf', 'application/pdf', '직원권익보호 프로그램 신청 가이드', 1, 'ADMIN001'),
('PGM', '1', '프로그램_운영_매뉴얼.pdf', '프로그램_운영_매뉴얼_20240301_004.pdf', '/uploads/program/2024/03/', 3072000, 'pdf', 'application/pdf', '프로그램 운영 매뉴얼', 1, 'ADMIN001'),
('PGM', '1', '프로그램_참여자_명단.xlsx', '프로그램_참여자_명단_20240301_005.xlsx', '/uploads/program/2024/03/', 256000, 'xlsx', 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet', '프로그램 참여자 명단', 2, 'ADMIN001');



-- =================================================================
-- 8. 첨부파일 관리 유틸리티 함수 (File Attachment Utility Functions)
-- =================================================================

-- 특정 참조 테이블의 첨부파일 목록 조회 함수 제거 - 자바에서 처리
-- GET_FILE_ATTACH_LIST 함수는 자바 서비스에서 처리
-- FileAttachService.getFileAttachList() 메서드로 대체



-- 첨부파일 다운로드 횟수 증가 함수 제거 - 자바에서 처리
-- INCREMENT_FILE_DOWNLOAD_COUNT 함수는 자바 서비스에서 처리
-- FileAttachService.incrementDownloadCount() 메서드로 대체



-- =================================================================
-- 9. 시스템 로그 관리 (System Log Management)
-- =================================================================

CREATE TABLE TB_SYS_LOG (
    LOG_SEQ BIGSERIAL NOT NULL,
    LOG_LEVEL VARCHAR(10) NOT NULL,
    LOG_TYPE VARCHAR(50) NOT NULL,
    LOG_MSG TEXT NOT NULL,
    LOG_DETAIL TEXT NULL,
    CLASS_NAME VARCHAR(200) NULL,
    METHOD_NAME VARCHAR(100) NULL,
    LINE_NUMBER INTEGER NULL,
    STACK_TRACE TEXT NULL,
    EMP_ID VARCHAR(20) NULL,
    SESSION_ID VARCHAR(100) NULL,
    REQUEST_URI VARCHAR(500) NULL,
    REQUEST_METHOD VARCHAR(10) NULL,
    REQUEST_PARAMS TEXT NULL,
    RESPONSE_STATUS INTEGER NULL,
    EXECUTION_TIME BIGINT NULL,
    IP_ADDRESS VARCHAR(45) NULL,
    USER_AGENT TEXT NULL,
    ERR_CODE VARCHAR(50) NULL,
    ERR_CAT VARCHAR(50) NULL,
    CRTD_DT TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CRTD_BY VARCHAR(20) NOT NULL DEFAULT 'SYSTEM',
    PRIMARY KEY (LOG_SEQ)
);

COMMENT ON TABLE TB_SYS_LOG IS '시스템 로그 테이블';
COMMENT ON COLUMN TB_SYS_LOG.LOG_SEQ IS '로그 시퀀스';
COMMENT ON COLUMN TB_SYS_LOG.LOG_LEVEL IS '로그 레벨 (INFO, WARN, ERROR, DEBUG)';
COMMENT ON COLUMN TB_SYS_LOG.LOG_TYPE IS '로그 타입 (SYSTEM, API, DATABASE, SECURITY, MESSENGER)';
COMMENT ON COLUMN TB_SYS_LOG.LOG_MSG IS '로그 메시지';
COMMENT ON COLUMN TB_SYS_LOG.LOG_DETAIL IS '상세 로그 내용';
COMMENT ON COLUMN TB_SYS_LOG.CLASS_NAME IS '발생 클래스명';
COMMENT ON COLUMN TB_SYS_LOG.METHOD_NAME IS '발생 메서드명';
COMMENT ON COLUMN TB_SYS_LOG.LINE_NUMBER IS '발생 라인 번호';
COMMENT ON COLUMN TB_SYS_LOG.STACK_TRACE IS '스택 트레이스';
COMMENT ON COLUMN TB_SYS_LOG.EMP_ID IS '사용자 사번';
COMMENT ON COLUMN TB_SYS_LOG.SESSION_ID IS '세션 ID';
COMMENT ON COLUMN TB_SYS_LOG.REQUEST_URI IS '요청 URI';
COMMENT ON COLUMN TB_SYS_LOG.REQUEST_METHOD IS '요청 메서드';
COMMENT ON COLUMN TB_SYS_LOG.REQUEST_PARAMS IS '요청 파라미터';
COMMENT ON COLUMN TB_SYS_LOG.RESPONSE_STATUS IS '응답 상태 코드';
COMMENT ON COLUMN TB_SYS_LOG.EXECUTION_TIME IS '실행 시간 (ms)';
COMMENT ON COLUMN TB_SYS_LOG.IP_ADDRESS IS 'IP 주소';
COMMENT ON COLUMN TB_SYS_LOG.USER_AGENT IS '사용자 에이전트';
COMMENT ON COLUMN TB_SYS_LOG.ERR_CODE IS '에러 코드';
COMMENT ON COLUMN TB_SYS_LOG.ERR_CAT IS '에러 카테고리';
COMMENT ON COLUMN TB_SYS_LOG.CRTD_DT IS '생성일시';
COMMENT ON COLUMN TB_SYS_LOG.CRTD_BY IS '생성자';

-- 시스템 로그 인덱스 생성
CREATE INDEX IDX_SYS_LOG_LEVEL ON TB_SYS_LOG (LOG_LEVEL);
CREATE INDEX IDX_SYS_LOG_TYPE ON TB_SYS_LOG (LOG_TYPE);
CREATE INDEX IDX_SYS_LOG_DATE ON TB_SYS_LOG (CRTD_DT);
CREATE INDEX IDX_SYS_LOG_EMP ON TB_SYS_LOG (EMP_ID);
CREATE INDEX IDX_SYS_LOG_ERROR ON TB_SYS_LOG (ERR_CODE);
CREATE INDEX IDX_SYS_LOG_ERROR_CATEGORY ON TB_SYS_LOG (ERR_CAT);
CREATE INDEX IDX_SYS_LOG_CLASS ON TB_SYS_LOG (CLASS_NAME);


-- =================================================================
-- 2. 직급 코드 테이블
-- =================================================================

CREATE TABLE TB_EMP_JBCL_CD (
    JBCL_CD     CHAR(1)      NOT NULL,           -- 직급코드
    JBCL_NM     VARCHAR(20)  NOT NULL,           -- 직급명
    JBCL_ORD    INTEGER      NOT NULL,           -- 정렬순서
    USE_YN      CHAR(1)      NOT NULL DEFAULT 'Y',
    REG_DT      TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    UPD_DT      TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (JBCL_CD)
);

COMMENT ON TABLE TB_EMP_JBCL_CD IS '직급 코드';
COMMENT ON COLUMN TB_EMP_JBCL_CD.JBCL_CD IS '직급코드';
COMMENT ON COLUMN TB_EMP_JBCL_CD.JBCL_NM IS '직급명';
COMMENT ON COLUMN TB_EMP_JBCL_CD.JBCL_ORD IS '정렬순서';
COMMENT ON COLUMN TB_EMP_JBCL_CD.USE_YN IS '사용여부';
COMMENT ON COLUMN TB_EMP_JBCL_CD.REG_DT IS '등록일시';
COMMENT ON COLUMN TB_EMP_JBCL_CD.UPD_DT IS '수정일시';

-- 직급 코드 데이터 삽입
INSERT INTO TB_EMP_JBCL_CD (JBCL_CD, JBCL_NM, JBCL_ORD) VALUES
('1', '1급', 1),
('2', '2급', 2),
('3', '3급', 3),
('4', '4급', 4),
('5', '5급', 5),
('6', '6급', 6),
('9', '9급', 7);

-- =================================================================
-- 3. 부점 코드 테이블
-- =================================================================

CREATE TABLE TB_EMP_BRCD (
    BRCD        CHAR(4)      NOT NULL,           -- 부점코드
    BR_NM       VARCHAR(50)  NOT NULL,           -- 부점명
    USE_YN      CHAR(1)      NOT NULL DEFAULT 'Y',
    REG_DT      TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    UPD_DT      TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (BRCD)
);

COMMENT ON TABLE TB_EMP_BRCD IS '부점 코드';
COMMENT ON COLUMN TB_EMP_BRCD.BRCD IS '부점코드';
COMMENT ON COLUMN TB_EMP_BRCD.BR_NM IS '부점명';
COMMENT ON COLUMN TB_EMP_BRCD.USE_YN IS '사용여부';
COMMENT ON COLUMN TB_EMP_BRCD.REG_DT IS '등록일시';
COMMENT ON COLUMN TB_EMP_BRCD.UPD_DT IS '수정일시';



-- =================================================================
-- 5. 직위 코드 테이블
-- =================================================================

CREATE TABLE TB_EMP_JBTT_CD (
    JBTT_CD     CHAR(4)      NOT NULL,           -- 직위코드
    JBTT_NM     VARCHAR(20)  NOT NULL,           -- 직위명
    JBTT_ORD    INTEGER      NOT NULL,           -- 정렬순서
    USE_YN      CHAR(1)      NOT NULL DEFAULT 'Y',
    REG_DT      TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    UPD_DT      TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (JBTT_CD)
);

COMMENT ON TABLE TB_EMP_JBTT_CD IS '직위 코드';
COMMENT ON COLUMN TB_EMP_JBTT_CD.JBTT_CD IS '직위코드';
COMMENT ON COLUMN TB_EMP_JBTT_CD.JBTT_NM IS '직위명';
COMMENT ON COLUMN TB_EMP_JBTT_CD.JBTT_ORD IS '정렬순서';
COMMENT ON COLUMN TB_EMP_JBTT_CD.USE_YN IS '사용여부';
COMMENT ON COLUMN TB_EMP_JBTT_CD.REG_DT IS '등록일시';
COMMENT ON COLUMN TB_EMP_JBTT_CD.UPD_DT IS '수정일시';

-- 직위 코드 데이터 삽입
INSERT INTO TB_EMP_JBTT_CD (JBTT_CD, JBTT_NM, JBTT_ORD) VALUES
('01', '사원', 1),
('02', '대리', 2),
('03', '과장', 3),
('04', '차장', 4),
('05', '부장', 5),
('06', '이사', 6),
('07', '상무', 7),
('08', '전무', 8),
('09', '부사장', 9),
('10', '사장', 10);

-- =================================================================
-- 파일 요약
-- =================================================================
-- 
-- 이 파일은 ERI Employee Rights Protection System (Management System)의 DDL 스크립트입니다.
-- 
-- 생성된 테이블:
-- 1. 공지사항 관리: TB_NTI_LST, TB_NTI_TRSM_RGST_DTL, TB_NTI_TRSM_TRGT_DTL
-- 2. 프로그램 관리: TB_PGM_LST, TB_PGM_PRE_ASGN_DTL, TB_PGM_CNTN_TRSM_DTL, TB_CNTN_TRSM_TRGT_DTL
-- 3. 상담 관리: TB_GNRL_CNSL, TB_GNRL_CNSL_DTL, TB_DSRD_PSY_TEST_DTL, TB_RMT_CNSL, TB_RMT_CNSL_DTL
-- 4. 관리자 관리: TB_ADMIN_LST
-- 5. 직원 참조: TB_EMP_REF
-- 6. 권한 관리: TB_AUTH_LST, TB_MNU_LST, TB_AUTH_GRP_AUTH_MAP
-- 7. 부서 관리: TB_DEPT_LST
-- 8. 상담사 관리: TB_CNSLR_LST
-- 9. 파일 첨부: TB_FILE_ATTACH
-- 10. 시스템 로그: TB_SYS_LOG
-- 11. 코드 테이블: TB_EMP_JBCL_CD, TB_EMP_BRCD, TB_EMP_JBTT_CD
-- 
-- 주요 특징:
-- - 모든 테이블에 테이블 및 컬럼 주석 추가
-- - 영문 약어를 Upper Camel Case 규칙으로 적용 (예: DATE -> DT, FILE_ATTACH -> FILE_ATT)
-- - 공통 컬럼 (DEL_YN, DEL_DT, REG_EMP_ID, UPD_EMP_ID, REG_DT, UPD_DT) 포함
-- - 인덱스 및 제약조건 정의
-- - 샘플 데이터 포함
-- =================================================================
