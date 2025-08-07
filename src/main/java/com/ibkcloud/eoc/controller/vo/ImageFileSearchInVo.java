package com.ibkcloud.eoc.controller.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 이미지 파일 검색 입력 VO
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ImageFileSearchInVo extends com.ibkcloud.eoc.cmm.dto.IbkCldEocDto {

    /**
     * 검색 타입
     */
    private String searchType;

    /**
     * 검색 키워드
     */
    private String searchKeyword;

    /**
     * 이미지 게시판 시퀀스
     */
    private Long imgBrdSeq;

    /**
     * 정렬 필드
     */
    private String sortBy;

    /**
     * 정렬 방향
     */
    private String sortDirection;

    /**
     * 페이지 번호
     */
    private Integer pageNo;

    /**
     * 페이지 크기
     */
    private Integer pageSize;
} 