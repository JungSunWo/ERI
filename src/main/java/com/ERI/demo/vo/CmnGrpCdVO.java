package com.ERI.demo.vo;

import lombok.Data;

/**
 * 공통 그룹 코드 VO
 */
@Data
public class CmnGrpCdVO {
    
    /**
     * 순번 (ROW_NUMBER)
     */
    private Integer rowNum;
    
    /**
     * 그룹 코드
     */
    private String grpCd;
    
    /**
     * 그룹 코드 명
     */
    private String grpCdNm;
    
    /**
     * 그룹 코드 설명
     */
    private String grpCdDesc;
    
    /**
     * 사용여부
     */
    private String useYn;
    
    /**
     * 등록자
     */
    private String rgstEmpId;
    
    /**
     * 등록일시
     */
    private String regDate;
    
    /**
     * 수정자
     */
    private String modEmpId;
    
    /**
     * 수정일시
     */
    private String modDate;
} 