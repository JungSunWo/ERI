# ERI Employee Rights Protection System

## 코드테이블 통합 관리 변경사항

### 개요
기존의 개별 코드 테이블들을 삭제하고 공통코드 테이블(`TB_CMN_GRP_CD`, `TB_CMN_DTL_CD`)로 통합 관리하도록 변경했습니다.

### 변경된 DDL 파일

#### 1. ERI_DDL.sql (사용자 시스템)
**삭제된 개별 코드 테이블:**
- `TB_GOAL_ACHV_UNIT_CD` (목표 달성 단위 코드)
- `TB_PGM_APP_TY_CD` (프로그램 신청 구분 코드)
- `TB_PGM_APP_STS_CD` (프로그램 신청 상태 코드)
- `TB_CNSL_TY_CD` (상담 종류 코드)
- `TB_SCT_CD` (바로가기 코드)
- `TB_SCT_STS_CD` (바로가기 상태 코드)

**공통코드 테이블에 추가된 코드 그룹:**
- `GOAL_ACHV_UNIT`: 목표 달성 단위 코드 (일별/주별/월별)
- `PGM_APP_TY`: 프로그램 신청 구분 코드 (신청/승인/반려)
- `PGM_APP_STS`: 프로그램 신청 상태 코드 (정상/취소/종료)
- `CNSL_TY`: 상담 종류 코드 (일반/비대면)
- `SCT_STS`: 바로가기 상태 코드 (정상/취소/종료)

#### 2. ERI_MG_DDL.sql (관리자 시스템)
**삭제된 개별 코드 테이블:**
- `TB_NTI_TRSM_MDI_CD` (공지사항 발송매체 코드)
- `TB_NTI_STS_CD` (공지사항 상태 코드)
- `TB_STS_MSG_RCMD_CD` (상태 메시지 추천 코드)
- `TB_HBT_CHLG_RCMD_CD` (습관형성 챌린지 추천 코드)
- `TB_EMP_ID_CNV_CD` (직원번호 변환코드)
- `TB_PGM_TY_CD` (프로그램 종류코드)
- `TB_PGM_APP_UNIT_CD` (프로그램 신청단위코드)
- `TB_GRVNC_TPC_CD` (고충주제 코드)
- `TB_DSRD_PSY_TEST_CD` (희망심리검사 코드)
- `TB_CNSL_LCTN_CD` (상담장소 코드)

**공통코드 테이블에 추가된 코드 그룹:**
- `NTI_TRSM_MDI`: 공지사항 발송매체 코드 (이메일/SMS/앱푸시)
- `NTI_STS`: 공지사항 상태 코드 (임시저장/발송대기/발송완료)
- `STS_MSG_RCMD`: 상태 메시지 추천 코드 (긍정적/중립적/부정적)
- `HBT_CHLG_RCMD`: 습관형성 챌린지 추천 코드 (일일/주간/월간)
- `EMP_ID_CNV`: 직원번호 변환코드 (내부/외부)
- `PGM_TY`: 프로그램 종류코드 (교육/워크샵/세미나)
- `PGM_APP_UNIT`: 프로그램 신청단위코드 (개인/팀/부서)
- `GRVNC_TPC`: 고충주제 코드 (업무/인사/복지)
- `DSRD_PSY_TEST`: 희망심리검사 코드 (MMPI/MBTI/스트레스)
- `CNSL_LCTN`: 상담장소 코드 (온라인/오프라인/전화)

#### 3. ERI_DSBD_DLL.sql (대시보드/통계 시스템)
**삭제된 개별 코드 테이블:**
- `TB_DEPT_LST_CD` (부점목록 코드)
- `TB_DEPT_TY_CD` (부점구분 코드)
- `TB_POS_CD` (직급 코드)
- `TB_GENDER_CD` (성별 코드)
- `TB_MCHR_TY_CD` (마음검진 구분코드)

