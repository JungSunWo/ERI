-- =================================================================
-- ERI Employee Rights Protection System (Management System) DDL Script
-- Generated based on the provided schema diagram.
-- MySQL 8.0+ Compatible
-- =================================================================

-- 데이터베이스 사용
USE eri_db;

-- =================================================================
-- 1. 공지사항 관리 (Notice Management)
-- =================================================================

-- 1.1. 공지사항 목록 (TB_NTI_LST)
CREATE TABLE TB_NTI_LST (
    SEQ         BIGINT       NOT NULL COMMENT '일련번호',
    TTL         VARCHAR(255) NULL     COMMENT '제목',
    CNTN        TEXT         NULL     COMMENT '내용',
    STS_CD      VARCHAR(20)  NULL     COMMENT '상태코드',
    RGST_DT     DATE         NULL     COMMENT '등록일자',
    RGST_TM     TIME         NULL     COMMENT '등록시간',
    RGST_EMP_ID VARCHAR(255)  NULL     COMMENT '등록직원번호',
    UPDT_DT     DATE         NULL     COMMENT '수정일자',
    UPDT_TM     TIME         NULL     COMMENT '수정시간',
    UPDT_EMP_ID VARCHAR(255)  NULL     COMMENT '수정직원번호',
    DEL_YN      CHAR(1)      NOT NULL DEFAULT 'N' COMMENT '삭제 여부 (Y: 삭제, N: 사용)',
    DEL_DATE    DATETIME     NULL     COMMENT '삭제 일시',
    REG_EMP_ID  VARCHAR(255) NULL     COMMENT '등록직원번호',
    UPD_EMP_ID  VARCHAR(255) NULL     COMMENT '수정직원번호',
    REG_DATE    DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '등록 일시',
    UPD_DATE    DATETIME     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정 일시',
    PRIMARY KEY (SEQ),
    INDEX IDX_DEL_YN (DEL_YN)
) COMMENT '공지사항 목록';

-- 1.2. 공지사항 발송등록 명세 (TB_NTI_TRSM_RGST_DTL)
CREATE TABLE TB_NTI_TRSM_RGST_DTL (
    RGST_DT      DATE        NOT NULL COMMENT '등록일자 (PK)',
    SEQ          BIGINT      NOT NULL COMMENT '일련번호 (PK)',
    NTI_SEQ      BIGINT      NULL     COMMENT '공지사항 일련번호',
    TRSM_MDI_CD  VARCHAR(20) NULL     COMMENT '발송매체코드',
    RGST_TM      TIME        NULL     COMMENT '등록시간',
    RGST_EMP_ID  VARCHAR(255) NULL     COMMENT '등록직원번호',
    UPDT_DT      DATE        NULL     COMMENT '수정일자',
    UPDT_TM      TIME        NULL     COMMENT '수정시간',
    UPDT_EMP_ID  VARCHAR(255) NULL     COMMENT '수정직원번호',
    DEL_YN       CHAR(1)      NOT NULL DEFAULT 'N' COMMENT '삭제 여부 (Y: 삭제, N: 사용)',
    DEL_DATE     DATETIME     NULL     COMMENT '삭제 일시',
    REG_DATE     DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '등록 일시',
    UPD_DATE     DATETIME     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정 일시',
    PRIMARY KEY (RGST_DT, SEQ),
    INDEX IDX_DEL_YN (DEL_YN)
) COMMENT '공지사항 발송등록 명세';

-- 1.3. 공지사항 발송대상자 명세 (TB_NTI_TRSM_TRGT_DTL)
CREATE TABLE TB_NTI_TRSM_TRGT_DTL (
    RGST_DT      DATE        NOT NULL COMMENT '등록일자 (PK)',
    SEQ          BIGINT      NOT NULL COMMENT '일련번호 (PK)',
    EMP_ID       VARCHAR(255) NOT NULL COMMENT '직원번호 (PK)',
    RGST_TM      TIME        NULL     COMMENT '등록시간',
    RGST_EMP_ID  VARCHAR(255) NULL     COMMENT '등록직원번호',
    UPDT_DT      DATE        NULL     COMMENT '수정일자',
    UPDT_TM      TIME        NULL     COMMENT '수정시간',
    UPDT_EMP_ID  VARCHAR(255) NULL     COMMENT '수정직원번호',
    DEL_YN       CHAR(1)      NOT NULL DEFAULT 'N' COMMENT '삭제 여부 (Y: 삭제, N: 사용)',
    DEL_DATE     DATETIME     NULL     COMMENT '삭제 일시',
    REG_DATE     DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '등록 일시',
    UPD_DATE     DATETIME     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정 일시',
    PRIMARY KEY (RGST_DT, SEQ, EMP_ID),
    INDEX IDX_DEL_YN (DEL_YN)
) COMMENT '공지사항 발송대상자 명세';

-- =================================================================
-- 2. 프로그램 관리 (Program Management)
-- =================================================================

