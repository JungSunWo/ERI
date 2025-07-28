package com.ERI.demo.vo;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 설문조사 응답 상세 VO
 * @author ERI
 */
@Data
public class SurveyResponseDetailVO {
    
    /**
     * 응답 상세 시퀀스
     */
    private Long responseDetailSeq;
    
    /**
     * 응답 시퀀스
     */
    private Long responseSeq;
    
    /**
     * 설문조사 시퀀스
     */
    private Long surveySeq;
    
    /**
     * 질문 시퀀스
     */
    private Long questionSeq;
    
    /**
     * 선택지 시퀀스 (단일/다중 선택인 경우)
     */
    private Long choiceSeq;
    
    /**
     * 텍스트 응답 (주관식인 경우)
     */
    private String textResponse;
    
    /**
     * 응답 순서 (다중 선택인 경우)
     */
    private Integer responseOrd;
    
    /**
     * 응답 점수
     */
    private Integer responseScore;
    
    /**
     * 등록자 ID
     */
    private String regId;
    
    /**
     * 등록일시
     */
    private LocalDateTime regDt;
    
    /**
     * 수정자 ID
     */
    private String updId;
    
    /**
     * 수정일시
     */
    private LocalDateTime updDt;
    
    /**
     * 삭제 여부 (Y/N)
     */
    private String delYn;
} 