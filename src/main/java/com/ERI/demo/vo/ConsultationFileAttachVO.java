package com.ERI.demo.vo;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 상담 파일 첨부 VO
 */
@Data
public class ConsultationFileAttachVO {
    
    // 기본 정보
    private Long fileSeq;                // 파일 시퀀스
    private Long boardSeq;               // 상담 게시글 일련번호
    private Long answerSeq;              // 답변 일련번호
    
    // 파일 정보
    private String originalFileName;     // 원본 파일명
    private String storedFileName;       // 저장 파일명
    private String filePath;             // 파일 경로
    private Long fileSize;               // 파일 크기 (bytes)
    private String fileExt;              // 파일 확장자
    private String fileType;             // 파일 타입 (MIME 타입)
    private Integer downloadCnt;         // 다운로드 횟수
    
    // 작성자 정보
    private String regEmpId;             // 등록직원ID
    private String updEmpId;             // 수정직원ID
    
    // 삭제 정보
    private String delYn;                // 삭제여부
    private LocalDateTime delDate;       // 삭제일시
    
    // 등록/수정 정보
    private LocalDateTime regDate;       // 등록일시
    private LocalDateTime updDate;       // 수정일시
    
    // 표시용 정보
    private String fileSizeDisplay;      // 파일 크기 표시용
    private String downloadUrl;          // 다운로드 URL
    private String previewUrl;           // 미리보기 URL
} 