-- 2.1. 프로그램 목록 (TB_PGM_LST)
CREATE TABLE TB_PGM_LST (
    SEQ             BIGINT       NOT NULL COMMENT '일련번호',
    PGM_ID          VARCHAR(20)  NULL     COMMENT '프로그램번호',
    PGM_NM          VARCHAR(100) NULL     COMMENT '프로그램명',
    PGM_DESC        TEXT         NULL     COMMENT '프로그램설명',
    PGM_TY_CD       VARCHAR(20)  NULL     COMMENT '프로그램종류코드',
    PGM_APP_UNIT_CD VARCHAR(20)  NULL     COMMENT '프로그램신청단위코드',
    PGM_STT_DT      DATE         NULL     COMMENT '프로그램시작일자',
    PGM_END_DT      DATE         NULL     COMMENT '프로그램종료일자',
    PGM_STT_TM      TIME         NULL     COMMENT '프로그램시작시간',
    PGM_END_TM      TIME         NULL     COMMENT '프로그램종료시간',
    PGM_PLCE        VARCHAR(100) NULL     COMMENT '프로그램장소',
    PGM_CNT         INT          NULL     COMMENT '프로그램건수',
    PGM_CNT_LMT     INT          NULL     COMMENT '프로그램건수제한',
    PGM_STS_CD      VARCHAR(20)  NULL     COMMENT '프로그램상태코드',
    RGST_DT         DATE         NULL     COMMENT '등록일자',
    RGST_TM         TIME         NULL     COMMENT '등록시간',
    RGST_EMP_ID     VARCHAR(255)  NULL     COMMENT '등록직원번호',
    UPDT_DT         DATE         NULL     COMMENT '수정일자',
    UPDT_TM         TIME         NULL     COMMENT '수정시간',
    UPDT_EMP_ID     VARCHAR(255)  NULL     COMMENT '수정직원번호',
    DEL_YN          CHAR(1)      NOT NULL DEFAULT 'N' COMMENT '삭제 여부 (Y: 삭제, N: 사용)',
    DEL_DATE        DATETIME     NULL     COMMENT '삭제 일시',
    REG_DATE        DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '등록 일시',
    UPD_DATE        DATETIME     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정 일시',
    PRIMARY KEY (SEQ),
    INDEX IDX_DEL_YN (DEL_YN)
) COMMENT '프로그램 목록';

-- 2.2. 프로그램 사전과제 명세 (TB_PGM_PRE_ASGN_DTL)
CREATE TABLE TB_PGM_PRE_ASGN_DTL (
    PGM_ID          VARCHAR(20) NOT NULL COMMENT '프로그램번호 (PK)',
    PRE_ASGN_SEQ    BIGINT      NOT NULL COMMENT '사전과제일련번호 (PK)',
    PRE_ASGN_TTL    VARCHAR(255) NULL     COMMENT '사전과제제목',
    PRE_ASGN_CNTN   TEXT         NULL     COMMENT '사전과제내용',
    PRE_ASGN_DUE_DT DATE         NULL     COMMENT '사전과제마감일자',
    PRE_ASGN_DUE_TM TIME         NULL     COMMENT '사전과제마감시간',
    RGST_DT         DATE         NULL     COMMENT '등록일자',
    RGST_TM         TIME         NULL     COMMENT '등록시간',
    RGST_EMP_ID     VARCHAR(255)  NULL     COMMENT '등록직원번호',
    UPDT_DT         DATE         NULL     COMMENT '수정일자',
    UPDT_TM         TIME         NULL     COMMENT '수정시간',
    UPDT_EMP_ID     VARCHAR(255)  NULL     COMMENT '수정직원번호',
    DEL_YN          CHAR(1)      NOT NULL DEFAULT 'N' COMMENT '삭제 여부 (Y: 삭제, N: 사용)',
    DEL_DATE        DATETIME     NULL     COMMENT '삭제 일시',
    REG_DATE        DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '등록 일시',
    UPD_DATE        DATETIME     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정 일시',
    PRIMARY KEY (PGM_ID, PRE_ASGN_SEQ),
    INDEX IDX_DEL_YN (DEL_YN)
) COMMENT '프로그램 사전과제 명세';

-- 2.3. 프로그램 콘텐츠 발송 명세 (TB_PGM_CNTN_TRSM_DTL)
CREATE TABLE TB_PGM_CNTN_TRSM_DTL (
    PGM_ID          VARCHAR(20) NOT NULL COMMENT '프로그램번호 (PK)',
    CNTN_SEQ        BIGINT      NOT NULL COMMENT '콘텐츠일련번호 (PK)',
    CNTN_TTL        VARCHAR(255) NULL     COMMENT '콘텐츠제목',
    CNTN_CNTN       TEXT        NULL     COMMENT '콘텐츠내용',
    CNTN_TRSM_DT    DATE        NULL     COMMENT '콘텐츠발송일자',
    CNTN_TRSM_TM    TIME        NULL     COMMENT '콘텐츠발송시간',
    RGST_DT         DATE        NULL     COMMENT '등록일자',
    RGST_TM         TIME        NULL     COMMENT '등록시간',
    RGST_EMP_ID     VARCHAR(255)  NULL     COMMENT '등록직원번호',
    UPDT_DT         DATE        NULL     COMMENT '수정일자',
    UPDT_TM         TIME        NULL     COMMENT '수정시간',
    UPDT_EMP_ID     VARCHAR(255)  NULL     COMMENT '수정직원번호',
    DEL_YN          CHAR(1)      NOT NULL DEFAULT 'N' COMMENT '삭제 여부 (Y: 삭제, N: 사용)',
    DEL_DATE        DATETIME     NULL     COMMENT '삭제 일시',
    REG_DATE        DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '등록 일시',
    UPD_DATE        DATETIME     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정 일시',
    PRIMARY KEY (PGM_ID, CNTN_SEQ),
    INDEX IDX_DEL_YN (DEL_YN)
) COMMENT '프로그램 콘텐츠 발송 명세';

