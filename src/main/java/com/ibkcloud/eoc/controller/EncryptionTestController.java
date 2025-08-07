package com.ibkcloud.eoc.controller;

import com.ibkcloud.eoc.cmm.exception.BizException;
import com.ibkcloud.eoc.cmm.util.EncryptionUtil;
import com.ibkcloud.eoc.service.EmpLstService;
import com.ibkcloud.eoc.controller.vo.EncryptionTestInVo;
import com.ibkcloud.eoc.controller.vo.EncryptionTestOutVo;
import com.ibkcloud.eoc.controller.vo.EmpLstOutVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 암호화 테스트 REST 컨트롤러
 */
@Slf4j
@RestController
@RequestMapping("/api/encryption-test")
@RequiredArgsConstructor
public class EncryptionTestController {

    private final EmpLstService empLstService;

    /**
     * 암호화 유틸리티 테스트
     */
    @PostMapping("/test-encryption")
    public ResponseEntity<EncryptionTestOutVo> inqEncryptionTest(@RequestBody EncryptionTestInVo request) {
        try {
            String originalText = request.getText();
            String encryptedText = EncryptionUtil.encrypt(originalText);
            String decryptedText = EncryptionUtil.decrypt(encryptedText);

            EncryptionTestOutVo response = new EncryptionTestOutVo();
            response.setSuccess(true);
            response.setOriginal(originalText);
            response.setEncrypted(encryptedText);
            response.setDecrypted(decryptedText);
            response.setIsMatch(originalText.equals(decryptedText));
            response.setMessage("암호화 테스트가 완료되었습니다.");

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("암호화 테스트 실패", e);
            throw new BizException("ET001", "암호화 테스트에 실패했습니다: " + e.getMessage());
        }
    }

    /**
     * 이메일 암호화 테스트
     */
    @PostMapping("/test-email-encryption")
    public ResponseEntity<EncryptionTestOutVo> inqEmailEncryptionTest(@RequestBody EncryptionTestInVo request) {
        try {
            String originalEmail = request.getEmail();
            String encryptedEmail = EncryptionUtil.encryptEmail(originalEmail);
            String decryptedEmail = EncryptionUtil.decryptEmail(encryptedEmail);

            EncryptionTestOutVo response = new EncryptionTestOutVo();
            response.setSuccess(true);
            response.setOriginal(originalEmail);
            response.setEncrypted(encryptedEmail);
            response.setDecrypted(decryptedEmail);
            response.setIsMatch(originalEmail.equals(decryptedEmail));
            response.setMessage("이메일 암호화 테스트가 완료되었습니다.");

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("이메일 암호화 테스트 실패", e);
            throw new BizException("ET002", "이메일 암호화 테스트에 실패했습니다: " + e.getMessage());
        }
    }

    /**
     * 전화번호 암호화 테스트
     */
    @PostMapping("/test-phone-encryption")
    public ResponseEntity<EncryptionTestOutVo> inqPhoneEncryptionTest(@RequestBody EncryptionTestInVo request) {
        try {
            String originalPhone = request.getPhone();
            String encryptedPhone = EncryptionUtil.encryptPhone(originalPhone);
            String decryptedPhone = EncryptionUtil.decryptPhone(encryptedPhone);

            EncryptionTestOutVo response = new EncryptionTestOutVo();
            response.setSuccess(true);
            response.setOriginal(originalPhone);
            response.setEncrypted(encryptedPhone);
            response.setDecrypted(decryptedPhone);
            response.setIsMatch(originalPhone.equals(decryptedPhone));
            response.setMessage("전화번호 암호화 테스트가 완료되었습니다.");

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("전화번호 암호화 테스트 실패", e);
            throw new BizException("ET003", "전화번호 암호화 테스트에 실패했습니다: " + e.getMessage());
        }
    }

    /**
     * 직원 정보 조회 (암호화된 데이터 확인)
     */
    @GetMapping("/employees")
    public ResponseEntity<EncryptionTestOutVo> inqEmployees() {
        try {
            List<EmpLstOutVo> employees = empLstService.inqAllEmployees();

            EncryptionTestOutVo response = new EncryptionTestOutVo();
            response.setSuccess(true);
            response.setCount(employees.size());
            response.setEmployees(employees);
            response.setMessage("직원 정보를 조회했습니다.");

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("직원 정보 조회 실패", e);
            throw new BizException("ET004", "직원 정보 조회에 실패했습니다: " + e.getMessage());
        }
    }

    /**
     * 특정 직원 정보 조회 (암호화된 데이터 확인)
     */
    @GetMapping("/employee/{empId}")
    public ResponseEntity<EncryptionTestOutVo> inqEmployee(@PathVariable String empId) {
        try {
            EmpLstOutVo employee = empLstService.inqEmployeeByEmpId(empId);

            if (employee == null) {
                throw new BizException("ET005", "직원을 찾을 수 없습니다: " + empId);
            }

            EncryptionTestOutVo response = new EncryptionTestOutVo();
            response.setSuccess(true);
            response.setEmployee(employee);
            response.setMessage("직원 정보를 조회했습니다.");

            return ResponseEntity.ok(response);
        } catch (BizException e) {
            throw e;
        } catch (Exception e) {
            log.error("직원 정보 조회 실패", e);
            throw new BizException("ET006", "직원 정보 조회에 실패했습니다: " + e.getMessage());
        }
    }

