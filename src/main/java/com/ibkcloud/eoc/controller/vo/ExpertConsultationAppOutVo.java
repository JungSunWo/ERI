package com.ibkcloud.eoc.controller.vo;

import com.ibkcloud.eoc.cmm.dto.IbkCldEocDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class ExpertConsultationAppOutVo extends IbkCldEocDto {
    
    // 기본 정보
    private Long appSeq;                   // 신청 일련번호
    private String appEmpId;               // 신청자 ID
    private String appEmpNm;               // 신청자명
    private String consultField;           // 상담 분야
    private String consultTitle;           // 상담 제목
    private String consultContent;         // 상담 내용
    private String aprvStsCd;             // 승인 상태 코드 (APRV001: 대기, APRV002: 승인, APRV003: 반려)
    private String aprvEmpId;             // 승인자 ID
    private String aprvEmpNm;             // 승인자명
    private LocalDateTime aprvDate;        // 승인일시
    private String rejRsn;                // 반려 사유
    private String consultStsCd;          // 상담 상태 코드 (CONS001: 대기, CONS002: 진행중, CONS003: 완료)
    private String expertEmpId;           // 전문가 ID
    private String expertEmpNm;           // 전문가명
    private LocalDateTime consultDate;     // 상담일시
    private String consultResult;          // 상담 결과
    private String consultTypeCd;         // 상담 분류 코드
    private String priorityCd;            // 우선순위 코드
    private String urgentYn;              // 긴급여부 (Y/N)
    
    // 작성자 정보
    private String regEmpId;              // 등록직원ID
    private String updEmpId;              // 수정직원ID
    private String regEmpNm;              // 등록직원명
    private String updEmpNm;              // 수정직원명
    
    // 삭제 정보
    private String delYn;                 // 삭제여부
    private LocalDateTime delDate;        // 삭제일시
    
    // 등록/수정 정보
    private LocalDateTime regDate;        // 등록일시
    private LocalDateTime updDate;        // 수정일시
    
    // 목록 조회용
    private List<ExpertConsultationAppOutVo> data; // 전문가 상담 신청 목록
    private Integer count;                // 총 개수
    private Integer pageNo;               // 현재 페이지
    private Integer pageSize;             // 페이지 크기
    private Integer ttalPageNbi;          // 총 페이지 수
} 