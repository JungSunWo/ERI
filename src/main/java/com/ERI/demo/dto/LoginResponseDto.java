package com.ERI.demo.dto;

import lombok.Data;
import java.util.List;
import java.util.Map;

/**
 * 로그인 응답 DTO
 */
@Data
public class LoginResponseDto {
    
    /**
     * 로그인 성공 여부
     */
    private boolean success;
    
    /**
     * 사용자 ID
     */
    private String userId;
    
    /**
     * 사용자 이름
     */
    private String userName;
    
    /**
     * 메시지
     */
    private String message;
    
    /**
     * 권한 그룹 목록
     */
    private List<String> authGroups;
    
    /**
     * 직원 캐시 (ID -> 이름 매핑)
     */
    private Map<String, String> employeeCache;
    
    /**
     * 성공 응답 생성
     */
    public static LoginResponseDto success(String userId, String userName, List<String> authGroups, Map<String, String> employeeCache) {
        LoginResponseDto response = new LoginResponseDto();
        response.success = true;
        response.userId = userId;
        response.userName = userName;
        response.message = "로그인이 성공했습니다.";
        response.authGroups = authGroups;
        response.employeeCache = employeeCache;
        return response;
    }
    
    /**
     * 실패 응답 생성
     */
    public static LoginResponseDto failure(String message) {
        LoginResponseDto response = new LoginResponseDto();
        response.success = false;
        response.message = message;
        return response;
    }
} 