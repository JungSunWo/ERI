package com.ERI.demo.Controller;

import com.ERI.demo.service.ImgBrdService;
import com.ERI.demo.vo.ImgFileLstVO;
import com.ERI.demo.vo.ImgSelLstVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 이미지 게시판 Controller
 */
@RestController
@RequestMapping("/api/img-brd")
public class ImgBrdController {
    
    @Autowired
    private ImgBrdService imgBrdService;
    

    
    /**
     * 이미지 파일 목록 조회
     */
    @GetMapping("/{imgBrdSeq}/images")
    public ResponseEntity<Map<String, Object>> getImgFileList(@PathVariable Long imgBrdSeq,
                                                             @RequestParam(required = false) String selEmpId) {
        Map<String, Object> response = new HashMap<>();
        try {
            List<ImgFileLstVO> imgFileList;
            if (selEmpId != null && !selEmpId.isEmpty()) {
                imgFileList = imgBrdService.getImgFileListWithSelection(imgBrdSeq, selEmpId);
            } else {
                imgFileList = imgBrdService.getImgFileListByBrdSeq(imgBrdSeq);
            }
            
            response.put("success", true);
            response.put("data", imgFileList);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "이미지 파일 목록 조회 중 오류가 발생했습니다.");
            return ResponseEntity.badRequest().body(response);
        }
    }
    
    /**
     * 이미지 파일 업로드
     */
    @PostMapping("/{imgBrdSeq}/upload")
    public ResponseEntity<Map<String, Object>> uploadImages(@PathVariable Long imgBrdSeq,
                                                          @RequestParam("files") List<MultipartFile> files,
                                                          @RequestParam(value = "texts", required = false) List<String> texts,
                                                          HttpServletRequest request) {
        Map<String, Object> response = new HashMap<>();
        try {
            // AuthInterceptor에서 전달받은 사용자 정보 사용
            String eriEmpId = (String) request.getAttribute("EMP_ID");
            
            if (eriEmpId == null) {
                response.put("success", false);
                response.put("message", "인증 정보를 찾을 수 없습니다.");
                return ResponseEntity.badRequest().body(response);
            }
            
            // 파일 개수 검증 (최소 1개 이상)
            if (files.size() == 0) {
                response.put("success", false);
                response.put("message", "업로드할 이미지를 선택해주세요.");
                return ResponseEntity.badRequest().body(response);
            }
            
            // 빈 파일 검증
            for (MultipartFile file : files) {
                if (file.isEmpty()) {
                    response.put("success", false);
                    response.put("message", "빈 파일이 포함되어 있습니다. 모든 이미지 파일을 확인해주세요.");
                    return ResponseEntity.badRequest().body(response);
                }
            }
            
            boolean result = imgBrdService.uploadImageFiles(imgBrdSeq, files, texts, eriEmpId);
            if (result) {
                response.put("success", true);
                response.put("message", files.size() + "개 이미지가 성공적으로 업로드되었습니다.");
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("message", "이미지 업로드에 실패했습니다.");
                return ResponseEntity.badRequest().body(response);
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "이미지 업로드 중 오류가 발생했습니다.");
            return ResponseEntity.badRequest().body(response);
        }
    }
    
    /**
     * 이미지 선택/해제
     */
    @PostMapping("/{imgBrdSeq}/toggle-selection")
    public ResponseEntity<Map<String, Object>> toggleImageSelection(@PathVariable Long imgBrdSeq,
                                                                  @RequestParam Long imgFileSeq,
                                                                  HttpServletRequest request) {
        Map<String, Object> response = new HashMap<>();
        try {
            // AuthInterceptor에서 전달받은 사용자 정보 사용
            String selEmpId = (String) request.getAttribute("EMP_ID");
            
            if (selEmpId == null) {
                response.put("success", false);
                response.put("message", "인증 정보를 찾을 수 없습니다.");
                return ResponseEntity.badRequest().body(response);
            }
            
            boolean result = imgBrdService.toggleImageSelection(imgBrdSeq, imgFileSeq, selEmpId);
            if (result) {
                response.put("success", true);
                response.put("message", "이미지 선택이 변경되었습니다.");
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("message", "이미지 선택 변경에 실패했습니다.");
                return ResponseEntity.badRequest().body(response);
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "이미지 선택 변경 중 오류가 발생했습니다.");
            return ResponseEntity.badRequest().body(response);
        }
    }
    
    /**
     * 사용자가 선택한 이미지 목록 조회
     */
    @GetMapping("/{imgBrdSeq}/selected")
    public ResponseEntity<Map<String, Object>> getSelectedImages(@PathVariable Long imgBrdSeq,
                                                               HttpServletRequest request) {
        Map<String, Object> response = new HashMap<>();
        try {
            // AuthInterceptor에서 전달받은 사용자 정보 사용
            String selEmpId = (String) request.getAttribute("EMP_ID");
            
            if (selEmpId == null) {
                response.put("success", false);
                response.put("message", "인증 정보를 찾을 수 없습니다.");
                return ResponseEntity.badRequest().body(response);
            }
            
            List<ImgSelLstVO> selectedImages = imgBrdService.getSelectedImageList(imgBrdSeq, selEmpId);
            response.put("success", true);
            response.put("data", selectedImages);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "선택한 이미지 목록 조회 중 오류가 발생했습니다.");
            return ResponseEntity.badRequest().body(response);
        }
    }
    
    /**
     * 모든 선택 해제
     */
    @DeleteMapping("/{imgBrdSeq}/clear-selections")
    public ResponseEntity<Map<String, Object>> clearAllSelections(@PathVariable Long imgBrdSeq,
                                                                HttpServletRequest request) {
        Map<String, Object> response = new HashMap<>();
        try {
            // AuthInterceptor에서 전달받은 사용자 정보 사용
            String selEmpId = (String) request.getAttribute("EMP_ID");
            
            if (selEmpId == null) {
                response.put("success", false);
                response.put("message", "인증 정보를 찾을 수 없습니다.");
                return ResponseEntity.badRequest().body(response);
            }
            
            boolean result = imgBrdService.clearAllSelections(imgBrdSeq, selEmpId);
            if (result) {
                response.put("success", true);
                response.put("message", "모든 선택이 해제되었습니다.");
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("message", "선택 해제에 실패했습니다.");
                return ResponseEntity.badRequest().body(response);
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "선택 해제 중 오류가 발생했습니다.");
            return ResponseEntity.badRequest().body(response);
        }
    }
    
    /**
     * 이미지 선택 통계 조회
     */
    @GetMapping("/{imgBrdSeq}/stats")
    public ResponseEntity<Map<String, Object>> getImageSelectionStats(@PathVariable Long imgBrdSeq) {
        Map<String, Object> response = new HashMap<>();
        try {
            List<ImgSelLstVO> stats = imgBrdService.getImageSelectionStats(imgBrdSeq);
            response.put("success", true);
            response.put("data", stats);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "이미지 선택 통계 조회 중 오류가 발생했습니다.");
            return ResponseEntity.badRequest().body(response);
        }
    }
    
    /**
     * 이미지 파일 다운로드
     */
    @GetMapping("/files/{imgFileSeq}/download")
    public ResponseEntity<Resource> downloadImageFile(@PathVariable Long imgFileSeq) {
        try {
            ImgFileLstVO imgFile = imgBrdService.getImgFileBySeq(imgFileSeq);
            if (imgFile == null) {
                return ResponseEntity.notFound().build();
            }
            
            // 파일 경로 구성
            String uploadPath = "uploads/tb_img_file_lst";
            Path filePath = Paths.get(uploadPath, imgFile.getImgFilePath());
            File file = filePath.toFile();
            
            if (!file.exists()) {
                return ResponseEntity.notFound().build();
            }
            
            Resource resource = new FileSystemResource(file);
            
            // Content-Type 설정
            String contentType = "image/jpeg"; // 기본값
            String filename = imgFile.getImgFilePath().toLowerCase();
            if (filename.endsWith(".png")) {
                contentType = "image/png";
            } else if (filename.endsWith(".gif")) {
                contentType = "image/gif";
            } else if (filename.endsWith(".webp")) {
                contentType = "image/webp";
            }
            
            return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + imgFile.getImgFileNm() + "\"")
                .body(resource);
                
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
    
    /**
     * 이미지 설명 수정
     */
    @PutMapping("/files/{imgFileSeq}/text")
    public ResponseEntity<Map<String, Object>> updateImageText(@PathVariable Long imgFileSeq,
                                                             @RequestBody Map<String, String> request,
                                                             HttpServletRequest httpRequest) {
        Map<String, Object> response = new HashMap<>();
        try {
            // AuthInterceptor에서 전달받은 사용자 정보 사용
            String eriEmpId = (String) httpRequest.getAttribute("EMP_ID");
            
            if (eriEmpId == null) {
                response.put("success", false);
                response.put("message", "인증 정보를 찾을 수 없습니다.");
                return ResponseEntity.badRequest().body(response);
            }
            
            String imgText = request.get("imgText");
            boolean result = imgBrdService.updateImageText(imgFileSeq, imgText, eriEmpId);
            
            if (result) {
                response.put("success", true);
                response.put("message", "이미지 설명이 수정되었습니다.");
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("message", "이미지 설명 수정에 실패했습니다.");
                return ResponseEntity.badRequest().body(response);
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "이미지 설명 수정 중 오류가 발생했습니다.");
            return ResponseEntity.badRequest().body(response);
        }
    }
    
    /**
     * 이미지 파일 삭제
     */
    @DeleteMapping("/files/{imgFileSeq}")
    public ResponseEntity<Map<String, Object>> deleteImageFile(@PathVariable Long imgFileSeq,
                                                             HttpServletRequest request) {
        Map<String, Object> response = new HashMap<>();
        try {
            // AuthInterceptor에서 전달받은 사용자 정보 사용
            String eriEmpId = (String) request.getAttribute("EMP_ID");
            
            // 디버깅용 로깅
            System.out.println("=== 이미지 파일 삭제 요청 ===");
            System.out.println("imgFileSeq: " + imgFileSeq);
            System.out.println("eriEmpId: " + eriEmpId);
            
            if (eriEmpId == null) {
                System.out.println("ERROR: eriEmpId is null - AuthInterceptor에서 전달받지 못함");
                response.put("success", false);
                response.put("message", "인증 정보를 찾을 수 없습니다.");
                return ResponseEntity.badRequest().body(response);
            }
            
            System.out.println("이미지 삭제 시도: imgFileSeq=" + imgFileSeq + ", eriEmpId=" + eriEmpId);
            boolean result = imgBrdService.deleteImgFile(imgFileSeq, eriEmpId);
            
            if (result) {
                System.out.println("이미지 삭제 성공");
                response.put("success", true);
                response.put("message", "이미지가 삭제되었습니다.");
                return ResponseEntity.ok(response);
            } else {
                System.out.println("이미지 삭제 실패 - 서비스 레이어에서 false 반환");
                response.put("success", false);
                response.put("message", "이미지 삭제에 실패했습니다.");
                return ResponseEntity.badRequest().body(response);
            }
        } catch (Exception e) {
            System.out.println("이미지 삭제 중 예외 발생: " + e.getMessage());
            e.printStackTrace();
            response.put("success", false);
            response.put("message", "이미지 삭제 중 오류가 발생했습니다.");
            return ResponseEntity.badRequest().body(response);
        }
    }
} 