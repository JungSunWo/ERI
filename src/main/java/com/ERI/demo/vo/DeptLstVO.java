package com.ERI.demo.vo;

import lombok.Data;

@Data
public class DeptLstVO {
    private Integer rowNum;  // 순번 (ROW_NUMBER)
    private String deptCd;  // 부서코드 (PK)
    private String deptNm;  // 부서내용
} 