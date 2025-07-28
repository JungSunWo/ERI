package com.ERI.demo.vo;

import lombok.Data;

/**
 * 설문조사 선택지 VO
 * @author ERI
 */
@Data
public class SurveyChoiceVO {
    private Long surveySeq;           // 설문조사 시퀀스
    private Long questionSeq;         // 질문 시퀀스
    private Long choiceSeq;           // 선택지 시퀀스
    private String choiceTtl;         // 선택지 제목
    private String choiceDesc;        // 선택지 설명
    private Integer choiceOrd;        // 선택지 순서
    private Integer choiceScore;      // 선택지 점수
    private String choiceValue;       // 선택지 값
    private String etcYn;             // 기타 선택지 여부 (Y: 기타, N: 일반)
    private String delYn;             // 삭제 여부
    private String regDt;             // 등록일시
    private String regEmpId;          // 등록자 ID
    private String modDt;             // 수정일시
    private String updEmpId;          // 수정자 ID
} 