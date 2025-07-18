# ERI 애플리케이션 로깅 설정 가이드

## 개요
Spring Boot 애플리케이션에서 SLF4J를 사용한 로깅 설정 방법을 설명합니다.

## 로깅 레벨
- **ERROR**: 오류 발생 시 (가장 중요)
- **WARN**: 경고 상황
- **INFO**: 일반적인 정보
- **DEBUG**: 디버깅 정보
- **TRACE**: 가장 상세한 정보

## 환경별 로깅 설정

### 1. 개발 환경 (application-dev.properties)
```properties
# 상세한 디버깅 정보 출력
logging.level.root=DEBUG
logging.level.com.ERI.demo=DEBUG
logging.level.org.springframework.web=DEBUG
logging.level.org.mybatis=DEBUG

# SQL 쿼리 로깅
logging.level.org.springframework.jdbc.core.JdbcTemplate=DEBUG

# 로그 파일: logs/eri-dev.log
logging.file.name=logs/eri-dev.log
logging.file.max-size=10MB
logging.file.max-history=7
```

### 2. 스테이징 환경 (application-staging.properties)
```properties
# 중간 수준의 로깅
logging.level.root=INFO
logging.level.com.ERI.demo=DEBUG
logging.level.org.springframework.web=INFO
logging.level.org.mybatis=INFO

# 로그 파일: logs/eri-staging.log
logging.file.name=logs/eri-staging.log
logging.file.max-size=50MB
logging.file.max-history=14
```

### 3. 운영 환경 (application-prod.properties)
```properties
# 최소한의 로깅 (성능 최적화)
logging.level.root=WARN
logging.level.com.ERI.demo=INFO
logging.level.org.springframework.web=WARN
logging.level.org.mybatis=WARN

# 로그 파일: logs/eri-prod.log
logging.file.name=logs/eri-prod.log
logging.file.max-size=100MB
logging.file.max-history=30
```

## 클래스에서 로깅 사용 방법

### 1. Logger 선언
```java
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class YourClass {
    private final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());
    
    // 또는 클래스명으로 직접 지정
    // private final Logger log = LoggerFactory.getLogger(YourClass.class);
}
```

### 2. 로깅 사용 예시
```java
public class AuthController {
    private final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());
    
    public void someMethod() {
        log.debug("디버그 메시지: {}", someVariable);
        log.info("정보 메시지: 사용자 로그인 시도");
        log.warn("경고 메시지: 데이터베이스 연결 지연");
        log.error("에러 메시지: {}", exception.getMessage(), exception);
    }
}
```

## 로그 패턴 설명

### 기본 패턴
```
%d{yyyy-MM-dd HH:mm:ss} [%thread] %-5level %logger{36} - %msg%n
```

### 패턴 요소
- `%d{yyyy-MM-dd HH:mm:ss}`: 날짜/시간
- `[%thread]`: 스레드명
- `%-5level`: 로그 레벨 (5자리 정렬)
- `%logger{36}`: 로거명 (최대 36자)
- `%msg`: 로그 메시지
- `%n`: 줄바꿈

### 개발 환경용 상세 패턴
```
%d{yyyy-MM-dd HH:mm:ss.SSS} [%thread] %-5level %logger{50} - %msg%n
```
- `.SSS`: 밀리초 포함
- `%logger{50}`: 더 긴 로거명 표시

## 로그 파일 관리

### 파일 크기 제한
- 개발: 10MB
- 스테이징: 50MB  
- 운영: 100MB

### 보관 기간
- 개발: 7일
- 스테이징: 14일
- 운영: 30일

### 로그 파일 위치
- 기본 경로: 애플리케이션 실행 디렉토리
- 파일명: `logs/eri-{환경}.log`

## 성능 고려사항

### 운영 환경에서 주의사항
1. **DEBUG 레벨 비활성화**: 성능 저하 방지
2. **SQL 로깅 최소화**: 데이터베이스 성능 영향 최소화
3. **로그 파일 크기 제한**: 디스크 공간 관리
4. **로그 보관 기간 설정**: 오래된 로그 자동 삭제

### 로깅 최적화 팁
```java
// 권장: 조건부 로깅
if (log.isDebugEnabled()) {
    log.debug("복잡한 계산 결과: {}", expensiveCalculation());
}

// 비권장: 항상 실행되는 로깅
log.debug("복잡한 계산 결과: {}", expensiveCalculation());
```

## 문제 해결

### 로그가 출력되지 않는 경우
1. 로깅 레벨 확인
2. 패키지명 확인
3. 로그 파일 경로 확인

### 로그 파일이 생성되지 않는 경우
1. `logs` 디렉토리 생성 권한 확인
2. 애플리케이션 실행 권한 확인
3. 디스크 공간 확인 