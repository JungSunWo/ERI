package com.ERI.demo.Controller;

import com.ERI.demo.dto.PageRequestDto;
import com.ERI.demo.dto.PageResponseDto;
import com.ERI.demo.dto.ErrorResponseDto;
import com.ERI.demo.service.CmnCodeService;
import com.ERI.demo.vo.CmnGrpCdVO;
import com.ERI.demo.vo.CmnDtlCdVO;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 공통코드 관리 컨트롤러
 */
@RestController
@RequestMapping("/api/cmn-code")
public class CmnCodeController {
    
    @Autowired
    private CmnCodeService cmnCodeService;
    
    private final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());
    
    // ==================== 그룹 코드 관리 ====================
    
    /**
     * 전체 공통 그룹 코드 조회 (페이징)
     * GET /api/cmn-code/group-codes
     */
    @GetMapping("/group-codes")
    public ResponseEntity<PageResponseDto<CmnGrpCdVO>> getAllGroupCodesWithPaging(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String sortBy,
            @RequestParam(defaultValue = "ASC") String sortDirection,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String searchField) {
        
        log.debug("그룹 코드 조회 요청: page={}, size={}, sortBy={}, keyword={}", page, size, sortBy, keyword);
        
        PageRequestDto pageRequest = new PageRequestDto();
        pageRequest.setPage(page);
        pageRequest.setSize(size);
        pageRequest.setSortBy(sortBy);
        pageRequest.setSortDirection(sortDirection);
        pageRequest.setKeyword(keyword);
        
        PageResponseDto<CmnGrpCdVO> response = cmnCodeService.getAllGroupCodesWithPaging(pageRequest);
        log.info("그룹 코드 조회 완료: 총 {}개", response.getTotalElements());
        return ResponseEntity.ok(response);
    }
    
    /**
     * 전체 공통 그룹 코드 조회 (페이징 없음)
     * GET /api/cmn-code/group-codes/all
     */
    @GetMapping("/group-codes/all")
    public ResponseEntity<List<CmnGrpCdVO>> getAllGroupCodes() {
        List<CmnGrpCdVO> groupCodes = cmnCodeService.getAllGroupCodes();
        return ResponseEntity.ok(groupCodes);
    }
    
    /**
     * 그룹 코드로 공통 그룹 코드 조회
     * GET /api/cmn-code/group-codes/{grpCd}
     */
    @GetMapping("/group-codes/{grpCd}")
    public ResponseEntity<?> getGroupCodeByGrpCd(@PathVariable String grpCd, HttpServletRequest request) {
        CmnGrpCdVO groupCode = cmnCodeService.getGroupCodeByGrpCd(grpCd);
        if (groupCode != null) {
            return ResponseEntity.ok(groupCode);
        } else {
            return ResponseEntity.status(404).body(ErrorResponseDto.notFound(
                "그룹 코드 '" + grpCd + "'를 찾을 수 없습니다.", 
                request.getRequestURI()
            ));
        }
    }
    
    /**
     * 공통 그룹 코드 등록
     * POST /api/cmn-code/group-codes
     */
    @PostMapping("/group-codes")
    public ResponseEntity<?> createGroupCode(@RequestBody CmnGrpCdVO cmnGrpCdVO, HttpServletRequest request) {
        try {
            int result = cmnCodeService.createGroupCode(cmnGrpCdVO);
            if (result > 0) {
                return ResponseEntity.ok("공통 그룹 코드가 성공적으로 등록되었습니다.");
            } else {
                return ResponseEntity.badRequest().body(ErrorResponseDto.badRequest(
                    "공통 그룹 코드 등록에 실패했습니다.", 
                    request.getRequestURI()
                ));
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ErrorResponseDto.badRequest(
                "공통 그룹 코드 등록 중 오류가 발생했습니다: " + e.getMessage(), 
                request.getRequestURI()
            ));
        }
    }
    
    /**
     * 공통 그룹 코드 수정
     * PUT /api/cmn-code/group-codes/{grpCd}
     */
    @PutMapping("/group-codes/{grpCd}")
    public ResponseEntity<?> updateGroupCode(@PathVariable String grpCd, @RequestBody CmnGrpCdVO cmnGrpCdVO, HttpServletRequest request) {
        try {
            cmnGrpCdVO.setGrpCd(grpCd);
            int result = cmnCodeService.updateGroupCode(cmnGrpCdVO);
            if (result > 0) {
                return ResponseEntity.ok("공통 그룹 코드가 성공적으로 수정되었습니다.");
            } else {
                return ResponseEntity.badRequest().body(ErrorResponseDto.badRequest(
                    "공통 그룹 코드 수정에 실패했습니다.", 
                    request.getRequestURI()
                ));
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ErrorResponseDto.badRequest(
                "공통 그룹 코드 수정 중 오류가 발생했습니다: " + e.getMessage(), 
                request.getRequestURI()
            ));
        }
    }
    
    /**
     * 공통 그룹 코드 삭제
     * DELETE /api/cmn-code/group-codes/{grpCd}
     */
    @DeleteMapping("/group-codes/{grpCd}")
    public ResponseEntity<?> deleteGroupCode(@PathVariable String grpCd, HttpServletRequest request) {
        try {
            int result = cmnCodeService.deleteGroupCode(grpCd);
            if (result > 0) {
                return ResponseEntity.ok("공통 그룹 코드가 성공적으로 삭제되었습니다.");
            } else {
                return ResponseEntity.badRequest().body(ErrorResponseDto.badRequest(
                    "공통 그룹 코드 삭제에 실패했습니다.", 
                    request.getRequestURI()
                ));
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ErrorResponseDto.badRequest(
                "공통 그룹 코드 삭제 중 오류가 발생했습니다: " + e.getMessage(), 
                request.getRequestURI()
            ));
        }
    }
    
    /**
     * 사용여부로 공통 그룹 코드 조회 (페이징)
     * GET /api/cmn-code/group-codes/use-yn/{useYn}
     */
    @GetMapping("/group-codes/use-yn/{useYn}")
    public ResponseEntity<PageResponseDto<CmnGrpCdVO>> getGroupCodesByUseYnWithPaging(
            @PathVariable String useYn,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String sortBy,
            @RequestParam(defaultValue = "ASC") String sortDirection,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String searchField) {
        
        PageRequestDto pageRequest = new PageRequestDto();
        pageRequest.setPage(page);
        pageRequest.setSize(size);
        pageRequest.setSortBy(sortBy);
        pageRequest.setSortDirection(sortDirection);
        pageRequest.setKeyword(keyword);
        
        PageResponseDto<CmnGrpCdVO> response = cmnCodeService.getGroupCodesByUseYnWithPaging(useYn, pageRequest);
        return ResponseEntity.ok(response);
    }
    
    /**
     * 사용여부로 공통 그룹 코드 조회 (페이징 없음)
     * GET /api/cmn-code/group-codes/use-yn/{useYn}/all
     */
    @GetMapping("/group-codes/use-yn/{useYn}/all")
    public ResponseEntity<List<CmnGrpCdVO>> getGroupCodesByUseYn(@PathVariable String useYn) {
        List<CmnGrpCdVO> groupCodes = cmnCodeService.getGroupCodesByUseYn(useYn);
        return ResponseEntity.ok(groupCodes);
    }
    
    // ==================== 상세 코드 관리 ====================
    
    /**
     * 전체 공통 상세 코드 조회 (페이징)
     * GET /api/cmn-code/detail-codes
     */
    @GetMapping("/detail-codes")
    public ResponseEntity<PageResponseDto<CmnDtlCdVO>> getAllDetailCodesWithPaging(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String sortBy,
            @RequestParam(defaultValue = "ASC") String sortDirection,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String searchField) {
        
        PageRequestDto pageRequest = new PageRequestDto();
        pageRequest.setPage(page);
        pageRequest.setSize(size);
        pageRequest.setSortBy(sortBy);
        pageRequest.setSortDirection(sortDirection);
        pageRequest.setKeyword(keyword);
        
        PageResponseDto<CmnDtlCdVO> response = cmnCodeService.getAllDetailCodesWithPaging(pageRequest);
        return ResponseEntity.ok(response);
    }
    
    /**
     * 전체 공통 상세 코드 조회 (페이징 없음)
     * GET /api/cmn-code/detail-codes/all
     */
    @GetMapping("/detail-codes/all")
    public ResponseEntity<List<CmnDtlCdVO>> getAllDetailCodes() {
        List<CmnDtlCdVO> detailCodes = cmnCodeService.getAllDetailCodes();
        return ResponseEntity.ok(detailCodes);
    }
    
    /**
     * 그룹 코드로 공통 상세 코드 조회 (페이징)
     * GET /api/cmn-code/detail-codes/group/{grpCd}
     */
    @GetMapping("/detail-codes/group/{grpCd}")
    public ResponseEntity<PageResponseDto<CmnDtlCdVO>> getDetailCodesByGrpCdWithPaging(
            @PathVariable String grpCd,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String sortBy,
            @RequestParam(defaultValue = "ASC") String sortDirection,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String searchField) {
        
        PageRequestDto pageRequest = new PageRequestDto();
        pageRequest.setPage(page);
        pageRequest.setSize(size);
        pageRequest.setSortBy(sortBy);
        pageRequest.setSortDirection(sortDirection);
        pageRequest.setKeyword(keyword);
        
        PageResponseDto<CmnDtlCdVO> response = cmnCodeService.getDetailCodesByGrpCdWithPaging(grpCd, pageRequest);
        return ResponseEntity.ok(response);
    }
    
    /**
     * 그룹 코드로 공통 상세 코드 조회 (페이징 없음)
     * GET /api/cmn-code/detail-codes/group/{grpCd}/all
     */
    @GetMapping("/detail-codes/group/{grpCd}/all")
    public ResponseEntity<List<CmnDtlCdVO>> getDetailCodesByGrpCd(@PathVariable String grpCd) {
        List<CmnDtlCdVO> detailCodes = cmnCodeService.getDetailCodesByGrpCd(grpCd);
        return ResponseEntity.ok(detailCodes);
    }
    
    /**
     * 그룹 코드와 상세 코드로 공통 상세 코드 조회
     * GET /api/cmn-code/detail-codes/{grpCd}/{dtlCd}
     */
    @GetMapping("/detail-codes/{grpCd}/{dtlCd}")
    public ResponseEntity<?> getDetailCodeByGrpCdAndDtlCd(@PathVariable String grpCd, @PathVariable String dtlCd, HttpServletRequest request) {
        CmnDtlCdVO detailCode = cmnCodeService.getDetailCodeByGrpCdAndDtlCd(grpCd, dtlCd);
        if (detailCode != null) {
            return ResponseEntity.ok(detailCode);
        } else {
            return ResponseEntity.status(404).body(ErrorResponseDto.notFound(
                "상세 코드 '" + grpCd + "/" + dtlCd + "'를 찾을 수 없습니다.", 
                request.getRequestURI()
            ));
        }
    }
    
    /**
     * 공통 상세 코드 등록
     * POST /api/cmn-code/detail-codes
     */
    @PostMapping("/detail-codes")
    public ResponseEntity<?> createDetailCode(@RequestBody CmnDtlCdVO cmnDtlCdVO, HttpServletRequest request) {
        try {
            int result = cmnCodeService.createDetailCode(cmnDtlCdVO);
            if (result > 0) {
                return ResponseEntity.ok("공통 상세 코드가 성공적으로 등록되었습니다.");
            } else {
                return ResponseEntity.badRequest().body(ErrorResponseDto.badRequest(
                    "공통 상세 코드 등록에 실패했습니다.", 
                    request.getRequestURI()
                ));
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ErrorResponseDto.badRequest(
                "공통 상세 코드 등록 중 오류가 발생했습니다: " + e.getMessage(), 
                request.getRequestURI()
            ));
        }
    }
    
    /**
     * 공통 상세 코드 수정
     * PUT /api/cmn-code/detail-codes/{grpCd}/{dtlCd}
     */
    @PutMapping("/detail-codes/{grpCd}/{dtlCd}")
    public ResponseEntity<?> updateDetailCode(@PathVariable String grpCd, @PathVariable String dtlCd, @RequestBody CmnDtlCdVO cmnDtlCdVO, HttpServletRequest request) {
        try {
            cmnDtlCdVO.setGrpCd(grpCd);
            cmnDtlCdVO.setDtlCd(dtlCd);
            int result = cmnCodeService.updateDetailCode(cmnDtlCdVO);
            if (result > 0) {
                return ResponseEntity.ok("공통 상세 코드가 성공적으로 수정되었습니다.");
            } else {
                return ResponseEntity.badRequest().body(ErrorResponseDto.badRequest(
                    "공통 상세 코드 수정에 실패했습니다.", 
                    request.getRequestURI()
                ));
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ErrorResponseDto.badRequest(
                "공통 상세 코드 수정 중 오류가 발생했습니다: " + e.getMessage(), 
                request.getRequestURI()
            ));
        }
    }
    
    /**
     * 공통 상세 코드 삭제
     * DELETE /api/cmn-code/detail-codes/{grpCd}/{dtlCd}
     */
    @DeleteMapping("/detail-codes/{grpCd}/{dtlCd}")
    public ResponseEntity<?> deleteDetailCode(@PathVariable String grpCd, @PathVariable String dtlCd, HttpServletRequest request) {
        try {
            int result = cmnCodeService.deleteDetailCode(grpCd, dtlCd);
            if (result > 0) {
                return ResponseEntity.ok("공통 상세 코드가 성공적으로 삭제되었습니다.");
            } else {
                return ResponseEntity.badRequest().body(ErrorResponseDto.badRequest(
                    "공통 상세 코드 삭제에 실패했습니다.", 
                    request.getRequestURI()
                ));
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ErrorResponseDto.badRequest(
                "공통 상세 코드 삭제 중 오류가 발생했습니다: " + e.getMessage(), 
                request.getRequestURI()
            ));
        }
    }
    
    /**
     * 사용여부로 공통 상세 코드 조회 (페이징)
     * GET /api/cmn-code/detail-codes/use-yn/{useYn}
     */
    @GetMapping("/detail-codes/use-yn/{useYn}")
    public ResponseEntity<PageResponseDto<CmnDtlCdVO>> getDetailCodesByUseYnWithPaging(
            @PathVariable String useYn,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String sortBy,
            @RequestParam(defaultValue = "ASC") String sortDirection,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String searchField) {
        
        PageRequestDto pageRequest = new PageRequestDto();
        pageRequest.setPage(page);
        pageRequest.setSize(size);
        pageRequest.setSortBy(sortBy);
        pageRequest.setSortDirection(sortDirection);
        pageRequest.setKeyword(keyword);
        
        PageResponseDto<CmnDtlCdVO> response = cmnCodeService.getDetailCodesByUseYnWithPaging(useYn, pageRequest);
        return ResponseEntity.ok(response);
    }
    
    /**
     * 사용여부로 공통 상세 코드 조회 (페이징 없음)
     * GET /api/cmn-code/detail-codes/use-yn/{useYn}/all
     */
    @GetMapping("/detail-codes/use-yn/{useYn}/all")
    public ResponseEntity<List<CmnDtlCdVO>> getDetailCodesByUseYn(@PathVariable String useYn) {
        List<CmnDtlCdVO> detailCodes = cmnCodeService.getDetailCodesByUseYn(useYn);
        return ResponseEntity.ok(detailCodes);
    }
    
    /**
     * 그룹 코드와 사용여부로 공통 상세 코드 조회 (페이징)
     * GET /api/cmn-code/detail-codes/group/{grpCd}/use-yn/{useYn}
     */
    @GetMapping("/detail-codes/group/{grpCd}/use-yn/{useYn}")
    public ResponseEntity<PageResponseDto<CmnDtlCdVO>> getDetailCodesByGrpCdAndUseYnWithPaging(
            @PathVariable String grpCd,
            @PathVariable String useYn,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String sortBy,
            @RequestParam(defaultValue = "ASC") String sortDirection,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String searchField) {
        
        PageRequestDto pageRequest = new PageRequestDto();
        pageRequest.setPage(page);
        pageRequest.setSize(size);
        pageRequest.setSortBy(sortBy);
        pageRequest.setSortDirection(sortDirection);
        pageRequest.setKeyword(keyword);
        
        PageResponseDto<CmnDtlCdVO> response = cmnCodeService.getDetailCodesByGrpCdAndUseYnWithPaging(grpCd, useYn, pageRequest);
        return ResponseEntity.ok(response);
    }
    
    /**
     * 그룹 코드와 사용여부로 공통 상세 코드 조회 (페이징 없음)
     * GET /api/cmn-code/detail-codes/group/{grpCd}/use-yn/{useYn}/all
     */
    @GetMapping("/detail-codes/group/{grpCd}/use-yn/{useYn}/all")
    public ResponseEntity<List<CmnDtlCdVO>> getDetailCodesByGrpCdAndUseYn(@PathVariable String grpCd, @PathVariable String useYn) {
        List<CmnDtlCdVO> detailCodes = cmnCodeService.getDetailCodesByGrpCdAndUseYn(grpCd, useYn);
        return ResponseEntity.ok(detailCodes);
    }
} 