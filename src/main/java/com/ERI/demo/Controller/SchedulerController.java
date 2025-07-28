package com.ERI.demo.Controller;

import com.ERI.demo.service.EmpLstService;
import com.ERI.demo.service.SchedulerConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.List;

/**
 * 스케줄링 배치 수동 실행 API Controller
 */
@RestController
@RequestMapping("/api/scheduler")
public class SchedulerController {

    @Autowired
    private EmpLstService empLstService;

    @Autowired
    private SchedulerConfigService schedulerConfigService;

    /**
     * empInfo.txt 파일 배치 적재 수동 실행
     */
    @PostMapping("/manual-emp-info-batch")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> manualEmpInfoBatch() {
        Map<String, Object> response = new HashMap<>();
        
        try {
            long startTime = System.currentTimeMillis();
            
            // 배치 실행
            empLstService.manualBatchLoadEmpInfo();
            
            long endTime = System.currentTimeMillis();
            long processingTime = endTime - startTime;
            
            // 처리된 직원 수 조회
            int totalEmployees = empLstService.getEmployeeCount();
            
            response.put("success", true);
            response.put("message", "empInfo.txt 파일 배치 적재 실행 완료");
            response.put("processingTime", processingTime + "ms");
            response.put("timestamp", System.currentTimeMillis());
            response.put("totalEmployees", totalEmployees);
            response.put("details", "empInfo.txt 파일을 읽어서 TB_EMP_LST 테이블에 직원 정보를 적재했습니다. ERI_EMP_ID는 E00000001 형식으로 자동 생성됩니다. TB_EMP_REF 동기화도 자동으로 실행되었습니다.");
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "배치 실행 실패: " + e.getMessage());
            response.put("error", e.getClass().getSimpleName());
            response.put("timestamp", System.currentTimeMillis());
            response.put("details", "자세한 오류 정보는 서버 로그를 확인해주세요.");
            
            return ResponseEntity.internalServerError().body(response);
        }
    }

    /**
     * TB_EMP_LST와 TB_EMP_REF 동기화 수동 실행
     */
    @PostMapping("/manual-emp-ref-sync")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> manualEmpRefSync() {
        Map<String, Object> response = new HashMap<>();
        
        try {
            long startTime = System.currentTimeMillis();
            
            // 동기화 실행
            empLstService.syncEmpRefFromEmpLst();
            
            long endTime = System.currentTimeMillis();
            long processingTime = endTime - startTime;
            
            // 처리된 직원 수 조회
            int totalEmployees = empLstService.getEmployeeCount();
            
            response.put("success", true);
            response.put("message", "TB_EMP_LST와 TB_EMP_REF 동기화 실행 완료");
            response.put("processingTime", processingTime + "ms");
            response.put("timestamp", System.currentTimeMillis());
            response.put("totalEmployees", totalEmployees);
            response.put("details", "TB_EMP_LST의 데이터를 TB_EMP_REF로 동기화했습니다. 새로운 직원은 삽입, 기존 직원은 업데이트, TB_EMP_LST에 없는 직원은 삭제됩니다.");
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "동기화 실행 실패: " + e.getMessage());
            response.put("error", e.getClass().getSimpleName());
            response.put("timestamp", System.currentTimeMillis());
            response.put("details", "자세한 오류 정보는 서버 로그를 확인해주세요.");
            
            return ResponseEntity.internalServerError().body(response);
        }
    }

    /**
     * 직원 데이터베이스 연결 테스트
     */
    @PostMapping("/test-db-connection")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> testDatabaseConnection() {
        Map<String, Object> response = new HashMap<>();
        
        try {
            // 간단한 데이터베이스 연결 테스트
            // TB_EMP_LST 테이블의 레코드 수를 조회하여 연결 상태 확인
            int recordCount = empLstService.getEmployeeCount();
            
            response.put("success", true);
            response.put("message", "직원 데이터베이스 연결 테스트 성공");
            response.put("recordCount", recordCount);
            response.put("timestamp", System.currentTimeMillis());
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "데이터베이스 연결 테스트 실패: " + e.getMessage());
            response.put("error", e.getClass().getSimpleName());
            response.put("timestamp", System.currentTimeMillis());
            
            return ResponseEntity.internalServerError().body(response);
        }
    }

    /**
     * 스케줄링 상태 확인
     */
    @PostMapping("/status")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getSchedulerStatus() {
        Map<String, Object> response = new HashMap<>();
        
        try {
            Map<String, Object> status = new HashMap<>();
            
            // 현재 시간
            status.put("currentTime", java.time.LocalDateTime.now().toString());
            
            // 직원 데이터베이스 연결 상태
            boolean dbConnected = empLstService.testEmployeeDbConnection();
            status.put("employeeDbConnected", dbConnected);
            
            // 전체 직원 수
            int totalEmployees = empLstService.getEmployeeCount();
            status.put("totalEmployees", totalEmployees);
            
            // empInfo.txt 파일 존재 여부
            String filePath = "src/main/resources/templates/empInfo.txt";
            boolean fileExists = java.nio.file.Files.exists(java.nio.file.Paths.get(filePath));
            status.put("empInfoFileExists", fileExists);
            status.put("empInfoFilePath", filePath);
            
            response.put("success", true);
            response.put("message", "스케줄링 상태 조회 완료");
            response.put("status", status);
            response.put("timestamp", System.currentTimeMillis());
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "상태 조회 실패: " + e.getMessage());
            response.put("error", e.getClass().getSimpleName());
            response.put("timestamp", System.currentTimeMillis());
            
            return ResponseEntity.internalServerError().body(response);
        }
    }

    /**
     * 직원 목록 조회 (배치 처리 결과 확인용)
     */
    @GetMapping("/employee-list")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getEmployeeList() {
        Map<String, Object> response = new HashMap<>();
        
        try {
            List<com.ERI.demo.vo.employee.EmpLstVO> employees = empLstService.getAllEmployees();
            
            response.put("success", true);
            response.put("message", "직원 목록 조회 완료");
            response.put("employees", employees);
            response.put("totalCount", employees.size());
            response.put("timestamp", System.currentTimeMillis());
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "직원 목록 조회 실패: " + e.getMessage());
            response.put("error", e.getClass().getSimpleName());
            response.put("timestamp", System.currentTimeMillis());
            
            return ResponseEntity.internalServerError().body(response);
        }
    }

    /**
     * 스케줄 설정 파일 다시 로드
     */
    @PostMapping("/reload-config")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> reloadScheduleConfig() {
        Map<String, Object> response = new HashMap<>();
        
        try {
            schedulerConfigService.loadAndScheduleTasks();
            
            response.put("success", true);
            response.put("message", "스케줄 설정 파일 다시 로드 완료");
            response.put("timestamp", System.currentTimeMillis());
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "스케줄 설정 로드 실패: " + e.getMessage());
            response.put("error", e.getClass().getSimpleName());
            response.put("timestamp", System.currentTimeMillis());
            
            return ResponseEntity.internalServerError().body(response);
        }
    }

    /**
     * 특정 스케줄 취소
     */
    @PostMapping("/cancel-schedule")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> cancelSchedule(@RequestParam String methodName) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            boolean cancelled = schedulerConfigService.cancelSchedule(methodName);
            
            response.put("success", cancelled);
            response.put("message", cancelled ? "스케줄 취소 완료" : "스케줄을 찾을 수 없습니다");
            response.put("methodName", methodName);
            response.put("timestamp", System.currentTimeMillis());
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "스케줄 취소 실패: " + e.getMessage());
            response.put("error", e.getClass().getSimpleName());
            response.put("timestamp", System.currentTimeMillis());
            
            return ResponseEntity.internalServerError().body(response);
        }
    }

    /**
     * 모든 스케줄 취소
     */
    @PostMapping("/cancel-all-schedules")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> cancelAllSchedules() {
        Map<String, Object> response = new HashMap<>();
        
        try {
            schedulerConfigService.clearAllSchedules();
            
            response.put("success", true);
            response.put("message", "모든 스케줄 취소 완료");
            response.put("timestamp", System.currentTimeMillis());
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "스케줄 취소 실패: " + e.getMessage());
            response.put("error", e.getClass().getSimpleName());
            response.put("timestamp", System.currentTimeMillis());
            
            return ResponseEntity.internalServerError().body(response);
        }
    }

    /**
     * TB_EMP_REF 동기화 배치 수동 실행
     */
    @PostMapping("/manual-emp-ref-sync-batch")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> manualEmpRefSyncBatch() {
        Map<String, Object> response = new HashMap<>();
        
        try {
            long startTime = System.currentTimeMillis();
            
            // 동기화 배치 실행
            schedulerConfigService.executeEmpRefSyncBatch();
            
            long endTime = System.currentTimeMillis();
            long processingTime = endTime - startTime;
            
            response.put("success", true);
            response.put("message", "TB_EMP_REF 동기화 배치 실행 완료");
            response.put("processingTime", processingTime + "ms");
            response.put("timestamp", System.currentTimeMillis());
            response.put("details", "TB_EMP_LST의 데이터를 TB_EMP_REF로 동기화하는 배치가 실행되었습니다.");
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "동기화 배치 실행 실패: " + e.getMessage());
            response.put("error", e.getClass().getSimpleName());
            response.put("timestamp", System.currentTimeMillis());
            response.put("details", "자세한 오류 정보는 서버 로그를 확인해주세요.");
            
            return ResponseEntity.internalServerError().body(response);
        }
    }

    /**
     * 현재 스케줄 상태 조회
     */
    @GetMapping("/schedule-status")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getScheduleStatus() {
        Map<String, Object> response = new HashMap<>();
        
        try {
            Map<String, Object> scheduleStatus = schedulerConfigService.getScheduleStatus();
            
            response.put("success", true);
            response.put("message", "스케줄 상태 조회 완료");
            response.put("scheduleStatus", scheduleStatus);
            response.put("timestamp", System.currentTimeMillis());
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "스케줄 상태 조회 실패: " + e.getMessage());
            response.put("error", e.getClass().getSimpleName());
            response.put("timestamp", System.currentTimeMillis());
            
            return ResponseEntity.internalServerError().body(response);
        }
    }
} 