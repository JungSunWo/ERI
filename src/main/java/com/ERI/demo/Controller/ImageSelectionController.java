package com.ERI.demo.controller;

import com.ERI.demo.service.ImageSelectionService;
import com.ERI.demo.vo.ImageSelectionVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 이미지 선택 REST 컨트롤러
 */
@RestController
@RequestMapping("/api/image-selection")
@CrossOrigin(origins = "*")
public class ImageSelectionController {

    @Autowired
    private ImageSelectionService imageSelectionService;

    /**
     * 이미지 선택 목록 조회 (페이징/검색)
     * @param page 페이지 번호
     * @param size 페이지 크기
     * @param searchType 검색 타입
     * @param searchKeyword 검색 키워드
     * @param imgBrdSeq 이미지 게시판 시퀀스
     * @param imgFileSeq 이미지 파일 시퀀스
     * @param selEmpId 선택한 직원 ID
     * @param sortBy 정렬 필드
     * @param sortDirection 정렬 방향
     * @return 이미지 선택 목록 및 페이징 정보
     */
    @GetMapping("/list")
    public ResponseEntity<Map<String, Object>> getImageSelectionList(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String searchType,
            @RequestParam(required = false) String searchKeyword,
            @RequestParam(required = false) Long imgBrdSeq,
            @RequestParam(required = false) Long imgFileSeq,
            @RequestParam(required = false) String selEmpId,
            @RequestParam(defaultValue = "SEL_DT") String sortBy,
            @RequestParam(defaultValue = "DESC") String sortDirection) {

        ImageSelectionVO imageSelection = new ImageSelectionVO();
        imageSelection.setPage(page);
        imageSelection.setSize(size);
        imageSelection.setSearchType(searchType);
        imageSelection.setSearchKeyword(searchKeyword);
        imageSelection.setImgBrdSeq(imgBrdSeq);
        imageSelection.setImgFileSeq(imgFileSeq);
        imageSelection.setSelEmpId(selEmpId);
        imageSelection.setSortBy(sortBy);
        imageSelection.setSortDirection(sortDirection);

        List<ImageSelectionVO> list = imageSelectionService.getImageSelectionList(imageSelection);
        int total = imageSelectionService.getImageSelectionCount(imageSelection);

        Map<String, Object> response = new HashMap<>();
        response.put("list", list);
        response.put("total", total);
        response.put("page", page);
        response.put("size", size);
        response.put("totalPages", (int) Math.ceil((double) total / size));

        return ResponseEntity.ok(response);
    }

