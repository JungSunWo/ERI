package com.ibkcloud.eoc.controller.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * 공지사항 출력 VO
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NtiLstOutVo {
    
    private boolean success;
    private String message;
    private String errorCode;
    
    private Long seq;
    private String title;
    private String content;
    private String status;
    private String statusNm;
    private String noticeType;
    private String noticeTypeNm;
    private String importance;
    private String importanceNm;
    private String startDt;
    private String endDt;
    private String viewCnt;
    private String remark;
    
    // 시스템 필드들
    private String regEmpId;
    private String regDt;
    private String updEmpId;
    private String updDt;
} 