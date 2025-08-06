-- =================================================================
-- ERI Employee Rights Protection System (User System) DDL Script
-- Generated based on the provided schema diagram.
-- PostgreSQL 12+ Compatible
-- 
-- 시스템 개요:
-- 이 스크립트는 ERI(Employee Rights Protection) 시스템의 데이터베이스 스키마를 정의합니다.
-- 직원권익보호, 상담 관리, 프로그램 관리, 게시판 관리 등의 기능을 지원합니다.
-- 
-- 주요 기능:
-- 1. 셀프케어 관리 - 직원의 자기관리 및 건강 관리
-- 2. 습관형성 챌린지 - 목표 설정 및 달성 관리
-- 3. 프로그램 신청 - 교육 프로그램 신청 및 승인
-- 4. 상담 신청 - 일반 상담 및 전문가 상담 관리
-- 5. 바로가기 관리 - 시스템 내 바로가기 기능
-- 6. 공통 코드 관리 - 시스템 전반의 코드 관리
-- 7. 직원권익게시판 - 직원 간 소통 및 건의사항 관리
-- 8. 전문가 상담 관리 - 전문 상담사와의 상담 관리
-- 
-- 작성일: 2025년
-- 작성자: 시스템 관리자
-- =================================================================

-- =================================================================
-- 데이터베이스 생성 설정
-- =================================================================
-- 
-- 주의사항:
-- 1. PostgreSQL에서는 CREATE DATABASE를 별도로 실행해야 합니다.
-- 2. 데이터베이스 생성 전에 기존 데이터베이스가 있다면 삭제 후 생성하세요.
-- 3. 한글 지원을 위해 UTF8 인코딩과 한국어 로케일을 설정합니다.
-- 
-- Database: eri_db
-- DROP DATABASE IF EXISTS eri_db;

-- CREATE DATABASE eri_db
--     WITH
--     OWNER = postgres
--     ENCODING = 'UTF8'
--     LC_COLLATE = 'Korean_Korea.949'
--     LC_CTYPE = 'Korean_Korea.949'
--     LOCALE_PROVIDER = 'libc'
--     TABLESPACE = pg_default
--     CONNECTION LIMIT = -1
--     IS_TEMPLATE = False;

-- =================================================================
-- 1. 셀프케어 관리 (Self-Care Management)
-- =================================================================
-- 
-- 셀프케어 관리 시스템은 직원의 자기관리 및 건강 관리를 지원합니다.
-- 직원들이 자신의 상태를 체크하고 적절한 케어 방법을 제안받을 수 있습니다.
-- 
-- 주요 기능:
-- - 직원의 현재 상태 진단
-- - 상태에 따른 맞춤형 메시지 제공
-- - 케어 방법 추천
-- - 배경 이미지 관리
-- 
-- =================================================================

-- 1.1. 셀프케어 상태 메시지 (TB_SELF_CARE_STS_MSG)
-- 직원의 현재 상태에 따른 맞춤형 메시지와 추천 사항을 관리합니다.
CREATE TABLE TB_SELF_CARE_STS_MSG (
    SEQ         BIGSERIAL    NOT NULL,                    -- 시퀀스 (PK)
    STS_MSG_CD  VARCHAR(20)  NULL,                        -- 상태 메시지 코드
    STS_MSG_CNTN VARCHAR(255) NULL,                       -- 상태 메시지 내용
    STS_MSG_RCMD_CD VARCHAR(20)  NULL,                    -- 상태 메시지 추천 코드
    DEL_YN      CHAR(1)      NOT NULL DEFAULT 'N',        -- 삭제 여부 (Y/N)
    DEL_DT      TIMESTAMP    NULL,                        -- 삭제 일시
    REG_EMP_ID  VARCHAR(255) NULL,                        -- 등록 직원 ID
    UPD_EMP_ID  VARCHAR(255) NULL,                        -- 수정 직원 ID
    REG_DT      TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP, -- 등록 일시
    UPD_DT      TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,   -- 수정 일시
    PRIMARY KEY (SEQ)
);

CREATE INDEX IDX_SELF_CARE_STS_MSG_DEL_YN ON TB_SELF_CARE_STS_MSG (DEL_YN);

-- 테이블 코멘트 추가
COMMENT ON TABLE TB_SELF_CARE_STS_MSG IS '셀프케어 상태 메시지';

-- 컬럼 코멘트 추가
COMMENT ON COLUMN TB_SELF_CARE_STS_MSG.SEQ IS '시퀀스 (PK)';
COMMENT ON COLUMN TB_SELF_CARE_STS_MSG.STS_MSG_CD IS '상태 메시지 코드';
COMMENT ON COLUMN TB_SELF_CARE_STS_MSG.STS_MSG_CNTN IS '상태 메시지 내용';
COMMENT ON COLUMN TB_SELF_CARE_STS_MSG.STS_MSG_RCMD_CD IS '상태 메시지 추천 코드';
COMMENT ON COLUMN TB_SELF_CARE_STS_MSG.DEL_YN IS '삭제 여부 (Y/N)';
COMMENT ON COLUMN TB_SELF_CARE_STS_MSG.DEL_DT IS '삭제 일시';
COMMENT ON COLUMN TB_SELF_CARE_STS_MSG.REG_EMP_ID IS '등록 직원 ID';
COMMENT ON COLUMN TB_SELF_CARE_STS_MSG.UPD_EMP_ID IS '수정 직원 ID';
COMMENT ON COLUMN TB_SELF_CARE_STS_MSG.REG_DT IS '등록 일시';
COMMENT ON COLUMN TB_SELF_CARE_STS_MSG.UPD_DT IS '수정 일시';

-- 1.2. 셀프케어 배경 이미지 (TB_SELF_CARE_BG_IMG)
CREATE TABLE TB_SELF_CARE_BG_IMG (
    SEQ         BIGSERIAL    NOT NULL,                    -- 시퀀스 (PK)
    BG_IMG_CD   VARCHAR(20)  NULL,                        -- 배경 이미지 코드
    BG_IMG_URL  VARCHAR(255) NULL,                        -- 배경 이미지 URL
    BG_IMG_DESC VARCHAR(255) NULL,                        -- 배경 이미지 설명
    DEL_YN      CHAR(1)      NOT NULL DEFAULT 'N',        -- 삭제 여부 (Y/N)
    DEL_DT      TIMESTAMP    NULL,                        -- 삭제 일시
    REG_EMP_ID  VARCHAR(255) NULL,                        -- 등록 직원 ID
    UPD_EMP_ID  VARCHAR(255) NULL,                        -- 수정 직원 ID
    REG_DT      TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP, -- 등록 일시
    UPD_DT      TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,   -- 수정 일시
    PRIMARY KEY (SEQ)
);

CREATE INDEX IDX_SELF_CARE_BG_IMG_DEL_YN ON TB_SELF_CARE_BG_IMG (DEL_YN);

-- 테이블 코멘트 추가
COMMENT ON TABLE TB_SELF_CARE_BG_IMG IS '셀프케어 배경 이미지';

-- 컬럼 코멘트 추가
COMMENT ON COLUMN TB_SELF_CARE_BG_IMG.SEQ IS '시퀀스 (PK)';
COMMENT ON COLUMN TB_SELF_CARE_BG_IMG.BG_IMG_CD IS '배경 이미지 코드';
COMMENT ON COLUMN TB_SELF_CARE_BG_IMG.BG_IMG_URL IS '배경 이미지 URL';
COMMENT ON COLUMN TB_SELF_CARE_BG_IMG.BG_IMG_DESC IS '배경 이미지 설명';
COMMENT ON COLUMN TB_SELF_CARE_BG_IMG.DEL_YN IS '삭제 여부 (Y/N)';
COMMENT ON COLUMN TB_SELF_CARE_BG_IMG.DEL_DT IS '삭제 일시';
COMMENT ON COLUMN TB_SELF_CARE_BG_IMG.REG_EMP_ID IS '등록 직원 ID';
COMMENT ON COLUMN TB_SELF_CARE_BG_IMG.UPD_EMP_ID IS '수정 직원 ID';
COMMENT ON COLUMN TB_SELF_CARE_BG_IMG.REG_DT IS '등록 일시';
COMMENT ON COLUMN TB_SELF_CARE_BG_IMG.UPD_DT IS '수정 일시';

-- =================================================================
-- 2. 습관형성 챌린지 관리 (Habit Formation Challenge Management)
-- =================================================================
-- 
-- 습관형성 챌린지 시스템은 직원들이 건강한 습관을 형성할 수 있도록 지원합니다.
-- 목표 설정, 달성 추적, 성과 관리 등의 기능을 제공합니다.
-- 
-- 주요 기능:
-- - 개인 목표 설정 및 관리
-- - 목표 달성 현황 추적
-- - 성과 분석 및 리포트
-- - 동기부여 메시지 제공
-- 
-- =================================================================

-- 2.1. 습관형성 챌린지 목표 (TB_HBT_CHLG_GOAL)
-- 직원들이 설정한 개인 목표와 달성 기준을 관리합니다.
CREATE TABLE TB_HBT_CHLG_GOAL (
    SEQ         BIGSERIAL    NOT NULL,                    -- 시퀀스 (PK)
    GOAL_TTL    VARCHAR(255) NULL,                        -- 목표 제목
    GOAL_CNTN   TEXT         NULL,                        -- 목표 내용
    GOAL_ACHV_UNIT_CD VARCHAR(20)  NULL,                  -- 목표 달성 단위 코드
    GOAL_ACHV_TGT_CNT INTEGER      NULL,                  -- 목표 달성 목표 수량
    GOAL_ACHV_RCMD_CD VARCHAR(20)  NULL,                  -- 목표 달성 추천 코드
    DEL_YN      CHAR(1)      NOT NULL DEFAULT 'N',        -- 삭제 여부 (Y/N)
    DEL_DT      TIMESTAMP    NULL,                        -- 삭제 일시
    REG_EMP_ID  VARCHAR(255) NULL,                        -- 등록 직원 ID
    UPD_EMP_ID  VARCHAR(255) NULL,                        -- 수정 직원 ID
    REG_DT      TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP, -- 등록 일시
    UPD_DT      TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,   -- 수정 일시
    PRIMARY KEY (SEQ)
);

CREATE INDEX IDX_HBT_CHLG_GOAL_DEL_YN ON TB_HBT_CHLG_GOAL (DEL_YN);

-- 테이블 코멘트 추가
COMMENT ON TABLE TB_HBT_CHLG_GOAL IS '습관 챌린지 목표';

