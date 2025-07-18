package com.ERI.demo.vo;

import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class RmtCnslDtlVO {
    private LocalDate appDt;           // 신청일자 (PK)
    private Long cnslAppSeq;           // 상담신청 일련번호 (PK)
    private Long cmtSeq;               // 댓글 일련번호 (PK)
    private Long dtlSeq;               // 명세 일련번호 (PK)
    private String atchFilePath;       // 첨부파일 경로
    private String atchFileNm;         // 첨부파일 이름
    private LocalDate cmtRgstDt;       // 댓글 등록일자
    private LocalTime cmtRgstTm;       // 댓글 등록시간
    private String cmtRgstrEmpId;      // 댓글 등록직원번호
} 