-- 2.4. 콘텐츠 발송대상자 명세 (TB_CNTN_TRSM_TRGT_DTL)
CREATE TABLE TB_CNTN_TRSM_TRGT_DTL (
    PGM_ID          VARCHAR(20) NOT NULL COMMENT '프로그램번호 (PK)',
    CNTN_SEQ        BIGINT      NOT NULL COMMENT '콘텐츠일련번호 (PK)',
    EMP_ID          VARCHAR(255) NOT NULL COMMENT '직원번호 (PK)',
    RGST_DT         DATE         NULL     COMMENT '등록일자',
    RGST_TM         TIME         NULL     COMMENT '등록시간',
    RGST_EMP_ID     VARCHAR(255)  NULL     COMMENT '등록직원번호',
    UPDT_DT         DATE         NULL     COMMENT '수정일자',
    UPDT_TM         TIME        NULL     COMMENT '수정시간',
    UPDT_EMP_ID     VARCHAR(255)  NULL     COMMENT '수정직원번호',
    DEL_YN          CHAR(1)      NOT NULL DEFAULT 'N' COMMENT '삭제 여부 (Y: 삭제, N: 사용)',
    DEL_DATE        DATETIME     NULL     COMMENT '삭제 일시',
    REG_DATE        DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '등록 일시',
    UPD_DATE        DATETIME     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정 일시',
    PRIMARY KEY (PGM_ID, CNTN_SEQ, EMP_ID),
    INDEX IDX_DEL_YN (DEL_YN)
) COMMENT '콘텐츠 발송대상자 명세';

-- =================================================================
-- 3. 상담관리시스템 (Counseling Management System)
-- =================================================================

-- 3.1. 일반 상담 (TB_GNRL_CNSL)
CREATE TABLE TB_GNRL_CNSL (
    APP_DT          DATE        NOT NULL COMMENT '신청일자 (PK)',
    CNSL_APP_SEQ    BIGINT      NOT NULL COMMENT '상담신청일련번호 (PK)',
    CNSL_SEQ        BIGINT      NOT NULL COMMENT '상담일련번호 (PK)',
    CNSL_TY_CD      VARCHAR(20) NULL     COMMENT '상담종류코드',
    CNSL_LCTN_CD    VARCHAR(20) NULL     COMMENT '상담장소코드',
    CNSL_DT         DATE        NULL     COMMENT '상담일자',
    CNSL_TM         TIME        NULL     COMMENT '상담시간',
    CNSL_DUR        INT         NULL     COMMENT '상담시간(분)',
    CNSL_CNTN       TEXT        NULL     COMMENT '상담내용',
    CNSL_STS_CD     VARCHAR(20) NULL     COMMENT '상담상태코드',
    RGST_DT         DATE        NULL     COMMENT '등록일자',
    RGST_TM         TIME        NULL     COMMENT '등록시간',
    RGST_EMP_ID     VARCHAR(255) NULL     COMMENT '등록직원번호',
    UPDT_DT         DATE        NULL     COMMENT '수정일자',
    UPDT_TM         TIME        NULL     COMMENT '수정시간',
    UPDT_EMP_ID     VARCHAR(255) NULL     COMMENT '수정직원번호',
    DEL_YN          CHAR(1)      NOT NULL DEFAULT 'N' COMMENT '삭제 여부 (Y: 삭제, N: 사용)',
    DEL_DATE        DATETIME     NULL     COMMENT '삭제 일시',
    REG_DATE        DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '등록 일시',
    UPD_DATE        DATETIME     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정 일시',
    PRIMARY KEY (APP_DT, CNSL_APP_SEQ, CNSL_SEQ),
    INDEX IDX_DEL_YN (DEL_YN)
) COMMENT '일반 상담';

-- 3.2. 일반 상담 명세 (TB_GNRL_CNSL_DTL)
CREATE TABLE TB_GNRL_CNSL_DTL (
    APP_DT          DATE        NOT NULL COMMENT '신청일자 (PK)',
    CNSL_APP_SEQ    BIGINT      NOT NULL COMMENT '상담신청일련번호 (PK)',
    CNSL_SEQ        BIGINT      NOT NULL COMMENT '상담일련번호 (PK)',
    DTL_SEQ         BIGINT      NOT NULL COMMENT '명세일련번호 (PK)',
    CNSL_DTL_CNTN   TEXT        NULL     COMMENT '상담명세내용',
    CNSL_DTL_RCMD   TEXT        NULL     COMMENT '상담명세추천',
    RGST_DT         DATE        NULL     COMMENT '등록일자',
    RGST_TM         TIME        NULL     COMMENT '등록시간',
    RGST_EMP_ID     VARCHAR(255) NULL     COMMENT '등록직원번호',
    UPDT_DT         DATE        NULL     COMMENT '수정일자',
    UPDT_TM         TIME        NULL     COMMENT '수정시간',
    UPDT_EMP_ID     VARCHAR(255) NULL     COMMENT '수정직원번호',
    DEL_YN          CHAR(1)      NOT NULL DEFAULT 'N' COMMENT '삭제 여부 (Y: 삭제, N: 사용)',
    DEL_DATE        DATETIME     NULL     COMMENT '삭제 일시',
    REG_DATE        DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '등록 일시',
    UPD_DATE        DATETIME     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정 일시',
    PRIMARY KEY (APP_DT, CNSL_APP_SEQ, CNSL_SEQ, DTL_SEQ),
    INDEX IDX_DEL_YN (DEL_YN)
) COMMENT '일반 상담 명세';

