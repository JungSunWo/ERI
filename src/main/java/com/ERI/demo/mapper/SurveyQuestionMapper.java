package com.ERI.demo.mapper;

import com.ERI.demo.vo.SurveyQuestionVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 설문조사 질문 Mapper
 * @author ERI
 */
@Mapper
public interface SurveyQuestionMapper {
    
    /**
     * 설문조사 질문 목록 조회
     */
    List<SurveyQuestionVO> selectSurveyQuestionList(@Param("surveySeq") Long surveySeq);
    
    /**
     * 설문조사 질문 상세 조회
     */
    SurveyQuestionVO selectSurveyQuestionBySeq(@Param("questionSeq") Long questionSeq);
    
    /**
     * 설문조사 질문 등록
     */
    int insertSurveyQuestion(SurveyQuestionVO surveyQuestionVO);
    
    /**
     * 설문조사 질문 수정
     */
    int updateSurveyQuestion(SurveyQuestionVO surveyQuestionVO);
    
    /**
     * 설문조사 질문 삭제
     */
    int deleteSurveyQuestion(@Param("questionSeq") Long questionSeq);
    
    /**
     * 설문조사 질문 순서 변경
     */
    int updateQuestionOrder(@Param("questionSeq") Long questionSeq, @Param("questionOrd") Integer questionOrd);
    
    /**
     * 설문조사 질문 개수 조회
     */
    int selectQuestionCount(@Param("surveySeq") Long surveySeq);
    
    /**
     * 설문조사 질문 순서 재정렬
     */
    int reorderQuestions(@Param("surveySeq") Long surveySeq);
} 