package com.ERI.demo.vo;

import java.time.LocalDateTime;

/**
 * 이미지 파일 VO
 * TB_IMG_FILE_LST 테이블과 매핑
 */
public class ImgFileLstVO {
    
    private Long imgFileSeq;                     // 이미지 파일 시퀀스
    private Long imgBrdSeq;                      // 이미지 게시판 시퀀스
    private String imgFileName;                  // 이미지 파일명 (원본)
    private String imgFileNm;                    // 이미지 파일명 (MyBatis 매핑용)
    private String imgFilePath;                  // 이미지 파일 경로
    private Long imgFileSize;                    // 이미지 파일 크기
    private String imgFileExt;                   // 이미지 파일 확장자
    private String imgText;                      // 이미지 관련 텍스트
    private Integer imgOrdr;                     // 이미지 순서
    private String eriEmpId;                     // 직원 ID (공통)
    private String regEmpId;                     // 등록자 ID
    private String updEmpId;                     // 수정자 ID
    private String delYn;                        // 삭제 여부
    private LocalDateTime delDate;               // 삭제 일시
    private LocalDateTime regDate;               // 등록 일시
    private LocalDateTime updDate;               // 수정 일시
    
    // 추가 필드 (조회 시 사용)
    private Boolean isSelected;                  // 선택 여부
    private String imgBrdTitl;                  // 이미지 게시판 제목
    
    // 기본 생성자
    public ImgFileLstVO() {}
    
    // 전체 필드 생성자
    public ImgFileLstVO(Long imgBrdSeq, String imgFileName, String imgFilePath, Long imgFileSize, 
                        String imgFileExt, String imgText, Integer imgOrdr, String eriEmpId) {
        this.imgBrdSeq = imgBrdSeq;
        this.imgFileName = imgFileName;
        this.imgFileNm = imgFileName; // MyBatis 매핑을 위해 동일한 값 설정
        this.imgFilePath = imgFilePath;
        this.imgFileSize = imgFileSize;
        this.imgFileExt = imgFileExt;
        this.imgText = imgText;
        this.imgOrdr = imgOrdr;
        this.eriEmpId = eriEmpId;
        this.delYn = "N";
    }
    
    // Getter와 Setter
    public Long getImgFileSeq() {
        return imgFileSeq;
    }
    
    public void setImgFileSeq(Long imgFileSeq) {
        this.imgFileSeq = imgFileSeq;
    }
    
    public Long getImgBrdSeq() {
        return imgBrdSeq;
    }
    
    public void setImgBrdSeq(Long imgBrdSeq) {
        this.imgBrdSeq = imgBrdSeq;
    }
    
    public String getImgFileName() {
        return imgFileName;
    }
    
    public void setImgFileName(String imgFileName) {
        this.imgFileName = imgFileName;
    }
    
    public String getImgFileNm() {
        return imgFileNm;
    }
    
    public void setImgFileNm(String imgFileNm) {
        this.imgFileNm = imgFileNm;
    }
    
    public String getImgFilePath() {
        return imgFilePath;
    }
    
    public void setImgFilePath(String imgFilePath) {
        this.imgFilePath = imgFilePath;
    }
    
    public Long getImgFileSize() {
        return imgFileSize;
    }
    
    public void setImgFileSize(Long imgFileSize) {
        this.imgFileSize = imgFileSize;
    }
    
    public String getImgFileExt() {
        return imgFileExt;
    }
    
    public void setImgFileExt(String imgFileExt) {
        this.imgFileExt = imgFileExt;
    }
    
    public String getImgText() {
        return imgText;
    }
    
    public void setImgText(String imgText) {
        this.imgText = imgText;
    }
    
    public Integer getImgOrdr() {
        return imgOrdr;
    }
    
    public void setImgOrdr(Integer imgOrdr) {
        this.imgOrdr = imgOrdr;
    }
    
    public String getEriEmpId() {
        return eriEmpId;
    }
    
    public void setEriEmpId(String eriEmpId) {
        this.eriEmpId = eriEmpId;
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
    
    public Boolean getIsSelected() {
        return isSelected;
    }
    
    public void setIsSelected(Boolean isSelected) {
        this.isSelected = isSelected;
    }
    
    public String getImgBrdTitl() {
        return imgBrdTitl;
    }
    
    public void setImgBrdTitl(String imgBrdTitl) {
        this.imgBrdTitl = imgBrdTitl;
    }
    
    @Override
    public String toString() {
        return "ImgFileLstVO{" +
                "imgFileSeq=" + imgFileSeq +
                ", imgBrdSeq=" + imgBrdSeq +
                ", imgFileName='" + imgFileName + '\'' +
                ", imgFileNm='" + imgFileNm + '\'' +
                ", imgFilePath='" + imgFilePath + '\'' +
                ", imgFileSize=" + imgFileSize +
                ", imgFileExt='" + imgFileExt + '\'' +
                ", imgText='" + imgText + '\'' +
                ", imgOrdr=" + imgOrdr +
                ", eriEmpId='" + eriEmpId + '\'' +
                ", regEmpId='" + regEmpId + '\'' +
                ", updEmpId='" + updEmpId + '\'' +
                ", delYn='" + delYn + '\'' +
                ", delDate=" + delDate +
                ", regDate=" + regDate +
                ", updDate=" + updDate +
                ", isSelected=" + isSelected +
                ", imgBrdTitl='" + imgBrdTitl + '\'' +
                '}';
    }
} 