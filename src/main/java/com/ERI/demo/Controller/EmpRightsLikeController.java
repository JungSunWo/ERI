package com.ERI.demo.Controller;

import com.ERI.demo.service.EmpRightsLikeService;
import com.ERI.demo.vo.EmpRightsLikeVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * 직원권익게시판 좋아요/싫어요 API 컨트롤러
 */
@Slf4j
@RestController
@RequestMapping("/api/emp-rights-like")
@RequiredArgsConstructor
public class EmpRightsLikeController {

    private final EmpRightsLikeService empRightsLikeService;

    /**
     * 게시글 좋아요/싫어요 처리
     */
    @PostMapping("/board/{boardSeq}")
    public ResponseEntity<Map<String, Object>> toggleBoardLike(@PathVariable Long boardSeq, 
                                                              @RequestBody EmpRightsLikeVO like, 
                                                              HttpSession session) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            // 세션에서 직원 정보 가져오기
            com.ERI.demo.vo.employee.EmpLstVO empInfo = (com.ERI.demo.vo.employee.EmpLstVO) session.getAttribute("EMP_INFO");
            if (empInfo == null) {
                response.put("success", false);
                response.put("message", "로그인이 필요합니다.");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }
            
            String sessionEmpId = empInfo.getEmpId();
            
            like.setBoardSeq(boardSeq);
            like.setRegEmpId(sessionEmpId);
            
            log.info("게시글 좋아요/싫어요 처리 요청: boardSeq={}, empId={}, likeType={}", 
                    boardSeq, sessionEmpId, like.getLikeType());
            
            boolean result = empRightsLikeService.toggleLike(like);
            
            if (result) {
                response.put("success", true);
                response.put("message", "좋아요/싫어요 처리가 완료되었습니다.");
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("message", "좋아요/싫어요 처리에 실패했습니다.");
                return ResponseEntity.badRequest().body(response);
            }
            
        } catch (Exception e) {
            log.error("게시글 좋아요/싫어요 처리 실패", e);
            
            response.put("success", false);
            response.put("message", "좋아요/싫어요 처리에 실패했습니다: " + e.getMessage());
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * 댓글 좋아요/싫어요 처리
     */
    @PostMapping("/comment/{commentSeq}")
    public ResponseEntity<Map<String, Object>> toggleCommentLike(@PathVariable Long commentSeq, 
                                                                @RequestBody EmpRightsLikeVO like, 
                                                                HttpSession session) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            // 세션에서 직원 정보 가져오기
            com.ERI.demo.vo.employee.EmpLstVO empInfo = (com.ERI.demo.vo.employee.EmpLstVO) session.getAttribute("EMP_INFO");
            if (empInfo == null) {
                response.put("success", false);
                response.put("message", "로그인이 필요합니다.");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }
            
            String sessionEmpId = empInfo.getEmpId();
            
            like.setCommentSeq(commentSeq);
            like.setRegEmpId(sessionEmpId);
            
            log.info("댓글 좋아요/싫어요 처리 요청: commentSeq={}, empId={}, likeType={}", 
                    commentSeq, sessionEmpId, like.getLikeType());
            
            boolean result = empRightsLikeService.toggleLike(like);
            
            if (result) {
                response.put("success", true);
                response.put("message", "좋아요/싫어요 처리가 완료되었습니다.");
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("message", "좋아요/싫어요 처리에 실패했습니다.");
                return ResponseEntity.badRequest().body(response);
            }
            
        } catch (Exception e) {
            log.error("댓글 좋아요/싫어요 처리 실패", e);
            
            response.put("success", false);
            response.put("message", "좋아요/싫어요 처리에 실패했습니다: " + e.getMessage());
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * 게시글 좋아요 상태 확인
     */
    @GetMapping("/board/{boardSeq}/status")
    public ResponseEntity<Map<String, Object>> getBoardLikeStatus(@PathVariable Long boardSeq, 
                                                                 HttpSession session) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            // 세션에서 직원 정보 가져오기
            com.ERI.demo.vo.employee.EmpLstVO empInfo = (com.ERI.demo.vo.employee.EmpLstVO) session.getAttribute("EMP_INFO");
            if (empInfo == null) {
                response.put("success", false);
                response.put("message", "로그인이 필요합니다.");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }
            
            String sessionEmpId = empInfo.getEmpId();
            
            log.info("게시글 좋아요 상태 확인 요청: boardSeq={}, empId={}", boardSeq, sessionEmpId);
            
            String status = empRightsLikeService.getLikeStatus(boardSeq, null, sessionEmpId);
            
            response.put("success", true);
            response.put("data", status);
            response.put("message", "게시글 좋아요 상태를 조회했습니다.");
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            log.error("게시글 좋아요 상태 확인 실패", e);
            
            response.put("success", false);
            response.put("message", "게시글 좋아요 상태 확인에 실패했습니다: " + e.getMessage());
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * 댓글 좋아요 상태 확인
     */
    @GetMapping("/comment/{commentSeq}/status")
    public ResponseEntity<Map<String, Object>> getCommentLikeStatus(@PathVariable Long commentSeq, 
                                                                   HttpSession session) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            // 세션에서 직원 정보 가져오기
            com.ERI.demo.vo.employee.EmpLstVO empInfo = (com.ERI.demo.vo.employee.EmpLstVO) session.getAttribute("EMP_INFO");
            if (empInfo == null) {
                response.put("success", false);
                response.put("message", "로그인이 필요합니다.");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }
            
            String sessionEmpId = empInfo.getEmpId();
            
            log.info("댓글 좋아요 상태 확인 요청: commentSeq={}, empId={}", commentSeq, sessionEmpId);
            
            String status = empRightsLikeService.getLikeStatus(null, commentSeq, sessionEmpId);
            
            response.put("success", true);
            response.put("data", status);
            response.put("message", "댓글 좋아요 상태를 조회했습니다.");
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            log.error("댓글 좋아요 상태 확인 실패", e);
            
            response.put("success", false);
            response.put("message", "댓글 좋아요 상태 확인에 실패했습니다: " + e.getMessage());
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
} 