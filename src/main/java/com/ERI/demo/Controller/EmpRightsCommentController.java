package com.ERI.demo.Controller;

import com.ERI.demo.service.EmpRightsCommentService;
import com.ERI.demo.vo.EmpRightsCommentVO;
import com.ERI.demo.mappers.EmpRightsCommentMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 직원권익게시판 댓글 API 컨트롤러
 */
@Slf4j
@RestController
@RequestMapping("/api/emp-rights-comment")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@RequiredArgsConstructor
public class EmpRightsCommentController {

    private final EmpRightsCommentService empRightsCommentService;
    private final EmpRightsCommentMapper empRightsCommentMapper;

    /**
     * 댓글 목록 조회
     */
    @GetMapping("/{boardSeq}")
    public ResponseEntity<Map<String, Object>> getCommentList(@PathVariable Long boardSeq, HttpSession session) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            log.info("댓글 목록 조회 요청: boardSeq={}", boardSeq);
            
            List<EmpRightsCommentVO> comments = empRightsCommentService.getCommentList(boardSeq);
            
            // 현재 로그인한 사용자 정보 가져오기
            com.ERI.demo.vo.employee.EmpLstVO empInfo = (com.ERI.demo.vo.employee.EmpLstVO) session.getAttribute("EMP_INFO");
            String sessionEmpId = null;
            if (empInfo != null) {
                sessionEmpId = empInfo.getEmpId();
            }
            
            // 각 댓글에 대해 현재 사용자가 작성자인지 확인
            if (sessionEmpId != null) {
                for (EmpRightsCommentVO comment : comments) {
                    boolean isAuthor = sessionEmpId.equals(comment.getRegEmpId());
                    comment.setIsAuthor(isAuthor);
                }
            }
            
            response.put("success", true);
            response.put("data", comments);
            response.put("message", "댓글 목록을 조회했습니다.");
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            log.error("댓글 목록 조회 실패", e);
            
            response.put("success", false);
            response.put("message", "댓글 목록 조회에 실패했습니다: " + e.getMessage());
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * 댓글 등록
     */
    @PostMapping
    public ResponseEntity<Map<String, Object>> createComment(@RequestBody EmpRightsCommentVO comment, HttpSession session) {
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
            
            comment.setRegEmpId(sessionEmpId);
            comment.setUpdEmpId(sessionEmpId);
            
            log.info("댓글 등록 요청: empId={}, boardSeq={}, content={}", 
                    sessionEmpId, comment.getBoardSeq(), comment.getCntn());
            
            EmpRightsCommentVO createdComment = empRightsCommentService.createComment(comment);
            
            response.put("success", true);
            response.put("data", createdComment);
            response.put("message", "댓글이 성공적으로 등록되었습니다.");
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            log.error("댓글 등록 실패", e);
            
            response.put("success", false);
            response.put("message", "댓글 등록에 실패했습니다: " + e.getMessage());
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * 댓글 수정
     */
    @PutMapping("/{seq}")
    public ResponseEntity<Map<String, Object>> updateComment(@PathVariable Long seq, @RequestBody EmpRightsCommentVO comment, HttpSession session) {
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
            
            // 기존 댓글 조회하여 작성자 확인
            EmpRightsCommentVO existingComment = empRightsCommentMapper.selectCommentBySeq(seq);
            if (existingComment == null) {
                response.put("success", false);
                response.put("message", "존재하지 않는 댓글입니다.");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
            
            // 작성자 본인인지 확인
            if (!sessionEmpId.equals(existingComment.getRegEmpId())) {
                response.put("success", false);
                response.put("message", "댓글 작성자만 수정할 수 있습니다.");
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
            }
            
            comment.setSeq(seq);
            comment.setUpdEmpId(sessionEmpId);
            
            log.info("댓글 수정 요청: seq={}, empId={}, content={}", seq, sessionEmpId, comment.getCntn());
            
            EmpRightsCommentVO updatedComment = empRightsCommentService.updateComment(comment);
            
            if (updatedComment != null) {
                response.put("success", true);
                response.put("data", updatedComment);
                response.put("message", "댓글이 성공적으로 수정되었습니다.");
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("message", "댓글 수정에 실패했습니다.");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
            }
            
        } catch (Exception e) {
            log.error("댓글 수정 실패", e);
            
            response.put("success", false);
            response.put("message", "댓글 수정에 실패했습니다: " + e.getMessage());
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * 댓글 삭제
     */
    @DeleteMapping("/{seq}")
    public ResponseEntity<Map<String, Object>> deleteComment(@PathVariable Long seq, HttpSession session) {
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
            
            // 기존 댓글 조회하여 작성자 확인
            EmpRightsCommentVO existingComment = empRightsCommentMapper.selectCommentBySeq(seq);
            if (existingComment == null) {
                response.put("success", false);
                response.put("message", "존재하지 않는 댓글입니다.");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
            
            // 작성자 본인인지 확인
            if (!sessionEmpId.equals(existingComment.getRegEmpId())) {
                response.put("success", false);
                response.put("message", "댓글 작성자만 삭제할 수 있습니다.");
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
            }
            
            log.info("댓글 삭제 요청: seq={}, empId={}", seq, sessionEmpId);
            
            // 하위 답글이 있는지 확인
            boolean hasReplies = empRightsCommentService.hasChildComments(seq);
            
            boolean deleted = empRightsCommentService.deleteComment(seq, sessionEmpId);
            
            if (deleted) {
                response.put("success", true);
                if (hasReplies) {
                    response.put("message", "댓글과 하위 답글이 모두 성공적으로 삭제되었습니다.");
                } else {
                    response.put("message", "댓글이 성공적으로 삭제되었습니다.");
                }
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("message", "댓글 삭제에 실패했습니다.");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
            }
            
        } catch (Exception e) {
            log.error("댓글 삭제 실패", e);
            
            response.put("success", false);
            response.put("message", "댓글 삭제에 실패했습니다: " + e.getMessage());
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
} 