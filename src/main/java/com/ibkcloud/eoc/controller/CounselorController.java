package com.ibkcloud.eoc.controller;

import com.ibkcloud.eoc.cmm.dto.PageRequest;
import com.ibkcloud.eoc.cmm.dto.Page;
import com.ibkcloud.eoc.cmm.exception.BizException;
import com.ibkcloud.eoc.service.CounselorService;
import com.ibkcloud.eoc.controller.vo.CounselorLstInVo;
import com.ibkcloud.eoc.controller.vo.CounselorLstOutVo;
import com.ibkcloud.eoc.controller.vo.CounselorLstSearchInVo;
import com.ibkcloud.eoc.controller.vo.CounselorLstSearchOutVo;
import com.ibkcloud.eoc.controller.vo.EmpLstSearchOutVo;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 상담사 API 컨트롤러
 */
@Slf4j
@RestController
@RequestMapping("/api/counselor")
@RequiredArgsConstructor
public class CounselorController {

    private final CounselorService counselorService;

    /**
     * 상담사 목록 조회
     */
    @GetMapping("/list")
    public ResponseEntity<CounselorLstSearchOutVo> inqCounselorList(
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "1") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false) String sortDirection) {

        try {
            PageRequest pageRequest = new PageRequest();
            pageRequest.setPageNo(pageNo);
            pageRequest.setPageSize(pageSize);
            pageRequest.setParam(keyword);
            
            Page<CounselorLstOutVo> result = counselorService.inqCounselorList(pageRequest);
            
            CounselorLstSearchOutVo response = new CounselorLstSearchOutVo();
            response.setSuccess(true);
            response.setData(result.getContents());
            response.setCount(result.getTtalDataNbi());
            response.setPageNo(result.getPageNo());
            response.setPageSize(result.getPageSize());
            response.setTtalPageNbi(result.getTtalPageNbi());
            response.setKeyword(keyword);
            response.setSortBy(sortBy);
            response.setSortDirection(sortDirection);
            response.setMessage("상담사 목록을 조회했습니다.");
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("상담사 목록 조회 실패", e);
            throw new BizException("C001", "상담사 목록 조회에 실패했습니다: " + e.getMessage());
        }
    }

    /**
     * 직원 목록 조회 (상담사 등록용)
     */
    @GetMapping("/employee/list")
    public ResponseEntity<EmpLstSearchOutVo> inqEmployeeList(
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "1") int pageNo,
            @RequestParam(defaultValue = "50") int pageSize,
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false) String sortDirection) {
        try {
            PageRequest pageRequest = new PageRequest();
            pageRequest.setPageNo(pageNo);
            pageRequest.setPageSize(pageSize);
            pageRequest.setParam(keyword);
            
            Page<CounselorLstOutVo> result = counselorService.inqEmployeeList(pageRequest);
            
            EmpLstSearchOutVo response = new EmpLstSearchOutVo();
            response.setSuccess(true);
            response.setData(result.getContents().stream()
                    .map(counselor -> {
                        // CounselorLstOutVo를 EmpLstOutVo로 변환하는 로직 필요
                        return new EmpLstOutVo();
                    })
                    .toList());
            response.setCount(result.getTtalDataNbi());
            response.setSearchKeyword(keyword);
            response.setMessage("직원 목록을 조회했습니다.");
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("직원 목록 조회 실패", e);
            throw new BizException("C002", "직원 목록 조회에 실패했습니다: " + e.getMessage());
        }
    }

    /**
     * 상담사 등록
     */
    @PostMapping("/register")
    public ResponseEntity<CounselorLstOutVo> rgsnCounselor(@RequestBody CounselorLstInVo counselorData, HttpServletRequest request) {
        try {
            // AuthInterceptor에서 전달받은 사용자 정보 사용
            String sessionEmpId = (String) request.getAttribute("EMP_ID");
            if (sessionEmpId == null) {
                throw new BizException("C003", "인증 정보를 찾을 수 없습니다.");
            }
            
            counselorData.setRegEmpId(sessionEmpId);
            
            log.info("상담사 등록 요청: counselorEmpId={}, counselorInfoClsfCd={}, regEmpId={}",
                    counselorData.getCounselorEmpId(), counselorData.getCounselorInfoClsfCd(), sessionEmpId);
            
            CounselorLstOutVo result = counselorService.rgsnCounselor(counselorData);
            
            result.setSuccess(true);
            result.setMessage("상담사가 성공적으로 등록되었습니다.");
            
            return ResponseEntity.ok(result);
        } catch (BizException e) {
            throw e;
        } catch (Exception e) {
            log.error("상담사 등록 실패", e);
            throw new BizException("C004", "상담사 등록에 실패했습니다: " + e.getMessage());
        }
    }

    /**
     * 상담사 수정
     */
    @PutMapping("/{counselorEmpId}")
    public ResponseEntity<CounselorLstOutVo> mdfcCounselor(
            @PathVariable String counselorEmpId,
            @RequestBody CounselorLstInVo counselorData,
            HttpServletRequest request) {
        try {
            // AuthInterceptor에서 전달받은 사용자 정보 사용
            String sessionEmpId = (String) request.getAttribute("EMP_ID");
            if (sessionEmpId == null) {
                throw new BizException("C003", "인증 정보를 찾을 수 없습니다.");
            }
            
            counselorData.setUpdEmpId(sessionEmpId);
            counselorData.setCounselorEmpId(counselorEmpId);
            
            log.info("상담사 수정 요청: counselorEmpId={}, counselorInfoClsfCd={}, updEmpId={}",
                    counselorEmpId, counselorData.getCounselorInfoClsfCd(), sessionEmpId);
            
            CounselorLstOutVo result = counselorService.mdfcCounselor(counselorData);
            
            result.setSuccess(true);
            result.setMessage("상담사 정보가 성공적으로 수정되었습니다.");
            
            return ResponseEntity.ok(result);
        } catch (BizException e) {
            throw e;
        } catch (Exception e) {
            log.error("상담사 수정 실패", e);
            throw new BizException("C005", "상담사 수정에 실패했습니다: " + e.getMessage());
        }
    }

    /**
     * 상담사 삭제
     */
    @DeleteMapping("/{counselorEmpId}")
    public ResponseEntity<CounselorLstOutVo> delCounselor(@PathVariable String counselorEmpId, HttpServletRequest request) {
        try {
            // AuthInterceptor에서 전달받은 사용자 정보 사용
            String sessionEmpId = (String) request.getAttribute("EMP_ID");
            if (sessionEmpId == null) {
                throw new BizException("C003", "인증 정보를 찾을 수 없습니다.");
            }
            
            log.info("상담사 삭제 요청: counselorEmpId={}, delEmpId={}", counselorEmpId, sessionEmpId);
            
            CounselorLstOutVo result = counselorService.delCounselor(counselorEmpId);
            
            result.setSuccess(true);
            result.setMessage("상담사가 성공적으로 삭제되었습니다.");
            
            return ResponseEntity.ok(result);
        } catch (BizException e) {
            throw e;
        } catch (Exception e) {
            log.error("상담사 삭제 실패", e);
            throw new BizException("C006", "상담사 삭제에 실패했습니다: " + e.getMessage());
        }
    }
} 