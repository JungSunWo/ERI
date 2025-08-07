package com.ibkcloud.eoc.controller;

import com.ibkcloud.eoc.cmm.dto.PageRequest;
import com.ibkcloud.eoc.cmm.exception.BizException;
import com.ibkcloud.eoc.controller.vo.*;
import com.ibkcloud.eoc.service.ExpertConsultationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import java.util.List;

/**
 * 전문가 상담 Controller
 */
@Slf4j
@RestController
@RequestMapping("/api/expert-consultation")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@RequiredArgsConstructor
public class ExpertConsultationController {

    private final ExpertConsultationService expertConsultationService;

    /**
     * 전문가 상담 신청 목록 조회
     */
    @GetMapping("/list")
    public ResponseEntity<ExpertConsultationAppOutVo> inqExpertConsultationAppList(
            @Valid PageRequest pageRequest,
            @Valid ExpertConsultationAppInVo searchCondition) {
        
        log.info("전문가 상담 신청 목록 조회 시작");
        
        try {
            ExpertConsultationAppOutVo result = expertConsultationService.inqExpertConsultationAppList(pageRequest, searchCondition);
            
            result.setSuccess(true);
            result.setMessage("전문가 상담 신청 목록을 조회했습니다.");
            result.setErrorCode("SUCCESS");
            
            log.info("전문가 상담 신청 목록 조회 완료 - 총 {}건", result.getCount());
            return ResponseEntity.ok(result);
            
        } catch (Exception e) {
            log.error("전문가 상담 신청 목록 조회 실패", e);
            throw new BizException("전문가 상담 신청 목록 조회에 실패했습니다: " + e.getMessage());
        }
    }

    /**
     * 전문가 상담 신청 상세 조회
     */
    @GetMapping("/{appSeq}")
    public ResponseEntity<ExpertConsultationAppOutVo> inqExpertConsultationAppBySeq(@PathVariable Long appSeq) {
        log.info("전문가 상담 신청 상세 조회 시작 - appSeq: {}", appSeq);
        
        try {
            ExpertConsultationAppOutVo result = expertConsultationService.inqExpertConsultationAppBySeq(appSeq);
            
            if (result == null) {
                throw new BizException("전문가 상담 신청을 찾을 수 없습니다.");
            }
            
            result.setSuccess(true);
            result.setMessage("전문가 상담 신청 정보를 조회했습니다.");
            result.setErrorCode("SUCCESS");
            
            log.info("전문가 상담 신청 상세 조회 완료 - appSeq: {}", appSeq);
            return ResponseEntity.ok(result);
            
        } catch (Exception e) {
            log.error("전문가 상담 신청 상세 조회 실패 - appSeq: {}", appSeq, e);
            throw new BizException("전문가 상담 신청 조회에 실패했습니다: " + e.getMessage());
        }
    }

    /**
     * 전문가 상담 신청 등록
     */
    @PostMapping("/create")
    public ResponseEntity<ExpertConsultationAppOutVo> rgsnExpertConsultationApp(
            @Valid ExpertConsultationAppInVo inVo,
            HttpServletRequest request) {
        
        log.info("전문가 상담 신청 등록 시작");
        
        try {
            // AuthInterceptor에서 전달받은 사용자 정보 사용
            String empId = (String) request.getAttribute("EMP_ID");
            
            if (empId == null) {
                throw new BizException("로그인이 필요합니다.");
            }
            
            inVo.setAppEmpId(empId);
            inVo.setRegEmpId(empId);
            inVo.setUpdEmpId(empId);
            
            ExpertConsultationAppOutVo result = expertConsultationService.rgsnExpertConsultationApp(inVo);
            
            result.setSuccess(true);
            result.setMessage("전문가 상담 신청이 성공적으로 등록되었습니다.");
            result.setErrorCode("SUCCESS");
            
            log.info("전문가 상담 신청 등록 완료 - appSeq: {}", result.getAppSeq());
            return ResponseEntity.ok(result);
            
        } catch (Exception e) {
            log.error("전문가 상담 신청 등록 실패", e);
            throw new BizException("전문가 상담 신청 등록에 실패했습니다: " + e.getMessage());
        }
    }

    /**
     * 전문가 상담 신청 수정
     */
    @PutMapping("/{appSeq}")
    public ResponseEntity<ExpertConsultationAppOutVo> mdfcExpertConsultationApp(
            @PathVariable Long appSeq,
            @Valid ExpertConsultationAppInVo inVo,
            HttpServletRequest request) {
        
        log.info("전문가 상담 신청 수정 시작 - appSeq: {}", appSeq);
        
        try {
            // AuthInterceptor에서 전달받은 사용자 정보 사용
            String empId = (String) request.getAttribute("EMP_ID");
            
            if (empId == null) {
                throw new BizException("로그인이 필요합니다.");
            }
            
            inVo.setAppSeq(appSeq);
            inVo.setUpdEmpId(empId);
            
            ExpertConsultationAppOutVo result = expertConsultationService.mdfcExpertConsultationApp(inVo);
            
            if (result == null) {
                throw new BizException("전문가 상담 신청을 찾을 수 없습니다.");
            }
            
            result.setSuccess(true);
            result.setMessage("전문가 상담 신청이 성공적으로 수정되었습니다.");
            result.setErrorCode("SUCCESS");
            
            log.info("전문가 상담 신청 수정 완료 - appSeq: {}", appSeq);
            return ResponseEntity.ok(result);
            
        } catch (Exception e) {
            log.error("전문가 상담 신청 수정 실패 - appSeq: {}", appSeq, e);
            throw new BizException("전문가 상담 신청 수정에 실패했습니다: " + e.getMessage());
        }
    }

