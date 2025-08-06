package com.ERI.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    private AuthInterceptor authInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor)
            .addPathPatterns("/api/**") // 보호할 경로
            .excludePathPatterns(
                "/api/public/**",        // 공개 API
                "/api/auth/login",       // 로그인 API
                "/api/auth/logout",       // 로그아웃 API
                "/api/batch/emp-info", 
                "/api/auth/get-emp-info",
                "/api/auth/session-status",
                "/api/menu/list",
                "/api/scheduler/**",
                "/api/db-test/**",
                "/api/encryption-test/**"  // 암호화 테스트 API
                
            );
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
            .allowedOrigins("http://localhost:3000") // 개발용 프론트엔드 주소
            .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH")
            .allowedHeaders("*")
            .exposedHeaders("*")
            .allowCredentials(true)
            .maxAge(3600); // preflight 요청 캐시 시간 (1시간)
        
        registry.addMapping("/uploads/**")
            .allowedOrigins("http://localhost:3000")
            .allowedMethods("GET", "OPTIONS")
            .allowedHeaders("*")
            .exposedHeaders("*")
            .allowCredentials(true)
            .maxAge(3600);
    }
    
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // favicon.ico 요청 처리
        registry.addResourceHandler("/favicon.ico")
            .addResourceLocations("classpath:/static/")
            .setCachePeriod(3600); // 1시간 캐시
        
        // 정적 리소스 처리
        registry.addResourceHandler("/static/**")
            .addResourceLocations("classpath:/static/")
            .setCachePeriod(3600);
        
        // 업로드된 파일 처리
        registry.addResourceHandler("/uploads/**")
            .addResourceLocations("file:uploads/")
            .setCachePeriod(3600);
    }
} 