package com.ERI.demo.controller;

import com.ERI.demo.vo.SurveyMstVO;
import com.ERI.demo.vo.SurveyResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

/**
 * 설문조사 컨트롤러
 * @author ERI
 */
@RestController
@RequestMapping("/api/survey")
public class SurveyController {
    
    @Autowired
    private com.ERI.demo.service.SurveyService surveyService;
    
    /**
     * 설문조사 목록 조회
     */
    @GetMapping("/list")
    public ResponseEntity<List<SurveyMstVO>> getSurveyList(SurveyMstVO surveyMstVO) {
        List<SurveyMstVO> surveyList = surveyService.getSurveyList(surveyMstVO);
        return ResponseEntity.ok(surveyList);
    }
    
    /**
     * 설문조사 상세 조회
     */
    @GetMapping("/{surveySeq}")
    public ResponseEntity<SurveyMstVO> getSurveyDetail(@PathVariable Long surveySeq) {
        SurveyMstVO survey = surveyService.getSurveyDetail(surveySeq);
        return ResponseEntity.ok(survey);
    }
    
    /**
     * 설문조사 등록
     */
    @PostMapping
    public ResponseEntity<Long> createSurvey(@RequestBody SurveyMstVO surveyMstVO) {
        Long surveySeq = surveyService.createSurvey(surveyMstVO);
        return ResponseEntity.ok(surveySeq);
    }
    
    /**
     * 설문조사 수정
     */
    @PutMapping("/{surveySeq}")
    public ResponseEntity<Void> updateSurvey(@PathVariable Long surveySeq, @RequestBody SurveyMstVO surveyMstVO) {
        surveyMstVO.setSurveySeq(surveySeq);
        surveyService.updateSurvey(surveyMstVO);
        return ResponseEntity.ok().build();
    }
    
    /**
     * 설문조사 삭제
     */
    @DeleteMapping("/{surveySeq}")
    public ResponseEntity<Void> deleteSurvey(@PathVariable Long surveySeq) {
        surveyService.deleteSurvey(surveySeq);
        return ResponseEntity.ok().build();
    }
    
    /**
     * 활성 설문조사 목록 조회
     */
    @GetMapping("/active")
    public ResponseEntity<List<SurveyMstVO>> getActiveSurveyList() {
        List<SurveyMstVO> surveyList = surveyService.getActiveSurveyList();
        return ResponseEntity.ok(surveyList);
    }
    
    /**
     * 설문조사 응답 제출
     */
    @PostMapping("/{surveySeq}/response")
    public ResponseEntity<Long> submitSurveyResponse(@PathVariable Long surveySeq, @RequestBody SurveyResponseVO surveyResponseVO) {
        surveyResponseVO.setSurveySeq(surveySeq);
        Long responseSeq = surveyService.submitSurveyResponse(surveyResponseVO);
        return ResponseEntity.ok(responseSeq);
    }
    
    /**
     * 설문조사 통계 조회
     */
    @GetMapping("/{surveySeq}/statistics")
    public ResponseEntity<Map<String, Object>> getSurveyStatistics(@PathVariable Long surveySeq) {
        Map<String, Object> statistics = surveyService.getSurveyStatistics(surveySeq);
        return ResponseEntity.ok(statistics);
    }
} 