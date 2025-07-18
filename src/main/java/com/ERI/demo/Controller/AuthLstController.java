package com.ERI.demo.Controller;

import com.ERI.demo.dto.PageRequestDto;
import com.ERI.demo.dto.PageResponseDto;
import com.ERI.demo.service.AuthLstService;
import com.ERI.demo.vo.AuthLstVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 권한 관리 컨트롤러
 */
@RestController
@RequestMapping("/api/auth")
public class AuthLstController {
    
    @Autowired
    private AuthLstService authLstService;
    
    /**
     * 권한 목록 조회 (페이징)
     */
    @GetMapping("/list")
    public ResponseEntity<PageResponseDto<AuthLstVO>> getAuthList(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String searchField,
            @RequestParam(defaultValue = "authLvl") String sortKey,
            @RequestParam(defaultValue = "desc") String sortOrder) {
        
        PageRequestDto pageRequest = new PageRequestDto();
        pageRequest.setPage(page);
        pageRequest.setSize(size);
        pageRequest.setSearchKeyword(keyword);
        pageRequest.setSearchField(searchField);
        pageRequest.setSortBy(sortKey);
        pageRequest.setSortDirection(sortOrder);
        
        PageResponseDto<AuthLstVO> response = authLstService.getAllAuthsWithPaging(pageRequest);
        return ResponseEntity.ok(response);
    }
    
    /**
     * 전체 권한 목록 조회 (페이징 없음)
     */
    @GetMapping("/all")
    public ResponseEntity<List<AuthLstVO>> getAllAuths() {
        List<AuthLstVO> auths = authLstService.getAllAuths();
        return ResponseEntity.ok(auths);
    }
    
    /**
     * 권한 상세 조회
     */
    @GetMapping("/{authCd}")
    public ResponseEntity<AuthLstVO> getAuth(@PathVariable String authCd) {
        AuthLstVO auth = authLstService.getAuthByAuthCd(authCd);
        if (auth == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(auth);
    }
    
    /**
     * 권한 등록
     */
    @PostMapping
    public ResponseEntity<Map<String, Object>> createAuth(@RequestBody AuthLstVO authLstVO) {
        Map<String, Object> response = new HashMap<>();
        
        // 권한 코드 중복 확인
        if (authLstService.isAuthCdExists(authLstVO.getAuthCd())) {
            response.put("success", false);
            response.put("message", "이미 존재하는 권한 코드입니다.");
            return ResponseEntity.badRequest().body(response);
        }
        
        // 권한레벨 유효성 검사
        if (!authLstService.isValidAuthLvl(authLstVO.getAuthLvl())) {
            response.put("success", false);
            response.put("message", "권한레벨은 1~10 사이의 값이어야 합니다.");
            return ResponseEntity.badRequest().body(response);
        }
        
        int result = authLstService.createAuth(authLstVO);
        if (result > 0) {
            response.put("success", true);
            response.put("message", "권한이 성공적으로 등록되었습니다.");
            return ResponseEntity.ok(response);
        } else {
            response.put("success", false);
            response.put("message", "권한 등록에 실패했습니다.");
            return ResponseEntity.badRequest().body(response);
        }
    }
    
    /**
     * 권한 수정
     */
    @PutMapping("/{authCd}")
    public ResponseEntity<Map<String, Object>> updateAuth(
            @PathVariable String authCd, 
            @RequestBody AuthLstVO authLstVO) {
        Map<String, Object> response = new HashMap<>();
        
        // 권한 존재 확인
        AuthLstVO existingAuth = authLstService.getAuthByAuthCd(authCd);
        if (existingAuth == null) {
            response.put("success", false);
            response.put("message", "존재하지 않는 권한입니다.");
            return ResponseEntity.notFound().build();
        }
        
        // 권한레벨 유효성 검사
        if (!authLstService.isValidAuthLvl(authLstVO.getAuthLvl())) {
            response.put("success", false);
            response.put("message", "권한레벨은 1~10 사이의 값이어야 합니다.");
            return ResponseEntity.badRequest().body(response);
        }
        
        authLstVO.setAuthCd(authCd);
        int result = authLstService.updateAuth(authLstVO);
        if (result > 0) {
            response.put("success", true);
            response.put("message", "권한이 성공적으로 수정되었습니다.");
            return ResponseEntity.ok(response);
        } else {
            response.put("success", false);
            response.put("message", "권한 수정에 실패했습니다.");
            return ResponseEntity.badRequest().body(response);
        }
    }
    
    /**
     * 권한 삭제
     */
    @DeleteMapping("/{authCd}")
    public ResponseEntity<Map<String, Object>> deleteAuth(@PathVariable String authCd) {
        Map<String, Object> response = new HashMap<>();
        
        // 권한 존재 확인
        AuthLstVO existingAuth = authLstService.getAuthByAuthCd(authCd);
        if (existingAuth == null) {
            response.put("success", false);
            response.put("message", "존재하지 않는 권한입니다.");
            return ResponseEntity.notFound().build();
        }
        
        int result = authLstService.deleteAuth(authCd);
        if (result > 0) {
            response.put("success", true);
            response.put("message", "권한이 성공적으로 삭제되었습니다.");
            return ResponseEntity.ok(response);
        } else {
            response.put("success", false);
            response.put("message", "권한 삭제에 실패했습니다.");
            return ResponseEntity.badRequest().body(response);
        }
    }
    
    /**
     * 권한레벨별 권한 목록 조회
     */
    @GetMapping("/level/{authLvl}")
    public ResponseEntity<List<AuthLstVO>> getAuthsByLevel(@PathVariable Integer authLvl) {
        List<AuthLstVO> auths = authLstService.getAuthsByAuthLvl(authLvl);
        return ResponseEntity.ok(auths);
    }
    
    /**
     * 권한레벨 이상의 권한 목록 조회
     */
    @GetMapping("/level/greater-than/{authLvl}")
    public ResponseEntity<List<AuthLstVO>> getAuthsByLevelGreaterThan(@PathVariable Integer authLvl) {
        List<AuthLstVO> auths = authLstService.getAuthsByAuthLvlGreaterThan(authLvl);
        return ResponseEntity.ok(auths);
    }
    
    /**
     * 권한 코드 중복 확인
     */
    @GetMapping("/check/{authCd}")
    public ResponseEntity<Map<String, Object>> checkAuthCdExists(@PathVariable String authCd) {
        Map<String, Object> response = new HashMap<>();
        boolean exists = authLstService.isAuthCdExists(authCd);
        response.put("exists", exists);
        response.put("message", exists ? "이미 존재하는 권한 코드입니다." : "사용 가능한 권한 코드입니다.");
        return ResponseEntity.ok(response);
    }
} 