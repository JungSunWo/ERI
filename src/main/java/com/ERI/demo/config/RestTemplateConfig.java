package com.ERI.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.nio.charset.StandardCharsets;
import java.time.Duration;

/**
 * RestTemplate 설정
 */
@Configuration
public class RestTemplateConfig {
    
    /**
     * 메신저 API용 RestTemplate
     */
    @Bean("messengerRestTemplate")
    public RestTemplate messengerRestTemplate() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(10000); // 10초
        factory.setReadTimeout(30000);    // 30초
        
        RestTemplate restTemplate = new RestTemplate(factory);
        
        // 문자 인코딩 설정
        restTemplate.getMessageConverters().add(0, 
            new StringHttpMessageConverter(StandardCharsets.UTF_8));
        
        // JSON 컨버터 추가
        restTemplate.getMessageConverters().add(
            new MappingJackson2HttpMessageConverter());
        
        return restTemplate;
    }
    
    /**
     * 기본 RestTemplate
     */
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder
            .setConnectTimeout(Duration.ofSeconds(10))
            .setReadTimeout(Duration.ofSeconds(30))
            .build();
    }
}