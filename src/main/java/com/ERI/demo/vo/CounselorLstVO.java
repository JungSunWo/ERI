package com.ERI.demo.vo;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;

/**
 * 상담사 목록 VO
 * TB_CNSLR_LST 테이블과 매핑
 */
public class CounselorLstVO {
    
    private Integer rowNum;                    // 순번 (ROW_NUMBER)
    private String counselorEmpId;             // 상담사 직원 ID
    private String counselorInfoClsfCd;        // 상담자정보구분코드
    private String regEmpId;                   // 등록직원ID
    private String updEmpId;                   // 수정직원ID
    private String delYn;                      // 삭제 여부
    private LocalDateTime delDate;             // 삭제 일시
    private LocalDateTime regDate;             // 등록 일시
    private LocalDateTime updDate;             // 수정 일시
    
    // TB_EMP_REF 테이블에서 JOIN으로 가져오는 필드들
    private String eriEmpId;                   // ERI 시스템 내부 식별번호
    private String empNm;                      // 직원명
    private String blngBrcd;                   // 소속부점코드
    private String beteamCd;                   // 소속팀코드
    private String jbttCd;                     // 직위코드
    private String jbclCd;                     // 직급코드
    private String empCpn;                     // 직원휴대폰번호
    private String ead;                        // 이메일주소
    
    // 상담자정보구분 코드명 (공통코드에서 가져옴)
    private String counselorInfoClsfNm;        // 상담자정보구분명
    
    // 기본 생성자
    public CounselorLstVO() {}
    
    // 전체 필드 생성자
    public CounselorLstVO(String counselorEmpId, String counselorInfoClsfCd, String regEmpId, String updEmpId, 
                         String delYn, LocalDateTime delDate, LocalDateTime regDate, LocalDateTime updDate) {
        this.rowNum = null; // 순번은 DB에서 자동 생성
        this.counselorEmpId = counselorEmpId;
        this.counselorInfoClsfCd = counselorInfoClsfCd;
        this.regEmpId = regEmpId;
        this.updEmpId = updEmpId;
        this.delYn = delYn;
        this.delDate = delDate;
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
    
    public String getCounselorEmpId() {
        return counselorEmpId;
    }
    
    public void setCounselorEmpId(String counselorEmpId) {
        this.counselorEmpId = counselorEmpId;
    }
    
    public String getCounselorInfoClsfCd() {
        return counselorInfoClsfCd;
    }
    
    public void setCounselorInfoClsfCd(String counselorInfoClsfCd) {
        this.counselorInfoClsfCd = counselorInfoClsfCd;
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
    
    // TB_EMP_REF 필드들의 Getter와 Setter
    public String getEriEmpId() {
        return eriEmpId;
    }
    
    public void setEriEmpId(String eriEmpId) {
        this.eriEmpId = eriEmpId;
    }
    
    public String getEmpNm() {
        return empNm;
    }
    
    public void setEmpNm(String empNm) {
        this.empNm = empNm;
    }
    
    public String getBlngBrcd() {
        return blngBrcd;
    }
    
    public void setBlngBrcd(String blngBrcd) {
        this.blngBrcd = blngBrcd;
    }
    
    public String getBeteamCd() {
        return beteamCd;
    }
    
    public void setBeteamCd(String beteamCd) {
        this.beteamCd = beteamCd;
    }
    
    public String getJbttCd() {
        return jbttCd;
    }
    
    public void setJbttCd(String jbttCd) {
        this.jbttCd = jbttCd;
    }
    
    public String getJbclCd() {
        return jbclCd;
    }
    
    public void setJbclCd(String jbclCd) {
        this.jbclCd = jbclCd;
    }
    
    public String getEmpCpn() {
        return empCpn;
    }
    
    public void setEmpCpn(String empCpn) {
        this.empCpn = empCpn;
    }
    
    public String getEad() {
        return ead;
    }
    
    public void setEad(String ead) {
        this.ead = ead;
    }
    
    // 상담자정보구분명 Getter와 Setter
    public String getCounselorInfoClsfNm() {
        return counselorInfoClsfNm;
    }
    
    public void setCounselorInfoClsfNm(String counselorInfoClsfNm) {
        this.counselorInfoClsfNm = counselorInfoClsfNm;
    }
    
    @Override
    public String toString() {
        return "CounselorLstVO{" +
                "counselorEmpId='" + counselorEmpId + '\'' +
                ", counselorInfoClsfCd='" + counselorInfoClsfCd + '\'' +
                ", counselorInfoClsfNm='" + counselorInfoClsfNm + '\'' +
                ", regEmpId='" + regEmpId + '\'' +
                ", updEmpId='" + updEmpId + '\'' +
                ", delYn='" + delYn + '\'' +
                ", delDate=" + delDate +
                ", regDate=" + regDate +
                ", updDate=" + updDate +
                ", eriEmpId='" + eriEmpId + '\'' +
                ", empNm='" + empNm + '\'' +
                ", blngBrcd='" + blngBrcd + '\'' +
                ", beteamCd='" + beteamCd + '\'' +
                ", jbttCd='" + jbttCd + '\'' +
                ", jbclCd='" + jbclCd + '\'' +
                ", empCpn='" + empCpn + '\'' +
                ", ead='" + ead + '\'' +
                '}';
    }
} 