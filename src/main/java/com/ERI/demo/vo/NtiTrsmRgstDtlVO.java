package com.ERI.demo.vo;

import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class NtiTrsmRgstDtlVO {
    private LocalDate rgstDt;      // 등록일자 (PK)
    private Long seq;              // 일련번호 (PK)
    private Long ntiSeq;           // 공지사항 번호 (FK)
    private LocalDate trsmReqDt;   // 발송 요청일자
    private LocalTime trsmReqTm;   // 발송 요청시간
    private String trsmMdiCd;      // 발송 매체코드
    private String trsmYn;         // 발송여부 (Y/N)
} 