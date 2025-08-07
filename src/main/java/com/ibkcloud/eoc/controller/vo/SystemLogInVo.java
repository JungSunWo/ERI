package com.ibkcloud.eoc.controller.vo;

import com.ibkcloud.eoc.cmm.dto.IbkCldEocDto;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
public class SystemLogInVo extends IbkCldEocDto {
    
    // 기본 정보
    private Long logSeq;                     // 로그 일련번호
    
    @Size(max = 20, message = "로그 레벨은 20자를 초과할 수 없습니다.")
    private String logLevel;                  // 로그 레벨 (INFO, WARN, ERROR, DEBUG)
    
    @Size(max = 50, message = "로그 타입은 50자를 초과할 수 없습니다.")
    private String logType;                   // 로그 타입 (API, SYSTEM, ERROR, SECURITY)
    
    @Size(max = 20, message = "직원 ID는 20자를 초과할 수 없습니다.")
    private String empId;                     // 직원 ID
    
    @Size(max = 100, message = "직원명은 100자를 초과할 수 없습니다.")
    private String empNm;                     // 직원명
    
    @Size(max = 50, message = "에러 코드는 50자를 초과할 수 없습니다.")
    private String errorCode;                 // 에러 코드
    
    @Size(max = 500, message = "에러 메시지는 500자를 초과할 수 없습니다.")
    private String errorMessage;              // 에러 메시지
    
    @Size(max = 200, message = "API URL은 200자를 초과할 수 없습니다.")
    private String apiUrl;                    // API URL
    
    @Size(max = 20, message = "HTTP 메서드는 20자를 초과할 수 없습니다.")
    private String httpMethod;                // HTTP 메서드
    
    private Integer responseTime;              // 응답 시간 (ms)
    
    private Integer statusCode;               // HTTP 상태 코드
    
    @Size(max = 1000, message = "요청 데이터는 1000자를 초과할 수 없습니다.")
    private String requestData;               // 요청 데이터
    
    @Size(max = 1000, message = "응답 데이터는 1000자를 초과할 수 없습니다.")
    private String responseData;              // 응답 데이터
    
    @Size(max = 500, message = "IP 주소는 500자를 초과할 수 없습니다.")
    private String ipAddress;                 // IP 주소
    
    @Size(max = 200, message = "사용자 에이전트는 200자를 초과할 수 없습니다.")
    private String userAgent;                 // 사용자 에이전트
    
    @Size(max = 10, message = "상태 코드는 10자를 초과할 수 없습니다.")
    private String stsCd;                     // 상태 코드 (STS001: 정상, STS002: 삭제)
    
    // 등록/수정 정보
    private LocalDateTime regDate;            // 등록일시
    private LocalDateTime updDate;            // 수정일시
    
    // 검색 조건
    private LocalDate startDate;              // 시작일
    private LocalDate endDate;                // 종료일
    private String searchKeyword;             // 검색 키워드
    private String sortKey;                   // 정렬 키
    private String sortOrder;                 // 정렬 순서
    private Integer page;                     // 페이지 번호
    private Integer size;                     // 페이지 크기
    private Integer days;                     // 일수 (오래된 로그 삭제용)
} 