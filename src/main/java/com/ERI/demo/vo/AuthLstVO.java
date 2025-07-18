package com.ERI.demo.vo;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class AuthLstVO {
    private Integer rowNum;          // 순번 (ROW_NUMBER)
    private String authCd;           // 권한코드 (PK)
    private String authNm;           // 권한명
    private String authDesc;         // 권한 설명
    private Integer authLvl;         // 권한레벨 (1~10, 숫자가 클수록 높은 권한)
    private LocalDateTime regDate;   // 등록일시
    private LocalDateTime updDate;   // 수정일시
} 