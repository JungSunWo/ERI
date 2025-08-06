package com.ERI.demo.vo;

import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;

/**
 * 상담 시간 제한 VO
 * TB_CNSL_TIME_LIMIT 테이블과 매핑
 */
@Data
public class ConsultationTimeLimitVO {
    
    // 기본 정보
    private Long limitSeq;                  // 제한 일련번호 (PK)
    private LocalDate cnslDt;               // 상담일자
    private LocalTime cnslTm;                // 상담시간
    private Integer maxCnt;                  // 최대신청수 (기본값 3)
    private Integer currentCnt;              // 현재신청수
    private String availYn;                  // 신청가능여부 (Y/N)
    
    // 삭제 정보
    private String delYn;                    // 삭제여부 (Y/N)
    private LocalDateTime delDt;             // 삭제일시
    
    // 등록/수정 정보
    private String regEmpId;                 // 등록직원ID
    private String updEmpId;                 // 수정직원ID
    private LocalDateTime regDt;             // 등록일시
    private LocalDateTime updDt;             // 수정일시
} 