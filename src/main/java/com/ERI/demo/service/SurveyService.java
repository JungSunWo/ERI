package com.ERI.demo.service;

import com.ERI.demo.dto.PageRequestDto;
import com.ERI.demo.dto.PageResponseDto;
import com.ERI.demo.mappers.SurveyMstMapper;
import com.ERI.demo.mappers.SurveyResponseMapper;
import com.ERI.demo.vo.SurveyMstVO;
import com.ERI.demo.vo.SurveyQuestionVO;
import com.ERI.demo.vo.SurveyChoiceVO;
import com.ERI.demo.vo.SurveyResponseVO;
import com.ERI.demo.vo.SurveyResponseDetailVO;
import com.ERI.demo.vo.SurveyStatsVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

/**
 * 설문조사 Service
 * @author ERI
 */
@Slf4j
@Service
public class SurveyService {

    @Autowired
    private SurveyMstMapper surveyMstMapper;
    
    @Autowired
    private SurveyResponseMapper surveyResponseMapper;

    /**
     * 설문조사 목록 조회 (페이징/검색/필터링) - PageResponseDto 사용
     * @param params 조회 파라미터
     * @param pageRequest 페이징 요청 정보
     * @return 설문조사 목록과 페이징 정보
     */
    public PageResponseDto<SurveyMstVO> getSurveyListWithPaging(Map<String, Object> params, PageRequestDto pageRequest) {
        log.info("설문조사 목록 조회 파라미터: {}", params);
        
        // 목록 조회
        List<SurveyMstVO> surveyList = surveyMstMapper.selectSurveyList(params);
        log.info("설문조사 목록 조회 결과: {}건", surveyList != null ? surveyList.size() : 0);
        
        // 총 건수 조회
        Map<String, Object> countParams = new HashMap<>(params);
        countParams.remove("offset");
        countParams.remove("size");
        int totalCount = surveyMstMapper.selectSurveyListCount(countParams);
        log.info("설문조사 총 건수: {}", totalCount);
        
        return new PageResponseDto<SurveyMstVO>(surveyList, pageRequest.getPage(), pageRequest.getSize(), totalCount, totalPages);
    }

    /**
     * 설문조사 목록 조회 (페이징/검색/필터링) - 기존 방식 (하위 호환성)
     * @param params 조회 파라미터
     * @return 설문조사 목록과 총 건수
     */
    public Map<String, Object> getSurveyList(Map<String, Object> params) {
        Map<String, Object> result = new HashMap<>();
        
        // 페이징 처리
        int page = params.get("page") != null ? Integer.parseInt(params.get("page").toString()) : 1;
        int size = params.get("size") != null ? Integer.parseInt(params.get("size").toString()) : 10;
        int offset = (page - 1) * size;
        
        params.put("page", offset);
        params.put("size", size);
        
        // 목록 조회
        List<SurveyMstVO> surveyList = surveyMstMapper.selectSurveyList(params);
        
        // 총 건수 조회
        params.remove("page");
        params.remove("size");
        int totalCount = surveyMstMapper.selectSurveyListCount(params);
        
        result.put("data", surveyList);
        result.put("totalCount", totalCount);
        result.put("page", page);
        result.put("size", size);
        result.put("totalPages", (int) Math.ceil((double) totalCount / size));
        
        return result;
    }

