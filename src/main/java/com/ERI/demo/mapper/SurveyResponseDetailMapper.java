package com.ERI.demo.mapper;

import com.ERI.demo.vo.SurveyResponseDetailVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 설문조사 응답 상세 Mapper
 * @author ERI
 */
@Mapper
public interface SurveyResponseDetailMapper {
    
    /**
     * 설문조사 응답 상세 목록 조회
     */
    List<SurveyResponseDetailVO> selectSurveyResponseDetailList(@Param("responseSeq") Long responseSeq);
    
    /**
     * 설문조사 응답 상세 상세 조회
     */
    SurveyResponseDetailVO selectSurveyResponseDetailBySeq(@Param("responseDetailSeq") Long responseDetailSeq);
    
    /**
     * 설문조사 응답 상세 등록
     */
    int insertSurveyResponseDetail(SurveyResponseDetailVO surveyResponseDetailVO);
    
    /**
     * 설문조사 응답 상세 수정
     */
    int updateSurveyResponseDetail(SurveyResponseDetailVO surveyResponseDetailVO);
    
    /**
     * 설문조사 응답 상세 삭제
     */
    int deleteSurveyResponseDetail(@Param("responseDetailSeq") Long responseDetailSeq);
    
    /**
     * 응답별 상세 목록 삭제
     */
    int deleteResponseDetailsByResponseSeq(@Param("responseSeq") Long responseSeq);
    
    /**
     * 질문별 응답 상세 조회
     */
    List<SurveyResponseDetailVO> selectResponseDetailsByQuestion(@Param("surveySeq") Long surveySeq, @Param("questionSeq") Long questionSeq);
    
    /**
     * 선택지별 응답 상세 조회
     */
    List<SurveyResponseDetailVO> selectResponseDetailsByChoice(@Param("surveySeq") Long surveySeq, @Param("choiceSeq") Long choiceSeq);
} 