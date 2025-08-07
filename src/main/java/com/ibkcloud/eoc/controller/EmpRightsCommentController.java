package com.ibkcloud.eoc.controller;

import com.ibkcloud.eoc.cmm.exception.BizException;
import com.ibkcloud.eoc.controller.vo.*;
import com.ibkcloud.eoc.service.EmpRightsCommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

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

    /**
     * 댓글 목록 조회
     */
    @GetMapping("/{boardSeq}")
    public ResponseEntity<EmpRightsCommentOutVo> inqEmpRightsCommentList(
            @PathVariable Long boardSeq,
            HttpServletRequest request) {
        
        log.info("댓글 목록 조회 시작 - boardSeq: {}", boardSeq);
        
        try {
            // AuthInterceptor에서 전달받은 사용자 정보 사용
            String empId = (String) request.getAttribute("EMP_ID");
            
            EmpRightsCommentOutVo result = empRightsCommentService.inqEmpRightsCommentList(boardSeq, empId);
            
            result.setSuccess(true);
            result.setMessage("댓글 목록을 조회했습니다.");
            result.setErrorCode("SUCCESS");
            
            log.info("댓글 목록 조회 완료 - boardSeq: {}, 총 {}건", boardSeq, result.getCount());
            return ResponseEntity.ok(result);
            
        } catch (Exception e) {
            log.error("댓글 목록 조회 실패 - boardSeq: {}", boardSeq, e);
            throw new BizException("댓글 목록 조회에 실패했습니다: " + e.getMessage());
        }
    }

    /**
     * 댓글 등록
     */
    @PostMapping
    public ResponseEntity<EmpRightsCommentOutVo> rgsnEmpRightsComment(
            @Valid EmpRightsCommentInVo inVo,
            HttpServletRequest request) {
        
        log.info("댓글 등록 시작 - boardSeq: {}", inVo.getBoardSeq());
        
        try {
            // AuthInterceptor에서 전달받은 사용자 정보 사용
            String empId = (String) request.getAttribute("EMP_ID");
            
            if (empId == null) {
                throw new BizException("로그인이 필요합니다.");
            }
            
            inVo.setRegEmpId(empId);
            inVo.setUpdEmpId(empId);
            
            EmpRightsCommentOutVo result = empRightsCommentService.rgsnEmpRightsComment(inVo);
            
            result.setSuccess(true);
            result.setMessage("댓글이 성공적으로 등록되었습니다.");
            result.setErrorCode("SUCCESS");
            
            log.info("댓글 등록 완료 - seq: {}", result.getSeq());
            return ResponseEntity.ok(result);
            
        } catch (Exception e) {
            log.error("댓글 등록 실패", e);
            throw new BizException("댓글 등록에 실패했습니다: " + e.getMessage());
        }
    }

    /**
     * 댓글 수정
     */
    @PutMapping("/{seq}")
    public ResponseEntity<EmpRightsCommentOutVo> mdfcEmpRightsComment(
            @PathVariable Long seq,
            @Valid EmpRightsCommentInVo inVo,
            HttpServletRequest request) {
        
        log.info("댓글 수정 시작 - seq: {}", seq);
        
        try {
            // AuthInterceptor에서 전달받은 사용자 정보 사용
            String empId = (String) request.getAttribute("EMP_ID");
            
            if (empId == null) {
                throw new BizException("로그인이 필요합니다.");
            }
            
            inVo.setSeq(seq);
            inVo.setUpdEmpId(empId);
            
            EmpRightsCommentOutVo result = empRightsCommentService.mdfcEmpRightsComment(inVo);
            
            if (result == null) {
                throw new BizException("댓글 수정에 실패했습니다.");
            }
            
            result.setSuccess(true);
            result.setMessage("댓글이 성공적으로 수정되었습니다.");
            result.setErrorCode("SUCCESS");
            
            log.info("댓글 수정 완료 - seq: {}", seq);
            return ResponseEntity.ok(result);
            
        } catch (Exception e) {
            log.error("댓글 수정 실패 - seq: {}", seq, e);
            throw new BizException("댓글 수정에 실패했습니다: " + e.getMessage());
        }
    }

    /**
     * 댓글 삭제
     */
    @DeleteMapping("/{seq}")
    public ResponseEntity<EmpRightsCommentOutVo> delEmpRightsComment(
            @PathVariable Long seq,
            HttpServletRequest request) {
        
        log.info("댓글 삭제 시작 - seq: {}", seq);
        
        try {
            // AuthInterceptor에서 전달받은 사용자 정보 사용
            String empId = (String) request.getAttribute("EMP_ID");
            
            if (empId == null) {
                throw new BizException("로그인이 필요합니다.");
            }
            
            boolean result = empRightsCommentService.delEmpRightsComment(seq, empId);
            
            if (!result) {
                throw new BizException("댓글 삭제에 실패했습니다.");
            }
            
            EmpRightsCommentOutVo outVo = new EmpRightsCommentOutVo();
            outVo.setSuccess(true);
            outVo.setMessage("댓글이 성공적으로 삭제되었습니다.");
            outVo.setErrorCode("SUCCESS");
            
            log.info("댓글 삭제 완료 - seq: {}", seq);
            return ResponseEntity.ok(outVo);
            
        } catch (Exception e) {
            log.error("댓글 삭제 실패 - seq: {}", seq, e);
            throw new BizException("댓글 삭제에 실패했습니다: " + e.getMessage());
        }
    }
}
