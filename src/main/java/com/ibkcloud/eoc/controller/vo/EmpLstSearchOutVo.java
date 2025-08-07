package com.ibkcloud.eoc.controller.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.List;

/**
 * 직원 정보 검색 출력 VO
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmpLstSearchOutVo {
    
    private boolean success;
    private String message;
    private String errorCode;
    
    private List<EmpLstOutVo> data;
    private Integer count;
    
    // 검색 조건들
    private String searchKeyword;
    private String branchCd;
    private String jbclCd;
    private String jbttCd;
    private String hlofYn;
    private String deptCd;
    private String teamCd;
    private String empSts;
    private String hireDtFrom;
    private String hireDtTo;
    private String retireDtFrom;
    private String retireDtTo;
} 