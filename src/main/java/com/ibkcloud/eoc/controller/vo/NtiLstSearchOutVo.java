package com.ibkcloud.eoc.controller.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.List;

/**
 * 공지사항 검색 출력 VO
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NtiLstSearchOutVo {
    
    private boolean success;
    private String message;
    private String errorCode;
    
    private List<NtiLstOutVo> data;
    private Integer count;
    private Integer pageNo;
    private Integer pageSize;
    
    // 검색 조건들
    private String title;
    private String content;
    private String status;
    private String noticeType;
    private String importance;
    private String startDtFrom;
    private String startDtTo;
    private String endDtFrom;
    private String endDtTo;
} 