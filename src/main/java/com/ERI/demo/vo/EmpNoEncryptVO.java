package com.ERI.demo.vo;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 직원번호 암호화 VO
 */
@Data
public class EmpNoEncryptVO {
    
    /**
     * 직원 일련번호
     */
    private Long empSeq;
    
    /**
     * 원본 직원번호
     */
    private String origEmpNo;
    
    /**
     * 암호화된 직원번호
     */
    private String encryptEmpNo;
    
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