package com.ERI.demo.Controller;

import com.ERI.demo.dto.PageRequestDto;
import com.ERI.demo.dto.PageResponseDto;
import com.ERI.demo.service.CmnCodeService;
import com.ERI.demo.vo.CmnGrpCdVO;
import com.ERI.demo.vo.CmnDtlCdVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 공통코드 관리 API 컨트롤러 (HTML 화면용)
 */
@RestController
@RequestMapping("/api/common-code")
public class CommonCodeController {
    
    @Autowired
    private CmnCodeService cmnCodeService;
    
    private final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());
    
    // ==================== 그룹 코드 관리 ====================
    
    /**
     * 그룹코드 목록 조회 (페이징)
     * GET /api/common-code/groups
     */
    @GetMapping("/groups")
    public ResponseEntity<Map<String, Object>> getGroups(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String sortKey,
            @RequestParam(defaultValue = "desc") String sortOrder,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String useYn) {
        
        try {
            PageRequestDto pageRequest = new PageRequestDto();
            pageRequest.setPage(page);
            pageRequest.setSize(size);
            pageRequest.setSortBy(sortKey);
            pageRequest.setSortDirection(sortOrder.toUpperCase());
            pageRequest.setKeyword(keyword);
            
            PageResponseDto<CmnGrpCdVO> response;
            if (useYn != null && !useYn.isEmpty()) {
                response = cmnCodeService.getGroupCodesByUseYnWithPaging(useYn, pageRequest);
            } else {
                response = cmnCodeService.getAllGroupCodesWithPaging(pageRequest);
            }
            
            Map<String, Object> result = new HashMap<>();
            result.put("success", true);
            result.put("data", response);
            
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            log.error("그룹코드 조회 중 오류 발생", e);
            Map<String, Object> result = new HashMap<>();
            result.put("success", false);
            result.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(result);
        }
    }
    
    /**
     * 그룹코드 검색
     * GET /api/common-code/groups/search
     */
    @GetMapping("/groups/search")
    public ResponseEntity<Map<String, Object>> searchGroups(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String sortKey,
            @RequestParam(defaultValue = "desc") String sortOrder,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String useYn) {
        
        return getGroups(page, size, sortKey, sortOrder, keyword, useYn);
    }
    
    /**
     * 그룹코드 상세 조회
     * GET /api/common-code/groups/{grpCd}
     */
    @GetMapping("/groups/{grpCd}")
    public ResponseEntity<Map<String, Object>> getGroup(@PathVariable String grpCd) {
        try {
            CmnGrpCdVO groupCode = cmnCodeService.getGroupCodeByGrpCd(grpCd);
            
            Map<String, Object> result = new HashMap<>();
            if (groupCode != null) {
                result.put("success", true);
                result.put("data", groupCode);
            } else {
                result.put("success", false);
                result.put("message", "그룹코드를 찾을 수 없습니다.");
            }
            
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            log.error("그룹코드 상세 조회 중 오류 발생", e);
            Map<String, Object> result = new HashMap<>();
            result.put("success", false);
            result.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(result);
        }
    }
    
    /**
     * 그룹코드 등록
     * POST /api/common-code/groups
     */
    @PostMapping("/groups")
    public ResponseEntity<Map<String, Object>> createGroup(@RequestBody CmnGrpCdVO groupCode) {
        try {
            int result = cmnCodeService.createGroupCode(groupCode);
            
            Map<String, Object> response = new HashMap<>();
            if (result > 0) {
                response.put("success", true);
                response.put("message", "그룹코드가 성공적으로 등록되었습니다.");
            } else {
                response.put("success", false);
                response.put("message", "그룹코드 등록에 실패했습니다.");
            }
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("그룹코드 등록 중 오류 발생", e);
            Map<String, Object> result = new HashMap<>();
            result.put("success", false);
            result.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(result);
        }
    }
    
    /**
     * 그룹코드 수정
     * PUT /api/common-code/groups/{grpCd}
     */
    @PutMapping("/groups/{grpCd}")
    public ResponseEntity<Map<String, Object>> updateGroup(@PathVariable String grpCd, @RequestBody CmnGrpCdVO groupCode) {
        try {
            groupCode.setGrpCd(grpCd);
            int result = cmnCodeService.updateGroupCode(groupCode);
            
            Map<String, Object> response = new HashMap<>();
            if (result > 0) {
                response.put("success", true);
                response.put("message", "그룹코드가 성공적으로 수정되었습니다.");
            } else {
                response.put("success", false);
                response.put("message", "그룹코드 수정에 실패했습니다.");
            }
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("그룹코드 수정 중 오류 발생", e);
            Map<String, Object> result = new HashMap<>();
            result.put("success", false);
            result.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(result);
        }
    }
    
    /**
     * 그룹코드 삭제
     * DELETE /api/common-code/groups/{grpCd}
     */
    @DeleteMapping("/groups/{grpCd}")
    public ResponseEntity<Map<String, Object>> deleteGroup(@PathVariable String grpCd) {
        try {
            int result = cmnCodeService.deleteGroupCode(grpCd);
            
            Map<String, Object> response = new HashMap<>();
            if (result > 0) {
                response.put("success", true);
                response.put("message", "그룹코드가 성공적으로 삭제되었습니다.");
            } else {
                response.put("success", false);
                response.put("message", "그룹코드 삭제에 실패했습니다.");
            }
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("그룹코드 삭제 중 오류 발생", e);
            Map<String, Object> result = new HashMap<>();
            result.put("success", false);
            result.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(result);
        }
    }
    
    // ==================== 상세 코드 관리 ====================
    
    /**
     * 상세코드 목록 조회 (페이징)
     * GET /api/common-code/details
     */
    @GetMapping("/details")
    public ResponseEntity<Map<String, Object>> getDetails(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String sortKey,
            @RequestParam(defaultValue = "desc") String sortOrder,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String useYn,
            @RequestParam(required = false) String grpCd) {
        
        try {
            PageRequestDto pageRequest = new PageRequestDto();
            pageRequest.setPage(page);
            pageRequest.setSize(size);
            pageRequest.setSortBy(sortKey);
            pageRequest.setSortDirection(sortOrder.toUpperCase());
            pageRequest.setKeyword(keyword);
            
            PageResponseDto<CmnDtlCdVO> response;
            if (grpCd != null && !grpCd.isEmpty()) {
                if (useYn != null && !useYn.isEmpty()) {
                    response = cmnCodeService.getDetailCodesByGrpCdAndUseYnWithPaging(grpCd, useYn, pageRequest);
                } else {
                    response = cmnCodeService.getDetailCodesByGrpCdWithPaging(grpCd, pageRequest);
                }
            } else {
                if (useYn != null && !useYn.isEmpty()) {
                    response = cmnCodeService.getDetailCodesByUseYnWithPaging(useYn, pageRequest);
                } else {
                    response = cmnCodeService.getAllDetailCodesWithPaging(pageRequest);
                }
            }
            
            Map<String, Object> result = new HashMap<>();
            result.put("success", true);
            result.put("data", response);
            
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            log.error("상세코드 조회 중 오류 발생", e);
            Map<String, Object> result = new HashMap<>();
            result.put("success", false);
            result.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(result);
        }
    }
    
    /**
     * 상세코드 검색
     * GET /api/common-code/details/search
     */
    @GetMapping("/details/search")
    public ResponseEntity<Map<String, Object>> searchDetails(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String sortKey,
            @RequestParam(defaultValue = "desc") String sortOrder,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String useYn,
            @RequestParam(required = false) String grpCd) {
        
        return getDetails(page, size, sortKey, sortOrder, keyword, useYn, grpCd);
    }
    
    /**
     * 상세코드 상세 조회
     * GET /api/common-code/details/{grpCd}/{dtlCd}
     */
    @GetMapping("/details/{grpCd}/{dtlCd}")
    public ResponseEntity<Map<String, Object>> getDetail(@PathVariable String grpCd, @PathVariable String dtlCd) {
        try {
            CmnDtlCdVO detailCode = cmnCodeService.getDetailCodeByGrpCdAndDtlCd(grpCd, dtlCd);
            
            Map<String, Object> result = new HashMap<>();
            if (detailCode != null) {
                result.put("success", true);
                result.put("data", detailCode);
            } else {
                result.put("success", false);
                result.put("message", "상세코드를 찾을 수 없습니다.");
            }
            
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            log.error("상세코드 상세 조회 중 오류 발생", e);
            Map<String, Object> result = new HashMap<>();
            result.put("success", false);
            result.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(result);
        }
    }
    
    /**
     * 상세코드 등록
     * POST /api/common-code/details
     */
    @PostMapping("/details")
    public ResponseEntity<Map<String, Object>> createDetail(@RequestBody CmnDtlCdVO detailCode) {
        try {
            int result = cmnCodeService.createDetailCode(detailCode);
            
            Map<String, Object> response = new HashMap<>();
            if (result > 0) {
                response.put("success", true);
                response.put("message", "상세코드가 성공적으로 등록되었습니다.");
            } else {
                response.put("success", false);
                response.put("message", "상세코드 등록에 실패했습니다.");
            }
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("상세코드 등록 중 오류 발생", e);
            Map<String, Object> result = new HashMap<>();
            result.put("success", false);
            result.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(result);
        }
    }
    
    /**
     * 상세코드 수정
     * PUT /api/common-code/details/{grpCd}/{dtlCd}
     */
    @PutMapping("/details/{grpCd}/{dtlCd}")
    public ResponseEntity<Map<String, Object>> updateDetail(@PathVariable String grpCd, @PathVariable String dtlCd, @RequestBody CmnDtlCdVO detailCode) {
        try {
            detailCode.setGrpCd(grpCd);
            detailCode.setDtlCd(dtlCd);
            int result = cmnCodeService.updateDetailCode(detailCode);
            
            Map<String, Object> response = new HashMap<>();
            if (result > 0) {
                response.put("success", true);
                response.put("message", "상세코드가 성공적으로 수정되었습니다.");
            } else {
                response.put("success", false);
                response.put("message", "상세코드 수정에 실패했습니다.");
            }
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("상세코드 수정 중 오류 발생", e);
            Map<String, Object> result = new HashMap<>();
            result.put("success", false);
            result.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(result);
        }
    }
    
    /**
     * 상세코드 삭제
     * DELETE /api/common-code/details/{grpCd}/{dtlCd}
     */
    @DeleteMapping("/details/{grpCd}/{dtlCd}")
    public ResponseEntity<Map<String, Object>> deleteDetail(@PathVariable String grpCd, @PathVariable String dtlCd) {
        try {
            int result = cmnCodeService.deleteDetailCode(grpCd, dtlCd);
            
            Map<String, Object> response = new HashMap<>();
            if (result > 0) {
                response.put("success", true);
                response.put("message", "상세코드가 성공적으로 삭제되었습니다.");
            } else {
                response.put("success", false);
                response.put("message", "상세코드 삭제에 실패했습니다.");
            }
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("상세코드 삭제 중 오류 발생", e);
            Map<String, Object> result = new HashMap<>();
            result.put("success", false);
            result.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(result);
        }
    }
} 