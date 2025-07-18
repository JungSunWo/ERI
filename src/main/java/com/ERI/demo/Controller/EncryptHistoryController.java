package com.ERI.demo.Controller;

import com.ERI.demo.service.EncryptHistoryService;
import com.ERI.demo.vo.EncryptHistoryVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 암호화 이력 컨트롤러
 */
@RestController
@RequestMapping("/api/encrypt-history")
public class EncryptHistoryController {
    @Autowired
    private EncryptHistoryService encryptHistoryService;
    
    private final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());

    /**
     * 전체 암호화 이력 조회
     */
    @GetMapping("/all")
    public List<EncryptHistoryVO> getAllEncryptHistory() {
        return encryptHistoryService.getAllEncryptHistory();
    }
    
    /**
     * 직원별 암호화 이력 조회
     */
    @GetMapping("/emp/{empSeq}")
    public List<EncryptHistoryVO> getEncryptHistoryByEmpSeq(@PathVariable Long empSeq) {
        return encryptHistoryService.getEncryptHistoryByEmpSeq(empSeq);
    }
    
    /**
     * 작업 타입별 암호화 이력 조회
     */
    @GetMapping("/type/{operationType}")
    public List<EncryptHistoryVO> getEncryptHistoryByOperationType(@PathVariable String operationType) {
        return encryptHistoryService.getEncryptHistoryByOperationType(operationType);
    }
    
    /**
     * 작업자별 암호화 이력 조회
     */
    @GetMapping("/operator/{operatorSeq}")
    public List<EncryptHistoryVO> getEncryptHistoryByOperatorSeq(@PathVariable Long operatorSeq) {
        return encryptHistoryService.getEncryptHistoryByOperatorSeq(operatorSeq);
    }
    
    /**
     * 기간별 암호화 이력 조회
     */
    @GetMapping("/date-range")
    public List<EncryptHistoryVO> getEncryptHistoryByDateRange(
            @RequestParam String startDate, 
            @RequestParam String endDate) {
        return encryptHistoryService.getEncryptHistoryByDateRange(startDate, endDate);
    }
    
    /**
     * 암호화 이력 저장 테스트
     */
    @PostMapping("/test-save")
    public String testSaveEncryptHistory() {
        try {
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
            return "암호화 이력 저장 테스트 완료: " + result + "건 저장됨";
            
        } catch (Exception e) {
            return "암호화 이력 저장 테스트 실패: " + e.getMessage();
        }
    }
} 