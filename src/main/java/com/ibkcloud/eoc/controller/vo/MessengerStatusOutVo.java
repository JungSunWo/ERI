package com.ibkcloud.eoc.controller.vo;

import com.ibkcloud.eoc.cmm.dto.IbkCldEocDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class MessengerStatusOutVo extends IbkCldEocDto {
    
    /**
     * 서비스 상태
     */
    private String status;
    
    /**
     * 타임스탬프
     */
    private Long timestamp;
} 