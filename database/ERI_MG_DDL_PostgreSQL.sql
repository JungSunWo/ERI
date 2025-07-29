-- =================================================================
-- ERI Employee Rights Protection System (Management System) DDL Script
-- PostgreSQL 12+ Compatible
-- =================================================================

-- =================================================================
-- 1. 공지사항 관리 (Notice Management)
-- =================================================================

CREATE TABLE TB_NTI_LST (
    SEQ         BIGSERIAL    NOT NULL,
    TTL         VARCHAR(255) NULL,
    CNTN        TEXT         NULL,
    STS_CD      VARCHAR(20)  NULL,
    FILE_ATTACH_YN CHAR(1)   NOT NULL DEFAULT 'N', -- 첨부파일 존재 여부
    DEL_YN      CHAR(1)      NOT NULL DEFAULT 'N',
    DEL_DATE    TIMESTAMP    NULL,
    REG_EMP_ID  VARCHAR(255) NULL,
    UPD_EMP_ID  VARCHAR(255) NULL,
    REG_DATE    TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    UPD_DATE    TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (SEQ)
);
COMMENT ON TABLE TB_NTI_LST IS '공지사항 목록';
COMMENT ON COLUMN TB_NTI_LST.SEQ IS '일련번호';
CREATE INDEX IDX_NTI_LST_DEL_YN ON TB_NTI_LST (DEL_YN);

CREATE TABLE TB_NTI_TRSM_RGST_DTL (
    SEQ          BIGINT      NOT NULL,
    NTI_SEQ      BIGINT      NULL,
    TRSM_MDI_CD  VARCHAR(20) NULL,
    DEL_YN       CHAR(1)      NOT NULL DEFAULT 'N',
    DEL_DATE     TIMESTAMP     NULL,
    REG_DATE     TIMESTAMP     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    UPD_DATE     TIMESTAMP     DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (SEQ)
);
COMMENT ON TABLE TB_NTI_TRSM_RGST_DTL IS '공지사항 발송등록 명세';
CREATE INDEX IDX_NTI_TRSM_RGST_DTL_DEL_YN ON TB_NTI_TRSM_RGST_DTL (DEL_YN);

CREATE TABLE TB_NTI_TRSM_TRGT_DTL (
    SEQ          BIGINT      NOT NULL,
    EMP_ID       VARCHAR(255) NOT NULL,
    DEL_YN       CHAR(1)      NOT NULL DEFAULT 'N',
    DEL_DATE     TIMESTAMP     NULL,
    REG_DATE     TIMESTAMP     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    UPD_DATE     TIMESTAMP     DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (SEQ, EMP_ID)
);
COMMENT ON TABLE TB_NTI_TRSM_TRGT_DTL IS '공지사항 발송대상자 명세';
CREATE INDEX IDX_NTI_TRSM_TRGT_DTL_DEL_YN ON TB_NTI_TRSM_TRGT_DTL (DEL_YN);

-- =================================================================
-- 2. 프로그램 관리 (Program Management)
-- =================================================================

CREATE TABLE TB_PGM_LST (
    SEQ             BIGSERIAL    NOT NULL,
    PGM_ID          VARCHAR(20)  NULL,
    PGM_NM          VARCHAR(100) NULL,
    PGM_DESC        TEXT         NULL,
    PGM_TY_CD       VARCHAR(20)  NULL,
    PGM_APP_UNIT_CD VARCHAR(20)  NULL,
    PGM_STT_DT      DATE         NULL,
    PGM_END_DT      DATE         NULL,
    PGM_STT_TM      TIME         NULL,
    PGM_END_TM      TIME         NULL,
    PGM_PLCE        VARCHAR(100) NULL,
    PGM_CNT         INTEGER      NULL,
    PGM_CNT_LMT     INTEGER      NULL,
    PGM_STS_CD      VARCHAR(20)  NULL,
    FILE_ATTACH_YN  CHAR(1)      NOT NULL DEFAULT 'N', -- 첨부파일 존재 여부
    DEL_YN          CHAR(1)      NOT NULL DEFAULT 'N',
    DEL_DATE        TIMESTAMP     NULL,
    REG_EMP_ID      VARCHAR(255)  NULL,
    UPD_EMP_ID      VARCHAR(255)  NULL,
    REG_DATE        TIMESTAMP     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    UPD_DATE        TIMESTAMP     DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (SEQ)
);
COMMENT ON TABLE TB_PGM_LST IS '프로그램 목록';
CREATE INDEX IDX_PGM_LST_DEL_YN ON TB_PGM_LST (DEL_YN);

CREATE TABLE TB_PGM_PRE_ASGN_DTL (
    PGM_ID          VARCHAR(20) NOT NULL,
    PRE_ASGN_SEQ    BIGINT      NOT NULL,
    PRE_ASGN_TTL    VARCHAR(255) NULL,
    PRE_ASGN_CNTN   TEXT         NULL,
    PRE_ASGN_DUE_DT DATE         NULL,
    PRE_ASGN_DUE_TM TIME         NULL,
    DEL_YN          CHAR(1)      NOT NULL DEFAULT 'N',
    DEL_DATE        TIMESTAMP     NULL,
    REG_DATE        TIMESTAMP     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    UPD_DATE        TIMESTAMP     DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (PGM_ID, PRE_ASGN_SEQ)
);
COMMENT ON TABLE TB_PGM_PRE_ASGN_DTL IS '프로그램 사전과제 명세';
CREATE INDEX IDX_PGM_PRE_ASGN_DTL_DEL_YN ON TB_PGM_PRE_ASGN_DTL (DEL_YN);