    /**
     * 전문가 상담 신청 삭제
     */
    @DeleteMapping("/{appSeq}")
    public ResponseEntity<ExpertConsultationAppOutVo> delExpertConsultationApp(
            @PathVariable Long appSeq,
            HttpServletRequest request) {
        
        log.info("전문가 상담 신청 삭제 시작 - appSeq: {}", appSeq);
        
        try {
            // AuthInterceptor에서 전달받은 사용자 정보 사용
            String empId = (String) request.getAttribute("EMP_ID");
            
            if (empId == null) {
                throw new BizException("로그인이 필요합니다.");
            }
            
            boolean result = expertConsultationService.delExpertConsultationApp(appSeq, empId);
            
            if (!result) {
                throw new BizException("전문가 상담 신청 삭제에 실패했습니다.");
            }
            
            ExpertConsultationAppOutVo outVo = new ExpertConsultationAppOutVo();
            outVo.setSuccess(true);
            outVo.setMessage("전문가 상담 신청이 성공적으로 삭제되었습니다.");
            outVo.setErrorCode("SUCCESS");
            
            log.info("전문가 상담 신청 삭제 완료 - appSeq: {}", appSeq);
            return ResponseEntity.ok(outVo);
            
        } catch (Exception e) {
            log.error("전문가 상담 신청 삭제 실패 - appSeq: {}", appSeq, e);
            throw new BizException("전문가 상담 신청 삭제에 실패했습니다: " + e.getMessage());
        }
    }

    /**
     * 내 전문가 상담 신청 목록 조회
     */
    @GetMapping("/my-list")
    public ResponseEntity<ExpertConsultationAppOutVo> inqMyExpertConsultationAppList(
            @Valid PageRequest pageRequest,
            HttpServletRequest request) {
        
        log.info("내 전문가 상담 신청 목록 조회 시작");
        
        try {
            // AuthInterceptor에서 전달받은 사용자 정보 사용
            String empId = (String) request.getAttribute("EMP_ID");
            
            if (empId == null) {
                throw new BizException("로그인이 필요합니다.");
            }
            
            ExpertConsultationAppOutVo result = expertConsultationService.inqMyExpertConsultationAppList(pageRequest, empId);
            
            result.setSuccess(true);
            result.setMessage("내 전문가 상담 신청 목록을 조회했습니다.");
            result.setErrorCode("SUCCESS");
            
            log.info("내 전문가 상담 신청 목록 조회 완료 - 총 {}건", result.getCount());
            return ResponseEntity.ok(result);
            
        } catch (Exception e) {
            log.error("내 전문가 상담 신청 목록 조회 실패", e);
            throw new BizException("내 전문가 상담 신청 목록 조회에 실패했습니다: " + e.getMessage());
        }
    }

    /**
     * 승인 상태 업데이트
     */
    @PutMapping("/{appSeq}/approval")
    public ResponseEntity<ExpertConsultationAppOutVo> mdfcApprovalStatus(
            @PathVariable Long appSeq,
            @RequestParam String aprvStsCd,
            @RequestParam(required = false) String rejRsn,
            HttpServletRequest request) {
        
        log.info("승인 상태 업데이트 시작 - appSeq: {}, aprvStsCd: {}", appSeq, aprvStsCd);
        
        try {
            // AuthInterceptor에서 전달받은 사용자 정보 사용
            String empId = (String) request.getAttribute("EMP_ID");
            
            if (empId == null) {
                throw new BizException("로그인이 필요합니다.");
            }
            
            ExpertConsultationAppOutVo result = expertConsultationService.mdfcApprovalStatus(appSeq, aprvStsCd, empId, rejRsn);
            
            if (result == null) {
                throw new BizException("전문가 상담 신청을 찾을 수 없습니다.");
            }
            
            result.setSuccess(true);
            result.setMessage("승인 상태가 성공적으로 업데이트되었습니다.");
            result.setErrorCode("SUCCESS");
            
            log.info("승인 상태 업데이트 완료 - appSeq: {}, aprvStsCd: {}", appSeq, aprvStsCd);
            return ResponseEntity.ok(result);
            
        } catch (Exception e) {
            log.error("승인 상태 업데이트 실패 - appSeq: {}, aprvStsCd: {}", appSeq, aprvStsCd, e);
            throw new BizException("승인 상태 업데이트에 실패했습니다: " + e.getMessage());
        }
    }
} 