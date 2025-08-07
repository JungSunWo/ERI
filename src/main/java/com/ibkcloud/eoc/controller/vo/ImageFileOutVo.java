package com.ibkcloud.eoc.controller.vo;

import com.ibkcloud.eoc.cmm.dto.IbkCldEocDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class ImageFileOutVo extends IbkCldEocDto {
    
    // 기본 정보
    private Long imgFileSeq;                // 이미지 파일 일련번호
    private Long imgBrdSeq;                 // 이미지 게시판 일련번호
    private String imgFileNm;                // 이미지 파일명
    private String originalFileName;         // 원본 파일명
    private String filePath;                 // 파일 경로
    private String fileExt;                  // 파일 확장자
    private Long fileSize;                   // 파일 크기 (bytes)
    private String fileType;                 // 파일 타입 (MIME type)
    private String imgText;                  // 이미지 텍스트
    private Integer imgOrd;                  // 이미지 순서
    private String stsCd;                    // 상태 코드 (STS001: 정상, STS002: 삭제)
    
    // 작성자 정보
    private String regEmpId;                 // 등록직원ID
    private String updEmpId;                 // 수정직원ID
    private String regEmpNm;                 // 등록직원명
    private String updEmpNm;                 // 수정직원명
    
    // 삭제 정보
    private String delYn;                    // 삭제여부
    private LocalDateTime delDate;           // 삭제일시
    
    // 등록/수정 정보
    private LocalDateTime regDate;           // 등록일시
    private LocalDateTime updDate;           // 수정일시
    
    // 목록 조회용
    private List<ImageFileOutVo> data;      // 이미지 파일 목록
    private Integer count;                   // 총 개수
    private Integer pageNo;                  // 현재 페이지
    private Integer pageSize;                // 페이지 크기
    private Integer ttalPageNbi;             // 총 페이지 수
    
    // 추가 정보
    private String downloadUrl;              // 다운로드 URL
    private String previewUrl;               // 미리보기 URL
    private String fileSizeFormatted;        // 포맷된 파일 크기
    private Boolean exists;                  // 파일명 존재 여부
} 