**공통코드 테이블에 추가된 코드 그룹:**
- `DEPT_TY`: 부서구분코드 (본사/지사/팀)
- `POS`: 직급코드 (사원/대리/과장/차장/부장)
- `GENDER`: 성별코드 (남성/여성)
- `MCHR_TY`: 정신건강검진종류코드 (스트레스/우울증/불안증)

### 공통코드 테이블 구조

#### TB_CMN_GRP_CD (공통 그룹 코드)
```sql
CREATE TABLE TB_CMN_GRP_CD (
    GRP_CD      VARCHAR(20)  NOT NULL COMMENT '그룹 코드',
    GRP_CD_NM   VARCHAR(100) NULL     COMMENT '그룹 코드 명',
    GRP_CD_DESC VARCHAR(255) NULL     COMMENT '그룹 코드 설명',
    USE_YN      CHAR(1)      NOT NULL DEFAULT 'Y' COMMENT '사용여부',
    PRIMARY KEY (GRP_CD)
) COMMENT '공통 그룹 코드';
```

#### TB_CMN_DTL_CD (공통 상세 코드)
```sql
CREATE TABLE TB_CMN_DTL_CD (
    GRP_CD      VARCHAR(20)  NOT NULL COMMENT '그룹 코드',
    DTL_CD      VARCHAR(20)  NOT NULL COMMENT '상세 코드',
    DTL_CD_NM   VARCHAR(100) NULL     COMMENT '상세 코드 명',
    DTL_CD_DESC VARCHAR(255) NULL     COMMENT '상세 코드 설명',
    SORT_ORD    INT          NULL     COMMENT '정렬 순서',
    USE_YN      CHAR(1)      NOT NULL DEFAULT 'Y' COMMENT '사용여부',
    PRIMARY KEY (GRP_CD, DTL_CD)
) COMMENT '공통 상세 코드';
```

### 생성된 Java 파일

#### VO 클래스
- `CmnGrpCdVO.java`: 공통 그룹 코드 VO
- `CmnDtlCdVO.java`: 공통 상세 코드 VO

#### Mapper 인터페이스
- `CmnGrpCdMapper.java`: 공통 그룹 코드 Mapper
- `CmnDtlCdMapper.java`: 공통 상세 코드 Mapper

#### Mapper XML 파일
- `CmnGrpCdMapper.xml`: 공통 그룹 코드 Mapper XML
- `CmnDtlCdMapper.xml`: 공통 상세 코드 Mapper XML

#### 서비스 클래스
- `CmnCodeService.java`: 공통코드 관리 서비스

#### 컨트롤러
- `CmnCodeController.java`: 공통코드 관리 컨트롤러

### API 엔드포인트

#### 그룹 코드 관리
- `GET /api/cmn-code/group`: 전체 그룹 코드 조회
- `GET /api/cmn-code/group/{grpCd}`: 특정 그룹 코드 조회
- `POST /api/cmn-code/group`: 그룹 코드 등록
- `PUT /api/cmn-code/group`: 그룹 코드 수정
- `DELETE /api/cmn-code/group/{grpCd}`: 그룹 코드 삭제
- `GET /api/cmn-code/group/use-yn/{useYn}`: 사용여부로 그룹 코드 조회

#### 상세 코드 관리
- `GET /api/cmn-code/detail`: 전체 상세 코드 조회
- `GET /api/cmn-code/detail/group/{grpCd}`: 그룹 코드별 상세 코드 조회
- `GET /api/cmn-code/detail/{grpCd}/{dtlCd}`: 특정 상세 코드 조회
- `POST /api/cmn-code/detail`: 상세 코드 등록
- `PUT /api/cmn-code/detail`: 상세 코드 수정
- `DELETE /api/cmn-code/detail/{grpCd}/{dtlCd}`: 상세 코드 삭제
- `DELETE /api/cmn-code/detail/group/{grpCd}`: 그룹 코드의 모든 상세 코드 삭제
- `GET /api/cmn-code/detail/use-yn/{useYn}`: 사용여부로 상세 코드 조회
- `GET /api/cmn-code/detail/group/{grpCd}/use-yn/{useYn}`: 그룹 코드와 사용여부로 상세 코드 조회