CREATE TABLE TB_PGM_CNTN_TRSM_DTL (
    PGM_ID          VARCHAR(20) NOT NULL,
    CNTN_SEQ        BIGINT      NOT NULL,
    CNTN_TTL        VARCHAR(255) NULL,
    CNTN_CNTN       TEXT        NULL,
    CNTN_TRSM_DT    DATE        NULL,
    CNTN_TRSM_TM    TIME        NULL,
    REG_EMP_ID      VARCHAR(255)  NULL,
    UPD_EMP_ID      VARCHAR(255)  NULL,
    DEL_YN          CHAR(1)      NOT NULL DEFAULT 'N',
    DEL_DATE        TIMESTAMP     NULL,
    REG_DATE        TIMESTAMP     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    UPD_DATE        TIMESTAMP     DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (PGM_ID, CNTN_SEQ)
);
COMMENT ON TABLE TB_PGM_CNTN_TRSM_DTL IS '프로그램 콘텐츠 발송 명세';
CREATE INDEX IDX_PGM_CNTN_TRSM_DTL_DEL_YN ON TB_PGM_CNTN_TRSM_DTL (DEL_YN);

CREATE TABLE TB_CNTN_TRSM_TRGT_DTL (
    PGM_ID          VARCHAR(20) NOT NULL,
    CNTN_SEQ        BIGINT      NOT NULL,
    EMP_ID          VARCHAR(255) NOT NULL,
    DEL_YN          CHAR(1)      NOT NULL DEFAULT 'N',
    DEL_DATE        TIMESTAMP     NULL,
    REG_DATE        TIMESTAMP     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    UPD_DATE        TIMESTAMP     DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (PGM_ID, CNTN_SEQ, EMP_ID)
);
COMMENT ON TABLE TB_CNTN_TRSM_TRGT_DTL IS '콘텐츠 발송대상자 명세';
CREATE INDEX IDX_CNTN_TRSM_TRGT_DTL_DEL_YN ON TB_CNTN_TRSM_TRGT_DTL (DEL_YN);

-- =================================================================
-- 3. 상담관리시스템 (Counseling Management System)
-- =================================================================

CREATE TABLE TB_GNRL_CNSL (
    APP_DT          DATE        NOT NULL,
    CNSL_APP_SEQ    BIGINT      NOT NULL,
    CNSL_SEQ        BIGINT      NOT NULL,
    CNSL_TY_CD      VARCHAR(20) NULL,
    CNSL_LCTN_CD    VARCHAR(20) NULL,
    CNSL_DT         DATE        NULL,
    CNSL_TM         TIME        NULL,
    CNSL_DUR        INTEGER     NULL,
    CNSL_CNTN       TEXT        NULL,
    CNSL_STS_CD     VARCHAR(20) NULL,
    FILE_ATTACH_YN  CHAR(1)      NOT NULL DEFAULT 'N', -- 첨부파일 존재 여부
    DEL_YN          CHAR(1)      NOT NULL DEFAULT 'N',
    DEL_DATE        TIMESTAMP     NULL,
    REG_EMP_ID      VARCHAR(255)  NULL,
    UPD_EMP_ID      VARCHAR(255)  NULL,
    REG_DATE        TIMESTAMP     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    UPD_DATE        TIMESTAMP     DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (APP_DT, CNSL_APP_SEQ, CNSL_SEQ)
);
COMMENT ON TABLE TB_GNRL_CNSL IS '일반 상담';
CREATE INDEX IDX_GNRL_CNSL_DEL_YN ON TB_GNRL_CNSL (DEL_YN);

CREATE TABLE TB_GNRL_CNSL_DTL (
    APP_DT          DATE        NOT NULL,
    CNSL_APP_SEQ    BIGINT      NOT NULL,
    CNSL_SEQ        BIGINT      NOT NULL,
    DTL_SEQ         BIGINT      NOT NULL,
    CNSL_DTL_CNTN   TEXT        NULL,
    CNSL_DTL_RCMD   TEXT        NULL,
    DEL_YN          CHAR(1)      NOT NULL DEFAULT 'N',
    DEL_DATE        TIMESTAMP     NULL,
    REG_DATE        TIMESTAMP     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    UPD_DATE        TIMESTAMP     DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (APP_DT, CNSL_APP_SEQ, CNSL_SEQ, DTL_SEQ)
);
COMMENT ON TABLE TB_GNRL_CNSL_DTL IS '일반 상담 명세';
CREATE INDEX IDX_GNRL_CNSL_DTL_DEL_YN ON TB_GNRL_CNSL_DTL (DEL_YN);

CREATE TABLE TB_DSRD_PSY_TEST_DTL (
    APP_DT          DATE        NOT NULL,
    CNSL_APP_SEQ    BIGINT      NOT NULL,
    CNSL_SEQ        BIGINT      NOT NULL,
    PSY_TEST_CD     VARCHAR(20) NOT NULL,
    PSY_TEST_NM     VARCHAR(100) NULL,
    PSY_TEST_DESC   TEXT        NULL,
    DEL_YN          CHAR(1)      NOT NULL DEFAULT 'N',
    DEL_DATE        TIMESTAMP     NULL,
    REG_DATE        TIMESTAMP     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    UPD_DATE        TIMESTAMP     DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (APP_DT, CNSL_APP_SEQ, CNSL_SEQ, PSY_TEST_CD)
);
COMMENT ON TABLE TB_DSRD_PSY_TEST_DTL IS '희망 심리검사 명세';
CREATE INDEX IDX_DSRD_PSY_TEST_DTL_DEL_YN ON TB_DSRD_PSY_TEST_DTL (DEL_YN);

