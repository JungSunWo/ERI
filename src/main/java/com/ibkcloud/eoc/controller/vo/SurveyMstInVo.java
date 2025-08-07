package com.ibkcloud.eoc.controller.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 설문조사 마스터 입력 VO
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SurveyMstInVo {
    
    private Long surveySeq;
    
    @NotBlank(message = "설문조사 제목은 필수입니다.")
    @Size(max = 200, message = "설문조사 제목은 200자 이하여야 합니다.")
    private String surveyTitle;
    
    @Size(max = 1000, message = "설문조사 설명은 1000자 이하여야 합니다.")
    private String surveyDesc;
    
    @Size(max = 20, message = "설문조사 상태는 20자 이하여야 합니다.")
    private String surveyStatus;
    
    @Size(max = 100, message = "설문조사 상태명은 100자 이하여야 합니다.")
    private String surveyStatusNm;
    
    @Size(max = 20, message = "설문조사 유형은 20자 이하여야 합니다.")
    private String surveyType;
    
    @Size(max = 100, message = "설문조사 유형명은 100자 이하여야 합니다.")
    private String surveyTypeNm;
    
    @Size(max = 20, message = "시작일자는 20자 이하여야 합니다.")
    private String startDt;
    
    @Size(max = 20, message = "종료일자는 20자 이하여야 합니다.")
    private String endDt;
    
    @Size(max = 20, message = "대상자는 20자 이하여야 합니다.")
    private String targetUser;
    
    @Size(max = 100, message = "대상자명은 100자 이하여야 합니다.")
    private String targetUserNm;
    
    @Size(max = 20, message = "응답 수는 20자 이하여야 합니다.")
    private String responseCnt;
    
    @Size(max = 20, message = "최대 응답 수는 20자 이하여야 합니다.")
    private String maxResponseCnt;
    
    @Size(max = 1000, message = "비고는 1000자 이하여야 합니다.")
    private String remark;
    
    // 시스템 필드들
    private String regEmpId;
    private String updEmpId;
} 