package com.ERI.demo.controller;

import com.ERI.demo.service.ImageBoardService;
import com.ERI.demo.vo.ImageBoardVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 이미지 게시판 REST 컨트롤러
 */
@RestController
@RequestMapping("/api/image-board")
@CrossOrigin(origins = "*")
public class ImageBoardController {

    @Autowired
    private ImageBoardService imageBoardService;

    /**
     * 이미지 게시판 목록 조회 (페이징/검색)
     * @param page 페이지 번호
     * @param size 페이지 크기
     * @param searchType 검색 타입
     * @param searchKeyword 검색 키워드
     * @param sortBy 정렬 필드
     * @param sortDirection 정렬 방향
     * @return 이미지 게시판 목록 및 페이징 정보
     */
    @GetMapping("/list")
    public ResponseEntity<Map<String, Object>> getImageBoardList(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String searchType,
            @RequestParam(required = false) String searchKeyword,
            @RequestParam(defaultValue = "IMG_BRD_SEQ") String sortBy,
            @RequestParam(defaultValue = "DESC") String sortDirection) {

        ImageBoardVO imageBoard = new ImageBoardVO();
        imageBoard.setPage(page);
        imageBoard.setSize(size);
        imageBoard.setSearchType(searchType);
        imageBoard.setSearchKeyword(searchKeyword);
        imageBoard.setSortBy(sortBy);
        imageBoard.setSortDirection(sortDirection);

        List<ImageBoardVO> list = imageBoardService.getImageBoardList(imageBoard);
        int total = imageBoardService.getImageBoardCount(imageBoard);

        Map<String, Object> response = new HashMap<>();
        response.put("list", list);
        response.put("total", total);
        response.put("page", page);
        response.put("size", size);
        response.put("totalPages", (int) Math.ceil((double) total / size));

        return ResponseEntity.ok(response);
    }

    /**
     * 이미지 게시판 상세 조회
     * @param imgBrdSeq 이미지 게시판 시퀀스
     * @return 이미지 게시판 정보
     */
    @GetMapping("/{imgBrdSeq}")
    public ResponseEntity<ImageBoardVO> getImageBoardBySeq(@PathVariable Long imgBrdSeq) {
        ImageBoardVO imageBoard = imageBoardService.getImageBoardBySeq(imgBrdSeq);
        if (imageBoard == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(imageBoard);
    }

    /**
     * 이미지 게시판과 이미지 파일들을 함께 조회
     * @param imgBrdSeq 이미지 게시판 시퀀스
     * @return 이미지 게시판 정보 (이미지 파일 목록 포함)
     */
    @GetMapping("/{imgBrdSeq}/with-files")
    public ResponseEntity<ImageBoardVO> getImageBoardWithFiles(@PathVariable Long imgBrdSeq) {
        ImageBoardVO imageBoard = imageBoardService.getImageBoardWithFiles(imgBrdSeq);
        if (imageBoard == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(imageBoard);
    }

    /**
     * 이미지 게시판 등록
     * @param imageBoard 이미지 게시판 정보
     * @return 등록된 이미지 게시판 정보
     */
    @PostMapping
    public ResponseEntity<ImageBoardVO> createImageBoard(@RequestBody ImageBoardVO imageBoard) {
        // 제목 중복 확인
        if (imageBoardService.isImageBoardTitleDuplicate(imageBoard.getImgBrdTitl())) {
            return ResponseEntity.badRequest().build();
        }
        
        ImageBoardVO createdBoard = imageBoardService.createImageBoard(imageBoard);
        return ResponseEntity.ok(createdBoard);
    }

    /**
     * 이미지 게시판 수정
     * @param imgBrdSeq 이미지 게시판 시퀀스
     * @param imageBoard 이미지 게시판 정보
     * @return 수정된 이미지 게시판 정보
     */
    @PutMapping("/{imgBrdSeq}")
    public ResponseEntity<ImageBoardVO> updateImageBoard(
            @PathVariable Long imgBrdSeq,
            @RequestBody ImageBoardVO imageBoard) {

        imageBoard.setImgBrdSeq(imgBrdSeq);
        ImageBoardVO updatedBoard = imageBoardService.updateImageBoard(imageBoard);
        return ResponseEntity.ok(updatedBoard);
    }

    /**
     * 이미지 게시판 삭제
     * @param imgBrdSeq 이미지 게시판 시퀀스
     * @return 삭제 결과
     */
    @DeleteMapping("/{imgBrdSeq}")
    public ResponseEntity<Map<String, Object>> deleteImageBoard(@PathVariable Long imgBrdSeq) {
        int deleted = imageBoardService.deleteImageBoard(imgBrdSeq);
        
        Map<String, Object> response = new HashMap<>();
        response.put("deleted", deleted > 0);
        response.put("imgBrdSeq", imgBrdSeq);

        return ResponseEntity.ok(response);
    }

    /**
     * 최근 등록된 이미지 게시판 목록 조회
     * @param limit 조회 개수 제한
     * @return 이미지 게시판 목록
     */
    @GetMapping("/recent")
    public ResponseEntity<List<ImageBoardVO>> getRecentImageBoardList(
            @RequestParam(defaultValue = "5") int limit) {
        List<ImageBoardVO> list = imageBoardService.getRecentImageBoardList(limit);
        return ResponseEntity.ok(list);
    }

    /**
     * 이미지 게시판 제목 중복 확인
     * @param imgBrdTitl 이미지 게시판 제목
     * @return 중복 여부
     */
    @GetMapping("/check-title")
    public ResponseEntity<Map<String, Object>> checkImageBoardTitle(
            @RequestParam String imgBrdTitl) {
        boolean isDuplicate = imageBoardService.isImageBoardTitleDuplicate(imgBrdTitl);
        
        Map<String, Object> response = new HashMap<>();
        response.put("isDuplicate", isDuplicate);
        response.put("imgBrdTitl", imgBrdTitl);

        return ResponseEntity.ok(response);
    }

    /**
     * 이미지 게시판 선택 통계 조회
     * @param imgBrdSeq 이미지 게시판 시퀀스
     * @return 선택 통계 정보
     */
    @GetMapping("/{imgBrdSeq}/statistics")
    public ResponseEntity<Map<String, Object>> getImageBoardStatistics(@PathVariable Long imgBrdSeq) {
        int selectionCount = imageBoardService.getImageBoardSelectionCount(imgBrdSeq);
        
        Map<String, Object> response = new HashMap<>();
        response.put("imgBrdSeq", imgBrdSeq);
        response.put("selectionCount", selectionCount);

        return ResponseEntity.ok(response);
    }
} 