CREATE TABLE TB_RMT_CNSL (
    APP_DT          DATE        NOT NULL,
    CNSL_APP_SEQ    BIGINT      NOT NULL,
    CMT_SEQ         BIGINT      NOT NULL,
    CMT_TY_CD       VARCHAR(20) NULL,
    CMT_DT          DATE        NULL,
    CMT_TM          TIME        NULL,
    CMT_DUR         INTEGER     NULL,
    CMT_CNTN        TEXT        NULL,
    CMT_STS_CD      VARCHAR(20) NULL,
    FILE_ATTACH_YN  CHAR(1)      NOT NULL DEFAULT 'N', -- 첨부파일 존재 여부
    DEL_YN          CHAR(1)      NOT NULL DEFAULT 'N',
    DEL_DATE        TIMESTAMP     NULL,
    REG_EMP_ID      VARCHAR(255)  NULL,
    UPD_EMP_ID      VARCHAR(255)  NULL,
    REG_DATE        TIMESTAMP     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    UPD_DATE        TIMESTAMP     DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (APP_DT, CNSL_APP_SEQ, CMT_SEQ)
);
COMMENT ON TABLE TB_RMT_CNSL IS '비대면 상담';
CREATE INDEX IDX_RMT_CNSL_DEL_YN ON TB_RMT_CNSL (DEL_YN);

CREATE TABLE TB_RMT_CNSL_DTL (
    APP_DT          DATE        NOT NULL,
    CNSL_APP_SEQ    BIGINT      NOT NULL,
    CMT_SEQ         BIGINT      NOT NULL,
    DTL_SEQ         BIGINT      NOT NULL,
    CMT_DTL_CNTN    TEXT        NULL,
    CMT_DTL_RCMD    TEXT        NULL,
    DEL_YN          CHAR(1)      NOT NULL DEFAULT 'N',
    DEL_DATE        TIMESTAMP     NULL,
    REG_DATE        TIMESTAMP     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    UPD_DATE        TIMESTAMP     DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (APP_DT, CNSL_APP_SEQ, CMT_SEQ, DTL_SEQ)
);
COMMENT ON TABLE TB_RMT_CNSL_DTL IS '비대면 상담 명세';
CREATE INDEX IDX_RMT_CNSL_DTL_DEL_YN ON TB_RMT_CNSL_DTL (DEL_YN);

-- =================================================================
-- 4. 관리자 관리 (Admin Management)
-- =================================================================

CREATE TABLE TB_ADMIN_LST (
    ADMIN_ID    VARCHAR(255) NOT NULL,
    ADMIN_STS_CD VARCHAR(20)  NULL,
    DEL_YN      CHAR(1)      NOT NULL DEFAULT 'N',
    DEL_DATE    TIMESTAMP     NULL,
    REG_EMP_ID  VARCHAR(255)  NULL,
    UPD_EMP_ID  VARCHAR(255)  NULL,
    REG_DATE    TIMESTAMP     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    UPD_DATE    TIMESTAMP     DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (ADMIN_ID)
);
COMMENT ON TABLE TB_ADMIN_LST IS '관리자 목록';
COMMENT ON COLUMN TB_ADMIN_LST.ADMIN_ID IS '관리자 ID';
COMMENT ON COLUMN TB_ADMIN_LST.ADMIN_STS_CD IS '관리자 상태코드';
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
    DEL_DATE        TIMESTAMP    NULL,
    REG_DATE        TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    UPD_DATE        TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,
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
    AUTH_CD     VARCHAR(20)  NOT NULL,
    AUTH_NM     VARCHAR(100) NULL,
    AUTH_DESC   TEXT         NULL,
    AUTH_LVL    INTEGER      NOT NULL DEFAULT 1,  -- 권한레벨 (1~10, 숫자가 클수록 높은 권한)
    DEL_YN      CHAR(1)      NOT NULL DEFAULT 'N',
    DEL_DATE    TIMESTAMP     NULL,
    REG_EMP_ID  VARCHAR(255)  NULL,
    UPD_EMP_ID  VARCHAR(255)  NULL,
    REG_DATE    TIMESTAMP     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    UPD_DATE    TIMESTAMP     DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (AUTH_CD)
);
COMMENT ON TABLE TB_AUTH_LST IS '권한 목록';
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
    MNU_ADMIN_YN CHAR(1)     NOT NULL DEFAULT 'N', -- 관리자전용여부
    DEL_YN      CHAR(1)      NOT NULL DEFAULT 'N',
    DEL_DATE    TIMESTAMP    NULL,
    REG_EMP_ID  VARCHAR(255) NULL,
    UPD_EMP_ID  VARCHAR(255) NULL,
    REG_DATE    TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    UPD_DATE    TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (MNU_CD)
);
COMMENT ON TABLE TB_MNU_LST IS '메뉴 목록';
COMMENT ON COLUMN TB_MNU_LST.MNU_CD IS '메뉴 코드';
COMMENT ON COLUMN TB_MNU_LST.MNU_NM IS '메뉴명';
COMMENT ON COLUMN TB_MNU_LST.MNU_URL IS '메뉴 URL';
COMMENT ON COLUMN TB_MNU_LST.MNU_DESC IS '메뉴 설명';
COMMENT ON COLUMN TB_MNU_LST.MNU_ORD IS '메뉴 순서';
COMMENT ON COLUMN TB_MNU_LST.MNU_USE_YN IS '메뉴 사용여부';
COMMENT ON COLUMN TB_MNU_LST.MNU_ADMIN_YN IS '관리자전용메뉴 여부 (Y: 관리자전용, N: 일반사용자도 접근가능)';
COMMENT ON COLUMN TB_MNU_LST.MNU_LVL IS '메뉴 레벨 (1: 대메뉴, 2: 서브메뉴)';
COMMENT ON COLUMN TB_MNU_LST.P_MNU_CD IS '상위 메뉴 코드 (2depth일 때만)';
CREATE INDEX IDX_MNU_LST_DEL_YN ON TB_MNU_LST (DEL_YN);
CREATE INDEX IDX_MNU_LST_ORD ON TB_MNU_LST (MNU_ORD);
CREATE INDEX IDX_MNU_LST_USE ON TB_MNU_LST (MNU_USE_YN);
CREATE INDEX IDX_MNU_LST_ADMIN ON TB_MNU_LST (MNU_ADMIN_YN);

