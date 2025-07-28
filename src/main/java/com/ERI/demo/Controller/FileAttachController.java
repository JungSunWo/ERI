package com.ERI.demo.Controller;

import com.ERI.demo.service.FileAttachService;
import com.ERI.demo.vo.FileAttachVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/file")
@RequiredArgsConstructor
public class FileAttachController {

    private final FileAttachService fileAttachService;

    @Value("${file.upload.path:/uploads}")
    private String uploadPath;

    /**
     * 첨부파일 목록 조회 (함수 대체)
     */
    @GetMapping("/list")
    public ResponseEntity<Map<String, Object>> getFileList(
            @RequestParam String refTblCd,
            @RequestParam String refPkVal) {
        
        Map<String, Object> response = new HashMap<>();
        
        try {
            List<FileAttachVO> fileList = fileAttachService.getFileAttachList(refTblCd, refPkVal);
            
            response.put("success", true);
            response.put("data", fileList);
            response.put("message", "첨부파일 목록을 조회했습니다.");
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            log.error("첨부파일 목록 조회 실패", e);
            
            response.put("success", false);
            response.put("message", "첨부파일 목록 조회에 실패했습니다: " + e.getMessage());
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * 파일 업로드 (단일/다중 모두 지원)
     */
    @PostMapping("/upload")
    public ResponseEntity<Map<String, Object>> uploadFiles(
            @RequestParam("files") MultipartFile[] files,
            @RequestParam("refTblCd") String refTblCd,
            @RequestParam("refPkVal") String refPkVal,
            @RequestParam(value = "fileDescs", required = false) String[] fileDescs,
            @RequestParam(value = "empId", defaultValue = "SYSTEM") String empId,
            HttpSession session) {
        
        Map<String, Object> response = new HashMap<>();
        
        // 세션에서 사용자 정보 확인
        com.ERI.demo.vo.employee.EmpLstVO empInfo = (com.ERI.demo.vo.employee.EmpLstVO) session.getAttribute("EMP_INFO");
        if (empInfo == null) {
            response.put("success", false);
            response.put("message", "로그인이 필요합니다.");
            return ResponseEntity.status(401).body(response);
        }
        
        String sessionEmpId = empInfo.getEmpId();
        
        try {
            List<FileAttachVO> uploadedFiles = fileAttachService.uploadMultipleFiles(
                files, refTblCd, refPkVal, fileDescs, sessionEmpId);
            
            response.put("success", true);
            response.put("data", uploadedFiles);
            response.put("message", uploadedFiles.size() + "개의 파일이 성공적으로 업로드되었습니다.");
            
            return ResponseEntity.ok(response);
            
        } catch (IllegalArgumentException e) {
            log.warn("파일 업로드 유효성 검사 실패: {}", e.getMessage());
            
            response.put("success", false);
            response.put("message", e.getMessage());
            
            return ResponseEntity.badRequest().body(response);
            
        } catch (Exception e) {
            log.error("파일 업로드 실패", e);
            
            response.put("success", false);
            response.put("message", "파일 업로드에 실패했습니다: " + e.getMessage());
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * 파일 업로드 (React 프론트엔드 호환용)
     */
    @PostMapping("/upload-multiple")
    public ResponseEntity<Map<String, Object>> uploadMultipleFiles(
            @RequestParam("files") MultipartFile[] files,
            @RequestParam("refTblCd") String refTblCd,
            @RequestParam("refPkVal") String refPkVal,
            @RequestParam(value = "fileDescs", required = false) String[] fileDescs,
            @RequestParam(value = "empId", defaultValue = "SYSTEM") String empId,
            HttpSession session) {
        
        // 기존 uploadFiles 메서드 호출
        return uploadFiles(files, refTblCd, refPkVal, fileDescs, empId, session);
    }

    /**
     * 파일 다운로드
     */
    @GetMapping("/download/{fileSeq}")
    public ResponseEntity<Resource> downloadFile(@PathVariable Long fileSeq) {
        try {
            FileAttachVO fileInfo = fileAttachService.getFileAttach(fileSeq);
            if (fileInfo == null) {
                return ResponseEntity.notFound().build();
            }
            
            // 파일 경로 생성 (TB_FILE_ATTACH 또는 TB_BOARD_FILE_ATTACH에 따라)
            String fileName = fileInfo.getFileSaveNm() != null ? fileInfo.getFileSaveNm() : fileInfo.getStoredFileName();
            Path filePath = Paths.get(uploadPath + fileInfo.getFilePath() + fileName);
            Resource resource = new UrlResource(filePath.toUri());
            
            if (!resource.exists()) {
                return ResponseEntity.notFound().build();
            }
            
            // 다운로드 횟수 증가 (함수 대체)
            Integer newCount = fileAttachService.incrementDownloadCount(fileSeq);
            log.info("파일 다운로드 횟수 증가: fileSeq={}, newCount={}", fileSeq, newCount);
            
            // 원본 파일명 결정 (TB_FILE_ATTACH 또는 TB_BOARD_FILE_ATTACH에 따라)
            String originalFileName = fileInfo.getFileNm() != null ? fileInfo.getFileNm() : fileInfo.getOriginalFileName();
            
            // 원본 파일명 URL 인코딩 (한글, 특수문자 처리)
            String encodedFileName = URLEncoder.encode(originalFileName, StandardCharsets.UTF_8.toString())
                    .replaceAll("\\+", "%20");
            
            // 파일 확장자에 따른 Content-Type 설정
            MediaType contentType = getContentType(fileInfo.getFileExt());
            
            // 다운로드 헤더 설정 (브라우저 호환성을 위한 이중 헤더)
            return ResponseEntity.ok()
                    .contentType(contentType)
                    .header(HttpHeaders.CONTENT_DISPOSITION, 
                            "attachment; filename=\"" + originalFileName + "\"; filename*=UTF-8''" + encodedFileName)
                    .header(HttpHeaders.CONTENT_LENGTH, String.valueOf(fileInfo.getFileSize()))
                    .body(resource);
                    
        } catch (MalformedURLException e) {
            log.error("파일 다운로드 실패: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (Exception e) {
            log.error("파일 다운로드 처리 실패: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * 이미지 파일 미리보기
     */
    @GetMapping("/preview/{fileSeq}")
    public ResponseEntity<Resource> previewFile(@PathVariable Long fileSeq) {
        try {
            FileAttachVO fileInfo = fileAttachService.getFileAttach(fileSeq);
            if (fileInfo == null) {
                return ResponseEntity.notFound().build();
            }
            
            // 이미지 파일인지 확인
            String extension = fileInfo.getFileExt().toLowerCase();
            if (!isImageFile(extension)) {
                return ResponseEntity.badRequest().build();
            }
            
            // 파일 경로 생성 (TB_FILE_ATTACH 또는 TB_BOARD_FILE_ATTACH에 따라)
            String fileName = fileInfo.getFileSaveNm() != null ? fileInfo.getFileSaveNm() : fileInfo.getStoredFileName();
            Path filePath = Paths.get(uploadPath + fileInfo.getFilePath() + fileName);
            Resource resource = new UrlResource(filePath.toUri());
            
            if (!resource.exists()) {
                return ResponseEntity.notFound().build();
            }
            
            // Content-Type 설정
            MediaType mediaType = getMediaType(extension);
            
            return ResponseEntity.ok()
                    .contentType(mediaType)
                    .body(resource);
                    
        } catch (MalformedURLException e) {
            log.error("파일 미리보기 실패: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * 첨부파일 삭제
     */
    @DeleteMapping("/{fileSeq}")
    public ResponseEntity<Map<String, Object>> deleteFile(
            @PathVariable Long fileSeq,
            @RequestParam(value = "empId", defaultValue = "SYSTEM") String empId,
            HttpSession session) {
        
        Map<String, Object> response = new HashMap<>();
        
        // 세션에서 사용자 정보 확인
        com.ERI.demo.vo.employee.EmpLstVO empInfo = (com.ERI.demo.vo.employee.EmpLstVO) session.getAttribute("EMP_INFO");
        if (empInfo == null) {
            response.put("success", false);
            response.put("message", "로그인이 필요합니다.");
            return ResponseEntity.status(401).body(response);
        }
        
        String sessionEmpId = empInfo.getEmpId();
        
        try {
            boolean deleted = fileAttachService.deleteFile(fileSeq, sessionEmpId);
            
            if (deleted) {
                response.put("success", true);
                response.put("message", "파일이 성공적으로 삭제되었습니다.");
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("message", "파일을 찾을 수 없습니다.");
                return ResponseEntity.notFound().build();
            }
            
        } catch (Exception e) {
            log.error("파일 삭제 실패", e);
            
            response.put("success", false);
            response.put("message", "파일 삭제에 실패했습니다: " + e.getMessage());
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * 참조 테이블의 모든 첨부파일 삭제
     */
    @DeleteMapping("/ref")
    public ResponseEntity<Map<String, Object>> deleteFilesByRef(
            @RequestParam String refTblCd,
            @RequestParam String refPkVal,
            @RequestParam(value = "empId", defaultValue = "SYSTEM") String empId,
            HttpSession session) {
        
        Map<String, Object> response = new HashMap<>();
        
        // 세션에서 사용자 정보 확인
        com.ERI.demo.vo.employee.EmpLstVO empInfo = (com.ERI.demo.vo.employee.EmpLstVO) session.getAttribute("EMP_INFO");
        if (empInfo == null) {
            response.put("success", false);
            response.put("message", "로그인이 필요합니다.");
            return ResponseEntity.status(401).body(response);
        }
        
        String sessionEmpId = empInfo.getEmpId();
        
        try {
            boolean deleted = fileAttachService.deleteFilesByRef(refTblCd, refPkVal, sessionEmpId);
            
            response.put("success", true);
            response.put("message", "첨부파일들이 성공적으로 삭제되었습니다.");
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            log.error("첨부파일 삭제 실패", e);
            
            response.put("success", false);
            response.put("message", "첨부파일 삭제에 실패했습니다: " + e.getMessage());
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * 파일 첨부 테스트 페이지
     */
    @GetMapping("/test")
    public String fileAttachTestPage() {
        return "file-attach-test";
    }

    /**
     * 이미지 파일인지 확인
     */
    private boolean isImageFile(String extension) {
        String[] imageExtensions = {"jpg", "jpeg", "png", "gif", "bmp", "webp"};
        for (String ext : imageExtensions) {
            if (ext.equalsIgnoreCase(extension)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 파일 확장자에 따른 MediaType 반환 (미리보기용)
     */
    private MediaType getMediaType(String extension) {
        switch (extension.toLowerCase()) {
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
    
    /**
     * 파일 확장자에 따른 Content-Type 반환 (다운로드용)
     */
    private MediaType getContentType(String extension) {
        switch (extension.toLowerCase()) {
            // 문서 파일
            case "pdf":
                return MediaType.APPLICATION_PDF;
            case "doc":
                return MediaType.valueOf("application/msword");
            case "docx":
                return MediaType.valueOf("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
            case "xls":
                return MediaType.valueOf("application/vnd.ms-excel");
            case "xlsx":
                return MediaType.valueOf("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            case "ppt":
                return MediaType.valueOf("application/vnd.ms-powerpoint");
            case "pptx":
                return MediaType.valueOf("application/vnd.openxmlformats-officedocument.presentationml.presentation");
            case "txt":
                return MediaType.TEXT_PLAIN;
            
            // 이미지 파일
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
            
            // 압축 파일
            case "zip":
                return MediaType.valueOf("application/zip");
            case "rar":
                return MediaType.valueOf("application/x-rar-compressed");
            case "7z":
                return MediaType.valueOf("application/x-7z-compressed");
            
            // 기타
            default:
                return MediaType.APPLICATION_OCTET_STREAM;
        }
    }
} 