package com.ERI.demo.vo;

import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;

@Data
public class EmpLstVO {
    private Integer rowNum;      // 순번 (ROW_NUMBER)
    private String empId;        // 직원번호 (PK)
    private String empNm;        // 직원명
    private String empDeptCd;    // 직원부서코드
    private String empPosCd;     // 직원직급코드
    private String empStsCd;     // 직원상태코드
    private String empEmail;     // 직원이메일

    private LocalDate rgstDt;    // 등록일자
    private LocalTime rgstTm;    // 등록시간
    private String rgstEmpId;    // 등록직원번호
    private LocalDate updtDt;    // 수정일자
    private LocalTime updtTm;    // 수정시간
    private String updtEmpId;    // 수정직원번호
    private String delYn;        // 삭제 여부 (Y: 삭제, N: 사용)
    private LocalDateTime delDate; // 삭제 일시
    private LocalDateTime regDate; // 등록 일시
    private LocalDateTime updDate; // 수정 일시
} 