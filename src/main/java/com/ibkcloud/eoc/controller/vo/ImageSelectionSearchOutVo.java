package com.ibkcloud.eoc.controller.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 이미지 선택 검색 출력 VO
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ImageSelectionSearchOutVo extends com.ibkcloud.eoc.cmm.dto.IbkCldEocDto {

    /**
     * 성공 여부
     */
    private Boolean success;

    /**
     * 메시지
     */
    private String message;

    /**
     * 에러 코드
     */
    private String errorCode;

    /**
     * 이미지 선택 목록
     */
    private List<ImageSelectionOutVo> data;

    /**
     * 전체 개수
     */
    private Integer count;

    /**
     * 페이지 번호
     */
    private Integer pageNo;

    /**
     * 페이지 크기
     */
    private Integer pageSize;

    /**
     * 전체 페이지 수
     */
    private Integer ttalPageNbi;

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
     * 이미지 파일 시퀀스
     */
    private Long imgFileSeq;

    /**
     * 선택한 직원 ID
     */
    private String selEmpId;

    /**
     * 정렬 필드
     */
    private String sortBy;

    /**
     * 정렬 방향
     */
    private String sortDirection;
} 