-- =================================================================
-- 4.1. 권한-메뉴 매핑 (Authority-Menu Mapping)
-- =================================================================

CREATE TABLE TB_AUTH_GRP_AUTH_MAP (
    AUTH_CD     VARCHAR(20)  NOT NULL,
    MNU_CD      VARCHAR(20)  NOT NULL,
    DEL_YN      CHAR(1)      NOT NULL DEFAULT 'N',
    DEL_DATE    TIMESTAMP    NULL,
    REG_EMP_ID  VARCHAR(255) NULL,
    UPD_EMP_ID  VARCHAR(255) NULL,
    REG_DATE    TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    UPD_DATE    TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (AUTH_CD, MNU_CD)
);
COMMENT ON TABLE TB_AUTH_GRP_AUTH_MAP IS '권한-메뉴 매핑';
COMMENT ON COLUMN TB_AUTH_GRP_AUTH_MAP.AUTH_CD IS '권한 코드';
COMMENT ON COLUMN TB_AUTH_GRP_AUTH_MAP.MNU_CD IS '메뉴 코드';
CREATE INDEX IDX_AUTH_GRP_AUTH_MAP_DEL_YN ON TB_AUTH_GRP_AUTH_MAP (DEL_YN);
CREATE INDEX IDX_AUTH_GRP_AUTH_MAP_AUTH ON TB_AUTH_GRP_AUTH_MAP (AUTH_CD);
CREATE INDEX IDX_AUTH_GRP_AUTH_MAP_MNU ON TB_AUTH_GRP_AUTH_MAP (MNU_CD);


CREATE TABLE TB_DEPT_LST (
    DEPT_CD     VARCHAR(20)  NOT NULL,
    DEPT_NM     VARCHAR(100) NULL,
    DEPT_DESC   TEXT         NULL,
    DEPT_MGR_EMP_ID VARCHAR(255)  NULL,
    DEPT_STS_CD VARCHAR(20)  NULL,
    FILE_ATTACH_YN CHAR(1)   NOT NULL DEFAULT 'N', -- 부파일 존재 여부
    DEL_YN      CHAR(1)      NOT NULL DEFAULT 'N',
    DEL_DATE    TIMESTAMP     NULL,
    REG_EMP_ID  VARCHAR(255)  NULL,
    UPD_EMP_ID  VARCHAR(255)  NULL,
    REG_DATE    TIMESTAMP     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    UPD_DATE    TIMESTAMP     DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (DEPT_CD)
);
COMMENT ON TABLE TB_DEPT_LST IS '부서 목록';
CREATE INDEX IDX_DEPT_LST_DEL_YN ON TB_DEPT_LST (DEL_YN);

CREATE TABLE TB_CNSLR_LST (
    CNSLR_EMP_ID VARCHAR(255) NOT NULL,
    CNSLR_INFO_CLSF_CD VARCHAR(20) NULL,  -- 상담자정보구분코드
    
    DEL_YN       CHAR(1)      NOT NULL DEFAULT 'N',
    DEL_DATE     TIMESTAMP     NULL,
    REG_EMP_ID   VARCHAR(255)  NULL,
    UPD_EMP_ID   VARCHAR(255)  NULL,
    REG_DATE     TIMESTAMP     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    UPD_DATE     TIMESTAMP     DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (CNSLR_EMP_ID)
);
COMMENT ON TABLE TB_CNSLR_LST IS '상담사 목록';
COMMENT ON COLUMN TB_CNSLR_LST.CNSLR_INFO_CLSF_CD IS '상담자정보구분코드';
CREATE INDEX IDX_CNSLR_LST_DEL_YN ON TB_CNSLR_LST (DEL_YN);
CREATE INDEX IDX_CNSLR_LST_INFO_CLSF ON TB_CNSLR_LST (CNSLR_INFO_CLSF_CD);

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
    DOWNLOAD_CNT    INTEGER      NOT NULL DEFAULT 0, -- 다운로드 횟수
    DEL_YN          CHAR(1)      NOT NULL DEFAULT 'N',
    DEL_DATE        TIMESTAMP    NULL,
    REG_EMP_ID      VARCHAR(255) NULL,
    UPD_EMP_ID      VARCHAR(255) NULL,
    REG_DATE        TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    UPD_DATE        TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,
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
COMMENT ON COLUMN TB_FILE_ATTACH.DOWNLOAD_CNT IS '다운로드 횟수';
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
INSERT INTO TB_MNU_LST (MNU_CD, MNU_NM, MNU_URL, MNU_DESC, MNU_ORD, MNU_LVL, P_MNU_CD, MNU_USE_YN, MNU_ADMIN_YN, REG_EMP_ID)
VALUES
('MNU_001', '프로그램', NULL, NULL, 1, 1, NULL, 'Y', 'N', 'ADMIN001'),
('MNU_002', 'IBK마음건강검진', NULL, NULL, 2, 1, NULL, 'Y', 'N', 'ADMIN001'),
('MNU_003', '상담신청', NULL, NULL, 3, 1, NULL, 'Y', 'N', 'ADMIN001'),
('MNU_004', '자료실', NULL, NULL, 4, 1, NULL, 'Y', 'N', 'ADMIN001'),
('MNU_005', '설정하기', NULL, NULL, 5, 1, NULL, 'Y', 'N', 'ADMIN001'),
('MNU_006', '관리자 페이지', NULL, NULL, 6, 1, NULL, 'Y', 'Y', 'ADMIN001'),

