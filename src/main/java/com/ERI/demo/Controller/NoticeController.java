package com.ERI.demo.Controller;

import com.ERI.demo.dto.UserAuthDto;
import com.ERI.demo.service.NoticeService;
import com.ERI.demo.vo.NtiLstVO;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/notice")
@RequiredArgsConstructor
public class NoticeController {

    private final NoticeService noticeService;

    /**
     * 공지사항 목록 조회
     */
    @GetMapping("/list")
    public ResponseEntity<Map<String, Object>> getNoticeList(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String status) {
        
        Map<String, Object> response = new HashMap<>();
        
        try {
            Map<String, Object> result = noticeService.getNoticeList(page, size, title, status);
            
            response.put("success", true);
            response.put("data", result);
            response.put("message", "공지사항 목록을 조회했습니다.");
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            log.error("공지사항 목록 조회 실패", e);
            
            response.put("success", false);
            response.put("message", "공지사항 목록 조회에 실패했습니다: " + e.getMessage());
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * 공지사항 상세 조회
     */
    @GetMapping("/{seq}")
    public ResponseEntity<Map<String, Object>> getNotice(@PathVariable Long seq) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            NtiLstVO notice = noticeService.getNoticeBySeq(seq);
            
            if (notice != null) {
                response.put("success", true);
                response.put("data", notice);
                response.put("message", "공지사항을 조회했습니다.");
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("message", "공지사항을 찾을 수 없습니다.");
                return ResponseEntity.notFound().build();
            }
            
        } catch (Exception e) {
            log.error("공지사항 조회 실패", e);
            
            response.put("success", false);
            response.put("message", "공지사항 조회에 실패했습니다: " + e.getMessage());
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * 세션에서 사용자 정보를 가져오는 헬퍼 메서드
     */
    private String getCurrentUserId(HttpSession session) {
        UserAuthDto userAuth = (UserAuthDto) session.getAttribute("USER_AUTH");
        if (userAuth == null) {
            throw new IllegalArgumentException("로그인이 필요합니다.");
        }
        return userAuth.getUserId();
    }

    /**
     * 공지사항 등록
     */
    @PostMapping
    public ResponseEntity<Map<String, Object>> createNotice(
            @RequestParam("title") String title,
            @RequestParam("content") String content,
            @RequestParam(value = "status", defaultValue = "STS001") String status,
            @RequestParam(value = "empId", required = false) String empId,
            @RequestParam(value = "files", required = false) MultipartFile[] files,
            HttpSession session) {
        
        Map<String, Object> response = new HashMap<>();
        
        try {
            // empId가 제공되지 않으면 세션에서 가져오기
            String currentEmpId = empId != null ? empId : getCurrentUserId(session);
            
            NtiLstVO notice = new NtiLstVO();
            notice.setTtl(title);
            notice.setCntn(content);
            notice.setStsCd(status);
            notice.setRgstEmpId(currentEmpId);
            
            NtiLstVO createdNotice = noticeService.createNotice(notice, files, currentEmpId);
            
            response.put("success", true);
            response.put("data", createdNotice);
            response.put("message", "공지사항이 성공적으로 등록되었습니다.");
            
            return ResponseEntity.ok(response);
            
        } catch (IllegalArgumentException e) {
            log.warn("공지사항 등록 유효성 검사 실패: {}", e.getMessage());
            
            response.put("success", false);
            response.put("message", e.getMessage());
            
            return ResponseEntity.badRequest().body(response);
            
        } catch (Exception e) {
            log.error("공지사항 등록 실패", e);
            
            response.put("success", false);
            response.put("message", "공지사항 등록에 실패했습니다: " + e.getMessage());
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * 공지사항 수정
     */
    @PutMapping("/{seq}")
    public ResponseEntity<Map<String, Object>> updateNotice(
            @PathVariable Long seq,
            @RequestParam("title") String title,
            @RequestParam("content") String content,
            @RequestParam(value = "status", defaultValue = "STS001") String status,
            @RequestParam(value = "empId", required = false) String empId,
            @RequestParam(value = "files", required = false) MultipartFile[] files,
            HttpSession session) {
        
        Map<String, Object> response = new HashMap<>();
        
        try {
            // empId가 제공되지 않으면 세션에서 가져오기
            String currentEmpId = empId != null ? empId : getCurrentUserId(session);
            
            NtiLstVO notice = new NtiLstVO();
            notice.setSeq(seq);
            notice.setTtl(title);
            notice.setCntn(content);
            notice.setStsCd(status);
            notice.setUpdtEmpId(currentEmpId);
            
            NtiLstVO updatedNotice = noticeService.updateNotice(notice, files, currentEmpId);
            
            if (updatedNotice != null) {
                response.put("success", true);
                response.put("data", updatedNotice);
                response.put("message", "공지사항이 성공적으로 수정되었습니다.");
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("message", "공지사항을 찾을 수 없습니다.");
                return ResponseEntity.notFound().build();
            }
            
        } catch (IllegalArgumentException e) {
            log.warn("공지사항 수정 유효성 검사 실패: {}", e.getMessage());
            
            response.put("success", false);
            response.put("message", e.getMessage());
            
            return ResponseEntity.badRequest().body(response);
            
        } catch (Exception e) {
            log.error("공지사항 수정 실패", e);
            
            response.put("success", false);
            response.put("message", "공지사항 수정에 실패했습니다: " + e.getMessage());
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * 공지사항 삭제
     */
    @DeleteMapping("/{seq}")
    public ResponseEntity<Map<String, Object>> deleteNotice(
            @PathVariable Long seq,
            @RequestParam(value = "empId", required = false) String empId,
            HttpSession session) {
        
        Map<String, Object> response = new HashMap<>();
        
        try {
            // empId가 제공되지 않으면 세션에서 가져오기
            String currentEmpId = empId != null ? empId : getCurrentUserId(session);
            
            boolean deleted = noticeService.deleteNotice(seq, currentEmpId);
            
            if (deleted) {
                response.put("success", true);
                response.put("message", "공지사항이 성공적으로 삭제되었습니다.");
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("message", "공지사항을 찾을 수 없습니다.");
                return ResponseEntity.notFound().build();
            }
            
        } catch (IllegalArgumentException e) {
            log.warn("공지사항 삭제 유효성 검사 실패: {}", e.getMessage());
            
            response.put("success", false);
            response.put("message", e.getMessage());
            
            return ResponseEntity.badRequest().body(response);
            
        } catch (Exception e) {
            log.error("공지사항 삭제 실패", e);
            
            response.put("success", false);
            response.put("message", "공지사항 삭제에 실패했습니다: " + e.getMessage());
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * 공지사항 상태 변경
     */
    @PatchMapping("/{seq}/status")
    public ResponseEntity<Map<String, Object>> updateNoticeStatus(
            @PathVariable Long seq,
            @RequestParam("status") String status,
            @RequestParam(value = "empId", required = false) String empId,
            HttpSession session) {
        
        Map<String, Object> response = new HashMap<>();
        
        try {
            // empId가 제공되지 않으면 세션에서 가져오기
            String currentEmpId = empId != null ? empId : getCurrentUserId(session);
            
            boolean updated = noticeService.updateNoticeStatus(seq, status, currentEmpId);
            
            if (updated) {
                response.put("success", true);
                response.put("message", "공지사항 상태가 변경되었습니다.");
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("message", "공지사항을 찾을 수 없습니다.");
                return ResponseEntity.notFound().build();
            }
            
        } catch (IllegalArgumentException e) {
            log.warn("공지사항 상태 변경 유효성 검사 실패: {}", e.getMessage());
            
            response.put("success", false);
            response.put("message", e.getMessage());
            
            return ResponseEntity.badRequest().body(response);
            
        } catch (Exception e) {
            log.error("공지사항 상태 변경 실패", e);
            
            response.put("success", false);
            response.put("message", "공지사항 상태 변경에 실패했습니다: " + e.getMessage());
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
} 