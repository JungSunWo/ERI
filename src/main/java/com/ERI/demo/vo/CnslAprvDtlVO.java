package com.ERI.demo.vo;

import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class CnslAprvDtlVO {
    private LocalDate appDt;
    private Long cnslAppSeq;
    private LocalDate trnsDt;
    private String aprvrEmpId;
    private String aprvTyCd;
    private String stsCd;
    private LocalTime aprvTm;
} 