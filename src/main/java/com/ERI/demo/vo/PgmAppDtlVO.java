package com.ERI.demo.vo;

import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class PgmAppDtlVO {
    private String empId;
    private String pgmId;
    private LocalDate appDt;
    private String appTyCd;
    private String stsCd;
    private String preAsgnCmplYn;
    private LocalTime appTm;
} 