package com.ERI.demo.util;

import com.ERI.demo.service.SystemLogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;

/**
 * 시스템 로그 유틸리티
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class SystemLogUtil {
    
    private final SystemLogService systemLogService;
    
    /**
     * API 호출 로그 기록
     */
    public void logApiCall(String message, String detail, String className, 
                          String methodName, String empId, HttpServletRequest request) {
        systemLogService.logInfo("API", message, detail, className, methodName, empId, request);
    }
    
    /**
     * API 호출 성공 로그
     */
    public void logApiSuccess(String apiName, String detail, String className, 
                             String methodName, String empId, HttpServletRequest request) {
        String message = String.format("API 호출 성공: %s", apiName);
        systemLogService.logInfo("API", message, detail, className, methodName, empId, request);
    }
    
    /**
     * API 호출 실패 로그
     */
    public void logApiError(String apiName, String detail, String className, 
                           String methodName, String empId, String errorCode, 
                           Exception exception, HttpServletRequest request) {
        String message = String.format("API 호출 실패: %s", apiName);
        systemLogService.logError("API", message, detail, className, methodName, 
                                empId, errorCode, "API", exception, request);
    }
    
    /**
     * 데이터베이스 작업 로그
     */
    public void logDatabaseOperation(String operation, String detail, String className, 
                                   String methodName, String empId, HttpServletRequest request) {
        String message = String.format("데이터베이스 작업: %s", operation);
        systemLogService.logInfo("DATABASE", message, detail, className, methodName, empId, request);
    }
    
    /**
     * 데이터베이스 오류 로그
     */
    public void logDatabaseError(String operation, String detail, String className, 
                               String methodName, String empId, String errorCode, 
                               Exception exception, HttpServletRequest request) {
        String message = String.format("데이터베이스 오류: %s", operation);
        systemLogService.logError("DATABASE", message, detail, className, methodName, 
                                empId, errorCode, "DATABASE", exception, request);
    }
    
    /**
     * 보안 관련 로그
     */
    public void logSecurityEvent(String event, String detail, String className, 
                               String methodName, String empId, HttpServletRequest request) {
        String message = String.format("보안 이벤트: %s", event);
        systemLogService.logInfo("SECURITY", message, detail, className, methodName, empId, request);
    }
    
    /**
     * 보안 위반 로그
     */
    public void logSecurityViolation(String violation, String detail, String className, 
                                   String methodName, String empId, String errorCode, 
                                   HttpServletRequest request) {
        String message = String.format("보안 위반: %s", violation);
        systemLogService.logError("SECURITY", message, detail, className, methodName, 
                                empId, errorCode, "SECURITY", null, request);
    }
    
    /**
     * 메신저 전송 로그
     */
    public void logMessengerSend(String message, String detail, String className, 
                               String methodName, String empId, HttpServletRequest request) {
        systemLogService.logInfo("MESSENGER", message, detail, className, methodName, empId, request);
    }
    
    /**
     * 메신저 전송 실패 로그
     */
    public void logMessengerError(String message, String detail, String className, 
                                String methodName, String empId, String errorCode, 
                                Exception exception, HttpServletRequest request) {
        systemLogService.logError("MESSENGER", message, detail, className, methodName, 
                                empId, errorCode, "EXTERNAL_API", exception, request);
    }
    
    /**
     * 시스템 시작 로그
     */
    public void logSystemStart(String detail) {
        systemLogService.logInfo("SYSTEM", "시스템 시작", detail, 
                               "com.ERI.demo.EriApplication", "main", "SYSTEM", null);
    }
    
    /**
     * 시스템 종료 로그
     */
    public void logSystemShutdown(String detail) {
        systemLogService.logInfo("SYSTEM", "시스템 종료", detail, 
                               "com.ERI.demo.EriApplication", "shutdown", "SYSTEM", null);
    }
    
    /**
     * 성능 경고 로그
     */
    public void logPerformanceWarning(String operation, String detail, String className, 
                                    String methodName, String empId, HttpServletRequest request) {
        String message = String.format("성능 경고: %s", operation);
        systemLogService.logWarn("SYSTEM", message, detail, className, methodName, 
                               empId, "PERFORMANCE_WARNING", "PERFORMANCE", request);
    }
    
    /**
     * 메모리 사용량 로그
     */
    public void logMemoryUsage(String detail) {
        systemLogService.logDebug("SYSTEM", "메모리 사용량 확인", detail, 
                                "com.ERI.demo.util.SystemMonitor", "checkMemoryUsage", "SYSTEM", null);
    }
    
    /**
     * 사용자 로그인 로그
     */
    public void logUserLogin(String empId, String detail, HttpServletRequest request) {
        String message = String.format("사용자 로그인: %s", empId);
        systemLogService.logInfo("SECURITY", message, detail, 
                               "com.ERI.demo.service.AuthService", "authenticate", empId, request);
    }
    
    /**
     * 사용자 로그아웃 로그
     */
    public void logUserLogout(String empId, String detail, HttpServletRequest request) {
        String message = String.format("사용자 로그아웃: %s", empId);
        systemLogService.logInfo("SECURITY", message, detail, 
                               "com.ERI.demo.service.AuthService", "logout", empId, request);
    }
}