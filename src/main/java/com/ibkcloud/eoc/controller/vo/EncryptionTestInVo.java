package com.ibkcloud.eoc.controller.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * 암호화 테스트 입력 VO
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class EncryptionTestInVo extends com.ibkcloud.eoc.cmm.dto.IbkCldEocDto {

    /**
     * 원본 텍스트
     */
    @NotBlank(message = "텍스트는 필수입니다.")
    @Size(max = 1000, message = "텍스트는 1000자를 초과할 수 없습니다.")
    private String text;

    /**
     * 원본 이메일
     */
    @Size(max = 100, message = "이메일은 100자를 초과할 수 없습니다.")
    private String email;

    /**
     * 원본 전화번호
     */
    @Size(max = 20, message = "전화번호는 20자를 초과할 수 없습니다.")
    private String phone;

    /**
     * 암호화된 텍스트
     */
    private String encryptedText;

    /**
     * 암호화된 이메일
     */
    private String encryptedEmail;

    /**
     * 암호화된 전화번호
     */
    private String encryptedPhone;
} 