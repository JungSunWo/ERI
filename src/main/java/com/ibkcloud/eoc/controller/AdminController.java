package com.ibkcloud.eoc.controller;

import com.ibkcloud.eoc.cmm.dto.PageRequest;
import com.ibkcloud.eoc.cmm.dto.Page;
import com.ibkcloud.eoc.cmm.exception.BizException;
import com.ibkcloud.eoc.service.AdminService;
import com.ibkcloud.eoc.controller.vo.AdminLstInVo;
import com.ibkcloud.eoc.controller.vo.AdminLstOutVo;
import com.ibkcloud.eoc.controller.vo.AdminLstSearchInVo;
import com.ibkcloud.eoc.controller.vo.AdminLstSearchOutVo;
import com.ibkcloud.eoc.controller.vo.EmpLstSearchOutVo;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<AdminLstSearchOutVo> inqAdminList(
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "1") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false) String sortDirection) {
        
        try {
            // PageRequest 생성
            PageRequest pageRequest = new PageRequest();
            pageRequest.setPageNo(pageNo);
            pageRequest.setPageSize(pageSize);
            pageRequest.setParam(keyword);
            
            Page<AdminLstOutVo> result = adminService.inqAdminList(pageRequest);
            
            AdminLstSearchOutVo response = new AdminLstSearchOutVo();
            response.setSuccess(true);
            response.setData(result.getContents());
            response.setCount(result.getTtalDataNbi());
            response.setPageNo(result.getPageNo());
            response.setPageSize(result.getPageSize());
            response.setTtalPageNbi(result.getTtalPageNbi());
            response.setKeyword(keyword);
            response.setSortBy(sortBy);
            response.setSortDirection(sortDirection);
            response.setMessage("관리자 목록을 조회했습니다.");
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            log.error("관리자 목록 조회 실패", e);
            throw new BizException("A001", "관리자 목록 조회에 실패했습니다: " + e.getMessage());
        }
    }

    /**
     * 직원 목록 조회 (관리자 등록용)
     */
    @GetMapping("/employee/list")
    public ResponseEntity<EmpLstSearchOutVo> inqEmployeeList(
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "1") int pageNo,
            @RequestParam(defaultValue = "50") int pageSize,
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false) String sortDirection) {
        
        try {
            // PageRequest 생성
            PageRequest pageRequest = new PageRequest();
            pageRequest.setPageNo(pageNo);
            pageRequest.setPageSize(pageSize);
            pageRequest.setParam(keyword);
            
            Page<AdminLstOutVo> result = adminService.inqEmployeeList(pageRequest);
            
            EmpLstSearchOutVo response = new EmpLstSearchOutVo();
            response.setSuccess(true);
            response.setData(result.getContents().stream()
                    .map(admin -> {
                        // AdminLstOutVo를 EmpLstOutVo로 변환하는 로직 필요
                        return new EmpLstOutVo();
                    })
                    .toList());
            response.setCount(result.getTtalDataNbi());
            response.setSearchKeyword(keyword);
            response.setMessage("직원 목록을 조회했습니다.");
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            log.error("직원 목록 조회 실패", e);
            throw new BizException("A002", "직원 목록 조회에 실패했습니다: " + e.getMessage());
        }
    }

    /**
     * 관리자 등록
     */
    @PostMapping("/register")
    public ResponseEntity<AdminLstOutVo> rgsnAdmin(@RequestBody AdminLstInVo adminData, HttpServletRequest request) {
        try {
            // AuthInterceptor에서 전달받은 사용자 정보 사용
            String sessionEmpId = (String) request.getAttribute("EMP_ID");
            
            if (sessionEmpId == null) {
                throw new BizException("A003", "인증 정보를 찾을 수 없습니다.");
            }
            
            // 등록자 ID 설정 (AuthInterceptor에서 전달받은 EMP_ID 사용)
            adminData.setRegEmpId(sessionEmpId);
            
            log.info("관리자 등록 요청: empId={}, adminLevel={}, regEmpId={}", 
                    adminData.getEmpId(), adminData.getAdminLevel(), sessionEmpId);
            
            AdminLstOutVo result = adminService.rgsnAdmin(adminData);
            
            result.setSuccess(true);
            result.setMessage("관리자가 성공적으로 등록되었습니다.");
            
            return ResponseEntity.ok(result);
            
        } catch (BizException e) {
            throw e;
        } catch (Exception e) {
            log.error("관리자 등록 실패", e);
            throw new BizException("A004", "관리자 등록에 실패했습니다: " + e.getMessage());
        }
    }

    /**
     * 관리자 수정
     */
    @PutMapping("/{adminId}")
    public ResponseEntity<AdminLstOutVo> mdfcAdmin(
            @PathVariable String adminId, 
            @RequestBody AdminLstInVo adminData, 
            HttpServletRequest request) {
        
        try {
            // AuthInterceptor에서 전달받은 사용자 정보 사용
            String sessionEmpId = (String) request.getAttribute("EMP_ID");
            
            if (sessionEmpId == null) {
                throw new BizException("A003", "인증 정보를 찾을 수 없습니다.");
            }
            
            // 수정자 ID 설정 (AuthInterceptor에서 전달받은 EMP_ID 사용)
            adminData.setUpdEmpId(sessionEmpId);
            adminData.setAdminId(adminId);
            
            log.info("관리자 수정 요청: adminId={}, adminLevel={}, updEmpId={}", 
                    adminId, adminData.getAdminLevel(), sessionEmpId);
            
            AdminLstOutVo result = adminService.mdfcAdmin(adminData);
            
            result.setSuccess(true);
            result.setMessage("관리자 정보가 성공적으로 수정되었습니다.");
            
            return ResponseEntity.ok(result);
            
        } catch (BizException e) {
            throw e;
        } catch (Exception e) {
            log.error("관리자 수정 실패", e);
            throw new BizException("A005", "관리자 수정에 실패했습니다: " + e.getMessage());
        }
    }

    /**
     * 관리자 삭제
     */
    @DeleteMapping("/{adminId}")
    public ResponseEntity<AdminLstOutVo> delAdmin(@PathVariable String adminId, HttpServletRequest request) {
        try {
            // AuthInterceptor에서 전달받은 사용자 정보 사용
            String sessionEmpId = (String) request.getAttribute("EMP_ID");
            
            if (sessionEmpId == null) {
                throw new BizException("A003", "인증 정보를 찾을 수 없습니다.");
            }
            
            log.info("관리자 삭제 요청: adminId={}, delEmpId={}", adminId, sessionEmpId);
            
            AdminLstOutVo result = adminService.delAdmin(adminId);
            
            result.setSuccess(true);
            result.setMessage("관리자가 성공적으로 삭제되었습니다.");
            
            return ResponseEntity.ok(result);
            
        } catch (BizException e) {
            throw e;
        } catch (Exception e) {
            log.error("관리자 삭제 실패", e);
            throw new BizException("A006", "관리자 삭제에 실패했습니다: " + e.getMessage());
        }
    }
} 