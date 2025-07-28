package com.ERI.demo.Controller;

import com.ERI.demo.dto.PageRequestDto;
import com.ERI.demo.dto.PageResponseDto;
import com.ERI.demo.vo.ConsultationAnswerVO;
import com.ERI.demo.vo.ConsultationBoardVO;
import com.ERI.demo.vo.ConsultationFileAttachVO;
import com.ERI.demo.service.ConsultationBoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

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
    public ResponseEntity<Map<String, Object>> getConsultationBoardList(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "regDate") String sortBy,
            @RequestParam(defaultValue = "DESC") String sortDirection,
            @RequestParam(required = false) String searchType,
            @RequestParam(required = false) String searchKeyword,
            @RequestParam(required = false) String categoryCd,
            @RequestParam(required = false) String stsCd,
            @RequestParam(required = false) String anonymousYn,
            @RequestParam(required = false) String urgentYn,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate) {
        
        Map<String, Object> response = new HashMap<>();
        
        try {
            PageRequestDto pageRequest = PageRequestDto.builder()
                .page(page)
                .size(size)
                .sortBy(sortBy)
                .sortDirection(sortDirection)
                .build();
            
            ConsultationBoardVO searchCondition = new ConsultationBoardVO();
            searchCondition.setSearchType(searchType);
            searchCondition.setSearchKeyword(searchKeyword);
            searchCondition.setCategoryCd(categoryCd);
            searchCondition.setStsCd(stsCd);
            searchCondition.setAnonymousYn(anonymousYn);
            searchCondition.setUrgentYn(urgentYn);
            searchCondition.setStartDate(startDate);
            searchCondition.setEndDate(endDate);
            
            PageResponseDto<ConsultationBoardVO> result = consultationBoardService.getConsultationBoardList(pageRequest, searchCondition);
            
            response.put("success", true);
            response.put("data", result);
            response.put("message", "상담 게시글 목록을 조회했습니다.");
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            log.error("상담 게시글 목록 조회 실패", e);
            
            response.put("success", false);
            response.put("message", "상담 게시글 목록 조회에 실패했습니다: " + e.getMessage());
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * 상담 게시글 상세 조회
     */
    @GetMapping("/{seq}")
    public ResponseEntity<Map<String, Object>> getConsultationBoardBySeq(@PathVariable Long seq) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            ConsultationBoardVO consultationBoard = consultationBoardService.getConsultationBoardBySeq(seq);
            
            if (consultationBoard != null) {
                response.put("success", true);
                response.put("data", consultationBoard);
                response.put("message", "상담 게시글 정보를 조회했습니다.");
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("message", "존재하지 않는 상담 게시글입니다.");
                return ResponseEntity.notFound().build();
            }
            
        } catch (Exception e) {
            log.error("상담 게시글 상세 조회 실패", e);
            
            response.put("success", false);
            response.put("message", "상담 게시글 상세 조회에 실패했습니다: " + e.getMessage());
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * 상담 게시글 등록
     */
    @PostMapping("/create")
    public ResponseEntity<Map<String, Object>> createConsultationBoard(
            @RequestParam("ttl") String ttl,
            @RequestParam("cntn") String cntn,
            @RequestParam("categoryCd") String categoryCd,
            @RequestParam("anonymousYn") String anonymousYn,
            @RequestParam(value = "nickname", required = false) String nickname,
            @RequestParam("priorityCd") String priorityCd,
            @RequestParam("urgentYn") String urgentYn,
            @RequestParam(value = "files", required = false) MultipartFile[] files,
            HttpServletRequest request) {
        
        Map<String, Object> response = new HashMap<>();
        
        try {
            // AuthInterceptor에서 전달받은 사용자 정보 사용
            String empId = (String) request.getAttribute("EMP_ID");
            
            if (empId == null) {
                response.put("success", false);
                response.put("message", "인증 정보를 찾을 수 없습니다.");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }
            
            ConsultationBoardVO consultationBoard = new ConsultationBoardVO();
            consultationBoard.setTtl(ttl);
            consultationBoard.setCntn(cntn);
            consultationBoard.setCategoryCd(categoryCd);
            consultationBoard.setAnonymousYn(anonymousYn);
            consultationBoard.setNickname(nickname);
            consultationBoard.setPriorityCd(priorityCd);
            consultationBoard.setUrgentYn(urgentYn);
            
            ConsultationBoardVO result = consultationBoardService.createConsultationBoard(consultationBoard, files, empId);
            
            response.put("success", true);
            response.put("message", "상담 게시글이 성공적으로 등록되었습니다.");
            response.put("data", result);
            
            return ResponseEntity.ok(response);
            
        } catch (IllegalArgumentException e) {
            log.warn("상담 게시글 등록 유효성 검사 실패: {}", e.getMessage());
            
            response.put("success", false);
            response.put("message", e.getMessage());
            
            return ResponseEntity.badRequest().body(response);
            
        } catch (Exception e) {
            log.error("상담 게시글 등록 실패", e);
            
            response.put("success", false);
            response.put("message", "상담 게시글 등록에 실패했습니다: " + e.getMessage());
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * 상담 게시글 수정
     */
    @PutMapping("/{seq}")
    public ResponseEntity<Map<String, Object>> updateConsultationBoard(
            @PathVariable Long seq,
            @RequestParam("ttl") String ttl,
            @RequestParam("cntn") String cntn,
            @RequestParam("categoryCd") String categoryCd,
            @RequestParam("anonymousYn") String anonymousYn,
            @RequestParam(value = "nickname", required = false) String nickname,
            @RequestParam("priorityCd") String priorityCd,
            @RequestParam("urgentYn") String urgentYn,
            @RequestParam(value = "files", required = false) MultipartFile[] files,
            HttpServletRequest request) {
        
        Map<String, Object> response = new HashMap<>();
        
        try {
            // AuthInterceptor에서 전달받은 사용자 정보 사용
            String empId = (String) request.getAttribute("EMP_ID");
            
            if (empId == null) {
                response.put("success", false);
                response.put("message", "인증 정보를 찾을 수 없습니다.");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }
            
            ConsultationBoardVO consultationBoard = new ConsultationBoardVO();
            consultationBoard.setSeq(seq);
            consultationBoard.setTtl(ttl);
            consultationBoard.setCntn(cntn);
            consultationBoard.setCategoryCd(categoryCd);
            consultationBoard.setAnonymousYn(anonymousYn);
            consultationBoard.setNickname(nickname);
            consultationBoard.setPriorityCd(priorityCd);
            consultationBoard.setUrgentYn(urgentYn);
            
            ConsultationBoardVO result = consultationBoardService.updateConsultationBoard(consultationBoard, files, empId);
            
            if (result != null) {
                response.put("success", true);
                response.put("message", "상담 게시글이 성공적으로 수정되었습니다.");
                response.put("data", result);
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("message", "상담 게시글 수정에 실패했습니다.");
                return ResponseEntity.badRequest().body(response);
            }
            
        } catch (IllegalArgumentException e) {
            log.warn("상담 게시글 수정 유효성 검사 실패: {}", e.getMessage());
            
            response.put("success", false);
            response.put("message", e.getMessage());
            
            return ResponseEntity.badRequest().body(response);
            
        } catch (Exception e) {
            log.error("상담 게시글 수정 실패", e);
            
            response.put("success", false);
            response.put("message", "상담 게시글 수정에 실패했습니다: " + e.getMessage());
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * 상담 게시글 삭제
     */
    @DeleteMapping("/{seq}")
    public ResponseEntity<Map<String, Object>> deleteConsultationBoard(
            @PathVariable Long seq,
            HttpServletRequest request) {
        
        Map<String, Object> response = new HashMap<>();
        
        try {
            // AuthInterceptor에서 전달받은 사용자 정보 사용
            String empId = (String) request.getAttribute("EMP_ID");
            
            if (empId == null) {
                response.put("success", false);
                response.put("message", "인증 정보를 찾을 수 없습니다.");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }
            
            boolean result = consultationBoardService.deleteConsultationBoard(seq, empId);
            
            if (result) {
                response.put("success", true);
                response.put("message", "상담 게시글이 성공적으로 삭제되었습니다.");
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("message", "상담 게시글 삭제에 실패했습니다.");
                return ResponseEntity.badRequest().body(response);
            }
            
        } catch (IllegalArgumentException e) {
            log.warn("상담 게시글 삭제 유효성 검사 실패: {}", e.getMessage());
            
            response.put("success", false);
            response.put("message", e.getMessage());
            
            return ResponseEntity.badRequest().body(response);
            
        } catch (Exception e) {
            log.error("상담 게시글 삭제 실패", e);
            
            response.put("success", false);
            response.put("message", "상담 게시글 삭제에 실패했습니다: " + e.getMessage());
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * 상담 답변 등록/수정
     */
    @PostMapping("/{seq}/answer")
    public ResponseEntity<Map<String, Object>> saveConsultationAnswer(
            @PathVariable Long seq,
            @RequestParam("cntn") String cntn,
            @RequestParam(value = "files", required = false) MultipartFile[] files,
            HttpServletRequest request) {
        
        Map<String, Object> response = new HashMap<>();
        
        try {
            // AuthInterceptor에서 전달받은 사용자 정보 사용
            String empId = (String) request.getAttribute("EMP_ID");
            
            if (empId == null) {
                response.put("success", false);
                response.put("message", "인증 정보를 찾을 수 없습니다.");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }
            
            ConsultationAnswerVO consultationAnswer = new ConsultationAnswerVO();
            consultationAnswer.setBoardSeq(seq);
            consultationAnswer.setCntn(cntn);
            
            ConsultationAnswerVO result = consultationBoardService.saveConsultationAnswer(consultationAnswer, files, empId);
            
            response.put("success", true);
            response.put("message", "답변이 성공적으로 저장되었습니다.");
            response.put("data", result);
            
            return ResponseEntity.ok(response);
            
        } catch (IllegalArgumentException e) {
            log.warn("상담 답변 저장 유효성 검사 실패: {}", e.getMessage());
            
            response.put("success", false);
            response.put("message", e.getMessage());
            
            return ResponseEntity.badRequest().body(response);
            
        } catch (Exception e) {
            log.error("상담 답변 저장 실패", e);
            
            response.put("success", false);
            response.put("message", "답변 저장에 실패했습니다: " + e.getMessage());
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * 내가 작성한 상담 게시글 목록 조회
     */
    @GetMapping("/my")
    public ResponseEntity<Map<String, Object>> getMyConsultationBoardList(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            HttpServletRequest request) {
        
        Map<String, Object> response = new HashMap<>();
        
        try {
            // AuthInterceptor에서 전달받은 사용자 정보 사용
            String empId = (String) request.getAttribute("EMP_ID");
            
            if (empId == null) {
                response.put("success", false);
                response.put("message", "인증 정보를 찾을 수 없습니다.");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }
            
            PageResponseDto<ConsultationBoardVO> result = consultationBoardService.getMyConsultationBoardList(page, size, empId);
            
            response.put("success", true);
            response.put("data", result);
            response.put("message", "내 상담 게시글 목록을 조회했습니다.");
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            log.error("내 상담 게시글 목록 조회 실패", e);
            
            response.put("success", false);
            response.put("message", "내 상담 게시글 목록 조회에 실패했습니다: " + e.getMessage());
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
} 