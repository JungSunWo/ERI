package com.ibkcloud.eoc.controller;

import com.ibkcloud.eoc.cmm.dto.PageRequest;
import com.ibkcloud.eoc.cmm.exception.BizException;
import com.ibkcloud.eoc.controller.vo.*;
import com.ibkcloud.eoc.service.BoardFileAttachService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

/**
 * 게시판 파일 첨부 관리 컨트롤러
 */
@Slf4j
@RestController
@RequestMapping("/api/board-file-attach")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@RequiredArgsConstructor
public class BoardFileAttachController {

    private final BoardFileAttachService boardFileAttachService;

    @Value("${file.upload.path:/uploads/board}")
    private String uploadPath;

    /**
     * 게시판 파일 첨부 목록 조회 (페이징/검색)
     */
    @GetMapping("/list")
    public ResponseEntity<BoardFileAttachOutVo> inqBoardFileAttachList(
            @Valid PageRequest pageRequest,
            @RequestParam(required = false) String searchType,
            @RequestParam(required = false) String searchKeyword,
            @RequestParam(required = false) Long brdSeq,
            @RequestParam(defaultValue = "FILE_SEQ") String sortBy,
            @RequestParam(defaultValue = "DESC") String sortDirection) {
        
        log.info("게시판 파일 첨부 목록 조회 시작 - searchType: {}, searchKeyword: {}, brdSeq: {}", 
                searchType, searchKeyword, brdSeq);
        
        try {
            BoardFileAttachInVo searchCondition = new BoardFileAttachInVo();
            searchCondition.setSearchType(searchType);
            searchCondition.setSearchKeyword(searchKeyword);
            searchCondition.setBrdSeq(brdSeq);
            searchCondition.setSortBy(sortBy);
            searchCondition.setSortDirection(sortDirection);
            
            BoardFileAttachOutVo result = boardFileAttachService.inqBoardFileAttachList(pageRequest, searchCondition);
            
            result.setSuccess(true);
            result.setMessage("게시판 파일 첨부 목록을 조회했습니다.");
            result.setErrorCode("SUCCESS");
            
            log.info("게시판 파일 첨부 목록 조회 완료 - 총 {}건", result.getCount());
            return ResponseEntity.ok(result);
            
        } catch (Exception e) {
            log.error("게시판 파일 첨부 목록 조회 실패", e);
            throw new BizException("게시판 파일 첨부 목록 조회에 실패했습니다: " + e.getMessage());
        }
    }

    /**
     * 게시판 파일 첨부 상세 조회
     */
    @GetMapping("/{fileSeq}")
    public ResponseEntity<BoardFileAttachOutVo> inqBoardFileAttachBySeq(@PathVariable Long fileSeq) {
        log.info("게시판 파일 첨부 상세 조회 시작 - fileSeq: {}", fileSeq);
        
        try {
            BoardFileAttachOutVo result = boardFileAttachService.inqBoardFileAttachBySeq(fileSeq);
            
            if (result == null) {
                throw new BizException("게시판 파일 첨부를 찾을 수 없습니다.");
            }
            
            result.setSuccess(true);
            result.setMessage("게시판 파일 첨부 정보를 조회했습니다.");
            result.setErrorCode("SUCCESS");
            
            log.info("게시판 파일 첨부 상세 조회 완료 - fileSeq: {}", fileSeq);
            return ResponseEntity.ok(result);
            
        } catch (Exception e) {
            log.error("게시판 파일 첨부 상세 조회 실패 - fileSeq: {}", fileSeq, e);
            throw new BizException("게시판 파일 첨부 조회에 실패했습니다: " + e.getMessage());
        }
    }

    /**
     * 게시글별 파일 첨부 목록 조회
     */
    @GetMapping("/board/{brdSeq}")
    public ResponseEntity<List<BoardFileAttachOutVo>> inqBoardFileAttachByBrdSeq(@PathVariable Long brdSeq) {
        log.info("게시글별 파일 첨부 목록 조회 시작 - brdSeq: {}", brdSeq);
        
        try {
            List<BoardFileAttachOutVo> result = boardFileAttachService.inqBoardFileAttachByBrdSeq(brdSeq);
            
            log.info("게시글별 파일 첨부 목록 조회 완료 - brdSeq: {}, 총 {}건", brdSeq, result.size());
            return ResponseEntity.ok(result);
            
        } catch (Exception e) {
            log.error("게시글별 파일 첨부 목록 조회 실패 - brdSeq: {}", brdSeq, e);
            throw new BizException("게시글별 파일 첨부 목록 조회에 실패했습니다: " + e.getMessage());
        }
    }

