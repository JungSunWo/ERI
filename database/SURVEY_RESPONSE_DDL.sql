-- =================================================================
-- 10. 설문조사 시스템 (Survey System) - 확장성 고려
-- =================================================================

-- 설문조사 마스터 테이블
CREATE TABLE TB_SURVEY_MST (
    SURVEY_SEQ       BIGSERIAL    NOT NULL,
    SURVEY_TTL       VARCHAR(255) NOT NULL,        -- 설문 제목
    SURVEY_DESC      TEXT         NULL,            -- 설문 설명
    SURVEY_TY_CD     VARCHAR(20)  NOT NULL,        -- 설문 유형 (HEALTH_CHECK, SATISFACTION, ETC)
    SURVEY_STS_CD    VARCHAR(20)  NOT NULL DEFAULT 'DRAFT', -- 설문 상태 (DRAFT, ACTIVE, CLOSED, ARCHIVED)
    SURVEY_STT_DT    DATE         NULL,            -- 설문 시작일
    SURVEY_END_DT    DATE         NULL,            -- 설문 종료일
    SURVEY_DUR_MIN   INTEGER      NULL,            -- 예상 소요시간(분)
    ANONYMOUS_YN     CHAR(1)      NOT NULL DEFAULT 'N', -- 익명 응답 여부
    DUPLICATE_YN     CHAR(1)      NOT NULL DEFAULT 'N', -- 중복 응답 허용 여부
    MAX_RESPONSE_CNT INTEGER      NULL,            -- 최대 응답 수 제한
    TARGET_EMP_TY_CD VARCHAR(20)  NULL,            -- 대상 직원 유형 (ALL, DEPT, POSITION, ETC)
    FILE_ATTACH_YN   CHAR(1)      NOT NULL DEFAULT 'N',
 
    DEL_YN           CHAR(1)      NOT NULL DEFAULT 'N',
    DEL_DATE         TIMESTAMP    NULL,
    REG_EMP_ID       VARCHAR(255) NULL,
    UPD_EMP_ID       VARCHAR(255) NULL,
    REG_DATE         TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    UPD_DATE         TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (SURVEY_SEQ)
);
COMMENT ON TABLE TB_SURVEY_MST IS '설문조사 마스터';
COMMENT ON COLUMN TB_SURVEY_MST.SURVEY_TY_CD IS '설문 유형 (HEALTH_CHECK: 건강검진, SATISFACTION: 만족도, ETC: 기타)';
COMMENT ON COLUMN TB_SURVEY_MST.SURVEY_STS_CD IS '설문 상태 (DRAFT: 작성중, ACTIVE: 진행중, CLOSED: 종료, ARCHIVED: 보관)';
COMMENT ON COLUMN TB_SURVEY_MST.ANONYMOUS_YN IS '익명 응답 여부 (Y: 익명, N: 실명)';
COMMENT ON COLUMN TB_SURVEY_MST.DUPLICATE_YN IS '중복 응답 허용 여부 (Y: 허용, N: 불허)';
CREATE INDEX IDX_SURVEY_MST_DEL_YN ON TB_SURVEY_MST (DEL_YN);
CREATE INDEX IDX_SURVEY_MST_STS_CD ON TB_SURVEY_MST (SURVEY_STS_CD);
CREATE INDEX IDX_SURVEY_MST_TY_CD ON TB_SURVEY_MST (SURVEY_TY_CD);