    /**
     * 설문조사 상세 조회
     * @param surveySeq 설문조사 시퀀스
     * @return 설문조사 정보 (질문과 선택지 포함)
     */
    public SurveyMstVO getSurveyBySeq(Long surveySeq) {
        log.info("설문조사 상세 조회 시작: surveySeq={}", surveySeq);
        
        try {
            SurveyMstVO survey = surveyMstMapper.selectSurveyBySeq(surveySeq);
            log.info("설문조사 기본 정보 조회 완료: survey={}", survey);
            
            if (survey != null) {
                // 질문 목록 조회
                List<SurveyQuestionVO> questions = surveyMstMapper.selectSurveyQuestions(surveySeq);
                log.info("설문조사 질문 목록 조회 완료: questions={}", questions);
                
                // 각 질문에 대한 선택지 조회
                for (SurveyQuestionVO question : questions) {
                    List<SurveyChoiceVO> choices = surveyMstMapper.selectSurveyChoices(surveySeq, question.getQuestionSeq());
                    log.info("질문 {} 선택지 조회 완료: choices={}", question.getQuestionSeq(), choices);
                    question.setChoices(choices);
                }
                
                survey.setQuestions(questions);
                log.info("설문조사 상세 조회 완료: surveySeq={}, questions={}", surveySeq, questions.size());
            } else {
                log.warn("설문조사를 찾을 수 없음: surveySeq={}", surveySeq);
            }
            
            return survey;
        } catch (Exception e) {
            log.error("설문조사 상세 조회 중 오류 발생: surveySeq={}", surveySeq, e);
            throw e;
        }
    }

    /**
     * 설문조사 등록
     * @param survey 설문조사 정보
     * @return 등록 결과
     */
    @Transactional
    public Map<String, Object> createSurvey(SurveyMstVO survey) {
        Map<String, Object> result = new HashMap<>();
        
        try {
            log.info("설문조사 생성 시작: surveyTtl={}", survey.getSurveyTtl());
            // 기본값 설정
            if (survey.getSurveyStsCd() == null) {
                survey.setSurveyStsCd("DRAFT");
            }
            if (survey.getAnonymousYn() == null) {
                survey.setAnonymousYn("Y");
            }
            if (survey.getDuplicateYn() == null) {
                survey.setDuplicateYn("N");
            }
            if (survey.getTargetEmpTyCd() == null) {
                survey.setTargetEmpTyCd("ALL");
            }
            
            // 등록자 정보 설정 (실제로는 세션에서 가져와야 함)
            survey.setRegEmpId("ADMIN001");
            
            log.info("설문조사 등록 전: surveySeq={}", survey.getSurveySeq());
            int insertCount = surveyMstMapper.insertSurvey(survey);
            log.info("설문조사 기본 정보 등록 완료: surveySeq={}, insertCount={}", survey.getSurveySeq(), insertCount);
            
            log.info("조건 확인: surveySeq={}, questions={}", survey.getSurveySeq(), survey.getQuestions());
            
            if (survey.getSurveySeq() != null && survey.getSurveySeq() > 0) {
                log.info("설문조사 등록 성공, 질문 처리 시작");
                // 질문 데이터가 있으면 처리
                if (survey.getQuestions() != null && !survey.getQuestions().isEmpty()) {
                    log.info("질문 등록 시작: 질문 개수={}", survey.getQuestions().size());
                    for (int i = 0; i < survey.getQuestions().size(); i++) {
                        SurveyQuestionVO question = survey.getQuestions().get(i);
                        question.setSurveySeq(survey.getSurveySeq());
                        question.setQuestionSeq((long) (i + 1)); // 질문 순서를 questionSeq로 사용
                        question.setRegEmpId(survey.getRegEmpId());
                        
                        // 질문 등록
                        int questionInsertCount = surveyMstMapper.insertSurveyQuestion(question);
                        log.info("질문 등록 완료: questionSeq={}, questionTtl={}", question.getQuestionSeq(), question.getQuestionTtl());
                        
                        // 선택지 데이터가 있으면 처리
                        if (question.getChoices() != null && !question.getChoices().isEmpty()) {
                            log.info("선택지 등록 시작: 질문={}, 선택지 개수={}", question.getQuestionTtl(), question.getChoices().size());
                            for (int j = 0; j < question.getChoices().size(); j++) {
                                SurveyChoiceVO choice = question.getChoices().get(j);
                                choice.setSurveySeq(survey.getSurveySeq());
                                choice.setQuestionSeq(question.getQuestionSeq());
                                choice.setChoiceSeq((long) (j + 1)); // 선택지 순서를 choiceSeq로 사용
                                choice.setRegEmpId(survey.getRegEmpId());
                                
                                // 선택지 등록
                                int choiceInsertCount = surveyMstMapper.insertSurveyChoice(choice);
                                log.info("선택지 등록 완료: choiceSeq={}, choiceTtl={}", choice.getChoiceSeq(), choice.getChoiceTtl());
                            }
                        }
                    }
                }
                
                result.put("success", true);
                result.put("message", "설문조사가 생성되었습니다.");
                result.put("surveySeq", survey.getSurveySeq());
            } else {
                result.put("success", false);
                result.put("message", "설문조사 생성에 실패했습니다.");
            }
        } catch (Exception e) {
            log.error("설문조사 생성 중 오류 발생", e);
            result.put("success", false);
            result.put("message", "설문조사 생성 중 오류가 발생했습니다: " + e.getMessage());
        }
        
        return result;
    }

