package com.ibkcloud.eoc.controller;

import com.ibkcloud.eoc.cmm.dto.PageRequest;
import com.ibkcloud.eoc.cmm.exception.BizException;
import com.ibkcloud.eoc.controller.vo.*;
import com.ibkcloud.eoc.service.MnuLstService;
import com.ibkcloud.eoc.util.SystemLogUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import java.util.List;

/**
 * 메뉴 관리 컨트롤러
 */
@Slf4j
@RestController
@RequestMapping("/api/menu")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@RequiredArgsConstructor
public class MnuLstController {

    private final MnuLstService mnuLstService;
    private final SystemLogUtil systemLogUtil;

    /**
     * 메뉴 목록 조회 (계층 구조)
     */
    @GetMapping("/list")
    public ResponseEntity<MnuLstOutVo> inqMenuList() {
        log.info("메뉴 목록 조회 시작");
        
        try {
            MnuLstOutVo result = mnuLstService.inqMenuList();
            
            result.setSuccess(true);
            result.setMessage("메뉴 목록을 조회했습니다.");
            result.setErrorCode("SUCCESS");
            
            log.info("메뉴 목록 조회 완료 - 총 {}건", result.getCount());
            return ResponseEntity.ok(result);
            
        } catch (Exception e) {
            log.error("메뉴 목록 조회 실패", e);
            throw new BizException("메뉴 목록 조회에 실패했습니다: " + e.getMessage());
        }
    }

    /**
     * 전체 메뉴 목록 조회 (사용자 권한에 따른 필터링)
     */
    @GetMapping("/all")
    public ResponseEntity<MnuLstOutVo> inqAllMenus(HttpServletRequest request) {
        log.info("전체 메뉴 목록 조회 시작");
        
        try {
            // AuthInterceptor에서 전달받은 사용자 정보 사용
            String empId = (String) request.getAttribute("EMP_ID");
            Boolean isAdmin = (Boolean) request.getAttribute("isAdmin");
            Boolean isCounselor = (Boolean) request.getAttribute("isCounselor");
            
            if (empId == null) {
                throw new BizException("인증 정보를 찾을 수 없습니다.");
            }
            
            // 사용자 권한 타입 결정
            String userAuthType = determineUserAuthType(isAdmin, isCounselor);
            
            log.info("메뉴 목록 조회 - 사용자: {}, 관리자여부: {}, 상담사여부: {}, 권한타입: {}", 
                    empId, isAdmin, isCounselor, userAuthType);
            
            MnuLstOutVo result = mnuLstService.inqAllMenus(userAuthType);
            
            result.setSuccess(true);
            result.setMessage("전체 메뉴 목록을 조회했습니다.");
            result.setErrorCode("SUCCESS");
            
            log.info("전체 메뉴 목록 조회 완료 - 총 {}건", result.getCount());
            return ResponseEntity.ok(result);
            
        } catch (Exception e) {
            log.error("전체 메뉴 목록 조회 실패", e);
            throw new BizException("메뉴 목록 조회에 실패했습니다: " + e.getMessage());
        }
    }

    /**
     * 사용자 권한 타입 결정
     * @param isAdmin 관리자 여부
     * @param isCounselor 상담사 여부
     * @return 권한 타입 (USER, COUNSELOR, ADMIN)
     */
    private String determineUserAuthType(Boolean isAdmin, Boolean isCounselor) {
        if (isAdmin != null && isAdmin) {
            return "ADMIN";
        } else if (isCounselor != null && isCounselor) {
            return "COUNSELOR";
        } else {
            return "USER";
        }
    }

