package com.ERI.demo.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 권한 체크를 위한 커스텀 어노테이션
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface RequireAuth {
    
    /**
     * 메뉴 코드
     */
    String menuCode();
    
    /**
     * 권한 코드 (READ, CREATE, UPDATE, DELETE, ALL)
     */
    String authCode() default "READ";
    
    /**
     * 관리자 권한 필요 여부
     */
    boolean requireAdmin() default false;
    
    /**
     * 슈퍼 관리자 권한 필요 여부
     */
    boolean requireSuperAdmin() default false;
} 