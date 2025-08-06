package com.ERI.demo.controller;

import com.ERI.demo.service.BoardFileAttachService;
import com.ERI.demo.vo.BoardFileAttachVO;
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
 * 게시판 파일 첨부 REST 컨트롤러
 */
@RestController
@RequestMapping("/api/board-file-attach")
@CrossOrigin(origins = "*")
public class BoardFileAttachController {

    @Autowired
    private BoardFileAttachService boardFileAttachService;

    @Value("${file.upload.path:/uploads/board}")
    private String uploadPath;

    /**
     * 파일 첨부 목록 조회 (페이징/검색)
     * @param page 페이지 번호
     * @param size 페이지 크기
     * @param searchType 검색 타입
     * @param searchKeyword 검색 키워드
     * @param brdSeq 게시글 시퀀스
     * @param sortBy 정렬 필드
     * @param sortDirection 정렬 방향
     * @return 파일 첨부 목록 및 페이징 정보
     */
    @GetMapping("/list")
    public ResponseEntity<Map<String, Object>> getBoardFileAttachList(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String searchType,
            @RequestParam(required = false) String searchKeyword,
            @RequestParam(required = false) Long brdSeq,
            @RequestParam(defaultValue = "FILE_SEQ") String sortBy,
            @RequestParam(defaultValue = "DESC") String sortDirection) {

        BoardFileAttachVO boardFileAttach = new BoardFileAttachVO();
        boardFileAttach.setPage(page);
        boardFileAttach.setSize(size);
        boardFileAttach.setSearchType(searchType);
        boardFileAttach.setSearchKeyword(searchKeyword);
        boardFileAttach.setBrdSeq(brdSeq);
        boardFileAttach.setSortBy(sortBy);
        boardFileAttach.setSortDirection(sortDirection);

        List<BoardFileAttachVO> list = boardFileAttachService.getBoardFileAttachList(boardFileAttach);
        int total = boardFileAttachService.getBoardFileAttachCount(boardFileAttach);

        Map<String, Object> response = new HashMap<>();
        response.put("list", list);
        response.put("total", total);
        response.put("page", page);
        response.put("size", size);
        response.put("totalPages", (int) Math.ceil((double) total / size));

        return ResponseEntity.ok(response);
    }