### 장점

1. **코드 관리의 일관성**: 모든 코드를 하나의 체계로 관리
2. **유지보수성 향상**: 코드 추가/수정/삭제가 용이
3. **데이터 무결성**: 공통코드 테이블을 통한 참조 무결성 보장
4. **확장성**: 새로운 코드 그룹 추가가 쉬움
5. **성능**: 인덱싱과 조인 최적화 가능

### 사용 예시

```java
// 그룹 코드 조회
List<CmnGrpCdVO> groupCodes = cmnCodeService.getAllGroupCodes();

// 특정 그룹의 상세 코드 조회
List<CmnDtlCdVO> detailCodes = cmnCodeService.getDetailCodesByGrpCd("PGM_APP_TY");

// 사용 중인 상세 코드만 조회
List<CmnDtlCdVO> activeCodes = cmnCodeService.getDetailCodesByGrpCdAndUseYn("PGM_APP_TY", "Y");
```

## 페이징 처리 기능 추가

### 페이징 관련 클래스:
- `PageRequestDto`: 페이징 요청을 위한 DTO
- `PageResponseDto<T>`: 페이징 응답을 위한 제네릭 DTO

### 페이징 기능 특징:
- **페이지 번호**: 1부터 시작
- **페이지 크기**: 기본값 10건
- **정렬**: 필드별 오름차순/내림차순 정렬 지원
- **검색**: 키워드 검색 및 특정 필드 검색 지원
- **메타데이터**: 전체 데이터 수, 전체 페이지 수, 이전/다음 페이지 존재 여부 등 제공

### 페이징 처리된 주요 Mapper들:
- `CmnGrpCdMapper`: 공통 그룹 코드 관리
- `CmnDtlCdMapper`: 공통 상세 코드 관리
- `EmpLstMapper`: 직원 목록 관리
- `PgmLstMapper`: 프로그램 목록 관리

### 페이징 API 엔드포인트 예시:
```
# 공통 그룹 코드 조회 (페이징)
GET /api/cmn-code/group-codes?page=1&size=10&sortBy=grpCd&sortDirection=ASC&keyword=검색어&searchField=grpCd

# 공통 상세 코드 조회 (페이징)
GET /api/cmn-code/detail-codes?page=1&size=20&sortBy=sortOrd&sortDirection=ASC

# 프로그램 목록 조회 (페이징)
GET /api/pgm/list?page=1&size=15&keyword=프로그램명
```

### 페이징 응답 형식:
```json
{
  "content": [...],           // 데이터 목록
  "totalElements": 100,       // 전체 데이터 수
  "totalPages": 10,          // 전체 페이지 수
  "currentPage": 1,          // 현재 페이지 번호
  "pageSize": 10,            // 페이지 크기
  "first": true,             // 첫 페이지 여부
  "last": false,             // 마지막 페이지 여부
  "hasPrevious": false,      // 이전 페이지 존재 여부
  "hasNext": true            // 다음 페이지 존재 여부
}
```

## 데이터베이스 구조

### 공통 코드 테이블

#### TB_CMN_GRP_CD (공통 그룹 코드)
```sql
CREATE TABLE TB_CMN_GRP_CD (
    GRP_CD VARCHAR(20) PRIMARY KEY COMMENT '그룹 코드',
    GRP_CD_NM VARCHAR(100) NOT NULL COMMENT '그룹 코드명',
    GRP_CD_DESC VARCHAR(500) COMMENT '그룹 코드 설명',
    USE_YN CHAR(1) DEFAULT 'Y' COMMENT '사용 여부'
);
```

