-- =====================================================
-- ERI 암호화 관련 DDL
-- 직원번호, 직원명 암호화 통합 테이블
-- =====================================================

-- 암호화 키 관리 테이블
CREATE TABLE TB_ENCRYPT_KEY (
    KEY_ID VARCHAR(50) NOT NULL COMMENT '키 ID',
    KEY_NAME VARCHAR(100) NOT NULL COMMENT '키 이름',
    KEY_VALUE VARCHAR(500) NOT NULL COMMENT '암호화 키 값',
    KEY_ALGORITHM VARCHAR(50) NOT NULL DEFAULT 'AES' COMMENT '키 알고리즘',
    KEY_SIZE INT NOT NULL DEFAULT 256 COMMENT '키 크기 (비트)',
    IS_ACTIVE CHAR(1) NOT NULL DEFAULT 'Y' COMMENT '활성화 여부',
    EXPIRY_DATE DATETIME COMMENT '만료 일시',
    REG_EMP_ID  VARCHAR(255) NULL     COMMENT '등록직원번호',
    UPD_EMP_ID  VARCHAR(255) NULL     COMMENT '수정직원번호',
    REG_DATE DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '등록 일시',
    UPD_DATE DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정 일시',
    PRIMARY KEY (KEY_ID)
) COMMENT '암호화 키 관리 테이블';

-- 통합 직원 암호화 테이블 (직원번호, 직원명 모두 포함)
CREATE TABLE TB_EMP_ENCRYPT (
    EMP_SEQ BIGINT AUTO_INCREMENT NOT NULL COMMENT '직원 일련번호',
    ORIG_EMP_NO VARCHAR(100) NOT NULL COMMENT '원본 직원번호',
    ENCRYPT_EMP_NO VARCHAR(255) NOT NULL COMMENT '암호화된 직원번호',
    ORIG_EMP_NM VARCHAR(100) NOT NULL COMMENT '원본 직원명',
    RANDOM_EMP_NM VARCHAR(100) NOT NULL COMMENT '랜덤 변형 한글명',
    ENCRYPT_ALGORITHM VARCHAR(50) NOT NULL COMMENT '암호화 알고리즘',
    ENCRYPT_KEY_ID VARCHAR(50) COMMENT '암호화 키 ID',
    ENCRYPT_IV VARCHAR(100) COMMENT '암호화 초기화 벡터',
    ENCRYPT_DATE DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '암호화 일시',
    DEL_YN CHAR(1) NOT NULL DEFAULT 'N' COMMENT '삭제 여부 (Y: 삭제, N: 사용)',
    DEL_DATE DATETIME COMMENT '삭제 일시',
    UPD_DATE DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정 일시',
    PRIMARY KEY (EMP_SEQ),
    INDEX IDX_ENCRYPT_KEY_ID (ENCRYPT_KEY_ID),
    INDEX IDX_ENCRYPT_DATE (ENCRYPT_DATE),
    INDEX IDX_RANDOM_EMP_NM (RANDOM_EMP_NM),
    INDEX IDX_DEL_YN (DEL_YN)
) COMMENT '통합 직원 암호화 테이블';

-- 암호화 이력 테이블
CREATE TABLE TB_ENCRYPT_HISTORY (
    HIST_ID BIGINT AUTO_INCREMENT NOT NULL COMMENT '이력 ID',
    EMP_SEQ BIGINT NOT NULL COMMENT '직원 일련번호',
    ORIG_EMP_NO VARCHAR(100) NOT NULL COMMENT '원본 직원번호',
    ENCRYPT_EMP_NO VARCHAR(255) NOT NULL COMMENT '암호화된 직원번호',
    ORIG_EMP_NM VARCHAR(100) NOT NULL COMMENT '원본 직원명',
    RANDOM_EMP_NM VARCHAR(100) NOT NULL COMMENT '랜덤 변형 한글명',
    ENCRYPT_ALGORITHM VARCHAR(50) NOT NULL COMMENT '암호화 알고리즘',
    ENCRYPT_KEY_ID VARCHAR(50) COMMENT '암호화 키 ID',
    ENCRYPT_IV VARCHAR(100) COMMENT '암호화 초기화 벡터',
    OPERATION_TYPE VARCHAR(20) NOT NULL COMMENT '작업 타입 (ENCRYPT: 암호화, DECRYPT: 복호화, DELETE: 삭제, RESTORE: 복구)',
    OPERATION_DATE DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '작업 일시',
    OPERATOR_SEQ BIGINT COMMENT '작업자 일련번호',
    PRIMARY KEY (HIST_ID),
    INDEX IDX_EMP_SEQ (EMP_SEQ),
    INDEX IDX_OPERATION_DATE (OPERATION_DATE)
) COMMENT '암호화 이력 테이블';

-- =====================================================
-- 샘플 데이터 삽입
-- =====================================================

