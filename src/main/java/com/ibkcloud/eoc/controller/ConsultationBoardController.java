package com.ibkcloud.eoc.controller;

import com.ibkcloud.eoc.cmm.dto.PageRequest;
import com.ibkcloud.eoc.cmm.dto.Page;
import com.ibkcloud.eoc.cmm.exception.BizException;
import com.ibkcloud.eoc.controller.vo.*;
import com.ibkcloud.eoc.service.ConsultationBoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

/**
 * 상담 게시판 Controller
 */
@Slf4j
@RestController
@RequestMapping("/api/consultation")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@RequiredArgsConstructor
public class ConsultationBoardController {

    private final ConsultationBoardService consultationBoardService;

    /**
     * 상담 게시글 목록 조회
     */
    @GetMapping("/list")
    public ResponseEntity<ConsultationBoardSearchOutVo> inqConsultationBoardList(
            @Valid ConsultationBoardSearchInVo searchInVo,
            @Valid PageRequest pageRequest,
            HttpServletRequest request) {
        
        log.info("상담 게시글 목록 조회 시작 - 검색조건: {}", searchInVo);
        
        try {
            ConsultationBoardSearchOutVo result = consultationBoardService.inqConsultationBoardList(searchInVo, pageRequest);
            
            result.setSuccess(true);
            result.setMessage("상담 게시글 목록을 조회했습니다.");
            result.setErrorCode("SUCCESS");
            
            log.info("상담 게시글 목록 조회 완료 - 총 {}건", result.getCount());
            return ResponseEntity.ok(result);
            
        } catch (Exception e) {
            log.error("상담 게시글 목록 조회 실패", e);
            throw new BizException("상담 게시글 목록 조회에 실패했습니다: " + e.getMessage());
        }
    }

    /**
     * 상담 게시글 상세 조회
     */
    @GetMapping("/{seq}")
    public ResponseEntity<ConsultationBoardOutVo> inqConsultationBoardBySeq(@PathVariable Long seq) {
        log.info("상담 게시글 상세 조회 시작 - seq: {}", seq);
        
        try {
            ConsultationBoardOutVo result = consultationBoardService.inqConsultationBoardBySeq(seq);
            
            if (result == null) {
                throw new BizException("존재하지 않는 상담 게시글입니다.");
            }
            
            result.setSuccess(true);
            result.setMessage("상담 게시글 정보를 조회했습니다.");
            result.setErrorCode("SUCCESS");
            
            log.info("상담 게시글 상세 조회 완료 - seq: {}", seq);
            return ResponseEntity.ok(result);
            
        } catch (Exception e) {
            log.error("상담 게시글 상세 조회 실패 - seq: {}", seq, e);
            throw new BizException("상담 게시글 상세 조회에 실패했습니다: " + e.getMessage());
        }
    }

    /**
     * 상담 게시글 등록
     */
    @PostMapping("/create")
    public ResponseEntity<ConsultationBoardOutVo> rgsnConsultationBoard(
            @Valid ConsultationBoardInVo inVo,
            @RequestParam(value = "files", required = false) MultipartFile[] files,
            HttpServletRequest request) {
        
        log.info("상담 게시글 등록 시작 - 제목: {}", inVo.getTtl());
        
        try {
            // AuthInterceptor에서 전달받은 사용자 정보 사용
            String empId = (String) request.getAttribute("EMP_ID");
            
            if (empId == null) {
                throw new BizException("인증 정보를 찾을 수 없습니다.");
            }
            
            inVo.setRegEmpId(empId);
            ConsultationBoardOutVo result = consultationBoardService.rgsnConsultationBoard(inVo, files);
            
            result.setSuccess(true);
            result.setMessage("상담 게시글이 성공적으로 등록되었습니다.");
            result.setErrorCode("SUCCESS");
            
            log.info("상담 게시글 등록 완료 - seq: {}", result.getSeq());
            return ResponseEntity.ok(result);
            
        } catch (Exception e) {
            log.error("상담 게시글 등록 실패", e);
            throw new BizException("상담 게시글 등록에 실패했습니다: " + e.getMessage());
        }
    }

