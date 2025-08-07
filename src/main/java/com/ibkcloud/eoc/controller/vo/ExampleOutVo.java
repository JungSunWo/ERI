package com.ibkcloud.eoc.controller.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @파일명 : ExampleOutVo
 * @논리명 : 예제 출력 VO
 * @작성자 : 시스템
 * @설명   : 예제 데이터 출력을 위한 VO 클래스
 */
@Getter
@Setter
@NoArgsConstructor
public class ExampleOutVo {
    
    /**
     * 예제 ID
     */
    private String id;
    
    /**
     * 예제 이름
     */
    private String name;
    
    /**
     * 예제 설명
     */
    private String description;
    
    /**
     * 생성 일시
     */
    private LocalDateTime createTime;
    
    /**
     * 수정 일시
     */
    private LocalDateTime updateTime;
} 