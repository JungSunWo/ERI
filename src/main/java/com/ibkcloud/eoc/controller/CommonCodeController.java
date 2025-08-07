package com.ibkcloud.eoc.controller;

import com.ibkcloud.eoc.cmm.dto.PageRequest;
import com.ibkcloud.eoc.cmm.exception.BizException;
import com.ibkcloud.eoc.controller.vo.*;
import com.ibkcloud.eoc.service.CmnCodeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

/**
 * 공통코드 관리 API 컨트롤러 (HTML 화면용)
 */
@Slf4j
@RestController
@RequestMapping("/api/common-code")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@RequiredArgsConstructor
public class CommonCodeController {

    private final CmnCodeService cmnCodeService;

    // ==================== 그룹 코드 관리 ====================

    /**
     * 그룹코드 목록 조회 (페이징)
     */
    @GetMapping("/groups")
    public ResponseEntity<CmnGrpCdOutVo> inqCommonGrpCdList(
            @Valid PageRequest pageRequest,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String useYn) {
        
        log.info("그룹코드 목록 조회 시작 - keyword: {}, useYn: {}", keyword, useYn);
        
        try {
            CmnGrpCdOutVo result;
            if (useYn != null && !useYn.isEmpty()) {
                result = cmnCodeService.inqCmnGrpCdByUseYn(useYn, pageRequest, keyword, null);
            } else {
                result = cmnCodeService.inqCmnGrpCdList(pageRequest, keyword, null);
            }
            
            result.setSuccess(true);
            result.setMessage("그룹코드 목록을 조회했습니다.");
            result.setErrorCode("SUCCESS");
            
            log.info("그룹코드 목록 조회 완료 - 총 {}건", result.getCount());
            return ResponseEntity.ok(result);
            
        } catch (Exception e) {
            log.error("그룹코드 목록 조회 실패", e);
            throw new BizException("그룹코드 목록 조회에 실패했습니다: " + e.getMessage());
        }
    }

    /**
     * 그룹코드 검색
     */
    @GetMapping("/groups/search")
    public ResponseEntity<CmnGrpCdOutVo> inqCommonGrpCdSearch(
            @Valid PageRequest pageRequest,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String useYn) {
        
        return inqCommonGrpCdList(pageRequest, keyword, useYn);
    }

    /**
     * 그룹코드 상세 조회
     */
    @GetMapping("/groups/{grpCd}")
    public ResponseEntity<CmnGrpCdOutVo> inqCommonGrpCdByGrpCd(@PathVariable String grpCd) {
        log.info("그룹코드 상세 조회 시작 - grpCd: {}", grpCd);
        
        try {
            CmnGrpCdOutVo result = cmnCodeService.inqCmnGrpCdByGrpCd(grpCd);
            
            if (result == null) {
                throw new BizException("그룹코드 '" + grpCd + "'를 찾을 수 없습니다.");
            }
            
            result.setSuccess(true);
            result.setMessage("그룹코드 정보를 조회했습니다.");
            result.setErrorCode("SUCCESS");
            
            log.info("그룹코드 상세 조회 완료 - grpCd: {}", grpCd);
            return ResponseEntity.ok(result);
            
        } catch (Exception e) {
            log.error("그룹코드 상세 조회 실패 - grpCd: {}", grpCd, e);
            throw new BizException("그룹코드 조회에 실패했습니다: " + e.getMessage());
        }
    }

    /**
     * 그룹코드 등록
     */
    @PostMapping("/groups")
    public ResponseEntity<CmnGrpCdOutVo> rgsnCommonGrpCd(
            @Valid CmnGrpCdInVo inVo,
            HttpServletRequest request) {
        
        log.info("그룹코드 등록 시작 - grpCd: {}", inVo.getGrpCd());
        
        try {
            CmnGrpCdOutVo result = cmnCodeService.rgsnCmnGrpCd(inVo);
            
            result.setSuccess(true);
            result.setMessage("그룹코드가 성공적으로 등록되었습니다.");
            result.setErrorCode("SUCCESS");
            
            log.info("그룹코드 등록 완료 - grpCd: {}", inVo.getGrpCd());
            return ResponseEntity.ok(result);
            
        } catch (Exception e) {
            log.error("그룹코드 등록 실패", e);
            throw new BizException("그룹코드 등록에 실패했습니다: " + e.getMessage());
        }
    }

