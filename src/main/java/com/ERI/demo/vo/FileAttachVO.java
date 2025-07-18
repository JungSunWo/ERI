package com.ERI.demo.vo;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class FileAttachVO {
    private Long fileSeq;           // 파일 일련번호
    private String refTblCd;        // 참조 테이블 코드
    private String refPkVal;        // 참조 테이블의 PK 값 (문자열)
    private String fileNm;          // 원본 파일명
    private String fileSaveNm;      // 저장된 파일명 (UUID 등)
    private String filePath;        // 파일 저장 경로
    private Long fileSize;          // 파일 크기 (bytes)
    private String fileExt;         // 파일 확장자
    private String fileMimeType;    // MIME 타입
    private String fileDesc;        // 파일 설명
    private Integer fileOrd;        // 파일 순서
    private Integer downloadCnt;    // 다운로드 횟수
    private String rgstEmpId;       // 등록자 ID
    private String updtEmpId;       // 수정자 ID
    private String delYn;           // 삭제 여부
    private LocalDateTime delDate;  // 삭제 일시
    private String regEmpId;        // 등록자 ID
    private String updEmpId;        // 수정자 ID
    private LocalDateTime regDate;  // 등록 일시
    private LocalDateTime updDate;  // 수정 일시
    
    // 추가 필드 (화면 표시용)
    private String fileSizeDisplay; // 파일 크기 표시용 (KB, MB 등)
    private String downloadUrl;     // 다운로드 URL
    private String previewUrl;      // 미리보기 URL (이미지인 경우)
} 