package com.ibkcloud.eoc.controller;

import com.ibkcloud.eoc.cmm.dto.PageRequest;
import com.ibkcloud.eoc.cmm.exception.BizException;
import com.ibkcloud.eoc.controller.vo.*;
import com.ibkcloud.eoc.service.ImageFileService;
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

/**
 * 이미지 파일 관리 컨트롤러
 */
@Slf4j
@RestController
@RequestMapping("/api/image-file")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@RequiredArgsConstructor
public class ImageFileController {

    private final ImageFileService imageFileService;

    @Value("${file.upload.path:/uploads/image}")
    private String uploadPath;

    /**
     * 이미지 파일 목록 조회 (페이징/검색)
     */
    @GetMapping("/list")
    public ResponseEntity<ImageFileOutVo> inqImageFileList(
            @Valid PageRequest pageRequest,
            @RequestParam(required = false) String searchType,
            @RequestParam(required = false) String searchKeyword,
            @RequestParam(required = false) Long imgBrdSeq,
            @RequestParam(defaultValue = "IMG_ORD") String sortBy,
            @RequestParam(defaultValue = "ASC") String sortDirection) {
        
        log.info("이미지 파일 목록 조회 시작 - searchType: {}, searchKeyword: {}, imgBrdSeq: {}", 
                searchType, searchKeyword, imgBrdSeq);
        
        try {
            ImageFileInVo searchCondition = new ImageFileInVo();
            searchCondition.setSearchType(searchType);
            searchCondition.setSearchKeyword(searchKeyword);
            searchCondition.setImgBrdSeq(imgBrdSeq);
            searchCondition.setSortBy(sortBy);
            searchCondition.setSortDirection(sortDirection);
            
            ImageFileOutVo result = imageFileService.inqImageFileList(pageRequest, searchCondition);
            
            result.setSuccess(true);
            result.setMessage("이미지 파일 목록을 조회했습니다.");
            result.setErrorCode("SUCCESS");
            
            log.info("이미지 파일 목록 조회 완료 - 총 {}건", result.getCount());
            return ResponseEntity.ok(result);
            
        } catch (Exception e) {
            log.error("이미지 파일 목록 조회 실패", e);
            throw new BizException("이미지 파일 목록 조회에 실패했습니다: " + e.getMessage());
        }
    }

    /**
     * 이미지 파일 상세 조회
     */
    @GetMapping("/{imgFileSeq}")
    public ResponseEntity<ImageFileOutVo> inqImageFileBySeq(@PathVariable Long imgFileSeq) {
        log.info("이미지 파일 상세 조회 시작 - imgFileSeq: {}", imgFileSeq);
        
        try {
            ImageFileOutVo result = imageFileService.inqImageFileBySeq(imgFileSeq);
            
            if (result == null) {
                throw new BizException("이미지 파일을 찾을 수 없습니다.");
            }
            
            result.setSuccess(true);
            result.setMessage("이미지 파일 정보를 조회했습니다.");
            result.setErrorCode("SUCCESS");
            
            log.info("이미지 파일 상세 조회 완료 - imgFileSeq: {}", imgFileSeq);
            return ResponseEntity.ok(result);
            
        } catch (Exception e) {
            log.error("이미지 파일 상세 조회 실패 - imgFileSeq: {}", imgFileSeq, e);
            throw new BizException("이미지 파일 조회에 실패했습니다: " + e.getMessage());
        }
    }

    /**
     * 이미지 게시판별 이미지 파일 목록 조회
     */
    @GetMapping("/board/{imgBrdSeq}")
    public ResponseEntity<List<ImageFileOutVo>> inqImageFileByBrdSeq(@PathVariable Long imgBrdSeq) {
        log.info("이미지 게시판별 이미지 파일 목록 조회 시작 - imgBrdSeq: {}", imgBrdSeq);
        
        try {
            List<ImageFileOutVo> result = imageFileService.inqImageFileByBrdSeq(imgBrdSeq);
            
            log.info("이미지 게시판별 이미지 파일 목록 조회 완료 - imgBrdSeq: {}, 총 {}건", imgBrdSeq, result.size());
            return ResponseEntity.ok(result);
            
        } catch (Exception e) {
            log.error("이미지 게시판별 이미지 파일 목록 조회 실패 - imgBrdSeq: {}", imgBrdSeq, e);
            throw new BizException("이미지 게시판별 이미지 파일 목록 조회에 실패했습니다: " + e.getMessage());
        }
    }

