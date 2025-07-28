-- =====================================================
-- 상담 게시판 시스템 DDL (Consultation Board System)
-- =====================================================
-- Q&A 형태의 고충상담 게시판을 위한 테이블 구조
-- 익명/기명 상담 지원, 관리자 답변 기능 포함
-- 익명 사용자 동일성 관리 기능 추가
-- =====================================================

-- 1. 상담 게시판 메인 테이블 (TB_CONSULTATION_BOARD)
CREATE TABLE TB_CONSULTATION_BOARD (
    SEQ                 BIGSERIAL       NOT NULL,                    -- 상담 일련번호 (PK)
    TTL                 VARCHAR(255)    NOT NULL,                    -- 제목
    CNTN                TEXT            NOT NULL,                    -- 내용
    STS_CD              VARCHAR(20)     NOT NULL DEFAULT 'STS001',   -- 상태코드 (STS001: 대기, STS002: 답변완료, STS003: 종료)
    CATEGORY_CD         VARCHAR(20)     NOT NULL,                    -- 카테고리 코드 (CAT001: 업무, CAT002: 인사, CAT003: 복지, CAT004: 기타)
    ANONYMOUS_YN        CHAR(1)         NOT NULL DEFAULT 'N',        -- 익명여부 (Y/N)
    ANONYMOUS_ID        BIGINT          NULL,                        -- 익명 사용자 ID (익명인 경우)
    NICKNAME            VARCHAR(100)    NULL,                        -- 익명 닉네임 (익명인 경우)
    FILE_ATTACH_YN      CHAR(1)         NOT NULL DEFAULT 'N',        -- 첨부파일 존재 여부
    VIEW_CNT            INTEGER         NOT NULL DEFAULT 0,          -- 조회수
    ANSWER_YN           CHAR(1)         NOT NULL DEFAULT 'N',        -- 답변여부 (Y/N)
    ANSWER_EMP_ID       VARCHAR(255)    NULL,                        -- 답변자 직원ID
    ANSWER_DATE         TIMESTAMP       NULL,                        -- 답변일시
    PRIORITY_CD         VARCHAR(20)     NOT NULL DEFAULT 'PRI003',   -- 우선순위 (PRI001: 긴급, PRI002: 높음, PRI003: 보통, PRI004: 낮음)
    URGENT_YN           CHAR(1)         NOT NULL DEFAULT 'N',        -- 긴급여부 (Y/N)
    DEL_YN              CHAR(1)         NOT NULL DEFAULT 'N',        -- 삭제여부
    DEL_DATE            TIMESTAMP       NULL,                        -- 삭제일시
    REG_EMP_ID          VARCHAR(255)    NOT NULL,                    -- 등록직원ID
    UPD_EMP_ID          VARCHAR(255)    NULL,                        -- 수정직원ID
    REG_DATE            TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP, -- 등록일시
    UPD_DATE            TIMESTAMP       DEFAULT CURRENT_TIMESTAMP,   -- 수정일시
    PRIMARY KEY (SEQ)
);

-- 2. 상담 게시판 답변 테이블 (TB_CONSULTATION_ANSWER)
CREATE TABLE TB_CONSULTATION_ANSWER (
    SEQ                 BIGSERIAL       NOT NULL,                    -- 답변 일련번호 (PK)
    BOARD_SEQ           BIGINT          NOT NULL,                    -- 상담 게시글 일련번호 (FK)
    CNTN                TEXT            NOT NULL,                    -- 답변 내용
    STS_CD              VARCHAR(20)     NOT NULL DEFAULT 'STS001',   -- 상태코드 (STS001: 임시저장, STS002: 등록완료)
    DEL_YN              CHAR(1)         NOT NULL DEFAULT 'N',        -- 삭제여부
    DEL_DATE            TIMESTAMP       NULL,                        -- 삭제일시
    REG_EMP_ID          VARCHAR(255)    NOT NULL,                    -- 등록직원ID
    UPD_EMP_ID          VARCHAR(255)    NULL,                        -- 수정직원ID
    REG_DATE            TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP, -- 등록일시
    UPD_DATE            TIMESTAMP       DEFAULT CURRENT_TIMESTAMP,   -- 수정일시
    PRIMARY KEY (SEQ)
);

