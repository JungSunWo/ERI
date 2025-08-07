package com.ibkcloud.eoc.dao.dto;

import com.ibkcloud.eoc.cmm.dto.IbkCldEocDto;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @파일명 : EmpLstDto
 * @논리명 : 직원 목록 DTO
 * @작성자 : 시스템
 * @설명   : 직원 정보 DB의 TB_EMP_LST 테이블을 위한 DTO 클래스
 */
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class EmpLstDto extends IbkCldEocDto {
    
    /**
     * ERI 직원 ID
     */
    private String eriEmpId;
    
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
    private String blngBrcd;
    
    /**
     * 부서명
     */
    private String blngBrnm;
    
    /**
     * 직급코드
     */
    private String jbttCd;
    
    /**
     * 직급명
     */
    private String jbttNm;
    
    /**
     * 직책코드
     */
    private String jbclCd;
    
    /**
     * 직책명
     */
    private String jbclNm;
    
    /**
     * 이메일주소
     */
    private String ead;
    
    /**
     * 직원휴대폰번호
     */
    private String empCpn;
    
    /**
     * 재직여부
     */
    private String hlofYn;
    
    /**
     * 입행일자
     */
    private LocalDate etbnDt;
    
    /**
     * 퇴직일자
     */
    private LocalDate tzgnDt;
    
    /**
     * 생성일시
     */
    private LocalDateTime cretTs;
    
    /**
     * 수정일시
     */
    private LocalDateTime mdfcTs;
} 