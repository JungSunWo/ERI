package com.ERI.demo.mapper;

import com.ERI.demo.vo.SurveyChoiceVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 설문조사 선택지 Mapper
 * @author ERI
 */
@Mapper
public interface SurveyChoiceMapper {
    
    /**
     * 설문조사 선택지 목록 조회
     */
    List<SurveyChoiceVO> selectSurveyChoiceList(@Param("questionSeq") Long questionSeq);
    
    /**
     * 설문조사 선택지 상세 조회
     */
    SurveyChoiceVO selectSurveyChoiceBySeq(@Param("choiceSeq") Long choiceSeq);
    
    /**
     * 설문조사 선택지 등록
     */
    int insertSurveyChoice(SurveyChoiceVO surveyChoiceVO);
    
    /**
     * 설문조사 선택지 수정
     */
    int updateSurveyChoice(SurveyChoiceVO surveyChoiceVO);
    
    /**
     * 설문조사 선택지 삭제
     */
    int deleteSurveyChoice(@Param("choiceSeq") Long choiceSeq);
    
    /**
     * 설문조사 선택지 순서 변경
     */
    int updateChoiceOrder(@Param("choiceSeq") Long choiceSeq, @Param("choiceOrd") Integer choiceOrd);
    
    /**
     * 설문조사 선택지 개수 조회
     */
    int selectChoiceCount(@Param("questionSeq") Long questionSeq);
    
    /**
     * 설문조사 선택지 순서 재정렬
     */
    int reorderChoices(@Param("questionSeq") Long questionSeq);
} 