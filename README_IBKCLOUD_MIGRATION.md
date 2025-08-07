# IBK클라우드 EOC 시스템 마이그레이션 완료

## 개요
ERI 백엔드 시스템을 IBK클라우드 업무 스타트킷 개발 가이드에 따라 `com.ibkcloud.eoc` 패키지로 마이그레이션을 완료했습니다.

## 마이그레이션된 구조

### 1. 패키지 구조
```
com.ibkcloud.eoc/
├── EocApplication.java                    # 메인 애플리케이션 클래스
├── cmm/                                  # 공통 패키지
│   ├── advice/
│   │   └── GlobalExceptionHandler.java   # 전역 예외 처리
│   ├── dto/
│   │   ├── ErrorResponseDto.java         # 오류 응답 DTO
│   │   ├── IbkCldEocDto.java            # 공통 DTO 부모 클래스
│   │   ├── PageRequest.java              # 페이지 조회요청
│   │   └── Page.java                     # 페이지 조회결과
│   ├── exception/
│   │   ├── BizException.java             # 비즈니스 예외
│   │   ├── UnauthorizedException.java    # 인증 예외
│   │   └── AccessDeniedException.java    # 접근 거부 예외
│   └── util/
│       ├── StringUtil.java               # 문자열 유틸리티
│       ├── NumberUtil.java               # 숫자 유틸리티
│       ├── DateUtil.java                 # 날짜 유틸리티
│       └── EncryptionUtil.java           # 암호화 유틸리티
├── controller/                           # Controller 계층
│   ├── AuthController.java               # 인증 Controller
│   ├── ExampleController.java            # 예제 Controller
│   └── vo/                              # Controller VO
│       ├── ExampleInVo.java             # 예제 입력 VO
│       ├── ExampleOutVo.java            # 예제 출력 VO
│       ├── LoginInVo.java               # 로그인 입력 VO
│       ├── LoginOutVo.java              # 로그인 출력 VO
│       └── CurrentUserOutVo.java        # 현재 사용자 출력 VO
├── service/                             # Service 계층
│   └── EmpLstService.java               # 직원 목록 서비스
├── dao/                                 # DAO 계층
│   ├── EmpLstDao.java                   # 직원 목록 DAO
│   ├── AdminLstDao.java                 # 관리자 목록 DAO
│   ├── CounselorLstDao.java             # 상담사 목록 DAO
│   ├── EmpRefDao.java                   # 직원 참조 DAO
│   └── dto/                            # DAO DTO
│       ├── EmpLstDto.java               # 직원 목록 DTO
│       ├── AdminLstDto.java             # 관리자 목록 DTO
│       ├── CounselorLstDto.java         # 상담사 목록 DTO
│       └── EmpRefDto.java               # 직원 참조 DTO
└── resources/
    ├── mapper/
    │   └── EmpLstMapper.xml             # 직원 목록 Mapper
    ├── message/
    │   └── messages.properties           # 메시지 설정
    └── application-local.yml             # 로컬 환경 설정
```

### 2. IBK클라우드 가이드 준수 사항

#### 2.1 명명 규칙
- **클래스명**: [기능명] + [계층명] 형식 (예: EmpLstService, AuthController)
- **메서드명**: [Action명] + [기능명] 형식 (예: inqEmployeeByEmpId, rgsnLogin)
- **패키지명**: com.ibkcloud.eoc 기본 패키지 사용

#### 2.2 계층 구조
- **Presentation**: Controller + Controller.vo
- **Business Logic**: Service
- **Data Access**: Dao + Dao.dto
- **Domain Model**: Vo/Dto

#### 2.3 공통 요소
- **AOP**: GlobalExceptionHandler
- **Config**: application-local.yml
- **Interceptor**: 향후 구현 예정
- **Util**: StringUtil, NumberUtil, DateUtil, EncryptionUtil

### 3. 주요 기능

#### 3.1 인증 기능
- 로그인/로그아웃 처리
- 현재 사용자 정보 조회
- 권한 검증
- 세션 상태 확인

#### 3.2 직원 관리 기능
- 전체 직원 목록 조회
- 직원 정보 조회 (ERI ID, 직원 ID)
- 직원 정보 등록/수정/삭제
- 지점별 직원 목록 조회
- 이메일/전화번호 암호화/복호화

#### 3.3 페이지 처리
- PageRequest: 페이지 조회요청
- Page: 페이지 조회결과
- 페이지 그룹 처리 지원

### 4. 설정 파일

#### 4.1 application-local.yml
- 데이터베이스 연결 설정
- Redis 연결 설정
- MyBatis 설정
- 로깅 설정
- 서버 설정
- 메시지 설정

#### 4.2 messages.properties
- 공통 오류 메시지
- 로그인 관련 메시지
- 인증 관련 메시지
- 데이터 처리 관련 메시지

### 5. 예외 처리

#### 5.1 예외 클래스
- BizException: 비즈니스 예외
- UnauthorizedException: 인증 예외
- AccessDeniedException: 접근 거부 예외

#### 5.2 전역 예외 처리
- GlobalExceptionHandler에서 모든 예외를 처리
- HTTP 상태 코드에 따른 적절한 응답
- 로그 기록

### 6. 유틸리티

#### 6.1 StringUtil
- 문자열 null/empty 체크
- 문자열 변환 및 치환
- 문자열 비교

#### 6.2 NumberUtil
- 문자열을 숫자로 변환
- 숫자 계산 및 검증
- BigDecimal 연산

#### 6.3 DateUtil
- 날짜/시간 변환
- 날짜 계산
- 날짜 유효성 검증

#### 6.4 EncryptionUtil
- 이메일/전화번호 암호화/복호화
- AES 암호화 알고리즘 사용

### 7. 다음 단계

#### 7.1 추가 마이그레이션 필요 항목
- 기존 ERI Controller들의 완전한 마이그레이션
- 기존 Service 클래스들의 마이그레이션
- 기존 DAO 클래스들의 마이그레이션
- Mapper XML 파일들의 마이그레이션

#### 7.2 향후 구현 예정
- 인터셉터 구현
- Redis 세션 클러스터링
- EAI 연계 기능
- 배치 처리 기능

### 8. 실행 방법

#### 8.1 로컬 환경 실행
```bash
# 환경 변수 설정
export db.ip=localhost
export db.schema=ERI
export db.username=root
export db.password=password
export redis.host=localhost

# 애플리케이션 실행
java -jar -Dspring.profiles.active=local target/eoc-application.jar
```

#### 8.2 API 테스트
- Swagger UI: http://localhost:8080/swagger-ui/index.html
- API 엔드포인트: http://localhost:8080/api/

### 9. 참고 자료
- IBK클라우드 업무 스타트킷 개발 가이드 v1.2
- Spring Boot 3.x 공식 문서
- MyBatis 공식 문서 