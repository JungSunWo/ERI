package com.ERI.demo.exception;

/**
 * 접근 거부 예외
 */
public class AccessDeniedException extends RuntimeException {
    
    public AccessDeniedException(String message) {
        super(message);
    }
    
    public AccessDeniedException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public AccessDeniedException(String menuCode, String authCode) {
        super(String.format("메뉴 '%s'에 대한 '%s' 권한이 없습니다.", menuCode, authCode));
    }
} 