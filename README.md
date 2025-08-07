# ERI Backend Migration to IBK Cloud EOC

## 개요
기존 ERI 백엔드 시스템을 IBK Cloud 업무 스타트킷 개발 가이드에 따라 `com.ibkcloud.eoc` 패키지로 마이그레이션한 프로젝트입니다.

## 패키지 구조
```
com.ibkcloud.eoc/
├── cmm/                    # 공통 모듈
│   ├── advice/            # AOP 관련 공통 클래스
│   ├── dto/               # 공통 DTO
│   ├── exception/         # 예외 처리
│   └── util/              # 유틸리티 클래스
├── controller/            # 컨트롤러 계층
│   └── vo/               # 컨트롤러 VO
├── service/              # 서비스 계층
├── dao/                  # 데이터 액세스 계층
│   └── dto/              # DAO DTO
└── EocApplication.java   # 메인 애플리케이션 클래스
```

## 완료된 작업

### 1. 기본 구조 및 공통 모듈
- ✅ 패키지 구조 변경 (`com.ERI.demo` → `com.ibkcloud.eoc`)
- ✅ 메인 애플리케이션 클래스 마이그레이션 (`EriApplication` → `EocApplication`)
- ✅ 공통 예외 처리 클래스 생성
  - `BizException`
  - `UnauthorizedException`
  - `AccessDeniedException`
  - `GlobalExceptionHandler`
- ✅ 공통 DTO 클래스 생성
  - `ErrorResponseDto`
  - `IbkCldEocDto`
  - `PageRequest`
  - `Page`
- ✅ 공통 유틸리티 클래스 생성
  - `StringUtil`
  - `NumberUtil`
  - `DateUtil`
  - `EncryptionUtil`

### 2. 컨트롤러 마이그레이션
- ✅ `AuthController` - 인증 관련 API
- ✅ `EmpLstController` - 직원 정보 관리 API
- ✅ `AdminController` - 관리자 정보 관리 API
- ✅ `NoticeController` - 공지사항 관리 API
- ✅ `CounselorController` - 상담사 정보 관리 API
- ✅ `SurveyController` - 설문조사 관리 API
- ✅ `ImageBoardController` - 이미지 게시판 API
- ✅ `ImageSelectionController` - 이미지 선택 API
- ✅ `ImageFileController` - 이미지 파일 API
- ✅ `BoardFileAttachController` - 게시판 파일 첨부 API
- ✅ `ExpertConsultationController` - 전문상담 API
- ✅ `EncryptionTestController` - 암호화 테스트 API
- ✅ `SchedulerController` - 스케줄러 관리 API
- ✅ `PageController` - 페이지 라우팅 API
- ✅ `SystemLogController` - 시스템 로그 관리 API
- ✅ `MnuLstController` - 메뉴 관리 API
- ✅ `MessengerController` - 메신저 알림 API
- ✅ `ImgBrdController` - 이미지 게시판 API
- ✅ `ConsultationBoardController` - 상담 게시판 API
- ✅ `EmpRightsBoardController` - 직원권익 게시판 API
- ✅ `EmpRightsCommentController` - 직원권익 댓글 API
- ✅ `CmnCodeController` - 공통코드 API
- ✅ `AuthLstController` - 권한 관리 API
- ✅ `CommonCodeController` - 공통코드 관리 API
- ✅ `ExpertConsultationController` - 전문가 상담 API
- ✅ `FileAttachController` - 파일 첨부 관리 API
- ✅ `ImageFileController` - 이미지 파일 관리 API
- ✅ `BoardFileAttachController` - 게시판 파일 첨부 관리 API
- ✅ `SystemLogController` - 시스템 로그 관리 API

