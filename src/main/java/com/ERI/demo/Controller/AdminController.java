package com.ERI.demo.Controller;

import com.ERI.demo.dto.PageRequestDto;
import com.ERI.demo.dto.PageResponseDto;
import com.ERI.demo.service.AdminService;
import com.ERI.demo.vo.AdminLstVO;
import com.ERI.demo.vo.employee.EmpLstVO;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 관리자 API 컨트롤러
 */
@Slf4j
@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    /**
     * 관리자 목록 조회
     */
    @GetMapping("/list")
    public ResponseEntity<Map<String, Object>> getAdminList(
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false) String sortDirection) {
        
        Map<String, Object> response = new HashMap<>();
        
        try {
            // PageRequestDto 생성
            PageRequestDto pageRequest = PageRequestDto.builder()
                    .page(page)
                    .size(size)
                    .keyword(keyword)
                    .sortBy(sortBy)
                    .sortDirection(sortDirection)
                    .build();
            
            PageResponseDto<AdminLstVO> result = adminService.getAdminList(pageRequest);
            
            response.put("success", true);
            response.put("data", result);
            response.put("message", "관리자 목록을 조회했습니다.");
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            log.error("관리자 목록 조회 실패", e);
            
            response.put("success", false);
            response.put("message", "관리자 목록 조회에 실패했습니다: " + e.getMessage());
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * 직원 목록 조회 (관리자 등록용)
     */
    @GetMapping("/employee/list")
    public ResponseEntity<Map<String, Object>> getEmployeeList(
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "50") int size,
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false) String sortDirection) {
        
        Map<String, Object> response = new HashMap<>();
        
        try {
            // PageRequestDto 생성
            PageRequestDto pageRequest = PageRequestDto.builder()
                    .page(page)
                    .size(size)
                    .keyword(keyword)
                    .sortBy(sortBy)
                    .sortDirection(sortDirection)
                    .build();
            
            PageResponseDto<EmpLstVO> result = adminService.getEmployeeList(pageRequest);
            
            response.put("success", true);
            response.put("data", result);
            response.put("message", "직원 목록을 조회했습니다.");
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            log.error("직원 목록 조회 실패", e);
            
            response.put("success", false);
            response.put("message", "직원 목록 조회에 실패했습니다: " + e.getMessage());
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * 관리자 등록
     */
    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> createAdmin(@RequestBody Map<String, Object> adminData, HttpServletRequest request) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            // AuthInterceptor에서 전달받은 사용자 정보 사용
            String sessionEmpId = (String) request.getAttribute("EMP_ID");
            
            if (sessionEmpId == null) {
                response.put("success", false);
                response.put("message", "인증 정보를 찾을 수 없습니다.");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }
            
            // 등록자 ID 설정 (AuthInterceptor에서 전달받은 EMP_ID 사용)
            adminData.put("regEmpId", sessionEmpId);
            
            log.info("관리자 등록 요청: empId={}, adminLevel={}, regEmpId={}", 
                    adminData.get("empId"), adminData.get("adminLevel"), sessionEmpId);
            
            Map<String, Object> result = adminService.createAdmin(adminData);
            
            response.put("success", result.get("success"));
            response.put("data", result.get("adminId"));
            response.put("message", result.get("message"));
            
            if ((Boolean) result.get("success")) {
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.badRequest().body(response);
            }
            
        } catch (Exception e) {
            log.error("관리자 등록 실패", e);
            
            response.put("success", false);
            response.put("message", "관리자 등록에 실패했습니다: " + e.getMessage());
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * 관리자 수정
     */
    @PutMapping("/{adminId}")
    public ResponseEntity<Map<String, Object>> updateAdmin(
            @PathVariable String adminId, 
            @RequestBody Map<String, Object> adminData, 
            HttpServletRequest request) {
        
        Map<String, Object> response = new HashMap<>();
        
        try {
            // AuthInterceptor에서 전달받은 사용자 정보 사용
            String sessionEmpId = (String) request.getAttribute("EMP_ID");
            
            if (sessionEmpId == null) {
                response.put("success", false);
                response.put("message", "인증 정보를 찾을 수 없습니다.");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }
            
            // 수정자 ID 설정 (AuthInterceptor에서 전달받은 EMP_ID 사용)
            adminData.put("updEmpId", sessionEmpId);
            
            log.info("관리자 수정 요청: adminId={}, adminLevel={}, updEmpId={}", 
                    adminId, adminData.get("adminLevel"), sessionEmpId);
            
            Map<String, Object> result = adminService.updateAdmin(adminId, adminData);
            
            response.put("success", result.get("success"));
            response.put("message", result.get("message"));
            
            if ((Boolean) result.get("success")) {
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.badRequest().body(response);
            }
            
        } catch (Exception e) {
            log.error("관리자 수정 실패", e);
            
            response.put("success", false);
            response.put("message", "관리자 수정에 실패했습니다: " + e.getMessage());
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * 관리자 삭제
     */
    @DeleteMapping("/{adminId}")
    public ResponseEntity<Map<String, Object>> deleteAdmin(@PathVariable String adminId, HttpServletRequest request) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            // AuthInterceptor에서 전달받은 사용자 정보 사용
            String sessionEmpId = (String) request.getAttribute("EMP_ID");
            
            if (sessionEmpId == null) {
                response.put("success", false);
                response.put("message", "인증 정보를 찾을 수 없습니다.");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }
            
            log.info("관리자 삭제 요청: adminId={}, delEmpId={}", adminId, sessionEmpId);
            
            Map<String, Object> result = adminService.deleteAdmin(adminId);
            
            response.put("success", result.get("success"));
            response.put("message", result.get("message"));
            
            if ((Boolean) result.get("success")) {
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.badRequest().body(response);
            }
            
        } catch (Exception e) {
            log.error("관리자 삭제 실패", e);
            
            response.put("success", false);
            response.put("message", "관리자 삭제에 실패했습니다: " + e.getMessage());
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
} 