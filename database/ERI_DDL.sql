-- =================================================================
-- ERI Employee Rights Protection System (User System) DDL Script
-- Generated based on the provided schema diagram.
-- MySQL 8.0+ Compatible
-- =================================================================

-- 데이터베이스 생성
CREATE DATABASE IF NOT EXISTS eri_db
CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci;

-- 데이터베이스 사용
USE eri_db;

-- =================================================================
-- 1. 셀프케어 관리 (Self-Care Management)
-- =================================================================

-- 1.1. 셀프케어 상태 메시지 (TB_SELF_CARE_STS_MSG)
CREATE TABLE TB_SELF_CARE_STS_MSG (
    SEQ         BIGINT       NOT NULL COMMENT '일련번호',
    STS_MSG_CD  VARCHAR(20)  NULL     COMMENT '상태 메시지 코드',
    STS_MSG_CNTN VARCHAR(255) NULL     COMMENT '상태 메시지 내용',
    STS_MSG_RCMD_CD VARCHAR(20)  NULL     COMMENT '상태 메시지 추천 코드',
    DEL_YN      CHAR(1)      NOT NULL DEFAULT 'N' COMMENT '삭제 여부 (Y: 삭제, N: 사용)',
    DEL_DATE    DATETIME     NULL     COMMENT '삭제 일시',
    REG_EMP_ID  VARCHAR(255) NULL     COMMENT '등록직원번호',
    UPD_EMP_ID  VARCHAR(255) NULL     COMMENT '수정직원번호',
    REG_DATE    DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '등록 일시',
    UPD_DATE    DATETIME     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정 일시',
    PRIMARY KEY (SEQ),
    INDEX IDX_DEL_YN (DEL_YN)
) COMMENT '셀프케어 상태 메시지';

-- 1.2. 셀프케어 배경 이미지 (TB_SELF_CARE_BG_IMG)
CREATE TABLE TB_SELF_CARE_BG_IMG (
    SEQ         BIGINT       NOT NULL COMMENT '일련번호',
    BG_IMG_CD   VARCHAR(20)  NULL     COMMENT '배경 이미지 코드',
    BG_IMG_URL  VARCHAR(255) NULL     COMMENT '배경 이미지 URL 주소',
    BG_IMG_DESC VARCHAR(255) NULL     COMMENT '배경 이미지 설명',
    DEL_YN      CHAR(1)      NOT NULL DEFAULT 'N' COMMENT '삭제 여부 (Y: 삭제, N: 사용)',
    DEL_DATE    DATETIME     NULL     COMMENT '삭제 일시',
    REG_EMP_ID  VARCHAR(255) NULL     COMMENT '등록직원번호',
    UPD_EMP_ID  VARCHAR(255) NULL     COMMENT '수정직원번호',
    REG_DATE    DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '등록 일시',
    UPD_DATE    DATETIME     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정 일시',
    PRIMARY KEY (SEQ),
    INDEX IDX_DEL_YN (DEL_YN)
) COMMENT '셀프케어 배경 이미지';

-- =================================================================
-- 2. 습관형성 챌린지 관리 (Habit Formation Challenge Management)
-- =================================================================

-- 2.1. 습관형성 챌린지 목표 (TB_HBT_CHLG_GOAL)
CREATE TABLE TB_HBT_CHLG_GOAL (
    SEQ         BIGINT       NOT NULL COMMENT '일련번호',
    GOAL_TTL    VARCHAR(255) NULL     COMMENT '목표 제목',
    GOAL_CNTN   TEXT         NULL     COMMENT '목표 내용',
    GOAL_ACHV_UNIT_CD VARCHAR(20)  NULL     COMMENT '목표 달성 단위 코드',
    GOAL_ACHV_TGT_CNT INT          NULL     COMMENT '목표 달성 목표 건수',
    GOAL_ACHV_RCMD_CD VARCHAR(20)  NULL     COMMENT '목표 달성 추천 코드',
    DEL_YN      CHAR(1)      NOT NULL DEFAULT 'N' COMMENT '삭제 여부 (Y: 삭제, N: 사용)',
    DEL_DATE    DATETIME     NULL     COMMENT '삭제 일시',
    REG_EMP_ID  VARCHAR(255) NULL     COMMENT '등록직원번호',
    UPD_EMP_ID  VARCHAR(255) NULL     COMMENT '수정직원번호',
    REG_DATE    DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '등록 일시',
    UPD_DATE    DATETIME     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정 일시',
    PRIMARY KEY (SEQ),
    INDEX IDX_DEL_YN (DEL_YN)
) COMMENT '습관형성 챌린지 목표';

