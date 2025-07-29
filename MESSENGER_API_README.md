# 메신저 알람/쪽지 전송 모듈

## 개요
이 모듈은 IBK 기업은행의 메신저 시스템과 연동하여 알람/쪽지를 전송하는 기능을 제공합니다.

## 주요 기능
- ✅ 동기/비동기 알람 전송
- ✅ 단일/다중 수신자 지원
- ✅ 링크 URL 및 텍스트 지원
- ✅ 환경별 설정 (개발/운영)
- ✅ 에러 처리 및 로깅
- ✅ 재시도 메커니즘

## API 스펙
- **요청 방식**: HTTP POST
- **Content-Type**: application/x-www-form-urlencoded
- **환경별 URL**:
  - 개발: `http://{target_ip}:{port}/servlet/AnnounceService`
  - 운영: `http://172.18.123.43:8010/servlet/AnnounceService`

## 필수 파라미터
| 파라미터명 | 설명 | 필수여부 |
|-----------|------|----------|
| SRV_CODE | 발송서버코드 (UC 관리자 확인 필요) | Y |
| RECIPIENT | 수신자 사번 (다중 시 쉼표 구분) | Y |

## 선택 파라미터
| 파라미터명 | 설명 | 최대길이 |
|-----------|------|----------|
| SEND | 발신자 사번 | - |
| SENDER_ALIAS | 발신자명 | - |
| TITLE | 알람 제목 | 11자 |
| BODY | 알람 내용 | 36자 |
| LINKTXT | 링크 텍스트 | - |
| LINKURL | 링크 URL | - |
| POPUP | 팝업창 출력 여부 (사용안함) | - |
| SVR_NAME | 발송서버명 (사용안함) | - |

## 사용 방법

### 1. 설정 파일 수정
`application.properties`에서 메신저 설정을 수정하세요:

```properties
# 메신저 API 환경 설정
messenger.environment=dev
messenger.srv-code=YOUR_SERVER_CODE_HERE
messenger.default-sender=SYSTEM
messenger.default-sender-alias=ERI 시스템
```

### 2. 컨트롤러 사용 예제

```java
@Autowired
private MessengerService messengerService;

// 동기 전송
MessengerAlertDto alertDto = MessengerAlertDto.builder()
    .recipient("S97441,S97442")
    .title("새 게시글")
    .body("새로운 게시글이 등록되었습니다.")
    .linkUrl("/resources/board/detail/123")
    .linkTxt("게시글 보기")
    .build();

MessengerAlertResponseDto response = messengerService.sendAlert(alertDto);

// 비동기 전송
CompletableFuture<MessengerAlertResponseDto> future = 
    messengerService.sendAlertAsync(alertDto);
```

### 3. 유틸리티 사용 예제

```java
@Autowired
private MessengerUtil messengerUtil;

// 단일 사용자 알람
boolean success = messengerUtil.sendAlertToUser(
    "S97441", 
    "새 게시글", 
    "새로운 게시글이 등록되었습니다.",
    "/resources/board/detail/123",
    "게시글 보기"
);

// 다중 사용자 알람
List<String> recipients = Arrays.asList("S97441", "S97442", "S97443");
boolean success = messengerUtil.sendAlertToUsers(
    recipients,
    "새 게시글",
    "새로운 게시글이 등록되었습니다.",
    "/resources/board/detail/123",
    "게시글 보기"
);

// 게시글 알람
messengerUtil.sendBoardAlert(
    recipients,
    "게시글 제목",
    "작성자명",
    123L
);
```

## API 엔드포인트

### 1. 알람 전송 (동기)
```
POST /api/messenger/alert
Content-Type: application/json

{
  "srvCode": "YOUR_SERVER_CODE",
  "recipient": "S97441,S97442",
  "title": "새 게시글",
  "body": "새로운 게시글이 등록되었습니다.",
  "linkUrl": "/resources/board/detail/123",
  "linkTxt": "게시글 보기"
}
```

