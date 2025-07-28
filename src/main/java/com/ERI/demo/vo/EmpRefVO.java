package com.ERI.demo.vo;

import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 직원 참조 정보 VO
 * TB_EMP_REF 테이블과 매핑
 */
@Data
public class EmpRefVO {
    
    private String eriEmpId;           // ERI 시스템 내부 식별번호
    private String empNm;              // 직원명
    private String gndrDcd;            // 성별구분코드 (M:남성, F:여성)
    private String hlofYn;             // 재직여부 (Y:재직, N:퇴직)
    private LocalDate etbnYmd;         // 입행년월일
    private String blngBrcd;           // 소속부점코드
    private String beteamCd;           // 소속팀코드
    private LocalDate exigBlngYmd;     // 현소속년월일
    private String trthWorkBrcd;       // 실제근무부점코드
    private String rswrBrcd;           // 주재근무부점코드
    private String jbttCd;             // 직위코드
    private LocalDate jbttYmd;         // 직위년월일
    private String nameNm;             // 호칭명
    private String jbclCd;             // 직급코드
    private LocalDate trthBirtYmd;     // 실제생년월일
    private LocalDate slcnUncgBirtYmd; // 양력환산생년월일
    private String inslDcd;            // 음양구분코드 (S:양력, L:음력)
    private String empCpn;             // 직원휴대폰번호
    private String empExtiNo;          // 직원내선번호
    private String ead;                // 이메일주소
    private String delYn;              // 삭제여부 (실제 삭제에서는 사용하지 않음)
    private LocalDateTime delDate;     // 삭제일시 (실제 삭제에서는 사용하지 않음)
    private LocalDateTime regDate;     // 등록일시
    private LocalDateTime updDate;     // 수정일시
} 