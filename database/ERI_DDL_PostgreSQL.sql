-- =================================================================
-- ERI Employee Rights Protection System (User System) DDL Script
-- Generated based on the provided schema diagram.
-- PostgreSQL 12+ Compatible
-- =================================================================

-- 데이터베이스 생성 (PostgreSQL에서는 CREATE DATABASE를 별도로 실행)
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

-- 1.1. 셀프케어 상태 메시지 (TB_SELF_CARE_STS_MSG)
CREATE TABLE TB_SELF_CARE_STS_MSG (
    SEQ         BIGSERIAL    NOT NULL,
    STS_MSG_CD  VARCHAR(20)  NULL,
    STS_MSG_CNTN VARCHAR(255) NULL,
    STS_MSG_RCMD_CD VARCHAR(20)  NULL,
    DEL_YN      CHAR(1)      NOT NULL DEFAULT 'N',
    DEL_DATE    TIMESTAMP    NULL,
    REG_EMP_ID  VARCHAR(255) NULL,
    UPD_EMP_ID  VARCHAR(255) NULL,
    REG_DATE    TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    UPD_DATE    TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (SEQ)
);

CREATE INDEX IDX_SELF_CARE_STS_MSG_DEL_YN ON TB_SELF_CARE_STS_MSG (DEL_YN);

-- 1.2. 셀프케어 배경 이미지 (TB_SELF_CARE_BG_IMG)
CREATE TABLE TB_SELF_CARE_BG_IMG (
    SEQ         BIGSERIAL    NOT NULL,
    BG_IMG_CD   VARCHAR(20)  NULL,
    BG_IMG_URL  VARCHAR(255) NULL,
    BG_IMG_DESC VARCHAR(255) NULL,
    DEL_YN      CHAR(1)      NOT NULL DEFAULT 'N',
    DEL_DATE    TIMESTAMP    NULL,
    REG_EMP_ID  VARCHAR(255) NULL,
    UPD_EMP_ID  VARCHAR(255) NULL,
    REG_DATE    TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    UPD_DATE    TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (SEQ)
);

CREATE INDEX IDX_SELF_CARE_BG_IMG_DEL_YN ON TB_SELF_CARE_BG_IMG (DEL_YN);

-- =================================================================
-- 2. 습관형성 챌린지 관리 (Habit Formation Challenge Management)
-- =================================================================

-- 2.1. 습관형성 챌린지 목표 (TB_HBT_CHLG_GOAL)
CREATE TABLE TB_HBT_CHLG_GOAL (
    SEQ         BIGSERIAL    NOT NULL,
    GOAL_TTL    VARCHAR(255) NULL,
    GOAL_CNTN   TEXT         NULL,
    GOAL_ACHV_UNIT_CD VARCHAR(20)  NULL,
    GOAL_ACHV_TGT_CNT INTEGER      NULL,
    GOAL_ACHV_RCMD_CD VARCHAR(20)  NULL,
    DEL_YN      CHAR(1)      NOT NULL DEFAULT 'N',
    DEL_DATE    TIMESTAMP    NULL,
    REG_EMP_ID  VARCHAR(255) NULL,
    UPD_EMP_ID  VARCHAR(255) NULL,
    REG_DATE    TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    UPD_DATE    TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (SEQ)
);

CREATE INDEX IDX_HBT_CHLG_GOAL_DEL_YN ON TB_HBT_CHLG_GOAL (DEL_YN);

-- 2.2. 챌린지 목표 달성 명세 (TB_CHLG_GOAL_ACHV_DTL)
CREATE TABLE TB_CHLG_GOAL_ACHV_DTL (
    SEQ         BIGSERIAL    NOT NULL,
    GOAL_SEQ    BIGINT       NULL,
    ACHV_DT     DATE         NULL,
    ACHV_CNT    INTEGER      NULL,
    ACHV_CNTN   TEXT         NULL,
    DEL_YN      CHAR(1)      NOT NULL DEFAULT 'N',
    DEL_DATE    TIMESTAMP    NULL,
    REG_EMP_ID  VARCHAR(255) NULL,
    UPD_EMP_ID  VARCHAR(255) NULL,
    REG_DATE    TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    UPD_DATE    TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (SEQ)
);