-- 3.3. 희망 심리검사 명세 (TB_DSRD_PSY_TEST_DTL)
CREATE TABLE TB_DSRD_PSY_TEST_DTL (
    APP_DT          DATE        NOT NULL COMMENT '신청일자 (PK)',
    CNSL_APP_SEQ    BIGINT      NOT NULL COMMENT '상담신청일련번호 (PK)',
    CNSL_SEQ        BIGINT      NOT NULL COMMENT '상담일련번호 (PK)',
    PSY_TEST_CD     VARCHAR(20) NOT NULL COMMENT '심리검사코드 (PK)',
    PSY_TEST_NM     VARCHAR(100) NULL     COMMENT '심리검사명',
    PSY_TEST_DESC   TEXT        NULL     COMMENT '심리검사설명',
    RGST_DT         DATE        NULL     COMMENT '등록일자',
    RGST_TM         TIME        NULL     COMMENT '등록시간',
    RGST_EMP_ID     VARCHAR(255) NULL     COMMENT '등록직원번호',
    UPDT_DT         DATE        NULL     COMMENT '수정일자',
    UPDT_TM         TIME        NULL     COMMENT '수정시간',
    UPDT_EMP_ID     VARCHAR(255) NULL     COMMENT '수정직원번호',
    DEL_YN          CHAR(1)      NOT NULL DEFAULT 'N' COMMENT '삭제 여부 (Y: 삭제, N: 사용)',
    DEL_DATE        DATETIME     NULL     COMMENT '삭제 일시',
    REG_DATE        DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '등록 일시',
    UPD_DATE        DATETIME     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정 일시',
    PRIMARY KEY (APP_DT, CNSL_APP_SEQ, CNSL_SEQ, PSY_TEST_CD),
    INDEX IDX_DEL_YN (DEL_YN)
) COMMENT '희망 심리검사 명세';

-- 3.4. 비대면 상담 (TB_RMT_CNSL)
CREATE TABLE TB_RMT_CNSL (
    APP_DT          DATE        NOT NULL COMMENT '신청일자 (PK)',
    CNSL_APP_SEQ    BIGINT      NOT NULL COMMENT '상담신청일련번호 (PK)',
    CMT_SEQ         BIGINT      NOT NULL COMMENT '댓글일련번호 (PK)',
    CMT_TY_CD       VARCHAR(20) NULL     COMMENT '댓글종류코드',
    CMT_DT          DATE        NULL     COMMENT '댓글일자',
    CMT_TM          TIME        NULL     COMMENT '댓글시간',
    CMT_DUR         INT         NULL     COMMENT '댓글시간(분)',
    CMT_CNTN        TEXT        NULL     COMMENT '댓글내용',
    CMT_STS_CD      VARCHAR(20) NULL     COMMENT '댓글상태코드',
    RGST_DT         DATE        NULL     COMMENT '등록일자',
    RGST_TM         TIME        NULL     COMMENT '등록시간',
    RGST_EMP_ID     VARCHAR(255) NULL     COMMENT '등록직원번호',
    UPDT_DT         DATE        NULL     COMMENT '수정일자',
    UPDT_TM         TIME        NULL     COMMENT '수정시간',
    UPDT_EMP_ID     VARCHAR(255) NULL     COMMENT '수정직원번호',
    DEL_YN          CHAR(1)      NOT NULL DEFAULT 'N' COMMENT '삭제 여부 (Y: 삭제, N: 사용)',
    DEL_DATE        DATETIME     NULL     COMMENT '삭제 일시',
    REG_DATE        DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '등록 일시',
    UPD_DATE        DATETIME     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정 일시',
    PRIMARY KEY (APP_DT, CNSL_APP_SEQ, CMT_SEQ),
    INDEX IDX_DEL_YN (DEL_YN)
) COMMENT '비대면 상담';

-- 3.5. 비대면 상담 명세 (TB_RMT_CNSL_DTL)
CREATE TABLE TB_RMT_CNSL_DTL (
    APP_DT          DATE        NOT NULL COMMENT '신청일자 (PK)',
    CNSL_APP_SEQ    BIGINT      NOT NULL COMMENT '상담신청일련번호 (PK)',
    CMT_SEQ         BIGINT      NOT NULL COMMENT '댓글일련번호 (PK)',
    DTL_SEQ         BIGINT      NOT NULL COMMENT '명세일련번호 (PK)',
    CMT_DTL_CNTN    TEXT        NULL     COMMENT '댓글명세내용',
    CMT_DTL_RCMD    TEXT        NULL     COMMENT '댓글명세추천',
    RGST_DT         DATE        NULL     COMMENT '등록일자',
    RGST_TM         TIME        NULL     COMMENT '등록시간',
    RGST_EMP_ID     VARCHAR(255) NULL     COMMENT '등록직원번호',
    UPDT_DT         DATE        NULL     COMMENT '수정일자',
    UPDT_TM         TIME        NULL     COMMENT '수정시간',
    UPDT_EMP_ID     VARCHAR(255) NULL     COMMENT '수정직원번호',
    DEL_YN          CHAR(1)      NOT NULL DEFAULT 'N' COMMENT '삭제 여부 (Y: 삭제, N: 사용)',
    DEL_DATE        DATETIME     NULL     COMMENT '삭제 일시',
    REG_DATE        DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '등록 일시',
    UPD_DATE        DATETIME     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정 일시',
    PRIMARY KEY (APP_DT, CNSL_APP_SEQ, CMT_SEQ, DTL_SEQ),
    INDEX IDX_DEL_YN (DEL_YN)
) COMMENT '비대면 상담 명세';