#### TB_CMN_DTL_CD (공통 상세 코드)
```sql
CREATE TABLE TB_CMN_DTL_CD (
    GRP_CD VARCHAR(20) NOT NULL COMMENT '그룹 코드',
    DTL_CD VARCHAR(20) NOT NULL COMMENT '상세 코드',
    DTL_CD_NM VARCHAR(100) NOT NULL COMMENT '상세 코드명',
    DTL_CD_DESC VARCHAR(500) COMMENT '상세 코드 설명',
    SORT_ORD INT DEFAULT 0 COMMENT '정렬 순서',
    USE_YN CHAR(1) DEFAULT 'Y' COMMENT '사용 여부',
    PRIMARY KEY (GRP_CD, DTL_CD),
    FOREIGN KEY (GRP_CD) REFERENCES TB_CMN_GRP_CD(GRP_CD)
);
```

## Java 클래스 구조

### 새로 생성된 클래스들:
- `src/main/java/com/ERI/demo/dto/PageRequestDto.java`
- `src/main/java/com/ERI/demo/dto/PageResponseDto.java`
- `src/main/java/com/ERI/demo/vo/CmnGrpCdVO.java`
- `src/main/java/com/ERI/demo/vo/CmnDtlCdVO.java`
- `src/main/java/com/ERI/demo/mappers/CmnGrpCdMapper.java`
- `src/main/java/com/ERI/demo/mappers/CmnDtlCdMapper.java`
- `src/main/java/com/ERI/demo/service/CmnCodeService.java`
- `src/main/java/com/ERI/demo/Controller/CmnCodeController.java`

### 수정된 클래스들:
- `src/main/java/com/ERI/demo/mappers/EmpLstMapper.java` (페이징 기능 추가)
- `src/main/java/com/ERI/demo/mappers/PgmLstMapper.java` (페이징 기능 추가)

### 삭제된 클래스들:
- 개별 코드 테이블 관련 VO, Mapper, XML 파일들

## API 엔드포인트

### 공통 코드 관리 API

#### 그룹 코드 관리
- `GET /api/cmn-code/group-codes` - 그룹 코드 목록 조회 (페이징)
- `GET /api/cmn-code/group-codes/all` - 그룹 코드 전체 목록 조회
- `GET /api/cmn-code/group-codes/{grpCd}` - 그룹 코드 상세 조회
- `POST /api/cmn-code/group-codes` - 그룹 코드 등록
- `PUT /api/cmn-code/group-codes/{grpCd}` - 그룹 코드 수정
- `DELETE /api/cmn-code/group-codes/{grpCd}` - 그룹 코드 삭제
- `GET /api/cmn-code/group-codes/use-yn/{useYn}` - 사용여부별 그룹 코드 조회 (페이징)

#### 상세 코드 관리
- `GET /api/cmn-code/detail-codes` - 상세 코드 목록 조회 (페이징)
- `GET /api/cmn-code/detail-codes/all` - 상세 코드 전체 목록 조회
- `GET /api/cmn-code/detail-codes/group/{grpCd}` - 그룹별 상세 코드 조회 (페이징)
- `GET /api/cmn-code/detail-codes/{grpCd}/{dtlCd}` - 상세 코드 상세 조회
- `POST /api/cmn-code/detail-codes` - 상세 코드 등록
- `PUT /api/cmn-code/detail-codes/{grpCd}/{dtlCd}` - 상세 코드 수정
- `DELETE /api/cmn-code/detail-codes/{grpCd}/{dtlCd}` - 상세 코드 삭제
- `GET /api/cmn-code/detail-codes/use-yn/{useYn}` - 사용여부별 상세 코드 조회 (페이징)

## 페이징 처리의 장점

### 1. 성능 향상
- 대용량 데이터 조회 시 메모리 사용량 감소
- 네트워크 트래픽 최적화
- 응답 시간 단축

