package com.ibkcloud.eoc.controller.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 전문가 상담 신청 검색 입력 VO
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ExpertConsultationAppSearchInVo extends com.ibkcloud.eoc.cmm.dto.IbkCldEocDto {

    /**
     * 검색 타입
     */
    private String searchType;

    /**
     * 검색 키워드
     */
    private String searchKeyword;

    /**
     * 신청자 ID
     */
    private String appEmpId;

    /**
     * 상담 분류 코드
     */
    private String cnslClsfCd;

    /**
     * 상담 상태 코드
     */
    private String cnslStsCd;

    /**
     * 승인 상태 코드
     */
    private String aprvStsCd;

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