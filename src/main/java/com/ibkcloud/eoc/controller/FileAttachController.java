package com.ibkcloud.eoc.controller;

import com.ibkcloud.eoc.cmm.exception.BizException;
import com.ibkcloud.eoc.controller.vo.*;
import com.ibkcloud.eoc.service.FileAttachService;
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

import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * 파일 첨부 관리 컨트롤러
 */
@Slf4j
@RestController
@RequestMapping("/api/file")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@RequiredArgsConstructor
public class FileAttachController {

    private final FileAttachService fileAttachService;

    @Value("${file.upload.path:/uploads}")
    private String uploadPath;

    /**
     * 첨부파일 목록 조회
     */
    @GetMapping("/list")
    public ResponseEntity<FileAttachOutVo> inqFileAttachList(
            @RequestParam String refTblCd,
            @RequestParam String refPkVal) {
        
        log.info("첨부파일 목록 조회 시작 - refTblCd: {}, refPkVal: {}", refTblCd, refPkVal);
        
        try {
            FileAttachOutVo result = fileAttachService.inqFileAttachList(refTblCd, refPkVal);
            
            result.setSuccess(true);
            result.setMessage("첨부파일 목록을 조회했습니다.");
            result.setErrorCode("SUCCESS");
            
            log.info("첨부파일 목록 조회 완료 - refTblCd: {}, refPkVal: {}, 총 {}건", refTblCd, refPkVal, result.getCount());
            return ResponseEntity.ok(result);
            
        } catch (Exception e) {
            log.error("첨부파일 목록 조회 실패 - refTblCd: {}, refPkVal: {}", refTblCd, refPkVal, e);
            throw new BizException("첨부파일 목록 조회에 실패했습니다: " + e.getMessage());
        }
    }

    /**
     * 파일 업로드 (단일/다중 모두 지원)
     */
    @PostMapping("/upload")
    public ResponseEntity<FileAttachOutVo> rgsnFileAttach(
            @RequestParam("files") MultipartFile[] files,
            @RequestParam("refTblCd") String refTblCd,
            @RequestParam("refPkVal") String refPkVal,
            @RequestParam(value = "fileDescs", required = false) String[] fileDescs,
            HttpServletRequest request) {
        
        log.info("파일 업로드 시작 - refTblCd: {}, refPkVal: {}, 파일 개수: {}", refTblCd, refPkVal, files.length);
        
        try {
            // AuthInterceptor에서 전달받은 사용자 정보 사용
            String empId = (String) request.getAttribute("EMP_ID");
            
            if (empId == null) {
                throw new BizException("로그인이 필요합니다.");
            }
            
            FileAttachOutVo result = fileAttachService.rgsnFileAttach(files, refTblCd, refPkVal, fileDescs, empId);
            
            result.setSuccess(true);
            result.setMessage(result.getCount() + "개의 파일이 성공적으로 업로드되었습니다.");
            result.setErrorCode("SUCCESS");
            
            log.info("파일 업로드 완료 - refTblCd: {}, refPkVal: {}, 업로드된 파일: {}개", refTblCd, refPkVal, result.getCount());
            return ResponseEntity.ok(result);
            
        } catch (Exception e) {
            log.error("파일 업로드 실패 - refTblCd: {}, refPkVal: {}", refTblCd, refPkVal, e);
            throw new BizException("파일 업로드에 실패했습니다: " + e.getMessage());
        }
    }

    /**
     * 다중 파일 업로드 (별도 엔드포인트)
     */
    @PostMapping("/upload-multiple")
    public ResponseEntity<FileAttachOutVo> rgsnMultipleFileAttach(
            @RequestParam("files") MultipartFile[] files,
            @RequestParam("refTblCd") String refTblCd,
            @RequestParam("refPkVal") String refPkVal,
            @RequestParam(value = "fileDescs", required = false) String[] fileDescs,
            HttpServletRequest request) {
        
        return rgsnFileAttach(files, refTblCd, refPkVal, fileDescs, request);
    }