-- 3. 상담 게시판 파일 첨부 테이블 (TB_CONSULTATION_FILE_ATTACH)
CREATE TABLE TB_CONSULTATION_FILE_ATTACH (
    FILE_SEQ            BIGSERIAL       NOT NULL,                    -- 파일 시퀀스 (PK)
    BOARD_SEQ           BIGINT          NULL,                        -- 상담 게시글 일련번호 (FK)
    ANSWER_SEQ          BIGINT          NULL,                        -- 답변 일련번호 (FK)
    ORIGINAL_FILE_NAME  VARCHAR(255)    NOT NULL,                    -- 원본 파일명
    STORED_FILE_NAME    VARCHAR(255)    NOT NULL,                    -- 저장 파일명
    FILE_PATH           VARCHAR(500)    NOT NULL,                    -- 파일 경로
    FILE_SIZE           BIGINT          NOT NULL,                    -- 파일 크기 (bytes)
    FILE_EXT            VARCHAR(50),                                 -- 파일 확장자
    FILE_TYPE           VARCHAR(100),                                -- 파일 타입 (MIME 타입)
    DOWNLOAD_CNT        INTEGER         DEFAULT 0,                   -- 다운로드 횟수
    DEL_YN              CHAR(1)         NOT NULL DEFAULT 'N',        -- 삭제여부
    DEL_DATE            TIMESTAMP       NULL,                        -- 삭제일시
    REG_EMP_ID          VARCHAR(255)    NOT NULL,                    -- 등록직원ID
    UPD_EMP_ID          VARCHAR(255)    NULL,                        -- 수정직원ID
    REG_DATE            TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP, -- 등록일시
    UPD_DATE            TIMESTAMP       DEFAULT CURRENT_TIMESTAMP,   -- 수정일시
    PRIMARY KEY (FILE_SEQ),
    CONSTRAINT CHK_FILE_TARGET CHECK (
        (BOARD_SEQ IS NOT NULL AND ANSWER_SEQ IS NULL) OR
        (BOARD_SEQ IS NULL AND ANSWER_SEQ IS NOT NULL)
    )
);

-- 4. 상담 게시판 조회 로그 테이블 (TB_CONSULTATION_VIEW_LOG)
CREATE TABLE TB_CONSULTATION_VIEW_LOG (
    SEQ                 BIGSERIAL       NOT NULL,                    -- 조회 로그 일련번호 (PK)
    BOARD_SEQ           BIGINT          NOT NULL,                    -- 상담 게시글 일련번호 (FK)
    REG_EMP_ID          VARCHAR(255)    NOT NULL,                    -- 조회자 직원ID
    VIEW_DATE           TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP, -- 조회일시
    IP_ADDRESS          VARCHAR(45),                                 -- IP 주소
    USER_AGENT          VARCHAR(500),                                -- 사용자 에이전트
    UPD_EMP_ID          VARCHAR(255)    NULL,                        -- 수정직원ID
    REG_DATE            TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP, -- 등록일시
    UPD_DATE            TIMESTAMP       DEFAULT CURRENT_TIMESTAMP,   -- 수정일시
    PRIMARY KEY (SEQ)
);

-- 5. 익명 사용자 관리 테이블 (TB_ANONYMOUS_USER)
CREATE TABLE TB_ANONYMOUS_USER (
    ANONYMOUS_ID        BIGSERIAL       NOT NULL,                    -- 익명 사용자 ID (일련번호)
    NICKNAME            VARCHAR(100)    NOT NULL,                    -- 익명 닉네임
    REG_DATE            TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP, -- 등록일시
    USE_YN              CHAR(1)         NOT NULL DEFAULT 'Y',        -- 사용여부
    DEL_YN              CHAR(1)         NOT NULL DEFAULT 'N',        -- 삭제 여부
    DEL_DATE            TIMESTAMP       NULL,                        -- 삭제 일시
    PRIMARY KEY (ANONYMOUS_ID),
    UNIQUE (NICKNAME)
);

