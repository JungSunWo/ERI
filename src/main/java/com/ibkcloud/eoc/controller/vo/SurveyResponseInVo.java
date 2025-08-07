package com.ibkcloud.eoc.controller.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * 설문조사 응답 입력 VO
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SurveyResponseInVo {
    
    @NotNull(message = "설문조사 일련번호는 필수입니다.")
    private Long surveySeq;
    
    @Size(max = 20, message = "응답자 ID는 20자 이하여야 합니다.")
    private String respondentId;
    
    @Size(max = 100, message = "응답자명은 100자 이하여야 합니다.")
    private String respondentNm;
    
    @Size(max = 20, message = "응답 상태는 20자 이하여야 합니다.")
    private String responseStatus;
    
    @Size(max = 100, message = "응답 상태명은 100자 이하여야 합니다.")
    private String responseStatusNm;
    
    @Size(max = 20, message = "응답 일시는 20자 이하여야 합니다.")
    private String responseDt;
    
    @Size(max = 1000, message = "응답 내용은 1000자 이하여야 합니다.")
    private String responseContent;
    
    @Size(max = 1000, message = "비고는 1000자 이하여야 합니다.")
    private String remark;
    
    // 설문조사 응답 상세 목록
    private List<SurveyResponseDetailInVo> responseDetails;
    
    // 시스템 필드들
    private String regEmpId;
    private String updEmpId;
} 