package com.ERI.demo.vo;

import java.time.LocalDateTime;

/**
 * 직원 암호화 정보 VO
 * 직원번호와 직원명 암호화 정보를 통합 관리
 */
public class EmpEncryptVO {
    
    private Long empSeq;                    // 직원 일련번호
    private String origEmpNo;               // 원본 직원번호
    private String encryptEmpNo;            // 암호화된 직원번호
    private String origEmpNm;               // 원본 직원명
    private String randomEmpNm;             // 랜덤 변형 한글명
    private String origEmpEmail;            // 직원 이메일 (암호화하지 않음)
    private String encryptAlgorithm;        // 암호화 알고리즘
    private String encryptKeyId;            // 암호화 키 ID
    private String encryptIv;               // 암호화 초기화 벡터
    private LocalDateTime encryptDate;      // 암호화 일시
    private String delYn;                   // 삭제 여부 (Y: 삭제, N: 사용)
    private LocalDateTime delDate;          // 삭제 일시
    private LocalDateTime updDate;          // 수정 일시
    
    // 기본 생성자
    public EmpEncryptVO() {}
    
    // 전체 필드 생성자
    public EmpEncryptVO(Long empSeq, String origEmpNo, String encryptEmpNo, 
                       String origEmpNm, String randomEmpNm, String origEmpEmail,
                       String encryptAlgorithm, String encryptKeyId, String encryptIv, 
                       LocalDateTime encryptDate, String delYn, LocalDateTime delDate, 
                       LocalDateTime updDate) {
        this.empSeq = empSeq;
        this.origEmpNo = origEmpNo;
        this.encryptEmpNo = encryptEmpNo;
        this.origEmpNm = origEmpNm;
        this.randomEmpNm = randomEmpNm;
        this.origEmpEmail = origEmpEmail;
        this.encryptAlgorithm = encryptAlgorithm;
        this.encryptKeyId = encryptKeyId;
        this.encryptIv = encryptIv;
        this.encryptDate = encryptDate;
        this.delYn = delYn;
        this.delDate = delDate;
        this.updDate = updDate;
    }
    
    // Getter/Setter 메서드들
    public Long getEmpSeq() {
        return empSeq;
    }
    
    public void setEmpSeq(Long empSeq) {
        this.empSeq = empSeq;
    }
    
    public String getOrigEmpNo() {
        return origEmpNo;
    }
    
    public void setOrigEmpNo(String origEmpNo) {
        this.origEmpNo = origEmpNo;
    }
    
    public String getEncryptEmpNo() {
        return encryptEmpNo;
    }
    
    public void setEncryptEmpNo(String encryptEmpNo) {
        this.encryptEmpNo = encryptEmpNo;
    }
    
    public String getOrigEmpNm() {
        return origEmpNm;
    }
    
    public void setOrigEmpNm(String origEmpNm) {
        this.origEmpNm = origEmpNm;
    }
    
    public String getRandomEmpNm() {
        return randomEmpNm;
    }
    
    public void setRandomEmpNm(String randomEmpNm) {
        this.randomEmpNm = randomEmpNm;
    }
    
    public String getOrigEmpEmail() {
        return origEmpEmail;
    }
    
    public void setOrigEmpEmail(String origEmpEmail) {
        this.origEmpEmail = origEmpEmail;
    }
    
    public String getEncryptAlgorithm() {
        return encryptAlgorithm;
    }
    
    public void setEncryptAlgorithm(String encryptAlgorithm) {
        this.encryptAlgorithm = encryptAlgorithm;
    }
    
    public String getEncryptKeyId() {
        return encryptKeyId;
    }
    
    public void setEncryptKeyId(String encryptKeyId) {
        this.encryptKeyId = encryptKeyId;
    }
    
    public String getEncryptIv() {
        return encryptIv;
    }
    
    public void setEncryptIv(String encryptIv) {
        this.encryptIv = encryptIv;
    }
    
    public LocalDateTime getEncryptDate() {
        return encryptDate;
    }
    
    public void setEncryptDate(LocalDateTime encryptDate) {
        this.encryptDate = encryptDate;
    }
    
    public String getDelYn() {
        return delYn;
    }
    
    public void setDelYn(String delYn) {
        this.delYn = delYn;
    }
    
    public LocalDateTime getDelDate() {
        return delDate;
    }
    
    public void setDelDate(LocalDateTime delDate) {
        this.delDate = delDate;
    }
    
    public LocalDateTime getUpdDate() {
        return updDate;
    }
    
    public void setUpdDate(LocalDateTime updDate) {
        this.updDate = updDate;
    }
    
    @Override
    public String toString() {
        return "EmpEncryptVO{" +
                "empSeq=" + empSeq +
                ", origEmpNo='" + origEmpNo + '\'' +
                ", encryptEmpNo='" + encryptEmpNo + '\'' +
                ", origEmpNm='" + origEmpNm + '\'' +
                ", randomEmpNm='" + randomEmpNm + '\'' +
                ", origEmpEmail='" + origEmpEmail + '\'' +
                ", encryptAlgorithm='" + encryptAlgorithm + '\'' +
                ", encryptKeyId='" + encryptKeyId + '\'' +
                ", encryptIv='" + encryptIv + '\'' +
                ", encryptDate=" + encryptDate +
                ", delYn='" + delYn + '\'' +
                ", delDate=" + delDate +
                ", updDate=" + updDate +
                '}';
    }
} 