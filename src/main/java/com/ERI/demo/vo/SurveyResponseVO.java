package com.ERI.demo.vo;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 설문조사 응답 VO
 * @author ERI
 */
@Data
public class SurveyResponseVO {
    
    /**
     * 응답 시퀀스
     */
    private Long responseSeq;
    
    /**
     * 설문조사 시퀀스
     */
    private Long surveySeq;
    
    /**
     * 직원 번호
     */
    private String empNo;
    
    /**
     * 직원명
     */
    private String empNm;
    
    /**
     * 부서명
     */
    private String deptNm;
    
    /**
     * 응답 시작 시간
     */
    private LocalDateTime responseSttDt;
    
    /**
     * 응답 완료 시간
     */
    private LocalDateTime responseEndDt;
    
    /**
     * 소요 시간 (분)
     */
    private Integer responseDurMin;
    
    /**
     * 응답 상태 코드 (IN_PROGRESS, COMPLETED, ABANDONED)
     */
    private String responseStsCd;
    
    /**
     * 익명 여부 (Y/N)
     */
    private String anonymousYn;
    
    /**
     * IP 주소
     */
    private String ipAddr;
    
    /**
     * 사용자 에이전트
     */
    private String userAgent;
    
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
    
    /**
     * 응답 상세 목록
     */
    private List<SurveyResponseDetailVO> responseDetails;
} 