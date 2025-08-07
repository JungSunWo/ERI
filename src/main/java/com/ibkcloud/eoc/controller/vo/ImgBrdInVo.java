package com.ibkcloud.eoc.controller.vo;

import com.ibkcloud.eoc.cmm.dto.IbkCldEocDto;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
@EqualsAndHashCode(callSuper = true)
public class ImgBrdInVo extends IbkCldEocDto {
    
    // 기본 정보
    private Long imgBrdSeq;                     // 이미지 게시판 일련번호
    private Long imgFileSeq;                    // 이미지 파일 일련번호
    
    @Size(max = 20, message = "직원 ID는 20자를 초과할 수 없습니다.")
    private String empId;                        // 직원 ID
    
    @Size(max = 20, message = "선택 직원 ID는 20자를 초과할 수 없습니다.")
    private String selEmpId;                     // 선택 직원 ID
    
    @Size(max = 500, message = "이미지 설명은 500자를 초과할 수 없습니다.")
    private String imgDesc;                      // 이미지 설명
    
    @Size(max = 200, message = "파일명은 200자를 초과할 수 없습니다.")
    private String fileName;                     // 파일명
    
    @Size(max = 500, message = "파일 경로는 500자를 초과할 수 없습니다.")
    private String filePath;                     // 파일 경로
    
    @Size(max = 100, message = "파일 확장자는 100자를 초과할 수 없습니다.")
    private String fileExt;                      // 파일 확장자
    
    private Long fileSize;                       // 파일 크기
    
    @Size(max = 10, message = "상태 코드는 10자를 초과할 수 없습니다.")
    private String stsCd;                        // 상태 코드 (STS001: 정상, STS002: 삭제)
    
    @Size(max = 1, message = "선택 여부는 1자를 초과할 수 없습니다.")
    private String selYn;                        // 선택 여부 (Y: 선택, N: 미선택)
    
    // 등록/수정 정보
    private LocalDateTime regDate;               // 등록일시
    private LocalDateTime updDate;               // 수정일시
    
    // 파일 업로드용
    private List<MultipartFile> files;          // 업로드할 파일 목록
    private List<String> texts;                  // 이미지 설명 목록
    
    // 추가 정보
    private String requestBody;                  // 요청 본문
    private Map<String, String> requestMap;      // 요청 맵
} 