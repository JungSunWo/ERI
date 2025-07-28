package com.ERI.demo.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 공지사항 페이징 요청 DTO
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class NtiLstPageRequestDto extends PageRequestDto {
    
    /**
     * 제목 검색어
     */
    private String ttl;
    
    /**
     * 내용 검색어
     */
    private String cntn;
    
    /**
     * 상태코드
     */
    private String stsCd;
    
    /**
     * 등록자ID
     */
    private String regEmpId;
    
    /**
     * 시작일자
     */
    private String startDate;
    
    /**
     * 종료일자
     */
    private String endDate;
} 