package com.ERI.demo.vo;

import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class GnrlCnslDtlVO {
    private LocalDate appDt;          // 신청일자 (PK)
    private Long cnslAppSeq;          // 상담신청 일련번호 (PK)
    private Long cnslSeq;             // 상담 일련번호 (PK)
    private Long dtlSeq;              // 명세 일련번호 (PK)
    private String cnslrEmpId;        // 상담 직원번호
    private String atchFilePath;      // 첨부파일 경로
    private String atchFileNm;        // 첨부파일 이름
    private LocalDate appDt2;         // 상담일자
    private LocalTime cnslStrtTm;     // 상담시작시간
    private LocalTime cnslEndTm;      // 상담종료시간
} 