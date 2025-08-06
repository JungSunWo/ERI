-- =================================================================
-- ERI 직원 정보 전용 데이터베이스 DDL (PostgreSQL)
-- =================================================================
-- 직원 정보는 보안상 별도 PostgreSQL 인스턴스에서 관리
-- 데이터베이스명: eri_employee_db
-- 포트: 5433 (메인 DB는 5432 사용)
-- 사용자: eri_employee_user
-- =================================================================

-- 데이터베이스 생성 (관리자 권한 필요)
-- CREATE DATABASE eri_employee_db
--     WITH 
--     OWNER = eri_employee_user
--     ENCODING = 'UTF8'
--     LC_COLLATE = 'ko_KR.UTF-8'
--     LC_CTYPE = 'ko_KR.UTF-8'
--     TABLESPACE = pg_default
--     CONNECTION LIMIT = -1;

-- 사용자 생성 (관리자 권한 필요)
-- CREATE USER eri_employee_user WITH PASSWORD 'password';
-- GRANT ALL PRIVILEGES ON DATABASE eri_employee_db TO eri_employee_user;

-- =================================================================
-- 1. 직원 기본 정보 테이블 (데이터 사전 기반)
-- =================================================================

CREATE TABLE TB_EMP_LST (
    EMP_ID          VARCHAR(255) NOT NULL,           -- 실제 직원번호 (외부 시스템에서 가져온 원본)
    ERI_EMP_ID      VARCHAR(20)  NOT NULL,           -- ERI 시스템 내부 식별번호 (E00000001 형식)
    EMP_NM          VARCHAR(50)  NULL,               -- 직원명 (emn)
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
    PRIMARY KEY (EMP_ID),
    UNIQUE (ERI_EMP_ID)
);

COMMENT ON TABLE TB_EMP_LST IS '직원 기본 정보 (데이터 사전 기반)';
COMMENT ON COLUMN TB_EMP_LST.EMP_ID IS '실제 직원번호 (외부 시스템에서 가져온 원본)';
COMMENT ON COLUMN TB_EMP_LST.ERI_EMP_ID IS 'ERI 시스템 내부 식별번호 (E00000001 형식)';
COMMENT ON COLUMN TB_EMP_LST.EMP_NM IS '직원명 (emn)';
COMMENT ON COLUMN TB_EMP_LST.GNDR_DCD IS '성별구분코드 (M:남성, F:여성)';
COMMENT ON COLUMN TB_EMP_LST.HLOF_YN IS '재직여부 (Y:재직, N:퇴직)';
COMMENT ON COLUMN TB_EMP_LST.ETBN_YMD IS '입행년월일 (은행 입행일자)';
COMMENT ON COLUMN TB_EMP_LST.BLNG_BRCD IS '소속부점코드';
COMMENT ON COLUMN TB_EMP_LST.BETEAM_CD IS '소속팀코드';
COMMENT ON COLUMN TB_EMP_LST.EXIG_BLNG_YMD IS '현소속년월일 (현재 소속 발령일자)';
COMMENT ON COLUMN TB_EMP_LST.TRTH_WORK_BRCD IS '실제근무부점코드';
COMMENT ON COLUMN TB_EMP_LST.RSWR_BRCD IS '주재근무부점코드';
COMMENT ON COLUMN TB_EMP_LST.JBTT_CD IS '직위코드';
COMMENT ON COLUMN TB_EMP_LST.JBTT_YMD IS '직위년월일 (직위 발령일자)';
COMMENT ON COLUMN TB_EMP_LST.NAME_NM IS '호칭명';
COMMENT ON COLUMN TB_EMP_LST.JBCL_CD IS '직급코드 (1:11급, 2:22급, 3:33급, 4:44급, 5:55급, 6:66급, 9:99급)';
COMMENT ON COLUMN TB_EMP_LST.TRTH_BIRT_YMD IS '실제생년월일';
COMMENT ON COLUMN TB_EMP_LST.SLCN_UNCG_BIRT_YMD IS '양력환산생년월일';
COMMENT ON COLUMN TB_EMP_LST.INSL_DCD IS '음양구분코드 (S:양력, L:음력)';
COMMENT ON COLUMN TB_EMP_LST.EMP_CPN IS '직원휴대폰번호';
COMMENT ON COLUMN TB_EMP_LST.EMP_EXTI_NO IS '직원내선번호';
COMMENT ON COLUMN TB_EMP_LST.EAD IS '이메일주소';
COMMENT ON COLUMN TB_EMP_LST.DEL_YN IS '삭제 여부 (Y/N)';
COMMENT ON COLUMN TB_EMP_LST.DEL_DT IS '삭제 일시';
COMMENT ON COLUMN TB_EMP_LST.REG_DT IS '등록 일시';
COMMENT ON COLUMN TB_EMP_LST.UPD_DT IS '수정 일시';

-- 인덱스 생성
CREATE INDEX IDX_EMP_LST_DEL_YN ON TB_EMP_LST (DEL_YN);
CREATE INDEX IDX_EMP_LST_ERI_ID ON TB_EMP_LST (ERI_EMP_ID);
CREATE INDEX IDX_EMP_LST_HLOF_YN ON TB_EMP_LST (HLOF_YN);
CREATE INDEX IDX_EMP_LST_BLNG_BRCD ON TB_EMP_LST (BLNG_BRCD);
CREATE INDEX IDX_EMP_LST_BETEAM_CD ON TB_EMP_LST (BETEAM_CD);
CREATE INDEX IDX_EMP_LST_JBCL_CD ON TB_EMP_LST (JBCL_CD);
CREATE INDEX IDX_EMP_LST_ETBN_YMD ON TB_EMP_LST (ETBN_YMD);
CREATE INDEX IDX_EMP_LST_EAD ON TB_EMP_LST (EAD);
CREATE INDEX IDX_EMP_LST_EMP_CPN ON TB_EMP_LST (EMP_CPN);

-- =================================================================
-- 파일 요약
-- =================================================================
-- 생성된 테이블:
-- 1. TB_EMP_LST - 직원 기본 정보 (데이터 사전 기반)
-- 
-- 주요 특징:
-- - 직원 정보는 보안상 별도 PostgreSQL 인스턴스에서 관리
-- - 실제 직원번호와 ERI 시스템 내부 식별번호를 분리하여 관리
-- - 은행 데이터 사전 기반의 표준화된 컬럼명 사용
-- - 재직/퇴직 상태, 부서/팀 정보, 직급/직위 정보 등 포함
-- - 개인정보(휴대폰, 이메일 등) 포함
-- 
-- 인덱스:
-- - 삭제여부, ERI 직원ID, 재직여부, 소속부점, 소속팀, 직급, 입행일자, 이메일, 휴대폰
-- =================================================================