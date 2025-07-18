package com.ERI.demo.vo;

import lombok.Data;
import java.time.LocalDate;

@Data
public class DlyEmpRgtUseStsVO {
    private LocalDate baseDt;      // 기준일자 (PK)
    private String empId;          // 직원번호 (PK)
    private String deptCd;         // 부서코드 (PK)
    private String posCd;          // 직급코드 (PK)
    private String genderCd;       // 성별코드 (PK)
    private Integer pgmUseCnt;     // 프로그램 이용건수
    private Integer mchrUseCnt;    // 마음검진 이용건수
    private Integer cnslUseCnt;    // 상담 이용건수
} 