### 3. 컨트롤러 VO 클래스
- ✅ `LoginInVo`, `LoginOutVo`, `CurrentUserOutVo` - 인증 관련 VO
- ✅ `EmpLstInVo`, `EmpLstOutVo`, `EmpLstSearchInVo`, `EmpLstSearchOutVo` - 직원 정보 VO
- ✅ `AdminLstInVo`, `AdminLstOutVo`, `AdminLstSearchInVo`, `AdminLstSearchOutVo` - 관리자 정보 VO
- ✅ `NtiLstInVo`, `NtiLstOutVo`, `NtiLstSearchInVo`, `NtiLstSearchOutVo` - 공지사항 VO
- ✅ `CounselorLstInVo`, `CounselorLstOutVo`, `CounselorLstSearchInVo`, `CounselorLstSearchOutVo` - 상담사 정보 VO
- ✅ `SurveyMstInVo`, `SurveyMstOutVo` - 설문조사 마스터 VO
- ✅ `SurveyResponseInVo`, `SurveyResponseOutVo` - 설문조사 응답 VO
- ✅ `SurveyResponseDetailInVo`, `SurveyResponseDetailOutVo` - 설문조사 응답 상세 VO
- ✅ `SurveyStatisticsOutVo` - 설문조사 통계 VO
- ✅ `ImageBoardInVo`, `ImageBoardOutVo` - 이미지 게시판 VO
- ✅ `ImageBoardSearchInVo`, `ImageBoardSearchOutVo` - 이미지 게시판 검색 VO
- ✅ `ImageFileOutVo` - 이미지 파일 VO
- ✅ `ImageSelectionInVo`, `ImageSelectionOutVo` - 이미지 선택 VO
- ✅ `ImageSelectionSearchInVo`, `ImageSelectionSearchOutVo` - 이미지 선택 검색 VO
- ✅ `ImageFileInVo`, `ImageFileOutVo` - 이미지 파일 VO
- ✅ `ImageFileSearchInVo`, `ImageFileSearchOutVo` - 이미지 파일 검색 VO
- ✅ `BoardFileAttachInVo`, `BoardFileAttachOutVo` - 게시판 파일 첨부 VO
- ✅ `BoardFileAttachSearchInVo`, `BoardFileAttachSearchOutVo` - 게시판 파일 첨부 검색 VO
- ✅ `ExpertConsultationAppInVo`, `ExpertConsultationAppOutVo` - 전문가 상담 신청 VO
- ✅ `ExpertConsultationAppSearchInVo`, `ExpertConsultationAppSearchOutVo` - 전문가 상담 신청 검색 VO
- ✅ `MnuLstInVo`, `MnuLstOutVo`, `MnuLstSearchInVo`, `MnuLstSearchOutVo` - 메뉴 관리 VO
- ✅ `MessengerAlertInVo`, `MessengerAlertOutVo` - 메신저 알림 VO
- ✅ `MessengerMessageInVo`, `MessengerMessageOutVo` - 메신저 메시지 VO
- ✅ `MessengerStatusOutVo` - 메신저 상태 VO
- ✅ `ImgBrdInVo`, `ImgBrdOutVo`, `ImgBrdSearchInVo`, `ImgBrdSearchOutVo` - 이미지 게시판 VO
- ✅ `ConsultationBoardInVo`, `ConsultationBoardOutVo` - 상담 게시판 VO
- ✅ `ConsultationBoardSearchInVo`, `ConsultationBoardSearchOutVo` - 상담 게시판 검색 VO
- ✅ `ConsultationAnswerInVo`, `ConsultationAnswerOutVo` - 상담 답변 VO
- ✅ `ConsultationFileAttachInVo`, `ConsultationFileAttachOutVo` - 상담 파일 첨부 VO
- ✅ `EmpRightsBoardInVo`, `EmpRightsBoardOutVo` - 직원권익 게시판 VO
- ✅ `EmpRightsBoardSearchInVo`, `EmpRightsBoardSearchOutVo` - 직원권익 게시판 검색 VO
- ✅ `EmpRightsFileAttachInVo`, `EmpRightsFileAttachOutVo` - 직원권익 파일 첨부 VO
- ✅ `EmpRightsCommentInVo`, `EmpRightsCommentOutVo` - 직원권익 댓글 VO
- ✅ `CmnGrpCdInVo`, `CmnGrpCdOutVo` - 공통 그룹 코드 VO
- ✅ `CmnDtlCdInVo`, `CmnDtlCdOutVo` - 공통 상세 코드 VO
- ✅ `AuthLstInVo`, `AuthLstOutVo` - 권한 관리 VO
- ✅ `CommonCodeController`는 `CmnGrpCdInVo`, `CmnGrpCdOutVo`, `CmnDtlCdInVo`, `CmnDtlCdOutVo` 재사용
- ✅ `ExpertConsultationAppInVo`, `ExpertConsultationAppOutVo` - 전문가 상담 신청 VO
- ✅ `FileAttachInVo`, `FileAttachOutVo` - 파일 첨부 관리 VO
- ✅ `ImageFileInVo`, `ImageFileOutVo` - 이미지 파일 관리 VO
- ✅ `BoardFileAttachInVo`, `BoardFileAttachOutVo` - 게시판 파일 첨부 관리 VO
- ✅ `SystemLogInVo`, `SystemLogOutVo` - 시스템 로그 관리 VO
- ✅ `SchedulerInVo`, `SchedulerOutVo` - 스케줄러 관리 VO
- ✅ `MnuLstInVo`, `MnuLstOutVo` - 메뉴 관리 VO
- ✅ `MessengerInVo`, `MessengerOutVo` - 메신저 관리 VO
- ✅ `ImgBrdInVo`, `ImgBrdOutVo` - 이미지 게시판 관리 VO

