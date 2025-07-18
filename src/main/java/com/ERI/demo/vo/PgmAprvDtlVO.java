package com.ERI.demo.vo;

import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class PgmAprvDtlVO {
    private String empId;
    private String pgmId;
    private LocalDate trnsDt;
    private Long seq;
    private String aprvrEmpId;
    private String aprvTyCd;
    private String stsCd;
    private LocalTime aprvTm;
} 