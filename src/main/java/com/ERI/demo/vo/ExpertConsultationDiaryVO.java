package com.ERI.demo.vo;

import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;

/**
 * 전문가 상담 일지 VO
 * TB_EXP_CNSL_DRY 테이블과 매핑
 */
@Data
public class ExpertConsultationDiaryVO {
    
    // 기본 정보
    private Long drySeq;                    // 일지 일련번호 (PK)
    private Long appSeq;                     // 신청 일련번호
    
    // 상담 정보
    private LocalDate cnslDt;               // 상담일자
    private LocalTime cnslTm;                // 상담시간
    private String cnslrEmpId;              // 상담사 직원ID
    private String cnslCntn;                 // 상담내용
    private String cnslRes;                  // 상담결과
    private String cnslRcmd;                 // 상담권고사항
    private Integer cnslDur;                 // 상담시간(분)
    private String cnslStsCd;               // 상담상태코드 (SCHEDULED/IN_PROGRESS/COMPLETED/CANCELLED)
    
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