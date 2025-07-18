package com.ERI.demo.vo;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;

/**
 * 관리자 목록 VO
 * TB_ADMIN_LST 테이블과 매핑
 */
public class AdminLstVO {
    
    private Integer rowNum;        // 순번 (ROW_NUMBER)
    private String adminId;        // 관리자 ID
    private String adminStsCd;     // 관리자 상태코드
    private String rgstEmpId;      // 등록직원ID
    private String updtEmpId;      // 수정직원ID
    private String delYn;          // 삭제 여부
    private LocalDateTime delDate; // 삭제 일시
    private String regEmpId;       // 등록직원ID
    private String updEmpId;       // 수정직원ID
    private LocalDateTime regDate; // 등록 일시
    private LocalDateTime updDate; // 수정 일시
    
    // 기본 생성자
    public AdminLstVO() {}
    
    // 전체 필드 생성자
    public AdminLstVO(String adminId, String adminStsCd, String rgstEmpId, String updtEmpId, 
                     String delYn, LocalDateTime delDate, String regEmpId, String updEmpId, 
                     LocalDateTime regDate, LocalDateTime updDate) {
        this.rowNum = null; // 순번은 DB에서 자동 생성
        this.adminId = adminId;
        this.adminStsCd = adminStsCd;
        this.rgstEmpId = rgstEmpId;
        this.updtEmpId = updtEmpId;
        this.delYn = delYn;
        this.delDate = delDate;
        this.regEmpId = regEmpId;
        this.updEmpId = updEmpId;
        this.regDate = regDate;
        this.updDate = updDate;
    }
    
    // Getter와 Setter
    public Integer getRowNum() {
        return rowNum;
    }
    
    public void setRowNum(Integer rowNum) {
        this.rowNum = rowNum;
    }
    
    public String getAdminId() {
        return adminId;
    }
    
    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }
    
    public String getAdminStsCd() {
        return adminStsCd;
    }
    
    public void setAdminStsCd(String adminStsCd) {
        this.adminStsCd = adminStsCd;
    }
    
    public String getRgstEmpId() {
        return rgstEmpId;
    }
    
    public void setRgstEmpId(String rgstEmpId) {
        this.rgstEmpId = rgstEmpId;
    }
    
    public String getUpdtEmpId() {
        return updtEmpId;
    }
    
    public void setUpdtEmpId(String updtEmpId) {
        this.updtEmpId = updtEmpId;
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
    
    public String getRegEmpId() {
        return regEmpId;
    }
    
    public void setRegEmpId(String regEmpId) {
        this.regEmpId = regEmpId;
    }
    
    public String getUpdEmpId() {
        return updEmpId;
    }
    
    public void setUpdEmpId(String updEmpId) {
        this.updEmpId = updEmpId;
    }
    
    public LocalDateTime getRegDate() {
        return regDate;
    }
    
    public void setRegDate(LocalDateTime regDate) {
        this.regDate = regDate;
    }
    
    public LocalDateTime getUpdDate() {
        return updDate;
    }
    
    public void setUpdDate(LocalDateTime updDate) {
        this.updDate = updDate;
    }
    
    @Override
    public String toString() {
        return "AdminLstVO{" +
                "adminId='" + adminId + '\'' +
                ", adminStsCd='" + adminStsCd + '\'' +
                ", rgstEmpId='" + rgstEmpId + '\'' +
                ", updtEmpId='" + updtEmpId + '\'' +
                ", delYn='" + delYn + '\'' +
                ", delDate=" + delDate +
                ", regEmpId='" + regEmpId + '\'' +
                ", updEmpId='" + updEmpId + '\'' +
                ", regDate=" + regDate +
                ", updDate=" + updDate +
                '}';
    }
} 