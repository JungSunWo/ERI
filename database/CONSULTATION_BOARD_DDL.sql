-- =====================================================
-- 상담 게시판 시스템 DDL (CNSL BRD System)
-- =====================================================
-- Q&A 형태의 고충상담 게시판을 위한 테이블 구조
-- 익명/기명 상담 지원, 관리자 답변 기능 포함
-- 익명 사용자 동일성 관리 기능 추가
-- =====================================================

-- 1. 상담 게시판 메인 테이블 (TB_CNSL_BRD)
CREATE TABLE TB_CNSL_BRD (
    SEQ                 BIGSERIAL       NOT NULL,                    -- 상담 일련번호 (PK)
    TTL                 VARCHAR(255)    NOT NULL,                    -- 제목
    CNTN                TEXT            NOT NULL,                    -- 내용
    STS_CD              VARCHAR(20)     NOT NULL DEFAULT 'STS001',   -- 상태코드 (STS001: 대기, STS002: 답변완료, STS003: 종료)
    CAT_CD              VARCHAR(20)     NOT NULL,                    -- 카테고리 코드 (CAT001: 업무, CAT002: 인사, CAT003: 복지, CAT004: 기타)
    ANON_YN             CHAR(1)         NOT NULL DEFAULT 'N',        -- 익명여부 (Y/N)
    NICK_NM             VARCHAR(100)    NULL,                        -- 익명 닉네임 (익명인 경우)
    FILE_ATT_YN         CHAR(1)         NOT NULL DEFAULT 'N',        -- 첨부파일 존재 여부
    VIEW_CNT            INTEGER         NOT NULL DEFAULT 0,          -- 조회수
    ANS_YN              CHAR(1)         NOT NULL DEFAULT 'N',        -- 답변여부 (Y/N)
    ANS_EMP_ID          VARCHAR(255)    NULL,                        -- 답변자 직원ID
    ANS_DT              TIMESTAMP       NULL,                        -- 답변일시
    PRI_CD              VARCHAR(20)     NOT NULL DEFAULT 'PRI003',   -- 우선순위 (PRI001: 긴급, PRI002: 높음, PRI003: 보통, PRI004: 낮음)
    URG_YN              CHAR(1)         NOT NULL DEFAULT 'N',        -- 긴급여부 (Y/N)
    DEL_YN              CHAR(1)         NOT NULL DEFAULT 'N',        -- 삭제여부
    DEL_DT              TIMESTAMP       NULL,                        -- 삭제일시
    REG_EMP_ID          VARCHAR(255)    NOT NULL,                    -- 등록직원ID
    UPD_EMP_ID          VARCHAR(255)    NULL,                        -- 수정직원ID
    REG_DT              TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP, -- 등록일시
    UPD_DT              TIMESTAMP       DEFAULT CURRENT_TIMESTAMP,   -- 수정일시
    PRIMARY KEY (SEQ)
);

-- 2. 상담 게시판 답변 테이블 (TB_CNSL_ANS)
CREATE TABLE TB_CNSL_ANS (
    SEQ                 BIGSERIAL       NOT NULL,                    -- 답변 일련번호 (PK)
    BRD_SEQ             BIGINT          NOT NULL,                    -- 상담 게시글 일련번호 (FK)
    CNTN                TEXT            NOT NULL,                    -- 답변 내용
    STS_CD              VARCHAR(20)     NOT NULL DEFAULT 'STS001',   -- 상태코드 (STS001: 임시저장, STS002: 등록완료)
    DEL_YN              CHAR(1)         NOT NULL DEFAULT 'N',        -- 삭제여부
    DEL_DT              TIMESTAMP       NULL,                        -- 삭제일시
    REG_EMP_ID          VARCHAR(255)    NOT NULL,                    -- 등록직원ID
    UPD_EMP_ID          VARCHAR(255)    NULL,                        -- 수정직원ID
    REG_DT              TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP, -- 등록일시
    UPD_DT              TIMESTAMP       DEFAULT CURRENT_TIMESTAMP,   -- 수정일시
    PRIMARY KEY (SEQ)
);

