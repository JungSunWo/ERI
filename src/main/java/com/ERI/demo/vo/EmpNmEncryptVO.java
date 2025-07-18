package com.ERI.demo.vo;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 직원명 암호화 VO
 */
@Data
public class EmpNmEncryptVO {
    
    /**
     * 직원 일련번호
     */
    private Long empSeq;
    
    /**
     * 원본 직원명
     */
    private String origEmpNm;
    
    /**
     * 랜덤 변형 한글명
     */
    private String randomEmpNm;
    
    /**
     * 암호화 알고리즘
     */
    private String encryptAlgorithm;
    
    /**
     * 암호화 초기화 벡터
     */
    private String encryptIv;
    
    /**
     * 암호화 일시
     */
    private LocalDateTime encryptDate;
    
    /**
     * 삭제 여부 (Y: 삭제, N: 사용)
     */
    private String delYn;
    
    /**
     * 삭제 일시
     */
    private LocalDateTime delDate;
    
    /**
     * 수정 일시
     */
    private LocalDateTime updDate;
} 