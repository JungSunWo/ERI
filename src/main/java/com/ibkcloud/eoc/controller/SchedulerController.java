package com.ibkcloud.eoc.controller;

import com.ibkcloud.eoc.cmm.exception.BizException;
import com.ibkcloud.eoc.controller.vo.*;
import com.ibkcloud.eoc.service.EmpLstService;
import com.ibkcloud.eoc.service.SchedulerConfigService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 스케줄링 배치 수동 실행 API 컨트롤러
 */
@Slf4j
@RestController
@RequestMapping("/api/scheduler")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@RequiredArgsConstructor
public class SchedulerController {

    private final EmpLstService empLstService;
    private final SchedulerConfigService schedulerConfigService;

    /**
     * empInfo.txt 파일 배치 적재 수동 실행 (이메일, 전화번호 암호화)
     */
    @PostMapping("/manual-emp-info-batch")
    public ResponseEntity<SchedulerOutVo> rgsnManualEmpInfoBatch() {
        log.info("empInfo.txt 파일 배치 적재 수동 실행 시작");
        
        try {
            long startTime = System.currentTimeMillis();
            
            // 배치 실행 (이메일과 전화번호가 암호화되어 저장됨)
            empLstService.manualBatchLoadEmpInfo();
            
            long endTime = System.currentTimeMillis();
            long processingTime = endTime - startTime;
            
            // 처리된 직원 수 조회
            int totalEmployees = empLstService.getEmployeeCount();
            
            SchedulerOutVo result = new SchedulerOutVo();
            result.setProcessingTime(processingTime);
            result.setTotalEmployees(totalEmployees);
            result.setTimestamp(System.currentTimeMillis());
            result.setDetails("empInfo.txt 파일을 읽어서 TB_EMP_LST 테이블에 직원 정보를 적재했습니다. 이메일과 전화번호는 암호화되어 저장됩니다. ERI_EMP_ID는 E00000001 형식으로 자동 생성됩니다. TB_EMP_REF 동기화도 자동으로 실행되었습니다.");
            
            result.setSuccess(true);
            result.setMessage("empInfo.txt 파일 배치 적재 실행 완료 (이메일, 전화번호 암호화)");
            result.setErrorCode("SUCCESS");
            
            log.info("empInfo.txt 파일 배치 적재 수동 실행 완료 - 처리시간: {}ms, 총 직원수: {}", processingTime, totalEmployees);
            return ResponseEntity.ok(result);
            
        } catch (Exception e) {
            log.error("empInfo.txt 파일 배치 적재 수동 실행 실패", e);
            throw new BizException("배치 실행에 실패했습니다: " + e.getMessage());
        }
    }

    /**
     * TB_EMP_LST와 TB_EMP_REF 동기화 수동 실행
     */
    @PostMapping("/manual-emp-ref-sync")
    public ResponseEntity<SchedulerOutVo> rgsnManualEmpRefSync() {
        log.info("TB_EMP_LST와 TB_EMP_REF 동기화 수동 실행 시작");
        
        try {
            long startTime = System.currentTimeMillis();
            
            // 동기화 실행
            empLstService.syncEmpRefFromEmpLst();
            
            long endTime = System.currentTimeMillis();
            long processingTime = endTime - startTime;
            
            // 처리된 직원 수 조회
            int totalEmployees = empLstService.getEmployeeCount();
            
            SchedulerOutVo result = new SchedulerOutVo();
            result.setProcessingTime(processingTime);
            result.setTotalEmployees(totalEmployees);
            result.setTimestamp(System.currentTimeMillis());
            result.setDetails("TB_EMP_LST의 데이터를 TB_EMP_REF로 동기화했습니다. 새로운 직원은 삽입, 기존 직원은 업데이트, TB_EMP_LST에 없는 직원은 삭제됩니다.");
            
            result.setSuccess(true);
            result.setMessage("TB_EMP_LST와 TB_EMP_REF 동기화 실행 완료");
            result.setErrorCode("SUCCESS");
            
            log.info("TB_EMP_LST와 TB_EMP_REF 동기화 수동 실행 완료 - 처리시간: {}ms, 총 직원수: {}", processingTime, totalEmployees);
            return ResponseEntity.ok(result);
            
        } catch (Exception e) {
            log.error("TB_EMP_LST와 TB_EMP_REF 동기화 수동 실행 실패", e);
            throw new BizException("동기화 실행에 실패했습니다: " + e.getMessage());
        }
    }