-- 3. 상담 게시판 파일 첨부 테이블 (TB_CNSL_FILE_ATT)
CREATE TABLE TB_CNSL_FILE_ATT (
    FILE_SEQ            BIGSERIAL       NOT NULL,                    -- 파일 시퀀스 (PK)
    BRD_SEQ             BIGINT          NULL,                        -- 상담 게시글 일련번호 (FK)
    ANS_SEQ             BIGINT          NULL,                        -- 답변 일련번호 (FK)
    ORIG_FILE_NM        VARCHAR(255)    NOT NULL,                    -- 원본 파일명
    STOR_FILE_NM        VARCHAR(255)    NOT NULL,                    -- 저장 파일명
    FILE_PATH           VARCHAR(500)    NOT NULL,                    -- 파일 경로
    FILE_SIZE           BIGINT          NOT NULL,                    -- 파일 크기 (bytes)
    FILE_EXT            VARCHAR(50),                                 -- 파일 확장자
    FILE_TYPE           VARCHAR(100),                                -- 파일 타입 (MIME 타입)
    DOWN_CNT            INTEGER         DEFAULT 0,                   -- 다운로드 횟수
    DEL_YN              CHAR(1)         NOT NULL DEFAULT 'N',        -- 삭제여부
    DEL_DT              TIMESTAMP       NULL,                        -- 삭제일시
    REG_EMP_ID          VARCHAR(255)    NOT NULL,                    -- 등록직원ID
    UPD_EMP_ID          VARCHAR(255)    NULL,                        -- 수정직원ID
    REG_DT              TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP, -- 등록일시
    UPD_DT              TIMESTAMP       DEFAULT CURRENT_TIMESTAMP,   -- 수정일시
    PRIMARY KEY (FILE_SEQ),
    CONSTRAINT CHK_FILE_TARGET CHECK (
        (BRD_SEQ IS NOT NULL AND ANS_SEQ IS NULL) OR
        (BRD_SEQ IS NULL AND ANS_SEQ IS NOT NULL)
    )
);

-- 4. 상담 게시판 조회 로그 테이블 (TB_CNSL_VIEW_LOG)
CREATE TABLE TB_CNSL_VIEW_LOG (
    SEQ                 BIGSERIAL       NOT NULL,                    -- 조회 로그 일련번호 (PK)
    BRD_SEQ             BIGINT          NOT NULL,                    -- 상담 게시글 일련번호 (FK)
    REG_EMP_ID          VARCHAR(255)    NOT NULL,                    -- 조회자 직원ID
    VIEW_DT             TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP, -- 조회일시
    IP_ADDR             VARCHAR(45),                                 -- IP 주소
    USER_AGENT          VARCHAR(500),                                -- 사용자 에이전트
    UPD_EMP_ID          VARCHAR(255)    NULL,                        -- 수정직원ID
    REG_DT              TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP, -- 등록일시
    UPD_DT              TIMESTAMP       DEFAULT CURRENT_TIMESTAMP,   -- 수정일시
    PRIMARY KEY (SEQ)
);

-- 5. 익명 사용자 관리 테이블 (TB_ANON_USER)
CREATE TABLE TB_ANON_USER (
    ANON_ID             VARCHAR(10)     NOT NULL,                    -- 익명 사용자 ID (A00000001 형식)
    NICK_NM             VARCHAR(100)    NOT NULL,                    -- 익명 닉네임
    REG_DT              TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP, -- 등록일시
    USE_YN              CHAR(1)         NOT NULL DEFAULT 'Y',        -- 사용여부
    DEL_YN              CHAR(1)         NOT NULL DEFAULT 'N',        -- 삭제 여부
    DEL_DT              TIMESTAMP       NULL,                        -- 삭제 일시
    PRIMARY KEY (ANON_ID),
    UNIQUE (NICK_NM)
);

-- 6. 익명 사용자 동일성 판단 테이블 (관리자 판단)
CREATE TABLE TB_ANON_ID_GRP (
    GRP_SEQ            BIGSERIAL       NOT NULL,                    -- 그룹 일련번호
    GRP_NM             VARCHAR(100)    NULL,                        -- 그룹명 (관리자 지정)
    GRP_DESC           VARCHAR(500)    NULL,                        -- 그룹 설명 (판단 근거)
    JUDG_EMP_ID        VARCHAR(255)    NOT NULL,                    -- 판단 관리자 ID
    JUDG_DT            TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP, -- 판단일시
    CONF_LVL           CHAR(1)         NOT NULL DEFAULT 'M',        -- 신뢰도 (H: 높음, M: 보통, L: 낮음)
    USE_YN             CHAR(1)         NOT NULL DEFAULT 'Y',        -- 사용여부
    DEL_YN             CHAR(1)         NOT NULL DEFAULT 'N',        -- 삭제 여부
    DEL_DT             TIMESTAMP       NULL,                        -- 삭제 일시
    REG_DT             TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP, -- 등록일시
    UPD_DT             TIMESTAMP       DEFAULT CURRENT_TIMESTAMP,   -- 수정일시
    PRIMARY KEY (GRP_SEQ)
);

