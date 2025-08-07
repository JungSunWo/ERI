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
public class EmpRightsBoardInVo extends IbkCldEocDto {
    
    // 기본 정보
    private Long seq;                    // 게시글 일련번호
    
    @NotBlank(message = "제목은 필수입니다.")
    @Size(max = 200, message = "제목은 200자를 초과할 수 없습니다.")
    private String ttl;                  // 제목
    
    @NotBlank(message = "내용은 필수입니다.")
    @Size(max = 4000, message = "내용은 4000자를 초과할 수 없습니다.")
    private String cntn;                 // 내용
    
    @Size(max = 10, message = "상태코드는 10자를 초과할 수 없습니다.")
    private String stsCd;                // 상태코드 (STS001: 임시저장, STS002: 등록완료, STS003: 삭제)
    
    // 분류 정보
    @NotBlank(message = "카테고리 코드는 필수입니다.")
    @Size(max = 10, message = "카테고리 코드는 10자를 초과할 수 없습니다.")
    private String categoryCd;           // 카테고리 코드 (CAT001: 업무환경, CAT002: 복리후생, CAT003: 인사제도, CAT004: 기타)
    
    @NotBlank(message = "공지여부는 필수입니다.")
    @Size(max = 1, message = "공지여부는 1자를 초과할 수 없습니다.")
    private String noticeYn;             // 공지여부 (Y/N)
    
    @NotBlank(message = "익명여부는 필수입니다.")
    @Size(max = 1, message = "익명여부는 1자를 초과할 수 없습니다.")
    private String anonymousYn;          // 익명여부 (Y/N)
    
    @Size(max = 50, message = "익명 닉네임은 50자를 초과할 수 없습니다.")
    private String nickname;             // 익명 닉네임 (익명인 경우)
    
    // 첨부파일 관련
    private String fileAttachYn;         // 첨부파일 존재 여부
    private Integer fileCount;           // 첨부파일 개수
    
    // 통계 정보
    private Integer viewCnt;             // 조회수
    private Integer likeCnt;             // 좋아요 수
    private Integer commentCnt;          // 댓글 수
    
    // 작성자 정보
    private String regEmpId;             // 등록직원ID
    private String updEmpId;             // 수정직원ID
    private String regEmpNm;             // 등록직원명 (익명이 아닌 경우)
    private String updEmpNm;             // 수정직원명
    
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
    private List<EmpRightsFileAttachInVo> files; // 첨부파일 목록
}
