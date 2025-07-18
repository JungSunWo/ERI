package com.ERI.demo.vo;

import lombok.Data;

@Data
public class AuthGrpLstVO {
    private Integer rowNum;      // 순번 (ROW_NUMBER)
    private String authGrpCd;    // 권한그룹코드 (PK)
    private String authGrpNm;    // 권한그룹명
    private String authGrpDesc;  // 권한그룹 설명
    private String rgstDt;       // 등록일시
    private String updtDt;       // 수정일시
} 