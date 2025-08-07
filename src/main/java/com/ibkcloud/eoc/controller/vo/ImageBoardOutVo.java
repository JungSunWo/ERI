package com.ibkcloud.eoc.controller.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.List;

/**
 * 이미지 게시판 출력 VO
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ImageBoardOutVo {
    
    private boolean success;
    private String message;
    private String errorCode;
    
    private Long imgBrdSeq;
    private String imgBrdTitl;
    private String imgBrdCntn;
    private String imgBrdSts;
    private String imgBrdStsNm;
    private String imgBrdTy;
    private String imgBrdTyNm;
    private String imgBrdCtgry;
    private String imgBrdCtgryNm;
    private String viewCnt;
    private String likeCnt;
    private String commentCnt;
    private String startDt;
    private String endDt;
    private String remark;
    
    // 추가 필드들
    private Boolean isDuplicate;
    private Integer selectionCount;
    
    // 이미지 파일 목록
    private List<ImageFileOutVo> imageFiles;
    
    // 시스템 필드들
    private String regEmpId;
    private String regDt;
    private String updEmpId;
    private String updDt;
} 