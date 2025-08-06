package com.ERI.demo.vo;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 상담 게시판 VO
 */
@Data
public class ConsultationBoardVO {
    
    // 기본 정보
    private Long seq;                    // 상담 일련번호
    private String ttl;                  // 제목
    private String cntn;                 // 내용
    private String stsCd;                // 상태코드 (STS001: 대기, STS002: 답변완료, STS003: 종료)
    
    // 분류 정보
    private String categoryCd;           // 카테고리 코드 (CAT001: 업무, CAT002: 인사, CAT003: 복지, CAT004: 기타)
    private String anonymousYn;          // 익명여부 (Y/N)
    private String nickname;             // 익명 닉네임 (익명인 경우)
    private String priorityCd;           // 우선순위 (PRI001: 긴급, PRI002: 높음, PRI003: 보통, PRI004: 낮음)
    private String urgentYn;             // 긴급여부 (Y/N)
    
    // 첨부파일 관련
    private String fileAttachYn;         // 첨부파일 존재 여부
    private Integer fileCount;           // 첨부파일 개수
    
    // 통계 정보
    private Integer viewCnt;             // 조회수
    
    // 답변 관련
    private String answerYn;             // 답변여부 (Y/N)
    private String answerEmpId;          // 답변자 직원ID
    private LocalDateTime answerDate;    // 답변일시
    
    // 작성자 정보
    private String regEmpId;             // 등록직원ID
    private String updEmpId;             // 수정직원ID
    private String regEmpNm;             // 등록직원명 (익명이 아닌 경우)
    private String updEmpNm;             // 수정직원명
    private String answerEmpNm;          // 답변자 직원명
    
    // 삭제 정보
    private String delYn;                // 삭제여부
    private LocalDateTime delDate;       // 삭제일시
    
    // 등록/수정 정보
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
    
    // 연관 데이터
    private ConsultationAnswerVO answer; // 답변 정보
    private List<ConsultationFileAttachVO> files; // 첨부파일 목록
} 