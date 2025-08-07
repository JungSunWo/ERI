package com.ibkcloud.eoc.controller.vo;

import com.ibkcloud.eoc.cmm.dto.IbkCldEocDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class MnuLstSearchOutVo extends IbkCldEocDto {
    
    private List<MnuLstOutVo> data; // 메뉴 목록
    private Integer count;           // 총 개수
    private Integer pageNo;          // 현재 페이지
    private Integer pageSize;        // 페이지 크기
    private Integer ttalPageNbi;     // 총 페이지 수
    
    // 검색 조건
    private String searchKeyword;    // 검색 키워드
    private Integer mnuLvl;          // 메뉴 레벨
    private String mnuUseYn;         // 사용여부
    private String mnuAuthType;      // 메뉴권한구분
    private String pMnuCd;           // 상위 메뉴 코드
    private String empId;            // 사용자 ID
    private String sortBy;           // 정렬 기준
    private String sortDirection;    // 정렬 방향
} 