-- 2.2. 챌린지 목표 달성 명세 (TB_CHLG_GOAL_ACHV_DTL)
CREATE TABLE TB_CHLG_GOAL_ACHV_DTL (
    SEQ         BIGINT       NOT NULL COMMENT '일련번호',
    GOAL_SEQ    BIGINT       NULL     COMMENT '목표 일련번호',
    ACHV_DT     DATE         NULL     COMMENT '달성 일자',
    ACHV_CNT    INT          NULL     COMMENT '달성 건수',
    ACHV_CNTN   TEXT         NULL     COMMENT '달성 내용',
    DEL_YN      CHAR(1)      NOT NULL DEFAULT 'N' COMMENT '삭제 여부 (Y: 삭제, N: 사용)',
    DEL_DATE    DATETIME     NULL     COMMENT '삭제 일시',
    REG_EMP_ID  VARCHAR(255) NULL     COMMENT '등록직원번호',
    UPD_EMP_ID  VARCHAR(255) NULL     COMMENT '수정직원번호',
    REG_DATE    DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '등록 일시',
    UPD_DATE    DATETIME     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정 일시',
    PRIMARY KEY (SEQ),
    INDEX IDX_DEL_YN (DEL_YN)
) COMMENT '챌린지 목표 달성 명세';

-- =================================================================
-- 3. 프로그램 신청 (Program Application)
-- =================================================================

-- 3.1. 프로그램_신청_명세 (PROGRAM_APPLICATION_DETAIL)
CREATE TABLE TB_PGM_APP_DTL (
    EMP_ID          VARCHAR(255) NOT NULL COMMENT '직원번호',
    PGM_ID          VARCHAR(20) NOT NULL COMMENT '프로그램번호',
    APP_DT          DATE        NOT NULL COMMENT '신청일자',
    APP_TY_CD       VARCHAR(20) NULL     COMMENT '신청구분코드',
    STS_CD          VARCHAR(20) NULL     COMMENT '상태코드',
    PRE_ASGN_CMPL_YN CHAR(1)     NULL     COMMENT '사전과제수행여부(Y/N)',
    APP_TM          TIME        NULL     COMMENT '신청시간',
    DEL_YN          CHAR(1)      NOT NULL DEFAULT 'N' COMMENT '삭제 여부 (Y: 삭제, N: 사용)',
    DEL_DATE        DATETIME     NULL     COMMENT '삭제 일시',
    REG_EMP_ID      VARCHAR(255) NULL     COMMENT '등록직원번호',
    UPD_EMP_ID      VARCHAR(255) NULL     COMMENT '수정직원번호',
    REG_DATE        DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '등록 일시',
    UPD_DATE        DATETIME     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정 일시',
    PRIMARY KEY (EMP_ID, PGM_ID, APP_DT),
    INDEX IDX_DEL_YN (DEL_YN)
) COMMENT '프로그램 신청 명세';

-- 3.2. 사전과제_수행_명세 (PRE_ASSIGNMENT_PERFORMANCE_DETAIL)
CREATE TABLE TB_PRE_ASGN_PERF_DTL (
    EMP_ID            VARCHAR(255) NOT NULL COMMENT '직원번호',
    PGM_ID            VARCHAR(20) NOT NULL COMMENT '프로그램번호',
    PRE_ASGN_SEQ      BIGINT      NOT NULL COMMENT '사전과제 일련번호',
    PERF_DT           DATE        NULL     COMMENT '과제수행일자',
    SRVY_ITM_CNTN     TEXT        NULL     COMMENT '설문조사항목내용',
    SRVY_ANS_CNTN     TEXT        NULL     COMMENT '설문조사답변내용',
    STS_CD            VARCHAR(20) NULL     COMMENT '상태코드',
    DEL_YN            CHAR(1)      NOT NULL DEFAULT 'N' COMMENT '삭제 여부 (Y: 삭제, N: 사용)',
    DEL_DATE          DATETIME     NULL     COMMENT '삭제 일시',
    REG_EMP_ID        VARCHAR(255) NULL     COMMENT '등록직원번호',
    UPD_EMP_ID        VARCHAR(255) NULL     COMMENT '수정직원번호',
    REG_DATE          DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '등록 일시',
    UPD_DATE          DATETIME     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정 일시',
    PRIMARY KEY (EMP_ID, PGM_ID, PRE_ASGN_SEQ),
    INDEX IDX_DEL_YN (DEL_YN)
) COMMENT '사전과제 수행 명세';

