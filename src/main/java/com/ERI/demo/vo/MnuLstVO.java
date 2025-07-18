package com.ERI.demo.vo;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class MnuLstVO {
    private String mnuCd;           // 메뉴 코드 (PK)
    private String mnuNm;           // 메뉴명
    private String mnuUrl;          // 메뉴 URL
    private String mnuDesc;         // 메뉴 설명
    private Integer mnuOrd;         // 메뉴 순서
    private Integer mnuLvl;         // 메뉴 레벨 (1: 대메뉴, 2: 서브메뉴)
    private String pMnuCd;          // 상위 메뉴 코드 (2depth일 때만)
    private String mnuUseYn;        // 사용여부
    private String mnuAdminYn;      // 관리자전용여부
    private String rgstEmpId;       // 등록자 ID
    private String updtEmpId;       // 수정자 ID
    private String delYn;           // 삭제 여부
    private LocalDateTime delDate;  // 삭제 일시
    private String regEmpId;        // 등록자 ID
    private LocalDateTime regDate;  // 등록 일시
    private String updEmpId;        // 수정자 ID
    private LocalDateTime updDate;  // 수정 일시
    
    // 추가 필드 (화면 표시용)
    private String pMnuNm;          // 상위 메뉴명
    private List<MnuLstVO> subMenus; // 하위 메뉴 목록
    private String mnuLvlText;      // 메뉴 레벨 텍스트
    private String mnuUseYnText;    // 사용여부 텍스트
    private String mnuAdminYnText;  // 관리자전용여부 텍스트
    private String regDateText;     // 등록일 텍스트
    private String updDateText;     // 수정일 텍스트
} 