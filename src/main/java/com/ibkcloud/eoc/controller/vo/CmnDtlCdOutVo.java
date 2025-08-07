package com.ibkcloud.eoc.controller.vo;

import com.ibkcloud.eoc.cmm.dto.IbkCldEocDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class CmnDtlCdOutVo extends IbkCldEocDto {
    
    // 기본 정보
    private String grpCd;                // 그룹 코드
    private String dtlCd;                // 상세 코드
    private String dtlCdNm;              // 상세 코드명
    private String dtlCdDesc;            // 상세 코드 설명
    private String useYn;                // 사용여부 (Y/N)
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
    
    // 목록 조회용
    private List<CmnDtlCdOutVo> data;   // 상세 코드 목록
    private Integer count;               // 총 개수
    private Integer pageNo;              // 현재 페이지
    private Integer pageSize;            // 페이지 크기
    private Integer ttalPageNbi;         // 총 페이지 수
}
