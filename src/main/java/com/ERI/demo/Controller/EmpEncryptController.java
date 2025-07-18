package com.ERI.demo.Controller;

import com.ERI.demo.service.EmpEncryptService;
import com.ERI.demo.service.EmpLstService;
import com.ERI.demo.vo.EmpEncryptVO;
import com.ERI.demo.vo.EmpLstVO;
import com.ERI.demo.dto.UserAuthDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 직원 암호화 정보 컨트롤러
 * 원본 직원번호로 직원정보를 조회하고 세션에 저장
 */
@RestController
@RequestMapping("/api/emp-encrypt")
public class EmpEncryptController {
    
    @Autowired
    private EmpEncryptService empEncryptService;
    
    @Autowired
    private EmpLstService empLstService;
    
    private final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());
    
    /**
     * 원본 직원번호로 직원정보를 조회하고 세션에 저장
     */
    @PostMapping("/get-emp-info")
    public ResponseEntity<Map<String, Object>> getEmpInfoAndSetSession(@RequestBody Map<String, String> request, HttpSession session) {
        String origEmpNo = request.get("origEmpNo");
        log.info("직원번호로 직원정보 조회 및 세션 저장 요청: {}", origEmpNo);
        Map<String, Object> response = new HashMap<>();
       
        
        if (origEmpNo == null || origEmpNo.trim().isEmpty()) {
            response.put("success", false);
            response.put("message", "직원번호가 필요합니다.");
            return ResponseEntity.ok(response);
        }
        
        try {
            // 1. 원본 직원번호로 tb_emp_encrypt 테이블에서 암호화된 직원번호 조회
            EmpEncryptVO empEncrypt = empEncryptService.getEmpEncryptByOrigEmpNo(origEmpNo);
            
            if (empEncrypt == null) {
                response.put("success", false);
                response.put("message", "해당 직원번호의 암호화 정보를 찾을 수 없습니다: " + origEmpNo);
                log.warn("암호화 정보 조회 실패: {}", origEmpNo);
                return ResponseEntity.ok(response);
            }
            
            String encryptEmpNo = empEncrypt.getEncryptEmpNo();
            log.info("암호화된 직원번호 조회 성공: {} -> {}", origEmpNo, encryptEmpNo);
            
            // 2. 암호화된 직원번호로 tb_emp_lst 테이블에서 직원정보 조회
            EmpLstVO empInfo = empEncryptService.getEmpInfoByOrigEmpNo(origEmpNo);
            
            if (empInfo == null) {
                response.put("success", false);
                response.put("message", "해당 직원번호의 직원정보를 찾을 수 없습니다");
                response.put("encryptEmpNo", encryptEmpNo);
                log.warn("직원정보 조회 실패: {}", encryptEmpNo);
                return ResponseEntity.ok(response);
            }
            
            // 3. TB_ADMIN_LST 테이블에서 관리자 여부 확인
            boolean isAdmin = empEncryptService.isAdminByOrigEmpNo(origEmpNo);
            log.info("관리자 여부 확인: {} -> {}", origEmpNo, isAdmin);
            
            // 4. UserAuthDto 생성 및 세션에 저장
            UserAuthDto userAuth = new UserAuthDto();
            userAuth.setUserId(encryptEmpNo);
            userAuth.setUserName(empInfo.getEmpNm());
            userAuth.setDeptCd(empInfo.getEmpDeptCd());
            userAuth.setRankCd(empInfo.getEmpPosCd());
            userAuth.setAdmin(isAdmin);
            // empStsCd와 origEmpNo는 UserAuthDto에 해당 필드가 없으므로 제외
            
            session.setAttribute("USER_AUTH", userAuth);
            session.setAttribute("empInfo", empInfo);             
            session.setAttribute("empEncrypt", empEncrypt);
            session.setAttribute("encryptEmpNo", encryptEmpNo);
            session.setAttribute("isAdmin", isAdmin);  // 관리자 여부 세션에 저장
            
            // 5. 전체 직원 목록 조회 (캐싱용)
            List<EmpLstVO> allEmployees = empLstService.getAllEmployeesForCache();
            Map<String, String> employeeCache = allEmployees.stream()
                .collect(Collectors.toMap(
                    EmpLstVO::getEmpId,
                    EmpLstVO::getEmpNm,
                    (existing, replacement) -> existing // 중복 키가 있을 경우 기존 값 유지
                ));
            
            // 6. 응답 데이터 구성
            response.put("success", true);
            response.put("message", "직원정보 조회 및 세션 저장 성공");        
            response.put("userAuth", userAuth);
            response.put("employeeCache", employeeCache);
            
            log.info("직원정보 조회 및 세션 저장 완료: {} (사용자: {}, 관리자: {}, 캐시: {}명)", 
                origEmpNo, userAuth.getUserId(), isAdmin, employeeCache.size());
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            log.error("직원정보 조회 중 오류 발생: {}, 오류: {}", origEmpNo, e.getMessage(), e);
            response.put("success", false);
            response.put("message", "조회 중 오류가 발생했습니다: " + e.getMessage());
            return ResponseEntity.ok(response);
        }
    }
} 