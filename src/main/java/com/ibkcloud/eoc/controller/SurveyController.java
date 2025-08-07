package com.ibkcloud.eoc.controller;

import com.ibkcloud.eoc.cmm.exception.BizException;
import com.ibkcloud.eoc.service.SurveyService;
import com.ibkcloud.eoc.controller.vo.SurveyMstInVo;
import com.ibkcloud.eoc.controller.vo.SurveyMstOutVo;
import com.ibkcloud.eoc.controller.vo.SurveyResponseInVo;
import com.ibkcloud.eoc.controller.vo.SurveyResponseOutVo;
import com.ibkcloud.eoc.controller.vo.SurveyStatisticsOutVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 설문조사 컨트롤러
 */
@Slf4j
@RestController
@RequestMapping("/api/survey")
@RequiredArgsConstructor
public class SurveyController {
    
    private final SurveyService surveyService;
    
    /**
     * 설문조사 목록 조회
     */
    @GetMapping("/list")
    public ResponseEntity<List<SurveyMstOutVo>> inqSurveyList(SurveyMstInVo surveyMstInVo) {
        try {
            List<SurveyMstOutVo> surveyList = surveyService.inqSurveyList(surveyMstInVo);
            return ResponseEntity.ok(surveyList);
        } catch (Exception e) {
            log.error("설문조사 목록 조회 실패", e);
            throw new BizException("S001", "설문조사 목록 조회에 실패했습니다: " + e.getMessage());
        }
    }
    
    /**
     * 설문조사 상세 조회
     */
    @GetMapping("/{surveySeq}")
    public ResponseEntity<SurveyMstOutVo> inqSurveyDetail(@PathVariable Long surveySeq) {
        try {
            SurveyMstOutVo survey = surveyService.inqSurveyDetail(surveySeq);
            
            if (survey == null) {
                throw new BizException("S002", "설문조사를 찾을 수 없습니다.");
            }
            
            survey.setSuccess(true);
            survey.setMessage("설문조사를 조회했습니다.");
            
            return ResponseEntity.ok(survey);
        } catch (BizException e) {
            throw e;
        } catch (Exception e) {
            log.error("설문조사 상세 조회 실패", e);
            throw new BizException("S003", "설문조사 상세 조회에 실패했습니다: " + e.getMessage());
        }
    }
    
    /**
     * 설문조사 등록
     */
    @PostMapping
    public ResponseEntity<SurveyMstOutVo> rgsnSurvey(@RequestBody SurveyMstInVo surveyMstInVo) {
        try {
            SurveyMstOutVo result = surveyService.rgsnSurvey(surveyMstInVo);
            
            result.setSuccess(true);
            result.setMessage("설문조사가 성공적으로 등록되었습니다.");
            
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            log.error("설문조사 등록 실패", e);
            throw new BizException("S004", "설문조사 등록에 실패했습니다: " + e.getMessage());
        }
    }
    
    /**
     * 설문조사 수정
     */
    @PutMapping("/{surveySeq}")
    public ResponseEntity<SurveyMstOutVo> mdfcSurvey(@PathVariable Long surveySeq, @RequestBody SurveyMstInVo surveyMstInVo) {
        try {
            surveyMstInVo.setSurveySeq(surveySeq);
            SurveyMstOutVo result = surveyService.mdfcSurvey(surveyMstInVo);
            
            result.setSuccess(true);
            result.setMessage("설문조사가 성공적으로 수정되었습니다.");
            
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            log.error("설문조사 수정 실패", e);
            throw new BizException("S005", "설문조사 수정에 실패했습니다: " + e.getMessage());
        }
    }
    
    /**
     * 설문조사 삭제
     */
    @DeleteMapping("/{surveySeq}")
    public ResponseEntity<SurveyMstOutVo> delSurvey(@PathVariable Long surveySeq) {
        try {
            SurveyMstOutVo result = surveyService.delSurvey(surveySeq);
            
            result.setSuccess(true);
            result.setMessage("설문조사가 성공적으로 삭제되었습니다.");
            
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            log.error("설문조사 삭제 실패", e);
            throw new BizException("S006", "설문조사 삭제에 실패했습니다: " + e.getMessage());
        }
    }
    
    /**
     * 활성 설문조사 목록 조회
     */
    @GetMapping("/active")
    public ResponseEntity<List<SurveyMstOutVo>> inqActiveSurveyList() {
        try {
            List<SurveyMstOutVo> surveyList = surveyService.inqActiveSurveyList();
            return ResponseEntity.ok(surveyList);
        } catch (Exception e) {
            log.error("활성 설문조사 목록 조회 실패", e);
            throw new BizException("S007", "활성 설문조사 목록 조회에 실패했습니다: " + e.getMessage());
        }
    }
    
    /**
     * 설문조사 응답 제출
     */
    @PostMapping("/{surveySeq}/response")
    public ResponseEntity<SurveyResponseOutVo> rgsnSurveyResponse(@PathVariable Long surveySeq, @RequestBody SurveyResponseInVo surveyResponseInVo) {
        try {
            surveyResponseInVo.setSurveySeq(surveySeq);
            SurveyResponseOutVo result = surveyService.rgsnSurveyResponse(surveyResponseInVo);
            
            result.setSuccess(true);
            result.setMessage("설문조사 응답이 성공적으로 제출되었습니다.");
            
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            log.error("설문조사 응답 제출 실패", e);
            throw new BizException("S008", "설문조사 응답 제출에 실패했습니다: " + e.getMessage());
        }
    }
    
    /**
     * 설문조사 통계 조회
     */
    @GetMapping("/{surveySeq}/statistics")
    public ResponseEntity<SurveyStatisticsOutVo> inqSurveyStatistics(@PathVariable Long surveySeq) {
        try {
            SurveyStatisticsOutVo statistics = surveyService.inqSurveyStatistics(surveySeq);
            
            statistics.setSuccess(true);
            statistics.setMessage("설문조사 통계를 조회했습니다.");
            
            return ResponseEntity.ok(statistics);
        } catch (Exception e) {
            log.error("설문조사 통계 조회 실패", e);
            throw new BizException("S009", "설문조사 통계 조회에 실패했습니다: " + e.getMessage());
        }
    }
} 