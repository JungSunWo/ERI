package com.ibkcloud.eoc.controller;

import com.ibkcloud.eoc.cmm.dto.PageRequest;
import com.ibkcloud.eoc.cmm.dto.Page;
import com.ibkcloud.eoc.cmm.exception.BizException;
import com.ibkcloud.eoc.controller.vo.*;
import com.ibkcloud.eoc.service.EmpRightsBoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * 직원권익게시판 API 컨트롤러
 */
@Slf4j
@RestController
@RequestMapping("/api/emp-rights-board")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@RequiredArgsConstructor
public class EmpRightsBoardController {

    private final EmpRightsBoardService empRightsBoardService;
    
    @Value("${file.upload.path:/uploads}")
    private String uploadPath;

    /**
     * 게시글 목록 조회 (페이징)
     */
    @GetMapping
    public ResponseEntity<EmpRightsBoardSearchOutVo> inqEmpRightsBoardList(
            @Valid EmpRightsBoardSearchInVo searchInVo,
            @Valid PageRequest pageRequest) {
        
        log.info("게시글 목록 조회 시작 - 검색조건: {}", searchInVo);
        
        try {
            EmpRightsBoardSearchOutVo result = empRightsBoardService.inqEmpRightsBoardList(searchInVo, pageRequest);
            
            result.setSuccess(true);
            result.setMessage("게시글 목록을 조회했습니다.");
            result.setErrorCode("SUCCESS");
            
            log.info("게시글 목록 조회 완료 - 총 {}건", result.getCount());
            return ResponseEntity.ok(result);
            
        } catch (Exception e) {
            log.error("게시글 목록 조회 실패", e);
            throw new BizException("게시글 목록 조회에 실패했습니다: " + e.getMessage());
        }
    }

    /**
     * 게시글 상세 조회
     */
    @GetMapping("/{seq}")
    public ResponseEntity<EmpRightsBoardOutVo> inqEmpRightsBoardBySeq(@PathVariable Long seq, HttpServletRequest request) {
        log.info("게시글 상세 조회 시작 - seq: {}", seq);
        
        try {
            // AuthInterceptor에서 전달받은 사용자 정보 사용
            String empId = (String) request.getAttribute("EMP_ID");
            
            EmpRightsBoardOutVo result = empRightsBoardService.inqEmpRightsBoardBySeq(seq, empId);
            
            if (result == null) {
                throw new BizException("존재하지 않는 게시글입니다.");
            }
            
            result.setSuccess(true);
            result.setMessage("게시글을 조회했습니다.");
            result.setErrorCode("SUCCESS");
            
            log.info("게시글 상세 조회 완료 - seq: {}", seq);
            return ResponseEntity.ok(result);
            
        } catch (Exception e) {
            log.error("게시글 상세 조회 실패 - seq: {}", seq, e);
            throw new BizException("게시글 조회에 실패했습니다: " + e.getMessage());
        }
    }

    /**
     * 게시글 등록
     */
    @PostMapping
    public ResponseEntity<EmpRightsBoardOutVo> rgsnEmpRightsBoard(
            @Valid EmpRightsBoardInVo inVo,
            HttpServletRequest request) {
        
        log.info("게시글 등록 시작 - 제목: {}", inVo.getTtl());
        
        try {
            // AuthInterceptor에서 전달받은 사용자 정보 사용
            String empId = (String) request.getAttribute("EMP_ID");
            
            if (empId == null) {
                throw new BizException("로그인이 필요합니다.");
            }
            
            inVo.setRegEmpId(empId);
            inVo.setUpdEmpId(empId);
            
            EmpRightsBoardOutVo result = empRightsBoardService.rgsnEmpRightsBoard(inVo);
            
            result.setSuccess(true);
            result.setMessage("게시글이 성공적으로 등록되었습니다.");
            result.setErrorCode("SUCCESS");
            
            log.info("게시글 등록 완료 - seq: {}", result.getSeq());
            return ResponseEntity.ok(result);
            
        } catch (Exception e) {
            log.error("게시글 등록 실패", e);
            throw new BizException("게시글 등록에 실패했습니다: " + e.getMessage());
        }
    }

