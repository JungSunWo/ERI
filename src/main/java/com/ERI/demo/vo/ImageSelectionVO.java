package com.ERI.demo.vo;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

/**
 * 이미지 선택 VO
 * TB_IMG_SEL_LST 테이블과 매핑
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImageSelectionVO {
    
    // 기본 정보
    private Long imgSelSeq;        // 이미지 선택 시퀀스 (PK)
    private Long imgBrdSeq;        // 이미지 게시판 시퀀스
    private Long imgFileSeq;       // 이미지 파일 시퀀스
    private String selEmpId;       // 선택한 직원 ID
    private LocalDateTime selDt;   // 선택 일시
    
    // 검색/페이징 관련
    private Integer page;          // 페이지 번호
    private Integer size;          // 페이지 크기
    private String searchType;     // 검색 타입
    private String searchKeyword;  // 검색 키워드
    private String sortBy;         // 정렬 필드
    private String sortDirection;  // 정렬 방향
    
    // 연관 데이터
    private String selEmpNm;       // 선택자명
    private ImageBoardVO imageBoard; // 이미지 게시판 정보
    private ImageFileVO imageFile;   // 이미지 파일 정보
    
    // 생성자 (기본 필드)
    public ImageSelectionVO(Long imgBrdSeq, Long imgFileSeq, String selEmpId) {
        this.imgBrdSeq = imgBrdSeq;
        this.imgFileSeq = imgFileSeq;
        this.selEmpId = selEmpId;
        this.selDt = LocalDateTime.now();
    }
    
    // 선택 일시 포맷팅
    public String getSelectionDateFormatted() {
        if (selDt == null) return "";
        return selDt.toString(); // 실제로는 원하는 포맷으로 변경
    }
    
    // 선택 여부 확인 (현재 시간 기준)
    public boolean isSelectedToday() {
        if (selDt == null) return false;
        LocalDateTime today = LocalDateTime.now();
        return selDt.toLocalDate().equals(today.toLocalDate());
    }
    
    // 선택 시간 경과 확인 (분 단위)
    public long getMinutesSinceSelection() {
        if (selDt == null) return 0;
        LocalDateTime now = LocalDateTime.now();
        return java.time.Duration.between(selDt, now).toMinutes();
    }
} 