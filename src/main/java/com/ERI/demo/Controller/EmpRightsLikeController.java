package com.ERI.demo.Controller;

import com.ERI.demo.service.EmpRightsLikeService;
import com.ERI.demo.vo.EmpRightsLikeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;

/**
 * 직원권익게시판 좋아요/싫어요 API 컨트롤러
 */
@RestController
@RequestMapping("/api/emp-rights-like")
public class EmpRightsLikeController {

    @Autowired
    private EmpRightsLikeService empRightsLikeService;

    /**
     * 게시글 좋아요/싫어요 처리
     */
    @PostMapping("/board/{boardSeq}")
    public ResponseEntity<Boolean> toggleBoardLike(@PathVariable Long boardSeq, @RequestBody EmpRightsLikeVO like, HttpSession session) {
        try {
            String sessionEmpId = (String) session.getAttribute("encryptEmpNo");
            if (sessionEmpId == null) {
                return ResponseEntity.badRequest().build();
            }
            
            like.setBoardSeq(boardSeq);
            like.setRgstEmpId(sessionEmpId);
            boolean result = empRightsLikeService.toggleLike(like);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * 댓글 좋아요/싫어요 처리
     */
    @PostMapping("/comment/{commentSeq}")
    public ResponseEntity<Boolean> toggleCommentLike(@PathVariable Long commentSeq, @RequestBody EmpRightsLikeVO like, HttpSession session) {
        try {
            String sessionEmpId = (String) session.getAttribute("encryptEmpNo");
            if (sessionEmpId == null) {
                return ResponseEntity.badRequest().build();
            }
            
            like.setCommentSeq(commentSeq);
            like.setRgstEmpId(sessionEmpId);
            boolean result = empRightsLikeService.toggleLike(like);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * 게시글 좋아요 상태 확인
     */
    @GetMapping("/board/{boardSeq}/status")
    public ResponseEntity<String> getBoardLikeStatus(@PathVariable Long boardSeq, HttpSession session) {
        try {
            String sessionEmpId = (String) session.getAttribute("encryptEmpNo");
            if (sessionEmpId == null) {
                return ResponseEntity.badRequest().build();
            }
            
            String status = empRightsLikeService.getLikeStatus(boardSeq, null, sessionEmpId);
            return ResponseEntity.ok(status);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * 댓글 좋아요 상태 확인
     */
    @GetMapping("/comment/{commentSeq}/status")
    public ResponseEntity<String> getCommentLikeStatus(@PathVariable Long commentSeq, HttpSession session) {
        try {
            String sessionEmpId = (String) session.getAttribute("encryptEmpNo");
            if (sessionEmpId == null) {
                return ResponseEntity.badRequest().build();
            }
            
            String status = empRightsLikeService.getLikeStatus(null, commentSeq, sessionEmpId);
            return ResponseEntity.ok(status);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
} 