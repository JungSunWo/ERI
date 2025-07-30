package com.ERI.demo.Controller;

import com.ERI.demo.service.MnuLstService;
import com.ERI.demo.util.SystemLogUtil;
import com.ERI.demo.vo.MnuLstVO;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/menu")
@RequiredArgsConstructor
public class MnuLstController {

    private final MnuLstService mnuLstService;
    private final SystemLogUtil systemLogUtil;

    /**
     * 메뉴 목록 조회 (계층 구조)
     */
    @GetMapping("/list")
    public ResponseEntity<Map<String, Object>> getMenuList() {
        Map<String, Object> response = new HashMap<>();
        
        try {
            List<MnuLstVO> menuList = mnuLstService.getMenuList();
            
            response.put("success", true);
            response.put("data", menuList);
            response.put("message", "메뉴 목록을 조회했습니다.");
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            log.error("메뉴 목록 조회 실패", e);
            
            response.put("success", false);
            response.put("message", "메뉴 목록 조회에 실패했습니다: " + e.getMessage());
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * 전체 메뉴 목록 조회 (사용자 권한에 따른 필터링)
     */
    @GetMapping("/all")
    public ResponseEntity<Map<String, Object>> getAllMenus(HttpServletRequest request) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            // AuthInterceptor에서 전달받은 사용자 정보 사용
            String empId = (String) request.getAttribute("EMP_ID");
            Boolean isAdmin = (Boolean) request.getAttribute("isAdmin");
            Boolean isCounselor = (Boolean) request.getAttribute("isCounselor");
            
            if (empId == null) {
                response.put("success", false);
                response.put("message", "인증 정보를 찾을 수 없습니다.");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }
            
            // 사용자 권한 타입 결정
            String userAuthType = determineUserAuthType(isAdmin, isCounselor);
            
            log.info("메뉴 목록 조회 - 사용자: {}, 관리자여부: {}, 상담사여부: {}, 권한타입: {}", 
                    empId, isAdmin, isCounselor, userAuthType);
            
            List<MnuLstVO> menuList = mnuLstService.getAllMenus(userAuthType);
            
            response.put("success", true);
            response.put("data", menuList);
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            log.error("메뉴 목록 조회 실패", e);
            
            response.put("success", false);
            response.put("message", "메뉴 목록 조회에 실패했습니다: " + e.getMessage());
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * 사용자 권한 타입 결정
     * @param isAdmin 관리자 여부
     * @param isCounselor 상담사 여부
     * @return 권한 타입 (USER, COUNSELOR, ADMIN)
     */
    private String determineUserAuthType(Boolean isAdmin, Boolean isCounselor) {
        // null 체크 및 기본값 설정
        if (isAdmin == null) isAdmin = false;
        if (isCounselor == null) isCounselor = false;
        
        // 권한 우선순위: 관리자 > 상담사 > 일반사용자
        if (isAdmin) {
            return "ADMIN";
        } else if (isCounselor) {
            return "COUNSELOR";
        } else {
            return "USER";
        }
    }

    /**
     * 메뉴 목록 조회 (페이징)
     */
    @GetMapping("/list/paging")
    public ResponseEntity<Map<String, Object>> getMenuListWithPaging(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String searchKeyword,
            @RequestParam(required = false) Integer mnuLvl,
            @RequestParam(required = false) String mnuUseYn,
            @RequestParam(required = false) String mnuAuthType) {
        
        Map<String, Object> response = new HashMap<>();
        
        try {
            List<MnuLstVO> menuList = mnuLstService.getMenuListWithPaging(page, size, searchKeyword, mnuLvl, mnuUseYn, mnuAuthType);
            int totalCount = mnuLstService.getMenuCount(searchKeyword, mnuLvl, mnuUseYn, mnuAuthType);
            
            response.put("success", true);
            response.put("data", menuList);
            response.put("totalCount", totalCount);
            response.put("currentPage", page);
            response.put("pageSize", size);
            response.put("message", "메뉴 목록을 조회했습니다.");
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            log.error("메뉴 목록 조회 실패", e);
            
            response.put("success", false);
            response.put("message", "메뉴 목록 조회에 실패했습니다: " + e.getMessage());
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * 메뉴 상세 조회
     */
    @GetMapping("/{mnuCd}")
    public ResponseEntity<Map<String, Object>> getMenuByCd(@PathVariable String mnuCd) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            MnuLstVO menu = mnuLstService.getMenuByCd(mnuCd);
            
            if (menu != null) {
                response.put("success", true);
                response.put("data", menu);
                response.put("message", "메뉴 정보를 조회했습니다.");
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("message", "존재하지 않는 메뉴입니다.");
                return ResponseEntity.notFound().build();
            }
            
        } catch (Exception e) {
            log.error("메뉴 상세 조회 실패", e);
            
            response.put("success", false);
            response.put("message", "메뉴 상세 조회에 실패했습니다: " + e.getMessage());
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * 상위 메뉴 목록 조회 (1depth 메뉴만)
     */
    @GetMapping("/parent")
    public ResponseEntity<Map<String, Object>> getParentMenuList() {
        Map<String, Object> response = new HashMap<>();
        
        try {
            List<MnuLstVO> menuList = mnuLstService.getParentMenuList();
            
            response.put("success", true);
            response.put("data", menuList);
            response.put("message", "상위 메뉴 목록을 조회했습니다.");
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            log.error("상위 메뉴 목록 조회 실패", e);
            
            response.put("success", false);
            response.put("message", "상위 메뉴 목록 조회에 실패했습니다: " + e.getMessage());
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * 하위 메뉴 목록 조회
     */
    @GetMapping("/sub/{pMnuCd}")
    public ResponseEntity<Map<String, Object>> getSubMenuList(@PathVariable String pMnuCd) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            List<MnuLstVO> menuList = mnuLstService.getSubMenuList(pMnuCd);
            
            response.put("success", true);
            response.put("data", menuList);
            response.put("message", "하위 메뉴 목록을 조회했습니다.");
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            log.error("하위 메뉴 목록 조회 실패", e);
            
            response.put("success", false);
            response.put("message", "하위 메뉴 목록 조회에 실패했습니다: " + e.getMessage());
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * 메뉴 등록
     */
    @PostMapping
    public ResponseEntity<Map<String, Object>> insertMenu(@RequestBody MnuLstVO mnuLstVO,
                                                         HttpServletRequest request) {
        Map<String, Object> response = new HashMap<>();
        String empId = null;
        
        try {
            // AuthInterceptor에서 전달받은 사용자 정보 사용
            empId = (String) request.getAttribute("EMP_ID");
            if (empId == null) {
                response.put("success", false);
                response.put("message", "인증 정보를 찾을 수 없습니다.");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }
            
            // 받은 데이터 로그 출력
            log.info("메뉴 등록 요청 데이터: mnuCd={}, mnuNm={}, mnuLvl={}, pMnuCd={}, empId={}", 
                    mnuLstVO.getMnuCd(), mnuLstVO.getMnuNm(), mnuLstVO.getMnuLvl(), 
                    mnuLstVO.getPMnuCd(), empId);
            
            // 전체 VO 객체 로그 출력
            log.info("전체 MnuLstVO 객체: {}", mnuLstVO);
            
            boolean result = mnuLstService.insertMenu(mnuLstVO, empId);
            
            if (result) {
                // 성공 로그 적재
                systemLogUtil.logApiSuccess("메뉴 등록", 
                    String.format("메뉴 코드: %s, 메뉴명: %s", mnuLstVO.getMnuCd(), mnuLstVO.getMnuNm()),
                    this.getClass().getName(), "insertMenu", empId, request);
                
                response.put("success", true);
                response.put("message", "메뉴가 성공적으로 등록되었습니다.");
                return ResponseEntity.ok(response);
            } else {
                // 실패 로그 적재
                systemLogUtil.logApiError("메뉴 등록", 
                    String.format("메뉴 등록 실패 - 메뉴 코드: %s", mnuLstVO.getMnuCd()),
                    this.getClass().getName(), "insertMenu", empId, "MENU_INSERT_FAILED",
                    new Exception("메뉴 등록 처리 실패"), request);
                
                response.put("success", false);
                response.put("message", "메뉴 등록에 실패했습니다.");
                return ResponseEntity.badRequest().body(response);
            }
            
        } catch (IllegalArgumentException e) {
            log.warn("메뉴 등록 유효성 검사 실패: {}", e.getMessage());
            
            // 유효성 검사 실패 로그 적재
            systemLogUtil.logApiError("메뉴 등록", 
                String.format("유효성 검사 실패 - 메뉴 코드: %s, 오류: %s", 
                    mnuLstVO != null ? mnuLstVO.getMnuCd() : "UNKNOWN", e.getMessage()),
                this.getClass().getName(), "insertMenu", empId, "VALIDATION_ERROR", e, request);
            
            response.put("success", false);
            response.put("message", e.getMessage());
            
            return ResponseEntity.badRequest().body(response);
            
        } catch (Exception e) {
            log.error("메뉴 등록 실패", e);
            
            // 예외 발생 로그 적재
            systemLogUtil.logApiError("메뉴 등록", 
                String.format("예외 발생 - 메뉴 코드: %s, 오류: %s", 
                    mnuLstVO != null ? mnuLstVO.getMnuCd() : "UNKNOWN", e.getMessage()),
                this.getClass().getName(), "insertMenu", empId, "EXCEPTION_ERROR", e, request);
            
            response.put("success", false);
            response.put("message", "메뉴 등록에 실패했습니다: " + e.getMessage());
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * 메뉴 수정
     */
    @PutMapping("/{mnuCd}")
    public ResponseEntity<Map<String, Object>> updateMenu(@PathVariable String mnuCd,
                                                         @RequestBody MnuLstVO mnuLstVO,
                                                         HttpServletRequest request) {
        Map<String, Object> response = new HashMap<>();
        String empId = null;
        
        try {
            // AuthInterceptor에서 전달받은 사용자 정보 사용
            empId = (String) request.getAttribute("EMP_ID");
            if (empId == null) {
                response.put("success", false);
                response.put("message", "인증 정보를 찾을 수 없습니다.");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }
            
            mnuLstVO.setMnuCd(mnuCd);
            
            // 받은 데이터 로그 출력
            log.info("메뉴 수정 요청 데이터: mnuCd={}, mnuNm={}, mnuLvl={}, pMnuCd={}, empId={}", 
                    mnuLstVO.getMnuCd(), mnuLstVO.getMnuNm(), mnuLstVO.getMnuLvl(), 
                    mnuLstVO.getPMnuCd(), empId);
            
            boolean result = mnuLstService.updateMenu(mnuLstVO, empId);
            
            if (result) {
                // 성공 로그 적재
                systemLogUtil.logApiSuccess("메뉴 수정", 
                    String.format("메뉴 코드: %s, 메뉴명: %s", mnuLstVO.getMnuCd(), mnuLstVO.getMnuNm()),
                    this.getClass().getName(), "updateMenu", empId, request);
                
                response.put("success", true);
                response.put("message", "메뉴가 성공적으로 수정되었습니다.");
                return ResponseEntity.ok(response);
            } else {
                // 실패 로그 적재
                systemLogUtil.logApiError("메뉴 수정", 
                    String.format("메뉴 수정 실패 - 메뉴 코드: %s", mnuLstVO.getMnuCd()),
                    this.getClass().getName(), "updateMenu", empId, "MENU_UPDATE_FAILED",
                    new Exception("메뉴 수정 처리 실패"), request);
                
                response.put("success", false);
                response.put("message", "메뉴 수정에 실패했습니다.");
                return ResponseEntity.badRequest().body(response);
            }
            
        } catch (IllegalArgumentException e) {
            log.warn("메뉴 수정 유효성 검사 실패: {}", e.getMessage());
            
            // 유효성 검사 실패 로그 적재
            systemLogUtil.logApiError("메뉴 수정", 
                String.format("유효성 검사 실패 - 메뉴 코드: %s, 오류: %s", mnuCd, e.getMessage()),
                this.getClass().getName(), "updateMenu", empId, "VALIDATION_ERROR", e, request);
            
            response.put("success", false);
            response.put("message", e.getMessage());
            
            return ResponseEntity.badRequest().body(response);
            
        } catch (Exception e) {
            log.error("메뉴 수정 실패", e);
            
            // 예외 발생 로그 적재
            systemLogUtil.logApiError("메뉴 수정", 
                String.format("예외 발생 - 메뉴 코드: %s, 오류: %s", mnuCd, e.getMessage()),
                this.getClass().getName(), "updateMenu", empId, "EXCEPTION_ERROR", e, request);
            
            response.put("success", false);
            response.put("message", "메뉴 수정에 실패했습니다: " + e.getMessage());
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * 메뉴 삭제
     */
    @DeleteMapping("/{mnuCd}")
    public ResponseEntity<Map<String, Object>> deleteMenu(@PathVariable String mnuCd,
                                                         HttpServletRequest request) {
        Map<String, Object> response = new HashMap<>();
        String empId = null;
        
        try {
            // AuthInterceptor에서 전달받은 사용자 정보 사용
            empId = (String) request.getAttribute("EMP_ID");
            if (empId == null) {
                response.put("success", false);
                response.put("message", "인증 정보를 찾을 수 없습니다.");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }
            
            boolean result = mnuLstService.deleteMenu(mnuCd, empId);
            
            if (result) {
                // 성공 로그 적재
                systemLogUtil.logApiSuccess("메뉴 삭제", 
                    String.format("메뉴 코드: %s", mnuCd),
                    this.getClass().getName(), "deleteMenu", empId, request);
                
                response.put("success", true);
                response.put("message", "메뉴가 성공적으로 삭제되었습니다.");
                return ResponseEntity.ok(response);
            } else {
                // 실패 로그 적재
                systemLogUtil.logApiError("메뉴 삭제", 
                    String.format("메뉴 삭제 실패 - 메뉴 코드: %s", mnuCd),
                    this.getClass().getName(), "deleteMenu", empId, "MENU_DELETE_FAILED",
                    new Exception("메뉴 삭제 처리 실패"), request);
                
                response.put("success", false);
                response.put("message", "메뉴 삭제에 실패했습니다.");
                return ResponseEntity.badRequest().body(response);
            }
            
        } catch (IllegalArgumentException e) {
            log.warn("메뉴 삭제 유효성 검사 실패: {}", e.getMessage());
            
            // 유효성 검사 실패 로그 적재
            systemLogUtil.logApiError("메뉴 삭제", 
                String.format("유효성 검사 실패 - 메뉴 코드: %s, 오류: %s", mnuCd, e.getMessage()),
                this.getClass().getName(), "deleteMenu", empId, "VALIDATION_ERROR", e, request);
            
            response.put("success", false);
            response.put("message", e.getMessage());
            
            return ResponseEntity.badRequest().body(response);
            
        } catch (Exception e) {
            log.error("메뉴 삭제 실패", e);
            
            // 예외 발생 로그 적재
            systemLogUtil.logApiError("메뉴 삭제", 
                String.format("예외 발생 - 메뉴 코드: %s, 오류: %s", mnuCd, e.getMessage()),
                this.getClass().getName(), "deleteMenu", empId, "EXCEPTION_ERROR", e, request);
            
            response.put("success", false);
            response.put("message", "메뉴 삭제에 실패했습니다: " + e.getMessage());
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * 메뉴 순서 변경
     */
    @PutMapping("/{mnuCd}/order")
    public ResponseEntity<Map<String, Object>> updateMenuOrder(@PathVariable String mnuCd,
                                                              @RequestParam Integer mnuOrd,
                                                              HttpServletRequest request) {
        Map<String, Object> response = new HashMap<>();
        String empId = null;
        
        try {
            // AuthInterceptor에서 전달받은 사용자 정보 사용
            empId = (String) request.getAttribute("EMP_ID");
            if (empId == null) {
                response.put("success", false);
                response.put("message", "인증 정보를 찾을 수 없습니다.");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }
            
            boolean result = mnuLstService.updateMenuOrder(mnuCd, mnuOrd, empId);
            
            if (result) {
                // 성공 로그 적재
                systemLogUtil.logApiSuccess("메뉴 순서 변경", 
                    String.format("메뉴 코드: %s, 순서: %d", mnuCd, mnuOrd),
                    this.getClass().getName(), "updateMenuOrder", empId, request);
                
                response.put("success", true);
                response.put("message", "메뉴 순서가 성공적으로 변경되었습니다.");
                return ResponseEntity.ok(response);
            } else {
                // 실패 로그 적재
                systemLogUtil.logApiError("메뉴 순서 변경", 
                    String.format("메뉴 순서 변경 실패 - 메뉴 코드: %s, 순서: %d", mnuCd, mnuOrd),
                    this.getClass().getName(), "updateMenuOrder", empId, "MENU_ORDER_UPDATE_FAILED",
                    new Exception("메뉴 순서 변경 처리 실패"), request);
                
                response.put("success", false);
                response.put("message", "메뉴 순서 변경에 실패했습니다.");
                return ResponseEntity.badRequest().body(response);
            }
            
        } catch (Exception e) {
            log.error("메뉴 순서 변경 실패", e);
            
            // 예외 발생 로그 적재
            systemLogUtil.logApiError("메뉴 순서 변경", 
                String.format("예외 발생 - 메뉴 코드: %s, 순서: %d, 오류: %s", mnuCd, mnuOrd, e.getMessage()),
                this.getClass().getName(), "updateMenuOrder", empId, "EXCEPTION_ERROR", e, request);
            
            response.put("success", false);
            response.put("message", "메뉴 순서 변경에 실패했습니다: " + e.getMessage());
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * 사용자별 접근 가능한 메뉴 목록 조회
     */
    @GetMapping("/user/{empId}")
    public ResponseEntity<Map<String, Object>> getUserAccessibleMenus(@PathVariable String empId,
                                                                     HttpServletRequest request) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            // AuthInterceptor에서 전달받은 사용자 정보 사용
            String currentEmpId = (String) request.getAttribute("EMP_ID");
            Boolean isAdmin = (Boolean) request.getAttribute("isAdmin");
            Boolean isCounselor = (Boolean) request.getAttribute("isCounselor");
            
            if (currentEmpId == null) {
                response.put("success", false);
                response.put("message", "인증 정보를 찾을 수 없습니다.");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }
            
            // 사용자 권한 타입 결정
            String userAuthType = determineUserAuthType(isAdmin, isCounselor);
            
            log.info("사용자 메뉴 목록 조회 - 요청사용자: {}, 대상사용자: {}, 권한타입: {}", 
                    currentEmpId, empId, userAuthType);
            
            List<MnuLstVO> menuList = mnuLstService.getAllMenus(userAuthType);
            
            response.put("success", true);
            response.put("data", menuList);
            response.put("message", "사용자 접근 가능한 메뉴 목록을 조회했습니다.");
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            log.error("사용자 메뉴 목록 조회 실패", e);
            
            response.put("success", false);
            response.put("message", "사용자 메뉴 목록 조회에 실패했습니다: " + e.getMessage());
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
} 