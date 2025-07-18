package com.ERI.demo.vo;

import lombok.Data;
import java.time.LocalDate;

@Data
public class RmtCnslVO {
    private LocalDate appDt;       // 신청일자 (PK)
    private Long cnslAppSeq;       // 상담신청 일련번호 (PK)
    private Long cmtSeq;           // 댓글 일련번호 (PK)
    private String cmtCntn;        // 댓글 내용
    private Integer cmtLen;        // 댓글 길이
    private String atchFileYn;     // 첨부파일여부 (Y/N)
    private String alrmTrsmYn;     // 알림발송여부 (Y/N)
} 