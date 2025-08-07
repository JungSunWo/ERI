package com.ibkcloud.eoc.dao.dto;

import com.ibkcloud.eoc.cmm.dto.IbkCldEocDto;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * @파일명 : AdminLstDto
 * @논리명 : 관리자 목록 DTO
 * @작성자 : 시스템
 * @설명   : 관리자 정보를 위한 DTO 클래스
 */
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class AdminLstDto extends IbkCldEocDto {
    
    /**
     * 관리자 ID
     */
    private String adminId;
    
    /**
     * 관리자명
     */
    private String adminNm;
    
    /**
     * 관리자 권한코드
     */
    private String adminAuthCd;
    
    /**
     * 관리자 권한명
     */
    private String adminAuthNm;
    
    /**
     * 활성화 여부
     */
    private String actvYn;
    
    /**
     * 생성일시
     */
    private LocalDateTime cretTs;
    
    /**
     * 수정일시
     */
    private LocalDateTime mdfcTs;
} 