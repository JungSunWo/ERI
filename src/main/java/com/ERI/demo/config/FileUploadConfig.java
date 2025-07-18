package com.ERI.demo.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * 파일 업로드 디렉토리 초기화 설정
 */
@Slf4j
@Component
public class FileUploadConfig implements CommandLineRunner {

    @Value("${file.upload.path:/uploads}")
    private String uploadPath;

    @Override
    public void run(String... args) throws Exception {
        initializeUploadDirectory();
    }

    /**
     * 업로드 디렉토리 초기화
     */
    private void initializeUploadDirectory() {
        try {
            Path uploadDir = Paths.get(uploadPath);
            
            if (!Files.exists(uploadDir)) {
                Files.createDirectories(uploadDir);
                log.info("업로드 디렉토리 생성 완료: {}", uploadDir.toAbsolutePath());
            } else {
                log.info("업로드 디렉토리 이미 존재: {}", uploadDir.toAbsolutePath());
            }
            
            // 디렉토리 쓰기 권한 확인
            if (!Files.isWritable(uploadDir)) {
                log.warn("업로드 디렉토리에 쓰기 권한이 없습니다: {}", uploadDir.toAbsolutePath());
            }
            
        } catch (IOException e) {
            log.error("업로드 디렉토리 초기화 실패: {}", e.getMessage(), e);
            throw new RuntimeException("업로드 디렉토리를 생성할 수 없습니다.", e);
        }
    }
} 