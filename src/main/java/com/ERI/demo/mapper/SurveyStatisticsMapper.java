package com.ERI.demo.mapper;

import com.ERI.demo.vo.SurveyStatsVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 설문조사 통계 Mapper
 * @author ERI
 */
@Mapper
public interface SurveyStatisticsMapper {
    
    /**
     * 설문조사 통계 목록 조회
     */
    List<SurveyStatsVO> selectSurveyStatisticsList(@Param("surveySeq") Long surveySeq);
    
    /**
     * 질문별 통계 조회
     */
    List<SurveyStatsVO> selectQuestionStatistics(@Param("surveySeq") Long surveySeq, @Param("questionSeq") Long questionSeq);
    
    /**
     * 선택지별 통계 조회
     */
    SurveyStatsVO selectChoiceStatistics(@Param("surveySeq") Long surveySeq, @Param("choiceSeq") Long choiceSeq);
    
    /**
     * 설문조사 통계 등록
     */
    int insertSurveyStatistics(SurveyStatsVO surveyStatsVO);
    
    /**
     * 설문조사 통계 수정
     */
    int updateSurveyStatistics(SurveyStatsVO surveyStatsVO);
    
    /**
     * 설문조사 통계 삭제
     */
    int deleteSurveyStatistics(@Param("surveySeq") Long surveySeq);
    
    /**
     * 설문조사 통계 계산 및 업데이트
     */
    int calculateAndUpdateStatistics(@Param("surveySeq") Long surveySeq);
    
    /**
     * 설문조사 전체 통계 조회
     */
    SurveyStatsVO selectSurveyOverallStatistics(@Param("surveySeq") Long surveySeq);
    
    /**
     * 설문조사 응답률 통계 조회
     */
    SurveyStatsVO selectSurveyResponseRateStatistics(@Param("surveySeq") Long surveySeq);
} 