package com.ibkcloud.eoc.controller.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * @파일명 : LoginInVo
 * @논리명 : 로그인 입력 VO
 * @작성자 : 시스템
 * @설명   : 로그인 요청을 위한 VO 클래스
 */
@Getter
@Setter
@NoArgsConstructor
public class LoginInVo {
    
    /**
     * 사용자 ID
     */
    @NotBlank(message = "사용자 ID는 필수입니다.")
    @Size(max = 50, message = "사용자 ID는 50자 이하여야 합니다.")
    private String userId;
    
    /**
     * 비밀번호
     */
    @NotBlank(message = "비밀번호는 필수입니다.")
    @Size(max = 100, message = "비밀번호는 100자 이하여야 합니다.")
    private String password;
} 