    /**
     * 데이터베이스 연결 테스트
     */
    @PostMapping("/test-db-connection")
    public ResponseEntity<SchedulerOutVo> inqTestDbConnection() {
        log.info("데이터베이스 연결 테스트 시작");
        
        try {
            boolean isConnected = empLstService.testDatabaseConnection();
            
            SchedulerOutVo result = new SchedulerOutVo();
            result.setIsConnected(isConnected);
            result.setTimestamp(System.currentTimeMillis());
            result.setDetails(isConnected ? "데이터베이스 연결이 정상입니다." : "데이터베이스 연결에 실패했습니다.");
            
            result.setSuccess(true);
            result.setMessage(isConnected ? "데이터베이스 연결 테스트 성공" : "데이터베이스 연결 테스트 실패");
            result.setErrorCode("SUCCESS");
            
            log.info("데이터베이스 연결 테스트 완료 - 연결상태: {}", isConnected);
            return ResponseEntity.ok(result);
            
        } catch (Exception e) {
            log.error("데이터베이스 연결 테스트 실패", e);
            throw new BizException("데이터베이스 연결 테스트에 실패했습니다: " + e.getMessage());
        }
    }

    /**
     * 스케줄러 상태 조회
     */
    @PostMapping("/status")
    public ResponseEntity<SchedulerOutVo> inqSchedulerStatus() {
        log.info("스케줄러 상태 조회 시작");
        
        try {
            SchedulerOutVo result = schedulerConfigService.inqSchedulerStatus();
            
            result.setSuccess(true);
            result.setMessage("스케줄러 상태를 조회했습니다.");
            result.setErrorCode("SUCCESS");
            
            log.info("스케줄러 상태 조회 완료");
            return ResponseEntity.ok(result);
            
        } catch (Exception e) {
            log.error("스케줄러 상태 조회 실패", e);
            throw new BizException("스케줄러 상태 조회에 실패했습니다: " + e.getMessage());
        }
    }

    /**
     * 직원 목록 조회
     */
    @GetMapping("/employee-list")
    public ResponseEntity<SchedulerOutVo> inqEmployeeList() {
        log.info("직원 목록 조회 시작");
        
        try {
            SchedulerOutVo result = empLstService.inqEmployeeList();
            
            result.setSuccess(true);
            result.setMessage("직원 목록을 조회했습니다.");
            result.setErrorCode("SUCCESS");
            
            log.info("직원 목록 조회 완료 - 총 {}건", result.getCount());
            return ResponseEntity.ok(result);
            
        } catch (Exception e) {
            log.error("직원 목록 조회 실패", e);
            throw new BizException("직원 목록 조회에 실패했습니다: " + e.getMessage());
        }
    }

    /**
     * 스케줄 설정 재로드
     */
    @PostMapping("/reload-config")
    public ResponseEntity<SchedulerOutVo> mdfcReloadConfig() {
        log.info("스케줄 설정 재로드 시작");
        
        try {
            SchedulerOutVo result = schedulerConfigService.mdfcReloadConfig();
            
            result.setSuccess(true);
            result.setMessage("스케줄 설정이 재로드되었습니다.");
            result.setErrorCode("SUCCESS");
            
            log.info("스케줄 설정 재로드 완료");
            return ResponseEntity.ok(result);
            
        } catch (Exception e) {
            log.error("스케줄 설정 재로드 실패", e);
            throw new BizException("스케줄 설정 재로드에 실패했습니다: " + e.getMessage());
        }
    }