    /**
     * 게시판 파일 첨부 업로드
     */
    @PostMapping("/upload")
    public ResponseEntity<BoardFileAttachOutVo> rgsnBoardFileAttach(
            @RequestParam("file") MultipartFile file,
            @RequestParam("brdSeq") Long brdSeq,
            HttpServletRequest request) {
        
        log.info("게시판 파일 첨부 업로드 시작 - brdSeq: {}, fileName: {}", brdSeq, file.getOriginalFilename());
        
        try {
            // AuthInterceptor에서 전달받은 사용자 정보 사용
            String empId = (String) request.getAttribute("EMP_ID");
            
            if (empId == null) {
                throw new BizException("로그인이 필요합니다.");
            }
            
            BoardFileAttachOutVo result = boardFileAttachService.rgsnBoardFileAttach(file, brdSeq, empId);
            
            result.setSuccess(true);
            result.setMessage("게시판 파일 첨부가 성공적으로 업로드되었습니다.");
            result.setErrorCode("SUCCESS");
            
            log.info("게시판 파일 첨부 업로드 완료 - fileSeq: {}", result.getFileSeq());
            return ResponseEntity.ok(result);
            
        } catch (Exception e) {
            log.error("게시판 파일 첨부 업로드 실패 - brdSeq: {}", brdSeq, e);
            throw new BizException("게시판 파일 첨부 업로드에 실패했습니다: " + e.getMessage());
        }
    }

    /**
     * 게시판 파일 첨부 다운로드
     */
    @GetMapping("/download/{fileSeq}")
    public ResponseEntity<Resource> inqBoardFileAttachDownload(@PathVariable Long fileSeq) {
        log.info("게시판 파일 첨부 다운로드 시작 - fileSeq: {}", fileSeq);
        
        try {
            BoardFileAttachOutVo fileInfo = boardFileAttachService.inqBoardFileAttachBySeq(fileSeq);
            
            if (fileInfo == null) {
                throw new BizException("게시판 파일 첨부를 찾을 수 없습니다.");
            }
            
            Path filePath = Paths.get(uploadPath, fileInfo.getFilePath());
            Resource resource = new UrlResource(filePath.toUri());
            
            if (!resource.exists()) {
                throw new BizException("게시판 파일 첨부가 존재하지 않습니다.");
            }
            
            String encodedFileName = URLEncoder.encode(fileInfo.getOriginalFileName(), StandardCharsets.UTF_8);
            
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", encodedFileName);
            
            log.info("게시판 파일 첨부 다운로드 완료 - fileSeq: {}, fileName: {}", fileSeq, fileInfo.getOriginalFileName());
            return ResponseEntity.ok()
                    .headers(headers)
                    .body(resource);
                    
        } catch (Exception e) {
            log.error("게시판 파일 첨부 다운로드 실패 - fileSeq: {}", fileSeq, e);
            throw new BizException("게시판 파일 첨부 다운로드에 실패했습니다: " + e.getMessage());
        }
    }

    /**
     * 게시판 파일 첨부 미리보기
     */
    @GetMapping("/preview/{fileSeq}")
    public ResponseEntity<Resource> inqBoardFileAttachPreview(@PathVariable Long fileSeq) {
        log.info("게시판 파일 첨부 미리보기 시작 - fileSeq: {}", fileSeq);
        
        try {
            BoardFileAttachOutVo fileInfo = boardFileAttachService.inqBoardFileAttachBySeq(fileSeq);
            
            if (fileInfo == null) {
                throw new BizException("게시판 파일 첨부를 찾을 수 없습니다.");
            }
            
            Path filePath = Paths.get(uploadPath, fileInfo.getFilePath());
            Resource resource = new UrlResource(filePath.toUri());
            
            if (!resource.exists()) {
                throw new BizException("게시판 파일 첨부가 존재하지 않습니다.");
            }
            
            // 이미지 파일인지 확인
            if (!isImageFile(fileInfo.getFileExt())) {
                throw new BizException("이미지 파일만 미리보기가 가능합니다.");
            }
            
            MediaType mediaType = getMediaType(fileInfo.getFileExt());
            
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(mediaType);
            
            log.info("게시판 파일 첨부 미리보기 완료 - fileSeq: {}, fileName: {}", fileSeq, fileInfo.getOriginalFileName());
            return ResponseEntity.ok()
                    .headers(headers)
                    .body(resource);
                    
        } catch (Exception e) {
            log.error("게시판 파일 첨부 미리보기 실패 - fileSeq: {}", fileSeq, e);
            throw new BizException("게시판 파일 첨부 미리보기에 실패했습니다: " + e.getMessage());
        }
    }

    /**
     * 게시판 파일 첨부 수정
     */
    @PutMapping("/{fileSeq}")
    public ResponseEntity<BoardFileAttachOutVo> mdfcBoardFileAttach(
            @PathVariable Long fileSeq,
            @Valid BoardFileAttachInVo inVo,
            HttpServletRequest request) {
        
        log.info("게시판 파일 첨부 수정 시작 - fileSeq: {}", fileSeq);
        
        try {
            // AuthInterceptor에서 전달받은 사용자 정보 사용
            String empId = (String) request.getAttribute("EMP_ID");
            
            if (empId == null) {
                throw new BizException("로그인이 필요합니다.");
            }
            
            inVo.setFileSeq(fileSeq);
            inVo.setUpdEmpId(empId);
            
            BoardFileAttachOutVo result = boardFileAttachService.mdfcBoardFileAttach(inVo);
            
            if (result == null) {
                throw new BizException("게시판 파일 첨부를 찾을 수 없습니다.");
            }
            
            result.setSuccess(true);
            result.setMessage("게시판 파일 첨부가 성공적으로 수정되었습니다.");
            result.setErrorCode("SUCCESS");
            
            log.info("게시판 파일 첨부 수정 완료 - fileSeq: {}", fileSeq);
            return ResponseEntity.ok(result);
            
        } catch (Exception e) {
            log.error("게시판 파일 첨부 수정 실패 - fileSeq: {}", fileSeq, e);
            throw new BizException("게시판 파일 첨부 수정에 실패했습니다: " + e.getMessage());
        }
    }

