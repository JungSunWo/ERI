package com.ERI.demo.Controller;

import com.ERI.demo.dto.PageRequestDto;
import com.ERI.demo.dto.PageResponseDto;
import com.ERI.demo.service.EmpRightsBoardService;
import com.ERI.demo.service.FileUploadService;
import com.ERI.demo.vo.EmpRightsBoardVO;
import com.ERI.demo.vo.FileAttachVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
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
    private final FileUploadService fileUploadService;
    
    @Value("${file.upload.path:/uploads}")
    private String uploadPath;

    /**
     * 게시글 목록 조회 (페이징)
     */
    @GetMapping
    public ResponseEntity<Map<String, Object>> getBoardList(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String searchType,
            @RequestParam(required = false) String searchKeyword,
            @RequestParam(required = false) String categoryCd,
            @RequestParam(required = false) String stsCd,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate,
            @RequestParam(defaultValue = "regDate") String sortBy,
            @RequestParam(defaultValue = "DESC") String sortDirection) {

        Map<String, Object> response = new HashMap<>();
        
        try {
            PageRequestDto pageRequest = new PageRequestDto();
            pageRequest.setPage(page);
            pageRequest.setSize(size);
            pageRequest.setSortBy(sortBy);
            pageRequest.setSortDirection(sortDirection);

            EmpRightsBoardVO searchCondition = new EmpRightsBoardVO();
            searchCondition.setSearchType(searchType);
            searchCondition.setSearchKeyword(searchKeyword);
            searchCondition.setCategoryCd(categoryCd);
            searchCondition.setStsCd(stsCd);
            searchCondition.setStartDate(startDate);
            searchCondition.setEndDate(endDate);

            log.info("게시글 목록 조회 요청: page={}, size={}, searchType={}, searchKeyword={}, categoryCd={}", 
                    page, size, searchType, searchKeyword, categoryCd);

            PageResponseDto<EmpRightsBoardVO> result = empRightsBoardService.getBoardList(pageRequest, searchCondition);
            
            response.put("success", true);
            response.put("content", result.getContent());
            response.put("totalElements", result.getTotalElements());
            response.put("totalPages", result.getTotalPages());
            response.put("currentPage", result.getCurrentPage());
            response.put("size", result.getSize());
            response.put("hasNext", result.isHasNext());
            response.put("hasPrevious", result.isHasPrevious());
            response.put("startPage", result.getStartPage());
            response.put("endPage", result.getEndPage());
            response.put("message", "게시글 목록을 조회했습니다.");
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            log.error("게시글 목록 조회 실패", e);
            
            response.put("success", false);
            response.put("message", "게시글 목록 조회에 실패했습니다: " + e.getMessage());
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * 게시글 상세 조회
     */
    @GetMapping("/{seq}")
    public ResponseEntity<Map<String, Object>> getBoardDetail(@PathVariable Long seq, HttpSession session) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            log.info("게시글 상세 조회 요청: seq={}", seq);
            
            // 조회수 증가
            empRightsBoardService.incrementViewCount(seq);
            
            // 게시글 상세 조회
            EmpRightsBoardVO board = empRightsBoardService.getBoardBySeq(seq);
            
            if (board != null) {
                // 현재 로그인한 사용자가 작성자인지 확인
                com.ERI.demo.vo.employee.EmpLstVO empInfo = (com.ERI.demo.vo.employee.EmpLstVO) session.getAttribute("EMP_INFO");
                boolean isAuthor = false;
                if (empInfo != null) {
                    String sessionEmpId = empInfo.getEmpId();
                    isAuthor = sessionEmpId != null && sessionEmpId.equals(board.getRegEmpId());
                }
                
                response.put("success", true);
                response.put("data", board);
                response.put("isAuthor", isAuthor);
                response.put("message", "게시글을 조회했습니다.");
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("message", "존재하지 않는 게시글입니다.");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
            
        } catch (Exception e) {
            log.error("게시글 상세 조회 실패", e);
            
            response.put("success", false);
            response.put("message", "게시글 조회에 실패했습니다: " + e.getMessage());
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * 게시글 등록
     */
    @PostMapping
    public ResponseEntity<Map<String, Object>> createBoard(@RequestBody EmpRightsBoardVO board, HttpSession session) {
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
            
            board.setRegEmpId(sessionEmpId);
            board.setUpdEmpId(sessionEmpId);
            
            log.info("게시글 등록 요청: empId={}, title={}, categoryCd={}", 
                    sessionEmpId, board.getTtl(), board.getCategoryCd());
            
            EmpRightsBoardVO createdBoard = empRightsBoardService.createBoard(board);
            
            response.put("success", true);
            response.put("data", createdBoard);
            response.put("message", "게시글이 성공적으로 등록되었습니다.");
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            log.error("게시글 등록 실패", e);
            
            response.put("success", false);
            response.put("message", "게시글 등록에 실패했습니다: " + e.getMessage());
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * 게시글 수정
     */
    @PutMapping("/{seq}")
    public ResponseEntity<Map<String, Object>> updateBoard(@PathVariable Long seq, @RequestBody EmpRightsBoardVO board, HttpSession session) {
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
            
            // 기존 게시글 조회하여 작성자 확인
            EmpRightsBoardVO existingBoard = empRightsBoardService.getBoardBySeq(seq);
            if (existingBoard == null) {
                response.put("success", false);
                response.put("message", "존재하지 않는 게시글입니다.");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
            
            // 작성자 본인인지 확인
            if (!sessionEmpId.equals(existingBoard.getRegEmpId())) {
                response.put("success", false);
                response.put("message", "게시글 작성자만 수정할 수 있습니다.");
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
            }
            
            board.setSeq(seq);
            board.setUpdEmpId(sessionEmpId);
            
            log.info("게시글 수정 요청: seq={}, empId={}, title={}", seq, sessionEmpId, board.getTtl());
            
            EmpRightsBoardVO updatedBoard = empRightsBoardService.updateBoard(board);
            
            if (updatedBoard != null) {
                response.put("success", true);
                response.put("data", updatedBoard);
                response.put("message", "게시글이 성공적으로 수정되었습니다.");
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("message", "게시글 수정에 실패했습니다.");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
            }
            
        } catch (Exception e) {
            log.error("게시글 수정 실패", e);
            
            response.put("success", false);
            response.put("message", "게시글 수정에 실패했습니다: " + e.getMessage());
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * 게시글 삭제
     */
    @DeleteMapping("/{seq}")
    public ResponseEntity<Map<String, Object>> deleteBoard(@PathVariable Long seq, HttpSession session) {
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
            
            // 기존 게시글 조회하여 작성자 확인
            EmpRightsBoardVO existingBoard = empRightsBoardService.getBoardBySeq(seq);
            if (existingBoard == null) {
                response.put("success", false);
                response.put("message", "존재하지 않는 게시글입니다.");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
            
            // 작성자 본인인지 확인
            if (!sessionEmpId.equals(existingBoard.getRegEmpId())) {
                response.put("success", false);
                response.put("message", "게시글 작성자만 삭제할 수 있습니다.");
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
            }
            
            log.info("게시글 삭제 요청: seq={}, empId={}", seq, sessionEmpId);
            
            boolean deleted = empRightsBoardService.deleteBoard(seq, sessionEmpId);
            
            if (deleted) {
                response.put("success", true);
                response.put("message", "게시글이 성공적으로 삭제되었습니다.");
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("message", "게시글 삭제에 실패했습니다.");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
            }
            
        } catch (Exception e) {
            log.error("게시글 삭제 실패", e);
            
            response.put("success", false);
            response.put("message", "게시글 삭제에 실패했습니다: " + e.getMessage());
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * 공지글 목록 조회
     */
    @GetMapping("/notices")
    public ResponseEntity<Map<String, Object>> getNoticeList() {
        Map<String, Object> response = new HashMap<>();
        
        try {
            log.info("공지글 목록 조회 요청");
            
            List<EmpRightsBoardVO> notices = empRightsBoardService.getNoticeList();
            
            response.put("success", true);
            response.put("data", notices);
            response.put("message", "공지글 목록을 조회했습니다.");
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            log.error("공지글 목록 조회 실패", e);
            
            response.put("success", false);
            response.put("message", "공지글 목록 조회에 실패했습니다: " + e.getMessage());
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * 내가 작성한 게시글 목록 조회
     */
    @GetMapping("/my")
    public ResponseEntity<Map<String, Object>> getMyBoardList(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
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

            PageRequestDto pageRequest = new PageRequestDto();
            pageRequest.setPage(page);
            pageRequest.setSize(size);

            log.info("내 게시글 목록 조회 요청: empId={}, page={}, size={}", sessionEmpId, page, size);

            PageResponseDto<EmpRightsBoardVO> result = empRightsBoardService.getMyBoardList(sessionEmpId, pageRequest);
            
            response.put("success", true);
            response.put("content", result.getContent());
            response.put("totalElements", result.getTotalElements());
            response.put("totalPages", result.getTotalPages());
            response.put("currentPage", result.getCurrentPage());
            response.put("size", result.getSize());
            response.put("hasNext", result.isHasNext());
            response.put("hasPrevious", result.isHasPrevious());
            response.put("startPage", result.getStartPage());
            response.put("endPage", result.getEndPage());
            response.put("message", "내 게시글 목록을 조회했습니다.");
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            log.error("내 게시글 목록 조회 실패", e);
            
            response.put("success", false);
            response.put("message", "내 게시글 목록 조회에 실패했습니다: " + e.getMessage());
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * 파일 업로드
     */
    @PostMapping("/{seq}/files")
    public ResponseEntity<Map<String, Object>> uploadFiles(
            @PathVariable Long seq,
            @RequestParam("files") List<MultipartFile> files,
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
            
            // 게시글 존재 여부 확인
            EmpRightsBoardVO board = empRightsBoardService.getBoardBySeq(seq);
            if (board == null) {
                response.put("success", false);
                response.put("message", "존재하지 않는 게시글입니다.");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
            
            // 작성자 본인인지 확인
            if (!sessionEmpId.equals(board.getRegEmpId())) {
                response.put("success", false);
                response.put("message", "게시글 작성자만 파일을 업로드할 수 있습니다.");
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
            }
            
            log.info("파일 업로드 요청: boardSeq={}, empId={}, fileCount={}", seq, sessionEmpId, files.size());
            
            List<FileAttachVO> uploadedFiles = fileUploadService.uploadFiles(files, seq, sessionEmpId);
            
            response.put("success", true);
            response.put("data", uploadedFiles);
            response.put("message", "파일이 성공적으로 업로드되었습니다.");
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            log.error("파일 업로드 실패: boardSeq={}", seq, e);
            
            response.put("success", false);
            response.put("message", "파일 업로드에 실패했습니다: " + e.getMessage());
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * 게시글별 파일 목록 조회
     */
    @GetMapping("/{seq}/files")
    public ResponseEntity<Map<String, Object>> getFiles(@PathVariable Long seq) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            log.info("파일 목록 조회 요청: boardSeq={}", seq);
            
            List<FileAttachVO> files = fileUploadService.getFilesByBoardSeq(seq);
            
            response.put("success", true);
            response.put("data", files);
            response.put("message", "파일 목록을 조회했습니다.");
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            log.error("파일 목록 조회 실패: boardSeq={}", seq, e);
            
            response.put("success", false);
            response.put("message", "파일 목록 조회에 실패했습니다: " + e.getMessage());
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * 파일 다운로드/스트리밍
     */
    @GetMapping("/files/{fileSeq}/download")
    public ResponseEntity<org.springframework.core.io.Resource> downloadFile(@PathVariable Long fileSeq) {
        try {
            log.info("파일 다운로드/스트리밍 요청: fileSeq={}", fileSeq);
            log.info("요청 URL 패턴: /api/emp-rights-board/files/{}/download", fileSeq);
            
            FileAttachVO fileAttach = fileUploadService.downloadFile(fileSeq);
            if (fileAttach == null) {
                return ResponseEntity.notFound().build();
            }
            
            // 파일 경로 처리
            Path filePath;
            String filePathStr = fileAttach.getFilePath();
            
            log.info("파일 경로 처리: filePathStr={}, uploadPath={}", filePathStr, uploadPath);
            
            // 이미 절대 경로인지 확인
            if (filePathStr.startsWith("/") || filePathStr.contains(":")) {
                // 절대 경로인 경우 (기존 데이터)
                filePath = Paths.get(filePathStr);
                log.info("절대 경로 사용: {}", filePath);
            } else {
                // 상대 경로인 경우 (새로운 데이터)
                filePath = Paths.get(uploadPath, filePathStr);
                log.info("상대 경로 사용: {}", filePath);
            }
            
            org.springframework.core.io.Resource resource = new org.springframework.core.io.FileSystemResource(filePath.toFile());
            
            log.info("파일 존재 여부: exists={}, readable={}, filePath={}", 
                    resource.exists(), resource.isReadable(), filePath);
            
            if (resource.exists() && resource.isReadable()) {
                try {
                    // 이미지나 동영상 파일인 경우 스트리밍을 위해 inline으로 설정
                    String contentDisposition = "inline";
                    if (fileAttach.getFileType() != null && 
                        !fileAttach.getFileType().startsWith("image/") && 
                        !fileAttach.getFileType().startsWith("video/")) {
                        contentDisposition = "attachment; filename=\"" + fileAttach.getOriginalFileName() + "\"";
                    }
                    
                    return ResponseEntity.ok()
                        .header("Content-Disposition", contentDisposition)
                        .header("Content-Type", fileAttach.getFileType())
                        .header("Accept-Ranges", "bytes")
                        .header("Content-Length", String.valueOf(fileAttach.getFileSize()))
                        .body(resource);
                } catch (Exception e) {
                    log.warn("파일 스트리밍 중 클라이언트 연결 중단: fileSeq={}, error={}", fileSeq, e.getMessage());
                    // 클라이언트 연결 중단은 정상적인 상황이므로 로그만 출력하고 예외를 던지지 않음
                    return ResponseEntity.ok().build();
                }
            } else {
                return ResponseEntity.notFound().build();
            }
            
        } catch (Exception e) {
            log.error("파일 다운로드/스트리밍 실패: fileSeq={}", fileSeq, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * 파일 삭제
     */
    @DeleteMapping("/files/{fileSeq}")
    public ResponseEntity<Map<String, Object>> deleteFile(@PathVariable Long fileSeq, HttpSession session) {
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
            
            log.info("파일 삭제 요청: fileSeq={}, empId={}", fileSeq, sessionEmpId);
            
            fileUploadService.deleteFile(fileSeq, sessionEmpId);
            
            response.put("success", true);
            response.put("message", "파일이 성공적으로 삭제되었습니다.");
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            log.error("파일 삭제 실패: fileSeq={}", fileSeq, e);
            
            response.put("success", false);
            response.put("message", "파일 삭제에 실패했습니다: " + e.getMessage());
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    
    /**
     * 파일 링크 정보 업데이트
     */
    @PutMapping("/files/{fileSeq}/links")
    public ResponseEntity<Map<String, Object>> updateFileLinks(
            @PathVariable Long fileSeq,
            @RequestBody Map<String, Object> linkData,
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
            Object linksObj = linkData.get("links");
            String imageLinks = null;
            
            log.info("받은 linkData 전체: {}", linkData);
            log.info("linksObj: {}", linksObj);
            log.info("linksObj 타입: {}", linksObj != null ? linksObj.getClass().getName() : "null");
            
            if (linksObj != null) {
                if (linksObj instanceof String) {
                    imageLinks = (String) linksObj;
                    // 빈 문자열이나 "[]"인 경우 빈 배열로 설정
                    if (imageLinks == null || imageLinks.trim().isEmpty() || imageLinks.equals("[]")) {
                        imageLinks = "[]";
                    }
                } else {
                    // Object를 JSON 문자열로 변환
                    ObjectMapper objectMapper = new ObjectMapper();
                    try {
                        imageLinks = objectMapper.writeValueAsString(linksObj);
                        // 빈 배열인 경우 확인
                        if (imageLinks.equals("[]")) {
                            imageLinks = "[]";
                        }
                    } catch (Exception e) {
                        log.error("링크 객체를 JSON으로 변환 실패", e);
                        imageLinks = "[]";
                    }
                }
            } else {
                imageLinks = "[]"; // null인 경우 빈 배열로 설정
            }
            
            log.info("받은 링크 데이터: {}", linkData);
            log.info("받은 링크 데이터 타입: {}", linkData.getClass().getName());
            log.info("받은 링크 데이터 키들: {}", linkData.keySet());
            log.info("추출된 imageLinks: {}", imageLinks);
            log.info("추출된 imageLinks 타입: {}", imageLinks != null ? imageLinks.getClass().getName() : "null");
            
            if (imageLinks == null) {
                response.put("success", false);
                response.put("message", "링크 정보가 필요합니다.");
                return ResponseEntity.badRequest().body(response);
            }

            log.info("파일 링크 정보 업데이트 요청: fileSeq={}, empId={}", fileSeq, sessionEmpId);
            
            fileUploadService.updateFileLinks(fileSeq, imageLinks);
            
            response.put("success", true);
            response.put("message", "파일 링크 정보가 업데이트되었습니다.");
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            log.error("파일 링크 정보 업데이트 실패: fileSeq={}", fileSeq, e);
            
            response.put("success", false);
            response.put("message", "파일 링크 정보 업데이트에 실패했습니다: " + e.getMessage());
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    
    /**
     * 파일 링크 정보 조회
     */
    @GetMapping("/files/{fileSeq}/links")
    public ResponseEntity<Map<String, Object>> getFileLinks(@PathVariable Long fileSeq) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            log.info("파일 링크 정보 조회 요청: fileSeq={}", fileSeq);
            
            FileAttachVO fileLinkInfo = fileUploadService.getFileLinks(fileSeq);
            
            if (fileLinkInfo != null) {
                response.put("success", true);
                response.put("data", fileLinkInfo);
                response.put("message", "파일 링크 정보를 조회했습니다.");
            } else {
                response.put("success", false);
                response.put("message", "파일 링크 정보가 없습니다.");
            }
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            log.error("파일 링크 정보 조회 실패: fileSeq={}", fileSeq, e);
            
            response.put("success", false);
            response.put("message", "파일 링크 정보 조회에 실패했습니다: " + e.getMessage());
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
} 