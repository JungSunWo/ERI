package com.ERI.demo.mappers;

import com.ERI.demo.vo.SurveyMstVO;
import com.ERI.demo.vo.SurveyQuestionVO;
import com.ERI.demo.vo.SurveyChoiceVO;
import com.ERI.demo.vo.SurveyResponseVO;
import com.ERI.demo.vo.SurveyStatsVO;
import java.math.BigDecimal;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 설문조사 마스터 Mapper
 * @author ERI
 */
@Mapper
public interface SurveyMstMapper {
    
    /**
     * 설문조사 목록 조회 (페이징/검색/필터링)
     * @param params 조회 파라미터
     * @return 설문조사 목록
     */
    List<SurveyMstVO> selectSurveyList(Map<String, Object> params);
    
    /**
     * 설문조사 목록 총 건수 조회
     * @param params 조회 파라미터
     * @return 총 건수
     */
    int selectSurveyListCount(Map<String, Object> params);
    
    /**
     * 설문조사 상세 조회
     * @param surveySeq 설문조사 시퀀스
     * @return 설문조사 정보
     */
    SurveyMstVO selectSurveyBySeq(@Param("surveySeq") Long surveySeq);
    
    /**
     * 설문조사 등록
     * @param survey 설문조사 정보
     * @return 등록된 행 수
     */
    int insertSurvey(SurveyMstVO survey);
    
    /**
     * 설문조사 수정
     * @param survey 설문조사 정보
     * @return 수정된 행 수
     */
    int updateSurvey(SurveyMstVO survey);
    
    /**
     * 설문조사 삭제 (논리 삭제)
     * @param surveySeq 설문조사 시퀀스
     * @return 삭제된 행 수
     */
    int deleteSurvey(@Param("surveySeq") Long surveySeq);
    
    /**
     * 설문조사 응답 수 업데이트
     * @param surveySeq 설문조사 시퀀스
     * @return 업데이트된 행 수
     */
    int updateResponseCount(@Param("surveySeq") Long surveySeq);
    
    /**
     * 설문 질문 등록
     * @param question 질문 정보
     * @return 등록된 행 수
     */
    int insertSurveyQuestion(SurveyQuestionVO question);
    
    /**
     * 설문 선택지 등록
     * @param choice 선택지 정보
     * @return 등록된 행 수
     */
    int insertSurveyChoice(SurveyChoiceVO choice);
    
    /**
     * 설문 질문 목록 조회
     * @param surveySeq 설문조사 시퀀스
     * @return 질문 목록
     */
    List<SurveyQuestionVO> selectSurveyQuestions(@Param("surveySeq") Long surveySeq);
    
    /**
     * 설문 선택지 목록 조회
     * @param surveySeq 설문조사 시퀀스
     * @param questionSeq 질문 시퀀스
     * @return 선택지 목록
     */
    List<SurveyChoiceVO> selectSurveyChoices(@Param("surveySeq") Long surveySeq, @Param("questionSeq") Long questionSeq);
    
    /**
     * 설문 질문 삭제
     * @param surveySeq 설문조사 시퀀스
     * @return 삭제된 행 수
     */
    int deleteSurveyQuestions(@Param("surveySeq") Long surveySeq);
    
    /**
     * 설문 선택지 삭제 (특정 질문의 선택지)
     * @param surveySeq 설문조사 시퀀스
     * @param questionSeq 질문 시퀀스
     * @return 삭제된 행 수
     */
    int deleteSurveyChoices(@Param("surveySeq") Long surveySeq, @Param("questionSeq") Long questionSeq);
    
    /**
     * 설문 선택지 삭제 (모든 선택지)
     * @param surveySeq 설문조사 시퀀스
     * @return 삭제된 행 수
     */
    int deleteAllSurveyChoices(@Param("surveySeq") Long surveySeq);

    /**
     * 전체 대상자 수 조회 (함수 대체)
     * @return 전체 대상자 수
     */
    Integer selectTotalTargetCount();

    /**
     * 설문 응답자 수 조회 (함수 대체)
     * @param surveySeq 설문 시퀀스
     * @return 응답자 수
     */
    Integer selectResponseCount(@Param("surveySeq") Long surveySeq);

    /**
     * 설문 응답 점수 조회 (함수 대체)
     * @param surveySeq 설문 시퀀스
     * @param responseSeq 응답 시퀀스
     * @return 총점
     */
    BigDecimal selectResponseScore(@Param("surveySeq") Long surveySeq, @Param("responseSeq") Long responseSeq);

    /**
     * 설문 통계 삭제 (함수 대체)
     * @param surveySeq 설문 시퀀스
     */
    void deleteSurveyStats(@Param("surveySeq") Long surveySeq);

    /**
     * 설문 통계 계산 (함수 대체)
     * @param surveySeq 설문 시퀀스
     * @return 통계 목록
     */
    List<SurveyStatsVO> calculateSurveyStats(@Param("surveySeq") Long surveySeq);

    /**
     * 설문 통계 등록 (함수 대체)
     * @param stats 통계 정보
     */
    void insertSurveyStats(SurveyStatsVO stats);

    /**
     * 설문 응답 현황 조회 (함수 대체)
     * @param surveySeq 설문 시퀀스
     * @return 응답 현황
     */
    SurveyResponseVO selectSurveyResponseStatus(@Param("surveySeq") Long surveySeq);

    /**
     * 설문 통계 조회 (함수 대체)
     * @param surveySeq 설문 시퀀스
     * @return 통계 목록
     */
    List<SurveyStatsVO> selectSurveyStats(@Param("surveySeq") Long surveySeq);

    /**
     * 응답 상태 업데이트 (함수 대체)
     * @param surveySeq 설문 시퀀스
     * @param responseSeq 응답 시퀀스
     * @param status 상태
     */
    void updateResponseStatus(@Param("surveySeq") Long surveySeq, @Param("responseSeq") Long responseSeq, @Param("status") String status);
} 