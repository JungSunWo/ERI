package com.ERI.demo.Controller;

import com.ERI.demo.service.EncryptHistoryService;
import com.ERI.demo.vo.EncryptHistoryVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * 테스트용 컨트롤러
 */
@RestController
@RequestMapping("/api/test")
public class TestController {
    
    private final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());
    
    @Autowired
    private EncryptHistoryService encryptHistoryService;
    
    /**
     * 간단한 테스트 API (인증 없음)
     * GET /api/test/simple
     */
    @GetMapping("/simple")
    public String simpleTest() {
        log.info("테스트 API 호출");
        return "API 테스트 성공";
    }
    
    /**
     * 암호화 이력 저장 테스트 (인증 없음)
     */
    @PostMapping("/encrypt-history")
    public ResponseEntity<Map<String, Object>> testEncryptHistory() {
        Map<String, Object> response = new HashMap<>();
        try {
            // 테스트용 암호화 이력 생성
            EncryptHistoryVO testHistory = encryptHistoryService.createEncryptHistory(
                1L,                    // 직원 일련번호
                "TEST001",             // 원본 직원번호
                "encrypted_test_001",  // 암호화된 직원번호
                "테스트직원",           // 원본 직원명
                "김테스트",             // 랜덤 변형 한글명
                "test@example.com",    // 원본 직원 이메일
                "SHA-256",             // 암호화 알고리즘
                "TEST_KEY_001",        // 암호화 키 ID
                "test_salt_key",       // 암호화 초기화 벡터
                "TEST",                // 작업 타입
                1L                     // 작업자 일련번호
            );
            
            int result = encryptHistoryService.saveEncryptHistory(testHistory);
            
            response.put("success", true);
            response.put("message", "암호화 이력 저장 테스트 완료");
            response.put("result", result);
            response.put("data", testHistory);
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "테스트 실패: " + e.getMessage());
            response.put("error", e.toString());
            return ResponseEntity.internalServerError().body(response);
        }
    }
    
    /**
     * 암호화 이력 조회 테스트 (인증 없음)
     */
    @GetMapping("/encrypt-history")
    public ResponseEntity<Map<String, Object>> testGetEncryptHistory() {
        Map<String, Object> response = new HashMap<>();
        try {
            var historyList = encryptHistoryService.getAllEncryptHistory();
            
            response.put("success", true);
            response.put("message", "암호화 이력 조회 테스트 완료");
            response.put("count", historyList.size());
            response.put("data", historyList);
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "테스트 실패: " + e.getMessage());
            response.put("error", e.toString());
            return ResponseEntity.internalServerError().body(response);
        }
    }
    
    /**
     * 간단한 상태 확인 API (인증 없음)
     */
    @GetMapping("/status")
    public Map<String, Object> getStatus() {
        Map<String, Object> status = new HashMap<>();
        status.put("status", "RUNNING");
        status.put("timestamp", LocalDateTime.now());
        status.put("message", "애플리케이션이 정상적으로 실행 중입니다.");
        return status;
    }
}
