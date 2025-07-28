package com.ERI.demo.vo;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 설문조사 통계 VO
 */
@Data
public class SurveyStatsVO {
    
    private Long surveySeq;              // 설문 시퀀스
    private Long questionSeq;            // 질문 시퀀스
    private Long choiceSeq;              // 선택지 시퀀스
    private Integer responseCnt;         // 응답 수
    private BigDecimal responseRate;     // 응답률 (%)
    private BigDecimal avgScore;         // 평균 점수
    private BigDecimal minScore;         // 최소 점수
    private BigDecimal maxScore;         // 최대 점수
    private BigDecimal stdDevScore;      // 표준편차
    private LocalDateTime lastUpdDate;   // 마지막 업데이트 일시
    
    // 추가 정보 (JOIN 시 사용)
    private String questionTtl;          // 질문 제목
    private String choiceTtl;            // 선택지 제목
    private String questionTyCd;         // 질문 타입 코드
} 