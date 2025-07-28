package com.ERI.demo.vo.employee;

import lombok.Data;
import org.apache.ibatis.type.Alias;
import java.time.LocalDateTime;
import java.time.LocalDate;

@Data
@Alias("EmployeeEmpLstVO")
public class EmpLstVO {
    private Integer rowNum;      // 순번 (ROW_NUMBER)
    private String empId;        // 실제 직원번호 (외부 시스템에서 가져온 원본) (PK)
    private String eriEmpId;     // ERI 시스템 내부 식별번호 (E00000001 형식)
    private String empNm;        // 직원명 (emn)
    private String gndrDcd;      // 성별구분코드 (M:남성, F:여성)
    private String hlofYn;       // 재직여부 (Y:재직, N:퇴직)
    private LocalDate etbnYmd;   // 입행년월일 (은행 입행일자)
    private String blngBrcd;     // 소속부점코드
    private String beteamCd;     // 소속팀코드
    private LocalDate exigBlngYmd; // 현소속년월일 (현재 소속 발령일자)
    private String trthWorkBrcd; // 실제근무부점코드
    private String rswrBrcd;     // 주재근무부점코드
    private String jbttCd;       // 직위코드
    private LocalDate jbttYmd;   // 직위년월일 (직위 발령일자)
    private String nameNm;       // 호칭명
    private String jbclCd;       // 직급코드 (1:11급, 2:22급, 3:33급, 4:44급, 5:55급, 6:66급, 9:99급)
    private LocalDate trthBirtYmd; // 실제생년월일
    private LocalDate slcnUncgBirtYmd; // 양력환산생년월일
    private String inslDcd;      // 음양구분코드 (S:양력, L:음력)
    private String empCpn;       // 직원휴대폰번호
    private String empExtiNo;    // 직원내선번호
    private String ead;          // 이메일주소

    private String delYn;        // 삭제 여부 (Y: 삭제, N: 사용)
    private LocalDateTime delDate; // 삭제 일시
    private LocalDateTime regDate; // 등록 일시
    private LocalDateTime updDate; // 수정 일시
} 