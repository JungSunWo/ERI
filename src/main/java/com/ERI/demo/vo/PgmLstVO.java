package com.ERI.demo.vo;

import lombok.Data;
import java.time.LocalDate;

@Data
public class PgmLstVO {
    private Integer rowNum;        // 순번 (ROW_NUMBER)
    private Long seq;              // 일련번호 (PK)
    private String pgmTyCd;        // 프로그램 종류코드 (FK)
    private String ttl;            // 제목
    private String cntn;           // 내용
    private LocalDate recrtStrtDt; // 모집기간 시작일자
    private LocalDate recrtEndDt;  // 모집기간 종료일자
    private LocalDate oprtStrtDt;  // 운영기간 시작일자
    private LocalDate oprtEndDt;   // 운영기간 종료일자
    private String pgmAppUnitCd;   // 프로그램 신청단위코드
    private Integer recrtPplCnt;   // 모집인원수
    private String cntnTrsmReqYn;  // 콘텐츠발송필요여부
    private String preAsgnRgstYn;  // 사전과제등록여부
} 