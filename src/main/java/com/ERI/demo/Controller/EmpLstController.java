package com.ERI.demo.Controller;

import com.ERI.demo.service.EmpLstService;
import com.ERI.demo.vo.employee.EmpLstVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 직원 정보 DB의 TB_EMP_LST 테이블 Controller
 * eri_employee_db 연결을 사용
 */
@RestController
@RequestMapping("/api/employee")
public class EmpLstController {

    @Autowired
    private EmpLstService empLstService;

    /**
     * 전체 직원 목록 조회
     */
    @GetMapping("/list")
    public ResponseEntity<Map<String, Object>> getAllEmployees() {
        try {
            List<EmpLstVO> employees = empLstService.getAllEmployees();
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", employees);
            response.put("count", employees.size());
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "직원 목록 조회 실패: " + e.getMessage());
            
            return ResponseEntity.internalServerError().body(response);
        }
    }

    /**
     * ERI_EMP_ID로 직원 정보 조회
     */
    @GetMapping("/{eriEmpId}")
    public ResponseEntity<Map<String, Object>> getEmployeeByEriId(@PathVariable String eriEmpId) {
        try {
            EmpLstVO employee = empLstService.getEmployeeByEriId(eriEmpId);
            
            Map<String, Object> response = new HashMap<>();
            if (employee != null) {
                response.put("success", true);
                response.put("data", employee);
            } else {
                response.put("success", false);
                response.put("message", "직원을 찾을 수 없습니다: " + eriEmpId);
            }
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "직원 정보 조회 실패: " + e.getMessage());
            
            return ResponseEntity.internalServerError().body(response);
        }
    }

    /**
     * EMP_ID로 직원 정보 조회
     */
    @GetMapping("/by-emp-id/{empId}")
    public ResponseEntity<Map<String, Object>> getEmployeeByEmpId(@PathVariable String empId) {
        try {
            EmpLstVO employee = empLstService.getEmployeeByEmpId(empId);
            
            Map<String, Object> response = new HashMap<>();
            if (employee != null) {
                response.put("success", true);
                response.put("data", employee);
            } else {
                response.put("success", false);
                response.put("message", "직원을 찾을 수 없습니다: " + empId);
            }
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "직원 정보 조회 실패: " + e.getMessage());
            
            return ResponseEntity.internalServerError().body(response);
        }
    }

    /**
     * 부점별 직원 목록 조회
     */
    @GetMapping("/branch/{branchCd}")
    public ResponseEntity<Map<String, Object>> getEmployeesByBranch(@PathVariable String branchCd) {
        try {
            List<EmpLstVO> employees = empLstService.getEmployeesByBranch(branchCd);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", employees);
            response.put("count", employees.size());
            response.put("branchCd", branchCd);
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "부점별 직원 목록 조회 실패: " + e.getMessage());
            
            return ResponseEntity.internalServerError().body(response);
        }
    }

    /**
     * 직원 정보 등록
     */
    @PostMapping
    public ResponseEntity<Map<String, Object>> insertEmployee(@RequestBody EmpLstVO employee) {
        try {
            int result = empLstService.insertEmployee(employee);
            
            Map<String, Object> response = new HashMap<>();
            if (result > 0) {
                response.put("success", true);
                response.put("message", "직원 정보가 성공적으로 등록되었습니다.");
                response.put("data", employee);
            } else {
                response.put("success", false);
                response.put("message", "직원 정보 등록에 실패했습니다.");
            }
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "직원 정보 등록 실패: " + e.getMessage());
            
            return ResponseEntity.internalServerError().body(response);
        }
    }

    /**
     * 직원 정보 수정
     */
    @PutMapping("/{eriEmpId}")
    public ResponseEntity<Map<String, Object>> updateEmployee(@PathVariable String eriEmpId, @RequestBody EmpLstVO employee) {
        try {
            employee.setEriEmpId(eriEmpId);
            int result = empLstService.updateEmployee(employee);
            
            Map<String, Object> response = new HashMap<>();
            if (result > 0) {
                response.put("success", true);
                response.put("message", "직원 정보가 성공적으로 수정되었습니다.");
                response.put("data", employee);
            } else {
                response.put("success", false);
                response.put("message", "직원 정보 수정에 실패했습니다.");
            }
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "직원 정보 수정 실패: " + e.getMessage());
            
            return ResponseEntity.internalServerError().body(response);
        }
    }

    /**
     * 직원 정보 삭제 (논리 삭제)
     */
    @DeleteMapping("/{eriEmpId}")
    public ResponseEntity<Map<String, Object>> deleteEmployee(@PathVariable String eriEmpId) {
        try {
            int result = empLstService.deleteEmployee(eriEmpId);
            
            Map<String, Object> response = new HashMap<>();
            if (result > 0) {
                response.put("success", true);
                response.put("message", "직원 정보가 성공적으로 삭제되었습니다.");
            } else {
                response.put("success", false);
                response.put("message", "직원 정보 삭제에 실패했습니다.");
            }
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "직원 정보 삭제 실패: " + e.getMessage());
            
            return ResponseEntity.internalServerError().body(response);
        }
    }

    /**
     * 직원 수 조회
     */
    @GetMapping("/count")
    public ResponseEntity<Map<String, Object>> getEmployeeCount() {
        try {
            int count = empLstService.getEmployeeCount();
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("count", count);
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "직원 수 조회 실패: " + e.getMessage());
            
            return ResponseEntity.internalServerError().body(response);
        }
    }

    /**
     * 조건별 직원 검색
     */
    @GetMapping("/search")
    public ResponseEntity<Map<String, Object>> searchEmployees(
            @RequestParam(required = false) String searchKeyword,
            @RequestParam(required = false) String branchCd,
            @RequestParam(required = false) String jbclCd,
            @RequestParam(required = false) String jbttCd,
            @RequestParam(required = false) String hlofYn) {
        try {
            List<EmpLstVO> employees = empLstService.searchEmployees(searchKeyword, branchCd, jbclCd, jbttCd, hlofYn);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", employees);
            response.put("count", employees.size());
            response.put("searchKeyword", searchKeyword);
            response.put("branchCd", branchCd);
            response.put("jbclCd", jbclCd);
            response.put("jbttCd", jbttCd);
            response.put("hlofYn", hlofYn);
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "직원 검색 실패: " + e.getMessage());
            
            return ResponseEntity.internalServerError().body(response);
        }
    }

    /**
     * 직원 정보 DB 연결 테스트
     */
    @GetMapping("/test-connection")
    public ResponseEntity<Map<String, Object>> testConnection() {
        try {
            boolean isConnected = empLstService.testEmployeeDbConnection();
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("connected", isConnected);
            response.put("message", isConnected ? "데이터베이스 연결 성공" : "데이터베이스 연결 실패");
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("connected", false);
            response.put("message", "데이터베이스 연결 테스트 실패: " + e.getMessage());
            
            return ResponseEntity.internalServerError().body(response);
        }
    }
} 