### 4. 서비스 계층
- ✅ `EmpLstService` - 직원 정보 서비스
- ✅ `AdminService` - 관리자 정보 서비스
- ✅ `NoticeService` - 공지사항 서비스
- ✅ `CounselorService` - 상담사 정보 서비스
- ✅ `SurveyService` - 설문조사 서비스
- ✅ `ImageBoardService` - 이미지 게시판 서비스
- ✅ `ImageSelectionService` - 이미지 선택 서비스
- ✅ `ImageFileService` - 이미지 파일 서비스
- ✅ `BoardFileAttachService` - 게시판 파일 첨부 서비스
- ✅ `ExpertConsultationService` - 전문가 상담 서비스
- ✅ `MnuLstService` - 메뉴 관리 서비스
- ✅ `MessengerService` - 메신저 서비스
- ✅ `ImgBrdService` - 이미지 게시판 서비스
- ✅ `ConsultationBoardService` - 상담 게시판 서비스
- ✅ `EmpRightsBoardService` - 직원권익 게시판 서비스
- ✅ `EmpRightsCommentService` - 직원권익 댓글 서비스
- ✅ `CmnCodeService` - 공통코드 서비스
- ✅ `AuthLstService` - 권한 관리 서비스
- ✅ `CommonCodeController`는 `CmnCodeService` 재사용
- ✅ `FileAttachService` - 파일 첨부 서비스
- ✅ `ImageFileService` - 이미지 파일 서비스
- ✅ `BoardFileAttachService` - 게시판 파일 첨부 서비스
- ✅ `SystemLogService` - 시스템 로그 서비스
- ✅ `SchedulerConfigService` - 스케줄러 설정 서비스
- ✅ `MnuLstService` - 메뉴 관리 서비스
- ✅ `MessengerService` - 메신저 서비스

### 5. DAO 계층
- ✅ `EmpLstDao` - 직원 정보 DAO
- ✅ `AdminLstDao` - 관리자 정보 DAO
- ✅ `CounselorLstDao` - 상담사 정보 DAO
- ✅ `EmpRefDao` - 직원 참조 DAO
- ✅ `SurveyMstDao` - 설문조사 마스터 DAO
- ✅ `SurveyResponseDao` - 설문조사 응답 DAO
- ✅ `ImageBoardDao` - 이미지 게시판 DAO
- ✅ `ImageSelectionDao` - 이미지 선택 DAO
- ✅ `ImageFileDao` - 이미지 파일 DAO
- ✅ `BoardFileAttachDao` - 게시판 파일 첨부 DAO
- ✅ `MnuLstDao` - 메뉴 관리 DAO
- ✅ `MessengerDao` - 메신저 DAO
- ✅ `ImgBrdDao` - 이미지 게시판 DAO
- ✅ `ConsultationBoardDao` - 상담 게시판 DAO
- ✅ `EmpRightsBoardDao` - 직원권익 게시판 DAO
- ✅ `EmpRightsCommentDao` - 직원권익 댓글 DAO
- ✅ `CmnCodeDao` - 공통코드 DAO
- ✅ `AuthLstDao` - 권한 관리 DAO
- ✅ `CommonCodeController`는 `CmnCodeDao` 재사용
- ✅ `FileAttachDao` - 파일 첨부 DAO
- ✅ `ImageFileDao` - 이미지 파일 DAO
- ✅ `BoardFileAttachDao` - 게시판 파일 첨부 DAO
- ✅ `SystemLogDao` - 시스템 로그 DAO

### 6. DAO DTO 클래스
- ✅ `EmpLstDto` - 직원 정보 DTO
- ✅ `AdminLstDto` - 관리자 정보 DTO
- ✅ `CounselorLstDto` - 상담사 정보 DTO
- ✅ `EmpRefDto` - 직원 참조 DTO
- ✅ `SurveyMstDto` - 설문조사 마스터 DTO
- ✅ `SurveyResponseDto` - 설문조사 응답 DTO
- ✅ `ImageBoardDto` - 이미지 게시판 DTO
- ✅ `ImageSelectionDto` - 이미지 선택 DTO
- ✅ `ImageFileDto` - 이미지 파일 DTO
- ✅ `BoardFileAttachDto` - 게시판 파일 첨부 DTO
- ✅ `ExpertConsultationDto` - 전문가 상담 DTO

### 7. 설정 파일
- ✅ `application-local.yml` - 로컬 개발 환경 설정
- ✅ `messages.properties` - 메시지 리소스 파일

## 주요 변경사항

### 1. 네이밍 컨벤션 적용
- 메서드명: `[Action명]+[기능명]` 형식 적용
  - `getAllEmployees` → `inqAllEmployees`
  - `insertEmployee` → `rgsnEmployee`
  - `updateEmployee` → `mdfcEmployee`
  - `deleteEmployee` → `delEmployee`

