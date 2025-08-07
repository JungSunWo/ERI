package com.ibkcloud.eoc.controller.vo;

import com.ibkcloud.eoc.cmm.dto.IbkCldEocDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
@EqualsAndHashCode(callSuper = true)
public class MessengerOutVo extends IbkCldEocDto {
    
    // 기본 정보
    private Long messengerSeq;                  // 메신저 일련번호
    private String sndId;                       // 발신자 ID
    private String rcvId;                       // 수신자 ID
    private String recipient;                   // 수신자 목록 (콤마로 구분)
    private String title;                       // 제목
    private String content;                     // 내용
    private String messageType;                 // 메시지 타입 (ALERT, MESSAGE)
    private String priority;                    // 우선순위 (HIGH, NORMAL, LOW)
    private String stsCd;                       // 상태 코드 (STS001: 전송대기, STS002: 전송완료, STS003: 전송실패)
    private LocalDateTime sendDate;             // 전송일시
    private LocalDateTime readDate;             // 읽음일시
    
    // 등록/수정 정보
    private LocalDateTime regDate;              // 등록일시
    private LocalDateTime updDate;              // 수정일시
    
    // 목록 조회용
    private List<MessengerOutVo> data;         // 메신저 목록
    private Integer count;                      // 총 개수
    
    // 상태 정보
    private Map<String, Object> status;         // 메신저 상태 정보
    
    // 추가 정보
    private String empId;                       // 직원 ID
    private String errorMessage;                // 에러 메시지
    private String code;                        // 응답 코드
    private Boolean isAsync;                    // 비동기 전송 여부
    private String recipients;                  // 수신자 목록
}
