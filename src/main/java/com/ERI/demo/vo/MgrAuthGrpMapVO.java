package com.ERI.demo.vo;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;

/**
 * 관리자-권한그룹 매핑 VO
 */
public class MgrAuthGrpMapVO {
    private String mgrEmpId;         // 관리자직원번호
    private String authGrpCd;        // 권한그룹코드
    private LocalDate rgstDt;        // 등록일자
    private LocalTime rgstTm;        // 등록시간
    private String regEmpId;         // 등록직원번호
    private LocalDate updtDt;        // 수정일자
    private LocalTime updtTm;        // 수정시간
    private String updEmpId;         // 수정직원번호
    private String delYn;            // 삭제 여부
    private LocalDateTime delDate;   // 삭제 일시
    private LocalDateTime regDate;   // 등록 일시
    private LocalDateTime updDate;   // 수정 일시

    // 기본 생성자
    public MgrAuthGrpMapVO() {}

    // 전체 필드 생성자
    public MgrAuthGrpMapVO(String mgrEmpId, String authGrpCd, LocalDate rgstDt, LocalTime rgstTm, 
                          String regEmpId, LocalDate updtDt, LocalTime updtTm, String updEmpId, 
                          String delYn, LocalDateTime delDate, LocalDateTime regDate, LocalDateTime updDate) {
        this.mgrEmpId = mgrEmpId;
        this.authGrpCd = authGrpCd;
        this.rgstDt = rgstDt;
        this.rgstTm = rgstTm;
        this.regEmpId = regEmpId;
        this.updtDt = updtDt;
        this.updtTm = updtTm;
        this.updEmpId = updEmpId;
        this.delYn = delYn;
        this.delDate = delDate;
        this.regDate = regDate;
        this.updDate = updDate;
    }

    // Getter와 Setter
    public String getMgrEmpId() { return mgrEmpId; }
    public void setMgrEmpId(String mgrEmpId) { this.mgrEmpId = mgrEmpId; }

    public String getAuthGrpCd() { return authGrpCd; }
    public void setAuthGrpCd(String authGrpCd) { this.authGrpCd = authGrpCd; }

    public LocalDate getRgstDt() { return rgstDt; }
    public void setRgstDt(LocalDate rgstDt) { this.rgstDt = rgstDt; }

    public LocalTime getRgstTm() { return rgstTm; }
    public void setRgstTm(LocalTime rgstTm) { this.rgstTm = rgstTm; }

    public String getRegEmpId() { return regEmpId; }
    public void setRegEmpId(String regEmpId) { this.regEmpId = regEmpId; }

    public LocalDate getUpdtDt() { return updtDt; }
    public void setUpdtDt(LocalDate updtDt) { this.updtDt = updtDt; }

    public LocalTime getUpdtTm() { return updtTm; }
    public void setUpdtTm(LocalTime updtTm) { this.updtTm = updtTm; }

    public String getUpdEmpId() { return updEmpId; }
    public void setUpdEmpId(String updEmpId) { this.updEmpId = updEmpId; }

    public String getDelYn() { return delYn; }
    public void setDelYn(String delYn) { this.delYn = delYn; }

    public LocalDateTime getDelDate() { return delDate; }
    public void setDelDate(LocalDateTime delDate) { this.delDate = delDate; }

    public LocalDateTime getRegDate() { return regDate; }
    public void setRegDate(LocalDateTime regDate) { this.regDate = regDate; }

    public LocalDateTime getUpdDate() { return updDate; }
    public void setUpdDate(LocalDateTime updDate) { this.updDate = updDate; }

    @Override
    public String toString() {
        return "MgrAuthGrpMapVO{" +
                "mgrEmpId='" + mgrEmpId + '\'' +
                ", authGrpCd='" + authGrpCd + '\'' +
                ", rgstDt=" + rgstDt +
                ", rgstTm=" + rgstTm +
                ", regEmpId='" + regEmpId + '\'' +
                ", updtDt=" + updtDt +
                ", updtTm=" + updtTm +
                ", updEmpId='" + updEmpId + '\'' +
                ", delYn='" + delYn + '\'' +
                ", delDate=" + delDate +
                ", regDate=" + regDate +
                ", updDate=" + updDate +
                '}';
    }
} 