-- =================================================================
-- 4. 권한관리 (Authority Management)
-- =================================================================

-- 4.1. 권한그룹 목록 (TB_AUTH_GRP_LST)
CREATE TABLE TB_AUTH_GRP_LST (
    AUTH_GRP_CD VARCHAR(20)  NOT NULL COMMENT '권한그룹코드',
    AUTH_GRP_NM VARCHAR(100) NULL     COMMENT '권한그룹명',
    AUTH_GRP_DESC TEXT       NULL     COMMENT '권한그룹설명',
    DEL_YN      CHAR(1)      NOT NULL DEFAULT 'N' COMMENT '삭제 여부 (Y: 삭제, N: 사용)',
    DEL_DATE    DATETIME     NULL     COMMENT '삭제 일시',
    REG_DATE    DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '등록 일시',
    UPD_DATE    DATETIME     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정 일시',
    PRIMARY KEY (AUTH_GRP_CD),
    INDEX IDX_DEL_YN (DEL_YN)
) COMMENT '권한그룹 목록';

-- 4.2. 관리자 목록 (TB_MGR_LST)
CREATE TABLE TB_MGR_LST (
    MGR_EMP_ID  VARCHAR(255) NOT NULL COMMENT '관리자직원번호',
    MGR_NM      VARCHAR(100) NULL     COMMENT '관리자명',
    MGR_DEPT_CD VARCHAR(20)  NULL     COMMENT '관리자부서코드',
    MGR_POS_CD  VARCHAR(20)  NULL     COMMENT '관리자직급코드',
    MGR_STS_CD  VARCHAR(20)  NULL     COMMENT '관리자상태코드',
    RGST_DT     DATE         NULL     COMMENT '등록일자',
    RGST_TM     TIME         NULL     COMMENT '등록시간',
    RGST_EMP_ID VARCHAR(255)  NULL     COMMENT '등록직원번호',
    UPDT_DT     DATE         NULL     COMMENT '수정일자',
    UPDT_TM     TIME         NULL     COMMENT '수정시간',
    UPDT_EMP_ID VARCHAR(255)  NULL     COMMENT '수정직원번호',
    DEL_YN      CHAR(1)      NOT NULL DEFAULT 'N' COMMENT '삭제 여부 (Y: 삭제, N: 사용)',
    DEL_DATE    DATETIME     NULL     COMMENT '삭제 일시',
    REG_DATE    DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '등록 일시',
    UPD_DATE    DATETIME     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정 일시',
    PRIMARY KEY (MGR_EMP_ID),
    INDEX IDX_DEL_YN (DEL_YN)
) COMMENT '관리자 목록';

-- 4.3. 권한 목록 (TB_AUTH_LST)
CREATE TABLE TB_AUTH_LST (
    AUTH_CD     VARCHAR(20)  NOT NULL COMMENT '권한코드',
    AUTH_NM     VARCHAR(100) NULL     COMMENT '권한명',
    AUTH_DESC   TEXT         NULL     COMMENT '권한설명',
    DEL_YN      CHAR(1)      NOT NULL DEFAULT 'N' COMMENT '삭제 여부 (Y: 삭제, N: 사용)',
    DEL_DATE    DATETIME     NULL     COMMENT '삭제 일시',
    REG_DATE    DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '등록 일시',
    UPD_DATE    DATETIME     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정 일시',
    PRIMARY KEY (AUTH_CD),
    INDEX IDX_DEL_YN (DEL_YN)
) COMMENT '권한 목록';

-- 4.4. 메뉴 목록 (TB_MNU_LST)
CREATE TABLE TB_MNU_LST (
    MNU_CD      VARCHAR(20)  NOT NULL COMMENT '메뉴코드',
    MNU_NM      VARCHAR(100) NULL     COMMENT '메뉴명',
    MNU_URL     VARCHAR(255) NULL     COMMENT '메뉴URL',
    MNU_DESC    TEXT         NULL     COMMENT '메뉴설명',
    DEL_YN      CHAR(1)      NOT NULL DEFAULT 'N' COMMENT '삭제 여부 (Y: 삭제, N: 사용)',
    DEL_DATE    DATETIME     NULL     COMMENT '삭제 일시',
    REG_DATE    DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '등록 일시',
    UPD_DATE    DATETIME     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정 일시',
    PRIMARY KEY (MNU_CD),
    INDEX IDX_DEL_YN (DEL_YN)
) COMMENT '메뉴 목록';

