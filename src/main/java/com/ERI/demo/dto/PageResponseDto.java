package com.ERI.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageResponseDto<T> {
    private List<T> content;           // 데이터 목록
    private long totalElements;        // 전체 데이터 개수 (int에서 long으로 변경)
    private int totalPages;            // 전체 페이지 개수
    private int currentPage;           // 현재 페이지
    private int size;                  // 페이지 크기
    private boolean hasNext;           // 다음 페이지 존재 여부
    private boolean hasPrevious;       // 이전 페이지 존재 여부
    private int startPage;             // 시작 페이지 번호
    private int endPage;               // 끝 페이지 번호
    
    /**
     * 생성자 (Builder 패턴 대신 사용)
     */
    public PageResponseDto(List<T> content, long totalElements, int currentPage, int size) {
        this.content = content;
        this.totalElements = totalElements;
        this.currentPage = currentPage;
        this.size = size;
        this.totalPages = (int) Math.ceil((double) totalElements / size);
        this.hasNext = currentPage < totalPages;
        this.hasPrevious = currentPage > 1;
        this.startPage = Math.max(1, currentPage - 2);
        this.endPage = Math.min(totalPages, currentPage + 2);
    }
    
    /**
     * 생성자 (서비스에서 사용하는 형태)
     */
    public PageResponseDto(List<T> content, int currentPage, int size, int totalCount, int totalPages) {
        this.content = content;
        this.totalElements = totalCount;
        this.currentPage = currentPage;
        this.size = size;
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
                .totalElements(0L)
                .totalPages(0)
                .currentPage(page)
                .size(size)
                .hasNext(false)
                .hasPrevious(false)
                .startPage(1)
                .endPage(1)
                .build();
    }
    
    /**
     * 다음 페이지 존재 여부 계산
     */
    public boolean isHasNext() {
        return currentPage < totalPages;
    }
    
    /**
     * 이전 페이지 존재 여부 계산
     */
    public boolean isHasPrevious() {
        return currentPage > 1;
    }
    
    /**
     * 시작 페이지 번호 계산
     */
    public int getStartPage() {
        return Math.max(1, currentPage - 2);
    }
    
    /**
     * 끝 페이지 번호 계산
     */
    public int getEndPage() {
        return Math.min(totalPages, currentPage + 2);
    }
} 