package com.ibkcloud.eoc.controller.vo;

import com.ibkcloud.eoc.cmm.dto.IbkCldEocDto;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class EmpRightsBoardSearchInVo extends IbkCldEocDto {
    
    @Size(max = 20, message = "검색 타입은 20자를 초과할 수 없습니다.")
    private String searchType;           // 검색 타입 (title/content)
    
    @Size(max = 100, message = "검색 키워드는 100자를 초과할 수 없습니다.")
    private String searchKeyword;        // 검색 키워드
    
    @Size(max = 10, message = "카테고리 코드는 10자를 초과할 수 없습니다.")
    private String categoryCd;           // 카테고리 코드
    
    @Size(max = 10, message = "상태코드는 10자를 초과할 수 없습니다.")
    private String stsCd;                // 상태코드
    
    @Size(max = 1, message = "공지여부는 1자를 초과할 수 없습니다.")
    private String noticeYn;             // 공지여부
    
    @Size(max = 1, message = "익명여부는 1자를 초과할 수 없습니다.")
    private String anonymousYn;          // 익명여부
    
    @Size(max = 10, message = "시작일은 10자를 초과할 수 없습니다.")
    private String startDate;            // 시작일
    
    @Size(max = 10, message = "종료일은 10자를 초과할 수 없습니다.")
    private String endDate;              // 종료일
    
    @Size(max = 50, message = "정렬 기준은 50자를 초과할 수 없습니다.")
    private String sortBy;               // 정렬 기준
    
    @Size(max = 10, message = "정렬 방향은 10자를 초과할 수 없습니다.")
    private String sortDirection;        // 정렬 방향
    
    private Integer pageNo;              // 페이지 번호
    private Integer pageSize;            // 페이지 크기
}
