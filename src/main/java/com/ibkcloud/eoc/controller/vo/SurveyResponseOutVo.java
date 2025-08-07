package com.ibkcloud.eoc.controller.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.List;

/**
 * 설문조사 응답 출력 VO
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SurveyResponseOutVo {
    
    private boolean success;
    private String message;
    private String errorCode;
    
    private Long responseSeq;
    private Long surveySeq;
    private String respondentId;
    private String respondentNm;
    private String responseStatus;
    private String responseStatusNm;
    private String responseDt;
    private String responseContent;
    private String remark;
    
    // 설문조사 응답 상세 목록
    private List<SurveyResponseDetailOutVo> responseDetails;
    
    // 시스템 필드들
    private String regEmpId;
    private String regDt;
    private String updEmpId;
    private String updDt;
} 