-- 4.5. 권한그룹-권한 매핑 (TB_AUTH_GRP_AUTH_MAP)
CREATE TABLE TB_AUTH_GRP_AUTH_MAP (
    AUTH_GRP_CD VARCHAR(20)  NOT NULL COMMENT '권한그룹코드 (PK)',
    AUTH_CD     VARCHAR(20)  NOT NULL COMMENT '권한코드 (PK)',
    RGST_DT     DATE         NULL     COMMENT '등록일자',
    RGST_TM     TIME         NULL     COMMENT '등록시간',
    RGST_EMP_ID VARCHAR(255)  NULL     COMMENT '등록직원번호',
    UPDT_DT     DATE         NULL     COMMENT '수정일자',
    UPDT_TM     TIME         NULL     COMMENT '수정시간',
    UPDT_EMP_ID VARCHAR(255)  NULL     COMMENT '수정직원번호',
    DEL_YN      CHAR(1)      NOT NULL DEFAULT 'N' COMMENT '삭제 여부 (Y: 삭제, N: 사용)',
    DEL_DATE    DATETIME     NULL     COMMENT '삭제 일시',
    REG_DATE    DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '등록 일시',
    UPD_DATE    DATETIME     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정 일시',
    PRIMARY KEY (AUTH_GRP_CD, AUTH_CD),
    INDEX IDX_DEL_YN (DEL_YN),
    FOREIGN KEY (AUTH_GRP_CD) REFERENCES TB_AUTH_GRP_LST(AUTH_GRP_CD),
    FOREIGN KEY (AUTH_CD) REFERENCES TB_AUTH_LST(AUTH_CD)
) COMMENT '권한그룹-권한 매핑';

-- 4.6. 권한-메뉴 매핑 (TB_AUTH_MNU_MAP)
CREATE TABLE TB_AUTH_MNU_MAP (
    AUTH_CD     VARCHAR(20)  NOT NULL COMMENT '권한코드 (PK)',
    MNU_CD      VARCHAR(20)  NOT NULL COMMENT '메뉴코드 (PK)',
    ACCESS_YN   CHAR(1)      NOT NULL DEFAULT 'Y' COMMENT '접근권한여부 (Y: 허용, N: 거부)',
    READ_YN     CHAR(1)      NOT NULL DEFAULT 'Y' COMMENT '조회권한여부 (Y: 허용, N: 거부)',
    WRITE_YN    CHAR(1)      NOT NULL DEFAULT 'N' COMMENT '등록권한여부 (Y: 허용, N: 거부)',
    UPDATE_YN   CHAR(1)      NOT NULL DEFAULT 'N' COMMENT '수정권한여부 (Y: 허용, N: 거부)',
    DELETE_YN   CHAR(1)      NOT NULL DEFAULT 'N' COMMENT '삭제권한여부 (Y: 허용, N: 거부)',
    RGST_DT     DATE         NULL     COMMENT '등록일자',
    RGST_TM     TIME         NULL     COMMENT '등록시간',
    RGST_EMP_ID VARCHAR(255)  NULL     COMMENT '등록직원번호',
    UPDT_DT     DATE         NULL     COMMENT '수정일자',
    UPDT_TM     TIME         NULL     COMMENT '수정시간',
    UPDT_EMP_ID VARCHAR(255)  NULL     COMMENT '수정직원번호',
    DEL_YN      CHAR(1)      NOT NULL DEFAULT 'N' COMMENT '삭제 여부 (Y: 삭제, N: 사용)',
    DEL_DATE    DATETIME     NULL     COMMENT '삭제 일시',
    REG_DATE    DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '등록 일시',
    UPD_DATE    DATETIME     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정 일시',
    PRIMARY KEY (AUTH_CD, MNU_CD),
    INDEX IDX_DEL_YN (DEL_YN),
    FOREIGN KEY (AUTH_CD) REFERENCES TB_AUTH_LST(AUTH_CD),
    FOREIGN KEY (MNU_CD) REFERENCES TB_MNU_LST(MNU_CD)
) COMMENT '권한-메뉴 매핑';

-- 4.7. 관리자-권한그룹 매핑 (TB_MGR_AUTH_GRP_MAP)
CREATE TABLE TB_MGR_AUTH_GRP_MAP (
    MGR_EMP_ID  VARCHAR(255) NOT NULL COMMENT '관리자직원번호 (PK)',
    AUTH_GRP_CD VARCHAR(20)  NOT NULL COMMENT '권한그룹코드 (PK)',
    RGST_DT     DATE         NULL     COMMENT '등록일자',
    RGST_TM     TIME         NULL     COMMENT '등록시간',
    RGST_EMP_ID VARCHAR(255)  NULL     COMMENT '등록직원번호',
    UPDT_DT     DATE         NULL     COMMENT '수정일자',
    UPDT_TM     TIME         NULL     COMMENT '수정시간',
    UPDT_EMP_ID VARCHAR(255)  NULL     COMMENT '수정직원번호',
    DEL_YN      CHAR(1)      NOT NULL DEFAULT 'N' COMMENT '삭제 여부 (Y: 삭제, N: 사용)',
    DEL_DATE    DATETIME     NULL     COMMENT '삭제 일시',
    REG_DATE    DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '등록 일시',
    UPD_DATE    DATETIME     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정 일시',
    PRIMARY KEY (MGR_EMP_ID, AUTH_GRP_CD),
    INDEX IDX_DEL_YN (DEL_YN),
    FOREIGN KEY (MGR_EMP_ID) REFERENCES TB_MGR_LST(MGR_EMP_ID),
    FOREIGN KEY (AUTH_GRP_CD) REFERENCES TB_AUTH_GRP_LST(AUTH_GRP_CD)
) COMMENT '관리자-권한그룹 매핑';

-- =================================================================
-- 5. 직원 관리 (Employee Management)
-- =================================================================