CREATE INDEX IDX_CHLG_GOAL_ACHV_DTL_DEL_YN ON TB_CHLG_GOAL_ACHV_DTL (DEL_YN);

-- =================================================================
-- 3. 프로그램 신청 (Program Application)
-- =================================================================

-- 3.1. 프로그램_신청_명세 (PROGRAM_APPLICATION_DETAIL)
CREATE TABLE TB_PGM_APP_DTL (
    EMP_ID          VARCHAR(255) NOT NULL,
    PGM_ID          VARCHAR(20) NOT NULL,
    APP_DT          DATE        NOT NULL,
    APP_TY_CD       VARCHAR(20) NULL,
    STS_CD          VARCHAR(20) NULL,
    PRE_ASGN_CMPL_YN CHAR(1)     NULL,
    APP_TM          TIME        NULL,
    DEL_YN          CHAR(1)      NOT NULL DEFAULT 'N',
    DEL_DATE        TIMESTAMP    NULL,
    REG_EMP_ID      VARCHAR(255) NULL,
    UPD_EMP_ID      VARCHAR(255) NULL,
    REG_DATE        TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    UPD_DATE        TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (EMP_ID, PGM_ID, APP_DT)
);

CREATE INDEX IDX_PGM_APP_DTL_DEL_YN ON TB_PGM_APP_DTL (DEL_YN);

-- 3.2. 사전과제_수행_명세 (PRE_ASSIGNMENT_PERFORMANCE_DETAIL)
CREATE TABLE TB_PRE_ASGN_PERF_DTL (
    EMP_ID            VARCHAR(255) NOT NULL,
    PGM_ID            VARCHAR(20) NOT NULL,
    PRE_ASGN_SEQ      BIGINT      NOT NULL,
    PERF_DT           DATE        NULL,
    SRVY_ITM_CNTN     TEXT        NULL,
    SRVY_ANS_CNTN     TEXT        NULL,
    STS_CD            VARCHAR(20) NULL,
    DEL_YN            CHAR(1)      NOT NULL DEFAULT 'N',
    DEL_DATE          TIMESTAMP    NULL,
    REG_EMP_ID        VARCHAR(255) NULL,
    UPD_EMP_ID        VARCHAR(255) NULL,
    REG_DATE          TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    UPD_DATE          TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (EMP_ID, PGM_ID, PRE_ASGN_SEQ)
);

CREATE INDEX IDX_PRE_ASGN_PERF_DTL_DEL_YN ON TB_PRE_ASGN_PERF_DTL (DEL_YN);

-- 3.3. 프로그램_승인_명세 (PROGRAM_APPROVAL_DETAIL)
CREATE TABLE TB_PGM_APRV_DTL (
    EMP_ID          VARCHAR(255) NOT NULL,
    PGM_ID          VARCHAR(20) NOT NULL,
    TRNS_DT         DATE        NOT NULL,
    SEQ             BIGINT      NOT NULL,
    APRVR_EMP_ID    VARCHAR(255) NULL,
    APRV_TY_CD      VARCHAR(20) NULL,
    STS_CD          VARCHAR(20) NULL,
    APRV_TM         TIME        NULL,
    DEL_YN          CHAR(1)      NOT NULL DEFAULT 'N',
    DEL_DATE        TIMESTAMP    NULL,
    REG_EMP_ID      VARCHAR(255) NULL,
    UPD_EMP_ID      VARCHAR(255) NULL,
    REG_DATE        TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    UPD_DATE        TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (EMP_ID, PGM_ID, TRNS_DT, SEQ)
);

CREATE INDEX IDX_PGM_APRV_DTL_DEL_YN ON TB_PGM_APRV_DTL (DEL_YN);

-- =================================================================
-- 4. 상담 신청 (Counseling Application)
-- =================================================================

