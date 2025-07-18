package com.ERI.demo.vo;

import lombok.Data;
import java.time.LocalDate;

@Data
public class GnrlCnslVO {
    private LocalDate appDt;       // 신청일자 (PK)
    private Long cnslAppSeq;       // 상담신청 일련번호 (PK)
    private Long cnslSeq;          // 상담 일련번호 (PK)
    private String cntn;           // 상담 내용
    private String reCnslYn;       // 재상담여부 (Y/N)
    private String atchFileYn;     // 첨부파일여부 (Y/N)
} 