    /**
     * 암호화된 데이터인지 확인
     */
    @PostMapping("/check-encrypted")
    public ResponseEntity<EncryptionTestOutVo> inqEncryptedCheck(@RequestBody EncryptionTestInVo request) {
        try {
            String text = request.getText();
            boolean isEncrypted = EncryptionUtil.isEncrypted(text);

            EncryptionTestOutVo response = new EncryptionTestOutVo();
            response.setSuccess(true);
            response.setOriginal(text);
            response.setIsEncrypted(isEncrypted);
            response.setMessage("암호화 여부를 확인했습니다.");

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("암호화 여부 확인 실패", e);
            throw new BizException("ET007", "암호화 여부 확인에 실패했습니다: " + e.getMessage());
        }
    }

    /**
     * 복호화 테스트
     */
    @PostMapping("/test-decryption")
    public ResponseEntity<EncryptionTestOutVo> inqDecryptionTest(@RequestBody EncryptionTestInVo request) {
        try {
            String encryptedText = request.getEncryptedText();

            if (encryptedText == null || encryptedText.trim().isEmpty()) {
                throw new BizException("ET008", "암호화된 텍스트를 입력해주세요.");
            }

            if (!EncryptionUtil.isEncrypted(encryptedText)) {
                throw new BizException("ET009", "입력된 텍스트가 암호화된 데이터가 아닙니다.");
            }

            String decryptedText = EncryptionUtil.decrypt(encryptedText);

            EncryptionTestOutVo response = new EncryptionTestOutVo();
            response.setSuccess(true);
            response.setEncrypted(encryptedText);
            response.setDecrypted(decryptedText);
            response.setMessage("복호화 테스트가 완료되었습니다.");

            return ResponseEntity.ok(response);
        } catch (BizException e) {
            throw e;
        } catch (Exception e) {
            log.error("복호화 테스트 실패", e);
            throw new BizException("ET010", "복호화 테스트에 실패했습니다: " + e.getMessage());
        }
    }

    /**
     * 이메일 복호화 테스트
     */
    @PostMapping("/test-email-decryption")
    public ResponseEntity<EncryptionTestOutVo> inqEmailDecryptionTest(@RequestBody EncryptionTestInVo request) {
        try {
            String encryptedEmail = request.getEncryptedEmail();

            if (encryptedEmail == null || encryptedEmail.trim().isEmpty()) {
                throw new BizException("ET011", "암호화된 이메일을 입력해주세요.");
            }

            if (!EncryptionUtil.isEncrypted(encryptedEmail)) {
                throw new BizException("ET012", "입력된 텍스트가 암호화된 이메일이 아닙니다.");
            }

            String decryptedEmail = EncryptionUtil.decryptEmail(encryptedEmail);

            EncryptionTestOutVo response = new EncryptionTestOutVo();
            response.setSuccess(true);
            response.setEncrypted(encryptedEmail);
            response.setDecrypted(decryptedEmail);
            response.setMessage("이메일 복호화 테스트가 완료되었습니다.");

            return ResponseEntity.ok(response);
        } catch (BizException e) {
            throw e;
        } catch (Exception e) {
            log.error("이메일 복호화 테스트 실패", e);
            throw new BizException("ET013", "이메일 복호화 테스트에 실패했습니다: " + e.getMessage());
        }
    }

    /**
     * 전화번호 복호화 테스트
     */
    @PostMapping("/test-phone-decryption")
    public ResponseEntity<EncryptionTestOutVo> inqPhoneDecryptionTest(@RequestBody EncryptionTestInVo request) {
        try {
            String encryptedPhone = request.getEncryptedPhone();

            if (encryptedPhone == null || encryptedPhone.trim().isEmpty()) {
                throw new BizException("ET014", "암호화된 전화번호를 입력해주세요.");
            }

            if (!EncryptionUtil.isEncrypted(encryptedPhone)) {
                throw new BizException("ET015", "입력된 텍스트가 암호화된 전화번호가 아닙니다.");
            }

            String decryptedPhone = EncryptionUtil.decryptPhone(encryptedPhone);

            EncryptionTestOutVo response = new EncryptionTestOutVo();
            response.setSuccess(true);
            response.setEncrypted(encryptedPhone);
            response.setDecrypted(decryptedPhone);
            response.setMessage("전화번호 복호화 테스트가 완료되었습니다.");

            return ResponseEntity.ok(response);
        } catch (BizException e) {
            throw e;
        } catch (Exception e) {
            log.error("전화번호 복호화 테스트 실패", e);
            throw new BizException("ET016", "전화번호 복호화 테스트에 실패했습니다: " + e.getMessage());
        }
    }
} 