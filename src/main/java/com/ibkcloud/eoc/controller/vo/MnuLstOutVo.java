package com.ibkcloud.eoc.controller.vo;

import com.ibkcloud.eoc.cmm.dto.IbkCldEocDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class MnuLstOutVo extends IbkCldEocDto {
    
    // 기본 정보
    private String mnuCd;                      // 메뉴 코드
    private String mnuNm;                      // 메뉴명
    private String mnuUrl;                     // 메뉴 URL
    private String pMnuCd;                     // 상위 메뉴 코드
    private Integer mnuLvl;                    // 메뉴 레벨 (1: 최상위, 2: 하위, 3: 세부)
    private Integer mnuOrd;                    // 메뉴 순서
    private String mnuUseYn;                   // 메뉴 사용 여부 (Y: 사용, N: 미사용)
    private String mnuAuthType;                // 메뉴 권한 타입 (USER, COUNSELOR, ADMIN)
    private String mnuDesc;                    // 메뉴 설명
    private String mnuIcon;                    // 메뉴 아이콘
    private String stsCd;                      // 상태 코드 (STS001: 정상, STS002: 삭제)
    
    // 등록/수정 정보
    private LocalDateTime regDate;             // 등록일시
    private LocalDateTime updDate;             // 수정일시
    
    // 목록 조회용
    private List<MnuLstOutVo> data;           // 메뉴 목록
    private Integer count;                     // 총 개수
    private Integer pageNo;                    // 현재 페이지
    private Integer pageSize;                  // 페이지 크기
    private Integer totalPages;                // 총 페이지 수
    
    // 계층 구조용
    private List<MnuLstOutVo> subMenus;       // 하위 메뉴 목록
    private String pMnuNm;                     // 상위 메뉴명
    private String mnuLvlText;                 // 메뉴 레벨 텍스트
    private String mnuUseYnText;               // 사용 여부 텍스트
    private String mnuAuthTypeText;            // 권한 타입 텍스트
    
    // 추가 정보
    private String empId;                      // 직원 ID
    private String userAuthType;               // 사용자 권한 타입
    private Boolean isAccessible;              // 접근 가능 여부
} 