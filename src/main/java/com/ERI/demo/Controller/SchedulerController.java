package com.ERI.demo.Controller;

import com.ERI.demo.service.DynamicSchedulerService;
import com.ERI.demo.service.EmpEncryptSchedulerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 스케줄링 배치 수동 실행 API Controller
 */
@RestController
@RequestMapping("/api/scheduler")
public class SchedulerController {

    @Autowired
    private EmpEncryptSchedulerService empEncryptSchedulerService;

    @Autowired
    private DynamicSchedulerService dynamicSchedulerService;

    /**
     * 암호화 데이터베이스 연결 테스트
     */
    @PostMapping("/test-db-connection")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> testDatabaseConnection() {
        Map<String, Object> response = new HashMap<>();
        
        try {
            // 데이터베이스 연결 테스트
            empEncryptSchedulerService.testDatabaseConnection();
            
            response.put("success", true);
            response.put("message", "암호화 데이터베이스 연결 테스트 완료");
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
     * eri_db의 tb_emp_lst 테이블 연결 테스트
     */
    @PostMapping("/test-emp-lst-connection")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> testEmpLstTableConnection() {
        Map<String, Object> response = new HashMap<>();
        
        try {
            // TB_EMP_LST 테이블 연결 테스트
            empEncryptSchedulerService.testEmpLstTableConnection();
            
            response.put("success", true);
            response.put("message", "eri_db TB_EMP_LST 테이블 연결 테스트 완료");
            response.put("timestamp", System.currentTimeMillis());
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "TB_EMP_LST 테이블 연결 테스트 실패: " + e.getMessage());
            response.put("error", e.getClass().getSimpleName());
            response.put("timestamp", System.currentTimeMillis());
            
            return ResponseEntity.internalServerError().body(response);
        }
    }

    /**
     * 직원 정보 SHA256 해시 배치 수동 실행
     */
    @PostMapping("/manual-emp-encrypt")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> manualEmpEncryptBatch() {
        Map<String, Object> response = new HashMap<>();
        
        try {
            long startTime = System.currentTimeMillis();
            
            // 배치 실행
            empEncryptSchedulerService.manualEmpEncryptBatch();
            
            long endTime = System.currentTimeMillis();
            long processingTime = endTime - startTime;
            
            response.put("success", true);
            response.put("message", "직원 정보 SHA256 해시 배치 실행 완료");
            response.put("processingTime", processingTime + "ms");
            response.put("timestamp", System.currentTimeMillis());
            response.put("details", "empInfo.txt 파일을 읽어서 직원 정보를 SHA256 해시하여 TB_EMP_ENCRYPT 테이블에 저장했습니다.");
            
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
     * 스케줄링 상태 확인
     */
    @PostMapping("/status")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getSchedulerStatus() {
        Map<String, Object> response = new HashMap<>();
        
        try {
            Map<String, Object> status = new HashMap<>();
            
            // 동적 스케줄 설정 조회
            Map<String, String> scheduleConfig = dynamicSchedulerService.getScheduleConfig();
            status.put("dynamicScheduleConfig", scheduleConfig);
            
            // 스케줄 설정 유효성 검증 결과
            Map<String, Boolean> validationResults = dynamicSchedulerService.validateAllScheduleConfig();
            status.put("validationResults", validationResults);
            
            // 현재 시간
            status.put("currentTime", java.time.LocalDateTime.now().toString());
            
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
     * 동적 스케줄 설정 재로드
     */
    @PostMapping("/reload-config")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> reloadScheduleConfig() {
        Map<String, Object> response = new HashMap<>();
        
        try {
            // 스케줄 설정 재로드
            dynamicSchedulerService.reloadScheduleConfig();
            
            // 재로드된 설정 조회
            Map<String, String> scheduleConfig = dynamicSchedulerService.getScheduleConfig();
            Map<String, Boolean> validationResults = dynamicSchedulerService.validateAllScheduleConfig();
            
            response.put("success", true);
            response.put("message", "스케줄 설정 재로드 완료");
            response.put("scheduleConfig", scheduleConfig);
            response.put("validationResults", validationResults);
            response.put("timestamp", System.currentTimeMillis());
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "스케줄 설정 재로드 실패: " + e.getMessage());
            response.put("error", e.getClass().getSimpleName());
            response.put("timestamp", System.currentTimeMillis());
            
            return ResponseEntity.internalServerError().body(response);
        }
    }

    /**
     * 특정 스케줄 메소드 수동 실행
     */
    @PostMapping("/execute-method")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> executeScheduledMethod(String methodName) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            long startTime = System.currentTimeMillis();
            
            // 스케줄된 메소드 실행
            dynamicSchedulerService.executeScheduledMethod(methodName);
            
            long endTime = System.currentTimeMillis();
            long processingTime = endTime - startTime;
            
            response.put("success", true);
            response.put("message", "스케줄 메소드 실행 완료: " + methodName);
            response.put("methodName", methodName);
            response.put("processingTime", processingTime + "ms");
            response.put("timestamp", System.currentTimeMillis());
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "스케줄 메소드 실행 실패: " + e.getMessage());
            response.put("methodName", methodName);
            response.put("error", e.getClass().getSimpleName());
            response.put("timestamp", System.currentTimeMillis());
            
            return ResponseEntity.internalServerError().body(response);
        }
    }
} 