-- 컬럼 코멘트 추가
COMMENT ON COLUMN TB_HBT_CHLG_GOAL.SEQ IS '시퀀스 (PK)';
COMMENT ON COLUMN TB_HBT_CHLG_GOAL.GOAL_TTL IS '목표 제목';
COMMENT ON COLUMN TB_HBT_CHLG_GOAL.GOAL_CNTN IS '목표 내용';
COMMENT ON COLUMN TB_HBT_CHLG_GOAL.GOAL_ACHV_UNIT_CD IS '목표 달성 단위 코드';
COMMENT ON COLUMN TB_HBT_CHLG_GOAL.GOAL_ACHV_TGT_CNT IS '목표 달성 목표 수량';
COMMENT ON COLUMN TB_HBT_CHLG_GOAL.GOAL_ACHV_RCMD_CD IS '목표 달성 추천 코드';
COMMENT ON COLUMN TB_HBT_CHLG_GOAL.DEL_YN IS '삭제 여부 (Y/N)';
COMMENT ON COLUMN TB_HBT_CHLG_GOAL.DEL_DT IS '삭제 일시';
COMMENT ON COLUMN TB_HBT_CHLG_GOAL.REG_EMP_ID IS '등록 직원 ID';
COMMENT ON COLUMN TB_HBT_CHLG_GOAL.UPD_EMP_ID IS '수정 직원 ID';
COMMENT ON COLUMN TB_HBT_CHLG_GOAL.REG_DT IS '등록 일시';
COMMENT ON COLUMN TB_HBT_CHLG_GOAL.UPD_DT IS '수정 일시';

-- 2.2. 챌린지 목표 달성 명세 (TB_CHLG_GOAL_ACHV_DTL)
CREATE TABLE TB_CHLG_GOAL_ACHV_DTL (
    SEQ         BIGSERIAL    NOT NULL,                    -- 시퀀스 (PK)
    GOAL_SEQ    BIGINT       NULL,                        -- 목표 시퀀스
    ACHV_DT     DATE         NULL,                        -- 달성 일자
    ACHV_CNT    INTEGER      NULL,                        -- 달성 횟수
    ACHV_CNTN   TEXT         NULL,                        -- 달성 내용
    DEL_YN      CHAR(1)      NOT NULL DEFAULT 'N',        -- 삭제 여부 (Y/N)
    DEL_DT      TIMESTAMP    NULL,                        -- 삭제 일시
    REG_EMP_ID  VARCHAR(255) NULL,                        -- 등록 직원 ID
    UPD_EMP_ID  VARCHAR(255) NULL,                        -- 수정 직원 ID
    REG_DT      TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP, -- 등록 일시
    UPD_DT      TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,   -- 수정 일시
    PRIMARY KEY (SEQ)
);

CREATE INDEX IDX_CHLG_GOAL_ACHV_DTL_DEL_YN ON TB_CHLG_GOAL_ACHV_DTL (DEL_YN);

-- 테이블 코멘트 추가
COMMENT ON TABLE TB_CHLG_GOAL_ACHV_DTL IS '챌린지 목표 달성 명세';

-- 컬럼 코멘트 추가
COMMENT ON COLUMN TB_CHLG_GOAL_ACHV_DTL.SEQ IS '시퀀스 (PK)';
COMMENT ON COLUMN TB_CHLG_GOAL_ACHV_DTL.GOAL_SEQ IS '목표 시퀀스';
COMMENT ON COLUMN TB_CHLG_GOAL_ACHV_DTL.ACHV_DT IS '달성 일자';
COMMENT ON COLUMN TB_CHLG_GOAL_ACHV_DTL.ACHV_CNT IS '달성 횟수';
COMMENT ON COLUMN TB_CHLG_GOAL_ACHV_DTL.ACHV_CNTN IS '달성 내용';
COMMENT ON COLUMN TB_CHLG_GOAL_ACHV_DTL.DEL_YN IS '삭제 여부 (Y/N)';
COMMENT ON COLUMN TB_CHLG_GOAL_ACHV_DTL.DEL_DT IS '삭제 일시';
COMMENT ON COLUMN TB_CHLG_GOAL_ACHV_DTL.REG_EMP_ID IS '등록 직원 ID';
COMMENT ON COLUMN TB_CHLG_GOAL_ACHV_DTL.UPD_EMP_ID IS '수정 직원 ID';
COMMENT ON COLUMN TB_CHLG_GOAL_ACHV_DTL.REG_DT IS '등록 일시';
COMMENT ON COLUMN TB_CHLG_GOAL_ACHV_DTL.UPD_DT IS '수정 일시';

-- =================================================================
-- 3. 프로그램 신청 (Program Application)
-- =================================================================
-- 
-- 프로그램 신청 시스템은 직원들이 교육 프로그램에 신청하고 관리할 수 있도록 지원합니다.
-- 신청부터 승인, 수료까지의 전체 프로세스를 관리합니다.
-- 
-- 주요 기능:
-- - 교육 프로그램 신청
-- - 사전 과제 수행 및 제출
-- - 승인 프로세스 관리
-- - 수료 현황 추적
-- 
-- =================================================================

-- 3.1. 프로그램_신청_명세 (PROGRAM_APPLICATION_DETAIL)
-- 직원의 프로그램 신청 정보와 상태를 관리합니다.
CREATE TABLE TB_PGM_APP_DTL (
    EMP_ID          VARCHAR(255) NOT NULL,                -- 직원 ID
    PGM_ID          VARCHAR(20) NOT NULL,                 -- 프로그램 ID
    APP_DT          DATE        NOT NULL,                 -- 신청 일자
    APP_TY_CD       VARCHAR(20) NULL,                     -- 신청 유형 코드
    STS_CD          VARCHAR(20) NULL,                     -- 상태 코드
    PRE_ASGN_CMPL_YN CHAR(1)     NULL,                   -- 사전 과제 완료 여부 (Y/N)
    APP_TM          TIME        NULL,                     -- 신청 시간
    DEL_YN          CHAR(1)      NOT NULL DEFAULT 'N',    -- 삭제 여부 (Y/N)
    DEL_DT          TIMESTAMP    NULL,                    -- 삭제 일시
    REG_EMP_ID      VARCHAR(255) NULL,                    -- 등록 직원 ID
    UPD_EMP_ID      VARCHAR(255) NULL,                    -- 수정 직원 ID
    REG_DT          TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP, -- 등록 일시
    UPD_DT          TIMESTAMP    DEFAULT CURRENT_TIMESTAMP, -- 수정 일시
    PRIMARY KEY (EMP_ID, PGM_ID, APP_DT)
);

CREATE INDEX IDX_PGM_APP_DTL_DEL_YN ON TB_PGM_APP_DTL (DEL_YN);

-- 테이블 코멘트 추가
COMMENT ON TABLE TB_PGM_APP_DTL IS '프로그램 신청 명세';

-- 컬럼 코멘트 추가
COMMENT ON COLUMN TB_PGM_APP_DTL.EMP_ID IS '직원 ID';
COMMENT ON COLUMN TB_PGM_APP_DTL.PGM_ID IS '프로그램 ID';
COMMENT ON COLUMN TB_PGM_APP_DTL.APP_DT IS '신청 일자';
COMMENT ON COLUMN TB_PGM_APP_DTL.APP_TY_CD IS '신청 유형 코드';
COMMENT ON COLUMN TB_PGM_APP_DTL.STS_CD IS '상태 코드';
COMMENT ON COLUMN TB_PGM_APP_DTL.PRE_ASGN_CMPL_YN IS '사전 과제 완료 여부 (Y/N)';
COMMENT ON COLUMN TB_PGM_APP_DTL.APP_TM IS '신청 시간';
COMMENT ON COLUMN TB_PGM_APP_DTL.DEL_YN IS '삭제 여부 (Y/N)';
COMMENT ON COLUMN TB_PGM_APP_DTL.DEL_DT IS '삭제 일시';
COMMENT ON COLUMN TB_PGM_APP_DTL.REG_EMP_ID IS '등록 직원 ID';
COMMENT ON COLUMN TB_PGM_APP_DTL.UPD_EMP_ID IS '수정 직원 ID';
COMMENT ON COLUMN TB_PGM_APP_DTL.REG_DT IS '등록 일시';
COMMENT ON COLUMN TB_PGM_APP_DTL.UPD_DT IS '수정 일시';

-- 3.2. 사전과제_수행_명세 (PRE_ASSIGNMENT_PERFORMANCE_DETAIL)
CREATE TABLE TB_PRE_ASGN_PERF_DTL (
    EMP_ID            VARCHAR(255) NOT NULL,               -- 직원 ID
    PGM_ID            VARCHAR(20) NOT NULL,                -- 프로그램 ID
    PRE_ASGN_SEQ      BIGINT      NOT NULL,               -- 사전 과제 시퀀스
    PERF_DT           DATE        NULL,                    -- 수행 일자
    SRVY_ITM_CNTN     TEXT        NULL,                    -- 설문 항목 내용
    SRVY_ANS_CNTN     TEXT        NULL,                    -- 설문 답변 내용
    STS_CD            VARCHAR(20) NULL,                    -- 상태 코드
    DEL_YN            CHAR(1)      NOT NULL DEFAULT 'N',   -- 삭제 여부 (Y/N)
    DEL_DT            TIMESTAMP    NULL,                   -- 삭제 일시
    REG_EMP_ID        VARCHAR(255) NULL,                   -- 등록 직원 ID
    UPD_EMP_ID        VARCHAR(255) NULL,                   -- 수정 직원 ID
    REG_DT            TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP, -- 등록 일시
    UPD_DT            TIMESTAMP    DEFAULT CURRENT_TIMESTAMP, -- 수정 일시
    PRIMARY KEY (EMP_ID, PGM_ID, PRE_ASGN_SEQ)
);

CREATE INDEX IDX_PRE_ASGN_PERF_DTL_DEL_YN ON TB_PRE_ASGN_PERF_DTL (DEL_YN);

-- 테이블 코멘트 추가
COMMENT ON TABLE TB_PRE_ASGN_PERF_DTL IS '사전과제 수행 명세';

