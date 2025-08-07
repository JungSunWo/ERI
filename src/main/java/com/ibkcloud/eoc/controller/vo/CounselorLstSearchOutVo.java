package com.ibkcloud.eoc.controller.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.List;

/**
 * 상담사 정보 검색 출력 VO
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CounselorLstSearchOutVo {
    
    private boolean success;
    private String message;
    private String errorCode;
    
    private List<CounselorLstOutVo> data;
    private Integer count;
    private Integer pageNo;
    private Integer pageSize;
    private Integer ttalPageNbi;
    
    // 검색 조건들
    private String keyword;
    private String sortBy;
    private String sortDirection;
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