package com.ERI.demo.Controller;

import com.ERI.demo.service.EmpRightsCommentService;
import com.ERI.demo.vo.EmpRightsCommentVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;
import java.util.List;

/**
 * 직원권익게시판 댓글 API 컨트롤러
 */
@RestController
@RequestMapping("/api/emp-rights-comment")
public class EmpRightsCommentController {

    @Autowired
    private EmpRightsCommentService empRightsCommentService;

    /**
     * 댓글 목록 조회
     */
    @GetMapping("/{boardSeq}")
    public ResponseEntity<List<EmpRightsCommentVO>> getCommentList(@PathVariable Long boardSeq) {
        try {
            List<EmpRightsCommentVO> comments = empRightsCommentService.getCommentList(boardSeq);
            return ResponseEntity.ok(comments);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * 댓글 등록
     */
    @PostMapping
    public ResponseEntity<EmpRightsCommentVO> createComment(@RequestBody EmpRightsCommentVO comment, HttpSession session) {
        try {
            String sessionEmpId = (String) session.getAttribute("encryptEmpNo");
            if (sessionEmpId == null) {
                return ResponseEntity.badRequest().build();
            }
            
            comment.setRgstEmpId(sessionEmpId);
            comment.setUpdtEmpId(sessionEmpId);
            
            EmpRightsCommentVO createdComment = empRightsCommentService.createComment(comment);
            return ResponseEntity.ok(createdComment);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * 댓글 수정
     */
    @PutMapping("/{seq}")
    public ResponseEntity<EmpRightsCommentVO> updateComment(@PathVariable Long seq, @RequestBody EmpRightsCommentVO comment, HttpSession session) {
        try {
            String sessionEmpId = (String) session.getAttribute("encryptEmpNo");
            if (sessionEmpId == null) {
                return ResponseEntity.badRequest().build();
            }
            
            comment.setSeq(seq);
            comment.setUpdtEmpId(sessionEmpId);
            
            EmpRightsCommentVO updatedComment = empRightsCommentService.updateComment(comment);
            if (updatedComment != null) {
                return ResponseEntity.ok(updatedComment);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * 댓글 삭제
     */
    @DeleteMapping("/{seq}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long seq, HttpSession session) {
        try {
            String sessionEmpId = (String) session.getAttribute("encryptEmpNo");
            if (sessionEmpId == null) {
                return ResponseEntity.badRequest().build();
            }
            
            boolean deleted = empRightsCommentService.deleteComment(seq, sessionEmpId);
            if (deleted) {
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
} 