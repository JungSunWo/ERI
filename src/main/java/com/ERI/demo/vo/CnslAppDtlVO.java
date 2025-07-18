package com.ERI.demo.vo;

import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class CnslAppDtlVO {
    private LocalDate appDt;
    private Long cnslAppSeq;
    private LocalDate trnsDt;
    private Long trnsSeq;
    private String appTyCd;
    private String stsCd;
    private LocalTime appTm;
} 