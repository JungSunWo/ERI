package com.ibkcloud.eoc.controller.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 전문가 상담 신청 검색 출력 VO
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ExpertConsultationAppSearchOutVo extends com.ibkcloud.eoc.cmm.dto.IbkCldEocDto {

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
     * 전문가 상담 신청 목록
     */
    private List<ExpertConsultationAppOutVo> data;

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
} 