-- 설문 문항 테이블
CREATE TABLE TB_SURVEY_QUESTION (
    SURVEY_SEQ       BIGINT       NOT NULL,
    QUESTION_SEQ     BIGINT       NOT NULL,
    QUESTION_TTL     VARCHAR(500) NOT NULL,        -- 문항 제목
    QUESTION_DESC    TEXT         NULL,            -- 문항 설명
    QUESTION_TY_CD   VARCHAR(20)  NOT NULL,        -- 문항 유형 (SINGLE_CHOICE, MULTIPLE_CHOICE, TEXT, SCALE, ETC)
    QUESTION_ORD     INTEGER      NOT NULL,        -- 문항 순서
    REQUIRED_YN      CHAR(1)      NOT NULL DEFAULT 'Y', -- 필수 응답 여부
    SKIP_LOGIC_YN    CHAR(1)      NOT NULL DEFAULT 'N', -- 건너뛰기 로직 사용 여부
    SKIP_CONDITION   TEXT         NULL,            -- 건너뛰기 조건 (JSON 형태)
    SCORE_YN         CHAR(1)      NOT NULL DEFAULT 'N', -- 점수 계산 여부
    SCORE_WEIGHT     DECIMAL(5,2) NULL DEFAULT 1.0, -- 점수 가중치

    DEL_YN           CHAR(1)      NOT NULL DEFAULT 'N',
    DEL_DATE         TIMESTAMP    NULL,
    REG_EMP_ID       VARCHAR(255) NULL,
    UPD_EMP_ID       VARCHAR(255) NULL,
    REG_DATE         TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    UPD_DATE         TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (SURVEY_SEQ, QUESTION_SEQ)
);
COMMENT ON TABLE TB_SURVEY_QUESTION IS '설문 문항';
COMMENT ON COLUMN TB_SURVEY_QUESTION.QUESTION_TY_CD IS '문항 유형 (SINGLE_CHOICE: 단일선택, MULTIPLE_CHOICE: 다중선택, TEXT: 텍스트, SCALE: 척도, ETC: 기타)';
COMMENT ON COLUMN TB_SURVEY_QUESTION.SKIP_CONDITION IS '건너뛰기 조건 (JSON 형태로 저장)';
CREATE INDEX IDX_SURVEY_QUESTION_DEL_YN ON TB_SURVEY_QUESTION (DEL_YN);
CREATE INDEX IDX_SURVEY_QUESTION_ORD ON TB_SURVEY_QUESTION (SURVEY_SEQ, QUESTION_ORD);

-- 설문 선택지 테이블
CREATE TABLE TB_SURVEY_CHOICE (
    SURVEY_SEQ       BIGINT       NOT NULL,
    QUESTION_SEQ     BIGINT       NOT NULL,
    CHOICE_SEQ       BIGINT       NOT NULL,
    CHOICE_TTL       VARCHAR(500) NOT NULL,        -- 선택지 제목
    CHOICE_DESC      TEXT         NULL,            -- 선택지 설명
    CHOICE_ORD       INTEGER      NOT NULL,        -- 선택지 순서
    CHOICE_SCORE     INTEGER      NULL,            -- 선택지 점수
    CHOICE_VALUE     VARCHAR(100) NULL,            -- 선택지 값
    ETC_YN           CHAR(1)      NOT NULL DEFAULT 'N', -- 기타 선택지 여부

    DEL_YN           CHAR(1)      NOT NULL DEFAULT 'N',
    DEL_DATE         TIMESTAMP    NULL,
    REG_EMP_ID       VARCHAR(255) NULL,
    UPD_EMP_ID       VARCHAR(255) NULL,
    REG_DATE         TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    UPD_DATE         TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (SURVEY_SEQ, QUESTION_SEQ, CHOICE_SEQ)
);
COMMENT ON TABLE TB_SURVEY_CHOICE IS '설문 선택지';
COMMENT ON COLUMN TB_SURVEY_CHOICE.CHOICE_SCORE IS '선택지 점수 (점수 계산 시 사용)';
COMMENT ON COLUMN TB_SURVEY_CHOICE.CHOICE_VALUE IS '선택지 값 (시스템 처리용)';
COMMENT ON COLUMN TB_SURVEY_CHOICE.ETC_YN IS '기타 선택지 여부 (Y: 기타, N: 일반)';
CREATE INDEX IDX_SURVEY_CHOICE_DEL_YN ON TB_SURVEY_CHOICE (DEL_YN);
CREATE INDEX IDX_SURVEY_CHOICE_ORD ON TB_SURVEY_CHOICE (SURVEY_SEQ, QUESTION_SEQ, CHOICE_ORD);

-- 설문 응답 테이블은 SURVEY_RESPONSE_DDL.sql에서 관리
-- TB_SURVEY_RESPONSE, TB_SURVEY_RESPONSE_DETAIL 테이블 정의 제거