    /**
     * 메뉴 목록 조회 (페이징)
     */
    @GetMapping("/list/paging")
    public ResponseEntity<MnuLstOutVo> inqMenuListWithPaging(
            @Valid PageRequest pageRequest,
            @RequestParam(required = false) String searchKeyword,
            @RequestParam(required = false) Integer mnuLvl,
            @RequestParam(required = false) String mnuUseYn,
            @RequestParam(required = false) String mnuAuthType) {
        
        log.info("메뉴 목록 조회 (페이징) 시작 - searchKeyword: {}, mnuLvl: {}, mnuUseYn: {}, mnuAuthType: {}", 
                searchKeyword, mnuLvl, mnuUseYn, mnuAuthType);
        
        try {
            MnuLstInVo searchCondition = new MnuLstInVo();
            searchCondition.setSearchKeyword(searchKeyword);
            searchCondition.setMnuLvl(mnuLvl);
            searchCondition.setMnuUseYn(mnuUseYn);
            searchCondition.setMnuAuthType(mnuAuthType);
            
            MnuLstOutVo result = mnuLstService.inqMenuListWithPaging(pageRequest, searchCondition);
            
            result.setSuccess(true);
            result.setMessage("메뉴 목록을 조회했습니다.");
            result.setErrorCode("SUCCESS");
            
            log.info("메뉴 목록 조회 (페이징) 완료 - 총 {}건", result.getCount());
            return ResponseEntity.ok(result);
            
        } catch (Exception e) {
            log.error("메뉴 목록 조회 (페이징) 실패", e);
            throw new BizException("메뉴 목록 조회에 실패했습니다: " + e.getMessage());
        }
    }

    /**
     * 메뉴 상세 조회
     */
    @GetMapping("/{mnuCd}")
    public ResponseEntity<MnuLstOutVo> inqMenuByCd(@PathVariable String mnuCd) {
        log.info("메뉴 상세 조회 시작 - mnuCd: {}", mnuCd);
        
        try {
            MnuLstOutVo result = mnuLstService.inqMenuByCd(mnuCd);
            
            if (result == null) {
                throw new BizException("메뉴를 찾을 수 없습니다.");
            }
            
            result.setSuccess(true);
            result.setMessage("메뉴 정보를 조회했습니다.");
            result.setErrorCode("SUCCESS");
            
            log.info("메뉴 상세 조회 완료 - mnuCd: {}", mnuCd);
            return ResponseEntity.ok(result);
            
        } catch (Exception e) {
            log.error("메뉴 상세 조회 실패 - mnuCd: {}", mnuCd, e);
            throw new BizException("메뉴 조회에 실패했습니다: " + e.getMessage());
        }
    }

    /**
     * 상위 메뉴 목록 조회
     */
    @GetMapping("/parent")
    public ResponseEntity<MnuLstOutVo> inqParentMenuList() {
        log.info("상위 메뉴 목록 조회 시작");
        
        try {
            MnuLstOutVo result = mnuLstService.inqParentMenuList();
            
            result.setSuccess(true);
            result.setMessage("상위 메뉴 목록을 조회했습니다.");
            result.setErrorCode("SUCCESS");
            
            log.info("상위 메뉴 목록 조회 완료 - 총 {}건", result.getCount());
            return ResponseEntity.ok(result);
            
        } catch (Exception e) {
            log.error("상위 메뉴 목록 조회 실패", e);
            throw new BizException("상위 메뉴 목록 조회에 실패했습니다: " + e.getMessage());
        }
    }

    /**
     * 하위 메뉴 목록 조회
     */
    @GetMapping("/sub/{pMnuCd}")
    public ResponseEntity<MnuLstOutVo> inqSubMenuList(@PathVariable String pMnuCd) {
        log.info("하위 메뉴 목록 조회 시작 - pMnuCd: {}", pMnuCd);
        
        try {
            MnuLstOutVo result = mnuLstService.inqSubMenuList(pMnuCd);
            
            result.setSuccess(true);
            result.setMessage("하위 메뉴 목록을 조회했습니다.");
            result.setErrorCode("SUCCESS");
            
            log.info("하위 메뉴 목록 조회 완료 - pMnuCd: {}, 총 {}건", pMnuCd, result.getCount());
            return ResponseEntity.ok(result);
            
        } catch (Exception e) {
            log.error("하위 메뉴 목록 조회 실패 - pMnuCd: {}", pMnuCd, e);
            throw new BizException("하위 메뉴 목록 조회에 실패했습니다: " + e.getMessage());
        }
    }

