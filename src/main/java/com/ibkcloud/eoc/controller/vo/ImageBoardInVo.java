package com.ibkcloud.eoc.controller.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 이미지 게시판 입력 VO
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ImageBoardInVo {
    
    private Long imgBrdSeq;
    
    @NotBlank(message = "이미지 게시판 제목은 필수입니다.")
    @Size(max = 200, message = "이미지 게시판 제목은 200자 이하여야 합니다.")
    private String imgBrdTitl;
    
    @Size(max = 1000, message = "이미지 게시판 내용은 1000자 이하여야 합니다.")
    private String imgBrdCntn;
    
    @Size(max = 20, message = "이미지 게시판 상태는 20자 이하여야 합니다.")
    private String imgBrdSts;
    
    @Size(max = 100, message = "이미지 게시판 상태명은 100자 이하여야 합니다.")
    private String imgBrdStsNm;
    
    @Size(max = 20, message = "이미지 게시판 유형은 20자 이하여야 합니다.")
    private String imgBrdTy;
    
    @Size(max = 100, message = "이미지 게시판 유형명은 100자 이하여야 합니다.")
    private String imgBrdTyNm;
    
    @Size(max = 20, message = "이미지 게시판 카테고리는 20자 이하여야 합니다.")
    private String imgBrdCtgry;
    
    @Size(max = 100, message = "이미지 게시판 카테고리명은 100자 이하여야 합니다.")
    private String imgBrdCtgryNm;
    
    @Size(max = 20, message = "조회수는 20자 이하여야 합니다.")
    private String viewCnt;
    
    @Size(max = 20, message = "좋아요 수는 20자 이하여야 합니다.")
    private String likeCnt;
    
    @Size(max = 20, message = "댓글 수는 20자 이하여야 합니다.")
    private String commentCnt;
    
    @Size(max = 20, message = "시작일자는 20자 이하여야 합니다.")
    private String startDt;
    
    @Size(max = 20, message = "종료일자는 20자 이하여야 합니다.")
    private String endDt;
    
    @Size(max = 1000, message = "비고는 1000자 이하여야 합니다.")
    private String remark;
    
    // 시스템 필드들
    private String regEmpId;
    private String updEmpId;
} 