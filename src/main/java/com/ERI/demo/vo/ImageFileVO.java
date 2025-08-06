package com.ERI.demo.vo;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

/**
 * 이미지 파일 VO
 * TB_IMG_FILE_LST 테이블과 매핑
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImageFileVO {
    
    // 기본 정보
    private Long imgFileSeq;       // 이미지 파일 시퀀스 (PK)
    private Long imgBrdSeq;        // 이미지 게시판 시퀀스
    private String imgFileNm;      // 이미지 파일명
    private String imgFilePath;    // 이미지 파일 경로
    private Long imgFileSize;      // 이미지 파일 크기
    private String imgFileExt;     // 이미지 파일 확장자
    private String imgText;        // 이미지 관련 텍스트
    private Integer imgOrd;        // 이미지 순서
    
    // 공통 정보
    private String regEmpId;       // 등록 직원 ID
    private String updEmpId;       // 수정 직원 ID
    private String delYn;          // 삭제 여부 (Y/N)
    private LocalDateTime delDt;   // 삭제 일시
    private LocalDateTime regDt;   // 등록 일시
    private LocalDateTime updDt;   // 수정 일시
    
    // 검색/페이징 관련
    private Integer page;          // 페이지 번호
    private Integer size;          // 페이지 크기
    private String searchType;     // 검색 타입
    private String searchKeyword;  // 검색 키워드
    private String sortBy;         // 정렬 필드
    private String sortDirection;  // 정렬 방향
    
    // 연관 데이터
    private String regEmpNm;       // 등록자명
    private String updEmpNm;       // 수정자명
    private String imageUrl;       // 이미지 URL
    private String thumbnailUrl;   // 썸네일 URL
    private String fileSizeFormatted; // 포맷된 파일 크기
    private Boolean isSelected;    // 선택 여부
    private Integer selectionCount; // 선택 횟수
    
    // 생성자 (기본 필드)
    public ImageFileVO(Long imgBrdSeq, String imgFileNm, String imgFilePath, 
                      Long imgFileSize, String imgFileExt, String imgText, 
                      Integer imgOrd, String regEmpId) {
        this.imgBrdSeq = imgBrdSeq;
        this.imgFileNm = imgFileNm;
        this.imgFilePath = imgFilePath;
        this.imgFileSize = imgFileSize;
        this.imgFileExt = imgFileExt;
        this.imgText = imgText;
        this.imgOrd = imgOrd != null ? imgOrd : 0;
        this.regEmpId = regEmpId;
        this.delYn = "N";
    }
    
    // 파일 크기 포맷팅
    public String getFileSizeFormatted() {
        if (imgFileSize == null) return "0 B";
        
        long size = imgFileSize;
        String[] units = {"B", "KB", "MB", "GB"};
        int unitIndex = 0;
        
        while (size >= 1024 && unitIndex < units.length - 1) {
            size /= 1024;
            unitIndex++;
        }
        
        return String.format("%.1f %s", (double) size, units[unitIndex]);
    }
    
    // 이미지 파일 여부 확인
    public boolean isImageFile() {
        if (imgFileExt == null) return false;
        String ext = imgFileExt.toLowerCase();
        return ext.matches("(jpg|jpeg|png|gif|bmp|webp)");
    }
    
    // 이미지 URL 생성
    public String getImageUrl() {
        if (imgFilePath == null || imgFileNm == null) return null;
        return imgFilePath + "/" + imgFileNm;
    }
    
    // 썸네일 URL 생성
    public String getThumbnailUrl() {
        if (imgFilePath == null || imgFileNm == null) return null;
        String fileName = imgFileNm;
        int lastDot = fileName.lastIndexOf(".");
        if (lastDot > 0) {
            String name = fileName.substring(0, lastDot);
            String ext = fileName.substring(lastDot);
            return imgFilePath + "/thumbnails/" + name + "_thumb" + ext;
        }
        return imgFilePath + "/thumbnails/" + fileName;
    }
    
    // 파일 확장자 추출
    public String getFileExtension() {
        if (imgFileNm == null || imgFileNm.lastIndexOf(".") == -1) {
            return "";
        }
        return imgFileNm.substring(imgFileNm.lastIndexOf(".") + 1).toLowerCase();
    }
    
    // 파일명만 추출 (확장자 제외)
    public String getFileNameWithoutExtension() {
        if (imgFileNm == null) return "";
        int lastDot = imgFileNm.lastIndexOf(".");
        return lastDot > 0 ? imgFileNm.substring(0, lastDot) : imgFileNm;
    }
} 