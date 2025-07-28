package com.ERI.demo.vo;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 익명 사용자 VO
 */
@Data
public class AnonymousUserVO {
    
    private Long anonymousId;            // 익명 사용자 ID (일련번호)
    private String nickname;             // 익명 닉네임
    private LocalDateTime regDate;       // 등록일시
    private String useYn;                // 사용여부
    private String delYn;                // 삭제 여부
    private LocalDateTime delDate;       // 삭제 일시
    
    // 추가 정보 (JOIN 시 사용)
    private Integer consultationCount;   // 상담 건수
    private LocalDateTime lastConsultationDate; // 마지막 상담일시
} 