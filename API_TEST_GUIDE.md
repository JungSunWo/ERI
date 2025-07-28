# ERI API 테스트 가이드 (함수 대체 후)

## 📋 개요
PostgreSQL 함수들을 자바 서비스로 대체한 후의 API 테스트 가이드입니다.

## 🔧 익명 사용자 관리 API

### 1. 익명 사용자 생성
```http
POST /api/anonymous-users?nickname=익명사용자1
```

**응답 예시:**
```json
{
  "anonymousId": 1
}
```

### 2. 익명 사용자 목록 조회
```http
GET /api/anonymous-users
```

**응답 예시:**
```json
[
  {
    "anonymousId": 1,
    "nickname": "익명사용자1",
    "regDate": "2024-01-15T10:30:00",
    "useYn": "Y",
    "delYn": "N"
  }
]
```

### 3. 익명 사용자 상세 조회
```http
GET /api/anonymous-users/1
```

### 4. 닉네임으로 익명 사용자 조회
```http
GET /api/anonymous-users/nickname/익명사용자1
```

### 5. 닉네임 중복 확인
```http
GET /api/anonymous-users/check-nickname/익명사용자1
```

**응답 예시:**
```json
true
```

### 6. 익명 사용자 삭제
```http
DELETE /api/anonymous-users/1
```

## 📁 파일 첨부 관리 API

### 1. 파일 목록 조회
```http
GET /api/file/list?refTblCd=NTI&refPkVal=123
```

**응답 예시:**
```json
{
  "success": true,
  "data": [
    {
      "fileSeq": 1,
      "fileNm": "공지사항.pdf",
      "fileSaveNm": "20240115_123456.pdf",
      "filePath": "/uploads/nti/2024/01/15/",
      "fileSize": 1024000,
      "downloadCnt": 5
    }
  ],
  "message": "첨부파일 목록을 조회했습니다."
}
```

### 2. 파일 다운로드 (다운로드 카운트 자동 증가)
```http
GET /api/file/download/1
```

### 3. 파일 미리보기
```http
GET /api/file/preview/1
```

## 📊 설문조사 통계 API

### 1. 설문 응답률 계산
```http
GET /api/surveys/1/response-rate
```

**응답 예시:**
```json
{
  "success": true,
  "data": 75.5,
  "message": "설문 응답률을 계산했습니다."
}
```

### 2. 설문 응답 점수 계산
```http
GET /api/surveys/1/responses/100/score
```

**응답 예시:**
```json
{
  "success": true,
  "data": 85.0,
  "message": "설문 응답 점수를 계산했습니다."
}
```

### 3. 설문 통계 업데이트
```http
POST /api/surveys/1/update-stats
```

**응답 예시:**
```json
{
  "success": true,
  "message": "설문 통계를 업데이트했습니다."
}
```

### 4. 설문 통계 조회
```http
GET /api/surveys/1/stats
```

**응답 예시:**
```json
{
  "success": true,
  "data": [
    {
      "surveySeq": 1,
      "questionSeq": 1,
      "choiceSeq": 1,
      "responseCnt": 50,
      "responseRate": 75.5,
      "avgScore": 4.2,
      "minScore": 1,
      "maxScore": 5,
      "stdDevScore": 0.8
    }
  ],
  "message": "설문 통계를 조회했습니다."
}
```

## 🧪 테스트 시나리오

### 시나리오 1: 익명 사용자 생성 및 관리
1. 익명 사용자 생성
2. 생성된 사용자 조회
3. 닉네임 중복 확인
4. 사용자 삭제

### 시나리오 2: 파일 첨부 관리
1. 파일 목록 조회
2. 파일 다운로드 (다운로드 카운트 확인)
3. 파일 미리보기

### 시나리오 3: 설문조사 통계
1. 설문 응답률 계산
2. 설문 응답 점수 계산
3. 설문 통계 업데이트
4. 설문 통계 조회

## ⚠️ 주의사항

1. **데이터베이스 연결**: PostgreSQL 데이터베이스가 실행 중이어야 합니다.
2. **테이블 생성**: 관련 테이블들이 생성되어 있어야 합니다.
3. **세션 관리**: 일부 API는 로그인 세션이 필요할 수 있습니다.
4. **파일 업로드**: 파일 업로드 기능은 별도로 구현되어 있습니다.

## 🔍 디버깅

### 로그 확인
- 애플리케이션 로그에서 함수 대체 관련 메시지 확인
- SQL 쿼리 실행 로그 확인

### 데이터베이스 확인
```sql
-- 익명 사용자 테이블 확인
SELECT * FROM TB_ANONYMOUS_USER WHERE DEL_YN = 'N';

-- 파일 첨부 테이블 확인
SELECT * FROM TB_FILE_ATTACH WHERE DEL_YN = 'N';

-- 설문 통계 테이블 확인
SELECT * FROM TB_SURVEY_STATS;
```

## 📝 변경사항 요약

### 제거된 PostgreSQL 함수들
- `CREATE_ANONYMOUS_USER()`
- `GET_FILE_ATTACH_LIST()`
- `INCREMENT_FILE_DOWNLOAD_COUNT()`
- `CALCULATE_SURVEY_RESPONSE_RATE()`
- `CALCULATE_SURVEY_SCORE()`
- `UPDATE_SURVEY_STATS()`

### 추가된 자바 서비스들
- `AnonymousUserService`
- `FileAttachService`
- `SurveyService` (기존 메서드 활용)

### 개선사항
- 유지보수성 향상
- 디버깅 용이성
- 테스트 가능성
- 확장성 개선 