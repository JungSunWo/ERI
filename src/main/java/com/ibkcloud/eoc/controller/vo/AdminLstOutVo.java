package com.ibkcloud.eoc.controller.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * 관리자 정보 출력 VO
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdminLstOutVo {
    
    private boolean success;
    private String message;
    private String errorCode;
    
    private String adminId;
    private String empId;
    private String empNm;
    private String adminLevel;
    private String adminLevelNm;
    private String adminSts;
    private String adminStsNm;
    private String startDt;
    private String endDt;
    private String remark;
    
    // 시스템 필드들
    private String regEmpId;
    private String regDt;
    private String updEmpId;
    private String updDt;
} 