-- 6. 익명 사용자 동일성 판단 테이블 (관리자 판단)
CREATE TABLE TB_ANONYMOUS_IDENTITY_GROUP (
    GROUP_SEQ           BIGSERIAL       NOT NULL,                    -- 그룹 일련번호
    GROUP_NAME          VARCHAR(100)    NULL,                        -- 그룹명 (관리자 지정)
    GROUP_DESC          VARCHAR(500)    NULL,                        -- 그룹 설명 (판단 근거)
    JUDGE_EMP_ID        VARCHAR(255)    NOT NULL,                    -- 판단 관리자 ID
    JUDGE_DATE          TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP, -- 판단일시
    CONFIDENCE_LEVEL    CHAR(1)         NOT NULL DEFAULT 'M',        -- 신뢰도 (H: 높음, M: 보통, L: 낮음)
    USE_YN              CHAR(1)         NOT NULL DEFAULT 'Y',        -- 사용여부
    DEL_YN              CHAR(1)         NOT NULL DEFAULT 'N',        -- 삭제 여부
    DEL_DATE            TIMESTAMP       NULL,                        -- 삭제 일시
    REG_DATE            TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP, -- 등록일시
    UPD_DATE            TIMESTAMP       DEFAULT CURRENT_TIMESTAMP,   -- 수정일시
    PRIMARY KEY (GROUP_SEQ)
);

-- 7. 익명 사용자 동일성 그룹 멤버 테이블
CREATE TABLE TB_ANONYMOUS_IDENTITY_MEMBER (
    MEMBER_SEQ          BIGSERIAL       NOT NULL,                    -- 멤버 일련번호
    GROUP_SEQ           BIGINT          NOT NULL,                    -- 그룹 일련번호
    ANONYMOUS_ID        BIGINT          NOT NULL,                    -- 익명 사용자 ID
    ADD_EMP_ID          VARCHAR(255)    NOT NULL,                    -- 추가 관리자 ID
    ADD_DATE            TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP, -- 추가일시
    ADD_REASON          VARCHAR(500)    NULL,                        -- 추가 사유
    USE_YN              CHAR(1)         NOT NULL DEFAULT 'Y',        -- 사용여부
    DEL_YN              CHAR(1)         NOT NULL DEFAULT 'N',        -- 삭제 여부
    DEL_DATE            TIMESTAMP       NULL,                        -- 삭제 일시
    PRIMARY KEY (MEMBER_SEQ),
    UNIQUE (GROUP_SEQ, ANONYMOUS_ID)
);

-- =====================================================
-- 외래키 제약조건 추가
-- =====================================================

-- 익명 사용자 ID에 대한 외래키 제약조건 추가 (필요시 주석 해제)
-- ALTER TABLE TB_CONSULTATION_BOARD 
-- ADD CONSTRAINT FK_CONSULTATION_BOARD_ANONYMOUS 
-- FOREIGN KEY (ANONYMOUS_ID) REFERENCES TB_ANONYMOUS_USER(ANONYMOUS_ID);

-- =====================================================
-- 인덱스 생성
-- =====================================================

-- TB_CONSULTATION_BOARD 인덱스
CREATE INDEX IDX_CONSULTATION_BOARD_STS_CD ON TB_CONSULTATION_BOARD(STS_CD);
CREATE INDEX IDX_CONSULTATION_BOARD_CATEGORY_CD ON TB_CONSULTATION_BOARD(CATEGORY_CD);
CREATE INDEX IDX_CONSULTATION_BOARD_ANONYMOUS_YN ON TB_CONSULTATION_BOARD(ANONYMOUS_YN);
CREATE INDEX IDX_CONSULTATION_BOARD_ANONYMOUS_ID ON TB_CONSULTATION_BOARD(ANONYMOUS_ID);
CREATE INDEX IDX_CONSULTATION_BOARD_ANSWER_YN ON TB_CONSULTATION_BOARD(ANSWER_YN);
CREATE INDEX IDX_CONSULTATION_BOARD_PRIORITY_CD ON TB_CONSULTATION_BOARD(PRIORITY_CD);
CREATE INDEX IDX_CONSULTATION_BOARD_URGENT_YN ON TB_CONSULTATION_BOARD(URGENT_YN);
CREATE INDEX IDX_CONSULTATION_BOARD_REG_EMP_ID ON TB_CONSULTATION_BOARD(REG_EMP_ID);
CREATE INDEX IDX_CONSULTATION_BOARD_ANSWER_EMP_ID ON TB_CONSULTATION_BOARD(ANSWER_EMP_ID);
CREATE INDEX IDX_CONSULTATION_BOARD_DEL_YN ON TB_CONSULTATION_BOARD(DEL_YN);
CREATE INDEX IDX_CONSULTATION_BOARD_REG_DATE ON TB_CONSULTATION_BOARD(REG_DATE);
CREATE INDEX IDX_CONSULTATION_BOARD_ANSWER_DATE ON TB_CONSULTATION_BOARD(ANSWER_DATE);

