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
import java.util.List;

/**
 * 공통코드 관리 컨트롤러
 */
@Slf4j
@RestController
@RequestMapping("/api/cmn-code")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@RequiredArgsConstructor
public class CmnCodeController {

    private final CmnCodeService cmnCodeService;

    // ==================== 그룹 코드 관리 ====================

    /**
     * 전체 공통 그룹 코드 조회 (페이징)
     */
    @GetMapping("/group-codes")
    public ResponseEntity<CmnGrpCdOutVo> inqCmnGrpCdList(
            @Valid PageRequest pageRequest,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String searchField) {
        
        log.info("그룹 코드 조회 시작 - keyword: {}, searchField: {}", keyword, searchField);
        
        try {
            CmnGrpCdOutVo result = cmnCodeService.inqCmnGrpCdList(pageRequest, keyword, searchField);
            
            result.setSuccess(true);
            result.setMessage("그룹 코드 목록을 조회했습니다.");
            result.setErrorCode("SUCCESS");
            
            log.info("그룹 코드 조회 완료 - 총 {}건", result.getCount());
            return ResponseEntity.ok(result);
            
        } catch (Exception e) {
            log.error("그룹 코드 조회 실패", e);
            throw new BizException("그룹 코드 조회에 실패했습니다: " + e.getMessage());
        }
    }

    /**
     * 전체 공통 그룹 코드 조회 (페이징 없음)
     */
    @GetMapping("/group-codes/all")
    public ResponseEntity<List<CmnGrpCdOutVo>> inqAllCmnGrpCdList() {
        log.info("전체 그룹 코드 조회 시작");
        
        try {
            List<CmnGrpCdOutVo> result = cmnCodeService.inqAllCmnGrpCdList();
            
            log.info("전체 그룹 코드 조회 완료 - 총 {}건", result.size());
            return ResponseEntity.ok(result);
            
        } catch (Exception e) {
            log.error("전체 그룹 코드 조회 실패", e);
            throw new BizException("전체 그룹 코드 조회에 실패했습니다: " + e.getMessage());
        }
    }

    /**
     * 그룹 코드로 공통 그룹 코드 조회
     */
    @GetMapping("/group-codes/{grpCd}")
    public ResponseEntity<CmnGrpCdOutVo> inqCmnGrpCdByGrpCd(@PathVariable String grpCd) {
        log.info("그룹 코드 상세 조회 시작 - grpCd: {}", grpCd);
        
        try {
            CmnGrpCdOutVo result = cmnCodeService.inqCmnGrpCdByGrpCd(grpCd);
            
            if (result == null) {
                throw new BizException("그룹 코드 '" + grpCd + "'를 찾을 수 없습니다.");
            }
            
            result.setSuccess(true);
            result.setMessage("그룹 코드 정보를 조회했습니다.");
            result.setErrorCode("SUCCESS");
            
            log.info("그룹 코드 상세 조회 완료 - grpCd: {}", grpCd);
            return ResponseEntity.ok(result);
            
        } catch (Exception e) {
            log.error("그룹 코드 상세 조회 실패 - grpCd: {}", grpCd, e);
            throw new BizException("그룹 코드 조회에 실패했습니다: " + e.getMessage());
        }
    }

    /**
     * 공통 그룹 코드 등록
     */
    @PostMapping("/group-codes")
    public ResponseEntity<CmnGrpCdOutVo> rgsnCmnGrpCd(
            @Valid CmnGrpCdInVo inVo,
            HttpServletRequest request) {
        
        log.info("그룹 코드 등록 시작 - grpCd: {}", inVo.getGrpCd());
        
        try {
            CmnGrpCdOutVo result = cmnCodeService.rgsnCmnGrpCd(inVo);
            
            result.setSuccess(true);
            result.setMessage("공통 그룹 코드가 성공적으로 등록되었습니다.");
            result.setErrorCode("SUCCESS");
            
            log.info("그룹 코드 등록 완료 - grpCd: {}", inVo.getGrpCd());
            return ResponseEntity.ok(result);
            
        } catch (Exception e) {
            log.error("그룹 코드 등록 실패", e);
            throw new BizException("공통 그룹 코드 등록에 실패했습니다: " + e.getMessage());
        }
    }