    /**
     * 그룹코드 수정
     */
    @PutMapping("/groups/{grpCd}")
    public ResponseEntity<CmnGrpCdOutVo> mdfcCommonGrpCd(
            @PathVariable String grpCd,
            @Valid CmnGrpCdInVo inVo) {
        
        log.info("그룹코드 수정 시작 - grpCd: {}", grpCd);
        
        try {
            inVo.setGrpCd(grpCd);
            CmnGrpCdOutVo result = cmnCodeService.mdfcCmnGrpCd(inVo);
            
            if (result == null) {
                throw new BizException("그룹코드 수정에 실패했습니다.");
            }
            
            result.setSuccess(true);
            result.setMessage("그룹코드가 성공적으로 수정되었습니다.");
            result.setErrorCode("SUCCESS");
            
            log.info("그룹코드 수정 완료 - grpCd: {}", grpCd);
            return ResponseEntity.ok(result);
            
        } catch (Exception e) {
            log.error("그룹코드 수정 실패 - grpCd: {}", grpCd, e);
            throw new BizException("그룹코드 수정에 실패했습니다: " + e.getMessage());
        }
    }

    /**
     * 그룹코드 삭제
     */
    @DeleteMapping("/groups/{grpCd}")
    public ResponseEntity<CmnGrpCdOutVo> delCommonGrpCd(@PathVariable String grpCd) {
        log.info("그룹코드 삭제 시작 - grpCd: {}", grpCd);
        
        try {
            boolean result = cmnCodeService.delCmnGrpCd(grpCd);
            
            if (!result) {
                throw new BizException("그룹코드 삭제에 실패했습니다.");
            }
            
            CmnGrpCdOutVo outVo = new CmnGrpCdOutVo();
            outVo.setSuccess(true);
            outVo.setMessage("그룹코드가 성공적으로 삭제되었습니다.");
            outVo.setErrorCode("SUCCESS");
            
            log.info("그룹코드 삭제 완료 - grpCd: {}", grpCd);
            return ResponseEntity.ok(outVo);
            
        } catch (Exception e) {
            log.error("그룹코드 삭제 실패 - grpCd: {}", grpCd, e);
            throw new BizException("그룹코드 삭제에 실패했습니다: " + e.getMessage());
        }
    }

    // ==================== 상세 코드 관리 ====================

    /**
     * 상세코드 목록 조회 (페이징)
     */
    @GetMapping("/details")
    public ResponseEntity<CmnDtlCdOutVo> inqCommonDtlCdList(
            @Valid PageRequest pageRequest,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String useYn,
            @RequestParam(required = false) String grpCd) {
        
        log.info("상세코드 목록 조회 시작 - keyword: {}, useYn: {}, grpCd: {}", keyword, useYn, grpCd);
        
        try {
            CmnDtlCdOutVo result;
            if (grpCd != null && !grpCd.isEmpty()) {
                if (useYn != null && !useYn.isEmpty()) {
                    result = cmnCodeService.inqCmnDtlCdByGrpCdAndUseYn(grpCd, useYn, pageRequest, keyword, null);
                } else {
                    result = cmnCodeService.inqCmnDtlCdByGrpCd(grpCd, pageRequest, keyword, null);
                }
            } else {
                if (useYn != null && !useYn.isEmpty()) {
                    result = cmnCodeService.inqCmnDtlCdByUseYn(useYn, pageRequest, keyword, null);
                } else {
                    result = cmnCodeService.inqCmnDtlCdList(pageRequest, keyword, null);
                }
            }
            
            result.setSuccess(true);
            result.setMessage("상세코드 목록을 조회했습니다.");
            result.setErrorCode("SUCCESS");
            
            log.info("상세코드 목록 조회 완료 - 총 {}건", result.getCount());
            return ResponseEntity.ok(result);
            
        } catch (Exception e) {
            log.error("상세코드 목록 조회 실패", e);
            throw new BizException("상세코드 목록 조회에 실패했습니다: " + e.getMessage());
        }
    }

    /**
     * 상세코드 검색
     */
    @GetMapping("/details/search")
    public ResponseEntity<CmnDtlCdOutVo> inqCommonDtlCdSearch(
            @Valid PageRequest pageRequest,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String useYn,
            @RequestParam(required = false) String grpCd) {
        
        return inqCommonDtlCdList(pageRequest, keyword, useYn, grpCd);
    }

