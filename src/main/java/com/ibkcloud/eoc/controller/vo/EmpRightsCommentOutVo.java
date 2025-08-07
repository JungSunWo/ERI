package com.ibkcloud.eoc.controller.vo;

import com.ibkcloud.eoc.cmm.dto.IbkCldEocDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class EmpRightsCommentOutVo extends IbkCldEocDto {
    
    // 기본 정보
    private Long seq;                    // 댓글 일련번호
    private Long boardSeq;               // 게시글 일련번호
    private String cntn;                 // 댓글 내용
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
    
    // 목록 조회용
    private List<EmpRightsCommentOutVo> data; // 댓글 목록
    private Integer count;               // 총 개수
}
