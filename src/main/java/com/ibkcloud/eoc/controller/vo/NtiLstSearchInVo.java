package com.ibkcloud.eoc.controller.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * 공지사항 검색 입력 VO
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NtiLstSearchInVo {
    
    private String title;
    private String content;
    private String status;
    private String noticeType;
    private String importance;
    private String startDtFrom;
    private String startDtTo;
    private String endDtFrom;
    private String endDtTo;
    private Integer pageNo;
    private Integer pageSize;
} 