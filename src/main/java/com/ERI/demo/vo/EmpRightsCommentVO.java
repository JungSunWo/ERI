package com.ERI.demo.vo;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 직원권익게시판 댓글 VO
 */
@Data
public class EmpRightsCommentVO {
    
    // 기본 정보
    private Long seq;                    // 댓글 일련번호
    private Long boardSeq;               // 게시글 일련번호
    private Long parentSeq;              // 부모 댓글 일련번호 (대댓글용)
    private String cntn;                 // 댓글 내용
    private Integer depth;               // 댓글 깊이 (0: 댓글, 1: 대댓글, 2: 대대댓글...)
    
    // 통계 정보
    private Integer likeCnt;             // 좋아요 수
    private Integer dislikeCnt;          // 싫어요 수
    
    // 분류 정보
    private String secretYn;             // 비밀댓글 여부
    
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
    
    // 내가 누른 좋아요 타입
    private String myLikeType;           // 내가 누른 좋아요 타입 (L/D/null)
    
    // 작성자 여부 (현재 로그인한 사용자가 작성자인지)
    private Boolean isAuthor;             // 작성자 여부
    
    // 대댓글 목록
    private List<EmpRightsCommentVO> replies; // 대댓글 목록
} 