    /**
     * 설문조사 수정
     * @param survey 설문조사 정보
     * @return 수정 결과
     */
    @Transactional
    public Map<String, Object> updateSurvey(SurveyMstVO survey) {
        Map<String, Object> result = new HashMap<>();
        
        try {
            log.info("설문조사 수정 시작: surveySeq={}", survey.getSurveySeq());
            
            // 수정자 정보 설정 (실제로는 세션에서 가져와야 함)
            survey.setUpdEmpId("ADMIN001");
            
            // 기본값 설정
            if (survey.getSurveyStsCd() == null) {
                survey.setSurveyStsCd("DRAFT");
            }
            if (survey.getAnonymousYn() == null) {
                survey.setAnonymousYn("N");
            }
            if (survey.getDuplicateYn() == null) {
                survey.setDuplicateYn("N");
            }
            if (survey.getTargetEmpTyCd() == null) {
                survey.setTargetEmpTyCd("ALL");
            }
            
            // 1. 기존 선택지 삭제 (외래키 제약조건 때문에 먼저 삭제)
            if (survey.getQuestions() != null && !survey.getQuestions().isEmpty()) {
                for (SurveyQuestionVO question : survey.getQuestions()) {
                    if (question.getQuestionSeq() != null) {
                        surveyMstMapper.deleteSurveyChoices(survey.getSurveySeq(), question.getQuestionSeq());
                        log.info("기존 선택지 삭제 완료: surveySeq={}, questionSeq={}", survey.getSurveySeq(), question.getQuestionSeq());
                    }
                }
            }
            
            // 2. 기존 질문 삭제
            surveyMstMapper.deleteSurveyQuestions(survey.getSurveySeq());
            log.info("기존 질문 삭제 완료: surveySeq={}", survey.getSurveySeq());
            
            // 3. 설문조사 기본 정보 수정
            int updateCount = surveyMstMapper.updateSurvey(survey);
            log.info("설문조사 기본 정보 수정 완료: updateCount={}", updateCount);
            
            // 4. 새로운 질문과 선택지 등록
            if (survey.getQuestions() != null && !survey.getQuestions().isEmpty()) {
                log.info("새로운 질문 등록 시작: 질문 개수={}", survey.getQuestions().size());
                for (int i = 0; i < survey.getQuestions().size(); i++) {
                    SurveyQuestionVO question = survey.getQuestions().get(i);
                    question.setSurveySeq(survey.getSurveySeq());
                    question.setQuestionSeq((long) (i + 1));
                    question.setRegEmpId(survey.getUpdEmpId());
                    
                    // 질문 등록
                    int questionInsertCount = surveyMstMapper.insertSurveyQuestion(question);
                    log.info("질문 등록 완료: questionSeq={}, questionTtl={}", question.getQuestionSeq(), question.getQuestionTtl());
                    
                    // 선택지 등록
                    if (question.getChoices() != null && !question.getChoices().isEmpty()) {
                        log.info("선택지 등록 시작: 질문={}, 선택지 개수={}", question.getQuestionTtl(), question.getChoices().size());
                        for (int j = 0; j < question.getChoices().size(); j++) {
                            SurveyChoiceVO choice = question.getChoices().get(j);
                            choice.setSurveySeq(survey.getSurveySeq());
                            choice.setQuestionSeq(question.getQuestionSeq());
                            choice.setChoiceSeq((long) (j + 1));
                            choice.setRegEmpId(survey.getUpdEmpId());
                            
                            int choiceInsertCount = surveyMstMapper.insertSurveyChoice(choice);
                            log.info("선택지 등록 완료: choiceSeq={}, choiceTtl={}", choice.getChoiceSeq(), choice.getChoiceTtl());
                        }
                    }
                }
            }
            
            result.put("success", true);
            result.put("message", "설문조사가 수정되었습니다.");
            
        } catch (Exception e) {
            log.error("설문조사 수정 중 오류 발생: surveySeq={}", survey.getSurveySeq(), e);
            result.put("success", false);
            result.put("message", "설문조사 수정 중 오류가 발생했습니다: " + e.getMessage());
        }
        
        return result;
    }

