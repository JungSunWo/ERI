package com.ERI.demo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * 에러 응답 DTO
 */
@Data
public class ErrorResponseDto {
    
    /**
     * 에러 코드
     */
    @JsonProperty("errorCode")
    private String errorCode;
    
    /**
     * 에러 메시지
     */
    @JsonProperty("errorMessage")
    private String errorMessage;
    
    /**
     * HTTP 상태 코드
     */
    @JsonProperty("status")
    private int status;
    
    /**
     * 에러 발생 시간
     */
    @JsonProperty("timestamp")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime timestamp;
    
    /**
     * 요청 경로
     */
    @JsonProperty("path")
    private String path;
    
    /**
     * 상세 정보 (선택사항)
     */
    @JsonProperty("details")
    private Object details;
    
    /**
     * 기본 생성자 (Jackson용)
     */
    public ErrorResponseDto() {
        this.timestamp = LocalDateTime.now();
    }
    
    /**
     * 생성자
     */
    public ErrorResponseDto(String errorCode, String errorMessage, int status, String path) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.status = status;
        this.path = path;
        this.timestamp = LocalDateTime.now();
    }
    
    /**
     * 생성자 (상세 정보 포함)
     */
    public ErrorResponseDto(String errorCode, String errorMessage, int status, String path, Object details) {
        this(errorCode, errorMessage, status, path);
        this.details = details;
    }
    
    // Lombok이 제대로 작동하지 않는 경우를 대비한 수동 getter 메서드들
    public String getErrorCode() {
        return errorCode;
    }
    
    public String getErrorMessage() {
        return errorMessage;
    }
    
    public int getStatus() {
        return status;
    }
    
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    
    public String getPath() {
        return path;
    }
    
    public Object getDetails() {
        return details;
    }
    
    // Lombok이 제대로 작동하지 않는 경우를 대비한 수동 setter 메서드들
    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
    
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
    
    public void setStatus(int status) {
        this.status = status;
    }
    
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
    
    public void setPath(String path) {
        this.path = path;
    }
    
    public void setDetails(Object details) {
        this.details = details;
    }
    
    /**
     * 접근 거부 에러 생성
     */
    public static ErrorResponseDto accessDenied(String path) {
        return new ErrorResponseDto(
            "ACCESS_DENIED",
            "접근 권한이 없습니다.",
            403,
            path
        );
    }
    
    /**
     * 인증 실패 에러 생성
     */
    public static ErrorResponseDto unauthorized(String path) {
        return new ErrorResponseDto(
            "UNAUTHORIZED",
            "로그인이 필요합니다.",
            401,
            path
        );
    }
    
    /**
     * 잘못된 요청 에러 생성
     */
    public static ErrorResponseDto badRequest(String message, String path) {
        return new ErrorResponseDto(
            "BAD_REQUEST",
            message,
            400,
            path
        );
    }
    
    /**
     * 서버 내부 에러 생성
     */
    public static ErrorResponseDto internalServerError(String path) {
        return new ErrorResponseDto(
            "INTERNAL_SERVER_ERROR",
            "서버 내부 오류가 발생했습니다.",
            500,
            path
        );
    }
    
    /**
     * 리소스 없음 에러 생성
     */
    public static ErrorResponseDto notFound(String message, String path) {
        return new ErrorResponseDto(
            "NOT_FOUND",
            message,
            404,
            path
        );
    }
    
    /**
     * 유효성 검증 에러 생성
     */
    public static ErrorResponseDto validationError(String message, String path, Object details) {
        return new ErrorResponseDto(
            "VALIDATION_ERROR",
            message,
            400,
            path,
            details
        );
    }
} 