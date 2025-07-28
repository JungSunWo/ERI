package com.ERI.demo.vo;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 직원권익게시판 좋아요/싫어요 VO
 */
@Data
public class EmpRightsLikeVO {
    
    // 기본 정보
    private Long seq;                    // 일련번호
    private Long boardSeq;               // 게시글 일련번호 (NULL이면 댓글 좋아요)
    private Long commentSeq;             // 댓글 일련번호 (NULL이면 게시글 좋아요)
    private String likeType;             // 좋아요 타입 (L: 좋아요, D: 싫어요)
    
    // 삭제 정보
    private String delYn;                // 삭제여부
    private LocalDateTime delDate;       // 삭제일시
    
    // 등록/수정 정보
    private String regEmpId;             // 등록직원ID
    private String updEmpId;             // 수정직원ID
    private LocalDateTime regDate;       // 등록일시
    private LocalDateTime updDate;       // 수정일시
} 