    /**
     * 메뉴 등록
     */
    @PostMapping
    public ResponseEntity<MnuLstOutVo> rgsnMenu(@RequestBody MnuLstInVo mnuLstInVo,
                                                HttpServletRequest request) {
        log.info("메뉴 등록 시작 - mnuCd: {}, mnuNm: {}", mnuLstInVo.getMnuCd(), mnuLstInVo.getMnuNm());
        
        try {
            // AuthInterceptor에서 전달받은 사용자 정보 사용
            String empId = (String) request.getAttribute("EMP_ID");
            if (empId == null) {
                throw new BizException("인증 정보를 찾을 수 없습니다.");
            }
            
            mnuLstInVo.setEmpId(empId);
            
            MnuLstOutVo result = mnuLstService.rgsnMenu(mnuLstInVo);
            
            result.setSuccess(true);
            result.setMessage("메뉴가 성공적으로 등록되었습니다.");
            result.setErrorCode("SUCCESS");
            
            // 시스템 로그 기록
            systemLogUtil.logSystemAction(empId, "MENU_CREATE", "메뉴 등록", 
                    "메뉴코드: " + mnuLstInVo.getMnuCd() + ", 메뉴명: " + mnuLstInVo.getMnuNm());
            
            log.info("메뉴 등록 완료 - mnuCd: {}", mnuLstInVo.getMnuCd());
            return ResponseEntity.ok(result);
            
        } catch (Exception e) {
            log.error("메뉴 등록 실패 - mnuCd: {}", mnuLstInVo.getMnuCd(), e);
            throw new BizException("메뉴 등록에 실패했습니다: " + e.getMessage());
        }
    }

    /**
     * 메뉴 수정
     */
    @PutMapping("/{mnuCd}")
    public ResponseEntity<MnuLstOutVo> mdfcMenu(@PathVariable String mnuCd,
                                                @RequestBody MnuLstInVo mnuLstInVo,
                                                HttpServletRequest request) {
        log.info("메뉴 수정 시작 - mnuCd: {}", mnuCd);
        
        try {
            // AuthInterceptor에서 전달받은 사용자 정보 사용
            String empId = (String) request.getAttribute("EMP_ID");
            if (empId == null) {
                throw new BizException("인증 정보를 찾을 수 없습니다.");
            }
            
            mnuLstInVo.setMnuCd(mnuCd);
            mnuLstInVo.setEmpId(empId);
            
            MnuLstOutVo result = mnuLstService.mdfcMenu(mnuLstInVo);
            
            result.setSuccess(true);
            result.setMessage("메뉴가 성공적으로 수정되었습니다.");
            result.setErrorCode("SUCCESS");
            
            // 시스템 로그 기록
            systemLogUtil.logSystemAction(empId, "MENU_UPDATE", "메뉴 수정", 
                    "메뉴코드: " + mnuCd + ", 메뉴명: " + mnuLstInVo.getMnuNm());
            
            log.info("메뉴 수정 완료 - mnuCd: {}", mnuCd);
            return ResponseEntity.ok(result);
            
        } catch (Exception e) {
            log.error("메뉴 수정 실패 - mnuCd: {}", mnuCd, e);
            throw new BizException("메뉴 수정에 실패했습니다: " + e.getMessage());
        }
    }

