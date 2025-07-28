package com.ERI.demo.Controller;

import com.ERI.demo.service.EmpLstService;
import com.ERI.demo.mappers.AdminLstMapper;
import com.ERI.demo.mappers.CounselorLstMapper;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 인증 관련 컨트롤러
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {
	
    
    @Autowired
    private EmpLstService empLstService;
    
    @Autowired
    private AdminLstMapper adminLstMapper;
    
    @Autowired
    private CounselorLstMapper counselorLstMapper;
    
    private final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());


    
    /**
     * 로그아웃
     * POST /api/auth/logout
     */
    @PostMapping(value = "/logout", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> logout(HttpSession session) {
        com.ERI.demo.vo.employee.EmpLstVO empInfo = (com.ERI.demo.vo.employee.EmpLstVO) session.getAttribute("EMP_INFO");
        if (empInfo != null) {
            log.info("사용자 로그아웃: {} ({})", empInfo.getEmpNm(), empInfo.getEriEmpId());
        }
        
        session.invalidate();
        return ResponseEntity.ok("로그아웃되었습니다.");
    }
    
    /**
     * 현재 로그인 사용자 정보 조회
     * GET /api/auth/current-user
     */
    @GetMapping(value = "/current-user", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<com.ERI.demo.vo.employee.EmpLstVO> getCurrentUser(HttpSession session) {
        com.ERI.demo.vo.employee.EmpLstVO empInfo = (com.ERI.demo.vo.employee.EmpLstVO) session.getAttribute("EMP_INFO");
        if (empInfo == null) {
            log.debug("인증되지 않은 사용자가 현재 사용자 정보 요청");
            return ResponseEntity.status(401).build();
        }
        
        log.debug("현재 사용자 정보 조회: {} ({})", empInfo.getEmpNm(), empInfo.getEriEmpId());
        return ResponseEntity.ok(empInfo);
    }
    
    /**
     * 권한 체크 테스트
     * GET /api/auth/check-auth
     */
    @GetMapping(value = "/check-auth", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> checkAuth(HttpSession session) {
        com.ERI.demo.vo.employee.EmpLstVO empInfo = (com.ERI.demo.vo.employee.EmpLstVO) session.getAttribute("EMP_INFO");
        if (empInfo == null) {
            log.debug("인증되지 않은 사용자가 권한 체크 요청");
            return ResponseEntity.status(401).body("로그인이 필요합니다.");
        }
        
        log.debug("권한 체크 요청: {} ({})", empInfo.getEmpNm(), empInfo.getEriEmpId());
        
        StringBuilder result = new StringBuilder();
        result.append("사용자: ").append(empInfo.getEmpNm()).append("\n");
        result.append("ERI 직원ID: ").append(empInfo.getEriEmpId()).append("\n");
        result.append("부서코드: ").append(empInfo.getBlngBrcd()).append("\n");
        result.append("직급코드: ").append(empInfo.getJbttCd()).append("\n");
        
        return ResponseEntity.ok(result.toString());
    }

    /**
     * 세션 상태(로그인 여부) 확인
     * GET /api/auth/session-status
     */
    @GetMapping(value = "/session-status", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> sessionStatus(HttpSession session) {
        com.ERI.demo.vo.employee.EmpLstVO empInfo = (com.ERI.demo.vo.employee.EmpLstVO) session.getAttribute("EMP_INFO");
        if (empInfo == null) {
            return ResponseEntity.ok(new java.util.HashMap<String, Object>() {{
                put("loggedIn", false);
                put("message", "로그인되어 있지 않습니다.");
            }});
        } else {
            return ResponseEntity.ok(new java.util.HashMap<String, Object>() {{
                put("loggedIn", true);
                put("userId", empInfo.getEriEmpId());
                put("userName", empInfo.getEmpNm());
                put("deptCd", empInfo.getBlngBrcd());
                put("rankCd", empInfo.getJbttCd());
                put("message", "로그인 상태입니다.");
            }});
        }
    }

    /**
     * 직원 로그인
     * POST /api/auth/login
     */
    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> login(@RequestBody Map<String, String> request, HttpSession session) {
        String empNo = request.get("origEmpNo");
        log.info("직원 로그인 시도: {}", empNo);
        
        try {
            // 직원 정보 조회 (employee 패키지의 EmpLstVO)
            com.ERI.demo.vo.employee.EmpLstVO employeeEmpInfo = empLstService.getEmployeeById(empNo);
            
            if (employeeEmpInfo == null) {
                log.warn("존재하지 않는 직원번호: {}", empNo);
                return ResponseEntity.ok(new java.util.HashMap<String, Object>() {{
                    put("success", false);
                    put("message", "존재하지 않는 직원번호입니다.");
                }});
            }
            
            // 세션에 직원 정보 저장
            session.setAttribute("EMP_INFO", employeeEmpInfo);
            session.setAttribute("EMP_ID", employeeEmpInfo.getEriEmpId());
            
            // 관리자 여부 확인 및 세션에 저장
            boolean isAdmin = checkAdminStatus(employeeEmpInfo.getEriEmpId());
            session.setAttribute("isAdmin", isAdmin);
            
            // 상담사 여부 확인 및 세션에 저장
            boolean isCounselor = checkCounselorStatus(employeeEmpInfo.getEriEmpId());
            session.setAttribute("isCounselor", isCounselor);
            
            // 관리자 여부 로그 출력
            log.info("로그인 사용자 권한 정보: {} ({}) - 관리자: {}, 상담사: {}", 
                    employeeEmpInfo.getEmpNm(), employeeEmpInfo.getEriEmpId(), isAdmin, isCounselor);
            
            // 응답 데이터 생성
            Map<String, Object> response = new java.util.HashMap<>();
            response.put("success", true);
            response.put("message", "로그인 성공");
            response.put("sessionId", session.getId());
            response.put("empInfo", employeeEmpInfo);
            response.put("userAuth", new java.util.HashMap<String, Object>() {{
                put("userId", employeeEmpInfo.getEriEmpId());
                put("userName", employeeEmpInfo.getEmpNm());
                put("deptCd", employeeEmpInfo.getBlngBrcd());
                put("rankCd", employeeEmpInfo.getJbttCd());
                put("admin", isAdmin);
                put("superAdmin", isAdmin); // 현재는 관리자면 슈퍼관리자로 처리
                put("counselor", isCounselor);
            }});
            response.put("isAdmin", isAdmin);
            response.put("isCounselor", isCounselor);
            
            log.info("직원 로그인 성공: {} ({})", employeeEmpInfo.getEmpNm(), employeeEmpInfo.getEriEmpId());
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            log.error("로그인 처리 중 오류 발생: {}", e.getMessage(), e);
            return ResponseEntity.ok(new java.util.HashMap<String, Object>() {{
                put("success", false);
                put("message", "로그인 처리 중 오류가 발생했습니다.");
            }});
        }
    }

    /**
     * 직원의 관리자 여부 확인
     * @param eriEmpId ERI 직원ID
     * @return 관리자 여부
     */
    private boolean checkAdminStatus(String eriEmpId) {
        try {
            // TB_ADMIN_LST 테이블에서 관리자 정보 조회
            int adminCount = adminLstMapper.countByAdminId(eriEmpId);
            boolean isAdmin = adminCount > 0;
            
            log.debug("관리자 여부 확인: {} -> {}", eriEmpId, isAdmin);
            return isAdmin;
            
        } catch (Exception e) {
            log.error("관리자 여부 확인 중 오류 발생: {}, 오류: {}", eriEmpId, e.getMessage(), e);
            return false; // 오류 발생 시 관리자가 아닌 것으로 처리
        }
    }

    /**
     * 직원의 상담사 여부 확인
     * @param eriEmpId ERI 직원ID
     * @return 상담사 여부
     */
    private boolean checkCounselorStatus(String eriEmpId) {
        try {
            // TB_COUNSELOR_LST 테이블에서 상담사 정보 조회
            int counselorCount = counselorLstMapper.countByCounselorEmpId(eriEmpId);
            boolean isCounselor = counselorCount > 0;
            
            log.debug("상담사 여부 확인: {} -> {}", eriEmpId, isCounselor);
            return isCounselor;
            
        } catch (Exception e) {
            log.error("상담사 여부 확인 중 오류 발생: {}, 오류: {}", eriEmpId, e.getMessage(), e);
            return false; // 오류 발생 시 상담사가 아닌 것으로 처리
        }
    }
} 