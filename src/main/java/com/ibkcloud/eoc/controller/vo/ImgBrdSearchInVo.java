package com.ibkcloud.eoc.controller.vo;

import com.ibkcloud.eoc.cmm.dto.IbkCldEocDto;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ImgBrdSearchInVo extends IbkCldEocDto {
    
    @NotNull(message = "이미지 게시판 시퀀스는 필수입니다.")
    private Long imgBrdSeq;                      // 이미지 게시판 시퀀스
    
    @Size(max = 20, message = "선택한 직원 ID는 20자를 초과할 수 없습니다.")
    private String selEmpId;                     // 선택한 직원 ID
    
    @Size(max = 100, message = "검색 키워드는 100자를 초과할 수 없습니다.")
    private String searchKeyword;                // 검색 키워드
    
    @Size(max = 50, message = "정렬 기준은 50자를 초과할 수 없습니다.")
    private String sortBy;                       // 정렬 기준
    
    @Size(max = 10, message = "정렬 방향은 10자를 초과할 수 없습니다.")
    private String sortDirection;                // 정렬 방향
    
    private Integer pageNo;                      // 페이지 번호
    private Integer pageSize;                    // 페이지 크기
} 