    /**
     * 이미지 파일 업로드
     */
    @PostMapping("/upload")
    public ResponseEntity<ImageFileOutVo> rgsnImageFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam("imgBrdSeq") Long imgBrdSeq,
            @RequestParam(required = false) String imgText,
            HttpServletRequest request) {
        
        log.info("이미지 파일 업로드 시작 - imgBrdSeq: {}, fileName: {}", imgBrdSeq, file.getOriginalFilename());
        
        try {
            // AuthInterceptor에서 전달받은 사용자 정보 사용
            String empId = (String) request.getAttribute("EMP_ID");
            
            if (empId == null) {
                throw new BizException("로그인이 필요합니다.");
            }
            
            ImageFileOutVo result = imageFileService.rgsnImageFile(file, imgBrdSeq, imgText, empId);
            
            result.setSuccess(true);
            result.setMessage("이미지 파일이 성공적으로 업로드되었습니다.");
            result.setErrorCode("SUCCESS");
            
            log.info("이미지 파일 업로드 완료 - imgFileSeq: {}", result.getImgFileSeq());
            return ResponseEntity.ok(result);
            
        } catch (Exception e) {
            log.error("이미지 파일 업로드 실패 - imgBrdSeq: {}", imgBrdSeq, e);
            throw new BizException("이미지 파일 업로드에 실패했습니다: " + e.getMessage());
        }
    }

    /**
     * 이미지 파일 다운로드
     */
    @GetMapping("/download/{imgFileSeq}")
    public ResponseEntity<Resource> inqImageFileDownload(@PathVariable Long imgFileSeq) {
        log.info("이미지 파일 다운로드 시작 - imgFileSeq: {}", imgFileSeq);
        
        try {
            ImageFileOutVo fileInfo = imageFileService.inqImageFileBySeq(imgFileSeq);
            
            if (fileInfo == null) {
                throw new BizException("이미지 파일을 찾을 수 없습니다.");
            }
            
            Path filePath = Paths.get(uploadPath, fileInfo.getFilePath());
            Resource resource = new UrlResource(filePath.toUri());
            
            if (!resource.exists()) {
                throw new BizException("이미지 파일이 존재하지 않습니다.");
            }
            
            String encodedFileName = URLEncoder.encode(fileInfo.getOriginalFileName(), StandardCharsets.UTF_8);
            
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", encodedFileName);
            
            log.info("이미지 파일 다운로드 완료 - imgFileSeq: {}, fileName: {}", imgFileSeq, fileInfo.getOriginalFileName());
            return ResponseEntity.ok()
                    .headers(headers)
                    .body(resource);
                    
        } catch (Exception e) {
            log.error("이미지 파일 다운로드 실패 - imgFileSeq: {}", imgFileSeq, e);
            throw new BizException("이미지 파일 다운로드에 실패했습니다: " + e.getMessage());
        }
    }

    /**
     * 이미지 파일 미리보기
     */
    @GetMapping("/preview/{imgFileSeq}")
    public ResponseEntity<Resource> inqImageFilePreview(@PathVariable Long imgFileSeq) {
        log.info("이미지 파일 미리보기 시작 - imgFileSeq: {}", imgFileSeq);
        
        try {
            ImageFileOutVo fileInfo = imageFileService.inqImageFileBySeq(imgFileSeq);
            
            if (fileInfo == null) {
                throw new BizException("이미지 파일을 찾을 수 없습니다.");
            }
            
            Path filePath = Paths.get(uploadPath, fileInfo.getFilePath());
            Resource resource = new UrlResource(filePath.toUri());
            
            if (!resource.exists()) {
                throw new BizException("이미지 파일이 존재하지 않습니다.");
            }
            
            MediaType mediaType = getMediaType(fileInfo.getFileExt());
            
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(mediaType);
            
            log.info("이미지 파일 미리보기 완료 - imgFileSeq: {}, fileName: {}", imgFileSeq, fileInfo.getOriginalFileName());
            return ResponseEntity.ok()
                    .headers(headers)
                    .body(resource);
                    
        } catch (Exception e) {
            log.error("이미지 파일 미리보기 실패 - imgFileSeq: {}", imgFileSeq, e);
            throw new BizException("이미지 파일 미리보기에 실패했습니다: " + e.getMessage());
        }
    }

    /**
     * 이미지 파일 수정
     */
    @PutMapping("/{imgFileSeq}")
    public ResponseEntity<ImageFileOutVo> mdfcImageFile(
            @PathVariable Long imgFileSeq,
            @Valid ImageFileInVo inVo,
            HttpServletRequest request) {
        
        log.info("이미지 파일 수정 시작 - imgFileSeq: {}", imgFileSeq);
        
        try {
            // AuthInterceptor에서 전달받은 사용자 정보 사용
            String empId = (String) request.getAttribute("EMP_ID");
            
            if (empId == null) {
                throw new BizException("로그인이 필요합니다.");
            }
            
            inVo.setImgFileSeq(imgFileSeq);
            inVo.setUpdEmpId(empId);
            
            ImageFileOutVo result = imageFileService.mdfcImageFile(inVo);
            
            if (result == null) {
                throw new BizException("이미지 파일을 찾을 수 없습니다.");
            }
            
            result.setSuccess(true);
            result.setMessage("이미지 파일이 성공적으로 수정되었습니다.");
            result.setErrorCode("SUCCESS");
            
            log.info("이미지 파일 수정 완료 - imgFileSeq: {}", imgFileSeq);
            return ResponseEntity.ok(result);
            
        } catch (Exception e) {
            log.error("이미지 파일 수정 실패 - imgFileSeq: {}", imgFileSeq, e);
            throw new BizException("이미지 파일 수정에 실패했습니다: " + e.getMessage());
        }
    }

    /**
     * 이미지 파일 삭제
     */
    @DeleteMapping("/{imgFileSeq}")
    public ResponseEntity<ImageFileOutVo> delImageFile(@PathVariable Long imgFileSeq) {
        log.info("이미지 파일 삭제 시작 - imgFileSeq: {}", imgFileSeq);
        
        try {
            boolean result = imageFileService.delImageFile(imgFileSeq);
            
            if (!result) {
                throw new BizException("이미지 파일 삭제에 실패했습니다.");
            }
            
            ImageFileOutVo outVo = new ImageFileOutVo();
            outVo.setSuccess(true);
            outVo.setMessage("이미지 파일이 성공적으로 삭제되었습니다.");
            outVo.setErrorCode("SUCCESS");
            
            log.info("이미지 파일 삭제 완료 - imgFileSeq: {}", imgFileSeq);
            return ResponseEntity.ok(outVo);
            
        } catch (Exception e) {
            log.error("이미지 파일 삭제 실패 - imgFileSeq: {}", imgFileSeq, e);
            throw new BizException("이미지 파일 삭제에 실패했습니다: " + e.getMessage());
        }
    }

    /**
     * 이미지 순서 수정
     */
    @PutMapping("/{imgFileSeq}/order")
    public ResponseEntity<ImageFileOutVo> mdfcImageOrder(
            @PathVariable Long imgFileSeq,
            @RequestParam Integer imgOrd) {
        
        log.info("이미지 순서 수정 시작 - imgFileSeq: {}, imgOrd: {}", imgFileSeq, imgOrd);
        
        try {
            ImageFileOutVo result = imageFileService.mdfcImageOrder(imgFileSeq, imgOrd);
            
            if (result == null) {
                throw new BizException("이미지 파일을 찾을 수 없습니다.");
            }
            
            result.setSuccess(true);
            result.setMessage("이미지 순서가 성공적으로 수정되었습니다.");
            result.setErrorCode("SUCCESS");
            
            log.info("이미지 순서 수정 완료 - imgFileSeq: {}, imgOrd: {}", imgFileSeq, imgOrd);
            return ResponseEntity.ok(result);
            
        } catch (Exception e) {
            log.error("이미지 순서 수정 실패 - imgFileSeq: {}, imgOrd: {}", imgFileSeq, imgOrd, e);
            throw new BizException("이미지 순서 수정에 실패했습니다: " + e.getMessage());
        }
    }

    /**
     * 이미지 텍스트 수정
     */
    @PutMapping("/{imgFileSeq}/text")
    public ResponseEntity<ImageFileOutVo> mdfcImageText(
            @PathVariable Long imgFileSeq,
            @RequestParam String imgText) {
        
        log.info("이미지 텍스트 수정 시작 - imgFileSeq: {}, imgText: {}", imgFileSeq, imgText);
        
        try {
            ImageFileOutVo result = imageFileService.mdfcImageText(imgFileSeq, imgText);
            
            if (result == null) {
                throw new BizException("이미지 파일을 찾을 수 없습니다.");
            }
            
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
     * 이미지 파일명 중복 확인
     */
    @GetMapping("/check-filename")
    public ResponseEntity<ImageFileOutVo> inqImageFileNameExists(
            @RequestParam String imgFileNm,
            @RequestParam Long imgBrdSeq) {
        
        log.info("이미지 파일명 중복 확인 시작 - imgFileNm: {}, imgBrdSeq: {}", imgFileNm, imgBrdSeq);
        
        try {
            boolean exists = imageFileService.isImageFileNameExists(imgFileNm, imgBrdSeq);
            
            ImageFileOutVo result = new ImageFileOutVo();
            result.setImgFileNm(imgFileNm);
            result.setImgBrdSeq(imgBrdSeq);
            result.setExists(exists);
            result.setSuccess(true);
            result.setMessage(exists ? "이미 존재하는 파일명입니다." : "사용 가능한 파일명입니다.");
            result.setErrorCode("SUCCESS");
            
            log.info("이미지 파일명 중복 확인 완료 - imgFileNm: {}, imgBrdSeq: {}, exists: {}", imgFileNm, imgBrdSeq, exists);
            return ResponseEntity.ok(result);
            
        } catch (Exception e) {
            log.error("이미지 파일명 중복 확인 실패 - imgFileNm: {}, imgBrdSeq: {}", imgFileNm, imgBrdSeq, e);
            throw new BizException("이미지 파일명 중복 확인에 실패했습니다: " + e.getMessage());
        }
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