package com.ibkcloud.eoc.controller.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * 직원 정보 검색 입력 VO
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmpLstSearchInVo {
    
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