-- TB_CONSULTATION_ANSWER 인덱스
CREATE INDEX IDX_CONSULTATION_ANSWER_BOARD_SEQ ON TB_CONSULTATION_ANSWER(BOARD_SEQ);
CREATE INDEX IDX_CONSULTATION_ANSWER_STS_CD ON TB_CONSULTATION_ANSWER(STS_CD);
CREATE INDEX IDX_CONSULTATION_ANSWER_REG_EMP_ID ON TB_CONSULTATION_ANSWER(REG_EMP_ID);
CREATE INDEX IDX_CONSULTATION_ANSWER_DEL_YN ON TB_CONSULTATION_ANSWER(DEL_YN);
CREATE INDEX IDX_CONSULTATION_ANSWER_REG_DATE ON TB_CONSULTATION_ANSWER(REG_DATE);

-- TB_CONSULTATION_FILE_ATTACH 인덱스
CREATE INDEX IDX_CONSULTATION_FILE_BOARD_SEQ ON TB_CONSULTATION_FILE_ATTACH(BOARD_SEQ);
CREATE INDEX IDX_CONSULTATION_FILE_ANSWER_SEQ ON TB_CONSULTATION_FILE_ATTACH(ANSWER_SEQ);
CREATE INDEX IDX_CONSULTATION_FILE_DEL_YN ON TB_CONSULTATION_FILE_ATTACH(DEL_YN);
CREATE INDEX IDX_CONSULTATION_FILE_REG_DATE ON TB_CONSULTATION_FILE_ATTACH(REG_DATE);

-- TB_CONSULTATION_VIEW_LOG 인덱스
CREATE INDEX IDX_CONSULTATION_VIEW_BOARD_SEQ ON TB_CONSULTATION_VIEW_LOG(BOARD_SEQ);
CREATE INDEX IDX_CONSULTATION_VIEW_REG_EMP_ID ON TB_CONSULTATION_VIEW_LOG(REG_EMP_ID);
CREATE INDEX IDX_CONSULTATION_VIEW_DATE ON TB_CONSULTATION_VIEW_LOG(VIEW_DATE);

-- TB_ANONYMOUS_USER 인덱스
CREATE INDEX IDX_ANONYMOUS_USER_USE_YN ON TB_ANONYMOUS_USER(USE_YN);
CREATE INDEX IDX_ANONYMOUS_USER_DEL_YN ON TB_ANONYMOUS_USER(DEL_YN);

-- TB_ANONYMOUS_IDENTITY_GROUP 인덱스
CREATE INDEX IDX_ANONYMOUS_IDENTITY_GROUP_JUDGE_EMP_ID ON TB_ANONYMOUS_IDENTITY_GROUP(JUDGE_EMP_ID);
CREATE INDEX IDX_ANONYMOUS_IDENTITY_GROUP_JUDGE_DATE ON TB_ANONYMOUS_IDENTITY_GROUP(JUDGE_DATE);
CREATE INDEX IDX_ANONYMOUS_IDENTITY_GROUP_USE_YN ON TB_ANONYMOUS_IDENTITY_GROUP(USE_YN);
CREATE INDEX IDX_ANONYMOUS_IDENTITY_GROUP_DEL_YN ON TB_ANONYMOUS_IDENTITY_GROUP(DEL_YN);

-- TB_ANONYMOUS_IDENTITY_MEMBER 인덱스
CREATE INDEX IDX_ANONYMOUS_IDENTITY_MEMBER_GROUP_SEQ ON TB_ANONYMOUS_IDENTITY_MEMBER(GROUP_SEQ);
CREATE INDEX IDX_ANONYMOUS_IDENTITY_MEMBER_ANONYMOUS_ID ON TB_ANONYMOUS_IDENTITY_MEMBER(ANONYMOUS_ID);
CREATE INDEX IDX_ANONYMOUS_IDENTITY_MEMBER_ADD_EMP_ID ON TB_ANONYMOUS_IDENTITY_MEMBER(ADD_EMP_ID);
CREATE INDEX IDX_ANONYMOUS_IDENTITY_MEMBER_USE_YN ON TB_ANONYMOUS_IDENTITY_MEMBER(USE_YN);
CREATE INDEX IDX_ANONYMOUS_IDENTITY_MEMBER_DEL_YN ON TB_ANONYMOUS_IDENTITY_MEMBER(DEL_YN);