-- 컬럼 코멘트 추가
COMMENT ON COLUMN TB_PRE_ASGN_PERF_DTL.EMP_ID IS '직원 ID';
COMMENT ON COLUMN TB_PRE_ASGN_PERF_DTL.PGM_ID IS '프로그램 ID';
COMMENT ON COLUMN TB_PRE_ASGN_PERF_DTL.PRE_ASGN_SEQ IS '사전 과제 시퀀스';
COMMENT ON COLUMN TB_PRE_ASGN_PERF_DTL.PERF_DT IS '수행 일자';
COMMENT ON COLUMN TB_PRE_ASGN_PERF_DTL.SRVY_ITM_CNTN IS '설문 항목 내용';
COMMENT ON COLUMN TB_PRE_ASGN_PERF_DTL.SRVY_ANS_CNTN IS '설문 답변 내용';
COMMENT ON COLUMN TB_PRE_ASGN_PERF_DTL.STS_CD IS '상태 코드';
COMMENT ON COLUMN TB_PRE_ASGN_PERF_DTL.DEL_YN IS '삭제 여부 (Y/N)';
COMMENT ON COLUMN TB_PRE_ASGN_PERF_DTL.DEL_DT IS '삭제 일시';
COMMENT ON COLUMN TB_PRE_ASGN_PERF_DTL.REG_EMP_ID IS '등록 직원 ID';
COMMENT ON COLUMN TB_PRE_ASGN_PERF_DTL.UPD_EMP_ID IS '수정 직원 ID';
COMMENT ON COLUMN TB_PRE_ASGN_PERF_DTL.REG_DT IS '등록 일시';
COMMENT ON COLUMN TB_PRE_ASGN_PERF_DTL.UPD_DT IS '수정 일시';

-- 3.3. 프로그램_승인_명세 (PROGRAM_APPROVAL_DETAIL)
CREATE TABLE TB_PGM_APRV_DTL (
    EMP_ID          VARCHAR(255) NOT NULL,                -- 직원 ID
    PGM_ID          VARCHAR(20) NOT NULL,                 -- 프로그램 ID
    TRNS_DT         DATE        NOT NULL,                 -- 전송 일자
    SEQ             BIGINT      NOT NULL,                 -- 시퀀스
    APRVR_EMP_ID    VARCHAR(255) NULL,                    -- 승인자 직원 ID
    APRV_TY_CD      VARCHAR(20) NULL,                     -- 승인 유형 코드
    STS_CD          VARCHAR(20) NULL,                     -- 상태 코드
    APRV_TM         TIME        NULL,                     -- 승인 시간
    DEL_YN          CHAR(1)      NOT NULL DEFAULT 'N',    -- 삭제 여부 (Y/N)
    DEL_DT          TIMESTAMP    NULL,                    -- 삭제 일시
    REG_EMP_ID      VARCHAR(255) NULL,                    -- 등록 직원 ID
    UPD_EMP_ID      VARCHAR(255) NULL,                    -- 수정 직원 ID
    REG_DT          TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP, -- 등록 일시
    UPD_DT          TIMESTAMP    DEFAULT CURRENT_TIMESTAMP, -- 수정 일시
    PRIMARY KEY (EMP_ID, PGM_ID, TRNS_DT, SEQ)
);

CREATE INDEX IDX_PGM_APRV_DTL_DEL_YN ON TB_PGM_APRV_DTL (DEL_YN);

-- 테이블 코멘트 추가
COMMENT ON TABLE TB_PGM_APRV_DTL IS '프로그램 승인 명세';

-- 컬럼 코멘트 추가
COMMENT ON COLUMN TB_PGM_APRV_DTL.EMP_ID IS '직원 ID';
COMMENT ON COLUMN TB_PGM_APRV_DTL.PGM_ID IS '프로그램 ID';
COMMENT ON COLUMN TB_PGM_APRV_DTL.TRNS_DT IS '전송 일자';
COMMENT ON COLUMN TB_PGM_APRV_DTL.SEQ IS '시퀀스';
COMMENT ON COLUMN TB_PGM_APRV_DTL.APRVR_EMP_ID IS '승인자 직원 ID';
COMMENT ON COLUMN TB_PGM_APRV_DTL.APRV_TY_CD IS '승인 유형 코드';
COMMENT ON COLUMN TB_PGM_APRV_DTL.STS_CD IS '상태 코드';
COMMENT ON COLUMN TB_PGM_APRV_DTL.APRV_TM IS '승인 시간';
COMMENT ON COLUMN TB_PGM_APRV_DTL.DEL_YN IS '삭제 여부 (Y/N)';
COMMENT ON COLUMN TB_PGM_APRV_DTL.DEL_DT IS '삭제 일시';
COMMENT ON COLUMN TB_PGM_APRV_DTL.REG_EMP_ID IS '등록 직원 ID';
COMMENT ON COLUMN TB_PGM_APRV_DTL.UPD_EMP_ID IS '수정 직원 ID';
COMMENT ON COLUMN TB_PGM_APRV_DTL.REG_DT IS '등록 일시';
COMMENT ON COLUMN TB_PGM_APRV_DTL.UPD_DT IS '수정 일시';

-- =================================================================
-- 4. 상담 신청 (Counseling Application)
-- =================================================================
-- 
-- 상담 신청 시스템은 직원들이 상담을 신청하고 관리할 수 있도록 지원합니다.
-- 일반 상담과 전문가 상담을 모두 지원하며, 승인 프로세스를 포함합니다.
-- 
-- 주요 기능:
-- - 상담 신청 및 관리
-- - 상담사 배정
-- - 승인 프로세스 관리
-- - 상담 일정 관리
-- 
-- =================================================================

-- 4.1. 상담_신청 (COUNSELING_APPLICATION)
-- 직원의 상담 신청 정보와 상담 일정을 관리합니다.
CREATE TABLE TB_CNSL_APP (
    APP_DT          DATE         NOT NULL,                -- 신청 일자
    SEQ             BIGINT       NOT NULL,                -- 시퀀스
    CNSL_TY_CD      VARCHAR(20)  NULL,                    -- 상담 유형 코드
    DSRD_CNSL_DT    DATE         NULL,                    -- 희망 상담 일자
    GRVNC_TPC_CD    VARCHAR(20)  NULL,                    -- 고민 주제 코드
    DSRD_PSY_TEST_CD VARCHAR(20)  NULL,                   -- 희망 심리 검사 코드
    CNSL_LCTN_CD    VARCHAR(20)  NULL,                    -- 상담 장소 코드
    CNSLR_EMP_ID    VARCHAR(255)  NULL,                   -- 상담사 직원 ID
    STS_CD          VARCHAR(20)  NULL,                    -- 상태 코드
    TTL             VARCHAR(255) NULL,                    -- 제목
    CNTN            TEXT         NULL,                    -- 내용
    APRV_YN         CHAR(1)      NULL,                    -- 승인 여부 (Y/N)
    APLCNT_EMP_MGMT_ID VARCHAR(20)  NULL,                 -- 신청자 직원 관리 ID
    DEL_YN          CHAR(1)      NOT NULL DEFAULT 'N',    -- 삭제 여부 (Y/N)
    DEL_DT          TIMESTAMP    NULL,                    -- 삭제 일시
    REG_EMP_ID      VARCHAR(255) NULL,                    -- 등록 직원 ID
    UPD_EMP_ID      VARCHAR(255) NULL,                    -- 수정 직원 ID
    REG_DT          TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP, -- 등록 일시
    UPD_DT          TIMESTAMP    DEFAULT CURRENT_TIMESTAMP, -- 수정 일시
    PRIMARY KEY (APP_DT, SEQ)
);

CREATE INDEX IDX_CNSL_APP_DEL_YN ON TB_CNSL_APP (DEL_YN);

-- 테이블 코멘트 추가
COMMENT ON TABLE TB_CNSL_APP IS '상담 신청';

-- 컬럼 코멘트 추가
COMMENT ON COLUMN TB_CNSL_APP.APP_DT IS '신청 일자';
COMMENT ON COLUMN TB_CNSL_APP.SEQ IS '시퀀스';
COMMENT ON COLUMN TB_CNSL_APP.CNSL_TY_CD IS '상담 유형 코드';
COMMENT ON COLUMN TB_CNSL_APP.DSRD_CNSL_DT IS '희망 상담 일자';
COMMENT ON COLUMN TB_CNSL_APP.GRVNC_TPC_CD IS '고민 주제 코드';
COMMENT ON COLUMN TB_CNSL_APP.DSRD_PSY_TEST_CD IS '희망 심리 검사 코드';
COMMENT ON COLUMN TB_CNSL_APP.CNSL_LCTN_CD IS '상담 장소 코드';
COMMENT ON COLUMN TB_CNSL_APP.CNSLR_EMP_ID IS '상담사 직원 ID';
COMMENT ON COLUMN TB_CNSL_APP.STS_CD IS '상태 코드';
COMMENT ON COLUMN TB_CNSL_APP.TTL IS '제목';
COMMENT ON COLUMN TB_CNSL_APP.CNTN IS '내용';
COMMENT ON COLUMN TB_CNSL_APP.APRV_YN IS '승인 여부 (Y/N)';
COMMENT ON COLUMN TB_CNSL_APP.APLCNT_EMP_MGMT_ID IS '신청자 직원 관리 ID';
COMMENT ON COLUMN TB_CNSL_APP.DEL_YN IS '삭제 여부 (Y/N)';
COMMENT ON COLUMN TB_CNSL_APP.DEL_DT IS '삭제 일시';
COMMENT ON COLUMN TB_CNSL_APP.REG_EMP_ID IS '등록 직원 ID';
COMMENT ON COLUMN TB_CNSL_APP.UPD_EMP_ID IS '수정 직원 ID';
COMMENT ON COLUMN TB_CNSL_APP.REG_DT IS '등록 일시';
COMMENT ON COLUMN TB_CNSL_APP.UPD_DT IS '수정 일시';

-- 4.2. 상담_신청_명세 (COUNSELING_APPLICATION_DETAIL)
CREATE TABLE TB_CNSL_APP_DTL (
    APP_DT          DATE        NOT NULL,                 -- 신청 일자
    CNSL_APP_SEQ    BIGINT      NOT NULL,                -- 상담 신청 시퀀스
    TRNS_DT         DATE        NOT NULL,                 -- 전송 일자
    TRNS_SEQ        BIGINT      NOT NULL,                -- 전송 시퀀스
    APP_TY_CD       VARCHAR(20) NULL,                    -- 신청 유형 코드
    STS_CD          VARCHAR(20) NULL,                    -- 상태 코드
    APP_TM          TIME        NULL,                    -- 신청 시간
    DEL_YN          CHAR(1)      NOT NULL DEFAULT 'N',    -- 삭제 여부 (Y/N)
    DEL_DT          TIMESTAMP    NULL,                    -- 삭제 일시
    REG_EMP_ID      VARCHAR(255) NULL,                    -- 등록 직원 ID
    UPD_EMP_ID      VARCHAR(255) NULL,                    -- 수정 직원 ID
    REG_DT          TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP, -- 등록 일시
    UPD_DT          TIMESTAMP    DEFAULT CURRENT_TIMESTAMP, -- 수정 일시
    PRIMARY KEY (APP_DT, CNSL_APP_SEQ, TRNS_DT, TRNS_SEQ)
);

