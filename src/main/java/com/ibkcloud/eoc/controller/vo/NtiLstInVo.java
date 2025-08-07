package com.ibkcloud.eoc.controller.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 공지사항 입력 VO
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NtiLstInVo {
    
    private Long seq;
    
    @NotBlank(message = "제목은 필수입니다.")
    @Size(max = 200, message = "제목은 200자 이하여야 합니다.")
    private String title;
    
    @NotBlank(message = "내용은 필수입니다.")
    @Size(max = 4000, message = "내용은 4000자 이하여야 합니다.")
    private String content;
    
    @Size(max = 20, message = "상태코드는 20자 이하여야 합니다.")
    private String status;
    
    @Size(max = 100, message = "상태명은 100자 이하여야 합니다.")
    private String statusNm;
    
    @Size(max = 20, message = "공지유형은 20자 이하여야 합니다.")
    private String noticeType;
    
    @Size(max = 100, message = "공지유형명은 100자 이하여야 합니다.")
    private String noticeTypeNm;
    
    @Size(max = 20, message = "중요도는 20자 이하여야 합니다.")
    private String importance;
    
    @Size(max = 100, message = "중요도명은 100자 이하여야 합니다.")
    private String importanceNm;
    
    @Size(max = 20, message = "시작일자는 20자 이하여야 합니다.")
    private String startDt;
    
    @Size(max = 20, message = "종료일자는 20자 이하여야 합니다.")
    private String endDt;
    
    @Size(max = 20, message = "조회수는 20자 이하여야 합니다.")
    private String viewCnt;
    
    @Size(max = 1000, message = "비고는 1000자 이하여야 합니다.")
    private String remark;
    
    // 시스템 필드들
    private String regEmpId;
    private String updEmpId;
} 