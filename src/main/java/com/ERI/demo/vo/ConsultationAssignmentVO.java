package com.ERI.demo.vo;

import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;

/**
 * 상담 배정 VO
 * TB_CNSL_ASSIGNMENT 테이블과 매핑
 */
@Data
public class ConsultationAssignmentVO {
    
    // 기본 정보
    private Long asgnSeq;                   // 배정 일련번호 (PK)
    private Long appSeq;                     // 신청 일련번호
    private String cnslrEmpId;              // 상담사 직원ID
    private LocalDate asgnDt;                // 배정일자
    private LocalTime asgnTm;                // 배정시간
    private String asgnStsCd;               // 배정상태코드 (ASSIGNED/CONFIRMED/CANCELLED)
    private String asgnRsn;                 // 배정사유
    
    // 삭제 정보
    private String delYn;                    // 삭제여부 (Y/N)
    private LocalDateTime delDt;             // 삭제일시
    
    // 등록/수정 정보
    private String regEmpId;                 // 등록직원ID
    private String updEmpId;                 // 수정직원ID
    private LocalDateTime regDt;             // 등록일시
    private LocalDateTime updDt;             // 수정일시
    
    // 연관 데이터
    private String cnslrEmpNm;              // 상담사 직원명
    private ExpertConsultationAppVO appInfo; // 신청 정보
} 