    /**
     * 게시글 수정
     */
    @PutMapping("/{seq}")
    public ResponseEntity<EmpRightsBoardOutVo> mdfcEmpRightsBoard(
            @PathVariable Long seq,
            @Valid EmpRightsBoardInVo inVo,
            HttpServletRequest request) {
        
        log.info("게시글 수정 시작 - seq: {}, 제목: {}", seq, inVo.getTtl());
        
        try {
            // AuthInterceptor에서 전달받은 사용자 정보 사용
            String empId = (String) request.getAttribute("EMP_ID");
            
            if (empId == null) {
                throw new BizException("로그인이 필요합니다.");
            }
            
            inVo.setSeq(seq);
            inVo.setUpdEmpId(empId);
            
            EmpRightsBoardOutVo result = empRightsBoardService.mdfcEmpRightsBoard(inVo);
            
            if (result == null) {
                throw new BizException("게시글 수정에 실패했습니다.");
            }
            
            result.setSuccess(true);
            result.setMessage("게시글이 성공적으로 수정되었습니다.");
            result.setErrorCode("SUCCESS");
            
            log.info("게시글 수정 완료 - seq: {}", seq);
            return ResponseEntity.ok(result);
            
        } catch (Exception e) {
            log.error("게시글 수정 실패 - seq: {}", seq, e);
            throw new BizException("게시글 수정에 실패했습니다: " + e.getMessage());
        }
    }

    /**
     * 게시글 삭제
     */
    @DeleteMapping("/{seq}")
    public ResponseEntity<EmpRightsBoardOutVo> delEmpRightsBoard(
            @PathVariable Long seq,
            HttpServletRequest request) {
        
        log.info("게시글 삭제 시작 - seq: {}", seq);
        
        try {
            // AuthInterceptor에서 전달받은 사용자 정보 사용
            String empId = (String) request.getAttribute("EMP_ID");
            
            if (empId == null) {
                throw new BizException("로그인이 필요합니다.");
            }
            
            boolean result = empRightsBoardService.delEmpRightsBoard(seq, empId);
            
            if (!result) {
                throw new BizException("게시글 삭제에 실패했습니다.");
            }
            
            EmpRightsBoardOutVo outVo = new EmpRightsBoardOutVo();
            outVo.setSuccess(true);
            outVo.setMessage("게시글이 성공적으로 삭제되었습니다.");
            outVo.setErrorCode("SUCCESS");
            
            log.info("게시글 삭제 완료 - seq: {}", seq);
            return ResponseEntity.ok(outVo);
            
        } catch (Exception e) {
            log.error("게시글 삭제 실패 - seq: {}", seq, e);
            throw new BizException("게시글 삭제에 실패했습니다: " + e.getMessage());
        }
    }

    /**
     * 공지사항 목록 조회
     */
    @GetMapping("/notices")
    public ResponseEntity<EmpRightsBoardSearchOutVo> inqNoticeList() {
        log.info("공지사항 목록 조회 시작");
        
        try {
            EmpRightsBoardSearchOutVo result = empRightsBoardService.inqNoticeList();
            
            result.setSuccess(true);
            result.setMessage("공지사항 목록을 조회했습니다.");
            result.setErrorCode("SUCCESS");
            
            log.info("공지사항 목록 조회 완료 - 총 {}건", result.getCount());
            return ResponseEntity.ok(result);
            
        } catch (Exception e) {
            log.error("공지사항 목록 조회 실패", e);
            throw new BizException("공지사항 목록 조회에 실패했습니다: " + e.getMessage());
        }
    }

