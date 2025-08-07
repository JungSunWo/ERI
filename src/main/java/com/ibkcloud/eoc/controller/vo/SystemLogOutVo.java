package com.ibkcloud.eoc.controller.vo;

import com.ibkcloud.eoc.cmm.dto.IbkCldEocDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
@EqualsAndHashCode(callSuper = true)
public class SystemLogOutVo extends IbkCldEocDto {
    
    // 기본 정보
    private Long logSeq;                     // 로그 일련번호
    private String logLevel;                  // 로그 레벨 (INFO, WARN, ERROR, DEBUG)
    private String logType;                   // 로그 타입 (API, SYSTEM, ERROR, SECURITY)
    private String empId;                     // 직원 ID
    private String empNm;                     // 직원명
    private String errorCode;                 // 에러 코드
    private String errorMessage;              // 에러 메시지
    private String apiUrl;                    // API URL
    private String httpMethod;                // HTTP 메서드
    private Integer responseTime;              // 응답 시간 (ms)
    private Integer statusCode;               // HTTP 상태 코드
    private String requestData;               // 요청 데이터
    private String responseData;              // 응답 데이터
    private String ipAddress;                 // IP 주소
    private String userAgent;                 // 사용자 에이전트
    private String stsCd;                     // 상태 코드 (STS001: 정상, STS002: 삭제)
    
    // 등록/수정 정보
    private LocalDateTime regDate;            // 등록일시
    private LocalDateTime updDate;            // 수정일시
    
    // 목록 조회용
    private List<SystemLogOutVo> data;       // 시스템 로그 목록
    private Integer count;                    // 총 개수
    private Integer pageNo;                   // 현재 페이지
    private Integer pageSize;                 // 페이지 크기
    private Integer ttalPageNbi;              // 총 페이지 수
    
    // 통계 정보
    private Map<String, Object> stats;        // 통계 데이터
    private Map<String, Integer> levelStats;  // 로그 레벨별 통계
    private Map<String, Integer> typeStats;   // 로그 타입별 통계
    private Map<String, Integer> errorStats;  // 에러별 통계
    private Map<String, Integer> periodStats; // 기간별 통계
    
    // 추가 정보
    private Integer deletedCount;             // 삭제된 개수
    private String period;                    // 기간 정보
} 