-- 7. 익명 사용자 동일성 그룹 멤버 테이블
CREATE TABLE TB_ANON_ID_MEMB (
    MEMB_SEQ            BIGSERIAL       NOT NULL,                    -- 멤버 일련번호
    GRP_SEQ             BIGINT          NOT NULL,                    -- 그룹 일련번호
    ANON_ID             BIGINT          NOT NULL,                    -- 익명 사용자 ID
    ADD_EMP_ID          VARCHAR(255)    NOT NULL,                    -- 추가 관리자 ID
    ADD_DT              TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP, -- 추가일시
    ADD_REAS            VARCHAR(500)    NULL,                        -- 추가 사유
    USE_YN              CHAR(1)         NOT NULL DEFAULT 'Y',        -- 사용여부
    DEL_YN              CHAR(1)         NOT NULL DEFAULT 'N',        -- 삭제 여부
    DEL_DT              TIMESTAMP       NULL,                        -- 삭제 일시
    PRIMARY KEY (MEMB_SEQ),
    UNIQUE (GRP_SEQ, ANON_ID)
);

-- =====================================================
-- 외래키 제약조건 (삭제됨)
-- =====================================================

-- 외래키 제약조건은 제거되었습니다.
-- 필요시 애플리케이션 레벨에서 참조 무결성을 관리합니다.

-- =====================================================
-- 인덱스 생성
-- =====================================================

-- TB_CNSL_BRD 인덱스
CREATE INDEX IDX_CNSL_BRD_STS_CD ON TB_CNSL_BRD(STS_CD);
CREATE INDEX IDX_CNSL_BRD_CAT_CD ON TB_CNSL_BRD(CAT_CD);
CREATE INDEX IDX_CNSL_BRD_ANON_YN ON TB_CNSL_BRD(ANON_YN);

CREATE INDEX IDX_CNSL_BRD_ANS_YN ON TB_CNSL_BRD(ANS_YN);
CREATE INDEX IDX_CNSL_BRD_PRI_CD ON TB_CNSL_BRD(PRI_CD);
CREATE INDEX IDX_CNSL_BRD_URG_YN ON TB_CNSL_BRD(URG_YN);
CREATE INDEX IDX_CNSL_BRD_REG_EMP_ID ON TB_CNSL_BRD(REG_EMP_ID);
CREATE INDEX IDX_CNSL_BRD_ANS_EMP_ID ON TB_CNSL_BRD(ANS_EMP_ID);
CREATE INDEX IDX_CNSL_BRD_DEL_YN ON TB_CNSL_BRD(DEL_YN);
CREATE INDEX IDX_CNSL_BRD_REG_DT ON TB_CNSL_BRD(REG_DT);
CREATE INDEX IDX_CNSL_BRD_ANS_DT ON TB_CNSL_BRD(ANS_DT);

-- TB_CNSL_ANS 인덱스
CREATE INDEX IDX_CNSL_ANS_BRD_SEQ ON TB_CNSL_ANS(BRD_SEQ);
CREATE INDEX IDX_CNSL_ANS_STS_CD ON TB_CNSL_ANS(STS_CD);
CREATE INDEX IDX_CNSL_ANS_REG_EMP_ID ON TB_CNSL_ANS(REG_EMP_ID);
CREATE INDEX IDX_CNSL_ANS_DEL_YN ON TB_CNSL_ANS(DEL_YN);
CREATE INDEX IDX_CNSL_ANS_REG_DT ON TB_CNSL_ANS(REG_DT);

-- TB_CNSL_FILE_ATT 인덱스
CREATE INDEX IDX_CNSL_FILE_BRD_SEQ ON TB_CNSL_FILE_ATT(BRD_SEQ);
CREATE INDEX IDX_CNSL_FILE_ANS_SEQ ON TB_CNSL_FILE_ATT(ANS_SEQ);
CREATE INDEX IDX_CNSL_FILE_DEL_YN ON TB_CNSL_FILE_ATT(DEL_YN);
CREATE INDEX IDX_CNSL_FILE_REG_DT ON TB_CNSL_FILE_ATT(REG_DT);

