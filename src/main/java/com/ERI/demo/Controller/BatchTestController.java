package com.ERI.demo.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 배치 테스트 페이지 뷰 컨트롤러
 */
@Controller
public class BatchTestController {

    /**
     * 배치 테스트 페이지
     */
    @GetMapping("/batch-test")
    public String batchTestPage() {
        return "batch-test";
    }
} 