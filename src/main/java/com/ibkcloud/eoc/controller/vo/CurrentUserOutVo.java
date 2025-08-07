package com.ibkcloud.eoc.controller.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @파일명 : CurrentUserOutVo
 * @논리명 : 현재 사용자 출력 VO
 * @작성자 : 시스템
 * @설명   : 현재 로그인한 사용자 정보를 위한 VO 클래스
 */
@Getter
@Setter
@NoArgsConstructor
public class CurrentUserOutVo {
    
    /**
     * 사용자 ID
     */
    private String userId;
    
    /**
     * 사용자명
     */
    private String userName;
    
    /**
     * 부서코드
     */
    private String deptCode;
    
    /**
     * 부서명
     */
    private String deptName;
    
    /**
     * 직급코드
     */
    private String positionCode;
    
    /**
     * 직급명
     */
    private String positionName;
    
    /**
     * 로그인 일시
     */
    private LocalDateTime loginTime;
    
    /**
     * 세션 ID
     */
    private String sessionId;
} 