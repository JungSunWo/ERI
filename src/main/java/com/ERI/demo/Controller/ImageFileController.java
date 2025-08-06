package com.ERI.demo.controller;

import com.ERI.demo.service.ImageFileService;
import com.ERI.demo.vo.ImageFileVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 이미지 파일 REST 컨트롤러
 */
@RestController
@RequestMapping("/api/image-file")
@CrossOrigin(origins = "*")
public class ImageFileController {

    @Autowired
    private ImageFileService imageFileService;

    @Value("${file.upload.path:/uploads/image}")
    private String uploadPath;

    /**
     * 이미지 파일 목록 조회 (페이징/검색)
     * @param page 페이지 번호
     * @param size 페이지 크기
     * @param searchType 검색 타입
     * @param searchKeyword 검색 키워드
     * @param imgBrdSeq 이미지 게시판 시퀀스
     * @param sortBy 정렬 필드
     * @param sortDirection 정렬 방향
     * @return 이미지 파일 목록 및 페이징 정보
     */
    @GetMapping("/list")
    public ResponseEntity<Map<String, Object>> getImageFileList(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String searchType,
            @RequestParam(required = false) String searchKeyword,
            @RequestParam(required = false) Long imgBrdSeq,
            @RequestParam(defaultValue = "IMG_ORD") String sortBy,
            @RequestParam(defaultValue = "ASC") String sortDirection) {

        ImageFileVO imageFile = new ImageFileVO();
        imageFile.setPage(page);
        imageFile.setSize(size);
        imageFile.setSearchType(searchType);
        imageFile.setSearchKeyword(searchKeyword);
        imageFile.setImgBrdSeq(imgBrdSeq);
        imageFile.setSortBy(sortBy);
        imageFile.setSortDirection(sortDirection);

        List<ImageFileVO> list = imageFileService.getImageFileList(imageFile);
        int total = imageFileService.getImageFileCount(imageFile);

        Map<String, Object> response = new HashMap<>();
        response.put("list", list);
        response.put("total", total);
        response.put("page", page);
        response.put("size", size);
        response.put("totalPages", (int) Math.ceil((double) total / size));

        return ResponseEntity.ok(response);
    }

