package com.ERI.demo.vo;

import lombok.Data;
import java.time.LocalDate;

@Data
public class NtiTrsmTrgtDtlVO {
    private LocalDate rgstDt;  // 등록일자 (PK)
    private Long seq;          // 일련번호 (PK)
    private String empId;      // 직원번호 (PK)
    private Long ntiSeq;       // 공지사항 번호 (FK)
} 