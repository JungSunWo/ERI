package com.ibkcloud.eoc.controller.vo;

import com.ibkcloud.eoc.cmm.dto.IbkCldEocDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
public class EmpRightsFileAttachOutVo extends IbkCldEocDto {
    
    // 기본 정보
    private Long seq;                    // 첨부파일 일련번호
    private Long boardSeq;               // 게시글 일련번호
    private String fileName;             // 파일명
    private String originalFileName;     // 원본파일명
    private String filePath;             // 파일경로
    private String fileExt;              // 파일확장자
    private Long fileSize;               // 파일크기 (bytes)
    private String fileType;             // 파일타입 (ATT001: 게시글)
    private String stsCd;                // 상태코드 (STS001: 정상, STS002: 삭제)
    
    // 작성자 정보
    private String regEmpId;             // 등록직원ID
    private String updEmpId;             // 수정직원ID
    private String regEmpNm;             // 등록직원명
    private String updEmpNm;             // 수정직원명
    
    // 삭제 정보
    private String delYn;                // 삭제여부
    private LocalDateTime delDate;       // 삭제일시
    
    // 등록/수정 정보
    private LocalDateTime regDate;       // 등록일시
    private LocalDateTime updDate;       // 수정일시
    
    // 추가 정보
    private String fileSizeFormatted;    // 포맷된 파일크기 (KB, MB 등)
    private String downloadUrl;          // 다운로드 URL
    private String previewUrl;           // 미리보기 URL
}
