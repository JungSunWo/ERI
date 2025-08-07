package com.ibkcloud.eoc.controller.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * 설문조사 마스터 출력 VO
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SurveyMstOutVo {
    
    private boolean success;
    private String message;
    private String errorCode;
    
    private Long surveySeq;
    private String surveyTitle;
    private String surveyDesc;
    private String surveyStatus;
    private String surveyStatusNm;
    private String surveyType;
    private String surveyTypeNm;
    private String startDt;
    private String endDt;
    private String targetUser;
    private String targetUserNm;
    private String responseCnt;
    private String maxResponseCnt;
    private String remark;
    
    // 시스템 필드들
    private String regEmpId;
    private String regDt;
    private String updEmpId;
    private String updDt;
} 