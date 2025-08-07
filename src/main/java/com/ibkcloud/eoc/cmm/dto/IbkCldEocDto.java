package com.ibkcloud.eoc.cmm.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * @파일명 : IbkCldEocDto
 * @논리명 : IBK 클라우드 EOC 공통 DTO
 * @작성자 : 시스템
 * @설명   : 데이터베이스의 공통속성을 제어하기 위한 부모 클래스
 */
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class IbkCldEocDto {
    
    /**
     * 시스템 최종 변경 ID
     */
    private String sysLsmdId;
    
    /**
     * 시스템 최종 변경일시
     */
    private LocalDateTime sysLsmdTs;
    
    /**
     * 등록 직원 ID
     */
    private String regEmpId;
    
    /**
     * 등록 일시
     */
    private LocalDateTime regTs;
    
    /**
     * 수정 직원 ID
     */
    private String updEmpId;
    
    /**
     * 수정 일시
     */
    private LocalDateTime updTs;
} 