-- TB_CNSL_VIEW_LOG 인덱스
CREATE INDEX IDX_CNSL_VIEW_BRD_SEQ ON TB_CNSL_VIEW_LOG(BRD_SEQ);
CREATE INDEX IDX_CNSL_VIEW_REG_EMP_ID ON TB_CNSL_VIEW_LOG(REG_EMP_ID);
CREATE INDEX IDX_CNSL_VIEW_DT ON TB_CNSL_VIEW_LOG(VIEW_DT);

-- TB_ANON_USER 인덱스
CREATE INDEX IDX_ANON_USER_USE_YN ON TB_ANON_USER(USE_YN);
CREATE INDEX IDX_ANON_USER_DEL_YN ON TB_ANON_USER(DEL_YN);

-- TB_ANON_ID_GRP 인덱스
CREATE INDEX IDX_ANON_ID_GRP_JUDG_EMP_ID ON TB_ANON_ID_GRP(JUDG_EMP_ID);
CREATE INDEX IDX_ANON_ID_GRP_JUDG_DT ON TB_ANON_ID_GRP(JUDG_DT);
CREATE INDEX IDX_ANON_ID_GRP_USE_YN ON TB_ANON_ID_GRP(USE_YN);
CREATE INDEX IDX_ANON_ID_GRP_DEL_YN ON TB_ANON_ID_GRP(DEL_YN);

-- TB_ANON_ID_MEMB 인덱스
CREATE INDEX IDX_ANON_ID_MEMB_GRP_SEQ ON TB_ANON_ID_MEMB(GRP_SEQ);
CREATE INDEX IDX_ANON_ID_MEMB_ANON_ID ON TB_ANON_ID_MEMB(ANON_ID);
CREATE INDEX IDX_ANON_ID_MEMB_ADD_EMP_ID ON TB_ANON_ID_MEMB(ADD_EMP_ID);
CREATE INDEX IDX_ANON_ID_MEMB_USE_YN ON TB_ANON_ID_MEMB(USE_YN);
CREATE INDEX IDX_ANON_ID_MEMB_DEL_YN ON TB_ANON_ID_MEMB(DEL_YN);

-- =====================================================
-- 테이블 코멘트 추가
-- =====================================================

COMMENT ON TABLE TB_CNSL_BRD IS '상담 게시판 메인 테이블';
COMMENT ON COLUMN TB_CNSL_BRD.SEQ IS '상담 일련번호';
COMMENT ON COLUMN TB_CNSL_BRD.TTL IS '제목';
COMMENT ON COLUMN TB_CNSL_BRD.CNTN IS '내용';
COMMENT ON COLUMN TB_CNSL_BRD.STS_CD IS '상태코드 (STS001: 대기, STS002: 답변완료, STS003: 종료)';
COMMENT ON COLUMN TB_CNSL_BRD.CAT_CD IS '카테고리 코드 (CAT001: 업무, CAT002: 인사, CAT003: 복지, CAT004: 기타)';
COMMENT ON COLUMN TB_CNSL_BRD.ANON_YN IS '익명여부 (Y/N)';

COMMENT ON COLUMN TB_CNSL_BRD.NICK_NM IS '익명 닉네임 (익명인 경우)';
COMMENT ON COLUMN TB_CNSL_BRD.FILE_ATT_YN IS '첨부파일 존재 여부';
COMMENT ON COLUMN TB_CNSL_BRD.VIEW_CNT IS '조회수';
COMMENT ON COLUMN TB_CNSL_BRD.ANS_YN IS '답변여부 (Y/N)';
COMMENT ON COLUMN TB_CNSL_BRD.ANS_EMP_ID IS '답변자 직원ID';
COMMENT ON COLUMN TB_CNSL_BRD.ANS_DT IS '답변일시';
COMMENT ON COLUMN TB_CNSL_BRD.PRI_CD IS '우선순위 (PRI001: 긴급, PRI002: 높음, PRI003: 보통, PRI004: 낮음)';
COMMENT ON COLUMN TB_CNSL_BRD.URG_YN IS '긴급여부 (Y/N)';
COMMENT ON COLUMN TB_CNSL_BRD.DEL_YN IS '삭제 여부 (Y/N)';
COMMENT ON COLUMN TB_CNSL_BRD.DEL_DT IS '삭제 일시';
COMMENT ON COLUMN TB_CNSL_BRD.REG_EMP_ID IS '등록 직원 ID';
COMMENT ON COLUMN TB_CNSL_BRD.UPD_EMP_ID IS '수정 직원 ID';
COMMENT ON COLUMN TB_CNSL_BRD.REG_DT IS '등록 일시';
COMMENT ON COLUMN TB_CNSL_BRD.UPD_DT IS '수정 일시';

