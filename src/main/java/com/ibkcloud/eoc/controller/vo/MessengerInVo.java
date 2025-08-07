package com.ibkcloud.eoc.controller.vo;

import com.ibkcloud.eoc.cmm.dto.IbkCldEocDto;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class MessengerInVo extends IbkCldEocDto {
    
    // 기본 정보
    private Long messengerSeq;                  // 메신저 일련번호
    
    @Size(max = 20, message = "발신자 ID는 20자를 초과할 수 없습니다.")
    private String sndId;                       // 발신자 ID
    
    @Size(max = 20, message = "수신자 ID는 20자를 초과할 수 없습니다.")
    private String rcvId;                       // 수신자 ID
    
    @Size(max = 500, message = "수신자 목록은 500자를 초과할 수 없습니다.")
    private String recipient;                   // 수신자 목록 (콤마로 구분)
    
    @Size(max = 200, message = "제목은 200자를 초과할 수 없습니다.")
    private String title;                       // 제목
    
    @Size(max = 2000, message = "내용은 2000자를 초과할 수 없습니다.")
    private String content;                     // 내용
    
    @Size(max = 20, message = "메시지 타입은 20자를 초과할 수 없습니다.")
    private String messageType;                 // 메시지 타입 (ALERT, MESSAGE)
    
    @Size(max = 20, message = "우선순위는 20자를 초과할 수 없습니다.")
    private String priority;                    // 우선순위 (HIGH, NORMAL, LOW)
    
    @Size(max = 10, message = "상태 코드는 10자를 초과할 수 없습니다.")
    private String stsCd;                       // 상태 코드 (STS001: 전송대기, STS002: 전송완료, STS003: 전송실패)
    
    private LocalDateTime sendDate;             // 전송일시
    private LocalDateTime readDate;             // 읽음일시
    
    // 등록/수정 정보
    private LocalDateTime regDate;              // 등록일시
    private LocalDateTime updDate;              // 수정일시
    
    // 추가 정보
    private String empId;                       // 직원 ID
    private String errorMessage;                // 에러 메시지
    private String code;                        // 응답 코드
    private Boolean isAsync;                    // 비동기 전송 여부
}
