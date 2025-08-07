package com.ibkcloud.eoc.cmm.exception;

/**
 * @파일명 : UnauthorizedException
 * @논리명 : 인증 예외
 * @작성자 : 시스템
 * @설명   : 인증이 필요한 경우 발생하는 예외 클래스
 */
public class UnauthorizedException extends RuntimeException {
    
    private String errorCode;
    
    public UnauthorizedException(String errorCode) {
        super(errorCode);
        this.errorCode = errorCode;
    }
    
    public UnauthorizedException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }
    
    public String getErrorCode() {
        return errorCode;
    }
} 