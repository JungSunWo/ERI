package com.ERI.demo.vo;

import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;

@Data
public class CnslrLstVO {
    private Integer rowNum;       // 순번 (ROW_NUMBER)
    private String cnslrEmpId;    // 상담사직원번호 (PK)
    private String cnslrNm;       // 상담사명
    private String cnslrDeptCd;   // 상담사부서코드
    private String cnslrPosCd;    // 상담사직급코드
    private String cnslrStsCd;    // 상담사상태코드
    private String cnslrSpecCd;   // 상담사전문분야코드
    private LocalDate rgstDt;     // 등록일자
    private LocalTime rgstTm;     // 등록시간
    private String rgstEmpId;     // 등록직원번호
    private LocalDate updtDt;     // 수정일자
    private LocalTime updtTm;     // 수정시간
    private String updtEmpId;     // 수정직원번호
    private String delYn;         // 삭제 여부 (Y: 삭제, N: 사용)
    private LocalDateTime delDate; // 삭제 일시
    private LocalDateTime regDate; // 등록 일시
    private LocalDateTime updDate; // 수정 일시
} 