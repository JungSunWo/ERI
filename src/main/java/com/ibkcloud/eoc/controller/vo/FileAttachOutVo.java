package com.ibkcloud.eoc.controller.vo;

import com.ibkcloud.eoc.cmm.dto.IbkCldEocDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class FileAttachOutVo extends IbkCldEocDto {
    
    // 기본 정보
    private Long fileSeq;                  // 파일 일련번호
    private String refTblCd;               // 참조 테이블 코드
    private String refPkVal;               // 참조 PK 값
    private String fileName;                // 파일명
    private String originalFileName;        // 원본 파일명
    private String filePath;                // 파일 경로
    private String fileExt;                 // 파일 확장자
    private Long fileSize;                  // 파일 크기 (bytes)
    private String fileType;                // 파일 타입 (MIME type)
    private String fileDesc;                // 파일 설명
    private String stsCd;                   // 상태 코드 (STS001: 정상, STS002: 삭제)
    
    // 작성자 정보
    private String regEmpId;                // 등록직원ID
    private String updEmpId;                // 수정직원ID
    private String regEmpNm;                // 등록직원명
    private String updEmpNm;                // 수정직원명
    
    // 삭제 정보
    private String delYn;                   // 삭제여부
    private LocalDateTime delDate;          // 삭제일시
    
    // 등록/수정 정보
    private LocalDateTime regDate;          // 등록일시
    private LocalDateTime updDate;          // 수정일시
    
    // 목록 조회용
    private List<FileAttachOutVo> data;    // 첨부파일 목록
    private Integer count;                  // 총 개수
    
    // 추가 정보
    private String downloadUrl;             // 다운로드 URL
    private String previewUrl;              // 미리보기 URL
    private String fileSizeFormatted;       // 포맷된 파일 크기
    private Boolean isImage;                // 이미지 파일 여부
}
