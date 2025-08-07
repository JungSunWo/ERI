package com.ibkcloud.eoc.controller.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * 상담사 정보 검색 입력 VO
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CounselorLstSearchInVo {
    
    private String keyword;
    private String counselorInfoClsfCd;
    private String counselorSts;
    private String counselorLevel;
    private String counselorSpecialty;
    private String empId;
    private String empNm;
    private String startDtFrom;
    private String startDtTo;
    private String endDtFrom;
    private String endDtTo;
} 