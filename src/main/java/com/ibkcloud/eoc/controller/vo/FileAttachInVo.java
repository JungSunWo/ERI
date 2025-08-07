package com.ibkcloud.eoc.controller.vo;

import com.ibkcloud.eoc.cmm.dto.IbkCldEocDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class FileAttachInVo extends IbkCldEocDto {
    
    // 기본 정보
    private Long fileSeq;                  // 파일 일련번호
    
    @NotBlank(message = "참조 테이블 코드는 필수입니다.")
    @Size(max = 20, message = "참조 테이블 코드는 20자를 초과할 수 없습니다.")
    private String refTblCd;               // 참조 테이블 코드
    
    @NotBlank(message = "참조 PK 값은 필수입니다.")
    @Size(max = 50, message = "참조 PK 값은 50자를 초과할 수 없습니다.")
    private String refPkVal;               // 참조 PK 값
    
    @Size(max = 200, message = "파일명은 200자를 초과할 수 없습니다.")
    private String fileName;                // 파일명
    
    @Size(max = 200, message = "원본 파일명은 200자를 초과할 수 없습니다.")
    private String originalFileName;        // 원본 파일명
    
    @Size(max = 500, message = "파일 경로는 500자를 초과할 수 없습니다.")
    private String filePath;                // 파일 경로
    
    @Size(max = 20, message = "파일 확장자는 20자를 초과할 수 없습니다.")
    private String fileExt;                 // 파일 확장자
    
    private Long fileSize;                  // 파일 크기 (bytes)
    
    @Size(max = 100, message = "파일 타입은 100자를 초과할 수 없습니다.")
    private String fileType;                // 파일 타입 (MIME type)
    
    @Size(max = 200, message = "파일 설명은 200자를 초과할 수 없습니다.")
    private String fileDesc;                // 파일 설명
    
    @Size(max = 10, message = "상태 코드는 10자를 초과할 수 없습니다.")
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
    
    // 파일 업로드용
    private MultipartFile[] files;          // 업로드할 파일들
    private String[] fileDescs;             // 파일 설명들
    
    // 추가 정보
    private String downloadUrl;             // 다운로드 URL
    private String previewUrl;              // 미리보기 URL
    private String fileSizeFormatted;       // 포맷된 파일 크기
}
