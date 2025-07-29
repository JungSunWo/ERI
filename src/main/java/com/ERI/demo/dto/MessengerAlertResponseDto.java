package com.ERI.demo.dto;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * 메신저 알람 전송 응답 DTO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessengerAlertResponseDto {
    
    /**
     * 성공 여부
     */
    private boolean success;
    
    /**
     * 응답 메시지
     */
    private String message;
    
    /**
     * 응답 코드
     */
    private String code;
    
    /**
     * 전송된 알람 수
     */
    private Integer sentCount;
    
    /**
     * 실패한 알람 수
     */
    private Integer failedCount;
    
    /**
     * 전체 수신자 수
     */
    private Integer totalRecipients;
}