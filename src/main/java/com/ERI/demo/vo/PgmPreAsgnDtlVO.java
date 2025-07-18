package com.ERI.demo.vo;

import lombok.Data;

@Data
public class PgmPreAsgnDtlVO {
    private String pgmId;       // 프로그램번호 (PK)
    private Long preAsgnSeq;    // 사전과제 일련번호 (PK)
    private String srvyItmCntn; // 설문조사항목내용
} 