package com.ibkcloud.eoc.controller.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * 설문조사 응답 상세 입력 VO
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SurveyResponseDetailInVo {
    
    @NotNull(message = "설문조사 일련번호는 필수입니다.")
    private Long surveySeq;
    
    @NotNull(message = "응답 일련번호는 필수입니다.")
    private Long responseSeq;
    
    @NotNull(message = "질문 일련번호는 필수입니다.")
    private Long questionSeq;
    
    @Size(max = 20, message = "질문 유형은 20자 이하여야 합니다.")
    private String questionType;
    
    @Size(max = 100, message = "질문 유형명은 100자 이하여야 합니다.")
    private String questionTypeNm;
    
    @Size(max = 500, message = "질문 내용은 500자 이하여야 합니다.")
    private String questionContent;
    
    @Size(max = 20, message = "답변 일련번호는 20자 이하여야 합니다.")
    private String answerSeq;
    
    @Size(max = 500, message = "답변 내용은 500자 이하여야 합니다.")
    private String answerContent;
    
    @Size(max = 20, message = "답변 점수는 20자 이하여야 합니다.")
    private String answerScore;
    
    @Size(max = 1000, message = "비고는 1000자 이하여야 합니다.")
    private String remark;
    
    // 시스템 필드들
    private String regEmpId;
    private String updEmpId;
} 