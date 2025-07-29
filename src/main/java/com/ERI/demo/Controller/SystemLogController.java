package com.ERI.demo.Controller;

import com.ERI.demo.service.SystemLogService;
import com.ERI.demo.vo.SystemLogSearchVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 시스템 로그 컨트롤러
 */
@Slf4j
@RestController
@RequestMapping("/api/system-log")
@RequiredArgsConstructor
public class SystemLogController {
    
    private final SystemLogService systemLogService;
    
    /**
     * 시스템 로그 조회 (페이징)
     */
    @GetMapping("/list")
    public ResponseEntity<Map<String, Object>> getSystemLogs(
            @ModelAttribute SystemLogSearchVo searchVo,
            HttpServletRequest request) {
        try {
            Map<String, Object> result = systemLogService.getSystemLogs(searchVo);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            log.error("시스템 로그 조회 중 오류 발생: {}", e.getMessage(), e);
            return ResponseEntity.internalServerError()
                    .body(Map.of("error", "시스템 로그 조회 중 오류가 발생했습니다."));
        }
    }
    
    /**
     * 에러 로그 통계 조회
     */
    @GetMapping("/error-stats")
    public ResponseEntity<List<Map<String, Object>>> getErrorLogStats(
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate) {
        try {
            List<Map<String, Object>> stats = systemLogService.getErrorLogStats(startDate, endDate);
            return ResponseEntity.ok(stats);
        } catch (Exception e) {
            log.error("에러 로그 통계 조회 중 오류 발생: {}", e.getMessage(), e);
            return ResponseEntity.internalServerError().build();
        }
    }
    
    /**
     * 로그 레벨별 통계 조회
     */
    @GetMapping("/level-stats")
    public ResponseEntity<List<Map<String, Object>>> getLogLevelStats() {
        try {
            List<Map<String, Object>> stats = systemLogService.getLogLevelStats();
            return ResponseEntity.ok(stats);
        } catch (Exception e) {
            log.error("로그 레벨별 통계 조회 중 오류 발생: {}", e.getMessage(), e);
            return ResponseEntity.internalServerError().build();
        }
    }
    
    /**
     * 로그 타입별 통계 조회
     */
    @GetMapping("/type-stats")
    public ResponseEntity<List<Map<String, Object>>> getLogTypeStats() {
        try {
            List<Map<String, Object>> stats = systemLogService.getLogTypeStats();
            return ResponseEntity.ok(stats);
        } catch (Exception e) {
            log.error("로그 타입별 통계 조회 중 오류 발생: {}", e.getMessage(), e);
            return ResponseEntity.internalServerError().build();
        }
    }
    
    /**
     * 시스템 로그 통계 뷰 조회
     */
    @GetMapping("/stats-view")
    public ResponseEntity<List<Map<String, Object>>> getSystemLogStatsView() {
        try {
            List<Map<String, Object>> stats = systemLogService.getSystemLogStatsView();
            return ResponseEntity.ok(stats);
        } catch (Exception e) {
            log.error("시스템 로그 통계 뷰 조회 중 오류 발생: {}", e.getMessage(), e);
            return ResponseEntity.internalServerError().build();
        }
    }
    
    /**
     * 특정 기간 로그 통계 조회
     */
    @GetMapping("/period-stats")
    public ResponseEntity<List<Map<String, Object>>> getLogStatsByPeriod(
            @RequestParam String startDate,
            @RequestParam String endDate) {
        try {
            List<Map<String, Object>> stats = systemLogService.getLogStatsByPeriod(startDate, endDate);
            return ResponseEntity.ok(stats);
        } catch (Exception e) {
            log.error("특정 기간 로그 통계 조회 중 오류 발생: {}", e.getMessage(), e);
            return ResponseEntity.internalServerError().build();
        }
    }
    
    /**
     * 오래된 로그 삭제
     */
    @DeleteMapping("/clean-old")
    public ResponseEntity<Map<String, Object>> deleteOldLogs(
            @RequestParam(defaultValue = "90") int days) {
        try {
            int deletedCount = systemLogService.deleteOldLogs(days);
            return ResponseEntity.ok(Map.of(
                "message", "오래된 로그가 성공적으로 삭제되었습니다.",
                "deletedCount", deletedCount
            ));
        } catch (Exception e) {
            log.error("오래된 로그 삭제 중 오류 발생: {}", e.getMessage(), e);
            return ResponseEntity.internalServerError()
                    .body(Map.of("error", "오래된 로그 삭제 중 오류가 발생했습니다."));
        }
    }
    
    /**
     * 특정 로그 삭제
     */
    @DeleteMapping("/{logSeq}")
    public ResponseEntity<Map<String, Object>> deleteSystemLog(@PathVariable Long logSeq) {
        try {
            int deletedCount = systemLogService.deleteSystemLog(logSeq);
            if (deletedCount > 0) {
                return ResponseEntity.ok(Map.of(
                    "message", "로그가 성공적으로 삭제되었습니다.",
                    "deletedCount", deletedCount
                ));
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            log.error("로그 삭제 중 오류 발생: {}", e.getMessage(), e);
            return ResponseEntity.internalServerError()
                    .body(Map.of("error", "로그 삭제 중 오류가 발생했습니다."));
        }
    }
    
    /**
     * 시스템 로그 상태 확인
     */
    @GetMapping("/status")
    public ResponseEntity<Map<String, Object>> getSystemLogStatus() {
        try {
            // 최근 24시간 로그 통계
            List<Map<String, Object>> levelStats = systemLogService.getLogLevelStats();
            List<Map<String, Object>> typeStats = systemLogService.getLogTypeStats();
            
            return ResponseEntity.ok(Map.of(
                "status", "OK",
                "message", "시스템 로그 서비스가 정상적으로 동작하고 있습니다.",
                "levelStats", levelStats,
                "typeStats", typeStats
            ));
        } catch (Exception e) {
            log.error("시스템 로그 상태 확인 중 오류 발생: {}", e.getMessage(), e);
            return ResponseEntity.internalServerError()
                    .body(Map.of(
                        "status", "ERROR",
                        "message", "시스템 로그 서비스에 문제가 있습니다.",
                        "error", e.getMessage()
                    ));
        }
    }
}