-- 설문 대상자 테이블
CREATE TABLE TB_SURVEY_TARGET (
    SURVEY_SEQ       BIGINT       NOT NULL,
    TARGET_SEQ       BIGSERIAL    NOT NULL,
    TARGET_TY_CD     VARCHAR(20)  NOT NULL,        -- 대상 유형 (EMP_ID, DEPT_CD, POSITION_CD, ALL)
    TARGET_VALUE     VARCHAR(255) NULL,            -- 대상 값
    TARGET_DESC      VARCHAR(500) NULL,            -- 대상 설명

    DEL_YN           CHAR(1)      NOT NULL DEFAULT 'N',
    DEL_DATE         TIMESTAMP    NULL,
    REG_EMP_ID       VARCHAR(255) NULL,
    UPD_EMP_ID       VARCHAR(255) NULL,
    REG_DATE         TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    UPD_DATE         TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (SURVEY_SEQ, TARGET_SEQ)
    
);
COMMENT ON TABLE TB_SURVEY_TARGET IS '설문 대상자';
COMMENT ON COLUMN TB_SURVEY_TARGET.TARGET_TY_CD IS '대상 유형 (EMP_ID: 개별직원, DEPT_CD: 부서, POSITION_CD: 직급, ALL: 전체)';
CREATE INDEX IDX_SURVEY_TARGET_DEL_YN ON TB_SURVEY_TARGET (DEL_YN);

-- 설문 결과 통계 테이블 (성능 향상을 위한 집계 테이블)
CREATE TABLE TB_SURVEY_STATS (
    SURVEY_SEQ       BIGINT       NOT NULL,
    QUESTION_SEQ     BIGINT       NOT NULL,
    CHOICE_SEQ       BIGINT       NULL,
    RESPONSE_CNT     INTEGER      NOT NULL DEFAULT 0, -- 응답 수
    RESPONSE_RATE    DECIMAL(5,2) NULL,            -- 응답률
    AVG_SCORE        DECIMAL(5,2) NULL,            -- 평균 점수
    MIN_SCORE        DECIMAL(5,2) NULL,            -- 최소 점수
    MAX_SCORE        DECIMAL(5,2) NULL,            -- 최대 점수
    STD_DEV_SCORE    DECIMAL(5,2) NULL,            -- 표준편차
    LAST_UPD_DATE    TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (SURVEY_SEQ, QUESTION_SEQ, CHOICE_SEQ)
);
COMMENT ON TABLE TB_SURVEY_STATS IS '설문 결과 통계 (집계 테이블)';
CREATE INDEX IDX_SURVEY_STATS_SURVEY ON TB_SURVEY_STATS (SURVEY_SEQ);

-- =================================================================
-- 11. 설문조사 샘플 데이터 (18개 문항 5지선다 건강검진 설문)
-- =================================================================

-- 건강검진 설문 마스터
INSERT INTO TB_SURVEY_MST (SURVEY_TTL, SURVEY_DESC, SURVEY_TY_CD, SURVEY_STS_CD, SURVEY_STT_DT, SURVEY_END_DT, SURVEY_DUR_MIN, ANONYMOUS_YN, DUPLICATE_YN, TARGET_EMP_TY_CD, REG_EMP_ID) VALUES
('IBK 마음건강검진 설문조사', '직원들의 마음건강 상태를 파악하기 위한 설문조사입니다. 정확한 결과를 위해 솔직하게 답변해 주시기 바랍니다.', 'HEALTH_CHECK', 'ACTIVE', CURRENT_DATE, CURRENT_DATE + INTERVAL '30 days', 15, 'N', 'N', 'ALL', 'ADMIN001');

