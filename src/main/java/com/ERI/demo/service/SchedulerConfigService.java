package com.ERI.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;

/**
 * 스케줄 설정 파일을 읽어서 동적으로 스케줄을 관리하는 서비스
 */
@Service
public class SchedulerConfigService {

    @Autowired
    private TaskScheduler taskScheduler;

    @Autowired
    private EmpLstService empLstService;

    private final Map<String, ScheduledFuture<?>> scheduledTasks = new ConcurrentHashMap<>();
    private final String configFilePath = "src/main/resources/templates/scheduler_empInfo.txt";

    /**
     * 스케줄 설정 파일을 읽어서 스케줄을 동적으로 설정
     */
    public void loadAndScheduleTasks() {
        try {
            System.out.println("==========================================");
            System.out.println("스케줄 설정 파일 로드 시작: " + LocalDateTime.now());
            System.out.println("설정 파일 경로: " + configFilePath);
            System.out.println("==========================================");
            
            // 기존 스케줄 정리
            clearAllSchedules();
            
            // 설정 파일 읽기
            Map<String, String> scheduleConfigs = readScheduleConfig();
            
            System.out.println("읽어온 스케줄 설정 수: " + scheduleConfigs.size());
            
            // 각 스케줄 설정에 따라 작업 등록
            for (Map.Entry<String, String> entry : scheduleConfigs.entrySet()) {
                String methodName = entry.getKey();
                String cronExpression = entry.getValue();
                
                scheduleTask(methodName, cronExpression);
            }
            
            System.out.println("==========================================");
            System.out.println("스케줄 설정 로드 완료: " + LocalDateTime.now());
            System.out.println("등록된 작업 수: " + scheduleConfigs.size());
            System.out.println("==========================================");
            
        } catch (Exception e) {
            System.err.println("==========================================");
            System.err.println("스케줄 설정 로드 실패: " + LocalDateTime.now());
            System.err.println("오류 내용: " + e.getMessage());
            System.err.println("==========================================");
            e.printStackTrace();
        }
    }

    /**
     * 스케줄 설정 파일 읽기
     */
    private Map<String, String> readScheduleConfig() throws IOException {
        Map<String, String> configs = new HashMap<>();
        
        if (!Files.exists(Paths.get(configFilePath))) {
            System.err.println("스케줄 설정 파일을 찾을 수 없습니다: " + configFilePath);
            return configs;
        }
        
        try (BufferedReader reader = new BufferedReader(new FileReader(configFilePath))) {
            String line;
            int lineNumber = 0;
            
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                
                // 주석이나 빈 줄 건너뛰기
                if (line.trim().isEmpty() || line.trim().startsWith("#")) {
                    continue;
                }
                
                // 메소드명=cron표현식 형식 파싱
                String[] parts = line.split("=");
                if (parts.length == 2) {
                    String methodName = parts[0].trim();
                    String cronExpression = parts[1].trim();
                    
                    // 주석 처리된 라인 건너뛰기
                    if (cronExpression.startsWith("#")) {
                        continue;
                    }
                    
                    configs.put(methodName, cronExpression);
                    System.out.println("스케줄 설정 로드: " + methodName + " -> " + cronExpression);
                } else {
                    System.err.println("라인 " + lineNumber + "의 형식이 올바르지 않습니다: " + line);
                }
            }
        }
        
