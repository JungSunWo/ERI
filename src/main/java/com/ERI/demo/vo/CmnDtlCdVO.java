package com.ERI.demo.vo;

import lombok.Data;

/**
 * 공통 상세 코드 VO
 */
@Data
public class CmnDtlCdVO {
    
    /**
     * 순번 (ROW_NUMBER)
     */
    private Integer rowNum;
    
    /**
     * 그룹 코드
     */
    private String grpCd;
    
    /**
     * 상세 코드
     */
    private String dtlCd;
    
    /**
     * 상세 코드 명
     */
    private String dtlCdNm;
    
    /**
     * 상세 코드 설명
     */
    private String dtlCdDesc;
    
    /**
     * 정렬 순서
     */
    private Integer sortOrd;
    
    /**
     * 사용여부
     */
    private String useYn;
    
    /**
     * 등록자
     */
    private String regEmpId;
    
    /**
     * 등록일시
     */
    private String regDate;
    
    /**
     * 수정자
     */
    private String updEmpId;
    
    /**
     * 수정일시
     */
    private String updDate;
} 