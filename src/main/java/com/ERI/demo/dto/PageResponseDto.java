package com.ERI.demo.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * 페이징 응답 DTO
 */
@Data
@Builder
public class PageResponseDto<T> {
    
    private List<T> content;        // 데이터 목록
    private int totalElements;      // 전체 데이터 개수
    private int totalPages;         // 전체 페이지 개수
    private int currentPage;        // 현재 페이지
    private int size;               // 페이지 크기
    private boolean hasNext;        // 다음 페이지 존재 여부
    private boolean hasPrevious;    // 이전 페이지 존재 여부
    private int startPage;          // 시작 페이지 번호
    private int endPage;            // 끝 페이지 번호
    
    public PageResponseDto() {}
    
    public PageResponseDto(List<T> content, int totalElements, int totalPages, int currentPage, int size, 
                          boolean hasNext, boolean hasPrevious, int startPage, int endPage) {
        this.content = content;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
        this.currentPage = currentPage;
        this.size = size;
        this.hasNext = hasNext;
        this.hasPrevious = hasPrevious;
        this.startPage = startPage;
        this.endPage = endPage;
    }
    
    public PageResponseDto(List<T> content, long totalElements, int currentPage, int size) {
        this.content = content;
        this.totalElements = (int) totalElements;
        this.currentPage = currentPage;
        this.size = size;
        this.totalPages = (int) Math.ceil((double) totalElements / size);
        this.hasNext = currentPage < this.totalPages;
        this.hasPrevious = currentPage > 1;
        this.startPage = Math.max(1, currentPage - 2);
        this.endPage = Math.min(this.totalPages, currentPage + 2);
    }
    
    /**
     * 5개 매개변수를 받는 생성자 (기존 코드 호환성)
     */
    public PageResponseDto(List<T> content, int currentPage, int size, int totalElements, int totalPages) {
        this.content = content;
        this.currentPage = currentPage;
        this.size = size;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
        this.hasNext = currentPage < totalPages;
        this.hasPrevious = currentPage > 1;
        this.startPage = Math.max(1, currentPage - 2);
        this.endPage = Math.min(totalPages, currentPage + 2);
    }
    
    /**
     * 빈 페이지 응답 생성
     * @param page 현재 페이지
     * @param size 페이지 크기
     * @return 빈 페이지 응답
     */
    public static <T> PageResponseDto<T> empty(int page, int size) {
        return PageResponseDto.<T>builder()
                .content(List.of())
                .totalElements(0)
                .totalPages(0)
                .currentPage(page)
                .size(size)
                .hasNext(false)
                .hasPrevious(false)
                .startPage(1)
                .endPage(1)
                .build();
    }
} 