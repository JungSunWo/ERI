package com.ERI.demo.vo;

import lombok.Data;

/**
 * 권한그룹-권한 매핑 VO
 */
@Data
public class AuthGrpAuthMapVO {
    private Integer rowNum;      // 순번 (ROW_NUMBER)
    private String authGrpCd;    // 권한그룹코드
    private String authCd;       // 권한코드
    private String rgstDt;       // 등록일시
    private String updtDt;       // 수정일시
    private String delYn;        // 삭제여부

    public String getDelYn() {
        return delYn;
    }

    public void setDelYn(String delYn) {
        this.delYn = delYn;
    }
} 