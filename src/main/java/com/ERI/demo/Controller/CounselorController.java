package com.ERI.demo.Controller;

import com.ERI.demo.dto.PageRequestDto;
import com.ERI.demo.dto.PageResponseDto;
import com.ERI.demo.service.CounselorService;
import com.ERI.demo.vo.CounselorLstVO;
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
    public ResponseEntity<Map<String, Object>> getCounselorList(
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false) String sortDirection) {

        Map<String, Object> response = new HashMap<>();
        try {
            PageRequestDto pageRequest = PageRequestDto.builder()
                    .page(page)
                    .size(size)
                    .keyword(keyword)
                    .sortBy(sortBy)
                    .sortDirection(sortDirection)
                    .build();
            PageResponseDto<CounselorLstVO> result = counselorService.getCounselorList(pageRequest);
            response.put("success", true);
            response.put("data", result);
            response.put("message", "상담사 목록을 조회했습니다.");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("상담사 목록 조회 실패", e);
            response.put("success", false);
            response.put("message", "상담사 목록 조회에 실패했습니다: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * 직원 목록 조회 (상담사 등록용)
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
            PageRequestDto pageRequest = PageRequestDto.builder()
                    .page(page)
                    .size(size)
                    .keyword(keyword)
                    .sortBy(sortBy)
                    .sortDirection(sortDirection)
                    .build();
            PageResponseDto<EmpLstVO> result = counselorService.getEmployeeList(pageRequest);
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
     * 상담사 등록
     */
    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> createCounselor(@RequestBody Map<String, Object> counselorData, HttpServletRequest request) {
        Map<String, Object> response = new HashMap<>();
        try {
            // AuthInterceptor에서 전달받은 사용자 정보 사용
            String sessionEmpId = (String) request.getAttribute("EMP_ID");
            if (sessionEmpId == null) {
                response.put("success", false);
                response.put("message", "인증 정보를 찾을 수 없습니다.");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }
            
            counselorData.put("regEmpId", sessionEmpId);
            log.info("상담사 등록 요청: counselorEmpId={}, counselorInfoClsfCd={}, regEmpId={}",
                    counselorData.get("counselorEmpId"), counselorData.get("counselorInfoClsfCd"), sessionEmpId);
            Map<String, Object> result = counselorService.createCounselor(counselorData);
            response.put("success", result.get("success"));
            response.put("data", result.get("counselorEmpId"));
            response.put("message", result.get("message"));
            if ((Boolean) result.get("success")) {
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.badRequest().body(response);
            }
        } catch (Exception e) {
            log.error("상담사 등록 실패", e);
            response.put("success", false);
            response.put("message", "상담사 등록에 실패했습니다: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * 상담사 수정
     */
    @PutMapping("/{counselorEmpId}")
    public ResponseEntity<Map<String, Object>> updateCounselor(
            @PathVariable String counselorEmpId,
            @RequestBody Map<String, Object> counselorData,
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
            
            counselorData.put("updEmpId", sessionEmpId);
            log.info("상담사 수정 요청: counselorEmpId={}, counselorInfoClsfCd={}, updEmpId={}",
                    counselorEmpId, counselorData.get("counselorInfoClsfCd"), sessionEmpId);
            Map<String, Object> result = counselorService.updateCounselor(counselorEmpId, counselorData);
            response.put("success", result.get("success"));
            response.put("message", result.get("message"));
            if ((Boolean) result.get("success")) {
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.badRequest().body(response);
            }
        } catch (Exception e) {
            log.error("상담사 수정 실패", e);
            response.put("success", false);
            response.put("message", "상담사 수정에 실패했습니다: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * 상담사 삭제
     */
    @DeleteMapping("/{counselorEmpId}")
    public ResponseEntity<Map<String, Object>> deleteCounselor(@PathVariable String counselorEmpId, HttpServletRequest request) {
        Map<String, Object> response = new HashMap<>();
        try {
            // AuthInterceptor에서 전달받은 사용자 정보 사용
            String sessionEmpId = (String) request.getAttribute("EMP_ID");
            if (sessionEmpId == null) {
                response.put("success", false);
                response.put("message", "인증 정보를 찾을 수 없습니다.");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }
            
            log.info("상담사 삭제 요청: counselorEmpId={}, delEmpId={}", counselorEmpId, sessionEmpId);
            Map<String, Object> result = counselorService.deleteCounselor(counselorEmpId);
            response.put("success", result.get("success"));
            response.put("message", result.get("message"));
            if ((Boolean) result.get("success")) {
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.badRequest().body(response);
            }
        } catch (Exception e) {
            log.error("상담사 삭제 실패", e);
            response.put("success", false);
            response.put("message", "상담사 삭제에 실패했습니다: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
} 