-- 18개 문항 추가 (5지선다)
INSERT INTO TB_SURVEY_QUESTION (SURVEY_SEQ, QUESTION_SEQ, QUESTION_TTL, QUESTION_TY_CD, QUESTION_ORD, REQUIRED_YN, SCORE_YN, SCORE_WEIGHT, REG_EMP_ID) VALUES
(1, 1, '최근 한 달간 기분이 우울하거나 절망감을 느낀 적이 있습니까?', 'SINGLE_CHOICE', 1, 'Y', 'Y', 1.0, 'ADMIN001'),
(1, 2, '최근 한 달간 평소보다 쉽게 피로를 느끼거나 에너지가 부족하다고 느낀 적이 있습니까?', 'SINGLE_CHOICE', 2, 'Y', 'Y', 1.0, 'ADMIN001'),
(1, 3, '최근 한 달간 평소보다 식욕이 떨어지거나 과식하는 경험이 있습니까?', 'SINGLE_CHOICE', 3, 'Y', 'Y', 1.0, 'ADMIN001'),
(1, 4, '최근 한 달간 잠들기 어렵거나 잠을 자지 못하는 경험이 있습니까?', 'SINGLE_CHOICE', 4, 'Y', 'Y', 1.0, 'ADMIN001'),
(1, 5, '최근 한 달간 평소보다 말하기나 행동이 느려졌다고 느낀 적이 있습니까?', 'SINGLE_CHOICE', 5, 'Y', 'Y', 1.0, 'ADMIN001'),
(1, 6, '최근 한 달간 자신을 비난하거나 실패자라고 느낀 적이 있습니까?', 'SINGLE_CHOICE', 6, 'Y', 'Y', 1.0, 'ADMIN001'),
(1, 7, '최근 한 달간 신문을 읽거나 TV를 보는 데 집중하기 어려운 경험이 있습니까?', 'SINGLE_CHOICE', 7, 'Y', 'Y', 1.0, 'ADMIN001'),
(1, 8, '최근 한 달간 죽고 싶다는 생각이나 자해할 생각이 든 적이 있습니까?', 'SINGLE_CHOICE', 8, 'Y', 'Y', 1.5, 'ADMIN001'),
(1, 9, '최근 한 달간 업무나 일상생활에서의 스트레스가 심하다고 느끼십니까?', 'SINGLE_CHOICE', 9, 'Y', 'Y', 1.0, 'ADMIN001'),
(1, 10, '최근 한 달간 가족이나 친구들과의 관계에서 어려움을 겪고 있습니까?', 'SINGLE_CHOICE', 10, 'Y', 'Y', 1.0, 'ADMIN001'),
(1, 11, '최근 한 달간 불안하거나 걱정이 많다고 느끼십니까?', 'SINGLE_CHOICE', 11, 'Y', 'Y', 1.0, 'ADMIN001'),
(1, 12, '최근 한 달간 평소보다 쉽게 화를 내거나 짜증을 내는 경험이 있습니까?', 'SINGLE_CHOICE', 12, 'Y', 'Y', 1.0, 'ADMIN001'),
(1, 13, '최근 한 달간 신체적 증상(두통, 소화불량, 근육통 등)이 심하다고 느끼십니까?', 'SINGLE_CHOICE', 13, 'Y', 'Y', 1.0, 'ADMIN001'),
(1, 14, '최근 한 달간 알코올이나 담배 사용량이 늘어났습니까?', 'SINGLE_CHOICE', 14, 'Y', 'Y', 1.2, 'ADMIN001'),
(1, 15, '최근 한 달간 취미나 관심사에 대한 흥미가 떨어졌습니까?', 'SINGLE_CHOICE', 15, 'Y', 'Y', 1.0, 'ADMIN001'),
(1, 16, '최근 한 달간 미래에 대한 희망이나 계획을 세우기 어려웠습니까?', 'SINGLE_CHOICE', 16, 'Y', 'Y', 1.0, 'ADMIN001'),
(1, 17, '최근 한 달간 자신의 건강 상태에 대해 걱정이 많습니까?', 'SINGLE_CHOICE', 17, 'Y', 'Y', 1.0, 'ADMIN001'),
(1, 18, '전반적으로 현재 자신의 마음건강 상태를 어떻게 평가하시겠습니까?', 'SINGLE_CHOICE', 18, 'Y', 'Y', 1.5, 'ADMIN001');

