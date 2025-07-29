package com.ERI.demo.vo;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDate;

/**
 * 시스템 로그 검색 VO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SystemLogSearchVo {
    
    /**
     * 로그 레벨 (INFO, WARN, ERROR, DEBUG)
     */
    private String logLevel;
    
    /**
     * 로그 타입 (SYSTEM, API, DATABASE, SECURITY, MESSENGER)
     */
    private String logType;
    
    /**
     * 사용자 사번
     */
    private String empId;
    
    /**
     * 에러 코드
     */
    private String errorCode;
    
    /**
     * 검색 시작일
     */
    private LocalDate startDate;
    
    /**
     * 검색 종료일
     */
    private LocalDate endDate;
    
    /**
     * 검색 키워드
     */
    private String searchKeyword;
    
    /**
     * 페이지 번호 (1부터 시작)
     */
    private Integer page = 1;
    
    /**
     * 페이지 크기
     */
    private Integer pageSize = 20;
    
    /**
     * 오프셋 (페이징용)
     */
    private Integer offset;
    
    /**
     * 리미트 (페이징용)
     */
    private Integer limit;
    
    /**
     * 페이징 정보 계산
     */
    public void calculatePaging() {
        if (page == null || page < 1) {
            page = 1;
        }
        if (pageSize == null || pageSize < 1) {
            pageSize = 20;
        }
        
        this.offset = (page - 1) * pageSize;
        this.limit = pageSize;
    }
}