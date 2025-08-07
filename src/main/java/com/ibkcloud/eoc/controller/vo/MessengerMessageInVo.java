package com.ibkcloud.eoc.controller.vo;

import com.ibkcloud.eoc.cmm.dto.IbkCldEocDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class MessengerMessageInVo extends IbkCldEocDto {
    
    /**
     * 응답 형태 구분
     * JSON 입력 시 JSON으로 반환
     */
    @Size(max = 10, message = "응답 형태 구분은 10자를 초과할 수 없습니다.")
    private String resType;
    
    /**
     * 쪽지 요청자 사번 [필수]
     * 메신저 사번
     */
    @NotBlank(message = "쪽지 요청자 사번은 필수입니다.")
    @Size(max = 20, message = "쪽지 요청자 사번은 20자를 초과할 수 없습니다.")
    private String userId;
    
    /**
     * 쪽지 대상자 사번 목록 [필수]
     * 메신저 수신자 사번 목록
     * 다중 수신 시 쉼표로 구분 (예: "사번, 사번, 사번")
     */
    @NotBlank(message = "쪽지 대상자 사번 목록은 필수입니다.")
    @Size(max = 1000, message = "쪽지 대상자 사번 목록은 1000자를 초과할 수 없습니다.")
    private String rcvId;
    
    /**
     * 쪽지 내용 [필수]
     * 쪽지 및 공지에 전송할 메시지 내용
     */
    @NotBlank(message = "쪽지 내용은 필수입니다.")
    @Size(max = 2000, message = "쪽지 내용은 2000자를 초과할 수 없습니다.")
    private String msg;
    
    /**
     * 메시지 구분 코드 [필수]
     * 1: 쪽지, 2: 공지
     * 빈 값 시 기본값 1 (쪽지)
     */
    @NotBlank(message = "메시지 구분 코드는 필수입니다.")
    @Size(max = 1, message = "메시지 구분 코드는 1자를 초과할 수 없습니다.")
    private String msgType;
    
    /**
     * 시스템 고유 구분 코드 [필수]
     * Web API 전송 권한 확인을 위한 고유 번호 (UC 관리자 확인 필요)
     */
    @NotBlank(message = "시스템 고유 구분 코드는 필수입니다.")
    @Size(max = 50, message = "시스템 고유 구분 코드는 50자를 초과할 수 없습니다.")
    private String chnlTypeClcd;
} 