### 2. 알람 전송 (비동기)
```
POST /api/messenger/alert/async
Content-Type: application/json

{
  "srvCode": "YOUR_SERVER_CODE",
  "recipient": "S97441,S97442",
  "title": "새 게시글",
  "body": "새로운 게시글이 등록되었습니다.",
  "linkUrl": "/resources/board/detail/123",
  "linkTxt": "게시글 보기"
}
```

### 3. 서비스 상태 확인
```
GET /api/messenger/status
```

## 응답 형식

### 성공 응답
```json
{
  "success": true,
  "message": "알람이 성공적으로 전송되었습니다.",
  "code": "SUCCESS",
  "sentCount": 2,
  "failedCount": 0,
  "totalRecipients": 2
}
```

### 실패 응답
```json
{
  "success": false,
  "message": "필수 파라미터가 누락되었습니다.",
  "code": "MISSING_REQUIRED_FIELDS",
  "sentCount": 0,
  "failedCount": 0,
  "totalRecipients": 0
}
```

## 에러 코드

| 코드 | 설명 |
|------|------|
| SUCCESS | 전송 성공 |
| MISSING_REQUIRED_FIELDS | 필수 파라미터 누락 |
| CLIENT_ERROR | 클라이언트 오류 (4xx) |
| SERVER_ERROR | 서버 오류 (5xx) |
| CONNECTION_ERROR | 연결 오류 |
| HTTP_ERROR | HTTP 상태 코드 오류 |
| INTERNAL_ERROR | 내부 서버 오류 |
| UNKNOWN_ERROR | 알 수 없는 오류 |

## 주의사항

1. **발송서버코드**: UC 관리자로부터 발송서버코드를 발급받아야 합니다.
2. **수신자 사번**: 실제 메신저에 등록된 사번이어야 합니다.
3. **제목/본문 길이**: 제목은 11자, 본문은 36자로 제한됩니다.
4. **환경 설정**: 개발/운영 환경에 따라 URL이 자동으로 변경됩니다.
5. **타임아웃**: 연결 10초, 읽기 30초로 설정되어 있습니다.

## 로깅

모든 메신저 API 호출은 로그로 기록됩니다:
- INFO: 성공적인 전송
- WARN: 전송 실패
- ERROR: 예외 발생

## 설정 파일

### application.properties
```properties
# 메신저 API 환경 설정
messenger.environment=dev
messenger.dev-url=http://{target_ip}:{port}/servlet/AnnounceService
messenger.prod-url=172.18.123.43:8010
messenger.srv-code=YOUR_SERVER_CODE_HERE
messenger.default-sender=SYSTEM
messenger.default-sender-alias=ERI 시스템
messenger.connect-timeout=10
messenger.read-timeout=30
messenger.max-retries=3
messenger.retry-interval=1000
```

## 파일 구조

```
src/main/java/com/ERI/demo/
├── config/
│   ├── MessengerConfig.java          # 메신저 설정
│   └── RestTemplateConfig.java       # RestTemplate 설정
├── Controller/
│   └── MessengerController.java      # 메신저 API 컨트롤러
├── dto/
│   ├── MessengerAlertDto.java        # 알람 전송 DTO
│   └── MessengerAlertResponseDto.java # 알람 응답 DTO
├── service/
│   └── MessengerService.java         # 메신저 서비스
└── util/
    └── MessengerUtil.java            # 메신저 유틸리티
```

## 테스트

### 1. 서비스 상태 확인
```bash
curl -X GET http://localhost:8080/api/messenger/status
```

### 2. 알람 전송 테스트
```bash
curl -X POST http://localhost:8080/api/messenger/alert \
  -H "Content-Type: application/json" \
  -d '{
    "srvCode": "YOUR_SERVER_CODE",
    "recipient": "S97441",
    "title": "테스트",
    "body": "메신저 알람 테스트입니다."
  }'
```

## 라이센스
IBK 기업은행 내부 사용을 위한 모듈입니다.