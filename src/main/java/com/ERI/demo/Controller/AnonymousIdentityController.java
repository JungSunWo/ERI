package com.ERI.demo.Controller;

import com.ERI.demo.service.AnonymousIdentityService;
import com.ERI.demo.vo.AnonymousIdentityGroupVO;
import com.ERI.demo.vo.AnonymousIdentityMemberVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 익명 사용자 동일성 판단 Controller
 */
@RestController
@RequestMapping("/api/anonymous-identity")
public class AnonymousIdentityController {
    
    @Autowired
    private AnonymousIdentityService anonymousIdentityService;
    
    /**
     * 익명 사용자 동일성 그룹 목록 조회
     */
    @GetMapping("/groups")
    public ResponseEntity<Map<String, Object>> getAnonymousIdentityGroupList(
            @RequestParam(value = "useYn", defaultValue = "Y") String useYn) {
        
        Map<String, Object> response = new HashMap<>();
        
        try {
            List<AnonymousIdentityGroupVO> groupList = anonymousIdentityService.getAnonymousIdentityGroupList(useYn);
            
            response.put("success", true);
            response.put("data", groupList);
            response.put("message", "익명 사용자 동일성 그룹 목록을 조회했습니다.");
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "익명 사용자 동일성 그룹 목록 조회 중 오류가 발생했습니다: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
    
    /**
     * 익명 사용자 동일성 그룹 상세 조회
     */
    @GetMapping("/groups/{groupSeq}")
    public ResponseEntity<Map<String, Object>> getAnonymousIdentityGroupBySeq(
            @PathVariable Long groupSeq) {
        
        Map<String, Object> response = new HashMap<>();
        
        try {
            AnonymousIdentityGroupVO group = anonymousIdentityService.getAnonymousIdentityGroupBySeq(groupSeq);
            
            if (group == null) {
                response.put("success", false);
                response.put("message", "해당 그룹을 찾을 수 없습니다.");
                return ResponseEntity.notFound().build();
            }
            
            response.put("success", true);
            response.put("data", group);
            response.put("message", "익명 사용자 동일성 그룹을 조회했습니다.");
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "익명 사용자 동일성 그룹 조회 중 오류가 발생했습니다: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
    
    /**
     * 익명 사용자 동일성 그룹 등록
     */
    @PostMapping("/groups")
    public ResponseEntity<Map<String, Object>> createAnonymousIdentityGroup(
            @RequestBody AnonymousIdentityGroupVO groupVO) {
        
        Map<String, Object> response = new HashMap<>();
        
        try {
            boolean result = anonymousIdentityService.createAnonymousIdentityGroup(groupVO);
            
            if (result) {
                response.put("success", true);
                response.put("data", groupVO);
                response.put("message", "익명 사용자 동일성 그룹을 등록했습니다.");
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("message", "익명 사용자 동일성 그룹 등록에 실패했습니다.");
                return ResponseEntity.badRequest().body(response);
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "익명 사용자 동일성 그룹 등록 중 오류가 발생했습니다: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
    
    /**
     * 익명 사용자 동일성 그룹 수정
     */
    @PutMapping("/groups/{groupSeq}")
    public ResponseEntity<Map<String, Object>> updateAnonymousIdentityGroup(
            @PathVariable Long groupSeq,
            @RequestBody AnonymousIdentityGroupVO groupVO) {
        
        Map<String, Object> response = new HashMap<>();
        
        try {
            groupVO.setGroupSeq(groupSeq);
            boolean result = anonymousIdentityService.updateAnonymousIdentityGroup(groupVO);
            
            if (result) {
                response.put("success", true);
                response.put("data", groupVO);
                response.put("message", "익명 사용자 동일성 그룹을 수정했습니다.");
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("message", "익명 사용자 동일성 그룹 수정에 실패했습니다.");
                return ResponseEntity.badRequest().body(response);
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "익명 사용자 동일성 그룹 수정 중 오류가 발생했습니다: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
    
    /**
     * 익명 사용자 동일성 그룹 삭제
     */
    @DeleteMapping("/groups/{groupSeq}")
    public ResponseEntity<Map<String, Object>> deleteAnonymousIdentityGroup(
            @PathVariable Long groupSeq,
            @RequestParam String delEmpId) {
        
        Map<String, Object> response = new HashMap<>();
        
        try {
            boolean result = anonymousIdentityService.deleteAnonymousIdentityGroup(groupSeq, delEmpId);
            
            if (result) {
                response.put("success", true);
                response.put("message", "익명 사용자 동일성 그룹을 삭제했습니다.");
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("message", "익명 사용자 동일성 그룹 삭제에 실패했습니다.");
                return ResponseEntity.badRequest().body(response);
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "익명 사용자 동일성 그룹 삭제 중 오류가 발생했습니다: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
    
    /**
     * 그룹 멤버 목록 조회
     */
    @GetMapping("/groups/{groupSeq}/members")
    public ResponseEntity<Map<String, Object>> getMemberListByGroupSeq(
            @PathVariable Long groupSeq,
            @RequestParam(value = "useYn", defaultValue = "Y") String useYn) {
        
        Map<String, Object> response = new HashMap<>();
        
        try {
            List<AnonymousIdentityMemberVO> memberList = anonymousIdentityService.getMemberDetailListByGroupSeq(groupSeq);
            
            response.put("success", true);
            response.put("data", memberList);
            response.put("message", "그룹 멤버 목록을 조회했습니다.");
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "그룹 멤버 목록 조회 중 오류가 발생했습니다: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
    
    /**
     * 익명 사용자를 그룹에 추가
     */
    @PostMapping("/groups/{groupSeq}/members")
    public ResponseEntity<Map<String, Object>> addMemberToGroup(
            @PathVariable Long groupSeq,
            @RequestBody AnonymousIdentityMemberVO memberVO) {
        
        Map<String, Object> response = new HashMap<>();
        
        try {
            memberVO.setGroupSeq(groupSeq);
            boolean result = anonymousIdentityService.addMemberToGroup(memberVO);
            
            if (result) {
                response.put("success", true);
                response.put("data", memberVO);
                response.put("message", "익명 사용자를 그룹에 추가했습니다.");
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("message", "익명 사용자 그룹 추가에 실패했습니다.");
                return ResponseEntity.badRequest().body(response);
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "익명 사용자 그룹 추가 중 오류가 발생했습니다: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
    
    /**
     * 그룹에서 멤버 제거
     */
    @DeleteMapping("/groups/{groupSeq}/members/{anonymousId}")
    public ResponseEntity<Map<String, Object>> removeMemberFromGroup(
            @PathVariable Long groupSeq,
            @PathVariable Long anonymousId,
            @RequestParam String delEmpId) {
        
        Map<String, Object> response = new HashMap<>();
        
        try {
            boolean result = anonymousIdentityService.removeMemberFromGroup(groupSeq, anonymousId, delEmpId);
            
            if (result) {
                response.put("success", true);
                response.put("message", "익명 사용자를 그룹에서 제거했습니다.");
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("message", "익명 사용자 그룹 제거에 실패했습니다.");
                return ResponseEntity.badRequest().body(response);
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "익명 사용자 그룹 제거 중 오류가 발생했습니다: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
    
    /**
     * 익명 사용자 ID로 소속 그룹 조회
     */
    @GetMapping("/anonymous/{anonymousId}/groups")
    public ResponseEntity<Map<String, Object>> getGroupsByAnonymousId(
            @PathVariable Long anonymousId) {
        
        Map<String, Object> response = new HashMap<>();
        
        try {
            List<AnonymousIdentityGroupVO> groupList = anonymousIdentityService.getGroupsByAnonymousId(anonymousId);
            
            response.put("success", true);
            response.put("data", groupList);
            response.put("message", "익명 사용자의 소속 그룹을 조회했습니다.");
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "익명 사용자 소속 그룹 조회 중 오류가 발생했습니다: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
} 