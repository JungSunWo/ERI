package com.ibkcloud.eoc.controller.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 직원 정보 입력 VO
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmpLstInVo {
    
    private String eriEmpId;
    
    @NotBlank(message = "EMP_ID는 필수입니다.")
    @Size(max = 20, message = "EMP_ID는 20자 이하여야 합니다.")
    private String empId;
    
    @NotBlank(message = "직원명은 필수입니다.")
    @Size(max = 100, message = "직원명은 100자 이하여야 합니다.")
    private String empNm;
    
    @Size(max = 20, message = "부점코드는 20자 이하여야 합니다.")
    private String branchCd;
    
    @Size(max = 100, message = "부점명은 100자 이하여야 합니다.")
    private String branchNm;
    
    @Size(max = 20, message = "직급코드는 20자 이하여야 합니다.")
    private String jbclCd;
    
    @Size(max = 100, message = "직급명은 100자 이하여야 합니다.")
    private String jbclNm;
    
    @Size(max = 20, message = "직책코드는 20자 이하여야 합니다.")
    private String jbttCd;
    
    @Size(max = 100, message = "직책명은 100자 이하여야 합니다.")
    private String jbttNm;
    
    @Size(max = 20, message = "부서코드는 20자 이하여야 합니다.")
    private String deptCd;
    
    @Size(max = 100, message = "부서명은 100자 이하여야 합니다.")
    private String deptNm;
    
    @Size(max = 20, message = "팀코드는 20자 이하여야 합니다.")
    private String teamCd;
    
    @Size(max = 100, message = "팀명은 100자 이하여야 합니다.")
    private String teamNm;
    
    @Size(max = 20, message = "입사일자는 20자 이하여야 합니다.")
    private String hireDt;
    
    @Size(max = 20, message = "퇴사일자는 20자 이하여야 합니다.")
    private String retireDt;
    
    @Size(max = 20, message = "휴직여부는 20자 이하여야 합니다.")
    private String hlofYn;
    
    @Size(max = 20, message = "휴직시작일자는 20자 이하여야 합니다.")
    private String hlofStrtDt;
    
    @Size(max = 20, message = "휴직종료일자는 20자 이하여야 합니다.")
    private String hlofEndDt;
    
    @Size(max = 20, message = "재직상태는 20자 이하여야 합니다.")
    private String empSts;
    
    @Size(max = 20, message = "재직상태명은 20자 이하여야 합니다.")
    private String empStsNm;
    
    @Size(max = 20, message = "이메일은 20자 이하여야 합니다.")
    private String email;
    
    @Size(max = 20, message = "전화번호는 20자 이하여야 합니다.")
    private String phone;
    
    @Size(max = 20, message = "휴대폰번호는 20자 이하여야 합니다.")
    private String mobile;
    
    @Size(max = 500, message = "주소는 500자 이하여야 합니다.")
    private String address;
    
    @Size(max = 20, message = "생년월일은 20자 이하여야 합니다.")
    private String birthDt;
    
    @Size(max = 10, message = "성별은 10자 이하여야 합니다.")
    private String gender;
    
    @Size(max = 20, message = "결혼여부는 20자 이하여야 합니다.")
    private String marryYn;
    
    @Size(max = 20, message = "자녀수는 20자 이하여야 합니다.")
    private String childCnt;
    
    @Size(max = 1000, message = "비고는 1000자 이하여야 합니다.")
    private String remark;
} 