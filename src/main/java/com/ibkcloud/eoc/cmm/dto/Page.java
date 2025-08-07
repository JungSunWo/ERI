package com.ibkcloud.eoc.cmm.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @파일명 : Page
 * @논리명 : 페이지 조회결과
 * @작성자 : 시스템
 * @설명   : 페이지 단위 조회 결과를 담는 클래스
 */
@Getter
@Setter
@NoArgsConstructor
public class Page<T> {
    
    /**
     * 페이지당 데이터 건수
     */
    private long pageSize;
    
    /**
     * 조회한 페이지번호
     */
    private long pageNo;
    
    /**
     * 전체 데이터 건수
     */
    private long ttalDataNbi;
    
    /**
     * 전체 페이지 수
     */
    private long ttalPageNbi;
    
    /**
     * 조회결과 목록
     */
    private List<T> contents;
    
    /**
     * 페이지 그룹당 페이지수
     */
    private long pageGrpNbi;
    
    /**
     * 페이지그룹당 조회 여부
     */
    private boolean isPageGrpInq;
    
    public Page(PageRequest<?> pageRequest) {
        this.pageSize = pageRequest.getPageSize();
        this.pageNo = pageRequest.getPageNo();
        this.pageGrpNbi = pageRequest.getPageGrpNbi();
        this.isPageGrpInq = pageRequest.isPageGrpInq();
    }
    
    /**
     * 전체 페이지 수 계산
     */
    public void calculateTotalPages() {
        if (this.pageSize > 0) {
            this.ttalPageNbi = (this.ttalDataNbi + this.pageSize - 1) / this.pageSize;
        }
    }
    
    /**
     * 현재 페이지가 첫 페이지인지 확인
     */
    public boolean isFirstPage() {
        return this.pageNo == 1;
    }
    
    /**
     * 현재 페이지가 마지막 페이지인지 확인
     */
    public boolean isLastPage() {
        return this.pageNo >= this.ttalPageNbi;
    }
    
    /**
     * 이전 페이지 번호 반환
     */
    public long getPreviousPage() {
        return this.pageNo > 1 ? this.pageNo - 1 : 1;
    }
    
    /**
     * 다음 페이지 번호 반환
     */
    public long getNextPage() {
        return this.pageNo < this.ttalPageNbi ? this.pageNo + 1 : this.ttalPageNbi;
    }
} 