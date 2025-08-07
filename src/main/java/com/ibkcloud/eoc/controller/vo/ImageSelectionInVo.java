package com.ibkcloud.eoc.controller.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * 이미지 선택 입력 VO
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ImageSelectionInVo extends com.ibkcloud.eoc.cmm.dto.IbkCldEocDto {

    /**
     * 이미지 선택 시퀀스
     */
    private Long imgSelSeq;

    /**
     * 이미지 게시판 시퀀스
     */
    @NotNull(message = "이미지 게시판 시퀀스는 필수입니다.")
    private Long imgBrdSeq;

    /**
     * 이미지 파일 시퀀스
     */
    @NotNull(message = "이미지 파일 시퀀스는 필수입니다.")
    private Long imgFileSeq;

    /**
     * 선택한 직원 ID
     */
    @NotNull(message = "선택한 직원 ID는 필수입니다.")
    @Size(max = 20, message = "직원 ID는 20자를 초과할 수 없습니다.")
    private String selEmpId;

    /**
     * 검색 타입
     */
    private String searchType;

    /**
     * 검색 키워드
     */
    private String searchKeyword;

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

    /**
     * 최대 선택 개수
     */
    private Integer maxSelCnt;
} 