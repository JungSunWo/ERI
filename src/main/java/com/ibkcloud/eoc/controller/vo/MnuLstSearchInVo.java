package com.ibkcloud.eoc.controller.vo;

import com.ibkcloud.eoc.cmm.dto.IbkCldEocDto;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class MnuLstSearchInVo extends IbkCldEocDto {
    
    @Size(max = 100, message = "검색 키워드는 100자를 초과할 수 없습니다.")
    private String searchKeyword;   // 검색 키워드
    
    private Integer mnuLvl;         // 메뉴 레벨
    private String mnuUseYn;        // 사용여부
    private String mnuAuthType;     // 메뉴권한구분
    private String pMnuCd;          // 상위 메뉴 코드
    private String empId;           // 사용자 ID
    
    @Size(max = 50, message = "정렬 기준은 50자를 초과할 수 없습니다.")
    private String sortBy;          // 정렬 기준
    
    @Size(max = 10, message = "정렬 방향은 10자를 초과할 수 없습니다.")
    private String sortDirection;   // 정렬 방향
    
    private Integer pageNo;         // 페이지 번호
    private Integer pageSize;       // 페이지 크기
} 