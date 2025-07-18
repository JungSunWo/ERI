package com.ERI.demo.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Favicon 처리 컨트롤러
 * 브라우저의 자동 favicon.ico 요청을 처리
 */
@RestController
public class FaviconController {
    
    /**
     * favicon.ico 요청 처리
     * 204 No Content로 응답하여 브라우저가 더 이상 요청하지 않도록 함
     */
    @GetMapping("/favicon.ico")
    public ResponseEntity<Void> favicon() {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
} 