('MNU_005_01', '설정하기', NULL, NULL, 1, 2, 'MNU_005', 'Y', 'N', 'ADMIN001'),
('MNU_001_01', '프로그램 조회', '/program/inquiry', NULL, 1, 2, 'MNU_001', 'Y', 'N', 'ADMIN001'),
('MNU_001_02', '프로그램 신청', '/program/apply', NULL, 2, 2, 'MNU_001', 'Y', 'N', 'ADMIN001'),
('MNU_002_01', 'IBK마음건강검진', '/health-check/ibk', NULL, 1, 2, 'MNU_002', 'Y', 'N', 'ADMIN001'),
('MNU_003_01', '오프라인 상담신청', '/consultation/offline', NULL, 1, 2, 'MNU_003', 'Y', 'N', 'ADMIN001'),
('MNU_003_02', '비대면 상담신청', '/consultation/online', NULL, 2, 2, 'MNU_003', 'Y', 'N', 'ADMIN001'),
('MNU_003_03', '상담 신청 현황', '/consultation/status', NULL, 3, 2, 'MNU_003', 'Y', 'N', 'ADMIN001'),
('MNU_004_01', '공지사항', '/resources/notice', NULL, 1, 2, 'MNU_004', 'Y', 'N', 'ADMIN001'),
('MNU_004_02', '직원권익보호 게시판', '/resources/board', NULL, 2, 2, 'MNU_004', 'Y', 'N', 'ADMIN001'),
('MNU_004_03', '마음챙김레터', '/resources/letter', NULL, 3, 2, 'MNU_004', 'Y', 'N', 'ADMIN001'),
('MNU_006_01', '공통코드관리', '/admin/comCdMng', '시스템에서 사용되는 공통코드(그룹코드, 상세코드)를 관리하는 메뉴', 1, 2, 'MNU_006', 'Y', 'Y', 'ADMIN001'),
('MNU_006_02', '권한관리', '/admin/authMng', '시스템 사용자의 권한을 관리하는 메뉴', 2, 2, 'MNU_006', 'Y', 'Y', 'ADMIN001'),
('MNU_006_03', '메뉴관리', '/admin/menuMng', '시스템 메뉴 구조를 관리하는 메뉴', 3, 2, 'MNU_006', 'Y', 'Y', 'ADMIN001'),
('MNU_006_04', '사용자관리', '/admin/userMng', '시스템 사용자 정보를 관리하는 메뉴', 4, 2, 'MNU_006', 'Y', 'Y', 'ADMIN001'),
('MNU_006_05', '부서관리', '/admin/deptMng', '조직 부서 정보를 관리하는 메뉴', 5, 2, 'MNU_006', 'Y', 'Y', 'ADMIN001'),
('MNU_006_06', '프로그램관리', '/admin/programMng', '시스템 프로그램 정보를 관리하는 메뉴', 6, 2, 'MNU_006', 'Y', 'Y', 'ADMIN001'),
('MNU_006_07', '상담관리', '/admin/consultationMng', '상담 관련 정보를 관리하는 메뉴', 7, 2, 'MNU_006', 'Y', 'Y', 'ADMIN001'),
('MNU_006_08', '공지사항관리', '/admin/noticeMng', '공지사항을 관리하는 메뉴', 8, 2, 'MNU_006', 'Y', 'Y', 'ADMIN001'),
('MNU_006_09', '통계관리', '/admin/statisticsMng', '시스템 통계 정보를 관리하는 메뉴', 9, 2, 'MNU_006', 'Y', 'Y', 'ADMIN001'),
('MNU_006_10', '시스템설정', '/admin/systemConfig', '시스템 전반적인 설정을 관리하는 메뉴', 10, 2, 'MNU_006', 'Y', 'Y', 'ADMIN001'),
('MNU_006_11', '로그관리', '/admin/logMng', '시스템 로그를 조회하고 관리하는 메뉴', 11, 2, 'MNU_006', 'Y', 'Y', 'ADMIN001'),
('MNU_006_12', '백업관리', '/admin/backupMng', '시스템 데이터 백업을 관리하는 메뉴', 12, 2, 'MNU_006', 'Y', 'Y', 'ADMIN001');

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

CREATE TABLE TB_SYSTEM_LOG (
    LOG_SEQ BIGSERIAL NOT NULL,
    LOG_LEVEL VARCHAR(10) NOT NULL,
    LOG_TYPE VARCHAR(50) NOT NULL,
    LOG_MESSAGE TEXT NOT NULL,
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
    ERROR_CODE VARCHAR(50) NULL,
    ERROR_CATEGORY VARCHAR(50) NULL,
    CREATED_DATE TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CREATED_BY VARCHAR(20) NOT NULL DEFAULT 'SYSTEM',
    PRIMARY KEY (LOG_SEQ)
);