    /**
     * 이미지 파일 상세 조회
     * @param imgFileSeq 이미지 파일 시퀀스
     * @return 이미지 파일 정보
     */
    @GetMapping("/{imgFileSeq}")
    public ResponseEntity<ImageFileVO> getImageFileBySeq(@PathVariable Long imgFileSeq) {
        ImageFileVO imageFile = imageFileService.getImageFileBySeq(imgFileSeq);
        if (imageFile == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(imageFile);
    }

    /**
     * 이미지 게시판별 이미지 파일 목록 조회
     * @param imgBrdSeq 이미지 게시판 시퀀스
     * @return 이미지 파일 목록
     */
    @GetMapping("/board/{imgBrdSeq}")
    public ResponseEntity<List<ImageFileVO>> getImageFileByBrdSeq(@PathVariable Long imgBrdSeq) {
        List<ImageFileVO> list = imageFileService.getImageFileByBrdSeq(imgBrdSeq);
        return ResponseEntity.ok(list);
    }

    /**
     * 이미지 파일 업로드
     * @param file 업로드된 파일
     * @param imgBrdSeq 이미지 게시판 시퀀스
     * @param imgText 이미지 텍스트
     * @param regEmpId 등록자 ID
     * @return 업로드된 이미지 파일 정보
     */
    @PostMapping("/upload")
    public ResponseEntity<ImageFileVO> uploadImageFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam("imgBrdSeq") Long imgBrdSeq,
            @RequestParam(required = false) String imgText,
            @RequestParam("regEmpId") String regEmpId) {

        try {
            ImageFileVO uploadedFile = imageFileService.uploadImageFile(file, imgBrdSeq, imgText, regEmpId, uploadPath);
            return ResponseEntity.ok(uploadedFile);
        } catch (IOException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * 이미지 파일 다운로드
     * @param imgFileSeq 이미지 파일 시퀀스
     * @return 이미지 파일 리소스
     */
    @GetMapping("/download/{imgFileSeq}")
    public ResponseEntity<Resource> downloadImageFile(@PathVariable Long imgFileSeq) {
        ImageFileVO imageFile = imageFileService.getImageFileBySeq(imgFileSeq);
        if (imageFile == null) {
            return ResponseEntity.notFound().build();
        }

        try {
            Path filePath = Paths.get(imageFile.getImgFilePath(), imageFile.getImgFileNm());
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists() && resource.isReadable()) {
                return ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_OCTET_STREAM)
                        .header(HttpHeaders.CONTENT_DISPOSITION, 
                                "attachment; filename=\"" + imageFile.getImgFileNm() + "\"")
                        .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (MalformedURLException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * 이미지 파일 미리보기
     * @param imgFileSeq 이미지 파일 시퀀스
     * @return 이미지 리소스
     */
    @GetMapping("/preview/{imgFileSeq}")
    public ResponseEntity<Resource> previewImageFile(@PathVariable Long imgFileSeq) {
        ImageFileVO imageFile = imageFileService.getImageFileBySeq(imgFileSeq);
        if (imageFile == null) {
            return ResponseEntity.notFound().build();
        }

        // 이미지 파일인지 확인
        if (!imageFile.isImageFile()) {
            return ResponseEntity.badRequest().build();
        }

        try {
            Path filePath = Paths.get(imageFile.getImgFilePath(), imageFile.getImgFileNm());
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists() && resource.isReadable()) {
                return ResponseEntity.ok()
                        .contentType(MediaType.parseMediaType("image/" + imageFile.getImgFileExt()))
                        .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (MalformedURLException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * 이미지 파일 수정
     * @param imgFileSeq 이미지 파일 시퀀스
     * @param imageFile 이미지 파일 정보
     * @return 수정된 이미지 파일 정보
     */
    @PutMapping("/{imgFileSeq}")
    public ResponseEntity<ImageFileVO> updateImageFile(
            @PathVariable Long imgFileSeq,
            @RequestBody ImageFileVO imageFile) {

        imageFile.setImgFileSeq(imgFileSeq);
        ImageFileVO updatedFile = imageFileService.updateImageFile(imageFile);
        return ResponseEntity.ok(updatedFile);
    }

    /**
     * 이미지 파일 삭제
     * @param imgFileSeq 이미지 파일 시퀀스
     * @return 삭제 결과
     */
    @DeleteMapping("/{imgFileSeq}")
    public ResponseEntity<Map<String, Object>> deleteImageFile(@PathVariable Long imgFileSeq) {
        ImageFileVO imageFile = imageFileService.getImageFileBySeq(imgFileSeq);
        if (imageFile == null) {
            return ResponseEntity.notFound().build();
        }

        // 물리적 파일 삭제
        boolean physicalDeleted = imageFileService.deletePhysicalFile(
                imageFile.getImgFilePath(), 
                imageFile.getImgFileNm()
        );

        // 논리적 삭제
        int deleted = imageFileService.deleteImageFile(imgFileSeq);

        Map<String, Object> response = new HashMap<>();
        response.put("deleted", deleted > 0);
        response.put("physicalDeleted", physicalDeleted);

        return ResponseEntity.ok(response);
    }

    /**
     * 이미지 순서 업데이트
     * @param imgFileSeq 이미지 파일 시퀀스
     * @param imgOrd 이미지 순서
     * @return 업데이트 결과
     */
    @PutMapping("/{imgFileSeq}/order")
    public ResponseEntity<Map<String, Object>> updateImageOrder(
            @PathVariable Long imgFileSeq,
            @RequestParam Integer imgOrd) {

        int updated = imageFileService.updateImageOrder(imgFileSeq, imgOrd);
        
        Map<String, Object> response = new HashMap<>();
        response.put("updated", updated > 0);
        response.put("imgFileSeq", imgFileSeq);
        response.put("imgOrd", imgOrd);

        return ResponseEntity.ok(response);
    }

    /**
     * 이미지 텍스트 업데이트
     * @param imgFileSeq 이미지 파일 시퀀스
     * @param imgText 이미지 텍스트
     * @return 업데이트 결과
     */
    @PutMapping("/{imgFileSeq}/text")
    public ResponseEntity<Map<String, Object>> updateImageText(
            @PathVariable Long imgFileSeq,
            @RequestParam String imgText) {

        int updated = imageFileService.updateImageText(imgFileSeq, imgText);
        
        Map<String, Object> response = new HashMap<>();
        response.put("updated", updated > 0);
        response.put("imgFileSeq", imgFileSeq);
        response.put("imgText", imgText);

        return ResponseEntity.ok(response);
    }

    /**
     * 이미지 파일명 중복 확인
     * @param imgFileNm 이미지 파일명
     * @param imgBrdSeq 이미지 게시판 시퀀스
     * @return 중복 여부
     */
    @GetMapping("/check-filename")
    public ResponseEntity<Map<String, Object>> checkImageFileName(
            @RequestParam String imgFileNm,
            @RequestParam Long imgBrdSeq) {
        boolean isDuplicate = imageFileService.isImageFileNameDuplicate(imgFileNm, imgBrdSeq);
        
        Map<String, Object> response = new HashMap<>();
        response.put("isDuplicate", isDuplicate);
        response.put("imgFileNm", imgFileNm);
        response.put("imgBrdSeq", imgBrdSeq);

        return ResponseEntity.ok(response);
    }
} 