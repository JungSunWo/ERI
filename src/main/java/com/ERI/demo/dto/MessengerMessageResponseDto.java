package com.ERI.demo.dto;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * 메신저 쪽지 전송 응답 DTO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessengerMessageResponseDto {
    
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
     * 전송된 쪽지 수
     */
    private Integer sentCount;
    
    /**
     * 실패한 쪽지 수
     */
    private Integer failedCount;
    
    /**
     * 전체 수신자 수
     */
    private Integer totalRecipients;
    
    /**
     * 메시지 타입 (1: 쪽지, 2: 공지)
     */
    private String messageType;
    
    /**
     * 발신자 사번
     */
    private String senderId;
}