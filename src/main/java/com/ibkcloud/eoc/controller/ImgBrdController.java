package com.ibkcloud.eoc.controller;

import com.ibkcloud.eoc.cmm.exception.BizException;
import com.ibkcloud.eoc.controller.vo.*;
import com.ibkcloud.eoc.service.ImgBrdService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletRequest;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * 이미지 게시판 컨트롤러
 */
@Slf4j
@RestController
@RequestMapping("/api/img-brd")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@RequiredArgsConstructor
public class ImgBrdController {
    
    private final ImgBrdService imgBrdService;
    
    /**
     * 이미지 파일 목록 조회
     */
    @GetMapping("/{imgBrdSeq}/images")
    public ResponseEntity<ImgBrdOutVo> inqImgFileList(@PathVariable Long imgBrdSeq,
                                                      @RequestParam(required = false) String selEmpId) {
        log.info("이미지 파일 목록 조회 시작 - imgBrdSeq: {}, selEmpId: {}", imgBrdSeq, selEmpId);
        
        try {
            ImgBrdOutVo result;
            if (selEmpId != null && !selEmpId.isEmpty()) {
                result = imgBrdService.inqImgFileListWithSelection(imgBrdSeq, selEmpId);
            } else {
                result = imgBrdService.inqImgFileListByBrdSeq(imgBrdSeq);
            }
            
            result.setSuccess(true);
            result.setMessage("이미지 파일 목록을 조회했습니다.");
            result.setErrorCode("SUCCESS");
            
            log.info("이미지 파일 목록 조회 완료 - imgBrdSeq: {}, 총 {}건", imgBrdSeq, result.getCount());
            return ResponseEntity.ok(result);
            
        } catch (Exception e) {
            log.error("이미지 파일 목록 조회 실패 - imgBrdSeq: {}", imgBrdSeq, e);
            throw new BizException("이미지 파일 목록 조회에 실패했습니다: " + e.getMessage());
        }
    }
    
    /**
     * 이미지 파일 업로드
     */
    @PostMapping("/{imgBrdSeq}/upload")
    public ResponseEntity<ImgBrdOutVo> rgsnUploadImages(@PathVariable Long imgBrdSeq,
                                                        @RequestParam("files") List<MultipartFile> files,
                                                        @RequestParam(value = "texts", required = false) List<String> texts,
                                                        HttpServletRequest request) {
        log.info("이미지 파일 업로드 시작 - imgBrdSeq: {}, files: {}개", imgBrdSeq, files.size());
        
        try {
            // AuthInterceptor에서 전달받은 사용자 정보 사용
            String empId = (String) request.getAttribute("EMP_ID");
            
            if (empId == null) {
                throw new BizException("인증 정보를 찾을 수 없습니다.");
            }
            
            // 파일 개수 검증 (최소 1개 이상)
            if (files.size() == 0) {
                throw new BizException("업로드할 이미지를 선택해주세요.");
            }
            
            // 빈 파일 검증
            for (MultipartFile file : files) {
                if (file.isEmpty()) {
                    throw new BizException("빈 파일이 포함되어 있습니다. 모든 이미지 파일을 확인해주세요.");
                }
            }
            
            ImgBrdOutVo result = imgBrdService.rgsnUploadImageFiles(imgBrdSeq, files, texts, empId);
            
            result.setSuccess(true);
            result.setMessage(files.size() + "개 이미지가 성공적으로 업로드되었습니다.");
            result.setErrorCode("SUCCESS");
            
            log.info("이미지 파일 업로드 완료 - imgBrdSeq: {}, files: {}개", imgBrdSeq, files.size());
            return ResponseEntity.ok(result);
            
        } catch (Exception e) {
            log.error("이미지 파일 업로드 실패 - imgBrdSeq: {}, files: {}개", imgBrdSeq, files.size(), e);
            throw new BizException("이미지 파일 업로드에 실패했습니다: " + e.getMessage());
        }
    }
    
