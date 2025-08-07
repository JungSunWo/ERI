package com.ibkcloud.eoc.controller.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * 설문조사 통계 출력 VO
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SurveyStatisticsOutVo {
    
    private boolean success;
    private String message;
    private String errorCode;
    
    private Long surveySeq;
    private String surveyTitle;
    private String totalResponses;
    private String completionRate;
    private String averageScore;
    private String responseDistribution;
    
    // 통계 데이터
    private Map<String, Object> statistics;
    private List<Map<String, Object>> questionStatistics;
    private List<Map<String, Object>> responseTrends;
    
    // 시스템 필드들
    private String regEmpId;
    private String regDt;
    private String updEmpId;
    private String updDt;
} 