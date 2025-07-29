package com.ERI.demo.vo;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

/**
 * 시스템 로그 VO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SystemLogVo {
    
    /**
     * 로그 시퀀스
     */
    private Long logSeq;
    
    /**
     * 로그 레벨 (INFO, WARN, ERROR, DEBUG)
     */
    private String logLevel;
    
    /**
     * 로그 타입 (SYSTEM, API, DATABASE, SECURITY, MESSENGER)
     */
    private String logType;
    
    /**
     * 로그 메시지
     */
    private String logMessage;
    
    /**
     * 상세 로그 내용
     */
    private String logDetail;
    
    /**
     * 발생 클래스명
     */
    private String className;
    
    /**
     * 발생 메서드명
     */
    private String methodName;
    
    /**
     * 발생 라인 번호
     */
    private Integer lineNumber;
    
    /**
     * 스택 트레이스
     */
    private String stackTrace;
    
    /**
     * 사용자 사번
     */
    private String empId;
    
    /**
     * 세션 ID
     */
    private String sessionId;
    
    /**
     * 요청 URI
     */
    private String requestUri;
    
    /**
     * 요청 메서드
     */
    private String requestMethod;
    
    /**
     * 요청 파라미터
     */
    private String requestParams;
    
    /**
     * 응답 상태 코드
     */
    private Integer responseStatus;
    
    /**
     * 실행 시간 (ms)
     */
    private Long executionTime;
    
    /**
     * IP 주소
     */
    private String ipAddress;
    
    /**
     * 사용자 에이전트
     */
    private String userAgent;
    
    /**
     * 에러 코드
     */
    private String errorCode;
    
    /**
     * 에러 카테고리
     */
    private String errorCategory;
    
    /**
     * 생성일시
     */
    private LocalDateTime createdDate;
    
    /**
     * 생성자
     */
    private String createdBy;
}