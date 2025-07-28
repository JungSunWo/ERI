package com.ERI.demo.vo;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 이미지 선택 VO
 */
@Data
public class ImgSelLstVO {
    
    private Long imgSelSeq;           // 이미지 선택 시퀀스
    private Long imgBrdSeq;           // 이미지 게시판 시퀀스
    private Long imgFileSeq;          // 이미지 파일 시퀀스
    private String selEmpId;          // 선택한 직원 ID
    private LocalDateTime selDate;    // 선택 일시
    
    // 추가 필드 (조회 시 사용)
    private String selEmpNm;          // 선택한 직원명
    private String imgFileNm;         // 이미지 파일명
    private String imgFilePath;       // 이미지 파일 경로
    private String imgText;           // 이미지 관련 텍스트
    private Integer selCnt;           // 선택된 개수 (통계용)
    private String selectedEmpList;   // 선택한 직원 목록 (통계용)
} 