    /**
     * 상담 게시글 수정
     */
    @PutMapping("/{seq}")
    public ResponseEntity<ConsultationBoardOutVo> mdfcConsultationBoard(
            @PathVariable Long seq,
            @Valid ConsultationBoardInVo inVo,
            @RequestParam(value = "files", required = false) MultipartFile[] files,
            HttpServletRequest request) {
        
        log.info("상담 게시글 수정 시작 - seq: {}, 제목: {}", seq, inVo.getTtl());
        
        try {
            // AuthInterceptor에서 전달받은 사용자 정보 사용
            String empId = (String) request.getAttribute("EMP_ID");
            
            if (empId == null) {
                throw new BizException("인증 정보를 찾을 수 없습니다.");
            }
            
            inVo.setSeq(seq);
            inVo.setUpdEmpId(empId);
            ConsultationBoardOutVo result = consultationBoardService.mdfcConsultationBoard(inVo, files);
            
            if (result == null) {
                throw new BizException("상담 게시글 수정에 실패했습니다.");
            }
            
            result.setSuccess(true);
            result.setMessage("상담 게시글이 성공적으로 수정되었습니다.");
            result.setErrorCode("SUCCESS");
            
            log.info("상담 게시글 수정 완료 - seq: {}", seq);
            return ResponseEntity.ok(result);
            
        } catch (Exception e) {
            log.error("상담 게시글 수정 실패 - seq: {}", seq, e);
            throw new BizException("상담 게시글 수정에 실패했습니다: " + e.getMessage());
        }
    }

    /**
     * 상담 게시글 삭제
     */
    @DeleteMapping("/{seq}")
    public ResponseEntity<ConsultationBoardOutVo> delConsultationBoard(
            @PathVariable Long seq,
            HttpServletRequest request) {
        
        log.info("상담 게시글 삭제 시작 - seq: {}", seq);
        
        try {
            // AuthInterceptor에서 전달받은 사용자 정보 사용
            String empId = (String) request.getAttribute("EMP_ID");
            
            if (empId == null) {
                throw new BizException("인증 정보를 찾을 수 없습니다.");
            }
            
            boolean result = consultationBoardService.delConsultationBoard(seq, empId);
            
            if (!result) {
                throw new BizException("상담 게시글 삭제에 실패했습니다.");
            }
            
            ConsultationBoardOutVo outVo = new ConsultationBoardOutVo();
            outVo.setSuccess(true);
            outVo.setMessage("상담 게시글이 성공적으로 삭제되었습니다.");
            outVo.setErrorCode("SUCCESS");
            
            log.info("상담 게시글 삭제 완료 - seq: {}", seq);
            return ResponseEntity.ok(outVo);
            
        } catch (Exception e) {
            log.error("상담 게시글 삭제 실패 - seq: {}", seq, e);
            throw new BizException("상담 게시글 삭제에 실패했습니다: " + e.getMessage());
        }
    }

    /**
     * 상담 답변 등록/수정
     */
    @PostMapping("/{seq}/answer")
    public ResponseEntity<ConsultationAnswerOutVo> rgsnConsultationAnswer(
            @PathVariable Long seq,
            @Valid ConsultationAnswerInVo inVo,
            @RequestParam(value = "files", required = false) MultipartFile[] files,
            HttpServletRequest request) {
        
        log.info("상담 답변 저장 시작 - boardSeq: {}", seq);
        
        try {
            // AuthInterceptor에서 전달받은 사용자 정보 사용
            String empId = (String) request.getAttribute("EMP_ID");
            
            if (empId == null) {
                throw new BizException("인증 정보를 찾을 수 없습니다.");
            }
            
            inVo.setBoardSeq(seq);
            inVo.setRegEmpId(empId);
            ConsultationAnswerOutVo result = consultationBoardService.rgsnConsultationAnswer(inVo, files);
            
            result.setSuccess(true);
            result.setMessage("답변이 성공적으로 저장되었습니다.");
            result.setErrorCode("SUCCESS");
            
            log.info("상담 답변 저장 완료 - boardSeq: {}", seq);
            return ResponseEntity.ok(result);
            
        } catch (Exception e) {
            log.error("상담 답변 저장 실패 - boardSeq: {}", seq, e);
            throw new BizException("답변 저장에 실패했습니다: " + e.getMessage());
        }
    }

    /**
     * 내가 작성한 상담 게시글 목록 조회
     */
    @GetMapping("/my")
    public ResponseEntity<ConsultationBoardSearchOutVo> inqMyConsultationBoardList(
            @Valid PageRequest pageRequest,
            HttpServletRequest request) {
        
        log.info("내 상담 게시글 목록 조회 시작");
        
        try {
            // AuthInterceptor에서 전달받은 사용자 정보 사용
            String empId = (String) request.getAttribute("EMP_ID");
            
            if (empId == null) {
                throw new BizException("인증 정보를 찾을 수 없습니다.");
            }
            
            ConsultationBoardSearchOutVo result = consultationBoardService.inqMyConsultationBoardList(pageRequest, empId);
            
            result.setSuccess(true);
            result.setMessage("내 상담 게시글 목록을 조회했습니다.");
            result.setErrorCode("SUCCESS");
            
            log.info("내 상담 게시글 목록 조회 완료 - 총 {}건", result.getCount());
            return ResponseEntity.ok(result);
            
        } catch (Exception e) {
            log.error("내 상담 게시글 목록 조회 실패", e);
            throw new BizException("내 상담 게시글 목록 조회에 실패했습니다: " + e.getMessage());
        }
    }
}
