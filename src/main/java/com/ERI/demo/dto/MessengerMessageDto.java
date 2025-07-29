package com.ERI.demo.dto;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * 메신저 쪽지 전송 DTO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessengerMessageDto {
    
    /**
     * 응답 형태 구분
     * JSON 입력 시 JSON으로 반환
     */
    private String resType;
    
    /**
     * 쪽지 요청자 사번 [필수]
     * 메신저 사번
     */
    private String userId;
    
    /**
     * 쪽지 대상자 사번 목록 [필수]
     * 메신저 수신자 사번 목록
     * 다중 수신 시 쉼표로 구분 (예: "사번, 사번, 사번")
     */
    private String rcvId;
    
    /**
     * 쪽지 내용 [필수]
     * 쪽지 및 공지에 전송할 메시지 내용
     */
    private String msg;
    
    /**
     * 메시지 구분 코드 [필수]
     * 1: 쪽지, 2: 공지
     * 빈 값 시 기본값 1 (쪽지)
     */
    private String msgType;
    
    /**
     * 시스템 고유 구분 코드 [필수]
     * Web API 전송 권한 확인을 위한 고유 번호 (UC 관리자 확인 필요)
     */
    private String chnlTypeClcd;
}