### 2. 사용자 경험 개선
- 페이지별 데이터 로딩으로 UI 반응성 향상
- 검색 및 필터링 기능과 연동 가능
- 정렬 기능으로 데이터 탐색 편의성 증대

### 3. 확장성
- 데이터 증가에 따른 성능 저하 방지
- 모바일 환경에서의 효율적인 데이터 전송
- API 호출 횟수 최적화

### 4. 유지보수성
- 일관된 페이징 처리 방식
- 재사용 가능한 페이징 컴포넌트
- 표준화된 API 응답 형식

## 기술 스택
- **Backend**: Spring Boot, MyBatis
- **Database**: MySQL
- **Build Tool**: Maven
- **Language**: Java 8+

## 실행 방법
1. 데이터베이스 스키마 실행 (ERI_DDL.sql, ERI_MG_DDL.sql, ERI_DSBD_DLL.sql)
2. application.properties에서 데이터베이스 연결 정보 설정
3. Maven 빌드 및 실행

## 주의사항
- 그룹 코드 삭제 시 관련된 상세 코드들도 함께 삭제됩니다.
- 페이징 처리 시 검색 조건과 정렬 조건을 적절히 조합하여 사용하세요.
- 페이지 크기는 시스템 성능을 고려하여 적절히 설정하세요.
- 권한 체크는 세션 기반이므로 세션 만료 시간을 적절히 설정하세요.
- 새로운 API 추가 시 인터셉터에 권한 체크 로직을 추가해야 합니다.
- 에러 응답은 표준화된 형식을 사용하여 프론트엔드와의 일관성을 유지하세요.

## Spring Batch 기능 추가

### 개요
Spring Boot Batch를 사용하여 대용량 데이터 처리를 위한 배치 Job을 구현했습니다.

### 구현된 배치 Job

#### 1. 직원 정보 배치 Job (`empInfoBatchJob`)
- **목적**: empInfo.txt 파일을 읽어서 직원번호와 직원명을 암호화하고 DB에 저장
- **파일 위치**: `src/main/resources/templates/empInfo.txt`
- **암호화 방식**: AES/CBC/PKCS5Padding
- **처리 과정**:
  1. 원본 직원번호 → 암호화 → TB_EMP_ENCRYPT.ENCRYPT_EMP_NO
  2. 원본 직원명 → 암호화 → TB_EMP_ENCRYPT.ORIG_EMP_NM
  3. 랜덤 한글명 생성 → TB_EMP_ENCRYPT.RANDOM_EMP_NM
  4. 암호화된 직원번호 → TB_EMP_LST.EMP_ID
- **처리 방식**: 기존 데이터는 업데이트, 신규 데이터는 삽입

### 배치 구성 요소

#### 설정 클래스
- `BatchConfig.java`: Spring Batch 기본 설정
- `EmpBatchJobConfig.java`: 직원 정보 배치 Job 설정

#### 배치 컴포넌트
- `EmpBatchReader.java`: empInfo.txt 파일 읽기
- `EmpBatchProcessor.java`: 직원번호 암호화 처리
- `EmpBatchWriter.java`: DB 저장 처리

#### 컨트롤러
- `BatchController.java`: 배치 Job 실행 API
- `BatchTestController.java`: 배치 테스트 페이지

### 배치 Job 실행 방법

#### 1. REST API를 통한 실행
```bash
# 직원 정보 배치 Job 실행
POST /api/batch/emp-info
```

#### 2. 웹 페이지를 통한 실행
```
http://localhost:8080/batch-test
```

### 배치 설정

#### application.properties 설정
```properties
# Batch Job 자동 실행 비활성화
spring.batch.job.enabled=false

# Batch 메타데이터 테이블 스키마 설정
spring.batch.jdbc.initialize-schema=always

# Batch Job 실행 시 로그 레벨
logging.level.org.springframework.batch=DEBUG
```