    /**
     * 설문조사 삭제
     * @param surveySeq 설문조사 시퀀스
     * @return 삭제 결과
     */
    @Transactional
    public Map<String, Object> deleteSurvey(Long surveySeq) {
        Map<String, Object> result = new HashMap<>();
        
        try {
            log.info("설문조사 삭제 시작: surveySeq={}", surveySeq);
            
            // 1. 선택지 삭제 (외래키 제약조건 때문에 먼저 삭제)
            surveyMstMapper.deleteAllSurveyChoices(surveySeq);
            log.info("모든 선택지 삭제 완료: surveySeq={}", surveySeq);
            
            // 2. 질문 삭제
            surveyMstMapper.deleteSurveyQuestions(surveySeq);
            log.info("모든 질문 삭제 완료: surveySeq={}", surveySeq);
            
            // 3. 설문조사 기본 정보 삭제
            int deleteCount = surveyMstMapper.deleteSurvey(surveySeq);
            log.info("설문조사 기본 정보 삭제 완료: deleteCount={}", deleteCount);
            
            if (deleteCount > 0) {
                result.put("success", true);
                result.put("message", "설문조사가 삭제되었습니다.");
            } else {
                result.put("success", false);
                result.put("message", "설문조사 삭제에 실패했습니다.");
            }
        } catch (Exception e) {
            log.error("설문조사 삭제 중 오류 발생: surveySeq={}", surveySeq, e);
            result.put("success", false);
            result.put("message", "설문조사 삭제 중 오류가 발생했습니다: " + e.getMessage());
        }
        
        return result;
    }

    /**
     * 설문조사 응답 수 업데이트
     * @param surveySeq 설문조사 시퀀스
     */
    @Transactional
    public void updateResponseCount(Long surveySeq) {
        surveyMstMapper.updateResponseCount(surveySeq);
    }

