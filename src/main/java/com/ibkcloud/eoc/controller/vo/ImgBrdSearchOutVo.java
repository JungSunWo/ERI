package com.ibkcloud.eoc.controller.vo;

import com.ibkcloud.eoc.cmm.dto.IbkCldEocDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class ImgBrdSearchOutVo extends IbkCldEocDto {
    
    private List<ImgBrdOutVo> data;             // 이미지 목록
    private Integer count;                       // 총 개수
    private Integer pageNo;                      // 현재 페이지
    private Integer pageSize;                    // 페이지 크기
    private Integer ttalPageNbi;                 // 총 페이지 수
    
    // 검색 조건
    private Long imgBrdSeq;                      // 이미지 게시판 시퀀스
    private String selEmpId;                     // 선택한 직원 ID
    private String searchKeyword;                // 검색 키워드
    private String sortBy;                       // 정렬 기준
    private String sortDirection;                // 정렬 방향
} 