-- 5.1. 직원 목록 (TB_EMP_LST)
CREATE TABLE TB_EMP_LST (
    EMP_ID      VARCHAR(255) NOT NULL COMMENT '직원번호',
    EMP_NM      VARCHAR(100) NULL     COMMENT '직원명',
    EMP_DEPT_CD VARCHAR(20)  NULL     COMMENT '직원부서코드',
    EMP_POS_CD  VARCHAR(20)  NULL     COMMENT '직원직급코드',
    EMP_STS_CD  VARCHAR(20)  NULL     COMMENT '직원상태코드',
    RGST_DT     DATE         NULL     COMMENT '등록일자',
    RGST_TM     TIME         NULL     COMMENT '등록시간',
    RGST_EMP_ID VARCHAR(255)  NULL     COMMENT '등록직원번호',
    UPDT_DT     DATE         NULL     COMMENT '수정일자',
    UPDT_TM     TIME         NULL     COMMENT '수정시간',
    UPDT_EMP_ID VARCHAR(255)  NULL     COMMENT '수정직원번호',
    DEL_YN      CHAR(1)      NOT NULL DEFAULT 'N' COMMENT '삭제 여부 (Y: 삭제, N: 사용)',
    DEL_DATE    DATETIME     NULL     COMMENT '삭제 일시',
    REG_DATE    DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '등록 일시',
    UPD_DATE    DATETIME     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정 일시',
    PRIMARY KEY (EMP_ID),
    INDEX IDX_DEL_YN (DEL_YN)
) COMMENT '직원 목록';

-- 5.2. 부서 목록 (TB_DEPT_LST)
CREATE TABLE TB_DEPT_LST (
    DEPT_CD     VARCHAR(20)  NOT NULL COMMENT '부서코드',
    DEPT_NM     VARCHAR(100) NULL     COMMENT '부서명',
    DEPT_DESC   TEXT         NULL     COMMENT '부서설명',
    DEPT_MGR_EMP_ID VARCHAR(255)  NULL     COMMENT '부서장직원번호',
    DEPT_STS_CD VARCHAR(20)  NULL     COMMENT '부서상태코드',
    RGST_DT     DATE         NULL     COMMENT '등록일자',
    RGST_TM     TIME         NULL     COMMENT '등록시간',
    RGST_EMP_ID VARCHAR(255)  NULL     COMMENT '등록직원번호',
    UPDT_DT     DATE         NULL     COMMENT '수정일자',
    UPDT_TM     TIME         NULL     COMMENT '수정시간',
    UPDT_EMP_ID VARCHAR(255)  NULL     COMMENT '수정직원번호',
    DEL_YN      CHAR(1)      NOT NULL DEFAULT 'N' COMMENT '삭제 여부 (Y: 삭제, N: 사용)',
    DEL_DATE    DATETIME     NULL     COMMENT '삭제 일시',
    REG_DATE    DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '등록 일시',
    UPD_DATE    DATETIME     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정 일시',
    PRIMARY KEY (DEPT_CD),
    INDEX IDX_DEL_YN (DEL_YN)
) COMMENT '부서 목록';

-- 5.3. 상담사 목록 (TB_CNSLR_LST)
CREATE TABLE TB_CNSLR_LST (
    CNSLR_EMP_ID VARCHAR(255) NOT NULL COMMENT '상담사직원번호',
    CNSLR_NM     VARCHAR(100) NULL     COMMENT '상담사명',
    CNSLR_DEPT_CD VARCHAR(20)  NULL     COMMENT '상담사부서코드',
    CNSLR_POS_CD VARCHAR(20)  NULL     COMMENT '상담사직급코드',
    CNSLR_STS_CD VARCHAR(20)  NULL     COMMENT '상담사상태코드',
    CNSLR_SPEC_CD VARCHAR(20)  NULL     COMMENT '상담사전문분야코드',
    RGST_DT      DATE         NULL     COMMENT '등록일자',
    RGST_TM      TIME         NULL     COMMENT '등록시간',
    RGST_EMP_ID  VARCHAR(255)  NULL     COMMENT '등록직원번호',
    UPDT_DT      DATE         NULL     COMMENT '수정일자',
    UPDT_TM      TIME         NULL     COMMENT '수정시간',
    UPDT_EMP_ID  VARCHAR(255)  NULL     COMMENT '수정직원번호',
    DEL_YN       CHAR(1)      NOT NULL DEFAULT 'N' COMMENT '삭제 여부 (Y: 삭제, N: 사용)',
    DEL_DATE     DATETIME     NULL     COMMENT '삭제 일시',
    REG_DATE     DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '등록 일시',
    UPD_DATE     DATETIME     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정 일시',
    PRIMARY KEY (CNSLR_EMP_ID),
    INDEX IDX_DEL_YN (DEL_YN)
) COMMENT '상담사 목록';

-- =================================================================
-- 6. 샘플 데이터 삽입 (Sample Data Insertion)
-- =================================================================

-- 6.1. 권한그룹 목록 샘플 데이터
INSERT INTO TB_AUTH_GRP_LST (AUTH_GRP_CD, AUTH_GRP_NM, AUTH_GRP_DESC) VALUES
('GRP_001', '시스템 관리자', '전체 시스템 관리 권한'),
('GRP_002', '부서 관리자', '부서별 관리 권한'),
('GRP_003', '일반 사용자', '기본 사용 권한'),
('GRP_004', '상담사', '상담 관련 권한'),
('GRP_005', '프로그램 관리자', '프로그램 관리 권한');

