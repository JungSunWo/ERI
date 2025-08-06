package com.ERI.demo.vo;

import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;

/**
 * 전문가 상담 신청 VO
 * TB_EXP_CNSL_APP 테이블과 매핑
 */
@Data
public class ExpertConsultationAppVO {
    
    // 기본 정보
    private Long appSeq;                    // 신청 일련번호 (PK)
    private LocalDate appDt;                // 신청일자
    private LocalTime appTm;                // 신청시간
    private String empId;                   // 신청자 직원ID
    
    // 상담 정보
    private LocalDate cnslDt;               // 상담일자
    private LocalTime cnslTm;               // 상담시간
    private String cnslrEmpId;              // 상담사 직원ID
    private String cnslTyCd;                // 상담유형코드 (FACE_TO_FACE/REMOTE)
    private String cnslCntn;                // 상담내용
    
    // 익명 및 승인 정보
    private String anonymousYn;             // 익명신청여부 (Y/N)
    private String aprvStsCd;               // 승인상태코드 (PENDING/APPROVED/REJECTED/COMPLETED)
    private String aprvEmpId;               // 승인자 직원ID
    private LocalDate aprvDt;               // 승인일자
    private LocalTime aprvTm;               // 승인시간
    private String rejRsn;                  // 반려사유
    
    // 삭제 정보
    private String delYn;                   // 삭제여부 (Y/N)
    private LocalDateTime delDt;            // 삭제일시
    
    // 등록/수정 정보
    private String regEmpId;                // 등록직원ID
    private String updEmpId;                // 수정직원ID
    private LocalDateTime regDt;            // 등록일시
    private LocalDateTime updDt;            // 수정일시
    
    // 검색 조건
    private String searchType;              // 검색 타입
    private String searchKeyword;           // 검색 키워드
    private String startDate;               // 시작일
    private String endDate;                 // 종료일
    
    // 페이징 정보
    private Integer offset;                 // 오프셋
    private Integer size;                   // 페이지 크기
    private String sortBy;                  // 정렬 기준
    private String sortDirection;           // 정렬 방향
    
    // 연관 데이터
    private String empNm;                   // 신청자 직원명
    private String cnslrEmpNm;              // 상담사 직원명
    private String aprvEmpNm;               // 승인자 직원명
} 