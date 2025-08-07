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
public class EmpRightsCommentInVo extends IbkCldEocDto {
    
    // 기본 정보
    private Long seq;                    // 댓글 일련번호
    
    @NotNull(message = "게시글 일련번호는 필수입니다.")
    private Long boardSeq;               // 게시글 일련번호
    
    @NotBlank(message = "댓글 내용은 필수입니다.")
    @Size(max = 1000, message = "댓글 내용은 1000자를 초과할 수 없습니다.")
    private String cntn;                 // 댓글 내용
    
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
    
    // 추가 정보
    private Boolean isAuthor;            // 현재 사용자가 작성자인지 여부
}
