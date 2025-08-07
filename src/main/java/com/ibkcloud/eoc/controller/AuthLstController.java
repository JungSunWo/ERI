package com.ibkcloud.eoc.controller;

import com.ibkcloud.eoc.cmm.dto.PageRequest;
import com.ibkcloud.eoc.cmm.exception.BizException;
import com.ibkcloud.eoc.controller.vo.*;
import com.ibkcloud.eoc.service.AuthLstService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import java.util.List;

/**
 * 권한 관리 컨트롤러
 */
@Slf4j
@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@RequiredArgsConstructor
public class AuthLstController {

    private final AuthLstService authLstService;

    /**
     * 권한 목록 조회 (페이징)
     */
    @GetMapping("/list")
    public ResponseEntity<AuthLstOutVo> inqAuthLstList(
            @Valid PageRequest pageRequest,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String searchField) {
        
        log.info("권한 목록 조회 시작 - keyword: {}, searchField: {}", keyword, searchField);
        
        try {
            AuthLstOutVo result = authLstService.inqAuthLstList(pageRequest, keyword, searchField);
            
            result.setSuccess(true);
            result.setMessage("권한 목록을 조회했습니다.");
            result.setErrorCode("SUCCESS");
            
            log.info("권한 목록 조회 완료 - 총 {}건", result.getCount());
            return ResponseEntity.ok(result);
            
        } catch (Exception e) {
            log.error("권한 목록 조회 실패", e);
            throw new BizException("권한 목록 조회에 실패했습니다: " + e.getMessage());
        }
    }

    /**
     * 전체 권한 목록 조회 (페이징 없음)
     */
    @GetMapping("/all")
    public ResponseEntity<List<AuthLstOutVo>> inqAllAuthLstList() {
        log.info("전체 권한 목록 조회 시작");
        
        try {
            List<AuthLstOutVo> result = authLstService.inqAllAuthLstList();
            
            log.info("전체 권한 목록 조회 완료 - 총 {}건", result.size());
            return ResponseEntity.ok(result);
            
        } catch (Exception e) {
            log.error("전체 권한 목록 조회 실패", e);
            throw new BizException("전체 권한 목록 조회에 실패했습니다: " + e.getMessage());
        }
    }

    /**
     * 권한 상세 조회
     */
    @GetMapping("/{authCd}")
    public ResponseEntity<AuthLstOutVo> inqAuthLstByAuthCd(@PathVariable String authCd) {
        log.info("권한 상세 조회 시작 - authCd: {}", authCd);
        
        try {
            AuthLstOutVo result = authLstService.inqAuthLstByAuthCd(authCd);
            
            if (result == null) {
                throw new BizException("권한 코드 '" + authCd + "'를 찾을 수 없습니다.");
            }
            
            result.setSuccess(true);
            result.setMessage("권한 정보를 조회했습니다.");
            result.setErrorCode("SUCCESS");
            
            log.info("권한 상세 조회 완료 - authCd: {}", authCd);
            return ResponseEntity.ok(result);
            
        } catch (Exception e) {
            log.error("권한 상세 조회 실패 - authCd: {}", authCd, e);
            throw new BizException("권한 조회에 실패했습니다: " + e.getMessage());
        }
    }

    /**
     * 권한 등록
     */
    @PostMapping
    public ResponseEntity<AuthLstOutVo> rgsnAuthLst(
            @Valid AuthLstInVo inVo,
            HttpServletRequest request) {
        
        log.info("권한 등록 시작 - authCd: {}", inVo.getAuthCd());
        
        try {
            // 권한 코드 중복 확인
            if (authLstService.isAuthCdExists(inVo.getAuthCd())) {
                throw new BizException("이미 존재하는 권한 코드입니다.");
            }
            
            // 권한레벨 유효성 검사
            if (!authLstService.isValidAuthLvl(inVo.getAuthLvl())) {
                throw new BizException("권한레벨은 1~10 사이의 값이어야 합니다.");
            }
            
            AuthLstOutVo result = authLstService.rgsnAuthLst(inVo);
            
            result.setSuccess(true);
            result.setMessage("권한이 성공적으로 등록되었습니다.");
            result.setErrorCode("SUCCESS");
            
            log.info("권한 등록 완료 - authCd: {}", inVo.getAuthCd());
            return ResponseEntity.ok(result);
            
        } catch (Exception e) {
            log.error("권한 등록 실패", e);
            throw new BizException("권한 등록에 실패했습니다: " + e.getMessage());
        }
    }

