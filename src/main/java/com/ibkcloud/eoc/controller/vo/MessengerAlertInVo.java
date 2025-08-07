package com.ibkcloud.eoc.controller.vo;

import com.ibkcloud.eoc.cmm.dto.IbkCldEocDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class MessengerAlertInVo extends IbkCldEocDto {
    
    /**
     * 발송서버코드 [필수]
     * Web API 전송 권한 확인을 위한 고유 번호 (UC 관리자 확인 필요)
     */
    @NotBlank(message = "발송서버코드는 필수입니다.")
    @Size(max = 50, message = "발송서버코드는 50자를 초과할 수 없습니다.")
    private String srvCode;
    
    /**
     * 수신자리스트 [필수]
     * 알람 수신자의 메신저 사번
     * 다중 수신 시 쉼표로 구분 (예: "사번, 사번, 사번")
     */
    @NotBlank(message = "수신자리스트는 필수입니다.")
    @Size(max = 1000, message = "수신자리스트는 1000자를 초과할 수 없습니다.")
    private String recipient;
    
    /**
     * 발신자사번
     * 알람 발신자의 메신저 사번
     */
    @Size(max = 20, message = "발신자사번은 20자를 초과할 수 없습니다.")
    private String send;
    
    /**
     * 발신자명
     * 알람 발신자 이름
     */
    @Size(max = 50, message = "발신자명은 50자를 초과할 수 없습니다.")
    private String senderAlias;
    
    /**
     * 제목
     * 알람 제목 (최대 11자)
     */
    @Size(max = 11, message = "제목은 11자를 초과할 수 없습니다.")
    private String title;
    
    /**
     * 본문
     * 알람에 전송할 메시지 내용 (최대 36자, 줄바꿈 미적용)
     */
    @Size(max = 36, message = "본문은 36자를 초과할 수 없습니다.")
    private String body;
    
    /**
     * 링크 텍스트
     * 알람 URL의 텍스트(제목)
     */
    @Size(max = 100, message = "링크 텍스트는 100자를 초과할 수 없습니다.")
    private String linkTxt;
    
    /**
     * 링크 URL
     * 알람 클릭 시 열릴 웹페이지 URL
     */
    @Size(max = 500, message = "링크 URL은 500자를 초과할 수 없습니다.")
    private String linkUrl;
    
    /**
     * 팝업창 출력 여부
     * 새 메신저에서 사용하지 않음
     */
    @Size(max = 1, message = "팝업창 출력 여부는 1자를 초과할 수 없습니다.")
    private String popup;
    
    /**
     * 발송서버명
     * 새 메신저에서 사용하지 않음
     */
    @Size(max = 50, message = "발송서버명은 50자를 초과할 수 없습니다.")
    private String svrName;
} 