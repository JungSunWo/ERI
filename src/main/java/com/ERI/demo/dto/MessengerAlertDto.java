package com.ERI.demo.dto;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * 메신저 알람 전송 DTO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessengerAlertDto {
    
    /**
     * 발송서버코드 [필수]
     * Web API 전송 권한 확인을 위한 고유 번호 (UC 관리자 확인 필요)
     */
    private String srvCode;
    
    /**
     * 수신자리스트 [필수]
     * 알람 수신자의 메신저 사번
     * 다중 수신 시 쉼표로 구분 (예: "사번, 사번, 사번")
     */
    private String recipient;
    
    /**
     * 발신자사번
     * 알람 발신자의 메신저 사번
     */
    private String send;
    
    /**
     * 발신자명
     * 알람 발신자 이름
     */
    private String senderAlias;
    
    /**
     * 제목
     * 알람 제목 (최대 11자)
     */
    private String title;
    
    /**
     * 본문
     * 알람에 전송할 메시지 내용 (최대 36자, 줄바꿈 미적용)
     */
    private String body;
    
    /**
     * 링크 텍스트
     * 알람 URL의 텍스트(제목)
     */
    private String linkTxt;
    
    /**
     * 링크 URL
     * 알람 클릭 시 열릴 웹페이지 URL
     */
    private String linkUrl;
    
    /**
     * 팝업창 출력 여부
     * 새 메신저에서 사용하지 않음
     */
    private String popup;
    
    /**
     * 발송서버명
     * 새 메신저에서 사용하지 않음
     */
    private String svrName;
}