-- 4.1. 상담_신청 (COUNSELING_APPLICATION)
CREATE TABLE TB_CNSL_APP (
    APP_DT          DATE         NOT NULL,
    SEQ             BIGINT       NOT NULL,
    CNSL_TY_CD      VARCHAR(20)  NULL,
    DSRD_CNSL_DT    DATE         NULL,
    GRVNC_TPC_CD    VARCHAR(20)  NULL,
    DSRD_PSY_TEST_CD VARCHAR(20)  NULL,
    CNSL_LCTN_CD    VARCHAR(20)  NULL,
    CNSLR_EMP_ID    VARCHAR(255)  NULL,
    STS_CD          VARCHAR(20)  NULL,
    TTL             VARCHAR(255) NULL,
    CNTN            TEXT         NULL,
    APRV_YN         CHAR(1)      NULL,
    APLCNT_EMP_MGMT_ID VARCHAR(20)  NULL,
    DEL_YN          CHAR(1)      NOT NULL DEFAULT 'N',
    DEL_DATE        TIMESTAMP    NULL,
    REG_EMP_ID      VARCHAR(255) NULL,
    UPD_EMP_ID      VARCHAR(255) NULL,
    REG_DATE        TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    UPD_DATE        TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (APP_DT, SEQ)
);

CREATE INDEX IDX_CNSL_APP_DEL_YN ON TB_CNSL_APP (DEL_YN);

-- 4.2. 상담_신청_명세 (COUNSELING_APPLICATION_DETAIL)
CREATE TABLE TB_CNSL_APP_DTL (
    APP_DT          DATE        NOT NULL,
    CNSL_APP_SEQ    BIGINT      NOT NULL,
    TRNS_DT         DATE        NOT NULL,
    TRNS_SEQ        BIGINT      NOT NULL,
    APP_TY_CD       VARCHAR(20) NULL,
    STS_CD          VARCHAR(20) NULL,
    APP_TM          TIME        NULL,
    DEL_YN          CHAR(1)      NOT NULL DEFAULT 'N',
    DEL_DATE        TIMESTAMP    NULL,
    REG_EMP_ID      VARCHAR(255) NULL,
    UPD_EMP_ID      VARCHAR(255) NULL,
    REG_DATE        TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    UPD_DATE        TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (APP_DT, CNSL_APP_SEQ, TRNS_DT, TRNS_SEQ)
);

CREATE INDEX IDX_CNSL_APP_DTL_DEL_YN ON TB_CNSL_APP_DTL (DEL_YN);

-- 4.3. 상담_승인_명세 (COUNSELING_APPROVAL_DETAIL)
CREATE TABLE TB_CNSL_APRV_DTL (
    APP_DT          DATE        NOT NULL,
    CNSL_APP_SEQ    BIGINT      NOT NULL,
    TRNS_DT         DATE        NOT NULL,
    APRVR_EMP_ID    VARCHAR(255) NULL,
    APRV_TY_CD      VARCHAR(20) NULL,
    STS_CD          VARCHAR(20) NULL,
    APRV_TM         TIME        NULL,
    DEL_YN          CHAR(1)      NOT NULL DEFAULT 'N',
    DEL_DATE        TIMESTAMP    NULL,
    REG_EMP_ID      VARCHAR(255) NULL,
    UPD_EMP_ID      VARCHAR(255) NULL,
    REG_DATE        TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    UPD_DATE        TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (APP_DT, CNSL_APP_SEQ, TRNS_DT)
);

CREATE INDEX IDX_CNSL_APRV_DTL_DEL_YN ON TB_CNSL_APRV_DTL (DEL_YN);

-- =================================================================
-- 5. 바로가기 관리 (Shortcut Management)
-- =================================================================

-- 5.1. 바로가기_목록 (SHORTCUT_LIST)
CREATE TABLE TB_SCT_LST (
    SEQ         BIGSERIAL    NOT NULL,
    SCT_CD      VARCHAR(20)  NULL,
    STS_CD      VARCHAR(20)  NULL,
    APLY_DT     DATE         NULL,
    URL         VARCHAR(255) NULL,
    DEL_YN      CHAR(1)      NOT NULL DEFAULT 'N',
    DEL_DATE    TIMESTAMP    NULL,
    REG_EMP_ID  VARCHAR(255) NULL,
    UPD_EMP_ID  VARCHAR(255) NULL,
    REG_DATE    TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    UPD_DATE    TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (SEQ)
);

