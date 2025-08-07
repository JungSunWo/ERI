package com.ibkcloud.eoc.controller.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * 설문조사 응답 상세 출력 VO
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SurveyResponseDetailOutVo {
    
    private boolean success;
    private String message;
    private String errorCode;
    
    private Long surveySeq;
    private Long responseSeq;
    private Long questionSeq;
    private String questionType;
    private String questionTypeNm;
    private String questionContent;
    private String answerSeq;
    private String answerContent;
    private String answerScore;
    private String remark;
    
    // 시스템 필드들
    private String regEmpId;
    private String regDt;
    private String updEmpId;
    private String updDt;
} 