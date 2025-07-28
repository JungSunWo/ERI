package com.ERI.demo.mappers;

import com.ERI.demo.vo.SurveyResponseVO;
import com.ERI.demo.vo.SurveyResponseDetailVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 설문조사 응답 Mapper
 * @author ERI
 */
@Mapper
public interface SurveyResponseMapper {
    
    /**
     * 설문조사 응답 등록
     */
    int insertSurveyResponse(SurveyResponseVO surveyResponse);
    
    /**
     * 설문조사 응답 상세 등록
     */
    int insertSurveyResponseDetail(SurveyResponseDetailVO responseDetail);
    
    /**
     * 설문조사 응답 수정
     */
    int updateSurveyResponse(SurveyResponseVO surveyResponse);
    
    /**
     * 설문조사 응답 조회
     */
    SurveyResponseVO selectSurveyResponseBySeq(@Param("responseSeq") Long responseSeq);
    
    /**
     * 설문조사별 응답 목록 조회
     */
    List<SurveyResponseVO> selectSurveyResponsesBySurveySeq(@Param("surveySeq") Long surveySeq, 
                                                           @Param("params") Map<String, Object> params);
    
    /**
     * 설문조사 응답 상세 목록 조회
     */
    List<SurveyResponseDetailVO> selectResponseDetailsByResponseSeq(@Param("responseSeq") Long responseSeq);
    
    /**
     * 설문조사 응답 개수 조회
     */
    int countSurveyResponses(@Param("surveySeq") Long surveySeq, @Param("params") Map<String, Object> params);
    
    /**
     * 직원별 설문조사 응답 여부 확인
     */
    SurveyResponseVO selectResponseBySurveyAndEmp(@Param("surveySeq") Long surveySeq, 
                                                 @Param("empNo") String empNo);
    
    /**
     * 설문조사 응답 삭제 (논리 삭제)
     */
    int deleteSurveyResponse(@Param("responseSeq") Long responseSeq, @Param("empId") String empId);
    
    /**
     * 설문조사 응답 상세 삭제 (논리 삭제)
     */
    int deleteSurveyResponseDetails(@Param("responseSeq") Long responseSeq, @Param("empId") String empId);
} 