CREATE INDEX IDX_SCT_LST_DEL_YN ON TB_SCT_LST (DEL_YN);

-- =================================================================
-- 6. 공통 코드 관리 (Common Code Management)
-- =================================================================

-- 6.1. 공통_그룹_코드 (COMMON_GROUP_CODE)
CREATE TABLE TB_CMN_GRP_CD (
    GRP_CD      VARCHAR(20)  NOT NULL,
    GRP_CD_NM   VARCHAR(100) NULL,
    GRP_CD_DESC VARCHAR(255) NULL,
    USE_YN      CHAR(1)      NOT NULL DEFAULT 'Y',
    DEL_YN      CHAR(1)      NOT NULL DEFAULT 'N',
    DEL_DATE    TIMESTAMP    NULL,
    REG_EMP_ID  VARCHAR(255) NULL,
    UPD_EMP_ID  VARCHAR(255) NULL,
    REG_DATE    TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    UPD_DATE    TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (GRP_CD)
);

CREATE INDEX IDX_CMN_GRP_CD_DEL_YN ON TB_CMN_GRP_CD (DEL_YN);

-- 6.2. 공통_상세_코드 (COMMON_DETAIL_CODE)
CREATE TABLE TB_CMN_DTL_CD (
    GRP_CD      VARCHAR(20)  NOT NULL,
    DTL_CD      VARCHAR(20)  NOT NULL,
    DTL_CD_NM   VARCHAR(100) NULL,
    DTL_CD_DESC VARCHAR(255) NULL,
    SORT_ORD    INTEGER      NULL,
    USE_YN      CHAR(1)      NOT NULL DEFAULT 'Y',
    DEL_YN      CHAR(1)      NOT NULL DEFAULT 'N',
    DEL_DATE    TIMESTAMP    NULL,
    REG_EMP_ID  VARCHAR(255) NULL,
    UPD_EMP_ID  VARCHAR(255) NULL,
    REG_DATE    TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    UPD_DATE    TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (GRP_CD, DTL_CD)
);

CREATE INDEX IDX_CMN_DTL_CD_DEL_YN ON TB_CMN_DTL_CD (DEL_YN);

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
('CNSL_TY', 'GENERAL', '일반', '일반 상담', 1, 'Y'),
('CNSL_TY', 'REMOTE', '비대면', '비대면 상담', 2, 'Y'),
('CNSLR_INFO_CLSF', 'PROFESSIONAL', '전문상담사', '전문 상담사', 1, 'Y'),
('CNSLR_INFO_CLSF', 'GENERAL', '일반상담사', '일반 상담사', 2, 'Y'),
('CNSLR_INFO_CLSF', 'INTERN', '인턴', '상담 인턴', 3, 'Y'),
('SCT_STS', 'NORMAL', '정상', '정상 상태', 1, 'Y'),
('SCT_STS', 'CANCEL', '취소', '취소된 상태', 2, 'Y'),
('SCT_STS', 'END', '종료', '종료된 상태', 3, 'Y');

-- =================================================================
-- 7. 직원권익게시판 관리 (Employee Rights Board Management)
-- =================================================================

-- 7.1. 직원권익게시판 목록 (TB_EMP_RIGHTS_BOARD)
CREATE TABLE TB_EMP_RIGHTS_BOARD (
    SEQ             BIGSERIAL    NOT NULL,
    TTL             VARCHAR(255) NULL,                    -- 제목
    CNTN            TEXT         NULL,                    -- 내용
    STS_CD          VARCHAR(20)  NULL,                    -- 상태코드 (ACTIVE/INACTIVE/DELETED)
    FILE_ATTACH_YN  CHAR(1)      NOT NULL DEFAULT 'N',    -- 첨부파일 존재 여부
    VIEW_CNT        INTEGER      NOT NULL DEFAULT 0,      -- 조회수
    LIKE_CNT        INTEGER      NOT NULL DEFAULT 0,      -- 좋아요 수
    DISLIKE_CNT     INTEGER      NOT NULL DEFAULT 0,      -- 싫어요 수
    CATEGORY_CD     VARCHAR(20)  NULL,                    -- 카테고리 코드 (GENERAL/COMPLAINT/SUGGESTION/QUESTION)
    SECRET_YN       CHAR(1)      NOT NULL DEFAULT 'N',    -- 비밀글 여부
    NOTICE_YN       CHAR(1)      NOT NULL DEFAULT 'N',    -- 공지글 여부
    DEL_YN          CHAR(1)      NOT NULL DEFAULT 'N',    -- 삭제여부
    DEL_DATE        TIMESTAMP    NULL,                    -- 삭제일시
    REG_EMP_ID      VARCHAR(255) NULL,                    -- 등록직원ID
    UPD_EMP_ID      VARCHAR(255) NULL,                    -- 수정직원ID
    REG_DATE        TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP, -- 등록일시
    UPD_DATE        TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,         -- 수정일시
    PRIMARY KEY (SEQ)
);