CREATE INDEX IDX_CNSL_APP_DTL_DEL_YN ON TB_CNSL_APP_DTL (DEL_YN);

-- 테이블 코멘트 추가
COMMENT ON TABLE TB_CNSL_APP_DTL IS '상담 신청 명세';

-- 컬럼 코멘트 추가
COMMENT ON COLUMN TB_CNSL_APP_DTL.APP_DT IS '신청 일자';
COMMENT ON COLUMN TB_CNSL_APP_DTL.CNSL_APP_SEQ IS '상담 신청 시퀀스';
COMMENT ON COLUMN TB_CNSL_APP_DTL.TRNS_DT IS '전송 일자';
COMMENT ON COLUMN TB_CNSL_APP_DTL.TRNS_SEQ IS '전송 시퀀스';
COMMENT ON COLUMN TB_CNSL_APP_DTL.APP_TY_CD IS '신청 유형 코드';
COMMENT ON COLUMN TB_CNSL_APP_DTL.STS_CD IS '상태 코드';
COMMENT ON COLUMN TB_CNSL_APP_DTL.APP_TM IS '신청 시간';
COMMENT ON COLUMN TB_CNSL_APP_DTL.DEL_YN IS '삭제 여부 (Y/N)';
COMMENT ON COLUMN TB_CNSL_APP_DTL.DEL_DT IS '삭제 일시';
COMMENT ON COLUMN TB_CNSL_APP_DTL.REG_EMP_ID IS '등록 직원 ID';
COMMENT ON COLUMN TB_CNSL_APP_DTL.UPD_EMP_ID IS '수정 직원 ID';
COMMENT ON COLUMN TB_CNSL_APP_DTL.REG_DT IS '등록 일시';
COMMENT ON COLUMN TB_CNSL_APP_DTL.UPD_DT IS '수정 일시';

-- 4.3. 상담_승인_명세 (COUNSELING_APPROVAL_DETAIL)
CREATE TABLE TB_CNSL_APRV_DTL (
    APP_DT          DATE        NOT NULL,                 -- 신청 일자
    CNSL_APP_SEQ    BIGINT      NOT NULL,                -- 상담 신청 시퀀스
    TRNS_DT         DATE        NOT NULL,                 -- 전송 일자
    APRVR_EMP_ID    VARCHAR(255) NULL,                    -- 승인자 직원 ID
    APRV_TY_CD      VARCHAR(20) NULL,                    -- 승인 유형 코드
    STS_CD          VARCHAR(20) NULL,                    -- 상태 코드
    APRV_TM         TIME        NULL,                    -- 승인 시간
    DEL_YN          CHAR(1)      NOT NULL DEFAULT 'N',    -- 삭제 여부 (Y/N)
    DEL_DT          TIMESTAMP    NULL,                    -- 삭제 일시
    REG_EMP_ID      VARCHAR(255) NULL,                    -- 등록 직원 ID
    UPD_EMP_ID      VARCHAR(255) NULL,                    -- 수정 직원 ID
    REG_DT          TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP, -- 등록 일시
    UPD_DT          TIMESTAMP    DEFAULT CURRENT_TIMESTAMP, -- 수정 일시
    PRIMARY KEY (APP_DT, CNSL_APP_SEQ, TRNS_DT)
);

CREATE INDEX IDX_CNSL_APRV_DTL_DEL_YN ON TB_CNSL_APRV_DTL (DEL_YN);

-- 테이블 코멘트 추가
COMMENT ON TABLE TB_CNSL_APRV_DTL IS '상담 승인 명세';

-- 컬럼 코멘트 추가
COMMENT ON COLUMN TB_CNSL_APRV_DTL.APP_DT IS '신청 일자';
COMMENT ON COLUMN TB_CNSL_APRV_DTL.CNSL_APP_SEQ IS '상담 신청 시퀀스';
COMMENT ON COLUMN TB_CNSL_APRV_DTL.TRNS_DT IS '전송 일자';
COMMENT ON COLUMN TB_CNSL_APRV_DTL.TRNS_SEQ IS '전송 시퀀스';
COMMENT ON COLUMN TB_CNSL_APRV_DTL.APRVR_EMP_ID IS '승인자 직원 ID';
COMMENT ON COLUMN TB_CNSL_APRV_DTL.APRV_TY_CD IS '승인 유형 코드';
COMMENT ON COLUMN TB_CNSL_APRV_DTL.STS_CD IS '상태 코드';
COMMENT ON COLUMN TB_CNSL_APRV_DTL.APRV_TM IS '승인 시간';
COMMENT ON COLUMN TB_CNSL_APRV_DTL.DEL_YN IS '삭제 여부 (Y/N)';
COMMENT ON COLUMN TB_CNSL_APRV_DTL.DEL_DT IS '삭제 일시';
COMMENT ON COLUMN TB_CNSL_APRV_DTL.REG_EMP_ID IS '등록 직원 ID';
COMMENT ON COLUMN TB_CNSL_APRV_DTL.UPD_EMP_ID IS '수정 직원 ID';
COMMENT ON COLUMN TB_CNSL_APRV_DTL.REG_DT IS '등록 일시';
COMMENT ON COLUMN TB_CNSL_APRV_DTL.UPD_DT IS '수정 일시';

-- =================================================================
-- 5. 바로가기 관리 (Shortcut Management)
-- =================================================================
-- 
-- 바로가기 관리 시스템은 시스템 내에서 자주 사용되는 기능에 대한 바로가기를 제공합니다.
-- 직원들이 필요한 기능에 빠르게 접근할 수 있도록 지원합니다.
-- 
-- 주요 기능:
-- - 자주 사용하는 기능 바로가기
-- - 사용자별 맞춤형 바로가기
-- - 바로가기 상태 관리
-- 
-- =================================================================

-- 5.1. 바로가기_목록 (SHORTCUT_LIST)
-- 시스템 내 바로가기 정보를 관리합니다.
CREATE TABLE TB_SCT_LST (
    SEQ         BIGSERIAL    NOT NULL,                    -- 시퀀스 (PK)
    SCT_CD      VARCHAR(20)  NULL,                        -- 바로가기 코드
    STS_CD      VARCHAR(20)  NULL,                        -- 상태 코드
    APLY_DT     DATE         NULL,                        -- 적용 일자
    URL         VARCHAR(255) NULL,                        -- URL
    DEL_YN      CHAR(1)      NOT NULL DEFAULT 'N',        -- 삭제 여부 (Y/N)
    DEL_DT      TIMESTAMP    NULL,                        -- 삭제 일시
    REG_EMP_ID  VARCHAR(255) NULL,                        -- 등록 직원 ID
    UPD_EMP_ID  VARCHAR(255) NULL,                        -- 수정 직원 ID
    REG_DT      TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP, -- 등록 일시
    UPD_DT      TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,   -- 수정 일시
    PRIMARY KEY (SEQ)
);

CREATE INDEX IDX_SCT_LST_DEL_YN ON TB_SCT_LST (DEL_YN);

-- 테이블 코멘트 추가
COMMENT ON TABLE TB_SCT_LST IS '바로가기 목록';

-- 컬럼 코멘트 추가
COMMENT ON COLUMN TB_SCT_LST.SEQ IS '시퀀스 (PK)';
COMMENT ON COLUMN TB_SCT_LST.SCT_NM IS '바로가기 명';
COMMENT ON COLUMN TB_SCT_LST.SCT_URL IS '바로가기 URL';
COMMENT ON COLUMN TB_SCT_LST.SCT_DESC IS '바로가기 설명';
COMMENT ON COLUMN TB_SCT_LST.SCT_ORD IS '바로가기 순서';
COMMENT ON COLUMN TB_SCT_LST.DEL_YN IS '삭제 여부 (Y/N)';
COMMENT ON COLUMN TB_SCT_LST.DEL_DT IS '삭제 일시';
COMMENT ON COLUMN TB_SCT_LST.REG_EMP_ID IS '등록 직원 ID';
COMMENT ON COLUMN TB_SCT_LST.UPD_EMP_ID IS '수정 직원 ID';
COMMENT ON COLUMN TB_SCT_LST.REG_DT IS '등록 일시';
COMMENT ON COLUMN TB_SCT_LST.UPD_DT IS '수정 일시';

-- =================================================================
-- 6. 공통 코드 관리 (Common Code Management)
-- =================================================================
-- 
-- 공통 코드 관리 시스템은 시스템 전반에서 사용되는 코드를 체계적으로 관리합니다.
-- 코드의 일관성과 유지보수성을 높이기 위한 중앙 집중식 코드 관리 시스템입니다.
-- 
-- 주요 기능:
-- - 코드 그룹 관리
-- - 상세 코드 관리
-- - 코드 사용 현황 추적
-- - 코드 변경 이력 관리
-- 
-- =================================================================

-- 6.1. 공통_그룹_코드 (COMMON_GROUP_CODE)
-- 시스템에서 사용되는 코드의 그룹을 관리합니다.
CREATE TABLE TB_CMN_GRP_CD (
    GRP_CD      VARCHAR(20)  NOT NULL,                    -- 그룹 코드 (PK)
    GRP_CD_NM   VARCHAR(100) NULL,                        -- 그룹 코드명
    GRP_CD_DESC VARCHAR(255) NULL,                        -- 그룹 코드 설명
    USE_YN      CHAR(1)      NOT NULL DEFAULT 'Y',        -- 사용 여부 (Y/N)
    DEL_YN      CHAR(1)      NOT NULL DEFAULT 'N',        -- 삭제 여부 (Y/N)
    DEL_DT      TIMESTAMP    NULL,                        -- 삭제 일시
    REG_EMP_ID  VARCHAR(255) NULL,                        -- 등록 직원 ID
    UPD_EMP_ID  VARCHAR(255) NULL,                        -- 수정 직원 ID
    REG_DT      TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP, -- 등록 일시
    UPD_DT      TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,   -- 수정 일시
    PRIMARY KEY (GRP_CD)
);

CREATE INDEX IDX_CMN_GRP_CD_DEL_YN ON TB_CMN_GRP_CD (DEL_YN);

-- 테이블 코멘트 추가
COMMENT ON TABLE TB_CMN_GRP_CD IS '공통 그룹 코드';

