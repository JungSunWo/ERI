package com.ibkcloud.eoc.controller.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * 이미지 게시판 검색 입력 VO
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ImageBoardSearchInVo {
    
    private String searchType;
    private String searchKeyword;
    private String imgBrdSts;
    private String imgBrdTy;
    private String imgBrdCtgry;
    private String startDtFrom;
    private String startDtTo;
    private String endDtFrom;
    private String endDtTo;
    private Integer pageNo;
    private Integer pageSize;
    private String sortBy;
    private String sortDirection;
} 