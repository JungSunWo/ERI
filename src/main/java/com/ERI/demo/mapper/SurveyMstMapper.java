package com.ERI.demo.mapper;

import com.ERI.demo.vo.SurveyMstVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 설문조사 마스터 Mapper
 * @author ERI
 */
@Mapper
public interface SurveyMstMapper {
    
    /**
     * 설문조사 목록 조회
     */
    List<SurveyMstVO> selectSurveyMstList(SurveyMstVO surveyMstVO);
    
    /**
     * 설문조사 목록 개수 조회
     */
    int selectSurveyMstCount(SurveyMstVO surveyMstVO);
    
    /**
     * 설문조사 상세 조회
     */
    SurveyMstVO selectSurveyMstBySeq(@Param("surveySeq") Long surveySeq);
    
    /**
     * 설문조사 등록
     */
    int insertSurveyMst(SurveyMstVO surveyMstVO);
    
    /**
     * 설문조사 수정
     */
    int updateSurveyMst(SurveyMstVO surveyMstVO);
    
    /**
     * 설문조사 삭제
     */
    int deleteSurveyMst(@Param("surveySeq") Long surveySeq);
    
    /**
     * 설문조사 상태 변경
     */
    int updateSurveyMstStatus(@Param("surveySeq") Long surveySeq, @Param("surveyStsCd") String surveyStsCd);
    
    /**
     * 활성 설문조사 목록 조회 (사용자용)
     */
    List<SurveyMstVO> selectActiveSurveyMstList();
    
    /**
     * 응답 수 업데이트
     */
    int updateResponseCount(@Param("surveySeq") Long surveySeq);
    
    /**
     * 응답률 업데이트
     */
    int updateResponseRate(@Param("surveySeq") Long surveySeq);
} 