    /**
     * 설문조사 결과 조회
     * @param surveySeq 설문조사 시퀀스
     * @return 설문조사 결과 데이터
     */
    public Map<String, Object> getSurveyResults(Long surveySeq) {
        Map<String, Object> result = new HashMap<>();
        try {
            log.info("설문조사 결과 조회 시작: surveySeq={}", surveySeq);
            
            // 설문조사 기본 정보 조회
            SurveyMstVO survey = surveyMstMapper.selectSurveyBySeq(surveySeq);
            if (survey == null) {
                result.put("success", false);
                result.put("message", "존재하지 않는 설문조사입니다.");
                return result;
            }

            // 임시로 더미 데이터 생성 (실제로는 DB에서 조회)
            Map<String, Object> statistics = new HashMap<>();
            statistics.put("totalResponses", 15);
            statistics.put("responseRate", 75.0);
            statistics.put("averageScore", 8.2);
            
            // 질문별 통계 생성
            List<Map<String, Object>> questionStats = new ArrayList<>();
            if (survey.getQuestions() != null) {
                for (SurveyQuestionVO question : survey.getQuestions()) {
                    if (question.getChoices() != null) {
                        for (SurveyChoiceVO choice : question.getChoices()) {
                            Map<String, Object> stat = new HashMap<>();
                            stat.put("questionSeq", question.getQuestionSeq());
                            stat.put("choiceSeq", choice.getChoiceSeq());
                            stat.put("responseCount", (int)(Math.random() * 10) + 1); // 임시 데이터
                            questionStats.add(stat);
                        }
                    }
                }
            }
            statistics.put("questionStats", questionStats);

            // 임시 응답 데이터 생성
            List<Map<String, Object>> responses = new ArrayList<>();
            for (int i = 1; i <= 15; i++) {
                Map<String, Object> response = new HashMap<>();
                response.put("responseSeq", i);
                response.put("regDt", "2025-01-20T10:30:00");
                response.put("totalScore", (int)(Math.random() * 50) + 30);
                
                List<Map<String, Object>> answers = new ArrayList<>();
                if (survey.getQuestions() != null) {
                    for (SurveyQuestionVO question : survey.getQuestions()) {
                        Map<String, Object> answer = new HashMap<>();
                        answer.put("questionSeq", question.getQuestionSeq());
                        answer.put("answerText", "임시 응답 " + i);
                        answer.put("score", (int)(Math.random() * 10) + 1);
                        answers.add(answer);
                    }
                }
                response.put("answers", answers);
                responses.add(response);
            }

            Map<String, Object> data = new HashMap<>();
            data.put("survey", survey);
            data.put("statistics", statistics);
            data.put("responses", responses);

            result.put("success", true);
            result.put("data", data);
            result.put("message", "설문조사 결과를 조회했습니다.");
            
            log.info("설문조사 결과 조회 완료: surveySeq={}", surveySeq);
            
        } catch (Exception e) {
            log.error("설문조사 결과 조회 중 오류 발생: surveySeq={}", surveySeq, e);
            result.put("success", false);
            result.put("message", "설문조사 결과 조회 중 오류가 발생했습니다: " + e.getMessage());
        }
        return result;
    }

