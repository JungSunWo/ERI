package com.ERI.demo.vo;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 이미지 게시판 VO
 * TB_IMG_BRD_LST 테이블과 매핑
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImageBoardVO {
    
    // 기본 정보
    private Long imgBrdSeq;        // 이미지 게시판 시퀀스 (PK)
    private String imgBrdTitl;     // 이미지 게시판 제목
    private String imgBrdDesc;     // 이미지 게시판 설명
    private Integer maxSelCnt;     // 최대 선택 개수 (기본값 5개)
    
    // 공통 정보
    private String regEmpId;       // 등록 직원 ID
    private String updEmpId;       // 수정 직원 ID
    private String delYn;          // 삭제 여부 (Y/N)
    private LocalDateTime delDt;   // 삭제 일시
    private LocalDateTime regDt;   // 등록 일시
    private LocalDateTime updDt;   // 수정 일시
    
    // 검색/페이징 관련
    private Integer page;          // 페이지 번호
    private Integer size;          // 페이지 크기
    private String searchType;     // 검색 타입
    private String searchKeyword;  // 검색 키워드
    private String sortBy;         // 정렬 필드
    private String sortDirection;  // 정렬 방향
    
    // 연관 데이터
    private String regEmpNm;       // 등록자명
    private String updEmpNm;       // 수정자명
    private List<ImageFileVO> imageFiles; // 이미지 파일 목록
    private Integer imageFileCount; // 이미지 파일 개수
    private Integer selectedCount;  // 선택된 이미지 개수
    
    // 생성자 (기본 필드)
    public ImageBoardVO(String imgBrdTitl, String imgBrdDesc, Integer maxSelCnt, String regEmpId) {
        this.imgBrdTitl = imgBrdTitl;
        this.imgBrdDesc = imgBrdDesc;
        this.maxSelCnt = maxSelCnt != null ? maxSelCnt : 5;
        this.regEmpId = regEmpId;
        this.delYn = "N";
    }
    
    // 이미지 파일 개수 계산
    public int getImageFileCount() {
        return imageFiles != null ? imageFiles.size() : 0;
    }
    
    // 선택 가능한 이미지 개수 확인
    public boolean canSelectMore(int currentSelectionCount) {
        return currentSelectionCount < maxSelCnt;
    }
    
    // 선택 가능한 이미지 개수 반환
    public int getRemainingSelectionCount(int currentSelectionCount) {
        return Math.max(0, maxSelCnt - currentSelectionCount);
    }
} 