package com.ERI.demo.vo;

import lombok.Data;
import java.time.LocalDate;

@Data
public class ChlgGoalAchvDtlVO {
    private String empId;
    private Long seq;
    private LocalDate baseDt;
    private String goalAchvYn;
} 