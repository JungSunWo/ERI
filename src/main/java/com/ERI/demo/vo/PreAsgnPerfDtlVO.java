package com.ERI.demo.vo;

import lombok.Data;
import java.time.LocalDate;

@Data
public class PreAsgnPerfDtlVO {
    private String empId;
    private String pgmId;
    private Long preAsgnSeq;
    private LocalDate perfDt;
    private String srvyItmCntn;
    private String srvyAnsCntn;
    private String stsCd;
} 