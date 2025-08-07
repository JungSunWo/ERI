package com.ibkcloud.eoc.controller.vo;

import com.ibkcloud.eoc.cmm.dto.IbkCldEocDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class AuthLstOutVo extends IbkCldEocDto {
    
    // 기본 정보
    private String authCd;                // 권한 코드
    private String authNm;                // 권한명
    private String authDesc;              // 권한 설명
    private Integer authLvl;              // 권한 레벨 (1~10)
    private String useYn;                 // 사용여부 (Y/N)
    private String sortOrder;             // 정렬순서
    
    // 작성자 정보
    private String regEmpId;              // 등록직원ID
    private String updEmpId;              // 수정직원ID
    private String regEmpNm;              // 등록직원명
    private String updEmpNm;              // 수정직원명
    
    // 삭제 정보
    private String delYn;                 // 삭제여부
    private LocalDateTime delDate;        // 삭제일시
    
    // 등록/수정 정보
    private LocalDateTime regDate;        // 등록일시
    private LocalDateTime updDate;        // 수정일시
    
    // 목록 조회용
    private List<AuthLstOutVo> data;     // 권한 목록
    private Integer count;                // 총 개수
    private Integer pageNo;               // 현재 페이지
    private Integer pageSize;             // 페이지 크기
    private Integer ttalPageNbi;          // 총 페이지 수
    
    // 추가 정보
    private Boolean exists;               // 권한 코드 존재 여부
}
