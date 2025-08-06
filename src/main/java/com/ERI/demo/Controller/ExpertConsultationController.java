package com.ERI.demo.controller;

import com.ERI.demo.dto.PageRequestDto;
import com.ERI.demo.dto.PageResponseDto;
import com.ERI.demo.service.ExpertConsultationService;
import com.ERI.demo.vo.ExpertConsultationAppVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 전문가 상담 Controller
 */
@Slf4j
@RestController
@RequestMapping("/api/expert-consultation")
@RequiredArgsConstructor
public class ExpertConsultationController {

    private final ExpertConsultationService expertConsultationService;

    /**
     * 전문가 상담 신청 목록 조회
     */
    @GetMapping("/list")
    public ResponseEntity<PageResponseDto<ExpertConsultationAppVO>> getExpertConsultationAppList(
            @ModelAttribute PageRequestDto pageRequest,
            @ModelAttribute ExpertConsultationAppVO searchCondition) {
        
        PageResponseDto<ExpertConsultationAppVO> result = expertConsultationService.getExpertConsultationAppList(pageRequest, searchCondition);
        return ResponseEntity.ok(result);
    }

    /**
     * 전문가 상담 신청 상세 조회
     */
    @GetMapping("/{appSeq}")
    public ResponseEntity<ExpertConsultationAppVO> getExpertConsultationAppBySeq(@PathVariable Long appSeq) {
        ExpertConsultationAppVO result = expertConsultationService.getExpertConsultationAppBySeq(appSeq);
        if (result == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(result);
    }

    /**
     * 전문가 상담 신청 등록
     */
    @PostMapping("/create")
    public ResponseEntity<Map<String, Object>> createExpertConsultationApp(
            @RequestBody ExpertConsultationAppVO expertConsultationApp,
            @RequestParam String empId) {
        
        try {
            ExpertConsultationAppVO result = expertConsultationService.createExpertConsultationApp(expertConsultationApp, empId);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", result);
            response.put("message", "전문가 상담 신청이 등록되었습니다.");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    /**
     * 전문가 상담 신청 수정
     */
    @PutMapping("/{appSeq}")
    public ResponseEntity<Map<String, Object>> updateExpertConsultationApp(
            @PathVariable Long appSeq,
            @RequestBody ExpertConsultationAppVO expertConsultationApp,
            @RequestParam String empId) {
        
        try {
            expertConsultationApp.setAppSeq(appSeq);
            ExpertConsultationAppVO result = expertConsultationService.updateExpertConsultationApp(expertConsultationApp, empId);
            if (result == null) {
                Map<String, Object> response = new HashMap<>();
                response.put("success", false);
                response.put("message", "전문가 상담 신청을 찾을 수 없습니다.");
                return ResponseEntity.notFound().build();
            }
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", result);
            response.put("message", "전문가 상담 신청이 수정되었습니다.");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    /**
     * 전문가 상담 신청 삭제
     */
    @DeleteMapping("/{appSeq}")
    public ResponseEntity<Map<String, Object>> deleteExpertConsultationApp(
            @PathVariable Long appSeq,
            @RequestParam String empId) {
        
        boolean result = expertConsultationService.deleteExpertConsultationApp(appSeq, empId);
        Map<String, Object> response = new HashMap<>();
        
        if (result) {
            response.put("success", true);
            response.put("message", "전문가 상담 신청이 삭제되었습니다.");
            return ResponseEntity.ok(response);
        } else {
            response.put("success", false);
            response.put("message", "전문가 상담 신청을 찾을 수 없습니다.");
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * 내 전문가 상담 신청 목록 조회
     */
    @GetMapping("/my-list")
    public ResponseEntity<PageResponseDto<ExpertConsultationAppVO>> getMyExpertConsultationAppList(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam String empId) {
        
        PageResponseDto<ExpertConsultationAppVO> result = expertConsultationService.getMyExpertConsultationAppList(page, size, empId);
        return ResponseEntity.ok(result);
    }

    /**
     * 전문가 상담 신청 승인/반려
     */
    @PutMapping("/{appSeq}/approval")
    public ResponseEntity<Map<String, Object>> updateApprovalStatus(
            @PathVariable Long appSeq,
            @RequestParam String aprvStsCd,
            @RequestParam String aprvEmpId,
            @RequestParam(required = false) String rejRsn) {
        
        try {
            ExpertConsultationAppVO result = expertConsultationService.updateApprovalStatus(appSeq, aprvStsCd, aprvEmpId, rejRsn);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", result);
            response.put("message", "승인 상태가 업데이트되었습니다.");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
} 