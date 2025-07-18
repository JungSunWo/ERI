package com.ERI.demo.vo;

import lombok.Data;
import java.time.LocalDate;

@Data
public class HbtChlgGoalVO {
    private String empId;
    private Long seq;
    private String goalCntn;
    private String goalAchvUnitCd;
    private LocalDate goalStrtDt;
    private LocalDate goalEndDt;
} 