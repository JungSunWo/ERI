# 🔐 이메일/전화번호 암호화 기능 가이드

## 개요

ERI 시스템에서 직원의 이메일과 전화번호를 암호화하여 데이터베이스에 저장하는 기능을 구현했습니다.

## 주요 기능

### 1. 암호화 유틸리티 (`EncryptionUtil`)
- **위치**: `src/main/java/com/ERI/demo/util/EncryptionUtil.java`
- **암호화 알고리즘**: AES (Advanced Encryption Standard)
- **키 길이**: 256비트
- **인코딩**: Base64

### 2. 암호화 대상 필드
- **이메일 주소** (`EAD` 컬럼)
- **휴대폰 번호** (`EMP_CPN` 컬럼)

### 3. 자동 암호화 처리
- `empInfo.txt` 파일 배치 처리 시 자동 암호화
- 직원 정보 등록/수정 시 자동 암호화
- 조회 시 자동 복호화

## 구현된 클래스들

### 1. EncryptionUtil
```java
// 주요 메서드
public static String encrypt(String plainText)
public static String decrypt(String encryptedText)
public static String encryptEmail(String email)
public static String decryptEmail(String encryptedEmail)
public static String encryptPhone(String phone)
public static String decryptPhone(String encryptedPhone)
public static boolean isEncrypted(String text)
```

### 2. EmpLstService (수정됨)
- 모든 조회 메서드에서 자동 복호화
- 모든 저장/수정 메서드에서 자동 암호화
- 배치 처리 시 이메일/전화번호 암호화

### 3. EncryptionTestController
- 암호화 기능 테스트 API
- RESTful 엔드포인트 제공

## API 엔드포인트

### 암호화 테스트 API
```
POST /api/encryption-test/test-encryption
POST /api/encryption-test/test-email-encryption
POST /api/encryption-test/test-phone-encryption
POST /api/encryption-test/check-encrypted
GET  /api/encryption-test/employees
GET  /api/encryption-test/employee/{empId}
```

### 배치 처리 API
```
POST /api/scheduler/manual-emp-info-batch
```

## 테스트 페이지

### 접속 방법
```
http://localhost:8080/encryption-test
```

### 테스트 기능
1. **기본 암호화 테스트**: 일반 텍스트 암호화/복호화
2. **이메일 암호화 테스트**: 이메일 주소 암호화/복호화
3. **전화번호 암호화 테스트**: 전화번호 암호화/복호화
4. **암호화 확인**: 텍스트가 암호화되었는지 확인
5. **직원 정보 조회**: DB에서 복호화된 데이터 확인
6. **배치 처리 실행**: empInfo.txt 파일 처리

## 사용 방법

### 1. 배치 처리 실행
```bash
# empInfo.txt 파일을 읽어서 DB에 저장 (암호화 포함)
curl -X POST http://localhost:8080/api/scheduler/manual-emp-info-batch
```

### 2. 암호화 테스트
```bash
# 이메일 암호화 테스트
curl -X POST http://localhost:8080/api/encryption-test/test-email-encryption \
  -H "Content-Type: application/json" \
  -d '{"email": "test@example.com"}'
```

### 3. 직원 정보 조회
```bash
# 전체 직원 조회 (복호화된 데이터)
curl http://localhost:8080/api/encryption-test/employees

# 특정 직원 조회
curl http://localhost:8080/api/encryption-test/employee/S97441
```

## 보안 고려사항

### 1. 암호화 키 관리
- 현재 하드코딩된 키 사용 (`ERI_EMPLOYEE_SECRET_KEY_2024`)
- **운영환경에서는 환경변수로 관리 권장**

### 2. 키 변경 시 주의사항
- 기존 암호화된 데이터는 복호화 불가
- 키 변경 전 전체 데이터 재암호화 필요

### 3. 로그 보안
- 암호화된 데이터가 로그에 출력되지 않도록 주의
- 디버그 모드에서 민감 정보 노출 방지

## 데이터베이스 스키마

### TB_EMP_LST 테이블
```sql
-- 이메일과 전화번호 컬럼이 암호화되어 저장됨
EAD VARCHAR(255)        -- 이메일 주소 (암호화)
EMP_CPN VARCHAR(20)     -- 휴대폰 번호 (암호화)
```

## 성능 고려사항

### 1. 암호화/복호화 오버헤드
- 조회 시 복호화 처리로 인한 약간의 성능 저하
- 대량 데이터 처리 시 배치 단위로 처리 권장

### 2. 메모리 사용량
- 암호화된 데이터는 원본보다 약 33% 크기 증가
- Base64 인코딩으로 인한 크기 증가

## 문제 해결

### 1. 암호화 실패
- 키 초기화 문제 확인
- 입력 데이터 유효성 검사
- 예외 처리 로그 확인

### 2. 복호화 실패
- 암호화되지 않은 데이터인지 확인
- 키 일치 여부 확인
- Base64 디코딩 오류 확인

### 3. 성능 문제
- 대량 데이터 조회 시 페이징 처리
- 캐싱 활용 고려
- 인덱스 최적화

## 향후 개선 사항

1. **키 관리 시스템**: 환경변수 기반 키 관리
2. **키 로테이션**: 주기적 키 변경 기능
3. **암호화 알고리즘**: 더 강력한 알고리즘 적용
4. **성능 최적화**: 배치 암호화/복호화
5. **모니터링**: 암호화 성공률 모니터링

## 관련 파일

- `src/main/java/com/ERI/demo/util/EncryptionUtil.java`
- `src/main/java/com/ERI/demo/service/EmpLstService.java`
- `src/main/java/com/ERI/demo/Controller/EncryptionTestController.java`
- `src/main/resources/templates/encryption-test.html`
- `src/main/resources/templates/empInfo.txt` 