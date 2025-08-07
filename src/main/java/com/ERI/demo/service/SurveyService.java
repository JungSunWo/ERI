package com.ERI.demo.service;

import com.ERI.demo.mapper.*;
import com.ERI.demo.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

/**
 * 설문조사 서비스
 * @author ERI
 */
@Service
public class SurveyService {
    
    @Autowired
    private SurveyMstMapper surveyMstMapper;
    
    @Autowired
    private SurveyQuestionMapper surveyQuestionMapper;
    
    @Autowired
    private SurveyChoiceMapper surveyChoiceMapper;
    
    @Autowired
    private SurveyResponseMapper surveyResponseMapper;
    
    @Autowired
    private SurveyResponseDetailMapper surveyResponseDetailMapper;
    
    @Autowired
    private SurveyStatisticsMapper surveyStatisticsMapper;
    
    /**
     * 설문조사 목록 조회
     */
    public List<SurveyMstVO> getSurveyList(SurveyMstVO surveyMstVO) {
        return surveyMstMapper.selectSurveyMstList(surveyMstVO);
    }
    
    /**
     * 설문조사 상세 조회
     */
    public SurveyMstVO getSurveyDetail(Long surveySeq) {
        return surveyMstMapper.selectSurveyMstBySeq(surveySeq);
    }
    
    /**
     * 설문조사 등록
     */
    @Transactional
    public Long createSurvey(SurveyMstVO surveyMstVO) {
        surveyMstMapper.insertSurveyMst(surveyMstVO);
        return surveyMstVO.getSurveySeq();
    }
    
    /**
     * 설문조사 수정
     */
    @Transactional
    public void updateSurvey(SurveyMstVO surveyMstVO) {
        surveyMstMapper.updateSurveyMst(surveyMstVO);
    }
    
    /**
     * 설문조사 삭제
     */
    @Transactional
    public void deleteSurvey(Long surveySeq) {
        surveyMstMapper.deleteSurveyMst(surveySeq);
    }
    
    /**
     * 활성 설문조사 목록 조회
     */
    public List<SurveyMstVO> getActiveSurveyList() {
        return surveyMstMapper.selectActiveSurveyMstList();
    }
} 