package com.ibkcloud.eoc.dao.dto;

import com.ibkcloud.eoc.cmm.dto.IbkCldEocDto;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * @파일명 : CounselorLstDto
 * @논리명 : 상담사 목록 DTO
 * @작성자 : 시스템
 * @설명   : 상담사 정보를 위한 DTO 클래스
 */
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class CounselorLstDto extends IbkCldEocDto {
    
    /**
     * 상담사 ID
     */
    private String counselorId;
    
    /**
     * 상담사명
     */
    private String counselorNm;
    
    /**
     * 상담사 자격코드
     */
    private String counselorQualCd;
    
    /**
     * 상담사 자격명
     */
    private String counselorQualNm;
    
    /**
     * 상담사 상태코드
     */
    private String counselorStsCd;
    
    /**
     * 상담사 상태명
     */
    private String counselorStsNm;
    
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