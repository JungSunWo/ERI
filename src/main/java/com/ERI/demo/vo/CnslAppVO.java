package com.ERI.demo.vo;

import lombok.Data;
import java.time.LocalDate;

@Data
public class CnslAppVO {
    private LocalDate appDt;
    private Long seq;
    private String cnslTyCd;
    private LocalDate dsrdCnslDt;
    private String grvncTpcCd;
    private String dsrdPsyTestCd;
    private String cnslLctnCd;
    private String cnslrEmpId;
    private String stsCd;
    private String ttl;
    private String cntn;
    private String aprvYn;
    private String aplcntEmpMgmtId;
} 