# ERI 암호화 데이터베이스 분리 설정 가이드

## 개요

ERI 시스템에서 암호화 관련 데이터를 별도의 데이터베이스(`eri_enc_db`)로 분리하여 보안을 강화하고 데이터 관리의 효율성을 높였습니다.

## 데이터베이스 구조

### 1. 메인 데이터베이스 (eri_db)
- **용도**: 일반 업무 데이터 저장
- **테이블**: 직원 기본 정보, 부서 정보, 프로그램 정보 등
- **접근**: 일반 업무 서비스에서 사용

### 2. 암호화 데이터베이스 (eri_enc_db)
- **용도**: 암호화된 직원 정보 및 이력 저장
- **테이블**: 
  - `TB_EMP_ENCRYPT`: 직원 암호화 정보
  - `TB_ENCRYPT_HISTORY`: 암호화 이력
- **접근**: 암호화 전용 서비스에서만 사용

## 설정 파일 구조

### 1. 데이터베이스 설정 클래스

#### EncryptionDatabaseConfig.java
```java
@Configuration
@MapperScan(
    basePackages = "com.ERI.demo.mappers.encryption",
    sqlSessionFactoryRef = "encryptionSqlSessionFactory",
    sqlSessionTemplateRef = "encryptionSqlSessionTemplate"
)
public class EncryptionDatabaseConfig {
    // 암호화 데이터베이스 전용 설정
}
```

#### MyBatisConfig.java (메인 데이터베이스)
```java
@Configuration
@MapperScan(
    basePackages = "com.ERI.demo.mappers",
    excludeFilters = @ComponentScan.Filter(
        type = FilterType.REGEX,
        pattern = ".*encryption.*"
    ),
    sqlSessionFactoryRef = "mainSqlSessionFactory",
    sqlSessionTemplateRef = "mainSqlSessionTemplate"
)
public class MyBatisConfig {
    // 메인 데이터베이스 전용 설정
}
```

### 2. 애플리케이션 프로퍼티 설정

#### application-dev.properties
```properties
# 메인 데이터베이스 설정
spring.datasource.url=jdbc:postgresql://localhost:5432/eri_db
spring.datasource.username=nicdb
spring.datasource.password=Nicdb2023!

# 암호화 데이터베이스 설정
spring.datasource.encryption.url=jdbc:postgresql://localhost:5432/eri_enc_db
spring.datasource.encryption.username=nicdb
spring.datasource.encryption.password=Nicdb2023!
spring.datasource.encryption.driver-class-name=org.postgresql.Driver

# 암호화 데이터베이스 연결 풀 설정
spring.datasource.encryption.hikari.connection-test-query=SELECT 1
spring.datasource.encryption.hikari.maximum-pool-size=5
spring.datasource.encryption.hikari.minimum-idle=2
```

## 매퍼 구조

### 1. 암호화 전용 매퍼 (eri_enc_db)
- **패키지**: `com.ERI.demo.mappers.encryption`
- **매퍼들**:
  - `EmpEncryptMapper`: 직원 암호화 정보 관리
  - `EncryptHistoryMapper`: 암호화 이력 관리
- **XML 위치**: `src/main/resources/mappers/encryption/`

### 2. 메인 매퍼 (eri_db)
- **패키지**: `com.ERI.demo.mappers`
- **매퍼들**: 일반 업무 관련 매퍼들
- **XML 위치**: `src/main/resources/mappers/`

## 서비스 구조

### 1. 암호화 전용 서비스
```java
@Service
@Transactional(transactionManager = "encryptionTransactionManager")
public class EmpEncryptService {
    @Autowired
    @Qualifier("encryptionSqlSessionTemplate")
    private EmpEncryptMapper empEncryptMapper;
}
```

### 2. 메인 서비스
```java
@Service
@Transactional(transactionManager = "mainTransactionManager")
public class GeneralService {
    @Autowired
    @Qualifier("mainSqlSessionTemplate")
    private GeneralMapper generalMapper;
}
```

## 데이터베이스 생성 스크립트

### PostgreSQL 암호화 데이터베이스 생성
```sql
-- 암호화 데이터베이스 생성
CREATE DATABASE eri_enc_db
    WITH
    OWNER = postgres
    ENCODING = 'UTF8'
    LC_COLLATE = 'Korean_Korea.949'
    LC_CTYPE = 'Korean_Korea.949'
    LOCALE_PROVIDER = 'libc'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1
    IS_TEMPLATE = False;
```

## 보안 고려사항

### 1. 데이터베이스 접근 제어
- 암호화 데이터베이스는 암호화 전용 사용자만 접근 가능
- 일반 업무 사용자는 암호화 데이터베이스에 접근 불가

### 2. 네트워크 보안
- 암호화 데이터베이스는 별도 네트워크 세그먼트에 배치 권장
- 방화벽을 통한 접근 제어

### 3. 백업 정책
- 암호화 데이터베이스는 별도 백업 정책 적용
- 암호화된 백업 파일 사용 권장

## 모니터링 및 로깅

### 1. 데이터베이스 연결 모니터링
```properties
# 암호화 데이터베이스 연결 상태 로깅
logging.level.com.ERI.demo.config.EncryptionDatabaseConfig=DEBUG
```

### 2. 트랜잭션 모니터링
```properties
# 암호화 데이터베이스 트랜잭션 로깅
logging.level.org.springframework.transaction=DEBUG
```

## 운영 시 주의사항

### 1. 데이터베이스 연결 관리
- 암호화 데이터베이스 연결 풀 크기는 일반 데이터베이스보다 작게 설정
- 연결 타임아웃 설정 확인

### 2. 성능 모니터링
- 암호화 데이터베이스 쿼리 성능 모니터링
- 인덱스 최적화 필요 시 별도 적용

### 3. 장애 대응
- 암호화 데이터베이스 장애 시 일반 업무 영향 최소화
- 장애 복구 절차 문서화

## 마이그레이션 가이드

### 1. 기존 데이터 마이그레이션
```sql
-- 기존 암호화 데이터를 새 데이터베이스로 이동
INSERT INTO eri_enc_db.tb_emp_encrypt 
SELECT * FROM eri_db.tb_emp_encrypt;
```

### 2. 애플리케이션 배포
1. 새로운 설정 파일 배포
2. 데이터베이스 연결 테스트
3. 암호화 기능 테스트
4. 전체 시스템 테스트

## 문제 해결

### 1. 연결 오류
- 데이터베이스 서버 상태 확인
- 네트워크 연결 상태 확인
- 사용자 권한 확인

### 2. 트랜잭션 오류
- 트랜잭션 매니저 설정 확인
- 데이터베이스 락 상태 확인

### 3. 성능 문제
- 쿼리 실행 계획 분석
- 인덱스 최적화
- 연결 풀 설정 조정 