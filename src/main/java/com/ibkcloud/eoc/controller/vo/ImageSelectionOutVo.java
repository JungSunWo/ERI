package com.ibkcloud.eoc.controller.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 이미지 선택 출력 VO
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ImageSelectionOutVo extends com.ibkcloud.eoc.cmm.dto.IbkCldEocDto {

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
     * 이미지 선택 시퀀스
     */
    private Long imgSelSeq;

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
     * 선택한 직원명
     */
    private String selEmpNm;

    /**
     * 선택 일시
     */
    private LocalDateTime selDt;

    /**
     * 이미지 게시판 제목
     */
    private String imgBrdTitl;

    /**
     * 이미지 파일명
     */
    private String imgFileNm;

    /**
     * 이미지 파일 경로
     */
    private String imgFilePath;

    /**
     * 선택 여부
     */
    private Boolean isSelected;

    /**
     * 선택 개수
     */
    private Integer count;

    /**
     * 선택 가능 여부
     */
    private Boolean canSelect;

    /**
     * 최대 선택 개수
     */
    private Integer maxSelCnt;

    /**
     * 전체 선택 개수
     */
    private Integer totalCount;

    /**
     * 선택 통계
     */
    private Integer selectionCount;

    /**
     * 삭제된 개수
     */
    private Integer deleted;
} 