    /**
     * 공통 그룹 코드 수정
     */
    @PutMapping("/group-codes/{grpCd}")
    public ResponseEntity<CmnGrpCdOutVo> mdfcCmnGrpCd(
            @PathVariable String grpCd,
            @Valid CmnGrpCdInVo inVo) {
        
        log.info("그룹 코드 수정 시작 - grpCd: {}", grpCd);
        
        try {
            inVo.setGrpCd(grpCd);
            CmnGrpCdOutVo result = cmnCodeService.mdfcCmnGrpCd(inVo);
            
            if (result == null) {
                throw new BizException("그룹 코드 수정에 실패했습니다.");
            }
            
            result.setSuccess(true);
            result.setMessage("공통 그룹 코드가 성공적으로 수정되었습니다.");
            result.setErrorCode("SUCCESS");
            
            log.info("그룹 코드 수정 완료 - grpCd: {}", grpCd);
            return ResponseEntity.ok(result);
            
        } catch (Exception e) {
            log.error("그룹 코드 수정 실패 - grpCd: {}", grpCd, e);
            throw new BizException("공통 그룹 코드 수정에 실패했습니다: " + e.getMessage());
        }
    }

    /**
     * 공통 그룹 코드 삭제
     */
    @DeleteMapping("/group-codes/{grpCd}")
    public ResponseEntity<CmnGrpCdOutVo> delCmnGrpCd(@PathVariable String grpCd) {
        log.info("그룹 코드 삭제 시작 - grpCd: {}", grpCd);
        
        try {
            boolean result = cmnCodeService.delCmnGrpCd(grpCd);
            
            if (!result) {
                throw new BizException("그룹 코드 삭제에 실패했습니다.");
            }
            
            CmnGrpCdOutVo outVo = new CmnGrpCdOutVo();
            outVo.setSuccess(true);
            outVo.setMessage("공통 그룹 코드가 성공적으로 삭제되었습니다.");
            outVo.setErrorCode("SUCCESS");
            
            log.info("그룹 코드 삭제 완료 - grpCd: {}", grpCd);
            return ResponseEntity.ok(outVo);
            
        } catch (Exception e) {
            log.error("그룹 코드 삭제 실패 - grpCd: {}", grpCd, e);
            throw new BizException("공통 그룹 코드 삭제에 실패했습니다: " + e.getMessage());
        }
    }

    /**
     * 사용여부별 그룹 코드 조회 (페이징)
     */
    @GetMapping("/group-codes/use-yn/{useYn}")
    public ResponseEntity<CmnGrpCdOutVo> inqCmnGrpCdByUseYn(
            @PathVariable String useYn,
            @Valid PageRequest pageRequest,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String searchField) {
        
        log.info("사용여부별 그룹 코드 조회 시작 - useYn: {}", useYn);
        
        try {
            CmnGrpCdOutVo result = cmnCodeService.inqCmnGrpCdByUseYn(useYn, pageRequest, keyword, searchField);
            
            result.setSuccess(true);
            result.setMessage("사용여부별 그룹 코드 목록을 조회했습니다.");
            result.setErrorCode("SUCCESS");
            
            log.info("사용여부별 그룹 코드 조회 완료 - useYn: {}, 총 {}건", useYn, result.getCount());
            return ResponseEntity.ok(result);
            
        } catch (Exception e) {
            log.error("사용여부별 그룹 코드 조회 실패 - useYn: {}", useYn, e);
            throw new BizException("사용여부별 그룹 코드 조회에 실패했습니다: " + e.getMessage());
        }
    }

    /**
     * 사용여부별 그룹 코드 조회 (페이징 없음)
     */
    @GetMapping("/group-codes/use-yn/{useYn}/all")
    public ResponseEntity<List<CmnGrpCdOutVo>> inqAllCmnGrpCdByUseYn(@PathVariable String useYn) {
        log.info("전체 사용여부별 그룹 코드 조회 시작 - useYn: {}", useYn);
        
        try {
            List<CmnGrpCdOutVo> result = cmnCodeService.inqAllCmnGrpCdByUseYn(useYn);
            
            log.info("전체 사용여부별 그룹 코드 조회 완료 - useYn: {}, 총 {}건", useYn, result.size());
            return ResponseEntity.ok(result);
            
        } catch (Exception e) {
            log.error("전체 사용여부별 그룹 코드 조회 실패 - useYn: {}", useYn, e);
            throw new BizException("전체 사용여부별 그룹 코드 조회에 실패했습니다: " + e.getMessage());
        }
    }

    // ==================== 상세 코드 관리 ====================