    /**
     * 게시판 파일 첨부 삭제
     */
    @DeleteMapping("/{fileSeq}")
    public ResponseEntity<BoardFileAttachOutVo> delBoardFileAttach(@PathVariable Long fileSeq) {
        log.info("게시판 파일 첨부 삭제 시작 - fileSeq: {}", fileSeq);
        
        try {
            boolean result = boardFileAttachService.delBoardFileAttach(fileSeq);
            
            if (!result) {
                throw new BizException("게시판 파일 첨부 삭제에 실패했습니다.");
            }
            
            BoardFileAttachOutVo outVo = new BoardFileAttachOutVo();
            outVo.setSuccess(true);
            outVo.setMessage("게시판 파일 첨부가 성공적으로 삭제되었습니다.");
            outVo.setErrorCode("SUCCESS");
            
            log.info("게시판 파일 첨부 삭제 완료 - fileSeq: {}", fileSeq);
            return ResponseEntity.ok(outVo);
            
        } catch (Exception e) {
            log.error("게시판 파일 첨부 삭제 실패 - fileSeq: {}", fileSeq, e);
            throw new BizException("게시판 파일 첨부 삭제에 실패했습니다: " + e.getMessage());
        }
    }

    /**
     * 게시글별 파일 첨부 삭제
     */
    @DeleteMapping("/board/{brdSeq}")
    public ResponseEntity<BoardFileAttachOutVo> delBoardFileAttachByBrdSeq(@PathVariable Long brdSeq) {
        log.info("게시글별 파일 첨부 삭제 시작 - brdSeq: {}", brdSeq);
        
        try {
            boolean result = boardFileAttachService.delBoardFileAttachByBrdSeq(brdSeq);
            
            if (!result) {
                throw new BizException("게시글별 파일 첨부 삭제에 실패했습니다.");
            }
            
            BoardFileAttachOutVo outVo = new BoardFileAttachOutVo();
            outVo.setSuccess(true);
            outVo.setMessage("게시글별 파일 첨부가 성공적으로 삭제되었습니다.");
            outVo.setErrorCode("SUCCESS");
            
            log.info("게시글별 파일 첨부 삭제 완료 - brdSeq: {}", brdSeq);
            return ResponseEntity.ok(outVo);
            
        } catch (Exception e) {
            log.error("게시글별 파일 첨부 삭제 실패 - brdSeq: {}", brdSeq, e);
            throw new BizException("게시글별 파일 첨부 삭제에 실패했습니다: " + e.getMessage());
        }
    }

    /**
     * 이미지 링크 수정
     */
    @PutMapping("/{fileSeq}/img-links")
    public ResponseEntity<BoardFileAttachOutVo> mdfcImgLinks(
            @PathVariable Long fileSeq,
            @RequestBody Map<String, String> request) {
        
        log.info("이미지 링크 수정 시작 - fileSeq: {}", fileSeq);
        
        try {
            BoardFileAttachOutVo result = boardFileAttachService.mdfcImgLinks(fileSeq, request);
            
            if (result == null) {
                throw new BizException("게시판 파일 첨부를 찾을 수 없습니다.");
            }
            
            result.setSuccess(true);
            result.setMessage("이미지 링크가 성공적으로 수정되었습니다.");
            result.setErrorCode("SUCCESS");
            
            log.info("이미지 링크 수정 완료 - fileSeq: {}", fileSeq);
            return ResponseEntity.ok(result);
            
        } catch (Exception e) {
            log.error("이미지 링크 수정 실패 - fileSeq: {}", fileSeq, e);
            throw new BizException("이미지 링크 수정에 실패했습니다: " + e.getMessage());
        }
    }

    /**
     * 이미지 파일 여부 확인
     */
    private boolean isImageFile(String extension) {
        if (extension == null) return false;
        
        String ext = extension.toLowerCase();
        return ext.matches("(jpg|jpeg|png|gif|bmp|webp)");
    }

    /**
     * 미디어 타입 반환
     */
    private MediaType getMediaType(String extension) {
        if (extension == null) return MediaType.APPLICATION_OCTET_STREAM;
        
        String ext = extension.toLowerCase();
        switch (ext) {
            case "jpg":
            case "jpeg":
                return MediaType.IMAGE_JPEG;
            case "png":
                return MediaType.IMAGE_PNG;
            case "gif":
                return MediaType.IMAGE_GIF;
            case "bmp":
                return MediaType.valueOf("image/bmp");
            case "webp":
                return MediaType.valueOf("image/webp");
            default:
                return MediaType.APPLICATION_OCTET_STREAM;
        }
    }
} 