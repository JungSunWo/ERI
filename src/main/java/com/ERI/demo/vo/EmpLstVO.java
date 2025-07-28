package com.ERI.demo.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.AccessLevel;
import java.time.LocalDateTime;

@Getter
@Setter
public class EmpLstVO {
    private Integer rowNum;      // 순번 (ROW_NUMBER)
    private String eriEmpId;     // ERI 직원ID (PK)
    private String empId;        // 직원번호 (PK) - 실제 값은 eriEmpId로 대체
    private String empNm;        // 직원명
    private String empDeptCd;    // 직원부서코드
    private String empPosCd;     // 직원직급코드
    private String empStsCd;     // 직원상태코드
    private String empEmail;     // 직원이메일

    private String delYn;        // 삭제 여부 (Y: 삭제, N: 사용)
    private LocalDateTime delDate; // 삭제 일시
    private LocalDateTime regDate; // 등록 일시
    private LocalDateTime updDate; // 수정 일시
    
    /**
     * 실제 직원번호 대신 ERI 직원ID를 반환 (보안상 실제 직원번호 노출 방지)
     */
    public String getEmpId() {
        return this.eriEmpId;
    }
} 