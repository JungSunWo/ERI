package com.ERI.demo.vo;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 상담사 정보 VO
 * TB_CNSLR_INFO 테이블과 매핑
 */
@Data
public class CounselorInfoVO {
    
    // 기본 정보
    private String cnslrEmpId;              // 상담사 직원ID (PK)
    private String cnslrNm;                  // 상담사명
    private String cnslrClsfCd;              // 상담사구분코드 (PROF/GEN/INT)
    private String cnslrSpclCd;              // 상담사전문분야코드
    private String cnslrLicCd;               // 상담사자격증코드
    private String cnslrIntro;               // 상담사소개
    private String cnslrImgUrl;              // 상담사이미지URL
    private String availYn;                  // 상담가능여부 (Y/N)
    
    // 삭제 정보
    private String delYn;                    // 삭제여부 (Y/N)
    private LocalDateTime delDt;             // 삭제일시
    
    // 등록/수정 정보
    private String regEmpId;                 // 등록직원ID
    private String updEmpId;                 // 수정직원ID
    private LocalDateTime regDt;             // 등록일시
    private LocalDateTime updDt;             // 수정일시
    
    // 연관 데이터
    private String cnslrClsfNm;             // 상담사 분류명
    private String cnslrSpclNm;             // 상담사 전문분야명
    private String cnslrLicNm;              // 상담사 자격증명
} 