    /**
     * 파일 첨부 상세 조회
     * @param fileSeq 파일 시퀀스
     * @return 파일 첨부 정보
     */
    @GetMapping("/{fileSeq}")
    public ResponseEntity<BoardFileAttachVO> getBoardFileAttachBySeq(@PathVariable Long fileSeq) {
        BoardFileAttachVO boardFileAttach = boardFileAttachService.getBoardFileAttachBySeq(fileSeq);
        if (boardFileAttach == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(boardFileAttach);
    }

    /**
     * 게시글별 파일 첨부 목록 조회
     * @param brdSeq 게시글 시퀀스
     * @return 파일 첨부 목록
     */
    @GetMapping("/board/{brdSeq}")
    public ResponseEntity<List<BoardFileAttachVO>> getBoardFileAttachByBrdSeq(@PathVariable Long brdSeq) {
        List<BoardFileAttachVO> list = boardFileAttachService.getBoardFileAttachByBrdSeq(brdSeq);
        return ResponseEntity.ok(list);
    }

    /**
     * 파일 업로드
     * @param file 업로드된 파일
     * @param brdSeq 게시글 시퀀스
     * @param regId 등록자 ID
     * @return 업로드된 파일 정보
     */
    @PostMapping("/upload")
    public ResponseEntity<BoardFileAttachVO> uploadFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam("brdSeq") Long brdSeq,
            @RequestParam("regId") String regId) {

        try {
            BoardFileAttachVO uploadedFile = boardFileAttachService.uploadFile(file, brdSeq, regId, uploadPath);
            return ResponseEntity.ok(uploadedFile);
        } catch (IOException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * 파일 다운로드
     * @param fileSeq 파일 시퀀스
     * @return 파일 리소스
     */
    @GetMapping("/download/{fileSeq}")
    public ResponseEntity<Resource> downloadFile(@PathVariable Long fileSeq) {
        BoardFileAttachVO boardFileAttach = boardFileAttachService.getBoardFileAttachBySeq(fileSeq);
        if (boardFileAttach == null) {
            return ResponseEntity.notFound().build();
        }

        try {
            Path filePath = Paths.get(boardFileAttach.getFilePath(), boardFileAttach.getStorFileNm());
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists() && resource.isReadable()) {
                // 다운로드 횟수 증가
                boardFileAttachService.incrementDownloadCount(fileSeq);

                return ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_OCTET_STREAM)
                        .header(HttpHeaders.CONTENT_DISPOSITION, 
                                "attachment; filename=\"" + boardFileAttach.getOrigFileNm() + "\"")
                        .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (MalformedURLException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * 파일 미리보기 (이미지 파일)
     * @param fileSeq 파일 시퀀스
     * @return 이미지 리소스
     */
    @GetMapping("/preview/{fileSeq}")
    public ResponseEntity<Resource> previewFile(@PathVariable Long fileSeq) {
        BoardFileAttachVO boardFileAttach = boardFileAttachService.getBoardFileAttachBySeq(fileSeq);
        if (boardFileAttach == null) {
            return ResponseEntity.notFound().build();
        }

        // 이미지 파일인지 확인
        if (!boardFileAttach.isImageFile()) {
            return ResponseEntity.badRequest().build();
        }

        try {
            Path filePath = Paths.get(boardFileAttach.getFilePath(), boardFileAttach.getStorFileNm());
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists() && resource.isReadable()) {
                return ResponseEntity.ok()
                        .contentType(MediaType.parseMediaType(boardFileAttach.getFileTyp()))
                        .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (MalformedURLException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * 파일 첨부 수정
     * @param fileSeq 파일 시퀀스
     * @param boardFileAttach 파일 첨부 정보
     * @return 수정된 파일 첨부 정보
     */
    @PutMapping("/{fileSeq}")
    public ResponseEntity<BoardFileAttachVO> updateBoardFileAttach(
            @PathVariable Long fileSeq,
            @RequestBody BoardFileAttachVO boardFileAttach) {

        boardFileAttach.setFileSeq(fileSeq);
        BoardFileAttachVO updatedFile = boardFileAttachService.updateBoardFileAttach(boardFileAttach);
        return ResponseEntity.ok(updatedFile);
    }

    /**
     * 파일 첨부 삭제
     * @param fileSeq 파일 시퀀스
     * @return 삭제 결과
     */
    @DeleteMapping("/{fileSeq}")
    public ResponseEntity<Map<String, Object>> deleteBoardFileAttach(@PathVariable Long fileSeq) {
        BoardFileAttachVO boardFileAttach = boardFileAttachService.getBoardFileAttachBySeq(fileSeq);
        if (boardFileAttach == null) {
            return ResponseEntity.notFound().build();
        }

        // 물리적 파일 삭제
        boolean physicalDeleted = boardFileAttachService.deletePhysicalFile(
                boardFileAttach.getFilePath(), 
                boardFileAttach.getStorFileNm()
        );

        // 논리적 삭제
        int deleted = boardFileAttachService.deleteBoardFileAttach(fileSeq);

        Map<String, Object> response = new HashMap<>();
        response.put("deleted", deleted > 0);
        response.put("physicalDeleted", physicalDeleted);

        return ResponseEntity.ok(response);
    }

    /**
     * 게시글별 파일 첨부 삭제
     * @param brdSeq 게시글 시퀀스
     * @return 삭제 결과
     */
    @DeleteMapping("/board/{brdSeq}")
    public ResponseEntity<Map<String, Object>> deleteBoardFileAttachByBrdSeq(@PathVariable Long brdSeq) {
        List<BoardFileAttachVO> fileList = boardFileAttachService.getBoardFileAttachByBrdSeq(brdSeq);
        
        // 물리적 파일 삭제
        int physicalDeleted = 0;
        for (BoardFileAttachVO file : fileList) {
            if (boardFileAttachService.deletePhysicalFile(file.getFilePath(), file.getStorFileNm())) {
                physicalDeleted++;
            }
        }

        // 논리적 삭제
        int deleted = boardFileAttachService.deleteBoardFileAttachByBrdSeq(brdSeq);

        Map<String, Object> response = new HashMap<>();
        response.put("deleted", deleted);
        response.put("physicalDeleted", physicalDeleted);
        response.put("totalFiles", fileList.size());

        return ResponseEntity.ok(response);
    }

    /**
     * 이미지 링크 정보 업데이트
     * @param fileSeq 파일 시퀀스
     * @param imgLinks 이미지 링크 정보
     * @return 업데이트 결과
     */
    @PutMapping("/{fileSeq}/img-links")
    public ResponseEntity<Map<String, Object>> updateImgLinks(
            @PathVariable Long fileSeq,
            @RequestBody Map<String, String> request) {

        String imgLinks = request.get("imgLinks");
        int updated = boardFileAttachService.updateImgLinks(fileSeq, imgLinks);

        Map<String, Object> response = new HashMap<>();
        response.put("updated", updated > 0);

        return ResponseEntity.ok(response);
    }
} 