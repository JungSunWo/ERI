package com.ERI.demo.service;

import com.ERI.demo.mappers.SystemLogMapper;
import com.ERI.demo.vo.SystemLogVo;
import com.ERI.demo.vo.SystemLogSearchVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * 시스템 로그 서비스
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SystemLogService {
    
    private final SystemLogMapper systemLogMapper;
    
    /**
     * 시스템 로그 기록
     */
    @Transactional
    public void logSystemEvent(String logLevel, String logType, String message, String detail, 
                              String className, String methodName, Integer lineNumber, 
                              String stackTrace, String empId, String errorCode, 
                              String errorCategory, HttpServletRequest request) {
        try {
            SystemLogVo logVo = SystemLogVo.builder()
                    .logLevel(logLevel)
                    .logType(logType)
                    .logMessage(message)
                    .logDetail(detail)
                    .className(className)
                    .methodName(methodName)
                    .lineNumber(lineNumber)
                    .stackTrace(stackTrace)
                    .empId(empId)
                    .sessionId(getSessionId(request))
                    .requestUri(getRequestUri(request))
                    .requestMethod(getRequestMethod(request))
                    .requestParams(getRequestParams(request))
                    .ipAddress(getClientIpAddress(request))
                    .userAgent(getUserAgent(request))
                    .errorCode(errorCode)
                    .errorCategory(errorCategory)
                    .createdDate(LocalDateTime.now())
                    .createdBy(empId != null ? empId : "SYSTEM")
                    .build();
            
            systemLogMapper.insertSystemLog(logVo);
        } catch (Exception e) {
            log.error("시스템 로그 기록 중 오류 발생: {}", e.getMessage(), e);
        }
    }
    
    /**
     * INFO 레벨 로그 기록
     */
    public void logInfo(String logType, String message, String detail, String className, 
                       String methodName, String empId, HttpServletRequest request) {
        logSystemEvent("INFO", logType, message, detail, className, methodName, null, 
                      null, empId, null, null, request);
    }
    
    /**
     * WARN 레벨 로그 기록
     */
    public void logWarn(String logType, String message, String detail, String className, 
                       String methodName, String empId, String errorCode, 
                       String errorCategory, HttpServletRequest request) {
        logSystemEvent("WARN", logType, message, detail, className, methodName, null, 
                      null, empId, errorCode, errorCategory, request);
    }
    
    /**
     * ERROR 레벨 로그 기록
     */
    public void logError(String logType, String message, String detail, String className, 
                        String methodName, String empId, String errorCode, 
                        String errorCategory, Exception exception, HttpServletRequest request) {
        String stackTrace = null;
        Integer lineNumber = null;
        
        if (exception != null) {
            stackTrace = getStackTrace(exception);
            lineNumber = getLineNumber(exception);
        }
        
        logSystemEvent("ERROR", logType, message, detail, className, methodName, lineNumber, 
                      stackTrace, empId, errorCode, errorCategory, request);
    }
    
    /**
     * DEBUG 레벨 로그 기록
     */
    public void logDebug(String logType, String message, String detail, String className, 
                        String methodName, String empId, HttpServletRequest request) {
        logSystemEvent("DEBUG", logType, message, detail, className, methodName, null, 
                      null, empId, null, null, request);
    }
    
    /**
     * 시스템 로그 조회 (페이징)
     */
    public Map<String, Object> getSystemLogs(SystemLogSearchVo searchVo) {
        searchVo.calculatePaging();
        
        List<SystemLogVo> logs = systemLogMapper.selectSystemLogs(searchVo);
        int totalCount = systemLogMapper.countSystemLogs(searchVo);
        
        return Map.of(
            "logs", logs,
            "totalCount", totalCount,
            "page", searchVo.getPage(),
            "pageSize", searchVo.getPageSize(),
            "totalPages", (int) Math.ceil((double) totalCount / searchVo.getPageSize())
        );
    }
    
    /**
     * 에러 로그 통계 조회
     */
    public List<Map<String, Object>> getErrorLogStats(String startDate, String endDate) {
        return systemLogMapper.selectErrorLogStats(startDate, endDate);
    }
    
    /**
     * 로그 레벨별 통계
     */
    public List<Map<String, Object>> getLogLevelStats() {
        return systemLogMapper.selectLogLevelStats();
    }
    
    /**
     * 로그 타입별 통계
     */
    public List<Map<String, Object>> getLogTypeStats() {
        return systemLogMapper.selectLogTypeStats();
    }
    
    /**
     * 시스템 로그 통계 뷰 조회
     */
    public List<Map<String, Object>> getSystemLogStatsView() {
        return systemLogMapper.selectSystemLogStatsView();
    }
    
    /**
     * 특정 기간 로그 통계
     */
    public List<Map<String, Object>> getLogStatsByPeriod(String startDate, String endDate) {
        return systemLogMapper.selectLogStatsByPeriod(startDate, endDate);
    }
    
    /**
     * 오래된 로그 삭제
     */
    @Transactional
    public int deleteOldLogs(int days) {
        return systemLogMapper.deleteOldLogs(days);
    }
    
    /**
     * 특정 로그 삭제
     */
    @Transactional
    public int deleteSystemLog(Long logSeq) {
        return systemLogMapper.deleteSystemLog(logSeq);
    }
    
    // ===== 유틸리티 메서드 =====
    
    private String getSessionId(HttpServletRequest request) {
        if (request == null) return null;
        return request.getSession() != null ? request.getSession().getId() : null;
    }
    
    private String getRequestUri(HttpServletRequest request) {
        if (request == null) return null;
        return request.getRequestURI();
    }
    
    private String getRequestMethod(HttpServletRequest request) {
        if (request == null) return null;
        return request.getMethod();
    }
    
    private String getRequestParams(HttpServletRequest request) {
        if (request == null) return null;
        return request.getQueryString();
    }
    
    private String getClientIpAddress(HttpServletRequest request) {
        if (request == null) return null;
        
        String xForwardedFor = request.getHeader("X-Forwarded-For");
        if (xForwardedFor != null && !xForwardedFor.isEmpty()) {
            return xForwardedFor.split(",")[0].trim();
        }
        
        String xRealIp = request.getHeader("X-Real-IP");
        if (xRealIp != null && !xRealIp.isEmpty()) {
            return xRealIp;
        }
        
        return request.getRemoteAddr();
    }
    
    private String getUserAgent(HttpServletRequest request) {
        if (request == null) return null;
        return request.getHeader("User-Agent");
    }
    
    private String getStackTrace(Exception exception) {
        if (exception == null) return null;
        
        StringBuilder sb = new StringBuilder();
        sb.append(exception.toString()).append("\n");
        
        for (StackTraceElement element : exception.getStackTrace()) {
            sb.append("\tat ").append(element.toString()).append("\n");
        }
        
        return sb.toString();
    }
    
    private Integer getLineNumber(Exception exception) {
        if (exception == null || exception.getStackTrace().length == 0) return null;
        return exception.getStackTrace()[0].getLineNumber();
    }
}