    /**
     * 이미지 선택 토글
     */
    @PostMapping("/{imgBrdSeq}/toggle-selection")
    public ResponseEntity<ImgBrdOutVo> mdfcToggleImageSelection(@PathVariable Long imgBrdSeq,
                                                               @RequestParam Long imgFileSeq,
                                                               HttpServletRequest request) {
        log.info("이미지 선택 토글 시작 - imgBrdSeq: {}, imgFileSeq: {}", imgBrdSeq, imgFileSeq);
        
        try {
            // AuthInterceptor에서 전달받은 사용자 정보 사용
            String empId = (String) request.getAttribute("EMP_ID");
            
            if (empId == null) {
                throw new BizException("인증 정보를 찾을 수 없습니다.");
            }
            
            ImgBrdInVo imgBrdInVo = new ImgBrdInVo();
            imgBrdInVo.setImgBrdSeq(imgBrdSeq);
            imgBrdInVo.setImgFileSeq(imgFileSeq);
            imgBrdInVo.setEmpId(empId);
            
            ImgBrdOutVo result = imgBrdService.mdfcToggleImageSelection(imgBrdInVo);
            
            result.setSuccess(true);
            result.setMessage("이미지 선택이 토글되었습니다.");
            result.setErrorCode("SUCCESS");
            
            log.info("이미지 선택 토글 완료 - imgBrdSeq: {}, imgFileSeq: {}", imgBrdSeq, imgFileSeq);
            return ResponseEntity.ok(result);
            
        } catch (Exception e) {
            log.error("이미지 선택 토글 실패 - imgBrdSeq: {}, imgFileSeq: {}", imgBrdSeq, imgFileSeq, e);
            throw new BizException("이미지 선택 토글에 실패했습니다: " + e.getMessage());
        }
    }
    
    /**
     * 선택된 이미지 목록 조회
     */
    @GetMapping("/{imgBrdSeq}/selected")
    public ResponseEntity<ImgBrdOutVo> inqSelectedImages(@PathVariable Long imgBrdSeq,
                                                        HttpServletRequest request) {
        log.info("선택된 이미지 목록 조회 시작 - imgBrdSeq: {}", imgBrdSeq);
        
        try {
            // AuthInterceptor에서 전달받은 사용자 정보 사용
            String empId = (String) request.getAttribute("EMP_ID");
            
            if (empId == null) {
                throw new BizException("인증 정보를 찾을 수 없습니다.");
            }
            
            ImgBrdOutVo result = imgBrdService.inqSelectedImages(imgBrdSeq, empId);
            
            result.setSuccess(true);
            result.setMessage("선택된 이미지 목록을 조회했습니다.");
            result.setErrorCode("SUCCESS");
            
            log.info("선택된 이미지 목록 조회 완료 - imgBrdSeq: {}, 총 {}건", imgBrdSeq, result.getCount());
            return ResponseEntity.ok(result);
            
        } catch (Exception e) {
            log.error("선택된 이미지 목록 조회 실패 - imgBrdSeq: {}", imgBrdSeq, e);
            throw new BizException("선택된 이미지 목록 조회에 실패했습니다: " + e.getMessage());
        }
    }
    
    /**
     * 모든 선택 해제
     */
    @DeleteMapping("/{imgBrdSeq}/clear-selections")
    public ResponseEntity<ImgBrdOutVo> delClearAllSelections(@PathVariable Long imgBrdSeq,
                                                            HttpServletRequest request) {
        log.info("모든 선택 해제 시작 - imgBrdSeq: {}", imgBrdSeq);
        
        try {
            // AuthInterceptor에서 전달받은 사용자 정보 사용
            String empId = (String) request.getAttribute("EMP_ID");
            
            if (empId == null) {
                throw new BizException("인증 정보를 찾을 수 없습니다.");
            }
            
            ImgBrdOutVo result = imgBrdService.delClearAllSelections(imgBrdSeq, empId);
            
            result.setSuccess(true);
            result.setMessage("모든 선택이 해제되었습니다.");
            result.setErrorCode("SUCCESS");
            
            log.info("모든 선택 해제 완료 - imgBrdSeq: {}", imgBrdSeq);
            return ResponseEntity.ok(result);
            
        } catch (Exception e) {
            log.error("모든 선택 해제 실패 - imgBrdSeq: {}", imgBrdSeq, e);
            throw new BizException("모든 선택 해제에 실패했습니다: " + e.getMessage());
        }
    }
    
    /**
     * 이미지 선택 통계 조회
     */
    @GetMapping("/{imgBrdSeq}/stats")
    public ResponseEntity<ImgBrdOutVo> inqImageSelectionStats(@PathVariable Long imgBrdSeq) {
        log.info("이미지 선택 통계 조회 시작 - imgBrdSeq: {}", imgBrdSeq);
        
        try {
            ImgBrdOutVo result = imgBrdService.inqImageSelectionStats(imgBrdSeq);
            
            result.setSuccess(true);
            result.setMessage("이미지 선택 통계를 조회했습니다.");
            result.setErrorCode("SUCCESS");
            
            log.info("이미지 선택 통계 조회 완료 - imgBrdSeq: {}", imgBrdSeq);
            return ResponseEntity.ok(result);
            
        } catch (Exception e) {
            log.error("이미지 선택 통계 조회 실패 - imgBrdSeq: {}", imgBrdSeq, e);
            throw new BizException("이미지 선택 통계 조회에 실패했습니다: " + e.getMessage());
        }
    }
    
