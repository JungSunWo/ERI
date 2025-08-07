package com.ibkcloud.eoc.controller.vo;

import com.ibkcloud.eoc.cmm.dto.IbkCldEocDto;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
public class SchedulerInVo extends IbkCldEocDto {
    
    // 기본 정보
    private Long schedulerSeq;                // 스케줄러 일련번호
    
    @Size(max = 100, message = "메서드명은 100자를 초과할 수 없습니다.")
    private String methodName;                 // 메서드명
    
    @Size(max = 200, message = "스케줄명은 200자를 초과할 수 없습니다.")
    private String scheduleName;               // 스케줄명
    
    @Size(max = 500, message = "스케줄 설명은 500자를 초과할 수 없습니다.")
    private String scheduleDesc;               // 스케줄 설명
    
    @Size(max = 50, message = "크론 표현식은 50자를 초과할 수 없습니다.")
    private String cronExpression;             // 크론 표현식
    
    @Size(max = 10, message = "상태 코드는 10자를 초과할 수 없습니다.")
    private String stsCd;                      // 상태 코드 (STS001: 정상, STS002: 중지)
    
    @Size(max = 20, message = "실행 상태는 20자를 초과할 수 없습니다.")
    private String executionStatus;            // 실행 상태 (RUNNING, COMPLETED, FAILED)
    
    private LocalDateTime lastExecutionTime;   // 마지막 실행 시간
    private LocalDateTime nextExecutionTime;   // 다음 실행 시간
    
    private Long processingTime;               // 처리 시간 (ms)
    private Integer totalEmployees;            // 총 직원 수
    private Integer processedCount;            // 처리된 개수
    
    @Size(max = 1000, message = "상세 정보는 1000자를 초과할 수 없습니다.")
    private String details;                    // 상세 정보
    
    @Size(max = 500, message = "에러 메시지는 500자를 초과할 수 없습니다.")
    private String errorMessage;               // 에러 메시지
    
    // 등록/수정 정보
    private LocalDateTime regDate;             // 등록일시
    private LocalDateTime updDate;             // 수정일시
    
    // 추가 정보
    private Long timestamp;                    // 타임스탬프
    private String errorType;                  // 에러 타입
} 