    /**
     * 메뉴 삭제
     */
    @DeleteMapping("/{mnuCd}")
    public ResponseEntity<MnuLstOutVo> delMenu(@PathVariable String mnuCd,
                                              HttpServletRequest request) {
        log.info("메뉴 삭제 시작 - mnuCd: {}", mnuCd);
        
        try {
            // AuthInterceptor에서 전달받은 사용자 정보 사용
            String empId = (String) request.getAttribute("EMP_ID");
            if (empId == null) {
                throw new BizException("인증 정보를 찾을 수 없습니다.");
            }
            
            MnuLstOutVo result = mnuLstService.delMenu(mnuCd, empId);
            
            result.setSuccess(true);
            result.setMessage("메뉴가 성공적으로 삭제되었습니다.");
            result.setErrorCode("SUCCESS");
            
            // 시스템 로그 기록
            systemLogUtil.logSystemAction(empId, "MENU_DELETE", "메뉴 삭제", 
                    "메뉴코드: " + mnuCd);
            
            log.info("메뉴 삭제 완료 - mnuCd: {}", mnuCd);
            return ResponseEntity.ok(result);
            
        } catch (Exception e) {
            log.error("메뉴 삭제 실패 - mnuCd: {}", mnuCd, e);
            throw new BizException("메뉴 삭제에 실패했습니다: " + e.getMessage());
        }
    }

    /**
     * 메뉴 순서 수정
     */
    @PutMapping("/{mnuCd}/order")
    public ResponseEntity<MnuLstOutVo> mdfcMenuOrder(@PathVariable String mnuCd,
                                                     @RequestParam Integer mnuOrd,
                                                     HttpServletRequest request) {
        log.info("메뉴 순서 수정 시작 - mnuCd: {}, mnuOrd: {}", mnuCd, mnuOrd);
        
        try {
            // AuthInterceptor에서 전달받은 사용자 정보 사용
            String empId = (String) request.getAttribute("EMP_ID");
            if (empId == null) {
                throw new BizException("인증 정보를 찾을 수 없습니다.");
            }
            
            MnuLstInVo mnuLstInVo = new MnuLstInVo();
            mnuLstInVo.setMnuCd(mnuCd);
            mnuLstInVo.setMnuOrd(mnuOrd);
            mnuLstInVo.setEmpId(empId);
            
            MnuLstOutVo result = mnuLstService.mdfcMenuOrder(mnuLstInVo);
            
            result.setSuccess(true);
            result.setMessage("메뉴 순서가 성공적으로 수정되었습니다.");
            result.setErrorCode("SUCCESS");
            
            // 시스템 로그 기록
            systemLogUtil.logSystemAction(empId, "MENU_ORDER_UPDATE", "메뉴 순서 수정", 
                    "메뉴코드: " + mnuCd + ", 순서: " + mnuOrd);
            
            log.info("메뉴 순서 수정 완료 - mnuCd: {}, mnuOrd: {}", mnuCd, mnuOrd);
            return ResponseEntity.ok(result);
            
        } catch (Exception e) {
            log.error("메뉴 순서 수정 실패 - mnuCd: {}, mnuOrd: {}", mnuCd, mnuOrd, e);
            throw new BizException("메뉴 순서 수정에 실패했습니다: " + e.getMessage());
        }
    }

    /**
     * 사용자별 접근 가능한 메뉴 조회
     */
    @GetMapping("/user/{empId}")
    public ResponseEntity<MnuLstOutVo> inqUserAccessibleMenus(@PathVariable String empId,
                                                             HttpServletRequest request) {
        log.info("사용자별 접근 가능한 메뉴 조회 시작 - empId: {}", empId);
        
        try {
            MnuLstOutVo result = mnuLstService.inqUserAccessibleMenus(empId);
            
            result.setSuccess(true);
            result.setMessage("사용자별 접근 가능한 메뉴를 조회했습니다.");
            result.setErrorCode("SUCCESS");
            
            log.info("사용자별 접근 가능한 메뉴 조회 완료 - empId: {}, 총 {}건", empId, result.getCount());
            return ResponseEntity.ok(result);
            
        } catch (Exception e) {
            log.error("사용자별 접근 가능한 메뉴 조회 실패 - empId: {}", empId, e);
            throw new BizException("사용자별 접근 가능한 메뉴 조회에 실패했습니다: " + e.getMessage());
        }
    }
} 