    /**
     * 설문조사 응답 등록
     * @param surveySeq 설문조사 시퀀스
     * @param responseData 응답 데이터
     * @param empId 직원 ID
     * @return 등록 결과
     */
    @Transactional
    public Map<String, Object> submitSurveyResponse(Long surveySeq, Map<String, Object> responseData, String empId) {
        Map<String, Object> result = new HashMap<>();
        
        try {
            log.info("설문조사 응답 등록 시작: surveySeq={}, empId={}", surveySeq, empId);
            
            // 설문조사 존재 여부 확인
            SurveyMstVO survey = surveyMstMapper.selectSurveyBySeq(surveySeq);
            if (survey == null) {
                result.put("success", false);
                result.put("message", "존재하지 않는 설문조사입니다.");
                return result;
            }
            
            // 중복 응답 확인 (익명이 아닌 경우)
            if (!"Y".equals(survey.getAnonymousYn())) {
                SurveyResponseVO existingResponse = surveyResponseMapper.selectResponseBySurveyAndEmp(surveySeq, empId);
                if (existingResponse != null) {
                    result.put("success", false);
                    result.put("message", "이미 응답한 설문조사입니다.");
                    return result;
                }
            }
            
            // 응답 데이터 생성
            SurveyResponseVO surveyResponse = new SurveyResponseVO();
            surveyResponse.setSurveySeq(surveySeq);
            surveyResponse.setEmpNo(empId);
            surveyResponse.setResponseStsCd("COMPLETED");
            surveyResponse.setAnonymousYn(survey.getAnonymousYn());
            surveyResponse.setRegId(empId);
            
            // 응답 등록
            surveyResponseMapper.insertSurveyResponse(surveyResponse);
            Long responseSeq = surveyResponse.getResponseSeq();
            
            // 응답 상세 등록
            @SuppressWarnings("unchecked")
            Map<String, Object> answers = (Map<String, Object>) responseData.get("answers");
            if (answers != null) {
                for (Map.Entry<String, Object> entry : answers.entrySet()) {
                    String questionSeqStr = entry.getKey();
                    Object answerValue = entry.getValue();
                    
                    Long questionSeq = Long.parseLong(questionSeqStr);
                    
                    if (answerValue instanceof List) {
                        // 다중 선택
                        @SuppressWarnings("unchecked")
                        List<Long> choiceSeqs = (List<Long>) answerValue;
                        for (int i = 0; i < choiceSeqs.size(); i++) {
                            SurveyResponseDetailVO detail = new SurveyResponseDetailVO();
                            detail.setResponseSeq(responseSeq);
                            detail.setSurveySeq(surveySeq);
                            detail.setQuestionSeq(questionSeq);
                            detail.setChoiceSeq(choiceSeqs.get(i));
                            detail.setResponseOrd(i + 1);
                            detail.setRegId(empId);
                            surveyResponseMapper.insertSurveyResponseDetail(detail);
                        }
                    } else if (answerValue instanceof String) {
                        // 텍스트 응답
                        SurveyResponseDetailVO detail = new SurveyResponseDetailVO();
                        detail.setResponseSeq(responseSeq);
                        detail.setSurveySeq(surveySeq);
                        detail.setQuestionSeq(questionSeq);
                        detail.setTextResponse((String) answerValue);
                        detail.setRegId(empId);
                        surveyResponseMapper.insertSurveyResponseDetail(detail);
                    } else if (answerValue instanceof Long || answerValue instanceof Integer) {
                        // 단일 선택
                        SurveyResponseDetailVO detail = new SurveyResponseDetailVO();
                        detail.setResponseSeq(responseSeq);
                        detail.setSurveySeq(surveySeq);
                        detail.setQuestionSeq(questionSeq);
                        detail.setChoiceSeq(Long.valueOf(answerValue.toString()));
                        detail.setRegId(empId);
                        surveyResponseMapper.insertSurveyResponseDetail(detail);
                    }
                }
            }
            
            // 응답 수 업데이트
            updateResponseCount(surveySeq);
            
            result.put("success", true);
            result.put("message", "설문조사 응답이 성공적으로 등록되었습니다.");
            result.put("responseSeq", responseSeq);
            
            log.info("설문조사 응답 등록 완료: responseSeq={}", responseSeq);
            
        } catch (Exception e) {
            log.error("설문조사 응답 등록 중 오류 발생: surveySeq={}, empId={}", surveySeq, empId, e);
            result.put("success", false);
            result.put("message", "설문조사 응답 등록 중 오류가 발생했습니다: " + e.getMessage());
        }
        
        return result;
    }

