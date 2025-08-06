package com.ERI.demo.vo;

import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;

/**
 * 상담사 스케줄 VO
 * TB_CNSLR_SCHEDULE 테이블과 매핑
 */
@Data
public class CounselorScheduleVO {
    
    // 기본 정보
    private Long schSeq;                    // 스케줄 일련번호 (PK)
    private String cnslrEmpId;              // 상담사 직원ID
    private LocalDate schDt;                 // 스케줄일자
    private LocalTime schTm;                 // 스케줄시간
    private String schTyCd;                  // 스케줄유형코드 (AVAILABLE/UNAVAILABLE/BREAK)
    private String schCntn;                  // 스케줄내용
    
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
} 