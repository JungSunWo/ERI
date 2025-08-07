package com.ibkcloud.eoc.controller;

import com.ibkcloud.eoc.cmm.exception.BizException;
import com.ibkcloud.eoc.service.EmpLstService;
import com.ibkcloud.eoc.controller.vo.EmpLstInVo;
import com.ibkcloud.eoc.controller.vo.EmpLstOutVo;
import com.ibkcloud.eoc.controller.vo.EmpLstSearchInVo;
import com.ibkcloud.eoc.controller.vo.EmpLstSearchOutVo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 직원 정보 관리 Controller
 * eri_employee_db 연결을 사용
 */
@RestController
@RequestMapping("/api/employee")
@RequiredArgsConstructor
public class EmpLstController {

    private final EmpLstService empLstService;

    /**
     * 전체 직원 목록 조회
     */
    @GetMapping("/list")
    public ResponseEntity<EmpLstSearchOutVo> inqAllEmployees() {
        try {
            List<EmpLstOutVo> employees = empLstService.inqAllEmployees();
            
            EmpLstSearchOutVo response = new EmpLstSearchOutVo();
            response.setSuccess(true);
            response.setData(employees);
            response.setCount(employees.size());
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            throw new BizException("E001", "직원 목록 조회 실패: " + e.getMessage());
        }
    }

    /**
     * ERI_EMP_ID로 직원 정보 조회
     */
    @GetMapping("/{eriEmpId}")
    public ResponseEntity<EmpLstOutVo> inqEmployeeByEriId(@PathVariable String eriEmpId) {
        try {
            EmpLstOutVo employee = empLstService.inqEmployeeByEriId(eriEmpId);
            
            if (employee == null) {
                throw new BizException("E002", "직원을 찾을 수 없습니다: " + eriEmpId);
            }
            
            employee.setSuccess(true);
            return ResponseEntity.ok(employee);
        } catch (BizException e) {
            throw e;
        } catch (Exception e) {
            throw new BizException("E003", "직원 정보 조회 실패: " + e.getMessage());
        }
    }

    /**
     * EMP_ID로 직원 정보 조회
     */
    @GetMapping("/by-emp-id/{empId}")
    public ResponseEntity<EmpLstOutVo> inqEmployeeByEmpId(@PathVariable String empId) {
        try {
            EmpLstOutVo employee = empLstService.inqEmployeeByEmpId(empId);
            
            if (employee == null) {
                throw new BizException("E002", "직원을 찾을 수 없습니다: " + empId);
            }
            
            employee.setSuccess(true);
            return ResponseEntity.ok(employee);
        } catch (BizException e) {
            throw e;
        } catch (Exception e) {
            throw new BizException("E003", "직원 정보 조회 실패: " + e.getMessage());
        }
    }

    /**
     * 부점별 직원 목록 조회
     */
    @GetMapping("/branch/{branchCd}")
    public ResponseEntity<EmpLstSearchOutVo> inqEmployeesByBranch(@PathVariable String branchCd) {
        try {
            List<EmpLstOutVo> employees = empLstService.inqEmployeesByBranch(branchCd);
            
            EmpLstSearchOutVo response = new EmpLstSearchOutVo();
            response.setSuccess(true);
            response.setData(employees);
            response.setCount(employees.size());
            response.setBranchCd(branchCd);
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            throw new BizException("E004", "부점별 직원 목록 조회 실패: " + e.getMessage());
        }
    }

    /**
     * 직원 정보 등록
     */
    @PostMapping
    public ResponseEntity<EmpLstOutVo> rgsnEmployee(@RequestBody EmpLstInVo employee) {
        try {
            EmpLstOutVo result = empLstService.rgsnEmployee(employee);
            
            result.setSuccess(true);
            result.setMessage("직원 정보가 성공적으로 등록되었습니다.");
            
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            throw new BizException("E005", "직원 정보 등록 실패: " + e.getMessage());
        }
    }

    /**
     * 직원 정보 수정
     */
    @PutMapping("/{eriEmpId}")
    public ResponseEntity<EmpLstOutVo> mdfcEmployee(@PathVariable String eriEmpId, @RequestBody EmpLstInVo employee) {
        try {
            employee.setEriEmpId(eriEmpId);
            EmpLstOutVo result = empLstService.mdfcEmployee(employee);
            
            result.setSuccess(true);
            result.setMessage("직원 정보가 성공적으로 수정되었습니다.");
            
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            throw new BizException("E006", "직원 정보 수정 실패: " + e.getMessage());
        }
    }

    /**
     * 직원 정보 삭제 (논리 삭제)
     */
    @DeleteMapping("/{eriEmpId}")
    public ResponseEntity<EmpLstOutVo> delEmployee(@PathVariable String eriEmpId) {
        try {
            EmpLstOutVo result = empLstService.delEmployee(eriEmpId);
            
            result.setSuccess(true);
            result.setMessage("직원 정보가 성공적으로 삭제되었습니다.");
            
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            throw new BizException("E007", "직원 정보 삭제 실패: " + e.getMessage());
        }
    }

    /**
     * 직원 수 조회
     */
    @GetMapping("/count")
    public ResponseEntity<EmpLstOutVo> inqEmployeeCount() {
        try {
            int count = empLstService.inqEmployeeCount();
            
            EmpLstOutVo response = new EmpLstOutVo();
            response.setSuccess(true);
            response.setCount(count);
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            throw new BizException("E008", "직원 수 조회 실패: " + e.getMessage());
        }
    }

    /**
     * 조건별 직원 검색
     */
    @GetMapping("/search")
    public ResponseEntity<EmpLstSearchOutVo> inqEmployeesByCondition(
            @RequestParam(required = false) String searchKeyword,
            @RequestParam(required = false) String branchCd,
            @RequestParam(required = false) String jbclCd,
            @RequestParam(required = false) String jbttCd,
            @RequestParam(required = false) String hlofYn) {
        try {
            EmpLstSearchInVo searchVo = new EmpLstSearchInVo();
            searchVo.setSearchKeyword(searchKeyword);
            searchVo.setBranchCd(branchCd);
            searchVo.setJbclCd(jbclCd);
            searchVo.setJbttCd(jbttCd);
            searchVo.setHlofYn(hlofYn);
            
            List<EmpLstOutVo> employees = empLstService.inqEmployeesByCondition(searchVo);
            
            EmpLstSearchOutVo response = new EmpLstSearchOutVo();
            response.setSuccess(true);
            response.setData(employees);
            response.setCount(employees.size());
            response.setSearchKeyword(searchKeyword);
            response.setBranchCd(branchCd);
            response.setJbclCd(jbclCd);
            response.setJbttCd(jbttCd);
            response.setHlofYn(hlofYn);
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            throw new BizException("E009", "직원 검색 실패: " + e.getMessage());
        }
    }

    /**
     * 직원 정보 DB 연결 테스트
     */
    @GetMapping("/test-connection")
    public ResponseEntity<EmpLstOutVo> inqDbConnection() {
        try {
            boolean isConnected = empLstService.inqEmployeeDbConnection();
            
            EmpLstOutVo response = new EmpLstOutVo();
            response.setSuccess(true);
            response.setConnected(isConnected);
            response.setMessage(isConnected ? "데이터베이스 연결 성공" : "데이터베이스 연결 실패");
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            throw new BizException("E010", "데이터베이스 연결 테스트 실패: " + e.getMessage());
        }
    }
} 