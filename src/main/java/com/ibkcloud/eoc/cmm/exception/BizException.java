package com.ibkcloud.eoc.cmm.exception;

/**
 * @파일명 : BizException
 * @논리명 : 비즈니스 예외
 * @작성자 : 시스템
 * @설명   : 비즈니스 로직에서 발생하는 예외를 처리하는 클래스
 */
public class BizException extends RuntimeException {
    
    private String errorCode;
    private Object[] parameters;
    
    public BizException(String errorCode) {
        super(errorCode);
        this.errorCode = errorCode;
    }
    
    public BizException(String errorCode, Object[] parameters) {
        super(errorCode);
        this.errorCode = errorCode;
        this.parameters = parameters;
    }
    
    public BizException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }
    
    public BizException(String errorCode, String message, Object[] parameters) {
        super(message);
        this.errorCode = errorCode;
        this.parameters = parameters;
    }
    
    public String getErrorCode() {
        return errorCode;
    }
    
    public Object[] getParameters() {
        return parameters;
    }
} 