package com.ERI.demo.Controller;

import com.ERI.demo.service.MnuLstService;
import com.ERI.demo.vo.MnuLstVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/menu")
@RequiredArgsConstructor
public class MnuLstController {

    private final MnuLstService mnuLstService;

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
     * 전체 메뉴 목록 조회 (계층 구조)
     */
    @GetMapping("/all")
    public ResponseEntity<Map<String, Object>> getAllMenus(HttpServletRequest request) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            // AuthInterceptor에서 전달받은 관리자 여부와 상담사 여부 사용
            Boolean isAdmin = (Boolean) request.getAttribute("isAdmin");
            Boolean isCounselor = (Boolean) request.getAttribute("isCounselor");
            if (isAdmin == null) isAdmin = false; // 기본값은 일반 사용자
            if (isCounselor == null) isCounselor = false; // 기본값은 일반 사용자
            
            log.info("전체 메뉴 목록 조회: 관리자 여부 = {}, 상담사 여부 = {}", isAdmin, isCounselor);
            
            List<MnuLstVO> menuList = mnuLstService.getAllMenus(isAdmin, isCounselor);
            
            response.put("success", true);
            response.put("data", menuList);
            response.put("message", "전체 메뉴 목록을 조회했습니다.");
            response.put("isAdmin", isAdmin); // 응답에 관리자 여부 포함
            response.put("isCounselor", isCounselor); // 응답에 상담사 여부 포함
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            log.error("전체 메뉴 목록 조회 실패", e);
            
            response.put("success", false);
            response.put("message", "전체 메뉴 목록 조회에 실패했습니다: " + e.getMessage());
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
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
            @RequestParam(required = false) String mnuAdminYn) {
        
        Map<String, Object> response = new HashMap<>();
        
        try {
            List<MnuLstVO> menuList = mnuLstService.getMenuListWithPaging(page, size, searchKeyword, mnuLvl, mnuUseYn, mnuAdminYn);
            int totalCount = mnuLstService.getMenuCount(searchKeyword, mnuLvl, mnuUseYn, mnuAdminYn);
            
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
        
        try {
            // AuthInterceptor에서 전달받은 사용자 정보 사용
            String empId = (String) request.getAttribute("EMP_ID");
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
                response.put("success", true);
                response.put("message", "메뉴가 성공적으로 등록되었습니다.");
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("message", "메뉴 등록에 실패했습니다.");
                return ResponseEntity.badRequest().body(response);
            }
            
        } catch (IllegalArgumentException e) {
            log.warn("메뉴 등록 유효성 검사 실패: {}", e.getMessage());
            
            response.put("success", false);
            response.put("message", e.getMessage());
            
            return ResponseEntity.badRequest().body(response);
            
        } catch (Exception e) {
            log.error("메뉴 등록 실패", e);
            
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
        
        try {
            // AuthInterceptor에서 전달받은 사용자 정보 사용
            String empId = (String) request.getAttribute("EMP_ID");
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
                response.put("success", true);
                response.put("message", "메뉴가 성공적으로 수정되었습니다.");
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("message", "메뉴 수정에 실패했습니다.");
                return ResponseEntity.badRequest().body(response);
            }
            
        } catch (IllegalArgumentException e) {
            log.warn("메뉴 수정 유효성 검사 실패: {}", e.getMessage());
            
            response.put("success", false);
            response.put("message", e.getMessage());
            
            return ResponseEntity.badRequest().body(response);
            
        } catch (Exception e) {
            log.error("메뉴 수정 실패", e);
            
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
        
        try {
            // AuthInterceptor에서 전달받은 사용자 정보 사용
            String empId = (String) request.getAttribute("EMP_ID");
            if (empId == null) {
                response.put("success", false);
                response.put("message", "인증 정보를 찾을 수 없습니다.");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }
            
            boolean result = mnuLstService.deleteMenu(mnuCd, empId);
            
            if (result) {
                response.put("success", true);
                response.put("message", "메뉴가 성공적으로 삭제되었습니다.");
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("message", "메뉴 삭제에 실패했습니다.");
                return ResponseEntity.badRequest().body(response);
            }
            
        } catch (IllegalArgumentException e) {
            log.warn("메뉴 삭제 유효성 검사 실패: {}", e.getMessage());
            
            response.put("success", false);
            response.put("message", e.getMessage());
            
            return ResponseEntity.badRequest().body(response);
            
        } catch (Exception e) {
            log.error("메뉴 삭제 실패", e);
            
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
        
        try {
            // AuthInterceptor에서 전달받은 사용자 정보 사용
            String empId = (String) request.getAttribute("EMP_ID");
            if (empId == null) {
                response.put("success", false);
                response.put("message", "인증 정보를 찾을 수 없습니다.");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }
            
            boolean result = mnuLstService.updateMenuOrder(mnuCd, mnuOrd, empId);
            
            if (result) {
                response.put("success", true);
                response.put("message", "메뉴 순서가 성공적으로 변경되었습니다.");
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("message", "메뉴 순서 변경에 실패했습니다.");
                return ResponseEntity.badRequest().body(response);
            }
            
        } catch (Exception e) {
            log.error("메뉴 순서 변경 실패", e);
            
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
                                                                     @RequestParam(defaultValue = "false") boolean isAdmin) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            List<MnuLstVO> menuList = mnuLstService.getUserAccessibleMenus(empId, isAdmin);
            
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