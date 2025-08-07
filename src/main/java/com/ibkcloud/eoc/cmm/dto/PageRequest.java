package com.ibkcloud.eoc.cmm.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

/**
 * @파일명 : PageRequest
 * @논리명 : 페이지 조회요청
 * @작성자 : 시스템
 * @설명   : 페이지 단위 조회를 위한 요청 클래스
 */
@Getter
@Setter
@NoArgsConstructor
public class PageRequest<T> {
    
    /**
     * 페이지당 데이터 건수
     */
    private long pageSize;
    
    /**
     * 조회할 페이지번호
     */
    private long pageNo;
    
    /**
     * 페이지 그룹당 페이지수
     */
    private long pageGrpNbi;
    
    /**
     * 페이지그룹당 조회 여부
     */
    private boolean isPageGrpInq;
    
    /**
     * 조회 파라미터
     */
    private T param;
    
    /**
     * LIMIT 값 계산
     */
    private long limit;
    
    /**
     * OFFSET 값 계산
     */
    private long offset;
    
    public PageRequest(long pageSize, long pageNo, T param) {
        this.pageSize = pageSize;
        this.pageNo = pageNo;
        this.param = param;
        calculateLimitOffset();
    }
    
    /**
     * LIMIT, OFFSET 값 계산
     */
    private void calculateLimitOffset() {
        this.limit = this.pageSize;
        this.offset = (this.pageNo - 1) * this.pageSize;
    }
    
    /**
     * 페이지 그룹당 조회 여부 설정
     */
    public void setIsPageGrpInq(boolean isPageGrpInq) {
        this.isPageGrpInq = isPageGrpInq;
        if (isPageGrpInq) {
            this.limit = this.pageSize * this.pageGrpNbi;
        } else {
            this.limit = this.pageSize;
        }
    }
} 