COMMENT ON TABLE TB_EMP_RIGHTS_BOARD IS '직원권익게시판 목록';
COMMENT ON COLUMN TB_EMP_RIGHTS_BOARD.SEQ IS '일련번호';
COMMENT ON COLUMN TB_EMP_RIGHTS_BOARD.TTL IS '제목';
COMMENT ON COLUMN TB_EMP_RIGHTS_BOARD.CNTN IS '내용';
COMMENT ON COLUMN TB_EMP_RIGHTS_BOARD.STS_CD IS '상태코드';
COMMENT ON COLUMN TB_EMP_RIGHTS_BOARD.FILE_ATTACH_YN IS '첨부파일 존재 여부';
COMMENT ON COLUMN TB_EMP_RIGHTS_BOARD.VIEW_CNT IS '조회수';
COMMENT ON COLUMN TB_EMP_RIGHTS_BOARD.LIKE_CNT IS '좋아요 수';
COMMENT ON COLUMN TB_EMP_RIGHTS_BOARD.DISLIKE_CNT IS '싫어요 수';
COMMENT ON COLUMN TB_EMP_RIGHTS_BOARD.CATEGORY_CD IS '카테고리 코드';
COMMENT ON COLUMN TB_EMP_RIGHTS_BOARD.SECRET_YN IS '비밀글 여부';
COMMENT ON COLUMN TB_EMP_RIGHTS_BOARD.NOTICE_YN IS '공지글 여부';

CREATE INDEX IDX_EMP_RIGHTS_BOARD_DEL_YN ON TB_EMP_RIGHTS_BOARD (DEL_YN);
CREATE INDEX IDX_EMP_RIGHTS_BOARD_CATEGORY ON TB_EMP_RIGHTS_BOARD (CATEGORY_CD);
CREATE INDEX IDX_EMP_RIGHTS_BOARD_NOTICE ON TB_EMP_RIGHTS_BOARD (NOTICE_YN);
CREATE INDEX IDX_EMP_RIGHTS_BOARD_REG_DATE ON TB_EMP_RIGHTS_BOARD (REG_DATE);

-- 7.2. 직원권익게시판 댓글 (TB_EMP_RIGHTS_COMMENT)
CREATE TABLE TB_EMP_RIGHTS_COMMENT (
    SEQ             BIGSERIAL    NOT NULL,
    BOARD_SEQ       BIGINT       NOT NULL,                -- 게시글 일련번호
    PARENT_SEQ      BIGINT       NULL,                    -- 부모 댓글 일련번호 (대댓글용)
    CNTN            TEXT         NOT NULL,                -- 댓글 내용
    DEPTH           INTEGER      NOT NULL DEFAULT 0,      -- 댓글 깊이 (0: 댓글, 1: 대댓글, 2: 대대댓글...)
    LIKE_CNT        INTEGER      NOT NULL DEFAULT 0,      -- 좋아요 수
    DISLIKE_CNT     INTEGER      NOT NULL DEFAULT 0,      -- 싫어요 수
    SECRET_YN       CHAR(1)      NOT NULL DEFAULT 'N',    -- 비밀댓글 여부
    DEL_YN          CHAR(1)      NOT NULL DEFAULT 'N',    -- 삭제여부
    DEL_DATE        TIMESTAMP    NULL,                    -- 삭제일시
    REG_EMP_ID      VARCHAR(255) NULL,                    -- 등록직원ID
    UPD_EMP_ID      VARCHAR(255) NULL,                    -- 수정직원ID
    REG_DATE        TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP, -- 등록일시
    UPD_DATE        TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,         -- 수정일시
    PRIMARY KEY (SEQ)
);