-- 암호화 키 샘플 데이터
INSERT INTO TB_ENCRYPT_KEY (KEY_ID, KEY_NAME, KEY_VALUE, KEY_ALGORITHM, KEY_SIZE, IS_ACTIVE, EXPIRY_DATE) VALUES
('KEY_001', '직원정보 암호화 키 1', 'MySecretKey123456789012345678901234567890', 'AES', 256, 'Y', DATE_ADD(NOW(), INTERVAL 1 YEAR)),
('KEY_002', '직원정보 암호화 키 2', 'AnotherSecretKey987654321098765432109876', 'AES', 256, 'Y', DATE_ADD(NOW(), INTERVAL 1 YEAR));

-- 직원 암호화 샘플 데이터
INSERT INTO TB_EMP_ENCRYPT (EMP_SEQ, ORIG_EMP_NO, ENCRYPT_EMP_NO, ORIG_EMP_NM, RANDOM_EMP_NM, ENCRYPT_ALGORITHM, ENCRYPT_KEY_ID, ENCRYPT_IV) VALUES
(1, '2024001', 'encrypted_emp_no_001', '홍길동', '김철수', 'AES', 'KEY_001', 'iv_001'),
(2, '2024002', 'encrypted_emp_no_002', '김영희', '이민수', 'AES', 'KEY_001', 'iv_002'),
(3, '2024003', 'encrypted_emp_no_003', '박지성', '최영희', 'AES', 'KEY_001', 'iv_003');

-- =====================================================
-- 뷰 생성 (마스킹된 데이터 조회용)
-- =====================================================

-- 직원번호 마스킹 뷰 (삭제되지 않은 데이터만)
CREATE VIEW V_EMP_NO_MASKED AS
SELECT 
    EMP_SEQ,
    CONCAT(LEFT(ORIG_EMP_NO, 2), '****', RIGHT(ORIG_EMP_NO, 2)) AS MASKED_EMP_NO,
    ORIG_EMP_NO AS ORIGINAL_EMP_NO,
    ENCRYPT_EMP_NO
FROM TB_EMP_ENCRYPT
WHERE DEL_YN = 'N';

-- 직원명 마스킹 뷰 (삭제되지 않은 데이터만)
CREATE VIEW V_EMP_NM_MASKED AS
SELECT 
    EMP_SEQ,
    CASE 
        WHEN LENGTH(ORIG_EMP_NM) > 2 THEN 
            CONCAT(LEFT(ORIG_EMP_NM, 1), '*', RIGHT(ORIG_EMP_NM, 1))
        WHEN LENGTH(ORIG_EMP_NM) = 2 THEN 
            CONCAT(LEFT(ORIG_EMP_NM, 1), '*')
        ELSE ORIG_EMP_NM
    END AS MASKED_EMP_NM,
    ORIG_EMP_NM AS ORIGINAL_EMP_NM,
    RANDOM_EMP_NM
FROM TB_EMP_ENCRYPT
WHERE DEL_YN = 'N';

-- =====================================================
-- 저장 프로시저 (암호화 처리)
-- =====================================================

DELIMITER //

-- 직원 정보 암호화 저장 프로시저
CREATE PROCEDURE SP_INSERT_EMP_ENCRYPT(
    IN p_emp_seq BIGINT,
    IN p_orig_emp_no VARCHAR(100),
    IN p_encrypt_emp_no VARCHAR(255),
    IN p_orig_emp_nm VARCHAR(100),
    IN p_random_emp_nm VARCHAR(100),
    IN p_encrypt_algorithm VARCHAR(50),
    IN p_encrypt_key_id VARCHAR(50),
    IN p_encrypt_iv VARCHAR(100),
    IN p_operator_seq BIGINT
)
BEGIN
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
        ROLLBACK;
        RESIGNAL;
    END;
    
    START TRANSACTION;
    
    -- 직원 암호화 테이블에 삽입/업데이트
    INSERT INTO TB_EMP_ENCRYPT (
        EMP_SEQ, ORIG_EMP_NO, ENCRYPT_EMP_NO, ORIG_EMP_NM, RANDOM_EMP_NM,
        ENCRYPT_ALGORITHM, ENCRYPT_KEY_ID, ENCRYPT_IV
    ) VALUES (
        p_emp_seq, p_orig_emp_no, p_encrypt_emp_no, p_orig_emp_nm, p_random_emp_nm,
        p_encrypt_algorithm, p_encrypt_key_id, p_encrypt_iv
    ) ON DUPLICATE KEY UPDATE
        ORIG_EMP_NO = p_orig_emp_no,
        ENCRYPT_EMP_NO = p_encrypt_emp_no,
        ORIG_EMP_NM = p_orig_emp_nm,
        RANDOM_EMP_NM = p_random_emp_nm,
        ENCRYPT_ALGORITHM = p_encrypt_algorithm,
        ENCRYPT_KEY_ID = p_encrypt_key_id,
        ENCRYPT_IV = p_encrypt_iv,
        ENCRYPT_DATE = NOW(),
        UPD_DATE = NOW();
    
    -- 암호화 이력에 기록
    INSERT INTO TB_ENCRYPT_HISTORY (
        EMP_SEQ, ORIG_EMP_NO, ENCRYPT_EMP_NO, ORIG_EMP_NM, RANDOM_EMP_NM,
        ENCRYPT_ALGORITHM, ENCRYPT_KEY_ID, ENCRYPT_IV,
        OPERATION_TYPE, OPERATOR_SEQ
    ) VALUES (
        p_emp_seq, p_orig_emp_no, p_encrypt_emp_no, p_orig_emp_nm, p_random_emp_nm,
        p_encrypt_algorithm, p_encrypt_key_id, p_encrypt_iv,
        'ENCRYPT', p_operator_seq
    );
    
    COMMIT;
