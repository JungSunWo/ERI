package com.ibkcloud.eoc.cmm.exception;

/**
 * @파일명 : AccessDeniedException
 * @논리명 : 접근 거부 예외
 * @작성자 : 시스템
 * @설명   : 접근 권한이 없는 경우 발생하는 예외 클래스
 */
public class AccessDeniedException extends RuntimeException {
    
    private String errorCode;
    
    public AccessDeniedException(String errorCode) {
        super(errorCode);
        this.errorCode = errorCode;
    }
    
    public AccessDeniedException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }
    
    public String getErrorCode() {
        return errorCode;
    }
} 