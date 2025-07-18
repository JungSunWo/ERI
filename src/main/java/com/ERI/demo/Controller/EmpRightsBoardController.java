package com.ERI.demo.Controller;

import com.ERI.demo.dto.PageRequestDto;
import com.ERI.demo.dto.PageResponseDto;
import com.ERI.demo.service.EmpRightsBoardService;
import com.ERI.demo.vo.EmpRightsBoardVO;
import com.ERI.demo.vo.EmpRightsCommentVO;
import com.ERI.demo.vo.EmpRightsLikeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;
import java.util.List;

/**
 * 직원권익게시판 API 컨트롤러
 */
@RestController
@RequestMapping("/api/emp-rights-board")
public class EmpRightsBoardController {

    @Autowired
    private EmpRightsBoardService empRightsBoardService;

    /**
     * 게시글 목록 조회 (페이징)
     */
    @GetMapping
    public ResponseEntity<PageResponseDto<EmpRightsBoardVO>> getBoardList(
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

        PageResponseDto<EmpRightsBoardVO> result = empRightsBoardService.getBoardList(pageRequest, searchCondition);
        return ResponseEntity.ok(result);
    }

    /**
     * 게시글 상세 조회
     */
    @GetMapping("/{seq}")
    public ResponseEntity<EmpRightsBoardVO> getBoardDetail(@PathVariable Long seq) {
        try {
            // 조회수 증가
            empRightsBoardService.incrementViewCount(seq);
            
            // 게시글 상세 조회
            EmpRightsBoardVO board = empRightsBoardService.getBoardBySeq(seq);
            if (board != null) {
                return ResponseEntity.ok(board);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * 게시글 등록
     */
    @PostMapping
    public ResponseEntity<EmpRightsBoardVO> createBoard(@RequestBody EmpRightsBoardVO board, HttpSession session) {
        try {
            String sessionEmpId = (String) session.getAttribute("encryptEmpNo");
            if (sessionEmpId == null) {
                return ResponseEntity.badRequest().build();
            }
            
            board.setRgstEmpId(sessionEmpId);
            board.setUpdtEmpId(sessionEmpId);
            
            EmpRightsBoardVO createdBoard = empRightsBoardService.createBoard(board);
            return ResponseEntity.ok(createdBoard);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * 게시글 수정
     */
    @PutMapping("/{seq}")
    public ResponseEntity<EmpRightsBoardVO> updateBoard(@PathVariable Long seq, @RequestBody EmpRightsBoardVO board, HttpSession session) {
        try {
            String sessionEmpId = (String) session.getAttribute("encryptEmpNo");
            if (sessionEmpId == null) {
                return ResponseEntity.badRequest().build();
            }
            
            board.setSeq(seq);
            board.setUpdtEmpId(sessionEmpId);
            
            EmpRightsBoardVO updatedBoard = empRightsBoardService.updateBoard(board);
            if (updatedBoard != null) {
                return ResponseEntity.ok(updatedBoard);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * 게시글 삭제
     */
    @DeleteMapping("/{seq}")
    public ResponseEntity<Void> deleteBoard(@PathVariable Long seq, HttpSession session) {
        try {
            String sessionEmpId = (String) session.getAttribute("encryptEmpNo");
            if (sessionEmpId == null) {
                return ResponseEntity.badRequest().build();
            }
            
            boolean deleted = empRightsBoardService.deleteBoard(seq, sessionEmpId);
            if (deleted) {
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * 공지글 목록 조회
     */
    @GetMapping("/notices")
    public ResponseEntity<List<EmpRightsBoardVO>> getNoticeList() {
        try {
            List<EmpRightsBoardVO> notices = empRightsBoardService.getNoticeList();
            return ResponseEntity.ok(notices);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * 내가 작성한 게시글 목록 조회
     */
    @GetMapping("/my")
    public ResponseEntity<PageResponseDto<EmpRightsBoardVO>> getMyBoardList(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            HttpSession session) {

        String sessionEmpId = (String) session.getAttribute("encryptEmpNo");
        if (sessionEmpId == null) {
            return ResponseEntity.badRequest().build();
        }

        PageRequestDto pageRequest = new PageRequestDto();
        pageRequest.setPage(page);
        pageRequest.setSize(size);

        PageResponseDto<EmpRightsBoardVO> result = empRightsBoardService.getMyBoardList(sessionEmpId, pageRequest);
        return ResponseEntity.ok(result);
    }
} 