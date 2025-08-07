package com.ibkcloud.eoc.controller.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 관리자 정보 입력 VO
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdminLstInVo {
    
    private String adminId;
    
    @NotBlank(message = "EMP_ID는 필수입니다.")
    @Size(max = 20, message = "EMP_ID는 20자 이하여야 합니다.")
    private String empId;
    
    @NotBlank(message = "관리자 레벨은 필수입니다.")
    @Size(max = 10, message = "관리자 레벨은 10자 이하여야 합니다.")
    private String adminLevel;
    
    @Size(max = 100, message = "관리자 레벨명은 100자 이하여야 합니다.")
    private String adminLevelNm;
    
    @Size(max = 20, message = "관리자 상태는 20자 이하여야 합니다.")
    private String adminSts;
    
    @Size(max = 100, message = "관리자 상태명은 100자 이하여야 합니다.")
    private String adminStsNm;
    
    @Size(max = 20, message = "시작일자는 20자 이하여야 합니다.")
    private String startDt;
    
    @Size(max = 20, message = "종료일자는 20자 이하여야 합니다.")
    private String endDt;
    
    @Size(max = 1000, message = "비고는 1000자 이하여야 합니다.")
    private String remark;
    
    // 시스템 필드들
    private String regEmpId;
    private String updEmpId;
} 