package com.ibkcloud.eoc.controller;

import com.ibkcloud.eoc.cmm.dto.PageRequest;
import com.ibkcloud.eoc.cmm.dto.Page;
import com.ibkcloud.eoc.cmm.exception.BizException;
import com.ibkcloud.eoc.service.ImageBoardService;
import com.ibkcloud.eoc.controller.vo.ImageBoardInVo;
import com.ibkcloud.eoc.controller.vo.ImageBoardOutVo;
import com.ibkcloud.eoc.controller.vo.ImageBoardSearchInVo;
import com.ibkcloud.eoc.controller.vo.ImageBoardSearchOutVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 이미지 게시판 REST 컨트롤러
 */
@Slf4j
@RestController
@RequestMapping("/api/image-board")
@RequiredArgsConstructor
public class ImageBoardController {

    private final ImageBoardService imageBoardService;

    /**
     * 이미지 게시판 목록 조회 (페이징/검색)
     */
    @GetMapping("/list")
    public ResponseEntity<ImageBoardSearchOutVo> inqImageBoardList(
            @RequestParam(defaultValue = "1") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(required = false) String searchType,
            @RequestParam(required = false) String searchKeyword,
            @RequestParam(defaultValue = "IMG_BRD_SEQ") String sortBy,
            @RequestParam(defaultValue = "DESC") String sortDirection) {

        try {
            ImageBoardSearchInVo searchVo = new ImageBoardSearchInVo();
            searchVo.setPageNo(pageNo);
            searchVo.setPageSize(pageSize);
            searchVo.setSearchType(searchType);
            searchVo.setSearchKeyword(searchKeyword);
            searchVo.setSortBy(sortBy);
            searchVo.setSortDirection(sortDirection);

            Page<ImageBoardOutVo> result = imageBoardService.inqImageBoardList(searchVo);

            ImageBoardSearchOutVo response = new ImageBoardSearchOutVo();
            response.setSuccess(true);
            response.setData(result.getContents());
            response.setCount(result.getTtalDataNbi());
            response.setPageNo(result.getPageNo());
            response.setPageSize(result.getPageSize());
            response.setTtalPageNbi(result.getTtalPageNbi());
            response.setSearchType(searchType);
            response.setSearchKeyword(searchKeyword);
            response.setSortBy(sortBy);
            response.setSortDirection(sortDirection);
            response.setMessage("이미지 게시판 목록을 조회했습니다.");

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("이미지 게시판 목록 조회 실패", e);
            throw new BizException("IB001", "이미지 게시판 목록 조회에 실패했습니다: " + e.getMessage());
        }
    }

    /**
     * 이미지 게시판 상세 조회
     */
    @GetMapping("/{imgBrdSeq}")
    public ResponseEntity<ImageBoardOutVo> inqImageBoardBySeq(@PathVariable Long imgBrdSeq) {
        try {
            ImageBoardOutVo imageBoard = imageBoardService.inqImageBoardBySeq(imgBrdSeq);
            
            if (imageBoard == null) {
                throw new BizException("IB002", "이미지 게시판을 찾을 수 없습니다.");
            }
            
            imageBoard.setSuccess(true);
            imageBoard.setMessage("이미지 게시판을 조회했습니다.");
            
            return ResponseEntity.ok(imageBoard);
        } catch (BizException e) {
            throw e;
        } catch (Exception e) {
            log.error("이미지 게시판 상세 조회 실패", e);
            throw new BizException("IB003", "이미지 게시판 상세 조회에 실패했습니다: " + e.getMessage());
        }
    }

    /**
     * 이미지 게시판과 이미지 파일들을 함께 조회
     */
    @GetMapping("/{imgBrdSeq}/with-files")
    public ResponseEntity<ImageBoardOutVo> inqImageBoardWithFiles(@PathVariable Long imgBrdSeq) {
        try {
            ImageBoardOutVo imageBoard = imageBoardService.inqImageBoardWithFiles(imgBrdSeq);
            
            if (imageBoard == null) {
                throw new BizException("IB002", "이미지 게시판을 찾을 수 없습니다.");
            }
            
            imageBoard.setSuccess(true);
            imageBoard.setMessage("이미지 게시판과 파일을 조회했습니다.");
            
            return ResponseEntity.ok(imageBoard);
        } catch (BizException e) {
            throw e;
        } catch (Exception e) {
            log.error("이미지 게시판 파일 조회 실패", e);
            throw new BizException("IB004", "이미지 게시판 파일 조회에 실패했습니다: " + e.getMessage());
        }
    }