-- 컬럼 코멘트 추가
COMMENT ON COLUMN TB_CMN_GRP_CD.GRP_CD IS '그룹 코드';
COMMENT ON COLUMN TB_CMN_GRP_CD.GRP_NM IS '그룹 명';
COMMENT ON COLUMN TB_CMN_GRP_CD.GRP_DESC IS '그룹 설명';
COMMENT ON COLUMN TB_CMN_GRP_CD.DEL_YN IS '삭제 여부 (Y/N)';
COMMENT ON COLUMN TB_CMN_GRP_CD.DEL_DT IS '삭제 일시';
COMMENT ON COLUMN TB_CMN_GRP_CD.REG_EMP_ID IS '등록 직원 ID';
COMMENT ON COLUMN TB_CMN_GRP_CD.UPD_EMP_ID IS '수정 직원 ID';
COMMENT ON COLUMN TB_CMN_GRP_CD.REG_DT IS '등록 일시';
COMMENT ON COLUMN TB_CMN_GRP_CD.UPD_DT IS '수정 일시';

-- 6.2. 공통_상세_코드 (COMMON_DETAIL_CODE)
CREATE TABLE TB_CMN_DTL_CD (
    GRP_CD      VARCHAR(20)  NOT NULL,                    -- 그룹 코드
    DTL_CD      VARCHAR(20)  NOT NULL,                    -- 상세 코드
    DTL_CD_NM   VARCHAR(100) NULL,                        -- 상세 코드명
    DTL_CD_DESC VARCHAR(255) NULL,                        -- 상세 코드 설명
    SORT_ORD    INTEGER      NULL,                        -- 정렬 순서
    USE_YN      CHAR(1)      NOT NULL DEFAULT 'Y',        -- 사용 여부 (Y/N)
    DEL_YN      CHAR(1)      NOT NULL DEFAULT 'N',        -- 삭제 여부 (Y/N)
    DEL_DT      TIMESTAMP    NULL,                        -- 삭제 일시
    REG_EMP_ID  VARCHAR(255) NULL,                        -- 등록 직원 ID
    UPD_EMP_ID  VARCHAR(255) NULL,                        -- 수정 직원 ID
    REG_DT      TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP, -- 등록 일시
    UPD_DT      TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,   -- 수정 일시
    PRIMARY KEY (GRP_CD, DTL_CD)
);

CREATE INDEX IDX_CMN_DTL_CD_DEL_YN ON TB_CMN_DTL_CD (DEL_YN);

-- 테이블 코멘트 추가
COMMENT ON TABLE TB_CMN_DTL_CD IS '공통 상세 코드';

-- 컬럼 코멘트 추가
COMMENT ON COLUMN TB_CMN_DTL_CD.GRP_CD IS '그룹 코드';
COMMENT ON COLUMN TB_CMN_DTL_CD.DTL_CD IS '상세 코드';
COMMENT ON COLUMN TB_CMN_DTL_CD.DTL_NM IS '상세 명';
COMMENT ON COLUMN TB_CMN_DTL_CD.DTL_DESC IS '상세 설명';
COMMENT ON COLUMN TB_CMN_DTL_CD.DTL_ORD IS '상세 순서';
COMMENT ON COLUMN TB_CMN_DTL_CD.DEL_YN IS '삭제 여부 (Y/N)';
COMMENT ON COLUMN TB_CMN_DTL_CD.DEL_DT IS '삭제 일시';
COMMENT ON COLUMN TB_CMN_DTL_CD.REG_EMP_ID IS '등록 직원 ID';
COMMENT ON COLUMN TB_CMN_DTL_CD.UPD_EMP_ID IS '수정 직원 ID';
COMMENT ON COLUMN TB_CMN_DTL_CD.REG_DT IS '등록 일시';
COMMENT ON COLUMN TB_CMN_DTL_CD.UPD_DT IS '수정 일시';

-- =================================================================
-- 공통 코드 예시 데이터 (Sample Data for Common Codes)
-- =================================================================
INSERT INTO TB_CMN_GRP_CD (GRP_CD, GRP_CD_NM, GRP_CD_DESC, USE_YN) VALUES
('GOAL_ACHV_UNIT', '목표 달성 단위 코드', '목표 달성의 단위를 관리하기 위한 코드 (일별/주별/월별)', 'Y'),
('PGM_APP_TY', '프로그램 신청 구분 코드', '프로그램 신청의 구분을 위한 코드 (신청/승인/반려)', 'Y'),
('PGM_APP_STS', '프로그램 신청 상태 코드', '프로그램 신청의 상태를 관리하기 위한 코드 (정상/취소/종료)', 'Y'),
('CNSL_TY', '상담 종류 코드', '상담의 종류를 구분하기 위한 코드 (일반/비대면)', 'Y'),
('CNSLR_INFO_CLSF', '상담자정보구분 코드', '상담자의 정보 구분을 위한 코드 (전문상담사/일반상담사/인턴)', 'Y'),
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
('CNSL_TY', 'GEN', '일반', '일반 상담', 1, 'Y'),
('CNSL_TY', 'REMOTE', '비대면', '비대면 상담', 2, 'Y'),
('CNSLR_INFO_CLSF', 'PROF', '전문상담사', '전문 상담사', 1, 'Y'),
('CNSLR_INFO_CLSF', 'GEN', '일반상담사', '일반 상담사', 2, 'Y'),
('CNSLR_INFO_CLSF', 'INT', '인턴', '상담 인턴', 3, 'Y'),
('SCT_STS', 'NORMAL', '정상', '정상 상태', 1, 'Y'),
('SCT_STS', 'CANCEL', '취소', '취소된 상태', 2, 'Y'),
('SCT_STS', 'END', '종료', '종료된 상태', 3, 'Y');

-- =================================================================
-- 7. 직원권익게시판 관리 (Employee Rights Board Management)
-- =================================================================
-- 
-- 직원권익게시판은 직원들이 자유롭게 의견을 나누고 건의사항을 제출할 수 있는 공간입니다.
-- 익명성 보장과 함께 건전한 소통 문화를 조성하는 것을 목표로 합니다.
-- 
-- 주요 기능:
-- - 게시글 작성 및 관리
-- - 댓글 및 대댓글 기능
-- - 좋아요/싫어요 기능
-- - 조회수 추적
-- - 익명 작성 지원
-- - 카테고리별 분류
-- 
-- =================================================================

-- 7.1. 직원권익게시판 목록 (TB_EMP_RT_BRD)
-- 직원권익게시판의 게시글 정보를 관리합니다.
CREATE TABLE TB_EMP_RT_BRD (
    SEQ             BIGSERIAL    NOT NULL,                -- 시퀀스 (PK)
    TTL             VARCHAR(255) NULL,                    -- 제목
    CNTN            TEXT         NULL,                    -- 내용
    STS_CD          VARCHAR(20)  NULL,                    -- 상태코드 (ACTIVE/INACTIVE/DELETED)
    FILE_ATT_YN  CHAR(1)      NOT NULL DEFAULT 'N',    -- 첨부파일 존재 여부
    VIEW_CNT        INTEGER      NOT NULL DEFAULT 0,      -- 조회수
    LIKE_CNT        INTEGER      NOT NULL DEFAULT 0,      -- 좋아요 수
          DISL_CNT        INTEGER      NOT NULL DEFAULT 0,      -- 싫어요 수
      CAT_CD          VARCHAR(20)  NULL,                    -- 카테고리 코드 (GEN/COMPL/SUGG/QST)
      SEC_YN          CHAR(1)      NOT NULL DEFAULT 'N',    -- 비밀글 여부
    NOTI_YN         CHAR(1)      NOT NULL DEFAULT 'N',    -- 공지글 여부
    DEL_YN          CHAR(1)      NOT NULL DEFAULT 'N',    -- 삭제여부
    DEL_DATE        TIMESTAMP    NULL,                    -- 삭제일시
    REG_EMP_ID      VARCHAR(255) NULL,                    -- 등록직원ID
    UPD_EMP_ID      VARCHAR(255) NULL,                    -- 수정직원ID
    REG_DATE        TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP, -- 등록일시
    UPD_DATE        TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,         -- 수정일시
    PRIMARY KEY (SEQ)
);

COMMENT ON TABLE TB_EMP_RT_BRD IS '직원권익게시판 목록';
COMMENT ON COLUMN TB_EMP_RT_BRD.SEQ IS '일련번호';
COMMENT ON COLUMN TB_EMP_RT_BRD.TTL IS '제목';
COMMENT ON COLUMN TB_EMP_RT_BRD.CNTN IS '내용';
COMMENT ON COLUMN TB_EMP_RT_BRD.STS_CD IS '상태코드';
COMMENT ON COLUMN TB_EMP_RT_BRD.FILE_ATT_YN IS '첨부파일 존재 여부';
COMMENT ON COLUMN TB_EMP_RT_BRD.VIEW_CNT IS '조회수';
COMMENT ON COLUMN TB_EMP_RT_BRD.LIKE_CNT IS '좋아요 수';
COMMENT ON COLUMN TB_EMP_RT_BRD.DISL_CNT IS '싫어요 수';
COMMENT ON COLUMN TB_EMP_RT_BRD.CAT_CD IS '카테고리 코드';
COMMENT ON COLUMN TB_EMP_RT_BRD.SEC_YN IS '비밀글 여부';
COMMENT ON COLUMN TB_EMP_RT_BRD.NOTI_YN IS '공지글 여부';
COMMENT ON COLUMN TB_EMP_RT_BRD.DEL_YN IS '삭제 여부 (Y/N)';
COMMENT ON COLUMN TB_EMP_RT_BRD.DEL_DT IS '삭제 일시';
COMMENT ON COLUMN TB_EMP_RT_BRD.REG_EMP_ID IS '등록 직원 ID';
COMMENT ON COLUMN TB_EMP_RT_BRD.UPD_EMP_ID IS '수정 직원 ID';
COMMENT ON COLUMN TB_EMP_RT_BRD.REG_DT IS '등록 일시';
COMMENT ON COLUMN TB_EMP_RT_BRD.UPD_DT IS '수정 일시';

CREATE INDEX IDX_EMP_RT_BRD_DEL_YN ON TB_EMP_RT_BRD (DEL_YN);
CREATE INDEX IDX_EMP_RT_BRD_CAT ON TB_EMP_RT_BRD (CAT_CD);
CREATE INDEX IDX_EMP_RT_BRD_NOTI ON TB_EMP_RT_BRD (NOTI_YN);
CREATE INDEX IDX_EMP_RT_BRD_REG_DT ON TB_EMP_RT_BRD (REG_DT);