### 2. 의존성 주입 방식 변경
- `@Autowired` → `@RequiredArgsConstructor` + `final` 필드

### 3. 예외 처리 표준화
- `BizException`을 통한 비즈니스 예외 처리
- `GlobalExceptionHandler`를 통한 전역 예외 처리

### 4. 응답 형식 표준화
- 모든 API 응답에 `success`, `message`, `errorCode` 필드 포함
- 페이지네이션 정보 표준화

## 남은 작업

### 1. 컨트롤러 마이그레이션 (진행 중)
- ✅ `ImageSelectionController` - 이미지 선택 API
- ✅ `ImageFileController` - 이미지 파일 API
- ✅ `BoardFileAttachController` - 게시판 파일 첨부 API
- ✅ `ExpertConsultationController` - 전문상담 API
- 🔄 `EncryptionTestController` - 암호화 테스트 API
- 🔄 `SchedulerController` - 스케줄러 API
- ✅ `PageController` - 페이지 관리 API
- ✅ `SystemLogController` - 시스템 로그 API
- ✅ `SchedulerController` - 스케줄러 API
- ✅ `MnuLstController` - 메뉴 관리 API
- ✅ `MessengerController` - 메신저 API
- ✅ `ImgBrdController` - 이미지 게시판 API
- 🔄 `ConsultationBoardController` - 상담 게시판 API
- 🔄 `EmpRightsBoardController` - 직원권익 게시판 API
- 🔄 `EmpRightsCommentController` - 직원권익 댓글 API
- 🔄 `CmnCodeController` - 공통코드 API
- 🔄 `AuthLstController` - 권한 관리 API
- 🔄 `CommonCodeController` - 공통코드 관리 API
- 🔄 `EmpRightsLikeController` - 직원권익 좋아요 API
- 🔄 `FileAttachController` - 파일 첨부 API
- 🔄 `NtiLstController` - 공지사항 목록 API
- 🔄 `AnonymousUserController` - 익명 사용자 API
- 🔄 `BatchTestController` - 배치 테스트 API
- 🔄 `DbTestController` - DB 테스트 API
- 🔄 `AnonymousIdentityController` - 익명 신원 API
- 🔄 `EmpLstTableCheckController` - 직원 테이블 체크 API
- 🔄 `NameGeneratorController` - 이름 생성기 API
- 🔄 `NoticeManagementController` - 공지사항 관리 API
- 🔄 `FaviconController` - 파비콘 API

### 2. 서비스 계층 마이그레이션
- 🔄 나머지 Service 클래스들

### 3. DAO 계층 마이그레이션
- 🔄 나머지 DAO 클래스들

### 4. Mapper XML 파일 마이그레이션
- 🔄 MyBatis Mapper XML 파일들

### 5. 추가 기능 구현
- 🔄 Interceptor 구현
- 🔄 Redis 세션 클러스터링
- 🔄 EAI 연동 기능
- 🔄 배치 처리 기능

## 실행 방법

### 1. 환경 설정
```bash
# PostgreSQL 데이터베이스 설정
# Redis 설정
# application-local.yml 파일 확인
```

### 2. 애플리케이션 실행
```bash
# Maven 빌드
mvn clean compile

# 애플리케이션 실행
mvn spring-boot:run -Dspring.profiles.active=local
```

### 3. API 테스트
```bash
# 인증 API 테스트
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"empId":"test","password":"test"}'

# 직원 목록 조회 테스트
curl -X GET http://localhost:8080/api/employee/list

# 설문조사 목록 조회 테스트
curl -X GET http://localhost:8080/api/survey/list

# 이미지 게시판 목록 조회 테스트
curl -X GET http://localhost:8080/api/image-board/list

# 이미지 선택 목록 조회 테스트
curl -X GET http://localhost:8080/api/image-selection/list

# 이미지 파일 목록 조회 테스트
curl -X GET http://localhost:8080/api/image-file/list

# 게시판 파일 첨부 목록 조회 테스트
curl -X GET http://localhost:8080/api/board-file-attach/list

# 전문가 상담 신청 목록 조회 테스트
curl -X GET http://localhost:8080/api/expert-consultation/list
```

## 기술 스택
- **Framework**: Spring Boot 3.x
- **Database**: PostgreSQL
- **ORM**: MyBatis
- **Cache**: Redis
- **Build Tool**: Maven
- **Language**: Java 17
- **Lombok**: 코드 간소화
- **Validation**: Bean Validation

## 참고 문서
- IBK클라우드 업무 스타트킷 개발 가이드
- Spring Boot 공식 문서
- MyBatis 공식 문서 