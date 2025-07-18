package com.ERI.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageRequestDto {
    private int page = 1;      // 현재 페이지 (기본값: 1)
    private int size = 10;     // 페이지 크기 (기본값: 10)
    
    // 정렬 관련 - 프론트엔드와 호환
    private String sortBy;     // 정렬 필드 (프론트엔드: sortKey)
    private String sortDirection = "ASC"; // 정렬 방향 (기본값: ASC, 프론트엔드: sortOrder)
    
    // 검색 관련 - 프론트엔드와 호환
    private String searchKeyword; // 검색 키워드 (프론트엔드: ttl)
    private String searchType;    // 검색 타입
    private String searchField;   // 검색 필드
    
    // 공지사항 검색 관련 추가 필드
    private String startDate;   // 시작일
    private String endDate;     // 종료일
    private String stsCd;       // 상태코드
    
    // 게시판 검색 관련 추가 필드
    private String categoryCd;  // 카테고리 코드
    
    // 하위 호환성을 위한 별칭 필드
    private String keyword;     // 키워드 (별칭)
    
    /**
     * 페이지와 크기를 받는 생성자
     */
    public PageRequestDto(int page, int size) {
        this.page = page;
        this.size = size;
    }
    
    /**
     * 페이징을 위한 오프셋 계산
     * @return 오프셋 값
     */
    public int getOffset() {
        return (page - 1) * size;
    }
} 