-- 3.3. 프로그램_승인_명세 (PROGRAM_APPROVAL_DETAIL)
CREATE TABLE TB_PGM_APRV_DTL (
    EMP_ID          VARCHAR(255) NOT NULL COMMENT '직원번호',
    PGM_ID          VARCHAR(20) NOT NULL COMMENT '프로그램번호',
    TRNS_DT         DATE        NOT NULL COMMENT '거래일자',
    SEQ             BIGINT      NOT NULL COMMENT '일련번호',
    APRVR_EMP_ID    VARCHAR(255) NULL     COMMENT '승인직원번호',
    APRV_TY_CD      VARCHAR(20) NULL     COMMENT '승인구분코드',
    STS_CD          VARCHAR(20) NULL     COMMENT '상태코드',
    APRV_TM         TIME        NULL     COMMENT '승인시간',
    DEL_YN          CHAR(1)      NOT NULL DEFAULT 'N' COMMENT '삭제 여부 (Y: 삭제, N: 사용)',
    DEL_DATE        DATETIME     NULL     COMMENT '삭제 일시',
    REG_EMP_ID      VARCHAR(255) NULL     COMMENT '등록직원번호',
    UPD_EMP_ID      VARCHAR(255) NULL     COMMENT '수정직원번호',
    REG_DATE        DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '등록 일시',
    UPD_DATE        DATETIME     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정 일시',
    PRIMARY KEY (EMP_ID, PGM_ID, TRNS_DT, SEQ),
    INDEX IDX_DEL_YN (DEL_YN)
) COMMENT '프로그램 승인 명세';

-- =================================================================
-- 4. 상담 신청 (Counseling Application)
-- =================================================================

-- 4.1. 상담_신청 (COUNSELING_APPLICATION)
CREATE TABLE TB_CNSL_APP (
    APP_DT          DATE         NOT NULL COMMENT '신청일자',
    SEQ             BIGINT       NOT NULL COMMENT '일련번호',
    CNSL_TY_CD      VARCHAR(20)  NULL     COMMENT '상담 종류코드',
    DSRD_CNSL_DT    DATE         NULL     COMMENT '상담 희망일자',
    GRVNC_TPC_CD    VARCHAR(20)  NULL     COMMENT '고충 주제코드',
    DSRD_PSY_TEST_CD VARCHAR(20)  NULL     COMMENT '희망 심리검사코드',
    CNSL_LCTN_CD    VARCHAR(20)  NULL     COMMENT '상담 장소코드',
    CNSLR_EMP_ID    VARCHAR(255)  NULL     COMMENT '상담자 직원번호',
    STS_CD          VARCHAR(20)  NULL     COMMENT '상태코드',
    TTL             VARCHAR(255) NULL     COMMENT '상담신청 제목',
    CNTN            TEXT         NULL     COMMENT '신청내용',
    APRV_YN         CHAR(1)      NULL     COMMENT '승인여부(Y/N)',
    APLCNT_EMP_MGMT_ID VARCHAR(20)  NULL     COMMENT '신청직원관리번호',
    DEL_YN          CHAR(1)      NOT NULL DEFAULT 'N' COMMENT '삭제 여부 (Y: 삭제, N: 사용)',
    DEL_DATE        DATETIME     NULL     COMMENT '삭제 일시',
    REG_EMP_ID      VARCHAR(255) NULL     COMMENT '등록직원번호',
    UPD_EMP_ID      VARCHAR(255) NULL     COMMENT '수정직원번호',
    REG_DATE        DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '등록 일시',
    UPD_DATE        DATETIME     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정 일시',
    PRIMARY KEY (APP_DT, SEQ),
    INDEX IDX_DEL_YN (DEL_YN)
) COMMENT '상담 신청';

-- 4.2. 상담_신청_명세 (COUNSELING_APPLICATION_DETAIL)
CREATE TABLE TB_CNSL_APP_DTL (
    APP_DT          DATE        NOT NULL COMMENT '신청일자',
    CNSL_APP_SEQ    BIGINT      NOT NULL COMMENT '상담신청 일련번호',
    TRNS_DT         DATE        NOT NULL COMMENT '거래일자',
    TRNS_SEQ        BIGINT      NOT NULL COMMENT '거래 일련번호',
    APP_TY_CD       VARCHAR(20) NULL     COMMENT '신청구분코드',
    STS_CD          VARCHAR(20) NULL     COMMENT '상태코드',
    APP_TM          TIME        NULL     COMMENT '신청시간',
    DEL_YN          CHAR(1)      NOT NULL DEFAULT 'N' COMMENT '삭제 여부 (Y: 삭제, N: 사용)',
    DEL_DATE        DATETIME     NULL     COMMENT '삭제 일시',
    REG_EMP_ID      VARCHAR(255) NULL     COMMENT '등록직원번호',
    UPD_EMP_ID      VARCHAR(255) NULL     COMMENT '수정직원번호',
    REG_DATE        DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '등록 일시',
    UPD_DATE        DATETIME     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정 일시',
    PRIMARY KEY (APP_DT, CNSL_APP_SEQ, TRNS_DT, TRNS_SEQ),
    INDEX IDX_DEL_YN (DEL_YN)
) COMMENT '상담 신청 명세';

