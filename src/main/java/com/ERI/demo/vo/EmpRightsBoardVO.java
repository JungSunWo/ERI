package com.ERI.demo.vo;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 직원권익게시판 VO
 */
@Data
public class EmpRightsBoardVO {
    
    // 기본 정보
    private Long seq;                    // 일련번호
    private String ttl;                  // 제목
    private String cntn;                 // 내용
    private String stsCd;                // 상태코드 (ACTIVE/INACTIVE/DELETED)
    
    // 첨부파일 관련
    private String fileAttachYn;         // 첨부파일 존재 여부
    private Integer fileCount;           // 첨부파일 개수
    
    // 통계 정보
    private Integer viewCnt;             // 조회수
    private Integer likeCnt;             // 좋아요 수
    private Integer dislikeCnt;          // 싫어요 수
    private Integer commentCount;        // 댓글 개수
    
    // 분류 정보
    private String categoryCd;           // 카테고리 코드 (GENERAL/COMPLAINT/SUGGESTION/QUESTION)
    private String secretYn;             // 비밀글 여부
    private String noticeYn;             // 공지글 여부
    
    // 작성자 정보
    private String rgstEmpId;            // 등록직원ID
    private String updtEmpId;            // 수정직원ID
    private String rgstEmpNm;            // 등록직원명
    private String updtEmpNm;            // 수정직원명
    
    // 삭제 정보
    private String delYn;                // 삭제여부
    private LocalDateTime delDate;       // 삭제일시
    
    // 등록/수정 정보
    private String regEmpId;             // 등록직원ID
    private String updEmpId;             // 수정직원ID
    private LocalDateTime regDate;       // 등록일시
    private LocalDateTime updDate;       // 수정일시
    
    // 검색 조건
    private String searchType;           // 검색 타입 (title/content)
    private String searchKeyword;        // 검색 키워드
    private String startDate;            // 시작일
    private String endDate;              // 종료일
    
    // 페이징 정보
    private Integer offset;              // 오프셋
    private Integer size;                // 페이지 크기
    private String sortBy;               // 정렬 기준
    private String sortDirection;        // 정렬 방향
    
    // 내가 누른 좋아요 타입
    private String myLikeType;           // 내가 누른 좋아요 타입 (L/D/null)
} 