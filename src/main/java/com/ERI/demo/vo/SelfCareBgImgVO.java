package com.ERI.demo.vo;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class SelfCareBgImgVO {
    private Integer rowNum;  // 순번 (ROW_NUMBER)
    private Long seq;
    private String bgImgCd;
    private String bgImgUrl;
    private String bgImgDesc;
    private String delYn;
    private LocalDateTime delDate;
    private String regEmpId; // 등록직원번호
    private String updEmpId; // 수정직원번호
    private LocalDateTime regDate;
    private LocalDateTime updDate;
} 