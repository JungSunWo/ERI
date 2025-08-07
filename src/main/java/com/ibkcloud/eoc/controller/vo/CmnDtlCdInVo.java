package com.ibkcloud.eoc.controller.vo;

import com.ibkcloud.eoc.cmm.dto.IbkCldEocDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
public class CmnDtlCdInVo extends IbkCldEocDto {
    
    // 기본 정보
    @NotBlank(message = "그룹 코드는 필수입니다.")
    @Size(max = 20, message = "그룹 코드는 20자를 초과할 수 없습니다.")
    private String grpCd;                // 그룹 코드
    
    @NotBlank(message = "상세 코드는 필수입니다.")
    @Size(max = 20, message = "상세 코드는 20자를 초과할 수 없습니다.")
    private String dtlCd;                // 상세 코드
    
    @NotBlank(message = "상세 코드명은 필수입니다.")
    @Size(max = 100, message = "상세 코드명은 100자를 초과할 수 없습니다.")
    private String dtlCdNm;              // 상세 코드명
    
    @Size(max = 200, message = "상세 코드 설명은 200자를 초과할 수 없습니다.")
    private String dtlCdDesc;            // 상세 코드 설명
    
    @NotBlank(message = "사용여부는 필수입니다.")
    @Size(max = 1, message = "사용여부는 1자를 초과할 수 없습니다.")
    private String useYn;                // 사용여부 (Y/N)
    
    @Size(max = 10, message = "정렬순서는 10자를 초과할 수 없습니다.")
    private String sortOrder;             // 정렬순서
    
    // 작성자 정보
    private String regEmpId;             // 등록직원ID
    private String updEmpId;             // 수정직원ID
    private String regEmpNm;             // 등록직원명
    private String updEmpNm;             // 수정직원명
    
    // 삭제 정보
    private String delYn;                // 삭제여부
    private LocalDateTime delDate;       // 삭제일시
    
    // 등록/수정 정보
    private LocalDateTime regDate;       // 등록일시
    private LocalDateTime updDate;       // 수정일시
    
    // 검색 조건
    private String keyword;              // 검색 키워드
    private String searchField;          // 검색 필드
    private String sortBy;               // 정렬 기준
    private String sortDirection;        // 정렬 방향
}