-- 6.2. 권한 목록 샘플 데이터
INSERT INTO TB_AUTH_LST (AUTH_CD, AUTH_NM, AUTH_DESC) VALUES
('AUTH_001', '사용자 관리', '사용자 정보 조회/등록/수정/삭제'),
('AUTH_002', '부서 관리', '부서 정보 조회/등록/수정/삭제'),
('AUTH_003', '프로그램 관리', '프로그램 정보 조회/등록/수정/삭제'),
('AUTH_004', '상담 관리', '상담 정보 조회/등록/수정/삭제'),
('AUTH_005', '공지사항 관리', '공지사항 조회/등록/수정/삭제'),
('AUTH_006', '통계 조회', '통계 정보 조회'),
('AUTH_007', '권한 관리', '권한 정보 조회/등록/수정/삭제'),
('AUTH_008', '메뉴 관리', '메뉴 정보 조회/등록/수정/삭제');

-- 6.3. 메뉴 목록 샘플 데이터
INSERT INTO TB_MNU_LST (MNU_CD, MNU_NM, MNU_URL, MNU_DESC) VALUES
('MNU_001', '대시보드', '/dashboard', '메인 대시보드'),
('MNU_002', '사용자 관리', '/user/management', '사용자 정보 관리'),
('MNU_003', '부서 관리', '/dept/management', '부서 정보 관리'),
('MNU_004', '프로그램 관리', '/program/management', '프로그램 정보 관리'),
('MNU_005', '상담 관리', '/counseling/management', '상담 정보 관리'),
('MNU_006', '공지사항 관리', '/notice/management', '공지사항 관리'),
('MNU_007', '통계 조회', '/statistics', '통계 정보 조회'),
('MNU_008', '권한 관리', '/auth/management', '권한 정보 관리'),
('MNU_009', '메뉴 관리', '/menu/management', '메뉴 정보 관리'),
('MNU_010', '암호화 관리', '/encryption/management', '직원 정보 암호화 관리');

-- 6.4. 권한그룹-권한 매핑 샘플 데이터
INSERT INTO TB_AUTH_GRP_AUTH_MAP (AUTH_GRP_CD, AUTH_CD, RGST_EMP_ID) VALUES
-- 시스템 관리자: 모든 권한
('GRP_001', 'AUTH_001', 'ADMIN001'),
('GRP_001', 'AUTH_002', 'ADMIN001'),
('GRP_001', 'AUTH_003', 'ADMIN001'),
('GRP_001', 'AUTH_004', 'ADMIN001'),
('GRP_001', 'AUTH_005', 'ADMIN001'),
('GRP_001', 'AUTH_006', 'ADMIN001'),
('GRP_001', 'AUTH_007', 'ADMIN001'),
('GRP_001', 'AUTH_008', 'ADMIN001'),
-- 부서 관리자: 제한된 권한
('GRP_002', 'AUTH_001', 'ADMIN001'),
('GRP_002', 'AUTH_002', 'ADMIN001'),
('GRP_002', 'AUTH_003', 'ADMIN001'),
('GRP_002', 'AUTH_006', 'ADMIN001'),
-- 일반 사용자: 기본 권한
('GRP_003', 'AUTH_006', 'ADMIN001'),
-- 상담사: 상담 관련 권한
('GRP_004', 'AUTH_004', 'ADMIN001'),
('GRP_004', 'AUTH_006', 'ADMIN001'),
-- 프로그램 관리자: 프로그램 관련 권한
('GRP_005', 'AUTH_003', 'ADMIN001'),
('GRP_005', 'AUTH_006', 'ADMIN001');

-- 6.5. 권한-메뉴 매핑 샘플 데이터
INSERT INTO TB_AUTH_MNU_MAP (AUTH_CD, MNU_CD, ACCESS_YN, READ_YN, WRITE_YN, UPDATE_YN, DELETE_YN, RGST_EMP_ID) VALUES
-- 사용자 관리 권한
('AUTH_001', 'MNU_002', 'Y', 'Y', 'Y', 'Y', 'Y', 'ADMIN001'),
-- 부서 관리 권한
('AUTH_002', 'MNU_003', 'Y', 'Y', 'Y', 'Y', 'Y', 'ADMIN001'),
-- 프로그램 관리 권한
('AUTH_003', 'MNU_004', 'Y', 'Y', 'Y', 'Y', 'Y', 'ADMIN001'),
-- 상담 관리 권한
('AUTH_004', 'MNU_005', 'Y', 'Y', 'Y', 'Y', 'Y', 'ADMIN001'),
-- 공지사항 관리 권한
('AUTH_005', 'MNU_006', 'Y', 'Y', 'Y', 'Y', 'Y', 'ADMIN001'),
-- 통계 조회 권한
('AUTH_006', 'MNU_007', 'Y', 'Y', 'N', 'N', 'N', 'ADMIN001'),
-- 권한 관리 권한
('AUTH_007', 'MNU_008', 'Y', 'Y', 'Y', 'Y', 'Y', 'ADMIN001'),
-- 메뉴 관리 권한
('AUTH_008', 'MNU_009', 'Y', 'Y', 'Y', 'Y', 'Y', 'ADMIN001');

-- 6.6. 관리자-권한그룹 매핑 샘플 데이터
INSERT INTO TB_MGR_AUTH_GRP_MAP (MGR_EMP_ID, AUTH_GRP_CD, RGST_EMP_ID) VALUES
('MGR001', 'GRP_001', 'ADMIN001'),
('MGR002', 'GRP_002', 'ADMIN001'),
('MGR003', 'GRP_003', 'ADMIN001'),
('MGR004', 'GRP_004', 'ADMIN001'),
('MGR005', 'GRP_005', 'ADMIN001'); 