-- 4.3. 상담_승인_명세 (COUNSELING_APPROVAL_DETAIL)
CREATE TABLE TB_CNSL_APRV_DTL (
    APP_DT          DATE        NOT NULL COMMENT '신청일자',
    CNSL_APP_SEQ    BIGINT      NOT NULL COMMENT '상담신청 일련번호',
    TRNS_DT         DATE        NOT NULL COMMENT '거래일자',
    APRVR_EMP_ID    VARCHAR(255) NULL     COMMENT '승인직원번호',
    APRV_TY_CD      VARCHAR(20) NULL     COMMENT '승인구분코드',
    STS_CD          VARCHAR(20) NULL     COMMENT '상태코드',
    APRV_TM         TIME        NULL     COMMENT '승인시간',
    DEL_YN          CHAR(1)      NOT NULL DEFAULT 'N' COMMENT '삭제 여부 (Y: 삭제, N: 사용)',
    DEL_DATE        DATETIME     NULL     COMMENT '삭제 일시',
    REG_EMP_ID      VARCHAR(255) NULL     COMMENT '등록직원번호',
    UPD_EMP_ID      VARCHAR(255) NULL     COMMENT '수정직원번호',
    REG_DATE        DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '등록 일시',
    UPD_DATE        DATETIME     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정 일시',
    PRIMARY KEY (APP_DT, CNSL_APP_SEQ, TRNS_DT),
    INDEX IDX_DEL_YN (DEL_YN)
) COMMENT '상담 승인 명세';

-- =================================================================
-- 5. 바로가기 관리 (Shortcut Management)
-- =================================================================

-- 5.1. 바로가기_목록 (SHORTCUT_LIST)
CREATE TABLE TB_SCT_LST (
    SEQ         BIGINT       NOT NULL COMMENT '일련번호',
    SCT_CD      VARCHAR(20)  NULL     COMMENT '바로가기 코드',
    STS_CD      VARCHAR(20)  NULL     COMMENT '상태코드',
    APLY_DT     DATE         NULL     COMMENT '바로가기 적용일자',
    URL         VARCHAR(255) NULL     COMMENT '바로가기 URL 주소 내용',
    DEL_YN      CHAR(1)      NOT NULL DEFAULT 'N' COMMENT '삭제 여부 (Y: 삭제, N: 사용)',
    DEL_DATE    DATETIME     NULL     COMMENT '삭제 일시',
    REG_EMP_ID  VARCHAR(255) NULL     COMMENT '등록직원번호',
    UPD_EMP_ID  VARCHAR(255) NULL     COMMENT '수정직원번호',
    REG_DATE    DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '등록 일시',
    UPD_DATE    DATETIME     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정 일시',
    PRIMARY KEY (SEQ),
    INDEX IDX_DEL_YN (DEL_YN)
) COMMENT '바로가기 목록';

-- =================================================================
-- 6. 공통 코드 관리 (Common Code Management)
-- =================================================================

-- 6.1. 공통_그룹_코드 (COMMON_GROUP_CODE)
CREATE TABLE TB_CMN_GRP_CD (
    GRP_CD      VARCHAR(20)  NOT NULL COMMENT '그룹 코드',
    GRP_CD_NM   VARCHAR(100) NULL     COMMENT '그룹 코드 명',
    GRP_CD_DESC VARCHAR(255) NULL     COMMENT '그룹 코드 설명',
    USE_YN      CHAR(1)      NOT NULL DEFAULT 'Y' COMMENT '사용여부',
    DEL_YN      CHAR(1)      NOT NULL DEFAULT 'N' COMMENT '삭제 여부 (Y: 삭제, N: 사용)',
    DEL_DATE    DATETIME     NULL     COMMENT '삭제 일시',
    REG_EMP_ID  VARCHAR(255) NULL     COMMENT '등록직원번호',
    UPD_EMP_ID  VARCHAR(255) NULL     COMMENT '수정직원번호',
    REG_DATE    DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '등록 일시',
    UPD_DATE    DATETIME     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정 일시',
    PRIMARY KEY (GRP_CD),
    INDEX IDX_DEL_YN (DEL_YN)
) COMMENT '공통 그룹 코드';

