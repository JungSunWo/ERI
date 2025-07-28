package com.ERI.demo.service;

import com.ERI.demo.dto.PageRequestDto;
import com.ERI.demo.dto.PageResponseDto;
import com.ERI.demo.mappers.AdminLstMapper;
import com.ERI.demo.mappers.employee.EmpLstMapper;
import com.ERI.demo.vo.AdminLstVO;
import com.ERI.demo.vo.employee.EmpLstVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 관리자 서비스
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AdminService {

    private final AdminLstMapper adminLstMapper;
    private final EmpLstMapper empLstMapper;

    /**
     * 관리자 목록 조회
     */
    public PageResponseDto<AdminLstVO> getAdminList(PageRequestDto pageRequest) {
        try {
            // 검색 조건 설정
            String searchKeyword = pageRequest.getKeyword() != null && !pageRequest.getKeyword().trim().isEmpty() 
                ? pageRequest.getKeyword().trim() 
                : null;
            
            List<AdminLstVO> adminList = adminLstMapper.selectAdminLstWithPaging(
                searchKeyword,
                pageRequest.getOffset(), 
                pageRequest.getSize(),
                pageRequest.getSort()
            );
            int totalCount = adminLstMapper.countAdminLstWithSearch(searchKeyword);
            
            // PageResponseDto 생성
            return new PageResponseDto<>(adminList, totalCount, pageRequest.getPage(), pageRequest.getSize());
            
        } catch (Exception e) {
            log.error("관리자 목록 조회 실패", e);
            return PageResponseDto.empty(pageRequest.getPage(), pageRequest.getSize());
        }
    }

    /**
     * 직원 목록 조회 (관리자 등록용)
     */
    public PageResponseDto<EmpLstVO> getEmployeeList(PageRequestDto pageRequest) {
        try {
            // 검색 조건 설정
            String searchKeyword = pageRequest.getKeyword() != null && !pageRequest.getKeyword().trim().isEmpty() 
                ? pageRequest.getKeyword().trim() 
                : null;
            
            List<EmpLstVO> employeeList = empLstMapper.selectEmployeesWithPaging(
                searchKeyword, 
                pageRequest.getOffset(), 
                pageRequest.getSize(),
                pageRequest.getSort()
            );
            int totalCount = empLstMapper.countEmployees(searchKeyword);
            
            // PageResponseDto 생성
            return new PageResponseDto<>(employeeList, totalCount, pageRequest.getPage(), pageRequest.getSize());
            
        } catch (Exception e) {
            log.error("직원 목록 조회 실패", e);
            return PageResponseDto.empty(pageRequest.getPage(), pageRequest.getSize());
        }
    }

    /**
     * 관리자 등록
     */
    @Transactional
    public Map<String, Object> createAdmin(Map<String, Object> adminData) {
        Map<String, Object> result = new HashMap<>();
        
        try {
            String empId = (String) adminData.get("empId");
            String eriEmpId = (String) adminData.get("eriEmpId");
            String adminLevel = (String) adminData.get("adminLevel");
            String adminDesc = (String) adminData.get("adminDesc");
            String regEmpId = (String) adminData.get("regEmpId");
            
            // 필수 필드 검증
            if (empId == null || empId.trim().isEmpty()) {
                result.put("success", false);
                result.put("message", "직원 ID는 필수입니다.");
                return result;
            }
            
            if (adminLevel == null || adminLevel.trim().isEmpty()) {
                result.put("success", false);
                result.put("message", "관리자 레벨은 필수입니다.");
                return result;
            }
            
            // 직원 존재 여부 확인 (ERI직원번호로 조회)
            EmpLstVO employee = empLstMapper.selectEmployeeByEriId(empId);
            if (employee == null) {
                result.put("success", false);
                result.put("message", "존재하지 않는 직원입니다.");
                return result;
            }
            
            // 이미 관리자인지 확인
            int existingCount = adminLstMapper.countByAdminId(empId);
            if (existingCount > 0) {
                result.put("success", false);
                result.put("message", "이미 관리자로 등록된 직원입니다.");
                return result;
            }
            
            // 관리자 정보 생성
            AdminLstVO adminLst = new AdminLstVO();
            adminLst.setAdminId(empId);
            adminLst.setAdminStsCd(adminLevel); // ADMIN_STS_CD에 adminLevel 저장
            adminLst.setRegEmpId(regEmpId != null ? regEmpId : empId);
            adminLst.setUpdEmpId(regEmpId != null ? regEmpId : empId);
            adminLst.setRegDate(null); // 등록일자는 DB에서 자동 설정
            adminLst.setUpdDate(null); // 수정일자는 DB에서 자동 설정
            
            // 관리자 등록
            int insertResult = adminLstMapper.insertAdminLst(adminLst);
            
            if (insertResult > 0) {
                log.info("관리자 등록 성공: empId={}, adminLevel={}", empId, adminLevel);
                result.put("success", true);
                result.put("message", "관리자가 성공적으로 등록되었습니다.");
                result.put("adminId", empId);
            } else {
                result.put("success", false);
                result.put("message", "관리자 등록에 실패했습니다.");
            }
            
        } catch (Exception e) {
            log.error("관리자 등록 실패", e);
            result.put("success", false);
            result.put("message", "관리자 등록 중 오류가 발생했습니다: " + e.getMessage());
        }
        
        return result;
    }

    /**
     * 관리자 수정
     */
    @Transactional
    public Map<String, Object> updateAdmin(String adminId, Map<String, Object> adminData) {
        Map<String, Object> result = new HashMap<>();
        
        try {
            // 기존 관리자 정보 조회
            AdminLstVO existingAdmin = adminLstMapper.selectByAdminId(adminId);
            if (existingAdmin == null) {
                result.put("success", false);
                result.put("message", "존재하지 않는 관리자입니다.");
                return result;
            }
            
            String adminLevel = (String) adminData.get("adminLevel");
            String updEmpId = (String) adminData.get("updEmpId");
            
            // 관리자 정보 업데이트
            existingAdmin.setAdminStsCd(adminLevel);
            existingAdmin.setUpdEmpId(updEmpId != null ? updEmpId : adminId);
            
            int updateResult = adminLstMapper.updateAdminLst(existingAdmin);
            
            if (updateResult > 0) {
                log.info("관리자 수정 성공: adminId={}, adminLevel={}", adminId, adminLevel);
                result.put("success", true);
                result.put("message", "관리자 정보가 성공적으로 수정되었습니다.");
            } else {
                result.put("success", false);
                result.put("message", "관리자 수정에 실패했습니다.");
            }
            
        } catch (Exception e) {
            log.error("관리자 수정 실패", e);
            result.put("success", false);
            result.put("message", "관리자 수정 중 오류가 발생했습니다: " + e.getMessage());
        }
        
        return result;
    }

    /**
     * 관리자 삭제 (실제 삭제)
     */
    @Transactional
    public Map<String, Object> deleteAdmin(String adminId) {
        Map<String, Object> result = new HashMap<>();
        
        try {
            // 기존 관리자 정보 조회
            AdminLstVO existingAdmin = adminLstMapper.selectByAdminId(adminId);
            if (existingAdmin == null) {
                result.put("success", false);
                result.put("message", "존재하지 않는 관리자입니다.");
                return result;
            }
            
            // 실제 삭제
            int deleteResult = adminLstMapper.deleteAdminLstByAdminId(adminId);
            
            if (deleteResult > 0) {
                log.info("관리자 삭제 성공: adminId={}", adminId);
                result.put("success", true);
                result.put("message", "관리자가 성공적으로 삭제되었습니다.");
            } else {
                result.put("success", false);
                result.put("message", "관리자 삭제에 실패했습니다.");
            }
            
        } catch (Exception e) {
            log.error("관리자 삭제 실패", e);
            result.put("success", false);
            result.put("message", "관리자 삭제 중 오류가 발생했습니다: " + e.getMessage());
        }
        
        return result;
    }
} 