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
public class ConsultationFileAttachInVo extends IbkCldEocDto {
    
    // 기본 정보
    private Long seq;                    // 첨부파일 일련번호
    
    @NotNull(message = "상담 게시글 일련번호는 필수입니다.")
    private Long boardSeq;               // 상담 게시글 일련번호
    
    private Long answerSeq;              // 답변 일련번호 (답변 첨부파일인 경우)
    
    @NotBlank(message = "파일명은 필수입니다.")
    @Size(max = 200, message = "파일명은 200자를 초과할 수 없습니다.")
    private String fileName;             // 파일명
    
    @NotBlank(message = "원본파일명은 필수입니다.")
    @Size(max = 200, message = "원본파일명은 200자를 초과할 수 없습니다.")
    private String originalFileName;     // 원본파일명
    
    @Size(max = 100, message = "파일경로는 100자를 초과할 수 없습니다.")
    private String filePath;             // 파일경로
    
    @Size(max = 50, message = "파일확장자는 50자를 초과할 수 없습니다.")
    private String fileExt;              // 파일확장자
    
    private Long fileSize;               // 파일크기 (bytes)
    
    @Size(max = 10, message = "파일타입은 10자를 초과할 수 없습니다.")
    private String fileType;             // 파일타입 (ATT001: 게시글, ATT002: 답변)
    
    @Size(max = 10, message = "상태코드는 10자를 초과할 수 없습니다.")
    private String stsCd;                // 상태코드 (STS001: 정상, STS002: 삭제)
    
    // 작성자 정보
    private String regEmpId;             // 등록직원ID
    private String updEmpId;             // 수정직원ID
    private String regEmpNm;             // 등록직원명
    private String updEmpNm;             // 수정직원명
    
    // 삭제 정보
    private String delYn;                // 삭제여부
    private LocalDateTime delDate;       // 삭제일시
    
    // 등록/수정 정보
    private LocalDateTime regDate;       // 등록일시
    private LocalDateTime updDate;       // 수정일시
}