        return configs;
    }

    /**
     * 작업 스케줄링
     */
    private void scheduleTask(String methodName, String cronExpression) {
        try {
            System.out.println("작업 스케줄링 시작: " + methodName + " (" + cronExpression + ")");
            
            ScheduledFuture<?> task = null;
            
            switch (methodName) {
                case "dailyEmpEncryptBatch":
                    task = taskScheduler.schedule(
                        () -> executeDailyEmpEncryptBatch(),
                        new CronTrigger(cronExpression)
                    );
                    break;
                    
                case "weeklyCleanupBatch":
                    task = taskScheduler.schedule(
                        () -> executeWeeklyCleanupBatch(),
                        new CronTrigger(cronExpression)
                    );
                    break;
                    
                case "monthlyStatisticsBatch":
                    task = taskScheduler.schedule(
                        () -> executeMonthlyStatisticsBatch(),
                        new CronTrigger(cronExpression)
                    );
                    break;
                    
                case "testBatch":
                    task = taskScheduler.schedule(
                        () -> executeTestBatch(),
                        new CronTrigger(cronExpression)
                    );
                    break;
                    
                default:
                    System.err.println("알 수 없는 메소드명: " + methodName);
                    return;
            }
            
            if (task != null) {
                scheduledTasks.put(methodName, task);
                System.out.println("작업 스케줄링 완료: " + methodName + " (" + cronExpression + ")");
                System.out.println("다음 실행 예정: " + getNextExecutionTime(cronExpression));
            }
            
        } catch (Exception e) {
            System.err.println("작업 스케줄링 실패: " + methodName + " - " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * 다음 실행 시간 계산 (대략적)
     */
    private String getNextExecutionTime(String cronExpression) {
        try {
            // 간단한 다음 실행 시간 계산 (정확하지 않을 수 있음)
            return "매분마다 실행 (testBatch의 경우)";
        } catch (Exception e) {
            return "계산 불가";
        }
    }

    /**
     * 모든 스케줄 정리
     */
    public void clearAllSchedules() {
        for (Map.Entry<String, ScheduledFuture<?>> entry : scheduledTasks.entrySet()) {
            String methodName = entry.getKey();
            ScheduledFuture<?> task = entry.getValue();
            
            if (task != null && !task.isCancelled()) {
                task.cancel(false);
                System.out.println("스케줄 취소: " + methodName);
            }
        }
        scheduledTasks.clear();
    }

    /**
     * 특정 스케줄 취소
     */
    public boolean cancelSchedule(String methodName) {
        ScheduledFuture<?> task = scheduledTasks.get(methodName);
        if (task != null && !task.isCancelled()) {
            task.cancel(false);
            scheduledTasks.remove(methodName);
            System.out.println("스케줄 취소 완료: " + methodName);
            return true;
        }
        return false;
    }

    /**
     * 현재 스케줄 상태 조회
     */
    public Map<String, Object> getScheduleStatus() {
        Map<String, Object> status = new HashMap<>();
        
        for (Map.Entry<String, ScheduledFuture<?>> entry : scheduledTasks.entrySet()) {
            String methodName = entry.getKey();
            ScheduledFuture<?> task = entry.getValue();
            
            Map<String, Object> taskStatus = new HashMap<>();
            taskStatus.put("scheduled", task != null);
            taskStatus.put("cancelled", task != null && task.isCancelled());
            taskStatus.put("done", task != null && task.isDone());
            
            status.put(methodName, taskStatus);
        }
        
        return status;
    }

    // ===== 실제 배치 작업 메소드들 =====

    /**
     * 매일 직원 정보 암호화 배치
     */
    public void executeDailyEmpEncryptBatch() {
        try {
            System.out.println("매일 직원 정보 암호화 배치 시작: " + LocalDateTime.now());
            // TODO: 실제 암호화 로직 구현
            System.out.println("매일 직원 정보 암호화 배치 완료: " + LocalDateTime.now());
        } catch (Exception e) {
            System.err.println("매일 직원 정보 암호화 배치 실패: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 주간 정리 배치
     */
    public void executeWeeklyCleanupBatch() {
        try {
            System.out.println("주간 정리 배치 시작: " + LocalDateTime.now());
            // TODO: 주간 정리 로직 구현
            System.out.println("주간 정리 배치 완료: " + LocalDateTime.now());
        } catch (Exception e) {
            System.err.println("주간 정리 배치 실패: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 월간 통계 배치
     */
    public void executeMonthlyStatisticsBatch() {
        try {
            System.out.println("월간 통계 배치 시작: " + LocalDateTime.now());
            // TODO: 월간 통계 로직 구현
            System.out.println("월간 통계 배치 완료: " + LocalDateTime.now());
        } catch (Exception e) {
            System.err.println("월간 통계 배치 실패: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 테스트 배치
     */
    public void executeTestBatch() {
        try {
            LocalDateTime startTime = LocalDateTime.now();
            System.out.println("==========================================");
            System.out.println("테스트 배치 시작: " + startTime);
            System.out.println("==========================================");
            
            // empInfo.txt 배치 처리 실행
            empLstService.manualBatchLoadEmpInfo();
            
            LocalDateTime endTime = LocalDateTime.now();
            long duration = java.time.Duration.between(startTime, endTime).toMillis();
            
            System.out.println("==========================================");
            System.out.println("테스트 배치 완료: " + endTime);
            System.out.println("처리 시간: " + duration + "ms");
            System.out.println("==========================================");
            
        } catch (Exception e) {
            LocalDateTime errorTime = LocalDateTime.now();
            System.err.println("==========================================");
            System.err.println("테스트 배치 실패: " + errorTime);
            System.err.println("오류 내용: " + e.getMessage());
            System.err.println("==========================================");
            e.printStackTrace();
        }
    }

    /**
     * TB_EMP_LST와 TB_EMP_REF 동기화 배치 실행
     */
    public void executeEmpRefSyncBatch() {
        try {
            System.out.println("==========================================");
            System.out.println("TB_EMP_REF 동기화 배치 시작: " + LocalDateTime.now());
            System.out.println("==========================================");
            
            empLstService.syncEmpRefFromEmpLst();
            
            System.out.println("==========================================");
            System.out.println("TB_EMP_REF 동기화 배치 완료: " + LocalDateTime.now());
            System.out.println("==========================================");
            
        } catch (Exception e) {
            System.err.println("==========================================");
            System.err.println("TB_EMP_REF 동기화 배치 실패: " + LocalDateTime.now());
            System.err.println("오류 내용: " + e.getMessage());
            System.err.println("==========================================");
            e.printStackTrace();
        }
    }
} 