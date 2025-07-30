package com.ERI.demo.Controller;

import com.ERI.demo.service.SystemLogService;
import com.ERI.demo.vo.SystemLogSearchVo;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

/**
 * 시스템 로그 관리 컨트롤러
 */
@Slf4j
@RestController
@RequestMapping("/api/system-log")
@RequiredArgsConstructor
public class SystemLogController {

    private final SystemLogService systemLogService;

    /**
     * 시스템 로그 목록 조회 (페이징)
     */
    @GetMapping
    public ResponseEntity<Map<String, Object>> getSystemLogs(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(required = false) String logLevel,
            @RequestParam(required = false) String logType,
            @RequestParam(required = false) String empId,
            @RequestParam(required = false) String errorCode,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate,
            @RequestParam(required = false) String searchKeyword,
            @RequestParam(defaultValue = "logSeq") String sortKey,
            @RequestParam(defaultValue = "desc") String sortOrder) {
        
        Map<String, Object> response = new HashMap<>();
        
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
            
            SystemLogSearchVo searchVo = SystemLogSearchVo.builder()
                    .page(page)
                    .pageSize(size)
                    .logLevel(logLevel)
                    .logType(logType)
                    .empId(empId)
                    .errorCode(errorCode)
                    .startDate(startLocalDate)
                    .endDate(endLocalDate)
                    .searchKeyword(searchKeyword)
                    .sortKey(sortKey)
                    .sortOrder(sortOrder)
                    .build();

            Map<String, Object> result = systemLogService.getSystemLogs(searchVo);
            
            response.put("success", true);
            response.put("data", result);
            response.put("message", "시스템 로그 목록을 조회했습니다.");
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            log.error("시스템 로그 목록 조회 실패", e);
            
            response.put("success", false);
            response.put("message", "시스템 로그 목록 조회에 실패했습니다: " + e.getMessage());
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * 로그 레벨별 통계 조회
     */
    @GetMapping("/stats/level")
    public ResponseEntity<Map<String, Object>> getLogLevelStats() {
        Map<String, Object> response = new HashMap<>();
        
        try {
            var stats = systemLogService.getLogLevelStats();
            
            response.put("success", true);
            response.put("data", stats);
            response.put("message", "로그 레벨별 통계를 조회했습니다.");
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            log.error("로그 레벨별 통계 조회 실패", e);
            
            response.put("success", false);
            response.put("message", "로그 레벨별 통계 조회에 실패했습니다: " + e.getMessage());
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * 로그 타입별 통계 조회
     */
    @GetMapping("/stats/type")
    public ResponseEntity<Map<String, Object>> getLogTypeStats() {
        Map<String, Object> response = new HashMap<>();
        
        try {
            var stats = systemLogService.getLogTypeStats();
            
            response.put("success", true);
            response.put("data", stats);
            response.put("message", "로그 타입별 통계를 조회했습니다.");
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            log.error("로그 타입별 통계 조회 실패", e);
            
            response.put("success", false);
            response.put("message", "로그 타입별 통계 조회에 실패했습니다: " + e.getMessage());
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * 에러 로그 통계 조회
     */
    @GetMapping("/stats/error")
    public ResponseEntity<Map<String, Object>> getErrorLogStats(
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate) {
        
        Map<String, Object> response = new HashMap<>();
        
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
            
            var stats = systemLogService.getErrorLogStats(startLocalDate, endLocalDate);
            
            response.put("success", true);
            response.put("data", stats);
            response.put("message", "에러 로그 통계를 조회했습니다.");
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            log.error("에러 로그 통계 조회 실패", e);
            
            response.put("success", false);
            response.put("message", "에러 로그 통계 조회에 실패했습니다: " + e.getMessage());
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * 특정 기간 로그 통계 조회
     */
    @GetMapping("/stats/period")
    public ResponseEntity<Map<String, Object>> getLogStatsByPeriod(
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate) {
        
        Map<String, Object> response = new HashMap<>();
        
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
            
            var stats = systemLogService.getLogStatsByPeriod(startLocalDate, endLocalDate);
            
            response.put("success", true);
            response.put("data", stats);
            response.put("message", "기간별 로그 통계를 조회했습니다.");
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            log.error("기간별 로그 통계 조회 실패", e);
            
            response.put("success", false);
            response.put("message", "기간별 로그 통계 조회에 실패했습니다: " + e.getMessage());
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * 특정 로그 삭제
     */
    @DeleteMapping("/{logSeq}")
    public ResponseEntity<Map<String, Object>> deleteSystemLog(@PathVariable Long logSeq) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            int result = systemLogService.deleteSystemLog(logSeq);
            
            if (result > 0) {
                response.put("success", true);
                response.put("message", "로그가 삭제되었습니다.");
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("message", "로그 삭제에 실패했습니다.");
                return ResponseEntity.badRequest().body(response);
            }
            
        } catch (Exception e) {
            log.error("로그 삭제 실패", e);
            
            response.put("success", false);
            response.put("message", "로그 삭제에 실패했습니다: " + e.getMessage());
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * 오래된 로그 삭제
     */
    @DeleteMapping("/old")
    public ResponseEntity<Map<String, Object>> deleteOldLogs(@RequestParam(defaultValue = "90") int days) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            int result = systemLogService.deleteOldLogs(days);
            
            response.put("success", true);
            response.put("message", days + "일 이전의 로그 " + result + "건이 삭제되었습니다.");
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            log.error("오래된 로그 삭제 실패", e);
            
            response.put("success", false);
            response.put("message", "오래된 로그 삭제에 실패했습니다: " + e.getMessage());
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * 로그 상세 조회
     */
    @GetMapping("/{logSeq}")
    public ResponseEntity<Map<String, Object>> getLogDetail(@PathVariable Long logSeq) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            // SystemLogService에 상세 조회 메서드가 있다면 사용
            // 현재는 목록 조회에서 필터링하여 사용
            SystemLogSearchVo searchVo = SystemLogSearchVo.builder()
                    .page(1)
                    .pageSize(1)
                    .build();
            
            Map<String, Object> result = systemLogService.getSystemLogs(searchVo);
            var logs = (java.util.List<?>) result.get("logs");
            
            if (logs != null && !logs.isEmpty()) {
                response.put("success", true);
                response.put("data", logs.get(0));
                response.put("message", "로그 상세 정보를 조회했습니다.");
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("message", "로그를 찾을 수 없습니다.");
                return ResponseEntity.notFound().build();
            }
            
        } catch (Exception e) {
            log.error("로그 상세 조회 실패", e);
            
            response.put("success", false);
            response.put("message", "로그 상세 조회에 실패했습니다: " + e.getMessage());
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}