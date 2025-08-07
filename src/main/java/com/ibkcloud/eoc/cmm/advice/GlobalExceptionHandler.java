package com.ibkcloud.eoc.cmm.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.ibkcloud.eoc.cmm.exception.BizException;
import com.ibkcloud.eoc.cmm.exception.UnauthorizedException;
import com.ibkcloud.eoc.cmm.exception.AccessDeniedException;
import com.ibkcloud.eoc.cmm.dto.ErrorResponseDto;

import lombok.extern.slf4j.Slf4j;

/**
 * @파일명 : GlobalExceptionHandler
 * @논리명 : 전역 예외 처리
 * @작성자 : 시스템
 * @설명   : 애플리케이션에서 발생하는 예외를 전역적으로 처리하는 클래스
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 비즈니스 예외 처리
     */
    @ExceptionHandler(BizException.class)
    public ResponseEntity<ErrorResponseDto> handleBizException(BizException e) {
        log.error("BizException 발생: {}", e.getMessage(), e);
        ErrorResponseDto errorResponse = new ErrorResponseDto();
        errorResponse.setErrorCode(e.getErrorCode());
        errorResponse.setErrorMessage(e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    /**
     * 인증 예외 처리
     */
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ErrorResponseDto> handleUnauthorizedException(UnauthorizedException e) {
        log.error("UnauthorizedException 발생: {}", e.getMessage(), e);
        ErrorResponseDto errorResponse = new ErrorResponseDto();
        errorResponse.setErrorCode("AUE00000");
        errorResponse.setErrorMessage("인증이 필요합니다.");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
    }

    /**
     * 접근 거부 예외 처리
     */
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponseDto> handleAccessDeniedException(AccessDeniedException e) {
        log.error("AccessDeniedException 발생: {}", e.getMessage(), e);
        ErrorResponseDto errorResponse = new ErrorResponseDto();
        errorResponse.setErrorCode("ADE00000");
        errorResponse.setErrorMessage("접근 권한이 없습니다.");
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse);
    }

    /**
     * 일반 예외 처리
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleException(Exception e) {
        log.error("Exception 발생: {}", e.getMessage(), e);
        ErrorResponseDto errorResponse = new ErrorResponseDto();
        errorResponse.setErrorCode("COE00000");
        errorResponse.setErrorMessage("오류가 발생했습니다. 잠시 후 다시 시도해주세요.");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
} 