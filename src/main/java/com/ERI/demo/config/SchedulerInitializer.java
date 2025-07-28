package com.ERI.demo.config;

import com.ERI.demo.service.SchedulerConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * 애플리케이션 시작 시 스케줄 설정을 자동으로 로드하는 초기화 클래스
 */
@Component
public class SchedulerInitializer implements ApplicationListener<ApplicationReadyEvent> {

    @Autowired
    private SchedulerConfigService schedulerConfigService;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        System.out.println("애플리케이션 시작 - 스케줄 설정 로드 중...");
        schedulerConfigService.loadAndScheduleTasks();
        System.out.println("스케줄 설정 로드 완료");
    }
} 