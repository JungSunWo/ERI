package com.ibkcloud.eoc.controller.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.List;

/**
 * 이미지 게시판 검색 출력 VO
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ImageBoardSearchOutVo {
    
    private boolean success;
    private String message;
    private String errorCode;
    
    private List<ImageBoardOutVo> data;
    private Integer count;
    private Integer pageNo;
    private Integer pageSize;
    private Integer ttalPageNbi;
    
    // 검색 조건들
    private String searchType;
    private String searchKeyword;
    private String sortBy;
    private String sortDirection;
    private String imgBrdSts;
    private String imgBrdTy;
    private String imgBrdCtgry;
    private String startDtFrom;
    private String startDtTo;
    private String endDtFrom;
    private String endDtTo;
} 