-- 7.2. 직원권익게시판 댓글 (TB_EMP_RT_CMT)
CREATE TABLE TB_EMP_RT_CMT (
    SEQ             BIGSERIAL    NOT NULL,                -- 시퀀스 (PK)
    BRD_SEQ         BIGINT       NOT NULL,                -- 게시글 일련번호
    PAR_SEQ         BIGINT       NULL,                    -- 부모 댓글 일련번호 (대댓글용)
    CNTN            TEXT         NOT NULL,                -- 댓글 내용
    DEP             INTEGER      NOT NULL DEFAULT 0,      -- 댓글 깊이 (0: 댓글, 1: 대댓글, 2: 대대댓글...)
    LIKE_CNT        INTEGER      NOT NULL DEFAULT 0,      -- 좋아요 수
    DISL_CNT        INTEGER      NOT NULL DEFAULT 0,      -- 싫어요 수
    SEC_YN          CHAR(1)      NOT NULL DEFAULT 'N',    -- 비밀댓글 여부
    DEL_YN          CHAR(1)      NOT NULL DEFAULT 'N',    -- 삭제여부
    DEL_DT          TIMESTAMP    NULL,                    -- 삭제일시
    REG_EMP_ID      VARCHAR(255) NULL,                    -- 등록직원ID
    UPD_EMP_ID      VARCHAR(255) NULL,                    -- 수정직원ID
    REG_DT          TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP, -- 등록일시
    UPD_DT          TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,         -- 수정일시
    PRIMARY KEY (SEQ)
);

COMMENT ON TABLE TB_EMP_RT_CMT IS '직원권익게시판 댓글';
COMMENT ON COLUMN TB_EMP_RT_CMT.SEQ IS '댓글 일련번호';
COMMENT ON COLUMN TB_EMP_RT_CMT.BRD_SEQ IS '게시글 일련번호';
COMMENT ON COLUMN TB_EMP_RT_CMT.PAR_SEQ IS '부모 댓글 일련번호';
COMMENT ON COLUMN TB_EMP_RT_CMT.CNTN IS '댓글 내용';
COMMENT ON COLUMN TB_EMP_RT_CMT.DEP IS '댓글 깊이';
COMMENT ON COLUMN TB_EMP_RT_CMT.LIKE_CNT IS '좋아요 수';
COMMENT ON COLUMN TB_EMP_RT_CMT.DISL_CNT IS '싫어요 수';
COMMENT ON COLUMN TB_EMP_RT_CMT.SEC_YN IS '비밀댓글 여부';
COMMENT ON COLUMN TB_EMP_RT_CMT.DEL_YN IS '삭제 여부 (Y/N)';
COMMENT ON COLUMN TB_EMP_RT_CMT.DEL_DT IS '삭제 일시';
COMMENT ON COLUMN TB_EMP_RT_CMT.REG_EMP_ID IS '등록 직원 ID';
COMMENT ON COLUMN TB_EMP_RT_CMT.UPD_EMP_ID IS '수정 직원 ID';
COMMENT ON COLUMN TB_EMP_RT_CMT.REG_DT IS '등록 일시';
COMMENT ON COLUMN TB_EMP_RT_CMT.UPD_DT IS '수정 일시';

CREATE INDEX IDX_EMP_RT_CMT_BRD ON TB_EMP_RT_CMT (BRD_SEQ);
CREATE INDEX IDX_EMP_RT_CMT_PAR ON TB_EMP_RT_CMT (PAR_SEQ);
CREATE INDEX IDX_EMP_RT_CMT_DEL_YN ON TB_EMP_RT_CMT (DEL_YN);
CREATE INDEX IDX_EMP_RT_CMT_DEP ON TB_EMP_RT_CMT (DEP);
CREATE INDEX IDX_EMP_RT_CMT_REG_DT ON TB_EMP_RT_CMT (REG_DT);

-- 7.3. 직원권익게시판 좋아요/싫어요 (TB_EMP_RT_LIKE)
CREATE TABLE TB_EMP_RT_LIKE (
    SEQ             BIGSERIAL    NOT NULL,                -- 시퀀스 (PK)
    BRD_SEQ         BIGINT       NULL,                    -- 게시글 일련번호 (NULL이면 댓글 좋아요)
    CMT_SEQ         BIGINT       NULL,                    -- 댓글 일련번호 (NULL이면 게시글 좋아요)
    LIKE_TYPE       CHAR(1)      NOT NULL,                -- 좋아요 타입 (L: 좋아요, D: 싫어요)
    REG_EMP_ID      VARCHAR(255) NOT NULL,                -- 등록직원ID
    DEL_YN          CHAR(1)      NOT NULL DEFAULT 'N',    -- 삭제여부
    DEL_DT          TIMESTAMP    NULL,                    -- 삭제일시
    UPD_EMP_ID      VARCHAR(255) NULL,                    -- 수정직원ID
    REG_DT          TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP, -- 등록일시
    UPD_DT          TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,         -- 수정일시
    PRIMARY KEY (SEQ),
    CONSTRAINT CHK_LIKE_TYPE CHECK (LIKE_TYPE IN ('L', 'D')),
    CONSTRAINT CHK_LIKE_TARGET CHECK (
                (BRD_SEQ IS NOT NULL AND CMT_SEQ IS NULL) OR
        (BRD_SEQ IS NULL AND CMT_SEQ IS NOT NULL)
    )
);

COMMENT ON TABLE TB_EMP_RT_LIKE IS '직원권익게시판 좋아요/싫어요';
COMMENT ON COLUMN TB_EMP_RT_LIKE.SEQ IS '일련번호';
COMMENT ON COLUMN TB_EMP_RT_LIKE.BRD_SEQ IS '게시글 일련번호';
COMMENT ON COLUMN TB_EMP_RT_LIKE.CMT_SEQ IS '댓글 일련번호';
COMMENT ON COLUMN TB_EMP_RT_LIKE.LIKE_TYPE IS '좋아요 타입 (L: 좋아요, D: 싫어요)';
COMMENT ON COLUMN TB_EMP_RT_LIKE.REG_EMP_ID IS '등록직원ID';
COMMENT ON COLUMN TB_EMP_RT_LIKE.DEL_YN IS '삭제 여부 (Y/N)';
COMMENT ON COLUMN TB_EMP_RT_LIKE.DEL_DT IS '삭제 일시';
COMMENT ON COLUMN TB_EMP_RT_LIKE.UPD_EMP_ID IS '수정 직원 ID';
COMMENT ON COLUMN TB_EMP_RT_LIKE.REG_DT IS '등록 일시';
COMMENT ON COLUMN TB_EMP_RT_LIKE.UPD_DT IS '수정 일시';

CREATE INDEX IDX_EMP_RT_LIKE_BRD ON TB_EMP_RT_LIKE (BRD_SEQ);
CREATE INDEX IDX_EMP_RT_LIKE_CMT ON TB_EMP_RT_LIKE (CMT_SEQ);
CREATE INDEX IDX_EMP_RT_LIKE_EMP ON TB_EMP_RT_LIKE (REG_EMP_ID);
CREATE INDEX IDX_EMP_RT_LIKE_TYPE ON TB_EMP_RT_LIKE (LIKE_TYPE);
CREATE INDEX IDX_EMP_RT_LIKE_DEL_YN ON TB_EMP_RT_LIKE (DEL_YN);

-- 7.4. 직원권익게시판 조회이력 (TB_EMP_RT_VIEW_LOG)
CREATE TABLE TB_EMP_RT_VIEW_LOG (
    SEQ             BIGSERIAL    NOT NULL,                -- 시퀀스 (PK)
    BRD_SEQ         BIGINT       NOT NULL,                -- 게시글 일련번호
    REG_EMP_ID      VARCHAR(255) NULL,                    -- 조회자 직원ID (NULL이면 비로그인)
    IP_ADDR         VARCHAR(45)  NULL,                    -- IP 주소
    USER_AGENT      TEXT         NULL,                    -- 사용자 에이전트
    REG_DT          TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP, -- 조회일시
    PRIMARY KEY (SEQ)
);

COMMENT ON TABLE TB_EMP_RT_VIEW_LOG IS '직원권익게시판 조회이력';
COMMENT ON COLUMN TB_EMP_RT_VIEW_LOG.SEQ IS '일련번호';
COMMENT ON COLUMN TB_EMP_RT_VIEW_LOG.BRD_SEQ IS '게시글 일련번호';
COMMENT ON COLUMN TB_EMP_RT_VIEW_LOG.REG_EMP_ID IS '조회자 직원ID';
COMMENT ON COLUMN TB_EMP_RT_VIEW_LOG.IP_ADDR IS 'IP 주소';
COMMENT ON COLUMN TB_EMP_RT_VIEW_LOG.USER_AGENT IS '사용자 에이전트';
COMMENT ON COLUMN TB_EMP_RT_VIEW_LOG.REG_DT IS '조회일시';

CREATE INDEX IDX_EMP_RT_VIEW_LOG_BRD ON TB_EMP_RT_VIEW_LOG (BRD_SEQ);
CREATE INDEX IDX_EMP_RT_VIEW_LOG_EMP ON TB_EMP_RT_VIEW_LOG (REG_EMP_ID);
CREATE INDEX IDX_EMP_RT_VIEW_LOG_DT ON TB_EMP_RT_VIEW_LOG (REG_DT);

-- =================================================================
-- 직원권익게시판 관련 공통 코드 추가
-- =================================================================

-- 게시판 카테고리 코드 추가
INSERT INTO TB_CMN_GRP_CD (GRP_CD, GRP_CD_NM, GRP_CD_DESC, USE_YN) VALUES
('BRD_CATEGORY', '게시판 카테고리 코드', '직원권익게시판의 카테고리를 관리하기 위한 코드', 'Y'),
('BRD_STATUS', '게시판 상태 코드', '직원권익게시판의 상태를 관리하기 위한 코드', 'Y');

INSERT INTO TB_CMN_DTL_CD (GRP_CD, DTL_CD, DTL_CD_NM, DTL_CD_DESC, SORT_ORD, USE_YN) VALUES
('BRD_CATEGORY', 'GEN', '일반', '일반 게시글', 1, 'Y'),
('BRD_CATEGORY', 'COMPL', '불만/민원', '불만사항 및 민원', 2, 'Y'),
('BRD_CATEGORY', 'SUGG', '건의사항', '건의사항', 3, 'Y'),
('BRD_CATEGORY', 'QST', '질문', '질문', 4, 'Y'),
('BRD_STATUS', 'ACTIVE', '활성', '활성 상태', 1, 'Y'),
('BRD_STATUS', 'INACTIVE', '비활성', '비활성 상태', 2, 'Y'),
('BRD_STATUS', 'DELETED', '삭제', '삭제된 상태', 3, 'Y');