-- =====================================================
-- 테이블 코멘트 추가
-- =====================================================

COMMENT ON TABLE TB_CONSULTATION_BOARD IS '상담 게시판 메인 테이블';
COMMENT ON COLUMN TB_CONSULTATION_BOARD.SEQ IS '상담 일련번호';
COMMENT ON COLUMN TB_CONSULTATION_BOARD.TTL IS '제목';
COMMENT ON COLUMN TB_CONSULTATION_BOARD.CNTN IS '내용';
COMMENT ON COLUMN TB_CONSULTATION_BOARD.STS_CD IS '상태코드 (STS001: 대기, STS002: 답변완료, STS003: 종료)';
COMMENT ON COLUMN TB_CONSULTATION_BOARD.CATEGORY_CD IS '카테고리 코드 (CAT001: 업무, CAT002: 인사, CAT003: 복지, CAT004: 기타)';
COMMENT ON COLUMN TB_CONSULTATION_BOARD.ANONYMOUS_YN IS '익명여부 (Y/N)';
COMMENT ON COLUMN TB_CONSULTATION_BOARD.ANONYMOUS_ID IS '익명 사용자 ID (익명인 경우)';
COMMENT ON COLUMN TB_CONSULTATION_BOARD.NICKNAME IS '익명 닉네임 (익명인 경우)';
COMMENT ON COLUMN TB_CONSULTATION_BOARD.FILE_ATTACH_YN IS '첨부파일 존재 여부';
COMMENT ON COLUMN TB_CONSULTATION_BOARD.VIEW_CNT IS '조회수';
COMMENT ON COLUMN TB_CONSULTATION_BOARD.ANSWER_YN IS '답변여부 (Y/N)';
COMMENT ON COLUMN TB_CONSULTATION_BOARD.ANSWER_EMP_ID IS '답변자 직원ID';
COMMENT ON COLUMN TB_CONSULTATION_BOARD.ANSWER_DATE IS '답변일시';
COMMENT ON COLUMN TB_CONSULTATION_BOARD.PRIORITY_CD IS '우선순위 (PRI001: 긴급, PRI002: 높음, PRI003: 보통, PRI004: 낮음)';
COMMENT ON COLUMN TB_CONSULTATION_BOARD.URGENT_YN IS '긴급여부 (Y/N)';

COMMENT ON TABLE TB_CONSULTATION_ANSWER IS '상담 게시판 답변 테이블';
COMMENT ON COLUMN TB_CONSULTATION_ANSWER.SEQ IS '답변 일련번호';
COMMENT ON COLUMN TB_CONSULTATION_ANSWER.BOARD_SEQ IS '상담 게시글 일련번호';
COMMENT ON COLUMN TB_CONSULTATION_ANSWER.CNTN IS '답변 내용';
COMMENT ON COLUMN TB_CONSULTATION_ANSWER.STS_CD IS '상태코드 (STS001: 임시저장, STS002: 등록완료)';
COMMENT ON COLUMN TB_CONSULTATION_ANSWER.REG_EMP_ID IS '답변자 직원ID';

COMMENT ON TABLE TB_CONSULTATION_FILE_ATTACH IS '상담 게시판 파일 첨부 테이블';
COMMENT ON COLUMN TB_CONSULTATION_FILE_ATTACH.FILE_SEQ IS '파일 시퀀스';
COMMENT ON COLUMN TB_CONSULTATION_FILE_ATTACH.BOARD_SEQ IS '상담 게시글 일련번호';
COMMENT ON COLUMN TB_CONSULTATION_FILE_ATTACH.ANSWER_SEQ IS '답변 일련번호';
COMMENT ON COLUMN TB_CONSULTATION_FILE_ATTACH.ORIGINAL_FILE_NAME IS '원본 파일명';
COMMENT ON COLUMN TB_CONSULTATION_FILE_ATTACH.STORED_FILE_NAME IS '저장 파일명';
COMMENT ON COLUMN TB_CONSULTATION_FILE_ATTACH.FILE_PATH IS '파일 경로';
COMMENT ON COLUMN TB_CONSULTATION_FILE_ATTACH.FILE_SIZE IS '파일 크기 (bytes)';
COMMENT ON COLUMN TB_CONSULTATION_FILE_ATTACH.FILE_EXT IS '파일 확장자';
COMMENT ON COLUMN TB_CONSULTATION_FILE_ATTACH.FILE_TYPE IS '파일 타입 (MIME 타입)';
COMMENT ON COLUMN TB_CONSULTATION_FILE_ATTACH.DOWNLOAD_CNT IS '다운로드 횟수';

