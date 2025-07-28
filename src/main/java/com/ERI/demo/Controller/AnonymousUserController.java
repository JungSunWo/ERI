package com.ERI.demo.Controller;

import com.ERI.demo.dto.PageRequestDto;
import com.ERI.demo.dto.PageResponseDto;
import com.ERI.demo.service.AnonymousUserService;
import com.ERI.demo.vo.AnonymousUserVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 익명 사용자 Controller
 */
@RestController
@RequestMapping("/api/anonymous-users")
@RequiredArgsConstructor
public class AnonymousUserController {
    
    private final AnonymousUserService anonymousUserService;
    
    /**
     * 익명 사용자 목록 조회 (함수 대체)
     */
    @GetMapping
    public ResponseEntity<List<AnonymousUserVO>> getAnonymousUserList() {
        List<AnonymousUserVO> result = anonymousUserService.getAnonymousUserList();
        return ResponseEntity.ok(result);
    }
    
    /**
     * 익명 사용자 상세 조회 (함수 대체)
     */
    @GetMapping("/{anonymousId}")
    public ResponseEntity<AnonymousUserVO> getAnonymousUserById(@PathVariable Long anonymousId) {
        AnonymousUserVO result = anonymousUserService.getAnonymousUser(anonymousId);
        return ResponseEntity.ok(result);
    }
    
    /**
     * 닉네임으로 익명 사용자 조회
     */
    @GetMapping("/nickname/{nickname}")
    public ResponseEntity<AnonymousUserVO> getAnonymousUserByNickname(@PathVariable String nickname) {
        AnonymousUserVO result = anonymousUserService.getAnonymousUserByNickname(nickname);
        return ResponseEntity.ok(result);
    }
    
    /**
     * 익명 사용자 등록 (함수 대체)
     */
    @PostMapping
    public ResponseEntity<Long> createAnonymousUser(@RequestParam String nickname) {
        Long anonymousId = anonymousUserService.createAnonymousUser(nickname);
        return ResponseEntity.ok(anonymousId);
    }
    
    /**
     * 익명 사용자 수정 (함수 대체)
     */
    @PutMapping("/{anonymousId}")
    public ResponseEntity<AnonymousUserVO> updateAnonymousUser(
            @PathVariable Long anonymousId,
            @RequestBody AnonymousUserVO anonymousUser) {
        
        anonymousUser.setAnonymousId(anonymousId);
        // TODO: AnonymousUserService에 updateAnonymousUser 메서드 추가 필요
        // AnonymousUserVO result = anonymousUserService.updateAnonymousUser(anonymousUser);
        return ResponseEntity.ok(anonymousUser);
    }
    
    /**
     * 익명 사용자 삭제 (함수 대체)
     */
    @DeleteMapping("/{anonymousId}")
    public ResponseEntity<Void> deleteAnonymousUser(@PathVariable Long anonymousId) {
        anonymousUserService.deleteAnonymousUser(anonymousId);
        return ResponseEntity.ok().build();
    }
    
    /**
     * 닉네임 중복 확인 (함수 대체)
     */
    @GetMapping("/check-nickname/{nickname}")
    public ResponseEntity<Boolean> checkNicknameExists(@PathVariable String nickname) {
        AnonymousUserVO user = anonymousUserService.getAnonymousUserByNickname(nickname);
        boolean exists = user != null;
        return ResponseEntity.ok(exists);
    }
    
    /**
     * 익명 사용자별 상담 건수 조회 (함수 대체)
     */
    @GetMapping("/with-consultation-count")
    public ResponseEntity<List<AnonymousUserVO>> getAnonymousUserWithConsultationCount() {
        // TODO: AnonymousUserService에 getAnonymousUserWithConsultationCount 메서드 추가 필요
        List<AnonymousUserVO> result = anonymousUserService.getAnonymousUserList();
        return ResponseEntity.ok(result);
    }
} 