#### pom.xml 의존성
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-batch</artifactId>
</dependency>
```

### 배치 Job 실행 결과

#### 성공 응답
```json
{
  "success": true,
  "message": "직원 정보 배치 Job 실행 완료",
  "jobExecutionId": 1,
  "jobStatus": "COMPLETED",
  "processingTime": "1500ms",
  "timestamp": 1640995200000
}
```

#### 실패 응답
```json
{
  "success": false,
  "message": "Job이 이미 실행 중입니다",
  "error": "JobExecutionAlreadyRunningException",
  "timestamp": 1640995200000
}
```

### 배치 Job 특징

#### 1. 청크 기반 처리
- 청크 크기: 10건씩 처리
- 메모리 효율성과 성능 최적화

#### 2. 트랜잭션 관리
- 각 청크별로 트랜잭션 처리
- 실패 시 롤백 보장

#### 3. 중복 실행 방지
- Job 파라미터로 실행 시간 추가
- 동일한 파라미터로 재실행 방지

#### 4. 에러 처리
- 개별 레코드 처리 실패 시 로그 기록
- 전체 Job 중단 없이 계속 처리

### 보안 고려사항

#### 1. 암호화 키 관리
- 현재: 코드에 하드코딩 (개발용)
- 운영 환경: 외부 설정 파일 또는 환경 변수 사용 권장

#### 2. 접근 권한
- 배치 Job 실행 API에 대한 권한 체크 필요
- 운영 환경에서는 관리자 권한으로만 실행 가능하도록 설정

### 확장 가능한 배치 Job 추가 방법

#### 1. 새로운 배치 Job 생성
```java
@Configuration
public class NewBatchJobConfig {
    
    @Bean
    public Job newBatchJob(JobRepository jobRepository, Step newBatchStep) {
        return new JobBuilder("newBatchJob", jobRepository)
                .start(newBatchStep)
                .build();
    }
    
    @Bean
    public Step newBatchStep(JobRepository jobRepository, 
                            PlatformTransactionManager transactionManager,
                            ItemReader<DataVO> reader,
                            ItemProcessor<DataVO, DataVO> processor,
                            ItemWriter<DataVO> writer) {
        return new StepBuilder("newBatchStep", jobRepository)
                .<DataVO, DataVO>chunk(10, transactionManager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }
}
```

#### 2. Reader, Processor, Writer 구현
- `ItemReader<T>`: 데이터 읽기
- `ItemProcessor<T, R>`: 데이터 처리/변환
- `ItemWriter<T>`: 데이터 저장

#### 3. Controller에 실행 API 추가
```java
@PostMapping("/new-batch")
public ResponseEntity<Map<String, Object>> runNewBatch() {
    // 배치 Job 실행 로직
}
```

### 모니터링 및 로깅

#### 1. 배치 Job 실행 로그
- Job 시작/종료 시간
- 처리된 레코드 수
- 성공/실패 건수
- 처리 시간

#### 2. 에러 로그
- 개별 레코드 처리 실패 정보
- 전체 Job 실패 원인
- 스택 트레이스

### 성능 최적화 팁

#### 1. 청크 크기 조정
- 메모리 사용량과 성능의 균형
- 일반적으로 100~1000건 사이 권장

#### 2. 병렬 처리
- 대용량 데이터 처리 시 멀티스레드 활용
- `TaskExecutor` 설정으로 병렬 처리 가능

#### 3. 배치 크기 최적화
- DB 저장 시 배치 크기 조정
- 네트워크 오버헤드 최소화

### 주의사항
- 배치 Job 실행 전 데이터베이스 백업 권장
- 대용량 데이터 처리 시 시스템 리소스 모니터링
- 운영 환경에서는 배치 Job 실행 시간을 시스템 부하가 적은 시간대에 설정
- 암호화 키는 보안 정책에 따라 안전하게 관리
- 배치 Job 실행 결과는 로그 파일에 상세히 기록하여 추적 가능하도록 설정 