    /**
     * 내가 작성한 게시글 목록 조회
     */
    @GetMapping("/my")
    public ResponseEntity<EmpRightsBoardSearchOutVo> inqMyEmpRightsBoardList(
            @Valid PageRequest pageRequest,
            HttpServletRequest request) {
        
        log.info("내가 작성한 게시글 목록 조회 시작");
        
        try {
            // AuthInterceptor에서 전달받은 사용자 정보 사용
            String empId = (String) request.getAttribute("EMP_ID");
            
            if (empId == null) {
                throw new BizException("로그인이 필요합니다.");
            }
            
            EmpRightsBoardSearchOutVo result = empRightsBoardService.inqMyEmpRightsBoardList(pageRequest, empId);
            
            result.setSuccess(true);
            result.setMessage("내가 작성한 게시글 목록을 조회했습니다.");
            result.setErrorCode("SUCCESS");
            
            log.info("내가 작성한 게시글 목록 조회 완료 - 총 {}건", result.getCount());
            return ResponseEntity.ok(result);
            
        } catch (Exception e) {
            log.error("내가 작성한 게시글 목록 조회 실패", e);
            throw new BizException("내가 작성한 게시글 목록 조회에 실패했습니다: " + e.getMessage());
        }
    }

    /**
     * 파일 업로드
     */
    @PostMapping("/{seq}/files")
    public ResponseEntity<EmpRightsFileAttachOutVo> rgsnEmpRightsFileAttach(
            @PathVariable Long seq,
            @RequestParam("files") List<MultipartFile> files,
            HttpServletRequest request) {
        
        log.info("파일 업로드 시작 - boardSeq: {}, 파일 개수: {}", seq, files.size());
        
        try {
            // AuthInterceptor에서 전달받은 사용자 정보 사용
            String empId = (String) request.getAttribute("EMP_ID");
            
            if (empId == null) {
                throw new BizException("로그인이 필요합니다.");
            }
            
            EmpRightsFileAttachOutVo result = empRightsBoardService.rgsnEmpRightsFileAttach(seq, files, empId);
            
            result.setSuccess(true);
            result.setMessage("파일이 성공적으로 업로드되었습니다.");
            result.setErrorCode("SUCCESS");
            
            log.info("파일 업로드 완료 - boardSeq: {}", seq);
            return ResponseEntity.ok(result);
            
        } catch (Exception e) {
            log.error("파일 업로드 실패 - boardSeq: {}", seq, e);
            throw new BizException("파일 업로드에 실패했습니다: " + e.getMessage());
        }
    }

    /**
     * 파일 목록 조회
     */
    @GetMapping("/{seq}/files")
    public ResponseEntity<EmpRightsFileAttachOutVo> inqEmpRightsFileAttachList(@PathVariable Long seq) {
        log.info("파일 목록 조회 시작 - boardSeq: {}", seq);
        
        try {
            EmpRightsFileAttachOutVo result = empRightsBoardService.inqEmpRightsFileAttachList(seq);
            
            result.setSuccess(true);
            result.setMessage("파일 목록을 조회했습니다.");
            result.setErrorCode("SUCCESS");
            
            log.info("파일 목록 조회 완료 - boardSeq: {}", seq);
            return ResponseEntity.ok(result);
            
        } catch (Exception e) {
            log.error("파일 목록 조회 실패 - boardSeq: {}", seq, e);
            throw new BizException("파일 목록 조회에 실패했습니다: " + e.getMessage());
        }
    }

    /**
     * 파일 다운로드
     */
    @GetMapping("/files/{fileSeq}/download")
    public ResponseEntity<Resource> inqEmpRightsFileAttachDownload(@PathVariable Long fileSeq) {
        log.info("파일 다운로드 시작 - fileSeq: {}", fileSeq);
        
        try {
            Resource resource = empRightsBoardService.inqEmpRightsFileAttachDownload(fileSeq);
            
            log.info("파일 다운로드 완료 - fileSeq: {}", fileSeq);
            return ResponseEntity.ok(resource);
            
        } catch (Exception e) {
            log.error("파일 다운로드 실패 - fileSeq: {}", fileSeq, e);
            throw new BizException("파일 다운로드에 실패했습니다: " + e.getMessage());
        }
    }