-- 5지선다 선택지 추가 (전혀 그렇지 않다=1점, 그렇지 않다=2점, 보통이다=3점, 그렇다=4점, 매우 그렇다=5점)
INSERT INTO TB_SURVEY_CHOICE (SURVEY_SEQ, QUESTION_SEQ, CHOICE_SEQ, CHOICE_TTL, CHOICE_ORD, CHOICE_SCORE, CHOICE_VALUE, REG_EMP_ID) VALUES
-- 문항 1
(1, 1, 1, '전혀 그렇지 않다', 1, 1, '1', 'ADMIN001'),
(1, 1, 2, '그렇지 않다', 2, 2, '2', 'ADMIN001'),
(1, 1, 3, '보통이다', 3, 3, '3', 'ADMIN001'),
(1, 1, 4, '그렇다', 4, 4, '4', 'ADMIN001'),
(1, 1, 5, '매우 그렇다', 5, 5, '5', 'ADMIN001'),

-- 문항 2
(1, 2, 1, '전혀 그렇지 않다', 1, 1, '1', 'ADMIN001'),
(1, 2, 2, '그렇지 않다', 2, 2, '2', 'ADMIN001'),
(1, 2, 3, '보통이다', 3, 3, '3', 'ADMIN001'),
(1, 2, 4, '그렇다', 4, 4, '4', 'ADMIN001'),
(1, 2, 5, '매우 그렇다', 5, 5, '5', 'ADMIN001'),

-- 문항 3
(1, 3, 1, '전혀 그렇지 않다', 1, 1, '1', 'ADMIN001'),
(1, 3, 2, '그렇지 않다', 2, 2, '2', 'ADMIN001'),
(1, 3, 3, '보통이다', 3, 3, '3', 'ADMIN001'),
(1, 3, 4, '그렇다', 4, 4, '4', 'ADMIN001'),
(1, 3, 5, '매우 그렇다', 5, 5, '5', 'ADMIN001'),

-- 문항 4
(1, 4, 1, '전혀 그렇지 않다', 1, 1, '1', 'ADMIN001'),
(1, 4, 2, '그렇지 않다', 2, 2, '2', 'ADMIN001'),
(1, 4, 3, '보통이다', 3, 3, '3', 'ADMIN001'),
(1, 4, 4, '그렇다', 4, 4, '4', 'ADMIN001'),
(1, 4, 5, '매우 그렇다', 5, 5, '5', 'ADMIN001'),

-- 문항 5
(1, 5, 1, '전혀 그렇지 않다', 1, 1, '1', 'ADMIN001'),
(1, 5, 2, '그렇지 않다', 2, 2, '2', 'ADMIN001'),
(1, 5, 3, '보통이다', 3, 3, '3', 'ADMIN001'),
(1, 5, 4, '그렇다', 4, 4, '4', 'ADMIN001'),
(1, 5, 5, '매우 그렇다', 5, 5, '5', 'ADMIN001'),

-- 문항 6
(1, 6, 1, '전혀 그렇지 않다', 1, 1, '1', 'ADMIN001'),
(1, 6, 2, '그렇지 않다', 2, 2, '2', 'ADMIN001'),
(1, 6, 3, '보통이다', 3, 3, '3', 'ADMIN001'),
(1, 6, 4, '그렇다', 4, 4, '4', 'ADMIN001'),
(1, 6, 5, '매우 그렇다', 5, 5, '5', 'ADMIN001'),

-- 문항 7
(1, 7, 1, '전혀 그렇지 않다', 1, 1, '1', 'ADMIN001'),
(1, 7, 2, '그렇지 않다', 2, 2, '2', 'ADMIN001'),
(1, 7, 3, '보통이다', 3, 3, '3', 'ADMIN001'),
(1, 7, 4, '그렇다', 4, 4, '4', 'ADMIN001'),
(1, 7, 5, '매우 그렇다', 5, 5, '5', 'ADMIN001'),

-- 문항 8
(1, 8, 1, '전혀 그렇지 않다', 1, 1, '1', 'ADMIN001'),
(1, 8, 2, '그렇지 않다', 2, 2, '2', 'ADMIN001'),
(1, 8, 3, '보통이다', 3, 3, '3', 'ADMIN001'),
(1, 8, 4, '그렇다', 4, 4, '4', 'ADMIN001'),
(1, 8, 5, '매우 그렇다', 5, 5, '5', 'ADMIN001'),