END //

-- 직원 정보 논리적 삭제 프로시저
CREATE PROCEDURE SP_DELETE_EMP_ENCRYPT(
    IN p_emp_seq BIGINT,
    IN p_operator_seq BIGINT
)
BEGIN
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
        ROLLBACK;
        RESIGNAL;
    END;
    
    START TRANSACTION;
    
    -- 직원 암호화 테이블 논리적 삭제
    UPDATE TB_EMP_ENCRYPT 
    SET DEL_YN = 'Y', 
        DEL_DATE = NOW(),
        UPD_DATE = NOW()
    WHERE EMP_SEQ = p_emp_seq AND DEL_YN = 'N';
    
    -- 삭제 이력에 기록
    INSERT INTO TB_ENCRYPT_HISTORY (
        EMP_SEQ, ORIG_EMP_NO, ENCRYPT_EMP_NO, ORIG_EMP_NM, RANDOM_EMP_NM,
        ENCRYPT_ALGORITHM, ENCRYPT_KEY_ID, ENCRYPT_IV,
        OPERATION_TYPE, OPERATOR_SEQ
    ) 
    SELECT EMP_SEQ, ORIG_EMP_NO, ENCRYPT_EMP_NO, ORIG_EMP_NM, RANDOM_EMP_NM,
           ENCRYPT_ALGORITHM, ENCRYPT_KEY_ID, ENCRYPT_IV,
           'DELETE', p_operator_seq
    FROM TB_EMP_ENCRYPT 
    WHERE EMP_SEQ = p_emp_seq;
    
    COMMIT;
END //

-- 직원 정보 복구 프로시저
CREATE PROCEDURE SP_RESTORE_EMP_ENCRYPT(
    IN p_emp_seq BIGINT,
    IN p_operator_seq BIGINT
)
BEGIN
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
        ROLLBACK;
        RESIGNAL;
    END;
    
    START TRANSACTION;
    
    -- 직원 암호화 테이블 복구
    UPDATE TB_EMP_ENCRYPT 
    SET DEL_YN = 'N', 
        DEL_DATE = NULL,
        UPD_DATE = NOW()
    WHERE EMP_SEQ = p_emp_seq AND DEL_YN = 'Y';
    
    -- 복구 이력에 기록
    INSERT INTO TB_ENCRYPT_HISTORY (
        EMP_SEQ, ORIG_EMP_NO, ENCRYPT_EMP_NO, ORIG_EMP_NM, RANDOM_EMP_NM,
        ENCRYPT_ALGORITHM, ENCRYPT_KEY_ID, ENCRYPT_IV,
        OPERATION_TYPE, OPERATOR_SEQ
    ) 
    SELECT EMP_SEQ, ORIG_EMP_NO, ENCRYPT_EMP_NO, ORIG_EMP_NM, RANDOM_EMP_NM,
           ENCRYPT_ALGORITHM, ENCRYPT_KEY_ID, ENCRYPT_IV,
           'RESTORE', p_operator_seq
    FROM TB_EMP_ENCRYPT 
    WHERE EMP_SEQ = p_emp_seq;
    
    COMMIT;
END //

DELIMITER ;

-- =====================================================
-- 권한 설정 (보안 강화)
-- =====================================================

-- 암호화 테이블에 대한 읽기 전용 권한 (일반 사용자)
-- GRANT SELECT ON TB_EMP_ENCRYPT TO 'eri_user'@'%';
-- GRANT SELECT ON V_EMP_NM_MASKED TO 'eri_user'@'%';
-- GRANT SELECT ON V_EMP_NO_MASKED TO 'eri_user'@'%';

-- 암호화 테이블에 대한 전체 권한 (관리자)
-- GRANT ALL PRIVILEGES ON TB_EMP_ENCRYPT TO 'eri_admin'@'%';
-- GRANT ALL PRIVILEGES ON TB_ENCRYPT_KEY TO 'eri_admin'@'%';
-- GRANT ALL PRIVILEGES ON TB_ENCRYPT_HISTORY TO 'eri_admin'@'%';
-- GRANT EXECUTE ON PROCEDURE SP_INSERT_EMP_ENCRYPT TO 'eri_admin'@'%';
-- GRANT EXECUTE ON PROCEDURE SP_DELETE_EMP_ENCRYPT TO 'eri_admin'@'%';
-- GRANT EXECUTE ON PROCEDURE SP_RESTORE_EMP_ENCRYPT TO 'eri_admin'@'%'; 