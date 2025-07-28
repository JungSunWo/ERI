package com.ERI.demo.service;

import com.ERI.demo.dto.PageRequestDto;
import com.ERI.demo.dto.PageResponseDto;
import com.ERI.demo.mappers.CounselorLstMapper;
import com.ERI.demo.mappers.employee.EmpLstMapper;
import com.ERI.demo.vo.CounselorLstVO;
import com.ERI.demo.vo.employee.EmpLstVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 상담사 서비스
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CounselorService {

    private final CounselorLstMapper counselorLstMapper;
    private final EmpLstMapper empLstMapper;

    /**
     * 상담사 목록 조회
     */
    public PageResponseDto<CounselorLstVO> getCounselorList(PageRequestDto pageRequest) {
        try {
            // 검색 조건 설정
            String searchKeyword = pageRequest.getKeyword() != null && !pageRequest.getKeyword().trim().isEmpty() 
                ? pageRequest.getKeyword().trim() 
                : null;
            
            List<CounselorLstVO> counselorList = counselorLstMapper.selectCounselorLstWithPaging(
                searchKeyword,
                pageRequest.getOffset(), 
                pageRequest.getSize(),
                pageRequest.getSort()
            );
            int totalCount = counselorLstMapper.countCounselorLstWithSearch(searchKeyword);
            
            // PageResponseDto 생성
            return new PageResponseDto<>(counselorList, totalCount, pageRequest.getPage(), pageRequest.getSize());
            
        } catch (Exception e) {
            log.error("상담사 목록 조회 실패", e);
            return PageResponseDto.empty(pageRequest.getPage(), pageRequest.getSize());
        }
    }

    /**
     * 직원 목록 조회 (상담사 등록용)
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
     * 상담사 등록
     */
    @Transactional
    public Map<String, Object> createCounselor(Map<String, Object> counselorData) {
        Map<String, Object> result = new HashMap<>();
        
        try {
            String counselorEmpId = (String) counselorData.get("counselorEmpId");
            String eriEmpId = (String) counselorData.get("eriEmpId");
            String counselorInfoClsfCd = (String) counselorData.get("counselorInfoClsfCd");
            String regEmpId = (String) counselorData.get("regEmpId");
            
            // 필수 필드 검증
            if (counselorEmpId == null || counselorEmpId.trim().isEmpty()) {
                result.put("success", false);
                result.put("message", "상담사 직원 ID는 필수입니다.");
                return result;
            }
            
            if (eriEmpId == null || eriEmpId.trim().isEmpty()) {
                result.put("success", false);
                result.put("message", "ERI 직원 ID는 필수입니다.");
                return result;
            }
            
            // 이미 등록된 상담사인지 확인
            CounselorLstVO existingCounselor = counselorLstMapper.selectCounselorByEmpId(counselorEmpId);
            if (existingCounselor != null) {
                result.put("success", false);
                result.put("message", "이미 등록된 상담사입니다.");
                return result;
            }
            
            // 상담사 등록
            CounselorLstVO counselor = new CounselorLstVO();
            counselor.setCounselorEmpId(counselorEmpId);
            counselor.setCounselorInfoClsfCd(counselorInfoClsfCd);
            counselor.setRegEmpId(regEmpId);
            
            int insertResult = counselorLstMapper.insertCounselor(counselor);
            
            if (insertResult > 0) {
                result.put("success", true);
                result.put("message", "상담사가 성공적으로 등록되었습니다.");
            } else {
                result.put("success", false);
                result.put("message", "상담사 등록에 실패했습니다.");
            }
            
        } catch (Exception e) {
            log.error("상담사 등록 실패", e);
            result.put("success", false);
            result.put("message", "상담사 등록 중 오류가 발생했습니다.");
        }
        
        return result;
    }

    /**
     * 상담사 수정
     */
    @Transactional
    public Map<String, Object> updateCounselor(String counselorEmpId, Map<String, Object> counselorData) {
        Map<String, Object> result = new HashMap<>();
        
        try {
            String counselorInfoClsfCd = (String) counselorData.get("counselorInfoClsfCd");
            String updEmpId = (String) counselorData.get("updEmpId");
            
            // 상담사 존재 여부 확인
            CounselorLstVO existingCounselor = counselorLstMapper.selectCounselorByEmpId(counselorEmpId);
            if (existingCounselor == null) {
                result.put("success", false);
                result.put("message", "존재하지 않는 상담사입니다.");
                return result;
            }
            
            // 상담사 정보 업데이트
            CounselorLstVO counselor = new CounselorLstVO();
            counselor.setCounselorEmpId(counselorEmpId);
            counselor.setCounselorInfoClsfCd(counselorInfoClsfCd);
            counselor.setUpdEmpId(updEmpId);
            
            int updateResult = counselorLstMapper.updateCounselor(counselor);
            
            if (updateResult > 0) {
                result.put("success", true);
                result.put("message", "상담사 정보가 성공적으로 수정되었습니다.");
            } else {
                result.put("success", false);
                result.put("message", "상담사 정보 수정에 실패했습니다.");
            }
            
        } catch (Exception e) {
            log.error("상담사 수정 실패", e);
            result.put("success", false);
            result.put("message", "상담사 수정 중 오류가 발생했습니다.");
        }
        
        return result;
    }

    /**
     * 상담사 삭제
     */
    @Transactional
    public Map<String, Object> deleteCounselor(String counselorEmpId) {
        Map<String, Object> result = new HashMap<>();
        
        try {
            // 상담사 존재 여부 확인
            CounselorLstVO existingCounselor = counselorLstMapper.selectCounselorByEmpId(counselorEmpId);
            if (existingCounselor == null) {
                result.put("success", false);
                result.put("message", "존재하지 않는 상담사입니다.");
                return result;
            }
            
            // 상담사 삭제 (논리적 삭제)
            int deleteResult = counselorLstMapper.deleteCounselor(counselorEmpId);
            
            if (deleteResult > 0) {
                result.put("success", true);
                result.put("message", "상담사가 성공적으로 삭제되었습니다.");
            } else {
                result.put("success", false);
                result.put("message", "상담사 삭제에 실패했습니다.");
            }
            
        } catch (Exception e) {
            log.error("상담사 삭제 실패", e);
            result.put("success", false);
            result.put("message", "상담사 삭제 중 오류가 발생했습니다.");
        }
        
        return result;
    }
} 