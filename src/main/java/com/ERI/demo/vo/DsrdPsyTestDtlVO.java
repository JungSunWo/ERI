package com.ERI.demo.vo;

import lombok.Data;
import java.time.LocalDate;

@Data
public class DsrdPsyTestDtlVO {
    private LocalDate appDt;        // 신청일자 (PK)
    private Long cnslAppSeq;        // 상담신청 일련번호 (PK)
    private Long cnslSeq;           // 상담 일련번호 (PK)
    private String psyTestCd;       // 심리검사 코드 (PK)
    private String psyTestRslt;     // 심리검사 결과
} 