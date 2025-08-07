package com.ibkcloud.eoc.controller.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 암호화 테스트 출력 VO
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class EncryptionTestOutVo extends com.ibkcloud.eoc.cmm.dto.IbkCldEocDto {

    /**
     * 성공 여부
     */
    private Boolean success;

    /**
     * 메시지
     */
    private String message;

    /**
     * 에러 코드
     */
    private String errorCode;

    /**
     * 원본 텍스트
     */
    private String original;

    /**
     * 암호화된 텍스트
     */
    private String encrypted;

    /**
     * 복호화된 텍스트
     */
    private String decrypted;

    /**
     * 일치 여부
     */
    private Boolean isMatch;

    /**
     * 암호화 여부
     */
    private Boolean isEncrypted;

    /**
     * 직원 목록
     */
    private List<EmpLstOutVo> employees;

    /**
     * 직원 정보
     */
    private EmpLstOutVo employee;

    /**
     * 직원 수
     */
    private Integer count;

    /**
     * 에러 메시지
     */
    private String error;
} 