    /**
     * 설문조사 응답 목록 조회
     * @param surveySeq 설문조사 시퀀스
     * @param params 조회 파라미터
     * @return 응답 목록
     */
    public Map<String, Object> getSurveyResponses(Long surveySeq, Map<String, Object> params) {
        Map<String, Object> result = new HashMap<>();
        
        try {
            log.info("설문조사 응답 목록 조회 시작: surveySeq={}", surveySeq);
            
            // 설문조사 존재 여부 확인
            SurveyMstVO survey = surveyMstMapper.selectSurveyBySeq(surveySeq);
            if (survey == null) {
                result.put("success", false);
                result.put("message", "존재하지 않는 설문조사입니다.");
                return result;
            }
            
            // 응답 목록 조회
            List<SurveyResponseVO> responses = surveyResponseMapper.selectSurveyResponsesBySurveySeq(surveySeq, params);
            int totalCount = surveyResponseMapper.countSurveyResponses(surveySeq, params);
            
            // 응답 상세 정보 조회
            for (SurveyResponseVO response : responses) {
                List<SurveyResponseDetailVO> details = surveyResponseMapper.selectResponseDetailsByResponseSeq(response.getResponseSeq());
                response.setResponseDetails(details);
            }
            
            result.put("success", true);
            result.put("data", responses);
            result.put("totalCount", totalCount);
            result.put("message", "설문조사 응답 목록을 조회했습니다.");
            
        } catch (Exception e) {
            log.error("설문조사 응답 목록 조회 중 오류 발생: surveySeq={}", surveySeq, e);
            result.put("success", false);
            result.put("message", "설문조사 응답 목록 조회 중 오류가 발생했습니다: " + e.getMessage());
        }
        
        return result;
    }

    /**
     * 설문 응답률 계산
     * @param surveySeq 설문 시퀀스
     * @return 응답률 (%)
     */
    public BigDecimal calculateResponseRate(Long surveySeq) {
        // 전체 대상자 수 계산
        Integer totalTarget = surveyMstMapper.selectTotalTargetCount();
        
        // 응답자 수 계산
        Integer totalResponse = surveyMstMapper.selectResponseCount(surveySeq);
        
        // 응답률 계산
        if (totalTarget != null && totalTarget > 0) {
            return BigDecimal.valueOf(totalResponse)
                    .divide(BigDecimal.valueOf(totalTarget), 4, BigDecimal.ROUND_HALF_UP)
                    .multiply(BigDecimal.valueOf(100));
        }
        
        return BigDecimal.ZERO;
    }

    /**
     * 설문 응답 점수 계산
     * @param surveySeq 설문 시퀀스
     * @param responseSeq 응답 시퀀스
     * @return 총점
     */
    public BigDecimal calculateScore(Long surveySeq, Long responseSeq) {
        BigDecimal totalScore = surveyMstMapper.selectResponseScore(surveySeq, responseSeq);
        return totalScore != null ? totalScore : BigDecimal.ZERO;
    }

    /**
     * 설문 통계 업데이트
     * @param surveySeq 설문 시퀀스
     */
    @Transactional
    public void updateSurveyStats(Long surveySeq) {
        // 기존 통계 삭제
        surveyMstMapper.deleteSurveyStats(surveySeq);
        
        // 새로운 통계 계산 및 삽입
        List<SurveyStatsVO> statsList = surveyMstMapper.calculateSurveyStats(surveySeq);
        
        for (SurveyStatsVO stats : statsList) {
            surveyMstMapper.insertSurveyStats(stats);
        }
    }

    /**
     * 설문 응답 현황 조회
     * @param surveySeq 설문 시퀀스
     * @return 응답 현황
     */
    public SurveyResponseVO getSurveyResponseStatus(Long surveySeq) {
        return surveyMstMapper.selectSurveyResponseStatus(surveySeq);
    }

    /**
     * 설문 통계 조회
     * @param surveySeq 설문 시퀀스
     * @return 통계 목록
     */
    public List<SurveyStatsVO> getSurveyStats(Long surveySeq) {
        return surveyMstMapper.selectSurveyStats(surveySeq);
    }

    /**
     * 설문 응답 완료 처리
     * @param surveySeq 설문 시퀀스
     * @param responseSeq 응답 시퀀스
     */
    @Transactional
    public void completeSurveyResponse(Long surveySeq, Long responseSeq) {
        // 응답 상태를 완료로 변경
        surveyMstMapper.updateResponseStatus(surveySeq, responseSeq, "COMPLETED");
        
        // 통계 업데이트
        updateSurveyStats(surveySeq);
    }
} 