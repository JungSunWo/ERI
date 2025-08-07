package com.ibkcloud.eoc.controller;

import com.ibkcloud.eoc.cmm.dto.PageRequest;
import com.ibkcloud.eoc.cmm.exception.BizException;
import com.ibkcloud.eoc.controller.vo.*;
import com.ibkcloud.eoc.service.SystemLogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * 시스템 로그 관리 컨트롤러
 */
@Slf4j
@RestController
@RequestMapping("/api/system-log")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@RequiredArgsConstructor
public class SystemLogController {

    private final SystemLogService systemLogService;

    /**
     * 시스템 로그 목록 조회 (페이징)
     */
    @GetMapping
    public ResponseEntity<SystemLogOutVo> inqSystemLogList(
            @Valid PageRequest pageRequest,
            @RequestParam(required = false) String logLevel,
            @RequestParam(required = false) String logType,
            @RequestParam(required = false) String empId,
            @RequestParam(required = false) String errorCode,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate,
            @RequestParam(required = false) String searchKeyword,
            @RequestParam(defaultValue = "logSeq") String sortKey,
            @RequestParam(defaultValue = "desc") String sortOrder) {
        
        log.info("시스템 로그 목록 조회 시작 - logLevel: {}, logType: {}, empId: {}", logLevel, logType, empId);
        
        try {
            // String 날짜를 LocalDate로 변환
            LocalDate startLocalDate = null;
            LocalDate endLocalDate = null;
            
            if (startDate != null && !startDate.trim().isEmpty()) {
                try {
                    startLocalDate = LocalDate.parse(startDate, DateTimeFormatter.ISO_LOCAL_DATE);
                } catch (Exception e) {
                    log.warn("Invalid startDate format: {}", startDate);
                }
            }
            
            if (endDate != null && !endDate.trim().isEmpty()) {
                try {
                    endLocalDate = LocalDate.parse(endDate, DateTimeFormatter.ISO_LOCAL_DATE);
                } catch (Exception e) {
                    log.warn("Invalid endDate format: {}", endDate);
                }
            }
            
            SystemLogInVo searchCondition = new SystemLogInVo();
            searchCondition.setLogLevel(logLevel);
            searchCondition.setLogType(logType);
            searchCondition.setEmpId(empId);
            searchCondition.setErrorCode(errorCode);
            searchCondition.setStartDate(startLocalDate);
            searchCondition.setEndDate(endLocalDate);
            searchCondition.setSearchKeyword(searchKeyword);
            searchCondition.setSortKey(sortKey);
            searchCondition.setSortOrder(sortOrder);
            
            SystemLogOutVo result = systemLogService.inqSystemLogList(pageRequest, searchCondition);
            
            result.setSuccess(true);
            result.setMessage("시스템 로그 목록을 조회했습니다.");
            result.setErrorCode("SUCCESS");
            
            log.info("시스템 로그 목록 조회 완료 - 총 {}건", result.getCount());
            return ResponseEntity.ok(result);
            
        } catch (Exception e) {
            log.error("시스템 로그 목록 조회 실패", e);
            throw new BizException("시스템 로그 목록 조회에 실패했습니다: " + e.getMessage());
        }
    }

    /**
     * 로그 레벨별 통계 조회
     */
    @GetMapping("/stats/level")
    public ResponseEntity<SystemLogOutVo> inqLogLevelStats() {
        log.info("로그 레벨별 통계 조회 시작");
        
        try {
            SystemLogOutVo result = systemLogService.inqLogLevelStats();
            
            result.setSuccess(true);
            result.setMessage("로그 레벨별 통계를 조회했습니다.");
            result.setErrorCode("SUCCESS");
            
            log.info("로그 레벨별 통계 조회 완료");
            return ResponseEntity.ok(result);
            
        } catch (Exception e) {
            log.error("로그 레벨별 통계 조회 실패", e);
            throw new BizException("로그 레벨별 통계 조회에 실패했습니다: " + e.getMessage());
        }
    }

    /**
     * 로그 타입별 통계 조회
     */
    @GetMapping("/stats/type")
    public ResponseEntity<SystemLogOutVo> inqLogTypeStats() {
        log.info("로그 타입별 통계 조회 시작");
        
        try {
            SystemLogOutVo result = systemLogService.inqLogTypeStats();
            
            result.setSuccess(true);
            result.setMessage("로그 타입별 통계를 조회했습니다.");
            result.setErrorCode("SUCCESS");
            
            log.info("로그 타입별 통계 조회 완료");
            return ResponseEntity.ok(result);
            
        } catch (Exception e) {
            log.error("로그 타입별 통계 조회 실패", e);
            throw new BizException("로그 타입별 통계 조회에 실패했습니다: " + e.getMessage());
        }
    }

    /**
     * 에러 로그 통계 조회
     */
    @GetMapping("/stats/error")
    public ResponseEntity<SystemLogOutVo> inqErrorLogStats(
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate) {
        
        log.info("에러 로그 통계 조회 시작 - startDate: {}, endDate: {}", startDate, endDate);
        
        try {
            // String 날짜를 LocalDate로 변환
            LocalDate startLocalDate = null;
            LocalDate endLocalDate = null;
            
            if (startDate != null && !startDate.trim().isEmpty()) {
                try {
                    startLocalDate = LocalDate.parse(startDate, DateTimeFormatter.ISO_LOCAL_DATE);
                } catch (Exception e) {
                    log.warn("Invalid startDate format: {}", startDate);
                }
            }
            
            if (endDate != null && !endDate.trim().isEmpty()) {
                try {
                    endLocalDate = LocalDate.parse(endDate, DateTimeFormatter.ISO_LOCAL_DATE);
                } catch (Exception e) {
                    log.warn("Invalid endDate format: {}", endDate);
                }
            }
            
            SystemLogInVo searchCondition = new SystemLogInVo();
            searchCondition.setStartDate(startLocalDate);
            searchCondition.setEndDate(endLocalDate);
            
            SystemLogOutVo result = systemLogService.inqErrorLogStats(searchCondition);
            
            result.setSuccess(true);
            result.setMessage("에러 로그 통계를 조회했습니다.");
            result.setErrorCode("SUCCESS");
            
            log.info("에러 로그 통계 조회 완료");
            return ResponseEntity.ok(result);
            
        } catch (Exception e) {
            log.error("에러 로그 통계 조회 실패", e);
            throw new BizException("에러 로그 통계 조회에 실패했습니다: " + e.getMessage());
        }
    }

