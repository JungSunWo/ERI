package com.ibkcloud.eoc.dao.dto;

import com.ibkcloud.eoc.cmm.dto.IbkCldEocDto;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * @파일명 : EmpRefDto
 * @논리명 : 직원 참조 DTO
 * @작성자 : 시스템
 * @설명   : 직원 참조 정보를 위한 DTO 클래스
 */
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class EmpRefDto extends IbkCldEocDto {
    
    /**
     * 직원 ID
     */
    private String empId;
    
    /**
     * 직원명
     */
    private String empNm;
    
    /**
     * 부서코드
     */
    private String deptCd;
    
    /**
     * 부서명
     */
    private String deptNm;
    
    /**
     * 직급코드
     */
    private String positionCd;
    
    /**
     * 직급명
     */
    private String positionNm;
    
    /**
     * 참조용 데이터
     */
    private String refData;
    
    /**
     * 생성일시
     */
    private LocalDateTime cretTs;
    
    /**
     * 수정일시
     */
    private LocalDateTime mdfcTs;
} 