COMMENT ON TABLE TB_CNSL_ANS IS '상담 게시판 답변 테이블';
COMMENT ON COLUMN TB_CNSL_ANS.SEQ IS '답변 일련번호';
COMMENT ON COLUMN TB_CNSL_ANS.BRD_SEQ IS '상담 게시글 일련번호';
COMMENT ON COLUMN TB_CNSL_ANS.CNTN IS '답변 내용';
COMMENT ON COLUMN TB_CNSL_ANS.STS_CD IS '상태코드 (STS001: 임시저장, STS002: 등록완료)';
COMMENT ON COLUMN TB_CNSL_ANS.REG_EMP_ID IS '답변자 직원ID';
COMMENT ON COLUMN TB_CNSL_ANS.DEL_YN IS '삭제 여부 (Y/N)';
COMMENT ON COLUMN TB_CNSL_ANS.DEL_DT IS '삭제 일시';
COMMENT ON COLUMN TB_CNSL_ANS.UPD_EMP_ID IS '수정 직원 ID';
COMMENT ON COLUMN TB_CNSL_ANS.REG_DT IS '등록 일시';
COMMENT ON COLUMN TB_CNSL_ANS.UPD_DT IS '수정 일시';

COMMENT ON TABLE TB_CNSL_FILE_ATT IS '상담 게시판 파일 첨부 테이블';
COMMENT ON COLUMN TB_CNSL_FILE_ATT.FILE_SEQ IS '파일 시퀀스';
COMMENT ON COLUMN TB_CNSL_FILE_ATT.BRD_SEQ IS '상담 게시글 일련번호';
COMMENT ON COLUMN TB_CNSL_FILE_ATT.ANS_SEQ IS '답변 일련번호';
COMMENT ON COLUMN TB_CNSL_FILE_ATT.ORIG_FILE_NM IS '원본 파일명';
COMMENT ON COLUMN TB_CNSL_FILE_ATT.STOR_FILE_NM IS '저장 파일명';
COMMENT ON COLUMN TB_CNSL_FILE_ATT.FILE_PATH IS '파일 경로';
COMMENT ON COLUMN TB_CNSL_FILE_ATT.FILE_SIZE IS '파일 크기 (bytes)';
COMMENT ON COLUMN TB_CNSL_FILE_ATT.FILE_EXT IS '파일 확장자';
COMMENT ON COLUMN TB_CNSL_FILE_ATT.FILE_TYPE IS '파일 타입 (MIME 타입)';
COMMENT ON COLUMN TB_CNSL_FILE_ATT.DOWN_CNT IS '다운로드 횟수';
COMMENT ON COLUMN TB_CNSL_FILE_ATT.DEL_YN IS '삭제 여부 (Y/N)';
COMMENT ON COLUMN TB_CNSL_FILE_ATT.DEL_DT IS '삭제 일시';
COMMENT ON COLUMN TB_CNSL_FILE_ATT.REG_EMP_ID IS '등록 직원 ID';
COMMENT ON COLUMN TB_CNSL_FILE_ATT.UPD_EMP_ID IS '수정 직원 ID';
COMMENT ON COLUMN TB_CNSL_FILE_ATT.REG_DT IS '등록 일시';
COMMENT ON COLUMN TB_CNSL_FILE_ATT.UPD_DT IS '수정 일시';

