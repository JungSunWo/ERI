package com.ibkcloud.eoc.controller.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * 관리자 정보 검색 입력 VO
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdminLstSearchInVo {
    
    private String keyword;
    private String adminLevel;
    private String adminSts;
    private String empId;
    private String empNm;
    private String startDtFrom;
    private String startDtTo;
    private String endDtFrom;
    private String endDtTo;
} 