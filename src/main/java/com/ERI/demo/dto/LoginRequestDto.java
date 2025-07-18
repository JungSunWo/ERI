package com.ERI.demo.dto;

import lombok.Data;

/**
 * 로그인 요청 DTO
 */
@Data
public class LoginRequestDto {
    
    /**
     * 사용자 ID (직원번호)
     */
    private String userId;
    
    /**
     * 비밀번호
     */
    private String password;
} 