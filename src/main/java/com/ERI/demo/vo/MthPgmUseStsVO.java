package com.ERI.demo.vo;

import lombok.Data;

@Data
public class MthPgmUseStsVO {
    private String baseYm;         // 기준년월 (PK)
    private String pgmId;          // 프로그램코드 (PK)
    private String deptCd;         // 부서코드 (PK)
    private String posCd;          // 직급코드 (PK)
    private String genderCd;       // 성별코드 (PK)
    private String empId;          // 직원번호 (PK)
    private Integer useCnt;        // 이용건수
} 