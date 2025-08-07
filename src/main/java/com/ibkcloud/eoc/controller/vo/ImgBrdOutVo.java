package com.ibkcloud.eoc.controller.vo;

import com.ibkcloud.eoc.cmm.dto.IbkCldEocDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
@EqualsAndHashCode(callSuper = true)
public class ImgBrdOutVo extends IbkCldEocDto {
    
    // 기본 정보
    private Long imgBrdSeq;                     // 이미지 게시판 일련번호
    private Long imgFileSeq;                    // 이미지 파일 일련번호
    private String empId;                        // 직원 ID
    private String selEmpId;                     // 선택 직원 ID
    private String imgDesc;                      // 이미지 설명
    private String fileName;                     // 파일명
    private String filePath;                     // 파일 경로
    private String fileExt;                      // 파일 확장자
    private Long fileSize;                       // 파일 크기
    private String stsCd;                        // 상태 코드 (STS001: 정상, STS002: 삭제)
    private String selYn;                        // 선택 여부 (Y: 선택, N: 미선택)
    
    // 등록/수정 정보
    private LocalDateTime regDate;               // 등록일시
    private LocalDateTime updDate;               // 수정일시
    
    // 목록 조회용
    private List<ImgBrdOutVo> data;             // 이미지 목록
    private Integer count;                       // 총 개수
    
    // 이미지 선택 정보
    private List<ImgBrdOutVo> selectedImages;   // 선택된 이미지 목록
    private Map<String, Object> stats;           // 통계 정보
    
    // 추가 정보
    private String empNm;                        // 직원명
    private String selEmpNm;                     // 선택 직원명
    private Integer selCnt;                      // 선택된 개수
    private String selectedEmpList;              // 선택한 직원 목록
    private Boolean isSelected;                  // 선택 여부
    private String imgBrdTitle;                  // 이미지 게시판 제목
} 