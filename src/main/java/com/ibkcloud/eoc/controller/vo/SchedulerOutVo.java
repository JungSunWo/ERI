package com.ibkcloud.eoc.controller.vo;

import com.ibkcloud.eoc.cmm.dto.IbkCldEocDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
@EqualsAndHashCode(callSuper = true)
public class SchedulerOutVo extends IbkCldEocDto {
    
    // 기본 정보
    private Long schedulerSeq;                // 스케줄러 일련번호
    private String methodName;                 // 메서드명
    private String scheduleName;               // 스케줄명
    private String scheduleDesc;               // 스케줄 설명
    private String cronExpression;             // 크론 표현식
    private String stsCd;                      // 상태 코드 (STS001: 정상, STS002: 중지)
    private String executionStatus;            // 실행 상태 (RUNNING, COMPLETED, FAILED)
    private LocalDateTime lastExecutionTime;   // 마지막 실행 시간
    private LocalDateTime nextExecutionTime;   // 다음 실행 시간
    private Long processingTime;               // 처리 시간 (ms)
    private Integer totalEmployees;            // 총 직원 수
    private Integer processedCount;            // 처리된 개수
    private String details;                    // 상세 정보
    private String errorMessage;               // 에러 메시지
    
    // 등록/수정 정보
    private LocalDateTime regDate;             // 등록일시
    private LocalDateTime updDate;             // 수정일시
    
    // 목록 조회용
    private List<SchedulerOutVo> data;        // 스케줄러 목록
    private Integer count;                     // 총 개수
    
    // 스케줄 상태 정보
    private Map<String, Object> scheduleStatus; // 스케줄 상태 정보
    private Map<String, Object> schedulerStatus; // 스케줄러 상태 정보
    private List<Map<String, Object>> employeeList; // 직원 목록
    
    // 추가 정보
    private Long timestamp;                    // 타임스탬프
    private String errorType;                  // 에러 타입
    private Boolean isRunning;                 // 실행 중 여부
    private Boolean isConnected;               // DB 연결 상태
} 