-- 6.2. 공통_상세_코드 (COMMON_DETAIL_CODE)
CREATE TABLE TB_CMN_DTL_CD (
    GRP_CD      VARCHAR(20)  NOT NULL COMMENT '그룹 코드',
    DTL_CD      VARCHAR(20)  NOT NULL COMMENT '상세 코드',
    DTL_CD_NM   VARCHAR(100) NULL     COMMENT '상세 코드 명',
    DTL_CD_DESC VARCHAR(255) NULL     COMMENT '상세 코드 설명',
    SORT_ORD    INT          NULL     COMMENT '정렬 순서',
    USE_YN      CHAR(1)      NOT NULL DEFAULT 'Y' COMMENT '사용여부',
    DEL_YN      CHAR(1)      NOT NULL DEFAULT 'N' COMMENT '삭제 여부 (Y: 삭제, N: 사용)',
    DEL_DATE    DATETIME     NULL     COMMENT '삭제 일시',
    REG_EMP_ID  VARCHAR(255) NULL     COMMENT '등록직원번호',
    UPD_EMP_ID  VARCHAR(255) NULL     COMMENT '수정직원번호',
    REG_DATE    DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '등록 일시',
    UPD_DATE    DATETIME     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정 일시',
    PRIMARY KEY (GRP_CD, DTL_CD),
    INDEX IDX_DEL_YN (DEL_YN)
) COMMENT '공통 상세 코드';

-- =================================================================
-- 공통 코드 예시 데이터 (Sample Data for Common Codes)
-- =================================================================
INSERT INTO TB_CMN_GRP_CD (GRP_CD, GRP_CD_NM, GRP_CD_DESC, USE_YN) VALUES
('GOAL_ACHV_UNIT', '목표 달성 단위 코드', '목표 달성의 단위를 관리하기 위한 코드 (일별/주별/월별)', 'Y'),
('PGM_APP_TY', '프로그램 신청 구분 코드', '프로그램 신청의 구분을 위한 코드 (신청/승인/반려)', 'Y'),
('PGM_APP_STS', '프로그램 신청 상태 코드', '프로그램 신청의 상태를 관리하기 위한 코드 (정상/취소/종료)', 'Y'),
('CNSL_TY', '상담 종류 코드', '상담의 종류를 구분하기 위한 코드 (일반/비대면)', 'Y'),
('SCT_STS', '바로가기 상태 코드', '바로가기의 상태를 관리하기 위한 코드 (정상/취소/종료)', 'Y');

INSERT INTO TB_CMN_DTL_CD (GRP_CD, DTL_CD, DTL_CD_NM, DTL_CD_DESC, SORT_ORD, USE_YN) VALUES
('GOAL_ACHV_UNIT', 'DAILY', '일별', '일별로 목표를 달성하는 단위', 1, 'Y'),
('GOAL_ACHV_UNIT', 'WEEKLY', '주별', '주별로 목표를 달성하는 단위', 2, 'Y'),
('GOAL_ACHV_UNIT', 'MONTHLY', '월별', '월별로 목표를 달성하는 단위', 3, 'Y'),
('PGM_APP_TY', 'APPLY', '신청', '프로그램 신청', 1, 'Y'),
('PGM_APP_TY', 'APPROVE', '승인', '프로그램 승인', 2, 'Y'),
('PGM_APP_TY', 'REJECT', '반려', '프로그램 반려', 3, 'Y'),
('PGM_APP_STS', 'NORMAL', '정상', '정상 상태', 1, 'Y'),
('PGM_APP_STS', 'CANCEL', '취소', '취소된 상태', 2, 'Y'),
('PGM_APP_STS', 'END', '종료', '종료된 상태', 3, 'Y'),
('CNSL_TY', 'GENERAL', '일반', '일반 상담', 1, 'Y'),
('CNSL_TY', 'REMOTE', '비대면', '비대면 상담', 2, 'Y'),
('SCT_STS', 'NORMAL', '정상', '정상 상태', 1, 'Y'),
('SCT_STS', 'CANCEL', '취소', '취소된 상태', 2, 'Y'),
('SCT_STS', 'END', '종료', '종료된 상태', 3, 'Y'); 