    /**
     * 이미지 게시판 등록
     */
    @PostMapping
    public ResponseEntity<ImageBoardOutVo> rgsnImageBoard(@RequestBody ImageBoardInVo imageBoard) {
        try {
            // 제목 중복 확인
            if (imageBoardService.inqImageBoardTitleDuplicate(imageBoard.getImgBrdTitl())) {
                throw new BizException("IB005", "이미지 게시판 제목이 중복됩니다.");
            }
            
            ImageBoardOutVo result = imageBoardService.rgsnImageBoard(imageBoard);
            
            result.setSuccess(true);
            result.setMessage("이미지 게시판이 성공적으로 등록되었습니다.");
            
            return ResponseEntity.ok(result);
        } catch (BizException e) {
            throw e;
        } catch (Exception e) {
            log.error("이미지 게시판 등록 실패", e);
            throw new BizException("IB006", "이미지 게시판 등록에 실패했습니다: " + e.getMessage());
        }
    }

    /**
     * 이미지 게시판 수정
     */
    @PutMapping("/{imgBrdSeq}")
    public ResponseEntity<ImageBoardOutVo> mdfcImageBoard(
            @PathVariable Long imgBrdSeq,
            @RequestBody ImageBoardInVo imageBoard) {

        try {
            imageBoard.setImgBrdSeq(imgBrdSeq);
            ImageBoardOutVo result = imageBoardService.mdfcImageBoard(imageBoard);
            
            result.setSuccess(true);
            result.setMessage("이미지 게시판이 성공적으로 수정되었습니다.");
            
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            log.error("이미지 게시판 수정 실패", e);
            throw new BizException("IB007", "이미지 게시판 수정에 실패했습니다: " + e.getMessage());
        }
    }

    /**
     * 이미지 게시판 삭제
     */
    @DeleteMapping("/{imgBrdSeq}")
    public ResponseEntity<ImageBoardOutVo> delImageBoard(@PathVariable Long imgBrdSeq) {
        try {
            ImageBoardOutVo result = imageBoardService.delImageBoard(imgBrdSeq);
            
            result.setSuccess(true);
            result.setMessage("이미지 게시판이 성공적으로 삭제되었습니다.");
            
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            log.error("이미지 게시판 삭제 실패", e);
            throw new BizException("IB008", "이미지 게시판 삭제에 실패했습니다: " + e.getMessage());
        }
    }

    /**
     * 최근 등록된 이미지 게시판 목록 조회
     */
    @GetMapping("/recent")
    public ResponseEntity<List<ImageBoardOutVo>> inqRecentImageBoardList(
            @RequestParam(defaultValue = "5") int limit) {
        try {
            List<ImageBoardOutVo> list = imageBoardService.inqRecentImageBoardList(limit);
            return ResponseEntity.ok(list);
        } catch (Exception e) {
            log.error("최근 이미지 게시판 목록 조회 실패", e);
            throw new BizException("IB009", "최근 이미지 게시판 목록 조회에 실패했습니다: " + e.getMessage());
        }
    }

    /**
     * 이미지 게시판 제목 중복 확인
     */
    @GetMapping("/check-title")
    public ResponseEntity<ImageBoardOutVo> inqImageBoardTitleDuplicate(
            @RequestParam String imgBrdTitl) {
        try {
            boolean isDuplicate = imageBoardService.inqImageBoardTitleDuplicate(imgBrdTitl);
            
            ImageBoardOutVo response = new ImageBoardOutVo();
            response.setSuccess(true);
            response.setIsDuplicate(isDuplicate);
            response.setImgBrdTitl(imgBrdTitl);
            response.setMessage(isDuplicate ? "제목이 중복됩니다." : "사용 가능한 제목입니다.");
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("이미지 게시판 제목 중복 확인 실패", e);
            throw new BizException("IB010", "이미지 게시판 제목 중복 확인에 실패했습니다: " + e.getMessage());
        }
    }

    /**
     * 이미지 게시판 선택 통계 조회
     */
    @GetMapping("/{imgBrdSeq}/statistics")
    public ResponseEntity<ImageBoardOutVo> inqImageBoardStatistics(@PathVariable Long imgBrdSeq) {
        try {
            ImageBoardOutVo statistics = imageBoardService.inqImageBoardStatistics(imgBrdSeq);
            
            statistics.setSuccess(true);
            statistics.setMessage("이미지 게시판 통계를 조회했습니다.");
            
            return ResponseEntity.ok(statistics);
        } catch (Exception e) {
            log.error("이미지 게시판 통계 조회 실패", e);
            throw new BizException("IB011", "이미지 게시판 통계 조회에 실패했습니다: " + e.getMessage());
        }
    }
} 