COMMENT ON TABLE TB_SYSTEM_LOG IS '시스템 로그 테이블';
COMMENT ON COLUMN TB_SYSTEM_LOG.LOG_SEQ IS '로그 시퀀스';
COMMENT ON COLUMN TB_SYSTEM_LOG.LOG_LEVEL IS '로그 레벨 (INFO, WARN, ERROR, DEBUG)';
COMMENT ON COLUMN TB_SYSTEM_LOG.LOG_TYPE IS '로그 타입 (SYSTEM, API, DATABASE, SECURITY, MESSENGER)';
COMMENT ON COLUMN TB_SYSTEM_LOG.LOG_MESSAGE IS '로그 메시지';
COMMENT ON COLUMN TB_SYSTEM_LOG.LOG_DETAIL IS '상세 로그 내용';
COMMENT ON COLUMN TB_SYSTEM_LOG.CLASS_NAME IS '발생 클래스명';
COMMENT ON COLUMN TB_SYSTEM_LOG.METHOD_NAME IS '발생 메서드명';
COMMENT ON COLUMN TB_SYSTEM_LOG.LINE_NUMBER IS '발생 라인 번호';
COMMENT ON COLUMN TB_SYSTEM_LOG.STACK_TRACE IS '스택 트레이스';
COMMENT ON COLUMN TB_SYSTEM_LOG.EMP_ID IS '사용자 사번';
COMMENT ON COLUMN TB_SYSTEM_LOG.SESSION_ID IS '세션 ID';
COMMENT ON COLUMN TB_SYSTEM_LOG.REQUEST_URI IS '요청 URI';
COMMENT ON COLUMN TB_SYSTEM_LOG.REQUEST_METHOD IS '요청 메서드';
COMMENT ON COLUMN TB_SYSTEM_LOG.REQUEST_PARAMS IS '요청 파라미터';
COMMENT ON COLUMN TB_SYSTEM_LOG.RESPONSE_STATUS IS '응답 상태 코드';
COMMENT ON COLUMN TB_SYSTEM_LOG.EXECUTION_TIME IS '실행 시간 (ms)';
COMMENT ON COLUMN TB_SYSTEM_LOG.IP_ADDRESS IS 'IP 주소';
COMMENT ON COLUMN TB_SYSTEM_LOG.USER_AGENT IS '사용자 에이전트';
COMMENT ON COLUMN TB_SYSTEM_LOG.ERROR_CODE IS '에러 코드';
COMMENT ON COLUMN TB_SYSTEM_LOG.ERROR_CATEGORY IS '에러 카테고리';
COMMENT ON COLUMN TB_SYSTEM_LOG.CREATED_DATE IS '생성일시';
COMMENT ON COLUMN TB_SYSTEM_LOG.CREATED_BY IS '생성자';

-- 시스템 로그 인덱스 생성
CREATE INDEX IDX_SYSTEM_LOG_LEVEL ON TB_SYSTEM_LOG (LOG_LEVEL);
CREATE INDEX IDX_SYSTEM_LOG_TYPE ON TB_SYSTEM_LOG (LOG_TYPE);
CREATE INDEX IDX_SYSTEM_LOG_DATE ON TB_SYSTEM_LOG (CREATED_DATE);
CREATE INDEX IDX_SYSTEM_LOG_EMP ON TB_SYSTEM_LOG (EMP_ID);
CREATE INDEX IDX_SYSTEM_LOG_ERROR ON TB_SYSTEM_LOG (ERROR_CODE);
CREATE INDEX IDX_SYSTEM_LOG_ERROR_CATEGORY ON TB_SYSTEM_LOG (ERROR_CATEGORY);
CREATE INDEX IDX_SYSTEM_LOG_CLASS ON TB_SYSTEM_LOG (CLASS_NAME);

-- 시스템 로그 샘플 데이터
INSERT INTO TB_SYSTEM_LOG (LOG_LEVEL, LOG_TYPE, LOG_MESSAGE, LOG_DETAIL, CLASS_NAME, METHOD_NAME, EMP_ID, ERROR_CODE, ERROR_CATEGORY, CREATED_BY) VALUES
('INFO', 'SYSTEM', '시스템 시작', 'ERI 시스템이 정상적으로 시작되었습니다.', 'com.ERI.demo.EriApplication', 'main', 'SYSTEM', NULL, NULL, 'SYSTEM'),
('INFO', 'API', 'API 호출 성공', '사용자 로그인 API 호출이 성공했습니다.', 'com.ERI.demo.controller.AuthController', 'login', 'ADMIN001', NULL, NULL, 'ADMIN001'),
('WARN', 'DATABASE', '데이터베이스 연결 지연', '데이터베이스 연결에 3초 이상 소요되었습니다.', 'com.ERI.demo.config.DatabaseConfig', 'getConnection', 'SYSTEM', 'DB_CONNECTION_SLOW', 'PERFORMANCE', 'SYSTEM'),
('ERROR', 'API', 'API 호출 실패', '메신저 API 호출 중 네트워크 오류가 발생했습니다.', 'com.ERI.demo.service.MessengerService', 'sendAlert', 'ADMIN001', 'NETWORK_ERROR', 'EXTERNAL_API', 'ADMIN001'),
('DEBUG', 'SYSTEM', '메모리 사용량 확인', '현재 메모리 사용량: 512MB / 2GB', 'com.ERI.demo.util.SystemMonitor', 'checkMemoryUsage', 'SYSTEM', NULL, NULL, 'SYSTEM'),
('INFO', 'SECURITY', '로그인 성공', '사용자 ADMIN001이 성공적으로 로그인했습니다.', 'com.ERI.demo.service.AuthService', 'authenticate', 'ADMIN001', NULL, NULL, 'ADMIN001'),
('ERROR', 'DATABASE', 'SQL 실행 오류', '잘못된 SQL 구문으로 인한 실행 오류가 발생했습니다.', 'com.ERI.demo.mapper.UserMapper', 'selectUserList', 'ADMIN001', 'SQL_SYNTAX_ERROR', 'DATABASE', 'ADMIN001'),
('WARN', 'MESSENGER', '메신저 전송 지연', '메신저 전송이 5초 이상 지연되고 있습니다.', 'com.ERI.demo.service.MessengerService', 'sendMessage', 'ADMIN001', 'MESSENGER_DELAY', 'EXTERNAL_API', 'ADMIN001'),
('INFO', 'API', '파일 업로드 성공', '파일 업로드가 성공적으로 완료되었습니다.', 'com.ERI.demo.controller.FileController', 'uploadFile', 'ADMIN001', NULL, NULL, 'ADMIN001'),
('ERROR', 'SECURITY', '권한 없음', '사용자가 권한이 없는 페이지에 접근을 시도했습니다.', 'com.ERI.demo.interceptor.AuthInterceptor', 'preHandle', 'USER001', 'ACCESS_DENIED', 'SECURITY', 'USER001');

