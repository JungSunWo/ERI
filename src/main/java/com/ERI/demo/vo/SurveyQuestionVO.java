package com.ERI.demo.vo;

import lombok.Data;
import java.util.List;
import com.ERI.demo.vo.SurveyChoiceVO;

/**
 * 설문조사 질문 VO
 * @author ERI
 */
@Data
public class SurveyQuestionVO {
    private Long surveySeq;           // 설문조사 시퀀스
    private Long questionSeq;         // 질문 시퀀스
    private String questionTtl;       // 질문 제목
    private String questionDesc;      // 질문 설명
    private String questionTyCd;      // 질문 유형 (SINGLE_CHOICE, MULTIPLE_CHOICE, TEXT, SCALE, ETC)
    private Integer questionOrd;      // 질문 순서
    private String requiredYn;        // 필수 응답 여부 (Y: 필수, N: 선택)
    private String skipLogicYn;       // 건너뛰기 로직 사용 여부 (Y: 사용, N: 미사용)
    private String skipCondition;     // 건너뛰기 조건 (JSON 형태)
    private String scoreYn;           // 점수 계산 여부 (Y: 계산, N: 미계산)
    private Double scoreWeight;       // 점수 가중치
    private String delYn;             // 삭제 여부
    private String regDt;             // 등록일시
    private String regEmpId;          // 등록자 ID
    private String modDt;             // 수정일시
    private String updEmpId;          // 수정자 ID
    
    // 선택지 리스트 (질문 생성/수정 시 사용)
    private List<SurveyChoiceVO> choices;
} 