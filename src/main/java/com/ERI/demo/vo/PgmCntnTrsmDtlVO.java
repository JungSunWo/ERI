package com.ERI.demo.vo;

import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class PgmCntnTrsmDtlVO {
    private String pgmId;         // 프로그램번호 (PK)
    private Long cntnSeq;         // 콘텐츠 일련번호 (PK)
    private String cntnCntn;      // 콘텐츠 내용
    private LocalDate trsmSchdDt; // 발송 예정일자
    private LocalTime trsmSchdTm; // 발송 예정시간
    private String trsmMdiCd;     // 발송 매체코드
    private String trsmYn;        // 발송여부 (Y/N)
} 