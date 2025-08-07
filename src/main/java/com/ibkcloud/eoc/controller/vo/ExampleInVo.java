package com.ibkcloud.eoc.controller.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * @파일명 : ExampleInVo
 * @논리명 : 예제 입력 VO
 * @작성자 : 시스템
 * @설명   : 예제 데이터 입력을 위한 VO 클래스
 */
@Getter
@Setter
@NoArgsConstructor
public class ExampleInVo {
    
    /**
     * 예제 ID
     */
    private String id;
    
    /**
     * 예제 이름
     */
    @NotBlank(message = "이름은 필수입니다.")
    @Size(max = 100, message = "이름은 100자 이하여야 합니다.")
    private String name;
    
    /**
     * 예제 설명
     */
    @Size(max = 1000, message = "설명은 1000자 이하여야 합니다.")
    private String description;
} 