COMMENT ON TABLE TB_CNSL_VIEW_LOG IS '상담 게시판 조회 로그 테이블';
COMMENT ON COLUMN TB_CNSL_VIEW_LOG.SEQ IS '조회 로그 일련번호';
COMMENT ON COLUMN TB_CNSL_VIEW_LOG.BRD_SEQ IS '상담 게시글 일련번호';
COMMENT ON COLUMN TB_CNSL_VIEW_LOG.REG_EMP_ID IS '조회자 직원ID';
COMMENT ON COLUMN TB_CNSL_VIEW_LOG.VIEW_DT IS '조회일시';
COMMENT ON COLUMN TB_CNSL_VIEW_LOG.IP_ADDR IS 'IP 주소';
COMMENT ON COLUMN TB_CNSL_VIEW_LOG.USER_AGENT IS '사용자 에이전트';
COMMENT ON COLUMN TB_CNSL_VIEW_LOG.UPD_EMP_ID IS '수정 직원 ID';
COMMENT ON COLUMN TB_CNSL_VIEW_LOG.REG_DT IS '등록 일시';
COMMENT ON COLUMN TB_CNSL_VIEW_LOG.UPD_DT IS '수정 일시';

COMMENT ON TABLE TB_ANON_USER IS '익명 사용자 관리';
COMMENT ON COLUMN TB_ANON_USER.ANON_ID IS '익명 사용자 ID (A00000001 형식)';
COMMENT ON COLUMN TB_ANON_USER.NICK_NM IS '익명 닉네임';
COMMENT ON COLUMN TB_ANON_USER.REG_DT IS '등록일시';
COMMENT ON COLUMN TB_ANON_USER.USE_YN IS '사용여부';
COMMENT ON COLUMN TB_ANON_USER.DEL_YN IS '삭제 여부';
COMMENT ON COLUMN TB_ANON_USER.DEL_DT IS '삭제 일시';

COMMENT ON TABLE TB_ANON_ID_GRP IS '익명 사용자 동일성 그룹 관리';
COMMENT ON COLUMN TB_ANON_ID_GRP.GRP_SEQ IS '그룹 일련번호';
COMMENT ON COLUMN TB_ANON_ID_GRP.GRP_NM IS '그룹명 (관리자 지정)';
COMMENT ON COLUMN TB_ANON_ID_GRP.GRP_DESC IS '그룹 설명 (판단 근거)';
COMMENT ON COLUMN TB_ANON_ID_GRP.JUDG_EMP_ID IS '판단 관리자 ID';
COMMENT ON COLUMN TB_ANON_ID_GRP.JUDG_DT IS '판단일시';
COMMENT ON COLUMN TB_ANON_ID_GRP.CONF_LVL IS '신뢰도 (H: 높음, M: 보통, L: 낮음)';
COMMENT ON COLUMN TB_ANON_ID_GRP.USE_YN IS '사용여부';
COMMENT ON COLUMN TB_ANON_ID_GRP.DEL_YN IS '삭제 여부';
COMMENT ON COLUMN TB_ANON_ID_GRP.DEL_DT IS '삭제 일시';
COMMENT ON COLUMN TB_ANON_ID_GRP.REG_DT IS '등록일시';
COMMENT ON COLUMN TB_ANON_ID_GRP.UPD_DT IS '수정일시';

COMMENT ON TABLE TB_ANON_ID_MEMB IS '익명 사용자 동일성 그룹 멤버';
COMMENT ON COLUMN TB_ANON_ID_MEMB.MEMB_SEQ IS '멤버 일련번호';
COMMENT ON COLUMN TB_ANON_ID_MEMB.GRP_SEQ IS '그룹 일련번호';
COMMENT ON COLUMN TB_ANON_ID_MEMB.ANON_ID IS '익명 사용자 ID';
COMMENT ON COLUMN TB_ANON_ID_MEMB.ADD_EMP_ID IS '추가 관리자 ID';
COMMENT ON COLUMN TB_ANON_ID_MEMB.ADD_DT IS '추가일시';
COMMENT ON COLUMN TB_ANON_ID_MEMB.ADD_REAS IS '추가 사유';
COMMENT ON COLUMN TB_ANON_ID_MEMB.USE_YN IS '사용여부';
COMMENT ON COLUMN TB_ANON_ID_MEMB.DEL_YN IS '삭제 여부';
COMMENT ON COLUMN TB_ANON_ID_MEMB.DEL_DT IS '삭제 일시';

-- =====================================================
-- 익명 사용자 등록 함수 제거 - 자바에서 처리
-- =====================================================
-- CREATE_ANONYMOUS_USER 함수는 자바 서비스에서 처리
-- AnonymousUserService.createAnonymousUser() 메서드로 대체

-- =====================================================
-- 기본 데이터 삽입 (공통 코드)
-- =====================================================