    /**
     * 특정 스케줄 취소
     */
    @PostMapping("/cancel-schedule")
    public ResponseEntity<SchedulerOutVo> delCancelSchedule(@RequestParam String methodName) {
        log.info("특정 스케줄 취소 시작 - methodName: {}", methodName);
        
        try {
            SchedulerOutVo result = schedulerConfigService.delCancelSchedule(methodName);
            
            result.setSuccess(true);
            result.setMessage("스케줄이 취소되었습니다.");
            result.setErrorCode("SUCCESS");
            
            log.info("특정 스케줄 취소 완료 - methodName: {}", methodName);
            return ResponseEntity.ok(result);
            
        } catch (Exception e) {
            log.error("특정 스케줄 취소 실패 - methodName: {}", methodName, e);
            throw new BizException("스케줄 취소에 실패했습니다: " + e.getMessage());
        }
    }

    /**
     * 모든 스케줄 취소
     */
    @PostMapping("/cancel-all-schedules")
    public ResponseEntity<SchedulerOutVo> delCancelAllSchedules() {
        log.info("모든 스케줄 취소 시작");
        
        try {
            SchedulerOutVo result = schedulerConfigService.delCancelAllSchedules();
            
            result.setSuccess(true);
            result.setMessage("모든 스케줄이 취소되었습니다.");
            result.setErrorCode("SUCCESS");
            
            log.info("모든 스케줄 취소 완료");
            return ResponseEntity.ok(result);
            
        } catch (Exception e) {
            log.error("모든 스케줄 취소 실패", e);
            throw new BizException("모든 스케줄 취소에 실패했습니다: " + e.getMessage());
        }
    }

    /**
     * TB_EMP_LST와 TB_EMP_REF 동기화 배치 수동 실행
     */
    @PostMapping("/manual-emp-ref-sync-batch")
    public ResponseEntity<SchedulerOutVo> rgsnManualEmpRefSyncBatch() {
        log.info("TB_EMP_LST와 TB_EMP_REF 동기화 배치 수동 실행 시작");
        
        try {
            long startTime = System.currentTimeMillis();
            
            // 배치 동기화 실행
            empLstService.manualBatchSyncEmpRef();
            
            long endTime = System.currentTimeMillis();
            long processingTime = endTime - startTime;
            
            // 처리된 직원 수 조회
            int totalEmployees = empLstService.getEmployeeCount();
            
            SchedulerOutVo result = new SchedulerOutVo();
            result.setProcessingTime(processingTime);
            result.setTotalEmployees(totalEmployees);
            result.setTimestamp(System.currentTimeMillis());
            result.setDetails("TB_EMP_LST와 TB_EMP_REF 배치 동기화를 실행했습니다. 새로운 직원은 삽입, 기존 직원은 업데이트, TB_EMP_LST에 없는 직원은 삭제됩니다.");
            
            result.setSuccess(true);
            result.setMessage("TB_EMP_LST와 TB_EMP_REF 동기화 배치 실행 완료");
            result.setErrorCode("SUCCESS");
            
            log.info("TB_EMP_LST와 TB_EMP_REF 동기화 배치 수동 실행 완료 - 처리시간: {}ms, 총 직원수: {}", processingTime, totalEmployees);
            return ResponseEntity.ok(result);
            
        } catch (Exception e) {
            log.error("TB_EMP_LST와 TB_EMP_REF 동기화 배치 수동 실행 실패", e);
            throw new BizException("배치 동기화 실행에 실패했습니다: " + e.getMessage());
        }
    }

    /**
     * 스케줄 상태 조회
     */
    @GetMapping("/schedule-status")
    public ResponseEntity<SchedulerOutVo> inqScheduleStatus() {
        log.info("스케줄 상태 조회 시작");
        
        try {
            SchedulerOutVo result = schedulerConfigService.inqScheduleStatus();
            
            result.setSuccess(true);
            result.setMessage("스케줄 상태를 조회했습니다.");
            result.setErrorCode("SUCCESS");
            
            log.info("스케줄 상태 조회 완료");
            return ResponseEntity.ok(result);
            
        } catch (Exception e) {
            log.error("스케줄 상태 조회 실패", e);
            throw new BizException("스케줄 상태 조회에 실패했습니다: " + e.getMessage());
        }
    }
} 