    /**
     * 파일 다운로드
     */
    @GetMapping("/download/{fileSeq}")
    public ResponseEntity<Resource> inqFileAttachDownload(@PathVariable Long fileSeq) {
        log.info("파일 다운로드 시작 - fileSeq: {}", fileSeq);
        
        try {
            FileAttachOutVo fileInfo = fileAttachService.inqFileAttachBySeq(fileSeq);
            
            if (fileInfo == null) {
                throw new BizException("파일을 찾을 수 없습니다.");
            }
            
            Path filePath = Paths.get(uploadPath, fileInfo.getFilePath());
            Resource resource = new UrlResource(filePath.toUri());
            
            if (!resource.exists()) {
                throw new BizException("파일이 존재하지 않습니다.");
            }
            
            String encodedFileName = URLEncoder.encode(fileInfo.getOriginalFileName(), StandardCharsets.UTF_8);
            
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", encodedFileName);
            
            log.info("파일 다운로드 완료 - fileSeq: {}, fileName: {}", fileSeq, fileInfo.getOriginalFileName());
            return ResponseEntity.ok()
                    .headers(headers)
                    .body(resource);
                    
        } catch (Exception e) {
            log.error("파일 다운로드 실패 - fileSeq: {}", fileSeq, e);
            throw new BizException("파일 다운로드에 실패했습니다: " + e.getMessage());
        }
    }

    /**
     * 파일 미리보기
     */
    @GetMapping("/preview/{fileSeq}")
    public ResponseEntity<Resource> inqFileAttachPreview(@PathVariable Long fileSeq) {
        log.info("파일 미리보기 시작 - fileSeq: {}", fileSeq);
        
        try {
            FileAttachOutVo fileInfo = fileAttachService.inqFileAttachBySeq(fileSeq);
            
            if (fileInfo == null) {
                throw new BizException("파일을 찾을 수 없습니다.");
            }
            
            Path filePath = Paths.get(uploadPath, fileInfo.getFilePath());
            Resource resource = new UrlResource(filePath.toUri());
            
            if (!resource.exists()) {
                throw new BizException("파일이 존재하지 않습니다.");
            }
            
            // 이미지 파일인지 확인
            if (!isImageFile(fileInfo.getFileExt())) {
                throw new BizException("이미지 파일만 미리보기가 가능합니다.");
            }
            
            MediaType mediaType = getMediaType(fileInfo.getFileExt());
            
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(mediaType);
            
            log.info("파일 미리보기 완료 - fileSeq: {}, fileName: {}", fileSeq, fileInfo.getOriginalFileName());
            return ResponseEntity.ok()
                    .headers(headers)
                    .body(resource);
                    
        } catch (Exception e) {
            log.error("파일 미리보기 실패 - fileSeq: {}", fileSeq, e);
            throw new BizException("파일 미리보기에 실패했습니다: " + e.getMessage());
        }
    }

    /**
     * 파일 삭제
     */
    @DeleteMapping("/{fileSeq}")
    public ResponseEntity<FileAttachOutVo> delFileAttach(
            @PathVariable Long fileSeq,
            HttpServletRequest request) {
        
        log.info("파일 삭제 시작 - fileSeq: {}", fileSeq);
        
        try {
            // AuthInterceptor에서 전달받은 사용자 정보 사용
            String empId = (String) request.getAttribute("EMP_ID");
            
            if (empId == null) {
                throw new BizException("로그인이 필요합니다.");
            }
            
            boolean result = fileAttachService.delFileAttach(fileSeq, empId);
            
            if (!result) {
                throw new BizException("파일 삭제에 실패했습니다.");
            }
            
            FileAttachOutVo outVo = new FileAttachOutVo();
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
     * 참조별 파일 삭제
     */
    @DeleteMapping("/ref")
    public ResponseEntity<FileAttachOutVo> delFileAttachByRef(
            @RequestParam String refTblCd,
            @RequestParam String refPkVal,
            HttpServletRequest request) {
        
        log.info("참조별 파일 삭제 시작 - refTblCd: {}, refPkVal: {}", refTblCd, refPkVal);
        
        try {
            // AuthInterceptor에서 전달받은 사용자 정보 사용
            String empId = (String) request.getAttribute("EMP_ID");
            
            if (empId == null) {
                throw new BizException("로그인이 필요합니다.");
            }
            
            boolean result = fileAttachService.delFileAttachByRef(refTblCd, refPkVal, empId);
            
            if (!result) {
                throw new BizException("참조별 파일 삭제에 실패했습니다.");
            }
            
            FileAttachOutVo outVo = new FileAttachOutVo();
            outVo.setSuccess(true);
            outVo.setMessage("참조별 파일이 성공적으로 삭제되었습니다.");
            outVo.setErrorCode("SUCCESS");
            
            log.info("참조별 파일 삭제 완료 - refTblCd: {}, refPkVal: {}", refTblCd, refPkVal);
            return ResponseEntity.ok(outVo);
            
        } catch (Exception e) {
            log.error("참조별 파일 삭제 실패 - refTblCd: {}, refPkVal: {}", refTblCd, refPkVal, e);
            throw new BizException("참조별 파일 삭제에 실패했습니다: " + e.getMessage());
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