-- 상담 상태 코드
INSERT INTO TB_CMN_DTL_CD (GRP_CD, DTL_CD, DTL_CD_NM, DTL_CD_DESC, SORT_ORD, USE_YN, REG_EMP_ID) VALUES
('CNSL_STS', 'STS001', '대기', '답변 대기 중', 1, 'Y', 'ADMIN001'),
('CNSL_STS', 'STS002', '답변완료', '답변 완료', 2, 'Y', 'ADMIN001'),
('CNSL_STS', 'STS003', '종료', '상담 종료', 3, 'Y', 'ADMIN001');

-- 상담 카테고리 코드
INSERT INTO TB_CMN_DTL_CD (GRP_CD, DTL_CD, DTL_CD_NM, DTL_CD_DESC, SORT_ORD, USE_YN, REG_EMP_ID) VALUES
('CNSL_CAT', 'CAT001', '업무', '업무 관련 상담', 1, 'Y', 'ADMIN001'),
('CNSL_CAT', 'CAT002', '인사', '인사 관련 상담', 2, 'Y', 'ADMIN001'),
('CNSL_CAT', 'CAT003', '복지', '복지 관련 상담', 3, 'Y', 'ADMIN001'),
('CNSL_CAT', 'CAT004', '기타', '기타 상담', 4, 'Y', 'ADMIN001');

-- 우선순위 코드
INSERT INTO TB_CMN_DTL_CD (GRP_CD, DTL_CD, DTL_CD_NM, DTL_CD_DESC, SORT_ORD, USE_YN, REG_EMP_ID) VALUES
('CNSL_PRI', 'PRI001', '긴급', '긴급', 1, 'Y', 'ADMIN001'),
('CNSL_PRI', 'PRI002', '높음', '높음', 2, 'Y', 'ADMIN001'),
('CNSL_PRI', 'PRI003', '보통', '보통', 3, 'Y', 'ADMIN001'),
('CNSL_PRI', 'PRI004', '낮음', '낮음', 4, 'Y', 'ADMIN001');

-- 답변 상태 코드
INSERT INTO TB_CMN_DTL_CD (GRP_CD, DTL_CD, DTL_CD_NM, DTL_CD_DESC, SORT_ORD, USE_YN, REG_EMP_ID) VALUES
('ANS_STS', 'STS001', '임시저장', '임시저장', 1, 'Y', 'ADMIN001'),
('ANS_STS', 'STS002', '등록완료', '등록완료', 2, 'Y', 'ADMIN001');

-- =====================================================
-- 그룹 코드 데이터 삽입
-- =====================================================

-- 상담 상태 그룹 코드
INSERT INTO TB_CMN_GRP_CD (GRP_CD, GRP_CD_NM, GRP_CD_DESC, USE_YN) VALUES
('CNSL_STS', '상담 상태 코드', '상담의 상태를 관리하기 위한 코드 (대기/답변완료/종료)', 'Y');

-- 상담 카테고리 그룹 코드
INSERT INTO TB_CMN_GRP_CD (GRP_CD, GRP_CD_NM, GRP_CD_DESC, USE_YN) VALUES
('CNSL_CAT', '상담 카테고리 코드', '상담의 카테고리를 구분하기 위한 코드 (업무/인사/복지/기타)', 'Y');

-- 우선순위 그룹 코드
INSERT INTO TB_CMN_GRP_CD (GRP_CD, GRP_CD_NM, GRP_CD_DESC, USE_YN) VALUES
('CNSL_PRI', '상담 우선순위 코드', '상담의 우선순위를 관리하기 위한 코드 (긴급/높음/보통/낮음)', 'Y');

-- 답변 상태 그룹 코드
INSERT INTO TB_CMN_GRP_CD (GRP_CD, GRP_CD_NM, GRP_CD_DESC, USE_YN) VALUES
('ANS_STS', '답변 상태 코드', '답변의 상태를 관리하기 위한 코드 (임시저장/등록완료)', 'Y');

-- =====================================================
-- 샘플 데이터 삽입 (테스트용)
-- =====================================================

-- 익명 사용자 샘플 데이터
INSERT INTO TB_ANON_USER (NICK_NM) VALUES
('익명사용자1'),
('익명사용자2'),
('익명사용자3');

