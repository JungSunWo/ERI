package com.ibkcloud.eoc.controller.vo;

import com.ibkcloud.eoc.cmm.dto.IbkCldEocDto;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
public class MnuLstInVo extends IbkCldEocDto {
    
    // 기본 정보
    @Size(max = 20, message = "메뉴 코드는 20자를 초과할 수 없습니다.")
    private String mnuCd;                      // 메뉴 코드
    
    @Size(max = 100, message = "메뉴명은 100자를 초과할 수 없습니다.")
    private String mnuNm;                      // 메뉴명
    
    @Size(max = 200, message = "메뉴 URL은 200자를 초과할 수 없습니다.")
    private String mnuUrl;                     // 메뉴 URL
    
    @Size(max = 20, message = "상위 메뉴 코드는 20자를 초과할 수 없습니다.")
    private String pMnuCd;                     // 상위 메뉴 코드
    
    private Integer mnuLvl;                    // 메뉴 레벨 (1: 최상위, 2: 하위, 3: 세부)
    private Integer mnuOrd;                    // 메뉴 순서
    
    @Size(max = 1, message = "메뉴 사용 여부는 1자를 초과할 수 없습니다.")
    private String mnuUseYn;                   // 메뉴 사용 여부 (Y: 사용, N: 미사용)
    
    @Size(max = 20, message = "메뉴 권한 타입은 20자를 초과할 수 없습니다.")
    private String mnuAuthType;                // 메뉴 권한 타입 (USER, COUNSELOR, ADMIN)
    
    @Size(max = 500, message = "메뉴 설명은 500자를 초과할 수 없습니다.")
    private String mnuDesc;                    // 메뉴 설명
    
    @Size(max = 50, message = "메뉴 아이콘은 50자를 초과할 수 없습니다.")
    private String mnuIcon;                    // 메뉴 아이콘
    
    @Size(max = 10, message = "상태 코드는 10자를 초과할 수 없습니다.")
    private String stsCd;                      // 상태 코드 (STS001: 정상, STS002: 삭제)
    
    // 등록/수정 정보
    private LocalDateTime regDate;             // 등록일시
    private LocalDateTime updDate;             // 수정일시
    
    // 검색 조건
    private String searchKeyword;              // 검색 키워드
    private String empId;                      // 직원 ID
    private String userAuthType;               // 사용자 권한 타입
    private Integer page;                      // 페이지 번호
    private Integer size;                      // 페이지 크기
} 