COMMENT ON TABLE TB_CONSULTATION_VIEW_LOG IS '상담 게시판 조회 로그 테이블';
COMMENT ON COLUMN TB_CONSULTATION_VIEW_LOG.SEQ IS '조회 로그 일련번호';
COMMENT ON COLUMN TB_CONSULTATION_VIEW_LOG.BOARD_SEQ IS '상담 게시글 일련번호';
COMMENT ON COLUMN TB_CONSULTATION_VIEW_LOG.REG_EMP_ID IS '조회자 직원ID';
COMMENT ON COLUMN TB_CONSULTATION_VIEW_LOG.VIEW_DATE IS '조회일시';
COMMENT ON COLUMN TB_CONSULTATION_VIEW_LOG.IP_ADDRESS IS 'IP 주소';
COMMENT ON COLUMN TB_CONSULTATION_VIEW_LOG.USER_AGENT IS '사용자 에이전트';

COMMENT ON TABLE TB_ANONYMOUS_USER IS '익명 사용자 관리';
COMMENT ON COLUMN TB_ANONYMOUS_USER.ANONYMOUS_ID IS '익명 사용자 ID (일련번호)';
COMMENT ON COLUMN TB_ANONYMOUS_USER.NICKNAME IS '익명 닉네임';
COMMENT ON COLUMN TB_ANONYMOUS_USER.REG_DATE IS '등록일시';
COMMENT ON COLUMN TB_ANONYMOUS_USER.USE_YN IS '사용여부';
COMMENT ON COLUMN TB_ANONYMOUS_USER.DEL_YN IS '삭제 여부';
COMMENT ON COLUMN TB_ANONYMOUS_USER.DEL_DATE IS '삭제 일시';

COMMENT ON TABLE TB_ANONYMOUS_IDENTITY_GROUP IS '익명 사용자 동일성 그룹 관리';
COMMENT ON COLUMN TB_ANONYMOUS_IDENTITY_GROUP.GROUP_SEQ IS '그룹 일련번호';
COMMENT ON COLUMN TB_ANONYMOUS_IDENTITY_GROUP.GROUP_NAME IS '그룹명 (관리자 지정)';
COMMENT ON COLUMN TB_ANONYMOUS_IDENTITY_GROUP.GROUP_DESC IS '그룹 설명 (판단 근거)';
COMMENT ON COLUMN TB_ANONYMOUS_IDENTITY_GROUP.JUDGE_EMP_ID IS '판단 관리자 ID';
COMMENT ON COLUMN TB_ANONYMOUS_IDENTITY_GROUP.JUDGE_DATE IS '판단일시';
COMMENT ON COLUMN TB_ANONYMOUS_IDENTITY_GROUP.CONFIDENCE_LEVEL IS '신뢰도 (H: 높음, M: 보통, L: 낮음)';
COMMENT ON COLUMN TB_ANONYMOUS_IDENTITY_GROUP.USE_YN IS '사용여부';
COMMENT ON COLUMN TB_ANONYMOUS_IDENTITY_GROUP.DEL_YN IS '삭제 여부';
COMMENT ON COLUMN TB_ANONYMOUS_IDENTITY_GROUP.DEL_DATE IS '삭제 일시';
COMMENT ON COLUMN TB_ANONYMOUS_IDENTITY_GROUP.REG_DATE IS '등록일시';
COMMENT ON COLUMN TB_ANONYMOUS_IDENTITY_GROUP.UPD_DATE IS '수정일시';