-- 문항 9
(1, 9, 1, '전혀 그렇지 않다', 1, 1, '1', 'ADMIN001'),
(1, 9, 2, '그렇지 않다', 2, 2, '2', 'ADMIN001'),
(1, 9, 3, '보통이다', 3, 3, '3', 'ADMIN001'),
(1, 9, 4, '그렇다', 4, 4, '4', 'ADMIN001'),
(1, 9, 5, '매우 그렇다', 5, 5, '5', 'ADMIN001'),

-- 문항 10
(1, 10, 1, '전혀 그렇지 않다', 1, 1, '1', 'ADMIN001'),
(1, 10, 2, '그렇지 않다', 2, 2, '2', 'ADMIN001'),
(1, 10, 3, '보통이다', 3, 3, '3', 'ADMIN001'),
(1, 10, 4, '그렇다', 4, 4, '4', 'ADMIN001'),
(1, 10, 5, '매우 그렇다', 5, 5, '5', 'ADMIN001'),

-- 문항 11
(1, 11, 1, '전혀 그렇지 않다', 1, 1, '1', 'ADMIN001'),
(1, 11, 2, '그렇지 않다', 2, 2, '2', 'ADMIN001'),
(1, 11, 3, '보통이다', 3, 3, '3', 'ADMIN001'),
(1, 11, 4, '그렇다', 4, 4, '4', 'ADMIN001'),
(1, 11, 5, '매우 그렇다', 5, 5, '5', 'ADMIN001'),

-- 문항 12
(1, 12, 1, '전혀 그렇지 않다', 1, 1, '1', 'ADMIN001'),
(1, 12, 2, '그렇지 않다', 2, 2, '2', 'ADMIN001'),
(1, 12, 3, '보통이다', 3, 3, '3', 'ADMIN001'),
(1, 12, 4, '그렇다', 4, 4, '4', 'ADMIN001'),
(1, 12, 5, '매우 그렇다', 5, 5, '5', 'ADMIN001'),

-- 문항 13
(1, 13, 1, '전혀 그렇지 않다', 1, 1, '1', 'ADMIN001'),
(1, 13, 2, '그렇지 않다', 2, 2, '2', 'ADMIN001'),
(1, 13, 3, '보통이다', 3, 3, '3', 'ADMIN001'),
(1, 13, 4, '그렇다', 4, 4, '4', 'ADMIN001'),
(1, 13, 5, '매우 그렇다', 5, 5, '5', 'ADMIN001'),

-- 문항 14
(1, 14, 1, '전혀 그렇지 않다', 1, 1, '1', 'ADMIN001'),
(1, 14, 2, '그렇지 않다', 2, 2, '2', 'ADMIN001'),
(1, 14, 3, '보통이다', 3, 3, '3', 'ADMIN001'),
(1, 14, 4, '그렇다', 4, 4, '4', 'ADMIN001'),
(1, 14, 5, '매우 그렇다', 5, 5, '5', 'ADMIN001'),

-- 문항 15
(1, 15, 1, '전혀 그렇지 않다', 1, 1, '1', 'ADMIN001'),
(1, 15, 2, '그렇지 않다', 2, 2, '2', 'ADMIN001'),
(1, 15, 3, '보통이다', 3, 3, '3', 'ADMIN001'),
(1, 15, 4, '그렇다', 4, 4, '4', 'ADMIN001'),
(1, 15, 5, '매우 그렇다', 5, 5, '5', 'ADMIN001'),

-- 문항 16
(1, 16, 1, '전혀 그렇지 않다', 1, 1, '1', 'ADMIN001'),
(1, 16, 2, '그렇지 않다', 2, 2, '2', 'ADMIN001'),
(1, 16, 3, '보통이다', 3, 3, '3', 'ADMIN001'),
(1, 16, 4, '그렇다', 4, 4, '4', 'ADMIN001'),
(1, 16, 5, '매우 그렇다', 5, 5, '5', 'ADMIN001'),

-- 문항 17
(1, 17, 1, '전혀 그렇지 않다', 1, 1, '1', 'ADMIN001'),
(1, 17, 2, '그렇지 않다', 2, 2, '2', 'ADMIN001'),
(1, 17, 3, '보통이다', 3, 3, '3', 'ADMIN001'),
(1, 17, 4, '그렇다', 4, 4, '4', 'ADMIN001'),
(1, 17, 5, '매우 그렇다', 5, 5, '5', 'ADMIN001'),