    /**
     * 전체 상세 코드 조회 (페이징)
     */
    @GetMapping("/detail-codes")
    public ResponseEntity<CmnDtlCdOutVo> inqCmnDtlCdList(
            @Valid PageRequest pageRequest,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String searchField) {
        
        log.info("상세 코드 조회 시작 - keyword: {}, searchField: {}", keyword, searchField);
        
        try {
            CmnDtlCdOutVo result = cmnCodeService.inqCmnDtlCdList(pageRequest, keyword, searchField);
            
            result.setSuccess(true);
            result.setMessage("상세 코드 목록을 조회했습니다.");
            result.setErrorCode("SUCCESS");
            
            log.info("상세 코드 조회 완료 - 총 {}건", result.getCount());
            return ResponseEntity.ok(result);
            
        } catch (Exception e) {
            log.error("상세 코드 조회 실패", e);
            throw new BizException("상세 코드 조회에 실패했습니다: " + e.getMessage());
        }
    }

    /**
     * 전체 상세 코드 조회 (페이징 없음)
     */
    @GetMapping("/detail-codes/all")
    public ResponseEntity<List<CmnDtlCdOutVo>> inqAllCmnDtlCdList() {
        log.info("전체 상세 코드 조회 시작");
        
        try {
            List<CmnDtlCdOutVo> result = cmnCodeService.inqAllCmnDtlCdList();
            
            log.info("전체 상세 코드 조회 완료 - 총 {}건", result.size());
            return ResponseEntity.ok(result);
            
        } catch (Exception e) {
            log.error("전체 상세 코드 조회 실패", e);
            throw new BizException("전체 상세 코드 조회에 실패했습니다: " + e.getMessage());
        }
    }

    /**
     * 그룹 코드별 상세 코드 조회 (페이징)
     */
    @GetMapping("/detail-codes/group/{grpCd}")
    public ResponseEntity<CmnDtlCdOutVo> inqCmnDtlCdByGrpCd(
            @PathVariable String grpCd,
            @Valid PageRequest pageRequest,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String searchField) {
        
        log.info("그룹 코드별 상세 코드 조회 시작 - grpCd: {}", grpCd);
        
        try {
            CmnDtlCdOutVo result = cmnCodeService.inqCmnDtlCdByGrpCd(grpCd, pageRequest, keyword, searchField);
            
            result.setSuccess(true);
            result.setMessage("그룹 코드별 상세 코드 목록을 조회했습니다.");
            result.setErrorCode("SUCCESS");
            
            log.info("그룹 코드별 상세 코드 조회 완료 - grpCd: {}, 총 {}건", grpCd, result.getCount());
            return ResponseEntity.ok(result);
            
        } catch (Exception e) {
            log.error("그룹 코드별 상세 코드 조회 실패 - grpCd: {}", grpCd, e);
            throw new BizException("그룹 코드별 상세 코드 조회에 실패했습니다: " + e.getMessage());
        }
    }

    /**
     * 그룹 코드별 상세 코드 조회 (페이징 없음)
     */
    @GetMapping("/detail-codes/group/{grpCd}/all")
    public ResponseEntity<List<CmnDtlCdOutVo>> inqAllCmnDtlCdByGrpCd(@PathVariable String grpCd) {
        log.info("전체 그룹 코드별 상세 코드 조회 시작 - grpCd: {}", grpCd);
        
        try {
            List<CmnDtlCdOutVo> result = cmnCodeService.inqAllCmnDtlCdByGrpCd(grpCd);
            
            log.info("전체 그룹 코드별 상세 코드 조회 완료 - grpCd: {}, 총 {}건", grpCd, result.size());
            return ResponseEntity.ok(result);
            
        } catch (Exception e) {
            log.error("전체 그룹 코드별 상세 코드 조회 실패 - grpCd: {}", grpCd, e);
            throw new BizException("전체 그룹 코드별 상세 코드 조회에 실패했습니다: " + e.getMessage());
        }
    }

    /**
     * 상세 코드 상세 조회
     */
    @GetMapping("/detail-codes/{grpCd}/{dtlCd}")
    public ResponseEntity<CmnDtlCdOutVo> inqCmnDtlCdByGrpCdAndDtlCd(
            @PathVariable String grpCd,
            @PathVariable String dtlCd) {
        
        log.info("상세 코드 상세 조회 시작 - grpCd: {}, dtlCd: {}", grpCd, dtlCd);
        
        try {
            CmnDtlCdOutVo result = cmnCodeService.inqCmnDtlCdByGrpCdAndDtlCd(grpCd, dtlCd);
            
            if (result == null) {
                throw new BizException("상세 코드를 찾을 수 없습니다.");
            }
            
            result.setSuccess(true);
            result.setMessage("상세 코드 정보를 조회했습니다.");
            result.setErrorCode("SUCCESS");
            
            log.info("상세 코드 상세 조회 완료 - grpCd: {}, dtlCd: {}", grpCd, dtlCd);
            return ResponseEntity.ok(result);
            
        } catch (Exception e) {
            log.error("상세 코드 상세 조회 실패 - grpCd: {}, dtlCd: {}", grpCd, dtlCd, e);
            throw new BizException("상세 코드 조회에 실패했습니다: " + e.getMessage());
        }
    }

