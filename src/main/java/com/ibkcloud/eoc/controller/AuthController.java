package com.ibkcloud.eoc.controller;

import com.ibkcloud.eoc.service.EmpLstService;
import com.ibkcloud.eoc.dao.AdminLstDao;
import com.ibkcloud.eoc.dao.CounselorLstDao;
import com.ibkcloud.eoc.controller.vo.LoginInVo;
import com.ibkcloud.eoc.controller.vo.LoginOutVo;
import com.ibkcloud.eoc.controller.vo.CurrentUserOutVo;
import com.ibkcloud.eoc.cmm.exception.BizException;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

/**
 * @파일명 : AuthController
 * @논리명 : 인증 Controller
 * @작성자 : 시스템
 * @설명   : 인증 관련 기능을 처리하는 Controller 클래스
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {
    
    private final EmpLstService empLstService;
    private final AdminLstDao adminLstDao;
    private final CounselorLstDao counselorLstDao;
    
    /**
     * @메서드명 : logout
     * @논리명 : 로그아웃
     * @설명 : 사용자 로그아웃 처리
     * @param : session - HTTP 세션
     * @return : ResponseEntity<String> - 로그아웃 결과
     */
    @PostMapping(value = "/logout", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> logout(HttpSession session) {
        log.info("로그아웃 요청 처리 시작");
        
        try {
            // 세션에서 사용자 정보 조회
            Object empInfo = session.getAttribute("EMP_INFO");
            if (empInfo != null) {
                log.info("사용자 로그아웃 처리");
            }
            
            // 세션 무효화
            session.invalidate();
            
            log.info("로그아웃 처리 완료");
            return ResponseEntity.ok("로그아웃되었습니다.");
            
        } catch (Exception e) {
            log.error("로그아웃 처리 중 오류 발생", e);
            throw new BizException("COE00000");
        }
    }
    
    /**
     * @메서드명 : inqCurrentUser
     * @논리명 : 현재 사용자 정보 조회
     * @설명 : 현재 로그인한 사용자 정보를 조회
     * @param : session - HTTP 세션
     * @return : ResponseEntity<CurrentUserOutVo> - 현재 사용자 정보
     */
    @GetMapping(value = "/current-user", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CurrentUserOutVo> inqCurrentUser(HttpSession session) {
        log.info("현재 사용자 정보 조회 요청");
        
        try {
            // 세션에서 사용자 정보 조회
            Object empInfo = session.getAttribute("EMP_INFO");
            if (empInfo == null) {
                log.debug("인증되지 않은 사용자가 현재 사용자 정보 요청");
                return ResponseEntity.status(401).build();
            }
            
            // 사용자 정보를 OutVo로 변환
            CurrentUserOutVo outVo = new CurrentUserOutVo();
            // TODO: empInfo를 outVo로 매핑하는 로직 구현
            
            log.info("현재 사용자 정보 조회 완료");
            return ResponseEntity.ok(outVo);
            
        } catch (Exception e) {
            log.error("현재 사용자 정보 조회 중 오류 발생", e);
            throw new BizException("COE00000");
        }
    }
    
    /**
     * @메서드명 : vrfcAuth
     * @논리명 : 권한 검증
     * @설명 : 사용자 권한을 검증
     * @param : session - HTTP 세션
     * @return : ResponseEntity<String> - 권한 검증 결과
     */
    @GetMapping(value = "/check-auth", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> vrfcAuth(HttpSession session) {
        log.info("권한 검증 요청");
        
        try {
            // 세션에서 사용자 정보 조회
            Object empInfo = session.getAttribute("EMP_INFO");
            if (empInfo == null) {
                log.debug("인증되지 않은 사용자가 권한 체크 요청");
                return ResponseEntity.status(401).body("로그인이 필요합니다.");
            }
            
            log.debug("권한 체크 요청 처리");
            
            StringBuilder result = new StringBuilder();
            result.append("사용자 권한 검증 완료\n");
            
            return ResponseEntity.ok(result.toString());
            
        } catch (Exception e) {
            log.error("권한 검증 중 오류 발생", e);
            throw new BizException("COE00000");
        }
    }
    
    /**
     * @메서드명 : inqSessionStatus
     * @논리명 : 세션 상태 조회
     * @설명 : 현재 세션의 로그인 상태를 조회
     * @param : session - HTTP 세션
     * @return : ResponseEntity<Map<String, Object>> - 세션 상태
     */
    @GetMapping(value = "/session-status", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> inqSessionStatus(HttpSession session) {
        log.info("세션 상태 조회 요청");
        
        try {
            // 세션에서 사용자 정보 조회
            Object empInfo = session.getAttribute("EMP_INFO");
            
            Map<String, Object> response = new java.util.HashMap<>();
            if (empInfo == null) {
                response.put("loggedIn", false);
                response.put("message", "로그인되지 않음");
            } else {
                response.put("loggedIn", true);
                response.put("message", "로그인됨");
            }
            
            log.info("세션 상태 조회 완료");
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            log.error("세션 상태 조회 중 오류 발생", e);
            throw new BizException("COE00000");
        }
    }
    
    /**
     * @메서드명 : rgsnLogin
     * @논리명 : 로그인
     * @설명 : 사용자 로그인 처리
     * @param : inVo - 로그인 요청 데이터
     * @param : session - HTTP 세션
     * @return : ResponseEntity<LoginOutVo> - 로그인 결과
     */
    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LoginOutVo> rgsnLogin(@Valid @RequestBody LoginInVo inVo, HttpSession session) {
        log.info("로그인 요청 처리 시작 - 사용자ID: {}", inVo.getUserId());
        
        try {
            // 로그인 처리 로직
            LoginOutVo outVo = new LoginOutVo();
            // TODO: 실제 로그인 처리 로직 구현
            
            // 세션에 사용자 정보 저장
            session.setAttribute("EMP_INFO", outVo);
            
            log.info("로그인 처리 완료 - 사용자ID: {}", inVo.getUserId());
            return ResponseEntity.ok(outVo);
            
        } catch (Exception e) {
            log.error("로그인 처리 중 오류 발생", e);
            throw new BizException("COE00000");
        }
    }
} 