COMMENT ON TABLE TB_ANONYMOUS_IDENTITY_MEMBER IS '익명 사용자 동일성 그룹 멤버';
COMMENT ON COLUMN TB_ANONYMOUS_IDENTITY_MEMBER.MEMBER_SEQ IS '멤버 일련번호';
COMMENT ON COLUMN TB_ANONYMOUS_IDENTITY_MEMBER.GROUP_SEQ IS '그룹 일련번호';
COMMENT ON COLUMN TB_ANONYMOUS_IDENTITY_MEMBER.ANONYMOUS_ID IS '익명 사용자 ID';
COMMENT ON COLUMN TB_ANONYMOUS_IDENTITY_MEMBER.ADD_EMP_ID IS '추가 관리자 ID';
COMMENT ON COLUMN TB_ANONYMOUS_IDENTITY_MEMBER.ADD_DATE IS '추가일시';
COMMENT ON COLUMN TB_ANONYMOUS_IDENTITY_MEMBER.ADD_REASON IS '추가 사유';
COMMENT ON COLUMN TB_ANONYMOUS_IDENTITY_MEMBER.USE_YN IS '사용여부';
COMMENT ON COLUMN TB_ANONYMOUS_IDENTITY_MEMBER.DEL_YN IS '삭제 여부';
COMMENT ON COLUMN TB_ANONYMOUS_IDENTITY_MEMBER.DEL_DATE IS '삭제 일시';

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
INSERT INTO TB_ANONYMOUS_USER (NICKNAME) VALUES
('익명사용자1'),
('익명사용자2'),
('익명사용자3');

-- 익명 사용자 동일성 그룹 샘플 데이터
INSERT INTO TB_ANONYMOUS_IDENTITY_GROUP (GROUP_NAME, GROUP_DESC, JUDGE_EMP_ID, CONFIDENCE_LEVEL) VALUES
('동일 사용자 그룹 1', '문체와 상담 패턴이 유사하여 동일 사용자로 판단', 'ADMIN001', 'H'),
('동일 사용자 그룹 2', '상담 시간대와 주제가 유사하여 동일 사용자로 추정', 'ADMIN002', 'M'),
('동일 사용자 그룹 3', 'IP 주소와 접속 패턴이 유사하여 동일 사용자로 판단', 'ADMIN001', 'H');

-- 익명 사용자 동일성 그룹 멤버 샘플 데이터
INSERT INTO TB_ANONYMOUS_IDENTITY_MEMBER (GROUP_SEQ, ANONYMOUS_ID, ADD_EMP_ID, ADD_REASON) VALUES
(1, 1, 'ADMIN001', '문체와 상담 스타일이 동일'),
(1, 2, 'ADMIN001', '동일한 문제 상황과 해결 방식을 제시'),
(2, 2, 'ADMIN002', '상담 시간대가 일치'),
(2, 3, 'ADMIN002', '유사한 업무 관련 상담 내용'),
(3, 1, 'ADMIN001', 'IP 주소와 접속 시간이 동일'),
(3, 3, 'ADMIN001', '동일한 네트워크 환경에서 접속');

-- 샘플 상담 게시글
INSERT INTO TB_CONSULTATION_BOARD (TTL, CNTN, STS_CD, CATEGORY_CD, ANONYMOUS_YN, ANONYMOUS_ID, NICKNAME, PRIORITY_CD, URGENT_YN, REG_EMP_ID) VALUES
('업무 환경 개선 제안', '현재 업무 환경에서 개선이 필요한 부분들을 제안드립니다. 1. 조명 개선 2. 공기질 개선 3. 휴식 공간 확대', 'STS001', 'CAT001', 'N', NULL, NULL, 'PRI003', 'N', 'EMP001'),
('인사 평가 기준 문의', '인사 평가 기준에 대해 궁금한 점이 있어 문의드립니다. 평가 기준이 어떻게 설정되는지, 그리고 평가 결과가 어떻게 반영되는지 알려주시면 감사하겠습니다.', 'STS002', 'CAT002', 'Y', 1, '익명사용자1', 'PRI002', 'N', 'EMP002'),
('복지 혜택 확대 요청', '직원들의 만족도를 높이기 위해 복지 혜택 확대를 요청드립니다. 특히 건강검진 항목 추가와 교육비 지원 확대를 희망합니다.', 'STS001', 'CAT003', 'N', NULL, NULL, 'PRI002', 'Y', 'EMP003');

-- 샘플 답변
INSERT INTO TB_CONSULTATION_ANSWER (BOARD_SEQ, CNTN, STS_CD, REG_EMP_ID) VALUES
(2, '인사 평가 기준에 대해 답변드리겠습니다.\n\n1. 평가 기준: 업무 성과(40%), 업무 능력(30%), 업무 태도(20%), 협력성(10%)\n2. 평가 결과 반영: 승진, 보너스, 교육 기회 등에 반영\n\n추가 문의사항이 있으시면 언제든 연락주세요.', 'STS002', 'ADMIN001');

-- =====================================================
-- DDL 완료
-- ===================================================== 