-- =================================================================
-- 직원권익게시판 샘플 데이터
-- =================================================================

-- 샘플 게시글 데이터
INSERT INTO TB_EMP_RT_BRD (TTL, CNTN, STS_CD, CAT_CD, NOTI_YN, REG_EMP_ID, VIEW_CNT) VALUES
('직원권익보호 시스템 오픈 안내', '직원권익보호 시스템이 오픈되었습니다. 많은 관심과 참여 부탁드립니다.', 'ACTIVE', 'GEN', 'Y', 'ADMIN001', 0),
('근무환경 개선 건의사항', '사무실 조명이 너무 어두워서 업무에 지장이 있습니다. 개선 부탁드립니다.', 'ACTIVE', 'SUGG', 'N', 'EMP001', 0),
('급여 관련 문의', '이번 달 급여에서 공제된 항목에 대해 문의드립니다.', 'ACTIVE', 'QST', 'N', 'EMP002', 0),
('식당 메뉴 개선 요청', '직원식당 메뉴가 단조로워서 개선을 요청드립니다.', 'ACTIVE', 'SUGG', 'N', 'EMP003', 0);

-- 샘플 댓글 데이터
INSERT INTO TB_EMP_RT_CMT (BRD_SEQ, CNTN, DEP, REG_EMP_ID) VALUES
(1, '시스템이 정말 유용해 보입니다. 잘 활용하겠습니다.', 0, 'EMP001'),
(1, '감사합니다. 많은 도움이 될 것 같습니다.', 0, 'EMP002'),
(2, '저도 같은 생각입니다. 조명 개선이 필요해 보입니다.', 0, 'EMP004'),
(2, '관리부서에서 검토 후 개선하도록 하겠습니다.', 0, 'ADMIN001'),
(3, '급여팀에서 확인 후 답변드리겠습니다.', 0, 'ADMIN002');

-- 샘플 대댓글 데이터
INSERT INTO TB_EMP_RT_CMT (BRD_SEQ, PAR_SEQ, CNTN, DEP, REG_EMP_ID) VALUES
(2, 4, '빠른 대응 감사합니다.', 1, 'EMP001'),
(2, 4, '기대하겠습니다.', 1, 'EMP005'),
(3, 5, '답변 기다리겠습니다.', 1, 'EMP002');

-- 샘플 좋아요 데이터
INSERT INTO TB_EMP_RT_LIKE (BRD_SEQ, LIKE_TYPE, REG_EMP_ID) VALUES
(1, 'L', 'EMP001'),
(1, 'L', 'EMP002'),
(2, 'L', 'EMP003'),
(2, 'L', 'EMP004'),
(3, 'L', 'EMP001');

-- 샘플 댓글 좋아요 데이터
INSERT INTO TB_EMP_RT_LIKE (CMT_SEQ, LIKE_TYPE, REG_EMP_ID) VALUES
(1, 'L', 'EMP002'),
(1, 'L', 'EMP003'),
(4, 'L', 'EMP001'),
(4, 'L', 'EMP005');

-- =================================================================
-- 8. 전문가 상담 관리 (Expert Consultation Management)
-- =================================================================
-- 
-- 전문가 상담 관리 시스템은 전문 상담사와의 상담을 체계적으로 관리합니다.
-- 상담 신청부터 상담 완료까지의 전체 프로세스를 지원하며, 시간대별 제한과 상담사 배정을 관리합니다.
-- 
-- 주요 기능:
-- - 전문가 상담 신청 및 관리
-- - 상담사 스케줄 관리
-- - 상담사 배정 및 관리
-- - 상담 일지 작성 및 관리
-- - 시간대별 상담 제한 설정
-- - 상담사 정보 관리
-- 
-- =================================================================

-- 8.1. 전문가 상담 신청 (TB_EXP_CNSL_APP)
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

-- 8.2. 전문가 상담 일지 (TB_EXP_CNSL_DRY)
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

-- 8.3. 상담사 스케줄 (TB_CNSLR_SCHEDULE)
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

-- 8.4. 상담사 배정 관리 (TB_CNSL_ASSIGNMENT)
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

-- 8.5. 상담 시간대별 제한 설정 (TB_CNSL_TIME_LIMIT)
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

-- 8.6. 상담사 정보 (TB_CNSLR_INFO)
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
-- 전문가 상담 관련 공통 코드 추가
-- =================================================================

-- 상담 관련 코드 추가
INSERT INTO TB_CMN_GRP_CD (GRP_CD, GRP_CD_NM, GRP_CD_DESC, USE_YN) VALUES
('EXP_CNSL_TY', '전문가 상담 유형 코드', '전문가 상담의 유형을 관리하기 위한 코드', 'Y'),
('EXP_CNSL_APRV_STS', '전문가 상담 승인 상태 코드', '전문가 상담 승인 상태를 관리하기 위한 코드', 'Y'),
('EXP_CNSL_STS', '전문가 상담 상태 코드', '전문가 상담 상태를 관리하기 위한 코드', 'Y'),
('CNSLR_SCH_TY', '상담사 스케줄 유형 코드', '상담사 스케줄 유형을 관리하기 위한 코드', 'Y'),
('CNSL_ASGN_STS', '상담 배정 상태 코드', '상담 배정 상태를 관리하기 위한 코드', 'Y'),
('CNSLR_SPCL', '상담사 전문분야 코드', '상담사의 전문분야를 관리하기 위한 코드', 'Y'),
('CNSLR_LIC', '상담사 자격증 코드', '상담사의 자격증을 관리하기 위한 코드', 'Y');

INSERT INTO TB_CMN_DTL_CD (GRP_CD, DTL_CD, DTL_CD_NM, DTL_CD_DESC, SORT_ORD, USE_YN) VALUES
('EXP_CNSL_TY', 'FACE_TO_FACE', '대면상담', '대면 상담', 1, 'Y'),
('EXP_CNSL_TY', 'REMOTE', '비대면상담', '비대면 상담', 2, 'Y'),
('EXP_CNSL_APRV_STS', 'PENDING', '승인대기', '승인 대기 상태', 1, 'Y'),
('EXP_CNSL_APRV_STS', 'APPROVED', '승인완료', '승인 완료 상태', 2, 'Y'),
('EXP_CNSL_APRV_STS', 'REJECTED', '승인반려', '승인 반려 상태', 3, 'Y'),
('EXP_CNSL_APRV_STS', 'COMPLETED', '상담완료', '상담 완료 상태', 4, 'Y'),
('EXP_CNSL_STS', 'SCHEDULED', '상담예정', '상담 예정 상태', 1, 'Y'),
('EXP_CNSL_STS', 'IN_PROGRESS', '상담진행중', '상담 진행 중 상태', 2, 'Y'),
('EXP_CNSL_STS', 'COMPLETED', '상담완료', '상담 완료 상태', 3, 'Y'),
('EXP_CNSL_STS', 'CANCELLED', '상담취소', '상담 취소 상태', 4, 'Y'),
('CNSLR_SCH_TY', 'AVAILABLE', '상담가능', '상담 가능 시간', 1, 'Y'),
('CNSLR_SCH_TY', 'UNAVAILABLE', '상담불가', '상담 불가 시간', 2, 'Y'),
('CNSLR_SCH_TY', 'BREAK', '휴식시간', '휴식 시간', 3, 'Y'),
('CNSL_ASGN_STS', 'ASSIGNED', '배정완료', '배정 완료 상태', 1, 'Y'),
('CNSL_ASGN_STS', 'CONFIRMED', '배정확정', '배정 확정 상태', 2, 'Y'),
('CNSL_ASGN_STS', 'CANCELLED', '배정취소', '배정 취소 상태', 3, 'Y'),
('CNSLR_SPCL', 'STRESS', '스트레스', '스트레스 상담 전문', 1, 'Y'),
('CNSLR_SPCL', 'RELATIONSHIP', '인간관계', '인간관계 상담 전문', 2, 'Y'),
('CNSLR_SPCL', 'CAREER', '진로상담', '진로 상담 전문', 3, 'Y'),
('CNSLR_SPCL', 'FAM', '가족상담', '가족 상담 전문', 4, 'Y'),
('CNSLR_SPCL', 'MENTAL_HLTH', '정신건강', '정신건강 상담 전문', 5, 'Y'),
('CNSLR_LIC', 'PSYCHOLOGIST', '심리상담사', '심리상담사 자격증', 1, 'Y'),
('CNSLR_LIC', 'SOCIAL_WORKER', '사회복지사', '사회복지사 자격증', 2, 'Y'),
('CNSLR_LIC', 'CAREER_COUNSELOR', '진로상담사', '진로상담사 자격증', 3, 'Y'),
('CNSLR_LIC', 'FAM_COUNSELOR', '가족상담사', '가족상담사 자격증', 4, 'Y');

-- =================================================================
-- 전문가 상담 샘플 데이터
-- =================================================================

-- 상담사 정보 샘플 데이터
INSERT INTO TB_CNSLR_INFO (CNSLR_EMP_ID, CNSLR_NM, CNSLR_CLSF_CD, CNSLR_SPCL_CD, CNSLR_LIC_CD, CNSLR_INTRO, AVAIL_YN) VALUES
('CNSLR001', '김상담', 'PROF', 'STRESS', 'PSYCHOLOGIST', '10년간 스트레스 상담 전문가로 활동하고 있습니다.', 'Y'),
('CNSLR002', '이심리', 'PROF', 'RELATIONSHIP', 'PSYCHOLOGIST', '인간관계 및 대인관계 상담 전문가입니다.', 'Y'),
('CNSLR003', '박진로', 'PROF', 'CAREER', 'CAREER_COUNSELOR', '진로 상담 및 직업 적성 검사 전문가입니다.', 'Y'),
('CNSLR004', '최가족', 'PROF', 'FAM', 'FAM_COUNSELOR', '가족 상담 및 부모교육 전문가입니다.', 'Y'),
('CNSLR005', '정정신', 'PROF', 'MENTAL_HLTH', 'PSYCHOLOGIST', '정신건강 및 심리치료 전문가입니다.', 'Y');

