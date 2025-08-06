package com.ERI.demo.Controller;

import com.ERI.demo.service.EmpLstService;
import com.ERI.demo.util.EncryptionUtil;
import com.ERI.demo.vo.employee.EmpLstVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 암호화 기능 테스트 컨트롤러
 */
@RestController
@RequestMapping("/api/encryption-test")
public class EncryptionTestController {

    @Autowired
    private EmpLstService empLstService;

    /**
     * 암호화 유틸리티 테스트
     */
    @PostMapping("/test-encryption")
    public ResponseEntity<Map<String, Object>> testEncryption(@RequestBody Map<String, String> request) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            String originalText = request.get("text");
            String encryptedText = EncryptionUtil.encrypt(originalText);
            String decryptedText = EncryptionUtil.decrypt(encryptedText);
            
            response.put("success", true);
            response.put("original", originalText);
            response.put("encrypted", encryptedText);
            response.put("decrypted", decryptedText);
            response.put("isMatch", originalText.equals(decryptedText));
            
        } catch (Exception e) {
            response.put("success", false);
            response.put("error", e.getMessage());
        }
        
        return ResponseEntity.ok(response);
    }

    /**
     * 이메일 암호화 테스트
     */
    @PostMapping("/test-email-encryption")
    public ResponseEntity<Map<String, Object>> testEmailEncryption(@RequestBody Map<String, String> request) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            String originalEmail = request.get("email");
            String encryptedEmail = EncryptionUtil.encryptEmail(originalEmail);
            String decryptedEmail = EncryptionUtil.decryptEmail(encryptedEmail);
            
            response.put("success", true);
            response.put("original", originalEmail);
            response.put("encrypted", encryptedEmail);
            response.put("decrypted", decryptedEmail);
            response.put("isMatch", originalEmail.equals(decryptedEmail));
            
        } catch (Exception e) {
            response.put("success", false);
            response.put("error", e.getMessage());
        }
        
        return ResponseEntity.ok(response);
    }

    /**
     * 전화번호 암호화 테스트
     */
    @PostMapping("/test-phone-encryption")
    public ResponseEntity<Map<String, Object>> testPhoneEncryption(@RequestBody Map<String, String> request) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            String originalPhone = request.get("phone");
            String encryptedPhone = EncryptionUtil.encryptPhone(originalPhone);
            String decryptedPhone = EncryptionUtil.decryptPhone(encryptedPhone);
            
            response.put("success", true);
            response.put("original", originalPhone);
            response.put("encrypted", encryptedPhone);
            response.put("decrypted", decryptedPhone);
            response.put("isMatch", originalPhone.equals(decryptedPhone));
            
        } catch (Exception e) {
            response.put("success", false);
            response.put("error", e.getMessage());
        }
        
        return ResponseEntity.ok(response);
    }

    /**
     * 직원 정보 조회 (암호화된 데이터 확인)
     */
    @GetMapping("/employees")
    public ResponseEntity<Map<String, Object>> getEmployees() {
        Map<String, Object> response = new HashMap<>();
        
        try {
            List<EmpLstVO> employees = empLstService.getAllEmployees();
            
            response.put("success", true);
            response.put("count", employees.size());
            response.put("employees", employees);
            
        } catch (Exception e) {
            response.put("success", false);
            response.put("error", e.getMessage());
        }
        
        return ResponseEntity.ok(response);
    }

    /**
     * 특정 직원 정보 조회 (암호화된 데이터 확인)
     */
    @GetMapping("/employee/{empId}")
    public ResponseEntity<Map<String, Object>> getEmployee(@PathVariable String empId) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            EmpLstVO employee = empLstService.getEmployeeByEmpId(empId);
            
            if (employee != null) {
                response.put("success", true);
                response.put("employee", employee);
            } else {
                response.put("success", false);
                response.put("error", "직원을 찾을 수 없습니다: " + empId);
            }
            
        } catch (Exception e) {
            response.put("success", false);
            response.put("error", e.getMessage());
        }
        
        return ResponseEntity.ok(response);
    }

    /**
     * 암호화된 데이터인지 확인
     */
    @PostMapping("/check-encrypted")
    public ResponseEntity<Map<String, Object>> checkEncrypted(@RequestBody Map<String, String> request) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            String text = request.get("text");
            boolean isEncrypted = EncryptionUtil.isEncrypted(text);
            
            response.put("success", true);
            response.put("text", text);
            response.put("isEncrypted", isEncrypted);
            
        } catch (Exception e) {
            response.put("success", false);
            response.put("error", e.getMessage());
        }
        
        return ResponseEntity.ok(response);
    }

    /**
     * 복호화 테스트
     */
    @PostMapping("/test-decryption")
    public ResponseEntity<Map<String, Object>> testDecryption(@RequestBody Map<String, String> request) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            String encryptedText = request.get("encryptedText");
            
            if (encryptedText == null || encryptedText.trim().isEmpty()) {
                response.put("success", false);
                response.put("error", "암호화된 텍스트를 입력해주세요.");
                return ResponseEntity.ok(response);
            }
            
            if (!EncryptionUtil.isEncrypted(encryptedText)) {
                response.put("success", false);
                response.put("error", "입력된 텍스트가 암호화된 데이터가 아닙니다.");
                return ResponseEntity.ok(response);
            }
            
            String decryptedText = EncryptionUtil.decrypt(encryptedText);
            
            response.put("success", true);
            response.put("encrypted", encryptedText);
            response.put("decrypted", decryptedText);
            
        } catch (Exception e) {
            response.put("success", false);
            response.put("error", "복호화 실패: " + e.getMessage());
        }
        
        return ResponseEntity.ok(response);
    }

    /**
     * 이메일 복호화 테스트
     */
    @PostMapping("/test-email-decryption")
    public ResponseEntity<Map<String, Object>> testEmailDecryption(@RequestBody Map<String, String> request) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            String encryptedEmail = request.get("encryptedEmail");
            
            if (encryptedEmail == null || encryptedEmail.trim().isEmpty()) {
                response.put("success", false);
                response.put("error", "암호화된 이메일을 입력해주세요.");
                return ResponseEntity.ok(response);
            }
            
            if (!EncryptionUtil.isEncrypted(encryptedEmail)) {
                response.put("success", false);
                response.put("error", "입력된 텍스트가 암호화된 이메일이 아닙니다.");
                return ResponseEntity.ok(response);
            }
            
            String decryptedEmail = EncryptionUtil.decryptEmail(encryptedEmail);
            
            response.put("success", true);
            response.put("encrypted", encryptedEmail);
            response.put("decrypted", decryptedEmail);
            
        } catch (Exception e) {
            response.put("success", false);
            response.put("error", "이메일 복호화 실패: " + e.getMessage());
        }
        
        return ResponseEntity.ok(response);
    }

    /**
     * 전화번호 복호화 테스트
     */
    @PostMapping("/test-phone-decryption")
    public ResponseEntity<Map<String, Object>> testPhoneDecryption(@RequestBody Map<String, String> request) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            String encryptedPhone = request.get("encryptedPhone");
            
            if (encryptedPhone == null || encryptedPhone.trim().isEmpty()) {
                response.put("success", false);
                response.put("error", "암호화된 전화번호를 입력해주세요.");
                return ResponseEntity.ok(response);
            }
            
            if (!EncryptionUtil.isEncrypted(encryptedPhone)) {
                response.put("success", false);
                response.put("error", "입력된 텍스트가 암호화된 전화번호가 아닙니다.");
                return ResponseEntity.ok(response);
            }
            
            String decryptedPhone = EncryptionUtil.decryptPhone(encryptedPhone);
            
            response.put("success", true);
            response.put("encrypted", encryptedPhone);
            response.put("decrypted", decryptedPhone);
            
        } catch (Exception e) {
            response.put("success", false);
            response.put("error", "전화번호 복호화 실패: " + e.getMessage());
        }
        
        return ResponseEntity.ok(response);
    }
} 