    /**
     * 파일 삭제
     */
    @DeleteMapping("/files/{fileSeq}")
    public ResponseEntity<EmpRightsFileAttachOutVo> delEmpRightsFileAttach(
            @PathVariable Long fileSeq,
            HttpServletRequest request) {
        
        log.info("파일 삭제 시작 - fileSeq: {}", fileSeq);
        
        try {
            // AuthInterceptor에서 전달받은 사용자 정보 사용
            String empId = (String) request.getAttribute("EMP_ID");
            
            if (empId == null) {
                throw new BizException("로그인이 필요합니다.");
            }
            
            boolean result = empRightsBoardService.delEmpRightsFileAttach(fileSeq, empId);
            
            if (!result) {
                throw new BizException("파일 삭제에 실패했습니다.");
            }
            
            EmpRightsFileAttachOutVo outVo = new EmpRightsFileAttachOutVo();
            outVo.setSuccess(true);
            outVo.setMessage("파일이 성공적으로 삭제되었습니다.");
            outVo.setErrorCode("SUCCESS");
            
            log.info("파일 삭제 완료 - fileSeq: {}", fileSeq);
            return ResponseEntity.ok(outVo);
            
        } catch (Exception e) {
            log.error("파일 삭제 실패 - fileSeq: {}", fileSeq, e);
            throw new BizException("파일 삭제에 실패했습니다: " + e.getMessage());
        }
    }

    /**
     * 파일 링크 업데이트
     */
    @PutMapping("/files/{fileSeq}/links")
    public ResponseEntity<EmpRightsFileAttachOutVo> mdfcEmpRightsFileAttachLinks(
            @PathVariable Long fileSeq,
            @RequestBody Map<String, Object> linkData,
            HttpServletRequest request) {
        
        log.info("파일 링크 업데이트 시작 - fileSeq: {}", fileSeq);
        
        try {
            // AuthInterceptor에서 전달받은 사용자 정보 사용
            String empId = (String) request.getAttribute("EMP_ID");
            
            if (empId == null) {
                throw new BizException("로그인이 필요합니다.");
            }
            
            EmpRightsFileAttachOutVo result = empRightsBoardService.mdfcEmpRightsFileAttachLinks(fileSeq, linkData, empId);
            
            result.setSuccess(true);
            result.setMessage("파일 링크가 성공적으로 업데이트되었습니다.");
            result.setErrorCode("SUCCESS");
            
            log.info("파일 링크 업데이트 완료 - fileSeq: {}", fileSeq);
            return ResponseEntity.ok(result);
            
        } catch (Exception e) {
            log.error("파일 링크 업데이트 실패 - fileSeq: {}", fileSeq, e);
            throw new BizException("파일 링크 업데이트에 실패했습니다: " + e.getMessage());
        }
    }

    /**
     * 파일 링크 조회
     */
    @GetMapping("/files/{fileSeq}/links")
    public ResponseEntity<EmpRightsFileAttachOutVo> inqEmpRightsFileAttachLinks(@PathVariable Long fileSeq) {
        log.info("파일 링크 조회 시작 - fileSeq: {}", fileSeq);
        
        try {
            EmpRightsFileAttachOutVo result = empRightsBoardService.inqEmpRightsFileAttachLinks(fileSeq);
            
            result.setSuccess(true);
            result.setMessage("파일 링크를 조회했습니다.");
            result.setErrorCode("SUCCESS");
            
            log.info("파일 링크 조회 완료 - fileSeq: {}", fileSeq);
            return ResponseEntity.ok(result);
            
        } catch (Exception e) {
            log.error("파일 링크 조회 실패 - fileSeq: {}", fileSeq, e);
            throw new BizException("파일 링크 조회에 실패했습니다: " + e.getMessage());
        }
    }
}
