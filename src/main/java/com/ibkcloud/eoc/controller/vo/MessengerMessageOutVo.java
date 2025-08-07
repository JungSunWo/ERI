package com.ibkcloud.eoc.controller.vo;

import com.ibkcloud.eoc.cmm.dto.IbkCldEocDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class MessengerMessageOutVo extends IbkCldEocDto {
    
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
    
    /**
     * 응답 형태 구분
     */
    private String resType;
    
    /**
     * 쪽지 요청자 사번
     */
    private String userId;
    
    /**
     * 쪽지 대상자 사번 목록
     */
    private String rcvId;
    
    /**
     * 쪽지 내용
     */
    private String msg;
    
    /**
     * 메시지 구분 코드
     */
    private String msgType;
    
    /**
     * 시스템 고유 구분 코드
     */
    private String chnlTypeClcd;
} 