-- 문항 18
(1, 18, 1, '매우 나쁨', 1, 1, '1', 'ADMIN001'),
(1, 18, 2, '나쁨', 2, 2, '2', 'ADMIN001'),
(1, 18, 3, '보통', 3, 3, '3', 'ADMIN001'),
(1, 18, 4, '좋음', 4, 4, '4', 'ADMIN001'),
(1, 18, 5, '매우 좋음', 5, 5, '5', 'ADMIN001');

-- 설문 대상자 (전체 직원)
INSERT INTO TB_SURVEY_TARGET (SURVEY_SEQ, TARGET_TY_CD, TARGET_VALUE, TARGET_DESC, REG_EMP_ID) VALUES
(1, 'ALL', 'ALL', '전체 직원', 'ADMIN001');


-- 설문조사 응답 테이블
CREATE TABLE TB_SURVEY_RESPONSE (
    RESPONSE_SEQ BIGSERIAL PRIMARY KEY,
    SURVEY_SEQ BIGINT NOT NULL,
    EMP_NO VARCHAR(20),
    EMP_NM VARCHAR(100),
    DEPT_NM VARCHAR(100),
    RESPONSE_STT_DT TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    RESPONSE_END_DT TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    RESPONSE_DUR_MIN INTEGER DEFAULT 0,
    RESPONSE_STS_CD VARCHAR(20) DEFAULT 'IN_PROGRESS',
    ANONYMOUS_YN CHAR(1) DEFAULT 'N',
    IP_ADDR VARCHAR(45),
    USER_AGENT TEXT,
    REG_ID VARCHAR(20) NOT NULL,
    REG_DT TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UPD_ID VARCHAR(20),
    UPD_DT TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    DEL_YN CHAR(1) DEFAULT 'N'
);

-- 설문조사 응답 상세 테이블
CREATE TABLE TB_SURVEY_RESPONSE_DTL (
    RESPONSE_DETAIL_SEQ BIGSERIAL PRIMARY KEY,
    RESPONSE_SEQ BIGINT NOT NULL,
    SURVEY_SEQ BIGINT NOT NULL,
    QUESTION_SEQ BIGINT NOT NULL,
    CHOICE_SEQ BIGINT,
    TEXT_RESPONSE TEXT,
    RESPONSE_ORD INTEGER DEFAULT 1,
    RESPONSE_SCORE INTEGER DEFAULT 0,
    REG_ID VARCHAR(20) NOT NULL,
    REG_DT TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UPD_ID VARCHAR(20),
    UPD_DT TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    DEL_YN CHAR(1) DEFAULT 'N'
);

-- 인덱스 생성
CREATE INDEX IDX_SURVEY_RESPONSE_SURVEY_SEQ ON TB_SURVEY_RESPONSE(SURVEY_SEQ);
CREATE INDEX IDX_SURVEY_RESPONSE_EMP_NO ON TB_SURVEY_RESPONSE(EMP_NO);
CREATE INDEX IDX_SURVEY_RESPONSE_REG_DT ON TB_SURVEY_RESPONSE(REG_DT);
CREATE INDEX IDX_SURVEY_RESPONSE_DEL_YN ON TB_SURVEY_RESPONSE(DEL_YN);

CREATE INDEX IDX_SURVEY_RESPONSE_DTL_RESPONSE_SEQ ON TB_SURVEY_RESPONSE_DTL(RESPONSE_SEQ);
CREATE INDEX IDX_SURVEY_RESPONSE_DTL_SURVEY_SEQ ON TB_SURVEY_RESPONSE_DTL(SURVEY_SEQ);
CREATE INDEX IDX_SURVEY_RESPONSE_DTL_QUESTION_SEQ ON TB_SURVEY_RESPONSE_DTL(QUESTION_SEQ);
CREATE INDEX IDX_SURVEY_RESPONSE_DTL_DEL_YN ON TB_SURVEY_RESPONSE_DTL(DEL_YN);