-- 익명 사용자 동일성 그룹 샘플 데이터
INSERT INTO TB_ANON_ID_GRP (GRP_NM, GRP_DESC, JUDG_EMP_ID, CONF_LVL) VALUES
('동일 사용자 그룹 1', '문체와 상담 패턴이 유사하여 동일 사용자로 판단', 'ADMIN001', 'H'),
('동일 사용자 그룹 2', '상담 시간대와 주제가 유사하여 동일 사용자로 추정', 'ADMIN002', 'M'),
('동일 사용자 그룹 3', 'IP 주소와 접속 패턴이 유사하여 동일 사용자로 판단', 'ADMIN001', 'H');

-- 익명 사용자 동일성 그룹 멤버 샘플 데이터
INSERT INTO TB_ANON_ID_MEMB (GRP_SEQ, ANON_ID, ADD_EMP_ID, ADD_REAS) VALUES
(1, 'A00000001', 'ADMIN001', '문체와 상담 스타일이 동일'),
(1, 'A00000002', 'ADMIN001', '동일한 문제 상황과 해결 방식을 제시'),
(2, 'A00000002', 'ADMIN002', '상담 시간대가 일치'),
(2, 'A00000003', 'ADMIN002', '유사한 업무 관련 상담 내용'),
(3, 'A00000001', 'ADMIN001', 'IP 주소와 접속 시간이 동일'),
(3, 'A00000003', 'ADMIN001', '동일한 네트워크 환경에서 접속');

-- 샘플 상담 게시글
INSERT INTO TB_CNSL_BRD (TTL, CNTN, STS_CD, CAT_CD, ANON_YN, NICK_NM, PRI_CD, URG_YN, REG_EMP_ID) VALUES
('업무 환경 개선 제안', '현재 업무 환경에서 개선이 필요한 부분들을 제안드립니다. 1. 조명 개선 2. 공기질 개선 3. 휴식 공간 확대', 'STS001', 'CAT001', 'N', NULL, 'PRI003', 'N', 'EMP001'),
('인사 평가 기준 문의', '인사 평가 기준에 대해 궁금한 점이 있어 문의드립니다. 평가 기준이 어떻게 설정되는지, 그리고 평가 결과가 어떻게 반영되는지 알려주시면 감사하겠습니다.', 'STS002', 'CAT002', 'Y', '익명사용자1', 'PRI002', 'N', 'EMP002'),
('복지 혜택 확대 요청', '직원들의 만족도를 높이기 위해 복지 혜택 확대를 요청드립니다. 특히 건강검진 항목 추가와 교육비 지원 확대를 희망합니다.', 'STS001', 'CAT003', 'N', NULL, 'PRI003', 'Y', 'EMP003');

-- 샘플 답변
INSERT INTO TB_CNSL_ANS (BRD_SEQ, CNTN, STS_CD, REG_EMP_ID) VALUES
(2, '인사 평가 기준에 대해 답변드리겠습니다.\n\n1. 평가 기준: 업무 성과(40%), 업무 능력(30%), 업무 태도(20%), 협력성(10%)\n2. 평가 결과 반영: 승진, 보너스, 교육 기회 등에 반영\n\n추가 문의사항이 있으시면 언제든 연락주세요.', 'STS002', 'ADMIN001');

-- =====================================================
-- 파일 요약
-- =====================================================
-- 생성된 테이블:
-- 1. TB_CNSL_BRD - 상담 게시판 메인 테이블
-- 2. TB_CNSL_ANS - 상담 게시판 답변 테이블
-- 3. TB_CNSL_FILE_ATT - 상담 게시판 파일 첨부 테이블
-- 4. TB_CNSL_VIEW_LOG - 상담 게시판 조회 로그 테이블
-- 5. TB_ANON_USER - 익명 사용자 관리
-- 6. TB_ANON_ID_GRP - 익명 사용자 동일성 그룹 관리
-- 7. TB_ANON_ID_MEMB - 익명 사용자 동일성 그룹 멤버
-- 
-- 주요 특징:
-- - Q&A 형태의 고충상담 게시판 시스템
-- - 익명/기명 상담 지원
-- - 관리자 답변 기능
-- - 익명 사용자 동일성 관리 기능
-- - 파일 첨부 및 조회 로그 기능
-- - 우선순위 및 긴급 여부 관리
-- 
-- 인덱스:
-- - 상태코드, 카테고리, 익명여부, 답변여부, 우선순위, 긴급여부
-- - 등록자, 답변자, 삭제여부, 등록일시, 답변일시
-- - 파일 첨부, 조회 로그, 익명 사용자 관리
-- =====================================================
-- DDL 완료
-- ===================================================== 