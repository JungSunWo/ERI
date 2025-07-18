package com.ERI.demo.vo;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 암호화 이력 VO
 */
@Data
public class EncryptHistoryVO {
    
    /**
     * 이력 ID
     */
    private Long histId;
    
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
     * 원본 직원명
     */
    private String origEmpNm;
    
    /**
     * 랜덤 변형 한글명
     */
    private String randomEmpNm;
    
    /**
     * 직원 이메일 (암호화하지 않음)
     */
    private String origEmpEmail;
    
    /**
     * 암호화 알고리즘
     */
    private String encryptAlgorithm;
    
    /**
     * 암호화 키 ID
     */
    private String encryptKeyId;
    
    /**
     * 암호화 초기화 벡터
     */
    private String encryptIv;
    
    /**
     * 작업 타입 (ENCRYPT: 암호화, DECRYPT: 복호화, DELETE: 삭제, RESTORE: 복구, RE_ENCRYPT: 재암호화)
     */
    private String operationType;
    
    /**
     * 작업 일시
     */
    private LocalDateTime operationDate;
    
    /**
     * 작업자 일련번호
     */
    private Long operatorSeq;
} 