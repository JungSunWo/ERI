package com.ERI.demo.vo;

import lombok.Data;
import java.time.LocalDate;

@Data
public class DlyMchrUseStsVO {
    private LocalDate baseDt;      // 기준일자 (PK)
    private String mchrTyCd;       // 마음검진종류코드 (PK)
    private String deptCd;         // 부서코드 (PK)
    private String posCd;          // 직급코드 (PK)
    private String genderCd;       // 성별코드 (PK)
    private String empId;          // 직원번호 (PK)
    private Integer useCnt;        // 이용건수
} 