COMMENT ON TABLE TB_EMP_RIGHTS_COMMENT IS '직원권익게시판 댓글';
COMMENT ON COLUMN TB_EMP_RIGHTS_COMMENT.SEQ IS '댓글 일련번호';
COMMENT ON COLUMN TB_EMP_RIGHTS_COMMENT.BOARD_SEQ IS '게시글 일련번호';
COMMENT ON COLUMN TB_EMP_RIGHTS_COMMENT.PARENT_SEQ IS '부모 댓글 일련번호';
COMMENT ON COLUMN TB_EMP_RIGHTS_COMMENT.CNTN IS '댓글 내용';
COMMENT ON COLUMN TB_EMP_RIGHTS_COMMENT.DEPTH IS '댓글 깊이';
COMMENT ON COLUMN TB_EMP_RIGHTS_COMMENT.LIKE_CNT IS '좋아요 수';
COMMENT ON COLUMN TB_EMP_RIGHTS_COMMENT.DISLIKE_CNT IS '싫어요 수';
COMMENT ON COLUMN TB_EMP_RIGHTS_COMMENT.SECRET_YN IS '비밀댓글 여부';

CREATE INDEX IDX_EMP_RIGHTS_COMMENT_BOARD ON TB_EMP_RIGHTS_COMMENT (BOARD_SEQ);
CREATE INDEX IDX_EMP_RIGHTS_COMMENT_PARENT ON TB_EMP_RIGHTS_COMMENT (PARENT_SEQ);
CREATE INDEX IDX_EMP_RIGHTS_COMMENT_DEL_YN ON TB_EMP_RIGHTS_COMMENT (DEL_YN);
CREATE INDEX IDX_EMP_RIGHTS_COMMENT_DEPTH ON TB_EMP_RIGHTS_COMMENT (DEPTH);
CREATE INDEX IDX_EMP_RIGHTS_COMMENT_REG_DATE ON TB_EMP_RIGHTS_COMMENT (REG_DATE);

-- 7.3. 직원권익게시판 좋아요/싫어요 (TB_EMP_RIGHTS_LIKE)
CREATE TABLE TB_EMP_RIGHTS_LIKE (
    SEQ             BIGSERIAL    NOT NULL,
    BOARD_SEQ       BIGINT       NULL,                    -- 게시글 일련번호 (NULL이면 댓글 좋아요)
    COMMENT_SEQ     BIGINT       NULL,                    -- 댓글 일련번호 (NULL이면 게시글 좋아요)
    LIKE_TYPE       CHAR(1)      NOT NULL,                -- 좋아요 타입 (L: 좋아요, D: 싫어요)
    REG_EMP_ID      VARCHAR(255) NOT NULL,                -- 등록직원ID
    DEL_YN          CHAR(1)      NOT NULL DEFAULT 'N',    -- 삭제여부
    DEL_DATE        TIMESTAMP    NULL,                    -- 삭제일시
    UPD_EMP_ID      VARCHAR(255) NULL,                    -- 수정직원ID
    REG_DATE        TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP, -- 등록일시
    UPD_DATE        TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,         -- 수정일시
    PRIMARY KEY (SEQ),
    CONSTRAINT CHK_LIKE_TYPE CHECK (LIKE_TYPE IN ('L', 'D')),
    CONSTRAINT CHK_LIKE_TARGET CHECK (
        (BOARD_SEQ IS NOT NULL AND COMMENT_SEQ IS NULL) OR 
        (BOARD_SEQ IS NULL AND COMMENT_SEQ IS NOT NULL)
    )
);

