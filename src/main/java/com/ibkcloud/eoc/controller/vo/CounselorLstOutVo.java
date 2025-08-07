package com.ibkcloud.eoc.controller.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * 상담사 정보 출력 VO
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CounselorLstOutVo {
    
    private boolean success;
    private String message;
    private String errorCode;
    
    private String counselorEmpId;
    private String empId;
    private String empNm;
    private String counselorInfoClsfCd;
    private String counselorInfoClsfNm;
    private String counselorSts;
    private String counselorStsNm;
    private String counselorLevel;
    private String counselorLevelNm;
    private String counselorSpecialty;
    private String counselorSpecialtyNm;
    private String startDt;
    private String endDt;
    private String remark;
    
    // 시스템 필드들
    private String regEmpId;
    private String regDt;
    private String updEmpId;
    private String updDt;
} 