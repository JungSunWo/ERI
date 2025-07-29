package com.ERI.demo.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import lombok.Data;

/**
 * 메신저 알람 전송 설정
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "messenger")
public class MessengerConfig {
    
    /**
     * 개발 환경 알람 URL
     */
    private String devUrl = "http://{target_ip}:{port}/servlet/AnnounceService";
    
    /**
     * 운영 환경 알람 URL
     */
    private String prodUrl = "172.18.123.43:8010";
    
    /**
     * 개발 환경 쪽지 URL
     */
    private String devMessageUrl = "http://{target_ip}:{port}/msg/sendMessage.do";
    
    /**
     * 운영 환경 쪽지 URL
     */
    private String prodMessageUrl = "172.18.76.21:8010";
    
    /**
     * 현재 환경 (dev/prod)
     */
    private String environment = "dev";
    
    /**
     * 발송서버코드
     */
    private String srvCode;
    
    /**
     * 기본 발신자사번
     */
    private String defaultSender;
    
    /**
     * 기본 발신자명
     */
    private String defaultSenderAlias = "ERI 시스템";
    
    /**
     * 연결 타임아웃 (초)
     */
    private int connectTimeout = 10;
    
    /**
     * 읽기 타임아웃 (초)
     */
    private int readTimeout = 30;
    
    /**
     * 최대 재시도 횟수
     */
    private int maxRetries = 3;
    
    /**
     * 재시도 간격 (밀리초)
     */
    private long retryInterval = 1000;
}