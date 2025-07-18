package com.ERI.demo.service;

import com.ERI.demo.mappers.encryption.EncryptHistoryMapper;
import com.ERI.demo.vo.EncryptHistoryVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 암호화 이력 서비스 (암호화 데이터베이스 전용)
 * eri_enc_db 데이터베이스의 암호화 이력 정보를 관리
 */
@Service
@Transactional(transactionManager = "encryptionTransactionManager")
public class EncryptHistoryService {
    
    @Autowired
    private EncryptHistoryMapper encryptHistoryMapper;
    
    /**
     * 암호화 이력 저장
     */
    public int saveEncryptHistory(EncryptHistoryVO encryptHistory) {
        if (encryptHistory.getOperationDate() == null) {
            encryptHistory.setOperationDate(LocalDateTime.now());
        }
        return encryptHistoryMapper.insertEncryptHistory(encryptHistory);
    }
    
    /**
     * 전체 암호화 이력 조회
     */
    public List<EncryptHistoryVO> getAllEncryptHistory() {
        return encryptHistoryMapper.selectAllEncryptHistory();
    }
    
    /**
     * 직원별 암호화 이력 조회
     */
    public List<EncryptHistoryVO> getEncryptHistoryByEmpSeq(Long empSeq) {
        return encryptHistoryMapper.selectHistoryByEmpSeq(empSeq);
    }
    
    /**
     * 작업 타입별 암호화 이력 조회
     */
    public List<EncryptHistoryVO> getEncryptHistoryByOperationType(String operationType) {
        return encryptHistoryMapper.selectHistoryByOperationType(operationType);
    }
    
    /**
     * 작업자별 암호화 이력 조회
     */
    public List<EncryptHistoryVO> getEncryptHistoryByOperatorSeq(Long operatorSeq) {
        return encryptHistoryMapper.selectEncryptHistoryByOperatorSeq(operatorSeq);
    }
    
    /**
     * 기간별 암호화 이력 조회
     */
    public List<EncryptHistoryVO> getEncryptHistoryByDateRange(String startDate, String endDate) {
        return encryptHistoryMapper.selectHistoryByDateRange(startDate, endDate);
    }
    
    /**
     * 최근 암호화 이력 조회
     */
    public List<EncryptHistoryVO> getRecentEncryptHistory(int limit) {
        return encryptHistoryMapper.selectRecentEncryptHistory(limit);
    }
    
    /**
     * 암호화 이력 생성 (편의 메서드)
     */
    public EncryptHistoryVO createEncryptHistory(
            Long empSeq, 
            String origEmpNo, 
            String encryptEmpNo,
            String origEmpNm,
            String randomEmpNm,
            String origEmpEmail,
            String encryptAlgorithm,
            String encryptKeyId,
            String encryptIv,
            String operationType,
            Long operatorSeq) {
        
        EncryptHistoryVO history = new EncryptHistoryVO();
        history.setEmpSeq(empSeq);
        history.setOrigEmpNo(origEmpNo);
        history.setEncryptEmpNo(encryptEmpNo);
        history.setOrigEmpNm(origEmpNm);
        history.setRandomEmpNm(randomEmpNm);
        history.setOrigEmpEmail(origEmpEmail);
        history.setEncryptAlgorithm(encryptAlgorithm);
        history.setEncryptKeyId(encryptKeyId);
        history.setEncryptIv(encryptIv);
        history.setOperationType(operationType);
        history.setOperationDate(LocalDateTime.now());
        history.setOperatorSeq(operatorSeq);
        
        return history;
    }
} 