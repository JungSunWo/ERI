package com.ERI.demo.vo;

import lombok.Data;
import java.util.List;
import com.ERI.demo.vo.SurveyQuestionVO;

/**
 * 설문조사 마스터 VO
 * @author ERI
 */
@Data
public class SurveyMstVO {
    private Long surveySeq;           // 설문조사 시퀀스
    private String surveyTtl;         // 설문 제목
    private String surveyDesc;        // 설문 설명
    private String surveyTyCd;        // 설문 유형 (HEALTH_CHECK, SATISFACTION, ETC)
    private String surveyStsCd;       // 설문 상태 (DRAFT, ACTIVE, CLOSED, ARCHIVED)
    private String surveySttDt;       // 설문 시작일
    private String surveyEndDt;       // 설문 종료일
    private Integer surveyDurMin;     // 예상 소요시간(분)
    private String anonymousYn;       // 익명 응답 여부 (Y: 익명, N: 실명)
    private String duplicateYn;       // 중복 응답 허용 여부 (Y: 허용, N: 불허)
    private Integer maxResponseCnt;   // 최대 응답 수
    private String targetEmpTyCd;     // 대상 직원 유형 (ALL, DEPT, POSITION, INDIVIDUAL)
    private Integer responseCnt;      // 응답 수
    private Double responseRate;      // 응답률
    private String delYn;             // 삭제 여부
    private String regDt;             // 등록일시
    private String regEmpId;          // 등록자 ID
    private String modDt;             // 수정일시
    private String updEmpId;          // 수정자 ID
    
    // 질문 리스트 (설문조사 생성/수정 시 사용)
    private List<SurveyQuestionVO> questions;
} 