COMMENT ON TABLE TB_EMP_RIGHTS_LIKE IS '직원권익게시판 좋아요/싫어요';
COMMENT ON COLUMN TB_EMP_RIGHTS_LIKE.SEQ IS '일련번호';
COMMENT ON COLUMN TB_EMP_RIGHTS_LIKE.BOARD_SEQ IS '게시글 일련번호';
COMMENT ON COLUMN TB_EMP_RIGHTS_LIKE.COMMENT_SEQ IS '댓글 일련번호';
COMMENT ON COLUMN TB_EMP_RIGHTS_LIKE.LIKE_TYPE IS '좋아요 타입 (L: 좋아요, D: 싫어요)';
COMMENT ON COLUMN TB_EMP_RIGHTS_LIKE.REG_EMP_ID IS '등록직원ID';

CREATE INDEX IDX_EMP_RIGHTS_LIKE_BOARD ON TB_EMP_RIGHTS_LIKE (BOARD_SEQ);
CREATE INDEX IDX_EMP_RIGHTS_LIKE_COMMENT ON TB_EMP_RIGHTS_LIKE (COMMENT_SEQ);
CREATE INDEX IDX_EMP_RIGHTS_LIKE_EMP ON TB_EMP_RIGHTS_LIKE (REG_EMP_ID);
CREATE INDEX IDX_EMP_RIGHTS_LIKE_TYPE ON TB_EMP_RIGHTS_LIKE (LIKE_TYPE);
CREATE INDEX IDX_EMP_RIGHTS_LIKE_DEL_YN ON TB_EMP_RIGHTS_LIKE (DEL_YN);

-- 7.4. 직원권익게시판 조회이력 (TB_EMP_RIGHTS_VIEW_LOG)
CREATE TABLE TB_EMP_RIGHTS_VIEW_LOG (
    SEQ             BIGSERIAL    NOT NULL,
    BOARD_SEQ       BIGINT       NOT NULL,                -- 게시글 일련번호
    REG_EMP_ID      VARCHAR(255) NULL,                    -- 조회자 직원ID (NULL이면 비로그인)
    IP_ADDR         VARCHAR(45)  NULL,                    -- IP 주소
    USER_AGENT      TEXT         NULL,                    -- 사용자 에이전트
    REG_DATE        TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP, -- 조회일시
    PRIMARY KEY (SEQ)
);

COMMENT ON TABLE TB_EMP_RIGHTS_VIEW_LOG IS '직원권익게시판 조회이력';
COMMENT ON COLUMN TB_EMP_RIGHTS_VIEW_LOG.SEQ IS '일련번호';
COMMENT ON COLUMN TB_EMP_RIGHTS_VIEW_LOG.BOARD_SEQ IS '게시글 일련번호';
COMMENT ON COLUMN TB_EMP_RIGHTS_VIEW_LOG.REG_EMP_ID IS '조회자 직원ID';
COMMENT ON COLUMN TB_EMP_RIGHTS_VIEW_LOG.IP_ADDR IS 'IP 주소';
COMMENT ON COLUMN TB_EMP_RIGHTS_VIEW_LOG.USER_AGENT IS '사용자 에이전트';

CREATE INDEX IDX_EMP_RIGHTS_VIEW_LOG_BOARD ON TB_EMP_RIGHTS_VIEW_LOG (BOARD_SEQ);
CREATE INDEX IDX_EMP_RIGHTS_VIEW_LOG_EMP ON TB_EMP_RIGHTS_VIEW_LOG (REG_EMP_ID);
CREATE INDEX IDX_EMP_RIGHTS_VIEW_LOG_DATE ON TB_EMP_RIGHTS_VIEW_LOG (REG_DATE);

-- =================================================================
-- 직원권익게시판 관련 공통 코드 추가
-- =================================================================

-- 게시판 카테고리 코드 추가
INSERT INTO TB_CMN_GRP_CD (GRP_CD, GRP_CD_NM, GRP_CD_DESC, USE_YN) VALUES
('BOARD_CATEGORY', '게시판 카테고리 코드', '직원권익게시판의 카테고리를 관리하기 위한 코드', 'Y'),
('BOARD_STATUS', '게시판 상태 코드', '직원권익게시판의 상태를 관리하기 위한 코드', 'Y');

