package com.ERI.demo.vo;

import lombok.Data;
import java.time.LocalDate;

@Data
public class SctLstVO {
    private Integer rowNum;  // 순번 (ROW_NUMBER)
    private Long seq;
    private String sctCd;
    private String stsCd;
    private LocalDate aplyDt;
    private String url;
} 