    /**
     * 이미지 파일 다운로드
     */
    @GetMapping("/files/{imgFileSeq}/download")
    public ResponseEntity<Resource> inqDownloadImageFile(@PathVariable Long imgFileSeq) {
        log.info("이미지 파일 다운로드 시작 - imgFileSeq: {}", imgFileSeq);
        
        try {
            ImgBrdOutVo fileInfo = imgBrdService.inqImageFileInfo(imgFileSeq);
            
            if (fileInfo == null) {
                throw new BizException("이미지 파일을 찾을 수 없습니다.");
            }
            
            String filePath = fileInfo.getFilePath();
            File file = new File(filePath);
            
            if (!file.exists()) {
                throw new BizException("파일이 존재하지 않습니다.");
            }
            
            Resource resource = new FileSystemResource(file);
            
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", fileInfo.getFileName());
            
            log.info("이미지 파일 다운로드 완료 - imgFileSeq: {}, fileName: {}", imgFileSeq, fileInfo.getFileName());
            return ResponseEntity.ok()
                    .headers(headers)
                    .body(resource);
            
        } catch (Exception e) {
            log.error("이미지 파일 다운로드 실패 - imgFileSeq: {}", imgFileSeq, e);
            throw new BizException("이미지 파일 다운로드에 실패했습니다: " + e.getMessage());
        }
    }
    
    /**
     * 이미지 텍스트 수정
     */
    @PutMapping("/files/{imgFileSeq}/text")
    public ResponseEntity<ImgBrdOutVo> mdfcImageText(@PathVariable Long imgFileSeq,
                                                     @RequestBody ImgBrdInVo imgBrdInVo,
                                                     HttpServletRequest request) {
        log.info("이미지 텍스트 수정 시작 - imgFileSeq: {}", imgFileSeq);
        
        try {
            // AuthInterceptor에서 전달받은 사용자 정보 사용
            String empId = (String) request.getAttribute("EMP_ID");
            
            if (empId == null) {
                throw new BizException("인증 정보를 찾을 수 없습니다.");
            }
            
            imgBrdInVo.setImgFileSeq(imgFileSeq);
            imgBrdInVo.setEmpId(empId);
            
            ImgBrdOutVo result = imgBrdService.mdfcImageText(imgBrdInVo);
            
            result.setSuccess(true);
            result.setMessage("이미지 텍스트가 성공적으로 수정되었습니다.");
            result.setErrorCode("SUCCESS");
            
            log.info("이미지 텍스트 수정 완료 - imgFileSeq: {}", imgFileSeq);
            return ResponseEntity.ok(result);
            
        } catch (Exception e) {
            log.error("이미지 텍스트 수정 실패 - imgFileSeq: {}", imgFileSeq, e);
            throw new BizException("이미지 텍스트 수정에 실패했습니다: " + e.getMessage());
        }
    }
    
    /**
     * 이미지 파일 삭제
     */
    @DeleteMapping("/files/{imgFileSeq}")
    public ResponseEntity<ImgBrdOutVo> delImageFile(@PathVariable Long imgFileSeq,
                                                   HttpServletRequest request) {
        log.info("이미지 파일 삭제 시작 - imgFileSeq: {}", imgFileSeq);
        
        try {
            // AuthInterceptor에서 전달받은 사용자 정보 사용
            String empId = (String) request.getAttribute("EMP_ID");
            
            if (empId == null) {
                throw new BizException("인증 정보를 찾을 수 없습니다.");
            }
            
            ImgBrdOutVo result = imgBrdService.delImageFile(imgFileSeq, empId);
            
            result.setSuccess(true);
            result.setMessage("이미지 파일이 성공적으로 삭제되었습니다.");
            result.setErrorCode("SUCCESS");
            
            log.info("이미지 파일 삭제 완료 - imgFileSeq: {}", imgFileSeq);
            return ResponseEntity.ok(result);
            
        } catch (Exception e) {
            log.error("이미지 파일 삭제 실패 - imgFileSeq: {}", imgFileSeq, e);
            throw new BizException("이미지 파일 삭제에 실패했습니다: " + e.getMessage());
        }
    }
} 