-- 코멘트 추가
COMMENT ON TABLE TB_SURVEY_RESPONSE IS '설문조사 응답 테이블';
COMMENT ON COLUMN TB_SURVEY_RESPONSE.RESPONSE_SEQ IS '응답 시퀀스';
COMMENT ON COLUMN TB_SURVEY_RESPONSE.SURVEY_SEQ IS '설문조사 시퀀스';
COMMENT ON COLUMN TB_SURVEY_RESPONSE.EMP_NO IS '직원 번호';
COMMENT ON COLUMN TB_SURVEY_RESPONSE.EMP_NM IS '직원명';
COMMENT ON COLUMN TB_SURVEY_RESPONSE.DEPT_NM IS '부서명';
COMMENT ON COLUMN TB_SURVEY_RESPONSE.RESPONSE_STT_DT IS '응답 시작 시간';
COMMENT ON COLUMN TB_SURVEY_RESPONSE.RESPONSE_END_DT IS '응답 완료 시간';
COMMENT ON COLUMN TB_SURVEY_RESPONSE.RESPONSE_DUR_MIN IS '소요 시간 (분)';
COMMENT ON COLUMN TB_SURVEY_RESPONSE.RESPONSE_STS_CD IS '응답 상태 코드 (IN_PROGRESS, COMPLETED, ABANDONED)';
COMMENT ON COLUMN TB_SURVEY_RESPONSE.ANONYMOUS_YN IS '익명 여부 (Y/N)';
COMMENT ON COLUMN TB_SURVEY_RESPONSE.IP_ADDR IS 'IP 주소';
COMMENT ON COLUMN TB_SURVEY_RESPONSE.USER_AGENT IS '사용자 에이전트';
COMMENT ON COLUMN TB_SURVEY_RESPONSE.REG_ID IS '등록자 ID';
COMMENT ON COLUMN TB_SURVEY_RESPONSE.REG_DT IS '등록일시';
COMMENT ON COLUMN TB_SURVEY_RESPONSE.UPD_ID IS '수정자 ID';
COMMENT ON COLUMN TB_SURVEY_RESPONSE.UPD_DT IS '수정일시';
COMMENT ON COLUMN TB_SURVEY_RESPONSE.DEL_YN IS '삭제 여부 (Y/N)';

COMMENT ON TABLE TB_SURVEY_RESPONSE_DTL IS '설문조사 응답 상세 테이블';
COMMENT ON COLUMN TB_SURVEY_RESPONSE_DTL.RESPONSE_DETAIL_SEQ IS '응답 상세 시퀀스';
COMMENT ON COLUMN TB_SURVEY_RESPONSE_DTL.RESPONSE_SEQ IS '응답 시퀀스';
COMMENT ON COLUMN TB_SURVEY_RESPONSE_DTL.SURVEY_SEQ IS '설문조사 시퀀스';
COMMENT ON COLUMN TB_SURVEY_RESPONSE_DTL.QUESTION_SEQ IS '질문 시퀀스';
COMMENT ON COLUMN TB_SURVEY_RESPONSE_DTL.CHOICE_SEQ IS '선택지 시퀀스 (단일/다중 선택인 경우)';
COMMENT ON COLUMN TB_SURVEY_RESPONSE_DTL.TEXT_RESPONSE IS '텍스트 응답 (주관식인 경우)';
COMMENT ON COLUMN TB_SURVEY_RESPONSE_DTL.RESPONSE_ORD IS '응답 순서 (다중 선택인 경우)';
COMMENT ON COLUMN TB_SURVEY_RESPONSE_DTL.RESPONSE_SCORE IS '응답 점수';
COMMENT ON COLUMN TB_SURVEY_RESPONSE_DTL.REG_ID IS '등록자 ID';
COMMENT ON COLUMN TB_SURVEY_RESPONSE_DTL.REG_DT IS '등록일시';
COMMENT ON COLUMN TB_SURVEY_RESPONSE_DTL.UPD_ID IS '수정자 ID';
COMMENT ON COLUMN TB_SURVEY_RESPONSE_DTL.UPD_DT IS '수정일시';
COMMENT ON COLUMN TB_SURVEY_RESPONSE_DTL.DEL_YN IS '삭제 여부 (Y/N)'; 