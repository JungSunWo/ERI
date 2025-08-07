package com.ibkcloud.eoc.controller.vo;

import com.ibkcloud.eoc.cmm.dto.IbkCldEocDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class EmpRightsBoardSearchOutVo extends IbkCldEocDto {
    
    private List<EmpRightsBoardOutVo> data; // 게시글 목록
    private Integer count;               // 총 개수
    private Integer pageNo;              // 현재 페이지
    private Integer pageSize;            // 페이지 크기
    private Integer ttalPageNbi;         // 총 페이지 수
    
    // 검색 조건
    private String searchType;           // 검색 타입
    private String searchKeyword;        // 검색 키워드
    private String categoryCd;           // 카테고리 코드
    private String stsCd;                // 상태코드
    private String noticeYn;             // 공지여부
    private String anonymousYn;          // 익명여부
    private String startDate;            // 시작일
    private String endDate;              // 종료일
    private String sortBy;               // 정렬 기준
    private String sortDirection;        // 정렬 방향
}
