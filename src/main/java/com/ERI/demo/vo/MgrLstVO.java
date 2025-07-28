package com.ERI.demo.vo;

import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;

@Data
public class MgrLstVO {
    private Integer rowNum;      // 순번 (ROW_NUMBER)
    private String mgrEmpId;     // 관리자직원번호 (PK)
    private String mgrNm;        // 관리자명
    private String mgrDeptCd;    // 관리자부서코드
    private String mgrPosCd;     // 관리자직급코드
    private String mgrStsCd;     // 관리자상태코드
    private LocalDate rgstDt;    // 등록일자
    private LocalTime rgstTm;    // 등록시간
    private String regEmpId;     // 등록직원번호
    private LocalDate updtDt;    // 수정일자
    private LocalTime updtTm;    // 수정시간
    private String updEmpId;     // 수정직원번호
    private String delYn;        // 삭제 여부 (Y: 삭제, N: 사용)
    private LocalDateTime delDate; // 삭제 일시
    private LocalDateTime regDate; // 등록 일시
    private LocalDateTime updDate; // 수정 일시
} 