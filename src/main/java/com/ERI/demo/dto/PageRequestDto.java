package com.ERI.demo.dto;

import lombok.Builder;
import lombok.Data;

/**
 * 페이징 요청 DTO
 */
@Data
@Builder
public class PageRequestDto {
    
    private int page = 1;           // 현재 페이지 (기본값: 1)
    private int size = 10;          // 페이지 크기 (기본값: 10)
    private String sortBy;          // 정렬 기준
    private String sortDirection;   // 정렬 방향 (ASC/DESC)
    private String keyword;         // 검색 키워드
    
    public PageRequestDto() {
        this.page = 1;
        this.size = 10;
    }
    
    public PageRequestDto(int page, int size) {
        this.page = page;
        this.size = size;
    }
    
    public PageRequestDto(int page, int size, String sortBy, String sortDirection) {
        this.page = page;
        this.size = size;
        this.sortBy = sortBy;
        this.sortDirection = sortDirection;
    }
    
    public PageRequestDto(int page, int size, String sortBy, String sortDirection, String keyword) {
        this.page = page;
        this.size = size;
        this.sortBy = sortBy;
        this.sortDirection = sortDirection;
        this.keyword = keyword;
    }
    
    /**
     * 오프셋 계산 (페이지 번호와 크기를 기반으로)
     * @return 오프셋 값
     */
    public int getOffset() {
        return (page - 1) * size;
    }
    
    /**
     * 정렬 문자열 생성 (sortBy,sortDirection 형태)
     * @return 정렬 문자열
     */
    public String getSort() {
        if (sortBy != null && !sortBy.trim().isEmpty() && sortDirection != null && !sortDirection.trim().isEmpty()) {
            return sortBy.trim() + "," + sortDirection.trim().toLowerCase();
        }
        return null;
    }
} 