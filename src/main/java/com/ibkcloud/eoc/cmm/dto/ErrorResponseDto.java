package com.ibkcloud.eoc.cmm.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

/**
 * @파일명 : ErrorResponseDto
 * @논리명 : 오류 응답 DTO
 * @작성자 : 시스템
 * @설명   : 오류 발생 시 클라이언트에게 전달하는 응답 DTO
 */
@Getter
@Setter
@NoArgsConstructor
public class ErrorResponseDto {
    
    private String errorCode;
    private String errorMessage;
    private String timestamp;
    
    public ErrorResponseDto(String errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.timestamp = java.time.LocalDateTime.now().toString();
    }
} 