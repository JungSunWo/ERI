package com.ERI.demo.vo;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 익명 상담 연관 VO
 */
@Data
public class AnonymousConsultationMapVO {
    
    private Long mapSeq;             // 연관 일련번호
    private Long anonymousId;        // 익명 사용자 ID
    private Long cnslAppSeq;         // 상담신청 일련번호
    private LocalDateTime regDate;   // 등록일시
    
    // 조인을 위한 추가 필드
    private String nickname;         // 익명 닉네임
} 