    /**
     * 상세 코드 등록
     */
    @PostMapping("/detail-codes")
    public ResponseEntity<CmnDtlCdOutVo> rgsnCmnDtlCd(
            @Valid CmnDtlCdInVo inVo,
            HttpServletRequest request) {
        
        log.info("상세 코드 등록 시작 - grpCd: {}, dtlCd: {}", inVo.getGrpCd(), inVo.getDtlCd());
        
        try {
            CmnDtlCdOutVo result = cmnCodeService.rgsnCmnDtlCd(inVo);
            
            result.setSuccess(true);
            result.setMessage("상세 코드가 성공적으로 등록되었습니다.");
            result.setErrorCode("SUCCESS");
            
            log.info("상세 코드 등록 완료 - grpCd: {}, dtlCd: {}", inVo.getGrpCd(), inVo.getDtlCd());
            return ResponseEntity.ok(result);
            
        } catch (Exception e) {
            log.error("상세 코드 등록 실패", e);
            throw new BizException("상세 코드 등록에 실패했습니다: " + e.getMessage());
        }
    }

    /**
     * 상세 코드 수정
     */
    @PutMapping("/detail-codes/{grpCd}/{dtlCd}")
    public ResponseEntity<CmnDtlCdOutVo> mdfcCmnDtlCd(
            @PathVariable String grpCd,
            @PathVariable String dtlCd,
            @Valid CmnDtlCdInVo inVo) {
        
        log.info("상세 코드 수정 시작 - grpCd: {}, dtlCd: {}", grpCd, dtlCd);
        
        try {
            inVo.setGrpCd(grpCd);
            inVo.setDtlCd(dtlCd);
            CmnDtlCdOutVo result = cmnCodeService.mdfcCmnDtlCd(inVo);
            
            if (result == null) {
                throw new BizException("상세 코드 수정에 실패했습니다.");
            }
            
            result.setSuccess(true);
            result.setMessage("상세 코드가 성공적으로 수정되었습니다.");
            result.setErrorCode("SUCCESS");
            
            log.info("상세 코드 수정 완료 - grpCd: {}, dtlCd: {}", grpCd, dtlCd);
            return ResponseEntity.ok(result);
            
        } catch (Exception e) {
            log.error("상세 코드 수정 실패 - grpCd: {}, dtlCd: {}", grpCd, dtlCd, e);
            throw new BizException("상세 코드 수정에 실패했습니다: " + e.getMessage());
        }
    }

    /**
     * 상세 코드 삭제
     */
    @DeleteMapping("/detail-codes/{grpCd}/{dtlCd}")
    public ResponseEntity<CmnDtlCdOutVo> delCmnDtlCd(
            @PathVariable String grpCd,
            @PathVariable String dtlCd) {
        
        log.info("상세 코드 삭제 시작 - grpCd: {}, dtlCd: {}", grpCd, dtlCd);
        
        try {
            boolean result = cmnCodeService.delCmnDtlCd(grpCd, dtlCd);
            
            if (!result) {
                throw new BizException("상세 코드 삭제에 실패했습니다.");
            }
            
            CmnDtlCdOutVo outVo = new CmnDtlCdOutVo();
            outVo.setSuccess(true);
            outVo.setMessage("상세 코드가 성공적으로 삭제되었습니다.");
            outVo.setErrorCode("SUCCESS");
            
            log.info("상세 코드 삭제 완료 - grpCd: {}, dtlCd: {}", grpCd, dtlCd);
            return ResponseEntity.ok(outVo);
            
        } catch (Exception e) {
            log.error("상세 코드 삭제 실패 - grpCd: {}, dtlCd: {}", grpCd, dtlCd, e);
            throw new BizException("상세 코드 삭제에 실패했습니다: " + e.getMessage());
        }
    }

