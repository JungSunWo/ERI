package com.ibkcloud.eoc.controller.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * 직원 정보 출력 VO
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmpLstOutVo {
    
    private boolean success;
    private String message;
    private String errorCode;
    
    private String eriEmpId;
    private String empId;
    private String empNm;
    private String branchCd;
    private String branchNm;
    private String jbclCd;
    private String jbclNm;
    private String jbttCd;
    private String jbttNm;
    private String deptCd;
    private String deptNm;
    private String teamCd;
    private String teamNm;
    private String hireDt;
    private String retireDt;
    private String hlofYn;
    private String hlofStrtDt;
    private String hlofEndDt;
    private String empSts;
    private String empStsNm;
    private String email;
    private String phone;
    private String mobile;
    private String address;
    private String birthDt;
    private String gender;
    private String marryYn;
    private String childCnt;
    private String remark;
    
    // 추가 필드들
    private Integer count;
    private Boolean connected;
    
    // 시스템 필드들
    private String regEmpId;
    private String regDt;
    private String updEmpId;
    private String updDt;
} 