    /**
     * 상세코드 상세 조회
     */
    @GetMapping("/details/{grpCd}/{dtlCd}")
    public ResponseEntity<CmnDtlCdOutVo> inqCommonDtlCdByGrpCdAndDtlCd(
            @PathVariable String grpCd,
            @PathVariable String dtlCd) {
        
        log.info("상세코드 상세 조회 시작 - grpCd: {}, dtlCd: {}", grpCd, dtlCd);
        
        try {
            CmnDtlCdOutVo result = cmnCodeService.inqCmnDtlCdByGrpCdAndDtlCd(grpCd, dtlCd);
            
            if (result == null) {
                throw new BizException("상세코드를 찾을 수 없습니다.");
            }
            
            result.setSuccess(true);
            result.setMessage("상세코드 정보를 조회했습니다.");
            result.setErrorCode("SUCCESS");
            
            log.info("상세코드 상세 조회 완료 - grpCd: {}, dtlCd: {}", grpCd, dtlCd);
            return ResponseEntity.ok(result);
            
        } catch (Exception e) {
            log.error("상세코드 상세 조회 실패 - grpCd: {}, dtlCd: {}", grpCd, dtlCd, e);
            throw new BizException("상세코드 조회에 실패했습니다: " + e.getMessage());
        }
    }

    /**
     * 상세코드 등록
     */
    @PostMapping("/details")
    public ResponseEntity<CmnDtlCdOutVo> rgsnCommonDtlCd(
            @Valid CmnDtlCdInVo inVo,
            HttpServletRequest request) {
        
        log.info("상세코드 등록 시작 - grpCd: {}, dtlCd: {}", inVo.getGrpCd(), inVo.getDtlCd());
        
        try {
            CmnDtlCdOutVo result = cmnCodeService.rgsnCmnDtlCd(inVo);
            
            result.setSuccess(true);
            result.setMessage("상세코드가 성공적으로 등록되었습니다.");
            result.setErrorCode("SUCCESS");
            
            log.info("상세코드 등록 완료 - grpCd: {}, dtlCd: {}", inVo.getGrpCd(), inVo.getDtlCd());
            return ResponseEntity.ok(result);
            
        } catch (Exception e) {
            log.error("상세코드 등록 실패", e);
            throw new BizException("상세코드 등록에 실패했습니다: " + e.getMessage());
        }
    }

    /**
     * 상세코드 수정
     */
    @PutMapping("/details/{grpCd}/{dtlCd}")
    public ResponseEntity<CmnDtlCdOutVo> mdfcCommonDtlCd(
            @PathVariable String grpCd,
            @PathVariable String dtlCd,
            @Valid CmnDtlCdInVo inVo) {
        
        log.info("상세코드 수정 시작 - grpCd: {}, dtlCd: {}", grpCd, dtlCd);
        
        try {
            inVo.setGrpCd(grpCd);
            inVo.setDtlCd(dtlCd);
            CmnDtlCdOutVo result = cmnCodeService.mdfcCmnDtlCd(inVo);
            
            if (result == null) {
                throw new BizException("상세코드 수정에 실패했습니다.");
            }
            
            result.setSuccess(true);
            result.setMessage("상세코드가 성공적으로 수정되었습니다.");
            result.setErrorCode("SUCCESS");
            
            log.info("상세코드 수정 완료 - grpCd: {}, dtlCd: {}", grpCd, dtlCd);
            return ResponseEntity.ok(result);
            
        } catch (Exception e) {
            log.error("상세코드 수정 실패 - grpCd: {}, dtlCd: {}", grpCd, dtlCd, e);
            throw new BizException("상세코드 수정에 실패했습니다: " + e.getMessage());
        }
    }

    /**
     * 상세코드 삭제
     */
    @DeleteMapping("/details/{grpCd}/{dtlCd}")
    public ResponseEntity<CmnDtlCdOutVo> delCommonDtlCd(
            @PathVariable String grpCd,
            @PathVariable String dtlCd) {
        
        log.info("상세코드 삭제 시작 - grpCd: {}, dtlCd: {}", grpCd, dtlCd);
        
        try {
            boolean result = cmnCodeService.delCmnDtlCd(grpCd, dtlCd);
            
            if (!result) {
                throw new BizException("상세코드 삭제에 실패했습니다.");
            }
            
            CmnDtlCdOutVo outVo = new CmnDtlCdOutVo();
            outVo.setSuccess(true);
            outVo.setMessage("상세코드가 성공적으로 삭제되었습니다.");
            outVo.setErrorCode("SUCCESS");
            
            log.info("상세코드 삭제 완료 - grpCd: {}, dtlCd: {}", grpCd, dtlCd);
            return ResponseEntity.ok(outVo);
            
        } catch (Exception e) {
            log.error("상세코드 삭제 실패 - grpCd: {}, dtlCd: {}", grpCd, dtlCd, e);
            throw new BizException("상세코드 삭제에 실패했습니다: " + e.getMessage());
        }
    }
}
