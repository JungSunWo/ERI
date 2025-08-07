package com.ERI.demo.mapper;

import com.ERI.demo.vo.SurveyResponseVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 설문조사 응답 Mapper
 * @author ERI
 */
@Mapper
public interface SurveyResponseMapper {
    
    /**
     * 설문조사 응답 목록 조회
     */
    List<SurveyResponseVO> selectSurveyResponseList(SurveyResponseVO surveyResponseVO);
    
    /**
     * 설문조사 응답 목록 개수 조회
     */
    int selectSurveyResponseCount(SurveyResponseVO surveyResponseVO);
    
    /**
     * 설문조사 응답 상세 조회
     */
    SurveyResponseVO selectSurveyResponseBySeq(@Param("responseSeq") Long responseSeq);
    
    /**
     * 설문조사 응답 등록
     */
    int insertSurveyResponse(SurveyResponseVO surveyResponseVO);
    
    /**
     * 설문조사 응답 수정
     */
    int updateSurveyResponse(SurveyResponseVO surveyResponseVO);
    
    /**
     * 설문조사 응답 삭제
     */
    int deleteSurveyResponse(@Param("responseSeq") Long responseSeq);
    
    /**
     * 사용자별 설문조사 응답 조회
     */
    SurveyResponseVO selectUserSurveyResponse(@Param("surveySeq") Long surveySeq, @Param("empNo") String empNo);
    
    /**
     * 설문조사 응답 상태 변경
     */
    int updateResponseStatus(@Param("responseSeq") Long responseSeq, @Param("responseStsCd") String responseStsCd);
    
    /**
     * 설문조사 응답 완료 처리
     */
    int completeSurveyResponse(@Param("responseSeq") Long responseSeq);
    
    /**
     * 설문조사 응답 중복 체크
     */
    int checkDuplicateResponse(@Param("surveySeq") Long surveySeq, @Param("empNo") String empNo);
} 