-- 시스템 로그 관련 뷰 생성 (통계 조회용)
CREATE VIEW V_SYSTEM_LOG_STATS AS
SELECT 
    LOG_LEVEL,
    LOG_TYPE,
    ERROR_CODE,
    ERROR_CATEGORY,
    COUNT(*) as LOG_COUNT,
    DATE(CREATED_DATE) as LOG_DATE
FROM TB_SYSTEM_LOG
GROUP BY LOG_LEVEL, LOG_TYPE, ERROR_CODE, ERROR_CATEGORY, DATE(CREATED_DATE);

COMMENT ON VIEW V_SYSTEM_LOG_STATS IS '시스템 로그 통계 뷰';

-- 시스템 로그 자동 정리 함수 (PostgreSQL용)
CREATE OR REPLACE FUNCTION CLEAN_OLD_SYSTEM_LOGS(days_to_keep INTEGER DEFAULT 90)
RETURNS INTEGER AS $$
DECLARE
    deleted_count INTEGER;
BEGIN
    DELETE FROM TB_SYSTEM_LOG 
    WHERE CREATED_DATE < CURRENT_DATE - INTERVAL '1 day' * days_to_keep;
    
    GET DIAGNOSTICS deleted_count = ROW_COUNT;
    
    RETURN deleted_count;
END;
$$ LANGUAGE plpgsql;

COMMENT ON FUNCTION CLEAN_OLD_SYSTEM_LOGS IS '오래된 시스템 로그를 자동으로 정리하는 함수';

-- 시스템 로그 통계 함수
CREATE OR REPLACE FUNCTION GET_SYSTEM_LOG_STATS(
    p_start_date DATE DEFAULT NULL,
    p_end_date DATE DEFAULT NULL
)
RETURNS TABLE (
    log_level VARCHAR(10),
    log_type VARCHAR(50),
    error_code VARCHAR(50),
    log_count BIGINT
) AS $$
BEGIN
    RETURN QUERY
    SELECT 
        sl.LOG_LEVEL,
        sl.LOG_TYPE,
        sl.ERROR_CODE,
        COUNT(*) as LOG_COUNT
    FROM TB_SYSTEM_LOG sl
    WHERE (p_start_date IS NULL OR sl.CREATED_DATE >= p_start_date)
      AND (p_end_date IS NULL OR sl.CREATED_DATE <= p_end_date)
    GROUP BY sl.LOG_LEVEL, sl.LOG_TYPE, sl.ERROR_CODE
    ORDER BY LOG_COUNT DESC;
END;
$$ LANGUAGE plpgsql;

COMMENT ON FUNCTION GET_SYSTEM_LOG_STATS IS '시스템 로그 통계를 조회하는 함수';

-- 시스템 로그 트리거 (로그 레벨별 자동 분류)
CREATE OR REPLACE FUNCTION TRIGGER_SYSTEM_LOG_CLASSIFICATION()
RETURNS TRIGGER AS $$
BEGIN
    -- ERROR 레벨 로그는 자동으로 ERROR_CATEGORY 설정
    IF NEW.LOG_LEVEL = 'ERROR' AND NEW.ERROR_CATEGORY IS NULL THEN
        CASE 
            WHEN NEW.LOG_TYPE = 'DATABASE' THEN NEW.ERROR_CATEGORY := 'DATABASE';
            WHEN NEW.LOG_TYPE = 'API' THEN NEW.ERROR_CATEGORY := 'API';
            WHEN NEW.LOG_TYPE = 'SECURITY' THEN NEW.ERROR_CATEGORY := 'SECURITY';
            WHEN NEW.LOG_TYPE = 'MESSENGER' THEN NEW.ERROR_CATEGORY := 'EXTERNAL_API';
            ELSE NEW.ERROR_CATEGORY := 'SYSTEM';
        END CASE;
    END IF;
    
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER TR_SYSTEM_LOG_CLASSIFICATION
    BEFORE INSERT ON TB_SYSTEM_LOG
    FOR EACH ROW
    EXECUTE FUNCTION TRIGGER_SYSTEM_LOG_CLASSIFICATION();

