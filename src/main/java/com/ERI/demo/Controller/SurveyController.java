package com.ERI.demo.Controller;

import com.ERI.demo.dto.PageRequestDto;
import com.ERI.demo.dto.PageResponseDto;
import com.ERI.demo.service.SurveyService;
import com.ERI.demo.vo.SurveyMstVO;
import com.ERI.demo.vo.SurveyStatsVO;
import java.math.BigDecimal;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 설문조사 Controller
 * @author ERI
 */
@Slf4j
@RestController
@RequestMapping("/api/surveys")
@RequiredArgsConstructor
public class SurveyController {

    private final SurveyService surveyService;

    /**
     * 설문조사 목록 조회 (페이징/검색/필터링)
     */
    @GetMapping
    public ResponseEntity<Map<String, Object>> getSurveyList(
            PageRequestDto pageRequest,
            @RequestParam(value = "surveyTyCd", required = false) String surveyTyCd,
            @RequestParam(value = "surveyStsCd", required = false) String surveyStsCd) {
        
        Map<String, Object> response = new HashMap<>();
        
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("searchKeyword", pageRequest.getSearchKeyword());
            params.put("surveyTyCd", surveyTyCd);
            params.put("surveyStsCd", surveyStsCd);
            params.put("offset", pageRequest.getOffset());
            params.put("size", pageRequest.getSize());
            
            PageResponseDto<SurveyMstVO> result = surveyService.getSurveyListWithPaging(params, pageRequest);
            
            response.put("success", true);
            response.put("data", result);
            response.put("message", "설문조사 목록을 조회했습니다.");
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            log.error("설문조사 목록 조회 실패", e);
            
            response.put("success", false);
            response.put("message", "설문조사 목록 조회에 실패했습니다: " + e.getMessage());
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * 설문조사 상세 조회
     */
    @GetMapping("/{surveySeq}")
    public ResponseEntity<Map<String, Object>> getSurveyBySeq(@PathVariable Long surveySeq) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            log.info("설문조사 상세 조회 시작: surveySeq={}", surveySeq);
            SurveyMstVO survey = surveyService.getSurveyBySeq(surveySeq);
            log.info("설문조사 상세 조회 완료: survey={}", survey);
            
            if (survey != null) {
                response.put("success", true);
                response.put("data", survey);
                response.put("message", "설문조사 정보를 조회했습니다.");
                return ResponseEntity.ok(response);
            } else {
                log.warn("존재하지 않는 설문조사: surveySeq={}", surveySeq);
                response.put("success", false);
                response.put("message", "존재하지 않는 설문조사입니다.");
                return ResponseEntity.notFound().build();
            }
            
        } catch (Exception e) {
            log.error("설문조사 상세 조회 실패: surveySeq={}", surveySeq, e);
            
            response.put("success", false);
            response.put("message", "설문조사 상세 조회에 실패했습니다: " + e.getMessage());
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * 설문조사 등록
     */
    @PostMapping
    public ResponseEntity<Map<String, Object>> createSurvey(@RequestBody SurveyMstVO survey,
                                                           HttpServletRequest request) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            // AuthInterceptor에서 전달받은 사용자 정보 사용
            String empId = (String) request.getAttribute("EMP_ID");
            if (empId == null) {
                response.put("success", false);
                response.put("message", "인증 정보를 찾을 수 없습니다.");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }
            
            // 받은 데이터 로그 출력
            log.info("설문조사 등록 요청 데이터: surveyTtl={}, surveyTyCd={}, empId={}", 
                    survey.getSurveyTtl(), survey.getSurveyTyCd(), empId);
            log.info("질문 개수: {}", survey.getQuestions() != null ? survey.getQuestions().size() : 0);
            if (survey.getQuestions() != null && !survey.getQuestions().isEmpty()) {
                for (int i = 0; i < survey.getQuestions().size(); i++) {
                    var question = survey.getQuestions().get(i);
                    log.info("질문 {}: questionTtl={}, questionTyCd={}, choices={}", 
                            i + 1, question.getQuestionTtl(), question.getQuestionTyCd(), 
                            question.getChoices() != null ? question.getChoices().size() : 0);
                }
            }
            
            log.info("SurveyService.createSurvey 호출 시작");
            Map<String, Object> result = surveyService.createSurvey(survey);
            log.info("SurveyService.createSurvey 호출 완료: result={}", result);
            
            if ((Boolean) result.get("success")) {
                response.put("success", true);
                response.put("message", "설문조사가 성공적으로 등록되었습니다.");
                response.put("surveySeq", result.get("surveySeq"));
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("message", result.get("message"));
                return ResponseEntity.badRequest().body(response);
            }
            
        } catch (Exception e) {
            log.error("설문조사 등록 실패", e);
            
            response.put("success", false);
            response.put("message", "설문조사 등록에 실패했습니다: " + e.getMessage());
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * 설문조사 수정
     */
    @PutMapping("/{surveySeq}")
    public ResponseEntity<Map<String, Object>> updateSurvey(@PathVariable Long surveySeq,
                                                           @RequestBody SurveyMstVO survey,
                                                           HttpServletRequest request) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            // AuthInterceptor에서 전달받은 사용자 정보 사용
            String empId = (String) request.getAttribute("EMP_ID");
            if (empId == null) {
                response.put("success", false);
                response.put("message", "인증 정보를 찾을 수 없습니다.");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }
            
            survey.setSurveySeq(surveySeq);
            
            // 받은 데이터 로그 출력
            log.info("설문조사 수정 요청 데이터: surveySeq={}, surveyTtl={}, surveyTyCd={}, empId={}", 
                    surveySeq, survey.getSurveyTtl(), survey.getSurveyTyCd(), empId);
            
            Map<String, Object> result = surveyService.updateSurvey(survey);
            
            if ((Boolean) result.get("success")) {
                response.put("success", true);
                response.put("message", "설문조사가 성공적으로 수정되었습니다.");
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("message", result.get("message"));
                return ResponseEntity.badRequest().body(response);
            }
            
        } catch (Exception e) {
            log.error("설문조사 수정 실패", e);
            
            response.put("success", false);
            response.put("message", "설문조사 수정에 실패했습니다: " + e.getMessage());
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * 설문조사 삭제
     */
    @DeleteMapping("/{surveySeq}")
    public ResponseEntity<Map<String, Object>> deleteSurvey(@PathVariable Long surveySeq,
                                                           HttpServletRequest request) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            // AuthInterceptor에서 전달받은 사용자 정보 사용
            String empId = (String) request.getAttribute("EMP_ID");
            if (empId == null) {
                response.put("success", false);
                response.put("message", "인증 정보를 찾을 수 없습니다.");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }
            
            Map<String, Object> result = surveyService.deleteSurvey(surveySeq);
            
            if ((Boolean) result.get("success")) {
                response.put("success", true);
                response.put("message", "설문조사가 성공적으로 삭제되었습니다.");
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("message", result.get("message"));
                return ResponseEntity.badRequest().body(response);
            }
            
        } catch (Exception e) {
            log.error("설문조사 삭제 실패", e);
            
            response.put("success", false);
            response.put("message", "설문조사 삭제에 실패했습니다: " + e.getMessage());
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * 설문조사 결과 조회
     */
    @GetMapping("/{surveySeq}/results")
    public ResponseEntity<Map<String, Object>> getSurveyResults(@PathVariable Long surveySeq) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            log.info("설문조사 결과 조회 시작: surveySeq={}", surveySeq);
            Map<String, Object> result = surveyService.getSurveyResults(surveySeq);
            
            if ((Boolean) result.get("success")) {
                response.put("success", true);
                response.put("data", result.get("data"));
                response.put("message", result.get("message"));
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("message", result.get("message"));
                return ResponseEntity.badRequest().body(response);
            }
            
        } catch (Exception e) {
            log.error("설문조사 결과 조회 실패: surveySeq={}", surveySeq, e);
            
            response.put("success", false);
            response.put("message", "설문조사 결과 조회에 실패했습니다: " + e.getMessage());
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * 설문조사 결과 다운로드 (임시 구현)
     */
    @GetMapping("/{surveySeq}/download")
    public ResponseEntity<Map<String, Object>> downloadSurveyResults(
            @PathVariable Long surveySeq,
            @RequestParam(value = "format", defaultValue = "excel") String format) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            // 임시로 성공 응답만 반환
            response.put("success", true);
            response.put("message", "다운로드 기능은 준비 중입니다.");
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            log.error("설문조사 결과 다운로드 실패", e);
            
            response.put("success", false);
            response.put("message", "설문조사 결과 다운로드에 실패했습니다: " + e.getMessage());
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * 설문조사 응답 등록
     */
    @PostMapping("/{surveySeq}/submit")
    public ResponseEntity<Map<String, Object>> submitSurveyResponse(
            @PathVariable Long surveySeq,
            @RequestBody Map<String, Object> responseData,
            HttpServletRequest request) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            // AuthInterceptor에서 전달받은 사용자 정보 사용
            String empId = (String) request.getAttribute("EMP_ID");
            if (empId == null) {
                response.put("success", false);
                response.put("message", "인증 정보를 찾을 수 없습니다.");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }
            
            log.info("설문조사 응답 등록 요청: surveySeq={}, empId={}", surveySeq, empId);
            
            Map<String, Object> result = surveyService.submitSurveyResponse(surveySeq, responseData, empId);
            
            if ((Boolean) result.get("success")) {
                response.put("success", true);
                response.put("message", result.get("message"));
                response.put("responseSeq", result.get("responseSeq"));
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("message", result.get("message"));
                return ResponseEntity.badRequest().body(response);
            }
            
        } catch (Exception e) {
            log.error("설문조사 응답 등록 실패: surveySeq={}", surveySeq, e);
            
            response.put("success", false);
            response.put("message", "설문조사 응답 등록에 실패했습니다: " + e.getMessage());
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * 설문조사 응답 목록 조회
     */
    @GetMapping("/{surveySeq}/responses")
    public ResponseEntity<Map<String, Object>> getSurveyResponses(
            @PathVariable Long surveySeq,
            @RequestParam Map<String, Object> params) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            log.info("설문조사 응답 목록 조회: surveySeq={}, params={}", surveySeq, params);
            
            Map<String, Object> result = surveyService.getSurveyResponses(surveySeq, params);
            
            if ((Boolean) result.get("success")) {
                response.put("success", true);
                response.put("data", result.get("data"));
                response.put("totalCount", result.get("totalCount"));
                response.put("message", result.get("message"));
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("message", result.get("message"));
                return ResponseEntity.badRequest().body(response);
            }
            
        } catch (Exception e) {
            log.error("설문조사 응답 목록 조회 실패: surveySeq={}", surveySeq, e);
            
            response.put("success", false);
            response.put("message", "설문조사 응답 목록 조회에 실패했습니다: " + e.getMessage());
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * 설문 응답률 계산 (함수 대체)
     */
    @GetMapping("/{surveySeq}/response-rate")
    public ResponseEntity<Map<String, Object>> calculateResponseRate(@PathVariable Long surveySeq) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            log.info("설문 응답률 계산: surveySeq={}", surveySeq);
            
            BigDecimal responseRate = surveyService.calculateResponseRate(surveySeq);
            
            response.put("success", true);
            response.put("data", responseRate);
            response.put("message", "설문 응답률을 계산했습니다.");
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            log.error("설문 응답률 계산 실패: surveySeq={}", surveySeq, e);
            
            response.put("success", false);
            response.put("message", "설문 응답률 계산에 실패했습니다: " + e.getMessage());
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * 설문 응답 점수 계산 (함수 대체)
     */
    @GetMapping("/{surveySeq}/responses/{responseSeq}/score")
    public ResponseEntity<Map<String, Object>> calculateScore(
            @PathVariable Long surveySeq,
            @PathVariable Long responseSeq) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            log.info("설문 응답 점수 계산: surveySeq={}, responseSeq={}", surveySeq, responseSeq);
            
            BigDecimal score = surveyService.calculateScore(surveySeq, responseSeq);
            
            response.put("success", true);
            response.put("data", score);
            response.put("message", "설문 응답 점수를 계산했습니다.");
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            log.error("설문 응답 점수 계산 실패: surveySeq={}, responseSeq={}", surveySeq, responseSeq, e);
            
            response.put("success", false);
            response.put("message", "설문 응답 점수 계산에 실패했습니다: " + e.getMessage());
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * 설문 통계 업데이트 (함수 대체)
     */
    @PostMapping("/{surveySeq}/update-stats")
    public ResponseEntity<Map<String, Object>> updateSurveyStats(@PathVariable Long surveySeq) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            log.info("설문 통계 업데이트: surveySeq={}", surveySeq);
            
            surveyService.updateSurveyStats(surveySeq);
            
            response.put("success", true);
            response.put("message", "설문 통계를 업데이트했습니다.");
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            log.error("설문 통계 업데이트 실패: surveySeq={}", surveySeq, e);
            
            response.put("success", false);
            response.put("message", "설문 통계 업데이트에 실패했습니다: " + e.getMessage());
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * 설문 통계 조회 (함수 대체)
     */
    @GetMapping("/{surveySeq}/stats")
    public ResponseEntity<Map<String, Object>> getSurveyStats(@PathVariable Long surveySeq) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            log.info("설문 통계 조회: surveySeq={}", surveySeq);
            
            List<SurveyStatsVO> stats = surveyService.getSurveyStats(surveySeq);
            
            response.put("success", true);
            response.put("data", stats);
            response.put("message", "설문 통계를 조회했습니다.");
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            log.error("설문 통계 조회 실패: surveySeq={}", surveySeq, e);
            
            response.put("success", false);
            response.put("message", "설문 통계 조회에 실패했습니다: " + e.getMessage());
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
} 