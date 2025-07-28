package com.ERI.demo.vo;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 파일 첨부 VO
 * TB_FILE_ATTACH (공지사항용)와 TB_BOARD_FILE_ATTACH (게시판용) 모두 지원
 * @author ERI
 */
@Data
public class FileAttachVO {
    
    /**
     * 파일 시퀀스
     */
    private Long fileSeq;
    
    /**
     * 게시글 시퀀스 (TB_BOARD_FILE_ATTACH용)
     */
    private Long boardSeq;
    
    /**
     * 참조 테이블 코드 (TB_FILE_ATTACH용)
     */
    private String refTblCd;
    
    /**
     * 참조 PK 값 (TB_FILE_ATTACH용)
     */
    private String refPkVal;
    
    /**
     * 원본 파일명
     */
    private String originalFileName;
    
    /**
     * 파일명 (TB_FILE_ATTACH용)
     */
    private String fileNm;
    
    /**
     * 저장 파일명
     */
    private String storedFileName;
    
    /**
     * 저장 파일명 (TB_FILE_ATTACH용)
     */
    private String fileSaveNm;
    
    /**
     * 파일 경로
     */
    private String filePath;
    
    /**
     * 파일 크기 (bytes)
     */
    private Long fileSize;
    
    /**
     * 파일 확장자
     */
    private String fileExt;
    
    /**
     * 파일 타입 (MIME 타입, TB_BOARD_FILE_ATTACH용)
     */
    private String fileType;
    
    /**
     * 파일 MIME 타입 (TB_FILE_ATTACH용)
     */
    private String fileMimeType;
    
    /**
     * 파일 설명 (TB_FILE_ATTACH용)
     */
    private String fileDesc;
    
    /**
     * 파일 순서 (TB_FILE_ATTACH용)
     */
    private Integer fileOrd;
    
    /**
     * 다운로드 횟수
     */
    private Integer downloadCnt;
    
    /**
     * 등록자 ID (TB_BOARD_FILE_ATTACH용)
     */
    private String regId;
    
    /**
     * 등록일시 (TB_BOARD_FILE_ATTACH용)
     */
    private LocalDateTime regDt;
    
    /**
     * 수정자 ID (TB_BOARD_FILE_ATTACH용)
     */
    private String updId;
    
    /**
     * 수정일시 (TB_BOARD_FILE_ATTACH용)
     */
    private LocalDateTime updDt;
    
    /**
     * 등록자 ID (TB_FILE_ATTACH용)
     */
    private String regEmpId;
    
    /**
     * 수정자 ID (TB_FILE_ATTACH용)
     */
    private String updEmpId;
    
    /**
     * 삭제 여부 (Y/N)
     */
    private String delYn;
    
    /**
     * 삭제일시 (TB_FILE_ATTACH용)
     */
    private LocalDateTime delDate;
    
    /**
     * 등록일시 (TB_FILE_ATTACH용)
     */
    private LocalDateTime regDate;
    
    /**
     * 수정일시 (TB_FILE_ATTACH용)
     */
    private LocalDateTime updDate;
    
    /**
     * 파일 크기 표시용 (KB, MB 등)
     */
    private String fileSizeDisplay;
    
    /**
     * 다운로드 URL
     */
    private String downloadUrl;
    
    /**
     * 미리보기 URL
     */
    private String previewUrl;
    
    /**
     * 이미지 링크 정보 (JSON 배열 형태로 저장)
     * 예: [
     *   {
     *     "id": "link1",
     *     "url": "http://example.com",
     *     "text": "링크 텍스트",
     *     "position": {"x": 100, "y": 200, "width": 150, "height": 50}
     *   }
     * ]
     */
    private String imageLinks;
} 