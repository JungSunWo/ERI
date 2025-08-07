package com.ibkcloud.eoc.controller.vo;

import com.ibkcloud.eoc.cmm.dto.IbkCldEocDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
public class ExpertConsultationAppInVo extends IbkCldEocDto {
    
    // 기본 정보
    private Long appSeq;                   // 신청 일련번호
    
    @NotBlank(message = "신청자 ID는 필수입니다.")
    @Size(max = 20, message = "신청자 ID는 20자를 초과할 수 없습니다.")
    private String appEmpId;               // 신청자 ID
    
    @NotBlank(message = "신청자명은 필수입니다.")
    @Size(max = 100, message = "신청자명은 100자를 초과할 수 없습니다.")
    private String appEmpNm;               // 신청자명
    
    @NotBlank(message = "상담 분야는 필수입니다.")
    @Size(max = 50, message = "상담 분야는 50자를 초과할 수 없습니다.")
    private String consultField;           // 상담 분야
    
    @NotBlank(message = "상담 제목은 필수입니다.")
    @Size(max = 200, message = "상담 제목은 200자를 초과할 수 없습니다.")
    private String consultTitle;           // 상담 제목
    
    @NotBlank(message = "상담 내용은 필수입니다.")
    @Size(max = 2000, message = "상담 내용은 2000자를 초과할 수 없습니다.")
    private String consultContent;         // 상담 내용
    
    @Size(max = 20, message = "승인 상태 코드는 20자를 초과할 수 없습니다.")
    private String aprvStsCd;             // 승인 상태 코드 (APRV001: 대기, APRV002: 승인, APRV003: 반려)
    
    private String aprvEmpId;             // 승인자 ID
    private String aprvEmpNm;             // 승인자명
    private LocalDateTime aprvDate;        // 승인일시
    
    @Size(max = 500, message = "반려 사유는 500자를 초과할 수 없습니다.")
    private String rejRsn;                // 반려 사유
    
    @Size(max = 20, message = "상담 상태 코드는 20자를 초과할 수 없습니다.")
    private String consultStsCd;          // 상담 상태 코드 (CONS001: 대기, CONS002: 진행중, CONS003: 완료)
    
    private String expertEmpId;           // 전문가 ID
    private String expertEmpNm;           // 전문가명
    private LocalDateTime consultDate;     // 상담일시
    
    @Size(max = 2000, message = "상담 결과는 2000자를 초과할 수 없습니다.")
    private String consultResult;          // 상담 결과
    
    @Size(max = 20, message = "상담 분류 코드는 20자를 초과할 수 없습니다.")
    private String consultTypeCd;         // 상담 분류 코드
    
    @Size(max = 20, message = "우선순위 코드는 20자를 초과할 수 없습니다.")
    private String priorityCd;            // 우선순위 코드
    
    @Size(max = 1, message = "긴급여부는 1자를 초과할 수 없습니다.")
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
    
    // 검색 조건
    private String keyword;               // 검색 키워드
    private String searchField;           // 검색 필드
    private String sortBy;                // 정렬 기준
    private String sortDirection;         // 정렬 방향
    private String startDate;             // 시작일
    private String endDate;               // 종료일
} 