INSERT INTO TB_CMN_DTL_CD (GRP_CD, DTL_CD, DTL_CD_NM, DTL_CD_DESC, SORT_ORD, USE_YN) VALUES
('BOARD_CATEGORY', 'GENERAL', '일반', '일반 게시글', 1, 'Y'),
('BOARD_CATEGORY', 'COMPLAINT', '불만/민원', '불만사항 및 민원', 2, 'Y'),
('BOARD_CATEGORY', 'SUGGESTION', '건의사항', '건의사항', 3, 'Y'),
('BOARD_CATEGORY', 'QUESTION', '질문', '질문', 4, 'Y'),
('BOARD_STATUS', 'ACTIVE', '활성', '활성 상태', 1, 'Y'),
('BOARD_STATUS', 'INACTIVE', '비활성', '비활성 상태', 2, 'Y'),
('BOARD_STATUS', 'DELETED', '삭제', '삭제된 상태', 3, 'Y');

-- =================================================================
-- 직원권익게시판 샘플 데이터
-- =================================================================

-- 샘플 게시글 데이터
INSERT INTO TB_EMP_RIGHTS_BOARD (TTL, CNTN, STS_CD, CATEGORY_CD, NOTICE_YN, REG_EMP_ID, VIEW_CNT) VALUES
('직원권익보호 시스템 오픈 안내', '직원권익보호 시스템이 오픈되었습니다. 많은 관심과 참여 부탁드립니다.', 'ACTIVE', 'GENERAL', 'Y', 'ADMIN001', 0),
('근무환경 개선 건의사항', '사무실 조명이 너무 어두워서 업무에 지장이 있습니다. 개선 부탁드립니다.', 'ACTIVE', 'SUGGESTION', 'N', 'EMP001', 0),
('급여 관련 문의', '이번 달 급여에서 공제된 항목에 대해 문의드립니다.', 'ACTIVE', 'QUESTION', 'N', 'EMP002', 0),
('식당 메뉴 개선 요청', '직원식당 메뉴가 단조로워서 개선을 요청드립니다.', 'ACTIVE', 'SUGGESTION', 'N', 'EMP003', 0);

-- 샘플 댓글 데이터
INSERT INTO TB_EMP_RIGHTS_COMMENT (BOARD_SEQ, CNTN, DEPTH, REG_EMP_ID) VALUES
(1, '시스템이 정말 유용해 보입니다. 잘 활용하겠습니다.', 0, 'EMP001'),
(1, '감사합니다. 많은 도움이 될 것 같습니다.', 0, 'EMP002'),
(2, '저도 같은 생각입니다. 조명 개선이 필요해 보입니다.', 0, 'EMP004'),
(2, '관리부서에서 검토 후 개선하도록 하겠습니다.', 0, 'ADMIN001'),
(3, '급여팀에서 확인 후 답변드리겠습니다.', 0, 'ADMIN002');

-- 샘플 대댓글 데이터
INSERT INTO TB_EMP_RIGHTS_COMMENT (BOARD_SEQ, PARENT_SEQ, CNTN, DEPTH, REG_EMP_ID) VALUES
(2, 4, '빠른 대응 감사합니다.', 1, 'EMP001'),
(2, 4, '기대하겠습니다.', 1, 'EMP005'),
(3, 5, '답변 기다리겠습니다.', 1, 'EMP002');

-- 샘플 좋아요 데이터
INSERT INTO TB_EMP_RIGHTS_LIKE (BOARD_SEQ, LIKE_TYPE, REG_EMP_ID) VALUES
(1, 'L', 'EMP001'),
(1, 'L', 'EMP002'),
(2, 'L', 'EMP003'),
(2, 'L', 'EMP004'),
(3, 'L', 'EMP001');

-- 샘플 댓글 좋아요 데이터
INSERT INTO TB_EMP_RIGHTS_LIKE (COMMENT_SEQ, LIKE_TYPE, REG_EMP_ID) VALUES
(1, 'L', 'EMP002'),
(1, 'L', 'EMP003'),
(4, 'L', 'EMP001'),
(4, 'L', 'EMP005');

