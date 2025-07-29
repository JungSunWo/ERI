package com.ERI.demo.exception;

import com.ERI.demo.dto.ErrorResponseDto;
import com.ERI.demo.service.SystemLogService;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.context.request.async.AsyncRequestNotUsableException;
import org.apache.catalina.connector.ClientAbortException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 글로벌 예외 처리
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    
    private final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());
    private final SystemLogService systemLogService;
    
    public GlobalExceptionHandler(SystemLogService systemLogService) {
        this.systemLogService = systemLogService;
    }
    
    /**
     * 미디어 타입 불일치 예외 처리
     */
    @ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
    public ResponseEntity<ErrorResponseDto> handleHttpMediaTypeNotAcceptable(HttpMediaTypeNotAcceptableException ex, HttpServletRequest request) {
        String errorCode = "MEDIA_TYPE_NOT_ACCEPTABLE";
        String message = "요청한 응답 형식을 지원하지 않습니다.";
        
        // 시스템 로그 기록
        systemLogService.logError("API", message, ex.getMessage(), 
                                this.getClass().getName(), "handleHttpMediaTypeNotAcceptable", 
                                getEmpIdFromRequest(request), errorCode, "API", ex, request);
        
        ErrorResponseDto errorResponse = new ErrorResponseDto(errorCode, message, 406, request.getRequestURI());
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
            .contentType(MediaType.APPLICATION_JSON)
            .body(errorResponse);
    }
    
    /**
     * 접근 거부 예외 처리
     */
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponseDto> handleAccessDenied(AccessDeniedException ex, HttpServletRequest request) {
        String errorCode = "ACCESS_DENIED";
        String message = "접근 권한이 없습니다.";
        
        // 시스템 로그 기록
        systemLogService.logError("SECURITY", message, ex.getMessage(), 
                                this.getClass().getName(), "handleAccessDenied", 
                                getEmpIdFromRequest(request), errorCode, "SECURITY", ex, request);
        
        ErrorResponseDto errorResponse = ErrorResponseDto.accessDenied(request.getRequestURI());
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
            .contentType(MediaType.APPLICATION_JSON)
            .body(errorResponse);
    }
    
    /**
     * 인증 실패 예외 처리
     */
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ErrorResponseDto> handleUnauthorized(UnauthorizedException ex, HttpServletRequest request) {
        String errorCode = "UNAUTHORIZED";
        String message = "인증이 필요합니다.";
        
        // 시스템 로그 기록
        systemLogService.logError("SECURITY", message, ex.getMessage(), 
                                this.getClass().getName(), "handleUnauthorized", 
                                getEmpIdFromRequest(request), errorCode, "SECURITY", ex, request);
        
        ErrorResponseDto errorResponse = ErrorResponseDto.unauthorized(request.getRequestURI());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
            .contentType(MediaType.APPLICATION_JSON)
            .body(errorResponse);
    }
    
    /**
     * 잘못된 요청 예외 처리
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponseDto> handleIllegalArgumentException(IllegalArgumentException ex, HttpServletRequest request) {
        String errorCode = "ILLEGAL_ARGUMENT";
        String message = ex.getMessage();
        
        // 시스템 로그 기록
        systemLogService.logError("API", message, ex.getMessage(), 
                                this.getClass().getName(), "handleIllegalArgumentException", 
                                getEmpIdFromRequest(request), errorCode, "API", ex, request);
        
        ErrorResponseDto errorResponse = ErrorResponseDto.badRequest(message, request.getRequestURI());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .contentType(MediaType.APPLICATION_JSON)
            .body(errorResponse);
    }
    
    /**
     * 필수 파라미터 누락 예외 처리
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorResponseDto> handleMissingParameter(MissingServletRequestParameterException ex, HttpServletRequest request) {
        String errorCode = "MISSING_PARAMETER";
        String message = "필수 파라미터가 누락되었습니다: " + ex.getParameterName();
        
        // 시스템 로그 기록
        systemLogService.logError("API", message, ex.getMessage(), 
                                this.getClass().getName(), "handleMissingParameter", 
                                getEmpIdFromRequest(request), errorCode, "API", ex, request);
        
        ErrorResponseDto errorResponse = ErrorResponseDto.badRequest(message, request.getRequestURI());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .contentType(MediaType.APPLICATION_JSON)
            .body(errorResponse);
    }
    
    /**
     * 파라미터 타입 불일치 예외 처리
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponseDto> handleTypeMismatch(MethodArgumentTypeMismatchException ex, HttpServletRequest request) {
        String errorCode = "TYPE_MISMATCH";
        String message = "파라미터 타입이 올바르지 않습니다: " + ex.getName() + " (예상 타입: " + ex.getRequiredType().getSimpleName() + ")";
        
        // 시스템 로그 기록
        systemLogService.logError("API", message, ex.getMessage(), 
                                this.getClass().getName(), "handleTypeMismatch", 
                                getEmpIdFromRequest(request), errorCode, "API", ex, request);
        
        ErrorResponseDto errorResponse = ErrorResponseDto.badRequest(message, request.getRequestURI());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .contentType(MediaType.APPLICATION_JSON)
            .body(errorResponse);
    }
    
    /**
     * 유효성 검증 실패 예외 처리 (@Valid)
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDto> handleValidationException(MethodArgumentNotValidException ex, HttpServletRequest request) {
        String errorCode = "VALIDATION_ERROR";
        String message = "입력 데이터가 유효하지 않습니다.";
        
        Map<String, String> fieldErrors = ex.getBindingResult().getFieldErrors().stream()
                .collect(Collectors.toMap(
                    FieldError::getField,
                    FieldError::getDefaultMessage,
                    (existing, replacement) -> existing
                ));
        
        // 시스템 로그 기록
        systemLogService.logError("API", message, "유효성 검증 실패: " + fieldErrors.toString(), 
                                this.getClass().getName(), "handleValidationException", 
                                getEmpIdFromRequest(request), errorCode, "API", ex, request);
        
        ErrorResponseDto errorResponse = ErrorResponseDto.validationError(message, request.getRequestURI(), fieldErrors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .contentType(MediaType.APPLICATION_JSON)
            .body(errorResponse);
    }
    
    /**
     * 바인딩 예외 처리
     */
    @ExceptionHandler(BindException.class)
    public ResponseEntity<ErrorResponseDto> handleBindException(BindException ex, HttpServletRequest request) {
        String errorCode = "BIND_ERROR";
        String message = "데이터 바인딩에 실패했습니다.";
        
        Map<String, String> fieldErrors = ex.getBindingResult().getFieldErrors().stream()
                .collect(Collectors.toMap(
                    FieldError::getField,
                    FieldError::getDefaultMessage,
                    (existing, replacement) -> existing
                ));
        
        // 시스템 로그 기록
        systemLogService.logError("API", message, "바인딩 실패: " + fieldErrors.toString(), 
                                this.getClass().getName(), "handleBindException", 
                                getEmpIdFromRequest(request), errorCode, "API", ex, request);
        
        ErrorResponseDto errorResponse = ErrorResponseDto.validationError(message, request.getRequestURI(), fieldErrors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .contentType(MediaType.APPLICATION_JSON)
            .body(errorResponse);
    }
    
    /**
     * 리소스 없음 예외 처리
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleNoHandlerFound(NoHandlerFoundException ex, HttpServletRequest request) {
        String errorCode = "NOT_FOUND";
        String message = "요청한 리소스를 찾을 수 없습니다: " + ex.getRequestURL();
        
        // 시스템 로그 기록
        systemLogService.logError("API", message, ex.getMessage(), 
                                this.getClass().getName(), "handleNoHandlerFound", 
                                getEmpIdFromRequest(request), errorCode, "API", ex, request);
        
        ErrorResponseDto errorResponse = ErrorResponseDto.notFound(message, request.getRequestURI());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .contentType(MediaType.APPLICATION_JSON)
            .body(errorResponse);
    }
    
    /**
     * 데이터베이스 관련 예외 처리
     */
    @ExceptionHandler(org.springframework.dao.DataAccessException.class)
    public ResponseEntity<ErrorResponseDto> handleDataAccessException(org.springframework.dao.DataAccessException ex, HttpServletRequest request) {
        String errorCode = "DATABASE_ERROR";
        String message = "데이터베이스 처리 중 오류가 발생했습니다.";
        
        // 시스템 로그 기록
        systemLogService.logError("DATABASE", message, ex.getMessage(), 
                                this.getClass().getName(), "handleDataAccessException", 
                                getEmpIdFromRequest(request), errorCode, "DATABASE", ex, request);
        
        ErrorResponseDto errorResponse = new ErrorResponseDto(errorCode, message, 500, request.getRequestURI());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .contentType(MediaType.APPLICATION_JSON)
            .body(errorResponse);
    }
    
    /**
     * MyBatis 관련 예외 처리
     */
    @ExceptionHandler(org.apache.ibatis.exceptions.PersistenceException.class)
    public ResponseEntity<ErrorResponseDto> handlePersistenceException(org.apache.ibatis.exceptions.PersistenceException ex, HttpServletRequest request) {
        String errorCode = "DATABASE_ERROR";
        String message = "데이터베이스 쿼리 실행 중 오류가 발생했습니다.";
        
        // 시스템 로그 기록
        systemLogService.logError("DATABASE", message, ex.getMessage(), 
                                this.getClass().getName(), "handlePersistenceException", 
                                getEmpIdFromRequest(request), errorCode, "DATABASE", ex, request);
        
        ErrorResponseDto errorResponse = new ErrorResponseDto(errorCode, message, 500, request.getRequestURI());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .contentType(MediaType.APPLICATION_JSON)
            .body(errorResponse);
    }
    
    /**
     * NullPointerException 처리
     */
    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<ErrorResponseDto> handleNullPointerException(NullPointerException ex, HttpServletRequest request) {
        String errorCode = "NULL_POINTER_ERROR";
        String message = "필수 데이터가 누락되었습니다.";
        
        // 시스템 로그 기록
        systemLogService.logError("SYSTEM", message, ex.getMessage(), 
                                this.getClass().getName(), "handleNullPointerException", 
                                getEmpIdFromRequest(request), errorCode, "SYSTEM", ex, request);
        
        ErrorResponseDto errorResponse = new ErrorResponseDto(errorCode, message, 500, request.getRequestURI());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .contentType(MediaType.APPLICATION_JSON)
            .body(errorResponse);
    }
    
    /**
     * 일반적인 런타임 예외 처리
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponseDto> handleRuntimeException(RuntimeException ex, HttpServletRequest request) {
        String errorCode = "RUNTIME_ERROR";
        String message = "시스템 오류가 발생했습니다.";
        
        // 시스템 로그 기록
        systemLogService.logError("SYSTEM", message, ex.getMessage(), 
                                this.getClass().getName(), "handleRuntimeException", 
                                getEmpIdFromRequest(request), errorCode, "SYSTEM", ex, request);
        
        ErrorResponseDto errorResponse = ErrorResponseDto.internalServerError(request.getRequestURI());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .contentType(MediaType.APPLICATION_JSON)
            .body(errorResponse);
    }
    
    /**
     * 클라이언트 연결 중단 예외 처리 (파일 스트리밍 등에서 발생)
     */
    @ExceptionHandler({AsyncRequestNotUsableException.class, ClientAbortException.class})
    public ResponseEntity<Void> handleClientAbortException(Exception e, HttpServletRequest request) {
        // 클라이언트 연결 중단은 정상적인 상황이므로 로그만 출력하고 응답하지 않음
        log.debug("클라이언트 연결 중단: {} {} - {}", request.getMethod(), request.getRequestURI(), e.getMessage());
        
        // 시스템 로그 기록 (WARN 레벨)
        systemLogService.logWarn("API", "클라이언트 연결 중단", e.getMessage(), 
                               this.getClass().getName(), "handleClientAbortException", 
                               getEmpIdFromRequest(request), "CLIENT_ABORT", "API", request);
        
        return ResponseEntity.ok().build();
    }

    /**
     * 기타 모든 예외 처리
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleException(Exception e, HttpServletRequest request) {
        String errorCode = "UNEXPECTED_ERROR";
        String message = "예상치 못한 오류가 발생했습니다.";
        
        log.error("예상치 못한 오류 발생: {} {}", request.getMethod(), request.getRequestURI(), e);
        
        // 시스템 로그 기록
        systemLogService.logError("SYSTEM", message, e.getMessage(), 
                                this.getClass().getName(), "handleException", 
                                getEmpIdFromRequest(request), errorCode, "SYSTEM", e, request);
        
        ErrorResponseDto errorResponse = ErrorResponseDto.internalServerError(request.getRequestURI());
        
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
    
    /**
     * 요청에서 사용자 사번 추출
     */
    private String getEmpIdFromRequest(HttpServletRequest request) {
        try {
            // 세션에서 사용자 정보 추출
            Object sessionUser = request.getSession().getAttribute("user");
            if (sessionUser != null && sessionUser instanceof Map) {
                Map<String, Object> userMap = (Map<String, Object>) sessionUser;
                return (String) userMap.get("empId");
            }
            
            // 헤더에서 사용자 정보 추출
            String empId = request.getHeader("X-User-Id");
            if (empId != null && !empId.isEmpty()) {
                return empId;
            }
            
            // Authorization 헤더에서 추출 (Bearer 토큰 등)
            String authHeader = request.getHeader("Authorization");
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                // 토큰에서 사용자 정보 추출 로직 (실제 구현에 따라 다름)
                return "UNKNOWN";
            }
            
            return "UNKNOWN";
        } catch (Exception ex) {
            log.warn("사용자 사번 추출 실패: {}", ex.getMessage());
            return "UNKNOWN";
        }
    }
} 