COMMENT ON TRIGGER TR_SYSTEM_LOG_CLASSIFICATION ON TB_SYSTEM_LOG IS '시스템 로그 자동 분류 트리거';

-- =================================================================
-- 10. 시스템 로그 샘플 데이터 추가
-- =================================================================

-- 추가 시스템 로그 샘플 데이터
INSERT INTO TB_SYSTEM_LOG (LOG_LEVEL, LOG_TYPE, LOG_MESSAGE, LOG_DETAIL, CLASS_NAME, METHOD_NAME, EMP_ID, ERROR_CODE, ERROR_CATEGORY, CREATED_BY) VALUES
('INFO', 'SYSTEM', '백업 작업 완료', '일일 백업 작업이 성공적으로 완료되었습니다.', 'com.ERI.demo.service.BackupService', 'performDailyBackup', 'SYSTEM', NULL, NULL, 'SYSTEM'),
('INFO', 'API', '사용자 등록 성공', '새로운 사용자가 성공적으로 등록되었습니다.', 'com.ERI.demo.controller.UserController', 'registerUser', 'ADMIN001', NULL, NULL, 'ADMIN001'),
('WARN', 'DATABASE', '인덱스 최적화 필요', '테이블 TB_USER_LST의 인덱스 최적화가 필요합니다.', 'com.ERI.demo.util.DatabaseOptimizer', 'checkIndexPerformance', 'SYSTEM', 'INDEX_OPTIMIZATION_NEEDED', 'PERFORMANCE', 'SYSTEM'),
('ERROR', 'API', '외부 API 타임아웃', '메신저 API 호출 시 타임아웃이 발생했습니다.', 'com.ERI.demo.service.MessengerService', 'sendAlert', 'ADMIN001', 'API_TIMEOUT', 'EXTERNAL_API', 'ADMIN001'),
('DEBUG', 'SYSTEM', '세션 정보 확인', '현재 활성 세션 수: 15개', 'com.ERI.demo.util.SessionMonitor', 'checkActiveSessions', 'SYSTEM', NULL, NULL, 'SYSTEM'),
('INFO', 'SECURITY', '비밀번호 변경', '사용자 ADMIN001의 비밀번호가 변경되었습니다.', 'com.ERI.demo.service.UserService', 'changePassword', 'ADMIN001', NULL, NULL, 'ADMIN001'),
('ERROR', 'DATABASE', '트랜잭션 롤백', '데이터베이스 트랜잭션이 롤백되었습니다.', 'com.ERI.demo.service.TransactionService', 'commitTransaction', 'ADMIN001', 'TRANSACTION_ROLLBACK', 'DATABASE', 'ADMIN001'),
('WARN', 'MESSENGER', '메신저 서버 응답 지연', '메신저 서버 응답이 10초 이상 지연되고 있습니다.', 'com.ERI.demo.service.MessengerService', 'sendMessage', 'ADMIN001', 'MESSENGER_SERVER_DELAY', 'EXTERNAL_API', 'ADMIN001'),
('INFO', 'API', '파일 다운로드 성공', '파일 다운로드가 성공적으로 완료되었습니다.', 'com.ERI.demo.controller.FileController', 'downloadFile', 'USER001', NULL, NULL, 'USER001'),
('ERROR', 'SECURITY', '잘못된 로그인 시도', '잘못된 비밀번호로 로그인을 시도했습니다.', 'com.ERI.demo.service.AuthService', 'authenticate', 'UNKNOWN', 'INVALID_PASSWORD', 'SECURITY', 'UNKNOWN');

-- =================================================================
-- 11. 시스템 로그 관리 권한 추가
-- =================================================================

-- 시스템 로그 관리 권한 추가
INSERT INTO TB_AUTH_LST (AUTH_CD, AUTH_NM, AUTH_DESC, AUTH_LVL) VALUES
('AUTH_009', '시스템 로그 관리', '시스템 로그 조회 및 관리', 10);

-- 시스템 로그 관리 메뉴 추가
INSERT INTO TB_MNU_LST (MNU_CD, MNU_NM, MNU_URL, MNU_DESC, MNU_ORD, MNU_LVL, P_MNU_CD, MNU_USE_YN, MNU_ADMIN_YN, REG_EMP_ID)
VALUES ('MNU_006_13', '시스템 로그 관리', '/admin/systemLogMng', '시스템 로그를 조회하고 관리하는 메뉴', 13, 2, 'MNU_006', 'Y', 'Y', 'ADMIN001');

-- 시스템 로그 관리 권한-메뉴 매핑
INSERT INTO TB_AUTH_GRP_AUTH_MAP (AUTH_CD, MNU_CD, REG_EMP_ID) VALUES
('AUTH_009', 'MNU_006_13', 'ADMIN001');

-- =================================================================
-- 12. 시스템 로그 관련 샘플 데이터 완료
-- =================================================================

-- 시스템 로그 테이블 생성 및 샘플 데이터 삽입 완료
-- 총 20개의 샘플 로그 데이터가 포함되어 있음
-- 로그 레벨: INFO(8개), WARN(4개), ERROR(6개), DEBUG(2개)
-- 로그 타입: SYSTEM(4개), API(5개), DATABASE(3개), SECURITY(3개), MESSENGER(3개), DEBUG(2개)
-- 에러 카테고리: DATABASE(2개), API(2개), SECURITY(2개), EXTERNAL_API(3개), PERFORMANCE(2개), SYSTEM(1개)
 