    /**
     * 기간별 로그 통계 조회
     */
    @GetMapping("/stats/period")
    public ResponseEntity<SystemLogOutVo> inqLogStatsByPeriod(
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate) {
        
        log.info("기간별 로그 통계 조회 시작 - startDate: {}, endDate: {}", startDate, endDate);
        
        try {
            // String 날짜를 LocalDate로 변환
            LocalDate startLocalDate = null;
            LocalDate endLocalDate = null;
            
            if (startDate != null && !startDate.trim().isEmpty()) {
                try {
                    startLocalDate = LocalDate.parse(startDate, DateTimeFormatter.ISO_LOCAL_DATE);
                } catch (Exception e) {
                    log.warn("Invalid startDate format: {}", startDate);
                }
            }
            
            if (endDate != null && !endDate.trim().isEmpty()) {
                try {
                    endLocalDate = LocalDate.parse(endDate, DateTimeFormatter.ISO_LOCAL_DATE);
                } catch (Exception e) {
                    log.warn("Invalid endDate format: {}", endDate);
                }
            }
            
            SystemLogInVo searchCondition = new SystemLogInVo();
            searchCondition.setStartDate(startLocalDate);
            searchCondition.setEndDate(endLocalDate);
            
            SystemLogOutVo result = systemLogService.inqLogStatsByPeriod(searchCondition);
            
            result.setSuccess(true);
            result.setMessage("기간별 로그 통계를 조회했습니다.");
            result.setErrorCode("SUCCESS");
            
            log.info("기간별 로그 통계 조회 완료");
            return ResponseEntity.ok(result);
            
        } catch (Exception e) {
            log.error("기간별 로그 통계 조회 실패", e);
            throw new BizException("기간별 로그 통계 조회에 실패했습니다: " + e.getMessage());
        }
    }

    /**
     * 시스템 로그 삭제
     */
    @DeleteMapping("/{logSeq}")
    public ResponseEntity<SystemLogOutVo> delSystemLog(@PathVariable Long logSeq) {
        log.info("시스템 로그 삭제 시작 - logSeq: {}", logSeq);
        
        try {
            boolean result = systemLogService.delSystemLog(logSeq);
            
            if (!result) {
                throw new BizException("시스템 로그 삭제에 실패했습니다.");
            }
            
            SystemLogOutVo outVo = new SystemLogOutVo();
            outVo.setSuccess(true);
            outVo.setMessage("시스템 로그가 성공적으로 삭제되었습니다.");
            outVo.setErrorCode("SUCCESS");
            
            log.info("시스템 로그 삭제 완료 - logSeq: {}", logSeq);
            return ResponseEntity.ok(outVo);
            
        } catch (Exception e) {
            log.error("시스템 로그 삭제 실패 - logSeq: {}", logSeq, e);
            throw new BizException("시스템 로그 삭제에 실패했습니다: " + e.getMessage());
        }
    }

    /**
     * 오래된 로그 삭제
     */
    @DeleteMapping("/old")
    public ResponseEntity<SystemLogOutVo> delOldLogs(@RequestParam(defaultValue = "90") int days) {
        log.info("오래된 로그 삭제 시작 - days: {}", days);
        
        try {
            SystemLogOutVo result = systemLogService.delOldLogs(days);
            
            result.setSuccess(true);
            result.setMessage(days + "일 이전의 로그가 성공적으로 삭제되었습니다.");
            result.setErrorCode("SUCCESS");
            
            log.info("오래된 로그 삭제 완료 - days: {}, deletedCount: {}", days, result.getDeletedCount());
            return ResponseEntity.ok(result);
            
        } catch (Exception e) {
            log.error("오래된 로그 삭제 실패 - days: {}", days, e);
            throw new BizException("오래된 로그 삭제에 실패했습니다: " + e.getMessage());
        }
    }

    /**
     * 로그 상세 조회
     */
    @GetMapping("/{logSeq}")
    public ResponseEntity<SystemLogOutVo> inqSystemLogBySeq(@PathVariable Long logSeq) {
        log.info("로그 상세 조회 시작 - logSeq: {}", logSeq);
        
        try {
            SystemLogOutVo result = systemLogService.inqSystemLogBySeq(logSeq);
            
            if (result == null) {
                throw new BizException("시스템 로그를 찾을 수 없습니다.");
            }
            
            result.setSuccess(true);
            result.setMessage("시스템 로그 정보를 조회했습니다.");
            result.setErrorCode("SUCCESS");
            
            log.info("로그 상세 조회 완료 - logSeq: {}", logSeq);
            return ResponseEntity.ok(result);
            
        } catch (Exception e) {
            log.error("로그 상세 조회 실패 - logSeq: {}", logSeq, e);
            throw new BizException("시스템 로그 조회에 실패했습니다: " + e.getMessage());
        }
    }
} 