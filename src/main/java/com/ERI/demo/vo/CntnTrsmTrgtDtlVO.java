package com.ERI.demo.vo;

import lombok.Data;

@Data
public class CntnTrsmTrgtDtlVO {
    private String pgmId;   // 프로그램번호 (PK)
    private Long cntnSeq;   // 콘텐츠번호 (PK)
    private String empId;   // 직원번호 (PK)
} 