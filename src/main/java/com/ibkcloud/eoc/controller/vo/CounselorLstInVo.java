package com.ibkcloud.eoc.controller.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 상담사 정보 입력 VO
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CounselorLstInVo {
    
    private String counselorEmpId;
    
    @NotBlank(message = "상담사 EMP_ID는 필수입니다.")
    @Size(max = 20, message = "상담사 EMP_ID는 20자 이하여야 합니다.")
    private String empId;
    
    @NotBlank(message = "상담사 정보 분류 코드는 필수입니다.")
    @Size(max = 20, message = "상담사 정보 분류 코드는 20자 이하여야 합니다.")
    private String counselorInfoClsfCd;
    
    @Size(max = 100, message = "상담사 정보 분류명은 100자 이하여야 합니다.")
    private String counselorInfoClsfNm;
    
    @Size(max = 20, message = "상담사 상태는 20자 이하여야 합니다.")
    private String counselorSts;
    
    @Size(max = 100, message = "상담사 상태명은 100자 이하여야 합니다.")
    private String counselorStsNm;
    
    @Size(max = 20, message = "상담사 레벨은 20자 이하여야 합니다.")
    private String counselorLevel;
    
    @Size(max = 100, message = "상담사 레벨명은 100자 이하여야 합니다.")
    private String counselorLevelNm;
    
    @Size(max = 20, message = "상담사 전문분야는 20자 이하여야 합니다.")
    private String counselorSpecialty;
    
    @Size(max = 100, message = "상담사 전문분야명은 100자 이하여야 합니다.")
    private String counselorSpecialtyNm;
    
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