-- 상담사 스케줄 샘플 데이터 (2025년 8월)
INSERT INTO TB_CNSLR_SCHEDULE (CNSLR_EMP_ID, SCH_DT, SCH_TM, SCH_TY_CD, SCH_CNTN) VALUES
-- 8월 4일 (월요일)
('CNSLR001', '2025-08-04', '10:00:00', 'AVAILABLE', '상담 가능'),
('CNSLR001', '2025-08-04', '12:00:00', 'AVAILABLE', '상담 가능'),
('CNSLR001', '2025-08-04', '14:00:00', 'AVAILABLE', '상담 가능'),
('CNSLR001', '2025-08-04', '16:00:00', 'AVAILABLE', '상담 가능'),
('CNSLR001', '2025-08-04', '18:00:00', 'AVAILABLE', '상담 가능'),
('CNSLR001', '2025-08-04', '20:00:00', 'AVAILABLE', '상담 가능'),
('CNSLR002', '2025-08-04', '10:00:00', 'AVAILABLE', '상담 가능'),
('CNSLR002', '2025-08-04', '12:00:00', 'AVAILABLE', '상담 가능'),
('CNSLR002', '2025-08-04', '14:00:00', 'AVAILABLE', '상담 가능'),
('CNSLR002', '2025-08-04', '16:00:00', 'AVAILABLE', '상담 가능'),
('CNSLR002', '2025-08-04', '18:00:00', 'AVAILABLE', '상담 가능'),
('CNSLR002', '2025-08-04', '20:00:00', 'AVAILABLE', '상담 가능'),
-- 8월 5일 (화요일)
('CNSLR001', '2025-08-05', '10:00:00', 'AVAILABLE', '상담 가능'),
('CNSLR001', '2025-08-05', '12:00:00', 'AVAILABLE', '상담 가능'),
('CNSLR001', '2025-08-05', '14:00:00', 'AVAILABLE', '상담 가능'),
('CNSLR001', '2025-08-05', '16:00:00', 'AVAILABLE', '상담 가능'),
('CNSLR001', '2025-08-05', '18:00:00', 'AVAILABLE', '상담 가능'),
('CNSLR001', '2025-08-05', '20:00:00', 'AVAILABLE', '상담 가능'),
-- 8월 6일 (수요일)
('CNSLR001', '2025-08-06', '10:00:00', 'AVAILABLE', '상담 가능'),
('CNSLR001', '2025-08-06', '12:00:00', 'AVAILABLE', '상담 가능'),
('CNSLR001', '2025-08-06', '14:00:00', 'AVAILABLE', '상담 가능'),
('CNSLR001', '2025-08-06', '16:00:00', 'AVAILABLE', '상담 가능'),
('CNSLR001', '2025-08-06', '18:00:00', 'AVAILABLE', '상담 가능'),
('CNSLR001', '2025-08-06', '20:00:00', 'AVAILABLE', '상담 가능'),
-- 8월 7일 (목요일)
('CNSLR001', '2025-08-07', '10:00:00', 'AVAILABLE', '상담 가능'),
('CNSLR001', '2025-08-07', '12:00:00', 'AVAILABLE', '상담 가능'),
('CNSLR001', '2025-08-07', '14:00:00', 'AVAILABLE', '상담 가능'),
('CNSLR001', '2025-08-07', '16:00:00', 'AVAILABLE', '상담 가능'),
('CNSLR001', '2025-08-07', '18:00:00', 'AVAILABLE', '상담 가능'),
('CNSLR001', '2025-08-07', '20:00:00', 'AVAILABLE', '상담 가능'),
-- 8월 8일 (금요일)
('CNSLR001', '2025-08-08', '10:00:00', 'AVAILABLE', '상담 가능'),
('CNSLR001', '2025-08-08', '12:00:00', 'AVAILABLE', '상담 가능'),
('CNSLR001', '2025-08-08', '14:00:00', 'AVAILABLE', '상담 가능'),
('CNSLR001', '2025-08-08', '16:00:00', 'AVAILABLE', '상담 가능'),
('CNSLR001', '2025-08-08', '18:00:00', 'AVAILABLE', '상담 가능'),
('CNSLR001', '2025-08-08', '20:00:00', 'AVAILABLE', '상담 가능');

-- 상담 시간대별 제한 설정 샘플 데이터 (2025년 8월)
INSERT INTO TB_CNSL_TIME_LIMIT (CNSL_DT, CNSL_TM, MAX_CNT, CURRENT_CNT, AVAIL_YN) VALUES
-- 8월 4일 (월요일)
('2025-08-04', '10:00:00', 3, 0, 'Y'),
('2025-08-04', '12:00:00', 3, 0, 'Y'),
('2025-08-04', '14:00:00', 3, 0, 'Y'),
('2025-08-04', '16:00:00', 3, 0, 'Y'),
('2025-08-04', '18:00:00', 3, 0, 'Y'),
('2025-08-04', '20:00:00', 3, 0, 'Y'),
-- 8월 5일 (화요일)
('2025-08-05', '10:00:00', 3, 0, 'Y'),
('2025-08-05', '12:00:00', 3, 0, 'Y'),
('2025-08-05', '14:00:00', 3, 0, 'Y'),
('2025-08-05', '16:00:00', 3, 0, 'Y'),
('2025-08-05', '18:00:00', 3, 0, 'Y'),
('2025-08-05', '20:00:00', 3, 0, 'Y'),
-- 8월 6일 (수요일)
('2025-08-06', '10:00:00', 3, 0, 'Y'),
('2025-08-06', '12:00:00', 3, 0, 'Y'),
('2025-08-06', '14:00:00', 3, 0, 'Y'),
('2025-08-06', '16:00:00', 3, 0, 'Y'),
('2025-08-06', '18:00:00', 3, 0, 'Y'),
('2025-08-06', '20:00:00', 3, 0, 'Y'),
-- 8월 7일 (목요일)
('2025-08-07', '10:00:00', 3, 0, 'Y'),
('2025-08-07', '12:00:00', 3, 0, 'Y'),
('2025-08-07', '14:00:00', 3, 0, 'Y'),
('2025-08-07', '16:00:00', 3, 0, 'Y'),
('2025-08-07', '18:00:00', 3, 0, 'Y'),
('2025-08-07', '20:00:00', 3, 0, 'Y'),
-- 8월 8일 (금요일)
('2025-08-08', '10:00:00', 3, 0, 'Y'),
('2025-08-08', '12:00:00', 3, 0, 'Y'),
('2025-08-08', '14:00:00', 3, 0, 'Y'),
('2025-08-08', '16:00:00', 3, 0, 'Y'),
('2025-08-08', '18:00:00', 3, 0, 'Y'),
('2025-08-08', '20:00:00', 3, 0, 'Y');

-- 샘플 상담 신청 데이터
INSERT INTO TB_EXP_CNSL_APP (APP_DT, APP_TM, EMP_ID, CNSL_DT, CNSL_TM, CNSL_TY_CD, CNSL_CNTN, ANONYMOUS_YN, APRV_STS_CD) VALUES
('2025-08-01', '09:30:00', 'EMP001', '2025-08-07', '14:00:00', 'FACE_TO_FACE', '업무 스트레스로 인한 상담을 받고 싶습니다.', 'N', 'APPROVED'),
('2025-08-01', '10:15:00', 'EMP002', '2025-08-07', '16:00:00', 'FACE_TO_FACE', '동료와의 관계 개선을 위한 상담을 받고 싶습니다.', 'Y', 'APPROVED'),
('2025-08-02', '14:20:00', 'EMP003', '2025-08-08', '10:00:00', 'FACE_TO_FACE', '진로 상담을 받고 싶습니다.', 'N', 'PENDING');

-- 샘플 상담사 배정 데이터
INSERT INTO TB_CNSL_ASSIGNMENT (APP_SEQ, CNSLR_EMP_ID, ASGN_DT, ASGN_TM, ASGN_STS_CD, ASGN_RSN) VALUES
(1, 'CNSLR001', '2025-08-01', '15:30:00', 'ASSIGNED', '스트레스 상담 전문가 배정'),
(2, 'CNSLR002', '2025-08-01', '16:45:00', 'ASSIGNED', '인간관계 상담 전문가 배정');

-- 샘플 상담 일지 데이터
INSERT INTO TB_EXP_CNSL_DRY (APP_SEQ, CNSL_DT, CNSL_TM, CNSLR_EMP_ID, CNSL_CNTN, CNSL_RES, CNSL_RCMD, CNSL_DUR) VALUES
(1, '2025-08-07', '14:00:00', 'CNSLR001', '업무 스트레스로 인한 상담을 진행했습니다. 상담자는 업무량 증가와 업무 환경 변화로 인한 스트레스를 호소했습니다.', '스트레스 관리 기법 교육 및 정기적인 상담을 권고했습니다.', '스트레스 관리 기법을 매일 실천하고, 2주 후 재상담을 권고합니다.', 60),
(2, '2025-08-07', '16:00:00', 'CNSLR002', '동료와의 관계 개선을 위한 상담을 진행했습니다. 상담자는 팀 내 소통 부족으로 인한 갈등을 호소했습니다.', '의사소통 기법 교육 및 팀 빌딩 활동을 권고했습니다.', '의사소통 기법을 실천하고, 팀 리더와의 정기적인 면담을 권고합니다.', 60);

-- =================================================================
-- 스크립트 실행 완료
-- =================================================================
-- 
-- 이 스크립트는 ERI Employee Rights Protection System의 전체 데이터베이스 스키마를 생성합니다.
-- 
-- 생성된 테이블:
-- 1. 셀프케어 관리: TB_SELF_CARE_STS_MSG, TB_SELF_CARE_BG_IMG
-- 2. 습관형성 챌린지: TB_HBT_CHLG_GOAL, TB_CHLG_GOAL_ACHV_DTL
-- 3. 프로그램 신청: TB_PGM_APP_DTL, TB_PRE_ASGN_PERF_DTL, TB_PGM_APRV_DTL
-- 4. 상담 신청: TB_CNSL_APP, TB_CNSL_APP_DTL, TB_CNSL_APRV_DTL
-- 5. 바로가기 관리: TB_SCT_LST
-- 6. 공통 코드: TB_CMN_GRP_CD, TB_CMN_DTL_CD
-- 7. 직원권익게시판: TB_EMP_RT_BRD, TB_EMP_RT_CMT, TB_EMP_RT_LIKE, TB_EMP_RT_VIEW_LOG
-- 8. 전문가 상담: TB_EXP_CNSL_APP, TB_EXP_CNSL_DRY, TB_CNSLR_SCHEDULE, TB_CNSL_ASSIGNMENT, TB_CNSL_TIME_LIMIT, TB_CNSLR_INFO
-- 
-- 주의사항:
-- 1. 스크립트 실행 전 데이터베이스가 생성되어 있어야 합니다.
-- 2. 필요한 경우 샘플 데이터를 추가로 입력하세요.
-- 3. 실제 운영 환경에서는 보안 설정을 추가로 구성하세요.
-- 
-- =================================================================