    /**
     * 사용여부별 상세 코드 조회 (페이징)
     */
    @GetMapping("/detail-codes/use-yn/{useYn}")
    public ResponseEntity<CmnDtlCdOutVo> inqCmnDtlCdByUseYn(
            @PathVariable String useYn,
            @Valid PageRequest pageRequest,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String searchField) {
        
        log.info("사용여부별 상세 코드 조회 시작 - useYn: {}", useYn);
        
        try {
            CmnDtlCdOutVo result = cmnCodeService.inqCmnDtlCdByUseYn(useYn, pageRequest, keyword, searchField);
            
            result.setSuccess(true);
            result.setMessage("사용여부별 상세 코드 목록을 조회했습니다.");
            result.setErrorCode("SUCCESS");
            
            log.info("사용여부별 상세 코드 조회 완료 - useYn: {}, 총 {}건", useYn, result.getCount());
            return ResponseEntity.ok(result);
            
        } catch (Exception e) {
            log.error("사용여부별 상세 코드 조회 실패 - useYn: {}", useYn, e);
            throw new BizException("사용여부별 상세 코드 조회에 실패했습니다: " + e.getMessage());
        }
    }

    /**
     * 사용여부별 상세 코드 조회 (페이징 없음)
     */
    @GetMapping("/detail-codes/use-yn/{useYn}/all")
    public ResponseEntity<List<CmnDtlCdOutVo>> inqAllCmnDtlCdByUseYn(@PathVariable String useYn) {
        log.info("전체 사용여부별 상세 코드 조회 시작 - useYn: {}", useYn);
        
        try {
            List<CmnDtlCdOutVo> result = cmnCodeService.inqAllCmnDtlCdByUseYn(useYn);
            
            log.info("전체 사용여부별 상세 코드 조회 완료 - useYn: {}, 총 {}건", useYn, result.size());
            return ResponseEntity.ok(result);
            
        } catch (Exception e) {
            log.error("전체 사용여부별 상세 코드 조회 실패 - useYn: {}", useYn, e);
            throw new BizException("전체 사용여부별 상세 코드 조회에 실패했습니다: " + e.getMessage());
        }
    }

    /**
     * 그룹 코드 및 사용여부별 상세 코드 조회 (페이징)
     */
    @GetMapping("/detail-codes/group/{grpCd}/use-yn/{useYn}")
    public ResponseEntity<CmnDtlCdOutVo> inqCmnDtlCdByGrpCdAndUseYn(
            @PathVariable String grpCd,
            @PathVariable String useYn,
            @Valid PageRequest pageRequest,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String searchField) {
        
        log.info("그룹 코드 및 사용여부별 상세 코드 조회 시작 - grpCd: {}, useYn: {}", grpCd, useYn);
        
        try {
            CmnDtlCdOutVo result = cmnCodeService.inqCmnDtlCdByGrpCdAndUseYn(grpCd, useYn, pageRequest, keyword, searchField);
            
            result.setSuccess(true);
            result.setMessage("그룹 코드 및 사용여부별 상세 코드 목록을 조회했습니다.");
            result.setErrorCode("SUCCESS");
            
            log.info("그룹 코드 및 사용여부별 상세 코드 조회 완료 - grpCd: {}, useYn: {}, 총 {}건", grpCd, useYn, result.getCount());
            return ResponseEntity.ok(result);
            
        } catch (Exception e) {
            log.error("그룹 코드 및 사용여부별 상세 코드 조회 실패 - grpCd: {}, useYn: {}", grpCd, useYn, e);
            throw new BizException("그룹 코드 및 사용여부별 상세 코드 조회에 실패했습니다: " + e.getMessage());
        }
    }

    /**
     * 그룹 코드 및 사용여부별 상세 코드 조회 (페이징 없음)
     */
    @GetMapping("/detail-codes/group/{grpCd}/use-yn/{useYn}/all")
    public ResponseEntity<List<CmnDtlCdOutVo>> inqAllCmnDtlCdByGrpCdAndUseYn(
            @PathVariable String grpCd,
            @PathVariable String useYn) {
        
        log.info("전체 그룹 코드 및 사용여부별 상세 코드 조회 시작 - grpCd: {}, useYn: {}", grpCd, useYn);
        
        try {
            List<CmnDtlCdOutVo> result = cmnCodeService.inqAllCmnDtlCdByGrpCdAndUseYn(grpCd, useYn);
            
            log.info("전체 그룹 코드 및 사용여부별 상세 코드 조회 완료 - grpCd: {}, useYn: {}, 총 {}건", grpCd, useYn, result.size());
            return ResponseEntity.ok(result);
            
        } catch (Exception e) {
            log.error("전체 그룹 코드 및 사용여부별 상세 코드 조회 실패 - grpCd: {}, useYn: {}", grpCd, useYn, e);
            throw new BizException("전체 그룹 코드 및 사용여부별 상세 코드 조회에 실패했습니다: " + e.getMessage());
        }
    }
}