    /**
     * 권한 수정
     */
    @PutMapping("/{authCd}")
    public ResponseEntity<AuthLstOutVo> mdfcAuthLst(
            @PathVariable String authCd,
            @Valid AuthLstInVo inVo) {
        
        log.info("권한 수정 시작 - authCd: {}", authCd);
        
        try {
            inVo.setAuthCd(authCd);
            
            // 권한레벨 유효성 검사
            if (!authLstService.isValidAuthLvl(inVo.getAuthLvl())) {
                throw new BizException("권한레벨은 1~10 사이의 값이어야 합니다.");
            }
            
            AuthLstOutVo result = authLstService.mdfcAuthLst(inVo);
            
            if (result == null) {
                throw new BizException("권한 수정에 실패했습니다.");
            }
            
            result.setSuccess(true);
            result.setMessage("권한이 성공적으로 수정되었습니다.");
            result.setErrorCode("SUCCESS");
            
            log.info("권한 수정 완료 - authCd: {}", authCd);
            return ResponseEntity.ok(result);
            
        } catch (Exception e) {
            log.error("권한 수정 실패 - authCd: {}", authCd, e);
            throw new BizException("권한 수정에 실패했습니다: " + e.getMessage());
        }
    }

    /**
     * 권한 삭제
     */
    @DeleteMapping("/{authCd}")
    public ResponseEntity<AuthLstOutVo> delAuthLst(@PathVariable String authCd) {
        log.info("권한 삭제 시작 - authCd: {}", authCd);
        
        try {
            boolean result = authLstService.delAuthLst(authCd);
            
            if (!result) {
                throw new BizException("권한 삭제에 실패했습니다.");
            }
            
            AuthLstOutVo outVo = new AuthLstOutVo();
            outVo.setSuccess(true);
            outVo.setMessage("권한이 성공적으로 삭제되었습니다.");
            outVo.setErrorCode("SUCCESS");
            
            log.info("권한 삭제 완료 - authCd: {}", authCd);
            return ResponseEntity.ok(outVo);
            
        } catch (Exception e) {
            log.error("권한 삭제 실패 - authCd: {}", authCd, e);
            throw new BizException("권한 삭제에 실패했습니다: " + e.getMessage());
        }
    }

    /**
     * 권한 레벨별 권한 목록 조회
     */
    @GetMapping("/level/{authLvl}")
    public ResponseEntity<List<AuthLstOutVo>> inqAuthLstByLevel(@PathVariable Integer authLvl) {
        log.info("권한 레벨별 권한 목록 조회 시작 - authLvl: {}", authLvl);
        
        try {
            List<AuthLstOutVo> result = authLstService.inqAuthLstByLevel(authLvl);
            
            log.info("권한 레벨별 권한 목록 조회 완료 - authLvl: {}, 총 {}건", authLvl, result.size());
            return ResponseEntity.ok(result);
            
        } catch (Exception e) {
            log.error("권한 레벨별 권한 목록 조회 실패 - authLvl: {}", authLvl, e);
            throw new BizException("권한 레벨별 권한 목록 조회에 실패했습니다: " + e.getMessage());
        }
    }

    /**
     * 권한 레벨보다 큰 권한 목록 조회
     */
    @GetMapping("/level/greater-than/{authLvl}")
    public ResponseEntity<List<AuthLstOutVo>> inqAuthLstByLevelGreaterThan(@PathVariable Integer authLvl) {
        log.info("권한 레벨보다 큰 권한 목록 조회 시작 - authLvl: {}", authLvl);
        
        try {
            List<AuthLstOutVo> result = authLstService.inqAuthLstByLevelGreaterThan(authLvl);
            
            log.info("권한 레벨보다 큰 권한 목록 조회 완료 - authLvl: {}, 총 {}건", authLvl, result.size());
            return ResponseEntity.ok(result);
            
        } catch (Exception e) {
            log.error("권한 레벨보다 큰 권한 목록 조회 실패 - authLvl: {}", authLvl, e);
            throw new BizException("권한 레벨보다 큰 권한 목록 조회에 실패했습니다: " + e.getMessage());
        }
    }

    /**
     * 권한 코드 존재 여부 확인
     */
    @GetMapping("/check/{authCd}")
    public ResponseEntity<AuthLstOutVo> inqAuthCdExists(@PathVariable String authCd) {
        log.info("권한 코드 존재 여부 확인 시작 - authCd: {}", authCd);
        
        try {
            boolean exists = authLstService.isAuthCdExists(authCd);
            
            AuthLstOutVo result = new AuthLstOutVo();
            result.setAuthCd(authCd);
            result.setExists(exists);
            result.setSuccess(true);
            result.setMessage(exists ? "존재하는 권한 코드입니다." : "존재하지 않는 권한 코드입니다.");
            result.setErrorCode("SUCCESS");
            
            log.info("권한 코드 존재 여부 확인 완료 - authCd: {}, exists: {}", authCd, exists);
            return ResponseEntity.ok(result);
            
        } catch (Exception e) {
            log.error("권한 코드 존재 여부 확인 실패 - authCd: {}", authCd, e);
            throw new BizException("권한 코드 존재 여부 확인에 실패했습니다: " + e.getMessage());
        }
    }
}
