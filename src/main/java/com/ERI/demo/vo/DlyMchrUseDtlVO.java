package com.ERI.demo.vo;

import lombok.Data;
import java.time.LocalDate;

@Data
public class DlyMchrUseDtlVO {
    private LocalDate baseDt;          // 기준일자 (PK)
    private String mchrTyCd;           // 마음검진구분코드 (PK)
    private String empId;              // 직원번호 (PK)
    private Long seq;                  // 일련번호 (PK)
    private String empNm;              // 직원성명
    private String posCd;              // 직급코드
    private String genderCd;           // 성별코드
    private String belongDeptCd;       // 소속부점코드
    private String belongDeptNm;       // 소속부점명
    private String deptTyCd;           // 부점구분코드
} 