package com.ERI.demo.config;

import com.ERI.demo.service.DynamicSchedulerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 동적 스케줄러 설정
 * 애플리케이션 시작 시 스케줄 설정을 초기화
 */
@Configuration
@EnableScheduling
public class DynamicSchedulerConfig {

    private static final Logger logger = LoggerFactory.getLogger(DynamicSchedulerConfig.class);

    /**
     * 애플리케이션 시작 시 스케줄 설정 초기화
     */
    @Bean
    public CommandLineRunner initializeDynamicScheduler(ApplicationContext applicationContext) {
        return args -> {
            logger.info("=== 동적 스케줄러 초기화 시작 ===");
            
            try {
                // ApplicationContext를 통해 서비스 가져오기
                DynamicSchedulerService dynamicSchedulerService = 
                    applicationContext.getBean(DynamicSchedulerService.class);
                
                // 스케줄 설정 파일 읽기
                dynamicSchedulerService.initializeScheduleConfig();
                
                // 스케줄 설정 유효성 검증
                var validationResults = dynamicSchedulerService.validateAllScheduleConfig();
                
                logger.info("스케줄 설정 유효성 검증 결과:");
                for (var entry : validationResults.entrySet()) {
                    String status = entry.getValue() ? "✅ 유효" : "❌ 무효";
                    logger.info("  {}: {}", entry.getKey(), status);
                }
                
                // 현재 스케줄 설정 출력
                var scheduleConfig = dynamicSchedulerService.getScheduleConfig();
                logger.info("현재 스케줄 설정:");
                for (var entry : scheduleConfig.entrySet()) {
                    logger.info("  {} = {}", entry.getKey(), entry.getValue());
                }
                
                logger.info("=== 동적 스케줄러 초기화 완료 ===");
                
            } catch (Exception e) {
                logger.error("동적 스케줄러 초기화 실패: {}", e.getMessage(), e);
            }
        };
    }
} 