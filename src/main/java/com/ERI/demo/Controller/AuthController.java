package com.ERI.demo.Controller;

import com.ERI.demo.dto.LoginRequestDto;
import com.ERI.demo.dto.LoginResponseDto;
import com.ERI.demo.dto.UserAuthDto;
import com.ERI.demo.service.AuthService;
import com.ERI.demo.service.EmpLstService;
import com.ERI.demo.vo.EmpLstVO;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 인증 관련 컨트롤러
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {
	
    
    @Autowired
    private AuthService authService;
    
    @Autowired
    private EmpLstService empLstService;
    
    private final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());

    /**
     * 로그인
     * POST /api/auth/login
     */
    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequest, HttpSession session) {
        log.info("로그인 시도: 사용자 ID = {}", loginRequest.getUserId());
        
        try {
        	
        	log.debug("loginRequest: {}", loginRequest);
            
            // 실제 구현에서는 비밀번호 검증 로직이 필요합니다.
            // 여기서는 간단히 사용자 존재 여부만 확인합니다.
            UserAuthDto userAuth = authService.getUserAuth(loginRequest.getUserId());
            
            if (userAuth == null) {
                log.warn("존재하지 않는 사용자 로그인 시도: {}", loginRequest.getUserId());
                return ResponseEntity.ok(LoginResponseDto.failure("존재하지 않는 사용자입니다."));
            }
            
            // 세션에 사용자 권한 정보 저장
            session.setAttribute("USER_AUTH", userAuth);
            log.info("사용자 로그인 성공: {} ({})", userAuth.getUserName(), userAuth.getUserId());
            
            // 전체 직원 목록 조회 (ID와 이름만)
            List<EmpLstVO> allEmployees = empLstService.getAllEmployeesForCache();
            Map<String, String> employeeCache = allEmployees.stream()
                .collect(Collectors.toMap(
                    EmpLstVO::getEmpId,
                    EmpLstVO::getEmpNm,
                    (existing, replacement) -> existing // 중복 키가 있을 경우 기존 값 유지
                ));
            
            // 로그인 응답 생성 (직원 캐시 포함)
            LoginResponseDto response = LoginResponseDto.success(
                userAuth.getUserId(),
                userAuth.getUserName(),
                userAuth.getAuthGroupCodes(),
                employeeCache
            );
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            log.error("로그인 처리 중 오류 발생: {}", e.getMessage(), e);
            return ResponseEntity.ok(LoginResponseDto.failure("로그인 처리 중 오류가 발생했습니다."));
        }
    }
    
    /**
     * 로그아웃
     * POST /api/auth/logout
     */
    @PostMapping(value = "/logout", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> logout(HttpSession session) {
        UserAuthDto userAuth = (UserAuthDto) session.getAttribute("USER_AUTH");
        if (userAuth != null) {
            log.info("사용자 로그아웃: {} ({})", userAuth.getUserName(), userAuth.getUserId());
        }
        
        session.invalidate();
        return ResponseEntity.ok("로그아웃되었습니다.");
    }
    
    /**
     * 현재 로그인 사용자 정보 조회
     * GET /api/auth/current-user
     */
    @GetMapping(value = "/current-user", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserAuthDto> getCurrentUser(HttpSession session) {
        UserAuthDto userAuth = (UserAuthDto) session.getAttribute("USER_AUTH");
        if (userAuth == null) {
            log.debug("인증되지 않은 사용자가 현재 사용자 정보 요청");
            return ResponseEntity.status(401).build();
        }
        
        log.debug("현재 사용자 정보 조회: {} ({})", userAuth.getUserName(), userAuth.getUserId());
        return ResponseEntity.ok(userAuth);
    }
    
    /**
     * 권한 체크 테스트
     * GET /api/auth/check-auth
     */
    @GetMapping(value = "/check-auth", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> checkAuth(HttpSession session) {
        UserAuthDto userAuth = (UserAuthDto) session.getAttribute("USER_AUTH");
        if (userAuth == null) {
            log.debug("인증되지 않은 사용자가 권한 체크 요청");
            return ResponseEntity.status(401).body("로그인이 필요합니다.");
        }
        
        log.debug("권한 체크 요청: {} ({})", userAuth.getUserName(), userAuth.getUserId());
        
        StringBuilder result = new StringBuilder();
        result.append("사용자: ").append(userAuth.getUserName()).append("\n");
        result.append("권한 그룹: ").append(userAuth.getAuthGroupCodes()).append("\n");
        result.append("관리자 여부: ").append(userAuth.isAdmin()).append("\n");
        result.append("슈퍼 관리자 여부: ").append(userAuth.isSuperAdmin()).append("\n");
        
        return ResponseEntity.ok(result.toString());
    }

    /**
     * 세션 상태(로그인 여부) 확인
     * GET /api/auth/session-status
     */
    @GetMapping(value = "/session-status", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> sessionStatus(HttpSession session) {
        UserAuthDto userAuth = (UserAuthDto) session.getAttribute("USER_AUTH");
        if (userAuth == null) {
            return ResponseEntity.ok(new java.util.HashMap<String, Object>() {{
                put("loggedIn", false);
                put("message", "로그인되어 있지 않습니다.");
            }});
        } else {
            return ResponseEntity.ok(new java.util.HashMap<String, Object>() {{
                put("loggedIn", true);
                put("userId", userAuth.getUserId());
                put("userName", userAuth.getUserName());
                put("authGroups", userAuth.getAuthGroupCodes());
                put("message", "로그인 상태입니다.");
            }});
        }
    }

    /**
     * 전체 직원 목록 조회 (캐싱용)
     * GET /api/auth/employee-cache
     */
    @GetMapping(value = "/employee-cache", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, String>> getEmployeeCache(HttpSession session) {
        UserAuthDto userAuth = (UserAuthDto) session.getAttribute("USER_AUTH");
        if (userAuth == null) {
            log.debug("인증되지 않은 사용자가 직원 캐시 요청");
            return ResponseEntity.status(401).build();
        }
        
        try {
            List<EmpLstVO> allEmployees = empLstService.getAllEmployeesForCache();
            Map<String, String> employeeCache = allEmployees.stream()
                .collect(Collectors.toMap(
                    EmpLstVO::getEmpId,
                    EmpLstVO::getEmpNm,
                    (existing, replacement) -> existing
                ));
            
            log.debug("직원 캐시 조회: {} 명의 직원 정보", employeeCache.size());
            return ResponseEntity.ok(employeeCache);
            
        } catch (Exception e) {
            log.error("직원 캐시 조회 중 오류 발생: {}", e.getMessage(), e);
            return ResponseEntity.status(500).build();
        }
    }
} 