-- 메뉴 테이블 컬럼 변경 스크립트
-- TB_MNU_LST 테이블에서 상담자전용 컬럼 삭제 및 관리자전용 컬럼을 메뉴권한구분으로 변경

-- 1. 상담자전용 컬럼 삭제
ALTER TABLE TB_MNU_LST DROP COLUMN IF EXISTS MNU_COUNSELOR_YN;

-- 2. 상담자전용 인덱스 삭제 (존재하는 경우)
DROP INDEX IF EXISTS IDX_MNU_LST_COUNSELOR;

-- 3. 관리자전용 컬럼을 메뉴권한구분으로 변경
-- 먼저 임시 컬럼 추가
ALTER TABLE TB_MNU_LST ADD COLUMN MNU_AUTH_TYPE VARCHAR(20) DEFAULT 'USER';

-- 4. 기존 데이터 마이그레이션
UPDATE TB_MNU_LST 
SET MNU_AUTH_TYPE = CASE 
    WHEN MNU_ADMIN_YN = 'Y' THEN 'ADMIN'
    ELSE 'USER'
END
WHERE DEL_YN = 'N';

-- 5. 관리자전용 컬럼 삭제
ALTER TABLE TB_MNU_LST DROP COLUMN MNU_ADMIN_YN;

-- 6. 관리자전용 인덱스 삭제
DROP INDEX IF EXISTS IDX_MNU_LST_ADMIN;

-- 7. 메뉴권한구분 인덱스 생성
CREATE INDEX IDX_MNU_LST_AUTH ON TB_MNU_LST (MNU_AUTH_TYPE);

-- 8. 컬럼 제약조건 추가
ALTER TABLE TB_MNU_LST ALTER COLUMN MNU_AUTH_TYPE SET NOT NULL;

-- 9. 변경 확인
SELECT column_name, data_type, is_nullable, column_default 
FROM information_schema.columns 
WHERE table_name = 'TB_MNU_LST' 
AND table_schema = 'public'
ORDER BY ordinal_position;

-- 10. 현재 메뉴 데이터 확인
SELECT MNU_CD, MNU_NM, MNU_AUTH_TYPE FROM TB_MNU_LST WHERE DEL_YN = 'N' ORDER BY MNU_LVL, MNU_ORD;