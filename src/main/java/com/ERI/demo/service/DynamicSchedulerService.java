package com.ERI.demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 동적 스케줄링 서비스
 * scheduler_empInfo.txt 파일을 읽어서 동적으로 스케줄을 설정
 */
@Service
public class DynamicSchedulerService {

    private static final Logger logger = LoggerFactory.getLogger(DynamicSchedulerService.class);

    @Autowired
    private EmpEncryptSchedulerService empEncryptSchedulerService;

    // 스케줄 설정을 저장하는 맵
    private final Map<String, String> scheduleConfig = new ConcurrentHashMap<>();
    
    // 스케줄 설정 파일 경로
    private static final String SCHEDULE_CONFIG_FILE = "templates/scheduler_empInfo.txt";

    /**
     * 스케줄 설정 파일을 읽어서 초기화
     */
    public void initializeScheduleConfig() {
        try {
            logger.info("스케줄 설정 파일 읽기 시작: {}", SCHEDULE_CONFIG_FILE);
            
            ClassPathResource resource = new ClassPathResource(SCHEDULE_CONFIG_FILE);
            if (!resource.exists()) {
                logger.warn("스케줄 설정 파일이 존재하지 않습니다: {}", SCHEDULE_CONFIG_FILE);
                return;
            }

            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8))) {

                String line;
                int lineCount = 0;

                while ((line = reader.readLine()) != null) {
                    lineCount++;
                    line = line.trim();
                    
                    // 빈 라인이나 주석 라인 스킵
                    if (line.isEmpty() || line.startsWith("#")) {
                        continue;
                    }

                    // 메소드명=cron표현식 형태로 파싱
                    String[] parts = line.split("=", 2);
                    if (parts.length == 2) {
                        String methodName = parts[0].trim();
                        String cronExpression = parts[1].trim();
                        
                        scheduleConfig.put(methodName, cronExpression);
                        logger.info("스케줄 설정 로드: {} = {}", methodName, cronExpression);
                    } else {
                        logger.warn("잘못된 스케줄 설정 형식 (라인 {}): {}", lineCount, line);
                    }
                }
                
                logger.info("스케줄 설정 파일 로드 완료 - 총 {}개 설정", scheduleConfig.size());
                
            }

        } catch (IOException e) {
            logger.error("스케줄 설정 파일 읽기 실패: {}", e.getMessage(), e);
        }
    }

    /**
     * 스케줄 설정을 다시 로드
     */
    public void reloadScheduleConfig() {
        logger.info("스케줄 설정 재로드 시작");
        scheduleConfig.clear();
        initializeScheduleConfig();
        logger.info("스케줄 설정 재로드 완료");
    }

    /**
     * 현재 스케줄 설정 조회
     */
    public Map<String, String> getScheduleConfig() {
        return new HashMap<>(scheduleConfig);
    }

    /**
     * 특정 메소드의 cron 표현식 조회
     */
    public String getCronExpression(String methodName) {
        return scheduleConfig.get(methodName);
    }

    /**
     * 동적 스케줄링을 위한 메소드 라우터
     * scheduler_empInfo.txt에서 설정된 메소들을 호출
     */
    public void executeScheduledMethod(String methodName) {
        try {
            logger.info("스케줄된 메소드 실행: {}", methodName);
            
            switch (methodName) {
                case "dailyEmpEncryptBatch":
                    empEncryptSchedulerService.dailyEmpEncryptBatch();
                    break;
                case "weeklyCleanupBatch":
                    empEncryptSchedulerService.weeklyCleanupBatch();
                    break;
                case "monthlyStatisticsBatch":
                    empEncryptSchedulerService.monthlyStatisticsBatch();
                    break;
                case "manualEmpEncryptBatch":
                    empEncryptSchedulerService.manualEmpEncryptBatch();
                    break;
                default:
                    logger.warn("알 수 없는 스케줄 메소드: {}", methodName);
            }
            
        } catch (Exception e) {
            logger.error("스케줄된 메소드 실행 실패: {}, 오류: {}", methodName, e.getMessage(), e);
        }
    }

    /**
     * 기본 스케줄 메소드들 (고정 cron 표현식 사용)
     * 동적 스케줄링은 별도로 구현
     */
    
    @Scheduled(cron = "0 0 2 * * ?")
    public void dynamicDailyEmpEncryptBatch() {
        logger.info("동적 스케줄: dailyEmpEncryptBatch 실행");
        executeScheduledMethod("dailyEmpEncryptBatch");
    }

    @Scheduled(cron = "0 0 3 ? * SUN")
    public void dynamicWeeklyCleanupBatch() {
        logger.info("동적 스케줄: weeklyCleanupBatch 실행");
        executeScheduledMethod("weeklyCleanupBatch");
    }

    @Scheduled(cron = "0 0 4 1 * ?")
    public void dynamicMonthlyStatisticsBatch() {
        logger.info("동적 스케줄: monthlyStatisticsBatch 실행");
        executeScheduledMethod("monthlyStatisticsBatch");
    }

    /**
     * 스케줄 설정 유효성 검증
     */
    public boolean validateCronExpression(String cronExpression) {
        try {
            new CronTrigger(cronExpression);
            return true;
        } catch (Exception e) {
            logger.error("잘못된 cron 표현식: {}, 오류: {}", cronExpression, e.getMessage());
            return false;
        }
    }

    /**
     * 모든 스케줄 설정의 유효성 검증
     */
    public Map<String, Boolean> validateAllScheduleConfig() {
        Map<String, Boolean> validationResults = new HashMap<>();
        
        for (Map.Entry<String, String> entry : scheduleConfig.entrySet()) {
            String methodName = entry.getKey();
            String cronExpression = entry.getValue();
            boolean isValid = validateCronExpression(cronExpression);
            validationResults.put(methodName, isValid);
            
            if (!isValid) {
                logger.warn("잘못된 스케줄 설정: {} = {}", methodName, cronExpression);
            }
        }
        
        return validationResults;
    }
} 