package com.ibkcloud.eoc.controller.vo;

import com.ibkcloud.eoc.cmm.dto.IbkCldEocDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class ConsultationAnswerInVo extends IbkCldEocDto {
    
    // 기본 정보
    private Long seq;                    // 답변 일련번호
    
    @NotNull(message = "상담 게시글 일련번호는 필수입니다.")
    private Long boardSeq;               // 상담 게시글 일련번호
    
    @NotBlank(message = "답변 내용은 필수입니다.")
    @Size(max = 4000, message = "답변 내용은 4000자를 초과할 수 없습니다.")
    private String cntn;                 // 답변 내용
    
    @Size(max = 10, message = "상태코드는 10자를 초과할 수 없습니다.")
    private String stsCd;                // 상태코드 (STS001: 임시저장, STS002: 등록완료)
    
    // 작성자 정보
    private String regEmpId;             // 답변자 직원ID
    private String updEmpId;             // 수정자 직원ID
    private String regEmpNm;             // 답변자 직원명
    private String updEmpNm;             // 수정자 직원명
    
    // 삭제 정보
    private String delYn;                // 삭제여부
    private LocalDateTime delDate;       // 삭제일시
    
    // 등록/수정 정보
    private LocalDateTime regDate;       // 등록일시
    private LocalDateTime updDate;       // 수정일시
    
    // 연관 데이터
    private List<ConsultationFileAttachInVo> files; // 첨부파일 목록
} 