    /**
     * 이미지 선택 상세 조회
     * @param imgSelSeq 이미지 선택 시퀀스
     * @return 이미지 선택 정보
     */
    @GetMapping("/{imgSelSeq}")
    public ResponseEntity<ImageSelectionVO> getImageSelectionBySeq(@PathVariable Long imgSelSeq) {
        ImageSelectionVO imageSelection = imageSelectionService.getImageSelectionBySeq(imgSelSeq);
        if (imageSelection == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(imageSelection);
    }

    /**
     * 이미지 게시판별 선택 목록 조회
     * @param imgBrdSeq 이미지 게시판 시퀀스
     * @return 이미지 선택 목록
     */
    @GetMapping("/board/{imgBrdSeq}")
    public ResponseEntity<List<ImageSelectionVO>> getImageSelectionByBrdSeq(@PathVariable Long imgBrdSeq) {
        List<ImageSelectionVO> list = imageSelectionService.getImageSelectionByBrdSeq(imgBrdSeq);
        return ResponseEntity.ok(list);
    }

    /**
     * 직원별 선택 목록 조회
     * @param selEmpId 선택한 직원 ID
     * @return 이미지 선택 목록
     */
    @GetMapping("/employee/{selEmpId}")
    public ResponseEntity<List<ImageSelectionVO>> getImageSelectionByEmpId(@PathVariable String selEmpId) {
        List<ImageSelectionVO> list = imageSelectionService.getImageSelectionByEmpId(selEmpId);
        return ResponseEntity.ok(list);
    }

    /**
     * 이미지 파일별 선택 목록 조회
     * @param imgFileSeq 이미지 파일 시퀀스
     * @return 이미지 선택 목록
     */
    @GetMapping("/file/{imgFileSeq}")
    public ResponseEntity<List<ImageSelectionVO>> getImageSelectionByFileSeq(@PathVariable Long imgFileSeq) {
        List<ImageSelectionVO> list = imageSelectionService.getImageSelectionByFileSeq(imgFileSeq);
        return ResponseEntity.ok(list);
    }

    /**
     * 이미지 선택 등록
     * @param imageSelection 이미지 선택 정보
     * @return 등록된 이미지 선택 정보
     */
    @PostMapping
    public ResponseEntity<ImageSelectionVO> createImageSelection(@RequestBody ImageSelectionVO imageSelection) {
        ImageSelectionVO createdSelection = imageSelectionService.createImageSelection(imageSelection);
        return ResponseEntity.ok(createdSelection);
    }

    /**
     * 이미지 선택 삭제
     * @param imgSelSeq 이미지 선택 시퀀스
     * @return 삭제 결과
     */
    @DeleteMapping("/{imgSelSeq}")
    public ResponseEntity<Map<String, Object>> deleteImageSelection(@PathVariable Long imgSelSeq) {
        int deleted = imageSelectionService.deleteImageSelection(imgSelSeq);
        
        Map<String, Object> response = new HashMap<>();
        response.put("deleted", deleted > 0);
        response.put("imgSelSeq", imgSelSeq);

        return ResponseEntity.ok(response);
    }

    /**
     * 직원별 이미지 선택 삭제
     * @param imgBrdSeq 이미지 게시판 시퀀스
     * @param selEmpId 선택한 직원 ID
     * @return 삭제 결과
     */
    @DeleteMapping("/board/{imgBrdSeq}/employee/{selEmpId}")
    public ResponseEntity<Map<String, Object>> deleteImageSelectionByEmpId(
            @PathVariable Long imgBrdSeq,
            @PathVariable String selEmpId) {
        int deleted = imageSelectionService.deleteImageSelectionByEmpId(imgBrdSeq, selEmpId);
        
        Map<String, Object> response = new HashMap<>();
        response.put("deleted", deleted);
        response.put("imgBrdSeq", imgBrdSeq);
        response.put("selEmpId", selEmpId);

        return ResponseEntity.ok(response);
    }

    /**
     * 이미지 선택 여부 확인
     * @param imgBrdSeq 이미지 게시판 시퀀스
     * @param imgFileSeq 이미지 파일 시퀀스
     * @param selEmpId 선택한 직원 ID
     * @return 선택 여부
     */
    @GetMapping("/check")
    public ResponseEntity<Map<String, Object>> checkImageSelection(
            @RequestParam Long imgBrdSeq,
            @RequestParam Long imgFileSeq,
            @RequestParam String selEmpId) {
        boolean isSelected = imageSelectionService.isImageSelected(imgBrdSeq, imgFileSeq, selEmpId);
        
        Map<String, Object> response = new HashMap<>();
        response.put("isSelected", isSelected);
        response.put("imgBrdSeq", imgBrdSeq);
        response.put("imgFileSeq", imgFileSeq);
        response.put("selEmpId", selEmpId);

        return ResponseEntity.ok(response);
    }

    /**
     * 직원의 선택 개수 조회
     * @param imgBrdSeq 이미지 게시판 시퀀스
     * @param selEmpId 선택한 직원 ID
     * @return 선택 개수
     */
    @GetMapping("/count/employee")
    public ResponseEntity<Map<String, Object>> getImageSelectionCountByEmpId(
            @RequestParam Long imgBrdSeq,
            @RequestParam String selEmpId) {
        int count = imageSelectionService.getImageSelectionCountByEmpId(imgBrdSeq, selEmpId);
        
        Map<String, Object> response = new HashMap<>();
        response.put("count", count);
        response.put("imgBrdSeq", imgBrdSeq);
        response.put("selEmpId", selEmpId);

        return ResponseEntity.ok(response);
    }

    /**
     * 이미지 파일의 선택 개수 조회
     * @param imgFileSeq 이미지 파일 시퀀스
     * @return 선택 개수
     */
    @GetMapping("/count/file/{imgFileSeq}")
    public ResponseEntity<Map<String, Object>> getImageSelectionCountByFileSeq(@PathVariable Long imgFileSeq) {
        int count = imageSelectionService.getImageSelectionCountByFileSeq(imgFileSeq);
        
        Map<String, Object> response = new HashMap<>();
        response.put("count", count);
        response.put("imgFileSeq", imgFileSeq);

        return ResponseEntity.ok(response);
    }

    /**
     * 이미지 게시판의 전체 선택 개수 조회
     * @param imgBrdSeq 이미지 게시판 시퀀스
     * @return 전체 선택 개수
     */
    @GetMapping("/count/board/{imgBrdSeq}")
    public ResponseEntity<Map<String, Object>> getImageSelectionCountByBrdSeq(@PathVariable Long imgBrdSeq) {
        int count = imageSelectionService.getImageSelectionCountByBrdSeq(imgBrdSeq);
        
        Map<String, Object> response = new HashMap<>();
        response.put("count", count);
        response.put("imgBrdSeq", imgBrdSeq);

        return ResponseEntity.ok(response);
    }

    /**
     * 이미지 선택 토글 (선택/해제)
     * @param imgBrdSeq 이미지 게시판 시퀀스
     * @param imgFileSeq 이미지 파일 시퀀스
     * @param selEmpId 선택한 직원 ID
     * @return 토글 결과
     */
    @PostMapping("/toggle")
    public ResponseEntity<Map<String, Object>> toggleImageSelection(
            @RequestParam Long imgBrdSeq,
            @RequestParam Long imgFileSeq,
            @RequestParam String selEmpId) {
        boolean isSelected = imageSelectionService.toggleImageSelection(imgBrdSeq, imgFileSeq, selEmpId);
        
        Map<String, Object> response = new HashMap<>();
        response.put("isSelected", isSelected);
        response.put("imgBrdSeq", imgBrdSeq);
        response.put("imgFileSeq", imgFileSeq);
        response.put("selEmpId", selEmpId);

        return ResponseEntity.ok(response);
    }

    /**
     * 이미지 선택 가능 여부 확인 (최대 선택 개수 체크)
     * @param imgBrdSeq 이미지 게시판 시퀀스
     * @param selEmpId 선택한 직원 ID
     * @param maxSelCnt 최대 선택 개수
     * @return 선택 가능 여부
     */
    @GetMapping("/can-select")
    public ResponseEntity<Map<String, Object>> canSelectImage(
            @RequestParam Long imgBrdSeq,
            @RequestParam String selEmpId,
            @RequestParam int maxSelCnt) {
        boolean canSelect = imageSelectionService.canSelectImage(imgBrdSeq, selEmpId, maxSelCnt);
        
        Map<String, Object> response = new HashMap<>();
        response.put("canSelect", canSelect);
        response.put("imgBrdSeq", imgBrdSeq);
        response.put("selEmpId", selEmpId);
        response.put("maxSelCnt", maxSelCnt);

        return ResponseEntity.ok(response);
    }

    /**
     * 이미지 선택 통계 조회
     * @param imgBrdSeq 이미지 게시판 시퀀스
     * @return 선택 통계 정보
     */
    @GetMapping("/statistics/{imgBrdSeq}")
    public ResponseEntity<Map<String, Object>> getImageSelectionStatistics(@PathVariable Long imgBrdSeq) {
        int selectionCount = imageSelectionService.getImageSelectionStatistics(imgBrdSeq);
        
        Map<String, Object> response = new HashMap<>();
        response.put("imgBrdSeq", imgBrdSeq);
        response.put("selectionCount", selectionCount);

        return ResponseEntity.ok(response);
    }
} 