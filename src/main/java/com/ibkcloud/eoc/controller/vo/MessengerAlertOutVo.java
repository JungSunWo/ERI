package com.ibkcloud.eoc.controller.vo;

import com.ibkcloud.eoc.cmm.dto.IbkCldEocDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class MessengerAlertOutVo extends IbkCldEocDto {
    
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
    
    /**
     * 발송서버코드
     */
    private String srvCode;
    
    /**
     * 수신자리스트
     */
    private String recipient;
    
    /**
     * 발신자사번
     */
    private String send;
    
    /**
     * 발신자명
     */
    private String senderAlias;
    
    /**
     * 제목
     */
    private String title;
    
    /**
     * 본문
     */
    private String body;
    
    /**
     * 링크 텍스트
     */
    private String linkTxt;
    
    /**
     * 링크 URL
     */
    private String linkUrl;
    
    /**
     * 팝업창 출력 여부
     */
    private String popup;
    
    /**
     * 발송서버명
     */
    private String svrName;
} 